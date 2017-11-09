package hc.fcdr.rws.model.product;

public class ProductLabelsDataResponse
{
    private int               status;
    private ProductLabelsData data;
    private String            message;

    public ProductLabelsDataResponse()
    {
        status = 0;
        data = new ProductLabelsData();
        message = "";
    }

    public ProductLabelsDataResponse(final int status,
            final ProductLabelsData data, final String message)
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

    public ProductLabelsData getData()
    {
        return data;
    }

    public void setData(final ProductLabelsData data)
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
