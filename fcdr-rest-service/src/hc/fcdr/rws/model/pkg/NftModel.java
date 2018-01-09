package hc.fcdr.rws.model.pkg;

import javax.xml.bind.annotation.XmlElement;

public class NftModel
{

    private String name;
    @XmlElement(nillable = true)
    private Double amount;
    private String unit_of_measure;
    @XmlElement(nillable = true)
    private Double daily_value;

    public NftModel()
    {
        name = null;
        amount = null;
        unit_of_measure = null;
        daily_value = null;
    }

    public NftModel(final String name, final Double amount,
            final String unit_of_measure, final Double daily_value)
    {
        super();
        this.name = name;
        this.amount = amount;
        this.unit_of_measure = unit_of_measure;
        this.daily_value = daily_value;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(final Double amount)
    {
        this.amount = amount;
    }

    public String getUnit_of_measure()
    {
        return unit_of_measure;
    }

    public void setUnit_of_measure(final String unit_of_measure)
    {
        this.unit_of_measure = unit_of_measure;
    }

    public Double getDaily_value()
    {
        return daily_value;
    }

    public void setDaily_value(final Double daily_value)
    {
        this.daily_value = daily_value;
    }

}
