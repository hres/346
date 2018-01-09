package hc.fcdr.rws.model.pkg;

public class PackageViewResponse {
    private int         status;
    private PackageViewDataResponse data;
    private String      message;
 
    
	public PackageViewResponse() {
	
		this.status = 0;
		this.data = null;
		this.message = null;
	}

    
	public PackageViewResponse(int status, PackageViewDataResponse data, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public PackageViewDataResponse getData() {
		return data;
	}


	public void setData(PackageViewDataResponse data) {
		this.data = data;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	
	
    
    
}
