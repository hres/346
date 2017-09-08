package hc.fcdr.rws.model;

import java.util.List;
import java.util.ArrayList;

public class ProductData
{
    private List<ProductResponse> dataList;
    private Integer               count;

    public ProductData()
    {
        this(new ArrayList<ProductResponse>(), 0);
    }

    public ProductData(List<ProductResponse> dataList, Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ProductResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<ProductResponse> dataList)
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

    public void add(ProductResponse o)
    {
        dataList.add(o);
        count++;
    }
}
