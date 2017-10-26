package hc.fcdr.rws.model.pkg;

import java.util.List;
import java.util.ArrayList;

public class PackageData
{
    private List<PackageResponse> dataList;
    private Integer               count;

    public PackageData()
    {
        this(new ArrayList<PackageResponse>(), 0);
    }

    public PackageData(List<PackageResponse> dataList, Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<PackageResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<PackageResponse> dataList)
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

    public void add(PackageResponse o)
    {
        dataList.add(o);
        count++;
    }
}
