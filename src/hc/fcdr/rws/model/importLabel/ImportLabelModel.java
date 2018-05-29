package hc.fcdr.rws.model.importLabel;

import java.util.ArrayList;
import java.util.List;


public class ImportLabelModel {

	private List<ImportLabelNft> nftList;
	private ImportLabelRequest ImportLabelRequest; 
	
	
	public ImportLabelModel() {
		
		this(new ArrayList<ImportLabelNft>(), null);
	}

	
	
	public ImportLabelModel(List<ImportLabelNft> nftList, ImportLabelRequest ImportLabelRequest) {
		super();
		this.nftList = nftList;
		this.ImportLabelRequest = ImportLabelRequest;
	}




	public List<ImportLabelNft> getNftList() {
		return nftList;
	}




	public void setNftList(List<ImportLabelNft> nftList) {
		this.nftList = nftList;
	}




	public ImportLabelRequest getImportLabelRequest() {
		return ImportLabelRequest;
	}




	public void setImportLabelRequest(ImportLabelRequest ImportLabelRequest) {
		this.ImportLabelRequest = ImportLabelRequest;
	}




	public void addNftValue(ImportLabelNft importLabelNft){
		nftList.add(importLabelNft);
	}
	
	public boolean isValid(){
		
		return ((ImportLabelRequest.getPackage_source()!= null && !ImportLabelRequest.getPackage_source().isEmpty())
				&& ((ImportLabelRequest.getPackage_description()!= null) && (!ImportLabelRequest.getPackage_description().isEmpty()))
				&& ((ImportLabelRequest.getRecord_id() > 0) && (ImportLabelRequest.getRecord_id()!= null))
				&&((ImportLabelRequest.getPackage_upc()!=null) && (!ImportLabelRequest.getPackage_upc().isEmpty()))
				&&((ImportLabelRequest.getPackage_collection_date()!=null)&&(ImportLabelRequest.getPackage_collection_date().toString().length()> 0))
				);
		

	}
}
