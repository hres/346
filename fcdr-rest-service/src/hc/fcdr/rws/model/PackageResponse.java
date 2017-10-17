package hc.fcdr.rws.model;

import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Package;

@XmlRootElement
public class PackageResponse
{
    private String    labelUpc;
    private String    labelDescription;
    private String    labelSource;
    private String    labelCollectionDate;
    private Timestamp labelCreationDate;
    private Timestamp labelLastEditDate;
    private String    labelLastEditedBy;
    private Long      productId;

    public PackageResponse()
    {
        super();
        this.labelUpc = "";
        this.labelDescription = "";
        this.labelSource = "";
        this.labelCollectionDate = null;
        this.labelCreationDate = null;
        this.labelLastEditDate = null;
        this.labelLastEditedBy = "";
        this.productId = 0L;
    }

    public PackageResponse(String labelUpc, String labelDescription,
            String labelSource, String labelCollectionDate,
            Timestamp labelCreationDate, Timestamp labelLastEditDate,
            String labelLastEditedBy, Long productId)
    {
        super();
        this.labelUpc = labelUpc;
        this.labelDescription = labelDescription;
        this.labelSource = labelSource;
        this.labelCollectionDate = labelCollectionDate;
        this.labelCreationDate = labelCreationDate;
        this.labelLastEditDate = labelLastEditDate;
        this.labelLastEditedBy = labelLastEditedBy;
        this.productId = productId;
    }

    public PackageResponse(Package _package)
    {
        super();
        this.labelUpc = _package.getUpc();
        this.labelDescription = _package.getDescription();
        this.labelSource = _package.getPackageSource();
        this.labelCollectionDate = _package.getPackageCollectionDate()
                                           .toString();
        this.labelCreationDate = _package.getCreationDate();
        this.labelLastEditDate = _package.getLastEditDate();
        this.labelLastEditedBy = _package.getEditedBy();
        this.productId = _package.getProductId();
    }

    public String getLabelUpc()
    {
        return labelUpc;
    }

    public void setLabelUpc(String labelUpc)
    {
        this.labelUpc = labelUpc;
    }

    public String getLabelDescription()
    {
        return labelDescription;
    }

    public void setLabelDescription(String labelDescription)
    {
        this.labelDescription = labelDescription;
    }

    public String getLabelSource()
    {
        return labelSource;
    }

    public void setLabelSource(String labelSource)
    {
        this.labelSource = labelSource;
    }

    public String getLabelCollectionDate()
    {
        return labelCollectionDate;
    }

    public void setLabelCollectionDate(String labelCollectionDate)
    {
        this.labelCollectionDate = labelCollectionDate;
    }

    public Timestamp getLabelCreationDate()
    {
        return labelCreationDate;
    }

    public void setLabelCreationDate(Timestamp labelCreationDate)
    {
        this.labelCreationDate = labelCreationDate;
    }

    public Timestamp getLabelLastEditDate()
    {
        return labelLastEditDate;
    }

    public void setLabelLastEditDate(Timestamp labelLastEditDate)
    {
        this.labelLastEditDate = labelLastEditDate;
    }

    public String getLabelLastEditedBy()
    {
        return labelLastEditedBy;
    }

    public void setLabelLastEditedBy(String labelLastEditedBy)
    {
        this.labelLastEditedBy = labelLastEditedBy;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

}
