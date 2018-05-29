package hc.fcdr.rws.model.sales;

public class SalesDataResponse
{
    private int       status;
    private SalesData data;
    private String    message;

    public SalesDataResponse()
    {
        status = 0;
        data = new SalesData();
        message = "";
    }

    public SalesDataResponse(final int status, final SalesData data,
            final String message)
    {
        super();
        this.status = status;
        this.data = data;
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

    public SalesData getData()
    {
        return data;
    }

    public void setData(final SalesData data)
    {
        this.data = data;
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
