package hc.fcdr.rws.model.pkg;

import javax.xml.bind.annotation.XmlElement;

public class PackageRequest
{
	@XmlElement(nillable=true)
    public String  labelUpc;
	@XmlElement(nillable=true)
    public String  labelDescription;
	@XmlElement(nillable=true)
    public String  labelSource;
	@XmlElement(nillable=true)
    public String  labelIngredients;
	@XmlElement(nillable=true)
    public String  collectionDateFrom;
	@XmlElement(nillable=true)
    public String  collectionDateTo;
	@XmlElement(nillable=true)
    public String  orderBy;
    @XmlElement
    public Integer offset;
    @XmlElement
    public boolean flag;

    @Override
    public String toString()
    {
        return "PackageRequest [labelUpc=" + labelUpc + ", labelDescription="
                + labelDescription + ", labelSource=" + labelSource
                + ", labelIngredients=" + labelIngredients
                + ", collectionDateFrom=" + collectionDateFrom
                + ", collectionDateTo=" + collectionDateTo + ", orderBy="
                + orderBy + ", offset=" + offset + ", flag=" + flag + "]";
    }

}
