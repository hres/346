package hc.fcdr.rws.model.sales;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SalesInsertRequest
{
    @XmlElement
    public String  sales_description;
    @XmlElement
    public String  sales_upc;
    @XmlElement
    public String  sales_brand;
    @XmlElement
    public String  sales_manufacturer;
    @XmlElement
    public Double  dollar_rank;
    @XmlElement
    public Double  dollar_volume;
    @XmlElement
    public Double  dollar_share;
    @XmlElement
    public Double  dollar_volume_percentage_change;
    @XmlElement
    public Double  kilo_volume;
    @XmlElement
    public Double  kilo_share;
    @XmlElement
    public Double  kilo_volume_percentage_change;
    @XmlElement
    public Double  average_ac_dist;
    @XmlElement
    public Double  average_retail_price;
    @XmlElement
    public String  sales_source;
    @XmlElement
    public String  nielsen_category;
    @XmlElement
    public Integer sales_year;
    @XmlElement
    public Boolean control_label_flag;
    @XmlElement
    public Double  kilo_volume_total;
    @XmlElement
    public Double  kilo_volume_rank;
    @XmlElement
    public Double  dollar_volume_total;
    @XmlElement
    public Double  cluster_number;
    @XmlElement
    public Double  product_grouping;
    @XmlElement
    public String  sales_product_description;
    @XmlElement
    public Double  classification_number;
    @XmlElement
    public String  classification_type;
    @XmlElement
    public String  sales_comment;
    @XmlElement
    public String  sales_collection_date;
    @XmlElement
    public Integer number_of_units;
    @XmlElement
    public String  edited_by;
    @XmlElement
    public Integer product_id;

    @Override
    public String toString()
    {
        return "SalesInsertRequest [sales_description=" + sales_description
                + ", sales_upc=" + sales_upc + ", sales_brand=" + sales_brand
                + ", sales_manufacturer=" + sales_manufacturer
                + ", dollar_rank=" + dollar_rank + ", dollar_volume="
                + dollar_volume + ", dollar_share=" + dollar_share
                + ", dollar_volume_percentage_change="
                + dollar_volume_percentage_change + ", kilo_volume="
                + kilo_volume + ", kilo_share=" + kilo_share
                + ", kilo_volume_percentage_change="
                + kilo_volume_percentage_change + ", average_ac_dist="
                + average_ac_dist + ", average_retail_price="
                + average_retail_price + ", sales_source=" + sales_source
                + ", nielsen_category=" + nielsen_category + ", sales_year="
                + sales_year + ", control_label_flag=" + control_label_flag
                + ", kilo_volume_total=" + kilo_volume_total
                + ", kilo_volume_rank=" + kilo_volume_rank
                + ", dollar_volume_total=" + dollar_volume_total
                + ", cluster_number=" + cluster_number + ", product_grouping="
                + product_grouping + ", sales_product_description="
                + sales_product_description + ", classification_number="
                + classification_number + ", classification_type="
                + classification_type + ", sales_comment=" + sales_comment
                + ", sales_collection_date=" + sales_collection_date
                + ", number_of_units=" + number_of_units + ", edited_by="
                + edited_by + ", product_id=" + product_id + "]";
    }

}
