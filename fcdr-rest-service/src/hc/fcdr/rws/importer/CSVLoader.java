package hc.fcdr.rws.importer;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;

import com.opencsv.bean.CsvToBeanBuilder;

import hc.fcdr.rws.db.ProductDao;
import hc.fcdr.rws.db.SalesDao;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.importer.ImportSalesData;
import hc.fcdr.rws.util.DateUtil;

public class CSVLoader
{
    private static final Logger logger =
            Logger.getLogger(CSVLoader.class.getName());
    private char                separator;
    private final SalesDao      salesDao;
    private final ProductDao    productDao;

    /**
     * Public constructor to build CSVLoader object with Connection details. The connection is closed on success or
     * failure.
     * 
     * @param connection
     */
    public CSVLoader(final Connection connection, final String schema)
    {
        separator = ',';
        salesDao = new SalesDao(connection, schema);
        productDao = new ProductDao(connection, schema);
    }

    public ImportStatistics loadCSV(final String csvFile,
            final String tableName, final boolean truncateBeforeLoad)
            throws Exception
    {
        final ImportStatistics importStatistics = new ImportStatistics();
        importStatistics.setImportDateTime(DateUtil.getReportDateTimeString());

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("Loading of sales data started...");

        List<ImportSalesData> importSalesDataList =
                new ArrayList<ImportSalesData>();

        importSalesDataList =
                new CsvToBeanBuilder(new FileReader(csvFile))
                        .withType(ImportSalesData.class).withSkipLines(1)
                        .build().parse();

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

    private void loadCSV(final List<ImportSalesData> importSalesDataList,
            final ImportStatistics importStatistics) throws Exception
    {
        final Integer numberOfRecordsProcessed = importSalesDataList.size();
        Integer numberOfInvalidRecords = 0;
        final List<ImportReportDetailRow> importReportDetailRowList =
                new ArrayList<ImportReportDetailRow>();

        List<ImportSalesData> importSalesDataListUpc =
                new ArrayList<ImportSalesData>();
        final Map<String, List<ImportSalesData>> m1 =
                new HashMap<String, List<ImportSalesData>>();

        for (final ImportSalesData importSalesData : importSalesDataList)
        {
            if (!importSalesData.isValidRecord())
            {
                numberOfInvalidRecords++;
                System.out.println(
                        "---Invalid record: " + importSalesData.getItemId());
                continue;
            }

            final List<Object> csvFieldList = importSalesData.getCsvFieldList();

            final String salesUpc = (String) csvFieldList.get(1);
            final Integer productId = salesDao.checkBySalesUpc(salesUpc);
            final Double classificationNumber = (Double) csvFieldList.get(25);
            final String classificationType = (String) csvFieldList.get(26);

            if (productId != null)
            {
                csvFieldList.add(1); // number of units ??
                csvFieldList.add(DateUtil.getCurrentTimeStamp()); // creation_date
                csvFieldList.add(DateUtil.getCurrentTimeStamp()); // last_edit_date
                csvFieldList.add("Zoltan1"); // edited by ??

                csvFieldList.add(productId);
                salesDao.insert(csvFieldList);

                // update product
                final List<Object> fieldsForProductUpdateList =
                        importSalesData.getFieldsForProductUpdateList();
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

            final ImportReportDetailRow importReportDetailRow =
                    new ImportReportDetailRow(
                            importSalesData.getItemId().toString(),
                            importSalesData.getSalesDescription());
            importReportDetailRowList.add(importReportDetailRow);

        } // end for

        final Map<Double, List<ImportSalesData>> m2 =
                new HashMap<Double, List<ImportSalesData>>();

        for (final Map.Entry<String, List<ImportSalesData>> entry : m1
                .entrySet())
        {
            /// m1.forEach((k, v) -> {

            final List<ImportSalesData> v = entry.getValue();

            if (v.size() > 1)
                insertProductSalesRecords(v);
            else
            {
                // Collect the remaining records by program_grouping
                final Double productGrouping = v.get(0).getProductGrouping();
                List<ImportSalesData> importSalesDataListProductGrouping =
                        new ArrayList<ImportSalesData>();

                if ((productGrouping != null)
                        && (m2.containsKey(productGrouping)))
                {
                    importSalesDataListProductGrouping =
                            m2.get(productGrouping);
                    importSalesDataListProductGrouping.add(v.get(0));
                    m2.replace(productGrouping,
                            importSalesDataListProductGrouping);
                }
                else if ((productGrouping != null)
                        && !(m2.containsKey(productGrouping)))
                {
                    // One sales record for one product.
                    importSalesDataListProductGrouping =
                            new ArrayList<ImportSalesData>();
                    importSalesDataListProductGrouping.add(v.get(0));
                    m2.put(productGrouping, importSalesDataListProductGrouping);
                }
                else
                    insertProductSalesRecords(v);
            }
            /// });
        }

        for (final Map.Entry<Double, List<ImportSalesData>> entry : m2
                .entrySet())
        {
            final List<ImportSalesData> v = entry.getValue();
            /// m2.forEach((k, v) -> {
            insertProductSalesRecords(v);
            /// });
        }

        importStatistics.setNumberOfRecordsProcessed(numberOfRecordsProcessed);
        importStatistics.setNumberOfInvalidRecords(numberOfInvalidRecords);
        importStatistics
                .setImportReportDetailRowList(importReportDetailRowList);
    }

    private void insertProductSalesRecords(
            final List<ImportSalesData> importSalesDataList) throws SQLException
    {
        // Get the first record just for the product.
        final ImportSalesData importSalesData = importSalesDataList.get(0);

        // create product, then create sales record
        final String productDescription =
                ((importSalesData.getSalesProductDescription() != null)
                        && !importSalesData.getSalesProductDescription()
                                .isEmpty())
                                        ? importSalesData
                                                .getSalesProductDescription()
                                        : importSalesData.getSalesDescription();

        final List<Object> csvFieldList = importSalesData.getCsvFieldList();

        // create new product
        final List<Object> fieldsForProductInsertList = new ArrayList<Object>();
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
            final Double classificationNumber = (Double) csvFieldList.get(25);
            final String classificationType = (String) csvFieldList.get(26);
            productId =
                    productDao.insert(fieldsForProductInsertList,
                            classificationNumber, classificationType);
        }
        catch (final DaoException e)
        {
            e.printStackTrace();
        }

        try
        {
            for (final ImportSalesData importSalesData2 : importSalesDataList)
            {
                final List<Object> csvFieldList2 =
                        importSalesData2.getCsvFieldList();

                // create a new sales record for this product.
                csvFieldList2.add(1); // number of units
                csvFieldList2.add(DateUtil.getCurrentTimeStamp()); // creation_date
                csvFieldList2.add(DateUtil.getCurrentTimeStamp()); // last_edit_date
                csvFieldList2.add("Zoltan3"); // edited by
                csvFieldList2.add(productId); // TODO check fo null
                salesDao.insert(csvFieldList2);
            }
        }
        catch (final DaoException e)
        {
            e.printStackTrace();
        }
    }

    public char getSeparator()
    {
        return separator;
    }

    public void setSeparator(final char separator)
    {
        this.separator = separator;
    }

}
