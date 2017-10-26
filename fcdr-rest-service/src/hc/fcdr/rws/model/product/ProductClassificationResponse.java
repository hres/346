package hc.fcdr.rws.model.product;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Product;

@XmlRootElement
public class ProductClassificationResponse
{
    private Long   product_id;
    private String product_manufacturer;
    private String product_brand;
    private String product_country;
    private String cnf_code;
    private String cluster_number;
    private String product_description;
    private String product_comment;
    private String creation_date;
    private String last_edit_date;
    private String edited_by;
    private String restaurant_type;
    private String type;
    private String classification_number;
    private String classification_name;
    private String classification_type;

    public ProductClassificationResponse()
    {
        super();
        this.product_id = 0L;
        this.product_manufacturer = "";
        this.product_brand = "";
        this.cnf_code = "";
        this.cluster_number = "";
        this.product_description = "";
        this.product_comment = "";
        this.creation_date = "";
        this.last_edit_date = "";
        this.edited_by = "";
        this.restaurant_type = "";
        this.type = "";
        this.classification_number = "";
        this.classification_name = "";
        this.classification_type = "";
    }

    public ProductClassificationResponse(Product product)
    {
        super();
        this.product_id = product.getId();
        this.product_manufacturer = product.getManufacturer();
        this.product_brand = product.getBrand();
        this.cnf_code = product.getCnfCode();
        this.cluster_number = product.getClusterNumber();
        this.product_description = product.getDescription();
        this.product_comment = product.getComment();
        this.creation_date = product.getCreationDate().toString();
        this.last_edit_date = product.getLastEditDate().toString();
        this.edited_by = product.getEditedBy();
        this.restaurant_type = product.getRestaurantType();
        this.type = product.getType();
        this.classification_number = "";
        this.classification_name = "";
        this.classification_type = "";
    }

    public Long getProduct_id()
    {
        return product_id;
    }

    public void setProduct_id(Long product_id)
    {
        this.product_id = product_id;
    }

    public String getProduct_manufacturer()
    {
        return product_manufacturer;
    }

    public void setProduct_manufacturer(String product_manufacturer)
    {
        this.product_manufacturer = product_manufacturer;
    }

    public String getProduct_brand()
    {
        return product_brand;
    }

    public void setProduct_brand(String product_brand)
    {
        this.product_brand = product_brand;
    }

    public String getProduct_country()
    {
        return product_country;
    }

    public void setProduct_country(String product_country)
    {
        this.product_country = product_country;
    }

    public String getCnf_code()
    {
        return cnf_code;
    }

    public void setCnf_code(String cnf_code)
    {
        this.cnf_code = cnf_code;
    }

    public String getCluster_number()
    {
        return cluster_number;
    }

    public void setCluster_number(String cluster_number)
    {
        this.cluster_number = cluster_number;
    }

    public String getProduct_description()
    {
        return product_description;
    }

    public void setProduct_description(String product_description)
    {
        this.product_description = product_description;
    }

    public String getProduct_comment()
    {
        return product_comment;
    }

    public void setProduct_comment(String product_comment)
    {
        this.product_comment = product_comment;
    }

    public String getCreation_date()
    {
        return creation_date;
    }

    public void setCreation_date(String creation_date)
    {
        this.creation_date = creation_date;
    }

    public String getLast_edit_date()
    {
        return last_edit_date;
    }

    public void setLast_edit_date(String last_edit_date)
    {
        this.last_edit_date = last_edit_date;
    }

    public String getEdited_by()
    {
        return edited_by;
    }

    public void setEdited_by(String edited_by)
    {
        this.edited_by = edited_by;
    }

    public String getRestaurant_type()
    {
        return restaurant_type;
    }

    public void setRestaurant_type(String restaurant_type)
    {
        this.restaurant_type = restaurant_type;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getClassification_number()
    {
        return classification_number;
    }

    public void setClassification_number(String classification_number)
    {
        this.classification_number = classification_number;
    }

    public String getClassification_name()
    {
        return classification_name;
    }

    public void setClassification_name(String classification_name)
    {
        this.classification_name = classification_name;
    }

    public String getClassification_type()
    {
        return classification_type;
    }

    public void setClassification_type(String classification_type)
    {
        this.classification_type = classification_type;
    }

}
