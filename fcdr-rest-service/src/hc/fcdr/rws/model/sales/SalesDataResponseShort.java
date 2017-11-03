package hc.fcdr.rws.model.sales;

public class SalesDataResponseShort
{
    private int            status;
    private SalesDataShort data;
    private String         message;

    public SalesDataResponseShort()
    {
        status = 0;
        data = new SalesDataShort();
        message = "";
    }

    public SalesDataResponseShort(int status, SalesDataShort data,
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

    public SalesDataShort getData()
    {
        return data;
    }

    public void setData(SalesDataShort data)
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
