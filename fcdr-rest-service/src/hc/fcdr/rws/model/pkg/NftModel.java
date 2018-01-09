package hc.fcdr.rws.model.pkg;
import javax.xml.bind.annotation.XmlElement;
public class NftModel {

	private String name; 
	@XmlElement(nillable=true)
	private Double amount;
	private String unit_of_measure;
	@XmlElement(nillable=true)
	private Double daily_value;
	
	
	
	public NftModel() {
		this.name = null;
		this.amount = null;
		this.unit_of_measure = null;
		this.daily_value = null;
	}



	public NftModel(String name, Double amount, String unit_of_measure, Double daily_value) {
		super();
		this.name = name;
		this.amount = amount;
		this.unit_of_measure = unit_of_measure;
		this.daily_value = daily_value;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
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
	
	
	
	
	
}
