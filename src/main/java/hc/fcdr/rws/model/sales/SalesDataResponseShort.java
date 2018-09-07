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

    public SalesDataResponseShort(final int status, final SalesDataShort data,
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

    public SalesDataShort getData()
    {
        return data;
    }

    public void setData(final SalesDataShort data)
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
