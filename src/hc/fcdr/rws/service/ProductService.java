package hc.fcdr.rws.service;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import hc.fcdr.rws.db.DbConnection;
import hc.fcdr.rws.db.ProductDao;
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

            try
            {
            	
                productDao =
                        new ProductDao(pgConnectionPool.getConnections(),
                        		pgConnectionPool.getSchema());
            }
            catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

/*
Accept a product id as argument and return all fields associated to that product except the classification information

*/
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
    
    /*
	This function isn't being used so far, but it should return the product information along with all the classification 
	number/name/type that match product id that is given in the paramter. 
    */

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
    /*
		Return an object containing the all the restaurant types stored in the restaurant type look up table in the database
    */


    @GET
    @Path("/restaurantTypes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurantTypes()
    {
        GenericList entity = new GenericList();
//        String userName = null;
//        final Principal userPrincipal = httpServletRequest.getUserPrincipal();
////        AccessToken token = (KeycloakPrincipal) httpServletRequest.getUserPrincipal().getKeycloakSecurityContext.getToken();
//
//        if (userPrincipal instanceof KeycloakPrincipal) {
//          
//          @SuppressWarnings("unchecked")
//          KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>)  sc.getUserPrincipal();
//
//          // this is how to get the real userName (or rather the login name)
//          userName = kp.getKeycloakSecurityContext().getIdToken().getPreferredUsername();
//          System.out.println("The name is "+userName);
//
//        }else {
//            System.out.println("Not an instance ");
//
//        }

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
   
    /*
		Return an object containing the all the types stored in the type look up table in the database
    */
    
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
    /*
    Accept a product id as argument and return all fields associated to that product *including the classification (at most one) 
    name/number/type of that product
    */
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
    
    /*
    	This method is used for the search by product functionality and takes a ProductRequest object as input param
    	It return an object containing the list of product that matched the search criteria of the input param 
    */
    @POST
    @Path("/productsfiltered")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchProducts(final ProductRequest productRequest)
            throws SQLException, IOException, Exception
    {


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
    
    /*
	This method is used for the search all (combination of product, sales and label) functionality and takes a ProductSalesLabelRequest 
	object as input param It returns an object containing the list of records that matched the search criteria of the input param 
     */

    @POST
    @Path("/productsaleslabel")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchProductsSalesLabels(
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
	/*
	 * Return an object containing the list of sales record associated the product that has the product id provided in the
	 * input param
	 */
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
	/*
	 * Return an object containing the list of labels record associated the product that has the product id provided in the
	 * input param
	 */
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
	/*
	 * This method updates a product record with the values provided in the ProductUpdateRequest object.
	 */
    @PUT
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
	/*
	 * This method updates create a new produt record.
	 */
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
	/*
	 * This method delete the product record that has the product id provided in the input param
	 */
    
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

	/*
	 * This method delete update the product id of a sales or label record (relink)
	 */
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
