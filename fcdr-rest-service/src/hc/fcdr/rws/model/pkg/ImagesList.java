package hc.fcdr.rws.model.pkg;

import java.util.ArrayList;
import java.util.List;

public class ImagesList {
    private List<Image> dataList;
    int status;
    
    public ImagesList() {
    	
        this(new ArrayList<Image>(),0);

    }
    
	public ImagesList(List<Image> dataList, int status) {
		super();
		this.dataList = dataList;
		this.status = status;
	}
	public List<Image> getDataList() {
		return dataList;
	}
	public void setDataList(List<Image> dataList) {
		this.dataList = dataList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	} 
    
    

}
