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

    public ProductUpdateDataResponse(int status, String message)
    {
        super();
        this.status = status;
        this.message = message;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
