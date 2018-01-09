package hc.fcdr.rws.model.pkg;

public class NftGetModel {

	private Integer package_id;
	private Boolean flag;
	
	public NftGetModel() {
		this.package_id = null;
		this.flag = null;
	}
	
	public NftGetModel(Integer package_id, Boolean flag) {
		super();
		this.package_id = package_id;
		this.flag = flag;
	}

	public Integer getPackage_id() {
		return package_id;
	}

	public void setPackage_id(Integer package_id) {
		this.package_id = package_id;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
	
	
}
