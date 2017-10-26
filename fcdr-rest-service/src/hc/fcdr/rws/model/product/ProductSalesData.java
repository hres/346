package hc.fcdr.rws.model.product;

import java.util.List;
import java.util.ArrayList;

public class ProductSalesData
{
    private List<ProductSalesResponse> dataList;
    private Integer                    count;

    public ProductSalesData()
    {
        this(new ArrayList<ProductSalesResponse>(), 0);
    }

    public ProductSalesData(List<ProductSalesResponse> dataList, Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ProductSalesResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<ProductSalesResponse> dataList)
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

    public void add(ProductSalesResponse o)
    {
        dataList.add(o);
        count++;
    }
}
