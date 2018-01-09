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
import hc.fcdr.rws.domain.Sales;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.sales.SalesData;
import hc.fcdr.rws.model.sales.SalesDataResponse;
import hc.fcdr.rws.model.sales.SalesDataResponseShort;
import hc.fcdr.rws.model.sales.SalesDataShort;
import hc.fcdr.rws.model.sales.SalesDeleteDataResponse;
import hc.fcdr.rws.model.sales.SalesInsertDataResponse;
import hc.fcdr.rws.model.sales.SalesInsertRequest;
import hc.fcdr.rws.model.sales.SalesRequest;
import hc.fcdr.rws.model.sales.SalesResponse;
import hc.fcdr.rws.model.sales.SalesResponseShort;
import hc.fcdr.rws.model.sales.SalesUpdateDataResponse;
import hc.fcdr.rws.model.sales.SalesUpdateRequest;
import hc.fcdr.rws.model.sales.SalesYearsData;
import hc.fcdr.rws.model.sales.SalesYearsDataResponse;
import hc.fcdr.rws.model.sales.SalesYearsResponse;
import hc.fcdr.rws.util.DaoUtil;

public class SalesDao extends PgDao
{
    private static final Logger logger       = Logger.getLogger(
            SalesDao.class.getName());
    private final String        schema;

    private static final String SQL_INSERT   = "insert into ${table}(${keys}) values(${values})";
    private static final String TABLE_REGEX  = "\\$\\{table\\}";
    private static final String KEYS_REGEX   = "\\$\\{keys\\}";
    private static final String VALUES_REGEX = "\\$\\{values\\}";

    public SalesDao(final Connection connection, final String schema)
    {
        super(connection);
        this.schema = schema;
    }

    public List<Sales> getSales() throws DaoException
    {
        ResultSet resultSet = null;
        final List<Sales> salesList = new ArrayList<Sales>();

        final String query = "select * from " + schema + "." + "sales";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
                salesList.add(DaoUtil.getSales(resultSet));
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return salesList;
    }

    public Sales getSales(final Long salesId) throws DaoException
    {
        ResultSet resultSet = null;
        Sales sales = null;

        final String query = "select * from " + schema + "."
                + "sales where sales_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { salesId });

            if (resultSet.next())
                sales = DaoUtil.getSales(resultSet);
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return sales;
    }

    public Integer checkBySalesUpc(final String salesUpc) throws DaoException
    {
        ResultSet resultSet = null;

        final String query = "select sales_product_id_fkey from " + schema + "."
                + "sales where sales_upc = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { salesUpc });

            if (resultSet.next())
                return resultSet.getInt("sales_product_id_fkey");
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    public String getSalesUpcByProductId(final Integer productId)
            throws DaoException
    {
        ResultSet resultSet = null;

        final String query = "select sales_upc from " + schema + "."
                + "sales where sales_product_id_fkey = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { productId });

            if (resultSet.next())
                return resultSet.getString("sales_upc");
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    public Integer insert(final List<Object> csvFieldList)
            throws DaoException
    {
        csvFieldList.remove(0);

        final String[] columns =
        { "sales_upc", "sales_description", "sales_brand", "sales_manufacturer",
                "dollar_rank", "dollar_volume", "dollar_share",
                "dollar_volume_percentage_change", "kilo_volume", "kilo_share",
                "kilo_volume_percentage_change", "average_ac_dist",
                "average_retail_price", "sales_source", "nielsen_category",
                "sales_collection_date", "sales_year", "control_label_flag",
                "kilo_volume_total", "kilo_volume_rank", "dollar_volume_total",
                "cluster_number", "product_grouping",
                "sales_product_description", "classification_number",
                "classification_type", "sales_comment", "number_of_units",
                "creation_date", "last_edit_date", "edited_by",
                "sales_product_id_fkey" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks = (String) questionmarks.subSequence(0,
                questionmarks.length() - 1);

        String query = SQL_INSERT.replaceFirst(TABLE_REGEX,
                schema + "." + "sales");
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        // returns the sales_id upon successful insert
        final Object o = executeUpdate(query, csvFieldList.toArray());

        return (Integer) o;
    }

    public SalesInsertDataResponse getSalesInsertResponse(

            final SalesInsertRequest salesInsertRequest)
            throws DaoException
    {
        final Map<String, Object> queryMap = DaoUtil.getQueryMap(
                salesInsertRequest);

        if (queryMap.isEmpty())
            return new SalesInsertDataResponse(
                    ResponseCodes.EMPTY_REQUEST.getCode(),
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            final Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new SalesInsertDataResponse(((ResponseCodes) o).getCode(),
                    ((ResponseCodes) o).getMessage());
        }

        // Check for valid classification_number.
        if (!checkClassification(salesInsertRequest.classification_number))
            return new SalesInsertDataResponse(
                    ResponseCodes.INVALID_CLASSIFICATION_NUMBER.getCode(),
                    ResponseCodes.INVALID_CLASSIFICATION_NUMBER.getMessage());

        if (!checkForSameSalesUpcProductId(salesInsertRequest.sales_upc,
                salesInsertRequest.product_id))
            return new SalesInsertDataResponse(
                    ResponseCodes.INVALID_UPC_PRODUCTID.getCode(),
                    ResponseCodes.INVALID_UPC_PRODUCTID.getMessage());

        final String[] columns =
        { "sales_description", "sales_upc", "sales_brand", "sales_manufacturer",
                "dollar_rank", "dollar_volume", "dollar_share",
                "dollar_volume_percentage_change", "kilo_volume", "kilo_share",
                "kilo_volume_percentage_change", "average_ac_dist",
                "average_retail_price", "sales_source", "nielsen_category",
                "sales_year", "control_label_flag", "kilo_volume_total",
                "kilo_volume_rank", "dollar_volume_total", "cluster_number",
                "product_grouping", "sales_product_description",
                "classification_number", "classification_type", "sales_comment",
                "sales_collection_date", "number_of_units", "edited_by",
                "creation_date", "last_edit_date", "sales_product_id_fkey" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks = (String) questionmarks.subSequence(0,
                questionmarks.length() - 1);

        String query = SQL_INSERT.replaceFirst(TABLE_REGEX,
                schema + "." + "sales");
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        final List<Object> salesInsertList = (List<Object>) queryMap.get(
                "sales_insert_list");

        executeUpdate(query, salesInsertList.toArray());

        return new SalesInsertDataResponse(ResponseCodes.OK.getCode(),
                ResponseCodes.OK.getMessage());
    }

    // ===

    public Boolean checkForSameSalesUpcProductId(final String upc,
            final Integer productId) throws DaoException
    {
        final Integer salesProductId = checkBySalesUpc(upc);

        if (salesProductId == null)
            return true;

        if (!salesProductId.equals(productId))
            return false;

        return true;
    }

    // ===

    public SalesUpdateDataResponse getSalesUpdateResponse(
            final SalesUpdateRequest salesUpdateRequest) throws DaoException
    {
        final Map<String, Object> queryMap = DaoUtil.getQueryMap(
                salesUpdateRequest);

        if (queryMap.isEmpty())
            return new SalesUpdateDataResponse(
                    ResponseCodes.EMPTY_REQUEST.getCode(),
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            final Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new SalesUpdateDataResponse(((ResponseCodes) o).getCode(),
                    ((ResponseCodes) o).getMessage());
        }

        // Check for sales.
        final Sales sales = getSales(salesUpdateRequest.sales_id);

        if ((sales == null) || (sales.getId() == 0L))
            return new SalesUpdateDataResponse(
                    ResponseCodes.NO_PRODUCT_FOUND.getCode(),
                    ResponseCodes.NO_PRODUCT_FOUND.getMessage());

        final String[] columns =
        { "sales_description", "sales_upc", "sales_brand", "sales_manufacturer",
                "dollar_rank", "dollar_volume", "dollar_share",
                "dollar_volume_percentage_change", "kilo_volume", "kilo_share",
                "kilo_volume_percentage_change", "average_ac_dist",
                "average_retail_price", "sales_source", "nielsen_category",
                "sales_year", "control_label_flag", "kilo_volume_total",
                "kilo_volume_rank", "dollar_volume_total", "cluster_number",
                "product_grouping", "sales_product_description",
                "classification_number", "classification_type", "sales_comment",
                "sales_collection_date", "number_of_units", "edited_by",
                "last_edit_date", "sales_id" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks = (String) questionmarks.subSequence(0,
                questionmarks.length() - 1);

        final String query = "update " + schema + "." + "sales set "
                + "sales_description = COALESCE(?, sales_description), "
                + "sales_upc = COALESCE(?, sales_upc), "
                + "sales_brand = COALESCE(?, sales_brand), "
                + "sales_manufacturer = COALESCE(?, sales_manufacturer), "
                + "dollar_rank = COALESCE(?, dollar_rank), "
                + "dollar_volume = COALESCE(?, dollar_volume), "
                + "dollar_share = COALESCE(?, dollar_share), "
                + "dollar_volume_percentage_change = COALESCE(?, dollar_volume_percentage_change), "
                + "kilo_volume = COALESCE(?, kilo_volume), "
                + "kilo_share = COALESCE(?, kilo_share), "
                + "kilo_volume_percentage_change = COALESCE(?, kilo_volume_percentage_change), "
                + "average_ac_dist = COALESCE(?, average_ac_dist), "
                + "average_retail_price = COALESCE(?, average_retail_price), "
                + "sales_source = COALESCE(?, sales_source), "
                + "nielsen_category = COALESCE(?, nielsen_category), "
                + "sales_year = COALESCE(?, sales_year), "
                + "control_label_flag = COALESCE(?, control_label_flag), "
                + "kilo_volume_total = COALESCE(?, kilo_volume_total), "
                + "kilo_volume_rank = COALESCE(?, kilo_volume_rank), "
                + "dollar_volume_total = COALESCE(?, dollar_volume_total), "
                + "cluster_number = COALESCE(?, cluster_number), "
                + "product_grouping = COALESCE(?, product_grouping), "
                + "sales_product_description = COALESCE(?, sales_product_description), "
                + "classification_number = COALESCE(?, classification_number), "
                + "classification_type = COALESCE(?, classification_type), "
                + "sales_comment = COALESCE(?, sales_comment), "
                + "sales_collection_date = COALESCE(?, sales_collection_date), "
                + "number_of_units = COALESCE(?, number_of_units), "
                + "edited_by = COALESCE(?, edited_by), "
                + "last_edit_date = COALESCE(?, last_edit_date) "
                + "where sales_id = ?";

        final List<Object> salesUpdateList = (List<Object>) queryMap.get(
                "sales_update_list");

        

        executeUpdate(query, salesUpdateList.toArray());

        return new SalesUpdateDataResponse(ResponseCodes.OK.getCode(),
                ResponseCodes.OK.getMessage());
    }

    // ===

    public SalesDataResponse getSalesResponse()
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        SalesResponse salesResponse = null;

        final SalesData data = new SalesData();

        final String query = "select * from " + schema + "." + "sales";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
            {
                salesResponse = DaoUtil.getSalesResponse(resultSet);
                data.add(salesResponse);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new SalesDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new SalesDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    // ===

    public SalesYearsDataResponse getSalesYearsResponse()
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        SalesYearsResponse salesYearsResponse = null;

        final SalesYearsData data = new SalesYearsData();

        final String query = "select distinct sales_year from " + schema + "."
                + "sales order by sales_year asc";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
            {
                salesYearsResponse = DaoUtil.getSalesYearsResponse(resultSet);
                data.add(salesYearsResponse);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new SalesYearsDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new SalesYearsDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    // ===

    public SalesDataResponse getSalesResponse(final long salesId)
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        SalesResponse salesResponse = null;

        final SalesData data = new SalesData();

        final String query = "select * from " + schema + "."
                + "sales where sales_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { salesId });

            if (resultSet.next())
            {
                salesResponse = DaoUtil.getSalesResponse(resultSet);
                data.add(salesResponse);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new SalesDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new SalesDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    public SalesDataResponseShort getSalesResponse(
            final SalesRequest salesRequest)
            throws SQLException, IOException, Exception
    {
        final Map<String, Object> queryMap = DaoUtil.getQueryMap(salesRequest);
        final Map<String, Object> queryMapCount = queryMap;

        if (queryMap.isEmpty())
            return new SalesDataResponseShort(
                    ResponseCodes.EMPTY_REQUEST.getCode(), null,
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            final Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new SalesDataResponseShort(((ResponseCodes) o).getCode(),
                    null, ((ResponseCodes) o).getMessage());
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
            queryMapCount.remove("collection_date_from");
            a = true;
        }

        if (queryMap.containsKey("collection_date_to"))
        {
            collectionDateTo = (String) queryMap.get("collection_date_to");
            queryMap.remove("collection_date_to");
            queryMapCount.remove("collection_date_to");

            b = true;
        }

        /// ===

        ResultSet resultSet = null;
        SalesResponseShort salesResponseShort = null;
        final SalesDataShort data = new SalesDataShort();
        Integer number_of_records = null;

        ResultSet resultSetCount = null;
        String query = "select * from " + schema + "." + "sales";
        String query_count = "select count(*) AS COUNT from " + schema + "."
                + "sales";

        // ===

        final String orderBy = salesRequest.orderBy;
        Integer offSet = salesRequest.offset;
        final boolean sortOrder = salesRequest.flag;

        String where_clause = "";
        String where_clause_count = "";

        int count = 0;
        String str;
        String sortDirection = null;

        final Iterator<String> keys = queryMap.keySet().iterator();
        final Iterator<String> keys_repeat = queryMap.keySet().iterator();

        while (keys.hasNext())
        {
            str = keys.next();

            if (str.equals("sales_year"))
                str = "CAST (" + str + " AS TEXT)";
            if (count == 0)
            {
                where_clause += " " + str + " LIKE ?";
                where_clause_count += " " + str + " LIKE ?";
            }
            else
            {
                where_clause += " AND " + str + " LIKE ?";
                where_clause_count += " AND " + str + " LIKE ?";
            }
            ++count;
        }

        /// ===

        if (a && b)
            if (count == 0)
            {
                where_clause += " sales_collection_date BETWEEN ? AND ? ";
                where_clause_count += " sales_collection_date BETWEEN ? AND ? ";

            }
            else
            {
                where_clause += " AND sales_collection_date BETWEEN ? AND ? ";
                where_clause_count += " AND sales_collection_date BETWEEN ? AND ? ";

            }

        /// ===

        try
        {
            if ((where_clause != null) && (where_clause.length() > 0))
            {
                query += " where " + where_clause;
                query_count += " where " + where_clause_count;
            }
            if (sortOrder)
                sortDirection = "ASC";
            else
                sortDirection = "DESC";

            offSet = offSet * 10;
            query += " ORDER BY " + orderBy + " " + sortDirection + " offset "
                    + offSet + " limit 10";

            final List<Object> objectList = new ArrayList<Object>();
            final List<Object> objectListCount = new ArrayList<Object>();

            if (count > 0)
                while (keys_repeat.hasNext())
                {
                    str = keys_repeat.next();
                    objectList.add("%" + queryMap.get(str) + "%");
                    objectListCount.add("%" + queryMap.get(str) + "%");

                }

            /// ===

            if (a && b)
            {
                objectList.add(java.sql.Date.valueOf(collectionDateFrom));
                objectList.add(java.sql.Date.valueOf(collectionDateTo));

                objectListCount.add(java.sql.Date.valueOf(collectionDateFrom));
                objectListCount.add(java.sql.Date.valueOf(collectionDateTo));
            }

            /// ===

            resultSet = executeQuery(query, objectList.toArray());
            resultSetCount = executeQuery(query_count,
                    objectListCount.toArray());

            resultSetCount.next();

            number_of_records = resultSetCount.getInt("COUNT");

            while (resultSet.next())
            {
                salesResponseShort = DaoUtil.getSalesResponseShort(resultSet);
                data.add(salesResponseShort);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new SalesDataResponseShort(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        data.setCount(number_of_records);

        if (data.getCount() == 0)
            return new SalesDataResponseShort(
                    ResponseCodes.NO_DATA_FOUND.getCode(), null,
                    ResponseCodes.NO_DATA_FOUND.getMessage());

        return new SalesDataResponseShort(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());

    }

    public SalesDeleteDataResponse getSalesDeleteResponse(final Integer id)
            throws SQLException, IOException, Exception
    {
        final String sql = "delete from " + schema + "."
                + "sales where sales_id = ?";

        try
        {
            final Integer deletedRow = (Integer) executeUpdate(sql, new Object[]
            { id });

            connection.setAutoCommit(false);
            if (deletedRow == 0)
                return new SalesDeleteDataResponse(
                        ResponseCodes.CANNOT_DELETE_SALES_RECORD.getCode(),
                        ResponseCodes.CANNOT_DELETE_SALES_RECORD.getMessage());
            connection.commit();
        }
        catch (final Exception e)
        {
            logger.error(e);
            connection.rollback();
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }
                
        return new SalesDeleteDataResponse(ResponseCodes.OK.getCode(),
                ResponseCodes.OK.getMessage());
    }

    public Boolean checkClassification(final Double classificationNumber)
            throws DaoException
    {
        if ((classificationNumber == null) || (classificationNumber == 0.0))
            return true;

        ResultSet resultSet = null;

        final String query = "select classification_id from " + schema + "."
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

}