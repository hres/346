package hc.fcdr.rws.model.sales;

import java.util.ArrayList;
import java.util.List;

public class SalesData
{
    private List<SalesResponse> dataList;
    private Integer             count;

    public SalesData()
    {
        this(new ArrayList<SalesResponse>(), 0);
    }

    public SalesData(final List<SalesResponse> dataList, final Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<SalesResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<SalesResponse> dataList)
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

    public void add(final SalesResponse o)
    {
        dataList.add(o);
        count++;
    }
}
