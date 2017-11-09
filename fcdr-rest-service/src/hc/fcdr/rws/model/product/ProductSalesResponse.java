package hc.fcdr.rws.model.product;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Sales;

@XmlRootElement
public class ProductSalesResponse
{
    private Long   sales_id;
    private String sales_upc;
    private Double dollar_volume;
    private String sales_year;
    private String nielsen_category;
    private String sales_source;

    public ProductSalesResponse()
    {
        super();
        this.sales_id = 0L;
        this.sales_upc = "";
        this.dollar_volume = 0.0;
        this.sales_year = "";
        this.nielsen_category = "";
        this.sales_source = "";
    }

    public ProductSalesResponse(final Long sales_id, final String sales_upc,
            final Double dollar_volume, final String sales_year,
            final String nielsen_category, final String sales_source)
    {
        super();
        this.sales_id = sales_id;
        this.sales_upc = sales_upc;
        this.dollar_volume = dollar_volume;
        this.sales_year = sales_year;
        this.nielsen_category = nielsen_category;
        this.sales_source = sales_source;
    }

    public ProductSalesResponse(final Sales sales)
    {
        super();
        this.sales_id = sales.getId();
        this.sales_upc = sales.getUpc();
        this.dollar_volume = sales.getDollarVolume();
        this.sales_year = (sales.getSalesYear() == null) ? ""
                : sales.getSalesYear().toString();
        this.nielsen_category = sales.getNielsenCategory();
        this.sales_source = sales.getSalesSource();
    }

    public Long getSales_id()
    {
        return sales_id;
    }

    public void setSales_id(final Long sales_id)
    {
        this.sales_id = sales_id;
    }

    public String getSales_upc()
    {
        return sales_upc;
    }

    public void setSales_upc(final String sales_upc)
    {
        this.sales_upc = sales_upc;
    }

    public Double getDollar_volume()
    {
        return dollar_volume;
    }

    public void setDollar_volume(final Double dollar_volume)
    {
        this.dollar_volume = dollar_volume;
    }

    public String getSales_year()
    {
        return sales_year;
    }

    public void setSales_year(final String sales_year)
    {
        this.sales_year = sales_year;
    }

    public String getNielsen_category()
    {
        return nielsen_category;
    }

    public void setNielsen_category(final String nielsen_category)
    {
        this.nielsen_category = nielsen_category;
    }

    public String getSales_source()
    {
        return sales_source;
    }

    public void setSales_source(final String sales_source)
    {
        this.sales_source = sales_source;
    }

}
