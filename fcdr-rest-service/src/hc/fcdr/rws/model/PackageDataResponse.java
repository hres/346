package hc.fcdr.rws.model;

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

    public PackageDataResponse(int status, PackageData data, String message)
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

    public PackageData getData()
    {
        return data;
    }

    public void setData(PackageData data)
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
