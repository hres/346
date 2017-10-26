package hc.fcdr.rws.model.importer;

import java.util.List;
import java.util.ArrayList;

public class ImportData
{
    private List<ImportResponse> dataList;
    private Integer              count;

    public ImportData()
    {
        this(new ArrayList<ImportResponse>(), 0);
    }

    public ImportData(List<ImportResponse> dataList, Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ImportResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<ImportResponse> dataList)
    {
        this.dataList = dataList;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public void add(ImportResponse o)
    {
        dataList.add(o);
        count++;
    }
}
