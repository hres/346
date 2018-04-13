package hc.fcdr.rws.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import hc.fcdr.rws.db.DbConnection;
import hc.fcdr.rws.db.PackageDao;
import hc.fcdr.rws.domain.Package;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.pkg.ComponentNameResponse;
import hc.fcdr.rws.model.pkg.GenericList;
import hc.fcdr.rws.model.pkg.ImagesList;
import hc.fcdr.rws.model.pkg.InsertPackageResponse;
import hc.fcdr.rws.model.pkg.NftGetModel;
import hc.fcdr.rws.model.pkg.NftRequest;
import hc.fcdr.rws.model.pkg.NftView;
import hc.fcdr.rws.model.pkg.PackageDataResponse;
import hc.fcdr.rws.model.pkg.PackageInsertRequest;
import hc.fcdr.rws.model.pkg.PackageRequest;
import hc.fcdr.rws.model.pkg.PackageUpdateRequest;
import hc.fcdr.rws.model.pkg.PackageViewResponse;
import hc.fcdr.rws.model.pkg.ResponseGeneric;
import hc.fcdr.rws.model.sales.SalesDeleteDataResponse;
import hc.fcdr.rws.util.ContextManager;

@Path("/PackageService")

public class PackageService extends Application
{
    static PackageDao packageDao = null;

    @PostConstruct
    public static void initialize()
    {
        if (packageDao == null)
        {
            final DbConnection pgConnectionPool = new DbConnection();
            pgConnectionPool.initialize();

            try
            {
                packageDao =
                        new PackageDao(pgConnectionPool.getConnection(),
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
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    @GET
    @Path("/package/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPackage(@PathParam("id") final long id)
    {

        // TODO verify
        PackageViewResponse entity = new PackageViewResponse();

        // TODO previous
        // PackageDataResponse entity = new PackageDataResponse();

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
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
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
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
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
                entity =
                        packageDao
                                .getPackageInsertResponse(packageInsertRequest);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(final PackageUpdateRequest packageUpdateRequest)
            throws SQLException, IOException, Exception
    {

        System.out.println("les valeurs sont: \n "
                + packageUpdateRequest.getPackage_collection_date());
        InsertPackageResponse entity = new InsertPackageResponse();
        try
        {
            if (packageDao != null)
                entity =
                        packageDao
                                .getPackageUpdateResponse(packageUpdateRequest);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    @GET
    @Path("/unitOfMeasure")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnitOfMeasure()
    {
        GenericList entity = new GenericList();

        try
        {
            if (packageDao != null)
                entity = packageDao.getListOfUnits();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        // return null;
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();

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

        // return null;
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    // ===
    @POST
    @Path("/updateNft")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateNft(final NftRequest nftRequest)
            throws SQLException, IOException, Exception
    {

        ResponseGeneric entity = new ResponseGeneric();
        try
        {
            if (packageDao != null)
                entity = packageDao.getNftUpdateResponse(nftRequest);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        // return null;
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    // ===
    @POST
    @Path("/getNft")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getNft(final NftGetModel nftGetModel)
            throws SQLException, IOException, Exception
    {

        NftView entity = new NftView();
        try
        {
            if (packageDao != null)
                entity =
                        packageDao.getNft(nftGetModel.getPackage_id(),
                                nftGetModel.getFlag());
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        // return null;

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    @GET
    @Path("/listofcomponents")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComponentName()
            throws SQLException, IOException, Exception
    {
        ComponentNameResponse entity = new ComponentNameResponse();

        try
        {
            if (packageDao != null)
                entity = packageDao.getComponents();

        }
        catch (final DaoException e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    // ===
    @OPTIONS
    @Path("/package")
    @Produces(MediaType.APPLICATION_XML)
    public String getSupportedOperations()
    {
        return "<operations>GET, PUT, POST, DELETE</operations>";
    }
    
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") final Integer id)
    {
    	ResponseGeneric entity = new ResponseGeneric();

        try
        {
            if (packageDao != null)
                entity = packageDao.getLabelDeleteResponse(id);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    // ===

    @GET
    @Path("/getLabelImages/{image_path}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response labelImages(@PathParam("image_path") final String image_path)
    {
    	File file = null;

        try
        {
            if (packageDao != null)
            	file = packageDao.getImage(image_path);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

		ResponseBuilder response = Response.ok((Object) file);
		 
		response.header("Content-Disposition", "attachment; filename=label_image.jpeg");
		
		return response.build();
    }

    @GET
    @Path("/getListOfImages/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response labelImages(@PathParam("id") final Integer package_id)
    {
    	ImagesList entity = null;

        try
        {
            if (packageDao != null)
            	entity = packageDao.getListOfImages(package_id);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }


        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

}
