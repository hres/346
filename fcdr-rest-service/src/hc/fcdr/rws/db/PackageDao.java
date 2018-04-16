package hc.fcdr.rws.db;

import java.io.File;
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
import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.domain.Package;
import hc.fcdr.rws.domain.Sales;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.importLabel.ExistingLabels;
import hc.fcdr.rws.model.importLabel.ImportLabelModel;
import hc.fcdr.rws.model.importLabel.ImportLabelNft;
import hc.fcdr.rws.model.importLabel.ImportLabelRequest;
import hc.fcdr.rws.model.pkg.ComponentName;
import hc.fcdr.rws.model.pkg.ComponentNameResponse;
import hc.fcdr.rws.model.pkg.GenericList;
import hc.fcdr.rws.model.pkg.ImagesList;
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

public class PackageDao extends PgDao {
	private static final Logger logger = Logger.getLogger(PackageDao.class.getName());
	private final String schema;
	private static final String SQL_INSERT = "insert into ${table}(${keys}) values(${values})";
	private static final String TABLE_REGEX = "\\$\\{table\\}";
	private static final String KEYS_REGEX = "\\$\\{keys\\}";
	private static final String VALUES_REGEX = "\\$\\{values\\}";

	public PackageDao(final Connection connection, final String schema) {
		super(connection);
		this.schema = schema;
	}

	public List<Package> getPackage() throws DaoException {
		ResultSet resultSet = null;
		final List<Package> packageList = new ArrayList<Package>();

		final String query = "select * from " + schema + "." + "package";

		try {
			resultSet = executeQuery(query, null);

			while (resultSet.next())
				packageList.add(DaoUtil.getPackage(resultSet));
		} catch (final SQLException e) {
			logger.error(e);
			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}

		return packageList;
	}

	public Package getPackage(final Long packageId) throws DaoException {
		ResultSet resultSet = null;
		Package _package = null;

		final String query = "select * from " + schema + "." + "package where package_id = ?";

		try {
			resultSet = executeQuery(query, new Object[] { packageId });

			if (resultSet.next())
				_package = DaoUtil.getPackage(resultSet);
		} catch (final SQLException e) {
			logger.error(e);
			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}

		return _package;
	}

	// ===

	public NftView getNft(final Integer packageId, final Boolean flag) throws DaoException {
		NftView resultSet = null;

		final String query = "select  amount, amount_unit_of_measure, percentage_daily_value, component_name,amount_per100g  from "
				+ schema + "." + "product_component pc INNER JOIN " + schema + "."
				+ "component c on pc.component_id = c.component_id where package_id = ? and as_ppd_flag = ? order by nft_order";

		System.out.println(query);
		try {
			resultSet = getNft(query, packageId, flag);
			resultSet.setStatus(200);

		} catch (final SQLException e) {
			logger.error(e);
			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}
		System.out.println(resultSet);
		return resultSet;
	}

	// ===
	public ResponseGeneric getNftInsertResponse(final NftRequest nftRequest) throws DaoException, SQLException {

		final Map<String, Object> queryMap = DaoUtil.getQueryMap(nftRequest);

		if (queryMap.containsKey("inputError")) {
			final Object o = queryMap.get("inputError");
			queryMap.remove("inputError");

			return new ResponseGeneric(((ResponseCodes) o).getCode(), ((ResponseCodes) o).getMessage());
		}

		System.out.println("The flag has the value of " + nftRequest.getFlag());

		if (nftRequest.getFlag() == null)
			return new ResponseGeneric(0, "Invalid flag");
		else if (nftRequest.getFlag() == true) {

			if (checkIfHasNft(nftRequest, schema, true) > 0)
				return new ResponseGeneric(777, "Has an NFT as sold already");
			else
				insertNft(nftRequest, schema);
		} else if (nftRequest.getFlag() == false) {
			if (checkIfHasNft(nftRequest, schema, false) > 0)
				return new ResponseGeneric(778, "Has an NFT as sold prepared");
			else
				insertNft(nftRequest, schema);
		} else
			return new ResponseGeneric(0, "Invalid flag");

		return new ResponseGeneric(ResponseCodes.OK.getCode(), ResponseCodes.OK.getMessage());
	}

	public ResponseGeneric getNftUpdateResponse(final NftRequest nftRequest) throws DaoException, SQLException {

		// nftRequest.getNft().size();

		if ((nftRequest.getFlag() == null) || (!(nftRequest.getPackage_id() instanceof Number)))
			return new ResponseGeneric(ResponseCodes.INVALID_INPUT_FIELDS.getCode(),
					ResponseCodes.INVALID_INPUT_FIELDS.getMessage());
		final String sql = "delete from " + schema + "." + "product_component where package_id = ? and as_ppd_flag = ?";

		if (nftRequest.getNft().size() < 1)
			try {
				final Integer deletedRow = (Integer) executeUpdate(sql,
						new Object[] { nftRequest.getPackage_id(), nftRequest.getFlag() });

				if (deletedRow == 0)
					return new ResponseGeneric(ResponseCodes.BAD_REQUEST.getCode(),
							ResponseCodes.BAD_REQUEST.getMessage());
			} catch (final Exception e) {
				logger.error(e);
				throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
			}
		else
			try {
				connection.setAutoCommit(false);
				final Map<String, Object> queryMap = DaoUtil.getQueryMap(nftRequest);

				if (queryMap.containsKey("inputError")) {
					final Object o = queryMap.get("inputError");
					queryMap.remove("inputError");

					return new ResponseGeneric(((ResponseCodes) o).getCode(), ((ResponseCodes) o).getMessage());
				}
				executeUpdate(sql, new Object[] { nftRequest.getPackage_id(), nftRequest.getFlag() });
				updateNft(nftRequest, schema);

				connection.commit();
			} catch (final SQLException e) {
				// TODO Auto-generated catch block
				connection.rollback();
				e.printStackTrace();

				// return false;
			}

		return new ResponseGeneric(ResponseCodes.OK.getCode(), ResponseCodes.OK.getMessage());

	}

	public InsertPackageResponse getPackageInsertResponse(final PackageInsertRequest packageInsertRequest)
			throws DaoException, SQLException {

		final Map<String, Object> queryMap = DaoUtil.getQueryMap(packageInsertRequest);

		if (queryMap.isEmpty())
			return new InsertPackageResponse(ResponseCodes.EMPTY_REQUEST.getCode(),
					ResponseCodes.EMPTY_REQUEST.getMessage());

		if (queryMap.containsKey("inputError")) {
			final Object o = queryMap.get("inputError");
			queryMap.remove("inputError");

			return new InsertPackageResponse(((ResponseCodes) o).getCode(), ((ResponseCodes) o).getMessage());
		}

		// Check for valid classification_number.
		if (packageInsertRequest.getClassification_number() != null)
			if (!checkClassification(packageInsertRequest.getClassification_number()))
				return new InsertPackageResponse(ResponseCodes.INVALID_CLASSIFICATION_NUMBER.getCode(),
						ResponseCodes.INVALID_CLASSIFICATION_NUMBER.getMessage());

		if (!checkForSamePackageUpcProductId(packageInsertRequest.getPackage_upc(),
				packageInsertRequest.getProduct_id()))
			return new InsertPackageResponse(ResponseCodes.INVALID_UPC_PRODUCTID.getCode(),
					ResponseCodes.INVALID_UPC_PRODUCTID.getMessage());

		final String[] columns = { "package_description", "package_upc", "package_brand", "package_manufacturer",
				"package_country", "package_size", "package_size_unit_of_measure", "storage_type", "storage_statements",
				"health_claims", "other_package_statements", "suggested_directions", "ingredients", "multi_part_flag",
				"nutrition_fact_table", "as_prepared_per_serving_amount", "as_prepared_unit_of_measure",
				"as_sold_per_serving_amount", "as_sold_unit_of_measure", "as_prepared_per_serving_amount_in_grams",
				"as_sold_per_serving_amount_in_grams", "package_comment", "package_source",
				"package_product_description", "number_of_units", "informed_dining_program", "product_grouping",
				"nielsen_item_rank", "nutrient_claims", "package_nielsen_category", "common_household_measure",
				"child_item", "package_classification_name", "edited_by", "package_classification_number",
				"package_product_id_fkey", "package_collection_date", "nft_last_update_date", "creation_date",
				"last_edit_date", "calculated" };

		String questionmarks = StringUtils.repeat("?,", columns.length);
		questionmarks = (String) questionmarks.subSequence(0, questionmarks.length() - 1);
		Object o = null;
		String query = SQL_INSERT.replaceFirst(TABLE_REGEX, schema + "." + "package");
		query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
		query = query.replaceFirst(VALUES_REGEX, questionmarks);

		final List<Object> packageInsertList = (List<Object>) queryMap.get("package_insert_list");

		// Returns the sales_id upon successful insert.
		try {
			connection.setAutoCommit(false);
			o = executeUpdate(query, packageInsertList.toArray());
		} catch (final SQLException e) {
			logger.error(e);
			connection.rollback();
			throw new DaoException(e, ResponseCodes.INTERNAL_SERVER_ERROR);
		}

		final InsertPackageResponse insertPackageResponse = new InsertPackageResponse(ResponseCodes.OK.getCode(),
				ResponseCodes.OK.getMessage());
		insertPackageResponse.setId(o);

		connection.commit();
		return insertPackageResponse;

	}

	/// =======

	public InsertPackageResponse getPackageUpdateResponse(final PackageUpdateRequest packageUpdateRequest)
			throws DaoException, SQLException {

		final Map<String, Object> queryMap = DaoUtil.getQueryMap(packageUpdateRequest);

		if (queryMap.isEmpty())
			return new InsertPackageResponse(ResponseCodes.EMPTY_REQUEST.getCode(),
					ResponseCodes.EMPTY_REQUEST.getMessage());

		if (queryMap.containsKey("inputError")) {
			final Object o = queryMap.get("inputError");
			queryMap.remove("inputError");

			return new InsertPackageResponse(((ResponseCodes) o).getCode(), ((ResponseCodes) o).getMessage());
		}

		// Check for valid classification_number.
		if (packageUpdateRequest.getClassification_number() != null)
			if (!checkClassification(packageUpdateRequest.getClassification_number()))
				return new InsertPackageResponse(ResponseCodes.INVALID_CLASSIFICATION_NUMBER.getCode(),
						ResponseCodes.INVALID_CLASSIFICATION_NUMBER.getMessage());

		final String[] columns = { "package_description", "package_brand", "package_manufacturer", "package_country",
				"package_size", "package_size_unit_of_measure", "storage_type", "storage_statements", "health_claims",
				"other_package_statements", "suggested_directions", "ingredients", "multi_part_flag",
				"nutrition_fact_table", "as_prepared_per_serving_amount", "as_prepared_unit_of_measure",
				"as_sold_per_serving_amount", "as_sold_unit_of_measure", "as_prepared_per_serving_amount_in_grams",
				"as_sold_per_serving_amount_in_grams", "package_comment", "package_source",
				"package_product_description", "number_of_units", "informed_dining_program", "product_grouping",
				"nielsen_item_rank", "nutrient_claims", "package_nielsen_category", "common_household_measure",
				"child_item", "package_classification_name", "edited_by", "package_classification_number",
				"package_collection_date", "nft_last_update_date", "last_edit_date", "calculated", "package_id" };

		String questionmarks = StringUtils.repeat("?,", columns.length);
		questionmarks = (String) questionmarks.subSequence(0, questionmarks.length() - 1);

		// String query = SQL_INSERT.replaceFirst(TABLE_REGEX,
		// schema + "." + "package");
		// query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns,
		// ","));
		// query = query.replaceFirst(VALUES_REGEX, questionmarks);

		final String query = "update " + schema + "." + "package set package_description = ?, " + "package_brand = ?, "
				+ "package_manufacturer = ?, " + "package_country = ?, " + "package_size = ?, "
				+ "package_size_unit_of_measure = ?, " + "storage_type = ?, " + "storage_statements = ?, "
				+ "health_claims = ?, " + "other_package_statements = ?, " + "suggested_directions = ?, "
				+ "ingredients = ?, " + "multi_part_flag = ?, " + "nutrition_fact_table = ?, "
				+ "as_prepared_per_serving_amount = ?, " + "as_prepared_unit_of_measure = ?, "
				+ "as_sold_per_serving_amount = ?, " + "as_sold_unit_of_measure = ?, "
				+ "as_prepared_per_serving_amount_in_grams = ?, " + "as_sold_per_serving_amount_in_grams = ?, "
				+ "package_comment = ?, " + "package_source = ?, " + "package_product_description = ?, "
				+ "number_of_units = ?, " + "informed_dining_program = ?, " + "product_grouping = ?, "
				+ "nielsen_item_rank = ?, " + "nutrient_claims = ?, " + "package_nielsen_category = ?, "
				+ "common_household_measure = ?, " + "child_item = ?, " + "package_classification_name = ?, "
				+ "edited_by = ?, " + "package_classification_number = ?, " + "package_collection_date = ?, "
				+ "nft_last_update_date = ?, " + "last_edit_date = ?, " + "calculated = ? " + "where package_id= ? ";

		@SuppressWarnings("unchecked")
		final List<Object> packageUpdateList = (List<Object>) queryMap.get("package_update_list");

		executeUpdate(query, packageUpdateList.toArray());

		final InsertPackageResponse insertPackageResponse = new InsertPackageResponse(ResponseCodes.OK.getCode(),
				ResponseCodes.OK.getMessage());

		getNftUpdateResponse(new NftRequest(true, getNft(packageUpdateRequest.getPackage_id(), true).getNft(),
				packageUpdateRequest.getPackage_id()));
		getNftUpdateResponse(new NftRequest(false, getNft(packageUpdateRequest.getPackage_id(), false).getNft(),
				packageUpdateRequest.getPackage_id()));

		return insertPackageResponse;

	}

	public PackageDataResponse getPackageResponse() throws SQLException, IOException, Exception {
		ResultSet resultSet = null;
		PackageResponse packageResponse = null;

		final PackageData data = new PackageData();

		final String query = "select * from " + schema + "." + "package";

		try {
			resultSet = executeQuery(query, null);

			while (resultSet.next()) {
				packageResponse = DaoUtil.getPackageResponse(resultSet);
				data.add(packageResponse);
			}
		} catch (final SQLException e) {
			logger.error(e);
			return new PackageDataResponse(ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
					ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
		}

		return new PackageDataResponse(ResponseCodes.OK.getCode(), data, ResponseCodes.OK.getMessage());
	}

	// ===

	public PackageViewResponse getPackageResponse(final Long packageId) throws SQLException, IOException, Exception {
		ResultSet resultSet = null;
		PackageViewData packageResponse = null;
		final PackageViewDataResponse data = new PackageViewDataResponse();

		final String query = "select * from " + schema + "." + "package where package_id = ?";

		try {
			resultSet = executeQuery(query, new Object[] { packageId });

			if (resultSet.next()) {
				packageResponse = DaoUtil.getPackageResponseView(resultSet);
				data.add(packageResponse);
			}
		} catch (final SQLException e) {
			logger.error(e);
			return new PackageViewResponse(ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
					ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
		}

		return new PackageViewResponse(ResponseCodes.OK.getCode(), data, ResponseCodes.OK.getMessage());
	}

	public PackageDataResponse getPackageResponse(final PackageRequest packageRequest)
			throws SQLException, IOException, Exception {
		final Map<String, Object> queryMap = DaoUtil.getQueryMap(packageRequest);

		if (queryMap.isEmpty())
			return new PackageDataResponse(ResponseCodes.EMPTY_REQUEST.getCode(), null,
					ResponseCodes.EMPTY_REQUEST.getMessage());

		if (queryMap.containsKey("inputError")) {
			final Object o = queryMap.get("inputError");
			queryMap.remove("inputError");

			return new PackageDataResponse(((ResponseCodes) o).getCode(), null, ((ResponseCodes) o).getMessage());
		}

		/// ===
		Integer number_of_records = null;
		String collectionDateFrom = "";
		String collectionDateTo = "";
		boolean a = false;
		boolean b = false;

		if (queryMap.containsKey("collection_date_from")) {
			collectionDateFrom = (String) queryMap.get("collection_date_from");
			queryMap.remove("collection_date_from");
			a = true;
		}

		if (queryMap.containsKey("collection_date_to")) {
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
		String query_count = "select count(*) AS COUNT from " + schema + "." + "package";

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

		while (keys.hasNext()) {
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

		try {
			if ((where_clause != null) && (where_clause.length() > 0)) {
				query += " where " + where_clause;
				query_count += " where " + where_clause;

			}

			if (sortOrder)
				sortDirection = "ASC";
			else
				sortDirection = "DESC";

			offSet = offSet * 10;
			query += " ORDER BY " + orderBy + " " + sortDirection + " offset " + offSet + " limit 10";

			final List<Object> objectList = new ArrayList<Object>();

			if (count > 0)
				while (keys_repeat.hasNext()) {
					str = keys_repeat.next();
					objectList.add("%" + queryMap.get(str) + "%");
				}

			/// ===

			if (a && b) {
				objectList.add(java.sql.Date.valueOf(collectionDateFrom));
				objectList.add(java.sql.Date.valueOf(collectionDateTo));
			}

			/// ===

			resultSet = executeQuery(query, objectList.toArray());
			resultSetCount = executeQuery(query_count, objectList.toArray());
			resultSetCount.next();

			number_of_records = resultSetCount.getInt("COUNT");

			while (resultSet.next()) {
				packageResponse = DaoUtil.getPackageResponse(resultSet);
				data.add(packageResponse);
			}
		} catch (final SQLException e) {
			logger.error(e);
			return new PackageDataResponse(ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
					ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
		}

		data.setCount(number_of_records);

		if (data.getCount() == 0)
			return new PackageDataResponse(ResponseCodes.NO_DATA_FOUND.getCode(), null,
					ResponseCodes.NO_DATA_FOUND.getMessage());

		return new PackageDataResponse(ResponseCodes.OK.getCode(), data, ResponseCodes.OK.getMessage());

	}

	public Boolean checkClassification(final String classificationNumber) throws DaoException {
		// if ((classificationNumber == null))
		// return true;

		ResultSet resultSet = null;

		final String query = "select classification_id from " + schema + "."
				+ "classification where classification_number = ?";

		try {
			resultSet = executeQuery(query, new Object[] { classificationNumber });

			if (resultSet.next())
				return true;
		} catch (final SQLException e) {
			logger.error(e);
			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}

		return false;
	}

	public ComponentNameResponse getComponents() throws DaoException {
		ResultSet resultSet = null;
		final ComponentNameResponse componentList = new ComponentNameResponse();

		final String query = "select component_name from " + schema + "." + "component";

		try {
			resultSet = executeQuery(query, null);

			while (resultSet.next())
				componentList.add(new ComponentName(resultSet.getString("component_name")));
		} catch (final SQLException e) {
			logger.error(e);
			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}
		System.out.println("list " + componentList.toString());

		return componentList;
	}

	public Boolean checkForSamePackageUpcProductId(final String upc, final Integer productId) throws DaoException {
		final Integer packageProductId = checkByPackageUpc(upc);

		if (packageProductId == null)
			return true;

		if (!packageProductId.equals(productId))
			return false;

		return true;
	}

	public Integer checkByPackageUpc(final String packageUpc) throws DaoException {
		ResultSet resultSet = null;

		final String query = "select package_product_id_fkey from " + schema + "." + "package where package_upc = ?";

		try {
			resultSet = executeQuery(query, new Object[] { packageUpc });

			if (resultSet.next())
				return resultSet.getInt("package_product_id_fkey");
		} catch (final SQLException e) {
			logger.error(e);
			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}

		return null;
	}

	public GenericList getListOfUnits() throws DaoException {

		final GenericList genericList = new GenericList();
		final String query = "select unit_of_measure_name from " + schema + ".unit_of_measure";
		;
		ResultSet resultSet = null;
		try {
			resultSet = executeQuery(query, null);

			while (resultSet.next())
				genericList.getDataList().add(resultSet.getString("unit_of_measure_name"));

		} catch (final SQLException e) {
			logger.error(e);
			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}
		return genericList;

	}

	private boolean insertNft(final NftRequest nftRequest, final String schema) throws DaoException, SQLException {

		String per_serving_amount_place_holder = null;
		String per_serving_amount_unit_of_measure_place_holder = null;
		String per_serving_amount_in_grams_place_holder = null;

		String sql = null;
		if (nftRequest.getFlag() == true) {
			sql = "select as_sold_per_serving_amount, as_sold_unit_of_measure, as_sold_per_serving_amount_in_grams "
					+ " from " + schema + ".package where package_id =?";
			per_serving_amount_place_holder = "as_sold_per_serving_amount";
			per_serving_amount_unit_of_measure_place_holder = "as_sold_unit_of_measure";
			per_serving_amount_in_grams_place_holder = "as_sold_per_serving_amount_in_grams";

		} else {
			sql = "select as_prepared_per_serving_amount, as_prepared_unit_of_measure, as_prepared_per_serving_amount_in_grams "
					+ " from " + schema + ".package where package_id =?";

			per_serving_amount_place_holder = "as_prepared_per_serving_amount";
			per_serving_amount_unit_of_measure_place_holder = "as_prepared_unit_of_measure";
			per_serving_amount_in_grams_place_holder = "as_prepared_per_serving_amount_in_grams";

		}
		ResultSet resultSet = null;
		Double PerServingAmount = null;
		String PerServingUnit = null;
		Double PerServingInGrams = null;

		System.out.println("Amount " + PerServingAmount + " Unit: " + PerServingUnit + " Grams: " + PerServingInGrams);

		final String query =

				"insert into " + schema + "." + "product_component(component_id, package_id, amount,"
						+ " amount_unit_of_measure, percentage_daily_value, as_ppd_flag, amount_per100g) "
						+ "select component_id, ?, ?, ?, ?, ?, ? from " + schema + ".component "
						+ "where component_id = (" + "select component_id from " + schema + "."
						+ "component where component_name= ?)";

		try {
			connection.setAutoCommit(false);
			resultSet = executeQuery(sql, new Object[] { nftRequest.getPackage_id() });

			resultSet.next();
			PerServingAmount = resultSet.getDouble(per_serving_amount_place_holder);
			PerServingUnit = resultSet.getString(per_serving_amount_unit_of_measure_place_holder);
			PerServingInGrams = resultSet.getDouble(per_serving_amount_in_grams_place_holder);

			for (final NftModel element : nftRequest.getNft()) {
				Double value = null;
				final PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setObject(1, nftRequest.getPackage_id());
				preparedStatement.setObject(2, element.getAmount());
				preparedStatement.setObject(3, element.getUnit_of_measure());
				preparedStatement.setObject(4, element.getDaily_value());
				preparedStatement.setObject(5, nftRequest.getFlag());

				if (element.getAmount() != null) {
					System.out.println("yes there is an amount: " + element.getAmount());
					if (PerServingInGrams != null && PerServingInGrams != 0.0 && PerServingInGrams != 0) {

						System.out.println("yes there is an amount per 100g: " + PerServingInGrams);
						value = (element.getAmount() / PerServingInGrams) * 100;
						System.out.println("the value set is : " + value);

					} else if ((PerServingAmount != null && PerServingAmount != 0 && PerServingAmount != 0.0)
							&& (PerServingUnit.equals("g"))) {
						value = (element.getAmount() / PerServingAmount) * 100;

					}
				}

				preparedStatement.setObject(6, value);
				preparedStatement.setObject(7, element.getName());
				System.out.println(preparedStatement);
				preparedStatement.executeUpdate();
			}

			connection.commit();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			connection.rollback();
			e.printStackTrace();

			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}

		return true;

	}

	private boolean updateNft(final NftRequest nftRequest, final String schema) throws DaoException, SQLException {

		String per_serving_amount_place_holder = null;
		String per_serving_amount_unit_of_measure_place_holder = null;
		String per_serving_amount_in_grams_place_holder = null;

		String sql = null;
		if (nftRequest.getFlag() == true) {
			sql = "select as_sold_per_serving_amount, as_sold_unit_of_measure, as_sold_per_serving_amount_in_grams "
					+ " from " + schema + ".package where package_id =?";
			per_serving_amount_place_holder = "as_sold_per_serving_amount";
			per_serving_amount_unit_of_measure_place_holder = "as_sold_unit_of_measure";
			per_serving_amount_in_grams_place_holder = "as_sold_per_serving_amount_in_grams";

		} else {
			sql = "select as_prepared_per_serving_amount, as_prepared_unit_of_measure, as_prepared_per_serving_amount_in_grams "
					+ " from " + schema + ".package where package_id =?";

			per_serving_amount_place_holder = "as_prepared_per_serving_amount";
			per_serving_amount_unit_of_measure_place_holder = "as_prepared_unit_of_measure";
			per_serving_amount_in_grams_place_holder = "as_prepared_per_serving_amount_in_grams";

		}
		ResultSet resultSet = null;
		Double PerServingAmount = null;
		String PerServingUnit = null;
		Double PerServingInGrams = null;

		final String query = "insert into " + schema + "." + "product_component(component_id, package_id, amount,"
				+ " amount_unit_of_measure, percentage_daily_value, as_ppd_flag, amount_per100g) "
				+ "select component_id, ?, ?, ?, ?, ?, ? from " + schema + ".component " + "where component_id = ("
				+ "select component_id from " + schema + "." + "component where component_name= ?)";

		try {

			connection.setAutoCommit(false);
			resultSet = executeQuery(sql, new Object[] { nftRequest.getPackage_id() });

			resultSet.next();
			PerServingAmount = resultSet.getDouble(per_serving_amount_place_holder);
			PerServingUnit = resultSet.getString(per_serving_amount_unit_of_measure_place_holder);
			PerServingInGrams = resultSet.getDouble(per_serving_amount_in_grams_place_holder);

			for (final NftModel element : nftRequest.getNft()) {
				// ecuteInsertNft(element, nftRequest.getPackage_id(),
				// nftRequest.getFlag(), schema);
				Double value = null;
				final PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setObject(1, nftRequest.getPackage_id());
				preparedStatement.setObject(2, element.getAmount());
				preparedStatement.setObject(3, element.getUnit_of_measure());
				preparedStatement.setObject(4, element.getDaily_value());
				preparedStatement.setObject(5, nftRequest.getFlag());

				if (element.getAmount() != null) {
					System.out.println("yes there is an amount: " + element.getAmount());
					if (PerServingInGrams != null && PerServingInGrams != 0.0 && PerServingInGrams != 0) {

						System.out.println("yes there is an amount per 100g: " + PerServingInGrams);
						value = (element.getAmount() / PerServingInGrams) * 100;
						System.out.println("the value set is : " + value);

					} else if ((PerServingAmount != null && PerServingAmount != 0 && PerServingAmount != 0.0)
							&& (PerServingUnit.equals("g"))) {
						value = (element.getAmount() / PerServingAmount) * 100;

					}
				}

				preparedStatement.setObject(6, value);
				preparedStatement.setObject(7, element.getName());
				preparedStatement.executeUpdate();
			}
			connection.commit();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			connection.rollback();
			e.printStackTrace();

			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}
		return true;

	}

	private NftView getNft(final String query, final Integer package_id, final Boolean flag)
			throws DaoException, SQLException {
		final NftView nftList = new NftView();
		ResultSet resultSet = null;

		try {

			final PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1, package_id);
			preparedStatement.setObject(2, flag);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String name = resultSet.getString("component_name");
				name = resultSet.wasNull() ? null : resultSet.getString("component_name");

				Double amount = resultSet.getDouble("amount");
				amount = resultSet.wasNull() ? null : resultSet.getDouble("amount");
				System.out.println(resultSet.getString("component_name") + ": " + amount);

				String amount_unit_of_measure = resultSet.getString("amount_unit_of_measure");
				amount_unit_of_measure = resultSet.wasNull() ? null : resultSet.getString("amount_unit_of_measure");

				Double percentage_daily_value = resultSet.getDouble("percentage_daily_value");
				percentage_daily_value = resultSet.wasNull() ? null : resultSet.getDouble("percentage_daily_value");

				Double amount_per100g = resultSet.getDouble("amount_per100g");
				amount_per100g = resultSet.wasNull() ? null : resultSet.getDouble("amount_per100g");
				// String name, Double amount, String unit_of_measure, Double
				// daily_value

				nftList.getNft().add(
						new NftModel(name, amount, amount_unit_of_measure, percentage_daily_value, amount_per100g));
			}

		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}

		return nftList;
	}

	private int checkIfHasNft(final NftRequest nftRequest, final String schema, final Boolean flag)
			throws DaoException {

		ResultSet resultSetCount = null;
		int number_of_records = 0;

		final String query = "select count (*) AS COUNT from " + schema + "."
				+ "product_component where as_ppd_flag = ? and package_id = ?";

		try {
			final PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1, flag);
			preparedStatement.setObject(2, nftRequest.getPackage_id());
			resultSetCount = preparedStatement.executeQuery();

			resultSetCount.next();
			number_of_records = resultSetCount.getInt("COUNT");
		} catch (final SQLException e) {
			logger.error(e);
			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}

		return number_of_records;
	}

	public ResponseGeneric getLabelDeleteResponse(final Integer id) throws SQLException, IOException, Exception {

		final String delete_components = "delete from " + schema + "." + "product_component where package_id = ?";

		final String sql = "delete from " + schema + "." + "package where package_id = ?";

		try {

			final Integer deletedRowdelete_components = (Integer) executeUpdate(delete_components, new Object[] { id });

			final Integer deletedRow = (Integer) executeUpdate(sql, new Object[] { id });

			if (deletedRow == 0) {
				return new ResponseGeneric(777, "Cannot delete record");
			}

		} catch (final Exception e) {
			logger.error(e);

			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}

		return new ResponseGeneric(ResponseCodes.OK.getCode(), ResponseCodes.OK.getMessage());
	}

	public void importLabel(String filePath) {
		final StopWatch stopWatch = new StopWatch();

		stopWatch.start();
		System.out.println("Program has started");

		//start(filePath);

		stopWatch.split();
		System.out.println(
				"Total time spent on loading the sales data: " + (stopWatch.getSplitTime() / 1000) + " seconds.");

	}












	public Boolean insertClassificationNumber(String classification_number, Integer product_id) {

		final String query1 = "insert into " + schema + "."
				+ "product_classification (product_classification_classification_id_fkey, product_classification_product_id_fkey) "
				+ " select classification_id, ? from " + schema + ".classification where "
				+ "classification_number = ?";

		try {
			executeUpdate(query1, new Object[] { product_id, classification_number });
			return true;
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}



	public Boolean updateClassification(String classification_number, Integer product_id) {

		Boolean flag = false;
		if (classification_number == null) {
			return false;
		}
		try {
			if (!checkClassification(classification_number)) {
				return false;
			}
		} catch (DaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

		try {
			if (checkProductClassificationProductId(product_id)) {

				if (updateClassificationNumber(classification_number, product_id)) {
					flag = true;
				} else {
					flag = false;
				}
				return true;
			} else {

				if (insertClassificationNumber(classification_number, product_id)) {
					flag = true;
				}
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return flag;

	}

	public Boolean checkProductClassificationProductId(final Integer productId) throws DaoException {
		ResultSet resultSet = null;

		final String query = "select product_classification_product_id_fkey from " + schema + "."
				+ "product_classification where product_classification_product_id_fkey = ?";

		try {
			resultSet = executeQuery(query, new Object[] { productId });

			if (resultSet.next())
				return true;
		} catch (final SQLException e) {
			logger.error(e);
			throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
		}

		return false;
	}

	public Boolean updateClassificationNumber(String classification_number, Integer product_id) {

		final String query1 = "update " + schema + "." + "product_classification set "
				+ "product_classification_classification_id_fkey = (" + "select classification_id from " + schema
				+ ".classification where "
				+ "classification_number = ?) where product_classification_product_id_fkey = ?";

		try {
			executeUpdate(query1, new Object[] { classification_number, product_id });
			return true;
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
    public ImagesList getListOfImages(int package_id) throws DaoException
    {
        ResultSet resultSet = null;
//        final List<Sales> salesList = new ArrayList<Sales>();
        ImagesList imageList = new ImagesList();

        final String query = "select image_path from " + schema + "." + "image where package_id_fkey = ?";

        try
        {
            resultSet = executeQuery(query, new Object[] {package_id});

            while (resultSet.next())
            	imageList.getDataList().add(resultSet.getString("image_path"));
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }
        imageList.setStatus(200);
        return imageList;
    }

    public File getImage(String image_path) {
    	
		String filePath = "/home/romario/Documents/imagesLabel/"+image_path;
		
		return new File(filePath);
		
    	
    	
    	
    }
    public ImagesList addImage(List<FormDataBodyPart> bodyParts, Integer id, ImportImageDao importImageDao) {
    	
    	BodyPartEntity bodyPartEntity = (BodyPartEntity) bodyParts.get(0).getEntity();
		String fileName = bodyParts.get(0).getContentDisposition().getFileName();
		
		if(fileName.contains(".")) {
			
			String secondaryFileName = ""+id+"-"+fileName;
			String extension = fileName.substring(fileName.indexOf(".")+1);

			String uploadedFileLocation = "/home/romario/Documents/imagesLabel/"+secondaryFileName;
			importImageDao.writeToFile(bodyPartEntity.getInputStream(), uploadedFileLocation);
			
			try {
				
				importImageDao.insertImage(secondaryFileName,fileName, id, extension);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		ImagesList imagesList = null;
		try {
			imagesList = getListOfImages(id);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return imagesList;
    }

}
