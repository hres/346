package hc.fcdr.rws.model.product;

public class ProductClassificationDataResponse
{
    private int                       status;
    private ProductClassificationData data;
    private String                    message;

    public ProductClassificationDataResponse()
    {
        status = 0;
        data = new ProductClassificationData();
        message = "";
    }

    public ProductClassificationDataResponse(int status,
            ProductClassificationData data, String message)
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

    public ProductClassificationData getData()
    {
        return data;
    }

    public void setData(ProductClassificationData data)
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
