package hc.fcdr.rws.model.report;

public class ReportDataResponse
{
    private int    status;
    private String message;

    public ReportDataResponse()
    {
        super();
        status = 0;
        message = "";
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(final int status)
    {
        this.status = status;
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
