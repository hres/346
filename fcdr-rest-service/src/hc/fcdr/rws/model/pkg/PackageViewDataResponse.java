package hc.fcdr.rws.model.pkg;

import java.util.ArrayList;
import java.util.List;

public class PackageViewDataResponse {

    private List<PackageViewData> dataList;
    private Integer               count;
    

	public PackageViewDataResponse() {
	
		 this(new ArrayList<PackageViewData>(), 0);
	}
    
	
	public PackageViewDataResponse(List<PackageViewData> dataList, Integer count) {
		super();
		this.dataList = dataList;
		this.count = count;
	}


	public List<PackageViewData> getDataList() {
		return dataList;
	}


	public void setDataList(List<PackageViewData> dataList) {
		this.dataList = dataList;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}
	
	 public void add(final PackageViewData o)
	    {
	        dataList.add(o);
	        count++;
	    }
    
	
	
    
}
