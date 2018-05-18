package hc.fcdr.rws.service;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter, ContainerRequestFilter
{

    @Override
    public void filter(final ContainerRequestContext requestContext,
            final ContainerResponseContext cres) throws IOException
    {
    	
  
    	System.out.println(requestContext.getHeaders());
    	System.out.println(requestContext.getHeaders().getFirst("origin") + " is the origin");
        cres.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200");
        cres.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");
        cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
        cres.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        cres.getHeaders().add("Access-Control-Max-Age", "1209600");
    }

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		//List<>
		// TODO Auto-generated method stub
	  	System.out.println(requestContext.getHeaders().get("referer")+" is the referer");
	  	System.out.println(requestContext.getProperty("referer") + " id the property");
//    	if(!requestContext.getHeaders().getFirst("referer").equals("http://localhost:4200/createproduct")) {
//    		
//    		return;
//    	}
		
	}

}