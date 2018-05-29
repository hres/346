package hc.fcdr.rws.model.sales;
import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;


public class ImportSalesMadatoryFields {

	private String sales_upc;
	private String sales_description;
	   @XmlElement(
	            nillable = true)
	private Date sales_collection_date;
	   @XmlElement(
	            nillable = true)
	private Double kilo_volume;
	private Double record_id;
	
	public ImportSalesMadatoryFields() {
		super();
		this.sales_upc = null;
		this.sales_description = null;
		this.sales_collection_date = null;
		this.kilo_volume = null;
		this.record_id = null;
	}
	
	public ImportSalesMadatoryFields(String sales_upc, String sales_description, Date sales_collection_date, Double kilo_volume, Double record_id)
			 {
		super();
		this.sales_upc = sales_upc;
		this.sales_description = sales_description;
		this.sales_collection_date = sales_collection_date;
		this.kilo_volume = kilo_volume;
		this.record_id =record_id;
	}

	


	public Double getRecord_id() {
		return record_id;
	}

	public void setRecord_id(Double record_id) {
		this.record_id = record_id;
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

	public Date getSales_collection_date() {
		return sales_collection_date;
	}

	public void setSales_collection_date(Date sales_collection_date) {
		this.sales_collection_date = sales_collection_date;
	}

	public Double getKilo_volume() {
		return kilo_volume;
	}

	public void setKilo_volume(Double kilo_volume) {
		this.kilo_volume = kilo_volume;
	}
	
	
	

	
	
	
		
	
}
