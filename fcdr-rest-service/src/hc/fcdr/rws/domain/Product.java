package hc.fcdr.rws.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
        name = "product")
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
    private String            clusterNumber;
    private String            comment;
    private String            manufacturer;
    private String            cnfCode;
    private Timestamp         creationDate;
    private Timestamp         lastEditDate;
    private String            editedBy;
    private String            restaurantType;
    private String            type;

    public Product()
    {
        super();
        id = 0L;
        description = "";
        brand = "";
        country = "";
        clusterNumber = "";
        comment = "";
        manufacturer = "";
        cnfCode = "";
        creationDate = null;
        lastEditDate = null;
        editedBy = "";
        restaurantType = "";
        type = "";
    }

    public Product(final Long id, final String description, final String brand,
            final String country, final String clusterNumber,
            final String comment, final String manufacturer,
            final String cnfCode, final Timestamp creationDate,
            final Timestamp lastEditDate, final String editedBy,
            final String restaurantType, final String type)
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
        this.restaurantType = restaurantType;
        this.type = type;
    }

    public Product(final Product product)
    {
        super();
        id = product.getId();
        description = product.getDescription();
        brand = product.getBrand();
        country = product.getCountry();
        clusterNumber = product.getClusterNumber();
        comment = product.getComment();
        manufacturer = product.getManufacturer();
        cnfCode = product.getCnfCode();
        creationDate = product.getCreationDate();
        lastEditDate = product.getLastEditDate();
        editedBy = product.getEditedBy();
        restaurantType = product.getRestaurantType();
        type = product.getType();
    }

    public Long getId()
    {
        return id;
    }

    @XmlElement
    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    @XmlElement
    public void setDescription(final String description)
    {
        this.description = description;
    }

    public String getBrand()
    {
        return brand;
    }

    @XmlElement
    public void setBrand(final String brand)
    {
        this.brand = brand;
    }

    public String getCountry()
    {
        return country;
    }

    @XmlElement
    public void setCountry(final String country)
    {
        this.country = country;
    }

    public String getClusterNumber()
    {
        return clusterNumber;
    }

    @XmlElement
    public void setClusterNumber(final String clusterNumber)
    {
        this.clusterNumber = clusterNumber;
    }

    public String getComment()
    {
        return comment;
    }

    @XmlElement
    public void setComment(final String comment)
    {
        this.comment = comment;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    @XmlElement
    public void setManufacturer(final String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public String getCnfCode()
    {
        return cnfCode;
    }

    @XmlElement
    public void setCnfCode(final String cnfCode)
    {
        this.cnfCode = cnfCode;
    }

    public Timestamp getCreationDate()
    {
        return creationDate;
    }

    @XmlElement
    public void setCreationDate(final Timestamp creationDate)
    {
        this.creationDate = creationDate;
    }

    public Timestamp getLastEditDate()
    {
        return lastEditDate;
    }

    @XmlElement
    public void setLastEditDate(final Timestamp lastEditDate)
    {
        this.lastEditDate = lastEditDate;
    }

    public String getEditedBy()
    {
        return editedBy;
    }

    @XmlElement
    public void setEditedBy(final String editedBy)
    {
        this.editedBy = editedBy;
    }

    public String getRestaurantType()
    {
        return restaurantType;
    }

    @XmlElement
    public void setRestaurantType(final String restaurantType)
    {
        this.restaurantType = restaurantType;
    }

    public String getType()
    {
        return type;
    }

    @XmlElement
    public void setType(final String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "Product [id="
                + id + ", description=" + description + ", brand=" + brand
                + ", country=" + country + ", clusterNumber=" + clusterNumber
                + ", comment=" + comment + ", manufacturer=" + manufacturer
                + ", cnfCode=" + cnfCode + ", creationDate=" + creationDate
                + ", lastEditDate=" + lastEditDate + ", editedBy=" + editedBy
                + ", restaurantType=" + restaurantType + ", type=" + type + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((brand == null) ? 0 : brand.hashCode());
        result =
                (prime * result)
                        + ((clusterNumber == null)
                                ? 0 : clusterNumber.hashCode());
        result =
                (prime * result) + ((cnfCode == null) ? 0 : cnfCode.hashCode());
        result =
                (prime * result) + ((comment == null) ? 0 : comment.hashCode());
        result =
                (prime * result) + ((country == null) ? 0 : country.hashCode());
        result =
                (prime * result)
                        + ((creationDate == null)
                                ? 0 : creationDate.hashCode());
        result =
                (prime * result)
                        + ((description == null) ? 0 : description.hashCode());
        result =
                (prime * result)
                        + ((editedBy == null) ? 0 : editedBy.hashCode());
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        result =
                (prime * result)
                        + ((lastEditDate == null)
                                ? 0 : lastEditDate.hashCode());
        result =
                (prime * result)
                        + ((manufacturer == null)
                                ? 0 : manufacturer.hashCode());
        result =
                (prime * result)
                        + ((restaurantType == null)
                                ? 0 : restaurantType.hashCode());
        result = (prime * result) + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Product other = (Product) obj;
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
        if (restaurantType == null)
        {
            if (other.restaurantType != null)
                return false;
        }
        else if (!restaurantType.equals(other.restaurantType))
            return false;
        if (type == null)
        {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        return true;
    }

}
