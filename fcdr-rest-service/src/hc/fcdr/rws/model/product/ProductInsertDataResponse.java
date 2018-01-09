package hc.fcdr.rws.model.product;


public class ProductInsertDataResponse
{
    private int    status;
    private String message;
    private Object id = null;
    

    public ProductInsertDataResponse()
    {
        status = 0;
        message = "";
    }

    public ProductInsertDataResponse(final int status, final String message)
    {
        super();
        this.status = status;
        this.message = message;
    }

    
    public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
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
