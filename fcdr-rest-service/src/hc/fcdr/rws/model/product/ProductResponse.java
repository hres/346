package hc.fcdr.rws.model.product;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Product;

@XmlRootElement
public class ProductResponse
{
    private Long   product_id;
    private String product_manufacturer;
    private String product_brand;
    private String cnf_code;
    private String cluster_number;
    private String product_description;
    private String product_comment;
    private String classification_number;
    private String classification_name;
    private String classification_type;

    public ProductResponse()
    {
        super();
        this.product_id = 0L;
        this.product_manufacturer = "";
        this.product_brand = "";
        this.cnf_code = "";
        this.cluster_number = "";
        this.product_description = "";
        this.product_comment = "";
        this.classification_number = "";
        this.classification_name = "";
        this.classification_type = "";
    }

    public ProductResponse(Long id, String manufacturer, String brand,
            String cnfCode, String clusterNumber, String description,
            String comment, String classificationNumber,
            String classificationName, String classificationType)
    {
        super();
        this.product_id = id;
        this.product_manufacturer = manufacturer;
        this.product_brand = brand;
        this.cnf_code = cnfCode;
        this.cluster_number = clusterNumber;
        this.product_description = description;
        this.product_comment = comment;

        this.classification_number = classificationNumber;
        this.classification_name = classificationName;
        this.classification_type = classificationType;
    }

    public ProductResponse(Product product, String classificationNumber,
            String classificationName, String classificationType)
    {
        super();
        this.product_id = product.getId();
        this.product_manufacturer = product.getManufacturer();
        this.product_brand = product.getBrand();
        this.cnf_code = product.getCnfCode();
        this.cluster_number = product.getClusterNumber();
        this.product_description = product.getDescription();
        this.product_comment = product.getComment();
        this.classification_number = classificationNumber;
        this.classification_name = classificationName;
        this.classification_type = classificationType;
    }

    public ProductResponse(Product product)
    {
        super();
        this.product_id = product.getId();
        this.product_manufacturer = product.getManufacturer();
        this.product_brand = product.getBrand();
        this.cnf_code = product.getCnfCode();
        this.cluster_number = product.getClusterNumber();
        this.product_description = product.getDescription();
        this.product_comment = product.getComment();
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

    public void setClassificationType(String classificationType)
    {
        this.classification_type = classificationType;
    }

}
