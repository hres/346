package hc.fcdr.rws.model.product;

import java.util.ArrayList;
import java.util.List;

public class ProductSalesData
{
    private List<ProductSalesResponse> dataList;
    private Integer                    count;

    public ProductSalesData()
    {
        this(new ArrayList<ProductSalesResponse>(), 0);
    }

    public ProductSalesData(final List<ProductSalesResponse> dataList,
            final Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ProductSalesResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<ProductSalesResponse> dataList)
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

    public void add(final ProductSalesResponse o)
    {
        dataList.add(o);
        count++;
    }
}
