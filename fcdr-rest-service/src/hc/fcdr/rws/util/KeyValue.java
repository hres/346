package hc.fcdr.rws.util;

import java.sql.Date;

public class KeyValue {

	private String description;
	private String upc;
	private String date;
	
	
	public KeyValue() {
		super();
		this.description = null;
		this.upc = null;
		this.date = null;
	
	}
	
	public KeyValue(String description, String upc, String date) {
		super();
		this.description = description;
		this.upc = upc;
		this.date = date;
	}
	
	
	   @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof KeyValue)) return false;
	        KeyValue key = (KeyValue) o;
	        return description.equals(key.description) && upc.equals(key.upc) && date == key.date;
	    }
	
	   @Override
	    public int hashCode() {

	        
	        int hash = 17;
	        hash = hash * 31 + description.hashCode();
	        hash = hash * 31 + upc.hashCode();
	        hash = hash * 31 + hashCode(date);
	
	        return hash;
	    }
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public static int hashCode(Object o) {
	    return o != null ? o.hashCode() : 0;
	}
	
}
