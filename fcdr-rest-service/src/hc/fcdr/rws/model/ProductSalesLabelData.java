package hc.fcdr.rws.model;

import java.util.List;
import java.util.ArrayList;

public class ProductSalesLabelData
{
    private List<ProductSalesLabelResponse> dataList;
    private Integer                         count;

    public ProductSalesLabelData()
    {
        this(new ArrayList<ProductSalesLabelResponse>(), 0);
    }

    public ProductSalesLabelData(List<ProductSalesLabelResponse> dataList,
            Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ProductSalesLabelResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<ProductSalesLabelResponse> dataList)
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

    public void add(ProductSalesLabelResponse o)
    {
        dataList.add(o);
        count++;
    }
}
