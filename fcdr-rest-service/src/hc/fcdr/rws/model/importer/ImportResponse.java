package hc.fcdr.rws.model.importer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ImportResponse
{
    private String itemId;
    private int    status;

    public ImportResponse()
    {
        super();
        itemId = "";
        status = 0;
    }

    public ImportResponse(final String itemId, final int status)
    {
        super();
        this.itemId = itemId;
        this.status = status;
    }

    public ImportResponse(final ImportResponse importResponse)
    {
        super();
        itemId = importResponse.getItemId();
        status = importResponse.getStatus();
    }

    public String getItemId()
    {
        return itemId;
    }

    public void setItemId(final String itemId)
    {
        this.itemId = itemId;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(final int status)
    {
        this.status = status;
    }

}
