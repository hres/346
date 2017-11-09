package hc.fcdr.rws.model.sales;

import java.util.ArrayList;
import java.util.List;

public class SalesDataShort
{
    private List<SalesResponseShort> dataList;
    private Integer                  count;

    public SalesDataShort()
    {
        this(new ArrayList<SalesResponseShort>(), 0);
    }

    public SalesDataShort(final List<SalesResponseShort> dataList,
            final Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<SalesResponseShort> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<SalesResponseShort> dataList)
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

    public void add(final SalesResponseShort o)
    {
        dataList.add(o);
        count++;
    }
}
