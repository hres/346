package hc.fcdr.rws.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductRequest
{
    // @XmlElement public String manufacturer;
    // @XmlElement public String brand;
    // @XmlElement public Integer cnfCode;
    // @XmlElement public Double clusterNumber;
    // @XmlElement public String description;
    // @XmlElement public String comment;
    // @XmlElement public Double classificationNumber;
    // @XmlElement public String classificationName;
    // @XmlElement public String classificationType;
    // @XmlElement public String orderby;
    // @XmlElement public Integer offset;
    // @XmlElement public boolean sortOrder;

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
                + orderby + ", offset=" + offset + ", flag=" + flag + "]";
    }

    // @Override
    // public String toString()
    // {
    // return "ProductRequest [manufacturer=" + manufacturer + ", brand="
    // + brand + ", cnfCode=" + cnfCode + ", clusterNumber="
    // + clusterNumber + ", description=" + description + ", comment="
    // + comment + ", classificationNumber=" + classificationNumber
    // + ", classificationName=" + classificationName
    // + ", classificationType=" + classificationType + ", orderby="
    // + orderby + ", offset=" + offset + ", sortOrder=" + sortOrder
    // + "]";
    // }

}
