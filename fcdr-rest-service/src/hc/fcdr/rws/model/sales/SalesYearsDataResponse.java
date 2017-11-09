package hc.fcdr.rws.model.sales;

public class SalesYearsDataResponse
{
    private int            status;
    private SalesYearsData data;
    private String         message;

    public SalesYearsDataResponse()
    {
        status = 0;
        data = new SalesYearsData();
        message = "";
    }

    public SalesYearsDataResponse(final int status, final SalesYearsData data,
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

    public SalesYearsData getData()
    {
        return data;
    }

    public void setData(final SalesYearsData data)
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
