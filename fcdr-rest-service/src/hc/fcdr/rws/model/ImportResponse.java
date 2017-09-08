package hc.fcdr.rws.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ImportResponse
{
    private String itemId;
    private int    status;

    public ImportResponse()
    {
        super();
        this.itemId = "";
        this.status = 0;
    }

    public ImportResponse(String itemId, int status)
    {
        super();
        this.itemId = itemId;
        this.status = status;
    }

    public ImportResponse(ImportResponse importResponse)
    {
        super();
        this.itemId = importResponse.getItemId();
        this.status = importResponse.getStatus();
    }

    public String getItemId()
    {
        return itemId;
    }

    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

}
