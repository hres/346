package hc.fcdr.rws.model.pkg;

import java.util.ArrayList;
import java.util.List;

public class PackageData
{
    private List<PackageResponse> dataList;
    private Integer               count;

    public PackageData()
    {
        this(new ArrayList<PackageResponse>(), 0);
    }

    public PackageData(final List<PackageResponse> dataList,
            final Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<PackageResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<PackageResponse> dataList)
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

    public void add(final PackageResponse o)
    {
        dataList.add(o);
        count++;
    }
}
