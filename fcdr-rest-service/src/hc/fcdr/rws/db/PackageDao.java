package hc.fcdr.rws.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.domain.Package;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.importLabel.ExistingLabels;
import hc.fcdr.rws.model.importLabel.ImportLabelModel;
import hc.fcdr.rws.model.importLabel.ImportLabelNft;
import hc.fcdr.rws.model.importLabel.ImportLabelRequest;
import hc.fcdr.rws.model.pkg.ComponentName;
import hc.fcdr.rws.model.pkg.ComponentNameResponse;
import hc.fcdr.rws.model.pkg.GenericList;
import hc.fcdr.rws.model.pkg.InsertPackageResponse;
import hc.fcdr.rws.model.pkg.NftModel;
import hc.fcdr.rws.model.pkg.NftRequest;
import hc.fcdr.rws.model.pkg.NftView;
import hc.fcdr.rws.model.pkg.PackageData;
import hc.fcdr.rws.model.pkg.PackageDataResponse;
import hc.fcdr.rws.model.pkg.PackageInsertRequest;
import hc.fcdr.rws.model.pkg.PackageRequest;
import hc.fcdr.rws.model.pkg.PackageResponse;
import hc.fcdr.rws.model.pkg.PackageUpdateRequest;
import hc.fcdr.rws.model.pkg.PackageViewData;
import hc.fcdr.rws.model.pkg.PackageViewDataResponse;
import hc.fcdr.rws.model.pkg.PackageViewResponse;
import hc.fcdr.rws.model.pkg.ResponseGeneric;
import hc.fcdr.rws.model.product.ProductInsertRequest;
import hc.fcdr.rws.model.sales.ImportMarketShare;
import hc.fcdr.rws.model.sales.SalesDeleteDataResponse;
import hc.fcdr.rws.model.sales.SalesInsertRequest;
import hc.fcdr.rws.util.DaoUtil;
import hc.fcdr.rws.util.DateUtil;

public class PackageDao extends PgDao
{
    private static final Logger logger       =
            Logger.getLogger(PackageDao.class.getName());
    private final String        schema;
    private static final String SQL_INSERT   =
            "insert into ${table}(${keys}) values(${values})";
    private static final String TABLE_REGEX  = "\\$\\{table\\}";
    private static final String KEYS_REGEX   = "\\$\\{keys\\}";
    private static final String VALUES_REGEX = "\\$\\{values\\}";

    public PackageDao(final Connection connection, final String schema)
    {
        super(connection);
        this.schema = schema;
    }

    public List<Package> getPackage() throws DaoException
    {
        ResultSet resultSet = null;
        final List<Package> packageList = new ArrayList<Package>();

        final String query = "select * from " + schema + "." + "package";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
                packageList.add(DaoUtil.getPackage(resultSet));
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return packageList;
    }

    public Package getPackage(final Long packageId) throws DaoException
    {
        ResultSet resultSet = null;
        Package _package = null;

        final String query =
                "select * from "
                        + schema + "." + "package where package_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { packageId });

            if (resultSet.next())
                _package = DaoUtil.getPackage(resultSet);
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return _package;
    }

    // ===

    public NftView getNft(final Integer packageId, final Boolean flag)
            throws DaoException
    {
        NftView resultSet = null;

        final String query =
                "select  amount, amount_unit_of_measure, percentage_daily_value, component_name,amount_per100g  from "
                        + schema + "." + "product_component pc INNER JOIN "
                        + schema + "."
                        + "component c on pc.component_id = c.component_id where package_id = ? and as_ppd_flag = ? order by nft_order";

        System.out.println(query);
        try
        {
            resultSet = getNft(query, packageId, flag);
            resultSet.setStatus(200);

        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }
        System.out.println(resultSet);
        return resultSet;
    }

    // ===
    public ResponseGeneric getNftInsertResponse(final NftRequest nftRequest)
            throws DaoException, SQLException
    {

        final Map<String, Object> queryMap = DaoUtil.getQueryMap(nftRequest);

        if (queryMap.containsKey("inputError"))
        {
            final Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new ResponseGeneric(((ResponseCodes) o).getCode(),
                    ((ResponseCodes) o).getMessage());
        }

        System.out.println("The flag has the value of " + nftRequest.getFlag());

        if (nftRequest.getFlag() == null)
            return new ResponseGeneric(0, "Invalid flag");
        else if (nftRequest.getFlag() == true)
        {

            if (checkIfHasNft(nftRequest, schema, true) > 0)
                return new ResponseGeneric(777, "Has an NFT as sold already");
            else
                insertNft(nftRequest, schema);
        }
        else if (nftRequest.getFlag() == false)
        {
            if (checkIfHasNft(nftRequest, schema, false) > 0)
                return new ResponseGeneric(778, "Has an NFT as sold prepared");
            else
                insertNft(nftRequest, schema);
        }
        else
            return new ResponseGeneric(0, "Invalid flag");

        return new ResponseGeneric(ResponseCodes.OK.getCode(),
                ResponseCodes.OK.getMessage());
    }

    public ResponseGeneric getNftUpdateResponse(final NftRequest nftRequest)
            throws DaoException, SQLException
    {

        // nftRequest.getNft().size();

        if ((nftRequest.getFlag() == null)
                || (!(nftRequest.getPackage_id() instanceof Number)))
            return new ResponseGeneric(
                    ResponseCodes.INVALID_INPUT_FIELDS.getCode(),
                    ResponseCodes.INVALID_INPUT_FIELDS.getMessage());
        final String sql =
                "delete from "
                        + schema + "."
                        + "product_component where package_id = ? and as_ppd_flag = ?";

        if (nftRequest.getNft().size() < 1)
            try
            {
                final Integer deletedRow =
                        (Integer) executeUpdate(sql, new Object[]
                        { nftRequest.getPackage_id(), nftRequest.getFlag() });

                if (deletedRow == 0)
                    return new ResponseGeneric(
                            ResponseCodes.BAD_REQUEST.getCode(),
                            ResponseCodes.BAD_REQUEST.getMessage());
            }
            catch (final Exception e)
            {
                logger.error(e);
                throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
            }
        else
            try
            {
                connection.setAutoCommit(false);
                final Map<String, Object> queryMap =
                        DaoUtil.getQueryMap(nftRequest);

                if (queryMap.containsKey("inputError"))
                {
                    final Object o = queryMap.get("inputError");
                    queryMap.remove("inputError");

                    return new ResponseGeneric(((ResponseCodes) o).getCode(),
                            ((ResponseCodes) o).getMessage());
                }
                executeUpdate(sql, new Object[]
                { nftRequest.getPackage_id(), nftRequest.getFlag() });
                updateNft(nftRequest, schema);

                connection.commit();
            }
            catch (final SQLException e)
            {
                // TODO Auto-generated catch block
                connection.rollback();
                e.printStackTrace();

                // return false;
            }

        return new ResponseGeneric(ResponseCodes.OK.getCode(),
                ResponseCodes.OK.getMessage());

    }

    public InsertPackageResponse getPackageInsertResponse(
            final PackageInsertRequest packageInsertRequest)
            throws DaoException, SQLException
    {

        final Map<String, Object> queryMap =
                DaoUtil.getQueryMap(packageInsertRequest);

        if (queryMap.isEmpty())
            return new InsertPackageResponse(
                    ResponseCodes.EMPTY_REQUEST.getCode(),
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            final Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new InsertPackageResponse(((ResponseCodes) o).getCode(),
                    ((ResponseCodes) o).getMessage());
        }

        // Check for valid classification_number.
        if (packageInsertRequest.getClassification_number() != null)
            if (!checkClassification(
                    packageInsertRequest.getClassification_number()))
                return new InsertPackageResponse(
                        ResponseCodes.INVALID_CLASSIFICATION_NUMBER.getCode(),
                        ResponseCodes.INVALID_CLASSIFICATION_NUMBER
                                .getMessage());

        if (!checkForSamePackageUpcProductId(
                packageInsertRequest.getPackage_upc(),
                packageInsertRequest.getProduct_id()))
            return new InsertPackageResponse(
                    ResponseCodes.INVALID_UPC_PRODUCTID.getCode(),
                    ResponseCodes.INVALID_UPC_PRODUCTID.getMessage());

        final String[] columns =
        {
                "package_description", "package_upc", "package_brand",
                "package_manufacturer", "package_country", "package_size",
                "package_size_unit_of_measure", "storage_type",
                "storage_statements", "health_claims",
                "other_package_statements", "suggested_directions",
                "ingredients", "multi_part_flag", "nutrition_fact_table",
                "as_prepared_per_serving_amount", "as_prepared_unit_of_measure",
                "as_sold_per_serving_amount", "as_sold_unit_of_measure",
                "as_prepared_per_serving_amount_in_grams",
                "as_sold_per_serving_amount_in_grams", "package_comment",
                "package_source", "package_product_description",
                "number_of_units", "informed_dining_program",
                "product_grouping", "nielsen_item_rank", "nutrient_claims",
                "package_nielsen_category", "common_household_measure",
                "child_item", "package_classification_name", "edited_by",
                "package_classification_number", "package_product_id_fkey",
                "package_collection_date", "nft_last_update_date",
                "creation_date", "last_edit_date", "calculated" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks =
                (String) questionmarks.subSequence(0,
                        questionmarks.length() - 1);
         Object o =null;
        String query =
                SQL_INSERT.replaceFirst(TABLE_REGEX, schema + "." + "package");
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        final List<Object> packageInsertList =
                (List<Object>) queryMap.get("package_insert_list");

        // Returns the sales_id upon successful insert.
        try{
        	connection.setAutoCommit(false);
         o = executeUpdate(query, packageInsertList.toArray());
        } catch (final SQLException e)
        {
            logger.error(e);
          connection.rollback();
            throw new DaoException(e, ResponseCodes.INTERNAL_SERVER_ERROR);
        }
        
        final InsertPackageResponse insertPackageResponse =
                new InsertPackageResponse(ResponseCodes.OK.getCode(),
                        ResponseCodes.OK.getMessage());
        insertPackageResponse.setId(o);
        
        connection.commit();
        return insertPackageResponse;

    }

    /// =======

    public InsertPackageResponse getPackageUpdateResponse(
            final PackageUpdateRequest packageUpdateRequest)
            throws DaoException, SQLException
    {

        final Map<String, Object> queryMap =
                DaoUtil.getQueryMap(packageUpdateRequest);

        if (queryMap.isEmpty())
            return new InsertPackageResponse(
                    ResponseCodes.EMPTY_REQUEST.getCode(),
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            final Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new InsertPackageResponse(((ResponseCodes) o).getCode(),
                    ((ResponseCodes) o).getMessage());
        }

        // Check for valid classification_number.
        if (packageUpdateRequest.getClassification_number() != null)
            if (!checkClassification(
                    packageUpdateRequest.getClassification_number()))
                return new InsertPackageResponse(
                        ResponseCodes.INVALID_CLASSIFICATION_NUMBER.getCode(),
                        ResponseCodes.INVALID_CLASSIFICATION_NUMBER
                                .getMessage());

        final String[] columns =
        {
                "package_description", "package_brand", "package_manufacturer",
                "package_country", "package_size",
                "package_size_unit_of_measure", "storage_type",
                "storage_statements", "health_claims",
                "other_package_statements", "suggested_directions",
                "ingredients", "multi_part_flag", "nutrition_fact_table",
                "as_prepared_per_serving_amount", "as_prepared_unit_of_measure",
                "as_sold_per_serving_amount", "as_sold_unit_of_measure",
                "as_prepared_per_serving_amount_in_grams",
                "as_sold_per_serving_amount_in_grams", "package_comment",
                "package_source", "package_product_description",
                "number_of_units", "informed_dining_program",
                "product_grouping", "nielsen_item_rank", "nutrient_claims",
                "package_nielsen_category", "common_household_measure",
                "child_item", "package_classification_name", "edited_by",
                "package_classification_number", "package_collection_date",
                "nft_last_update_date", "last_edit_date", "calculated",
                "package_id" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks =
                (String) questionmarks.subSequence(0,
                        questionmarks.length() - 1);

        // String query = SQL_INSERT.replaceFirst(TABLE_REGEX,
        // schema + "." + "package");
        // query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        // query = query.replaceFirst(VALUES_REGEX, questionmarks);

        final String query =
                "update "
                        + schema + "." + "package set package_description = ?, "
                        + "package_brand = ?, " + "package_manufacturer = ?, "
                        + "package_country = ?, " + "package_size = ?, "
                        + "package_size_unit_of_measure = ?, "
                        + "storage_type = ?, " + "storage_statements = ?, "
                        + "health_claims = ?, "
                        + "other_package_statements = ?, "
                        + "suggested_directions = ?, " + "ingredients = ?, "
                        + "multi_part_flag = ?, " + "nutrition_fact_table = ?, "
                        + "as_prepared_per_serving_amount = ?, "
                        + "as_prepared_unit_of_measure = ?, "
                        + "as_sold_per_serving_amount = ?, "
                        + "as_sold_unit_of_measure = ?, "
                        + "as_prepared_per_serving_amount_in_grams = ?, "
                        + "as_sold_per_serving_amount_in_grams = ?, "
                        + "package_comment = ?, " + "package_source = ?, "
                        + "package_product_description = ?, "
                        + "number_of_units = ?, "
                        + "informed_dining_program = ?, "
                        + "product_grouping = ?, " + "nielsen_item_rank = ?, "
                        + "nutrient_claims = ?, "
                        + "package_nielsen_category = ?, "
                        + "common_household_measure = ?, " + "child_item = ?, "
                        + "package_classification_name = ?, "
                        + "edited_by = ?, "
                        + "package_classification_number = ?, "
                        + "package_collection_date = ?, "
                        + "nft_last_update_date = ?, " + "last_edit_date = ?, "
                        + "calculated = ? " + "where package_id= ? ";

        @SuppressWarnings("unchecked")
        final List<Object> packageUpdateList =
                (List<Object>) queryMap.get("package_update_list");

        executeUpdate(query, packageUpdateList.toArray());
        
        
        
        final InsertPackageResponse insertPackageResponse =
                new InsertPackageResponse(ResponseCodes.OK.getCode(),
                        ResponseCodes.OK.getMessage());
  
        
        getNftUpdateResponse(new NftRequest(true, getNft(packageUpdateRequest.getPackage_id(), true).getNft(), packageUpdateRequest.getPackage_id()));
        getNftUpdateResponse(new NftRequest(false, getNft(packageUpdateRequest.getPackage_id(), false).getNft(), packageUpdateRequest.getPackage_id()));

        return insertPackageResponse;

    }

    public PackageDataResponse getPackageResponse()
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        PackageResponse packageResponse = null;

        final PackageData data = new PackageData();

        final String query = "select * from " + schema + "." + "package";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
            {
                packageResponse = DaoUtil.getPackageResponse(resultSet);
                data.add(packageResponse);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new PackageDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new PackageDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    // ===

    public PackageViewResponse getPackageResponse(final Long packageId)
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        PackageViewData packageResponse = null;
        final PackageViewDataResponse data = new PackageViewDataResponse();

        final String query =
                "select * from "
                        + schema + "." + "package where package_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { packageId });

            if (resultSet.next())
            {
                packageResponse = DaoUtil.getPackageResponseView(resultSet);
                data.add(packageResponse);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new PackageViewResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new PackageViewResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    public PackageDataResponse getPackageResponse(
            final PackageRequest packageRequest)
            throws SQLException, IOException, Exception
    {
        final Map<String, Object> queryMap =
                DaoUtil.getQueryMap(packageRequest);

        if (queryMap.isEmpty())
            return new PackageDataResponse(
                    ResponseCodes.EMPTY_REQUEST.getCode(), null,
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            final Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new PackageDataResponse(((ResponseCodes) o).getCode(), null,
                    ((ResponseCodes) o).getMessage());
        }

        /// ===
        Integer number_of_records = null;
        String collectionDateFrom = "";
        String collectionDateTo = "";
        boolean a = false;
        boolean b = false;

        if (queryMap.containsKey("collection_date_from"))
        {
            collectionDateFrom = (String) queryMap.get("collection_date_from");
            queryMap.remove("collection_date_from");
            a = true;
        }

        if (queryMap.containsKey("collection_date_to"))
        {
            collectionDateTo = (String) queryMap.get("collection_date_to");
            queryMap.remove("collection_date_to");
            b = true;
        }

        /// ===

        ResultSet resultSet = null;
        ResultSet resultSetCount = null;

        PackageResponse packageResponse = null;
        final PackageData data = new PackageData();

        String query = "select * from " + schema + "." + "package";
        String query_count =
                "select count(*) AS COUNT from " + schema + "." + "package";

        // ===

        final String orderBy = packageRequest.orderBy;
        Integer offSet = packageRequest.offset;
        final boolean sortOrder = packageRequest.flag;

        String where_clause = "";
        int count = 0;
        String str;
        String sortDirection = null;

        final Iterator<String> keys = queryMap.keySet().iterator();
        final Iterator<String> keys_repeat = queryMap.keySet().iterator();

        while (keys.hasNext())
        {
            str = keys.next();

            if (count == 0)
                where_clause += " " + str + " LIKE ?";
            else
                where_clause += " AND " + str + " LIKE ?";

            ++count;
        }

        /// ===

        if (a && b)
            if (count == 0)
                where_clause += " package_collection_date BETWEEN ? AND ? ";
            else
                where_clause += " AND package_collection_date BETWEEN ? AND ? ";

        /// ===

        try
        {
            if ((where_clause != null) && (where_clause.length() > 0))
            {
                query += " where " + where_clause;
                query_count += " where " + where_clause;

            }

            if (sortOrder)
                sortDirection = "ASC";
            else
                sortDirection = "DESC";

            offSet = offSet * 10;
            query +=
                    " ORDER BY "
                            + orderBy + " " + sortDirection + " offset "
                            + offSet + " limit 10";

            final List<Object> objectList = new ArrayList<Object>();

            if (count > 0)
                while (keys_repeat.hasNext())
                {
                    str = keys_repeat.next();
                    objectList.add("%" + queryMap.get(str) + "%");
                }

            /// ===

            if (a && b)
            {
                objectList.add(java.sql.Date.valueOf(collectionDateFrom));
                objectList.add(java.sql.Date.valueOf(collectionDateTo));
            }

            /// ===

            resultSet = executeQuery(query, objectList.toArray());
            resultSetCount = executeQuery(query_count, objectList.toArray());
            resultSetCount.next();

            number_of_records = resultSetCount.getInt("COUNT");

            while (resultSet.next())
            {
                packageResponse = DaoUtil.getPackageResponse(resultSet);
                data.add(packageResponse);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new PackageDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        data.setCount(number_of_records);

        if (data.getCount() == 0)
            return new PackageDataResponse(
                    ResponseCodes.NO_DATA_FOUND.getCode(), null,
                    ResponseCodes.NO_DATA_FOUND.getMessage());

        return new PackageDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());

    }

    public Boolean checkClassification(final String classificationNumber)
            throws DaoException
    {
//        if ((classificationNumber == null))
//            return true;

        ResultSet resultSet = null;

        final String query =
                "select classification_id from "
                        + schema + "."
                        + "classification where classification_number = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { classificationNumber });

            if (resultSet.next())
                return true;
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return false;
    }

    public ComponentNameResponse getComponents() throws DaoException
    {
        ResultSet resultSet = null;
        final ComponentNameResponse componentList = new ComponentNameResponse();

        final String query =
                "select component_name from " + schema + "." + "component";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
                componentList.add(new ComponentName(
                        resultSet.getString("component_name")));
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }
        System.out.println("list " + componentList.toString());

        return componentList;
    }

    public Boolean checkForSamePackageUpcProductId(final String upc,
            final Integer productId) throws DaoException
    {
        final Integer packageProductId = checkByPackageUpc(upc);

        if (packageProductId == null)
            return true;

        if (!packageProductId.equals(productId))
            return false;

        return true;
    }

    public Integer checkByPackageUpc(final String packageUpc)
            throws DaoException
    {
        ResultSet resultSet = null;

        final String query =
                "select package_product_id_fkey from "
                        + schema + "." + "package where package_upc = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { packageUpc });

            if (resultSet.next())
                return resultSet.getInt("package_product_id_fkey");
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    public GenericList getListOfUnits() throws DaoException
    {

        final GenericList genericList = new GenericList();
        final String query =
                "select unit_of_measure_name from "
                        + schema + ".unit_of_measure";
        ;
        ResultSet resultSet = null;
        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
                genericList.getDataList()
                        .add(resultSet.getString("unit_of_measure_name"));

        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }
        return genericList;

    }

    private boolean insertNft(final NftRequest nftRequest, final String schema)
            throws DaoException, SQLException
    {

    	String per_serving_amount_place_holder = null;
    	String per_serving_amount_unit_of_measure_place_holder = null;
    	String per_serving_amount_in_grams_place_holder= null;


    	String sql = null;
    	if(nftRequest.getFlag() ==true){
    	 sql = "select as_sold_per_serving_amount, as_sold_unit_of_measure, as_sold_per_serving_amount_in_grams "
    			+ " from "+schema+".package where package_id =?";
    	 per_serving_amount_place_holder = "as_sold_per_serving_amount";
    	 per_serving_amount_unit_of_measure_place_holder = "as_sold_unit_of_measure";
    	 per_serving_amount_in_grams_place_holder = "as_sold_per_serving_amount_in_grams";
    	
    	}else{
        	 sql = "select as_prepared_per_serving_amount, as_prepared_unit_of_measure, as_prepared_per_serving_amount_in_grams "
        			+ " from "+schema+".package where package_id =?";
        	 
        	 per_serving_amount_place_holder = "as_prepared_per_serving_amount";
        	 per_serving_amount_unit_of_measure_place_holder = "as_prepared_unit_of_measure";
        	 per_serving_amount_in_grams_place_holder = "as_prepared_per_serving_amount_in_grams";
    	
    	}
    	ResultSet resultSet = null;
    	Double PerServingAmount = null;
    	String PerServingUnit = null;
    	Double PerServingInGrams = null;
    	
    	 
    	 System.out.println("Amount "+PerServingAmount + " Unit: "+PerServingUnit + " Grams: "+PerServingInGrams);
    	
        final String query =

                "insert into "
                        + schema + "."
                        + "product_component(component_id, package_id, amount,"
                        + " amount_unit_of_measure, percentage_daily_value, as_ppd_flag, amount_per100g) "
                        + "select component_id, ?, ?, ?, ?, ?, ? from " + schema
                        + ".component " + "where component_id = ("
                        + "select component_id from " + schema + "."
                        + "component where component_name= ?)";

        try
        {
            connection.setAutoCommit(false);
       	 resultSet = executeQuery(sql, new Object[]
                 { nftRequest.getPackage_id() });
     	
     	 resultSet.next();
     	 PerServingAmount = resultSet.getDouble(per_serving_amount_place_holder);
     	 PerServingUnit = resultSet.getString(per_serving_amount_unit_of_measure_place_holder);
     	 PerServingInGrams = resultSet.getDouble(per_serving_amount_in_grams_place_holder); 
     	
            for (final NftModel element : nftRequest.getNft())
            {
            	Double value = null;
                final PreparedStatement preparedStatement =
                        connection.prepareStatement(query);
                preparedStatement.setObject(1, nftRequest.getPackage_id());
                preparedStatement.setObject(2, element.getAmount());
                preparedStatement.setObject(3, element.getUnit_of_measure());
                preparedStatement.setObject(4, element.getDaily_value());
                preparedStatement.setObject(5, nftRequest.getFlag());
             
                
                if(element.getAmount() != null){
                	System.out.println("yes there is an amount: "+element.getAmount());
                	if(PerServingInGrams != null && PerServingInGrams != 0.0 && PerServingInGrams !=0){
                		
                    	System.out.println("yes there is an amount per 100g: "+PerServingInGrams);
                    	 value = (element.getAmount()/PerServingInGrams)*100;
                    	System.out.println("the value set is : "+value);

                		

                	}else if((PerServingAmount != null && PerServingAmount != 0 && PerServingAmount != 0.0) &&(PerServingUnit.equals("g"))){
                		value = (element.getAmount()/PerServingAmount)*100;
                		
                		
                	}
                }
                
                preparedStatement.setObject(6, value);
                preparedStatement.setObject(7, element.getName());
                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
            }

            connection.commit();
        }
        catch (final SQLException e)
        {
            // TODO Auto-generated catch block
            connection.rollback();
            e.printStackTrace();

            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return true;

    }

    private boolean updateNft(final NftRequest nftRequest, final String schema)
            throws DaoException, SQLException
    {
    	
    	String per_serving_amount_place_holder = null;
    	String per_serving_amount_unit_of_measure_place_holder = null;
    	String per_serving_amount_in_grams_place_holder= null;


    	String sql = null;
    	if(nftRequest.getFlag() ==true){
    	 sql = "select as_sold_per_serving_amount, as_sold_unit_of_measure, as_sold_per_serving_amount_in_grams "
    			+ " from "+schema+".package where package_id =?";
    	 per_serving_amount_place_holder = "as_sold_per_serving_amount";
    	 per_serving_amount_unit_of_measure_place_holder = "as_sold_unit_of_measure";
    	 per_serving_amount_in_grams_place_holder = "as_sold_per_serving_amount_in_grams";
    	
    	}else{
        	 sql = "select as_prepared_per_serving_amount, as_prepared_unit_of_measure, as_prepared_per_serving_amount_in_grams "
        			+ " from "+schema+".package where package_id =?";
        	 
        	 per_serving_amount_place_holder = "as_prepared_per_serving_amount";
        	 per_serving_amount_unit_of_measure_place_holder = "as_prepared_unit_of_measure";
        	 per_serving_amount_in_grams_place_holder = "as_prepared_per_serving_amount_in_grams";
    	
    	}
    	ResultSet resultSet = null;
    	Double PerServingAmount = null;
    	String PerServingUnit = null;
    	Double PerServingInGrams = null;

        final String query =
                "insert into "
                        + schema + "."
                        + "product_component(component_id, package_id, amount,"
                        + " amount_unit_of_measure, percentage_daily_value, as_ppd_flag, amount_per100g) "
                        + "select component_id, ?, ?, ?, ?, ?, ? from " + schema
                        + ".component " + "where component_id = ("
                        + "select component_id from " + schema + "."
                        + "component where component_name= ?)";

        try
        {
        	
            connection.setAutoCommit(false);
       	 resultSet = executeQuery(sql, new Object[]
                 { nftRequest.getPackage_id() });
     	
     	 resultSet.next();
     	 PerServingAmount = resultSet.getDouble(per_serving_amount_place_holder);
     	 PerServingUnit = resultSet.getString(per_serving_amount_unit_of_measure_place_holder);
     	 PerServingInGrams = resultSet.getDouble(per_serving_amount_in_grams_place_holder); 
     	
            for (final NftModel element : nftRequest.getNft())
            {
                // ecuteInsertNft(element, nftRequest.getPackage_id(), nftRequest.getFlag(), schema);
            	Double value = null;
                final PreparedStatement preparedStatement =
                        connection.prepareStatement(query);
                preparedStatement.setObject(1, nftRequest.getPackage_id());
                preparedStatement.setObject(2, element.getAmount());
                preparedStatement.setObject(3, element.getUnit_of_measure());
                preparedStatement.setObject(4, element.getDaily_value());
                preparedStatement.setObject(5, nftRequest.getFlag());
                
                
                
                
                if(element.getAmount() != null){
                	System.out.println("yes there is an amount: "+element.getAmount());
                	if(PerServingInGrams != null && PerServingInGrams != 0.0 && PerServingInGrams !=0){
                		
                    	System.out.println("yes there is an amount per 100g: "+PerServingInGrams);
                    	 value = (element.getAmount()/PerServingInGrams)*100;
                    	System.out.println("the value set is : "+value);

                		

                	}else if((PerServingAmount != null && PerServingAmount != 0 && PerServingAmount != 0.0) &&(PerServingUnit.equals("g"))){
                		value = (element.getAmount()/PerServingAmount)*100;
                		
                		
                	}
                }
                
                
                
                preparedStatement.setObject(6, value);
                preparedStatement.setObject(7, element.getName());
                preparedStatement.executeUpdate();
            }
            connection.commit();
        }
        catch (final SQLException e)
        {
            // TODO Auto-generated catch block
        	connection.rollback();
            e.printStackTrace();

            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }
        return true;

    }

    private NftView getNft(final String query, final Integer package_id,
            final Boolean flag) throws DaoException, SQLException
    {
        final NftView nftList = new NftView();
        ResultSet resultSet = null;

        try
        {

            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setObject(1, package_id);
            preparedStatement.setObject(2, flag);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                String name = resultSet.getString("component_name");
                name =
                        resultSet.wasNull()
                                ? null : resultSet.getString("component_name");

                Double amount = resultSet.getDouble("amount");
                amount =
                        resultSet.wasNull()
                                ? null : resultSet.getDouble("amount");
                System.out.println(
                        resultSet.getString("component_name") + ": " + amount);
               
                String amount_unit_of_measure =
                        resultSet.getString("amount_unit_of_measure");
                amount_unit_of_measure =
                        resultSet.wasNull()
                                ? null
                                : resultSet.getString("amount_unit_of_measure");

                Double percentage_daily_value =
                        resultSet.getDouble("percentage_daily_value");
                percentage_daily_value =
                        resultSet.wasNull()
                                ? null
                                : resultSet.getDouble("percentage_daily_value");
                
                Double amount_per100g =  resultSet.getDouble("amount_per100g");
                amount_per100g = resultSet.wasNull()? null: resultSet.getDouble("amount_per100g");
                // String name, Double amount, String unit_of_measure, Double daily_value
             
                nftList.getNft().add(new NftModel(name, amount,
                        amount_unit_of_measure, percentage_daily_value, amount_per100g));
            }

        }
        catch (final SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return null;
        }

        return nftList;
    }

    private int checkIfHasNft(final NftRequest nftRequest, final String schema,
            final Boolean flag) throws DaoException
    {

        ResultSet resultSetCount = null;
        int number_of_records = 0;

        final String query =
                "select count (*) AS COUNT from "
                        + schema + "."
                        + "product_component where as_ppd_flag = ? and package_id = ?";

        try
        {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setObject(1, flag);
            preparedStatement.setObject(2, nftRequest.getPackage_id());
            resultSetCount = preparedStatement.executeQuery();

            resultSetCount.next();
            number_of_records = resultSetCount.getInt("COUNT");
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return number_of_records;
    }
    
    public ResponseGeneric getLabelDeleteResponse(final Integer id)
            throws SQLException, IOException, Exception
    {

        final String  delete_components =
                "delete from " + schema + "." + "product_component where package_id = ?";

        final String sql =
                "delete from " + schema + "." + "package where package_id = ?";     
      
        try
        	{

        	 final Integer deletedRowdelete_components = (Integer)  executeUpdate(delete_components, new Object[]{ id });

            final Integer deletedRow = (Integer) executeUpdate(sql, new Object[]{ id });
            

            
            if (deletedRow == 0){
                return new ResponseGeneric(
                        777,
                        "Cannot delete record");
            }
          
        }
        catch (final Exception e)
        {
            logger.error(e);
         
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }
   
        
        return new ResponseGeneric(ResponseCodes.OK.getCode(),
                ResponseCodes.OK.getMessage());
    }

	public void importLabel(String filePath) {
		final StopWatch stopWatch = new StopWatch();
		
		stopWatch.start();
		System.out.println("Program has started");

		
		start(filePath);

		stopWatch.split();
		System.out.println(
				"Total time spent on loading the sales data: " + (stopWatch.getSplitTime() / 1000) + " seconds.");

		
	}

	private void start(String file) {
		
		Map<String, List<ExistingLabels>> existingLabels = new HashMap<String,List<ExistingLabels>>();
		Map<String, List<ImportLabelModel>> dataFromTempByUpc = new HashMap<String,List<ImportLabelModel>>();
		Map<String, List<ExistingLabels>> existingLabelStoredByUPC = new HashMap<String,List<ExistingLabels>>();
		Map<String, Integer> mapUpcImRank = new HashMap<String, Integer>();
		List<ImportLabelModel> uniqueLabels = new ArrayList<ImportLabelModel>();
		Map<Double, List<ImportLabelModel>> labelSortedByGrouping = new HashMap<Double, List<ImportLabelModel>>();
		int total = 0;
		try {
			emptyTempTable();
			loadData( file);
			existingLabelStoredByUPC = getExistingLabels(existingLabels);
			getDataFromTemp(existingLabels,dataFromTempByUpc);
			for(Map.Entry<String, List<ImportLabelModel>> entry : dataFromTempByUpc.entrySet()){
				
				total+=entry.getValue().size();
			}
			
			labelUpcMatch(dataFromTempByUpc,existingLabelStoredByUPC);
			mapUpcImRank = getUpcRank();
			try {
				labelSortedByGrouping = labelByUpcWithinFile(uniqueLabels, dataFromTempByUpc, mapUpcImRank);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			buildByGrouping(labelSortedByGrouping,uniqueLabels);
			buildQueryUniqueLabel(uniqueLabels);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Number of existing label "+existingLabels.size());

		
		
		
//		for (List<ImportLabelModel> list : dataFromTempByUpc.values()) {
//		    total += list.size();
//		}
		
		

		
		System.out.println("Number of valid records in the file "+total);

		
	}

	private Map<String, List<ExistingLabels>> getExistingLabels(Map<String, List<ExistingLabels>> data) {
		
		Map<String, List<ExistingLabels>> existingLabelStoredByUPC = new HashMap<String,List<ExistingLabels>>();

		
		
		ResultSet resultSet = null;
		String sql = "select package_id, package_upc, "
				+ " package_description, product_grouping,  package_source, package_collection_date from " + schema + ".package";
		
		try {
			ExistingLabels existingLabel;
			resultSet = executeQuery(sql, null);
			try {
				while (resultSet.next()) {
					
					Integer package_id = resultSet.getInt("package_id");
					String label_description = resultSet.getString("package_description");
					String label_source  = resultSet.getString("package_source");
					Date label_collection_date = resultSet.getDate("package_collection_date");
					String label_upc = resultSet.getString("package_upc");
					Double product_grouping = resultSet.getDouble("product_grouping");
					
					existingLabel = new ExistingLabels(package_id,label_upc, label_description, label_source,label_collection_date,product_grouping );
					
					String sb = existingLabel.getLabel_description()
								+existingLabel.getLabel_upc()
								+existingLabel.getLabel_source()
								+(existingLabel.getLabel_collection_date()!=null?existingLabel.getLabel_collection_date().toString():"null");
					
								//System.out.println("**"+sb);
								
								if (!data.containsKey(sb)) {
									List<ExistingLabels> item = new ArrayList<>();
									item.add(existingLabel);
									data.put(sb, item);
									if(!existingLabelStoredByUPC.containsKey(existingLabel.getLabel_upc())){
										existingLabelStoredByUPC.put(existingLabel.getLabel_upc(), item);
									}
								
								}
								
								
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existingLabelStoredByUPC;
	}
	public void loadData(String csvFile) throws SQLException{
		
		String sql = "COPY "+schema+".label_temp "+
		"	( "+
			 " record_id , "+
			 " label_upc, "+
			 " nielsen_item_rank, "+
			"  nielsen_category, "+
			 " label_description, "+
			 " label_brand, "+
			 " label_manufacturer, "+
			 " label_country, "+
			 " package_size, "+
			 " package_size_unit_of_measure, "+
			"  number_of_units, "+
			 " storage_type, "+
			 " storage_statement, "+
			 " collection_date, "+
			 " health_claims, "+
			 " nutrient_claims, "+
			 " other_package_statements, "+
			 " suggested_directions, "+
			 " ingredients, "+
			 " multipart, " +
			 " nutrition_fact_table, "+
			 " common_household_measure, "+
			 " per_serving_amount_as_sold, "+
			 " per_serving_amount_unit_of_measure_as_sold, "+
			 " per_serving_amount_as_prepared, "+
			 " per_serving_amount_unit_of_measure_as_prepared, "+
			 " energy_kcal_as_sold, "+
			 " energy_kcal_as_prepared, "+
			 " energy_kj_as_sold, "+
			 " energy_kj_as_prepared, "+
			 " fat_as_sold, "+
			 " fat_daily_value_as_sold, "+
			 " fat_daily_value_as_prepared, "+
			 "  saturated_fat_as_sold, "+
			 " trans_fat_as_sold, "+
			 " saturated_plus_trans_daily_value_as_sold,  "+
			 " saturated_plus_trans_daily_value_as_prepared, "+
			 " fat_polyunsatured_as_sold, "+
			 " fat_polyunsaturated_omega_6_as_sold, "+
			 " fat_polyunsaturated_omega_3_as_sold, "+
			 " fat_monounsaturated_as_sold, "+
			 " carbohydrates_as_sold, "+
			 " carbohydrates_daily_value_as_sold, "+
			 " carbohydrates_daily_value_as_prepared, "+
			 " fibre_as_sold, "+
			 " fibre_daily_value_sold, "+
			 "  fibre_daily_value_as_prepared, "+
			 " soluble_fibre_as_sold, "+
			 " insoluble_fibre_as_sold, "+
			 " sugar_total_sold, "+
			 " sugar_total_daily_value_as_sold, "+
			 " sugar_total_daily_value_as_prepared, "+
			 " sugar_alcohols_as_sold, "+
			 " starch_as_sold, "+
			 " protein_as_sold, "+
			 " cholesterol_as_sold, "+
			 " cholesterol_daily_value_as_sold, "+
			 " cholesterol_daily_value_as_prepared, "+
			 " sodium_as_sold, "+
			 " sodium_daily_value_as_sold, "+
			 " sodium_daily_value_as_prepared, "+
			 " potassium_as_sold, "+
			 " potassium_daily_value_as_sold, "+
			 " potassium_daily_value_as_prepared, "+
			 " calcium_as_sold, "+
			 " calcium_daily_value_as_sold, "+
			 " calcium_daily_value_as_prepared, "+
			 " iron_as_sold, "+
			 " iron_daily_value_as_sold, "+
			 " iron_daily_value_as_prepared, "+
			 " vitamin_a_as_sold, "+
			 " vitamin_a_daily_value_as_sold, "+
			 " vitamin_a_daily_value_as_prepared, "+
			 " vitamin_c_as_sold, "+
			 " vitamin_c_daily_value_as_sold, "+
			 " vitamin_c_daily_value_as_prepared, "+
			 " vitamin_d_as_sold, "+
			 " vitamin_d_daily_value_as_sold, "+
			 " vitamin_d_daily_value_as_prepared, "+
			 " vitamin_e_as_sold, "+
			 " vitamin_e_daily_value_as_sold, "+
			 " vitamin_e_daily_value_as_prepared,  	 "+
			 " vitamin_k_as_sold, "+
			 " vitamin_k_daily_value_as_sold, "+
			 " vitamin_k_daily_value_as_prepared, "+  
			 " thiamine_as_sold, "+
			 " thiamine_daily_value_as_sold, "+
			 " thiamine_daily_value_as_prepared,  "+
			 " riboflavin_as_sold, "+
			 " riboflavin_daily_value_as_sold, "+
			 " riboflavin_daily_value_as_prepared,   "+
			 " niacin_as_sold, "+
			 " niacin_daily_value_as_sold, "+
			 " niacin_daily_value_as_prepared,   "+
			 " vitamin_b6_as_sold, "+
			 " vitamin_b6_daily_value_as_sold, "+
			 " vitamin_b6_daily_value_as_prepared,   "+
			 " folate_as_sold, "+
			 " folate_daily_value_as_sold, "+
			 " folate_daily_value_as_prepared,   "+
			 " vitamin_b12_as_sold, "+
			 " vitamin_b12_daily_value_as_sold, "+
			 " vitamin_b12_daily_value_as_prepared,  "+
			 " biotin_as_sold, "+
			 " biotin_daily_value_as_sold, "+
			 " biotin_daily_value_as_prepared, "+  
			 " choline_as_sold, "+
			 " choline_daily_value_as_sold, "+
			 " choline_daily_value_as_prepared,    "+  
			 " pantothenate_as_sold, "+
			 " pantothenate_daily_value_as_sold, "+
			 " pantothenate_daily_value_as_prepared, "+   
			 " phosphorus_as_sold, "+
			 " phosphorus_daily_value_as_sold, "+
			 " phosphorus_daily_value_as_prepared,   "+
			 
			 " iodide_as_sold, "+
			 " iodide_daily_value_as_sold, "+
			 " iodide_daily_value_as_prepared,   "+
			 
			 " magnesium_as_sold, "+
			 " magnesium_daily_value_as_sold, "+
			 " magnesium_daily_value_as_prepared,   "+ 
			 " zinc_as_sold, "+
			 " zinc_daily_value_as_sold, "+
			 " zinc_daily_value_as_prepared,    "+
			 " selenium_as_sold, "+
			 " selenium_daily_value_as_sold, "+
			 "  selenium_daily_value_as_prepared,   "+
			 " copper_as_sold, "+
			 " copper_daily_value_as_sold, "+
			 " copper_daily_value_as_prepared,    "+
			 "  manganese_as_sold, "+
			 " manganese_daily_value_as_sold, "+
			 " manganese_daily_value_as_prepared,   "+
			 " chromium_as_sold, "+
			 " chromium_daily_value_as_sold, "+
			 " chromium_daily_value_as_prepared,  "+
			 " molybdenum_as_sold, "+
			 " molybdenum_daily_value_as_sold, "+
			 " molybdenum_daily_value_as_prepared,  "+
			 " chloride_as_sold, "+
			 " chloride_daily_value_as_sold, "+
			 " chloride_daily_value_as_prepared,  "+
			 " per_serving_amount_in_grams_as_sold, "+
			 " per_serving_amount_in_grams_as_prepared, "+
			 " label_comment, "+
			 " label_source, "+
			 " product_description, "+
			 " type, "+
			 " type_of_restaurant, "+
			 " informed_dining_program, "+
			 " nft_last_update_date, "+
			 " child_item, "+
			 " product_grouping, "+
			 " classification_number ) "+
			  "FROM '" + csvFile + "'  CSV HEADER DELIMITER ','";
		
		Statement stmt = connection.createStatement();

		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void emptyTempTable() throws SQLException{
		
		Statement statement = connection.createStatement();
		
		String sql = "truncate "+schema+".label_temp";
		
		 
		statement.executeUpdate(sql);
	
	}
	
	public void getDataFromTemp(Map<String, List<ExistingLabels>> labelInDb, Map<String, List<ImportLabelModel>> dataFromTempByUpc) throws SQLException{
		
		Map<String, ImportLabelModel> data = new HashMap<String,ImportLabelModel>();
		Map<Double, String> invalidLabels = new HashMap<Double, String>();
		Map<Double, String> duplicatesLabels = new HashMap<Double, String>();
		
		//Map<String, List<ImportLabelModel>> dataFromTempByUpc = new HashMap<String,List<ImportLabelModel>>();

		int count = 0;
		String sql = "select * from " + schema + ".label_temp";
		ResultSet resultSet = null;
		ImportLabelModel importLabelModel;
		
		try {
			resultSet = executeQuery(sql, null);
			
			while(resultSet.next()){
				++count;
				importLabelModel = DaoUtil.populateLabelModel( resultSet);
				String sb = importLabelModel.getImportLabelRequest().getPackage_description()+ 
							importLabelModel.getImportLabelRequest().getPackage_upc() +
							importLabelModel.getImportLabelRequest().getPackage_source() +
							importLabelModel.getImportLabelRequest().getPackage_collection_date();
				
				
				if(importLabelModel.isValid()){
					
					if(labelInDb.containsKey(sb) || data.containsKey(sb)){
						//System.out.println("Yes dup");

						duplicatesLabels.put(importLabelModel.getImportLabelRequest().getRecord_id(), importLabelModel.getImportLabelRequest().getPackage_description());
						

					}else{			
						

						data.put(sb, importLabelModel);
						if(dataFromTempByUpc.containsKey(importLabelModel.getImportLabelRequest().getPackage_upc())){
							//append
							dataFromTempByUpc.get(importLabelModel.getImportLabelRequest().getPackage_upc()).add(importLabelModel);
						
						}else{
							//new element in List
							 List<ImportLabelModel> item = new ArrayList<>();
							 item.add(importLabelModel);
							 dataFromTempByUpc.put(importLabelModel.getImportLabelRequest().getPackage_upc(), item);
								//System.out.println("Neew key");
		
						}
						
					}
					
					
				}else{

					invalidLabels.put(importLabelModel.getImportLabelRequest().getRecord_id(), importLabelModel.getImportLabelRequest().getPackage_description());
					
				}
			}
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	System.out.println("Number of invalid records: "+invalidLabels.size());
	
	}


public void labelUpcMatch(Map<String, List<ImportLabelModel>> dataFromTempByUpc,Map<String, List<ExistingLabels>> existingLabelsByUpc ){
		
	Map<Integer, List<ImportLabelModel>> insertLabelUpcMatch =new  HashMap<Integer, List<ImportLabelModel>>();
		
	for (final Iterator<Entry<String, List<ImportLabelModel>>> it = dataFromTempByUpc.entrySet().iterator(); it
			.hasNext();) {

		Entry<String, List<ImportLabelModel>> entry = it.next();		
		List<ImportLabelModel> list = new ArrayList<ImportLabelModel>();// entry.getValue();
		if(existingLabelsByUpc.containsKey(entry.getKey())){
			
			//Validate same UPC different product grouping
			
			final List<ImportLabelModel> v = entry.getValue();

			for (final ImportLabelModel element : v) {
				
				if(element.getImportLabelRequest().getProduct_grouping() !=existingLabelsByUpc.get(entry.getKey()).get(0).getProduct_grouping()){
					//Report
				}else{
					list.add(element);
				}
			}
			
			
			insertLabelUpcMatch.put(existingLabelsByUpc.get(entry.getKey()).get(0).getproduct_id(), list);
			it.remove();
		}
		
		
		
		
	}
		buildQueryUpcMatchInDB(insertLabelUpcMatch);
	
	}


private void buildQueryUpcMatchInDB(Map<Integer, List<ImportLabelModel>> insertLabelUpcMatch) {
	
	for(final Iterator<Entry<Integer, List<ImportLabelModel>>> it = insertLabelUpcMatch.entrySet().iterator(); it.hasNext(); ){
		
	
				Entry<Integer, List<ImportLabelModel>> entry = it.next();
				final List<ImportLabelModel> v = entry.getValue();

				
					
					try {
						for (final ImportLabelModel element : v) {
							
						int  package_id = createLabel(entry.getKey(),element.getImportLabelRequest());
						try {
							insertNftImport(element, package_id);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
						
					} catch (DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
	}
	

	
}

public Map<Double, List<ImportLabelModel>>  labelByUpcWithinFile(List<ImportLabelModel> uniqueLabels , Map<String, List<ImportLabelModel>> dataFromTempByUpc, Map<String, Integer> mapUpcImRank) throws DaoException{
	
	
	Map<Double, List<ImportLabelModel>> labelSortedByGrouping =new  HashMap<Double, List<ImportLabelModel>>();

	for (final Iterator<Entry<String, List<ImportLabelModel>>> it = dataFromTempByUpc.entrySet().iterator(); it
			.hasNext();) {

		Entry<String, List<ImportLabelModel>> entry = it.next();	
		
		if(entry.getValue().size()>1){
			
			insertLabelGroupedyUpcWithinWhile(entry.getValue());
			
			
		}else{
			
			final List<ImportLabelModel> v = entry.getValue();

			for (final ImportLabelModel element : v) {
				
				if(element.getImportLabelRequest().getProduct_grouping()!= null){
					
					if(labelSortedByGrouping.containsKey(element.getImportLabelRequest().getProduct_grouping())){
						
						labelSortedByGrouping.get(element.getImportLabelRequest().getProduct_grouping()).add(element);
						
						
					}else{
						
						List<ImportLabelModel> item = new ArrayList<>();
						item.add(element);
						labelSortedByGrouping.put(element.getImportLabelRequest().getProduct_grouping(), item);	
						
					}

					
				}else if(element.getImportLabelRequest().getNielsen_item_rank()!= null && mapUpcImRank.containsKey(element.getImportLabelRequest().getNielsen_item_rank())){
					//If Nielsen Item rank exists in db remove it from the map 
					
					try {
						Map<String, Integer> upcItem = getUpcRank();
						
						int package_id = createLabel(upcItem.get(element.getImportLabelRequest().getNielsen_item_rank()), element.getImportLabelRequest());
						insertNftImport(element, package_id);	
						
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
				}else{
					
					uniqueLabels.add(element);
				}
				
			}
			
		}
		it.remove();
	}
	
	return labelSortedByGrouping;
	
}

private void insertLabelGroupedyUpcWithinWhile(List<ImportLabelModel> value) {
	// TODO Auto-generated method stub
	try {
		int product_id =	insertProducts(value.get(0).getImportLabelRequest());
		//TODO add classification number, check consistency between product description, manufacturer, class, etc...
		if(value.get(0).getImportLabelRequest().getClassification_number() !=null && !value.get(0).getImportLabelRequest().getClassification_number().isEmpty()){
			if(checkClassification(value.get(0).getImportLabelRequest().getClassification_number())){
				//System.out.println("oui called 2"+element.getImportLabelRequest().getClassification_number());
				insertClassificationNumber(value.get(0).getImportLabelRequest().getClassification_number(), product_id);
			}
		}
		for(ImportLabelModel importLabelModel: value){
			
			int package_id = createLabel(product_id,importLabelModel.getImportLabelRequest());
			insertNftImport(importLabelModel, package_id);
		}
	} catch (DaoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

public void buildByGrouping(Map<Double, List<ImportLabelModel>> labelSortedByGrouping, List<ImportLabelModel> uniqueLabels){
	
	for (final Iterator<Entry<Double, List<ImportLabelModel>>> it = labelSortedByGrouping.entrySet().iterator(); it
			.hasNext();) {

		Entry<Double, List<ImportLabelModel>> entry = it.next();	
		
		if(entry.getValue().size()>1){
			

			try {
			int product_id =	insertProducts(entry.getValue().get(0).getImportLabelRequest());
			
			if(entry.getValue().get(0).getImportLabelRequest().getClassification_number() !=null && !entry.getValue().get(0).getImportLabelRequest().getClassification_number().isEmpty()){
				if(checkClassification(entry.getValue().get(0).getImportLabelRequest().getClassification_number())){
					System.out.println("oui called 2"+entry.getValue().get(0).getImportLabelRequest().getClassification_number());
					insertClassificationNumber(entry.getValue().get(0).getImportLabelRequest().getClassification_number(), product_id);
				}
			}
			//TODO add classification number, check consistency between product description, manufacturer, class, etc...
			
			for(ImportLabelModel importLabelModel: entry.getValue()){
				
				int package_id = createLabel(product_id,importLabelModel.getImportLabelRequest());
				insertNftImport(importLabelModel, package_id);
				
			}
			
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
			uniqueLabels.add(entry.getValue().get(0));
		}
	
	}
	}
	


	public Map<String, Integer> getUpcRank() throws SQLException{
		ResultSet resultSet = null;
		Map<String, Integer> upcItem = new HashMap<String, Integer>();
		String sql = "select distinct sales_product_id_fkey, sales_upc from "+schema+".sales";
		
		try {
			resultSet = executeQuery(sql, null);
			while(resultSet.next()){
				Integer product_id = resultSet.getInt("sales_product_id_fkey");
				String sales_upc = resultSet.getString("sales_upc");
				
				if(!upcItem.containsKey(product_id)){
					upcItem.put(sales_upc, product_id);
				}
			}
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return upcItem;
	
	}
	
	public void buildQueryUniqueLabel(List<ImportLabelModel> uniqueLabels){
		
		Map<Integer, List<ImportLabelNft>> nft = new HashMap<Integer, List<ImportLabelNft>>();
		
		for(ImportLabelModel element: uniqueLabels){
			
			try {
				int id = insertProducts(element.getImportLabelRequest());

				if(element.getImportLabelRequest().getClassification_number() !=null && !element.getImportLabelRequest().getClassification_number().isEmpty()){
					if(checkClassification(element.getImportLabelRequest().getClassification_number())){
						//System.out.println("oui called 2"+element.getImportLabelRequest().getClassification_number());
						insertClassificationNumber(element.getImportLabelRequest().getClassification_number(), id);
					}
				}
				int label_id = createLabel(id,element.getImportLabelRequest());
				nft.put(label_id, element.getNftList());
				insertNftImport(element,label_id);
				
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	private int createLabel(int product_id, ImportLabelRequest importLabelRequest) throws DaoException {
		
//		String query = "insert into "+schema+".package (package_product_id_fkey,package_description,package_upc,"
//				+ " package_brand, package_manufacturer,package_country,package_size,package_size_unit_of_measure, "
//				+ "storage_type,storage_statements,health_claims,other_package_statements,suggested_directions, "
//				+ "ingredients , multi_part_flag, nutrition_fact_table,  as_prepared_per_serving_amount,  as_prepared_unit_of_measure, "
//				+ "as_sold_per_serving_amount,as_sold_unit_of_measure,as_prepared_per_serving_amount_in_grams, "
//				+ "as_sold_per_serving_amount_in_grams, package_comment, package_source, package_product_description, "
//				+ "package_collection_date, number_of_units,edited_by,informed_dining_program,nft_last_update_date, "
//				+ "product_grouping, child_item,classification_number,classification_name, nielsen_item_rank, "
//				+ " nutrient_claims,package_nielsen_category, common_household_measure,creation_date, "
//				+ " last_edit_date,calculated ) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
//				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? )";

	      final String[] columns =
	          {
	                  "package_description", "package_upc", "package_brand",
	                  "package_manufacturer", "package_country", "package_size",
	                  "package_size_unit_of_measure", "storage_type",
	                  "storage_statements", "health_claims",
	                  "other_package_statements", "suggested_directions",
	                  "ingredients", "multi_part_flag", "nutrition_fact_table",
	                  "as_prepared_per_serving_amount", "as_prepared_unit_of_measure",
	                  "as_sold_per_serving_amount", "as_sold_unit_of_measure",
	                  "as_prepared_per_serving_amount_in_grams",
	                  "as_sold_per_serving_amount_in_grams", "package_comment",
	                  "package_source", "package_product_description",
	                  "number_of_units", "informed_dining_program",
	                  "product_grouping", "nielsen_item_rank", "nutrient_claims",
	                  "package_nielsen_category", "common_household_measure",
	                  "child_item", "package_classification_name", "edited_by",
	                  "package_classification_number", "package_product_id_fkey",
	                  "package_collection_date", "nft_last_update_date",
	                  "creation_date", "last_edit_date", "calculated" };
	      
		
		
		
        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks =
                (String) questionmarks.subSequence(0,
                        questionmarks.length() - 1);
         //int package_id = 0;
        String query =
                SQL_INSERT.replaceFirst(TABLE_REGEX, schema + "." + "package");
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        final List<Object> packageInsertList =DaoUtil.getQueryMap(importLabelRequest,product_id );
      
        	int package_id = (int) executeUpdate(query, packageInsertList.toArray());
        	
        	
        	
        	
		
		
		return package_id;
	}
	
	public int  insertProducts(ImportLabelRequest item) throws DaoException, SQLException{
		
		final Timestamp now = DateUtil.getCurrentTimeStamp();
		
		String sql = "insert into "+schema+".product (product_manufacturer, product_description, "
				+ "cluster_number, product_brand, creation_date, last_edit_date, type, restaurant_type) values (?, ?, ?, ?, ?, ?, ?, ?) ";

	
			String description = item.getPackage_product_description()!=null?item.getPackage_product_description(): item.getPackage_description();
				 int obj = (int) executeUpdate(sql, new Object[]{item.getPackage_manufacturer(),description,null,
						 item.getPackage_brand(), now, now, item.getType(), item.getType_of_restaurant()});
	            

		
		return obj;

		
	}
	
	
	public Boolean insertClassificationNumber(String classification_number, Integer product_id) {

		final String query1 = "insert into " + schema + "."
				+ "product_classification (product_classification_classification_id_fkey, product_classification_product_id_fkey) "
				+ " select classification_id, ? from " + schema + ".classification where " + "classification_number = ?";

		try {
			executeUpdate(query1, new Object[] { product_id, classification_number });
			return true;
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
    private boolean insertNftImport(ImportLabelModel importLabelModel, Integer package_id)
            throws DaoException, SQLException
    {


    	
    	Double PerServingAmount = null;
    	String PerServingUnit = null;
    	Double PerServingInGrams = null;
    	
    	 
    	
        final String query =

                "insert into "
                        + schema + "."
                        + "product_component(component_id, package_id, amount,"
                        + " amount_unit_of_measure, percentage_daily_value, as_ppd_flag, amount_per100g) "
                        + "select component_id, ?, ?, ?, ?, ?, ? from " + schema
                        + ".component " + "where component_id = ("
                        + "select component_id from " + schema + "."
                        + "component where component_name= ?)";

        try
        {
            connection.setAutoCommit(false);
//       	 resultSet = executeQuery(sql, new Object[]
//                 { nftRequest.getPackage_id() });
//     	
//     	 resultSet.next();
//     	 PerServingAmount = resultSet.getDouble(per_serving_amount_place_holder);
//     	 PerServingUnit = resultSet.getString(per_serving_amount_unit_of_measure_place_holder);
//     	 PerServingInGrams = resultSet.getDouble(per_serving_amount_in_grams_place_holder); 
     	
            for (final ImportLabelNft element :importLabelModel.getNftList())
            {
            	
            	
            	if(element.getFlag()){
                	 PerServingAmount = importLabelModel.getImportLabelRequest().getAs_sold_per_serving_amount();
                 	 PerServingUnit = importLabelModel.getImportLabelRequest().getAs_sold_unit_of_measure();
                 	 PerServingInGrams = importLabelModel.getImportLabelRequest().getAs_sold_per_serving_amount_in_grams(); 
            		
            	}else{
               	 PerServingAmount = importLabelModel.getImportLabelRequest().getAs_prepared_per_serving_amount();
             	 PerServingUnit = importLabelModel.getImportLabelRequest().getAs_prepared_unit_of_measure();
            	PerServingInGrams = importLabelModel.getImportLabelRequest().getAs_prepared_per_serving_amount_in_grams(); 

            	}
            	
            	
            	
            	Double value = null;
                final PreparedStatement preparedStatement =
                        connection.prepareStatement(query);
                preparedStatement.setObject(1, package_id);
                preparedStatement.setObject(2, element.getAmount());
                preparedStatement.setObject(3, element.getUnit_of_measure());
                preparedStatement.setObject(4, element.getDaily_value());
                preparedStatement.setObject(5, element.getFlag());
             
                
                if(element.getAmount() != null){
                	if(PerServingInGrams != null && PerServingInGrams != 0.0 && PerServingInGrams !=0){
                		
                    	 value = (element.getAmount()/PerServingInGrams)*100;
                    	

                		

                	}else if((PerServingAmount != null && PerServingAmount != 0 && PerServingAmount != 0.0) &&(PerServingUnit.equals("g"))){
                		value = (element.getAmount()/PerServingAmount)*100;
                		
                		
                	}
                }
                
                preparedStatement.setObject(6, value);
                preparedStatement.setObject(7, element.getcomponent_name());
                preparedStatement.executeUpdate();
            }

            connection.commit();
        }
        catch (final SQLException e)
        {
            // TODO Auto-generated catch block
            connection.rollback();
            e.printStackTrace();

            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return true;

    }
}



