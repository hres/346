package hc.fcdr.rws.model.pkg;

import java.util.ArrayList;
import java.util.List;

public class GenericList {
	private List<String> dataList;

	
	public GenericList() {
		this( new ArrayList<String>());
	}
	public GenericList(List<String> dataList) {
		super();
		this.dataList = dataList;
	}
	public List<String> getDataList() {
		return dataList;
	}
	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}
	
	
	
	
	
}
