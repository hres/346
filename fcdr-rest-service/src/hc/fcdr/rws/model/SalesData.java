package hc.fcdr.rws.model;

import java.util.List;
import java.util.ArrayList;

public class SalesData
{
    private List<SalesResponse> dataList;
    private Integer             count;

    public SalesData()
    {
        this(new ArrayList<SalesResponse>(), 0);
    }

    public SalesData(List<SalesResponse> dataList, Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<SalesResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<SalesResponse> dataList)
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

    public void add(SalesResponse o)
    {
        dataList.add(o);
        count++;
    }
}
