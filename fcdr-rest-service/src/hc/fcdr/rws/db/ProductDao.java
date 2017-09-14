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
import hc.fcdr.rws.domain.Product;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.ProductData;
import hc.fcdr.rws.model.ProductDataResponse;
import hc.fcdr.rws.model.ProductRequest;
import hc.fcdr.rws.model.ProductResponse;

public class ProductDao extends PgDao
{
    private static final Logger logger       = Logger.getLogger(
            ProductDao.class.getName());
    private String              schema;

    private static final String SQL_INSERT   = "insert into ${table}(${keys}) values(${values})";
    private static final String TABLE_REGEX  = "\\$\\{table\\}";
    private static final String KEYS_REGEX   = "\\$\\{keys\\}";
    private static final String VALUES_REGEX = "\\$\\{values\\}";

    public ProductDao(Connection connection, String schema)
    {
        super(connection);
        this.schema = schema;
    }

    public List<Product> getProducts() throws DaoException
    {
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<Product>();

        String query = "select * from " + schema + "." + "product";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
                productList.add(DaoUtil.getProduct(resultSet));
        }
        catch (SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return productList;
    }

    public Product getProduct(Long productId) throws DaoException
    {
        ResultSet resultSet = null;
        Product product = null;

        String query = "select * from " + schema + "."
                + "product where product_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { productId });

            if (resultSet.next())
                product = DaoUtil.getProduct(resultSet);
        }
        catch (SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return product;
    }

    // ===

    public ProductDataResponse getProductResponse()
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        ProductResponse productResponse = null;

        ProductData data = new ProductData();

        String query = "select p.product_id, p.product_description, p.product_brand, "
                + " p.product_country, p.cluster_number, p.product_comment, p.product_manufacturer, p.cnf_code, "
                + " p.creation_date, p.last_edit_date, p.edited_by, "
                + "c.classification_number, c.classification_type, c.classification_name from "
                + schema + "." + "product p " + "left outer join " + schema
                + "."
                + "product_classification pc on p.product_id = pc.product_classification_product_id_fkey left outer join "
                + schema + "."
                + "classification c on pc.product_classification_classification_id_fkey = c.classification_id";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
            {
                productResponse = DaoUtil.getProductResponse(resultSet);
                data.add(productResponse);
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            return new ProductDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new ProductDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    // ===

    public ProductDataResponse getProductResponse(Long productId)
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        ProductResponse productResponse = null;

        ProductData data = new ProductData();

        String query = "select p.product_id, p.product_description, p.product_brand, "
                + " p.product_country, p.cluster_number, p.product_comment, p.product_manufacturer, p.cnf_code, "
                + " p.creation_date, p.last_edit_date, p.edited_by, "
                + "c.classification_number, c.classification_type, c.classification_name from "
                + schema + "." + "product p " + "left outer join " + schema
                + "."
                + "product_classification pc on p.product_id = pc.product_classification_product_id_fkey left outer join "
                + schema + "."
                + "classification c on pc.product_classification_classification_id_fkey = c.classification_id where p.product_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { productId });

            if (resultSet.next())
            {
                productResponse = DaoUtil.getProductResponse(resultSet);
                data.add(productResponse);
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            return new ProductDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new ProductDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    public ProductDataResponse getProductResponse(ProductRequest productRequest)
            throws SQLException, IOException, Exception
    {
        Map<String, Object> queryMap = DaoUtil.getQueryMap(productRequest);

        if (queryMap.isEmpty())
            return new ProductDataResponse(
                    ResponseCodes.EMPTY_REQUEST.getCode(), null,
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new ProductDataResponse(((ResponseCodes) o).getCode(), null,
                    ((ResponseCodes) o).getMessage());
        }

        ResultSet resultSet = null;
        ProductResponse productResponse = null;
        ProductData data = new ProductData();

        String query = "select p.product_id, p.product_description, p.product_brand, "
                + " p.product_country, p.cluster_number, p.product_comment, p.product_manufacturer, p.cnf_code, "
                + " p.creation_date, p.last_edit_date, p.edited_by, "
                + "c.classification_number, c.classification_type, c.classification_name from "
                + schema + "." + "product p " + "left outer join " + schema
                + "."
                + "product_classification pc on p.product_id = pc.product_classification_product_id_fkey left outer join "
                + schema + "."
                + "classification c on pc.product_classification_classification_id_fkey = c.classification_id";

        // ===

        String orderBy = productRequest.orderby;
        Integer offSet = productRequest.offset;
        boolean sortOrder = productRequest.flag;

        String where_clause = "";
        int count = 0;
        String str;
        String sortDirection = null;

        Iterator<String> keys = queryMap.keySet().iterator();
        Iterator<String> keys_repeat = queryMap.keySet().iterator();

        while (keys.hasNext())
        {
            str = keys.next();

            if (str.equals("cluster_number") || str.equals("cnf_code"))
                str = "CAST (" + str + " AS TEXT)";
            if (count == 0)
                where_clause += " " + str + " LIKE ?";
            else
                where_clause += " AND " + str + " LIKE ?";

            ++count;
        }

        // ===

        try
        {
            if ((where_clause != null) && (where_clause.length() > 0))
                query += " where " + "p." + where_clause.trim();

            if (sortOrder)
                sortDirection = "ASC";
            else
                sortDirection = "DESC";

            offSet = offSet * 10;
            String prefix = (("classification_number".equalsIgnoreCase(orderBy))
                    || ("classification_name".equalsIgnoreCase(orderBy))
                    || ("classification_type".equalsIgnoreCase(orderBy))) ? "c."
                            : "p.";

            query += " ORDER BY " + prefix + orderBy + " " + sortDirection
                    + " offset " + offSet + " limit 10";

            List<Object> objectList = new ArrayList<Object>();

            if (count > 0)
                while (keys_repeat.hasNext())
                {
                    str = keys_repeat.next();
                    objectList.add("%" + queryMap.get(str) + "%");
                }

            resultSet = executeQuery(query, objectList.toArray());

            while (resultSet.next())
            {
                productResponse = DaoUtil.getProductResponse(resultSet);
                data.add(productResponse);
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            return new ProductDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        if (data.getCount() == 0)
            return new ProductDataResponse(
                    ResponseCodes.NO_DATA_FOUND.getCode(), null,
                    ResponseCodes.NO_DATA_FOUND.getMessage());

        return new ProductDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());

    }

    public Object update(List<Object> list, Double classificationNumber,
            String classificationType) throws DaoException
    {
        String[] columns =
        { "cluster_number", "product_description", "product_brand",
                "product_manufacturer" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks = (String) questionmarks.subSequence(0,
                questionmarks.length() - 1);

        String query = "update " + schema + "." + "product set "
                + "product_brand = COALESCE(?, product_brand), "
                + "product_manufacturer = COALESCE(?, product_manufacturer), "
                + "cluster_number = COALESCE(?, cluster_number), "
                + "product_description = COALESCE(?, product_description) "
                + "where product_id = ?";

        // String query = SQL_INSERT.replaceFirst(TABLE_REGEX,
        // schema + "." + "sales");
        // query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        // query = query.replaceFirst(VALUES_REGEX, questionmarks);

        /// System.out.println("Query: " + query);

        executeUpdate(query, list.toArray());

        Integer productId = (Integer) list.get(list.size() - 1);

        /// ===

        if (productId != null)
            if (classificationNumber != null)
            {
                Integer classificationId = checkClassification(
                        classificationNumber);

                if (classificationId != null)
                {
                    List<Object> sqlArgumentList = new ArrayList<Object>();
                    sqlArgumentList.add(productId);
                    sqlArgumentList.add(classificationId);
                    Object o = insertProductClassification(sqlArgumentList);

                    return o;
                }
            }

        return null;
    }

    public Integer insert(List<Object> csvFieldList,
            Double classificationNumber, String classificationType)
            throws DaoException
    {
        String[] columns =
        { "product_description", "product_brand", "product_country",
                "cluster_number", "product_comment", "product_manufacturer",
                "cnf_code", "creation_date", "last_edit_date", "edited_by" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks = (String) questionmarks.subSequence(0,
                questionmarks.length() - 1);

        String query = SQL_INSERT.replaceFirst(TABLE_REGEX,
                schema + "." + "product");
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        /// System.out.println("Product insert sql: " + query);

        // ===

        // returns the product_id upon successful insert
        Integer productId = (Integer) executeUpdate(query,
                csvFieldList.toArray());

        /// ===

        if (productId != null)
            if (classificationNumber != null)
            {
                Integer classificationId = checkClassification(
                        classificationNumber);

                if (classificationId != null)
                {
                    List<Object> sqlArgumentList = new ArrayList<Object>();
                    sqlArgumentList.add(productId);
                    sqlArgumentList.add(classificationId);
                    Object o = insertProductClassification(sqlArgumentList);
                }
            }

        return productId;
    }

    public Integer insertProductClassification(List<Object> sqlArgumentList)
            throws DaoException
    {
        String[] columns =
        { "product_classification_product_id_fkey",
                "product_classification_classification_id_fkey" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks = (String) questionmarks.subSequence(0,
                questionmarks.length() - 1);

        String query = SQL_INSERT.replaceFirst(TABLE_REGEX,
                schema + "." + "product_classification");
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        /// System.out.println("Product Classification insert sql: " + query);

        // returns the classification_id upon successful insert
        try
        {
            return (Integer) executeUpdate(query, sqlArgumentList.toArray());
        }
        catch (Exception e)
        {
            e.getMessage();
        }

        return null;
    }

    public Integer checkClassification(Double classificationNumber)
            throws DaoException
    {
        ResultSet resultSet = null;

        String query = "select classification_id from " + schema + "."
                + "classification where classification_number = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { classificationNumber });

            if (resultSet.next())
                return resultSet.getInt("classification_id");
        }
        catch (SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

}