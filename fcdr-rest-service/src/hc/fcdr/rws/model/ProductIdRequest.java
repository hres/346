package hc.fcdr.rws.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductIdRequest
{
    @XmlElement
    public Long productId;

    @Override
    public String toString()
    {
        return "ProductIdRequest [productId=" + productId + "]";
    }
}
