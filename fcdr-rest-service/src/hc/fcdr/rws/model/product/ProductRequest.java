package hc.fcdr.rws.model.product;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductRequest
{
    @XmlElement
    public String  product_manufacturer;
    @XmlElement
    public String  product_brand;
    @XmlElement
    public Integer cnf_code;
    @XmlElement
    public Double  cluster_number;
    @XmlElement
    public String  product_description;
    @XmlElement
    public String  product_comment;
    @XmlElement
    public Double  classification_number;
    @XmlElement
    public String  classification_name;
    @XmlElement
    public String  classification_type;
    @XmlElement
    public String  orderby;
    @XmlElement
    public Integer offset;
    @XmlElement
    public boolean flag;
    @XmlElement
    public String  restaurant_type;
    @XmlElement
    public String  type;

    @Override
    public String toString()
    {
        return "ProductRequest [product_manufacturer=" + product_manufacturer
                + ", product_brand=" + product_brand + ", cnf_code=" + cnf_code
                + ", cluster_number=" + cluster_number
                + ", product_description=" + product_description
                + ", product_comment=" + product_comment
                + ", classification_number=" + classification_number
                + ", classification_name=" + classification_name
                + ", classification_type=" + classification_type + ", orderby="
                + orderby + ", offset=" + offset + ", flag=" + flag
                + ", restaurant_type=" + restaurant_type + ", type=" + type
                + "]";
    }

}
