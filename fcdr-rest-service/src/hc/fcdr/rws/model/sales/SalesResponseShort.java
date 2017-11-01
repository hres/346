package hc.fcdr.rws.model.sales;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Sales;

@XmlRootElement
public class SalesResponseShort
{
    private String  salesUpc;
    private String  salesDescription;
    private String  salesSource;
    private Integer salesYear;
    private String  nielsenCategory;
    private Double  dollarVolume;
    private Double  kiloVolume;
    private Long    productId;

    public SalesResponseShort()
    {
        super();
        this.salesUpc = "";
        this.salesDescription = "";
        this.salesSource = "";
        this.salesYear = 0;
        this.nielsenCategory = "";
        this.dollarVolume = 0.0;
        this.kiloVolume = 0.0;
        this.productId = 0L;
    }

    public SalesResponseShort(String salesUpc, String salesDescription,
            String salesSource, Integer salesYear, String nielsenCategory,
            Double dollarVolume, Double kiloVolume, Long productId)
    {
        super();
        this.salesUpc = salesUpc;
        this.salesDescription = salesDescription;
        this.salesSource = salesSource;
        this.salesYear = salesYear;
        this.nielsenCategory = nielsenCategory;
        this.dollarVolume = dollarVolume;
        this.kiloVolume = kiloVolume;
        this.productId = productId;
    }

    public SalesResponseShort(Sales sales)
    {
        super();
        this.salesUpc = sales.getUpc();
        this.salesDescription = sales.getDescription();
        this.salesSource = sales.getSalesSource();
        this.salesYear = sales.getSalesYear();
        this.nielsenCategory = sales.getNielsenCategory();
        this.dollarVolume = sales.getDollarVolume();
        this.kiloVolume = sales.getKiloVolume();
        this.productId = sales.getProductId();
    }

    public String getSalesUpc()
    {
        return salesUpc;
    }

    public void setSalesUpc(String salesUpc)
    {
        this.salesUpc = salesUpc;
    }

    public String getSalesDescription()
    {
        return salesDescription;
    }

    public void setSalesDescription(String salesDescription)
    {
        this.salesDescription = salesDescription;
    }

    public String getSalesSource()
    {
        return salesSource;
    }

    public void setSalesSource(String salesSource)
    {
        this.salesSource = salesSource;
    }

    public Integer getSalesYear()
    {
        return salesYear;
    }

    public void setSalesYear(Integer salesYear)
    {
        this.salesYear = salesYear;
    }

    public String getNielsenCategory()
    {
        return nielsenCategory;
    }

    public void setNielsenCategory(String nielsenCategory)
    {
        this.nielsenCategory = nielsenCategory;
    }

    public Double getDollarVolume()
    {
        return dollarVolume;
    }

    public void setDollarVolume(Double dollarVolume)
    {
        this.dollarVolume = dollarVolume;
    }

    public Double getKiloVolume()
    {
        return kiloVolume;
    }

    public void setKiloVolume(Double kiloVolume)
    {
        this.kiloVolume = kiloVolume;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

}
