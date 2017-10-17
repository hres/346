package hc.fcdr.rws.model;

public class ProductSalesLabelDataResponse
{
    private int                   status;
    private ProductSalesLabelData data;
    private String                message;

    public ProductSalesLabelDataResponse()
    {
        status = 0;
        data = new ProductSalesLabelData();
        message = "";
    }

    public ProductSalesLabelDataResponse(int status, ProductSalesLabelData data,
            String message)
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

    public ProductSalesLabelData getData()
    {
        return data;
    }

    public void setData(ProductSalesLabelData data)
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
