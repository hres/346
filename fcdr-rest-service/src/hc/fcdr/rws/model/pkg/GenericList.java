package hc.fcdr.rws.model.pkg;

import java.util.ArrayList;
import java.util.List;

public class GenericList
{
    private List<String> dataList;

    public GenericList()
    {
        this(new ArrayList<String>());
    }

    public GenericList(final List<String> dataList)
    {
        super();
        this.dataList = dataList;
    }

    public List<String> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<String> dataList)
    {
        this.dataList = dataList;
    }

}
