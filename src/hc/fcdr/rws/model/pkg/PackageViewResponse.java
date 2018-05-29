package hc.fcdr.rws.model.pkg;

public class PackageViewResponse
{
    private int                     status;
    private PackageViewDataResponse data;
    private String                  message;

    public PackageViewResponse()
    {

        status = 0;
        data = null;
        message = null;
    }

    public PackageViewResponse(final int status,
            final PackageViewDataResponse data, final String message)
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

    public PackageViewDataResponse getData()
    {
        return data;
    }

    public void setData(final PackageViewDataResponse data)
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
