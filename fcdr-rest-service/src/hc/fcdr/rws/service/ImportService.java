package hc.fcdr.rws.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.db.DbConnection;
import hc.fcdr.rws.db.ImportMarketShareDao;
import hc.fcdr.rws.db.ImportSalesDao;
import hc.fcdr.rws.db.ProductDao;
import hc.fcdr.rws.except.MailProcessorException;
import hc.fcdr.rws.importer.CSVLoader;
import hc.fcdr.rws.importer.ImportStatistics;
import hc.fcdr.rws.mail.MailProcessor;
import hc.fcdr.rws.model.importer.ImportDataResponse;
import hc.fcdr.rws.model.importer.ImportRequest;
import hc.fcdr.rws.reportengine.ImportReport;
import hc.fcdr.rws.util.ContextManager;

@Path("/ImportService")
public class ImportService extends Application
{
    static CSVLoader     loader                = null;

    private final String REPORT_DIRECTORY_ROOT =
            (!System.getProperty("java.io.tmpdir").endsWith(File.separator)
                    ? (System.getProperty("java.io.tmpdir") + File.separator)
                    : System.getProperty("java.io.tmpdir"));

    private final String REPORT_FILE           =
            REPORT_DIRECTORY_ROOT + "fcdrSalesImportReport.pdf";

    static ImportMarketShareDao importSalesDao = null;


    @PostConstruct
    public static void initialize()
    {
        if (loader == null)
        {
            final DbConnection pgConnectionPool = new DbConnection();
            pgConnectionPool.initialize();

            try
            {
                loader =
                        new CSVLoader(pgConnectionPool.getConnection(),
                                ContextManager.getJndiValue("SCHEMA"));
                

                importSalesDao =
                        new ImportMarketShareDao(pgConnectionPool.getConnection(),
                        		ContextManager.getJndiValue("SCHEMA"));
            }
            catch (final SQLException e)
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
        final String importInputDir = importRequest.inputDir;
        final boolean sendMail = importRequest.sendMail;
        ImportDataResponse entity = new ImportDataResponse();

        // ===
        final Properties properties = new Properties();

        properties.setProperty("mailSmtp",
                ContextManager.getJndiValue("MAIL_SMTP"));
        properties.setProperty("mailSenderName",
                ContextManager.getJndiValue("MAIL_SENDER_NAME"));
        properties.setProperty("mailSenderAddress",
                ContextManager.getJndiValue("MAIL_SENDER_ADDRESS"));
        properties.setProperty("mailReceiverAddress",
                ContextManager.getJndiValue("MAIL_RECEIVER_ADDRESS"));
        properties.setProperty("mailId",
                ContextManager.getJndiValue("MAIL_ID"));
        properties.setProperty("mailPasswd",
                ContextManager.getJndiValue("MAIL_PASSWD"));
        properties.setProperty("mailSubject",
                ContextManager.getJndiValue("MAIL_SUBJECT"));
        properties.setProperty("mailText",
                ContextManager.getJndiValue("MAIL_TEXT"));

        // ===

         try
         {
        
            URL url =
                    new URL("http://10.148.179.244:8080/fcdr-rest-service/rest/ReportService/report");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input =
                    "{\"inputDir\":\"/home/zoltanh/Documents/FoodModernizationProject/importInput/\",\"sendMail\":true }";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                throw new RuntimeException(
                        "Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Report Service .... \n");
            while ((output = br.readLine()) != null)
            {
                System.out.println(output);
            }

            conn.disconnect();

        }
        catch (MalformedURLException e)
        {

            e.printStackTrace();

        }
        catch (IOException e)
        {

            e.printStackTrace();

        }

        // ===

        try
        {
            final ImportStatistics importStatistics =
                    loader.loadCSV(
                            /// importInputDir + "SALESDATA_20170921.csv",
                            importInputDir + "salesdata_20171003_short.csv",
                            "sales", false);

            // Generate report.
            new ImportReport(importStatistics);
        }
        catch (final java.lang.NumberFormatException e)
        {
            entity =
                    new ImportDataResponse(
                            ResponseCodes.NOT_ACCEPTABLE.getCode(), null,
                            ResponseCodes.NOT_ACCEPTABLE.getMessage());

            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .type(MediaType.APPLICATION_JSON).entity(entity).build();
        }
        catch (final com.opencsv.exceptions.CsvDataTypeMismatchException e1)
        {
            entity =
                    new ImportDataResponse(
                            ResponseCodes.NOT_ACCEPTABLE.getCode(), null,
                            ResponseCodes.NOT_ACCEPTABLE.getMessage());

            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .type(MediaType.APPLICATION_JSON).entity(entity).build();
        }
        catch (final Exception e2)
        {
            entity =
                    new ImportDataResponse(
                            ResponseCodes.NOT_ACCEPTABLE.getCode(), null,
                            ResponseCodes.NOT_ACCEPTABLE.getMessage());

            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .type(MediaType.APPLICATION_JSON).entity(entity).build();
        }

        // ===

        if (sendMail)
        {
            final String[] filesToAttach = new String[]
            { REPORT_FILE };

            if (!sendEmail(properties, filesToAttach))
            {
                entity =
                        new ImportDataResponse(
                                ResponseCodes.NOT_ACCEPTABLE.getCode(), null,
                                ResponseCodes.NOT_ACCEPTABLE.getMessage());

                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .type(MediaType.APPLICATION_JSON).entity(entity)
                        .build();
            }
        }
        // ===

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    private boolean sendEmail(final Properties properties,
            final String[] filesToAttach)
    {
        final MailProcessor mailProcessor = new MailProcessor(properties);

        try
        {
            mailProcessor.setAttachments(filesToAttach);
            mailProcessor.sendMessage();
        }
        catch (final MailProcessorException e)
        {
            return false;
        }

        return true;
    }

    
    @POST
    @Path("/importMarketShare")
    @Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.CHARSET_PARAMETER)
    public Response getImportMarketShare(@Context HttpServletRequest requestContext, @Context SecurityContext context)
            throws SQLException, IOException, Exception
    {
    	   String ipAddressRequestCameFrom = requestContext.getRemoteAddr();
    	   System.out.println("ip: "+ipAddressRequestCameFrom);
    	   //Also if security is enabled
//    	   Principal principal = context.getUserPrincipal();
//    	   String userName = principal.getName();
    	
//    	importSalesDao.testImport("/tmp/otherSales.csv");
    	importSalesDao.testImport("/tmp/bigFile.csv");
    	//importSalesDao.testImport("/tmp/sales16.csv");
    	
    	return null;
    }
}
