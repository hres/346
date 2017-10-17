package hc.fcdr.rws.model;

import java.util.List;
import java.util.ArrayList;

public class ProductClassificationData
{
    private List<ProductClassificationResponse> dataList;
    private Integer                             count;

    public ProductClassificationData()
    {
        this(new ArrayList<ProductClassificationResponse>(), 0);
    }

    public ProductClassificationData(
            List<ProductClassificationResponse> dataList, Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ProductClassificationResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<ProductClassificationResponse> dataList)
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

    public void add(ProductClassificationResponse o)
    {
        dataList.add(o);
        count++;
    }
}
