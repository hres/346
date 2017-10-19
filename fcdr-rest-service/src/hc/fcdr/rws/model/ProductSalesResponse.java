package hc.fcdr.rws.model;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Sales;

@XmlRootElement
public class ProductSalesResponse
{
    private Long sales_id;
    private String  sales_upc;
    private Double  dollar_volume;
    private Integer sales_year;
    private String  nielsen_category;
    private String  sales_source;
    
    public ProductSalesResponse()
    {
        super();
        this.sales_id = 0L;
        this.sales_upc = "";
        this.dollar_volume = 0.0;
        this.sales_year = 0;
        this.nielsen_category = "";
        this.sales_source = "";
    }

    
    public ProductSalesResponse(Long sales_id, String sales_upc,
            Double dollar_volume, Integer sales_year, String nielsen_category,
            String sales_source)
    {
        super();
        this.sales_id = sales_id;
        this.sales_upc = sales_upc;
        this.dollar_volume = dollar_volume;
        this.sales_year = sales_year;
        this.nielsen_category = nielsen_category;
        this.sales_source = sales_source;
    }

    public ProductSalesResponse(Sales sales)
    {
        super();
        this.sales_id = sales.getId();
        this.sales_upc = sales.getUpc();
        this.dollar_volume = sales.getDollarVolume();
        this.sales_year = sales.getSalesYear();
        this.nielsen_category = sales.getNielsenCategory();
        this.sales_source = sales.getSalesSource();
    }


    public Long getSales_id()
    {
        return sales_id;
    }


    public void setSales_id(Long sales_id)
    {
        this.sales_id = sales_id;
    }


    public String getSales_upc()
    {
        return sales_upc;
    }


    public void setSales_upc(String sales_upc)
    {
        this.sales_upc = sales_upc;
    }


    public Double getDollar_volume()
    {
        return dollar_volume;
    }


    public void setDollar_volume(Double dollar_volume)
    {
        this.dollar_volume = dollar_volume;
    }


    public Integer getSales_year()
    {
        return sales_year;
    }


    public void setSales_year(Integer sales_year)
    {
        this.sales_year = sales_year;
    }


    public String getNielsen_category()
    {
        return nielsen_category;
    }


    public void setNielsen_category(String nielsen_category)
    {
        this.nielsen_category = nielsen_category;
    }


    public String getSales_source()
    {
        return sales_source;
    }


    public void setSales_source(String sales_source)
    {
        this.sales_source = sales_source;
    }

    
}
