package hc.fcdr.rws.model.importLabel;

import javax.xml.bind.annotation.XmlElement;

public class ImportLabelNft {
    @XmlElement(
            nillable = true)
    private String component_name;
    @XmlElement(
            nillable = true)
    private Double amount;
    @XmlElement(
            nillable = true)
    private String unit_of_measure;
    @XmlElement(
            nillable = true)
    private Double daily_value;
    @XmlElement(
            nillable = true)
    private Double amount_per100g;
    @XmlElement(
            nillable = true)
    private Boolean flag;
    
    
    
	public ImportLabelNft() {
		super();
		this.component_name = null;
		this.amount = null;
		this.unit_of_measure = null;
		this.daily_value = null;
		this.amount_per100g = null;
		this.flag = null;
	}



	public ImportLabelNft(String component_name, Double amount, String unit_of_measure, Double daily_value, Double amount_per100g,
			Boolean flag) {
		super();
		this.component_name = component_name;
		this.amount = amount;
		this.unit_of_measure = unit_of_measure;
		this.daily_value = daily_value;
		this.amount_per100g = amount_per100g;
		this.flag = flag;
	}



	public String getcomponent_name() {
		return component_name;
	}



	public void setcomponent_name(String component_name) {
		this.component_name = component_name;
	}



	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}



	public String getUnit_of_measure() {
		return unit_of_measure;
	}



	public void setUnit_of_measure(String unit_of_measure) {
		this.unit_of_measure = unit_of_measure;
	}



	public Double getDaily_value() {
		return daily_value;
	}



	public void setDaily_value(Double daily_value) {
		this.daily_value = daily_value;
	}



	public Double getAmount_per100g() {
		return amount_per100g;
	}



	public void setAmount_per100g(Double amount_per100g) {
		this.amount_per100g = amount_per100g;
	}



	public Boolean getFlag() {
		return flag;
	}



	public void setFlag(Boolean flag) {
		this.flag = flag;
	} 
    
    
	
    
}
