package hc.fcdr.rws.model.importLabel;

import java.sql.Date;

public class ExistingLabels {
	
	private Integer product_id;
	private String label_upc;
	private String label_description;
	private String label_source;
	private Date label_collection_date;
	private Double product_grouping;
	
	
	
	public ExistingLabels() {
		super();
		this.product_id = null;
		this.label_upc = null;
		this.label_description = null;
		this.label_source = null;
		this.label_collection_date = null;
		this.setProduct_grouping(null);
	}
	
	public ExistingLabels(Integer product_id, String label_upc, String label_description, String label_source,
			Date label_collection_date, Double product_grouping) {
		super();
		this.product_id = product_id;
		this.label_upc = label_upc;
		this.label_description = label_description;
		this.label_source = label_source;
		this.label_collection_date = label_collection_date;
		this.setProduct_grouping(product_grouping);
	}

	
	public Integer getproduct_id() {
		return product_id;
	}

	public void setproduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public String getLabel_upc() {
		return label_upc;
	}

	public void setLabel_upc(String label_upc) {
		this.label_upc = label_upc;
	}

	public String getLabel_description() {
		return label_description;
	}

	public void setLabel_description(String label_description) {
		this.label_description = label_description;
	}

	public String getLabel_source() {
		return label_source;
	}

	public void setLabel_source(String label_source) {
		this.label_source = label_source;
	}

	public Date getLabel_collection_date() {
		return label_collection_date;
	}

	public void setLabel_collection_date(Date label_collection_date) {
		this.label_collection_date = label_collection_date;
	}

	

	public Double getProduct_grouping() {
		return product_grouping;
	}

	public void setProduct_grouping(Double product_grouping) {
		this.product_grouping = product_grouping;
	}
	
	
	
	
	

}
