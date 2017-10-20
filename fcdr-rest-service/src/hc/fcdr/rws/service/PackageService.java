package hc.fcdr.rws.service;

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

import hc.fcdr.rws.db.PackageDao;
import hc.fcdr.rws.db.PgConnectionPool;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.PackageDataResponse;
import hc.fcdr.rws.model.PackageRequest;
import hc.fcdr.rws.util.ContextManager;
import hc.fcdr.rws.domain.Package;

@Path("/PackageService")
public class PackageService extends Application
{
    static PackageDao packageDao = null;

    @PostConstruct
    public static void initialize()
    {
        if (packageDao == null)
        {
            PgConnectionPool pgConnectionPool = new PgConnectionPool();
            pgConnectionPool.initialize();

            try
            {
                packageDao = new PackageDao(pgConnectionPool.getConnection(),
                        ContextManager.getJndiValue("SCHEMA"));
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    @GET
    @Path("/packageraw")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Package> getPackageRawAll()
    {
        try
        {
            if (packageDao != null)
                return packageDao.getPackage();
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<Package>();
    }

    @GET
    @Path("/packageraw/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Package getPackageRaw(@PathParam("id") long id)
    {
        try
        {
            if (packageDao != null)
                return packageDao.getPackage(id);
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }

        return new Package();
    }

    // ==============================

    @GET
    @Path("/package")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPackage()
    {
        PackageDataResponse entity = new PackageDataResponse();

        try
        {
            if (packageDao != null)
                entity = packageDao.getPackageResponse();
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
    @Path("/package/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPackage(@PathParam("id") long id)
    {
        PackageDataResponse entity = new PackageDataResponse();

        try
        {
            if (packageDao != null)
                entity = packageDao.getPackageResponse(id);
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

    @POST
    @Path("/packagefiltered")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPackage(final PackageRequest packageRequest)
            throws SQLException, IOException, Exception
    {
        PackageDataResponse entity = new PackageDataResponse();

        try
        {
            if (packageDao != null)
                entity = packageDao.getPackageResponse(packageRequest);
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
    @Path("/package")
    @Produces(MediaType.APPLICATION_XML)
    public String getSupportedOperations()
    {
        return "<operations>GET, PUT, POST, DELETE</operations>";
    }
}
