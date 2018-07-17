package hc.fcdr.rws.except;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LoadFileExceptionMapper implements ExceptionMapper<LoadFileException>{

	@Override
	public Response toResponse(LoadFileException exception) {
		
		
	    exception.printStackTrace();
	    Logger.getGlobal().log(
	      Level.SEVERE,
	      exception.getMessage()
	    );
	    ErrorMessageList e = new ErrorMessageList();	  
	    ErrorMessage eMessage = new ErrorMessage(exception.getMessage(),  "http://sodium-monitoring-dev-webapp.canadacentral.cloudapp.azure.com", 555);
	   e.getErrorList().add(eMessage);
	   
	    return Response.status(Status.NOT_ACCEPTABLE).type(MediaType.APPLICATION_JSON).entity(e).build();
	}

}
