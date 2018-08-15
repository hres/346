package hc.fcdr.rws.service;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
public class CORSFilter implements ContainerResponseFilter
{
	


	
//	private static final String SECURE_URL_PREFIX = "ClassificationService/classification";
    @Override
    public void filter(final ContainerRequestContext requestContext,
            final ContainerResponseContext cres) throws IOException
    {
    	
//    	System.out.println("Security: "+httpServletRequest.getSession().toString());
  
//System.out.println("Header "+requestContext.getHeaders());
//System.out.println("name  "+requestContext.getSecurityContext());
//
//System.out.println(requestContext.getUriInfo().getPath()+" is tthe path found by AG");
//
    	
//if(requestContext.getHeaders().get("origin").get(0).equals("http://localhost:4200")) {
//	System.out.println(requestContext.getHeaders().get("origin") + " is here 2");
//	return;
//	//throw new IOException();
//}
    	
//        if(requestContext.getUriInfo().getPath().contains(SECURE_URL_PREFIX)) {
//            Response res = Response
//      			   .status(Response.Status.UNAUTHORIZED)
//      			   .entity("User cannot access")
//      			   .build();
//             
//             requestContext.abortWith(res);
//        	
    	
//        }
    	
//    	System.out.println("Auth: "+requestContext.getHeaders().get("Authorization").get(0));
//    	System.out.println("Auth: "+requestContext.getHeaders().get("Authorization").size());

        cres.getHeaders().add("Access-Control-Allow-Origin", "*");
        cres.getHeaders().add("Access-Control-Allow-Headers",
                "ORIGIN, CONTENT-TYPE, ACCEPT, AUTHORIZATION,ACCESS_CONTROL_ALLOW_ORIGIN");
        cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
        cres.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        cres.getHeaders().add("Access-Control-Max-Age", "1209600");
        cres.getHeaders().add("Referrer-Policy",                  "no-referrer");
        
        


    }

    

}
//
//@Provider
//public class CORSFilter implements ContainerRequestFilter
//{
//	
//	
//	private static final String SECURE_URL_PREFIX = "ProductService";
//    public void filter(final ContainerRequestContext requestContext) throws IOException
//    {
//    	
////    	System.out.println("Auth: "+requestContext.getHeaders().get("Authorization").size());
//
//        if(requestContext.getUriInfo().getPath().contains(SECURE_URL_PREFIX)) {
//        	return;
//        	
//        }
//
//        Response res = Response
//  			   .status(Response.Status.UNAUTHORIZED)
//  			   .entity("User cannot access")
//  			   .build();
//         
//         requestContext.abortWith(res);
//
//    }
//
//    
//
//}