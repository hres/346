package hc.fcdr.rws.model.pkg;

import java.util.ArrayList;
import java.util.List;

public class ImagesList {
    private List<String> dataList;
    int status;
    
    public ImagesList() {
    	
        this(new ArrayList<String>(),0);

    }
    
	public ImagesList(List<String> dataList, int status) {
		super();
		this.dataList = dataList;
		this.status = status;
	}
	public List<String> getDataList() {
		return dataList;
	}
	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	} 
    
    

}
