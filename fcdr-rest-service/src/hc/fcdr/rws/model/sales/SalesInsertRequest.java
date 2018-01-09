package hc.fcdr.rws.model.sales;

import javax.xml.bind.annotation.XmlElement;

public class SalesInsertRequest
{
   @XmlElement(nillable=true)
    public String  sales_description;
   @XmlElement(nillable=true)
    public String  sales_upc =null;
   @XmlElement(nillable=true)
    public String  sales_brand;
   @XmlElement(nillable=true)
    public String  sales_manufacturer;
   @XmlElement(nillable=true)
    public Double  dollar_rank=null;
   @XmlElement(nillable=true)
    public Double  dollar_volume=null;
   @XmlElement(nillable=true)
    public Double  dollar_share=null;
   @XmlElement(nillable=true)
    public Double  dollar_volume_percentage_change=null;
   @XmlElement(nillable=true)
    public Double  kilo_volume=null;
   @XmlElement(nillable=true)
    public Double  kilo_share=null;
   @XmlElement(nillable=true)
    public Double  kilo_volume_percentage_change=null;
   @XmlElement(nillable=true)
    public Double  average_ac_dist=null;
   @XmlElement(nillable=true)
    public Double  average_retail_price=null;
   @XmlElement(nillable=true)
    public String  sales_source;
   @XmlElement(nillable=true)
    public String  nielsen_category;
   @XmlElement(nillable=true)
    public Integer sales_year=null;
   @XmlElement(nillable=true)
    public Boolean control_label_flag=null;
   @XmlElement(nillable=true)
    public Double  kilo_volume_total=null;
   @XmlElement(nillable=true)
    public Double  kilo_volume_rank=null;
   @XmlElement(nillable=true)
    public Double  dollar_volume_total=null;
   @XmlElement(nillable=true)
    public Double  cluster_number=null;
   @XmlElement(nillable=true)
    public Double  product_grouping=null;
   @XmlElement(nillable=true)
    public String  sales_product_description;
   @XmlElement(nillable=true)
    public Double  classification_number=null;
   @XmlElement(nillable=true)
    public String  classification_type=null;
   @XmlElement(nillable=true)
    public String  sales_comment;
   @XmlElement(nillable=true)
    public String  sales_collection_date;
   @XmlElement(nillable=true)
    public Integer number_of_units=null;
   @XmlElement(nillable=true)
    public String  edited_by;
    public Integer product_id=null;

   public SalesInsertRequest() {
	
	this.sales_description = null;
	this.sales_upc = null;
	this.sales_brand = null;
	this.sales_manufacturer = null;
	this.dollar_rank = null;
	this.dollar_volume = null;
	this.dollar_share = null;
	this.dollar_volume_percentage_change = null;
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
	this.number_of_units =null;
	this.edited_by = null;
	this.product_id = null;
}

   
   
   
   
    public SalesInsertRequest(String sales_description, String sales_upc, String sales_brand, String sales_manufacturer,
		Double dollar_rank, Double dollar_volume, Double dollar_share, Double dollar_volume_percentage_change,
		Double kilo_volume, Double kilo_share, Double kilo_volume_percentage_change, Double average_ac_dist,
		Double average_retail_price, String sales_source, String nielsen_category, Integer sales_year,
		Boolean control_label_flag, Double kilo_volume_total, Double kilo_volume_rank, Double dollar_volume_total,
		Double cluster_number, Double product_grouping, String sales_product_description, Double classification_number,
		String classification_type, String sales_comment, String sales_collection_date, Integer number_of_units,
		String edited_by, Integer product_id) {
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
	this.product_id = product_id;
}






	public String getSales_description() {
		return sales_description;
	}





	public void setSales_description(String sales_description) {
		this.sales_description = sales_description;
	}





	public String getSales_upc() {
		return sales_upc;
	}





	public void setSales_upc(String sales_upc) {
		this.sales_upc = sales_upc;
	}





	public String getSales_brand() {
		return sales_brand;
	}





	public void setSales_brand(String sales_brand) {
		this.sales_brand = sales_brand;
	}





	public String getSales_manufacturer() {
		return sales_manufacturer;
	}





	public void setSales_manufacturer(String sales_manufacturer) {
		this.sales_manufacturer = sales_manufacturer;
	}





	public Double getDollar_rank() {
		return dollar_rank;
	}





	public void setDollar_rank(Double dollar_rank) {
		this.dollar_rank = dollar_rank;
	}





	public Double getDollar_volume() {
		return dollar_volume;
	}





	public void setDollar_volume(Double dollar_volume) {
		this.dollar_volume = dollar_volume;
	}





	public Double getDollar_share() {
		return dollar_share;
	}





	public void setDollar_share(Double dollar_share) {
		this.dollar_share = dollar_share;
	}





	public Double getDollar_volume_percentage_change() {
		return dollar_volume_percentage_change;
	}





	public void setDollar_volume_percentage_change(Double dollar_volume_percentage_change) {
		this.dollar_volume_percentage_change = dollar_volume_percentage_change;
	}





	public Double getKilo_volume() {
		return kilo_volume;
	}





	public void setKilo_volume(Double kilo_volume) {
		this.kilo_volume = kilo_volume;
	}





	public Double getKilo_share() {
		return kilo_share;
	}





	public void setKilo_share(Double kilo_share) {
		this.kilo_share = kilo_share;
	}





	public Double getKilo_volume_percentage_change() {
		return kilo_volume_percentage_change;
	}





	public void setKilo_volume_percentage_change(Double kilo_volume_percentage_change) {
		this.kilo_volume_percentage_change = kilo_volume_percentage_change;
	}





	public Double getAverage_ac_dist() {
		return average_ac_dist;
	}





	public void setAverage_ac_dist(Double average_ac_dist) {
		this.average_ac_dist = average_ac_dist;
	}





	public Double getAverage_retail_price() {
		return average_retail_price;
	}





	public void setAverage_retail_price(Double average_retail_price) {
		this.average_retail_price = average_retail_price;
	}





	public String getSales_source() {
		return sales_source;
	}





	public void setSales_source(String sales_source) {
		this.sales_source = sales_source;
	}





	public String getNielsen_category() {
		return nielsen_category;
	}





	public void setNielsen_category(String nielsen_category) {
		this.nielsen_category = nielsen_category;
	}





	public Integer getSales_year() {
		return sales_year;
	}





	public void setSales_year(Integer sales_year) {
		this.sales_year = sales_year;
	}





	public Boolean getControl_label_flag() {
		return control_label_flag;
	}





	public void setControl_label_flag(Boolean control_label_flag) {
		this.control_label_flag = control_label_flag;
	}





	public Double getKilo_volume_total() {
		return kilo_volume_total;
	}





	public void setKilo_volume_total(Double kilo_volume_total) {
		this.kilo_volume_total = kilo_volume_total;
	}





	public Double getKilo_volume_rank() {
		return kilo_volume_rank;
	}





	public void setKilo_volume_rank(Double kilo_volume_rank) {
		this.kilo_volume_rank = kilo_volume_rank;
	}





	public Double getDollar_volume_total() {
		return dollar_volume_total;
	}





	public void setDollar_volume_total(Double dollar_volume_total) {
		this.dollar_volume_total = dollar_volume_total;
	}





	public Double getCluster_number() {
		return cluster_number;
	}





	public void setCluster_number(Double cluster_number) {
		this.cluster_number = cluster_number;
	}





	public Double getProduct_grouping() {
		return product_grouping;
	}





	public void setProduct_grouping(Double product_grouping) {
		this.product_grouping = product_grouping;
	}





	public String getSales_product_description() {
		return sales_product_description;
	}





	public void setSales_product_description(String sales_product_description) {
		this.sales_product_description = sales_product_description;
	}





	public Double getClassification_number() {
		return classification_number;
	}





	public void setClassification_number(Double classification_number) {
		this.classification_number = classification_number;
	}





	public String getClassification_type() {
		return classification_type;
	}





	public void setClassification_type(String classification_type) {
		this.classification_type = classification_type;
	}





	public String getSales_comment() {
		return sales_comment;
	}





	public void setSales_comment(String sales_comment) {
		this.sales_comment = sales_comment;
	}





	public String getSales_collection_date() {
		return sales_collection_date;
	}





	public void setSales_collection_date(String sales_collection_date) {
		this.sales_collection_date = sales_collection_date;
	}





	public Integer getNumber_of_units() {
		return number_of_units;
	}





	public void setNumber_of_units(Integer number_of_units) {
		this.number_of_units = number_of_units;
	}





	public String getEdited_by() {
		return edited_by;
	}





	public void setEdited_by(String edited_by) {
		this.edited_by = edited_by;
	}





	public Integer getProduct_id() {
		return product_id;
	}





	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
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
                + edited_by + ", product_id=" + product_id + "]";
    }

}
