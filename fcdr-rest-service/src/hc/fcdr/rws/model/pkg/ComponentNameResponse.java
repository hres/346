package hc.fcdr.rws.model.pkg;

import java.util.ArrayList;
import java.util.List;

public class ComponentNameResponse {

	private List<ComponentName> dataList = new ArrayList<ComponentName>();

	public ComponentNameResponse() {
		this(new ArrayList<ComponentName>());
	}
	public ComponentNameResponse(List<ComponentName> dataList) {
		super();
		this.dataList = dataList;
	}
	public List<ComponentName> getDataList() {
		return dataList;
	}
	public void setDataList(List<ComponentName> dataList) {
		this.dataList = dataList;
	}
	public void add(ComponentName componentName){
		this.dataList.add(componentName);
	}
	
	
}
