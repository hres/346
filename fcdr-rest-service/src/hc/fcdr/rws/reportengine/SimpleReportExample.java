package hc.fcdr.rws.reportengine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class SimpleReportExample
{

    public static void main(final String[] args)
    {
        Connection connection = null;
        try
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/fcdrdb", "fcdruser",
                    "fcdruser");
        }
        catch (final SQLException e)
        {
            e.printStackTrace();
            return;
        }
        catch (final ClassNotFoundException e)
        {
            e.printStackTrace();
            return;
        }

        final JasperReportBuilder report = DynamicReports.report();// a new report
        report.columns(
                Columns.column("Id", "classification_id",
                        DataTypes.integerType()).setHorizontalAlignment(
                                HorizontalAlignment.LEFT),
                Columns.column("Number", "classification_number",
                        DataTypes.stringType()),
                Columns.column("Type", "classification_type",
                        DataTypes.stringType()).setHorizontalAlignment(
                                HorizontalAlignment.LEFT)).title(// title of the report
                                        Components.text(
                                                "SimpleReportExample").setHorizontalAlignment(
                                                        HorizontalAlignment.CENTER)).pageFooter(
                                                                Components.pageXofY())// show page number on the page
                                                                                      // footer
                .setDataSource(
                        "select classification_id, classification_number, classification_type from fcdrschema.classification",
                        connection);

        try
        {
            report.show();// show the report
            report.toPdf(new FileOutputStream("/home/zoltanh/report.pdf"));// export the report to a pdf file
        }
        catch (final DRException e)
        {
            e.printStackTrace();
        }
        catch (final FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
