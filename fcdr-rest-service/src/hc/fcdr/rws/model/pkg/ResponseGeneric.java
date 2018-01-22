package hc.fcdr.rws.model.pkg;

public class ResponseGeneric
{

    private int    status;
    private String message;
    private Integer record_id;

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
    
    

    public Integer getRecord_id() {
		return record_id;
	}

	public void setRecord_id(Integer record_id) {
		this.record_id = record_id;
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
