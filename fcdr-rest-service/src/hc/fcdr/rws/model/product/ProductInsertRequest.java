package hc.fcdr.rws.model.product;

import javax.xml.bind.annotation.XmlElement;

public class ProductInsertRequest
{

    @XmlElement(
            nillable = true)
    private String  product_manufacturer;
    @XmlElement(
            nillable = true)
    private String  product_brand;
    @XmlElement(
            nillable = true)
    private String  product_description;
    @XmlElement(
            nillable = true)
    private String  product_comment;
    @XmlElement(
            nillable = true)
    private Integer cnf_code;
    @XmlElement(
            nillable = true)
    private Double  cluster_number;
    @XmlElement(
            nillable = true)
    private String  restaurant_type;
    @XmlElement(
            nillable = true)
    private String  type;
    @XmlElement(
            nillable = true)
    private Double  classification_number;
    @XmlElement(
            nillable = true)
    private String  classification_name;
    @XmlElement(
            nillable = true)
    private String  classification_type;

    public ProductInsertRequest()
    {

        product_manufacturer = null;
        product_brand = null;
        product_description = null;
        product_comment = null;
        cnf_code = null;
        cluster_number = null;
        restaurant_type = null;
        type = null;
        classification_number = null;
        classification_name = null;
        classification_type = null;

    }

    public ProductInsertRequest(final String product_manufacturer,
            final String product_brand, final String product_description,
            final String product_comment, final Integer cnf_code,
            final Double cluster_number, final String restaurant_type,
            final String type, final Double classification_number,
            final String classification_name, final String classification_type)
    {
        super();
        this.product_manufacturer = product_manufacturer;
        this.product_brand = product_brand;
        this.product_description = product_description;
        this.product_comment = product_comment;
        this.cnf_code = cnf_code;
        this.cluster_number = cluster_number;
        this.restaurant_type = restaurant_type;
        this.type = type;
        this.classification_number = classification_number;
        this.classification_name = classification_name;
        this.classification_type = classification_type;
    }

    public String getProduct_manufacturer()
    {
        return product_manufacturer;
    }

    public void setProduct_manufacturer(final String product_manufacturer)
    {
        this.product_manufacturer = product_manufacturer;
    }

    public String getProduct_brand()
    {
        return product_brand;
    }

    public void setProduct_brand(final String product_brand)
    {
        this.product_brand = product_brand;
    }

    public String getProduct_description()
    {
        return product_description;
    }

    public void setProduct_description(final String product_description)
    {
        this.product_description = product_description;
    }

    public String getProduct_comment()
    {
        return product_comment;
    }

    public void setProduct_comment(final String product_comment)
    {
        this.product_comment = product_comment;
    }

    public Integer getCnf_code()
    {
        return cnf_code;
    }

    public void setCnf_code(final Integer cnf_code)
    {
        this.cnf_code = cnf_code;
    }

    public Double getCluster_number()
    {
        return cluster_number;
    }

    public void setCluster_number(final Double cluster_number)
    {
        this.cluster_number = cluster_number;
    }

    public String getRestaurant_type()
    {
        return restaurant_type;
    }

    public void setRestaurant_type(final String restaurant_type)
    {
        this.restaurant_type = restaurant_type;
    }

    public String getType()
    {
        return type;
    }

    public void setType(final String type)
    {
        this.type = type;
    }

    public Double getClassification_number()
    {
        return classification_number;
    }

    public void setClassification_number(final Double classification_number)
    {
        this.classification_number = classification_number;
    }

    public String getClassification_name()
    {
        return classification_name;
    }

    public void setClassification_name(final String classification_name)
    {
        this.classification_name = classification_name;
    }

    public String getClassification_type()
    {
        return classification_type;
    }

    public void setClassification_type(final String classification_type)
    {
        this.classification_type = classification_type;
    }

}
