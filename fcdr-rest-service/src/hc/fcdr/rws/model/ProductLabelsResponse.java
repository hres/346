package hc.fcdr.rws.model;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Package;

@XmlRootElement
public class ProductLabelsResponse
{
    private Long      label_id;
    private String    label_upc;
    private String    label_description;
    private String    label_collection_date;
    private String    label_source;
    private Timestamp label_creation_date;
    private Timestamp label_last_edit_date;
    private String    label_last_edited_by;

    public ProductLabelsResponse()
    {
        super();
        this.label_id = 0L;
        this.label_upc = "";
        this.label_description = "";
        this.label_collection_date = "";
        this.label_source = "";
        this.label_creation_date = null;
        this.label_last_edit_date = null;
        this.label_last_edited_by = "";
    }

    public ProductLabelsResponse(Long label_id, String label_upc,
            String label_description, String label_collection_date,
            String label_source, Timestamp label_creation_date,
            Timestamp label_last_edit_date, String label_last_edited_by)
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

    public ProductLabelsResponse(Package _package)
    {
        super();
        this.label_id = _package.getId();
        this.label_upc = _package.getUpc();
        this.label_description = _package.getDescription();
        this.label_collection_date = _package.getPackageCollectionDate()
                                             .toString();
        this.label_source = _package.getPackageSource();
        this.label_creation_date = _package.getCreationDate();
        this.label_last_edit_date = _package.getLastEditDate();
        this.label_last_edited_by = _package.getEditedBy();
    }

    public Long getLabel_id()
    {
        return label_id;
    }

    public void setLabel_id(Long label_id)
    {
        this.label_id = label_id;
    }

    public String getLabel_upc()
    {
        return label_upc;
    }

    public void setLabel_upc(String label_upc)
    {
        this.label_upc = label_upc;
    }

    public String getLabel_description()
    {
        return label_description;
    }

    public void setLabel_description(String label_description)
    {
        this.label_description = label_description;
    }

    public String getLabel_collection_date()
    {
        return label_collection_date;
    }

    public void setLabel_collection_date(String label_collection_date)
    {
        this.label_collection_date = label_collection_date;
    }

    public String getLabel_source()
    {
        return label_source;
    }

    public void setLabel_source(String label_source)
    {
        this.label_source = label_source;
    }

    public Timestamp getLabel_creation_date()
    {
        return label_creation_date;
    }

    public void setLabel_creation_date(Timestamp label_creation_date)
    {
        this.label_creation_date = label_creation_date;
    }

    public Timestamp getLabel_last_edit_date()
    {
        return label_last_edit_date;
    }

    public void setLabel_last_edit_date(Timestamp label_last_edit_date)
    {
        this.label_last_edit_date = label_last_edit_date;
    }

    public String getLabel_last_edited_by()
    {
        return label_last_edited_by;
    }

    public void setLabel_last_edited_by(String label_last_edited_by)
    {
        this.label_last_edited_by = label_last_edited_by;
    }

}
