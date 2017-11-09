package hc.fcdr.rws.model.product;

import java.util.ArrayList;
import java.util.List;

public class ProductLabelsData
{
    private List<ProductLabelsResponse> dataList;
    private Integer                     count;

    public ProductLabelsData()
    {
        this(new ArrayList<ProductLabelsResponse>(), 0);
    }

    public ProductLabelsData(final List<ProductLabelsResponse> dataList,
            final Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ProductLabelsResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<ProductLabelsResponse> dataList)
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

    public void add(final ProductLabelsResponse o)
    {
        dataList.add(o);
        count++;
    }
}
