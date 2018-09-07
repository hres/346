package hc.fcdr.rws.except;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper  implements ExceptionMapper<Throwable> {
  @Override
  public Response toResponse(Throwable exception) {
    exception.printStackTrace();
    Logger.getGlobal().log(
      Level.SEVERE,
      exception.getMessage()
    );
    return Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Internal Server Error").build();
  }
}

