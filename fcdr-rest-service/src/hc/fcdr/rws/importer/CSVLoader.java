package hc.fcdr.rws.importer;

import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.StopWatch;

import com.opencsv.bean.CsvToBeanBuilder;

import hc.fcdr.rws.db.ProductDao;
import hc.fcdr.rws.db.SalesDao;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.importer.ImportSalesData;
import hc.fcdr.rws.util.DateUtil;

import org.apache.log4j.Logger;

public class CSVLoader
{
    private static final Logger logger = Logger.getLogger(
            CSVLoader.class.getName());
    private char                separator;
    private SalesDao            salesDao;
    private ProductDao          productDao;

    /**
     * Public constructor to build CSVLoader object with Connection details. The connection is closed on success or
     * failure.
     * 
     * @param connection
     */
    public CSVLoader(Connection connection, String schema)
    {
        this.separator = ',';
        salesDao = new SalesDao(connection, schema);
        productDao = new ProductDao(connection, schema);
    }

    public ImportStatistics loadCSV(String csvFile, String tableName,
            boolean truncateBeforeLoad) throws Exception
    {
        ImportStatistics importStatistics = new ImportStatistics();
        importStatistics.setImportDateTime(DateUtil.getReportDateTimeString());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("Loading of sales data started...");

        List<ImportSalesData> importSalesDataList = new ArrayList<ImportSalesData>();

        importSalesDataList = new CsvToBeanBuilder(
                new FileReader(csvFile)).withType(ImportSalesData.class)
                                        .withSkipLines(1)
                                        .build()
                                        .parse();

        stopWatch.split();
        System.out.println("Total time spent on loading the sales data: "
                + (stopWatch.getSplitTime() / 1000) + " seconds.");
        importStatistics.setTotalLoadingTime((stopWatch.getSplitTime() / 1000));

        /// importSalesDataList.remove(importSalesDataList.size() - 1);

        System.out.println("Processing of sales data started...");

        loadCSV(importSalesDataList, importStatistics);

        stopWatch.stop();
        System.out.println("Total time spent on processing the sales data: "
                + (stopWatch.getTime() / 1000) + " seconds.");
        importStatistics.setTotalProcessingTime((stopWatch.getTime() / 1000));

        return importStatistics;
    }

    private void loadCSV(List<ImportSalesData> importSalesDataList,
            ImportStatistics importStatistics) throws Exception
    {
        Integer numberOfRecordsProcessed = importSalesDataList.size();
        Integer numberOfInvalidRecords = 0;
        List<ImportReportDetailRow> importReportDetailRowList = new ArrayList<ImportReportDetailRow>();

        List<ImportSalesData> importSalesDataListUpc = new ArrayList<ImportSalesData>();
        Map<String, List<ImportSalesData>> m1 = new HashMap<String, List<ImportSalesData>>();

        for (ImportSalesData importSalesData : importSalesDataList)
        {
            if (!importSalesData.isValidRecord())
            {
                numberOfInvalidRecords++;
                System.out.println(
                        "---Invalid record: " + importSalesData.getItemId());
                continue;
            }

            List<Object> csvFieldList = importSalesData.getCsvFieldList();

            String salesUpc = (String) csvFieldList.get(1);
            Integer productId = salesDao.checkBySalesUpc(salesUpc);
            Double classificationNumber = (Double) csvFieldList.get(25);
            String classificationType = (String) csvFieldList.get(26);

            if (productId != null)
            {
                csvFieldList.add(1); // number of units ??
                csvFieldList.add(DateUtil.getCurrentTimeStamp()); // creation_date
                csvFieldList.add(DateUtil.getCurrentTimeStamp()); // last_edit_date
                csvFieldList.add("Zoltan1"); // edited by ??

                csvFieldList.add(productId);
                salesDao.insert(csvFieldList);

                // update product
                List<Object> fieldsForProductUpdateList = importSalesData.getFieldsForProductUpdateList();
                fieldsForProductUpdateList.add(productId);
                productDao.update(fieldsForProductUpdateList,
                        classificationNumber, classificationType);
            }
            else // process records with same sales_upc; attach them to a brand new product
            if (m1.containsKey(salesUpc))
            {
                importSalesDataListUpc = m1.get(salesUpc);
                importSalesDataListUpc.add(importSalesData);
                m1.replace(salesUpc, importSalesDataListUpc);
            }
            else
            {
                importSalesDataListUpc = new ArrayList<ImportSalesData>();
                importSalesDataListUpc.add(importSalesData);
                m1.put(salesUpc, importSalesDataListUpc);
            }

            ImportReportDetailRow importReportDetailRow = new ImportReportDetailRow(
                    importSalesData.getItemId().toString(),
                    importSalesData.getSalesDescription());
            importReportDetailRowList.add(importReportDetailRow);

        } // end for

        Map<Double, List<ImportSalesData>> m2 = new HashMap<Double, List<ImportSalesData>>();

        for (Map.Entry<String, List<ImportSalesData>> entry : m1.entrySet())
        {
            /// m1.forEach((k, v) -> {

            List<ImportSalesData> v = entry.getValue();

            if (v.size() > 1)
                insertProductSalesRecords(v);
            else
            {
                // Collect the remaining records by program_grouping
                Double productGrouping = v.get(0).getProductGrouping();
                List<ImportSalesData> importSalesDataListProductGrouping = new ArrayList<ImportSalesData>();

                if ((productGrouping != null)
                        && (m2.containsKey(productGrouping)))
                {
                    importSalesDataListProductGrouping = m2.get(
                            productGrouping);
                    importSalesDataListProductGrouping.add(v.get(0));
                    m2.replace(productGrouping,
                            importSalesDataListProductGrouping);
                }
                else if ((productGrouping != null)
                        && !(m2.containsKey(productGrouping)))
                {
                    // One sales record for one product.
                    importSalesDataListProductGrouping = new ArrayList<ImportSalesData>();
                    importSalesDataListProductGrouping.add(v.get(0));
                    m2.put(productGrouping, importSalesDataListProductGrouping);
                }
                else
                    insertProductSalesRecords(v);
            }
            /// });
        }

        for (Map.Entry<Double, List<ImportSalesData>> entry : m2.entrySet())
        {
            List<ImportSalesData> v = entry.getValue();
            /// m2.forEach((k, v) -> {
            insertProductSalesRecords(v);
            /// });
        }

        importStatistics.setNumberOfRecordsProcessed(numberOfRecordsProcessed);
        importStatistics.setNumberOfInvalidRecords(numberOfInvalidRecords);
        importStatistics.setImportReportDetailRowList(
                importReportDetailRowList);
    }

    private void insertProductSalesRecords(
            List<ImportSalesData> importSalesDataList)
    {
        // Get the first record just for the product.
        ImportSalesData importSalesData = importSalesDataList.get(0);

        // create product, then create sales record
        String productDescription = ((importSalesData.getSalesProductDescription() != null)
                && !importSalesData.getSalesProductDescription().isEmpty())
                        ? importSalesData.getSalesProductDescription()
                        : importSalesData.getSalesDescription();

        List<Object> csvFieldList = importSalesData.getCsvFieldList();

        // create new product
        List<Object> fieldsForProductInsertList = new ArrayList<Object>();
        fieldsForProductInsertList.add(productDescription);
        fieldsForProductInsertList.add(csvFieldList.get(3)); // brand
        fieldsForProductInsertList.add("Canada"); // country
        fieldsForProductInsertList.add(csvFieldList.get(22)); // cluster number
        fieldsForProductInsertList.add(csvFieldList.get(27)); // comment
        fieldsForProductInsertList.add(csvFieldList.get(4)); // manufacturer
        fieldsForProductInsertList.add(777); // cnf code example
        fieldsForProductInsertList.add(DateUtil.getCurrentTimeStamp()); // creation date
        fieldsForProductInsertList.add(DateUtil.getCurrentTimeStamp()); // last edit date
        fieldsForProductInsertList.add("Zoltan2"); // edited by

        Integer productId = null;

        try
        {
            Double classificationNumber = (Double) csvFieldList.get(25);
            String classificationType = (String) csvFieldList.get(26);
            productId = productDao.insert(fieldsForProductInsertList,
                    classificationNumber, classificationType);
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }

        try
        {
            for (ImportSalesData importSalesData2 : importSalesDataList)
            {
                List<Object> csvFieldList2 = importSalesData2.getCsvFieldList();

                // create a new sales record for this product.
                csvFieldList2.add(1); // number of units
                csvFieldList2.add(DateUtil.getCurrentTimeStamp()); // creation_date
                csvFieldList2.add(DateUtil.getCurrentTimeStamp()); // last_edit_date
                csvFieldList2.add("Zoltan3"); // edited by
                csvFieldList2.add(productId); // TODO check fo null
                Integer salesId = salesDao.insert(csvFieldList2);
            }
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }
    }

    public char getSeparator()
    {
        return separator;
    }

    public void setSeparator(char separator)
    {
        this.separator = separator;
    }

}
