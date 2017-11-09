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

    public SalesYearsResponse(final String salesYear)
    {
        super();
        this.salesYear = salesYear;
    }

    public SalesYearsResponse(final Sales sales)
    {
        super();
        this.salesYear = sales.getSalesYear();
    }

    public String getSalesYear()
    {
        return salesYear;
    }

    public void setSalesYear(final String salesYear)
    {
        this.salesYear = salesYear;
    }

}
