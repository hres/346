package hc.fcdr.rws.importer;

public class ImportReportDetailRow
{
    private String  itemId;
    private String  salesDescription;
    
    public ImportReportDetailRow()
    {
        super();
        this.itemId = "";
        this.salesDescription = "";
    }
    
    public ImportReportDetailRow(String itemId, String salesDescription)
    {
        super();
        this.itemId = itemId;
        this.salesDescription = salesDescription;
    }

    public String getItemId()
    {
        return itemId;
    }

    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }

    public String getSalesDescription()
    {
        return salesDescription;
    }

    public void setSalesDescription(String salesDescription)
    {
        this.salesDescription = salesDescription;
    }

    @Override
    public String toString()
    {
        return "ImportReportDetailLine [itemId=" + itemId
                + ", salesDescription=" + salesDescription + "]";
    }
    
    
    
}
