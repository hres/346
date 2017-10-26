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

    public SalesDataResponse(int status, SalesData data, String message)
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

    public void setStatus(int status)
    {
        this.status = status;
    }

    public SalesData getData()
    {
        return data;
    }

    public void setData(SalesData data)
    {
        this.data = data;
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
