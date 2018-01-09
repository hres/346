package hc.fcdr.rws.model.sales;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import hc.fcdr.rws.domain.Sales;


public class SalesResponse
{
    @XmlElement(nillable=true)
    private String    sales_description;
    @XmlElement(nillable=true)
    private String    sales_upc;
    @XmlElement(nillable=true)
    private String    sales_brand;
    @XmlElement(nillable=true)
    private String    sales_manufacturer;
    @XmlElement(nillable=true)
    private Double    dollar_rank;
    @XmlElement(nillable=true)
    private Double    dollar_volume;
    @XmlElement(nillable=true)
    private Double    dollar_share;
    @XmlElement(nillable=true)
    private Double    dollar_volume_percentage_change;
    @XmlElement(nillable=true)
    private Double    kilo_volume;
    @XmlElement(nillable=true)
    private Double    kilo_share;
    @XmlElement(nillable=true)
    private Double    kilo_volume_percentage_change;
    @XmlElement(nillable=true)
    private Double    average_ac_dist;
    @XmlElement(nillable=true)
    private Double    average_retail_price;
    private String    sales_source;
    private String    nielsen_category;
    @XmlElement(nillable=true)
    private String    sales_year;
    @XmlElement(nillable=true)
    private Boolean   control_label_flag;
    @XmlElement(nillable=true)
    private Double    kilo_volume_total;
    @XmlElement(nillable=true)
    private Double    kilo_volume_rank;
    @XmlElement(nillable=true)
    private Double    dollar_volume_total;
    @XmlElement(nillable=true)
    private Double    cluster_number;
    @XmlElement(nillable=true)
    private Double    product_grouping;
 
    private String    sales_product_description;
    @XmlElement(nillable=true)
    private Double    classification_number;
    @XmlElement(nillable=true)
    private String    classification_type;
    @XmlElement(nillable=true)
    private String    sales_comment;
    @XmlElement(nillable=true)
    private String      sales_collection_date;
    @XmlElement(nillable=true)
    private Integer   number_of_units;
    @XmlElement(nillable=true)
    private String    edited_by;
    @XmlElement(nillable=true)
    private Timestamp creation_date;
    @XmlElement(nillable=true)
    private Timestamp last_edit_date;
    @XmlElement(nillable=true)
    private Long      product_id;
    @XmlElement(nillable=true)
    private Long      sales_id;

    public SalesResponse()
    {
        super();
        this.sales_description = null;
        this.sales_upc = null;
        this.sales_brand = null;
        this.sales_manufacturer = null;
        this.dollar_rank = null;
        this.dollar_volume = null;
        this.dollar_share = null;
        this.dollar_volume_percentage_change = null;
        this.kilo_volume = null;
        this.kilo_share = null;
        this.kilo_volume_percentage_change = null;
        this.average_ac_dist = null;
        this.average_retail_price = null;
        this.sales_source = null;
        this.nielsen_category = null;
        this.sales_year = null;
        this.control_label_flag = null;
        this.kilo_volume_total = null;
        this.kilo_volume_rank = null;
        this.dollar_volume_total = null;
        this.cluster_number = null;
        this.product_grouping = null;
        this.sales_product_description = null;
        this.classification_number = null;
        this.classification_type = null;
        this.sales_comment = null;
        this.sales_collection_date = null;
        this.number_of_units = null;
        this.edited_by = null;
        this.creation_date = null;
        this.last_edit_date = null;
        this.product_id = null;
        this.sales_id = null;

    }

    public SalesResponse(final Sales sales)
    {
        super();
        this.sales_description = sales.getDescription();
        this.sales_upc = sales.getUpc();
        this.sales_brand = sales.getBrand();
        this.sales_manufacturer = sales.getManufacturer();
        this.dollar_rank = sales.getDollarRank();
        this.dollar_volume = sales.getDollarVolume();
        this.dollar_share = sales.getDollarShare();
        this.dollar_volume_percentage_change = sales.getDollarVolumePercentageChange();
        this.kilo_volume = sales.getKiloVolume();
        this.kilo_share = sales.getKiloShare();
        this.kilo_volume_percentage_change = sales.getKiloVolumePercentageChange();
        this.average_ac_dist = sales.getAverageAcDist();
        this.average_retail_price = sales.getAverageRetailPrice();
        this.sales_source = sales.getSalesSource();
        this.nielsen_category = sales.getNielsenCategory();
        this.sales_year = sales.getSalesYear();
        this.control_label_flag = sales.getControlLabelFlag();
        this.kilo_volume_total = sales.getKiloVolumeTotal();
        this.kilo_volume_rank = sales.getKiloVolumeRank();
        this.dollar_volume_total = sales.getDollarVolumeTotal();
        this.cluster_number = sales.getClusterNumber();
        this.product_grouping = sales.getProductGrouping();
        this.sales_product_description = sales.getSalesProductDescription();
        this.classification_number = sales.getClassificationNumber();
        this.classification_type = sales.getClassificationType();
        this.sales_comment = sales.getSalesComment();
        this.sales_collection_date = sales.getSalesCollectionDate();
        this.number_of_units = sales.getNumberOfUnits();
        this.edited_by = sales.getEditedBy();
        this.creation_date = sales.getCreationDate();
        this.last_edit_date = sales.getLastEditDate();
        this.product_id = sales.getProductId();
        this.sales_id = sales.getId();
        System.out.println("the sales id is " + this.sales_id);

    }

    public String getSales_description()
    {
        return sales_description;
    }

    public void setSales_description(final String sales_description)
    {
        this.sales_description = sales_description;
    }

    public String getSales_upc()
    {
        return sales_upc;
    }

    public void setSales_upc(final String sales_upc)
    {
        this.sales_upc = sales_upc;
    }

    public String getSales_brand()
    {
        return sales_brand;
    }

    public void setSales_brand(final String sales_brand)
    {
        this.sales_brand = sales_brand;
    }

    public String getSales_manufacturer()
    {
        return sales_manufacturer;
    }

    public void setSales_manufacturer(final String sales_manufacturer)
    {
        this.sales_manufacturer = sales_manufacturer;
    }

    public Double getDollar_rank()
    {
        return dollar_rank;
    }

    public void setDollar_rank(final Double dollar_rank)
    {
        this.dollar_rank = dollar_rank;
    }

    public Double getDollar_volume()
    {
        return dollar_volume;
    }

    public void setDollar_volume(final Double dollar_volume)
    {
        this.dollar_volume = dollar_volume;
    }

    public Double getDollar_share()
    {
        return dollar_share;
    }

    public void setDollar_share(final Double dollar_share)
    {
        this.dollar_share = dollar_share;
    }

    public Double getDollar_volume_percentage_change()
    {
        return dollar_volume_percentage_change;
    }

    public void setDollar_volume_percentage_change(
            final Double dollar_volume_percentage_change)
    {
        this.dollar_volume_percentage_change = dollar_volume_percentage_change;
    }

    public Double getKilo_volume()
    {
        return kilo_volume;
    }

    public void setKilo_volume(final Double kilo_volume)
    {
        this.kilo_volume = kilo_volume;
    }

    public Double getKilo_share()
    {
        return kilo_share;
    }

    public void setKilo_share(final Double kilo_share)
    {
        this.kilo_share = kilo_share;
    }

    public Double getKilo_volume_percentage_change()
    {
        return kilo_volume_percentage_change;
    }

    public void setKilo_volume_percentage_change(
            final Double kilo_volume_percentage_change)
    {
        this.kilo_volume_percentage_change = kilo_volume_percentage_change;
    }

    public Double getAverage_ac_dist()
    {
        return average_ac_dist;
    }

    public void setAverage_ac_dist(final Double average_ac_dist)
    {
        this.average_ac_dist = average_ac_dist;
    }

    public Double getAverage_retail_price()
    {
        return average_retail_price;
    }

    public void setAverage_retail_price(final Double average_retail_price)
    {
        this.average_retail_price = average_retail_price;
    }

    public String getSales_source()
    {
        return sales_source;
    }

    public void setSales_source(final String sales_source)
    {
        this.sales_source = sales_source;
    }

    public String getNielsen_category()
    {
        return nielsen_category;
    }

    public void setNielsen_category(final String nielsen_category)
    {
        this.nielsen_category = nielsen_category;
    }

    public String getSales_year()
    {
        return sales_year;
    }

    public void setSales_year(final String sales_year)
    {
        this.sales_year = sales_year;
    }

    public Boolean getControl_label_flag()
    {
        return control_label_flag;
    }

    public void setControl_label_flag(final Boolean control_label_flag)
    {
        this.control_label_flag = control_label_flag;
    }

    public Double getKilo_volume_total()
    {
        return kilo_volume_total;
    }

    public void setKilo_volume_total(final Double kilo_volume_total)
    {
        this.kilo_volume_total = kilo_volume_total;
    }

    public Double getKilo_volume_rank()
    {
        return kilo_volume_rank;
    }

    public void setKilo_volume_rank(final Double kilo_volume_rank)
    {
        this.kilo_volume_rank = kilo_volume_rank;
    }

    public Double getDollar_volume_total()
    {
        return dollar_volume_total;
    }

    public void setDollar_volume_total(final Double dollar_volume_total)
    {
        this.dollar_volume_total = dollar_volume_total;
    }

    public Double getCluster_number()
    {
        return cluster_number;
    }

    public void setCluster_number(final Double cluster_number)
    {
        this.cluster_number = cluster_number;
    }

    public Double getProduct_grouping()
    {
        return product_grouping;
    }

    public void setProduct_grouping(final Double product_grouping)
    {
        this.product_grouping = product_grouping;
    }

    public String getSales_product_description()
    {
        return sales_product_description;
    }

    public void setSales_product_description(
            final String sales_product_description)
    {
        this.sales_product_description = sales_product_description;
    }

    public Double getClassification_number()
    {
        return classification_number;
    }

    public void setClassification_number(final Double classification_number)
    {
        this.classification_number = classification_number;
    }

    public String getClassification_type()
    {
        return classification_type;
    }

    public void setClassification_type(final String classification_type)
    {
        this.classification_type = classification_type;
    }

    public String getSales_comment()
    {
        return sales_comment;
    }

    public void setSales_comment(final String sales_comment)
    {
        this.sales_comment = sales_comment;
    }

    public String getSales_collection_date()
    {
        return sales_collection_date;
    }

    public void setSales_collection_date(String sales_collection_date){
        this.sales_collection_date = sales_collection_date;
    }

    public Integer getNumber_of_units()
    {
        return number_of_units;
    }

    public void setNumber_of_units(final Integer number_of_units)
    {
        this.number_of_units = number_of_units;
    }

    public String getEdited_by()
    {
        return edited_by;
    }

    public void setEdited_by(final String edited_by)
    {
        this.edited_by = edited_by;
    }

    public Timestamp getCreation_date()
    {
        return creation_date;
    }

    public void setCreation_date(final Timestamp creation_date)
    {
        this.creation_date = creation_date;
    }

    public Timestamp getLast_edit_date()
    {
        return last_edit_date;
    }

    public void setLast_edit_date(final Timestamp last_edit_date)
    {
        this.last_edit_date = last_edit_date;
    }

    public Long getProduct_id()
    {
        return product_id;
    }

    public void setProduct_id(final Long product_id)
    {
        this.product_id = product_id;
    }
    public Long geSales_id()
    {
        return sales_id;
    }

    public void setSales_id(final Long sales_id)
    {
        this.product_id = sales_id;
    }

}
