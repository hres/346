package hc.fcdr.rws.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
public class Product implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 4278768761440183227L;
    private Long              id;
    private String            description;
    private String            brand;
    private String            country;
    //private Double            clusterNumber;
    private String            clusterNumber;
    private String            comment;
    private String            manufacturer;
    //private Integer           cnfCode;
    private String           cnfCode;
    private Timestamp         creationDate;
    private Timestamp         lastEditDate;
    private String            editedBy;

    public Product()
    {
        super();
        this.id = 0L;
        this.description = "";
        this.brand = "";
        this.country = "";
        //this.clusterNumber = 0.0;
        this.clusterNumber = "";
        this.comment = "";
        this.manufacturer = "";
        //this.cnfCode = 0;
        this.cnfCode = "";
        this.creationDate = null;
        this.lastEditDate = null;
        this.editedBy = "";
    }

    public Product(Long id, String description, String brand, String country,
            //Double clusterNumber, String comment, String manufacturer,
            String clusterNumber, String comment, String manufacturer,
            //Integer cnfCode, Timestamp creationDate, Timestamp lastEditDate,
            String cnfCode, Timestamp creationDate, Timestamp lastEditDate,
            String editedBy)
    {
        super();
        this.id = id;
        this.description = description;
        this.brand = brand;
        this.country = country;
        this.clusterNumber = clusterNumber;
        this.comment = comment;
        this.manufacturer = manufacturer;
        this.cnfCode = cnfCode;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
        this.editedBy = editedBy;
    }

    public Product(Product product)
    {
        super();
        this.id = product.getId();
        this.description = product.getDescription();
        this.brand = product.getBrand();
        this.country = product.getCountry();
        this.clusterNumber = product.getClusterNumber();
        this.comment = product.getComment();
        this.manufacturer = product.getManufacturer();
        this.cnfCode = product.getCnfCode();
        this.creationDate = product.getCreationDate();
        this.lastEditDate = product.getLastEditDate();
        this.editedBy = product.getEditedBy();
    }

    public Long getId()
    {
        return id;
    }

    @XmlElement
    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    @XmlElement
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getBrand()
    {
        return brand;
    }

    @XmlElement
    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getCountry()
    {
        return country;
    }

    @XmlElement
    public void setCountry(String country)
    {
        this.country = country;
    }

    //public Double getClusterNumber()
    public String getClusterNumber()
    {
        return clusterNumber;
    }

    @XmlElement
    //public void setClusterNumber(Double clusterNumber)
    public void setClusterNumber(String clusterNumber)
    {
        this.clusterNumber = clusterNumber;
    }

    public String getComment()
    {
        return comment;
    }

    @XmlElement
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    @XmlElement
    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    //public Integer getCnfCode()
    public String getCnfCode()
    {
        return cnfCode;
    }

    @XmlElement
    //public void setCnfCode(Integer cnfCode)
    public void setCnfCode(String cnfCode)
    {
        this.cnfCode = cnfCode;
    }

    public Timestamp getCreationDate()
    {
        return creationDate;
    }

    @XmlElement
    public void setCreationDate(Timestamp creationDate)
    {
        this.creationDate = creationDate;
    }

    public Timestamp getLastEditDate()
    {
        return lastEditDate;
    }

    @XmlElement
    public void setLastEditDate(Timestamp lastEditDate)
    {
        this.lastEditDate = lastEditDate;
    }

    public String getEditedBy()
    {
        return editedBy;
    }

    @XmlElement
    public void setEditedBy(String editedBy)
    {
        this.editedBy = editedBy;
    }

    @Override
    public String toString()
    {
        return "Product [id=" + id + ", description=" + description + ", brand="
                + brand + ", country=" + country + ", clusterNumber="
                + clusterNumber + ", comment=" + comment + ", manufacturer="
                + manufacturer + ", cnfCode=" + cnfCode + ", creationDate="
                + creationDate + ", lastEditDate=" + lastEditDate
                + ", editedBy=" + editedBy + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((brand == null) ? 0 : brand.hashCode());
        result = (prime * result)
                + ((clusterNumber == null) ? 0 : clusterNumber.hashCode());
        result = (prime * result)
                + ((cnfCode == null) ? 0 : cnfCode.hashCode());
        result = (prime * result)
                + ((comment == null) ? 0 : comment.hashCode());
        result = (prime * result)
                + ((country == null) ? 0 : country.hashCode());
        result = (prime * result)
                + ((creationDate == null) ? 0 : creationDate.hashCode());
        result = (prime * result)
                + ((description == null) ? 0 : description.hashCode());
        result = (prime * result)
                + ((editedBy == null) ? 0 : editedBy.hashCode());
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        result = (prime * result)
                + ((lastEditDate == null) ? 0 : lastEditDate.hashCode());
        result = (prime * result)
                + ((manufacturer == null) ? 0 : manufacturer.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (brand == null)
        {
            if (other.brand != null)
                return false;
        }
        else if (!brand.equals(other.brand))
            return false;
        if (clusterNumber == null)
        {
            if (other.clusterNumber != null)
                return false;
        }
        else if (!clusterNumber.equals(other.clusterNumber))
            return false;
        if (cnfCode == null)
        {
            if (other.cnfCode != null)
                return false;
        }
        else if (!cnfCode.equals(other.cnfCode))
            return false;
        if (comment == null)
        {
            if (other.comment != null)
                return false;
        }
        else if (!comment.equals(other.comment))
            return false;
        if (country == null)
        {
            if (other.country != null)
                return false;
        }
        else if (!country.equals(other.country))
            return false;
        if (creationDate == null)
        {
            if (other.creationDate != null)
                return false;
        }
        else if (!creationDate.equals(other.creationDate))
            return false;
        if (description == null)
        {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (editedBy == null)
        {
            if (other.editedBy != null)
                return false;
        }
        else if (!editedBy.equals(other.editedBy))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (lastEditDate == null)
        {
            if (other.lastEditDate != null)
                return false;
        }
        else if (!lastEditDate.equals(other.lastEditDate))
            return false;
        if (manufacturer == null)
        {
            if (other.manufacturer != null)
                return false;
        }
        else if (!manufacturer.equals(other.manufacturer))
            return false;
        return true;
    }

}
