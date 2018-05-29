package hc.fcdr.rws.model.sales;

public class SalesDeleteDataResponse
{
    private int    status;
    private String message;

    public SalesDeleteDataResponse()
    {
        status = 0;
        message = "";
    }

    public SalesDeleteDataResponse(final int status, final String message)
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
