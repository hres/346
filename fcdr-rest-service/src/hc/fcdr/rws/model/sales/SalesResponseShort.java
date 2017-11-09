package hc.fcdr.rws.model.sales;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Sales;

@XmlRootElement
public class SalesResponseShort
{
    private String salesUpc;
    private String salesDescription;
    private String salesSource;
    private String salesYear;
    private String nielsenCategory;
    private Double dollarVolume;
    private Double kiloVolume;
    private Long   productId;

    public SalesResponseShort()
    {
        super();
        this.salesUpc = "";
        this.salesDescription = "";
        this.salesSource = "";
        this.salesYear = "";
        this.nielsenCategory = "";
        this.dollarVolume = 0.0;
        this.kiloVolume = 0.0;
        this.productId = 0L;
    }

    public SalesResponseShort(final String salesUpc,
            final String salesDescription, final String salesSource,
            final String salesYear, final String nielsenCategory,
            final Double dollarVolume, final Double kiloVolume,
            final Long productId)
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

    public SalesResponseShort(final Sales sales)
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

    public void setSalesUpc(final String salesUpc)
    {
        this.salesUpc = salesUpc;
    }

    public String getSalesDescription()
    {
        return salesDescription;
    }

    public void setSalesDescription(final String salesDescription)
    {
        this.salesDescription = salesDescription;
    }

    public String getSalesSource()
    {
        return salesSource;
    }

    public void setSalesSource(final String salesSource)
    {
        this.salesSource = salesSource;
    }

    public String getSalesYear()
    {
        return salesYear;
    }

    public void setSalesYear(final String salesYear)
    {
        this.salesYear = salesYear;
    }

    public String getNielsenCategory()
    {
        return nielsenCategory;
    }

    public void setNielsenCategory(final String nielsenCategory)
    {
        this.nielsenCategory = nielsenCategory;
    }

    public Double getDollarVolume()
    {
        return dollarVolume;
    }

    public void setDollarVolume(final Double dollarVolume)
    {
        this.dollarVolume = dollarVolume;
    }

    public Double getKiloVolume()
    {
        return kiloVolume;
    }

    public void setKiloVolume(final Double kiloVolume)
    {
        this.kiloVolume = kiloVolume;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(final Long productId)
    {
        this.productId = productId;
    }

}
