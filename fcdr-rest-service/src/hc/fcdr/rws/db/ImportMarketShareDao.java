package hc.fcdr.rws.db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;

import com.opencsv.bean.CsvToBeanBuilder;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.importer.ImportSalesData;
import hc.fcdr.rws.model.pkg.NftModel;
import hc.fcdr.rws.model.product.ProductInsertRequest;
import hc.fcdr.rws.model.sales.ImportMarketShare;
import hc.fcdr.rws.model.sales.ImportSalesMadatoryFields;
import hc.fcdr.rws.model.sales.ImportSalesSummary;
import hc.fcdr.rws.model.sales.SalesInsertRequest;
import hc.fcdr.rws.model.sales.UpdateProductFields;
import hc.fcdr.rws.util.DaoUtil;
import hc.fcdr.rws.util.DateUtil;

import java.security.*;

public class ImportMarketShareDao extends PgDao {

	private static final Logger logger = Logger.getLogger(ImportSalesDao.class.getName());
	private final String schema;
	private static final String SQL_INSERT = "insert into ${table}(${keys}) values(${values})";
	private static final String TABLE_REGEX = "\\$\\{table\\}";
	private static final String KEYS_REGEX = "\\$\\{keys\\}";
	private static final String VALUES_REGEX = "\\$\\{values\\}";

	Map<Double, String> invalidRecords = new HashMap<Double, String>();
	Map<Double, String> duplicatesRecords = new HashMap<Double, String>();

	Map<String, Integer> recordInDbByUPC = new HashMap<String, Integer>();

	Map<String, List<ImportMarketShare>> salesInFileByUPC = new HashMap<String, List<ImportMarketShare>>();
	Map<Double, List<ImportMarketShare>> salesWithGrouping = new HashMap<Double, List<ImportMarketShare>>();
	ImportSalesSummary importSalesSummary;
	public ImportMarketShareDao(final Connection connection, final String schema) {
		super(connection);
		this.schema = schema;
		importSalesSummary = new ImportSalesSummary();
	}

	@SuppressWarnings("unchecked")
	public void testImport(String csvFile) throws IllegalStateException, FileNotFoundException,
			UnsupportedEncodingException, NoSuchAlgorithmException {

		System.out.println("yes here " + csvFile);

		List<ImportMarketShare> list = new ArrayList<ImportMarketShare>();
		Map<String, List<ImportMarketShare>> salesInFile = new HashMap<String, List<ImportMarketShare>>();
		Map<String, List<ImportMarketShare>> salesInDB = new HashMap<String, List<ImportMarketShare>>();

		// List<ImportMarketShare> upcMatchInDB= new
		// ArrayList<ImportMarketShare>();

		// List<ImportMarketShare> salesInDB = new
		// ArrayList<ImportMarketShare>();
		@SuppressWarnings("unused")

		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		// list = new CsvToBeanBuilder(new FileReader(csvFile))
		// .withType(ImportMarketShare.class).withSkipLines(1)
		// .build().parse();

		try {
			salesInDB = getAllExistingSales();
			loadData(csvFile);
			System.out.println("sales in db: " + salesInDB.size());
			salesInFile = getAllSalesInTemp(salesInDB); 
			buildQuesriesUpcMatch();
			buildQuesriesUpcMatchWithinfile();
			buildQuesriesProductGroupingMatchWithinfile();
			QueriesNoMatch();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println(salesInFile.size());
		// int dups = 0;
		// int total = 0;
		//
		// for (final Entry<String, List<ImportMarketShare>> entry :
		// salesInFile.entrySet()){
		// dups += entry.getValue().size() - 1;
		// total += entry.getValue().size();
		//// final List<ImportMarketShare> v = entry.getValue();
		//
		//
		//// for (final ImportMarketShare element : v){
		//// System.out.println("la descriptio est de
		// "+element.getSales_description());
		//// }
		// }

		// System.out.println(dups);
		// System.out.println(total);
		// System.out.println("List of Duplicates");
		// for (final Entry<Double, String> entry :
		// duplicatesRecords.entrySet()){
		//
		// System.out.println(entry.getKey() + "/" + entry.getValue());
		// }

		// System.out.println("List of invalids");
		// for (final Entry<Double, String> entry : invalidRecords.entrySet()){
		//
		// System.out.println(entry.getKey() + "/" + entry.getValue());
		// }
		//
		System.out.println( importSalesSummary.getDuplicatesRecords().size()+ " is the number of duplicates");

		System.out.println(invalidRecords.size() + " is the number of invalids");

		stopWatch.split();
		System.out.println(
				"Total time spent on loading the sales data: " + (stopWatch.getSplitTime() / 1000) + " seconds.");

	}

	public Map<String, List<ImportMarketShare>> getAllExistingSales() throws SQLException {

		String sql = "select * from " + schema + ".sales";
		List<ImportMarketShare> list = new ArrayList<ImportMarketShare>();
		ResultSet resultSet = null;
		Map<String, List<ImportMarketShare>> data = new HashMap<String, List<ImportMarketShare>>();
		ImportMarketShare importMarketShare = null;

		try {

			resultSet = executeQuery(sql, null);

			while (resultSet.next()) {

				importMarketShare = new ImportMarketShare(

						resultSet.getDouble("sales_product_id_fkey"), resultSet.getString("sales_upc"),
						resultSet.getString("sales_description"), resultSet.getString("sales_brand"),
						resultSet.getString("sales_manufacturer"), resultSet.getDouble("dollar_rank"),
						resultSet.getDouble("dollar_volume"), resultSet.getDouble("dollar_share"),
						resultSet.getDouble("dollar_volume_percentage_change"), resultSet.getDouble("kilo_volume"),
						resultSet.getDouble("kilo_share"), resultSet.getDouble("kilo_volume_percentage_change"),
						resultSet.getDouble("average_ac_dist"), resultSet.getDouble("average_retail_price"),
						resultSet.getString("sales_source"), resultSet.getString("nielsen_category"),
						resultSet.getDate("sales_collection_date"), resultSet.getInt("sales_year"),
						resultSet.getBoolean("control_label_flag"), resultSet.getDouble("kilo_volume_total"),
						resultSet.getDouble("kilo_rank"), resultSet.getDouble("dollar_volume_total"),
						resultSet.getInt("cluster_number"), resultSet.getDouble("product_grouping"),
						resultSet.getString("sales_product_description"), resultSet.getString("classification_number"),
						resultSet.getString("classification_type"), resultSet.getString("sales_comment")

				);

				String sb = new StringBuilder().append(importMarketShare.getKilo_volume())
						.append(importMarketShare.getSales_upc()).append(importMarketShare.getSales_description())
						.append(importMarketShare.getSales_collection_date()).toString();

				if (!data.containsKey(sb)) {
					// data.get(sb).add(importMarketShare);
					// }
					// else {
					System.out.println("Voici " + sb);
					List<ImportMarketShare> item = new ArrayList<>();
					item.add(importMarketShare);
					data.put(sb, item);
				}

				if (!recordInDbByUPC.containsKey((resultSet.getString("sales_upc")))) {
					Integer val = (int) resultSet.getDouble("sales_product_id_fkey");
					System.out.println("The product id is " + val);
					recordInDbByUPC.put(resultSet.getString("sales_upc"), val);
				}
			}

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	public Map<String, List<ImportMarketShare>> getAllSalesInTemp(Map<String, List<ImportMarketShare>> existingSales)
			throws SQLException {

		String sql = "select * from " + schema + ".sales_temp";
		List<ImportMarketShare> list = new ArrayList<ImportMarketShare>();
		ResultSet resultSet = null;
		Map<String, List<ImportMarketShare>> data = new HashMap<String, List<ImportMarketShare>>();
		Map<Double, String> duplicatesRecords = new HashMap<Double, String>();

		ImportMarketShare importMarketShare = null;

		try {

			resultSet = executeQuery(sql, null);
			while (resultSet.next()) {

				importMarketShare = new ImportMarketShare(

						resultSet.getDouble("record_id"), resultSet.getString("sales_upc"),
						resultSet.getString("sales_description"), resultSet.getString("sales_brand"),
						resultSet.getString("sales_manufacturer"), resultSet.getDouble("dollar_rank"),
						resultSet.getDouble("dollar_volume"), resultSet.getDouble("dollar_share"),
						resultSet.getDouble("dollar_volume_percentage_change"), resultSet.getDouble("kilo_volume"),
						resultSet.getDouble("kilo_share"), resultSet.getDouble("kilo_volume_percentage_change"),
						resultSet.getDouble("average_ac_dist"), resultSet.getDouble("average_retail_price"),
						resultSet.getString("sales_source"), resultSet.getString("nielsen_category"),
						resultSet.getDate("sales_collection_date"), resultSet.getInt("sales_year"),
						resultSet.getBoolean("control_label_flag"), resultSet.getDouble("kilo_volume_total"),
						resultSet.getDouble("kilo_rank"), resultSet.getDouble("dollar_volume_total"),
						resultSet.getInt("cluster_number"), resultSet.getDouble("product_grouping"),
						resultSet.getString("sales_product_description"), resultSet.getString("classification_number"),
						resultSet.getString("classification_type"), resultSet.getString("sales_comment")

				);

				String sb = new StringBuilder().append(importMarketShare.getKilo_volume())
											   .append(importMarketShare.getSales_upc())
											   .append(importMarketShare.getSales_description())
											   .append(importMarketShare.getSales_collection_date()).toString();

				// value=value.concat(importMarketShare.getSales_description()
				// == null ?"":importMarketShare.getSales_description() );
				// value=value.concat(String.valueOf(importMarketShare.getKilo_volume())==null?);
				// value=value.concat(String.valueOf(importMarketShare.getSales_collection_date()));

				if (importMarketShare.isValidRecord()) {

					if (data.containsKey(sb) || existingSales.containsKey(sb)) {
						System.out.println(importMarketShare.getItem_id());
					  duplicatesRecords.put(importMarketShare.getItem_id(), importMarketShare.getSales_description());
						//importSalesSummary.getDuplicatesRecords().put(importMarketShare.getItem_id(), importMarketShare.getSales_description());
						// data.get(sb).add(importMarketShare);
					} else {
						List<ImportMarketShare> item = new ArrayList<>();
						item.add(importMarketShare);
						data.put(sb, item);

						if (salesInFileByUPC.containsKey(importMarketShare.getSales_upc())) {

							salesInFileByUPC.get(importMarketShare.getSales_upc()).add(importMarketShare);
						} else {
							List<ImportMarketShare> itemUpc = new ArrayList<>();
							itemUpc.add(importMarketShare);
							salesInFileByUPC.put(importMarketShare.getSales_upc(), itemUpc);

						}

					}
				} else {
					invalidRecords.put(importMarketShare.getItem_id(), importMarketShare.getSales_description());

				}

			}

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  importSalesSummary.setDuplicatesRecords(duplicatesRecords);

		return data;
	}

	public void loadData(String csvFile) throws SQLException {
		String sql = "COPY fcdrschema.sales_temp(record_id, sales_upc,sales_description, sales_brand, sales_manufacturer,dollar_rank,dollar_volume,dollar_share, "
				+ "   dollar_volume_percentage_change,kilo_volume, kilo_share, kilo_volume_percentage_change, average_ac_dist, average_retail_price, "
				+ "  sales_source, nielsen_category, sales_collection_date, sales_year, control_label_flag, kilo_volume_total, kilo_rank, dollar_volume_total, "
				+ " cluster_number,product_grouping, sales_product_description, classification_number, classification_type, sales_comment) "
				+ "FROM '" + csvFile + "'  CSV HEADER DELIMITER ','";

		Statement stmt = connection.createStatement();

		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buildQuesriesUpcMatch() {

		for (final Iterator<Entry<String, List<ImportMarketShare>>> it = salesInFileByUPC.entrySet().iterator(); it
				.hasNext();) {

			Entry<String, List<ImportMarketShare>> entry = it.next();
			// }
			//
			// for (final Entry<String, List<ImportMarketShare>> entry :
			// salesInFileByUPC.entrySet()){

			List<SalesInsertRequest> list = new ArrayList<SalesInsertRequest>();
			// List<List<Object>> obj = new ArrayList<List<Object>>();
			// dups += entry.getValue().size() - 1;
			// total += entry.getValue().size();
			// final List<ImportMarketShare> v = entry.getValue();

			// for (final ImportMarketShare element : v){
			// System.out.println("la descriptio est de
			// "+element.getSales_description());
			// }

			if (recordInDbByUPC.containsKey(entry.getKey())) {

				final List<ImportMarketShare> v = entry.getValue();

				for (final ImportMarketShare element : v) {

					list.add(new SalesInsertRequest(element.getSales_description(), element.getSales_upc(),
							element.getSales_brand(), element.getSales_manufacturer(), element.getDollar_rank(),
							element.getDollar_volume(), element.getDollar_share(),
							element.getDollar_volume_percentage_change(), element.getKilo_volume(),
							element.getKilo_share(), element.getKilo_volume_percentage_change(),
							element.getAverage_ac_dist(), element.getAverage_retail_price(), element.getSales_source(),
							element.getNielsen_category(), element.getsales_year(), element.getControl_label_flag(),
							element.getKilo_volume_total(), element.getKilo_rank(), element.getDollar_volume_total(),
							(double) element.getCluster_number(), element.getProduct_grouping(),
							element.getProduct_description(),

							element.getClassification_number(), element.getClassification_type(),
							element.getSales_comment(), null, // element.getSales_collection_date(),
							null, null, recordInDbByUPC.get(entry.getKey())

					));
					System.out.println("again " + recordInDbByUPC.get(entry.getKey()));

				}
				insertSales(list);

				// remove from Map
				// salesInFileByUPC.remove(entry.getKey());
				it.remove();

			}
		}

	}

	public void insertSales(List<SalesInsertRequest> v) {

		UpdateProductFields updateProductFields = new UpdateProductFields();

		StringBuffer sql = new StringBuffer("insert into " + schema + ".sales ( " + " sales_upc,sales_description, "
				+ "sales_brand, " + "sales_manufacturer," + "dollar_rank,dollar_volume, "
				+ "dollar_share, dollar_volume_percentage_change,kilo_volume, "
				+ "kilo_share, kilo_volume_percentage_change, average_ac_dist, "
				+ "average_retail_price, sales_source, nielsen_category, "
				+ " sales_year, control_label_flag, kilo_volume_total,"
				+ " kilo_rank, dollar_volume_total, cluster_number, product_grouping, "
				+ "classification_number, classification_type, "
				+ "sales_comment, sales_collection_date,  sales_product_description, "
				+ "number_of_units, edited_by, sales_product_id_fkey) values "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::date, ?, ?, ?, ?)");

		// for(int i=0; i<v.size()-1; i++){
		//
		// sql.append(", (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
		// ?, ?)");
		// }
		//
		try {
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

			// String values = " ";
			// int i = 0;

			for (SalesInsertRequest value : v) {

				preparedStatement.setString(1, value.getSales_upc());
				preparedStatement.setString(2, value.getSales_description());
				preparedStatement.setString(3, value.getSales_brand());
				preparedStatement.setString(4, value.getSales_manufacturer());
				preparedStatement.setDouble(5, value.getDollar_rank());
				preparedStatement.setDouble(6, value.getDollar_volume());
				preparedStatement.setDouble(7, value.getDollar_share());
				preparedStatement.setDouble(8, value.getDollar_volume_percentage_change());
				preparedStatement.setDouble(9, value.getKilo_volume());
				preparedStatement.setDouble(10, value.getKilo_share());
				preparedStatement.setDouble(11, value.getKilo_volume_percentage_change());
				preparedStatement.setDouble(12, value.getAverage_ac_dist());
				preparedStatement.setDouble(13, value.getAverage_retail_price());
				preparedStatement.setString(14, value.getSales_source());
				preparedStatement.setString(15, value.getNielsen_category());
				preparedStatement.setInt(16, value.getSales_year());
				preparedStatement.setBoolean(17, value.getControl_label_flag());
				preparedStatement.setDouble(18, value.getKilo_volume_total());
				preparedStatement.setDouble(19, value.getKilo_volume_rank());
				preparedStatement.setDouble(20, value.getDollar_volume_total());
				preparedStatement.setDouble(21, value.getCluster_number());
				preparedStatement.setDouble(22, value.getProduct_grouping());
				preparedStatement.setString(23, value.getClassification_number());
				preparedStatement.setString(24, value.getClassification_type());
				preparedStatement.setString(25, value.getSales_comment());
				preparedStatement.setString(26, value.getSales_collection_date());
				preparedStatement.setString(27, value.getSales_product_description());
				preparedStatement.setDouble(28, value.getCluster_number()); // change
																			// to
																			// number
																			// of
																			// unit
				preparedStatement.setString(29, "Romario");
				preparedStatement.setInt(30, value.getProduct_id());

				preparedStatement.addBatch();

				if (value.getSales_product_description() != null) {
					updateProductFields.setProduct_description(value.getSales_product_description());
				}
				if (value.getCluster_number() != null && value.getCluster_number() > 0) {
					updateProductFields.setCluster_number(value.getCluster_number().intValue());
				}
				if (value.getSales_manufacturer() != null) {
					updateProductFields.setProduct_manufacturer(value.getSales_manufacturer());
				}
				if (value.getSales_brand() != null) {
					updateProductFields.setProduct_brand(value.getSales_brand());
				}
				if (value.getClassification_number() != null) {
					updateProductFields.setClassification_number(value.getClassification_number());
				}

				updateProductFields.setProduct_id(value.getProduct_id());

			}

			System.out.println("la requette " + preparedStatement);
			preparedStatement.executeUpdate();
			connection.commit();
			// for(int i=0; i<v.size(); i++){
			// preparedStatement.setString(i, );
			//
			// }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			updateProductFields(updateProductFields);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void updateProductFields(UpdateProductFields updateProductFields) throws SQLException {

		try {
			connection.setAutoCommit(false);
			if (updateProductFields.getClassification_number() != null) {
				updateClassification(updateProductFields.getClassification_number(),
						updateProductFields.getProduct_id());
			}
			if (updateProductFields.getProduct_brand() != null) {
				updateBrand(updateProductFields.getProduct_brand(), updateProductFields.getProduct_id());
			}
			if (updateProductFields.getProduct_manufacturer() != null) {
				updateManufacturer(updateProductFields.getProduct_manufacturer(), updateProductFields.getProduct_id());
			}
			if (updateProductFields.getProduct_description() != null) {
				updateProductDescription(updateProductFields.getProduct_description(),
						updateProductFields.getProduct_id());
			}

			if (updateProductFields.getCluster_number() != null) {
				updateClusterNumber(updateProductFields.getCluster_number(), updateProductFields.getProduct_id());
			}
		} catch (SQLException e) {
			connection.rollback();
		}
		connection.commit();
	}

	public Boolean checkClassification(final String classificationNumber) throws DaoException {
		if ((classificationNumber == null))
			return false;

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

	public Boolean insertClassificationNumber(String classification_number, Integer product_id) {

		final String query1 = "insert into " + schema + "."
				+ "product_classification (product_classification_classification_id_fkey, product_classification_product_id_fkey) "
				+ " select classification_id, ? from " + schema + "classification where " + "classification_number = ?";

		try {
			executeUpdate(query1, new Object[] { product_id, classification_number });
			return true;
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public Boolean updateManufacturer(String product_manufacturer, Integer product_id) {

		if (product_manufacturer == null)
			return false;

		final String query = "update " + schema + "." + "product set product_manufacturer = ? where product_id = ?";

		try {
			executeUpdate(query, new Object[] { product_manufacturer, product_id });
			return true;
		} catch (final DaoException e) {
			e.printStackTrace();
			return false;
		}

	}

	public Boolean updateBrand(String product_brand, Integer product_id) {

		if (product_brand == null)
			return false;

		final String query = "update " + schema + "." + "product set product_brand = ? where product_id = ?";

		try {
			executeUpdate(query, new Object[] { product_brand, product_id });
			return true;
		} catch (final DaoException e) {
			e.printStackTrace();
			return false;
		}

	}

	public Boolean updateProductDescription(String product_description, Integer product_id) {

		if (product_description == null)
			return false;

		final String query = "update " + schema + "." + "product set product_description = ? where product_id = ?";

		try {
			executeUpdate(query, new Object[] { product_description, product_id });
			return true;
		} catch (final DaoException e) {
			e.printStackTrace();
			return false;
		}

	}

	public Boolean updateClusterNumber(Integer cluster_number, Integer product_id) {

		if (cluster_number == null)
			return false;

		final String query = "update " + schema + "." + "product set cluster_number = ? where product_id = ?";

		try {
			executeUpdate(query, new Object[] { cluster_number, product_id });
			return true;
		} catch (final DaoException e) {
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

	public void buildQuesriesUpcMatchWithinfile() {

		for (final Iterator<Entry<String, List<ImportMarketShare>>> it = salesInFileByUPC.entrySet().iterator(); it
				.hasNext();) {

			Entry<String, List<ImportMarketShare>> entry = it.next();
			final List<ImportMarketShare> v = entry.getValue();

			if (entry.getValue().size() > 1) {
				
				
				
				
				
				it.remove();

			} else {
				if (v.get(0).getProduct_grouping() > 0) {
					// build HashMap based on grouping

					//for (final ImportMarketShare element : v) {
							//System.out.println("the product groupings are: "+v.get(0).getProduct_grouping());
						if (salesWithGrouping.containsKey(v.get(0).getProduct_grouping())) {
							// append to object
							salesWithGrouping.get(v.get(0).getProduct_grouping()).add(v.get(0));

						} else {
							// new key
							List<ImportMarketShare> itemUpc = new ArrayList<>();
							itemUpc.add(v.get(0));
							salesWithGrouping.put(v.get(0).getProduct_grouping(), itemUpc);

						}
					//}
					it.remove();
				} else {
					// unique products

				}

			}

		}
	}
	
	public void buildQuesriesProductGroupingMatchWithinfile(){
		
		for (final Iterator<Entry<Double, List<ImportMarketShare>>> it = salesWithGrouping.entrySet().iterator(); it
				.hasNext();) {

			Entry<Double, List<ImportMarketShare>> entry = it.next();
			final List<ImportMarketShare> v = entry.getValue();
			
			if (entry.getValue().size() > 1) {
				
				
				
			}else{
				
				salesInFileByUPC.put(v.get(0).getSales_upc(), v);
				
				
			}
			
			
			
		}
		
		
		
		
	}
	
	public void QueriesNoMatch(){
		List<ProductInsertRequest> products_with_classification = new ArrayList<ProductInsertRequest>();
		List<ProductInsertRequest> products_with_out_classification = new ArrayList<ProductInsertRequest>();

		
		for (final Iterator<Entry<String, List<ImportMarketShare>>> it = salesInFileByUPC.entrySet().iterator(); it
				.hasNext();) {

			Entry<String, List<ImportMarketShare>> entry = it.next();
			
			
			
			final List<ImportMarketShare> v = entry.getValue();
			
				
				salesInFileByUPC.put(v.get(0).getSales_upc(), v);
				
				if(v.get(0).getClassification_number() != null && !v.get(0).getClassification_number().isEmpty()){
					products_with_classification.add(new ProductInsertRequest(
						v.get(0).getSales_manufacturer(), 
						v.get(0).getSales_brand(),
						v.get(0).getProduct_description().length()>0?v.get(0).getProduct_description():v.get(0).getSales_description(),
						null,
						null,
						v.get(0).getCluster_number().doubleValue(),
						null,
						null, 
						v.get(0).getClassification_number(),
						null,
						v.get(0).getClassification_type()
						
						));
				}else{
					products_with_out_classification.add(new ProductInsertRequest(
							v.get(0).getSales_manufacturer(), 
							v.get(0).getSales_brand(),
							v.get(0).getProduct_description().length()>0?v.get(0).getProduct_description():v.get(0).getSales_description(),
							null,
							null,
							v.get(0).getCluster_number().doubleValue(),
							null,
							null, 
							v.get(0).getClassification_number(),
							null,
							v.get(0).getClassification_type()
							
							));
				}
			}
			
			
			
		
		try {
			insertProducts(products_with_out_classification);
			insertProducts(products_with_classification);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void insertProducts(List<ProductInsertRequest> products) throws DaoException, SQLException{
		  ResultSet generatedKeys = null;
		final Timestamp now = DateUtil.getCurrentTimeStamp();
		
		String sql = "insert into "+schema+".product (product_manufacturer, product_description, "
				+ "cluster_number, product_brand, creation_date, last_edit_date) values (?, ?, ?, ?, ?, ?) ";

System.out.println("the size of the list is: "+products.size());		
		try {
			int i = 0;
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			for(ProductInsertRequest item:products){
				
				preparedStatement.setString(1, item.getProduct_manufacturer());
				preparedStatement.setString(2, item.getProduct_description());
				preparedStatement.setDouble(3, item.getCluster_number());
				preparedStatement.setString(4,item.getProduct_brand());
				preparedStatement.setObject(5, now);
				preparedStatement.setObject(6, now);
				

				preparedStatement.addBatch();
		            i++;

		            if (i % 1000 == 0 || i == products.size()) {
		            	preparedStatement.executeBatch(); // Execute every 1000 items.
		            	connection.commit();
			            generatedKeys = preparedStatement.getGeneratedKeys();
						while(generatedKeys.next()){
						System.out.println(generatedKeys.getInt(1) +"is the id");
		            }

				}
			
			}
//			System.out.println(preparedStatement);
//			preparedStatement.executeUpdate();
			
//			generatedKeys = preparedStatement.getGeneratedKeys();
//			
//			while(generatedKeys.next()){
//				System.out.println(generatedKeys.getInt(0) +"is the id");
//			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			connection.rollback();
			e.printStackTrace();
		}

		
		
		

		
	}

}
