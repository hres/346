package hc.fcdr.rws.model.importer;

public class ImportDataResponse
{
    private int        status;
    private ImportData data;
    private String     message;

    public ImportDataResponse()
    {
        status = 0;
        data = new ImportData();
        message = "";
    }

    public ImportDataResponse(final int status, final ImportData data,
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

    public ImportData getData()
    {
        return data;
    }

    public void setData(final ImportData data)
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
