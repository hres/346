package hc.fcdr.rws.model;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Sales;

@XmlRootElement
public class SalesYearsResponse
{
    private Integer salesYear;

    public SalesYearsResponse()
    {
        super();
        this.salesYear = 0;
    }

    public SalesYearsResponse(Integer salesYear)
    {
        super();
        this.salesYear = salesYear;
    }

    public SalesYearsResponse(Sales sales)
    {
        super();
        this.salesYear = sales.getSalesYear();
    }

    public Integer getSalesYear()
    {
        return salesYear;
    }

    public void setSalesYear(Integer salesYear)
    {
        this.salesYear = salesYear;
    }

}
