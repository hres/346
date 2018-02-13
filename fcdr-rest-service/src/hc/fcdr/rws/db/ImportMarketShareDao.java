package hc.fcdr.rws.db;

import java.io.BufferedWriter;
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
import hc.fcdr.rws.model.sales.ImportSalesReport;
import hc.fcdr.rws.model.sales.ImportSalesSummary;
import hc.fcdr.rws.model.sales.SalesInsertRequest;
import hc.fcdr.rws.model.sales.UpdateProductFields;
import hc.fcdr.rws.util.DaoUtil;
import hc.fcdr.rws.util.DateUtil;
import hc.fcdr.rws.util.KeyValue;

import java.security.*;

public class ImportMarketShareDao extends PgDao {

	private static final Logger logger = Logger.getLogger(ImportSalesDao.class.getName());
	private final String schema;
	private static final String SQL_INSERT = "insert into ${table}(${keys}) values(${values})";
	private static final String TABLE_REGEX = "\\$\\{table\\}";
	private static final String KEYS_REGEX = "\\$\\{keys\\}";
	private static final String VALUES_REGEX = "\\$\\{values\\}";

//	Map<Double, String> duplicatesRecords = new HashMap<Double, String>();



	ImportSalesSummary importSalesSummary;
	
	public ImportMarketShareDao(final Connection connection, final String schema) {
		super(connection);
		this.schema = schema;
		importSalesSummary = new ImportSalesSummary();
	}

	public void testImport(String csvFile, BufferedWriter output) throws IllegalStateException, FileNotFoundException,
			UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {



		final StopWatch stopWatch = new StopWatch();
		
		stopWatch.start();
		System.out.println("Program has started");

		
		start(csvFile,output);


		

		stopWatch.split();
		System.out.println(
				"Total time spent on loading the sales data: " + (stopWatch.getSplitTime() / 1000) + " seconds.");

	}

	public Map<String, List<ImportMarketShare>> getAllExistingSales(Map<String, Integer> recordInDbByUPC) throws SQLException {

		String sql = "select * from " + schema + ".sales";
		List<ImportMarketShare> list = new ArrayList<ImportMarketShare>();
		ResultSet resultSet = null;
		Map<String, List<ImportMarketShare>> data = new HashMap<String, List<ImportMarketShare>>();
		ImportMarketShare importMarketShare = null;

		try {

			resultSet = executeQuery(sql, null);

			while (resultSet.next()) {
				Double dollar_share = resultSet.getDouble("dollar_share");
				dollar_share = resultSet.wasNull()?null:resultSet.getDouble("dollar_share");
			    Double dollar_rank = resultSet.getDouble("dollar_rank");
			    dollar_rank  = resultSet.wasNull()?null:resultSet.getDouble("dollar_rank");
			    Double dollar_volume = resultSet.getDouble("dollar_volume");
			    dollar_volume = resultSet.wasNull()?null:resultSet.getDouble("dollar_volume");
			    Double dollar_volume_percentage_change = resultSet.getDouble(
		                "dollar_volume_percentage_change");
			    dollar_volume_percentage_change = resultSet.getDouble(
		                "dollar_volume_percentage_change");
			    
			    Double kilo_volume = resultSet.getDouble("kilo_volume");
			    kilo_volume = resultSet.wasNull()?null:resultSet.getDouble("kilo_volume");
			    
			    Double kilo_share = resultSet.getDouble("kilo_share");
			    kilo_share  = resultSet.wasNull()?null:resultSet.getDouble("kilo_share");
		        Double kilo_volume_percentage_change = resultSet.getDouble(
		                "kilo_volume_percentage_change");
		        kilo_volume_percentage_change = resultSet.wasNull()?null:resultSet.getDouble("kilo_volume_percentage_change");
		        Double average_ac_dist = resultSet.getDouble("average_ac_dist");
		        average_ac_dist = resultSet.wasNull()?null:resultSet.getDouble("average_ac_dist");
		        Double average_retail_price = resultSet.getDouble(
		                "average_retail_price");
		        average_retail_price = resultSet.wasNull()?null:resultSet.getDouble("average_retail_price");
		        
		        
		        Boolean control_label_flag = resultSet.getBoolean(
		                "control_label_flag");
		        control_label_flag = resultSet.wasNull()?null:resultSet.getBoolean("control_label_flag");
		        Double kilo_volume_total = resultSet.getDouble("kilo_volume_total");
		        kilo_volume_total = resultSet.wasNull()?null:resultSet.getDouble("kilo_volume_total");
		        Double kilo_rank = resultSet.getDouble("kilo_rank");
		        kilo_rank = resultSet.wasNull()?null:resultSet.getDouble("kilo_rank");
		        
		        Double dollar_volume_total = resultSet.getDouble(
		                "dollar_volume_total");
		        
		        
		        dollar_volume_total = resultSet.getDouble("dollar_volume_total");
		        dollar_volume_total = resultSet.wasNull()?null:resultSet.getDouble("dollar_volume_total");
		        Integer cluster_number = resultSet.getInt("cluster_number");
		        cluster_number = resultSet.wasNull()?null:resultSet.getInt("cluster_number");
		        
		        Double product_grouping = resultSet.getDouble("product_grouping");
		        product_grouping = resultSet.wasNull()?null:resultSet.getDouble("product_grouping");
		        
		        Integer sales_year = resultSet.getInt("sales_year");
		        sales_year = resultSet.wasNull()?null:resultSet.getInt("sales_year");
				importMarketShare = new ImportMarketShare(

						resultSet.getDouble("sales_product_id_fkey"), resultSet.getString("sales_upc"),
						resultSet.getString("sales_description"), resultSet.getString("sales_brand"),
						resultSet.getString("sales_manufacturer"), dollar_rank,
						dollar_volume, dollar_share,dollar_volume_percentage_change, 
						kilo_volume,kilo_share, kilo_volume_percentage_change,
						average_ac_dist, average_retail_price,
						resultSet.getString("sales_source"), resultSet.getString("nielsen_category"),
						resultSet.getDate("sales_collection_date"), sales_year,
						control_label_flag, kilo_volume_total,
						kilo_rank, dollar_volume_total,
						cluster_number, product_grouping,
						resultSet.getString("sales_product_description"), resultSet.getString("classification_number"),
						resultSet.getString("classification_type"), resultSet.getString("sales_comment")

				);


				
				String sb = importMarketShare.getSales_upc() 
						+ importMarketShare.getKilo_volume() 
						+importMarketShare.getSales_description()
						+(resultSet.getDate("sales_collection_date")!= null? resultSet.getDate("sales_collection_date").toString():"null");

				if (!data.containsKey(sb)) {

					List<ImportMarketShare> item = new ArrayList<>();
					item.add(importMarketShare);
					data.put(sb, item);
				

				if (!recordInDbByUPC.containsKey((resultSet.getString("sales_upc")))) {
					Integer val = (int) resultSet.getDouble("sales_product_id_fkey");
					//System.out.println("The product id is " + val);
					recordInDbByUPC.put(resultSet.getString("sales_upc"), val);
				}
				
			}
			}

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	public Map<String, List<ImportMarketShare>> getAllSalesInTemp(Map<Double, String> invalidRecords,Map<String, 
			List<ImportMarketShare>> salesInFileByUPC, Map<String, Integer> recordInDbByUPC, ImportSalesReport importSalesReport)
			throws SQLException {

		Map<String, List<ImportMarketShare>> existingSales  = getAllExistingSales( recordInDbByUPC);
		String sql = "select * from " + schema + ".sales_temp";
		ResultSet resultSet = null;
		Map<String, List<ImportMarketShare>> data = new HashMap<String, List<ImportMarketShare>>();
		Map<Double, String> duplicatesRecords = new HashMap<Double, String>();

		
		ImportMarketShare importMarketShare = null;
		int count = 0;
		
		
		

		try {

			resultSet = executeQuery(sql, null);
			
			while (resultSet.next()) {
				Double dollar_share = resultSet.getDouble("dollar_share");
				dollar_share = resultSet.wasNull()?null:resultSet.getDouble("dollar_share");
			    Double dollar_rank = resultSet.getDouble("dollar_rank");
			    dollar_rank  = resultSet.wasNull()?null:resultSet.getDouble("dollar_rank");
			    Double dollar_volume = resultSet.getDouble("dollar_volume");
			    dollar_volume = resultSet.wasNull()?null:resultSet.getDouble("dollar_volume");
			    Double dollar_volume_percentage_change = resultSet.getDouble(
		                "dollar_volume_percentage_change");
			    
			    
			    dollar_volume_percentage_change = resultSet.wasNull()?null:resultSet.getDouble("dollar_volume_percentage_change");
			    
			    Double kilo_volume = resultSet.getDouble("kilo_volume");
			    kilo_volume = resultSet.wasNull()?null:resultSet.getDouble("kilo_volume");
			    
			    Double kilo_share = resultSet.getDouble("kilo_share");
			    kilo_share  = resultSet.wasNull()?null:resultSet.getDouble("kilo_share");
		        Double kilo_volume_percentage_change = resultSet.getDouble(
		                "kilo_volume_percentage_change");
		        kilo_volume_percentage_change = resultSet.wasNull()?null:resultSet.getDouble("kilo_volume_percentage_change");
		        Double average_ac_dist = resultSet.getDouble("average_ac_dist");
		        average_ac_dist = resultSet.wasNull()?null:resultSet.getDouble("average_ac_dist");
		        Double average_retail_price = resultSet.getDouble(
		                "average_retail_price");
		        average_retail_price = resultSet.wasNull()?null:resultSet.getDouble("average_retail_price");
		        
		        
		        Boolean control_label_flag = resultSet.getBoolean(
		                "control_label_flag");
		        control_label_flag = resultSet.wasNull()?null:resultSet.getBoolean("control_label_flag");
		        Double kilo_volume_total = resultSet.getDouble("kilo_volume_total");
		        kilo_volume_total = resultSet.wasNull()?null:resultSet.getDouble("kilo_volume_total");
		        Double kilo_rank = resultSet.getDouble("kilo_rank");
		        kilo_rank = resultSet.wasNull()?null:resultSet.getDouble("kilo_rank");
		        
		        Double dollar_volume_total = resultSet.getDouble(
		                "dollar_volume_total");
		        
		        
		        dollar_volume_total = resultSet.getDouble("dollar_volume_total");
		        dollar_volume_total = resultSet.wasNull()?null:resultSet.getDouble("dollar_volume_total");
		        Integer cluster_number = resultSet.getInt("cluster_number");
		        cluster_number = resultSet.wasNull()?null:resultSet.getInt("cluster_number");
		        
		        Double product_grouping = resultSet.getDouble("product_grouping");
		        product_grouping = resultSet.wasNull()?null:resultSet.getDouble("product_grouping");
		        
		        Integer sales_year = resultSet.getInt("sales_year");
		        sales_year = resultSet.wasNull()?null:resultSet.getInt("sales_year");
		        
				importMarketShare = new ImportMarketShare(

						resultSet.getDouble("record_id"), resultSet.getString("sales_upc"),
						resultSet.getString("sales_description"), resultSet.getString("sales_brand"),
						resultSet.getString("sales_manufacturer"), dollar_rank,
						dollar_volume, dollar_share,dollar_volume_percentage_change, 
						kilo_volume,kilo_share, kilo_volume_percentage_change,
						average_ac_dist, average_retail_price,
						resultSet.getString("sales_source"), resultSet.getString("nielsen_category"),
						resultSet.getDate("sales_collection_date"), sales_year,
						control_label_flag, kilo_volume_total,
						kilo_rank, dollar_volume_total,
						cluster_number, product_grouping,
						resultSet.getString("sales_product_description"), resultSet.getString("classification_number"),
						resultSet.getString("classification_type"), resultSet.getString("sales_comment")

				);
		
				++count;

				if (importMarketShare.isValidRecord()) {
					
					String sb = importMarketShare.getSales_upc() + importMarketShare.getKilo_volume() +importMarketShare.getSales_description()+resultSet.getDate("sales_collection_date").toString();

					if (data.containsKey(sb) || existingSales.containsKey(sb)) {
					  duplicatesRecords.put(importMarketShare.getItem_id(), importMarketShare.getSales_description());
						importSalesReport.getList_of_duplicate_records().add("RecordID: "+importMarketShare.getItem_id()+" "+importMarketShare.getSales_description());

					} else {
						//System.out.println("UPC: "+importMarketShare.getSales_upc() +" count "+(++count));
//						try {
//							output.write("UPC: "+importMarketShare.getSales_description());
//							 output.newLine();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
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
					
					importSalesReport.getList_of_invalid_records().add("RecordID: "+importMarketShare.getItem_id()+" "+importMarketShare.getSales_description());
				}

			}

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  importSalesSummary.setDuplicatesRecords(duplicatesRecords);
		  importSalesReport.setNumber_of_records(count);
		
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

	public void buildQuesriesUpcMatch(Map<String, List<ImportMarketShare>> salesInFileByUPC, Map<String, Integer> recordInDbByUPC,ImportSalesReport importSalesReport) {

		for (final Iterator<Entry<String, List<ImportMarketShare>>> it = salesInFileByUPC.entrySet().iterator(); it
				.hasNext();) {

			Entry<String, List<ImportMarketShare>> entry = it.next();

			
			List<SalesInsertRequest> list = new ArrayList<SalesInsertRequest>();


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
							element.getCluster_number()!=null?(double) element.getCluster_number():null, element.getProduct_grouping(),
							element.getProduct_description(),

							element.getClassification_number(), element.getClassification_type(),
							element.getSales_comment(), element.getSales_collection_date().toString(),
							null, null, recordInDbByUPC.get(entry.getKey())

					));
					importSalesReport.getList_of_records_linked_by_upc().add("RecordID: "+element.getItem_id()+" "+element.getSales_description());

				
				}
				insertSales(list);


				it.remove();

			}
		}

	}

	public void insertSales(List<SalesInsertRequest> v) {

		UpdateProductFields updateProductFields = new UpdateProductFields();
		final Timestamp now = DateUtil.getCurrentTimeStamp();

		StringBuffer sql = new StringBuffer("insert into " + schema + ".sales ( " + " sales_upc,sales_description, "
				+ "sales_brand, " + "sales_manufacturer," + "dollar_rank,dollar_volume, "
				+ "dollar_share, dollar_volume_percentage_change,kilo_volume, "
				+ "kilo_share, kilo_volume_percentage_change, average_ac_dist, "
				+ "average_retail_price, sales_source, nielsen_category, "
				+ " sales_year, control_label_flag, kilo_volume_total,"
				+ " kilo_rank, dollar_volume_total, cluster_number, product_grouping, "
				+ "classification_number, classification_type, "
				+ "sales_comment, sales_collection_date,  sales_product_description, "
				+ "number_of_units, edited_by, sales_product_id_fkey, creation_date, last_edit_date) values "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::date, ?, ?, ?, ?, ?, ?)");


		try {
			//connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());


			for (SalesInsertRequest value : v) {

				preparedStatement.setString(1, value.getSales_upc());
				preparedStatement.setString(2, value.getSales_description());
				preparedStatement.setString(3, value.getSales_brand());
				preparedStatement.setString(4, value.getSales_manufacturer());
				preparedStatement.setObject(5, value.getDollar_rank());
				preparedStatement.setObject(6, value.getDollar_volume());
				preparedStatement.setObject(7, value.getDollar_share());
				preparedStatement.setObject(8, value.getDollar_volume_percentage_change());
				preparedStatement.setObject(9, value.getKilo_volume());
				preparedStatement.setObject(10, value.getKilo_share());
				preparedStatement.setObject(11, value.getKilo_volume_percentage_change());
				preparedStatement.setObject(12, value.getAverage_ac_dist());
				preparedStatement.setObject(13, value.getAverage_retail_price());
				preparedStatement.setString(14, value.getSales_source());
				preparedStatement.setString(15, value.getNielsen_category());
				preparedStatement.setObject(16, value.getSales_year());
				preparedStatement.setBoolean(17, value.getControl_label_flag());
				preparedStatement.setObject(18, value.getKilo_volume_total());
				preparedStatement.setObject(19, value.getKilo_volume_rank());
				preparedStatement.setObject(20, value.getDollar_volume_total());
				preparedStatement.setObject(21, value.getCluster_number());
				preparedStatement.setObject(22, value.getProduct_grouping());
				preparedStatement.setString(23, value.getClassification_number());
				preparedStatement.setString(24, value.getClassification_type());
				preparedStatement.setString(25, value.getSales_comment());
				preparedStatement.setString(26, value.getSales_collection_date());
				preparedStatement.setString(27, value.getSales_product_description());
				preparedStatement.setInt(28, java.sql.Types.INTEGER); 
				preparedStatement.setString(29, "Romario");
				preparedStatement.setInt(30, value.getProduct_id());
				preparedStatement.setTimestamp(31, now);
				preparedStatement.setTimestamp(32, now);


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

			preparedStatement.executeUpdate();
			//connection.commit();


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

//try 
			//connection.setAutoCommit(false);
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
		
	//	connection.commit();
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

	public void buildQuesriesUpcMatchWithinfile(Map<String, List<ImportMarketShare>> salesInFileByUPC, Map<Double, List<ImportMarketShare>> salesWithGrouping, ImportSalesReport importSalesReport ) {

		for (final Iterator<Entry<String, List<ImportMarketShare>>> it = salesInFileByUPC.entrySet().iterator(); it
				.hasNext();) {

			Entry<String, List<ImportMarketShare>> entry = it.next();
			final List<ImportMarketShare> v = entry.getValue();

			if (entry.getValue().size() > 1) {
				
				insertRecordByGrouping(v);
				
				//ImportSalesReport importSalesReport
				for (ImportMarketShare element: v){
					importSalesReport.getList_of_records_linked_by_upc().add("RecordID: "+element.getItem_id()+" "+element.getSales_description());
	
				}
				
				
				it.remove();

			} else {
				if (v.get(0).getProduct_grouping() != null) {
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
	
	public void buildQuesriesProductGroupingMatchWithinfile(Map<String, List<ImportMarketShare>> salesInFileByUPC, Map<Double, List<ImportMarketShare>> salesWithGrouping, ImportSalesReport importSalesReport  ){
		
		for (final Iterator<Entry<Double, List<ImportMarketShare>>> it = salesWithGrouping.entrySet().iterator(); it
				.hasNext();) {

			Entry<Double, List<ImportMarketShare>> entry = it.next();
			final List<ImportMarketShare> v = entry.getValue();
			
			if (entry.getValue().size() > 1) {
				
				insertRecordByGrouping (v);
				
				for (ImportMarketShare element: v){
					importSalesReport.getList_of_records_linked_by_grouping().add("RecordID: "+element.getItem_id()+" "+element.getSales_description());
	
				}

				
			}else{
				
				salesInFileByUPC.put(v.get(0).getSales_upc(), v);
				
				
			}
			
			
			
		}
		
	}
	
	private void insertRecordByGrouping(List<ImportMarketShare> v) {
		List<SalesInsertRequest> list = new ArrayList<SalesInsertRequest>();

		ProductInsertRequest product;
		
		product = new ProductInsertRequest(
				v.get(0).getSales_manufacturer(), 
				v.get(0).getSales_brand(),
				v.get(0).getProduct_description().length()>0?v.get(0).getProduct_description():v.get(0).getSales_description(),
				null,
				null,
				v.get(0).getCluster_number()!=null?v.get(0).getCluster_number().doubleValue():null,
				null,
				null, 
				v.get(0).getClassification_number(),
				null,
				v.get(0).getClassification_type()
				
				);
		int key = 0;
		try {
			key = insertProducts(product);
			if(v.get(0).getClassification_number() != null && !v.get(0).getClassification_number().isEmpty()){
				if(checkClassification(product.getClassification_number())){
				insertClassificationNumber(product.getClassification_number(), key);

				
			}
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (ImportMarketShare element: v){
			list.add(new SalesInsertRequest(element.getSales_description(), element.getSales_upc(),
					element.getSales_brand(), element.getSales_manufacturer(), element.getDollar_rank(),
					element.getDollar_volume(), element.getDollar_share(),
					element.getDollar_volume_percentage_change(), element.getKilo_volume(),
					element.getKilo_share(), element.getKilo_volume_percentage_change(),
					element.getAverage_ac_dist(), element.getAverage_retail_price(), element.getSales_source(),
					element.getNielsen_category(), element.getsales_year(), element.getControl_label_flag(),
					element.getKilo_volume_total(), element.getKilo_rank(), element.getDollar_volume_total(),
					element.getCluster_number() !=null?(double) element.getCluster_number():null, element.getProduct_grouping(),
					element.getProduct_description(),

					element.getClassification_number(), element.getClassification_type(),
					element.getSales_comment(), element.getSales_collection_date().toString(),
					null, null, key)

			);
	}
		try {
			insertSalesNoUpdate(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void QueriesNoMatch(Map<String, List<ImportMarketShare>> salesInFileByUPC,ImportSalesReport importSalesReport ){
		ProductInsertRequest products_with_classification = new ProductInsertRequest();
	
		List<SalesInsertRequest> list = new ArrayList<SalesInsertRequest>();
		
		for (final Iterator<Entry<String, List<ImportMarketShare>>> it = salesInFileByUPC.entrySet().iterator(); it
				.hasNext();) {

			Entry<String, List<ImportMarketShare>> entry = it.next();
			
			
			
			final List<ImportMarketShare> v = entry.getValue();
			
				
				salesInFileByUPC.put(v.get(0).getSales_upc(), v);
				

				products_with_classification = new ProductInsertRequest(
					v.get(0).getSales_manufacturer(), 
					v.get(0).getSales_brand(),
					v.get(0).getProduct_description().length()>0?v.get(0).getProduct_description():v.get(0).getSales_description(),
					null,
					null,
					v.get(0).getCluster_number() != null?v.get(0).getCluster_number().doubleValue():null,
					null,
					null, 
					v.get(0).getClassification_number(),
					null,
					v.get(0).getClassification_type()
					
					);
				int key;
				try {
					key = insertProducts(products_with_classification);
                    populateList(list, key,v.get(0), importSalesReport);
					if(v.get(0).getClassification_number() != null && !v.get(0).getClassification_number().isEmpty()){
					if(checkClassification(products_with_classification.getClassification_number())){
					insertClassificationNumber(products_with_classification.getClassification_number(), key);

					
				}else{
					//System.out.println("the class number is: "+v.get(0).getClassification_number());
				}
					}
				} catch (DaoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


				
			}
		
	
		
		try {
			insertSalesNoUpdate(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void populateList(List<SalesInsertRequest> list, int key, ImportMarketShare element, ImportSalesReport importSalesReport) {
		
		importSalesReport.getList_of_records_to_new_product().add("RecordID: "+element.getItem_id()+" "+element.getSales_description());

		list.add(new SalesInsertRequest(element.getSales_description(), element.getSales_upc(),
				element.getSales_brand(), element.getSales_manufacturer(), element.getDollar_rank(),
				element.getDollar_volume(), element.getDollar_share(),
				element.getDollar_volume_percentage_change(), element.getKilo_volume(),
				element.getKilo_share(), element.getKilo_volume_percentage_change(),
				element.getAverage_ac_dist(), element.getAverage_retail_price(), element.getSales_source(),
				element.getNielsen_category(), element.getsales_year(), element.getControl_label_flag(),
				element.getKilo_volume_total(), element.getKilo_rank(), element.getDollar_volume_total(),
				element.getCluster_number()!=null?(double) element.getCluster_number():null, 
				element.getProduct_grouping(),element.getProduct_description(),
			
				element.getClassification_number(), element.getClassification_type(),
				element.getSales_comment(), element.getSales_collection_date().toString(),
				null, null, key)

		);
		
	}

	public int  insertProducts(ProductInsertRequest item) throws DaoException, SQLException{
		   ResultSet generatedKeys = null;
		final Timestamp now = DateUtil.getCurrentTimeStamp();
		
		String sql = "insert into "+schema+".product (product_manufacturer, product_description, "
				+ "cluster_number, product_brand, creation_date, last_edit_date) values (?, ?, ?, ?, ?, ?) ";

	
			
				 int obj = (int) executeUpdate(sql, new Object[]{item.getProduct_manufacturer(),item.getProduct_description(),item.getCluster_number(),
						 item.getProduct_brand(), now, now});
	            

		
		return obj;

		
	}
	
	public void insertSalesNoUpdate(List<SalesInsertRequest> v) throws SQLException {
		final Timestamp now = DateUtil.getCurrentTimeStamp();

		System.out.println(v.size()+" is the size of the list");

		String sql = "insert into " + schema + ".sales ( " + " sales_upc,sales_description, "
				+ "sales_brand, " + "sales_manufacturer," + "dollar_rank,dollar_volume, "
				+ "dollar_share, dollar_volume_percentage_change,kilo_volume, "
				+ "kilo_share, kilo_volume_percentage_change, average_ac_dist, "
				+ "average_retail_price, sales_source, nielsen_category, "
				+ " sales_year, control_label_flag, kilo_volume_total,"
				+ " kilo_rank, dollar_volume_total, cluster_number, product_grouping, "
				+ "classification_number, classification_type, "
				+ "sales_comment, sales_collection_date,  sales_product_description, "
				+ "number_of_units, edited_by, sales_product_id_fkey, creation_date, last_edit_date) values "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::date, ?, ?, ?, ?, ?, ?)";

		 int i = 0;
         final PreparedStatement preparedStatement =
                 connection.prepareStatement(sql);
			for (SalesInsertRequest value : v) {
				

                
				preparedStatement.setString(1, value.getSales_upc());
				preparedStatement.setString(2, value.getSales_description());
				preparedStatement.setString(3, value.getSales_brand());
				preparedStatement.setString(4, value.getSales_manufacturer());
				preparedStatement.setObject(5, value.getDollar_rank());
				preparedStatement.setObject(6, value.getDollar_volume());
				preparedStatement.setObject(7, value.getDollar_share());
				preparedStatement.setObject(8, value.getDollar_volume_percentage_change());
				preparedStatement.setObject(9, value.getKilo_volume());
				preparedStatement.setObject(10, value.getKilo_share());
				preparedStatement.setObject(11, value.getKilo_volume_percentage_change());
				preparedStatement.setObject(12, value.getAverage_ac_dist());
				preparedStatement.setObject(13, value.getAverage_retail_price());
				preparedStatement.setString(14, value.getSales_source());
				preparedStatement.setString(15, value.getNielsen_category());
				preparedStatement.setObject(16, value.getSales_year());
				preparedStatement.setObject(17, value.getControl_label_flag());
				preparedStatement.setObject(18, value.getKilo_volume_total());
				preparedStatement.setObject(19, value.getKilo_volume_rank());
				preparedStatement.setObject(20, value.getDollar_volume_total());
				preparedStatement.setObject(21, value.getCluster_number());
				preparedStatement.setObject(22, value.getProduct_grouping());
				preparedStatement.setString(23, value.getClassification_number());
				preparedStatement.setString(24, value.getClassification_type());
				preparedStatement.setString(25, value.getSales_comment());
				preparedStatement.setString(26, value.getSales_collection_date());
				preparedStatement.setString(27, value.getSales_product_description());
				preparedStatement.setObject(28, java.sql.Types.INTEGER); // change
																			// to
																			// number
																			// of
																			// unit
				preparedStatement.setString(29, "Romario");
				preparedStatement.setInt(30, value.getProduct_id());
				preparedStatement.setTimestamp(31, now);
				preparedStatement.setTimestamp(32, now);

				preparedStatement.addBatch();
	            
				i++;

	            if (i % 1000 == 0 || i == v.size()) {
	            	preparedStatement.executeBatch(); // Execute every 1000 items.
	            	
		         
			}



			}


		

	}
	
	public void start(String csvFile, BufferedWriter output) throws SQLException{
		//emptyTempTable();
		Map<String, Integer> recordInDbByUPC = new HashMap<String, Integer>();
		Map<String, List<ImportMarketShare>> salesInFile= new HashMap<String, List<ImportMarketShare>>();

		Map<Double, String> invalidRecords = new HashMap<Double, String>();
		Map<String, List<ImportMarketShare>> salesInFileByUPC = new HashMap<String, List<ImportMarketShare>>();
		Map<Double, List<ImportMarketShare>> salesWithGrouping = new HashMap<Double, List<ImportMarketShare>>();
		ImportSalesReport importSalesReport = new ImportSalesReport();

		try{
			connection.setAutoCommit(false);
			emptyTempTable();
			loadData(csvFile);
		salesInFile =	getAllSalesInTemp(invalidRecords, salesInFileByUPC, recordInDbByUPC, importSalesReport); 
		System.out.println("done splitting data");
		buildQuesriesUpcMatch(salesInFileByUPC, recordInDbByUPC, importSalesReport);
		buildQuesriesUpcMatchWithinfile(salesInFileByUPC, salesWithGrouping, importSalesReport);
		buildQuesriesProductGroupingMatchWithinfile(salesInFileByUPC, salesWithGrouping, importSalesReport);
		QueriesNoMatch(salesInFileByUPC, importSalesReport);
		connection.commit();
		}catch (SQLException e){
			logger.error(e);
			connection.rollback();
		}
		
		try {
			output.write("Number of records in the file: "+importSalesReport.getNumber_of_records());
			output.newLine();
			
			output.write("Number of dupplicate records: "+importSalesReport.getList_of_duplicate_records().size());
			output.newLine();
			output.write("Number of skipped records: "+importSalesReport.getList_of_invalid_records().size());
			output.newLine();
			
			output.write("Number of records linked by upc: "+importSalesReport.getList_of_records_linked_by_upc().size());
			output.newLine();
			
			output.write("Number of records linked by product grouping: "+importSalesReport.getList_of_records_linked_by_grouping().size());
			output.newLine();
			output.write("Number of records attached to new products: "+importSalesReport.getList_of_records_to_new_product().size());
			output.newLine();
			output.newLine();
			
			output.write("List of duplicate records");
			output.newLine();
			output.newLine();
			for(String element: importSalesReport.getList_of_duplicate_records()){
				output.write(element);
				output.newLine();
			}
			output.newLine();
			output.newLine();
			output.newLine();
			
			output.write("List of skipped records");
			output.newLine();
			output.newLine();
			for(String element: importSalesReport.getList_of_invalid_records()){
				output.write(element);
				output.newLine();
			}
			output.newLine();
			output.newLine();
			output.newLine();
			output.write("List of records linked by upc");
			output.newLine();
			output.newLine();
			for(String element: importSalesReport.getList_of_records_linked_by_upc()){
				output.write(element);
				output.newLine();
			}
			output.newLine();
			output.newLine();
			output.newLine();
			output.write("List of records linked by product grouping");
			output.newLine();
			output.newLine();
			for(String element: importSalesReport.getList_of_records_linked_by_grouping()){
				output.write(element);
				output.newLine();
			}
			output.newLine();
			output.newLine();
			output.newLine();
			
			output.write("List of records attached to new products");
			output.newLine();
			output.newLine();
			for(String element: importSalesReport.getList_of_records_to_new_product()){
				output.write(element);
				output.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(salesInFile.size());
		if(salesInFile.size() < 1){
			 try {
				output.write("Failed uploading the file");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		System.out.println( importSalesSummary.getDuplicatesRecords().size()+ " is the number of duplicates");

		System.out.println(invalidRecords.size() + " is the number of invalids");

	}

	public void emptyTempTable() throws SQLException{
		
		Statement statement = connection.createStatement();
		
		String sql = "truncate "+schema+".sales_temp";
		  int result = statement.executeUpdate(sql);
	
	}
}
