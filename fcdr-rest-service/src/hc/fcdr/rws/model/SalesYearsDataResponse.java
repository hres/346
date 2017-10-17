package hc.fcdr.rws.model;

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

    public SalesYearsDataResponse(int status, SalesYearsData data,
            String message)
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

    public SalesYearsData getData()
    {
        return data;
    }

    public void setData(SalesYearsData data)
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
