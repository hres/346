package hc.fcdr.rws.service;

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

import hc.fcdr.rws.db.DbConnection;
import hc.fcdr.rws.db.ProductDao;
import hc.fcdr.rws.domain.Product;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.pkg.GenericList;
import hc.fcdr.rws.model.pkg.ResponseGeneric;
import hc.fcdr.rws.model.product.ProductClassificationDataResponse;
import hc.fcdr.rws.model.product.ProductDataResponse;
import hc.fcdr.rws.model.product.ProductInsertDataResponse;
import hc.fcdr.rws.model.product.ProductInsertRequest;
import hc.fcdr.rws.model.product.ProductLabelsDataResponse;
import hc.fcdr.rws.model.product.ProductRequest;
import hc.fcdr.rws.model.product.ProductSalesDataResponse;
import hc.fcdr.rws.model.product.ProductSalesLabelDataResponse;
import hc.fcdr.rws.model.product.ProductSalesLabelRequest;
import hc.fcdr.rws.model.product.ProductUpdateDataResponse;
import hc.fcdr.rws.model.product.ProductUpdateRequest;
import hc.fcdr.rws.model.product.RelinkRecord;
import hc.fcdr.rws.util.ContextManager;

@Path("/ProductService")
public class ProductService extends Application
{
    static ProductDao productDao = null;

    @PostConstruct
    public static void initialize()
    {
        if (productDao == null)
        {
            final DbConnection pgConnectionPool = new DbConnection();
            pgConnectionPool.initialize();

            try
            {
            	System.out.println("schema is "+ContextManager.getJndiValue("SCHEMA"));
            	
                productDao =
                        new ProductDao(pgConnectionPool.getConnection(),
                        		ContextManager.getJndiValue("SCHEMA"));
            }
            catch (final SQLException e)
            {
                e.printStackTrace();
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    @GET
    @Path("/productsraw")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductsRawAll()
    {
        try
        {
            if (productDao != null)
                return productDao.getProducts();
        }
        catch (final DaoException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<Product>();
    }

    @GET
    @Path("/productsraw/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductsRaw(@PathParam("id") final int id)
    {
        try
        {
            if (productDao != null)
                return productDao.getProduct(id);
        }
        catch (final DaoException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new Product();
    }

    // ==============================

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts()
    {
        ProductDataResponse entity = new ProductDataResponse();

        try
        {
            if (productDao != null)
                entity = productDao.getProductResponse();
        }
        catch (final Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(@PathParam("id") final int id)
    {
        ProductDataResponse entity = new ProductDataResponse();

        try
        {
            if (productDao != null)
                entity = productDao.getProductResponse(id);
        }
        catch (final Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    @GET
    @Path("/productclassifications/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductClassifications(@PathParam("id") final int id)
    {
        final Boolean RETURN_FIRST_RECORD_FOUND = false;
        ProductClassificationDataResponse entity =
                new ProductClassificationDataResponse();

        try
        {
            if (productDao != null)
                entity =
                        productDao.getProductClassificationResponse(id,
                                RETURN_FIRST_RECORD_FOUND);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    
    @GET
    @Path("/restaurantTypes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurantTypes()
    {
        GenericList entity = new GenericList();

        try
        {
            if (productDao != null)
                entity = productDao.getRestaurantTypes();
        }
        catch (final Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }
    //=====
    
    @GET
    @Path("/types")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTypes()
    {
        GenericList entity = new GenericList();

        try
        {
            if (productDao != null)
                entity = productDao.getTypes();
        }
        catch (final Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }
    @GET
    @Path("/productclassification/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductClassification(@PathParam("id") final int id)
    {
        final Boolean RETURN_FIRST_RECORD_FOUND = true;
        ProductClassificationDataResponse entity =
                new ProductClassificationDataResponse();

        try
        {
            if (productDao != null)
                entity =
                        productDao.getProductClassificationResponse(id,
                                RETURN_FIRST_RECORD_FOUND);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    @POST
    @Path("/productsfiltered")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getProducts(final ProductRequest productRequest)
            throws SQLException, IOException, Exception
    {

        /// String applicationEnvironment = ContextManager.getJndiValue(
        /// "APPLICATION_ENVIRONMENT");

        ProductDataResponse entity = new ProductDataResponse();

        try
        {
            if (productDao != null)
                entity = productDao.getProductResponse(productRequest);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    // ===========
    
    

    @POST
    @Path("/productsaleslabel")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getProductSalesLabel(
            final ProductSalesLabelRequest productSalesLabelRequest)
            throws SQLException, IOException, Exception
    {
        ProductSalesLabelDataResponse entity =
                new ProductSalesLabelDataResponse();

        try
        {
            if (productDao != null)
                entity =
                        productDao.getProductSalesLabelResponse(
                                productSalesLabelRequest);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    // ===========

    @GET
    @Path("/productsales/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductSales(@PathParam("id") final long id)
    {
        ProductSalesDataResponse entity = new ProductSalesDataResponse();

        try
        {
            if (productDao != null)
                entity = productDao.getProductSalesResponse(id);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    // ===========

    @GET
    @Path("/productlabels/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductLabels(@PathParam("id") final long id)
    {
        ProductLabelsDataResponse entity = new ProductLabelsDataResponse();

        try
        {
            if (productDao != null)
                entity = productDao.getProductLabelsResponse(id);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    // ===========

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(final ProductUpdateRequest productUpdateRequest)
            throws SQLException, IOException, Exception
    {
        ProductUpdateDataResponse entity = new ProductUpdateDataResponse();

        try
        {
            if (productDao != null)
                entity =
                        productDao
                                .getProductUpdateResponse(productUpdateRequest);
        }
        catch (final Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    // ===========

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(final ProductInsertRequest productInsertRequest)
            throws SQLException, IOException, Exception
    {
        ProductInsertDataResponse entity = new ProductInsertDataResponse();

        try
        {
            if (productDao != null)
                entity =
                        productDao
                                .getProductInsertResponse(productInsertRequest);
        }
        catch (final Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    // ===========

    @OPTIONS
    @Path("/products")
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
            if (productDao != null)
                entity = productDao.getProductDeleteResponse(id);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }

    @POST
    @Path("/relinkRecord")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response relink(final RelinkRecord relinkRecord)
            throws SQLException, IOException, Exception
    {
    	ResponseGeneric entity = new ResponseGeneric();

        try
        {
            if (productDao != null)
                entity =
                        productDao
                                .relinkRecordResponse(relinkRecord);
        }
        catch (final Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }
    
}
