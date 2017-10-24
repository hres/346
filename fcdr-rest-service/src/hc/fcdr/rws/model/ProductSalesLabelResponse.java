package hc.fcdr.rws.model;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Product;

@XmlRootElement
public class ProductSalesLabelResponse
{
    private Long   product_id;
    private String product_description;
    private String product_brand;
    private String product_manufacturer;
    private String cnf_code;
    private String cluster_number;
    private String product_comment;

    private String classification_number;
    private String classification_name;
    private String classification_type;

    private String sales_year;
    private String sales_description;
    private String sales_upc;
    private String nielsen_category;
    private String sales_source;
    private String sales_collection_date;
    private String dollar_rank;
    private String sales_comment;

    private String label_upc;
    private String label_description;
    private String label_source;
    private String label_ingredients;
    private String label_collection_date;
    private String label_comment;

    public ProductSalesLabelResponse()
    {
        super();
        this.product_id = 0L;
        this.product_description = "";
        this.product_brand = "";
        this.product_manufacturer = "";
        this.cnf_code = "";
        this.cluster_number = "";
        this.product_comment = "";

        this.classification_number = "";
        this.classification_name = "";
        this.classification_type = "";

        this.sales_year = "";
        this.sales_description = "";
        this.sales_upc = "";
        this.nielsen_category = "";
        this.sales_source = "";
        this.sales_collection_date = "";
        this.dollar_rank = "";
        this.sales_comment = "";

        this.label_upc = "";
        this.label_description = "";
        this.label_source = "";
        this.label_ingredients = "";
        this.label_collection_date = "";
        this.label_comment = "";
    }

    public ProductSalesLabelResponse(Product product)
    {
        super();
        this.product_id = 0L;
        this.product_description = product.getDescription();
        this.product_brand = product.getBrand();
        this.product_manufacturer = product.getManufacturer();
        this.cnf_code = product.getCnfCode();
        this.cluster_number = product.getClusterNumber();
        this.product_comment = product.getComment();

        this.classification_number = "";
        this.classification_name = "";
        this.classification_type = "";

        this.sales_year = "";
        this.sales_description = "";
        this.sales_upc = "";
        this.nielsen_category = "";
        this.sales_source = "";
        this.sales_collection_date = "";
        this.dollar_rank = "";
        this.sales_comment = "";

        this.label_upc = "";
        this.label_description = "";
        this.label_source = "";
        this.label_ingredients = "";
        this.label_collection_date = "";
        this.label_comment = "";
    }

    public Long getProduct_id()
    {
        return product_id;
    }

    public void setProduct_id(Long product_id)
    {
        this.product_id = product_id;
    }

    public String getProduct_description()
    {
        return product_description;
    }

    public void setProduct_description(String product_description)
    {
        this.product_description = product_description;
    }

    public String getProduct_brand()
    {
        return product_brand;
    }

    public void setProduct_brand(String product_brand)
    {
        this.product_brand = product_brand;
    }

    public String getProduct_manufacturer()
    {
        return product_manufacturer;
    }

    public void setProduct_manufacturer(String product_manufacturer)
    {
        this.product_manufacturer = product_manufacturer;
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

    public String getSales_year()
    {
        return sales_year;
    }

    public void setSales_year(String sales_year)
    {
        this.sales_year = sales_year;
    }

    public String getSales_description()
    {
        return sales_description;
    }

    public void setSales_description(String sales_description)
    {
        this.sales_description = sales_description;
    }

    public String getSales_upc()
    {
        return sales_upc;
    }

    public void setSales_upc(String sales_upc)
    {
        this.sales_upc = sales_upc;
    }

    public String getNielsen_category()
    {
        return nielsen_category;
    }

    public void setNielsen_category(String nielsen_category)
    {
        this.nielsen_category = nielsen_category;
    }

    public String getSales_source()
    {
        return sales_source;
    }

    public void setSales_source(String sales_source)
    {
        this.sales_source = sales_source;
    }

    public String getSales_collection_date()
    {
        return sales_collection_date;
    }

    public void setSales_collection_date(String sales_collection_date)
    {
        this.sales_collection_date = sales_collection_date;
    }

    public String getDollar_rank()
    {
        return dollar_rank;
    }

    public void setDollar_rank(String dollar_rank)
    {
        this.dollar_rank = dollar_rank;
    }

    public String getSales_comment()
    {
        return sales_comment;
    }

    public void setSales_comment(String sales_comment)
    {
        this.sales_comment = sales_comment;
    }

    public String getLabel_upc()
    {
        return label_upc;
    }

    public void setLabel_upc(String label_upc)
    {
        this.label_upc = label_upc;
    }

    public String getLabel_description()
    {
        return label_description;
    }

    public void setLabel_description(String label_description)
    {
        this.label_description = label_description;
    }

    public String getLabel_source()
    {
        return label_source;
    }

    public void setLabel_source(String label_source)
    {
        this.label_source = label_source;
    }

    public String getLabel_ingredients()
    {
        return label_ingredients;
    }

    public void setLabel_ingredients(String label_ingredients)
    {
        this.label_ingredients = label_ingredients;
    }

    public String getLabel_collection_date()
    {
        return label_collection_date;
    }

    public void setLabel_collection_date(String label_collection_date)
    {
        this.label_collection_date = label_collection_date;
    }

    public String getLabel_comment()
    {
        return label_comment;
    }

    public void setLabel_comment(String label_comment)
    {
        this.label_comment = label_comment;
    }

}
