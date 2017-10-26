package hc.fcdr.rws.model.report;

public class ReportDataResponse
{
    private int    status;
    private String message;

    public ReportDataResponse()
    {
        super();
        this.status = 0;
        this.message = "";
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
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
