package hc.fcdr.rws.model.sales;

public class SalesInsertDataResponse
{
    private int    status;
    private String message;

    public SalesInsertDataResponse()
    {
        status = 0;
        message = "";
    }

    public SalesInsertDataResponse(final int status, final String message)
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
