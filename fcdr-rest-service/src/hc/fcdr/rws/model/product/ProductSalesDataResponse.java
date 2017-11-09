package hc.fcdr.rws.model.product;

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

    public ProductSalesDataResponse(final int status,
            final ProductSalesData data, final String message)
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

    public ProductSalesData getData()
    {
        return data;
    }

    public void setData(final ProductSalesData data)
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
