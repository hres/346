package hc.fcdr.rws.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "package")
public class Package implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -1022262431565740067L;
    private Long              id;
    private String            description;
    private String            upc;
    private String            brand;
    private String            manufacturer;
    private String            country;
    private Double            size;
    private String            sizeUnitOfMeasure;
    private String            storageType;
    private String            storageStatements;
    private String            healthClaims;
    private String            otherPackageStatements;
    private String            suggestedDirections;
    private String            ingredients;
    private Boolean           multiPartFlag;
    private String            nutritionFactTable;
    private Double            asPreparedPerServingAmount;
    private String            asPreparedUnitOfMeasure;
    private Double            asSoldPerServingAmount;
    private String            asSoldUnitOfMeasure;
    private Double            asPreparedPerServingAmountInGrams;
    private Double            asSoldPerServingAmountInGrams;
    private String            packageComment;
    private String            packageSource;
    private String            packageProductDescription;
    private Date              packageCollectionDate;
    private Integer           numberOfUnits;
    private Timestamp         creationDate;
    private Timestamp         lastEditDate;
    private String            editedBy;
    private Long              productId;

    public Package()
    {
        super();
        this.id = 0L;
        this.description = "";
        this.upc = "";
        this.brand = "";
        this.manufacturer = "";
        this.country = "";
        this.size = 0.0;
        this.sizeUnitOfMeasure = "";
        this.storageType = "";
        this.storageStatements = "";
        this.healthClaims = "";
        this.otherPackageStatements = "";
        this.suggestedDirections = "";
        this.ingredients = "";
        this.multiPartFlag = false;
        this.nutritionFactTable = "";
        this.asPreparedPerServingAmount = 0.0;
        this.asPreparedUnitOfMeasure = "";
        this.asSoldPerServingAmount = 0.0;
        this.asSoldUnitOfMeasure = "";
        this.asPreparedPerServingAmountInGrams = 0.0;
        this.asSoldPerServingAmountInGrams = 0.0;
        this.packageComment = "";
        this.packageSource = "";
        this.packageProductDescription = "";
        this.packageCollectionDate = null;
        this.numberOfUnits = 0;
        this.creationDate = null;
        this.lastEditDate = null;
        this.editedBy = "";
        this.productId = 0L;
    }

    public Package(Long id, String description, String upc, String brand,
            String manufacturer, String country, Double size,
            String sizeUnitOfMeasure, String storageType,
            String storageStatements, String healthClaims,
            String otherPackageStatements, String suggestedDirections,
            String ingredients, Boolean multiPartFlag,
            String nutritionFactTable, Double asPreparedPerServingAmount,
            String asPreparedUnitOfMeasure, Double asSoldPerServingAmount,
            String asSoldUnitOfMeasure,
            Double asPreparedPerServingAmountInGrams,
            Double asSoldPerServingAmountInGrams, String packageComment,
            String packageSource, String packageProductDescription,
            Date packageCollectionDate, Integer numberOfUnits,
            Timestamp creationDate, Timestamp lastEditDate, String editedBy,
            Long productId)
    {
        super();
        this.id = id;
        this.description = description;
        this.upc = upc;
        this.brand = brand;
        this.manufacturer = manufacturer;
        this.country = country;
        this.size = size;
        this.sizeUnitOfMeasure = sizeUnitOfMeasure;
        this.storageType = storageType;
        this.storageStatements = storageStatements;
        this.healthClaims = healthClaims;
        this.otherPackageStatements = otherPackageStatements;
        this.suggestedDirections = suggestedDirections;
        this.ingredients = ingredients;
        this.multiPartFlag = multiPartFlag;
        this.nutritionFactTable = nutritionFactTable;
        this.asPreparedPerServingAmount = asPreparedPerServingAmount;
        this.asPreparedUnitOfMeasure = asPreparedUnitOfMeasure;
        this.asSoldPerServingAmount = asSoldPerServingAmount;
        this.asSoldUnitOfMeasure = asSoldUnitOfMeasure;
        this.asPreparedPerServingAmountInGrams = asPreparedPerServingAmountInGrams;
        this.asSoldPerServingAmountInGrams = asSoldPerServingAmountInGrams;
        this.packageComment = packageComment;
        this.packageSource = packageSource;
        this.packageProductDescription = packageProductDescription;
        this.packageCollectionDate = packageCollectionDate;
        this.numberOfUnits = numberOfUnits;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
        this.editedBy = editedBy;
        this.productId = productId;
    }

    public Package(Package _package)
    {
        super();
        this.id = _package.getId();
        this.description = _package.getDescription();
        this.upc = _package.getUpc();
        this.brand = _package.getBrand();
        this.manufacturer = _package.getManufacturer();
        this.country = _package.getCountry();
        this.size = _package.getSize();
        this.sizeUnitOfMeasure = _package.getSizeUnitOfMeasure();
        this.storageType = _package.getStorageType();
        this.storageStatements = _package.getStorageStatements();
        this.healthClaims = _package.getHealthClaims();
        this.otherPackageStatements = _package.getOtherPackageStatements();
        this.suggestedDirections = _package.getSuggestedDirections();
        this.ingredients = _package.getIngredients();
        this.multiPartFlag = _package.getMultiPartFlag();
        this.nutritionFactTable = _package.getNutritionFactTable();
        this.asPreparedPerServingAmount = _package.getAsPreparedPerServingAmount();
        this.asPreparedUnitOfMeasure = _package.getAsPreparedUnitOfMeasure();
        this.asSoldPerServingAmount = _package.getAsSoldPerServingAmount();
        this.asSoldUnitOfMeasure = _package.getAsSoldUnitOfMeasure();
        this.asPreparedPerServingAmountInGrams = _package.getAsPreparedPerServingAmountInGrams();
        this.asSoldPerServingAmountInGrams = _package.getAsSoldPerServingAmountInGrams();
        this.packageComment = _package.getPackageComment();
        this.packageSource = _package.getPackageSource();
        this.packageProductDescription = _package.getPackageProductDescription();
        this.packageCollectionDate = _package.getPackageCollectionDate();
        this.numberOfUnits = _package.getNumberOfUnits();
        this.creationDate = _package.getCreationDate();
        this.lastEditDate = _package.getLastEditDate();
        this.editedBy = _package.getEditedBy();
        this.productId = _package.getProductId();
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

    public String getUpc()
    {
        return upc;
    }

    @XmlElement
    public void setUpc(String upc)
    {
        this.upc = upc;
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

    public String getManufacturer()
    {
        return manufacturer;
    }

    @XmlElement
    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
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

    public Double getSize()
    {
        return size;
    }

    @XmlElement
    public void setSize(Double size)
    {
        this.size = size;
    }

    public String getSizeUnitOfMeasure()
    {
        return sizeUnitOfMeasure;
    }

    @XmlElement
    public void setSizeUnitOfMeasure(String sizeUnitOfMeasure)
    {
        this.sizeUnitOfMeasure = sizeUnitOfMeasure;
    }

    public String getStorageType()
    {
        return storageType;
    }

    @XmlElement
    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }

    public String getStorageStatements()
    {
        return storageStatements;
    }

    @XmlElement
    public void setStorageStatements(String storageStatements)
    {
        this.storageStatements = storageStatements;
    }

    public String getHealthClaims()
    {
        return healthClaims;
    }

    @XmlElement
    public void setHealthClaims(String healthClaims)
    {
        this.healthClaims = healthClaims;
    }

    public String getOtherPackageStatements()
    {
        return otherPackageStatements;
    }

    @XmlElement
    public void setOtherPackageStatements(String otherPackageStatements)
    {
        this.otherPackageStatements = otherPackageStatements;
    }

    public String getSuggestedDirections()
    {
        return suggestedDirections;
    }

    @XmlElement
    public void setSuggestedDirections(String suggestedDirections)
    {
        this.suggestedDirections = suggestedDirections;
    }

    public String getIngredients()
    {
        return ingredients;
    }

    @XmlElement
    public void setIngredients(String ingredients)
    {
        this.ingredients = ingredients;
    }

    public Boolean getMultiPartFlag()
    {
        return multiPartFlag;
    }

    @XmlElement
    public void setMultiPartFlag(Boolean multiPartFlag)
    {
        this.multiPartFlag = multiPartFlag;
    }

    public String getNutritionFactTable()
    {
        return nutritionFactTable;
    }

    @XmlElement
    public void setNutritionFactTable(String nutritionFactTable)
    {
        this.nutritionFactTable = nutritionFactTable;
    }

    public Double getAsPreparedPerServingAmount()
    {
        return asPreparedPerServingAmount;
    }

    @XmlElement
    public void setAsPreparedPerServingAmount(Double asPreparedPerServingAmount)
    {
        this.asPreparedPerServingAmount = asPreparedPerServingAmount;
    }

    public String getAsPreparedUnitOfMeasure()
    {
        return asPreparedUnitOfMeasure;
    }

    @XmlElement
    public void setAsPreparedUnitOfMeasure(String asPreparedUnitOfMeasure)
    {
        this.asPreparedUnitOfMeasure = asPreparedUnitOfMeasure;
    }

    public Double getAsSoldPerServingAmount()
    {
        return asSoldPerServingAmount;
    }

    @XmlElement
    public void setAsSoldPerServingAmount(Double asSoldPerServingAmount)
    {
        this.asSoldPerServingAmount = asSoldPerServingAmount;
    }

    public String getAsSoldUnitOfMeasure()
    {
        return asSoldUnitOfMeasure;
    }

    @XmlElement
    public void setAsSoldUnitOfMeasure(String asSoldUnitOfMeasure)
    {
        this.asSoldUnitOfMeasure = asSoldUnitOfMeasure;
    }

    public Double getAsPreparedPerServingAmountInGrams()
    {
        return asPreparedPerServingAmountInGrams;
    }

    @XmlElement
    public void setAsPreparedPerServingAmountInGrams(
            Double asPreparedPerServingAmountInGrams)
    {
        this.asPreparedPerServingAmountInGrams = asPreparedPerServingAmountInGrams;
    }

    public Double getAsSoldPerServingAmountInGrams()
    {
        return asSoldPerServingAmountInGrams;
    }

    @XmlElement
    public void setAsSoldPerServingAmountInGrams(
            Double asSoldPerServingAmountInGrams)
    {
        this.asSoldPerServingAmountInGrams = asSoldPerServingAmountInGrams;
    }

    public String getPackageComment()
    {
        return packageComment;
    }

    @XmlElement
    public void setPackageComment(String packageComment)
    {
        this.packageComment = packageComment;
    }

    public String getPackageSource()
    {
        return packageSource;
    }

    @XmlElement
    public void setPackageSource(String packageSource)
    {
        this.packageSource = packageSource;
    }

    public String getPackageProductDescription()
    {
        return packageProductDescription;
    }

    @XmlElement
    public void setPackageProductDescription(String packageProductDescription)
    {
        this.packageProductDescription = packageProductDescription;
    }

    public Date getPackageCollectionDate()
    {
        return packageCollectionDate;
    }

    @XmlElement
    public void setPackageCollectionDate(Date packageCollectionDate)
    {
        this.packageCollectionDate = packageCollectionDate;
    }

    public Integer getNumberOfUnits()
    {
        return numberOfUnits;
    }

    @XmlElement
    public void setNumberOfUnits(Integer numberOfUnits)
    {
        this.numberOfUnits = numberOfUnits;
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

    public Long getProductId()
    {
        return productId;
    }

    @XmlElement
    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((asPreparedPerServingAmount == null) ? 0
                : asPreparedPerServingAmount.hashCode());
        result = (prime * result)
                + ((asPreparedPerServingAmountInGrams == null) ? 0
                        : asPreparedPerServingAmountInGrams.hashCode());
        result = (prime * result) + ((asPreparedUnitOfMeasure == null) ? 0
                : asPreparedUnitOfMeasure.hashCode());
        result = (prime * result) + ((asSoldPerServingAmount == null) ? 0
                : asSoldPerServingAmount.hashCode());
        result = (prime * result) + ((asSoldPerServingAmountInGrams == null) ? 0
                : asSoldPerServingAmountInGrams.hashCode());
        result = (prime * result) + ((asSoldUnitOfMeasure == null) ? 0
                : asSoldUnitOfMeasure.hashCode());
        result = (prime * result) + ((brand == null) ? 0 : brand.hashCode());
        result = (prime * result)
                + ((country == null) ? 0 : country.hashCode());
        result = (prime * result)
                + ((creationDate == null) ? 0 : creationDate.hashCode());
        result = (prime * result)
                + ((description == null) ? 0 : description.hashCode());
        result = (prime * result)
                + ((editedBy == null) ? 0 : editedBy.hashCode());
        result = (prime * result)
                + ((healthClaims == null) ? 0 : healthClaims.hashCode());
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        result = (prime * result)
                + ((ingredients == null) ? 0 : ingredients.hashCode());
        result = (prime * result)
                + ((lastEditDate == null) ? 0 : lastEditDate.hashCode());
        result = (prime * result)
                + ((manufacturer == null) ? 0 : manufacturer.hashCode());
        result = (prime * result)
                + ((multiPartFlag == null) ? 0 : multiPartFlag.hashCode());
        result = (prime * result)
                + ((numberOfUnits == null) ? 0 : numberOfUnits.hashCode());
        result = (prime * result) + ((otherPackageStatements == null) ? 0
                : otherPackageStatements.hashCode());
        result = (prime * result) + ((packageCollectionDate == null) ? 0
                : packageCollectionDate.hashCode());
        result = (prime * result)
                + ((packageComment == null) ? 0 : packageComment.hashCode());
        result = (prime * result) + ((packageProductDescription == null) ? 0
                : packageProductDescription.hashCode());
        result = (prime * result)
                + ((packageSource == null) ? 0 : packageSource.hashCode());
        result = (prime * result)
                + ((productId == null) ? 0 : productId.hashCode());
        result = (prime * result) + ((size == null) ? 0 : size.hashCode());
        result = (prime * result) + ((sizeUnitOfMeasure == null) ? 0
                : sizeUnitOfMeasure.hashCode());
        result = (prime * result) + ((storageStatements == null) ? 0
                : storageStatements.hashCode());
        result = (prime * result)
                + ((storageType == null) ? 0 : storageType.hashCode());
        result = (prime * result) + ((suggestedDirections == null) ? 0
                : suggestedDirections.hashCode());
        result = (prime * result) + ((upc == null) ? 0 : upc.hashCode());
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
        Package other = (Package) obj;
        if (asPreparedPerServingAmount == null)
        {
            if (other.asPreparedPerServingAmount != null)
                return false;
        }
        else if (!asPreparedPerServingAmount.equals(
                other.asPreparedPerServingAmount))
            return false;
        if (asPreparedPerServingAmountInGrams == null)
        {
            if (other.asPreparedPerServingAmountInGrams != null)
                return false;
        }
        else if (!asPreparedPerServingAmountInGrams.equals(
                other.asPreparedPerServingAmountInGrams))
            return false;
        if (asPreparedUnitOfMeasure == null)
        {
            if (other.asPreparedUnitOfMeasure != null)
                return false;
        }
        else if (!asPreparedUnitOfMeasure.equals(other.asPreparedUnitOfMeasure))
            return false;
        if (asSoldPerServingAmount == null)
        {
            if (other.asSoldPerServingAmount != null)
                return false;
        }
        else if (!asSoldPerServingAmount.equals(other.asSoldPerServingAmount))
            return false;
        if (asSoldPerServingAmountInGrams == null)
        {
            if (other.asSoldPerServingAmountInGrams != null)
                return false;
        }
        else if (!asSoldPerServingAmountInGrams.equals(
                other.asSoldPerServingAmountInGrams))
            return false;
        if (asSoldUnitOfMeasure == null)
        {
            if (other.asSoldUnitOfMeasure != null)
                return false;
        }
        else if (!asSoldUnitOfMeasure.equals(other.asSoldUnitOfMeasure))
            return false;
        if (brand == null)
        {
            if (other.brand != null)
                return false;
        }
        else if (!brand.equals(other.brand))
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
        if (healthClaims == null)
        {
            if (other.healthClaims != null)
                return false;
        }
        else if (!healthClaims.equals(other.healthClaims))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (ingredients == null)
        {
            if (other.ingredients != null)
                return false;
        }
        else if (!ingredients.equals(other.ingredients))
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
        if (multiPartFlag == null)
        {
            if (other.multiPartFlag != null)
                return false;
        }
        else if (!multiPartFlag.equals(other.multiPartFlag))
            return false;
        if (numberOfUnits == null)
        {
            if (other.numberOfUnits != null)
                return false;
        }
        else if (!numberOfUnits.equals(other.numberOfUnits))
            return false;
        if (otherPackageStatements == null)
        {
            if (other.otherPackageStatements != null)
                return false;
        }
        else if (!otherPackageStatements.equals(other.otherPackageStatements))
            return false;
        if (packageCollectionDate == null)
        {
            if (other.packageCollectionDate != null)
                return false;
        }
        else if (!packageCollectionDate.equals(other.packageCollectionDate))
            return false;
        if (packageComment == null)
        {
            if (other.packageComment != null)
                return false;
        }
        else if (!packageComment.equals(other.packageComment))
            return false;
        if (packageProductDescription == null)
        {
            if (other.packageProductDescription != null)
                return false;
        }
        else if (!packageProductDescription.equals(
                other.packageProductDescription))
            return false;
        if (packageSource == null)
        {
            if (other.packageSource != null)
                return false;
        }
        else if (!packageSource.equals(other.packageSource))
            return false;
        if (productId == null)
        {
            if (other.productId != null)
                return false;
        }
        else if (!productId.equals(other.productId))
            return false;
        if (size == null)
        {
            if (other.size != null)
                return false;
        }
        else if (!size.equals(other.size))
            return false;
        if (sizeUnitOfMeasure == null)
        {
            if (other.sizeUnitOfMeasure != null)
                return false;
        }
        else if (!sizeUnitOfMeasure.equals(other.sizeUnitOfMeasure))
            return false;
        if (storageStatements == null)
        {
            if (other.storageStatements != null)
                return false;
        }
        else if (!storageStatements.equals(other.storageStatements))
            return false;
        if (storageType == null)
        {
            if (other.storageType != null)
                return false;
        }
        else if (!storageType.equals(other.storageType))
            return false;
        if (suggestedDirections == null)
        {
            if (other.suggestedDirections != null)
                return false;
        }
        else if (!suggestedDirections.equals(other.suggestedDirections))
            return false;
        if (upc == null)
        {
            if (other.upc != null)
                return false;
        }
        else if (!upc.equals(other.upc))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Package [id=" + id + ", description=" + description + ", upc="
                + upc + ", brand=" + brand + ", manufacturer=" + manufacturer
                + ", country=" + country + ", size=" + size
                + ", sizeUnitOfMeasure=" + sizeUnitOfMeasure + ", storageType="
                + storageType + ", storageStatements=" + storageStatements
                + ", healthClaims=" + healthClaims + ", otherPackageStatements="
                + otherPackageStatements + ", suggestedDirections="
                + suggestedDirections + ", ingredients=" + ingredients
                + ", multiPartFlag=" + multiPartFlag
                + ", asPreparedPerServingAmount=" + asPreparedPerServingAmount
                + ", asPreparedUnitOfMeasure=" + asPreparedUnitOfMeasure
                + ", asSoldPerServingAmount=" + asSoldPerServingAmount
                + ", asSoldUnitOfMeasure=" + asSoldUnitOfMeasure
                + ", asPreparedPerServingAmountInGrams="
                + asPreparedPerServingAmountInGrams
                + ", asSoldPerServingAmountInGrams="
                + asSoldPerServingAmountInGrams + ", packageComment="
                + packageComment + ", packageSource=" + packageSource
                + ", packageProductDescription=" + packageProductDescription
                + ", packageCollectionDate=" + packageCollectionDate
                + ", numberOfUnits=" + numberOfUnits + ", creationDate="
                + creationDate + ", lastEditDate=" + lastEditDate
                + ", editedBy=" + editedBy + ", productId=" + productId + "]";
    }

}
