package hc.fcdr.rws.model.sales;

import java.util.HashMap;
import java.util.Map;

public class ImportSalesSummary {

	private Map<Double, String> duplicatesRecords;

	public ImportSalesSummary() {
		super();
		this.duplicatesRecords = null;
	}
	
	public ImportSalesSummary(Map<Double, String> duplicatesRecords) {
		super();
		this.duplicatesRecords = duplicatesRecords;
	}

	public Map<Double, String> getDuplicatesRecords() {
		return duplicatesRecords;
	}

	public void setDuplicatesRecords(Map<Double, String> duplicatesRecords) {
		this.duplicatesRecords = duplicatesRecords;
	}
	
	
	
}
