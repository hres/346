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

import hc.fcdr.rws.db.PgConnectionPool;
import hc.fcdr.rws.db.ProductDao;
import hc.fcdr.rws.domain.Product;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.ProductClassificationDataResponse;
import hc.fcdr.rws.model.ProductDataResponse;
import hc.fcdr.rws.model.ProductRequest;
import hc.fcdr.rws.model.ProductSalesDataResponse;
import hc.fcdr.rws.model.ProductSalesLabelDataResponse;
import hc.fcdr.rws.model.ProductSalesLabelRequest;
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
    @Path("/productsraw")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductsRawAll()
    {
        try
        {
            if (productDao != null)
                return productDao.getProducts();
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<Product>();
    }

    @GET
    @Path("/productsraw/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductsRaw(@PathParam("id") long id,
            @Context HttpServletResponse servletResponse)
    {
        try
        {
            if (productDao != null)
                return productDao.getProduct(id);
        }
        catch (DaoException e)
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
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(entity)
                       .build();
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(@PathParam("id") long id,
            @Context HttpServletResponse servletResponse)
    {
        ProductDataResponse entity = new ProductDataResponse();

        try
        {
            if (productDao != null)
                entity = productDao.getProductResponse(id);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(entity)
                       .build();
    }

    @GET
    @Path("/productclassifications/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductClassifications(@PathParam("id") long id,
            @Context HttpServletResponse servletResponse)
    {
        final Boolean RETURN_FIRST_RECORD_FOUND = false;
        ProductClassificationDataResponse entity = new ProductClassificationDataResponse();

        try
        {
            if (productDao != null)
                entity = productDao.getProductClassificationResponse(id,
                        RETURN_FIRST_RECORD_FOUND);
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
    @Path("/productclassification/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductClassification(@PathParam("id") long id,
            @Context HttpServletResponse servletResponse)
    {
        final Boolean RETURN_FIRST_RECORD_FOUND = true;
        ProductClassificationDataResponse entity = new ProductClassificationDataResponse();

        try
        {
            if (productDao != null)
                entity = productDao.getProductClassificationResponse(id,
                        RETURN_FIRST_RECORD_FOUND);
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
    @Path("/productsfiltered")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getProducts(final ProductRequest productRequest)
            throws SQLException, IOException, Exception
    {

        String applicationEnvironment = ContextManager.getJndiValue(
                "APPLICATION_ENVIRONMENT");

        ProductDataResponse entity = new ProductDataResponse();

        try
        {
            if (productDao != null)
                entity = productDao.getProductResponse(productRequest);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(entity)
                       .build();
    }

    // ===========
    
    @POST
    @Path("/productsaleslabel")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getProductSalesLabel(final ProductSalesLabelRequest productSalesLabelRequest)
            throws SQLException, IOException, Exception
    {
        ProductSalesLabelDataResponse entity = new ProductSalesLabelDataResponse();

        try
        {
            if (productDao != null)
                entity = productDao.getProductSalesLabelResponse(productSalesLabelRequest);
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
    
    // ===========
    
    @GET
    @Path("/productsales/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductSales(@PathParam("id") long id,
            @Context HttpServletResponse servletResponse)
    {
        ProductSalesDataResponse entity = new ProductSalesDataResponse();

        try
        {
            if (productDao != null)
                entity = productDao.getProductSalesResponse(id);
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

    
    // ===========

    // @PUT
    // @Path("/users")
    // @Produces(MediaType.APPLICATION_XML)
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    // public String createUser(@FormParam("id") int id, @FormParam("name") String name, @FormParam("profession") String
    // profession,
    // @Context HttpServletResponse servletResponse) throws IOException
    // {
    // User user = new User(id, name, profession);
    // int result = userDao.addUser(user);
    //
    // if (result == 1)
    // return SUCCESS_RESULT;
    //
    // return FAILURE_RESULT;
    // }
    //
    // @POST
    // @Path("/users")
    // @Produces(MediaType.APPLICATION_XML)
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    // public String updateUser(@FormParam("id") int id, @FormParam("name") String name, @FormParam("profession") String
    // profession,
    // @Context HttpServletResponse servletResponse) throws IOException
    // {
    // User user = new User(id, name, profession);
    // int result = userDao.updateUser(user);
    //
    // if (result == 1)
    // return SUCCESS_RESULT;
    //
    // return FAILURE_RESULT;
    // }
    //
    // @DELETE
    // @Path("users/{userid}")
    // @Produces(MediaType.APPLICATION_XML)
    // public String deleteUser(@PathParam("userid") int userid)
    // {
    // int result = userDao.deleteUser(userid);
    //
    // if (result == 1)
    // return SUCCESS_RESULT;
    //
    // return FAILURE_RESULT;
    // }

    @OPTIONS
    @Path("/products")
    @Produces(MediaType.APPLICATION_XML)
    public String getSupportedOperations()
    {
        return "<operations>GET, PUT, POST, DELETE</operations>";
    }
}
