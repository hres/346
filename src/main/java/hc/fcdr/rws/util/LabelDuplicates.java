package hc.fcdr.rws.util;

import javax.xml.bind.annotation.XmlElement;

public class LabelDuplicates {
	 @XmlElement(
	            nillable = true)
	    private String  package_description;
	    @XmlElement(
	            nillable = true)
	    private String  package_upc;
	    @XmlElement(
	            nillable = true)
	    private String  package_collection_date;
	    @XmlElement(
	            nillable = true)
	    private String  package_source;
	    
	    
	    
	    
	    
		public LabelDuplicates(String package_description, String package_upc, String package_collection_date,
				String package_source) {
			super();
			this.package_description = package_description;
			this.package_upc = package_upc;
			this.package_collection_date = package_collection_date;
			this.package_source = package_source;
		}
		
		public LabelDuplicates() {
			super();
			this.package_description = null;
			this.package_upc = null;
			this.package_collection_date = null;
			this.package_source = null;
		}	
		
		public String getPackage_description() {
			return package_description;
		}
		public void setPackage_description(String package_description) {
			this.package_description = package_description;
		}
		public String getPackage_upc() {
			return package_upc;
		}
		public void setPackage_upc(String package_upc) {
			this.package_upc = package_upc;
		}
		public String getPackage_collection_date() {
			return package_collection_date;
		}
		public void setPackage_collection_date(String package_collection_date) {
			this.package_collection_date = package_collection_date;
		}
		public String getPackage_source() {
			return package_source;
		}
		public void setPackage_source(String package_source) {
			this.package_source = package_source;
		}
	    
	    
	    
	    
	    
	    

}
