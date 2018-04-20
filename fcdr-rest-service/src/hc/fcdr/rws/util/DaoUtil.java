package hc.fcdr.rws.util;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.domain.Classification;
import hc.fcdr.rws.domain.Package;
import hc.fcdr.rws.domain.Product;
import hc.fcdr.rws.domain.Sales;
import hc.fcdr.rws.model.classification.ClassificationResponse;
import hc.fcdr.rws.model.importLabel.ImportLabelModel;
import hc.fcdr.rws.model.importLabel.ImportLabelNft;
import hc.fcdr.rws.model.importLabel.ImportLabelRequest;
import hc.fcdr.rws.model.pkg.NftModel;
import hc.fcdr.rws.model.pkg.NftRequest;
import hc.fcdr.rws.model.pkg.PackageInsertRequest;
import hc.fcdr.rws.model.pkg.PackageRequest;
import hc.fcdr.rws.model.pkg.PackageResponse;
import hc.fcdr.rws.model.pkg.PackageUpdateRequest;
import hc.fcdr.rws.model.pkg.PackageViewData;
import hc.fcdr.rws.model.product.ProductClassificationResponse;
import hc.fcdr.rws.model.product.ProductInsertRequest;
import hc.fcdr.rws.model.product.ProductLabelsResponse;
import hc.fcdr.rws.model.product.ProductRequest;
import hc.fcdr.rws.model.product.ProductResponse;
import hc.fcdr.rws.model.product.ProductSalesLabelRequest;
import hc.fcdr.rws.model.product.ProductSalesLabelResponse;
import hc.fcdr.rws.model.product.ProductSalesResponse;
import hc.fcdr.rws.model.product.ProductUpdateRequest;
import hc.fcdr.rws.model.sales.SalesInsertRequest;
import hc.fcdr.rws.model.sales.SalesRequest;
import hc.fcdr.rws.model.sales.SalesResponse;
import hc.fcdr.rws.model.sales.SalesResponseShort;
import hc.fcdr.rws.model.sales.SalesUpdateRequest;
import hc.fcdr.rws.model.sales.SalesYearsResponse;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;

/**
 * Utility class for DAO's. This class contains commonly used DAO logic which is been refactored in single static
 * methods. As far it contains a PreparedStatement values setter and several quiet close methods.
 * 
 */
public final class DaoUtil
{

    // Constructors
    // -------------------------------------------------------------------------------
//    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    private DaoUtil()
    {

    }

    // Actions
    // ------------------------------------------------------------------------------------

    /**
     * Returns a PreparedStatement of the given connection, set with the given SQL query and the given parameter values.
     * 
     * @param connection
     *            The Connection to create the PreparedStatement from.
     * @param sql
     *            The SQL query to construct the PreparedStatement with.
     * @param returnGeneratedKeys
     *            Set whether to return generated keys or not.
     * @param values
     *            The parameter values to be set in the created PreparedStatement.
     * @throws SQLException
     *             If something fails during creating the PreparedStatement.
     */
    public static PreparedStatement prepareStatement(
            final Connection connection, final String sql,
            final boolean returnGeneratedKeys, final Object... values)
            throws SQLException
    {
        final PreparedStatement preparedStatement = connection.prepareStatement(
                sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS
                        : Statement.NO_GENERATED_KEYS);

        if (values != null)
            setValues(preparedStatement, values);

        return preparedStatement;
    }

    /**
     * Set the given parameter values in the given PreparedStatement.
     * 
     * @param connection
     *            The PreparedStatement to set the given parameter values in.
     * @param values
     *            The parameter values to be set in the created PreparedStatement.
     * @throws SQLException
     *             If something fails during setting the PreparedStatement values.
     */
    public static void setValues(final PreparedStatement preparedStatement,
            final Object... values) throws SQLException
    {
        for (int i = 0; i < values.length; i++)
            preparedStatement.setObject(i + 1, values[i]);
    }

    /**
     * Converts the given java.util.Date to java.sql.Date.
     * 
     * @param date
     *            The java.util.Date to be converted to java.sql.Date.
     * @return The converted java.sql.Date.
     */
    public static Date toSqlDate(final java.util.Date date)
    {
        return (date != null) ? new Date(date.getTime()) : null;
    }

    /**
     * Quietly close the Connection. Any errors will be printed to the stderr.
     * 
     * @param connection
     *            The Connection to be closed quietly.
     */
    public static void close(final Connection connection)
    {
        if (connection != null)
            try
            {
                connection.close();
            }
            catch (final SQLException e)
            {
                System.err.println(
                        "Closing Connection failed: " + e.getMessage());
                e.printStackTrace();
            }
    }

    /**
     * Quietly close the Statement. Any errors will be printed to the stderr.
     * 
     * @param statement
     *            The Statement to be closed quietly.
     */
    public static void close(final Statement statement)
    {
        if (statement != null)
            try
            {
                statement.close();
            }
            catch (final SQLException e)
            {
                System.err.println(
                        "Closing Statement failed: " + e.getMessage());
                e.printStackTrace();
            }
    }

    /**
     * Quietly close the ResultSet. Any errors will be printed to the stderr.
     * 
     * @param resultSet
     *            The ResultSet to be closed quietly.
     */
    public static void close(final ResultSet resultSet)
    {
        if (resultSet != null)
            try
            {
                resultSet.close();
            }
            catch (final SQLException e)
            {
                System.err.println(
                        "Closing ResultSet failed: " + e.getMessage());
                e.printStackTrace();
            }
    }

    /**
     * Quietly close the Connection and Statement. Any errors will be printed to the stderr.
     * 
     * @param connection
     *            The Connection to be closed quietly.
     * @param statement
     *            The Statement to be closed quietly.
     */
    public static void close(final Connection connection,
            final Statement statement)
    {
        close(statement);
        close(connection);
    }

    /**
     * Quietly close the Connection, Statement and ResultSet. Any errors will be printed to the stderr.
     * 
     * @param connection
     *            The Connection to be closed quietly.
     * @param statement
     *            The Statement to be closed quietly.
     * @param resultSet
     *            The ResultSet to be closed quietly.
     */
    public static void close(final Connection connection,
            final Statement statement, final ResultSet resultSet)
    {
        close(resultSet);
        close(statement);
        close(connection);
    }

    /**
     * Quietly close Statement and ResultSet. Any errors will be printed to the stderr.
     * 
     * @param statement
     *            The Statement to be closed quietly.
     * @param resultSet
     *            The ResultSet to be closed quietly.
     */
    public static void close(final Statement statement,
            final ResultSet resultSet)
    {
        close(resultSet);
        close(statement);
    }

    /**
     * Returns the Long value corresponding to the given field on the given ResultSet. If the value cannot be parsed to
     * Long, or does not exist, it returns a null value.
     * 
     * @param rs
     *            ResultSet
     * @param field
     *            Field we want to obtain the value from
     * @return Long value if the field exists and can be parsed to Long. Null otherwise.
     */
    public static Long getLongFromResultSet(final ResultSet rs,
            final String field)
    {

        Long result = null;

        try
        {
            final Object value = rs.getObject(field);
            if (value != null)
                result = (Long) value;
        }
        catch (final Exception e)
        {
        }
        return result;
    }

    /**
     * Returns a string list corresponding to the given field on the given ResultSet. If the value cannot be parsed to
     * an array, or does not exist, it returns an empty value.
     * 
     * @param rs
     *            ResultSet
     * @param field
     *            Field we want to obtain the value from
     * @return Long value if the field exists and can be parsed to Long. Null otherwise.
     */
    public static List<String> getArrayFromResultSet(final ResultSet rs,
            final String field)
    {

        List<String> result;

        try
        {
            final Array arrayChunks = rs.getArray(field);
            final String[] chunks = (String[]) arrayChunks.getArray();
            result = Arrays.asList(chunks);

            if (result.contains(null))
                result = new ArrayList<String>();
        }
        catch (final Exception e)
        {
            result = new ArrayList<String>();
        }

        return result;
    }

    public static Integer getIntFromResultSet(final ResultSet rs,
            final String field)
    {

        Integer result = null;

        try
        {
            final Object value = rs.getObject(field);
            if (value != null)
                result = (Integer) value;
        }
        catch (final Exception e)
        {
        }
        return result;
    }

    // ===
    // ===

    public static Product getProduct(final ResultSet result) throws SQLException
    {

        final Product product = new Product();

        product.setId(result.getLong("product_id"));
        product.setDescription(result.getString("product_description"));
        product.setBrand(result.getString("product_brand"));
        product.setCountry(result.getString("product_country"));

        product.setClusterNumber(
                ((result.getString("cluster_number") == null) ? ""
                        : result.getString("cluster_number")));

        product.setComment(((result.getString("product_comment") == null) ? ""
                : result.getString("product_comment")));
        product.setManufacturer(result.getString("product_manufacturer"));

        product.setCnfCode(((result.getString("cnf_code") == null) ? ""
                : result.getString("cnf_code")));

        product.setCreationDate(result.getTimestamp("creation_date"));
        product.setLastEditDate(result.getTimestamp("last_edit_date"));
        product.setEditedBy(result.getString("edited_by"));

        /// Maybe needed only if returning these values.
        product.setRestaurantType(result.getString("restaurant_type"));
        product.setType(result.getString("type"));

        return product;
    }

    public static ProductResponse getProductResponse(final ResultSet resultSet)
            throws SQLException
    {
        final Product product = getProduct(resultSet);
        final ProductResponse productResponse = new ProductResponse(product);

        productResponse.setClassification_number(
                ((resultSet.getString("classification_number") == null) ? ""
                        : resultSet.getString("classification_number")));
        productResponse.setClassification_name(
                ((resultSet.getString("classification_name") == null) ? ""
                        : resultSet.getString("classification_name")));
        productResponse.setClassification_type(
                ((resultSet.getString("classification_type") == null) ? ""
                        : resultSet.getString("classification_type")));

        return productResponse;
    }

    // NftRequest nftRequest
    public static Map<String, Object> getQueryMap(final NftRequest nftRequest)
    {

        final Map<String, Object> queryMap = new HashMap<String, Object>();

        for (final NftModel element : nftRequest.getNft())
        {

            if (element.getName().isEmpty())
            {

                System.out.println("name of invalid " + element.getName()
                        + " amount" + element.getAmount());

                queryMap.put("inputError", ResponseCodes.INVALID_INPUT_FIELDS);
                return queryMap;

            }
            if (((element.getAmount() == null)
                    && (element.getUnit_of_measure()!=null && !element.getUnit_of_measure().isEmpty()))
                    || ((element.getAmount() != null)
                            && (element.getUnit_of_measure()==null) && element.getUnit_of_measure().isEmpty()))
            {

//                System.out.println("name of invalid " + element.getName()
//                        + " amount" + element.getAmount());
                
                queryMap.put("inputError", ResponseCodes.INVALID_INPUT_FIELDS);
                return queryMap;

            }
            queryMap.put(element.getName(), "placeholder");
        }

        if (!queryMap.isEmpty() && (nftRequest.getFlag() == true))
        {
            if (!queryMap.containsKey("Fat"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Energy"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Energy KJ"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Saturated Fat"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Trans Fat"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Omega-6 Polyunsaturated Fat"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Omega-3 Polyunsaturated Fat"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Carbohydrates"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Fibre"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Insoluble Fibre"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Sugar"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Sugar Alcohols"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Starch"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Protein"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Cholesterol"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Sodium"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
            if (!queryMap.containsKey("Saturated + Trans Fat"))
            {

                queryMap.put("inputError",
                        ResponseCodes.MISSING_MANDATORY_FIELDS);
                return queryMap;
            }
        }
        // queryMap.key
        return queryMap;

    }

    public static Map<String, Object> getQueryMap(
            final PackageInsertRequest request)
    {

        final Map<String, Object> queryMap = new HashMap<String, Object>();
        final List<Object> labelInsertList = new ArrayList<Object>();

        if (!request.getPackage_description().isEmpty())
            queryMap.put("package_description",
                    request.getPackage_description());
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);
            return queryMap;
        }
        labelInsertList.add(request.getPackage_description());

        if (!request.getPackage_upc().isEmpty())
            queryMap.put("package_upc", request.getPackage_upc());
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);
            return queryMap;
        }
        labelInsertList.add(request.getPackage_upc());

        if (!request.getPackage_brand().isEmpty())
            queryMap.put("package_brand", request.getPackage_brand());

        labelInsertList.add(request.getPackage_brand());

        if (!request.getPackage_manufacturer().isEmpty())
            queryMap.put("package_manufacturer",
                    request.getPackage_manufacturer());

        labelInsertList.add(request.getPackage_manufacturer());

        if (!request.getPackage_country().isEmpty())
            queryMap.put("package_country", request.getPackage_country());

        labelInsertList.add(request.getPackage_country());

        if (request.getPackage_size() != null)
            queryMap.put("package_size", request.getPackage_size());

        labelInsertList.add(request.getPackage_size());

        if (!request.getPackage_size_unit_of_measure().isEmpty())
            queryMap.put("package_size_unit_of_measure",
                    request.getPackage_size_unit_of_measure());

        labelInsertList.add(request.getPackage_size_unit_of_measure());

        if (!request.getStorage_type().isEmpty())
            queryMap.put("storage_type", request.getStorage_type());

        labelInsertList.add(request.getStorage_type());

        if (!request.getStorage_statements().isEmpty())
            queryMap.put("storage_statements", request.getStorage_statements());

        labelInsertList.add(request.getStorage_statements());

        if (!request.getHealth_claims().isEmpty())
            queryMap.put("health_claims", request.getHealth_claims());

        labelInsertList.add(request.getHealth_claims());

        if (!request.getOther_package_statements().isEmpty())
            queryMap.put("other_package_statements",
                    request.getOther_package_statements());

        labelInsertList.add(request.getOther_package_statements());

        if (!request.getSuggested_directions().isEmpty())
            queryMap.put("suggested_directions",
                    request.getSuggested_directions());

        labelInsertList.add(request.getSuggested_directions());

        if (!request.getIngredients().isEmpty())
            queryMap.put("ingredients", request.getIngredients());

        labelInsertList.add(request.getIngredients());

        if (request.getMulti_part_flag() != null)
            queryMap.put("multi_part_flag", request.getMulti_part_flag());

        labelInsertList.add(request.getMulti_part_flag());

        if (!request.getNutrition_fact_table().isEmpty())
            queryMap.put("nutrition_fact_table",
                    request.getNutrition_fact_table());

        labelInsertList.add(request.getNutrition_fact_table());

        if (request.getAs_prepared_per_serving_amount() != null)
            queryMap.put("as_prepared_per_serving_amount",
                    request.getAs_prepared_per_serving_amount());

        labelInsertList.add(request.getAs_prepared_per_serving_amount());

        if (!request.getAs_prepared_unit_of_measure().isEmpty())
            queryMap.put("as_prepared_unit_of_measure",
                    request.getAs_prepared_unit_of_measure());

        labelInsertList.add(request.getAs_prepared_unit_of_measure());

        if (request.getAs_sold_per_serving_amount() != null)
            queryMap.put("as_sold_per_serving_amount",
                    request.getAs_sold_per_serving_amount());

        labelInsertList.add(request.getAs_sold_per_serving_amount());

        if (!request.getAs_sold_unit_of_measure().isEmpty())
            queryMap.put("as_sold_unit_of_measure",
                    request.getAs_sold_unit_of_measure());

        labelInsertList.add(request.getAs_sold_unit_of_measure());

        if (request.getAs_prepared_per_serving_amount_in_grams() != null)
            queryMap.put("as_prepared_per_serving_amount_in_grams",
                    request.getAs_prepared_per_serving_amount_in_grams());

        labelInsertList.add(
                request.getAs_prepared_per_serving_amount_in_grams());

        if (request.getAs_sold_per_serving_amount_in_grams() != null)
            queryMap.put("as_sold_per_serving_amount_in_grams",
                    request.getAs_sold_per_serving_amount_in_grams());

        labelInsertList.add(request.getAs_sold_per_serving_amount_in_grams());

        if (!request.getPackage_comment().isEmpty())
            queryMap.put("package_comment", request.getPackage_comment());

        labelInsertList.add(request.getPackage_comment());

        if (!request.getPackage_source().isEmpty())
            queryMap.put("package_source", request.getPackage_source());
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);
            return queryMap;
        }

        labelInsertList.add(request.getPackage_source());

        if (!request.getPackage_product_description().isEmpty())
            queryMap.put("package_product_description",
                    request.getPackage_product_description());

        labelInsertList.add(request.getPackage_product_description());

        if (request.getNumber_of_units() != null)
            queryMap.put("number_of_units", request.getNumber_of_units());

        labelInsertList.add(request.getNumber_of_units());

        if (request.getInformed_dining_program() != null)
            queryMap.put("informed_dining_program",
                    request.getInformed_dining_program());

        labelInsertList.add(request.getInformed_dining_program());

        if (request.getProduct_grouping() != null)
            queryMap.put("product_grouping", request.getProduct_grouping());

        labelInsertList.add(request.getProduct_grouping());

        if (request.getNielsen_item_rank() != null)
            queryMap.put("nielsen_item_rank", request.getNielsen_item_rank());

        labelInsertList.add(request.getNielsen_item_rank());

        if (!request.getNutrient_claims().isEmpty())
            queryMap.put("nutrient_claims", request.getNutrient_claims());

        labelInsertList.add(request.getNutrient_claims());

        if (!request.getPackage_nielsen_category().isEmpty())
            queryMap.put("package_nielsen_category",
                    request.getPackage_nielsen_category());

        labelInsertList.add(request.getPackage_nielsen_category());

        if (!request.getCommon_household_measure().isEmpty())
            queryMap.put("common_household_measure",
                    request.getCommon_household_measure());

        labelInsertList.add(request.getCommon_household_measure());

        if (request.getChild_item() != null)
            queryMap.put("child_item", request.getChild_item());

        labelInsertList.add(request.getChild_item());

        if (!request.getClassification_name().isEmpty())
            queryMap.put("package_classification_name",
                    request.getClassification_name());

        labelInsertList.add(request.getClassification_name());

        if (request.getEdited_by() != null)
            queryMap.put("edited_by", request.getEdited_by());

        labelInsertList.add(request.getEdited_by());

        if (request.getClassification_number() != null)
            queryMap.put("package_classification_number",
                    request.getClassification_number());

        labelInsertList.add(request.getClassification_number());

        if (request.getProduct_id() != null)
        {
            if (isType(request.getProduct_id().toString(), "int"))
            {
                if (request.getProduct_id() > 0)
                    queryMap.put("package_product_id_fkey",
                            request.getProduct_id());
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);
            return queryMap;
        }
        labelInsertList.add(request.getProduct_id());

        if (!request.getPackage_collection_date().isEmpty())
        {
            queryMap.put("package_collection_date",
                    request.getPackage_collection_date());
            labelInsertList.add(
                    Date.valueOf(request.getPackage_collection_date()));
        }
        else
            labelInsertList.add(null);

        if (!request.getPackage_collection_date().isEmpty())
        {
            queryMap.put("nft_last_update_date",
                    request.getPackage_collection_date());
            labelInsertList.add(
                    Date.valueOf(request.getPackage_collection_date()));
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);
            return queryMap;
        }

        final Timestamp now = DateUtil.getCurrentTimeStamp();
        queryMap.put("creation_date", now);
        labelInsertList.add(now);
        queryMap.put("last_edit_date", now);
        labelInsertList.add(now);

        if (request.getCalculated() != null)
            queryMap.put("calculated", request.getCalculated());

        labelInsertList.add(request.getCalculated());
        queryMap.put("package_insert_list", labelInsertList);

        return queryMap;
    }

    ///
    
    public static List<Object> getQueryMap(
            final ImportLabelRequest request, Integer product_id)
    {
    	  final List<Object> labelInsertList = new ArrayList<Object>();
    	  
    	  labelInsertList.add(request.getPackage_description());
    	  labelInsertList.add(request.getPackage_upc());
    	  labelInsertList.add(request.getPackage_brand())
    	  ;
    	  labelInsertList.add(request.getPackage_manufacturer());
    	  labelInsertList.add(request.getPackage_country());
    	  labelInsertList.add(request.getPackage_size());
    	  
    	  labelInsertList.add(request.getPackage_size_unit_of_measure());
    	  labelInsertList.add(request.getStorage_type());
    	  labelInsertList.add(request.getStorage_statements());
    	  labelInsertList.add(request.getHealth_claims());
    	  
    	  
    	  labelInsertList.add(request.getOther_package_statements());
    	  labelInsertList.add(request.getSuggested_directions());
    	  labelInsertList.add(request.getIngredients());
    	  labelInsertList.add(request.getMulti_part_flag());
    	  labelInsertList.add(request.getNutrition_fact_table());
    	  labelInsertList.add(request.getAs_prepared_per_serving_amount());
    	  labelInsertList.add(request.getAs_prepared_unit_of_measure());
    	  labelInsertList.add(request.getAs_sold_per_serving_amount());
    	  labelInsertList.add(request.getAs_sold_unit_of_measure());
    	  labelInsertList.add(request.getAs_prepared_per_serving_amount_in_grams());
    	  labelInsertList.add(request.getAs_sold_per_serving_amount_in_grams());
    	  labelInsertList.add(request.getPackage_comment());
    	  labelInsertList.add(request.getPackage_source());
    	  labelInsertList.add(request.getPackage_product_description());
    	  labelInsertList.add(request.getNumber_of_units());
    	  labelInsertList.add(request.getInformed_dining_program());
    	  labelInsertList.add(request.getProduct_grouping());
    	  labelInsertList.add(request.getNielsen_item_rank());
    	  labelInsertList.add(request.getNutrient_claims());
    	  labelInsertList.add(request.getPackage_nielsen_category());
    	  labelInsertList.add(request.getCommon_household_measure());
    	  labelInsertList.add(request.getChild_item());
    	  labelInsertList.add(request.getClassification_name());
    	  labelInsertList.add(null); //edited by
    	  labelInsertList.add(request.getClassification_number());
    	  labelInsertList.add(product_id);
    	  labelInsertList.add(Date.valueOf(request.getPackage_collection_date()));
    	  labelInsertList.add(request.getNft_last_update_date());
    	  final Timestamp now = DateUtil.getCurrentTimeStamp();
    	  labelInsertList.add(now); //creation date
    	  labelInsertList.add(now);//last edit  date
    	  labelInsertList.add(request.getCalculated());
    	  
    	
    	
    	return labelInsertList;
    	
    }

    public static Map<String, Object> getQueryMap(
            final PackageUpdateRequest request)
    {

        final Map<String, Object> queryMap = new HashMap<String, Object>();
        final List<Object> labelUpdateList = new ArrayList<Object>();

        System.out.println(request.toString());
        if (!request.getPackage_description().isEmpty())
            queryMap.put("package_description",
                    request.getPackage_description());
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);
            return queryMap;
        }
        labelUpdateList.add(request.getPackage_description());

        if (request.getPackage_brand() != null)
            queryMap.put("package_brand", request.getPackage_brand());

        labelUpdateList.add(request.getPackage_brand());

        if (request.getPackage_manufacturer() != null)
            queryMap.put("package_manufacturer",
                    request.getPackage_manufacturer());

        labelUpdateList.add(request.getPackage_manufacturer());

        if (request.getPackage_country() != null)
            queryMap.put("package_country", request.getPackage_country());

        labelUpdateList.add(request.getPackage_country());

        if (request.getPackage_size() != null)
            queryMap.put("package_size", request.getPackage_size());

        labelUpdateList.add(request.getPackage_size());

        if (request.getPackage_size_unit_of_measure() != null)
            queryMap.put("package_size_unit_of_measure",
                    request.getPackage_size_unit_of_measure());

        labelUpdateList.add(request.getPackage_size_unit_of_measure());

        if (request.getStorage_type() != null)
            queryMap.put("storage_type", request.getStorage_type());

        labelUpdateList.add(request.getStorage_type());

        if (request.getStorage_statements() != null)
            queryMap.put("storage_statements", request.getStorage_statements());

        labelUpdateList.add(request.getStorage_statements());

        if (request.getHealth_claims() != null)
            queryMap.put("health_claims", request.getHealth_claims());

        labelUpdateList.add(request.getHealth_claims());

        if (request.getOther_package_statements() != null)
            queryMap.put("other_package_statements",
                    request.getOther_package_statements());

        labelUpdateList.add(request.getOther_package_statements());

        if (request.getSuggested_directions() != null)
            queryMap.put("suggested_directions",
                    request.getSuggested_directions());

        labelUpdateList.add(request.getSuggested_directions());

        if (request.getIngredients() != null)
            queryMap.put("ingredients", request.getIngredients());

        labelUpdateList.add(request.getIngredients());

        if (request.getMulti_part_flag() != null)
            queryMap.put("multi_part_flag", request.getMulti_part_flag());

        labelUpdateList.add(request.getMulti_part_flag());

        if (request.getNutrition_fact_table() != null)
            queryMap.put("nutrition_fact_table",
                    request.getNutrition_fact_table());

        labelUpdateList.add(request.getNutrition_fact_table());

        if (request.getAs_prepared_per_serving_amount() != null)
            queryMap.put("as_prepared_per_serving_amount",
                    request.getAs_prepared_per_serving_amount());

        labelUpdateList.add(request.getAs_prepared_per_serving_amount());

        if (request.getAs_prepared_unit_of_measure() != null)
            queryMap.put("as_prepared_unit_of_measure",
                    request.getAs_prepared_unit_of_measure());

        labelUpdateList.add(request.getAs_prepared_unit_of_measure());

        if (request.getAs_sold_per_serving_amount() != null)
            queryMap.put("as_sold_per_serving_amount",
                    request.getAs_sold_per_serving_amount());

        labelUpdateList.add(request.getAs_sold_per_serving_amount());

        if (request.getAs_sold_unit_of_measure() != null)
            queryMap.put("as_sold_unit_of_measure",
                    request.getAs_sold_unit_of_measure());

        labelUpdateList.add(request.getAs_sold_unit_of_measure());

        if (request.getAs_prepared_per_serving_amount_in_grams() != null)
            queryMap.put("as_prepared_per_serving_amount_in_grams",
                    request.getAs_prepared_per_serving_amount_in_grams());

        labelUpdateList.add(
                request.getAs_prepared_per_serving_amount_in_grams());

        if (request.getAs_sold_per_serving_amount_in_grams() != null)
            queryMap.put("as_sold_per_serving_amount_in_grams",
                    request.getAs_sold_per_serving_amount_in_grams());

        labelUpdateList.add(request.getAs_sold_per_serving_amount_in_grams());

        if (request.getPackage_comment() != null)
            queryMap.put("package_comment", request.getPackage_comment());

        labelUpdateList.add(request.getPackage_comment());

        if (request.getPackage_source() != null)
            queryMap.put("package_source", request.getPackage_source());
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);
            return queryMap;
        }

        labelUpdateList.add(request.getPackage_source());

        if (request.getPackage_product_description() != null)
            queryMap.put("package_product_description",
                    request.getPackage_product_description());

        labelUpdateList.add(request.getPackage_product_description());

        if (request.getNumber_of_units() != null)
            queryMap.put("number_of_units", request.getNumber_of_units());

        labelUpdateList.add(request.getNumber_of_units());

        if (request.getInformed_dining_program() != null)
            queryMap.put("informed_dining_program",
                    request.getInformed_dining_program());

        labelUpdateList.add(request.getInformed_dining_program());

        if (request.getProduct_grouping() != null)
            queryMap.put("product_grouping", request.getProduct_grouping());

        labelUpdateList.add(request.getProduct_grouping());

        if (request.getNielsen_item_rank() != null)
            queryMap.put("nielsen_item_rank", request.getNielsen_item_rank());

        labelUpdateList.add(request.getNielsen_item_rank());

        if (request.getNutrient_claims() != null)
            queryMap.put("nutrient_claims", request.getNutrient_claims());

        labelUpdateList.add(request.getNutrient_claims());

        if (request.getPackage_nielsen_category() != null)
            queryMap.put("package_nielsen_category",
                    request.getPackage_nielsen_category());

        labelUpdateList.add(request.getPackage_nielsen_category());

        if (request.getCommon_household_measure() != null)
            queryMap.put("common_household_measure",
                    request.getCommon_household_measure());

        labelUpdateList.add(request.getCommon_household_measure());

        if (request.getChild_item() != null)
            queryMap.put("child_item", request.getChild_item());

        labelUpdateList.add(request.getChild_item());

        if (request.getClassification_name() != null)
            queryMap.put("package_classification_name",
                    request.getClassification_name());

        labelUpdateList.add(request.getClassification_name());

        if (request.getEdited_by() != null)
            queryMap.put("edited_by", request.getEdited_by());

        labelUpdateList.add(request.getEdited_by());

        if (request.getClassification_number() != null)
            queryMap.put("package_classification_number",
                    request.getClassification_number());

        labelUpdateList.add(request.getClassification_number());

        if (request.getPackage_collection_date() != null)
        {
            queryMap.put("package_collection_date",
                    request.getPackage_collection_date());
            labelUpdateList.add(
                    Date.valueOf(request.getPackage_collection_date()));
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);
            return queryMap;
        }

        if (request.getNft_last_update_date() != null)
        {
            queryMap.put("nft_last_update_date",
                    request.getNft_last_update_date());
            labelUpdateList.add(
                    Date.valueOf(request.getNft_last_update_date()));
        }
        else
            labelUpdateList.add(null);
        // queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);
        // return queryMap;

        final Timestamp now = DateUtil.getCurrentTimeStamp();

        queryMap.put("last_edit_date", now);
        labelUpdateList.add(now);

        if (request.getCalculated() != null)
            queryMap.put("calculated", request.getCalculated());
        labelUpdateList.add(request.getCalculated());
        if (request.getPackage_id() != null)
        {
            if (isType(request.getPackage_id().toString(), "int"))
            {
                if (request.getPackage_id() > 0)
                    queryMap.put("package_id", request.getPackage_id());
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);
            return queryMap;
        }
        labelUpdateList.add(request.getPackage_id());

        queryMap.put("package_update_list", labelUpdateList);

        return queryMap;
    }

    ///
    public static Map<String, Object> getQueryMap(final ProductRequest request)
    {
        final Map<String, Object> queryMap = new HashMap<String, Object>();

        if (!request.product_manufacturer.isEmpty())
            queryMap.put("product_manufacturer", request.product_manufacturer);
        if (!request.product_brand.isEmpty())
            queryMap.put("product_brand", request.product_brand);

        if (request.cnf_code != null)
        {
            if (isType(request.cnf_code.toString(), "int"))
            {
                if (request.cnf_code > 0)
                    queryMap.put("cnf_code", request.cnf_code);
            }
            else
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);

        if (request.cluster_number != null)
        {
        			if (request.cluster_number > 0.0)
                    queryMap.put("cluster_number", request.cluster_number.intValue());
        
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);

        if (!request.product_description.isEmpty())
            queryMap.put("product_description", request.product_description);
        if (!request.product_comment.isEmpty())
            queryMap.put("product_comment", request.product_comment);

        if (!request.classification_number.isEmpty())
        {
                    queryMap.put("classification_number",
                            request.classification_number);
        }
        if (!request.classification_name.isEmpty())
            queryMap.put("classification_name", request.classification_name);
        if (!request.classification_type.isEmpty())
            queryMap.put("classification_type", request.classification_type);

        if (request.offset != null)
        {
            if (!isType(request.offset.toString(), "int"))
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);

        if (!request.restaurant_type.isEmpty())
            queryMap.put("restaurant_type", request.restaurant_type);
        if (!request.type.isEmpty())
            queryMap.put("type", request.type);

        return queryMap;
    }

    // ===

    public static Map<String, Object> getQueryMap(
            final ProductInsertRequest request)
    {

        final Map<String, Object> queryMap = new HashMap<String, Object>();
        final List<Object> productInsertList = new ArrayList<Object>();

        if (request.getProduct_manufacturer() != null)
            queryMap.put("product_manufacturer",
                    request.getProduct_manufacturer());
        productInsertList.add(request.getProduct_manufacturer());

        if (request.getProduct_brand() != null)
            queryMap.put("product_brand", request.getProduct_brand());

        productInsertList.add(request.getProduct_brand());

        if (request.getCnf_code() != null)
            if (request.getCnf_code() instanceof Number)
                queryMap.put("cnf_code", request.getCnf_code());
            else
                queryMap.put("inputError", ResponseCodes.INVALID_INPUT_FIELDS);
        productInsertList.add(request.getCnf_code());

        if (request.getCluster_number() != null)
            if (request.getCluster_number() instanceof Number)
                queryMap.put("cluster_number", request.getCluster_number());
            else
                queryMap.put("inputError", ResponseCodes.INVALID_INPUT_FIELDS);
        productInsertList.add(request.getCluster_number());

        if (request.getProduct_description() != null)
            queryMap.put("product_description",
                    request.getProduct_description());
        else
            queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);

        productInsertList.add(request.getProduct_description());

        if (request.getProduct_comment() != null)
            queryMap.put("product_comment", request.getProduct_comment());

        productInsertList.add(request.getProduct_comment());

        if (request.getType() != null)
            queryMap.put("type", request.getType());

        productInsertList.add(request.getType());

        if (request.getRestaurant_type() != null)
            queryMap.put("restaurant_type", request.getRestaurant_type());

        productInsertList.add(request.getRestaurant_type());

        final Timestamp now = DateUtil.getCurrentTimeStamp();

        productInsertList.add(now);
        productInsertList.add(now);

        if (request.getClassification_number() != null)
                queryMap.put("classification_number",
                        request.getClassification_number());


        queryMap.put("product_insert_list", productInsertList);

        return queryMap;
    }

    public static ProductClassificationResponse getProductClassificationResponse(
            final ResultSet resultSet) throws SQLException
    {
        final Product product = getProduct(resultSet);
        final ProductClassificationResponse productClassificationResponse = new ProductClassificationResponse(
                product);

        productClassificationResponse.setClassification_number(
                ((resultSet.getString("classification_number") == null) ? ""
                        : resultSet.getString("classification_number")));
        productClassificationResponse.setClassification_name(
                ((resultSet.getString("classification_name") == null) ? ""
                        : resultSet.getString("classification_name")));
        productClassificationResponse.setClassification_type(
                ((resultSet.getString("classification_type") == null) ? ""
                        : resultSet.getString("classification_type")));

        return productClassificationResponse;
    }

    // ===

    public static Sales getSales(final ResultSet result) throws SQLException
    {
        final Sales sales = new Sales();

        sales.setId(result.getLong("sales_id"));
        sales.setDescription(result.getString("sales_description"));
        sales.setUpc(result.getString("sales_upc"));
        sales.setBrand(result.getString("sales_brand"));
        sales.setManufacturer(result.getString("sales_manufacturer"));

        final Double dollar_rank = result.getDouble("dollar_rank");
        sales.setDollarRank(result.wasNull() ? null : dollar_rank);

        final Double dollar_volume = result.getDouble("dollar_volume");
        sales.setDollarVolume(result.wasNull() ? null : dollar_volume);

        final Double dollar_share = result.getDouble("dollar_share");
        sales.setDollarShare(result.wasNull() ? null : dollar_share);

        final Double dollar_volume_percentage_change = result.getDouble(
                "dollar_volume_percentage_change");
        sales.setDollarVolumePercentageChange(
                result.wasNull() ? null : dollar_volume_percentage_change);

        final Double kilo_volume = result.getDouble("kilo_volume");
        sales.setKiloVolume(result.wasNull() ? null : kilo_volume);

        final Double kilo_share = result.getDouble("kilo_share");
        sales.setKiloShare(result.wasNull() ? null : kilo_share);

        final Double kilo_volume_percentage_change = result.getDouble(
                "kilo_volume_percentage_change");
        sales.setKiloVolumePercentageChange(
                result.wasNull() ? null : kilo_volume_percentage_change);

        final Double average_ac_dist = result.getDouble("average_ac_dist");
        sales.setAverageAcDist(result.wasNull() ? null : average_ac_dist);

        // sales.setAverageAcDist(result.getDouble("average_ac_dist"));
        // sales.setAverageRetailPrice(result.getDouble("average_retail_price"));

        final Double average_retail_price = result.getDouble(
                "average_retail_price");
        sales.setAverageRetailPrice(
                result.wasNull() ? null : average_retail_price);

        sales.setSalesSource(result.getString("sales_source"));
        sales.setNielsenCategory(result.getString("nielsen_category"));

        final String sales_year = result.getString("sales_year");
        sales.setSalesYear(result.wasNull() ? null : sales_year);

        final Boolean control_label_flag = result.getBoolean(
                "control_label_flag");
        sales.setControlLabelFlag(result.wasNull() ? null : control_label_flag);

        final Double kilo_volume_total = result.getDouble("kilo_volume_total");
        sales.setKiloVolumeTotal(result.wasNull() ? null : kilo_volume_total);

        final Double kilo_volume_rank = result.getDouble("kilo_rank");
        sales.setKiloVolumeRank(result.wasNull() ? null : kilo_volume_rank);

        final Double dollar_volume_total = result.getDouble(
                "dollar_volume_total");
        sales.setDollarVolumeTotal(
                result.wasNull() ? null : dollar_volume_total);

        final Double cluster_number = result.getDouble("cluster_number");
        sales.setClusterNumber(result.wasNull() ? null : cluster_number);

        final Double product_grouping = result.getDouble("product_grouping");
        sales.setProductGrouping(result.wasNull() ? null : product_grouping);

        sales.setSalesProductDescription(
                result.getString("sales_product_description"));


        sales.setClassificationNumber(result.getString(
                "classification_number"));

        sales.setClassificationType(result.getString("classification_type"));
        sales.setSalesComment(result.getString("sales_comment"));

        sales.setSalesCollectionDate(result.getString("sales_collection_date"));

        final Integer number_of_units = result.getInt("number_of_units");

        sales.setNumberOfUnits((result.wasNull() ? null : number_of_units));

        sales.setCreationDate(result.getTimestamp("creation_date"));
        sales.setLastEditDate(result.getTimestamp("last_edit_date"));
        sales.setEditedBy(result.getString("edited_by"));
        sales.setProductId(result.getLong("sales_product_id_fkey"));

        return sales;
    }

    public static SalesResponseShort getSalesResponseShort(
            final ResultSet resultSet) throws SQLException
    {
        final Sales sales = getSales(resultSet);
        final SalesResponseShort salesResponseShort = new SalesResponseShort(
                sales);

        return salesResponseShort;
    }

    public static SalesResponse getSalesResponse(final ResultSet resultSet)
            throws SQLException
    {

        final Sales sales = getSales(resultSet);
        final SalesResponse salesResponse = new SalesResponse(sales);

        return salesResponse;

    }

    public static Map<String, Object> getQueryMap(final SalesRequest request)
    {
        final Map<String, Object> queryMap = new HashMap<String, Object>();

        if (!request.salesUpc.isEmpty())
            if (StringUtilities.isNumeric(request.salesUpc))
                queryMap.put("sales_upc", request.salesUpc);
            else
                queryMap.put("inputError", ResponseCodes.INVALID_UPC);
        if (!request.salesDescription.isEmpty())
            queryMap.put("sales_description", request.salesDescription);
        if (!request.salesSource.isEmpty())
            queryMap.put("sales_source", request.salesSource);

        if (request.salesYear != null)
            if (isType(request.salesYear.toString(), "int"))
            {
                if (request.salesYear > 0)
                    queryMap.put("sales_year", request.salesYear);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
                return queryMap;
            }

        if (!request.nielsenCategory.isEmpty())
            queryMap.put("nielsen_category", request.nielsenCategory);
        if (!request.salesComment.isEmpty())
            queryMap.put("sales_comment", request.salesComment);

        
//        if (DateUtil.validateDates(request.collectionDateFrom,
//                request.collectionDateTo))
//        {
//            if (!request.collectionDateFrom.isEmpty()
//                    && !request.collectionDateTo.isEmpty())
//            {
//                queryMap.put("collection_date_from",
//                        request.collectionDateFrom);
//                queryMap.put("collection_date_to", request.collectionDateTo);
//            }
//        }
//        else
//            queryMap.put("inputError", ResponseCodes.INVALID_DATE);
        
        if(request.collectionDateFrom != null && request.collectionDateTo != null){
        if (DateUtil.validateDates(request.collectionDateFrom,
                request.collectionDateTo))
        {
            if (!request.collectionDateFrom.isEmpty()
                    && !request.collectionDateTo.isEmpty())
            {
                queryMap.put("collection_date_from",
                        request.collectionDateFrom);
                queryMap.put("collection_date_to", request.collectionDateTo);
            }
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_DATE);
    }else if (request.collectionDateFrom != null && request.collectionDateTo == null){
        queryMap.put("inputError", ResponseCodes.INVALID_DATE);

    }else if (request.collectionDateFrom == null && request.collectionDateTo != null){
        queryMap.put("inputError", ResponseCodes.INVALID_DATE);

    }

        if (request.offset != null)
        {
            if (!isType(request.offset.toString(), "int"))
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);

        return queryMap;
    }

    public static Map<String, Object> getQueryMap(
            final SalesInsertRequest request)
    {
        final Map<String, Object> queryMap = new HashMap<String, Object>();
        final List<Object> salesInsertList = new ArrayList<Object>();

        if (!request.sales_description.isEmpty())
            queryMap.put("sales_description", request.sales_description);
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_SALES_DESCRIPTION);
            return queryMap;
        }
        salesInsertList.add(request.sales_description);

        if (!request.sales_upc.isEmpty())
            if (StringUtilities.isNumeric(request.sales_upc))
                queryMap.put("sales_upc", request.sales_upc);
            else
                queryMap.put("inputError", ResponseCodes.INVALID_UPC);
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_SALES_UPC);
            return queryMap;
        }
        salesInsertList.add(request.sales_upc);

        if (!request.sales_brand.isEmpty())
            queryMap.put("sales_brand", request.sales_brand);
        salesInsertList.add(request.sales_brand);

        if (!request.sales_manufacturer.isEmpty())
            queryMap.put("sales_manufacturer", request.sales_manufacturer);
        salesInsertList.add(request.sales_manufacturer);

        if (request.dollar_rank != null)
            if (request.dollar_rank instanceof Number)
                queryMap.put("dollar_rank", request.dollar_rank);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesInsertList.add(request.dollar_rank);

        if (request.dollar_volume != null)
        {

            if (request.dollar_volume instanceof Number)
                queryMap.put("dollar_volume", request.dollar_volume);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_DOLLAR_VOLUME);
            return queryMap;
        }
        salesInsertList.add(request.dollar_volume);

        if (request.dollar_share != null)
        {
            if (request.dollar_share instanceof Number)
                queryMap.put("dollar_share", request.dollar_share);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_DOLLAR_SHARE);
            return queryMap;
        }
        salesInsertList.add(request.dollar_share);

        if (request.dollar_volume_percentage_change != null)
        {
            if (request.dollar_volume_percentage_change instanceof Number)
                queryMap.put("dollar_volume_percentage_change",
                        request.dollar_volume_percentage_change);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError",
                    ResponseCodes.MISSING_DOLLAR_VOLUME_PERCENTAGE_CHANGE);
            return queryMap;
        }
        salesInsertList.add(request.dollar_volume_percentage_change);

        if (request.kilo_volume != null)
        {
            if (request.kilo_volume instanceof Number)
                queryMap.put("kilo_volume", request.kilo_volume);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_KILO_VOLUME);
            return queryMap;
        }
        salesInsertList.add(request.kilo_volume);

        if (request.kilo_share != null)
        {
            if (request.kilo_share instanceof Number)
                queryMap.put("kilo_share", request.kilo_share);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_KILO_SHARE);
            return queryMap;
        }
        salesInsertList.add(request.kilo_share);

        if (request.kilo_volume_percentage_change != null)
        {
            if (request.kilo_volume_percentage_change instanceof Number)
                queryMap.put("kilo_volume_percentage_change",
                        request.kilo_volume_percentage_change);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError",
                    ResponseCodes.MISSING_KILO_VOLUME_PERCENTAGE_CHANGE);
            return queryMap;
        }
        salesInsertList.add(request.kilo_volume_percentage_change);

        if (request.average_ac_dist != null)
            if (request.average_ac_dist instanceof Number)
                queryMap.put("average_ac_dist", request.average_ac_dist);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesInsertList.add(request.average_ac_dist);

        if (request.average_retail_price != null)
            if (request.average_retail_price instanceof Number)
                queryMap.put("average_retail_price",
                        request.average_retail_price);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesInsertList.add(request.average_retail_price);

        if (!request.sales_source.isEmpty())
            queryMap.put("sales_source", request.sales_source);
        salesInsertList.add(request.sales_source);

        if (!request.nielsen_category.isEmpty())
            queryMap.put("nielsen_category", request.nielsen_category);
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_NIELSEN_CATEGORY);
            return queryMap;
        }
        salesInsertList.add(request.nielsen_category);

        if (request.sales_year != null)
            if (isType(request.sales_year.toString(), "int"))
            {
                if (request.sales_year > 0)
                    queryMap.put("sales_year", request.sales_year);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
                return queryMap;
            }
        salesInsertList.add(request.sales_year);

        if (request.control_label_flag != null)
            queryMap.put("control_label_flag", request.control_label_flag);
        salesInsertList.add(request.control_label_flag);

        if (request.kilo_volume_total != null)
        {
            if (request.kilo_volume_total instanceof Number)
                queryMap.put("kilo_volume_total", request.kilo_volume_total);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_KILO_VOLUME_TOTAL);
            return queryMap;
        }
        salesInsertList.add(request.kilo_volume_total);

        if (request.kilo_volume_rank != null)
            if (request.kilo_volume_rank instanceof Number)
                queryMap.put("kilo_volume_rank", request.kilo_volume_rank);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesInsertList.add(request.kilo_volume_rank);

        if (request.dollar_volume_total != null)
        {
            if (request.dollar_volume_total instanceof Number)
                queryMap.put("dollar_volume_total",
                        request.dollar_volume_total);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError",
                    ResponseCodes.MISSING_DOLLAR_VOLUME_TOTAL);
            return queryMap;
        }
        salesInsertList.add(request.dollar_volume_total);

        if (request.cluster_number != null)
            if (request.cluster_number instanceof Number)
                queryMap.put("cluster_number", request.cluster_number);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesInsertList.add(request.cluster_number);

        if (request.product_grouping != null)
            if (request.product_grouping instanceof Number)
                queryMap.put("product_grouping", request.product_grouping);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesInsertList.add(request.product_grouping);

        if (!request.sales_product_description.isEmpty())
            queryMap.put("sales_product_description",
                    request.sales_product_description);
        salesInsertList.add(request.sales_product_description);

        if (request.classification_number != null)
                queryMap.put("classification_number",
                        request.classification_number);
         
        salesInsertList.add(request.classification_number);

        if (!request.classification_type.isEmpty())
            queryMap.put("classification_type", request.classification_type);
        salesInsertList.add(request.classification_type);

        if (!request.sales_comment.isEmpty())
            queryMap.put("sales_comment", request.sales_comment);
        salesInsertList.add(request.sales_comment);

        if (request.sales_collection_date != null)
        {
            queryMap.put("sales_collection_date",
                    request.sales_collection_date);
            salesInsertList.add(Date.valueOf(request.sales_collection_date));
        }else{
        	salesInsertList.add(null);
        }
        

        if (request.number_of_units != null)
            if (request.number_of_units instanceof Number)
                queryMap.put("number_of_units", request.number_of_units);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
                return queryMap;
            }
        salesInsertList.add(request.number_of_units);

        if ((request.edited_by != null) && !request.edited_by.isEmpty())
            queryMap.put("edited_by", request.edited_by);
        salesInsertList.add(request.edited_by);

        final Timestamp now = DateUtil.getCurrentTimeStamp();
        queryMap.put("creation_date", now);
        salesInsertList.add(now);
        queryMap.put("last_edit_date", now);
        salesInsertList.add(now);

        System.out.println(request.sales_description + "IS THE ID");
        if (request.product_id != null)
        {
            if (isType(request.product_id.toString(), "int"))
            {
                if (request.product_id > 0)
                    queryMap.put("sales_product_id_fkey", request.product_id);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_PRODUCT_ID);
            return queryMap;
        }
        salesInsertList.add(request.product_id);

        queryMap.put("sales_insert_list", salesInsertList);

        return queryMap;
    }

    // ---

    public static Map<String, Object> getQueryMap(
            final SalesUpdateRequest request)
    {
        final Map<String, Object> queryMap = new HashMap<String, Object>();
        final List<Object> salesUpdateList = new ArrayList<Object>();

        if (request.sales_description != null)
            queryMap.put("sales_description", request.sales_description);
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_SALES_DESCRIPTION);
            return queryMap;
        }
        salesUpdateList.add(request.sales_description);

        if (!request.sales_upc.isEmpty())
            if (StringUtilities.isNumeric(request.sales_upc))
                queryMap.put("sales_upc", request.sales_upc);
            else
                queryMap.put("inputError", ResponseCodes.INVALID_UPC);
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_SALES_UPC);
            return queryMap;
        }
        salesUpdateList.add(request.sales_upc);

        if (request.sales_brand != null)
            queryMap.put("sales_brand", request.sales_brand);
        salesUpdateList.add(request.sales_brand);

        if (request.sales_manufacturer != null)
            queryMap.put("sales_manufacturer", request.sales_manufacturer);
        salesUpdateList.add(request.sales_manufacturer);

        if (request.dollar_rank != null)

            if (request.dollar_rank instanceof Number)
                queryMap.put("dollar_rank", request.dollar_rank);

            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.dollar_rank);

        if (request.dollar_volume != null)
        {

            if (request.dollar_volume instanceof Number)

                queryMap.put("dollar_volume", request.dollar_volume);

            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_DOLLAR_VOLUME);
            return queryMap;
        }
        salesUpdateList.add(request.dollar_volume);

        if (request.dollar_share != null)
        {

            if (request.dollar_share instanceof Number)
                queryMap.put("dollar_share", request.dollar_share);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_DOLLAR_SHARE);
            return queryMap;
        }
        salesUpdateList.add(request.dollar_share);

        if (request.dollar_volume_percentage_change != null)
        {

            if (request.dollar_volume_percentage_change instanceof Number)
                queryMap.put("dollar_volume_percentage_change",
                        request.dollar_volume_percentage_change);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError",
                    ResponseCodes.MISSING_DOLLAR_VOLUME_PERCENTAGE_CHANGE);
            return queryMap;
        }
        salesUpdateList.add(request.dollar_volume_percentage_change);

        if (request.kilo_volume != null)
        {

            if (request.kilo_volume instanceof Number)
                queryMap.put("kilo_volume", request.kilo_volume);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_KILO_VOLUME);
            return queryMap;
        }
        salesUpdateList.add(request.kilo_volume);

        if (request.kilo_share != null)
        {

            if (request.kilo_share instanceof Number)
                queryMap.put("kilo_share", request.kilo_share);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_KILO_SHARE);
            return queryMap;
        }
        salesUpdateList.add(request.kilo_share);

        if (request.kilo_volume_percentage_change != null)
        {

            if (request.kilo_volume_percentage_change instanceof Number)
                queryMap.put("kilo_volume_percentage_change",
                        request.kilo_volume_percentage_change);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError",
                    ResponseCodes.MISSING_KILO_VOLUME_PERCENTAGE_CHANGE);
            return queryMap;
        }
        salesUpdateList.add(request.kilo_volume_percentage_change);

        if (request.average_ac_dist != null)

            if (request.average_ac_dist instanceof Number)
                queryMap.put("average_ac_dist", request.average_ac_dist);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.average_ac_dist);

        if (request.average_retail_price != null)

            if (request.average_retail_price instanceof Number)
                queryMap.put("average_retail_price",
                        request.average_retail_price);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.average_retail_price);

        if (!request.sales_source.isEmpty())
            queryMap.put("sales_source", request.sales_source);
        salesUpdateList.add(request.sales_source);

        if (!request.nielsen_category.isEmpty())
            queryMap.put("nielsen_category", request.nielsen_category);
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_NIELSEN_CATEGORY);
            return queryMap;
        }
        salesUpdateList.add(request.nielsen_category);

        if (request.sales_year != null)

            if (request.sales_year instanceof Number)
                queryMap.put("sales_year", request.sales_year);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
                return queryMap;
            }
        salesUpdateList.add(request.sales_year);

        if (request.control_label_flag != null)
            queryMap.put("control_label_flag", request.control_label_flag);
        salesUpdateList.add(request.control_label_flag);

        if (request.kilo_volume_total != null)
        {

            if (request.kilo_volume_total instanceof Number)
                queryMap.put("kilo_volume_total", request.kilo_volume_total);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_KILO_VOLUME_TOTAL);
            return queryMap;
        }
        salesUpdateList.add(request.kilo_volume_total);

        if (request.kilo_volume_rank != null)

            if (request.kilo_volume_rank instanceof Number)
                queryMap.put("kilo_volume_rank", request.kilo_volume_rank);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.kilo_volume_rank);

        if (request.dollar_volume_total != null)
        {

            if (request.dollar_volume_total instanceof Number)
                queryMap.put("dollar_volume_total",
                        request.dollar_volume_total);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError",
                    ResponseCodes.MISSING_DOLLAR_VOLUME_TOTAL);
            return queryMap;
        }
        salesUpdateList.add(request.dollar_volume_total);

        if (request.cluster_number != null)

            if (request.cluster_number instanceof Number)
                queryMap.put("cluster_number", request.cluster_number);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.cluster_number);

        if (request.product_grouping != null)

            if (request.product_grouping instanceof Number)
                queryMap.put("product_grouping", request.product_grouping);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.product_grouping);

        if (request.sales_product_description != null)
            queryMap.put("sales_product_description",
                    request.sales_product_description);
        salesUpdateList.add(request.sales_product_description);

        if (request.classification_number != null)
                queryMap.put("classification_number",
                        request.classification_number);

        salesUpdateList.add(request.classification_number);

        if (request.classification_type != null)
            queryMap.put("classification_type", request.classification_type);
        salesUpdateList.add(request.classification_type);

        if (request.sales_comment != null)
            queryMap.put("sales_comment", request.sales_comment);
        salesUpdateList.add(request.sales_comment);

        if (request.sales_collection_date != null)
        {
            queryMap.put("sales_collection_date",
                    request.sales_collection_date);
            salesUpdateList.add(Date.valueOf(request.sales_collection_date));
        }else{
        	 salesUpdateList.add(null);
        }
        


        if (request.number_of_units != null)

            if (request.number_of_units instanceof Number)
                queryMap.put("number_of_units", request.number_of_units);
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
                return queryMap;
            }
        salesUpdateList.add(request.number_of_units);
        System.out.print("in the daoUtil" + request.number_of_units);
        if (request.edited_by != null)
            queryMap.put("edited_by", request.edited_by);
        salesUpdateList.add(request.edited_by);

        
        final Timestamp now = DateUtil.getCurrentTimeStamp();
        queryMap.put("last_edit_date", now);
        salesUpdateList.add(now);

        System.out.println("that sales id is omg" + request.sales_id);
        if (request.sales_id != null)
        {
            if (request.sales_id instanceof Number)
            {
                if (request.sales_id > 0)
                    queryMap.put("sales_id", request.sales_id);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
                return queryMap;
            }
        }
        else
        {
            queryMap.put("inputError", ResponseCodes.MISSING_SALES_ID);
            return queryMap;
        }
        salesUpdateList.add(request.sales_id);

        queryMap.put("sales_update_list", salesUpdateList);

        return queryMap;
    }

    // ---

    public static SalesYearsResponse getSalesYearsResponse(
            final ResultSet resultSet) throws SQLException
    {
        final String salesYear = ((resultSet.getString("sales_year") == null)
                ? ""
                : resultSet.getString("sales_year"));

        return new SalesYearsResponse(salesYear);

    }

    // ===

    public static Package getPackage(final ResultSet result) throws SQLException
    {
        final Package _package = new Package();

        _package.setId(result.getLong("package_id"));
        _package.setDescription(result.getString("package_description"));
        _package.setUpc(result.getString("package_upc"));
        _package.setBrand(result.getString("package_brand"));
        _package.setManufacturer(result.getString("package_manufacturer"));
        _package.setSize(result.getDouble("package_size"));
        _package.setSizeUnitOfMeasure(
                result.getString("package_size_unit_of_measure"));
        _package.setStorageType(result.getString("storage_type"));
        _package.setStorageStatements(result.getString("storage_statements"));
        _package.setHealthClaims(result.getString("health_claims"));
        _package.setOtherPackageStatements(
                result.getString("other_package_statements"));
        _package.setSuggestedDirections(
                result.getString("suggested_directions"));
        _package.setIngredients(result.getString("ingredients"));
        _package.setMultiPartFlag(result.getBoolean("multi_part_flag"));
        _package.setNutritionFactTable(
                result.getString("nutrition_fact_table"));
        _package.setAsPreparedPerServingAmount(
                result.getDouble("as_prepared_per_serving_amount"));
        _package.setAsPreparedUnitOfMeasure(
                result.getString("as_prepared_unit_of_measure"));
        _package.setAsSoldPerServingAmount(
                result.getDouble("as_sold_per_serving_amount"));
        _package.setAsSoldUnitOfMeasure(
                result.getString("as_sold_unit_of_measure"));
        _package.setAsPreparedPerServingAmountInGrams(
                result.getDouble("as_prepared_per_serving_amount_in_grams"));
        _package.setAsSoldPerServingAmountInGrams(
                result.getDouble("as_sold_per_serving_amount_in_grams"));
        _package.setPackageComment(result.getString("package_comment"));
        _package.setPackageSource(result.getString("package_source"));
        _package.setPackageCollectionDate(
                result.getDate("package_collection_date"));
        _package.setNumberOfUnits(result.getInt("number_of_units"));
        _package.setCreationDate(result.getTimestamp("creation_date"));
        _package.setLastEditDate(result.getTimestamp("last_edit_date"));
        _package.setEditedBy(result.getString("edited_by"));
        _package.setProductId(result.getLong("package_product_id_fkey"));

        return _package;
    }

    public static PackageResponse getPackageResponse(final ResultSet resultSet)
            throws SQLException
    {
        final Package _package = getPackage(resultSet);
        final PackageResponse packageResponse = new PackageResponse(_package);

        return packageResponse;
    }

    public static PackageViewData getPackageResponseView(
            final ResultSet resultSet) throws SQLException
    {
        final PackageViewData packageResponse = new PackageViewData();
        packageResponse.setPackage_id(resultSet.getInt("package_id"));

        packageResponse.setProduct_id(
                resultSet.getInt("package_product_id_fkey"));

        packageResponse.setPackage_description(
                resultSet.getString("package_description"));

        packageResponse.setPackage_upc(resultSet.getString("package_upc"));
        packageResponse.setPackage_brand(resultSet.getString("package_brand"));

        packageResponse.setPackage_manufacturer(
                resultSet.getString("package_manufacturer"));
        packageResponse.setPackage_country(
                resultSet.getString("package_country"));

        final Double package_size = resultSet.getDouble("package_size");
        packageResponse.setPackage_size(
                resultSet.wasNull() ? null : package_size);

        packageResponse.setPackage_size_unit_of_measure(
                resultSet.getString("package_size_unit_of_measure"));
        packageResponse.setStorage_type(resultSet.getString("storage_type"));
        System.out.println("we are here 5");

        packageResponse.setStorage_statements(
                resultSet.getString("storage_statements"));
        packageResponse.setHealth_claims(resultSet.getString("health_claims"));
        packageResponse.setOther_package_statements(
                resultSet.getString("other_package_statements"));
        packageResponse.setSuggested_directions(
                resultSet.getString("suggested_directions"));
        packageResponse.setIngredients(resultSet.getString("ingredients"));

        final Boolean multi_part_flag = resultSet.getBoolean("multi_part_flag");
        packageResponse.setMulti_part_flag(
                resultSet.wasNull() ? null : multi_part_flag);

        packageResponse.setNutrition_fact_table(
                resultSet.getString("nutrition_fact_table"));

        final Double as_prepared_per_serving_amount = resultSet.getDouble(
                "as_prepared_per_serving_amount");
        packageResponse.setAs_prepared_per_serving_amount(
                resultSet.wasNull() ? null : as_prepared_per_serving_amount);

        packageResponse.setAs_prepared_unit_of_measure(
                resultSet.getString("as_prepared_unit_of_measure"));

        final Double as_sold_per_serving_amount = resultSet.getDouble(
                "as_sold_per_serving_amount");
        packageResponse.setAs_sold_per_serving_amount(
                resultSet.wasNull() ? null : as_sold_per_serving_amount);

        packageResponse.setAs_sold_unit_of_measure(
                resultSet.getString("as_sold_unit_of_measure"));

        final Double as_prepared_per_serving_amount_in_grams = resultSet.getDouble(
                "as_prepared_per_serving_amount_in_grams");
        packageResponse.setAs_prepared_per_serving_amount_in_grams(
                resultSet.wasNull() ? null
                        : as_prepared_per_serving_amount_in_grams);

        final Double as_sold_per_serving_amount_in_grams = resultSet.getDouble(
                "as_sold_per_serving_amount_in_grams");
        packageResponse.setAs_sold_per_serving_amount_in_grams(
                resultSet.wasNull() ? null
                        : as_sold_per_serving_amount_in_grams);

        packageResponse.setPackage_comment(
                resultSet.getString("package_comment"));
        packageResponse.setPackage_source(
                resultSet.getString("package_source"));
        packageResponse.setPackage_product_description(
                resultSet.getString("package_product_description"));
        packageResponse.setPackage_collection_date(
                resultSet.getString("package_collection_date"));

        final Integer number_of_units = resultSet.getInt("number_of_units");
        packageResponse.setNumber_of_units(
                resultSet.wasNull() ? null : number_of_units);

        packageResponse.setEdited_by(resultSet.getString("edited_by"));
        System.out.println("we are here 6");

        final Boolean informed_dining_program = resultSet.getBoolean(
                "informed_dining_program");
        packageResponse.setInformed_dining_program(
                resultSet.wasNull() ? null : informed_dining_program);

        packageResponse.setNft_last_update_date(
                resultSet.getString("nft_last_update_date"));
        System.out.println("we are here 7");

        final Double product_grouping = resultSet.getDouble("product_grouping");
        packageResponse.setProduct_grouping(
                resultSet.wasNull() ? null : product_grouping);

        final Boolean child_item = resultSet.getBoolean("child_item");
        packageResponse.setChild_item(resultSet.wasNull() ? null : child_item);


        packageResponse.setClassification_number(
        		resultSet.getString(
                        "package_classification_number"));

        packageResponse.setClassification_name(
                resultSet.getString("package_classification_name"));

        final String nielsen_item_rank = resultSet.getString(
                "nielsen_item_rank");
        packageResponse.setNielsen_item_rank(
                resultSet.wasNull() ? null : nielsen_item_rank);

        packageResponse.setNutrient_claims(
                resultSet.getString("nutrient_claims"));
        packageResponse.setPackage_nielsen_category(
                resultSet.getString("package_nielsen_category"));
        packageResponse.setCommon_household_measure(
                resultSet.getString("common_household_measure"));

        packageResponse.setCreation_date(resultSet.getString("creation_date"));
        packageResponse.setLast_edit_date(
                resultSet.getString("last_edit_date"));

        final Boolean calculated = resultSet.getBoolean("calculated");
        packageResponse.setCalculated(resultSet.wasNull() ? null : calculated);

        return packageResponse;
    }
	public static ImportLabelModel populateLabelModel(ResultSet rs) throws SQLException {
		
		ImportLabelModel importLabelModel = new ImportLabelModel();
		ImportLabelRequest packageInsertRequest = new ImportLabelRequest();
		
		Double record_id = rs.getDouble("record_id");
		packageInsertRequest.setRecord_id(record_id);
		
		String label_upc = rs.getString("label_upc");
		packageInsertRequest.setPackage_upc(label_upc);
		
		String nielsen_item_rank = rs.getString("nielsen_item_rank");
		packageInsertRequest.setNielsen_item_rank(nielsen_item_rank);
		
		String nielsen_category = rs.getString("nielsen_category");
		packageInsertRequest.setPackage_nielsen_category(nielsen_category);
		
		String label_description = rs.getString("label_description");
		packageInsertRequest.setPackage_description(label_description);
		
		String label_brand = rs.getString("label_brand");
		packageInsertRequest.setPackage_brand(label_brand);
		
		String label_manufacturer = rs.getString("label_manufacturer");
		packageInsertRequest.setPackage_manufacturer(label_manufacturer);
		
		String label_country = rs.getString("label_country");
		packageInsertRequest.setPackage_country(label_country);
		
		Double package_size = rs.getDouble("package_size");
		package_size = rs.wasNull()?null: rs.getDouble("package_size");
		packageInsertRequest.setPackage_size(package_size);
		
		String package_size_unit_of_measure = rs.getString("package_size_unit_of_measure");
		packageInsertRequest.setPackage_size_unit_of_measure(package_size_unit_of_measure);
		
		Integer number_of_units = rs.getInt("number_of_units");	
		number_of_units = rs.wasNull()?null: rs.getInt("number_of_units");
		packageInsertRequest.setNumber_of_units(number_of_units);
		
		
		Double product_grouping = rs.getDouble("product_grouping");
		product_grouping = rs.wasNull()?null: rs.getDouble("product_grouping");
		packageInsertRequest.setProduct_grouping(product_grouping);
		
		
		String storage_type = rs.getString("storage_type");
		packageInsertRequest.setStorage_type(storage_type);
		
		String storage_statement = rs.getString("storage_statement");
		packageInsertRequest.setStorage_statements(storage_statement);
		
		Date label_collection_date = rs.getDate("collection_date");
		packageInsertRequest.setPackage_collection_date(label_collection_date.toString());
		
		String health_claims = rs.getString("health_claims");
		packageInsertRequest.setHealth_claims(health_claims);
		
		String other_package_statements = rs.getString("other_package_statements");
		packageInsertRequest.setOther_package_statements(other_package_statements);
		
		String suggested_directions = rs.getString("suggested_directions");
		packageInsertRequest.setSuggested_directions(suggested_directions);
		
		String ingredients = rs.getString("ingredients");
		packageInsertRequest.setIngredients(ingredients);
		
		Boolean multipart = rs.getBoolean("multipart");
		multipart = rs.wasNull()?null: rs.getBoolean("multipart");
		packageInsertRequest.setMulti_part_flag(multipart);
		
		String nutrition_fact_table = rs.getString("nutrition_fact_table");
		packageInsertRequest.setNutrition_fact_table(nutrition_fact_table);
		
		String common_household_measure = rs.getString("common_household_measure");
		packageInsertRequest.setCommon_household_measure(common_household_measure);
		
		Double per_serving_amount_as_sold = rs.getDouble("per_serving_amount_as_sold");
		per_serving_amount_as_sold = rs.wasNull()?null: rs.getDouble("per_serving_amount_as_sold");
		packageInsertRequest.setAs_sold_per_serving_amount(per_serving_amount_as_sold);
		
		String per_serving_amount_as_sold_unit_of_measure = rs.getString("per_serving_amount_unit_of_measure_as_sold");
		packageInsertRequest.setAs_sold_unit_of_measure(per_serving_amount_as_sold_unit_of_measure);
		
		Double per_serving_amount_as_prepared = rs.getDouble("per_serving_amount_as_prepared");
		per_serving_amount_as_prepared = rs.wasNull()?null: rs.getDouble("per_serving_amount_as_prepared");
		packageInsertRequest.setAs_prepared_per_serving_amount(per_serving_amount_as_prepared);
		
		String as_prepared_unit_of_measure = rs.getString("per_serving_amount_unit_of_measure_as_prepared");
		packageInsertRequest.setAs_prepared_unit_of_measure(as_prepared_unit_of_measure);
		
		
		String label_comment = rs.getString("label_comment");
		
		packageInsertRequest.setPackage_comment(label_comment);
		
		String classification_number = rs.getString("classification_number");
		packageInsertRequest.setClassification_number(classification_number);
		
		String label_source = rs.getString("label_source");
		packageInsertRequest.setPackage_source(label_source);
		
		String package_product_description = rs.getString("product_description");
		packageInsertRequest.setPackage_product_description(package_product_description);
		
		String type = rs.getString("type");
		packageInsertRequest.setType(type);
		
		String type_of_restaurant = rs.getString("type_of_restaurant");
		packageInsertRequest.setType_of_restaurant(type_of_restaurant);
		
		Boolean informed_dining_program = rs.getBoolean("informed_dining_program");
		informed_dining_program = rs.wasNull()?null: rs.getBoolean("informed_dining_program");
		packageInsertRequest.setInformed_dining_program(informed_dining_program);
		
		Date nft_last_update_date = rs.getDate("nft_last_update_date");
		packageInsertRequest.setNft_last_update_date(nft_last_update_date);
		
		Boolean child_item = rs.getBoolean("child_item");
		child_item = rs.wasNull()?null:rs.getBoolean("child_item");
		packageInsertRequest.setChild_item(child_item);

		
		Double per_serving_amount_in_grams_as_sold = rs.getDouble("per_serving_amount_in_grams_as_sold");
		per_serving_amount_in_grams_as_sold = rs.wasNull()?null: rs.getDouble("per_serving_amount_in_grams_as_prepared");
		packageInsertRequest.setAs_sold_per_serving_amount_in_grams(per_serving_amount_in_grams_as_sold);
		
		Double per_serving_amount_in_grams_as_prepared = rs.getDouble("per_serving_amount_in_grams_as_prepared");
		per_serving_amount_in_grams_as_prepared = rs.wasNull()?null: rs.getDouble("per_serving_amount_in_grams_as_prepared");
		packageInsertRequest.setAs_prepared_per_serving_amount_in_grams(per_serving_amount_in_grams_as_prepared);
		
		importLabelModel.setImportLabelRequest(packageInsertRequest);
		
		Double energy_kcal_as_sold = rs.getDouble("energy_kcal_as_sold");
		energy_kcal_as_sold = rs.wasNull()?null: rs.getDouble("energy_kcal_as_sold");		
		//Energy KCAL
		importLabelModel.getNftList().add(new ImportLabelNft("Energy", energy_kcal_as_sold,(energy_kcal_as_sold!= null ? "kcal":null), null,null, true));
		
		
		Double energy_kj_as_sold = rs.getDouble("energy_kj_as_sold");
		energy_kj_as_sold = rs.wasNull()?null: rs.getDouble("energy_kj_as_sold");		
		
		//Energy KJ as sold
		importLabelModel.getNftList().add(new ImportLabelNft("Energy KJ", energy_kj_as_sold,(energy_kj_as_sold!=null? "KJ":null), null,null, true));

		
		Double energy_kj_as_prepared = rs.getDouble("energy_kj_as_prepared");
		energy_kj_as_sold = rs.wasNull()?null: rs.getDouble("energy_kj_as_prepared");		
		//Energy KJ as prepared
		if(energy_kj_as_sold!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Energy KJ", energy_kj_as_prepared, (energy_kj_as_prepared!=null?"KJ":null), null,null, false));
		}
		
		//Fat
		Double fat_as_sold = rs.getDouble("fat_as_sold");
		fat_as_sold = rs.wasNull()?null: rs.getDouble("fat_as_sold");
		Double fat_daily_value_as_sold = rs.getDouble("fat_daily_value_as_sold");
		fat_daily_value_as_sold = rs.wasNull()?null:rs.getDouble("fat_daily_value_as_sold");
		
		Double fat_daily_value_as_prepared = rs.getDouble("fat_daily_value_as_prepared");
		fat_daily_value_as_prepared = rs.wasNull()?null:rs.getDouble("fat_daily_value_as_prepared");		
		
	
		
		importLabelModel.getNftList().add(new ImportLabelNft("Fat", fat_as_sold,(fat_as_sold!=null? "g":null), fat_daily_value_as_sold,null, true));
		
		if(fat_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Fat", null,null, fat_daily_value_as_prepared,null, false));
		}
		
		//Saturated Fat
		Double saturated_fat_as_sold = rs.getDouble("saturated_fat_as_sold");
		saturated_fat_as_sold = rs.wasNull()?null: rs.getDouble("saturated_fat_as_sold");
		importLabelModel.getNftList().add(new ImportLabelNft("Saturated Fat", saturated_fat_as_sold,(saturated_fat_as_sold!=null? "g":null), null,null, true));

	
		//Trans Fat

		Double saturated_plus_trans_daily_value_as_sold = rs.getDouble("saturated_plus_trans_daily_value_as_sold");
		saturated_plus_trans_daily_value_as_sold = rs.wasNull()?null: rs.getDouble("saturated_plus_trans_daily_value_as_sold");
		importLabelModel.getNftList().add(new ImportLabelNft("Saturated + Trans Fat", null,null, saturated_plus_trans_daily_value_as_sold,null, true));

	
		Double saturated_plus_trans_daily_value_as_prepared = rs.getDouble("saturated_plus_trans_daily_value_as_prepared");
		saturated_plus_trans_daily_value_as_prepared = rs.wasNull()?null: rs.getDouble("saturated_plus_trans_daily_value_as_prepared");
		if(saturated_plus_trans_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Saturated + Trans Fat", null,null, saturated_plus_trans_daily_value_as_prepared,null, false));
		}
		//Fat polyunsaturated
		Double fat_polyunsatured_as_sold = rs.getDouble("fat_polyunsatured_as_sold");
		fat_polyunsatured_as_sold = rs.wasNull()?null:fat_polyunsatured_as_sold;

		
		if(fat_polyunsatured_as_sold!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Fat Polyunsatyrated", fat_polyunsatured_as_sold,(fat_polyunsatured_as_sold!=null? "g":null), null,null, true));
		}
		
		Double fat_polyunsaturated_omega_6_as_sold = rs.getDouble("fat_polyunsaturated_omega_6_as_sold");
		fat_polyunsaturated_omega_6_as_sold = rs.wasNull()?null: fat_polyunsaturated_omega_6_as_sold;
		
		importLabelModel.getNftList().add(new ImportLabelNft("Omega-6 Polyunsaturated Fat", fat_polyunsaturated_omega_6_as_sold,(fat_polyunsaturated_omega_6_as_sold!=null? "g":null), null,null, true));

		
		Double fat_polyunsaturated_omega_3_as_sold = rs.getDouble("fat_polyunsaturated_omega_3_as_sold");
		fat_polyunsaturated_omega_3_as_sold = rs.wasNull()?null: fat_polyunsaturated_omega_3_as_sold;
		
		importLabelModel.getNftList().add(new ImportLabelNft("Omega-3 Polyunsaturated Fat", fat_polyunsaturated_omega_3_as_sold,(fat_polyunsaturated_omega_3_as_sold!=null? "g":null), null,null, true));

		//Fat Mono unsaturared
		
		Double fat_mono_unsaturated = rs.getDouble("fat_monounsaturated_as_sold");
		fat_mono_unsaturated = rs.wasNull()?null:fat_mono_unsaturated;

		
		if(fat_mono_unsaturated!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Fat Monounsatyrated", fat_mono_unsaturated,(fat_mono_unsaturated!=null? "g":null), null,null, true));
		}
		//Carbohydrates
		Double carbohydrates_as_sold = rs.getDouble("carbohydrates_as_sold");
		carbohydrates_as_sold = rs.wasNull()?null: rs.getDouble("carbohydrates_as_sold");
		Double carbohydrates_daily_value_as_sold = rs.getDouble("carbohydrates_daily_value_as_sold");
		carbohydrates_daily_value_as_sold = rs.wasNull()?null:rs.getDouble("carbohydrates_daily_value_as_sold");
		
		Double carbohydrates_daily_value_as_prepared = rs.getDouble("carbohydrates_daily_value_as_prepared");
		carbohydrates_daily_value_as_prepared = rs.wasNull()?null:rs.getDouble("carbohydrates_daily_value_as_prepared");		
		

		
		importLabelModel.getNftList().add(new ImportLabelNft("Carbohydrates", carbohydrates_as_sold,(carbohydrates_as_sold!=null? "g":null), carbohydrates_daily_value_as_sold,null, true));
		
		if(carbohydrates_daily_value_as_prepared!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Carbohydrates", null,null, carbohydrates_daily_value_as_prepared,null, false));
		
		}
		
		//Fibre
		
		Double fibre_as_sold = rs.getDouble("fibre_as_sold");
		fibre_as_sold = rs.wasNull()?null: rs.getDouble("fibre_as_sold");
		Double fibre_daily_value_sold = rs.getDouble("fibre_daily_value_sold");
		fibre_daily_value_sold = rs.wasNull()?null:rs.getDouble("fibre_daily_value_sold");
		
		
		Double fibre_daily_value_as_prepared = rs.getDouble("fibre_daily_value_as_prepared");
		fibre_daily_value_as_prepared = rs.wasNull()?null:rs.getDouble("fibre_daily_value_as_prepared");		
		
		
		importLabelModel.getNftList().add(new ImportLabelNft("Fibre", fibre_as_sold,(fibre_as_sold!=null? "g":null), fibre_daily_value_sold,null, true));
		
		if(fibre_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Fibre", null,null, fibre_daily_value_as_prepared,null, false));
		}
		//Soluble fibre
		
		Double soluble_fibre_as_sold = rs.getDouble("soluble_fibre_as_sold");
		soluble_fibre_as_sold = rs.wasNull()?null: rs.getDouble("soluble_fibre_as_sold");
		importLabelModel.getNftList().add(new ImportLabelNft("Soluble Fibre", soluble_fibre_as_sold,(soluble_fibre_as_sold!=null? "g":null), null,null, true));
	
		
		//Insoluble fibre
		
		Double insoluble_fibre_as_sold = rs.getDouble("insoluble_fibre_as_sold");
		insoluble_fibre_as_sold = rs.wasNull()?null: rs.getDouble("insoluble_fibre_as_sold");
		importLabelModel.getNftList().add(new ImportLabelNft("Insoluble Fibre", insoluble_fibre_as_sold,(insoluble_fibre_as_sold!=null? "g":null), null,null, true));
	
		
		//Sugar total 
		
		Double sugar_total_sold = rs.getDouble("sugar_total_sold");
		sugar_total_sold = rs.wasNull()?null: sugar_total_sold;
		
		Double sugar_total_daily_value_as_sold = rs.getDouble("sugar_total_daily_value_as_sold");
		sugar_total_daily_value_as_sold = rs.wasNull()? null: sugar_total_daily_value_as_sold;
		
		Double sugar_total_daily_value_as_prepared = rs.getDouble("sugar_total_daily_value_as_prepared");
		sugar_total_daily_value_as_prepared = rs.wasNull()?null: sugar_total_daily_value_as_prepared;
		
		
		importLabelModel.getNftList().add(new ImportLabelNft("Sugar", sugar_total_sold,(sugar_total_sold!=null? "g":null), sugar_total_daily_value_as_sold,null, true));
		
		if(sugar_total_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Sugar", null,null, sugar_total_daily_value_as_prepared,null, false));
		}
		
		//Sugar alcohols 
		Double sugar_alcohols_as_sold = rs.getDouble("sugar_alcohols_as_sold");
		sugar_alcohols_as_sold = rs.wasNull()?null: sugar_alcohols_as_sold; 
		
		importLabelModel.getNftList().add(new ImportLabelNft("Sugar Acohols", sugar_alcohols_as_sold,(sugar_alcohols_as_sold!=null? "g":null), null,null, true));

		//Starch 
		Double starch_as_sold = rs.getDouble("starch_as_sold");
		starch_as_sold = rs.wasNull()?null: starch_as_sold; 
		
		importLabelModel.getNftList().add(new ImportLabelNft("Starch", starch_as_sold,(starch_as_sold!=null? "g":null), null,null, true));

		
		//Proteins 
		
		Double protein_as_sold = rs.getDouble("protein_as_sold");
		protein_as_sold = rs.wasNull()?null: protein_as_sold; 
		
		importLabelModel.getNftList().add(new ImportLabelNft("Protein", protein_as_sold,(protein_as_sold!=null? "g":null), null,null, true));

		//Cholesterol
		Double cholesterol_as_sold = rs.getDouble("cholesterol_as_sold");
		cholesterol_as_sold = rs.wasNull()?null: cholesterol_as_sold;
		
		Double cholesterol_daily_value_as_sold = rs.getDouble("cholesterol_daily_value_as_sold");
		cholesterol_daily_value_as_sold = rs.wasNull()? null: cholesterol_daily_value_as_sold;
		
		Double cholesterol_daily_value_as_prepared= rs.getDouble("cholesterol_daily_value_as_prepared");
		cholesterol_daily_value_as_prepared = rs.wasNull()?null: cholesterol_daily_value_as_prepared;
		
		
		importLabelModel.getNftList().add(new ImportLabelNft("Cholesterol", cholesterol_as_sold,(cholesterol_as_sold!=null? "g":null), cholesterol_daily_value_as_sold,null, true));
	
		if(cholesterol_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Cholesterol", null,null, cholesterol_daily_value_as_prepared,null, false));
		}
		//Sodium 
		Double sodium_as_sold = rs.getDouble("sodium_as_sold");
		sodium_as_sold = rs.wasNull()?null: sodium_as_sold;
		
		Double sodium_daily_value_as_sold = rs.getDouble("sodium_daily_value_as_sold");
		sodium_daily_value_as_sold = rs.wasNull()? null: sodium_daily_value_as_sold;
		
		Double sodium_daily_value_as_prepared= rs.getDouble("sodium_daily_value_as_prepared");
		sodium_daily_value_as_prepared = rs.wasNull()?null: sodium_daily_value_as_prepared;
		
		
		importLabelModel.getNftList().add(new ImportLabelNft("Sodium", sodium_as_sold,(sodium_as_sold!=null? "g":null), sodium_daily_value_as_sold,null, true));
		
		if(sodium_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Sodium", null,null, sodium_daily_value_as_prepared,null, false));
		}
		//Potassium 
		Double potassium_as_sold = rs.getDouble("potassium_as_sold");
		potassium_as_sold = rs.wasNull()?null: potassium_as_sold;
		
		Double potassium_daily_value_as_sold = rs.getDouble("potassium_daily_value_as_sold");
		potassium_daily_value_as_sold = rs.wasNull()? null: potassium_daily_value_as_sold;
		
		Double potassium_daily_value_as_prepared= rs.getDouble("potassium_daily_value_as_prepared");
		potassium_daily_value_as_prepared = rs.wasNull()?null: potassium_daily_value_as_prepared;
		
		
		importLabelModel.getNftList().add(new ImportLabelNft("Potassium", potassium_as_sold,(potassium_as_sold!=null? "g":null), potassium_daily_value_as_sold,null, true));
		
		if(potassium_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Potassium", null,null, potassium_daily_value_as_prepared,null, false));
		}
	
		//Calcium 
		Double calcium_as_sold = rs.getDouble("calcium_as_sold");
		calcium_as_sold = rs.wasNull()?null: calcium_as_sold;
		
		Double calcium_daily_value_as_sold = rs.getDouble("calcium_daily_value_as_sold");
		calcium_daily_value_as_sold = rs.wasNull()? null: calcium_daily_value_as_sold;
		
		Double calcium_daily_value_as_prepared= rs.getDouble("calcium_daily_value_as_prepared");
		calcium_daily_value_as_prepared = rs.wasNull()?null: calcium_daily_value_as_prepared;
		
		if(calcium_as_sold!=null || calcium_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Calcium", calcium_as_sold,(calcium_as_sold!=null? "g":null), calcium_daily_value_as_sold,null, true));
		}
		
		if(calcium_daily_value_as_prepared!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Calcium", null,null, calcium_daily_value_as_prepared,null, false));
		}
		//Iron 
		Double iron_as_sold = rs.getDouble("iron_as_sold");
		iron_as_sold = rs.wasNull()?null: iron_as_sold;
		
		Double iron_daily_value_as_sold = rs.getDouble("iron_daily_value_as_sold");
		iron_daily_value_as_sold = rs.wasNull()? null: iron_daily_value_as_sold;
		
		Double iron_daily_value_as_prepared= rs.getDouble("iron_daily_value_as_prepared");
		iron_daily_value_as_prepared = rs.wasNull()?null: iron_daily_value_as_prepared;
		
		
		if(iron_as_sold!=null || iron_daily_value_as_sold !=null ){
		importLabelModel.getNftList().add(new ImportLabelNft("Iron", iron_as_sold,(iron_as_sold!=null? "g":null), iron_daily_value_as_sold,null, true));
		}
		
		if(iron_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Iron", null,null, iron_daily_value_as_prepared,null, false));
		}
		
		//Vitamin A 
		Double vitamin_a_as_sold = rs.getDouble("vitamin_a_as_sold");
		vitamin_a_as_sold = rs.wasNull()?null: vitamin_a_as_sold;
		
		Double vitamin_a_daily_value_as_sold = rs.getDouble("vitamin_a_daily_value_as_sold");
		vitamin_a_daily_value_as_sold = rs.wasNull()? null: vitamin_a_daily_value_as_sold;
		
		Double vitamin_a_daily_value_as_prepared= rs.getDouble("vitamin_a_daily_value_as_prepared");
		vitamin_a_daily_value_as_prepared = rs.wasNull()?null: vitamin_a_daily_value_as_prepared;
		
		
		if(vitamin_a_as_sold!= null || vitamin_a_daily_value_as_sold!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin A", vitamin_a_as_sold,(vitamin_a_as_sold!=null? "g":null), vitamin_a_daily_value_as_sold,null, true));
		}
		
		if(vitamin_a_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin A", null,null, vitamin_a_daily_value_as_prepared,null, false));
		}
		//Vitamin C
		Double vitamin_c_as_sold = rs.getDouble("vitamin_c_as_sold");
		vitamin_c_as_sold = rs.wasNull()?null: vitamin_c_as_sold;
		
		Double vitamin_c_daily_value_as_sold = rs.getDouble("vitamin_c_daily_value_as_sold");
		vitamin_c_daily_value_as_sold = rs.wasNull()? null: vitamin_c_daily_value_as_sold;
		
		Double vitamin_c_daily_value_as_prepared= rs.getDouble("vitamin_c_daily_value_as_prepared");
		vitamin_c_daily_value_as_prepared = rs.wasNull()?null: vitamin_c_daily_value_as_prepared;
		
		if(vitamin_c_as_sold!=null || vitamin_c_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin C", vitamin_c_as_sold,(vitamin_c_as_sold!=null? "g":null), vitamin_c_daily_value_as_sold,null, true));
		
		}
		if(vitamin_c_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin C", null,null, vitamin_c_daily_value_as_prepared,null, false));
	
		}
		//Vitamin D
		Double vitamin_d_as_sold = rs.getDouble("vitamin_d_as_sold");
		vitamin_d_as_sold = rs.wasNull()?null: vitamin_d_as_sold;
		
		Double vitamin_d_daily_value_as_sold = rs.getDouble("vitamin_d_daily_value_as_sold");
		vitamin_d_daily_value_as_sold = rs.wasNull()? null: vitamin_d_daily_value_as_sold;
		
		Double vitamin_d_daily_value_as_prepared= rs.getDouble("vitamin_d_daily_value_as_prepared");
		vitamin_d_daily_value_as_prepared = rs.wasNull()?null: vitamin_d_daily_value_as_prepared;
		
		
		if(vitamin_d_as_sold!= null || vitamin_d_daily_value_as_sold!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin D", vitamin_d_as_sold,(vitamin_d_as_sold!=null? "g":null), vitamin_d_daily_value_as_sold,null, true));		
		}
		
		if(vitamin_d_daily_value_as_prepared!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin D", null,null, vitamin_d_daily_value_as_prepared,null, false));
		}
		
		//Vitamin E
		Double vitamin_e_as_sold = rs.getDouble("vitamin_e_as_sold");
		vitamin_e_as_sold = rs.wasNull()?null: vitamin_e_as_sold;
		
		Double vitamin_e_daily_value_as_sold = rs.getDouble("vitamin_e_daily_value_as_sold");
		vitamin_e_daily_value_as_sold = rs.wasNull()? null: vitamin_e_daily_value_as_sold;
		
		Double vitamin_e_daily_value_as_prepared= rs.getDouble("vitamin_e_daily_value_as_prepared");
		vitamin_e_daily_value_as_prepared = rs.wasNull()?null: vitamin_e_daily_value_as_prepared;
		
		
		if(vitamin_e_as_sold!= null || vitamin_e_daily_value_as_sold!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin E", vitamin_e_as_sold,(vitamin_e_as_sold!=null? "g":null), vitamin_e_daily_value_as_sold,null, true));
		}
		
		if(vitamin_e_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin E", null,null, vitamin_e_daily_value_as_prepared,null, false));
		}
		
		//Vitamin K
		Double vitamin_k_as_sold = rs.getDouble("vitamin_k_as_sold");
		vitamin_k_as_sold = rs.wasNull()?null: vitamin_e_as_sold;
		
		Double vitamin_k_daily_value_as_sold = rs.getDouble("vitamin_k_daily_value_as_sold");
		vitamin_e_daily_value_as_sold = rs.wasNull()? null: vitamin_e_daily_value_as_sold;
		
		Double vitamin_k_daily_value_as_prepared= rs.getDouble("vitamin_k_daily_value_as_prepared");
		vitamin_k_daily_value_as_prepared = rs.wasNull()?null: vitamin_k_daily_value_as_prepared;
		
		
		if(vitamin_k_as_sold!= null || vitamin_k_daily_value_as_sold!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin K", vitamin_k_as_sold,(vitamin_k_as_sold!=null? "g":null), vitamin_k_daily_value_as_sold,null, true));
		}
		
		if(vitamin_k_daily_value_as_prepared!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin K", null,null, vitamin_k_daily_value_as_prepared,null, false));
		}
		//Vitamin B6
		Double vitamin_b6_as_sold = rs.getDouble("vitamin_b6_as_sold");
		vitamin_b6_as_sold = rs.wasNull()?null: vitamin_b6_as_sold;
		
		Double vitamin_b6_daily_value_as_sold = rs.getDouble("vitamin_b6_daily_value_as_sold");
		vitamin_b6_daily_value_as_sold = rs.wasNull()? null: vitamin_b6_daily_value_as_sold;
		
		Double vitamin_b6_daily_value_as_prepared= rs.getDouble("vitamin_b6_daily_value_as_prepared");
		vitamin_b6_daily_value_as_prepared = rs.wasNull()?null: vitamin_b6_daily_value_as_prepared;
		
		
		if(vitamin_b6_as_sold!=null || vitamin_b6_daily_value_as_sold!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin B6", vitamin_b6_as_sold,(vitamin_b6_as_sold!=null? "g":null), vitamin_b6_daily_value_as_sold,null, true));
		
		}
		if(vitamin_b6_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin B6", null,null, vitamin_b6_daily_value_as_prepared,null, false));
		}
		
		//Vitamin B12
		Double vitamin_b12_as_sold = rs.getDouble("vitamin_b12_as_sold");
		vitamin_b12_as_sold = rs.wasNull()?null: vitamin_b12_as_sold;
		
		Double vitamin_b12_daily_value_as_sold = rs.getDouble("vitamin_b12_daily_value_as_sold");
		vitamin_b12_daily_value_as_sold = rs.wasNull()? null: vitamin_b12_daily_value_as_sold;
		
		Double vitamin_b12_daily_value_as_prepared= rs.getDouble("vitamin_b12_daily_value_as_prepared");
		vitamin_b12_daily_value_as_prepared = rs.wasNull()?null: vitamin_b12_daily_value_as_prepared;
		
		
		if(vitamin_b12_as_sold!=null || vitamin_b12_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin B12", vitamin_b12_as_sold,(vitamin_b12_as_sold!=null? "g":null), vitamin_b12_daily_value_as_sold,null, true));
		}
		if(vitamin_b12_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Vitamin B12", null,null, vitamin_b12_daily_value_as_prepared,null, false));
		}
		//Thiamine
		Double thiamine_as_sold = rs.getDouble("thiamine_as_sold");
		thiamine_as_sold = rs.wasNull()?null: thiamine_as_sold;
		
		Double thiamine_daily_value_as_sold = rs.getDouble("thiamine_daily_value_as_sold");
		thiamine_daily_value_as_sold = rs.wasNull()? null: thiamine_daily_value_as_sold;
		
		Double thiamine_daily_value_as_prepared= rs.getDouble("thiamine_daily_value_as_prepared");
		thiamine_daily_value_as_prepared = rs.wasNull()?null: thiamine_daily_value_as_prepared;
		
		
		if(thiamine_as_sold!=null || thiamine_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Thiamine", thiamine_as_sold,(thiamine_as_sold!=null? "g":null), thiamine_daily_value_as_sold,null, true));
		}
		if(thiamine_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Thiamine", null,null, thiamine_daily_value_as_prepared,null, false));
		}
		
		//Riboflavin
		Double riboflavin_as_sold = rs.getDouble("riboflavin_as_sold");
		riboflavin_as_sold = rs.wasNull()?null: riboflavin_as_sold;
		
		Double riboflavin_daily_value_as_sold = rs.getDouble("riboflavin_daily_value_as_sold");
		riboflavin_daily_value_as_sold = rs.wasNull()? null: riboflavin_daily_value_as_sold;
		
		Double riboflavin_daily_value_as_prepared= rs.getDouble("riboflavin_daily_value_as_prepared");
		riboflavin_daily_value_as_prepared = rs.wasNull()?null: riboflavin_daily_value_as_prepared;
		
		
		if(riboflavin_as_sold!=null || riboflavin_daily_value_as_sold != null){
		importLabelModel.getNftList().add(new ImportLabelNft("Riboflavin", riboflavin_as_sold,(riboflavin_as_sold!=null? "g":null), riboflavin_daily_value_as_sold,null, true));
		}
		if(riboflavin_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Riboflavin", null,null, riboflavin_daily_value_as_prepared,null, false));
		}
		
		//Niacin
		Double niacin_as_sold = rs.getDouble("niacin_as_sold");
		niacin_as_sold = rs.wasNull()?null: niacin_as_sold;
		
		Double niacin_daily_value_as_sold = rs.getDouble("niacin_daily_value_as_sold");
		niacin_daily_value_as_sold = rs.wasNull()? null: niacin_daily_value_as_sold;
		
		Double niacin_daily_value_as_prepared= rs.getDouble("niacin_daily_value_as_prepared");
		niacin_daily_value_as_prepared = rs.wasNull()?null: niacin_daily_value_as_prepared;
		
		
		if(niacin_as_sold!=null || niacin_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Niacin", niacin_as_sold,(niacin_as_sold!=null? "g":null), niacin_daily_value_as_sold,null, true));
		}
		if(niacin_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Niacin", null,null, niacin_daily_value_as_prepared,null, false));
		}
		
		//Folate
		Double folate_as_sold = rs.getDouble("folate_as_sold");
		folate_as_sold = rs.wasNull()?null: folate_as_sold;
		
		Double folate_daily_value_as_sold = rs.getDouble("folate_daily_value_as_sold");
		folate_daily_value_as_sold = rs.wasNull()? null: folate_daily_value_as_sold;
		
		Double folate_daily_value_as_prepared= rs.getDouble("folate_daily_value_as_prepared");
		folate_daily_value_as_prepared = rs.wasNull()?null: folate_daily_value_as_prepared;
		
		
		if(folate_as_sold!= null || folate_daily_value_as_sold !=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Folate", folate_as_sold,(folate_as_sold!=null? "g":null), folate_daily_value_as_sold,null, true));
		}
		
		if(folate_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Folate", null,null, folate_daily_value_as_prepared,null, false));
		}
		//Biotin
		Double biotin_as_sold = rs.getDouble("biotin_as_sold");
		biotin_as_sold = rs.wasNull()?null: biotin_as_sold;
		
		Double biotin_daily_value_as_sold = rs.getDouble("biotin_daily_value_as_sold");
		biotin_daily_value_as_sold = rs.wasNull()? null: biotin_daily_value_as_sold;
		
		Double biotin_daily_value_as_prepared= rs.getDouble("biotin_daily_value_as_prepared");
		biotin_daily_value_as_prepared = rs.wasNull()?null: biotin_daily_value_as_prepared;
				
		if(biotin_as_sold!=null || biotin_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Biotin", biotin_as_sold,(biotin_as_sold!=null? "g":null), biotin_daily_value_as_sold,null, true));
		}
		if(biotin_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Biotin", null,null, biotin_daily_value_as_prepared,null, false));
		}
		
		//Choline
		Double choline_as_sold = rs.getDouble("choline_as_sold");
		choline_as_sold = rs.wasNull()?null: choline_as_sold;
		
		Double choline_daily_value_as_sold = rs.getDouble("choline_daily_value_as_sold");
		choline_daily_value_as_sold = rs.wasNull()? null: choline_daily_value_as_sold;
		
		Double choline_daily_value_as_prepared= rs.getDouble("choline_daily_value_as_prepared");
		choline_daily_value_as_prepared = rs.wasNull()?null: choline_daily_value_as_prepared;
		
		Integer choline_id = getComponentId("Choline");
		
		if(choline_as_sold!=null || choline_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Choline", choline_as_sold,(choline_as_sold!=null? "g":null), choline_daily_value_as_sold,null, true));
		}
		if(choline_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Choline", null,null, choline_daily_value_as_prepared,null, false));
		}
		
		//Pantothenate
		Double pantothenate_as_sold = rs.getDouble("pantothenate_as_sold");
		pantothenate_as_sold = rs.wasNull()?null: pantothenate_as_sold;
		
		Double pantothenate_daily_value_as_sold = rs.getDouble("pantothenate_daily_value_as_sold");
		pantothenate_daily_value_as_sold = rs.wasNull()? null: pantothenate_daily_value_as_sold;
		
		Double pantothenate_daily_value_as_prepared= rs.getDouble("pantothenate_daily_value_as_prepared");
		pantothenate_daily_value_as_prepared = rs.wasNull()?null: pantothenate_daily_value_as_prepared;
		
		Integer pantothenate_id = getComponentId("Pantothenate");
		
		if(pantothenate_as_sold!=null || pantothenate_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Pantothenate", pantothenate_as_sold,(pantothenate_as_sold!=null? "g":null), pantothenate_daily_value_as_sold,null, true));
		}
		if(pantothenate_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Pantothenate", null,null, pantothenate_daily_value_as_prepared,null, false));
		}
		
		//Phosphorus
		Double phosphorus_as_sold = rs.getDouble("phosphorus_as_sold");
		phosphorus_as_sold = rs.wasNull()?null: phosphorus_as_sold;
		
		Double phosphorus_daily_value_as_sold = rs.getDouble("phosphorus_daily_value_as_sold");
		phosphorus_daily_value_as_sold = rs.wasNull()? null: phosphorus_daily_value_as_sold;
		
		Double phosphorus_daily_value_as_prepared= rs.getDouble("phosphorus_daily_value_as_prepared");
		phosphorus_daily_value_as_prepared = rs.wasNull()?null: phosphorus_daily_value_as_prepared;
		
		Integer phosphorus_id = getComponentId("Phosphorus");
		
		if(phosphorus_as_sold!=null || phosphorus_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Phosphorus", phosphorus_as_sold,(phosphorus_as_sold!=null? "g":null), phosphorus_daily_value_as_sold,null, true));
		}
		if(phosphorus_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Phosphorus", null,null, phosphorus_daily_value_as_prepared,null, false));
		}
		
		//magnesium
		Double magnesium_as_sold = rs.getDouble("magnesium_as_sold");
		magnesium_as_sold = rs.wasNull()?null: magnesium_as_sold;
		
		Double magnesium_daily_value_as_sold = rs.getDouble("magnesium_daily_value_as_sold");
		magnesium_daily_value_as_sold = rs.wasNull()? null: magnesium_daily_value_as_sold;
		
		Double magnesium_daily_value_as_prepared= rs.getDouble("magnesium_daily_value_as_prepared");
		magnesium_daily_value_as_prepared = rs.wasNull()?null: magnesium_daily_value_as_prepared;
		
		Integer magnesium_id = getComponentId("Magnesium");
		
		if(magnesium_as_sold!= null || magnesium_daily_value_as_sold!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Magnesium", magnesium_as_sold,(magnesium_as_sold!=null? "g":null), magnesium_daily_value_as_sold,null, true));
		}
		if(magnesium_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Magnesium", null,null, magnesium_daily_value_as_prepared,null, false));
		}

		//Zinc
		Double zinc_as_sold = rs.getDouble("zinc_as_sold");
		zinc_as_sold = rs.wasNull()?null: zinc_as_sold;
		
		Double zinc_daily_value_as_sold = rs.getDouble("zinc_daily_value_as_sold");
		zinc_daily_value_as_sold = rs.wasNull()? null: zinc_daily_value_as_sold;
		
		Double zinc_daily_value_as_prepared= rs.getDouble("zinc_daily_value_as_prepared");
		zinc_daily_value_as_prepared = rs.wasNull()?null: zinc_daily_value_as_prepared;
		
		if(zinc_as_sold!=null || zinc_daily_value_as_sold!= null){
		importLabelModel.getNftList().add(new ImportLabelNft("Zinc", zinc_as_sold,(zinc_as_sold!=null? "g":null), zinc_daily_value_as_sold,null, true));
		}
		if(zinc_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Zinc", null,null, zinc_daily_value_as_prepared,null, false));
		}
		//Selenium
		Double selenium_as_sold = rs.getDouble("selenium_as_sold");
		selenium_as_sold = rs.wasNull()?null: selenium_as_sold;
		
		Double selenium_daily_value_as_sold = rs.getDouble("selenium_daily_value_as_sold");
		selenium_daily_value_as_sold = rs.wasNull()? null: selenium_daily_value_as_sold;
		
		Double selenium_daily_value_as_prepared= rs.getDouble("selenium_daily_value_as_prepared");
		selenium_daily_value_as_prepared = rs.wasNull()?null: selenium_daily_value_as_prepared;
		
		
		if(selenium_as_sold!=null || selenium_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Selenium", selenium_as_sold,(selenium_as_sold!=null? "g":null), selenium_daily_value_as_sold,null, true));
		}
		if(selenium_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Selenium", null,null, selenium_daily_value_as_prepared,null, false));
		}


		//Copper
		Double copper_as_sold = rs.getDouble("copper_as_sold");
		copper_as_sold = rs.wasNull()?null: copper_as_sold;
		
		Double copper_daily_value_as_sold = rs.getDouble("copper_daily_value_as_sold");
		copper_daily_value_as_sold = rs.wasNull()? null: copper_daily_value_as_sold;
		
		Double copper_daily_value_as_prepared= rs.getDouble("copper_daily_value_as_prepared");
		copper_daily_value_as_prepared = rs.wasNull()?null: copper_daily_value_as_prepared;
		
		
		if(copper_as_sold!= null || copper_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Copper", copper_as_sold,(copper_as_sold!=null? "g":null), copper_daily_value_as_sold,null, true));
		}
		if(copper_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Copper", null,null, copper_daily_value_as_prepared,null, false));
		}
		
		//Manganese
		Double manganese_as_sold = rs.getDouble("manganese_as_sold");
		manganese_as_sold = rs.wasNull()?null: manganese_as_sold;
		
		Double manganese_daily_value_as_sold = rs.getDouble("manganese_daily_value_as_sold");
		manganese_daily_value_as_sold = rs.wasNull()? null: manganese_daily_value_as_sold;
		
		Double manganese_daily_value_as_prepared= rs.getDouble("manganese_daily_value_as_prepared");
		manganese_daily_value_as_prepared = rs.wasNull()?null: manganese_daily_value_as_prepared;
		
		
		if(manganese_as_sold!=null || manganese_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Manganese", manganese_as_sold,(manganese_as_sold!=null? "g":null), manganese_daily_value_as_sold,null, true));
		}
		if(manganese_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Manganese", null,null, manganese_daily_value_as_prepared,null, false));
		}

		//Chromium
		Double chromium_as_sold = rs.getDouble("chromium_as_sold");
		chromium_as_sold = rs.wasNull()?null: chromium_as_sold;
		
		Double chromium_daily_value_as_sold = rs.getDouble("chromium_daily_value_as_sold");
		chromium_daily_value_as_sold = rs.wasNull()? null: chromium_daily_value_as_sold;
		
		Double chromium_daily_value_as_prepared= rs.getDouble("chromium_daily_value_as_prepared");
		chromium_daily_value_as_prepared = rs.wasNull()?null: chromium_daily_value_as_prepared;
		
		
		if(chromium_as_sold!=null || chromium_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Chromium", chromium_as_sold,(chromium_as_sold!=null? "g":null), chromium_daily_value_as_sold,null, true));
		}
		if(chromium_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Chromium", null,null, chromium_daily_value_as_prepared,null, false));
		}

		//Molybdenum
		Double molybdenum_as_sold = rs.getDouble("molybdenum_as_sold");
		molybdenum_as_sold = rs.wasNull()?null: molybdenum_as_sold;
		
		Double molybdenum_daily_value_as_sold = rs.getDouble("molybdenum_daily_value_as_sold");
		molybdenum_daily_value_as_sold = rs.wasNull()? null: molybdenum_daily_value_as_sold;
		
		Double molybdenum_daily_value_as_prepared= rs.getDouble("molybdenum_daily_value_as_prepared");
		molybdenum_daily_value_as_prepared = rs.wasNull()?null: molybdenum_daily_value_as_prepared;
		
		
		if(molybdenum_as_sold!=null || molybdenum_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Molybdenum", molybdenum_as_sold,(molybdenum_as_sold!=null? "g":null), molybdenum_daily_value_as_sold,null, true));
		}
		if(molybdenum_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("Molybdenum", null,null, molybdenum_daily_value_as_prepared,null, false));
		}
		//Molybdenum
		Double chloride_as_sold = rs.getDouble("chloride_as_sold");
		chloride_as_sold = rs.wasNull()?null: chloride_as_sold;
		
		Double chloride_daily_value_as_sold = rs.getDouble("chloride_daily_value_as_sold");
		chloride_daily_value_as_sold = rs.wasNull()? null: chloride_daily_value_as_sold;
		
		Double chloride_daily_value_as_prepared= rs.getDouble("chloride_daily_value_as_prepared");
		chloride_daily_value_as_prepared = rs.wasNull()?null: chloride_daily_value_as_prepared;
		
		
		if(chloride_as_sold!=null || chloride_daily_value_as_sold!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("chloride", chloride_as_sold,(chloride_as_sold!=null? "g":null), chloride_daily_value_as_sold,null, true));
		}
		if(chloride_daily_value_as_prepared!=null){
		importLabelModel.getNftList().add(new ImportLabelNft("chloride", null,null, chloride_daily_value_as_prepared,null, false));
		}





		return importLabelModel;
	}

    public static Map<String, Object> getQueryMap(final PackageRequest request)
    {
        final Map<String, Object> queryMap = new HashMap<String, Object>();

        if (!request.labelUpc.isEmpty())
                queryMap.put("package_upc", request.labelUpc);
       

        if (!request.labelDescription.isEmpty())
            queryMap.put("package_description", request.labelDescription);
        if (!request.labelSource.isEmpty())
            queryMap.put("package_source", request.labelSource);
        if (!request.labelIngredients.isEmpty())
            queryMap.put("ingredients", request.labelIngredients);

        if(request.collectionDateFrom != null && request.collectionDateTo != null){
        if (DateUtil.validateDates(request.collectionDateFrom,
                request.collectionDateTo))
        {
            if (!request.collectionDateFrom.isEmpty()
                    && !request.collectionDateTo.isEmpty())
            {
                queryMap.put("collection_date_from",
                        request.collectionDateFrom);
                queryMap.put("collection_date_to", request.collectionDateTo);
            }
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_DATE);
    }else if (request.collectionDateFrom != null && request.collectionDateTo == null){
        queryMap.put("inputError", ResponseCodes.INVALID_DATE);

    }else if (request.collectionDateFrom == null && request.collectionDateTo != null){
        queryMap.put("inputError", ResponseCodes.INVALID_DATE);

    }

        if (request.offset != null)
        {
            if (!isType(request.offset.toString(), "int"))
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);

        return queryMap;
    }

    // ===

    public static Classification getClassification(final ResultSet result)
            throws SQLException
    {
        final Classification classification = new Classification();

        classification.setId(result.getLong("classification_id"));
        classification.setClassificationNumber(
                ((result.getString("classification_number") == null) ? ""
                        : result.getString("classification_number")));
        classification.setClassificationName(
                result.getString("classification_name"));
        classification.setClassificationType(
                result.getString("classification_type"));

        return classification;
    }

    public static ClassificationResponse getClassificationResponse(
            final ResultSet resultSet) throws SQLException
    {
        final Classification classification = getClassification(resultSet);
        final ClassificationResponse classificationResponse = new ClassificationResponse(
                classification);

        return classificationResponse;
    }

    // ===

    public static ProductSalesResponse getProductSalesResponse(
            final ResultSet resultSet) throws SQLException
    {
        final Sales sales = getSales(resultSet);
        final ProductSalesResponse productSalesResponse = new ProductSalesResponse(
                sales);

        return productSalesResponse;
    }

    // ===

    public static ProductLabelsResponse getProductLabelsResponse(
            final ResultSet resultSet) throws SQLException
    {
        final Package _package = getPackage(resultSet);
        final ProductLabelsResponse productLabelsResponse = new ProductLabelsResponse(
                _package);

        return productLabelsResponse;
    }

    // ===

    public static ProductSalesLabelResponse getProductSalesLabelResponse(
            final ResultSet resultSet) throws SQLException
    {
        final Product product = getProduct(resultSet);
        final ProductSalesLabelResponse productSalesLabelResponse = new ProductSalesLabelResponse(
                product);

        productSalesLabelResponse.setClassification_number(
                ((resultSet.getString("classification_number") == null) ? ""
                        : resultSet.getString("classification_number")));

        productSalesLabelResponse.setClassification_name(
                ((resultSet.getString("classification_name") == null) ? ""
                        : resultSet.getString("classification_name")));
        productSalesLabelResponse.setClassification_type(
                ((resultSet.getString("classification_type") == null) ? ""
                        : resultSet.getString("classification_type")));

        productSalesLabelResponse.setSales_year(
                ((resultSet.getString("sales_year") == null) ? ""
                        : resultSet.getString("sales_year")));
        productSalesLabelResponse.setSales_description(
                ((resultSet.getString("sales_description") == null) ? ""
                        : resultSet.getString("sales_description")));
        productSalesLabelResponse.setSales_upc(
                ((resultSet.getString("sales_upc") == null) ? ""
                        : resultSet.getString("sales_upc")));
        productSalesLabelResponse.setNielsen_category(
                ((resultSet.getString("nielsen_category") == null) ? ""
                        : resultSet.getString("nielsen_category")));
        productSalesLabelResponse.setSales_source(
                ((resultSet.getString("sales_source") == null) ? ""
                        : resultSet.getString("sales_source")));
        productSalesLabelResponse.setSales_collection_date(
                ((resultSet.getString("sales_collection_date") == null) ? ""
                        : resultSet.getString("sales_collection_date")));

        productSalesLabelResponse.setDollar_rank(
                ((resultSet.getString("dollar_rank") == null) ? ""
                        : resultSet.getString("dollar_rank")));

        productSalesLabelResponse.setSales_comment(
                ((resultSet.getString("sales_comment") == null) ? ""
                        : resultSet.getString("sales_comment")));

        productSalesLabelResponse.setLabel_upc(
                ((resultSet.getString("package_upc") == null) ? ""
                        : resultSet.getString("package_upc")));
        productSalesLabelResponse.setLabel_description(
                ((resultSet.getString("package_description") == null) ? ""
                        : resultSet.getString("package_description")));
        productSalesLabelResponse.setLabel_source(
                ((resultSet.getString("package_source") == null) ? ""
                        : resultSet.getString("package_source")));
        productSalesLabelResponse.setLabel_ingredients(
                ((resultSet.getString("ingredients") == null) ? ""
                        : resultSet.getString("ingredients")));
        productSalesLabelResponse.setLabel_collection_date(
                ((resultSet.getString("package_collection_date") == null) ? ""
                        : resultSet.getString("package_collection_date")));
        productSalesLabelResponse.setLabel_comment(
                ((resultSet.getString("package_comment") == null) ? ""
                        : resultSet.getString("package_comment")));

        return productSalesLabelResponse;
    }

    // ---

    public static Map<String, Object> getQueryMap(
            final ProductSalesLabelRequest request)
    {
        final Map<String, Object> queryMap = new HashMap<String, Object>();

        if (!request.product_description.isEmpty())
            queryMap.put("product_description", request.product_description);
        if (!request.product_brand.isEmpty())
            queryMap.put("product_brand", request.product_brand);
        if (!request.product_manufacturer.isEmpty())
            queryMap.put("product_manufacturer", request.product_manufacturer);

        if (request.cnf_code != null)
        {
            if (isType(request.cnf_code.toString(), "int"))
            {
                if (request.cnf_code > 0)
                    queryMap.put("cnf_code", request.cnf_code);
            }
            else
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);

        if (request.cluster_number != null)
        {
            if (isType(request.cluster_number.toString(), "double"))
            {
                if (request.cluster_number > 0.0)
                    queryMap.put("cluster_number", request.cluster_number);
            }
            else
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);

        if (!request.product_comment.isEmpty())
            queryMap.put("product_comment", request.product_comment);

        if (!request.classification_number.isEmpty() && request.classification_number != "")
        {

                    queryMap.put("classification_number",
                            request.classification_number);

        }
       
       
        if (!request.classification_name.isEmpty())
            queryMap.put("classification_name", request.classification_name);
        if (!request.classification_type.isEmpty())
            queryMap.put("classification_type", request.classification_type);

        // ===

        if (request.sales_year != null)
        {
            if (isType(request.sales_year.toString(), "int"))
            {
                if (request.sales_year > 0)
                    queryMap.put("sales_year", request.sales_year);
            }
            else
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);

        if (!request.sales_description.isEmpty())
            queryMap.put("sales_description", request.sales_description);

        if (!request.sales_upc.isEmpty())
            if (StringUtilities.isNumeric(request.sales_upc))
                queryMap.put("sales_upc", request.sales_upc);
            else
                queryMap.put("inputError", ResponseCodes.INVALID_UPC);

        if (!request.nielsen_category.isEmpty())
            queryMap.put("nielsen_category", request.nielsen_category);

        if (!request.sales_source.isEmpty())
            queryMap.put("sales_source", request.sales_source);

        if(request.sales_collection_date_from != null && request.sales_collection_date_to != null){
        if (DateUtil.validateDates(request.sales_collection_date_from,
                request.sales_collection_date_to))
        {
            if (!request.sales_collection_date_from.isEmpty()
                    && !request.sales_collection_date_to.isEmpty())
            {
                queryMap.put("sales_collection_date_from",
                        request.sales_collection_date_from);
                queryMap.put("sales_collection_date_to",
                        request.sales_collection_date_to);
            }
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_DATE);
    }else if(request.sales_collection_date_from != null && request.sales_collection_date_to == null){
    	 queryMap.put("inputError", ResponseCodes.INVALID_DATE);
    }else if(request.sales_collection_date_from == null && request.sales_collection_date_to != null){
   	 queryMap.put("inputError", ResponseCodes.INVALID_DATE);
   }

        /// ===

        if ((request.dollar_rank_from != null)
                && (request.dollar_rank_to != null))
        {
            if (isType(request.dollar_rank_from.toString(), "double"))
            {
                if (request.dollar_rank_from > 0.0)
                    queryMap.put("dollar_rank_from", request.dollar_rank_from);
            }
            else
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);

            if (isType(request.dollar_rank_to.toString(), "double"))
            {
                if (request.dollar_rank_to > 0.0)
                    queryMap.put("dollar_rank_to", request.dollar_rank_to);
            }
            else
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);

        /// ===

        if (!request.sales_comment.isEmpty())
            queryMap.put("sales_comment", request.sales_comment);

        // ===

        if (!request.label_upc.isEmpty())
            if (StringUtilities.isNumeric(request.label_upc))
                queryMap.put("package_upc", request.label_upc);
            else
                queryMap.put("inputError", ResponseCodes.INVALID_UPC);

        if (!request.label_description.isEmpty())
            queryMap.put("package_description", request.label_description);
        if (!request.label_source.isEmpty())
            queryMap.put("package_source", request.label_source);
        if (!request.label_ingredients.isEmpty())
            queryMap.put("ingredients", request.label_ingredients);


        if(request.label_collection_date_from != null && request.label_collection_date_to != null){
        if (DateUtil.validateDates(request.label_collection_date_from,
                request.label_collection_date_to))
        {
            if (!request.label_collection_date_from.isEmpty()
                    && !request.label_collection_date_to.isEmpty())
            {
                queryMap.put("collection_date_from",
                        request.label_collection_date_from);
                queryMap.put("collection_date_to", request.label_collection_date_to);
            }
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_DATE);
    }else if (request.label_collection_date_from != null && request.label_collection_date_to == null){
        queryMap.put("inputError", ResponseCodes.INVALID_DATE);

    }else if (request.label_collection_date_from == null && request.label_collection_date_to != null){
        queryMap.put("inputError", ResponseCodes.INVALID_DATE);

    }
        // ===

        if (request.offset != null)
        {
            if (!isType(request.offset.toString(), "int"))
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);

        return queryMap;
    }

    // ===

    public static Map<String, Object> getQueryMap(
            final ProductUpdateRequest request)
    {
        final Map<String, Object> queryMap = new HashMap<String, Object>();

        if ((request.product_manufacturer != null)
                && !request.product_manufacturer.isEmpty())
            queryMap.put("product_manufacturer", request.product_manufacturer);
        if ((request.product_brand != null) && !request.product_brand.isEmpty())
            queryMap.put("product_brand", request.product_brand);

        if ((request.cnf_code != null) && !request.cnf_code.isEmpty())
            if (isType(request.cnf_code, "int"))
                queryMap.put("cnf_code", request.cnf_code);
            else
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);

        if ((request.cluster_number != null)
                && !request.cluster_number.isEmpty())
            if (isType(request.cluster_number, "double"))
                queryMap.put("cluster_number", request.cluster_number);
            else
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);

        if ((request.product_description != null)
                && !request.product_description.isEmpty())
            queryMap.put("product_description", request.product_description);
        else
            queryMap.put("inputError",
                    ResponseCodes.MISSING_PRODUCT_DESCRIPTION);

        if ((request.product_comment != null)
                && !request.product_comment.isEmpty())
            queryMap.put("product_comment", request.product_comment);

        if (request.classification_number != null && !request.classification_number.isEmpty())
        {

                    queryMap.put("classification_number",
                            request.classification_number);
            
            
        }
        

        if ((request.classification_name != null)
                && !request.classification_name.isEmpty())
            queryMap.put("classification_name", request.classification_name);
        if ((request.classification_type != null)
                && !request.classification_type.isEmpty())
            queryMap.put("classification_type", request.classification_type);

        if ((request.restaurant_type != null)
                && !request.restaurant_type.isEmpty())
            queryMap.put("restaurant_type", request.restaurant_type);
        if ((request.type != null) && !request.type.isEmpty())
            queryMap.put("type", request.type);

        if ((request.edited_by != null) && !request.edited_by.isEmpty())
            queryMap.put("edited_by", request.edited_by);

        return queryMap;
    }

    // ===

    public static Boolean isType(final String testStr, final String type)
    {
        try
        {
            if (type.equalsIgnoreCase("float"))
                Float.parseFloat(testStr);
            else if (type.equalsIgnoreCase("int"))
                Integer.parseInt(testStr);
            else if (type.equalsIgnoreCase("double"))
                Double.parseDouble(testStr);
            else if (type.equalsIgnoreCase("long"))
                Long.parseLong(testStr);
            return true;
        }
        catch (final Exception e)
        {
            return false;
        }

    }
	private static Integer getComponentId(String string) {
		// TODO Auto-generated method stub
		return 1;
	}

}
