package hc.fcdr.rws.model.importer;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvBindByPosition;

import hc.fcdr.rws.util.DateUtil;

public class ImportSalesData
{
    @CsvBindByPosition(position = 0)
    private Double  itemId;

    @CsvBindByPosition(position = 1)
    private String  salesUpc;

    @CsvBindByPosition(position = 2)
    private String  salesDescription;

    @CsvBindByPosition(position = 3)
    private String  salesBrand;

    @CsvBindByPosition(position = 4)
    private String  salesManufacturer;

    @CsvBindByPosition(position = 5)
    private Double  dollarRank;

    @CsvBindByPosition(position = 6)
    private Double  dollarVolume;

    @CsvBindByPosition(position = 7)
    private Double  dollarShare;

    @CsvBindByPosition(position = 8)
    private Double  dollarVolumePercentageChange;

    @CsvBindByPosition(position = 9)
    private Double  kiloVolume;

    @CsvBindByPosition(position = 10)
    private Double  kiloShare;

    @CsvBindByPosition(position = 11)
    private Double  kiloVolumePercentageChange;

    @CsvBindByPosition(position = 12)
    private Double  averageAcDist;

    @CsvBindByPosition(position = 13)
    private Double  averageRetailPrice;

    @CsvBindByPosition(position = 14)
    private String  salesSource;

    @CsvBindByPosition(position = 15)
    private String  nielsenCategory;

    @CsvBindByPosition(position = 16)
    /// @CsvDate("yyyy-mm-dd")
    private String  salesCollectionDate;

    @CsvBindByPosition(position = 17)
    private Integer salesYear;

    @CsvBindByPosition(position = 18)
    private Boolean controlLabelFlag;

    @CsvBindByPosition(position = 19)
    private Double  kiloVolumeTotal;

    @CsvBindByPosition(position = 20)
    private Double  kiloVolumeRank;

    @CsvBindByPosition(position = 21)
    private Double  dollarVolumeTotal;

    @CsvBindByPosition(position = 22)
    private Double  clusterNumber;

    @CsvBindByPosition(position = 23)
    private Double  productGrouping;

    @CsvBindByPosition(position = 24)
    private String  salesProductDescription;

    @CsvBindByPosition(position = 25)
    private Double  classificationNumber;

    @CsvBindByPosition(position = 26)
    private String  classificationType;

    @CsvBindByPosition(position = 27)
    private String  salesComment;

    public ImportSalesData()
    {
        super();
    }

    public ImportSalesData(final String salesUpc, final String salesDescription,
            final String salesBrand, final String salesManufacturer,
            final Double dollarRank, final Double dollarVolume,
            final Double dollarShare, final Double dollarVolumePercentageChange,
            final Double kiloVolume, final Double kiloShare,
            final Double kiloVolumePercentageChange, final Double averageAcDist,
            final Double averageRetailPrice, final String salesSource,
            final String nielsenCategory, final String salesCollectionDate,
            final Integer salesYear, final Boolean controlLabelFlag,
            final Double kiloVolumeTotal, final Double kiloVolumeRank,
            final Double dollarVolumeTotal, final Double clusterNumber,
            final Double productGrouping, final String salesProductDescription,
            final Double classificationNumber, final String classificationType,
            final String salesComment)
    {
        super();
        this.salesUpc = salesUpc;
        this.salesDescription = salesDescription;
        this.salesBrand = salesBrand;
        this.salesManufacturer = salesManufacturer;
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
        this.salesCollectionDate = salesCollectionDate;
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
    }

    public Double getItemId()
    {
        return itemId;
    }

    public String getSalesUpc()
    {
        return salesUpc;
    }

    public void setSalesUpc(final String salesUpc)
    {
        this.salesUpc = salesUpc;
    }

    public String getSalesDescription()
    {
        return salesDescription;
    }

    public void setSalesDescription(final String salesDescription)
    {
        this.salesDescription = salesDescription;
    }

    public String getSalesBrand()
    {
        return salesBrand;
    }

    public void setSalesBrand(final String salesBrand)
    {
        this.salesBrand = salesBrand;
    }

    public String getSalesManufacturer()
    {
        return salesManufacturer;
    }

    public void setSalesManufacturer(final String salesManufacturer)
    {
        this.salesManufacturer = salesManufacturer;
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

    public String getSalesCollectionDate()
    {
        return salesCollectionDate;
    }

    public void setSalesCollectionDate(final String salesCollectionDate)
    {
        this.salesCollectionDate = salesCollectionDate;
    }

    public Integer getSalesYear()
    {
        return salesYear;
    }

    public void setSalesYear(final Integer salesYear)
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

    public Boolean isValidRecord()
    {
        return (((salesUpc != null) && !salesUpc.isEmpty())
                && ((salesDescription != null) && !salesDescription.isEmpty())
                && ((kiloVolume != null) && (kiloVolume > 0.0))
                && ((salesSource != null) && !salesSource.isEmpty())
                && ((salesYear != null) && (salesYear > 1900))
                && (salesCollectionDate != null));
    }

    public List<Object> getCsvFieldList()
    {
        final List<Object> a = new ArrayList<Object>();

        a.add(itemId);// 0
        a.add(salesUpc);// 1
        a.add(salesDescription);// 2
        a.add(salesBrand);// 3
        a.add(salesManufacturer);// 4
        a.add(dollarRank);// 5
        a.add(dollarVolume);// 6
        a.add(dollarShare);// 7
        a.add(dollarVolumePercentageChange);// 8
        a.add(kiloVolume);// 9
        a.add(kiloShare);// 10
        a.add(kiloVolumePercentageChange);// 11
        a.add(averageAcDist);// 12
        a.add(averageRetailPrice);// 13
        a.add(salesSource);// 14
        a.add(nielsenCategory);// 15
        a.add(new java.sql.Date(
                DateUtil.convertToDate(salesCollectionDate).getTime()));// 16
        a.add(salesYear);// 17
        a.add(controlLabelFlag);// 18
        a.add(kiloVolumeTotal);// 19
        a.add(kiloVolumeRank);// 20
        a.add(dollarVolumeTotal);// 21
        a.add(clusterNumber);// 22
        a.add(productGrouping);// 23
        a.add(salesProductDescription);// 24
        a.add(classificationNumber);// 25
        a.add(classificationType);// 26
        a.add(salesComment);// 27

        return a;
    }

    public List<Object> getFieldsForProductUpdateList()
    {
        final List<Object> a = new ArrayList<Object>();

        a.add(salesBrand);
        a.add(salesManufacturer);
        a.add(clusterNumber);
        a.add(salesProductDescription);

        return a;
    }

    @Override
    public String toString()
    {
        return "ImportSalesData [salesUpc=" + salesUpc + ", salesDescription="
                + salesDescription + ", salesBrand=" + salesBrand
                + ", salesManufacturer=" + salesManufacturer + ", dollarRank="
                + dollarRank + ", dollarVolume=" + dollarVolume
                + ", dollarShare=" + dollarShare
                + ", dollarVolumePercentageChange="
                + dollarVolumePercentageChange + ", kiloVolume=" + kiloVolume
                + ", kiloShare=" + kiloShare + ", kiloVolumePercentageChange="
                + kiloVolumePercentageChange + ", averageAcDist="
                + averageAcDist + ", averageRetailPrice=" + averageRetailPrice
                + ", salesSource=" + salesSource + ", nielsenCategory="
                + nielsenCategory + ", salesCollectionDate="
                + salesCollectionDate + ", salesYear=" + salesYear
                + ", controlLabelFlag=" + controlLabelFlag
                + ", kiloVolumeTotal=" + kiloVolumeTotal + ", kiloVolumeRank="
                + kiloVolumeRank + ", dollarVolumeTotal=" + dollarVolumeTotal
                + ", clusterNumber=" + clusterNumber + ", productGrouping="
                + productGrouping + ", salesProductDescription="
                + salesProductDescription + ", classificationNumber="
                + classificationNumber + ", classificationType="
                + classificationType + ", salesComment=" + salesComment + "]";
    }

}
