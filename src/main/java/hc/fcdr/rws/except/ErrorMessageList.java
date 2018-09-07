package hc.fcdr.rws.except;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessageList {
	
    private List<ErrorMessage> errorList;

    
    
    public ErrorMessageList()
    {
        this(new ArrayList<ErrorMessage>());
    }

    public ErrorMessageList(final List<ErrorMessage> errorList)
    {
        super();
        this.errorList = errorList;
    }
    
    
	public List<ErrorMessage> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ErrorMessage> errorList) {
		this.errorList = errorList;
	}
    
    
    
    
}
