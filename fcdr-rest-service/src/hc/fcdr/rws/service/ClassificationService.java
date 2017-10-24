package hc.fcdr.rws.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.annotation.PostConstruct;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hc.fcdr.rws.db.ClassificationDao;
import hc.fcdr.rws.db.PgConnectionPool;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.ClassificationDataResponse;
import hc.fcdr.rws.util.ContextManager;
import hc.fcdr.rws.domain.Classification;

@Path("/ClassificationService")
public class ClassificationService extends Application
{
    static ClassificationDao classificationDao = null;

    @PostConstruct
    public static void initialize()
    {
        if (classificationDao == null)
        {
            PgConnectionPool pgConnectionPool = new PgConnectionPool();
            pgConnectionPool.initialize();

            try
            {
                classificationDao = new ClassificationDao(
                        pgConnectionPool.getConnection(),
                        ContextManager.getJndiValue("SCHEMA"));
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    @GET
    @Path("/classificationraw")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Classification> getClassificationRawAll()
    {
        try
        {
            if (classificationDao != null)
                return classificationDao.getClassification();
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<Classification>();
    }

    @GET
    @Path("/classificationraw/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Classification getClassificationRaw(@PathParam("id") long id)
    {
        try
        {
            if (classificationDao != null)
                return classificationDao.getClassification(id);
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }

        return new Classification();
    }

    // ==============================

    @GET
    @Path("/classification")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClassification()
    {
        ClassificationDataResponse entity = new ClassificationDataResponse();

        try
        {
            if (classificationDao != null)
                entity = classificationDao.getClassificationResponse();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(entity)
                       .build();
    }

    @GET
    @Path("/classification/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClassification(@PathParam("id") long id)
    {
        ClassificationDataResponse entity = new ClassificationDataResponse();

        try
        {
            if (classificationDao != null)
                entity = classificationDao.getClassificationResponse(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(entity)
                       .build();
    }

    @OPTIONS
    @Path("/classification")
    @Produces(MediaType.APPLICATION_XML)
    public String getSupportedOperations()
    {
        return "<operations>GET, PUT, POST, DELETE</operations>";
    }
}
