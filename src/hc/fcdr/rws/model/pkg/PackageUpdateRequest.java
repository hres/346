package hc.fcdr.rws.model.pkg;

import javax.xml.bind.annotation.XmlElement;

public class PackageUpdateRequest
{

    private Integer package_id;
    @XmlElement(
            nillable = true)
    private String  package_description;
    @XmlElement(
            nillable = true)
    private String  package_upc;
    @XmlElement(
            nillable = true)
    private String  package_brand;
    @XmlElement(
            nillable = true)
    private String  package_manufacturer;
    @XmlElement(
            nillable = true)
    private String  package_country;
    @XmlElement(
            nillable = true)
    private Double  package_size;
    @XmlElement(
            nillable = true)
    private String  package_size_unit_of_measure;
    @XmlElement(
            nillable = true)
    private String  storage_type;
    @XmlElement(
            nillable = true)
    private String  storage_statements;
    @XmlElement(
            nillable = true)
    private String  health_claims;
    @XmlElement(
            nillable = true)
    private String  other_package_statements;
    @XmlElement(
            nillable = true)
    private String  suggested_directions;
    @XmlElement(
            nillable = true)
    private String  ingredients;
    @XmlElement(
            nillable = true)
    private Boolean multi_part_flag;
    @XmlElement(
            nillable = true)
    private String  nutrition_fact_table;
    @XmlElement(
            nillable = true)
    private Double  as_prepared_per_serving_amount;
    @XmlElement(
            nillable = true)
    private String  as_prepared_unit_of_measure;
    @XmlElement(
            nillable = true)
    private Double  as_sold_per_serving_amount;
    @XmlElement(
            nillable = true)
    private String  as_sold_unit_of_measure;
    @XmlElement(
            nillable = true)
    private Double  as_prepared_per_serving_amount_in_grams;
    @XmlElement(
            nillable = true)
    private Double  as_sold_per_serving_amount_in_grams;
    @XmlElement(
            nillable = true)
    private String  package_comment;
    @XmlElement(
            nillable = true)
    private String  package_source;
    @XmlElement(
            nillable = true)
    private String  package_product_description;
    @XmlElement(
            nillable = true)
    private String  package_collection_date;
    @XmlElement(
            nillable = true)
    private Integer number_of_units;
    @XmlElement(
            nillable = true)
    private String  edited_by;
    @XmlElement(
            nillable = true)
    private Boolean informed_dining_program;
    @XmlElement(
            nillable = true)
    private String  nft_last_update_date;
    @XmlElement(
            nillable = true)
    private Double  product_grouping;
    @XmlElement(
            nillable = true)
    private Boolean child_item;
    @XmlElement(
            nillable = true)
    private String  classification_number;
    @XmlElement(
            nillable = true)
    private String  classification_name;
    @XmlElement(
            nillable = true)
    private Double  nielsen_item_rank;
    @XmlElement(
            nillable = true)
    private String  nutrient_claims;
    @XmlElement(
            nillable = true)
    private String  package_nielsen_category;
    @XmlElement(
            nillable = true)
    private String  common_household_measure;
    @XmlElement(
            nillable = true)
    private String  creation_date;
    @XmlElement(
            nillable = true)
    private String  last_edit_date;
    @XmlElement(
            nillable = true)
    private Boolean calculated;

    public PackageUpdateRequest()
    {
        super();
        package_id = null;
        package_description = null;
        package_upc = null;
        package_brand = null;
        package_manufacturer = null;
        package_country = null;
        package_size = null;
        package_size_unit_of_measure = null;
        storage_type = null;
        storage_statements = null;
        health_claims = null;
        other_package_statements = null;
        suggested_directions = null;
        ingredients = null;
        multi_part_flag = null;
        nutrition_fact_table = null;
        as_prepared_per_serving_amount = null;
        as_prepared_unit_of_measure = null;
        as_sold_per_serving_amount = null;
        as_sold_unit_of_measure = null;
        as_prepared_per_serving_amount_in_grams = null;
        as_sold_per_serving_amount_in_grams = null;
        package_comment = null;
        package_source = null;
        package_product_description = null;
        package_collection_date = null;
        number_of_units = null;
        edited_by = null;
        informed_dining_program = null;
        nft_last_update_date = null;
        product_grouping = null;
        child_item = null;
        classification_number = null;
        classification_name = null;
        nielsen_item_rank = null;
        nutrient_claims = null;
        package_nielsen_category = null;
        common_household_measure = null;
        creation_date = null;
        last_edit_date = null;
        calculated = null;
    }

    public PackageUpdateRequest(final Integer package_id,
            final String package_description, final String package_upc,
            final String package_brand, final String package_manufacturer,
            final String package_country, final Double package_size,
            final String package_size_unit_of_measure,
            final String storage_type, final String storage_statements,
            final String health_claims, final String other_package_statements,
            final String suggested_directions, final String ingredients,
            final Boolean multi_part_flag, final String nutrition_fact_table,
            final Double as_prepared_per_serving_amount,
            final String as_prepared_unit_of_measure,
            final Double as_sold_per_serving_amount,
            final String as_sold_unit_of_measure,
            final Double as_prepared_per_serving_amount_in_grams,
            final Double as_sold_per_serving_amount_in_grams,
            final String package_comment, final String package_source,
            final String package_product_description,
            final String package_collection_date, final Integer number_of_units,
            final String edited_by, final Boolean informed_dining_program,
            final String nft_last_update_date, final Double product_grouping,
            final Boolean child_item, final String classification_number,
            final String classification_name, final Double nielsen_item_rank,
            final String nutrient_claims, final String package_nielsen_category,
            final String common_household_measure, final String creation_date,
            final String last_edit_date, final Boolean calculated)
    {
        super();
        this.package_id = package_id;
        this.package_description = package_description;
        this.package_upc = package_upc;
        this.package_brand = package_brand;
        this.package_manufacturer = package_manufacturer;
        this.package_country = package_country;
        this.package_size = package_size;
        this.package_size_unit_of_measure = package_size_unit_of_measure;
        this.storage_type = storage_type;
        this.storage_statements = storage_statements;
        this.health_claims = health_claims;
        this.other_package_statements = other_package_statements;
        this.suggested_directions = suggested_directions;
        this.ingredients = ingredients;
        this.multi_part_flag = multi_part_flag;
        this.nutrition_fact_table = nutrition_fact_table;
        this.as_prepared_per_serving_amount = as_prepared_per_serving_amount;
        this.as_prepared_unit_of_measure = as_prepared_unit_of_measure;
        this.as_sold_per_serving_amount = as_sold_per_serving_amount;
        this.as_sold_unit_of_measure = as_sold_unit_of_measure;
        this.as_prepared_per_serving_amount_in_grams =
                as_prepared_per_serving_amount_in_grams;
        this.as_sold_per_serving_amount_in_grams =
                as_sold_per_serving_amount_in_grams;
        this.package_comment = package_comment;
        this.package_source = package_source;
        this.package_product_description = package_product_description;
        this.package_collection_date = package_collection_date;
        this.number_of_units = number_of_units;
        this.edited_by = edited_by;
        this.informed_dining_program = informed_dining_program;
        this.nft_last_update_date = nft_last_update_date;
        this.product_grouping = product_grouping;
        this.child_item = child_item;
        this.classification_number = classification_number;
        this.classification_name = classification_name;
        this.nielsen_item_rank = nielsen_item_rank;
        this.nutrient_claims = nutrient_claims;
        this.package_nielsen_category = package_nielsen_category;
        this.common_household_measure = common_household_measure;
        this.creation_date = creation_date;
        this.last_edit_date = last_edit_date;
        this.calculated = calculated;
    }

    public Integer getPackage_id()
    {
        return package_id;
    }

    public void setPackage_id(final Integer package_id)
    {
        this.package_id = package_id;
    }

    public String getPackage_description()
    {
        return package_description;
    }

    public void setPackage_description(final String package_description)
    {
        this.package_description = package_description;
    }

    public String getPackage_upc()
    {
        return package_upc;
    }

    public void setPackage_upc(final String package_upc)
    {
        this.package_upc = package_upc;
    }

    public String getPackage_brand()
    {
        return package_brand;
    }

    public void setPackage_brand(final String package_brand)
    {
        this.package_brand = package_brand;
    }

    public String getPackage_manufacturer()
    {
        return package_manufacturer;
    }

    public void setPackage_manufacturer(final String package_manufacturer)
    {
        this.package_manufacturer = package_manufacturer;
    }

    public String getPackage_country()
    {
        return package_country;
    }

    public void setPackage_country(final String package_country)
    {
        this.package_country = package_country;
    }

    public Double getPackage_size()
    {
        return package_size;
    }

    public void setPackage_size(final Double package_size)
    {
        this.package_size = package_size;
    }

    public String getPackage_size_unit_of_measure()
    {
        return package_size_unit_of_measure;
    }

    public void setPackage_size_unit_of_measure(
            final String package_size_unit_of_measure)
    {
        this.package_size_unit_of_measure = package_size_unit_of_measure;
    }

    public String getStorage_type()
    {
        return storage_type;
    }

    public void setStorage_type(final String storage_type)
    {
        this.storage_type = storage_type;
    }

    public String getStorage_statements()
    {
        return storage_statements;
    }

    public void setStorage_statements(final String storage_statements)
    {
        this.storage_statements = storage_statements;
    }

    public String getHealth_claims()
    {
        return health_claims;
    }

    public void setHealth_claims(final String health_claims)
    {
        this.health_claims = health_claims;
    }

    public String getOther_package_statements()
    {
        return other_package_statements;
    }

    public void setOther_package_statements(
            final String other_package_statements)
    {
        this.other_package_statements = other_package_statements;
    }

    public String getSuggested_directions()
    {
        return suggested_directions;
    }

    public void setSuggested_directions(final String suggested_directions)
    {
        this.suggested_directions = suggested_directions;
    }

    public String getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(final String ingredients)
    {
        this.ingredients = ingredients;
    }

    public Boolean getMulti_part_flag()
    {
        return multi_part_flag;
    }

    public void setMulti_part_flag(final Boolean multi_part_flag)
    {
        this.multi_part_flag = multi_part_flag;
    }

    public String getNutrition_fact_table()
    {
        return nutrition_fact_table;
    }

    public void setNutrition_fact_table(final String nutrition_fact_table)
    {
        this.nutrition_fact_table = nutrition_fact_table;
    }

    public Double getAs_prepared_per_serving_amount()
    {
        return as_prepared_per_serving_amount;
    }

    public void setAs_prepared_per_serving_amount(
            final Double as_prepared_per_serving_amount)
    {
        this.as_prepared_per_serving_amount = as_prepared_per_serving_amount;
    }

    public String getAs_prepared_unit_of_measure()
    {
        return as_prepared_unit_of_measure;
    }

    public void setAs_prepared_unit_of_measure(
            final String as_prepared_unit_of_measure)
    {
        this.as_prepared_unit_of_measure = as_prepared_unit_of_measure;
    }

    public Double getAs_sold_per_serving_amount()
    {
        return as_sold_per_serving_amount;
    }

    public void setAs_sold_per_serving_amount(
            final Double as_sold_per_serving_amount)
    {
        this.as_sold_per_serving_amount = as_sold_per_serving_amount;
    }

    public String getAs_sold_unit_of_measure()
    {
        return as_sold_unit_of_measure;
    }

    public void setAs_sold_unit_of_measure(final String as_sold_unit_of_measure)
    {
        this.as_sold_unit_of_measure = as_sold_unit_of_measure;
    }

    public Double getAs_prepared_per_serving_amount_in_grams()
    {
        return as_prepared_per_serving_amount_in_grams;
    }

    public void setAs_prepared_per_serving_amount_in_grams(
            final Double as_prepared_per_serving_amount_in_grams)
    {
        this.as_prepared_per_serving_amount_in_grams =
                as_prepared_per_serving_amount_in_grams;
    }

    public Double getAs_sold_per_serving_amount_in_grams()
    {
        return as_sold_per_serving_amount_in_grams;
    }

    public void setAs_sold_per_serving_amount_in_grams(
            final Double as_sold_per_serving_amount_in_grams)
    {
        this.as_sold_per_serving_amount_in_grams =
                as_sold_per_serving_amount_in_grams;
    }

    public String getPackage_comment()
    {
        return package_comment;
    }

    public void setPackage_comment(final String package_comment)
    {
        this.package_comment = package_comment;
    }

    public String getPackage_source()
    {
        return package_source;
    }

    public void setPackage_source(final String package_source)
    {
        this.package_source = package_source;
    }

    public String getPackage_product_description()
    {
        return package_product_description;
    }

    public void setPackage_product_description(
            final String package_product_description)
    {
        this.package_product_description = package_product_description;
    }

    public String getPackage_collection_date()
    {
        return package_collection_date;
    }

    public void setPackage_collection_date(final String package_collection_date)
    {
        this.package_collection_date = package_collection_date;
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

    public Boolean getInformed_dining_program()
    {
        return informed_dining_program;
    }

    public void setInformed_dining_program(
            final Boolean informed_dining_program)
    {
        this.informed_dining_program = informed_dining_program;
    }

    public String getNft_last_update_date()
    {
        return nft_last_update_date;
    }

    public void setNft_last_update_date(final String nft_last_update_date)
    {
        this.nft_last_update_date = nft_last_update_date;
    }

    public Double getProduct_grouping()
    {
        return product_grouping;
    }

    public void setProduct_grouping(final Double product_grouping)
    {
        this.product_grouping = product_grouping;
    }

    public Boolean getChild_item()
    {
        return child_item;
    }

    public void setChild_item(final Boolean child_item)
    {
        this.child_item = child_item;
    }

    public String getClassification_number()
    {
        return classification_number;
    }

    public void setClassification_number(final String classification_number)
    {
        this.classification_number = classification_number;
    }

    public String getClassification_name()
    {
        return classification_name;
    }

    public void setClassification_name(final String classification_name)
    {
        this.classification_name = classification_name;
    }

    public Double getNielsen_item_rank()
    {
        return nielsen_item_rank;
    }

    public void setNielsen_item_rank(final Double nielsen_item_rank)
    {
        this.nielsen_item_rank = nielsen_item_rank;
    }

    public String getNutrient_claims()
    {
        return nutrient_claims;
    }

    public void setNutrient_claims(final String nutrient_claims)
    {
        this.nutrient_claims = nutrient_claims;
    }

    public String getPackage_nielsen_category()
    {
        return package_nielsen_category;
    }

    public void setPackage_nielsen_category(
            final String package_nielsen_category)
    {
        this.package_nielsen_category = package_nielsen_category;
    }

    public String getCommon_household_measure()
    {
        return common_household_measure;
    }

    public void setCommon_household_measure(
            final String common_household_measure)
    {
        this.common_household_measure = common_household_measure;
    }

    public String getCreation_date()
    {
        return creation_date;
    }

    public void setCreation_date(final String creation_date)
    {
        this.creation_date = creation_date;
    }

    public String getLast_edit_date()
    {
        return last_edit_date;
    }

    public void setLast_edit_date(final String last_edit_date)
    {
        this.last_edit_date = last_edit_date;
    }

    public Boolean getCalculated()
    {
        return calculated;
    }

    public void setCalculated(final Boolean calculated)
    {
        this.calculated = calculated;
    }

}
