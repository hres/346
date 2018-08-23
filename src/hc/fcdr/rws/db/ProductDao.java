package hc.fcdr.rws.db;

import static hc.fcdr.rws.util.DaoUtil.prepareStatement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.domain.Product;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.except.NoRowsAffectedDAOException;
import hc.fcdr.rws.model.pkg.GenericList;
import hc.fcdr.rws.model.pkg.ResponseGeneric;
import hc.fcdr.rws.model.product.ProductClassificationData;
import hc.fcdr.rws.model.product.ProductClassificationDataResponse;
import hc.fcdr.rws.model.product.ProductClassificationResponse;
import hc.fcdr.rws.model.product.ProductData;
import hc.fcdr.rws.model.product.ProductDataResponse;
import hc.fcdr.rws.model.product.ProductInsertDataResponse;
import hc.fcdr.rws.model.product.ProductInsertRequest;
import hc.fcdr.rws.model.product.ProductLabelsData;
import hc.fcdr.rws.model.product.ProductLabelsDataResponse;
import hc.fcdr.rws.model.product.ProductLabelsResponse;
import hc.fcdr.rws.model.product.ProductRequest;
import hc.fcdr.rws.model.product.ProductResponse;
import hc.fcdr.rws.model.product.ProductSalesData;
import hc.fcdr.rws.model.product.ProductSalesDataResponse;
import hc.fcdr.rws.model.product.ProductSalesLabelData;
import hc.fcdr.rws.model.product.ProductSalesLabelDataResponse;
import hc.fcdr.rws.model.product.ProductSalesLabelRequest;
import hc.fcdr.rws.model.product.ProductSalesLabelResponse;
import hc.fcdr.rws.model.product.ProductSalesResponse;
import hc.fcdr.rws.model.product.ProductUpdateDataResponse;
import hc.fcdr.rws.model.product.ProductUpdateRequest;
import hc.fcdr.rws.model.product.RelinkRecord;
import hc.fcdr.rws.model.sales.SalesYearsData;
import hc.fcdr.rws.model.sales.SalesYearsDataResponse;
import hc.fcdr.rws.model.sales.SalesYearsResponse;
import hc.fcdr.rws.util.DaoUtil;
import hc.fcdr.rws.util.DateUtil;

public class ProductDao extends PgDao
{
    private static final Logger logger       =
            Logger.getLogger(ProductDao.class.getName());
    private final String        schema;

    private static final String SQL_INSERT   =
            "insert into ${table}(${keys}) values(${values})";
    private static final String TABLE_REGEX  = "\\$\\{table\\}";
    private static final String KEYS_REGEX   = "\\$\\{keys\\}";
    private static final String VALUES_REGEX = "\\$\\{values\\}";
    static Properties prop = new Properties();
	static InputStream input = null;
	
    public ProductDao(final Connection connection, final String schema)
    {
        super(connection);
        this.schema = schema;
    	try {
			input = new FileInputStream("/etc/sodium-monitoring/config.properties");
			prop.load(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public List<Product> getProducts() throws DaoException
    {
        ResultSet resultSet = null;
        final List<Product> productList = new ArrayList<Product>();

        final String query = "select * from " + schema + "." + "product";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
                productList.add(DaoUtil.getProduct(resultSet));
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return productList;
    }

    public Product getProduct(final Integer productId) throws DaoException
    {
        ResultSet resultSet = null;
        Product product = null;

        final String query =
                "select * from "
                        + schema + "." + "product where product_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { productId });

            if (resultSet.next())
                product = DaoUtil.getProduct(resultSet);
        }
        catch (final SQLException e)
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

        final ProductData data = new ProductData();

        final String query =
                "select p.product_id, p.product_description, p.product_brand, "
                        + " p.product_country, p.cluster_number, p.product_comment, p.product_manufacturer, p.cnf_code, "
                        + " p.creation_date, p.last_edit_date, p.edited_by, p.restaurant_type, p.type,"
                        + "c.classification_number, c.classification_type, c.classification_name from "
                        + schema + "." + "product p " + "left outer join "
                        + schema + "."
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
        catch (final SQLException e)
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

    public ProductDataResponse getProductResponse(final Integer productId)
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        ProductResponse productResponse = null;

        final ProductData data = new ProductData();

        final String query =
                "select p.product_id, p.product_description, p.product_brand, "
                        + " p.product_country, p.cluster_number, p.product_comment, p.product_manufacturer, p.cnf_code, "
                        + " p.creation_date, p.last_edit_date, p.edited_by, p.restaurant_type, p.type,"
                        + "c.classification_number, c.classification_type, c.classification_name from "
                        + schema + "." + "product p " + "left outer join "
                        + schema + "."
                        + "product_classification pc on p.product_id = pc.product_classification_product_id_fkey left outer join "
                        + schema + "."
                        + "classification c on pc.product_classification_classification_id_fkey = c.classification_id where p.product_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { productId });

            while (resultSet.next())
            {
                productResponse = DaoUtil.getProductResponse(resultSet);
                data.add(productResponse);
            }
        }
        catch (final SQLException e)
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

    public ProductClassificationDataResponse getProductClassificationResponse(
            final Integer productId, final Boolean returnFirstRecordFound)
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        ProductClassificationResponse productClassificationResponse = null;

        final ProductClassificationData data = new ProductClassificationData();

        final String query =
                "select p.product_id, p.product_description, p.product_brand, "
                        + " p.product_country, p.cluster_number, p.product_comment, p.product_manufacturer, p.cnf_code, "
                        + " p.creation_date, p.last_edit_date, p.edited_by, p.restaurant_type, p.type,"
                        + "c.classification_number, c.classification_type, c.classification_name from "
                        + schema + "." + "product p " + "left outer join "
                        + schema + "."
                        + "product_classification pc on p.product_id = pc.product_classification_product_id_fkey left outer join "
                        + schema + "."
                        + "classification c on pc.product_classification_classification_id_fkey = c.classification_id where p.product_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { productId });

            if (returnFirstRecordFound)
            {
                if (resultSet.next())
                {
                    productClassificationResponse =
                            DaoUtil.getProductClassificationResponse(resultSet);
                    data.add(productClassificationResponse);
                }
            }
            else
                while (resultSet.next())
                {
                    productClassificationResponse =
                            DaoUtil.getProductClassificationResponse(resultSet);
                    data.add(productClassificationResponse);
                }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new ProductClassificationDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new ProductClassificationDataResponse(ResponseCodes.OK.getCode(),
                data, ResponseCodes.OK.getMessage());
    }

    // ===

    public ProductDataResponse getProductResponse(
            final ProductRequest productRequest)
            throws SQLException, IOException, Exception
    {
        final Map<String, Object> queryMap =
                DaoUtil.getQueryMap(productRequest);

        if (queryMap.isEmpty())
            return new ProductDataResponse(
                    ResponseCodes.EMPTY_REQUEST.getCode(), null,
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            final Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new ProductDataResponse(((ResponseCodes) o).getCode(), null,
                    ((ResponseCodes) o).getMessage());
        }

        ResultSet resultSet = null;
        ResultSet resultSetCount = null;
        ProductResponse productResponse = null;
        final ProductData data = new ProductData();
        Integer number_of_records = null;
        // set the count

        String query =
                "select p.product_id, p.product_description, p.product_brand, "
                        + " p.product_country, p.cluster_number, p.product_comment, p.product_manufacturer, p.cnf_code, "
                        + " p.creation_date, p.last_edit_date, p.edited_by, p.restaurant_type, p.type,"
                        + "c.classification_number, c.classification_type, c.classification_name from "
                        + schema + "." + "product p " + "left outer join "
                        + schema + "."
                        + "product_classification pc on p.product_id = pc.product_classification_product_id_fkey left outer join "
                        + schema + "."
                        + "classification c on pc.product_classification_classification_id_fkey = c.classification_id";

        // ===

        // Query to get the count
        String query_count =
                "select count(*) AS COUNT from "
                        + schema + "." + "product p " + "left outer join "
                        + schema + "."
                        + "product_classification pc on p.product_id = pc.product_classification_product_id_fkey left outer join "
                        + schema + "."
                        + "classification c on pc.product_classification_classification_id_fkey = c.classification_id";

        ///////////
        final String orderBy = productRequest.orderby;
        Integer offSet = productRequest.offset;
        final boolean sortOrder = productRequest.flag;

        String where_clause = "";
        int count = 0;
        String str0;
        String str;
        String str1;
        String sortDirection = null;

        final Iterator<String> keys = queryMap.keySet().iterator();
        final Iterator<String> keys_repeat = queryMap.keySet().iterator();

        while (keys.hasNext())
        {
            str0 = keys.next();

            final String prx =
                    (("classification_number".equalsIgnoreCase(str0))
                            || ("classification_name".equalsIgnoreCase(str0))
                            || ("classification_type".equalsIgnoreCase(str0)))
                                    ? "c." : "p.";

            str1 = prx + str0;

            if (str0.equals("cluster_number")
                    || str0.equals("cnf_code")
                   )
                str1 = "CAST (" + str1 + " AS TEXT)";

            if (count == 0)
                where_clause += " " + str1 + " LIKE ?";
            else
                where_clause += " AND " + str1 + " LIKE ?";

            ++count;
        }

        // ===

        try
        {
            if ((where_clause != null) && (where_clause.length() > 0))
                query += " where " + where_clause.trim();
            query_count += " where " + where_clause.trim();

            if (sortOrder)
                sortDirection = "ASC";
            else
                sortDirection = "DESC";

            offSet = offSet * 10;

            final String prefix =
                    (("classification_number".equalsIgnoreCase(orderBy))
                            || ("classification_name".equalsIgnoreCase(orderBy))
                            || ("classification_type"
                                    .equalsIgnoreCase(orderBy))) ? "c." : "p.";

            query +=
                    " ORDER BY "
                            + prefix + orderBy + " " + sortDirection
                            + " offset " + offSet + " limit 10";

            final List<Object> objectList = new ArrayList<Object>();
            final List<Object> objectListCount = new ArrayList<Object>();

            if (count > 0)
                while (keys_repeat.hasNext())
                {
                    str = keys_repeat.next();
                    objectList.add("%" + queryMap.get(str) + "%");
                    objectListCount.add("%" + queryMap.get(str) + "%");

                }

            resultSet = executeQuery(query, objectList.toArray());
            resultSetCount =
                    executeQuery(query_count, objectListCount.toArray());

            resultSetCount.next();
            number_of_records = resultSetCount.getInt("COUNT");
            while (resultSet.next())
            {
                productResponse = DaoUtil.getProductResponse(resultSet);
                data.add(productResponse);
            }
        }
        catch (final SQLException e)
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

        data.setCount(number_of_records);

        return new ProductDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());

    }

    // ===

    public ProductSalesDataResponse getProductSalesResponse(
            final long productId) throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        ProductSalesResponse productSalesResponse = null;

        final ProductSalesData data = new ProductSalesData();

        final String query =
                "select * from "
                        + schema + "."
                        + "sales where sales_product_id_fkey = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { productId });

            while (resultSet.next())
            {
                productSalesResponse =
                        DaoUtil.getProductSalesResponse(resultSet);
                data.add(productSalesResponse);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new ProductSalesDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new ProductSalesDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    // ===

    public ProductLabelsDataResponse getProductLabelsResponse(
            final long productId) throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        ProductLabelsResponse productLabelsResponse = null;

        final ProductLabelsData data = new ProductLabelsData();

        final String query =
                "select * from "
                        + schema + "."
                        + "package where package_product_id_fkey = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { productId });

            while (resultSet.next())
            {
                productLabelsResponse =
                        DaoUtil.getProductLabelsResponse(resultSet);
                data.add(productLabelsResponse);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new ProductLabelsDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new ProductLabelsDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    // ===

    public ProductSalesLabelDataResponse getProductSalesLabelResponse(
            final ProductSalesLabelRequest productSalesLabelRequest)
            throws SQLException, IOException, Exception
    {
        final Map<String, Object> queryMap =
                DaoUtil.getQueryMap(productSalesLabelRequest);
        final Map<String, Object> queryMapCount = queryMap;

        if (queryMap.isEmpty())
            return new ProductSalesLabelDataResponse(
                    ResponseCodes.EMPTY_REQUEST.getCode(), null,
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            final Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new ProductSalesLabelDataResponse(
                    ((ResponseCodes) o).getCode(), null,
                    ((ResponseCodes) o).getMessage());
        }

        /// ===

        String salesCollectionDateFrom = "";
        String salesCollectionDateTo = "";
        boolean as = false;
        boolean bs = false;

        if (queryMap.containsKey("sales_collection_date_from"))
        {
            salesCollectionDateFrom =
                    (String) queryMap.get("sales_collection_date_from");
            queryMap.remove("sales_collection_date_from");
            queryMapCount.remove("sales_collection_date_from");
            as = true;
        }

        if (queryMap.containsKey("sales_collection_date_to"))
        {
            salesCollectionDateTo =
                    (String) queryMap.get("sales_collection_date_to");
            queryMap.remove("sales_collection_date_to");
            queryMapCount.remove("sales_collection_date_to");
            bs = true;
        }

        /// ===
        /// ===

        String labelCollectionDateFrom = "";
        String labelCollectionDateTo = "";
        boolean al = false;
        boolean bl = false;

        if (queryMap.containsKey("package_collection_date_from"))
        {
            labelCollectionDateFrom =
                    (String) queryMap.get("package_collection_date_from");
            queryMap.remove("package_collection_date_from");
            queryMapCount.remove("package_collection_date_from");
            al = true;
        }

        if (queryMap.containsKey("package_collection_date_to"))
        {
            labelCollectionDateTo =
                    (String) queryMap.get("package_collection_date_to");
            queryMap.remove("package_collection_date_to");
            queryMapCount.remove("package_collection_date_to");
            bl = true;
        }

        /// ===
        /// ===

        Double dollarRankFrom = 0.0;
        Double dollarRankTo = 0.0;
        boolean ad = false;
        boolean bd = false;

        if (queryMap.containsKey("dollar_rank_from"))
        {
            dollarRankFrom = (Double) queryMap.get("dollar_rank_from");
            queryMap.remove("dollar_rank_from");
            queryMapCount.remove("dollar_rank_from");
            ad = true;
        }

        if (queryMap.containsKey("dollar_rank_to"))
        {
            dollarRankTo = (Double) queryMap.get("dollar_rank_to");
            queryMap.remove("dollar_rank_to");
            queryMapCount.remove("dollar_rank_to");
            bd = true;
        }

        /// ===

        ResultSet resultSet = null;
        ResultSet resultSetCount = null;
        Integer number_of_records = null;

        ProductSalesLabelResponse productSalesLabelResponse = null;
        final ProductSalesLabelData data = new ProductSalesLabelData();

        String query =
                "select p.product_id, p.product_description, p.product_brand, "
                        + " p.product_country, p.cluster_number, p.product_comment, p.product_manufacturer, p.cnf_code, "
                        + " p.creation_date, p.last_edit_date, p.edited_by, p.restaurant_type, p.type,"
                        + "c.classification_number, c.classification_type, c.classification_name, s.sales_year, s.sales_description, s.sales_upc, s.nielsen_category, s.sales_source, s.sales_collection_date, s.dollar_rank, s.sales_comment, l.package_upc, l.package_description, l.package_source, l.ingredients, l.package_collection_date, l.package_comment from "
                        + schema + "." + "product p " + "left outer join "
                        + schema + "."
                        + "product_classification pc on p.product_id = pc.product_classification_product_id_fkey left outer join "
                        + schema + "."
                        + "classification c on pc.product_classification_classification_id_fkey = c.classification_id "
                        + " left outer join " + schema + "."
                        + "sales s on p.product_id = s.sales_product_id_fkey "
                        + " left outer join " + schema + "."
                        + "package l on p.product_id = l.package_product_id_fkey ";

        String query_count =
                "select count(*) AS COUNT from "
                        + schema + "." + "product p " + "left outer join "
                        + schema + "."
                        + "product_classification pc on p.product_id = pc.product_classification_product_id_fkey left outer join "
                        + schema + "."
                        + "classification c on pc.product_classification_classification_id_fkey = c.classification_id "
                        + " left outer join " + schema + "."
                        + "sales s on p.product_id = s.sales_product_id_fkey "
                        + " left outer join " + schema + "."
                        + "package l on p.product_id = l.package_product_id_fkey ";
        // ===

         String orderBy = label2Package(productSalesLabelRequest.orderby);
        if(orderBy.equals("package_ingredients")) {
        orderBy = "ingredients";
        }
        Integer offSet = productSalesLabelRequest.offset;
        final boolean sortOrder = productSalesLabelRequest.flag;

        String where_clause = "";
        int count = 0;
        String str0;
        String str;
        String str1;
        String sortDirection = null;

        final Iterator<String> keys = queryMap.keySet().iterator();
        final Iterator<String> keys_repeat = queryMap.keySet().iterator();

        while (keys.hasNext())
        {
            str0 = keys.next();
            str1 = prefix(str0) + str0;

            if (str0.equals("cluster_number")
                    || str0.equals("cnf_code") || str0.equals("sales_year")
                    )
                str1 = "CAST (" + str1 + " AS TEXT)";

            if (count == 0)
                where_clause += " " + str1 + " LIKE ?";
            else
                where_clause += " AND " + str1 + " LIKE ?";
            ++count;
        }

        /// ===

        if (as && bs)
            if (count == 0)
                where_clause += " s.sales_collection_date BETWEEN ? AND ? ";
            else
                where_clause += " AND s.sales_collection_date BETWEEN ? AND ? ";

        /// ===
        /// ===

        if (al && bl)
            if (count == 0)
                where_clause += " l.package_collection_date BETWEEN ? AND ? ";
            else
                where_clause +=
                        " AND l.package_collection_date BETWEEN ? AND ? ";

        /// ===
        /// ===

        if (ad && bd)
            if (count == 0)
                where_clause += " s.dollar_rank BETWEEN ? AND ? ";
            else
                where_clause += " AND s.dollar_rank BETWEEN ? AND ? ";

        /// ===

        try
        {
            if ((where_clause != null) && (where_clause.length() > 0))
                query += " where " + where_clause.trim();
            query_count += " where " + where_clause.trim();

            if (sortOrder)
                sortDirection = "ASC";
            else
                sortDirection = "DESC";

            offSet = offSet * 10;

            query +=
                    " ORDER BY "
                            + prefix(orderBy) + orderBy + " " + sortDirection
                            + " offset " + offSet + " limit 10";

            final List<Object> objectList = new ArrayList<Object>();

            if (count > 0)
                while (keys_repeat.hasNext())
                {
                    str = keys_repeat.next();
                    objectList.add("%" + queryMap.get(str) + "%");
                }

            /// ===

            if (as && bs)
            {
                objectList.add(java.sql.Date.valueOf(salesCollectionDateFrom));
                objectList.add(java.sql.Date.valueOf(salesCollectionDateTo));
            }

            /// ===
            /// ===

            if (al && bl)
            {
                objectList.add(java.sql.Date.valueOf(labelCollectionDateFrom));
                objectList.add(java.sql.Date.valueOf(labelCollectionDateTo));
            }

            /// ===
            /// ===

            if (ad && bd)
            {
                objectList.add(dollarRankFrom);
                objectList.add(dollarRankTo);
            }

            /// ===
            /// System.out.println(query);
            resultSet = executeQuery(query, objectList.toArray());
            resultSetCount = executeQuery(query_count, objectList.toArray());
            /// System.out.println("++++ " + query_count);
            resultSetCount.next();
            number_of_records = resultSetCount.getInt("COUNT");

            while (resultSet.next())
            {
                productSalesLabelResponse =
                        DaoUtil.getProductSalesLabelResponse(resultSet);
                data.add(productSalesLabelResponse);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new ProductSalesLabelDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        data.setCount(number_of_records);
        if (data.getCount() == 0)
            return new ProductSalesLabelDataResponse(
                    ResponseCodes.NO_DATA_FOUND.getCode(), null,
                    ResponseCodes.NO_DATA_FOUND.getMessage());

        return new ProductSalesLabelDataResponse(ResponseCodes.OK.getCode(),
                data, ResponseCodes.OK.getMessage());

    }

    // ===

    public Object update(final List<Object> list,
            final String classificationNumber, final String classificationType)
            throws DaoException
    {
        final String[] columns =
        {
                "cluster_number", "product_description", "product_brand",
                "product_manufacturer" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks =
                (String) questionmarks.subSequence(0,
                        questionmarks.length() - 1);

        final String query =
                "update "
                        + schema + "." + "product set "
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

        final Integer productId = (Integer) list.get(list.size() - 1);

        /// ===

        if (productId != null)
            if (classificationNumber != null)
            {
                final Integer classificationId =
                        checkClassification(classificationNumber);

                if (classificationId != null)
                {
                    final List<Object> sqlArgumentList =
                            new ArrayList<Object>();
                    sqlArgumentList.add(productId);
                    sqlArgumentList.add(classificationId);
                    final Object o =
                            insertProductClassification(sqlArgumentList);

                    return o;
                }
            }

        return null;
    }

    public Integer insert(final List<Object> csvFieldList,
            final String classificationNumber, final String classificationType)
            throws DaoException
    {
        final String[] columns =
        {
                "product_description", "product_brand", "product_country",
                "cluster_number", "product_comment", "product_manufacturer",
                "cnf_code", "creation_date", "last_edit_date", "edited_by" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks =
                (String) questionmarks.subSequence(0,
                        questionmarks.length() - 1);
        String query =
                SQL_INSERT.replaceFirst(TABLE_REGEX, schema + "." + "product");
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        /// System.out.println("Product insert sql: " + query);

        // ===

        // returns the product_id upon successful insert
        final Integer productId =
                (Integer) executeUpdate(query, csvFieldList.toArray());

        /// ===

        if (productId != null)
            if (classificationNumber != null)
            {
                final Integer classificationId =
                        checkClassification(classificationNumber);

                if (classificationId != null)
                {
                    final List<Object> sqlArgumentList =
                            new ArrayList<Object>();
                    sqlArgumentList.add(productId);
                    sqlArgumentList.add(classificationId);
                    insertProductClassification(sqlArgumentList);
                }
            }

        return productId;
    }

    // ===

    public Boolean checkProductClassificationProductId(final Integer productId)
            throws DaoException
    {
        ResultSet resultSet = null;

        final String query =
                "select product_classification_product_id_fkey from "
                        + schema + "."
                        + "product_classification where product_classification_product_id_fkey = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { productId });

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

    public Boolean checkProductClassificationClassificationId(
            final Integer classificationId) throws DaoException
    {
        ResultSet resultSet = null;

        final String query =
                "select product_classification_classification_id_fkey from "
                        + schema + "."
                        + "product_classification where product_classification_classification_id_fkey = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { classificationId });

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

    // ===

    // ===

    public Integer getProductClassificationProductId(
            final Integer classificationId) throws DaoException
    {
        ResultSet resultSet = null;

        final String query =
                "select product_classification_product_id_fkey from "
                        + schema + "."
                        + "product_classification where product_classification_classification_id_fkey = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { classificationId });

            if (resultSet.next())
                return resultSet
                        .getInt("product_classification_product_id_fkey");
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    public Integer getProductClassificationClassificationId(
            final Integer productId) throws DaoException
    {
        ResultSet resultSet = null;

        final String query =
                "select product_classification_classification_id_fkey from "
                        + schema + "."
                        + "product_classification where product_classification_product_id_fkey = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { productId });

            if (resultSet.next())
                return resultSet.getInt(
                        "product_classification_classification_id_fkey");
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    // ===

    public Integer insertProductClassification(
            final List<Object> sqlArgumentList) throws DaoException
    {
        final String[] columns =
        {
                "product_classification_product_id_fkey",
                "product_classification_classification_id_fkey" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks =
                (String) questionmarks.subSequence(0,
                        questionmarks.length() - 1);

        String query =
                SQL_INSERT.replaceFirst(TABLE_REGEX,
                        schema + "." + "product_classification");
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        // returns the classification_id upon successful insert
        try
        {
            return (Integer) executeUpdate(query, sqlArgumentList.toArray());
        }
        catch (final Exception e)
        {
            e.getMessage();
        }

        return null;
    }

    public Integer deleteProductClassification(final Integer productId)
            throws DaoException
    {
        final String sql =
                "delete from "
                        + schema + "."
                        + "product_classification where product_classification_product_id_fkey = ?";

        try
        {
            return (Integer) executeUpdate(sql, new Object[]
            { productId });
        }
        catch (final Exception e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }
    }

    public Integer checkClassification(final String classificationNumber)
            throws DaoException
    {
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
                return resultSet.getInt("classification_id");
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    // ===

    public ProductUpdateDataResponse getProductUpdateResponse(
            final ProductUpdateRequest productUpdateRequest)
            throws SQLException, IOException, Exception
    {
        final Map<String, Object> queryMap =
                DaoUtil.getQueryMap(productUpdateRequest);

        if (queryMap.isEmpty())
            return new ProductUpdateDataResponse(
                    ResponseCodes.EMPTY_REQUEST.getCode(),
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            final Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new ProductUpdateDataResponse(((ResponseCodes) o).getCode(),
                    ((ResponseCodes) o).getMessage());
        }

        // Check for product.
        final Product product = getProduct(productUpdateRequest.product_id);

        if ((product == null) || (product.getId() == 0L))
            return new ProductUpdateDataResponse(
                    ResponseCodes.NO_PRODUCT_FOUND.getCode(),
                    ResponseCodes.NO_PRODUCT_FOUND.getMessage());

        final String[] columns =
        {
                "last_edit_date", "product_manufacturer", "product_brand",
                "product_description", "product_comment", "cnf_code",
                "cluster_number", "restaurant_type", "type", "edited_by" };

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks =
                (String) questionmarks.subSequence(0,
                        questionmarks.length() - 1);

        final String query =
                "update "
                        + schema + "." + "product set "
                        + "last_edit_date = COALESCE(?, last_edit_date), "
                        + "product_manufacturer = COALESCE(?, product_manufacturer), "
                        + "product_brand = COALESCE(?, product_brand), "
                        + "product_description = COALESCE(?, product_description), "
                        + "product_comment = COALESCE(?, product_comment), "
                        + "cnf_code = ?, " + "cluster_number = ?, "
                        + "restaurant_type = COALESCE(?, restaurant_type), "
                        + "type = COALESCE(?, type), "
                        + "edited_by = COALESCE(?, edited_by) "
                        + "where product_id = ?";

        final Timestamp lastEditDate = DateUtil.getCurrentTimeStamp();
        final List<Object> productFieldList =
                productUpdateRequest.getProductFieldList();
        productFieldList.add(0, lastEditDate);

        executeUpdate(query, productFieldList.toArray());

        /// ===

        if (productUpdateRequest.product_id != null)
            if ((productUpdateRequest.classification_number != null)
                    && (!productUpdateRequest.classification_number.isEmpty()))
            {
                final Integer classificationId =
                        checkClassification(
                                productUpdateRequest.classification_number);

                if (classificationId != null)
                {
                    final Boolean productIdPCExists =
                            checkProductClassificationProductId(
                                    productUpdateRequest.product_id);
                    final Boolean classificationIdPCExists =
                            checkProductClassificationClassificationId(
                                    classificationId);

                    getProductClassificationProductId(classificationId);
                    final Integer classificationIdPC =
                            getProductClassificationClassificationId(
                                    productUpdateRequest.product_id);

                    if (!productIdPCExists && !classificationIdPCExists)
                    {
                        // Create a new record in the product_classification table.
                        final List<Object> sqlArgumentList =
                                new ArrayList<Object>();
                        sqlArgumentList.add(productUpdateRequest.product_id);
                        sqlArgumentList.add(classificationId);
                        insertProductClassification(sqlArgumentList);
                    }
                    else if (!productIdPCExists && classificationIdPCExists)
                    {
                        // Create a new record in the product_classification table.
                        final List<Object> sqlArgumentList =
                                new ArrayList<Object>();
                        sqlArgumentList.add(productUpdateRequest.product_id);
                        sqlArgumentList.add(classificationId);
                        insertProductClassification(sqlArgumentList);
                    }
                    else if (productIdPCExists && !classificationIdPCExists)
                    {
                        final String[] columns1 =
                        { "classificationId" };

                        String questionmarks1 =
                                StringUtils.repeat("?,", columns1.length);
                        questionmarks1 =
                                (String) questionmarks1.subSequence(0,
                                        questionmarks1.length() - 1);

                        final String query1 =
                                "update "
                                        + schema + "."
                                        + "product_classification set "
                                        + "product_classification_classification_id_fkey = COALESCE(?, product_classification_classification_id_fkey) "
                                        + "where product_classification_product_id_fkey = ?";

                        executeUpdate(query1, new Object[]
                        { classificationId, productUpdateRequest.product_id });
                    }
                    else if (productIdPCExists
                            && (classificationId != classificationIdPC))
                    {
                        final String[] columns1 =
                        { "classificationId" };

                        String questionmarks1 =
                                StringUtils.repeat("?,", columns1.length);
                        questionmarks1 =
                                (String) questionmarks1.subSequence(0,
                                        questionmarks1.length() - 1);

                        final String query1 =
                                "update "
                                        + schema + "."
                                        + "product_classification set "
                                        + "product_classification_classification_id_fkey = COALESCE(?, product_classification_classification_id_fkey) "
                                        + "where product_classification_product_id_fkey = ?";

                        executeUpdate(query1, new Object[]
                        { classificationId, productUpdateRequest.product_id });
                    }
                }
            }
            else
                deleteProductClassification(productUpdateRequest.product_id);

        return new ProductUpdateDataResponse(ResponseCodes.OK.getCode(),
                ResponseCodes.OK.getMessage());
    }

    public ProductInsertDataResponse getProductInsertResponse(
            final ProductInsertRequest productInsertRequest) throws DaoException
    {

        boolean flag = true;
        Integer classificationId = null;
        final String[] columns =
        {
                "product_manufacturer", "product_brand", "cnf_code",
                "cluster_number", "product_description", "product_comment",
                "type", "restaurant_type", "creation_date", "last_edit_date" };

        final Map<String, Object> queryMap =
                DaoUtil.getQueryMap(productInsertRequest);

        if (queryMap.isEmpty())
            return new ProductInsertDataResponse(
                    ResponseCodes.EMPTY_REQUEST.getCode(),
                    ResponseCodes.EMPTY_REQUEST.getMessage());

        if (queryMap.containsKey("inputError"))
        {
            final Object o = queryMap.get("inputError");
            queryMap.remove("inputError");

            return new ProductInsertDataResponse(((ResponseCodes) o).getCode(),
                    ((ResponseCodes) o).getMessage());
        }

        if (productInsertRequest.getClassification_number() != null)
        {
            if (checkClassification(
                    productInsertRequest.getClassification_number()) < 1)
            {
                flag = false;
                return new ProductInsertDataResponse(
                        ResponseCodes.INVALID_INPUT_FIELDS.getCode(),
                        ResponseCodes.INVALID_INPUT_FIELDS.getMessage());
            }
            else
                classificationId =
                        checkClassification(productInsertRequest
                                .getClassification_number());
        }
        else
            flag = false;

        String questionmarks = StringUtils.repeat("?,", columns.length);
        questionmarks =
                (String) questionmarks.subSequence(0,
                        questionmarks.length() - 1);

        String query =
                SQL_INSERT.replaceFirst(TABLE_REGEX, schema + "." + "product");
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        @SuppressWarnings("unchecked")
        final List<Object> productInsertList =
                (List<Object>) queryMap.get("product_insert_list");

        // Returns the sales_id upon successful insert.
        final Object o = executeUpdate(query, productInsertList.toArray());

        if (flag)
        {
            // Create a new record in the product_classification table.
            final List<Object> sqlArgumentList = new ArrayList<Object>();
            sqlArgumentList.add(o);
            sqlArgumentList.add(classificationId);
            insertProductClassification(sqlArgumentList);
        }
        final ProductInsertDataResponse response =
                new ProductInsertDataResponse(ResponseCodes.OK.getCode(),
                        ResponseCodes.OK.getMessage());

        response.setId(o);

        return response;
    }
    // ===

    private String prefix(final String str0)
    {
        String prx = "p.";

        if (("classification_number".equalsIgnoreCase(str0))
                || ("classification_name".equalsIgnoreCase(str0))
                || ("classification_type".equalsIgnoreCase(str0)))
            prx = "c.";
        else if (("sales_year".equalsIgnoreCase(str0))
                || ("sales_description".equalsIgnoreCase(str0))
                || ("sales_upc".equalsIgnoreCase(str0))
                || ("nielsen_category".equalsIgnoreCase(str0))
                || ("sales_source".equalsIgnoreCase(str0))
                || ("sales_collection_date".equalsIgnoreCase(str0))
                || ("dollar_rank".equalsIgnoreCase(str0))
                || ("sales_comment".equalsIgnoreCase(str0)))
            prx = "s.";
        else if (("package_upc".equalsIgnoreCase(str0))
                || ("package_description".equalsIgnoreCase(str0))
                || ("package_source".equalsIgnoreCase(str0))
                || ("ingredients".equalsIgnoreCase(str0))
                || ("package_collection_date".equalsIgnoreCase(str0))
                || ("package_comment".equalsIgnoreCase(str0)))
            prx = "l.";

        return prx;
    }

    private String label2Package(final String s)
    {
        if (s.startsWith("label"))
            return s.replace("label", "package");
        return s;
    }

	public GenericList getRestaurantTypes() 
			throws SQLException, IOException, Exception{
        ResultSet resultSet = null;
        

        final GenericList data = new GenericList();

        final String query =
                "select distinct name from "
                        + schema + "." + "restaurant_types order by name asc";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
            {
            	String name = null;
                name = (resultSet.getString("name"));
                System.out.println("name: "+name);
                data.add(name);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return null;
        }

        return data;
	}

	public GenericList getTypes() 
			throws SQLException, IOException, Exception{
        ResultSet resultSet = null;
        

        final GenericList data = new GenericList();

        final String query =
                "select distinct name from "
                        + schema + "." + "types order by name asc";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
            {
            	String name = null;
                name = (resultSet.getString("name"));
                System.out.println("name: "+name);
                data.add(name);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return null;
        }

        return data;
	}

	public ResponseGeneric getProductDeleteResponse(Integer id) throws DaoException, SQLException {
		
		
		
		
		final String query_sales =
                "delete from " + schema + "." + "sales where sales_product_id_fkey = ?";
        final String  query_component =
                "delete from " + schema + "." + "product_component where package_id IN (select package_id from "
                		+schema+".package where package_product_id_fkey = ?)";

        final String query_package =
                "delete from " + schema + "." + "package where package_product_id_fkey = ?";     
        final String query_product_classification =
                "delete from " + schema + "." + "product_classification where product_classification_product_id_fkey = ?";   
		final String query_product =
                "delete from " + schema + "." + "product where product_id = ?";
        
        connection.setAutoCommit(false);
        try{
        	
        	
            final Integer deletedSales = (Integer) executeUpdateProductDao(query_sales, new Object[]
            { id });
        	
            final Integer deletedComponents = (Integer) executeUpdateProductDao(query_component, new Object[]
            { id });    	
            
            
            List<Integer> listOfIds = getAllPackageIds(id);
            if(listOfIds.size() > 0) {
            	System.out.println("en effet");
            for(Integer package_id : listOfIds) {
            
            	deletePackageImages(package_id);

            }
            }
            final Integer deletedPackages = (Integer) executeUpdateProductDao(query_package, new Object[]
            { id });  
            
            final Integer deletedClassification = (Integer) executeUpdateProductDao(query_product_classification, new Object[]
            { id });  
            
            final Integer deletedProduct = (Integer) executeUpdateProductDao(query_product, new Object[]
            { id });  
        }catch (final Exception e){
        	System.out.println(e);
        	 logger.error(e);
             connection.rollback();
             throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }
        
        connection.commit();

        return new ResponseGeneric(ResponseCodes.OK.getCode(),
                ResponseCodes.OK.getMessage());
	}
	
	public boolean deletePackageImages( Integer id) {
		
		ResultSet resultSet = null;

		List<String> listOfImages = new ArrayList<>();
		
		final String sql = "select image_path from " + schema + "." + "image where package_id_fkey = ?";
	      try {
				resultSet = executeQuery(sql, new Object[] {id});
				try {
					while(resultSet.next()) {
						listOfImages.add(resultSet.getString("image_path"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 if(cleanImages(listOfImages)){
			 return true;
		 }else {
			 return false;
		 }
	}
	
	public boolean cleanImages(List<String> listOfImages) {
		boolean valid = true;
		String uploadedFileLocation = null;
		if(listOfImages.size() > 0) {
			
			for (String item: listOfImages) {
				
				uploadedFileLocation = prop.getProperty("images")+item;
				File file = new File(uploadedFileLocation);
				
				if(deleteFile(file)) {
					final String sql = "delete from " + schema + "." + "image where image_path = ?";
					 try {
						executeUpdate(sql, new Object[] { item });
					} catch (DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else {
					valid = false;
				}
			}
		}else {
			return true;
		}
		return valid;
	}
	public boolean deleteFile(File file) {
		if(file.delete()) {
			return true;
		}else{
			return false;
		}
		
	}
	
	public List<Integer> getAllPackageIds(Integer id) throws DaoException {
    	System.out.println("called");

		
		List<Integer> listOfIds = new ArrayList<Integer>();
		
		final String query = "select package_id from " + schema + ".package where package_product_id_fkey = ?";
		;
		ResultSet resultSet = null;
		try {
	    	System.out.println("called");

			resultSet = executeQuery(query, new Object[] {id});
	    	System.out.println("called");


			while (resultSet.next()) {
				listOfIds.add(resultSet.getInt("package_id"));
			}

		} catch (final SQLException e) {
			logger.error(e);
			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}
		System.out.println("size: "+listOfIds.size());
		return listOfIds;
	}
	
	
    protected int executeUpdateProductDao(final String query, final Object[] values)
            throws DaoException, SQLException
    {
    	
        PreparedStatement preparedStatement = null;
         int affectedRows = 0;
         

       
            preparedStatement =
                    prepareStatement(connection, query, true, values);
            System.out.println(preparedStatement);
           affectedRows = preparedStatement.executeUpdate();

        
        return affectedRows;
    }

	public ResponseGeneric relinkRecordResponse(RelinkRecord relinkRecord) throws DaoException, SQLException {
		// TODO Auto-generated method stub
		if(relinkRecord.getProduct_id() == null || relinkRecord.getRecord_id() == null || relinkRecord.getType() ==null){
			
			return new ResponseGeneric(ResponseCodes.BAD_REQUEST.getCode(), ResponseCodes.BAD_REQUEST.getMessage());
		}
		
		Integer id;
			if(relinkRecord.getType().equals("sales")){
				
				String getId = "select sales_product_id_fkey from "+schema+"."+"sales where sales_id = ?";
				ResultSet resultSet = executeQuery(getId,new Object[] {relinkRecord.getRecord_id()} );
				resultSet.next();
	             id = resultSet.getInt("sales_product_id_fkey");
	            
				String sql = "update "+schema+"."+"sales set sales_product_id_fkey = ? where sales_id = ?";
				executeUpdate(sql, new Object[] { relinkRecord.getProduct_id(), relinkRecord.getRecord_id()});
				
			           
				
			}else if(relinkRecord.getType().equals("package")){
				String getId = "select package_product_id_fkey from "+schema+"."+"package where package_id = ?"; 
				ResultSet resultSet =executeQuery(getId,new Object[] {relinkRecord.getRecord_id()} );
				resultSet.next();
	             id = resultSet.getInt("package_product_id_fkey");
				String sql = "update "+schema+"."+"package set package_product_id_fkey = ? where package_id = ?";
				executeUpdate(sql, new Object[] { relinkRecord.getProduct_id(), relinkRecord.getRecord_id() });
				

				
			}else{
				
				return new ResponseGeneric(ResponseCodes.BAD_REQUEST.getCode(), ResponseCodes.BAD_REQUEST.getMessage());

			}
			
		
	 ResponseGeneric response = new ResponseGeneric(ResponseCodes.OK.getCode(),
	                ResponseCodes.OK.getMessage());
	 				response.setRecord_id(id);
	 
	 return response;
	}
}