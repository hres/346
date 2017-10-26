package hc.fcdr.rws.model.pkg;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PackageRequest
{
    @XmlElement
    public String  labelUpc;
    @XmlElement
    public String  labelDescription;
    @XmlElement
    public String  labelSource;
    @XmlElement
    public String  labelIngredients;
    @XmlElement
    public String  collectionDateFrom;
    @XmlElement
    public String  collectionDateTo;
    @XmlElement
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
