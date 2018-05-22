package hc.fcdr.rws.model.sales;

import javax.xml.bind.annotation.XmlElement;

public class SalesUpdateRequest
{
    @XmlElement(
            nillable = true)
    public String  sales_description;
    @XmlElement(
            nillable = true)
    public String  sales_upc;
    @XmlElement(
            nillable = true)
    public String  sales_brand;
    @XmlElement(
            nillable = true)
    public String  sales_manufacturer;
    @XmlElement(
            nillable = true)
    public Double  dollar_rank;
    @XmlElement(
            nillable = true)
    public Double  dollar_volume;
    @XmlElement(
            nillable = true)
    public Double  dollar_share;
    @XmlElement(
            nillable = true)
    public Double  dollar_volume_percentage_change;
    @XmlElement(
            nillable = true)
    public Double  kilo_volume;
    @XmlElement(
            nillable = true)
    public Double  kilo_share;
    @XmlElement(
            nillable = true)
    public Double  kilo_volume_percentage_change;
    @XmlElement(
            nillable = true)
    public Double  average_ac_dist;
    @XmlElement(
            nillable = true)
    public Double  average_retail_price;
    @XmlElement(
            nillable = true)
    public String  sales_source;
    @XmlElement(
            nillable = true)
    public String  nielsen_category;
    @XmlElement(
            nillable = true)
    public Integer sales_year;
    @XmlElement(
            nillable = true)
    public Boolean control_label_flag;
    @XmlElement(
            nillable = true)
    public Double  kilo_volume_total;
    @XmlElement(
            nillable = true)
    public Double  kilo_volume_rank;
    @XmlElement(
            nillable = true)
    public Double  dollar_volume_total;
    @XmlElement(
            nillable = true)
    public Double  cluster_number;
    @XmlElement(
            nillable = true)
    public Double  product_grouping;
    @XmlElement(
            nillable = true)
    public String  sales_product_description;
    @XmlElement(
            nillable = true)
    public String  classification_number;
    @XmlElement(
            nillable = true)
    public String  classification_type;
    @XmlElement(
            nillable = true)
    public String  sales_comment;
    @XmlElement(
            nillable = true)
    public String  sales_collection_date;

    public Integer number_of_units;
    @XmlElement(
            nillable = true)
    public String  edited_by;
    public Long    sales_id;

    public SalesUpdateRequest()
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
        sales_id = null;
    }

    public SalesUpdateRequest(final String sales_description,
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
            final String edited_by, final Long sales_id)
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
        this.sales_id = sales_id;
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
                + edited_by + ", sales_id=" + sales_id + "]";
    }

}
