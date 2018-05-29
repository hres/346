package hc.fcdr.rws.model.importer;

import java.util.ArrayList;
import java.util.List;

public class ImportData
{
    private List<ImportResponse> dataList;
    private Integer              count;

    public ImportData()
    {
        this(new ArrayList<ImportResponse>(), 0);
    }

    public ImportData(final List<ImportResponse> dataList, final Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ImportResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<ImportResponse> dataList)
    {
        this.dataList = dataList;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(final Integer count)
    {
        this.count = count;
    }

    public void add(final ImportResponse o)
    {
        dataList.add(o);
        count++;
    }
}
