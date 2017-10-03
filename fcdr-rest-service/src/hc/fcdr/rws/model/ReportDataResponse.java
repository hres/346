package hc.fcdr.rws.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ReportDataResponse
{
    private int             status;
    private FileInputStream data;
    private String          message;

    public ReportDataResponse()
    {
        status = 0;
        data = null;
        message = "";
    }

//    public ReportDataResponse(int status, File file, String message)
//            throws FileNotFoundException
//    {
//        super();
//        this.status = status;
//        this.data = new FileInputStream(file);
//        this.message = message;
//    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public FileInputStream getData()
    {
        return data;
    }

    public void setData(File file) throws FileNotFoundException
    {
        this.data = new FileInputStream(file);
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
