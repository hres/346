package hc.fcdr.rws.model.sales;





import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import com.opencsv.bean.CsvBindByPosition;

public class ImportMarketShare {

    @CsvBindByPosition(position = 0)
	private Double item_id;
    @CsvBindByPosition(position = 1)
	private String sales_upc; 
    @CsvBindByPosition(position = 2)
	private String sales_description;
    @CsvBindByPosition(position = 3)
	private String sales_brand; 
    @CsvBindByPosition(position = 4)
	private String sales_manufacturer;
    @CsvBindByPosition(position = 5)
	private Double dollar_rank;
    @CsvBindByPosition(position = 6)
	private Double dollar_volume;
    @CsvBindByPosition(position = 7)
	private Double dollar_share;
    @CsvBindByPosition(position = 8)
	private Double dollar_volume_percentage_change;
    @CsvBindByPosition(position = 9)
	private Double kilo_volume;
    @CsvBindByPosition(position = 10)
	private Double kilo_share;
    @CsvBindByPosition(position = 11)
	private Double kilo_volume_percentage_change;
    @CsvBindByPosition(position = 12)
	private Double average_ac_dist;
    @CsvBindByPosition(position = 13)
	private Double average_retail_price;
    @CsvBindByPosition(position = 14)
	private String sales_source;
    @CsvBindByPosition(position = 15)
	private String nielsen_category;
    @CsvBindByPosition(position = 16)
	private Date sales_collection_date;
    @CsvBindByPosition(position = 17)
	private Integer sales_year;
    @CsvBindByPosition(position = 18)
	private Boolean control_label_flag;
    @CsvBindByPosition(position = 19)
	private Double kilo_volume_total;
    @CsvBindByPosition(position = 20)
	private Double kilo_rank;
    @CsvBindByPosition(position = 21)
	private Double dollar_volume_total;
    @CsvBindByPosition(position = 22)
	private Integer cluster_number;
    @CsvBindByPosition(position = 23)
	private Double product_grouping;
    @CsvBindByPosition(position = 24)
	private String product_description;
    @CsvBindByPosition(position = 25)
	private String classification_number;
    @CsvBindByPosition(position = 26)
	private String classification_type;
    @CsvBindByPosition(position = 27)
	private String sales_comment;
	
	
//	private Double item_id;
//	private String sales_upc; 
//	private String sales_description;
//	private String sales_brand; 
//	private String sales_manufacturer;
//	private Double dollar_rank;
//	private Double dollar_volume;
//	private Double dollar_share;
//	private Double dollar_volume_percentage_change;
//	private Double kilo_volume;
//	private Double kilo_share;
//	private Double kilo_volume_percentage_change;
//	private Double average_ac_dist;
//	private Double average_retail_price;
//	private String sales_source;
//	private String nielsen_category;
//    @XmlElement(
//            nillable = true)
//	private Date sales_collection_date;
//	private Integer sales_year;
//	private Boolean control_label_flag;
//	private Double kilo_volume_total;
//	private Double kilo_rank;
//	private Double dollar_volume_total;
//	private Integer cluster_number;
//	private Double product_grouping;
//	private String product_description;
//	private Double classification_number;
//	private String classification_type;
//  
//	private String sales_comment;
	public ImportMarketShare() {
		super();
		this.item_id = null;
		this.sales_upc = null;
		this.sales_description = null;
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
		this.sales_collection_date = null;
		this.sales_year = null;
		this.control_label_flag = null;
		this.kilo_volume_total = null;
		this.kilo_rank = null;
		this.dollar_volume_total = null;
		this.cluster_number = null;
		this.product_grouping = null;
		this.product_description = null;
		this.classification_number = null;
		this.classification_type = null;
		this.sales_comment = null;
	}
	
	
	
	
	public ImportMarketShare(Double item_id, String sales_upc, String sales_description, String sales_brand,
			String sales_manufacturer, Double dollar_rank, Double dollar_volume, Double dollar_share,
			Double dollar_volume_percentage_change, Double kilo_volume, Double kilo_share,
			Double kilo_volume_percentage_change, Double average_ac_dist, Double average_retail_price,
			String sales_source, String nielsen_category, Date sales_collection_date, Integer sales_year,
			Boolean control_label_flag, Double kilo_volume_total, Double kilo_rank, Double dollar_volume_total,
			Integer cluster_number, Double product_grouping, String product_description, String classification_number,
			String classification_type, String sales_comment) {
		super();
		this.item_id = item_id;
		this.sales_upc = sales_upc;
		this.sales_description = sales_description;
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
		this.sales_collection_date = sales_collection_date;
		this.sales_year = sales_year;
		this.control_label_flag = control_label_flag;
		this.kilo_volume_total = kilo_volume_total;
		this.kilo_rank = kilo_rank;
		this.dollar_volume_total = dollar_volume_total;
		this.cluster_number = cluster_number;
		this.product_grouping = product_grouping;
		this.product_description = product_description;
		this.classification_number = classification_number;
		this.classification_type = classification_type;
		this.sales_comment = sales_comment;
	}
	public Double getItem_id() {
		return item_id;
	}
	public void setItem_id(Double item_id) {
		this.item_id = item_id;
	}
	public String getSales_upc() {
		return sales_upc;
	}
	public void setSales_upc(String sales_upc) {
		this.sales_upc = sales_upc;
	}
	public String getSales_description() {
		return sales_description;
	}
	public void setSales_description(String sales_description) {
		this.sales_description = sales_description;
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
	public Date getSales_collection_date() {
		return sales_collection_date;
	}
	public void setSales_collection_date(Date sales_collection_date) {
		this.sales_collection_date = sales_collection_date;
	}
	public Integer getsales_year() {
		return sales_year;
	}
	public void setsales_year(Integer sales_year) {
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
	public Double getKilo_rank() {
		return kilo_rank;
	}
	public void setKilo_rank(Double kilo_rank) {
		this.kilo_rank = kilo_rank;
	}
	public Double getDollar_volume_total() {
		return dollar_volume_total;
	}
	public void setDollar_volume_total(Double dollar_volume_total) {
		this.dollar_volume_total = dollar_volume_total;
	}
	public Integer getCluster_number() {
		return cluster_number;
	}
	public void setCluster_number(Integer cluster_number) {
		this.cluster_number = cluster_number;
	}
	public Double getProduct_grouping() {
		return product_grouping;
	}
	public void setProduct_grouping(Double product_grouping) {
		this.product_grouping = product_grouping;
	}
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	public String getClassification_number() {
		return classification_number;
	}
	public void setClassification_number(String classification_number) {
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
	
	
    public Boolean isValidRecord()
    {
        return (((sales_upc != null) && !sales_upc.isEmpty())
                && ((sales_description != null) && !sales_description.isEmpty())
                && ((kilo_volume != null) && (kilo_volume > 0))
                && ((sales_source != null) && !sales_source.isEmpty())
                && ((sales_year != null) && (sales_year > 1900))
                && (sales_collection_date != null));
    }
	
	
	
}
