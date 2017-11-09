package hc.fcdr.rws.model.classification;

import java.util.ArrayList;
import java.util.List;

public class ClassificationData
{
    private List<ClassificationResponse> dataList;
    private Integer                      count;

    public ClassificationData()
    {
        this(new ArrayList<ClassificationResponse>(), 0);
    }

    public ClassificationData(final List<ClassificationResponse> dataList,
            final Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ClassificationResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<ClassificationResponse> dataList)
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

    public void add(final ClassificationResponse o)
    {
        dataList.add(o);
        count++;
    }
}
