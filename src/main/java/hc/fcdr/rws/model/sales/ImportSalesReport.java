package hc.fcdr.rws.model.sales;

import java.util.ArrayList;
import java.util.List;

public class ImportSalesReport {

	private Integer number_of_records;

	private List<String> list_of_invalid_records; 
	private List<String>  list_of_duplicate_records; 
	private List<String>  list_of_records_linked_by_upc;
	private List<String>  list_of_records_linked_by_grouping;
	private List<String>  list_of_records_to_new_product;
	
	
	
	public ImportSalesReport() {
		this(0, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

	}
	
	
	
	
	public ImportSalesReport(Integer number_of_records,List<String> list_of_invalid_records,
			List<String> list_of_duplicate_records, List<String> list_of_records_linked_by_upc,
			List<String> list_of_records_linked_by_grouping, List<String> list_of_records_to_new_product) {
		super();
		this.number_of_records = number_of_records;

		this.list_of_invalid_records = list_of_invalid_records;
		this.list_of_duplicate_records = list_of_duplicate_records;
		this.list_of_records_linked_by_upc = list_of_records_linked_by_upc;
		this.list_of_records_linked_by_grouping = list_of_records_linked_by_grouping;
		this.list_of_records_to_new_product = list_of_records_to_new_product;
	}


	public Integer getNumber_of_records() {
		return number_of_records;
	}


	public void setNumber_of_records(Integer number_of_records) {
		this.number_of_records = number_of_records;
	}






	public List<String> getList_of_invalid_records() {
		return list_of_invalid_records;
	}


	public void setList_of_invalid_records(List<String> list_of_invalid_records) {
		this.list_of_invalid_records = list_of_invalid_records;
	}


	public List<String> getList_of_duplicate_records() {
		return list_of_duplicate_records;
	}


	public void setList_of_duplicate_records(List<String> list_of_duplicate_records) {
		this.list_of_duplicate_records = list_of_duplicate_records;
	}


	public List<String> getList_of_records_linked_by_upc() {
		return list_of_records_linked_by_upc;
	}


	public void setList_of_records_linked_by_upc(List<String> list_of_records_linked_by_upc) {
		this.list_of_records_linked_by_upc = list_of_records_linked_by_upc;
	}


	public List<String> getList_of_records_linked_by_grouping() {
		return list_of_records_linked_by_grouping;
	}


	public void setList_of_records_linked_by_grouping(List<String> list_of_records_linked_by_grouping) {
		this.list_of_records_linked_by_grouping = list_of_records_linked_by_grouping;
	}


	public List<String> getList_of_records_to_new_product() {
		return list_of_records_to_new_product;
	}


	public void setList_of_records_to_new_product(List<String> list_of_records_to_new_product) {
		this.list_of_records_to_new_product = list_of_records_to_new_product;
	}
	
	
	
	
}
