package hc.fcdr.rws.model.sales;

public class UpdateProductFields {

	private String product_description;
	private String product_brand;
	private Integer cluster_number;
	private String product_manufacturer;
	private String classification_number;
	
	
	public UpdateProductFields() {
		super();
		this.product_description = null;
		this.product_brand = null;
		this.cluster_number = null;
		this.product_manufacturer = null;
		this.classification_number = null;
	} 
	
	public UpdateProductFields(String product_description, String product_brand, Integer cluster_number,
			String product_manufacturer, String classification_number) {
		super();
		this.product_description = product_description;
		this.product_brand = product_brand;
		this.cluster_number = cluster_number;
		this.product_manufacturer = product_manufacturer;
		this.classification_number = classification_number;
	}

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}

	public String getProduct_brand() {
		return product_brand;
	}

	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}

	public Integer getCluster_number() {
		return cluster_number;
	}

	public void setCluster_number(Integer cluster_number) {
		this.cluster_number = cluster_number;
	}

	public String getProduct_manufacturer() {
		return product_manufacturer;
	}

	public void setProduct_manufacturer(String product_manufacturer) {
		this.product_manufacturer = product_manufacturer;
	}

	public String getClassification_number() {
		return classification_number;
	}

	public void setClassification_number(String classification_number) {
		this.classification_number = classification_number;
	} 
	
	
	
	
}
