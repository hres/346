package hc.fcdr.rws.model.sales;

import java.util.List;
import java.util.ArrayList;

public class SalesYearsData
{
    private List<SalesYearsResponse> dataList;
    private Integer                  count;

    public SalesYearsData()
    {
        this(new ArrayList<SalesYearsResponse>(), 0);
    }

    public SalesYearsData(List<SalesYearsResponse> dataList, Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<SalesYearsResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<SalesYearsResponse> dataList)
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

    public void add(SalesYearsResponse o)
    {
        dataList.add(o);
        count++;
    }
}
