package hc.fcdr.rws.model.product;

import javax.xml.bind.annotation.XmlElement;

public class ProductInsertRequest {
	
    @XmlElement(nillable=true)
    private String  product_manufacturer;
    @XmlElement(nillable=true)
    private String  product_brand;
    @XmlElement(nillable=true)
    private String  product_description;
    @XmlElement(nillable=true)
    private String  product_comment;
    @XmlElement(nillable=true)
    private Integer  cnf_code;
    @XmlElement(nillable=true)
    private Double  cluster_number;
    @XmlElement(nillable=true)
    private String  restaurant_type;
    @XmlElement(nillable=true)
    private String  type;
    @XmlElement(nillable=true)
    private Double  classification_number;
    @XmlElement(nillable=true)
    private String  classification_name;
    @XmlElement(nillable=true)
    private String  classification_type;
    
    
    
    
	public ProductInsertRequest() {

		this.product_manufacturer = null;
		this.product_brand = null;
		this.product_description = null;
		this.product_comment = null;
		this.cnf_code = null;
		this.cluster_number = null;
		this.restaurant_type = null;
		this.type = null;
		this.classification_number = null;
		this.classification_name = null;
		this.classification_type = null;
		
	}




	public ProductInsertRequest(String product_manufacturer, String product_brand, String product_description,
			String product_comment, Integer cnf_code, Double cluster_number, String restaurant_type, String type,
			Double classification_number, String classification_name, String classification_type) {
		super();
		this.product_manufacturer = product_manufacturer;
		this.product_brand = product_brand;
		this.product_description = product_description;
		this.product_comment = product_comment;
		this.cnf_code = cnf_code;
		this.cluster_number = cluster_number;
		this.restaurant_type = restaurant_type;
		this.type = type;
		this.classification_number = classification_number;
		this.classification_name = classification_name;
		this.classification_type = classification_type;
	}




	public String getProduct_manufacturer() {
		return product_manufacturer;
	}




	public void setProduct_manufacturer(String product_manufacturer) {
		this.product_manufacturer = product_manufacturer;
	}




	public String getProduct_brand() {
		return product_brand;
	}




	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}




	public String getProduct_description() {
		return product_description;
	}




	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}




	public String getProduct_comment() {
		return product_comment;
	}




	public void setProduct_comment(String product_comment) {
		this.product_comment = product_comment;
	}




	public Integer getCnf_code() {
		return cnf_code;
	}




	public void setCnf_code(Integer cnf_code) {
		this.cnf_code = cnf_code;
	}




	public Double getCluster_number() {
		return cluster_number;
	}




	public void setCluster_number(Double cluster_number) {
		this.cluster_number = cluster_number;
	}




	public String getRestaurant_type() {
		return restaurant_type;
	}




	public void setRestaurant_type(String restaurant_type) {
		this.restaurant_type = restaurant_type;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public Double getClassification_number() {
		return classification_number;
	}




	public void setClassification_number(Double classification_number) {
		this.classification_number = classification_number;
	}




	public String getClassification_name() {
		return classification_name;
	}




	public void setClassification_name(String classification_name) {
		this.classification_name = classification_name;
	}




	public String getClassification_type() {
		return classification_type;
	}




	public void setClassification_type(String classification_type) {
		this.classification_type = classification_type;
	}
    
	
	
    

}
