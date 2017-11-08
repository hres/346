package hc.fcdr.rws.model.sales;

import java.util.List;
import java.util.ArrayList;

public class SalesDataShort
{
    private List<SalesResponseShort> dataList;
    private Integer                  count;

    public SalesDataShort()
    {
        this(new ArrayList<SalesResponseShort>(), 0);
    }

    public SalesDataShort(List<SalesResponseShort> dataList, Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<SalesResponseShort> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<SalesResponseShort> dataList)
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

    public void add(SalesResponseShort o)
    {
        dataList.add(o);
        count++;
    }
}
