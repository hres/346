package hc.fcdr.rws.util;

import javax.xml.bind.annotation.XmlElement;

public class LabelGrouping {
    @XmlElement(
            nillable = true)
    private String  package_brand;
    @XmlElement(
            nillable = true)
    private String  package_manufacturer;
    @XmlElement(
            nillable = true)
    private String  classification_number;
    @XmlElement(
            nillable = true)
    private String type_of_restaurant;
    
    
	public LabelGrouping() {
		super();
		this.package_brand = null;
		this.package_manufacturer = null;
		this.classification_number = null;
		this.type_of_restaurant = null;
	}
    
    
	public LabelGrouping(String package_brand, String package_manufacturer, String classification_number,
			String type_of_restaurant) {
		super();
		this.package_brand = package_brand;
		this.package_manufacturer = package_manufacturer;
		this.classification_number = classification_number;
		this.type_of_restaurant = type_of_restaurant;
	}
	
	public String getPackage_brand() {
		return package_brand;
	}
	public void setPackage_brand(String package_brand) {
		this.package_brand = package_brand;
	}
	public String getPackage_manufacturer() {
		return package_manufacturer;
	}
	public void setPackage_manufacturer(String package_manufacturer) {
		this.package_manufacturer = package_manufacturer;
	}
	public String getClassification_number() {
		return classification_number;
	}
	public void setClassification_number(String classification_number) {
		this.classification_number = classification_number;
	}
	public String getType_of_restaurant() {
		return type_of_restaurant;
	}
	public void setType_of_restaurant(String type_of_restaurant) {
		this.type_of_restaurant = type_of_restaurant;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classification_number == null) ? 0 : classification_number.hashCode());
		result = prime * result + ((package_brand == null) ? 0 : package_brand.hashCode());
		result = prime * result + ((package_manufacturer == null) ? 0 : package_manufacturer.hashCode());
		result = prime * result + ((type_of_restaurant == null) ? 0 : type_of_restaurant.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LabelGrouping other = (LabelGrouping) obj;
		if (classification_number == null) {
			if (other.classification_number != null)
				return false;
		} else if (!classification_number.equals(other.classification_number))
			return false;
		if (package_brand == null) {
			if (other.package_brand != null)
				return false;
		} else if (!package_brand.equals(other.package_brand))
			return false;
		if (package_manufacturer == null) {
			if (other.package_manufacturer != null)
				return false;
		} else if (!package_manufacturer.equals(other.package_manufacturer))
			return false;
		if (type_of_restaurant == null) {
			if (other.type_of_restaurant != null)
				return false;
		} else if (!type_of_restaurant.equals(other.type_of_restaurant))
			return false;
		return true;
	}
    
    
    
    
}
