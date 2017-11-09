package hc.fcdr.rws.model.sales;

import java.util.ArrayList;
import java.util.List;

public class SalesYearsData
{
    private List<SalesYearsResponse> dataList;
    private Integer                  count;

    public SalesYearsData()
    {
        this(new ArrayList<SalesYearsResponse>(), 0);
    }

    public SalesYearsData(final List<SalesYearsResponse> dataList,
            final Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<SalesYearsResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<SalesYearsResponse> dataList)
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

    public void add(final SalesYearsResponse o)
    {
        dataList.add(o);
        count++;
    }
}
