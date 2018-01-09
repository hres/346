package hc.fcdr.rws.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlElement;

public class Sales implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 6132531076830409718L;
    private Long              id;
    private String            description;
    private String            upc;
    private String            brand;
    private String            manufacturer;
    private Double            dollarRank;
    private Double            dollarVolume;
    private Double            dollarShare;
    private Double            dollarVolumePercentageChange;
    private Double            kiloVolume;
    private Double            kiloShare;
    private Double            kiloVolumePercentageChange;
    private Double            averageAcDist;
    private Double            averageRetailPrice;
    private String            salesSource;
    private String            nielsenCategory;
    private String            salesYear;
    private Boolean           controlLabelFlag;
    private Double            kiloVolumeTotal;
    private Double            kiloVolumeRank;
    private Double            dollarVolumeTotal;
    private Double            clusterNumber;
    private Double            productGrouping;
    private String            salesProductDescription;
    private Double            classificationNumber;
    private String            classificationType;
    private String            salesComment;
//    @XmlElement(nillable=true)
    private String              salesCollectionDate;
    private Integer           numberOfUnits;
    private Timestamp         creationDate;
    private Timestamp         lastEditDate;
    private String            editedBy;
    private Long              productId;

    public Sales()
    {
        super();
        this.id = 0L;
        this.description = "";
        this.upc = "";
        this.brand = "";
        this.manufacturer = "";
        this.dollarRank = null;
        this.dollarVolume = null;
        this.dollarShare = null;
        this.dollarVolumePercentageChange = null;
        this.kiloVolume = null;
        this.kiloShare = null;
        this.kiloVolumePercentageChange = null;
        this.averageAcDist = null;
        this.averageRetailPrice = null;
        this.salesSource = null;
        this.nielsenCategory = null;
        this.salesYear = null;
        this.controlLabelFlag = null;
        this.kiloVolumeTotal = null;
        this.kiloVolumeRank = null;
        this.dollarVolumeTotal = null;
        this.clusterNumber = null;
        this.productGrouping = null;
        this.salesProductDescription = null;
        this.classificationNumber = null;
        this.classificationType = null;
        this.salesComment = null;
        this.salesCollectionDate = null;
        this.numberOfUnits = null;
        this.creationDate = null;
        this.lastEditDate = null;
        this.editedBy = null;
        this.productId = null;
    }

    public Sales(final Long id, final String description, final String upc,
            final String brand, final String manufacturer,
            final Double dollarRank, final Double dollarVolume,
            final Double dollarShare, final Double dollarVolumePercentageChange,
            final Double kiloVolume, final Double kiloShare,
            final Double kiloVolumePercentageChange, final Double averageAcDist,
            final Double averageRetailPrice, final String salesSource,
            final String nielsenCategory, final String salesYear,
            final Boolean controlLabelFlag, final Double kiloVolumeTotal,
            final Double kiloVolumeRank, final Double dollarVolumeTotal,
            final Double clusterNumber, final Double productGrouping,
            final String salesProductDescription,
            final Double classificationNumber, final String classificationType,
            final String salesComment, final String salesCollectionDate,
            final Integer numberOfUnits, final Timestamp creationDate,
            final Timestamp lastEditDate, final String editedBy,
            final Long productId)
    {
        super();
        this.id = id;
        this.description = description;
        this.upc = upc;
        this.brand = brand;
        this.manufacturer = manufacturer;
        this.dollarRank = dollarRank;
        this.dollarVolume = dollarVolume;
        this.dollarShare = dollarShare;
        this.dollarVolumePercentageChange = dollarVolumePercentageChange;
        this.kiloVolume = kiloVolume;
        this.kiloShare = kiloShare;
        this.kiloVolumePercentageChange = kiloVolumePercentageChange;
        this.averageAcDist = averageAcDist;
        this.averageRetailPrice = averageRetailPrice;
        this.salesSource = salesSource;
        this.nielsenCategory = nielsenCategory;
        this.salesYear = salesYear;
        this.controlLabelFlag = controlLabelFlag;
        this.kiloVolumeTotal = kiloVolumeTotal;
        this.kiloVolumeRank = kiloVolumeRank;
        this.dollarVolumeTotal = dollarVolumeTotal;
        this.clusterNumber = clusterNumber;
        this.productGrouping = productGrouping;
        this.salesProductDescription = salesProductDescription;
        this.classificationNumber = classificationNumber;
        this.classificationType = classificationType;
        this.salesComment = salesComment;
        this.salesCollectionDate = salesCollectionDate;
        this.numberOfUnits = numberOfUnits;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
        this.editedBy = editedBy;
        this.productId = productId;
    }

    public Sales(final Sales sales)
    {
        super();
        this.id = sales.getId();
        this.description = sales.getDescription();
        this.upc = sales.getUpc();
        this.brand = sales.getBrand();
        this.manufacturer = sales.getManufacturer();
        this.dollarRank = sales.getDollarRank();
        this.dollarVolume = sales.getDollarVolume();
        this.dollarShare = sales.getDollarShare();
        this.dollarVolumePercentageChange = sales.getDollarVolumePercentageChange();
        this.kiloVolume = sales.getKiloVolume();
        this.kiloShare = sales.getKiloShare();
        this.kiloVolumePercentageChange = sales.getKiloVolumePercentageChange();
        this.averageAcDist = sales.getAverageAcDist();
        this.averageRetailPrice = sales.getAverageRetailPrice();
        this.salesSource = sales.getSalesSource();
        this.nielsenCategory = sales.getNielsenCategory();
        this.salesYear = sales.getSalesYear();
        this.controlLabelFlag = sales.getControlLabelFlag();
        this.kiloVolumeTotal = sales.getKiloVolumeTotal();
        this.kiloVolumeRank = sales.getKiloVolumeRank();
        this.dollarVolumeTotal = sales.getDollarVolumeTotal();
        this.clusterNumber = sales.getClusterNumber();
        this.productGrouping = sales.getProductGrouping();
        this.salesProductDescription = sales.getSalesProductDescription();
        this.classificationNumber = sales.getClassificationNumber();
        this.classificationType = sales.getClassificationType();
        this.salesComment = sales.getSalesComment();
        this.salesCollectionDate = sales.getSalesCollectionDate();
        this.numberOfUnits = sales.getNumberOfUnits();
        this.creationDate = sales.getCreationDate();
        this.lastEditDate = sales.getLastEditDate();
        this.editedBy = sales.getEditedBy();
        this.productId = sales.getProductId();
    }

    public Long getId()
    {
        return id;
    }

    
    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    
    public void setDescription(final String description)
    {
        this.description = description;
    }

    public String getUpc()
    {
        return upc;
    }

    
    public void setUpc(final String upc)
    {
        this.upc = upc;
    }

    public String getBrand()
    {
        return brand;
    }

    
    public void setBrand(final String brand)
    {
        this.brand = brand;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    
    public void setManufacturer(final String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public Double getDollarRank()
    {
        return dollarRank;
    }

    
    public void setDollarRank(final Double dollarRank)
    {
        this.dollarRank = dollarRank;
    }

    public Double getDollarVolume()
    {
        return dollarVolume;
    }

    
    public void setDollarVolume(final Double dollarVolume)
    {
        this.dollarVolume = dollarVolume;
    }

    public Double getDollarShare()
    {
        return dollarShare;
    }

    
    public void setDollarShare(final Double dollarShare)
    {
        this.dollarShare = dollarShare;
    }

    public Double getDollarVolumePercentageChange()
    {
        return dollarVolumePercentageChange;
    }

    
    public void setDollarVolumePercentageChange(
            final Double dollarVolumePercentageChange)
    {
        this.dollarVolumePercentageChange = dollarVolumePercentageChange;
    }

    public Double getKiloVolume()
    {
        return kiloVolume;
    }

    
    public void setKiloVolume(final Double kiloVolume)
    {
        this.kiloVolume = kiloVolume;
    }

    public Double getKiloShare()
    {
        return kiloShare;
    }

    
    public void setKiloShare(final Double kiloShare)
    {
        this.kiloShare = kiloShare;
    }

    public Double getKiloVolumePercentageChange()
    {
        return kiloVolumePercentageChange;
    }

    
    public void setKiloVolumePercentageChange(
            final Double kiloVolumePercentageChange)
    {
        this.kiloVolumePercentageChange = kiloVolumePercentageChange;
    }

    public Double getAverageAcDist()
    {
        return averageAcDist;
    }

    
    public void setAverageAcDist(final Double averageAcDist)
    {
        this.averageAcDist = averageAcDist;
    }

    public Double getAverageRetailPrice()
    {
        return averageRetailPrice;
    }

    
    public void setAverageRetailPrice(final Double averageRetailPrice)
    {
        this.averageRetailPrice = averageRetailPrice;
    }

    public String getSalesSource()
    {
        return salesSource;
    }

    
    public void setSalesSource(final String salesSource)
    {
        this.salesSource = salesSource;
    }

    public String getNielsenCategory()
    {
        return nielsenCategory;
    }

    
    public void setNielsenCategory(final String nielsenCategory)
    {
        this.nielsenCategory = nielsenCategory;
    }

    public String getSalesYear()
    {
        return salesYear;
    }

    
    public void setSalesYear(final String salesYear)
    {
        this.salesYear = salesYear;
    }

    public Boolean getControlLabelFlag()
    {
        return controlLabelFlag;
    }

    
    public void setControlLabelFlag(final Boolean controlLabelFlag)
    {
        this.controlLabelFlag = controlLabelFlag;
    }

    public Double getKiloVolumeTotal()
    {
        return kiloVolumeTotal;
    }

    
    public void setKiloVolumeTotal(final Double kiloVolumeTotal)
    {
        this.kiloVolumeTotal = kiloVolumeTotal;
    }

    public Double getKiloVolumeRank()
    {
        return kiloVolumeRank;
    }

    
    public void setKiloVolumeRank(final Double kiloVolumeRank)
    {
        this.kiloVolumeRank = kiloVolumeRank;
    }

    public Double getDollarVolumeTotal()
    {
        return dollarVolumeTotal;
    }

    
    public void setDollarVolumeTotal(final Double dollarVolumeTotal)
    {
        this.dollarVolumeTotal = dollarVolumeTotal;
    }

    public Double getClusterNumber()
    {
        return clusterNumber;
    }

    
    public void setClusterNumber(final Double clusterNumber)
    {
        this.clusterNumber = clusterNumber;
    }

    public Double getProductGrouping()
    {
        return productGrouping;
    }

    
    public void setProductGrouping(final Double productGrouping)
    {
        this.productGrouping = productGrouping;
    }

    public String getSalesProductDescription()
    {
        return salesProductDescription;
    }

    
    public void setSalesProductDescription(final String salesProductDescription)
    {
        this.salesProductDescription = salesProductDescription;
    }

    public Double getClassificationNumber()
    {
        return classificationNumber;
    }

    
    public void setClassificationNumber(final Double classificationNumber)
    {
        this.classificationNumber = classificationNumber;
    }

    public String getClassificationType()
    {
        return classificationType;
    }

    
    public void setClassificationType(final String classificationType)
    {
        this.classificationType = classificationType;
    }

    public String getSalesComment()
    {
        return salesComment;
    }

    
    public void setSalesComment(final String salesComment)
    {
        this.salesComment = salesComment;
    }

    public String getSalesCollectionDate()
    {
        return salesCollectionDate;
    }

    
    public void setSalesCollectionDate(final String salesCollectionDate)
    {
        this.salesCollectionDate = salesCollectionDate;
    }

    public Integer getNumberOfUnits()
    {
        return numberOfUnits;
    }

    
    public void setNumberOfUnits(final Integer numberOfUnits)
    {
        this.numberOfUnits = numberOfUnits;
    }

    public Timestamp getCreationDate()
    {
        return creationDate;
    }

    
    public void setCreationDate(final Timestamp creationDate)
    {
        this.creationDate = creationDate;
    }

    public Timestamp getLastEditDate()
    {
        return lastEditDate;
    }

    
    public void setLastEditDate(final Timestamp lastEditDate)
    {
        this.lastEditDate = lastEditDate;
    }

    public String getEditedBy()
    {
        return editedBy;
    }

    
    public void setEditedBy(final String editedBy)
    {
        this.editedBy = editedBy;
    }

    public Long getProductId()
    {
        return productId;
    }

    
    public void setProductId(final Long productId)
    {
        this.productId = productId;
    }

//    @Override
//    public int hashCode()
//    {
//        final int prime = 31;
//        int result = 1;
//        result = (prime * result)
//                + ((averageAcDist == null) ? 0 : averageAcDist.hashCode());
//        result = (prime * result) + ((averageRetailPrice == null) ? 0
//                : averageRetailPrice.hashCode());
//        result = (prime * result) + ((brand == null) ? 0 : brand.hashCode());
//        result = (prime * result) + ((classificationNumber == null) ? 0
//                : classificationNumber.hashCode());
//        result = (prime * result) + ((classificationType == null) ? 0
//                : classificationType.hashCode());
//        result = (prime * result)
//                + ((clusterNumber == null) ? 0 : clusterNumber.hashCode());
//        result = (prime * result) + ((controlLabelFlag == null) ? 0
//                : controlLabelFlag.hashCode());
//        result = (prime * result)
//                + ((creationDate == null) ? 0 : creationDate.hashCode());
//        result = (prime * result)
//                + ((description == null) ? 0 : description.hashCode());
//        result = (prime * result)
//                + ((dollarRank == null) ? 0 : dollarRank.hashCode());
//        result = (prime * result)
//                + ((dollarShare == null) ? 0 : dollarShare.hashCode());
//        result = (prime * result)
//                + ((dollarVolume == null) ? 0 : dollarVolume.hashCode());
//        result = (prime * result) + ((dollarVolumePercentageChange == null) ? 0
//                : dollarVolumePercentageChange.hashCode());
//        result = (prime * result) + ((dollarVolumeTotal == null) ? 0
//                : dollarVolumeTotal.hashCode());
//        result = (prime * result)
//                + ((editedBy == null) ? 0 : editedBy.hashCode());
//        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
//        result = (prime * result)
//                + ((kiloShare == null) ? 0 : kiloShare.hashCode());
//        result = (prime * result)
//                + ((kiloVolume == null) ? 0 : kiloVolume.hashCode());
//        result = (prime * result) + ((kiloVolumePercentageChange == null) ? 0
//                : kiloVolumePercentageChange.hashCode());
//        result = (prime * result)
//                + ((kiloVolumeRank == null) ? 0 : kiloVolumeRank.hashCode());
//        result = (prime * result)
//                + ((kiloVolumeTotal == null) ? 0 : kiloVolumeTotal.hashCode());
//        result = (prime * result)
//                + ((lastEditDate == null) ? 0 : lastEditDate.hashCode());
//        result = (prime * result)
//                + ((manufacturer == null) ? 0 : manufacturer.hashCode());
//        result = (prime * result)
//                + ((nielsenCategory == null) ? 0 : nielsenCategory.hashCode());
//        result = (prime * result)
//                + ((numberOfUnits == null) ? 0 : numberOfUnits.hashCode());
//        result = (prime * result)
//                + ((productGrouping == null) ? 0 : productGrouping.hashCode());
//        result = (prime * result)
//                + ((productId == null) ? 0 : productId.hashCode());
//        result = (prime * result) + ((salesCollectionDate == null) ? 0
//                : salesCollectionDate.hashCode());
//        result = (prime * result)
//                + ((salesComment == null) ? 0 : salesComment.hashCode());
//        result = (prime * result) + ((salesProductDescription == null) ? 0
//                : salesProductDescription.hashCode());
//        result = (prime * result)
//                + ((salesSource == null) ? 0 : salesSource.hashCode());
//        result = (prime * result)
//                + ((salesYear == null) ? 0 : salesYear.hashCode());
//        result = (prime * result) + ((upc == null) ? 0 : upc.hashCode());
//        return result;
//    }

//    @Override
//    public boolean equals(final Object obj)
//    {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        final Sales other = (Sales) obj;
//        if (averageAcDist == null)
//        {
//            if (other.averageAcDist != null)
//                return false;
//        }
//        else if (!averageAcDist.equals(other.averageAcDist))
//            return false;
//        if (averageRetailPrice == null)
//        {
//            if (other.averageRetailPrice != null)
//                return false;
//        }
//        else if (!averageRetailPrice.equals(other.averageRetailPrice))
//            return false;
//        if (brand == null)
//        {
//            if (other.brand != null)
//                return false;
//        }
//        else if (!brand.equals(other.brand))
//            return false;
//        if (classificationNumber == null)
//        {
//            if (other.classificationNumber != null)
//                return false;
//        }
//        else if (!classificationNumber.equals(other.classificationNumber))
//            return false;
//        if (classificationType == null)
//        {
//            if (other.classificationType != null)
//                return false;
//        }
//        else if (!classificationType.equals(other.classificationType))
//            return false;
//        if (clusterNumber == null)
//        {
//            if (other.clusterNumber != null)
//                return false;
//        }
//        else if (!clusterNumber.equals(other.clusterNumber))
//            return false;
//        if (controlLabelFlag == null)
//        {
//            if (other.controlLabelFlag != null)
//                return false;
//        }
//        else if (!controlLabelFlag.equals(other.controlLabelFlag))
//            return false;
//        if (creationDate == null)
//        {
//            if (other.creationDate != null)
//                return false;
//        }
//        else if (!creationDate.equals(other.creationDate))
//            return false;
//        if (description == null)
//        {
//            if (other.description != null)
//                return false;
//        }
//        else if (!description.equals(other.description))
//            return false;
//        if (dollarRank == null)
//        {
//            if (other.dollarRank != null)
//                return false;
//        }
//        else if (!dollarRank.equals(other.dollarRank))
//            return false;
//        if (dollarShare == null)
//        {
//            if (other.dollarShare != null)
//                return false;
//        }
//        else if (!dollarShare.equals(other.dollarShare))
//            return false;
//        if (dollarVolume == null)
//        {
//            if (other.dollarVolume != null)
//                return false;
//        }
//        else if (!dollarVolume.equals(other.dollarVolume))
//            return false;
//        if (dollarVolumePercentageChange == null)
//        {
//            if (other.dollarVolumePercentageChange != null)
//                return false;
//        }
//        else if (!dollarVolumePercentageChange.equals(
//                other.dollarVolumePercentageChange))
//            return false;
//        if (dollarVolumeTotal == null)
//        {
//            if (other.dollarVolumeTotal != null)
//                return false;
//        }
//        else if (!dollarVolumeTotal.equals(other.dollarVolumeTotal))
//            return false;
//        if (editedBy == null)
//        {
//            if (other.editedBy != null)
//                return false;
//        }
//        else if (!editedBy.equals(other.editedBy))
//            return false;
//        if (id == null)
//        {
//            if (other.id != null)
//                return false;
//        }
//        else if (!id.equals(other.id))
//            return false;
//        if (kiloShare == null)
//        {
//            if (other.kiloShare != null)
//                return false;
//        }
//        else if (!kiloShare.equals(other.kiloShare))
//            return false;
//        if (kiloVolume == null)
//        {
//            if (other.kiloVolume != null)
//                return false;
//        }
//        else if (!kiloVolume.equals(other.kiloVolume))
//            return false;
//        if (kiloVolumePercentageChange == null)
//        {
//            if (other.kiloVolumePercentageChange != null)
//                return false;
//        }
//        else if (!kiloVolumePercentageChange.equals(
//                other.kiloVolumePercentageChange))
//            return false;
//        if (kiloVolumeRank == null)
//        {
//            if (other.kiloVolumeRank != null)
//                return false;
//        }
//        else if (!kiloVolumeRank.equals(other.kiloVolumeRank))
//            return false;
//        if (kiloVolumeTotal == null)
//        {
//            if (other.kiloVolumeTotal != null)
//                return false;
//        }
//        else if (!kiloVolumeTotal.equals(other.kiloVolumeTotal))
//            return false;
//        if (lastEditDate == null)
//        {
//            if (other.lastEditDate != null)
//                return false;
//        }
//        else if (!lastEditDate.equals(other.lastEditDate))
//            return false;
//        if (manufacturer == null)
//        {
//            if (other.manufacturer != null)
//                return false;
//        }
//        else if (!manufacturer.equals(other.manufacturer))
//            return false;
//        if (nielsenCategory == null)
//        {
//            if (other.nielsenCategory != null)
//                return false;
//        }
//        else if (!nielsenCategory.equals(other.nielsenCategory))
//            return false;
//        if (numberOfUnits == null)
//        {
//            if (other.numberOfUnits != null)
//                return false;
//        }
//        else if (!numberOfUnits.equals(other.numberOfUnits))
//            return false;
//        if (productGrouping == null)
//        {
//            if (other.productGrouping != null)
//                return false;
//        }
//        else if (!productGrouping.equals(other.productGrouping))
//            return false;
//        if (productId == null)
//        {
//            if (other.productId != null)
//                return false;
//        }
//        else if (!productId.equals(other.productId))
//            return false;
//        if (salesCollectionDate == null)
//        {
//            if (other.salesCollectionDate != null)
//                return false;
//        }
//        else if (!salesCollectionDate.equals(other.salesCollectionDate))
//            return false;
//        if (salesComment == null)
//        {
//            if (other.salesComment != null)
//                return false;
//        }
//        else if (!salesComment.equals(other.salesComment))
//            return false;
//        if (salesProductDescription == null)
//        {
//            if (other.salesProductDescription != null)
//                return false;
//        }
//        else if (!salesProductDescription.equals(other.salesProductDescription))
//            return false;
//        if (salesSource == null)
//        {
//            if (other.salesSource != null)
//                return false;
//        }
//        else if (!salesSource.equals(other.salesSource))
//            return false;
//        if (salesYear == null)
//        {
//            if (other.salesYear != null)
//                return false;
//        }
//        else if (!salesYear.equals(other.salesYear))
//            return false;
//        if (upc == null)
//        {
//            if (other.upc != null)
//                return false;
//        }
//        else if (!upc.equals(other.upc))
//            return false;
//        return true;
//    }

    @Override
    public String toString()
    {
        return "Sales [id=" + id + ", description=" + description + ", upc="
                + upc + ", brand=" + brand + ", manufacturer=" + manufacturer
                + ", dollarRank=" + dollarRank + ", dollarVolume="
                + dollarVolume + ", dollarShare=" + dollarShare
                + ", dollarVolumePercentageChange="
                + dollarVolumePercentageChange + ", kiloVolume=" + kiloVolume
                + ", kiloShare=" + kiloShare + ", kiloVolumePercentageChange="
                + kiloVolumePercentageChange + ", averageAcDist="
                + averageAcDist + ", averageRetailPrice=" + averageRetailPrice
                + ", salesSource=" + salesSource + ", nielsenCategory="
                + nielsenCategory + ", salesYear=" + salesYear
                + ", controlLabelFlag=" + controlLabelFlag
                + ", kiloVolumeTotal=" + kiloVolumeTotal + ", kiloVolumeRank="
                + kiloVolumeRank + ", dollarVolumeTotal=" + dollarVolumeTotal
                + ", clusterNumber=" + clusterNumber + ", productGrouping="
                + productGrouping + ", salesProductDescription="
                + salesProductDescription + ", classificationNumber="
                + classificationNumber + ", classificationType="
                + classificationType + ", salesComment=" + salesComment
                + ", salesCollectionDate=" + salesCollectionDate
                + ", numberOfUnits=" + numberOfUnits + ", creationDate="
                + creationDate + ", lastEditDate=" + lastEditDate
                + ", editedBy=" + editedBy + ", productId=" + productId + "]";
    }

}
