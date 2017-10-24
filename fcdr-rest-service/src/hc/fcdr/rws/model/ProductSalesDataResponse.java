package hc.fcdr.rws.model;

public class ProductSalesDataResponse
{
    private int              status;
    private ProductSalesData data;
    private String           message;

    public ProductSalesDataResponse()
    {
        status = 0;
        data = new ProductSalesData();
        message = "";
    }

    public ProductSalesDataResponse(int status, ProductSalesData data,
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

    public ProductSalesData getData()
    {
        return data;
    }

    public void setData(ProductSalesData data)
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
