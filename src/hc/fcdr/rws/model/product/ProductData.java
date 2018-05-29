package hc.fcdr.rws.model.product;

import java.util.ArrayList;
import java.util.List;

public class ProductData
{
    private List<ProductResponse> dataList;
    private Integer               count;

    public ProductData()
    {
        this(new ArrayList<ProductResponse>(), 0);
    }

    public ProductData(final List<ProductResponse> dataList,
            final Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ProductResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<ProductResponse> dataList)
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

    public void add(final ProductResponse o)
    {
        dataList.add(o);
        count++;
    }
}
