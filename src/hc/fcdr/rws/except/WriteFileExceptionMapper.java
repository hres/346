package hc.fcdr.rws.except;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WriteFileExceptionMapper implements ExceptionMapper<WriteFileException>{

	@Override
	public Response toResponse(WriteFileException exception) {
	    exception.printStackTrace();
	    Logger.getGlobal().log(
	      Level.SEVERE,
	      exception.getMessage()
	    );
	    ErrorMessageList e = new ErrorMessageList();	  
	    ErrorMessage eMessage = new ErrorMessage(exception.getMessage(),  "http://sodium-monitoring-dev-webapp.canadacentral.cloudapp.azure.com", 555);
	   e.getErrorList().add(eMessage);
	   
	    return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e).build();
	}

	
}
