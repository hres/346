package hc.fcdr.rws.model.sales;

import javax.xml.bind.annotation.XmlElement;

public class SalesInsertRequest
{
    @XmlElement(
            nillable = true)
    public String  sales_description;
    @XmlElement(
            nillable = true)
    public String  sales_upc                       = null;
    @XmlElement(
            nillable = true)
    public String  sales_brand;
    @XmlElement(
            nillable = true)
    public String  sales_manufacturer;
    @XmlElement(
            nillable = true)
    public Double  dollar_rank                     = null;
    @XmlElement(
            nillable = true)
    public Double  dollar_volume                   = null;
    @XmlElement(
            nillable = true)
    public Double  dollar_share                    = null;
    @XmlElement(
            nillable = true)
    public Double  dollar_volume_percentage_change = null;
    @XmlElement(
            nillable = true)
    public Double  kilo_volume                     = null;
    @XmlElement(
            nillable = true)
    public Double  kilo_share                      = null;
    @XmlElement(
            nillable = true)
    public Double  kilo_volume_percentage_change   = null;
    @XmlElement(
            nillable = true)
    public Double  average_ac_dist                 = null;
    @XmlElement(
            nillable = true)
    public Double  average_retail_price            = null;
    @XmlElement(
            nillable = true)
    public String  sales_source;
    @XmlElement(
            nillable = true)
    public String  nielsen_category;
    @XmlElement(
            nillable = true)
    public Integer sales_year                      = null;
    @XmlElement(
            nillable = true)
    public Boolean control_label_flag              = null;
    @XmlElement(
            nillable = true)
    public Double  kilo_volume_total               = null;
    @XmlElement(
            nillable = true)
    public Double  kilo_volume_rank                = null;
    @XmlElement(
            nillable = true)
    public Double  dollar_volume_total             = null;
    @XmlElement(
            nillable = true)
    public Double  cluster_number                  = null;
    @XmlElement(
            nillable = true)
    public Double  product_grouping                = null;
    @XmlElement(
            nillable = true)
    public String  sales_product_description;
    @XmlElement(
            nillable = true)
    public String  classification_number           = null;
    @XmlElement(
            nillable = true)
    public String  classification_type             = null;
    @XmlElement(
            nillable = true)
    public String  sales_comment;
    @XmlElement(
            nillable = true)
    public String  sales_collection_date;
    @XmlElement(
            nillable = true)
    public Integer number_of_units                 = null;
    @XmlElement(
            nillable = true)
    public String  edited_by;
    public Integer product_id                      = null;

    public SalesInsertRequest()
    {

        sales_description = null;
        sales_upc = null;
        sales_brand = null;
        sales_manufacturer = null;
        dollar_rank = null;
        dollar_volume = null;
        dollar_share = null;
        dollar_volume_percentage_change = null;
        kilo_volume = null;
        kilo_share = null;
        kilo_volume_percentage_change = null;
        average_ac_dist = null;
        average_retail_price = null;
        sales_source = null;
        nielsen_category = null;
        sales_year = null;
        control_label_flag = null;
        kilo_volume_total = null;
        kilo_volume_rank = null;
        dollar_volume_total = null;
        cluster_number = null;
        product_grouping = null;
        sales_product_description = null;
        classification_number = null;
        classification_type = null;
        sales_comment = null;
        sales_collection_date = null;
        number_of_units = null;
        edited_by = null;
        product_id = null;
    }

    public SalesInsertRequest(final String sales_description,
            final String sales_upc, final String sales_brand,
            final String sales_manufacturer, final Double dollar_rank,
            final Double dollar_volume, final Double dollar_share,
            final Double dollar_volume_percentage_change,
            final Double kilo_volume, final Double kilo_share,
            final Double kilo_volume_percentage_change,
            final Double average_ac_dist, final Double average_retail_price,
            final String sales_source, final String nielsen_category,
            final Integer sales_year, final Boolean control_label_flag,
            final Double kilo_volume_total, final Double kilo_volume_rank,
            final Double dollar_volume_total, final Double cluster_number,
            final Double product_grouping,
            final String sales_product_description,
            final String classification_number,
            final String classification_type, final String sales_comment,
            final String sales_collection_date, final Integer number_of_units,
            final String edited_by, final Integer product_id)
    {
        super();
        this.sales_description = sales_description;
        this.sales_upc = sales_upc;
        this.sales_brand = sales_brand;
        this.sales_manufacturer = sales_manufacturer;
        this.dollar_rank = dollar_rank;
        this.dollar_volume = dollar_volume;
        this.dollar_share = dollar_share;
        this.dollar_volume_percentage_change = dollar_volume_percentage_change;
        this.kilo_volume = kilo_volume;
        this.kilo_share = kilo_share;
        this.kilo_volume_percentage_change = kilo_volume_percentage_change;
        this.average_ac_dist = average_ac_dist;
        this.average_retail_price = average_retail_price;
        this.sales_source = sales_source;
        this.nielsen_category = nielsen_category;
        this.sales_year = sales_year;
        this.control_label_flag = control_label_flag;
        this.kilo_volume_total = kilo_volume_total;
        this.kilo_volume_rank = kilo_volume_rank;
        this.dollar_volume_total = dollar_volume_total;
        this.cluster_number = cluster_number;
        this.product_grouping = product_grouping;
        this.sales_product_description = sales_product_description;
        this.classification_number = classification_number;
        this.classification_type = classification_type;
        this.sales_comment = sales_comment;
        this.sales_collection_date = sales_collection_date;
        this.number_of_units = number_of_units;
        this.edited_by = edited_by;
        this.product_id = product_id;
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

    public Integer getSales_year()
    {
        return sales_year;
    }

    public void setSales_year(final Integer sales_year)
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

    public String getClassification_number()
    {
        return classification_number;
    }

    public void setClassification_number(final String classification_number)
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

    public void setSales_collection_date(final String sales_collection_date)
    {
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

    public Integer getProduct_id()
    {
        return product_id;
    }

    public void setProduct_id(final Integer product_id)
    {
        this.product_id = product_id;
    }

    @Override
    public String toString()
    {
        return "SalesInsertRequest [sales_description="
                + sales_description + ", sales_upc=" + sales_upc
                + ", sales_brand=" + sales_brand + ", sales_manufacturer="
                + sales_manufacturer + ", dollar_rank=" + dollar_rank
                + ", dollar_volume=" + dollar_volume + ", dollar_share="
                + dollar_share + ", dollar_volume_percentage_change="
                + dollar_volume_percentage_change + ", kilo_volume="
                + kilo_volume + ", kilo_share=" + kilo_share
                + ", kilo_volume_percentage_change="
                + kilo_volume_percentage_change + ", average_ac_dist="
                + average_ac_dist + ", average_retail_price="
                + average_retail_price + ", sales_source=" + sales_source
                + ", nielsen_category=" + nielsen_category + ", sales_year="
                + sales_year + ", control_label_flag=" + control_label_flag
                + ", kilo_volume_total=" + kilo_volume_total
                + ", kilo_volume_rank=" + kilo_volume_rank
                + ", dollar_volume_total=" + dollar_volume_total
                + ", cluster_number=" + cluster_number + ", product_grouping="
                + product_grouping + ", sales_product_description="
                + sales_product_description + ", classification_number="
                + classification_number + ", classification_type="
                + classification_type + ", sales_comment=" + sales_comment
                + ", sales_collection_date=" + sales_collection_date
                + ", number_of_units=" + number_of_units + ", edited_by="
                + edited_by + ", product_id=" + product_id + "]";
    }

}
