package hc.fcdr.rws.model.pkg;

import java.util.List;
import java.util.ArrayList;


public class NftRequest {

	private Boolean flag; 
	private List<NftModel> nft;
	private Integer package_id;
	
	public NftRequest() {
		
		  this(null,new ArrayList<NftModel>(), 0);
		
	}
	
	
	public NftRequest(Boolean flag, ArrayList<NftModel> nft, Integer package_id) {
		super();
		this.flag = flag;
		this.nft = nft;
		this.package_id = package_id;
	}


	public Boolean getFlag() {
		return flag;
	}


	public void setFlag(Boolean flag) {
		this.flag = flag;
	}


	public List<NftModel> getNft() {
		return nft;
	}


	public void setNft(List<NftModel> nft) {
		this.nft = nft;
	}


	public Integer getPackage_id() {
		return package_id;
	}


	public void setPackage_id(Integer package_id) {
		this.package_id = package_id;
	}
	
	
	
	
	
}
