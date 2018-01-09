package hc.fcdr.rws.model.pkg;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Package;

@XmlRootElement
public class PackageResponse
{
    private String    labelUpc;
    private String    labelDescription;
    private String    labelSource;
    private Date    labelCollectionDate;
    private Timestamp labelCreationDate;
    private Timestamp labelLastEditDate;
    private String    labelLastEditedBy;
    private Long      productId;

    public PackageResponse()
    {
        super();
        this.labelUpc = null;
        this.labelDescription = null;
        this.labelSource = null;
        this.labelCollectionDate = null;
        this.labelCreationDate = null;
        this.labelLastEditDate = null;
        this.labelLastEditedBy = "";
        this.productId = 0L;
    }

    public PackageResponse(final String labelUpc, final String labelDescription,
            final String labelSource, final Date labelCollectionDate,
            final Timestamp labelCreationDate,
            final Timestamp labelLastEditDate, final String labelLastEditedBy,
            final Long productId)
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

    public PackageResponse(final Package _package)
    {
        super();
        this.labelUpc = _package.getUpc();
        this.labelDescription = _package.getDescription();
        this.labelSource = _package.getPackageSource();
        this.labelCollectionDate =_package.getPackageCollectionDate(); 
        this.labelCreationDate = _package.getCreationDate();
        this.labelLastEditDate = _package.getLastEditDate();
        this.labelLastEditedBy = _package.getEditedBy();
        this.productId = _package.getProductId();
    }

    public String getLabelUpc()
    {
        return labelUpc;
    }

    public void setLabelUpc(final String labelUpc)
    {
        this.labelUpc = labelUpc;
    }

    public String getLabelDescription()
    {
        return labelDescription;
    }

    public void setLabelDescription(final String labelDescription)
    {
        this.labelDescription = labelDescription;
    }

    public String getLabelSource()
    {
        return labelSource;
    }

    public void setLabelSource(final String labelSource)
    {
        this.labelSource = labelSource;
    }

    public Date getLabelCollectionDate()
    {
        return labelCollectionDate;
    }

    public void setLabelCollectionDate(final Date labelCollectionDate)
    {
        this.labelCollectionDate = labelCollectionDate;
    }

    public Timestamp getLabelCreationDate()
    {
        return labelCreationDate;
    }

    public void setLabelCreationDate(final Timestamp labelCreationDate)
    {
        this.labelCreationDate = labelCreationDate;
    }

    public Timestamp getLabelLastEditDate()
    {
        return labelLastEditDate;
    }

    public void setLabelLastEditDate(final Timestamp labelLastEditDate)
    {
        this.labelLastEditDate = labelLastEditDate;
    }

    public String getLabelLastEditedBy()
    {
        return labelLastEditedBy;
    }

    public void setLabelLastEditedBy(final String labelLastEditedBy)
    {
        this.labelLastEditedBy = labelLastEditedBy;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(final Long productId)
    {
        this.productId = productId;
    }

}
