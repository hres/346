package hc.fcdr.rws.model.product;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Package;

@XmlRootElement
public class ProductLabelsResponse
{
    private Long      label_id;
    private String    label_upc;
    private String    label_description;
    private Date      label_collection_date;
    private String    label_source;
    private Timestamp label_creation_date;
    private Timestamp label_last_edit_date;
    private String    label_last_edited_by;

    public ProductLabelsResponse()
    {
        super();
        label_id = 0L;
        label_upc = "";
        label_description = "";
        label_collection_date = null;
        label_source = "";
        label_creation_date = null;
        label_last_edit_date = null;
        label_last_edited_by = "";
    }

    public ProductLabelsResponse(final Long label_id, final String label_upc,
            final String label_description, final Date label_collection_date,
            final String label_source, final Timestamp label_creation_date,
            final Timestamp label_last_edit_date,
            final String label_last_edited_by)
    {
        super();
        this.label_id = label_id;
        this.label_upc = label_upc;
        this.label_description = label_description;
        this.label_collection_date = label_collection_date;
        this.label_source = label_source;
        this.label_creation_date = label_creation_date;
        this.label_last_edit_date = label_last_edit_date;
        this.label_last_edited_by = label_last_edited_by;
    }

    public ProductLabelsResponse(final Package _package)
    {
        super();
        label_id = _package.getId();
        label_upc = _package.getUpc();
        label_description = _package.getDescription();
        label_collection_date = _package.getPackageCollectionDate();
        label_source = _package.getPackageSource();
        label_creation_date = _package.getCreationDate();
        label_last_edit_date = _package.getLastEditDate();
        label_last_edited_by = _package.getEditedBy();
    }

    public Long getLabel_id()
    {
        return label_id;
    }

    public void setLabel_id(final Long label_id)
    {
        this.label_id = label_id;
    }

    public String getLabel_upc()
    {
        return label_upc;
    }

    public void setLabel_upc(final String label_upc)
    {
        this.label_upc = label_upc;
    }

    public String getLabel_description()
    {
        return label_description;
    }

    public void setLabel_description(final String label_description)
    {
        this.label_description = label_description;
    }

    public Date getLabel_collection_date()
    {
        return label_collection_date;
    }

    public void setLabel_collection_date(final Date label_collection_date)
    {
        this.label_collection_date = label_collection_date;
    }

    public String getLabel_source()
    {
        return label_source;
    }

    public void setLabel_source(final String label_source)
    {
        this.label_source = label_source;
    }

    public Timestamp getLabel_creation_date()
    {
        return label_creation_date;
    }

    public void setLabel_creation_date(final Timestamp label_creation_date)
    {
        this.label_creation_date = label_creation_date;
    }

    public Timestamp getLabel_last_edit_date()
    {
        return label_last_edit_date;
    }

    public void setLabel_last_edit_date(final Timestamp label_last_edit_date)
    {
        this.label_last_edit_date = label_last_edit_date;
    }

    public String getLabel_last_edited_by()
    {
        return label_last_edited_by;
    }

    public void setLabel_last_edited_by(final String label_last_edited_by)
    {
        this.label_last_edited_by = label_last_edited_by;
    }

}
