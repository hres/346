package hc.fcdr.rws.model.pkg;

public class PackageDataResponse
{
    private int         status;
    private PackageData data;
    private String      message;

    public PackageDataResponse()
    {
        status = 0;
        data = new PackageData();
        message = "";
    }

    public PackageDataResponse(final int status, final PackageData data,
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

    public PackageData getData()
    {
        return data;
    }

    public void setData(final PackageData data)
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
