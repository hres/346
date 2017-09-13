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

import hc.fcdr.rws.util.DaoUtil;
import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.domain.Sales;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.SalesData;
import hc.fcdr.rws.model.SalesDataResponse;
import hc.fcdr.rws.model.SalesRequest;
import hc.fcdr.rws.model.SalesResponse;

public class SalesDao extends PgDao
{
    private static final Logger logger       = Logger.getLogger(
            SalesDao.class.getName());
    private String              schema;

    private static final String SQL_INSERT   = "insert into ${table}(${keys}) values(${values})";
    private static final String TABLE_REGEX  = "\\$\\{table\\}";
    private static final String KEYS_REGEX   = "\\$\\{keys\\}";
    private static final String VALUES_REGEX = "\\$\\{values\\}";

    public SalesDao(Connection connection, String schema)
    {
        super(connection);
        this.schema = schema;
    }

    public List<Sales> getSales() throws DaoException
    {
        ResultSet resultSet = null;
        List<Sales> salesList = new ArrayList<Sales>();

        String query = "select * from " + schema + "." + "sales";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
                salesList.add(DaoUtil.getSales(resultSet));
        }
        catch (SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return salesList;
    }

    public Sales getSales(Long salesId) throws DaoException
    {
        ResultSet resultSet = null;
        Sales sales = null;

        String query = "select * from " + schema + "."
                + "sales where sales_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { salesId });

            if (resultSet.next())
                sales = DaoUtil.getSales(resultSet);
        }
        catch (SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return sales;
    }

    public Integer checkBySalesUpc(String salesUpc) throws DaoException
    {
        ResultSet resultSet = null;

        String query = "select sales_product_id_fkey from " + schema + "."
                + "sales where sales_upc = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { salesUpc });

            if (resultSet.next())
                return resultSet.getInt("sales_product_id_fkey");
        }
        catch (SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    public Integer insert(List<Object> csvFieldList) throws DaoException
    {
        csvFieldList.remove(0);
        
        String[] columns =
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

        System.out.println("Sales insert sql: " + query);

        // ===

        // returns the sales_id upon successful insert
        Object o = executeUpdate(query, csvFieldList.toArray());

        return (Integer) o;
    }

    // ===

    public SalesDataResponse getSalesResponse()
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        SalesResponse salesResponse = null;

        SalesData data = new SalesData();

        String query = "select * from " + schema + "." + "sales";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
            {
                salesResponse = DaoUtil.getSalesResponse(resultSet);
                data.add(salesResponse);
            }
        }
        catch (SQLException e)
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

    public SalesDataResponse getSalesResponse(Long salesId)
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        SalesResponse salesResponse = null;

        SalesData data = new SalesData();

        String query = "select * from " + schema + "."
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
        catch (SQLException e)
        {
            logger.error(e);
            return new SalesDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new SalesDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    public SalesDataResponse getSalesResponse(SalesRequest salesRequest)
            throws SQLException, IOException, Exception
    {
        Map<String, Object> queryMap = DaoUtil.getQueryMap(salesRequest);

        if (queryMap.isEmpty())
            return new SalesDataResponse(ResponseCodes.EMPTY_REQUEST.getCode(),
                    null, ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new SalesDataResponse(((ResponseCodes) o).getCode(), null,
                    ((ResponseCodes) o).getMessage());
        }

        // ===

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

        // ===

        ResultSet resultSet = null;
        SalesResponse salesResponse = null;
        SalesData data = new SalesData();

        String query = "select * from " + schema + "." + "sales";

        // ===

        String orderBy = salesRequest.orderBy;
        Integer offSet = salesRequest.offset;
        boolean sortOrder = salesRequest.flag;

        String where_clause = "";
        int count = 0;
        String str;
        String sortDirection = null;

        Iterator<String> keys = queryMap.keySet().iterator();
        Iterator<String> keys_repeat = queryMap.keySet().iterator();

        while (keys.hasNext())
        {
            str = keys.next();

            if (count == 0)
                where_clause += " " + str + " LIKE ?";
            else
                where_clause += " AND " + str + " LIKE ?";

            ++count;
        }

        if (a && b)
            if (count == 0)
                where_clause += " sales_collection_date BETWEEN ? AND ? ";
            else
                where_clause += " AND sales_collection_date BETWEEN ? AND ? ";

        // ===

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

            List<Object> objectList = new ArrayList<Object>();

            if (count > 0)
                while (keys_repeat.hasNext())
                {
                    str = keys_repeat.next();
                    objectList.add("%" + queryMap.get(str) + "%");
                }

            if (a && b)
            {
                objectList.add(java.sql.Date.valueOf(collectionDateFrom));
                objectList.add(java.sql.Date.valueOf(collectionDateTo));
            }

            resultSet = executeQuery(query, objectList.toArray());

            while (resultSet.next())
            {
                salesResponse = DaoUtil.getSalesResponse(resultSet);
                data.add(salesResponse);
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            return new SalesDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        if (data.getCount() == 0)
            return new SalesDataResponse(ResponseCodes.NO_DATA_FOUND.getCode(),
                    null, ResponseCodes.NO_DATA_FOUND.getMessage());

        return new SalesDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());

    }

}