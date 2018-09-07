package hc.fcdr.rws.model.sales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportSalesSummary {

	private Map<Double, String> duplicatesRecords;
	Map<Double, String> invalidRecords; // = new HashMap<Double, String>();

	Map<String, Integer> recordInDbByUPC; // = new HashMap<String, Integer>();

	Map<String, List<ImportMarketShare>> salesInFileByUPC;// = new HashMap<String, List<ImportMarketShare>>();
	Map<Double, List<ImportMarketShare>> salesWithGrouping;// = new HashMap<Double, List<ImportMarketShare>>();
	
	public ImportSalesSummary() {
		super();
		this.duplicatesRecords = null;
		this.invalidRecords = null;
		this.recordInDbByUPC = null;
		this.salesInFileByUPC = null;
		this.salesWithGrouping = null;
	}
	
	
	public ImportSalesSummary(Map<Double, String> duplicatesRecords, Map<Double, String> invalidRecords,
			Map<String, Integer> recordInDbByUPC, Map<String, List<ImportMarketShare>> salesInFileByUPC,
			Map<Double, List<ImportMarketShare>> salesWithGrouping) {
		super();
		this.duplicatesRecords = duplicatesRecords;
		this.invalidRecords = invalidRecords;
		this.recordInDbByUPC = recordInDbByUPC;
		this.salesInFileByUPC = salesInFileByUPC;
		this.salesWithGrouping = salesWithGrouping;
	}


	public Map<Double, String> getDuplicatesRecords() {
		return duplicatesRecords;
	}


	public void setDuplicatesRecords(Map<Double, String> duplicatesRecords) {
		this.duplicatesRecords = duplicatesRecords;
	}


	public Map<Double, String> getInvalidRecords() {
		return invalidRecords;
	}


	public void setInvalidRecords(Map<Double, String> invalidRecords) {
		this.invalidRecords = invalidRecords;
	}


	public Map<String, Integer> getRecordInDbByUPC() {
		return recordInDbByUPC;
	}


	public void setRecordInDbByUPC(Map<String, Integer> recordInDbByUPC) {
		this.recordInDbByUPC = recordInDbByUPC;
	}


	public Map<String, List<ImportMarketShare>> getSalesInFileByUPC() {
		return salesInFileByUPC;
	}


	public void setSalesInFileByUPC(Map<String, List<ImportMarketShare>> salesInFileByUPC) {
		this.salesInFileByUPC = salesInFileByUPC;
	}


	public Map<Double, List<ImportMarketShare>> getSalesWithGrouping() {
		return salesWithGrouping;
	}


	public void setSalesWithGrouping(Map<Double, List<ImportMarketShare>> salesWithGrouping) {
		this.salesWithGrouping = salesWithGrouping;
	}
	

	
	
	
}
