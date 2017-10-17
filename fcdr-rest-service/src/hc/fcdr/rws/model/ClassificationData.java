package hc.fcdr.rws.model;

import java.util.List;
import java.util.ArrayList;

public class ClassificationData
{
    private List<ClassificationResponse> dataList;
    private Integer                      count;

    public ClassificationData()
    {
        this(new ArrayList<ClassificationResponse>(), 0);
    }

    public ClassificationData(List<ClassificationResponse> dataList,
            Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ClassificationResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<ClassificationResponse> dataList)
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

    public void add(ClassificationResponse o)
    {
        dataList.add(o);
        count++;
    }
}
