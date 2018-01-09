package hc.fcdr.rws.model.pkg;

public class ResponseGeneric {

	
	  private int    status;
	   private String message;
	    
    public ResponseGeneric()
    {
        status = 0;
        message = "";
    }

    public ResponseGeneric(final int status, final String message)
    {
        super();
        this.status = status;
        this.message = message;
    }
    
	public int getStatus()
    {
        return status;
    }

    public void setStatus(final int status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }


}
