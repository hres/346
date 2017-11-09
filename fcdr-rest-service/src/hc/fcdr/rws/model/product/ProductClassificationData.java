package hc.fcdr.rws.model.product;

import java.util.ArrayList;
import java.util.List;

public class ProductClassificationData
{
    private List<ProductClassificationResponse> dataList;
    private Integer                             count;

    public ProductClassificationData()
    {
        this(new ArrayList<ProductClassificationResponse>(), 0);
    }

    public ProductClassificationData(
            final List<ProductClassificationResponse> dataList,
            final Integer count)
    {
        this.dataList = dataList;
        this.count = count;
    }

    public List<ProductClassificationResponse> getDataList()
    {
        return dataList;
    }

    public void setDataList(final List<ProductClassificationResponse> dataList)
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

    public void add(final ProductClassificationResponse o)
    {
        dataList.add(o);
        count++;
    }
}
