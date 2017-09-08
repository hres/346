package hc.fcdr.rws.model;

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

    public ProductDataResponse(int status, ProductData data, String message)
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

    public ProductData getData()
    {
        return data;
    }

    public void setData(ProductData data)
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
