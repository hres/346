package hc.fcdr.rws.service;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.except.DaoException;

@Provider
public class CORSFilter implements ContainerResponseFilter
{

    @Override
    public void filter(final ContainerRequestContext requestContext,
            final ContainerResponseContext cres) throws IOException
    {
    	
  
//System.out.println(requestContext.getHeaders());
//System.out.println(requestContext.getUriInfo().getPath()+" is tthe path found by AG");
//
//if(requestContext.getHeaders().get("origin").get(0).equals("http://localhost:4200")) {
//	System.out.println(requestContext.getHeaders().get("origin") + " is here 2");
//	//throw new IOException();
//}
//    	
        cres.getHeaders().add("Access-Control-Allow-Origin", "*");
        cres.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");
        cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
        cres.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        cres.getHeaders().add("Access-Control-Max-Age", "1209600");
        
    }


}