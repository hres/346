package hc.fcdr.rws.model;

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

    public ImportDataResponse(int status, ImportData data, String message)
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

    public ImportData getData()
    {
        return data;
    }

    public void setData(ImportData data)
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
