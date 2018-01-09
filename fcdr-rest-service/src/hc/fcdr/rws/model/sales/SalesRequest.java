package hc.fcdr.rws.model.sales;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SalesRequest
{
    @XmlElement
    public String  salesUpc;
    @XmlElement
    public String  salesDescription;
    @XmlElement
    public String  salesSource;
    @XmlElement
    public Integer salesYear;
    @XmlElement
    public String  nielsenCategory;
    @XmlElement
    public String  salesComment;
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
        return "SalesRequest [salesUpc="
                + salesUpc + ", salesDescription=" + salesDescription
                + ", salesSource=" + salesSource + ", salesYear=" + salesYear
                + ", nielsenCategory=" + nielsenCategory + ", salesComment="
                + salesComment + ", orderBy=" + orderBy + ", offset=" + offset
                + ", flag=" + flag + "]";
    }

}
