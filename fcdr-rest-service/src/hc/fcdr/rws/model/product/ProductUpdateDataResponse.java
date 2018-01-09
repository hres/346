package hc.fcdr.rws.model.product;

public class ProductUpdateDataResponse
{
    private int    status;
    private String message;

    public ProductUpdateDataResponse()
    {
        status = 0;
        message = "";
    }

    public ProductUpdateDataResponse(final int status, final String message)
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
