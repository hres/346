package hc.fcdr.rws.model.importLabel;

import java.util.ArrayList;
import java.util.List;

public class ImportLabelReport {

	List<String> skippedRecords;
	List<String> productGroupingAndLabelUpcNoMatch;
	List<String> labelsLinkedByNielsenItemRank;
	List<String> labelsLinkedByLabelUpc;
	List<String> labelsLinkedToNewProducts;
	List<String> labelSharingSameGrouping;
	List<String> duplicatesLabels;
	
	
		public ImportLabelReport(){
			
			this(new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),
					new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
		}
		
	
	public ImportLabelReport(List<String> skippedRecords, List<String> productGroupingAndLabelUpcNoMatch,
			List<String> labelsLinkedByNielsenItemRank, List<String> labelsLinkedByLabelUpc,
			List<String> labelsLinkedToNewProducts, List<String> labelSharingSameGrouping,List<String> duplicatesLabels) {
		super();
		this.skippedRecords = skippedRecords;
		this.productGroupingAndLabelUpcNoMatch = productGroupingAndLabelUpcNoMatch;
		this.labelsLinkedByNielsenItemRank = labelsLinkedByNielsenItemRank;
		this.labelsLinkedByLabelUpc = labelsLinkedByLabelUpc;
		this.labelsLinkedToNewProducts = labelsLinkedToNewProducts;
		this.labelSharingSameGrouping = labelSharingSameGrouping;
		this.duplicatesLabels = duplicatesLabels;
	}
	
	
	public List<String> getDuplicatesLabels() {
		return duplicatesLabels;
	}


	public void setDuplicatesLabels(List<String> duplicatesLabels) {
		this.duplicatesLabels = duplicatesLabels;
	}


	public List<String> getSkippedRecords() {
		return skippedRecords;
	}
	public void setSkippedRecords(List<String> skippedRecords) {
		this.skippedRecords = skippedRecords;
	}
	public List<String> getProductGroupingAndLabelUpcNoMatch() {
		return productGroupingAndLabelUpcNoMatch;
	}
	public void setProductGroupingAndLabelUpcNoMatch(List<String> productGroupingAndLabelUpcNoMatch) {
		this.productGroupingAndLabelUpcNoMatch = productGroupingAndLabelUpcNoMatch;
	}
	public List<String> getLabelsLinkedByNielsenItemRank() {
		return labelsLinkedByNielsenItemRank;
	}
	public void setLabelsLinkedByNielsenItemRank(List<String> labelsLinkedByNielsenItemRank) {
		this.labelsLinkedByNielsenItemRank = labelsLinkedByNielsenItemRank;
	}
	public List<String> getLabelsLinkedByLabelUpc() {
		return labelsLinkedByLabelUpc;
	}
	public void setLabelsLinkedByLabelUpc(List<String> labelsLinkedByLabelUpc) {
		this.labelsLinkedByLabelUpc = labelsLinkedByLabelUpc;
	}
	public List<String> getLabelsLinkedToNewProducts() {
		return labelsLinkedToNewProducts;
	}
	public void setLabelsLinkedToNewProducts(List<String> labelsLinkedToNewProducts) {
		this.labelsLinkedToNewProducts = labelsLinkedToNewProducts;
	}
	public List<String> getLabelSharingSameGrouping() {
		return labelSharingSameGrouping;
	}
	public void setLabelSharingSameGrouping(List<String> labelSharingSameGrouping) {
		this.labelSharingSameGrouping = labelSharingSameGrouping;
	}
	
	
	
	
	
}
