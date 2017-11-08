package hc.fcdr.rws.model.sales;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Sales;

@XmlRootElement
public class SalesYearsResponse
{
    private String salesYear;

    public SalesYearsResponse()
    {
        super();
        this.salesYear = "";
    }

    public SalesYearsResponse(String salesYear)
    {
        super();
        this.salesYear = salesYear;
    }

    public SalesYearsResponse(Sales sales)
    {
        super();
        this.salesYear = sales.getSalesYear();
    }

    public String getSalesYear()
    {
        return salesYear;
    }

    public void setSalesYear(String salesYear)
    {
        this.salesYear = salesYear;
    }

}
