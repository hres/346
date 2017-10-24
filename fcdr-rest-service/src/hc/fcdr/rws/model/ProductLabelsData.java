package hc.fcdr.rws.model;

import java.util.List;
import java.util.ArrayList;

public class ProductLabelsData
{
    private List<ProductLabelsResponse> dataList;
    private Integer                     count;

    public ProductLabelsData()
    {
        this(new ArrayList<ProductLabelsResponse>(), 0);
    }

    public ProductLabelsData(List<ProductLabelsResponse> dataList,
            Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ProductLabelsResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<ProductLabelsResponse> dataList)
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

    public void add(ProductLabelsResponse o)
    {
        dataList.add(o);
        count++;
    }
}
