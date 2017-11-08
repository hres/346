package hc.fcdr.rws.model.sales;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Sales;

@XmlRootElement
public class SalesResponse
{
    private String    sales_description;
    private String    sales_upc;
    private String    sales_brand;
    private String    sales_manufacturer;
    private Double    dollar_rank;
    private Double    dollar_volume;
    private Double    dollar_share;
    private Double    dollar_volume_percentage_change;
    private Double    kilo_volume;
    private Double    kilo_share;
    private Double    kilo_volume_percentage_change;
    private Double    average_ac_dist;
    private Double    average_retail_price;
    private String    sales_source;
    private String    nielsen_category;
    private String   sales_year;
    private Boolean   control_label_flag;
    private Double    kilo_volume_total;
    private Double    kilo_volume_rank;
    private Double    dollar_volume_total;
    private Double    cluster_number;
    private Double    product_grouping;
    private String    sales_product_description;
    private Double    classification_number;
    private String    classification_type;
    private String    sales_comment;
    private Date      sales_collection_date;
    private Integer   number_of_units;
    private String    edited_by;
    private Timestamp creation_date;
    private Timestamp last_edit_date;
    private Long      product_id;

    public SalesResponse()
    {
        super();
        this.sales_description = "";
        this.sales_upc = "";
        this.sales_brand = "";
        this.sales_manufacturer = "";
        this.dollar_rank = 0.0;
        this.dollar_volume = 0.0;
        this.dollar_share = 0.0;
        this.dollar_volume_percentage_change = 0.0;
        this.kilo_volume = 0.0;
        this.kilo_share = 0.0;
        this.kilo_volume_percentage_change = 0.0;
        this.average_ac_dist = 0.0;
        this.average_retail_price = 0.0;
        this.sales_source = "";
        this.nielsen_category = "";
        this.sales_year = "";
        this.control_label_flag = false;
        this.kilo_volume_total = 0.0;
        this.kilo_volume_rank = 0.0;
        this.dollar_volume_total = 0.0;
        this.cluster_number = 0.0;
        this.product_grouping = 0.0;
        this.sales_product_description = "";
        this.classification_number = 0.0;
        this.classification_type = "";
        this.sales_comment = "";
        this.sales_collection_date = null;
        this.number_of_units = 0;
        this.edited_by = "";
        this.creation_date = null;
        this.last_edit_date = null;
        this.product_id = 0L;

    }

    public SalesResponse(Sales sales)
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

    }

    public String getSales_description()
    {
        return sales_description;
    }

    public void setSales_description(String sales_description)
    {
        this.sales_description = sales_description;
    }

    public String getSales_upc()
    {
        return sales_upc;
    }

    public void setSales_upc(String sales_upc)
    {
        this.sales_upc = sales_upc;
    }

    public String getSales_brand()
    {
        return sales_brand;
    }

    public void setSales_brand(String sales_brand)
    {
        this.sales_brand = sales_brand;
    }

    public String getSales_manufacturer()
    {
        return sales_manufacturer;
    }

    public void setSales_manufacturer(String sales_manufacturer)
    {
        this.sales_manufacturer = sales_manufacturer;
    }

    public Double getDollar_rank()
    {
        return dollar_rank;
    }

    public void setDollar_rank(Double dollar_rank)
    {
        this.dollar_rank = dollar_rank;
    }

    public Double getDollar_volume()
    {
        return dollar_volume;
    }

    public void setDollar_volume(Double dollar_volume)
    {
        this.dollar_volume = dollar_volume;
    }

    public Double getDollar_share()
    {
        return dollar_share;
    }

    public void setDollar_share(Double dollar_share)
    {
        this.dollar_share = dollar_share;
    }

    public Double getDollar_volume_percentage_change()
    {
        return dollar_volume_percentage_change;
    }

    public void setDollar_volume_percentage_change(
            Double dollar_volume_percentage_change)
    {
        this.dollar_volume_percentage_change = dollar_volume_percentage_change;
    }

    public Double getKilo_volume()
    {
        return kilo_volume;
    }

    public void setKilo_volume(Double kilo_volume)
    {
        this.kilo_volume = kilo_volume;
    }

    public Double getKilo_share()
    {
        return kilo_share;
    }

    public void setKilo_share(Double kilo_share)
    {
        this.kilo_share = kilo_share;
    }

    public Double getKilo_volume_percentage_change()
    {
        return kilo_volume_percentage_change;
    }

    public void setKilo_volume_percentage_change(
            Double kilo_volume_percentage_change)
    {
        this.kilo_volume_percentage_change = kilo_volume_percentage_change;
    }

    public Double getAverage_ac_dist()
    {
        return average_ac_dist;
    }

    public void setAverage_ac_dist(Double average_ac_dist)
    {
        this.average_ac_dist = average_ac_dist;
    }

    public Double getAverage_retail_price()
    {
        return average_retail_price;
    }

    public void setAverage_retail_price(Double average_retail_price)
    {
        this.average_retail_price = average_retail_price;
    }

    public String getSales_source()
    {
        return sales_source;
    }

    public void setSales_source(String sales_source)
    {
        this.sales_source = sales_source;
    }

    public String getNielsen_category()
    {
        return nielsen_category;
    }

    public void setNielsen_category(String nielsen_category)
    {
        this.nielsen_category = nielsen_category;
    }

    public String getSales_year()
    {
        return sales_year;
    }

    public void setSales_year(String sales_year)
    {
        this.sales_year = sales_year;
    }

    public Boolean getControl_label_flag()
    {
        return control_label_flag;
    }

    public void setControl_label_flag(Boolean control_label_flag)
    {
        this.control_label_flag = control_label_flag;
    }

    public Double getKilo_volume_total()
    {
        return kilo_volume_total;
    }

    public void setKilo_volume_total(Double kilo_volume_total)
    {
        this.kilo_volume_total = kilo_volume_total;
    }

    public Double getKilo_volume_rank()
    {
        return kilo_volume_rank;
    }

    public void setKilo_volume_rank(Double kilo_volume_rank)
    {
        this.kilo_volume_rank = kilo_volume_rank;
    }

    public Double getDollar_volume_total()
    {
        return dollar_volume_total;
    }

    public void setDollar_volume_total(Double dollar_volume_total)
    {
        this.dollar_volume_total = dollar_volume_total;
    }

    public Double getCluster_number()
    {
        return cluster_number;
    }

    public void setCluster_number(Double cluster_number)
    {
        this.cluster_number = cluster_number;
    }

    public Double getProduct_grouping()
    {
        return product_grouping;
    }

    public void setProduct_grouping(Double product_grouping)
    {
        this.product_grouping = product_grouping;
    }

    public String getSales_product_description()
    {
        return sales_product_description;
    }

    public void setSales_product_description(String sales_product_description)
    {
        this.sales_product_description = sales_product_description;
    }

    public Double getClassification_number()
    {
        return classification_number;
    }

    public void setClassification_number(Double classification_number)
    {
        this.classification_number = classification_number;
    }

    public String getClassification_type()
    {
        return classification_type;
    }

    public void setClassification_type(String classification_type)
    {
        this.classification_type = classification_type;
    }

    public String getSales_comment()
    {
        return sales_comment;
    }

    public void setSales_comment(String sales_comment)
    {
        this.sales_comment = sales_comment;
    }

    public Date getSales_collection_date()
    {
        return sales_collection_date;
    }

    public void setSales_collection_date(Date sales_collection_date)
    {
        this.sales_collection_date = sales_collection_date;
    }

    public Integer getNumber_of_units()
    {
        return number_of_units;
    }

    public void setNumber_of_units(Integer number_of_units)
    {
        this.number_of_units = number_of_units;
    }

    public String getEdited_by()
    {
        return edited_by;
    }

    public void setEdited_by(String edited_by)
    {
        this.edited_by = edited_by;
    }

    public Timestamp getCreation_date()
    {
        return creation_date;
    }

    public void setCreation_date(Timestamp creation_date)
    {
        this.creation_date = creation_date;
    }

    public Timestamp getLast_edit_date()
    {
        return last_edit_date;
    }

    public void setLast_edit_date(Timestamp last_edit_date)
    {
        this.last_edit_date = last_edit_date;
    }

    public Long getProduct_id()
    {
        return product_id;
    }

    public void setProduct_id(Long product_id)
    {
        this.product_id = product_id;
    }

}
