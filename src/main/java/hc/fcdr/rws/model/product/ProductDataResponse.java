package hc.fcdr.rws.model.product;

public class ProductDataResponse
{
    private int         status;
    private ProductData data;
    private String      message;

    public ProductDataResponse()
    {
        status = 0;
        data = new ProductData();
        message = "";
    }

    public ProductDataResponse(final int status, final ProductData data,
            final String message)
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

    public ProductData getData()
    {
        return data;
    }

    public void setData(final ProductData data)
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
