package hc.fcdr.rws.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.ws.rs.Produces;
import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.db.PgConnectionPool;
import hc.fcdr.rws.except.MailProcessorException;
import hc.fcdr.rws.importer.CSVLoader;
import hc.fcdr.rws.mail.MailProcessor;
import hc.fcdr.rws.model.ImportDataResponse;
import hc.fcdr.rws.model.ImportRequest;
import hc.fcdr.rws.model.ImportResponse;
import hc.fcdr.rws.util.ContextManager;

@Path("/ImportService")
public class ImportService extends Application
{
    static CSVLoader loader = null;

    @PostConstruct
    public static void initialize()
    {
        if (loader == null)
        {
            PgConnectionPool pgConnectionPool = new PgConnectionPool();
            pgConnectionPool.initialize();

            try
            {
                loader = new CSVLoader(pgConnectionPool.getConnection(),
                        ContextManager.getJndiValue("SCHEMA"));
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    @POST
    @Path("/import")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getImport(final ImportRequest importRequest)
            throws SQLException, IOException, Exception
    {
        
        
        String applicationEnvironment = ContextManager.getJndiValue(
                "APPLICATION_ENVIRONMENT");

        String importInputDir = importRequest.inputDir;

        ImportResponse entity0 = new ImportResponse();
        ImportDataResponse entity = new ImportDataResponse();

        //===
        Properties properties = new Properties();

        // Temporary; should come from context.xml
//        properties.setProperty("mailSmtp", "10.224.40.190");
//        properties.setProperty("mailSenderName", "FCDR Sodium Service");
//        properties.setProperty("mailSenderAddress", "Zoltan.Hernadi@canada.ca");
//        properties.setProperty("mailReceiverAddress", "Zoltan.Hernadi@canada.ca");
//        properties.setProperty("mailId", "noreply");
//        properties.setProperty("mailPasswd", "");
//        properties.setProperty("mailSubject", "FCDR Sodium Service Import Results");
//        properties.setProperty("mailText", "The sales data import has been successful");
        
        //===
        
        try
        {
            /// loader.loadCSV(importInputDir + "SalesProductData20170814.csv", "sales",
            loader.loadCSV(
                    importInputDir + "SALESDATA_20170921.csv",
                    ///importInputDir + "salesdata_20170920_10records.csv",
                    ///importInputDir + "Nielsen2015SalesData_FCDR_20170913.csv",
                    "sales", false);
        }
        catch (java.lang.NumberFormatException e)
        {
            entity = new ImportDataResponse(
                    ResponseCodes.NOT_ACCEPTABLE.getCode(), null,
                    ResponseCodes.NOT_ACCEPTABLE.getMessage());

            return Response.status(Response.Status.NOT_ACCEPTABLE).type(
                    MediaType.APPLICATION_JSON).entity(entity).build();
        }
        catch (com.opencsv.exceptions.CsvDataTypeMismatchException e1)
        {
            entity = new ImportDataResponse(
                    ResponseCodes.NOT_ACCEPTABLE.getCode(), null,
                    ResponseCodes.NOT_ACCEPTABLE.getMessage());

            return Response.status(Response.Status.NOT_ACCEPTABLE).type(
                    MediaType.APPLICATION_JSON).entity(entity).build();
        }
        catch (Exception e2)
        {
            entity = new ImportDataResponse(
                    ResponseCodes.NOT_ACCEPTABLE.getCode(), null,
                    ResponseCodes.NOT_ACCEPTABLE.getMessage());

            return Response.status(Response.Status.NOT_ACCEPTABLE).type(
                    MediaType.APPLICATION_JSON).entity(entity).build();
        }

        //===
        
//        if (!sendEmail(properties))
//        {
//            entity = new ImportDataResponse(
//                    ResponseCodes.NOT_ACCEPTABLE.getCode(), null,
//                    ResponseCodes.NOT_ACCEPTABLE.getMessage());
//
//            return Response.status(Response.Status.NOT_ACCEPTABLE).type(
//                    MediaType.APPLICATION_JSON).entity(entity).build();
//        }
//        
        //===
        
        return Response.status(Response.Status.OK).type(
                MediaType.APPLICATION_JSON).entity(entity).build();
    }

    private boolean sendEmail(Properties properties)
    {
        MailProcessor mailProcessor = new MailProcessor(properties);

        try
        {
            // Send message only if there are unprocessed files.
            mailProcessor.sendMessage();
        }
        catch (MailProcessorException e)
        {
            return false;
        }

        return true;
    }
}
