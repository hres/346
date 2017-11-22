package hc.fcdr.rws.model.sales;

import javax.xml.bind.annotation.XmlElement;


public class SalesUpdateRequest
{
       @XmlElement(nillable=true)
    public String  sales_description;
       @XmlElement(nillable=true)
    public String  sales_upc;
       @XmlElement(nillable=true)
    public String  sales_brand;
       @XmlElement(nillable=true)
    public String  sales_manufacturer;
       @XmlElement(nillable=true)
    public Double  dollar_rank;
       @XmlElement(nillable=true)
    public Double  dollar_volume;
       @XmlElement(nillable=true)
    public Double  dollar_share;
       @XmlElement(nillable=true)
    public Double  dollar_volume_percentage_change;
       @XmlElement(nillable=true)
    public Double  kilo_volume;
       @XmlElement(nillable=true)
    public Double  kilo_share;
       @XmlElement(nillable=true)
    public Double  kilo_volume_percentage_change;
       @XmlElement(nillable=true)
    public Double  average_ac_dist;
       @XmlElement(nillable=true)
    public Double  average_retail_price;
       @XmlElement(nillable=true)
    public String  sales_source;
       @XmlElement(nillable=true)
    public String  nielsen_category;
       @XmlElement(nillable=true)
    public Integer sales_year;
       @XmlElement(nillable=true)
    public Boolean control_label_flag;
       @XmlElement(nillable=true)
    public Double  kilo_volume_total;
       @XmlElement(nillable=true)
    public Double  kilo_volume_rank;
       @XmlElement(nillable=true)
    public Double  dollar_volume_total;
       @XmlElement(nillable=true)
    public Double  cluster_number;
       @XmlElement(nillable=true)
    public Double  product_grouping;
       @XmlElement(nillable=true)
    public String  sales_product_description;
       @XmlElement(nillable=true)
    public Double  classification_number;
       @XmlElement(nillable=true)
    public String  classification_type;
       @XmlElement(nillable=true)
    public String  sales_comment;
       @XmlElement(nillable=true)
    public String  sales_collection_date;

    public Integer number_of_units;
       @XmlElement(nillable=true)
    public String  edited_by;
    public Long    sales_id;

       public SalesUpdateRequest() {

   		this.sales_description = null;
   		this.sales_upc =null;
   		this.sales_brand = null;
   		this.sales_manufacturer = null;
   		this.dollar_rank = null;
   		this.dollar_volume = null;
   		this.dollar_share = null;
   		this.dollar_volume_percentage_change =null;
   		this.kilo_volume = null;
   		this.kilo_share = null;
   		this.kilo_volume_percentage_change = null;
   		this.average_ac_dist = null;
   		this.average_retail_price = null;
   		this.sales_source = null;
   		this.nielsen_category = null;
   		this.sales_year = null;
   		this.control_label_flag = null;
   		this.kilo_volume_total = null;
   		this.kilo_volume_rank = null;
   		this.dollar_volume_total = null;
   		this.cluster_number = null;
   		this.product_grouping = null;
   		this.sales_product_description = null;
   		this.classification_number = null;
   		this.classification_type = null;
   		this.sales_comment = null;
   		this.sales_collection_date = null;
   		this.number_of_units = null;
   		this.edited_by = null;
   		this.sales_id = null;
   	}
     
       
       
    public SalesUpdateRequest(String sales_description, String sales_upc, String sales_brand, String sales_manufacturer,
			Double dollar_rank, Double dollar_volume, Double dollar_share, Double dollar_volume_percentage_change,
			Double kilo_volume, Double kilo_share, Double kilo_volume_percentage_change, Double average_ac_dist,
			Double average_retail_price, String sales_source, String nielsen_category, Integer sales_year,
			Boolean control_label_flag, Double kilo_volume_total, Double kilo_volume_rank, Double dollar_volume_total,
			Double cluster_number, Double product_grouping, String sales_product_description,
			Double classification_number, String classification_type, String sales_comment,
			String sales_collection_date, Integer number_of_units, String edited_by, Long sales_id) {
		super();
		this.sales_description = sales_description;
		this.sales_upc = sales_upc;
		this.sales_brand = sales_brand;
		this.sales_manufacturer = sales_manufacturer;
		this.dollar_rank = dollar_rank;
		this.dollar_volume = dollar_volume;
		this.dollar_share = dollar_share;
		this.dollar_volume_percentage_change = dollar_volume_percentage_change;
		this.kilo_volume = kilo_volume;
		this.kilo_share = kilo_share;
		this.kilo_volume_percentage_change = kilo_volume_percentage_change;
		this.average_ac_dist = average_ac_dist;
		this.average_retail_price = average_retail_price;
		this.sales_source = sales_source;
		this.nielsen_category = nielsen_category;
		this.sales_year = sales_year;
		this.control_label_flag = control_label_flag;
		this.kilo_volume_total = kilo_volume_total;
		this.kilo_volume_rank = kilo_volume_rank;
		this.dollar_volume_total = dollar_volume_total;
		this.cluster_number = cluster_number;
		this.product_grouping = product_grouping;
		this.sales_product_description = sales_product_description;
		this.classification_number = classification_number;
		this.classification_type = classification_type;
		this.sales_comment = sales_comment;
		this.sales_collection_date = sales_collection_date;
		this.number_of_units = number_of_units;
		this.edited_by = edited_by;
		this.sales_id = sales_id;
	}




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
                + edited_by + ", sales_id=" + sales_id + "]";
    }

}
