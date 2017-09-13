package hc.fcdr.rws.service;

import java.io.IOException;
import java.sql.SQLException;
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
import hc.fcdr.rws.importer.CSVLoader;
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

        try
        {
            ///loader.loadCSV(importInputDir + "SalesProductData20170814.csv", "sales",
            loader.loadCSV(importInputDir + "Nielsen2015SalesData_FCDR_161209.csv", "sales",
                false);
        }
        catch (java.lang.NumberFormatException e)
        {
            entity = new ImportDataResponse(
                    ResponseCodes.NOT_ACCEPTABLE.getCode(), null, ResponseCodes.NOT_ACCEPTABLE.getMessage());
            
            return Response.status(Response.Status.NOT_ACCEPTABLE).type(
                MediaType.APPLICATION_JSON).entity(entity).build();
        }
        catch (com.opencsv.exceptions.CsvDataTypeMismatchException e1)
        {
            entity = new ImportDataResponse(
                    ResponseCodes.NOT_ACCEPTABLE.getCode(), null, ResponseCodes.NOT_ACCEPTABLE.getMessage());
            
            return Response.status(Response.Status.NOT_ACCEPTABLE).type(
                MediaType.APPLICATION_JSON).entity(entity).build();
        }
        catch (Exception e2)
        {
            entity = new ImportDataResponse(
                    ResponseCodes.NOT_ACCEPTABLE.getCode(), null, ResponseCodes.NOT_ACCEPTABLE.getMessage());
            
            return Response.status(Response.Status.NOT_ACCEPTABLE).type(
                MediaType.APPLICATION_JSON).entity(entity).build();
        }

        return Response.status(Response.Status.OK).type(
                MediaType.APPLICATION_JSON).entity(entity).build();
    }

}
