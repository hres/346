package hc.fcdr.rws.model.product;

public class RelinkRecord {
	private Integer record_id;
	private Integer product_id;
	private String type;
	
	
	public RelinkRecord() {
		super();
		this.record_id = null;
		this.product_id = null;
		this.type = null;
	}
	
	
	public RelinkRecord(Integer record_id, Integer product_id, String type) {
		super();
		this.record_id = record_id;
		this.product_id = product_id;
		this.type = type;
	}
	public Integer getRecord_id() {
		return record_id;
	}
	public void setRecord_id(Integer record_id) {
		this.record_id = record_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	

}
