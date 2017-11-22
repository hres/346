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
import hc.fcdr.rws.model.pkg.PackageRequest;
import hc.fcdr.rws.model.pkg.PackageResponse;
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

/**
 * Utility class for DAO's. This class contains commonly used DAO logic which is been refactored in single static
 * methods. As far it contains a PreparedStatement values setter and several quiet close methods.
 * 
 */
public final class DaoUtil
{

    // Constructors
    // -------------------------------------------------------------------------------

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

        if (!request.product_description.isEmpty())
            queryMap.put("product_description", request.product_description);
        if (!request.product_comment.isEmpty())
            queryMap.put("product_comment", request.product_comment);

        if (request.classification_number != null)
        {
            if (isType(request.classification_number.toString(), "double"))
            {
                if (request.classification_number > 0.0)
                    queryMap.put("classification_number",
                            request.classification_number);
            }
            else
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);

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

    public static Map<String, Object> getQueryMap(final ProductInsertRequest request){
        
    	final Map<String, Object> queryMap = new HashMap<String, Object>();
        final List<Object> productInsertList = new ArrayList<Object>();
    	
    	 if (request.getProduct_manufacturer() !=null)
             queryMap.put("product_manufacturer", request.getProduct_manufacturer());
         productInsertList.add(request.getProduct_manufacturer());
	 
         if (request.getProduct_brand() !=null)
             queryMap.put("product_brand", request.getProduct_brand() );
         
         productInsertList.add(request.getProduct_brand());

         if (request.getCnf_code() != null)
         {

                 if (request.getCnf_code() instanceof Number){
                     queryMap.put("cnf_code", request.getCnf_code());
             }
             else
                 queryMap.put("inputError", ResponseCodes.INVALID_INPUT_FIELDS);
         }
         productInsertList.add(request.getCnf_code());

         if (request.getCluster_number() != null)
         {

                 if (request.getCluster_number() instanceof Number){
                     queryMap.put("cluster_number", request.getCluster_number());
             }
             else
                 queryMap.put("inputError", ResponseCodes.INVALID_INPUT_FIELDS);
         }
         productInsertList.add(request.getCluster_number());


         if (request.getProduct_description() != null)
             queryMap.put("product_description", request.getProduct_description());
         else
        	 queryMap.put("inputError", ResponseCodes.MISSING_MANDATORY_FIELDS);
         
         productInsertList.add(request.getProduct_description());

         if (request.getProduct_comment() !=null)
             queryMap.put("product_comment", request.getProduct_comment());

         productInsertList.add(request.getProduct_comment());

         if (request.getType() !=null)
             queryMap.put("type", request.getType());

         productInsertList.add(request.getType());
         
         if (request.getRestaurant_type() !=null)
             queryMap.put("restaurant_type", request.getRestaurant_type());

         productInsertList.add(request.getRestaurant_type());
         
         final Timestamp now = DateUtil.getCurrentTimeStamp();
         
         productInsertList.add(now);
         productInsertList.add(now);
         
         if (request.getClassification_number() != null)
         {

                 if (request.getClassification_number() instanceof Number){
                     queryMap.put("classification_number",
                             request.getClassification_number());
             }
             else
                 queryMap.put("inputError", ResponseCodes.INVALID_INPUT_FIELDS);
         }
         
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

        Double dollar_rank = result.getDouble("dollar_rank");
        sales.setDollarRank(result.wasNull()?null:dollar_rank);
  
        
        Double dollar_volume = result.getDouble("dollar_volume");
        sales.setDollarVolume(result.wasNull()?null:dollar_volume);
 
        Double dollar_share = result.getDouble("dollar_share");
        sales.setDollarShare(result.wasNull()?null:dollar_share);
        
        Double dollar_volume_percentage_change = result.getDouble("dollar_volume_percentage_change");
        sales.setDollarVolumePercentageChange(result.wasNull()?null:dollar_volume_percentage_change);
        
        Double kilo_volume = result.getDouble("kilo_volume");
        sales.setKiloVolume(result.wasNull()?null:kilo_volume);
 
        
        Double kilo_share = result.getDouble("kilo_share");
        sales.setKiloShare(result.wasNull()?null:kilo_share);
        
        Double kilo_volume_percentage_change = result.getDouble("kilo_volume_percentage_change");
        sales.setKiloVolumePercentageChange(result.wasNull()?null:kilo_volume_percentage_change);
        
        
        Double average_ac_dist = result.getDouble("average_ac_dist");
        sales.setAverageAcDist(result.wasNull()?null:average_ac_dist);
        
//        sales.setAverageAcDist(result.getDouble("average_ac_dist"));
//        sales.setAverageRetailPrice(result.getDouble("average_retail_price"));
        
        Double average_retail_price = result.getDouble("average_retail_price");
        sales.setAverageRetailPrice(result.wasNull()?null:average_retail_price);
        
        sales.setSalesSource(result.getString("sales_source"));
        sales.setNielsenCategory(result.getString("nielsen_category"));
    
        
        String sales_year = result.getString("sales_year");
        sales.setSalesYear(result.wasNull()?null:sales_year);
 
        
        Boolean control_label_flag = result.getBoolean("control_label_flag");
        sales.setControlLabelFlag(result.wasNull()?null:control_label_flag);
   
        Double kilo_volume_total = result.getDouble("kilo_volume_total");
        sales.setKiloVolumeTotal(result.wasNull()?null:kilo_volume_total);
  
        Double kilo_volume_rank = result.getDouble("kilo_volume_rank");
        sales.setKiloVolumeRank(result.wasNull()?null:kilo_volume_rank);
        
        Double dollar_volume_total = result.getDouble("dollar_volume_total");
        sales.setDollarVolumeTotal(result.wasNull()?null:dollar_volume_total);
   
        Double cluster_number = result.getDouble("cluster_number");
        sales.setClusterNumber(result.wasNull()?null:cluster_number);
 
        Double product_grouping = result.getDouble("product_grouping");
        sales.setProductGrouping(result.wasNull()?null:product_grouping);
        
               sales.setSalesProductDescription(
                result.getString("sales_product_description"));
 
               Double classification_number = result.getDouble("classification_number");
               sales.setClassificationNumber(result.wasNull()?null:classification_number);

        sales.setClassificationType(result.getString("classification_type"));
        sales.setSalesComment(result.getString("sales_comment"));
        
        sales.setSalesCollectionDate(result.getString("sales_collection_date"));
        
        
        Integer number_of_units = result.getInt("number_of_units");
        
        sales.setNumberOfUnits((result.wasNull()?null:number_of_units));
        
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
        {
            if (request.salesYear instanceof Number)
            {
              
                    queryMap.put("sales_year", request.salesYear);
            }
            else
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);

        if (!request.nielsenCategory.isEmpty())
            queryMap.put("nielsen_category", request.nielsenCategory);
        if (!request.salesComment.isEmpty())
            queryMap.put("sales_comment", request.salesComment);

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
        	if(request.dollar_rank instanceof Number){
                    queryMap.put("dollar_rank", request.dollar_rank);
        	}
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesInsertList.add(request.dollar_rank);
  

        if (request.dollar_volume != null)
        {
  		
            if (request.dollar_volume instanceof Number)
            {
             
                    queryMap.put("dollar_volume", request.dollar_volume);
            }
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
            {
              
                    queryMap.put("dollar_share", request.dollar_share);
            }
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

            {

                    queryMap.put("dollar_volume_percentage_change",
                            request.dollar_volume_percentage_change);
            }
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
            {
                
                    queryMap.put("kilo_volume", request.kilo_volume);
            }
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
            {
                
                    queryMap.put("kilo_share", request.kilo_share);
            }
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

            {
              
                    queryMap.put("kilo_volume_percentage_change",
                            request.kilo_volume_percentage_change);
            }
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
            {
                    queryMap.put("average_ac_dist", request.average_ac_dist);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesInsertList.add(request.average_ac_dist);

        if (request.average_retail_price != null)
            if (request.average_retail_price instanceof Number)
            {
                    queryMap.put("average_retail_price",
                            request.average_retail_price);
            }
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
                

        if (request.control_label_flag !=null)
            queryMap.put("control_label_flag", request.control_label_flag);
        salesInsertList.add(request.control_label_flag);

        if (request.kilo_volume_total != null)
        {
            if (request.kilo_volume_total instanceof Number)
            {
                    queryMap.put("kilo_volume_total",
                            request.kilo_volume_total);
            }
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
            {
                    queryMap.put("kilo_volume_rank", request.kilo_volume_rank);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesInsertList.add(request.kilo_volume_rank);

        if (request.dollar_volume_total != null)
        {
            if (request.dollar_volume_total instanceof Number)
            {
                    queryMap.put("dollar_volume_total",
                            request.dollar_volume_total);
            }
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
            {
                    queryMap.put("cluster_number", request.cluster_number);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesInsertList.add(request.cluster_number);

        if (request.product_grouping != null)
            if (request.product_grouping instanceof Number)
            {
                    queryMap.put("product_grouping", request.product_grouping);
            }
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
        	if (request.classification_number instanceof Number)
        	{
                    queryMap.put("classification_number",request.classification_number);
                           
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesInsertList.add(request.classification_number);

        if (!request.classification_type.isEmpty())
            queryMap.put("classification_type", request.classification_type);
        salesInsertList.add(request.classification_type);

        if (!request.sales_comment.isEmpty())
            queryMap.put("sales_comment", request.sales_comment);
        salesInsertList.add(request.sales_comment);

        if (!request.sales_collection_date.isEmpty())
        {
            queryMap.put("sales_collection_date",
                    request.sales_collection_date);
            salesInsertList.add(Date.valueOf(request.sales_collection_date));
        }
        else
            salesInsertList.add(null);

        if (request.number_of_units != null)
            if (request.number_of_units instanceof Number)
            {
                    queryMap.put("number_of_units", request.number_of_units);
            }
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
        	
        	if(request.dollar_rank instanceof Number)
                    queryMap.put("dollar_rank", request.dollar_rank);
            
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.dollar_rank);

        if (request.dollar_volume != null)
        {
           
        	if(request.dollar_volume instanceof Number)

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
    
            	if(request.dollar_share instanceof Number){
                    queryMap.put("dollar_share", request.dollar_share);
            }
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
       
            	if(request.dollar_volume_percentage_change instanceof Number){
                    queryMap.put("dollar_volume_percentage_change",
                            request.dollar_volume_percentage_change);
            }
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
            
        	if(request.kilo_volume instanceof Number){
                    queryMap.put("kilo_volume", request.kilo_volume);
            }
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
         
            	if(request.kilo_share instanceof Number){
                    queryMap.put("kilo_share", request.kilo_share);
            }
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
           
                if (request.kilo_volume_percentage_change instanceof Number){
                    queryMap.put("kilo_volume_percentage_change",
                            request.kilo_volume_percentage_change);
            }
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

            	if(request.average_ac_dist instanceof Number){
                    queryMap.put("average_ac_dist", request.average_ac_dist);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.average_ac_dist);

        if (request.average_retail_price != null)
  
            	if(request.average_retail_price instanceof Number){
                    queryMap.put("average_retail_price",
                            request.average_retail_price);
            }
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

            	if(request.sales_year instanceof Number){
                    queryMap.put("sales_year", request.sales_year);
            }
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

            	if(request.kilo_volume_total instanceof Number){
                    queryMap.put("kilo_volume_total",
                            request.kilo_volume_total);
            }
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

            	if(request.kilo_volume_rank instanceof Number){
                    queryMap.put("kilo_volume_rank", request.kilo_volume_rank);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.kilo_volume_rank);

        if (request.dollar_volume_total != null)
        {

            	if(request.dollar_volume_total instanceof Number){
                    queryMap.put("dollar_volume_total",
                            request.dollar_volume_total);
            }
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

            	if(request.cluster_number instanceof Number){
                    queryMap.put("cluster_number", request.cluster_number);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.cluster_number);

        if (request.product_grouping != null)

            	if(request.product_grouping instanceof Number){
                    queryMap.put("product_grouping", request.product_grouping);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.product_grouping);

        if (request.sales_product_description!=null)
            queryMap.put("sales_product_description",
                    request.sales_product_description);
        salesUpdateList.add(request.sales_product_description);

        if (request.classification_number != null)

            	if(request.classification_number instanceof Number){
                    queryMap.put("classification_number",
                            request.classification_number);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
                return queryMap;
            }
        salesUpdateList.add(request.classification_number);

        if (request.classification_type != null)
            queryMap.put("classification_type", request.classification_type);
        salesUpdateList.add(request.classification_type);

        if (request.sales_comment!=null)
            queryMap.put("sales_comment", request.sales_comment);
        salesUpdateList.add(request.sales_comment);

        if (request.sales_collection_date != null)
        {
            queryMap.put("sales_collection_date",
                    request.sales_collection_date);
            salesUpdateList.add(Date.valueOf(request.sales_collection_date));
        }
        else
            salesUpdateList.add(null);

        if (request.number_of_units != null)

            	if(request.number_of_units instanceof Number){
                    queryMap.put("number_of_units", request.number_of_units);
            }
            else
            {
                queryMap.put("inputError", ResponseCodes.INVALID_INTEGER);
                return queryMap;
            }
        salesUpdateList.add(request.number_of_units);
        System.out.print("in the daoUtil"+request.number_of_units);
        if (request.edited_by != null)
            queryMap.put("edited_by", request.edited_by);
        salesUpdateList.add(request.edited_by);

        final Timestamp now = DateUtil.getCurrentTimeStamp();
        queryMap.put("last_edit_date", now);
        salesUpdateList.add(now);

        System.out.println("that sales id is omg"+request.sales_id);
        if (request.sales_id != null)
        {
        	if(request.sales_id instanceof Number)
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

    public static Map<String, Object> getQueryMap(final PackageRequest request)
    {
        final Map<String, Object> queryMap = new HashMap<String, Object>();

        if (!request.labelUpc.isEmpty())
            if (StringUtilities.isNumeric(request.labelUpc))
                queryMap.put("package_upc", request.labelUpc);
            else
                queryMap.put("inputError", ResponseCodes.INVALID_UPC);

        if (!request.labelDescription.isEmpty())
            queryMap.put("package_description", request.labelDescription);
        if (!request.labelSource.isEmpty())
            queryMap.put("package_source", request.labelSource);
        if (!request.labelIngredients.isEmpty())
            queryMap.put("ingredients", request.labelIngredients);

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

        if (request.classification_number != null)
        {
            if (isType(request.classification_number.toString(), "double"))
            {
                if (request.classification_number > 0.0)
                    queryMap.put("classification_number",
                            request.classification_number);
            }
            else
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);

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
            queryMap.put("package_ingredients", request.label_ingredients);

        if (DateUtil.validateDates(request.label_collection_date_from,
                request.label_collection_date_to))
        {
            if (!request.label_collection_date_from.isEmpty()
                    && !request.label_collection_date_to.isEmpty())
            {
                queryMap.put("package_collection_date_from",
                        request.label_collection_date_from);
                queryMap.put("package_collection_date_to",
                        request.label_collection_date_to);
            }
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_DATE);

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

        if (request.classification_number != null)
        {
            if (isType(request.classification_number.toString(), "double"))
            {
                if (request.classification_number > 0.0)
                    queryMap.put("classification_number",
                            request.classification_number);
            }
            else
                queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);
        }
        else
            queryMap.put("inputError", ResponseCodes.INVALID_DOUBLE);

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

}
