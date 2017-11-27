package hc.fcdr.rws.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.domain.Package;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.pkg.InsertPackageResponse;
import hc.fcdr.rws.model.pkg.PackageData;
import hc.fcdr.rws.model.pkg.PackageDataResponse;
import hc.fcdr.rws.model.pkg.PackageInsertRequest;
import hc.fcdr.rws.model.pkg.PackageRequest;
import hc.fcdr.rws.model.pkg.PackageResponse;
import hc.fcdr.rws.model.sales.SalesInsertDataResponse;
import hc.fcdr.rws.model.sales.SalesInsertRequest;
import hc.fcdr.rws.util.DaoUtil;

public class PackageDao extends PgDao
{
    private static final Logger logger = Logger.getLogger(
            PackageDao.class.getName());
    private final String        schema;
    private static final String SQL_INSERT   = "insert into ${table}(${keys}) values(${values})";
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

        final String query = "select * from " + schema + "."
                + "package where package_id = ?";

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

    public InsertPackageResponse getPackageInsertResponse(PackageInsertRequest packageInsertRequest) throws DaoException
    {
    	
    	SalesDao salesDao = null;
        final Map<String, Object> queryMap = DaoUtil.getQueryMap(
        		packageInsertRequest);

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
        if (!salesDao.checkClassification(packageInsertRequest.getPackage_classification_number()))
            return new InsertPackageResponse(
                    ResponseCodes.INVALID_CLASSIFICATION_NUMBER.getCode(),
                    ResponseCodes.INVALID_CLASSIFICATION_NUMBER.getMessage());



        final String[] columns =
        { "package_description", "package_upc", "sales_brand", "package_manufacturer",
                "package_country", "package_size", "package_size_unit_of_measure",
                "storage_type", "storage_statements", "other_package_statements",
                "suggested_directions", "ingredients","multi_part_flag",
                "nutrition_fact_table", "as_prepared_per_serving_amount", "as_prepared_unit_of_measure",
                "as_sold_per_serving_amount", "as_sold_unit_of_measure", "as_prepared_per_serving_amount_in_grams",
                "as_sold_per_serving_amount_in_grams", "package_comment", "package_source", "package_product_description",
                "package_collection_date", "number_of_units", "edited_by", "informed_dining_program","nft_last_update_date",
                "product_grouping", "child_item", "package_classification_number", "package_classification_name", "nielsen_item_rank",
                "nutrient_claims", "package_nielsen_category", "common_household_measure",
                "creation_date", "last_edit_date", "package_product_id_fkey" };

        
        
        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks = (String) questionmarks.subSequence(0,
                questionmarks.length() - 1);

        String query = SQL_INSERT.replaceFirst(TABLE_REGEX,
                schema + "." + "sales");
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        final List<Object> packageInsertList = (List<Object>) queryMap.get(
                "package_insert_list");

        // Returns the sales_id upon successful insert.
        final Object o = executeUpdate(query, packageInsertList.toArray());

        return new InsertPackageResponse(ResponseCodes.OK.getCode(),
                ResponseCodes.OK.getMessage());
    }
    ///=======
    
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

    public PackageDataResponse getPackageResponse(final Long packageId)
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        PackageResponse packageResponse = null;

        final PackageData data = new PackageData();

        final String query = "select * from " + schema + "."
                + "package where package_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { packageId });

            if (resultSet.next())
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

    public PackageDataResponse getPackageResponse(
            final PackageRequest packageRequest)
            throws SQLException, IOException, Exception
    {
        final Map<String, Object> queryMap = DaoUtil.getQueryMap(
                packageRequest);

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
        PackageResponse packageResponse = null;
        final PackageData data = new PackageData();

        String query = "select * from " + schema + "." + "package";

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
                query += " where " + where_clause;

            if (sortOrder)
                sortDirection = "ASC";
            else
                sortDirection = "DESC";

            offSet = offSet * 10;
            query += " ORDER BY " + orderBy + " " + sortDirection + " offset "
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

        if (data.getCount() == 0)
            return new PackageDataResponse(
                    ResponseCodes.NO_DATA_FOUND.getCode(), null,
                    ResponseCodes.NO_DATA_FOUND.getMessage());

        return new PackageDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());

    }
    

}