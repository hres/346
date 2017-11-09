package hc.fcdr.rws.model.product;

import java.util.ArrayList;
import java.util.List;

public class ProductSalesLabelData
{
    private List<ProductSalesLabelResponse> dataList;
    private Integer                         count;

    public ProductSalesLabelData()
    {
        this(new ArrayList<ProductSalesLabelResponse>(), 0);
    }

    public ProductSalesLabelData(final List<ProductSalesLabelResponse> dataList,
            final Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ProductSalesLabelResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<ProductSalesLabelResponse> dataList)
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

    public void add(final ProductSalesLabelResponse o)
    {
        dataList.add(o);
        count++;
    }
}
