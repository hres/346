package hc.fcdr.rws.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hc.fcdr.rws.db.PackageDao;
import hc.fcdr.rws.db.PgConnectionPool;
import hc.fcdr.rws.domain.Classification;
import hc.fcdr.rws.domain.Package;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.pkg.ComponentName;
import hc.fcdr.rws.model.pkg.ComponentNameResponse;
import hc.fcdr.rws.model.pkg.InsertPackageResponse;
import hc.fcdr.rws.model.pkg.NftModel;
import hc.fcdr.rws.model.pkg.NftRequest;
import hc.fcdr.rws.model.pkg.PackageDataResponse;
import hc.fcdr.rws.model.pkg.PackageInsertRequest;
import hc.fcdr.rws.model.pkg.PackageRequest;
import hc.fcdr.rws.model.pkg.PackageViewResponse;
import hc.fcdr.rws.model.pkg.ResponseGeneric;
import hc.fcdr.rws.model.sales.SalesInsertDataResponse;
import hc.fcdr.rws.model.sales.SalesInsertRequest;
import hc.fcdr.rws.util.ContextManager;
import javax.xml.bind.annotation.XmlRootElement;

@Path("/PackageService")

public class PackageService extends Application
{
    static PackageDao packageDao = null;

    @PostConstruct
    public static void initialize()
    {
        if (packageDao == null)
        {
            final PgConnectionPool pgConnectionPool = new PgConnectionPool();
            pgConnectionPool.initialize();

            try
            {
                packageDao = new PackageDao(pgConnectionPool.getConnection(),
                        ContextManager.getJndiValue("SCHEMA"));
            }
            catch (final SQLException e)
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
        catch (final DaoException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<Package>();
    }

    @GET
    @Path("/packageraw/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Package getPackageRaw(@PathParam("id") final long id)
    {
        try
        {
            if (packageDao != null)
                return packageDao.getPackage(id);
        }
        catch (final DaoException e)
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
        catch (final Exception e)
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
    public Response getPackage(@PathParam("id") final long id)
    {
    	System.out.println("here");
    	PackageViewResponse entity = new PackageViewResponse();

        try
        {
            if (packageDao != null)
                entity = packageDao.getPackageResponse(id);
        }
        catch (final Exception e)
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
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(entity)
                       .build();
    }
    @POST
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(final PackageInsertRequest packageInsertRequest)
            throws SQLException, IOException, Exception
    {
        InsertPackageResponse entity = new InsertPackageResponse();
        try
        {
            if (packageDao != null)
                entity = packageDao.getPackageInsertResponse(packageInsertRequest);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(entity)
                       .build();
    }

    
    @POST
    @Path("/insertNft")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertNft(final NftRequest nftRequest)
            throws SQLException, IOException, Exception
    {
    	

    	ResponseGeneric entity = new ResponseGeneric();
        try
        {
            if (packageDao != null)
                entity = packageDao.getNftInsertResponse(nftRequest);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        	//return null;
        return Response.status(Response.Status.OK)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(entity)
                       .build();
    }
    // ===
    @OPTIONS
    @Path("/package")
    @Produces(MediaType.APPLICATION_XML)
    public String getSupportedOperations()
    {
        return "<operations>GET, PUT, POST, DELETE</operations>";
    }
    
    @GET
    @Path("/listofcomponents")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComponentName()throws SQLException, IOException, Exception
    {
    	ComponentNameResponse entity = new ComponentNameResponse();
    
    	
        //List<String> entity =  new ArrayList<String>();

        try
        {
            if (packageDao != null)
            	entity =  packageDao.getComponents();
            
        }
        catch (final DaoException e)
        {
            e.printStackTrace();
        }

       // return new ArrayList<String>();

//    	Response response = Response.ok(entity).build();
        //return response;
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(entity)
                .build();
    }

}
