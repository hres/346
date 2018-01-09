package hc.fcdr.rws.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.db.Connect;
import hc.fcdr.rws.db.PgConnectionPool;
import hc.fcdr.rws.db.ProductDao;
import hc.fcdr.rws.model.report.ReportDataResponse;
import hc.fcdr.rws.model.report.ReportRequest;
import hc.fcdr.rws.util.ContextManager;

@Path("/ReportService")
public class ReportService extends Application
{
    static ProductDao productDao = null;

    @PostConstruct
    public static void initialize() throws IOException, Exception
    {
        if (productDao == null)
        {
//            final PgConnectionPool pgConnectionPool = new PgConnectionPool();
//            pgConnectionPool.initialize();

            try
            {
            	Connect connect = new Connect();
            	Connection connection = connect.getConnections();	
                productDao = new ProductDao(connection,
                        ContextManager.getJndiValue("SCHEMA"));
            }
            catch (final SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    @POST
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getReport(final ReportRequest reportRequest)
            throws SQLException, IOException, Exception
    {

        final ReportDataResponse entity = new ReportDataResponse();
        entity.setStatus(ResponseCodes.OK.getCode());
        entity.setMessage(ResponseCodes.OK.getMessage());

        return Response.status(Response.Status.OK)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(entity)
                       .build();
    }

}
