package hc.fcdr.rws.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import hc.fcdr.rws.util.DaoUtil;
import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.pkg.PackageData;
import hc.fcdr.rws.model.pkg.PackageDataResponse;
import hc.fcdr.rws.model.pkg.PackageRequest;
import hc.fcdr.rws.model.pkg.PackageResponse;
import hc.fcdr.rws.domain.Package;

public class PackageDao extends PgDao
{
    private static final Logger logger = Logger.getLogger(
            PackageDao.class.getName());
    private String              schema;

    public PackageDao(Connection connection, String schema)
    {
        super(connection);
        this.schema = schema;
    }

    public List<Package> getPackage() throws DaoException
    {
        ResultSet resultSet = null;
        List<Package> packageList = new ArrayList<Package>();

        String query = "select * from " + schema + "." + "package";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
                packageList.add(DaoUtil.getPackage(resultSet));
        }
        catch (SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return packageList;
    }

    public Package getPackage(Long packageId) throws DaoException
    {
        ResultSet resultSet = null;
        Package _package = null;

        String query = "select * from " + schema + "."
                + "package where package_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { packageId });

            if (resultSet.next())
                _package = DaoUtil.getPackage(resultSet);
        }
        catch (SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return _package;
    }

    // ===

    public PackageDataResponse getPackageResponse()
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        PackageResponse packageResponse = null;

        PackageData data = new PackageData();

        String query = "select * from " + schema + "." + "package";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
            {
                packageResponse = DaoUtil.getPackageResponse(resultSet);
                data.add(packageResponse);
            }
        }
        catch (SQLException e)
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

    public PackageDataResponse getPackageResponse(Long packageId)
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        PackageResponse packageResponse = null;

        PackageData data = new PackageData();

        String query = "select * from " + schema + "."
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
        catch (SQLException e)
        {
            logger.error(e);
            return new PackageDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new PackageDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    public PackageDataResponse getPackageResponse(PackageRequest packageRequest)
            throws SQLException, IOException, Exception
    {
        Map<String, Object> queryMap = DaoUtil.getQueryMap(packageRequest);

        if (queryMap.isEmpty())
            return new PackageDataResponse(
                    ResponseCodes.EMPTY_REQUEST.getCode(), null,
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            Object o = queryMap.get("inputError");
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
        PackageData data = new PackageData();

        String query = "select * from " + schema + "." + "package";

        // ===

        String orderBy = packageRequest.orderBy;
        Integer offSet = packageRequest.offset;
        boolean sortOrder = packageRequest.flag;

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

            List<Object> objectList = new ArrayList<Object>();

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
        catch (SQLException e)
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