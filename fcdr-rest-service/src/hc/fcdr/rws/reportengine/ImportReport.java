package hc.fcdr.rws.reportengine;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import hc.fcdr.rws.importer.ImportReportDetailRow;
import hc.fcdr.rws.importer.ImportStatistics;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class ImportReport
{
    private ImportStatistics importStatistics;

    private final String     REPORT_DIRECTORY_ROOT = (!System.getProperty(
            "java.io.tmpdir").endsWith(File.separator)
                    ? (System.getProperty("java.io.tmpdir") + File.separator)
                    : System.getProperty("java.io.tmpdir"));

    private final String     REPORT_FILE           = REPORT_DIRECTORY_ROOT
            + "fcdrSalesImportReport.pdf";

    public ImportReport(ImportStatistics importStatistics)
    {
        this.importStatistics = importStatistics;
        build();
    }

    private void build()
    {
        try
        {

            report().setTemplate(Templates.reportTemplate)
                    .setPageFormat(PageType.A5)
                    .columns(col.column("Item ID", "itemId", type.stringType()),
                            col.column("Description", "description",
                                    type.stringType()))
                    .title(Templates.createTitleComponent(
                            "Sales Import Report"),
                            cmp.horizontalList(
                                    cmp.text("Import Date: ")
                                       .setFixedWidth(200),
                                    cmp.text(
                                            importStatistics.getImportDateTime())),
                            cmp.horizontalList(
                                    cmp.text("Total Loading Time (sec): ")
                                       .setFixedWidth(200),
                                    cmp.text(
                                            importStatistics.getTotalLoadingTime())),
                            cmp.horizontalList(
                                    cmp.text("Total Processing Time (sec): ")
                                       .setFixedWidth(200),
                                    cmp.text(
                                            importStatistics.getTotalProcessingTime())),
                            cmp.horizontalList(
                                    cmp.text("Number of Records Processed: ")
                                       .setFixedWidth(200),
                                    cmp.text(
                                            importStatistics.getNumberOfRecordsProcessed())),
                            cmp.horizontalList(
                                    cmp.text("Number of Invalid Records: ")
                                       .setFixedWidth(200),
                                    cmp.text(
                                            importStatistics.getNumberOfInvalidRecords())),
                            cmp.horizontalList(cmp.text("").setFixedWidth(300),
                                    cmp.text("")))

                    .pageFooter(Templates.footerComponent)
                    .setDataSource(createDataSource())
                    .toPdf(new FileOutputStream(REPORT_FILE));
        }
        catch (DRException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private JRDataSource createDataSource()
    {
        DRDataSource dataSource = new DRDataSource("itemId", "description");

        for (ImportReportDetailRow importReportDetailRow : importStatistics.getImportReportDetailRowList())
            dataSource.add(importReportDetailRow.getItemId(),
                    importReportDetailRow.getSalesDescription());

        return dataSource;
    }

}