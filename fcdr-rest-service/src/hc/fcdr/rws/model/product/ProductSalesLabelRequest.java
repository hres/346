package hc.fcdr.rws.model.product;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductSalesLabelRequest
{
    @XmlElement
    public String  product_description;
    @XmlElement
    public String  product_brand;
    @XmlElement
    public String  product_manufacturer;
    @XmlElement
    public Double  classification_number;
    @XmlElement
    public String  classification_name;
    @XmlElement
    public String  classification_type;
    @XmlElement
    public Integer cnf_code;
    @XmlElement
    public Double  cluster_number;
    @XmlElement
    public String  product_comment;

    @XmlElement
    public Integer sales_year;
    @XmlElement
    public String  sales_description;
    @XmlElement
    public String  sales_upc;
    @XmlElement
    public String  nielsen_category;
    @XmlElement
    public String  sales_source;
    @XmlElement
    public String  sales_collection_date_from;
    @XmlElement
    public String  sales_collection_date_to;
    @XmlElement
    public Double  dollar_rank_from;
    @XmlElement
    public Double  dollar_rank_to;
    @XmlElement
    public String  sales_comment;

    @XmlElement
    public String  label_upc;
    @XmlElement
    public String  label_description;
    @XmlElement
    public String  label_source;
    @XmlElement
    public String  label_ingredients;
    @XmlElement
    public String  label_collection_date_from;
    @XmlElement
    public String  label_collection_date_to;
    @XmlElement
    public String  label_comment;

    @XmlElement
    public String  orderby;
    @XmlElement
    public boolean flag;
    @XmlElement
    public Integer offset;

    @Override
    public String toString()
    {
        return "ProductSalesLabelRequest [product_description="
                + product_description + ", product_brand=" + product_brand
                + ", product_manufacturer=" + product_manufacturer
                + ", classification_number=" + classification_number
                + ", classification_name=" + classification_name
                + ", classification_type=" + classification_type + ", cnf_code="
                + cnf_code + ", cluster_number=" + cluster_number
                + ", product_comment=" + product_comment + ", sales_year="
                + sales_year + ", sales_description=" + sales_description
                + ", sales_upc=" + sales_upc + ", nielsen_category="
                + nielsen_category + ", sales_source=" + sales_source
                + ", sales_collection_date_from=" + sales_collection_date_from
                + ", sales_collection_date_to=" + sales_collection_date_to
                + ", dollar_rank_from=" + dollar_rank_from + ", dollar_rank_to="
                + dollar_rank_to + ", sales_comment=" + sales_comment
                + ", label_upc=" + label_upc + ", label_description="
                + label_description + ", label_source=" + label_source
                + ", label_ingredients=" + label_ingredients
                + ", label_collection_date_from=" + label_collection_date_from
                + ", label_collection_date_to=" + label_collection_date_to
                + ", label_comment=" + label_comment + ", orderby=" + orderby
                + ", flag=" + flag + ", offset=" + offset + "]";
    }

}
