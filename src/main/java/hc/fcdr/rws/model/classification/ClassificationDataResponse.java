package hc.fcdr.rws.model.classification;

public class ClassificationDataResponse
{
    private int                status;
    private ClassificationData data;
    private String             message;

    public ClassificationDataResponse()
    {
        status = 0;
        data = new ClassificationData();
        message = "";
    }

    public ClassificationDataResponse(final int status,
            final ClassificationData data, final String message)
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

    public ClassificationData getData()
    {
        return data;
    }

    public void setData(final ClassificationData data)
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
