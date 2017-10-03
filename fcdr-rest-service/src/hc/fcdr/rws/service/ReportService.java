package hc.fcdr.rws.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.db.PgConnectionPool;
import hc.fcdr.rws.db.ProductDao;
import hc.fcdr.rws.domain.Product;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.ProductDataResponse;
import hc.fcdr.rws.model.ProductRequest;
import hc.fcdr.rws.model.ReportDataResponse;
import hc.fcdr.rws.model.ReportProductRequest;
import hc.fcdr.rws.util.ContextManager;

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

@Path("/ReportService")
public class ReportService extends Application
{
    static ProductDao productDao = null;

    @PostConstruct
    public static void initialize()
    {
        if (productDao == null)
        {
            PgConnectionPool pgConnectionPool = new PgConnectionPool();
            pgConnectionPool.initialize();

            try
            {
                productDao = new ProductDao(pgConnectionPool.getConnection(),
                        ContextManager.getJndiValue("SCHEMA"));
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    @GET
    @Path("/productreport")
    // @Produces(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.TEXT_HTML)
    /// @Produces("application/pdf")
    // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getProductReport()
            // final ReportProductRequest reportProductRequest)
            throws SQLException, IOException, Exception
    {
        ReportDataResponse entity = new ReportDataResponse();

        try
        {
            /// if (productDao != null)
            /// entity = productDao.getReportResponse(reportProductRequest);

            generateReport();

            File file = new File("/home/zoltanh/report.pdf");
            // FileInputStream fileInputStream = new FileInputStream(file);
            // javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok(
            // (Object) fileInputStream);
            // responseBuilder.type("application/pdf");
            // ///responseBuilder.header("Content-Disposition", "filename=report.pdf");
            // responseBuilder.header("Content-Disposition", "attachment; filename=report.pdf");
            //
            // return responseBuilder.build();

            // entity = new ReportDataResponse(ResponseCodes.OK.getCode(),file,
            // ResponseCodes.OK.getMessage());

            ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition",
                    "attachment;filename=report.pdf");
            return response.build();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK).type(
                MediaType.APPLICATION_JSON).entity(entity).build();
    }

    @OPTIONS
    @Path("/products")
    @Produces(MediaType.APPLICATION_XML)
    public String getSupportedOperations()
    {
        return "<operations>GET, PUT, POST, DELETE</operations>";
    }

    public static void generateReport()
    {
        Connection connection = null;
        try
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/fcdrdb", "fcdruser",
                    "fcdruser");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return;
        }

        JasperReportBuilder report = DynamicReports.report();// a new report
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
            /// report.show();// show the report
            report.toPdf(new FileOutputStream("/home/zoltanh/report.pdf"));// export the report to a pdf file
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
}
