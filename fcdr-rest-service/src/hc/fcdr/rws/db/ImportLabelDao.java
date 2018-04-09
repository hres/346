package hc.fcdr.rws.db;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.importLabel.ExistingLabels;
import hc.fcdr.rws.model.importLabel.ImportLabelModel;
import hc.fcdr.rws.model.importLabel.ImportLabelNft;
import hc.fcdr.rws.model.importLabel.ImportLabelReport;
import hc.fcdr.rws.model.importLabel.ImportLabelRequest;
import hc.fcdr.rws.util.DaoUtil;
import hc.fcdr.rws.util.DateUtil;
import hc.fcdr.rws.util.LabelGrouping;

public class ImportLabelDao extends PgDao{
	private static final String SQL_INSERT = "insert into ${table}(${keys}) values(${values})";
	private static final String TABLE_REGEX = "\\$\\{table\\}";
	private static final String KEYS_REGEX = "\\$\\{keys\\}";
	private static final String VALUES_REGEX = "\\$\\{values\\}";
	private static final Logger logger = Logger.getLogger(ImportLabelDao.class.getName());
	private final String schema;
	ImportLabelReport importLabelReport;
	
	
	
		
	
	public ImportLabelDao(Connection connection, final String schema) {
		super(connection);
		this.schema = schema;
		importLabelReport = new ImportLabelReport();
	}
	
	public void importLabel(String filePath, BufferedWriter output)throws IllegalStateException, FileNotFoundException,
	UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
		final StopWatch stopWatch = new StopWatch();
		

		stopWatch.start();
		System.out.println("Program has started");

		try {
			start(filePath,output);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		stopWatch.split();
		System.out.println(
				"Total time spent on loading the sales data: " + (stopWatch.getSplitTime() / 1000) + " seconds.");

	}
	private void start(String file, BufferedWriter output) throws SQLException {
		importLabelReport = new ImportLabelReport();
		// Will hold all distinct existing labels
		// used to check for duplicates
		Map<String, List<ExistingLabels>> existingLabels = new HashMap<String, List<ExistingLabels>>();

		// Will hold all valid records from the csv file with Label UPC as key
		Map<String, List<ImportLabelModel>> dataFromTempByUpc = new HashMap<String, List<ImportLabelModel>>();
		// Will have distinct existing label with Label UPC as key
		Map<String, List<ExistingLabels>> existingLabelStoredByUPC = new HashMap<String, List<ExistingLabels>>();

		// Will contains all distinct sales upcs as key and their product id as
		// value
		Map<String, Integer> mapUpcImRank = new HashMap<String, Integer>();

		// Will contain all unique Labels from the CSV file
		List<ImportLabelModel> uniqueLabels = new ArrayList<ImportLabelModel>();

		// Will contain all records in in the CSV file which have a product
		// grouping value
		Map<Double, List<ImportLabelModel>> labelSortedByGrouping = new HashMap<Double, List<ImportLabelModel>>();
		
		Map<String, List<ImportLabelModel>>  dups = null;// =  new HashMap<String, List<ImportLabelModel>>();

		try {
			connection.setAutoCommit(false);
			emptyTempTable();

			loadData(file);

			existingLabelStoredByUPC = getExistingLabels(existingLabels);

			dups = getDataFromTemp(existingLabels, dataFromTempByUpc);

			labelUpcMatch(dataFromTempByUpc, existingLabelStoredByUPC);
			mapUpcImRank = getUpcRank();
			try {
				labelSortedByGrouping = labelByUpcWithinFile(uniqueLabels, dataFromTempByUpc, mapUpcImRank);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buildByGrouping(labelSortedByGrouping, uniqueLabels, mapUpcImRank);
			buildQueryUniqueLabel(uniqueLabels);
			connection.commit();

		} catch (SQLException e) {
			logger.error(e);
			connection.rollback();
//			e.printStackTrace();
		}

		System.out.println("Number of existing label " + existingLabels.size());

		try {
			
			output.write("Number of records in the file: "+importLabelReport.numberOfRecords);
			output.newLine();
			output.write("just writing some text");
			
			output.write("Number of dupplicate records: "+importLabelReport.getDuplicatesLabels().size());
			output.newLine();
			
			output.write("Number of skipped records: "+importLabelReport.getSkippedRecords().size());
			output.newLine();
			
			output.write("Number of records linked by labek upc: "+importLabelReport.getLabelsLinkedByLabelUpc());
			output.newLine();

			output.write("Number of records linked by nielsen item rank: "+importLabelReport.getLabelsLinkedByNielsenItemRank());
			output.newLine();
			
			output.write("Number of records linked by product grouping: "+importLabelReport.getLabelSharingSameGrouping());
			output.newLine();
			
			output.write("Number of records with invalid product grouping/brand/manufacturer/restaurant type "+importLabelReport.getProductGroupingAndLabelUpcNoMatch());
			output.newLine();
			
			output.write("Number of records attached to new products: "+importLabelReport.getLabelsLinkedToNewProducts());
			output.newLine();
			output.newLine();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

		System.out.println("Number of invalid records in the file " + importLabelReport.getSkippedRecords().size());
		System.out.println("Number of duplicate records in the file " + importLabelReport.getDuplicatesLabels().size());
		System.out.println("Number of records sharing product grouping in the file " + importLabelReport.getLabelSharingSameGrouping().size());
		System.out.println("Number of records linked by label upc in the file " + importLabelReport.getLabelsLinkedByLabelUpc().size());
		System.out.println("Number of records linked by nielsen item rank in the file " + importLabelReport.getLabelsLinkedByNielsenItemRank().size());
		System.out.println("Number of records linked to new products in the file " + importLabelReport.getLabelsLinkedToNewProducts().size());
		System.out.println("Number of records with wrong product grouping/label upc " + importLabelReport.getProductGroupingAndLabelUpcNoMatch().size());

		
	}
	
	// empty the staging table
	public void emptyTempTable() throws SQLException {

		Statement statement = connection.createStatement();

		String sql = "truncate " + schema + ".label_temp";

		statement.executeUpdate(sql);

	}
	
	// load the file in the staging table
	public void loadData(String csvFile) throws SQLException {

		String sql = "COPY " + schema + ".label_temp " + "	( " + " record_id , " + " label_upc, "
				+ " nielsen_item_rank, " + "  nielsen_category, " + " label_description, " + " label_brand, "
				+ " label_manufacturer, " + " label_country, " + " package_size, " + " package_size_unit_of_measure, "
				+ "  number_of_units, " + " storage_type, " + " storage_statement, " + " collection_date, "
				+ " health_claims, " + " nutrient_claims, " + " other_package_statements, " + " suggested_directions, "
				+ " ingredients, " + " multipart, " + " nutrition_fact_table, " + " common_household_measure, "
				+ " per_serving_amount_as_sold, " + " per_serving_amount_unit_of_measure_as_sold, "
				+ " per_serving_amount_as_prepared, " + " per_serving_amount_unit_of_measure_as_prepared, "
				+ " energy_kcal_as_sold, " + " energy_kcal_as_prepared, " + " energy_kj_as_sold, "
				+ " energy_kj_as_prepared, " + " fat_as_sold, " + " fat_daily_value_as_sold, "
				+ " fat_daily_value_as_prepared, " + "  saturated_fat_as_sold, " + " trans_fat_as_sold, "
				+ " saturated_plus_trans_daily_value_as_sold,  " + " saturated_plus_trans_daily_value_as_prepared, "
				+ " fat_polyunsatured_as_sold, " + " fat_polyunsaturated_omega_6_as_sold, "
				+ " fat_polyunsaturated_omega_3_as_sold, " + " fat_monounsaturated_as_sold, "
				+ " carbohydrates_as_sold, " + " carbohydrates_daily_value_as_sold, "
				+ " carbohydrates_daily_value_as_prepared, " + " fibre_as_sold, " + " fibre_daily_value_sold, "
				+ "  fibre_daily_value_as_prepared, " + " soluble_fibre_as_sold, " + " insoluble_fibre_as_sold, "
				+ " sugar_total_sold, " + " sugar_total_daily_value_as_sold, "
				+ " sugar_total_daily_value_as_prepared, " + " sugar_alcohols_as_sold, " + " starch_as_sold, "
				+ " protein_as_sold, " + " cholesterol_as_sold, " + " cholesterol_daily_value_as_sold, "
				+ " cholesterol_daily_value_as_prepared, " + " sodium_as_sold, " + " sodium_daily_value_as_sold, "
				+ " sodium_daily_value_as_prepared, " + " potassium_as_sold, " + " potassium_daily_value_as_sold, "
				+ " potassium_daily_value_as_prepared, " + " calcium_as_sold, " + " calcium_daily_value_as_sold, "
				+ " calcium_daily_value_as_prepared, " + " iron_as_sold, " + " iron_daily_value_as_sold, "
				+ " iron_daily_value_as_prepared, " + " vitamin_a_as_sold, " + " vitamin_a_daily_value_as_sold, "
				+ " vitamin_a_daily_value_as_prepared, " + " vitamin_c_as_sold, " + " vitamin_c_daily_value_as_sold, "
				+ " vitamin_c_daily_value_as_prepared, " + " vitamin_d_as_sold, " + " vitamin_d_daily_value_as_sold, "
				+ " vitamin_d_daily_value_as_prepared, " + " vitamin_e_as_sold, " + " vitamin_e_daily_value_as_sold, "
				+ " vitamin_e_daily_value_as_prepared,  	 " + " vitamin_k_as_sold, "
				+ " vitamin_k_daily_value_as_sold, " + " vitamin_k_daily_value_as_prepared, " + " thiamine_as_sold, "
				+ " thiamine_daily_value_as_sold, " + " thiamine_daily_value_as_prepared,  " + " riboflavin_as_sold, "
				+ " riboflavin_daily_value_as_sold, " + " riboflavin_daily_value_as_prepared,   " + " niacin_as_sold, "
				+ " niacin_daily_value_as_sold, " + " niacin_daily_value_as_prepared,   " + " vitamin_b6_as_sold, "
				+ " vitamin_b6_daily_value_as_sold, " + " vitamin_b6_daily_value_as_prepared,   " + " folate_as_sold, "
				+ " folate_daily_value_as_sold, " + " folate_daily_value_as_prepared,   " + " vitamin_b12_as_sold, "
				+ " vitamin_b12_daily_value_as_sold, " + " vitamin_b12_daily_value_as_prepared,  " + " biotin_as_sold, "
				+ " biotin_daily_value_as_sold, " + " biotin_daily_value_as_prepared, " + " choline_as_sold, "
				+ " choline_daily_value_as_sold, " + " choline_daily_value_as_prepared,    " + " pantothenate_as_sold, "
				+ " pantothenate_daily_value_as_sold, " + " pantothenate_daily_value_as_prepared, "
				+ " phosphorus_as_sold, " + " phosphorus_daily_value_as_sold, "
				+ " phosphorus_daily_value_as_prepared,   " +

				" iodide_as_sold, " + " iodide_daily_value_as_sold, " + " iodide_daily_value_as_prepared,   " +

				" magnesium_as_sold, " + " magnesium_daily_value_as_sold, " + " magnesium_daily_value_as_prepared,   "
				+ " zinc_as_sold, " + " zinc_daily_value_as_sold, " + " zinc_daily_value_as_prepared,    "
				+ " selenium_as_sold, " + " selenium_daily_value_as_sold, " + "  selenium_daily_value_as_prepared,   "
				+ " copper_as_sold, " + " copper_daily_value_as_sold, " + " copper_daily_value_as_prepared,    "
				+ "  manganese_as_sold, " + " manganese_daily_value_as_sold, "
				+ " manganese_daily_value_as_prepared,   " + " chromium_as_sold, " + " chromium_daily_value_as_sold, "
				+ " chromium_daily_value_as_prepared,  " + " molybdenum_as_sold, " + " molybdenum_daily_value_as_sold, "
				+ " molybdenum_daily_value_as_prepared,  " + " chloride_as_sold, " + " chloride_daily_value_as_sold, "
				+ " chloride_daily_value_as_prepared,  " + " per_serving_amount_in_grams_as_sold, "
				+ " per_serving_amount_in_grams_as_prepared, " + " label_comment, " + " label_source, "
				+ " product_description, " + " type, " + " type_of_restaurant, " + " informed_dining_program, "
				+ " nft_last_update_date, " + " child_item, " + " product_grouping, " + " classification_number ) "
				+ "FROM '" + csvFile + "'  CSV HEADER DELIMITER ','";

		Statement stmt = connection.createStatement();

		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// Populate data with labels in the DB with label_upc, label_description,
	// label_source,label_collection_date as key
	// Also build and return a map with existing labels. Label upc is the key
	private Map<String, List<ExistingLabels>> getExistingLabels(Map<String, List<ExistingLabels>> data) {

		Map<String, List<ExistingLabels>> existingLabelStoredByUPC = new HashMap<String, List<ExistingLabels>>();

		ResultSet resultSet = null;
		String sql = "select package_product_id_fkey, package_upc, "
				+ " package_description, product_grouping,  package_source, package_collection_date from " + schema
				+ ".package";

		try {
			ExistingLabels existingLabel;
			resultSet = executeQuery(sql, null);
			try {
				while (resultSet.next()) {

					Integer product_id = resultSet.getInt("package_product_id_fkey");
					String label_description = resultSet.getString("package_description");
					String label_source = resultSet.getString("package_source");
					Date label_collection_date = resultSet.getDate("package_collection_date");
					String label_upc = resultSet.getString("package_upc");
					Double product_grouping = resultSet.getDouble("product_grouping");

					existingLabel = new ExistingLabels(product_id, label_upc, label_description, label_source,
							label_collection_date, product_grouping);

					String sb = existingLabel.getLabel_description() + existingLabel.getLabel_upc()
							+ existingLabel.getLabel_source() + (existingLabel.getLabel_collection_date() != null
									? existingLabel.getLabel_collection_date().toString() : "null");

					// System.out.println("**"+sb);

					if (!data.containsKey(sb)) {
						List<ExistingLabels> item = new ArrayList<>();
						item.add(existingLabel);
						data.put(sb, item);
						if (!existingLabelStoredByUPC.containsKey(existingLabel.getLabel_upc())) {
							existingLabelStoredByUPC.put(existingLabel.getLabel_upc(), item);
						}

					}

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existingLabelStoredByUPC;
	}




	
	// This methods get data from the staging table, check if valid, build a key
	// sb and check if it exists in the DB
	// it stores all invalid records in invalidsLabels
	// stores all duplicates in duplicatesLabels
	// stores non invalids and non duplicates records in dataFromTempByUpc
	
	public Map<String, List<ImportLabelModel>> getDataFromTemp(Map<String, List<ExistingLabels>> labelInDb,Map<String, List<ImportLabelModel>> dataFromTempByUpc) throws SQLException {
		
		//Map<Double, String> duplicatesLabels = new HashMap<Double, String>();
		
		Map<String, List<ImportLabelModel>> dups = new HashMap<String, List<ImportLabelModel>>();
		Map<String, ImportLabelModel> data = new HashMap<String, ImportLabelModel>();
		int count = 0;
		String sql = "select * from " + schema + ".label_temp";
		ResultSet resultSet = null;
		ImportLabelModel importLabelModel;

		
		try {
			resultSet = executeQuery(sql, null);

			while (resultSet.next()) {
				++count;
				importLabelModel = DaoUtil.populateLabelModel(resultSet);
				
				String sb = importLabelModel.getImportLabelRequest().getPackage_description()
						+ importLabelModel.getImportLabelRequest().getPackage_upc()
						+ importLabelModel.getImportLabelRequest().getPackage_source()
						+ importLabelModel.getImportLabelRequest().getPackage_collection_date();

				if (importLabelModel.isValid()) {
					String str = ""+importLabelModel.getImportLabelRequest().getRecord_id()+" "+importLabelModel.getImportLabelRequest().getPackage_description();

					if(!labelInDb.containsKey(sb)) {
						
						
						
						if(!dups.containsKey(sb) &&!data.containsKey(sb)) {
							//clean data
							data.put(sb, importLabelModel);
							
																				
							
						}else if(dups.containsKey(sb) &&!data.containsKey(sb)) {
							dups.get(sb).add(importLabelModel);
							//System.out.println("Upc: "+importLabelModel.getImportLabelRequest().getPackage_upc());
							importLabelReport.getDuplicatesLabels().add(str);
							
						}else if(!dups.containsKey(sb) && data.containsKey(sb)) {
							List<ImportLabelModel> item = new ArrayList<>();
							ImportLabelModel labelRemoved = data.remove(sb);
							item.add(labelRemoved);
							item.add(importLabelModel);
							dups.put(sb, item);
							String str1 = ""+labelRemoved.getImportLabelRequest().getRecord_id()+" "+labelRemoved.getImportLabelRequest().getPackage_description();
							
							importLabelReport.getDuplicatesLabels().add(str);
							importLabelReport.getDuplicatesLabels().add(str1);
							
							//System.out.println("Upc: "+importLabelModel.getImportLabelRequest().getPackage_upc());

						}
												
					}else {
						//Duplicate
						if(dups.containsKey(sb)) {
							dups.get(sb).add(importLabelModel);
							importLabelReport.getDuplicatesLabels().add(str);

							//System.out.println("Upc: "+importLabelModel.getImportLabelRequest().getPackage_upc());

						}else {
							
							List<ImportLabelModel> item = new ArrayList<>();
							item.add(importLabelModel);
							//item.add(data.remove(sb));
							dups.put(sb, item);
							importLabelReport.getDuplicatesLabels().add(str);

							//System.out.println("Upc: "+importLabelModel.getImportLabelRequest().getPackage_upc());


						}
						
					}
					



				} else {
					
					String str = ""+importLabelModel.getImportLabelRequest().getRecord_id()+" "+importLabelModel.getImportLabelRequest().getPackage_description();
					importLabelReport.getSkippedRecords().add(str);
					
					//System.out.println("Invalid records: "+str);

				}
			}

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		importLabelReport.numberOfRecords = count;
		buildMapByUPC(data,dataFromTempByUpc);
		System.out.println("Total number of records in the file " + count);
		return dups;
		
	}
	
	
	// Extract data from dataFromTempByUpc that has a label upc which exists in
	// existingLabelsByUpc
	// insert the extracted data in appropriate tables
	// TODO check if records with same label upc also have the same product grouping 

	private void buildMapByUPC(Map<String, ImportLabelModel> data,
			Map<String, List<ImportLabelModel>> dataFromTempByUpc) {
		// TODO Auto-generated method stub
		
		for (final Iterator<Entry<String, ImportLabelModel>> it = data.entrySet().iterator(); it
				.hasNext();) {
			
			Entry<String, ImportLabelModel> entry = it.next();
			final ImportLabelModel importLabelModel = entry.getValue();
			if (dataFromTempByUpc.containsKey(importLabelModel.getImportLabelRequest().getPackage_upc())) {
				
				// append
				dataFromTempByUpc.get(importLabelModel.getImportLabelRequest().getPackage_upc())
						.add(importLabelModel);

			} else {
				// new element in List
				List<ImportLabelModel> item = new ArrayList<>();
				item.add(importLabelModel);
				dataFromTempByUpc.put(importLabelModel.getImportLabelRequest().getPackage_upc(), item);
				// System.out.println("Neew key");

			}
			
			
			
		}
		
		
		
	}

	public void labelUpcMatch(Map<String, List<ImportLabelModel>> dataFromTempByUpc,
			Map<String, List<ExistingLabels>> existingLabelsByUpc) {

		Map<Integer, List<ImportLabelModel>> insertLabelUpcMatch = new HashMap<Integer, List<ImportLabelModel>>();

		for (final Iterator<Entry<String, List<ImportLabelModel>>> it = dataFromTempByUpc.entrySet().iterator(); it
				.hasNext();) {

			Entry<String, List<ImportLabelModel>> entry = it.next();
			List<ImportLabelModel> list = new ArrayList<ImportLabelModel>();// entry.getValue();

			if (existingLabelsByUpc.containsKey(entry.getKey())) {
				System.out.println("yes contains");

				list = entry.getValue();

//				System.out.println("id: "+existingLabelsByUpc.get(entry.getKey()).get(0).getproduct_id());
				
				insertLabelUpcMatch.put(existingLabelsByUpc.get(entry.getKey()).get(0).getproduct_id(), list);
				it.remove();
			}

		}
		buildQueryUpcMatchInDB(insertLabelUpcMatch);

	}
	private void buildQueryUpcMatchInDB(Map<Integer, List<ImportLabelModel>> insertLabelUpcMatch) {

		for (final Iterator<Entry<Integer, List<ImportLabelModel>>> it = insertLabelUpcMatch.entrySet().iterator(); it
				.hasNext();) {

			Entry<Integer, List<ImportLabelModel>> entry = it.next();
			final List<ImportLabelModel> v = entry.getValue();

			try {
				for (final ImportLabelModel element : v) {
					String str = ""+element.getImportLabelRequest().getRecord_id()+" "+element.getImportLabelRequest().getPackage_description();
					
				
					
					System.out.println("found iun db: "+element.getImportLabelRequest().getPackage_upc() +"product id: "+entry.getKey());

					int package_id = createLabel(entry.getKey(), element.getImportLabelRequest());
					
					if (element.getImportLabelRequest().getPackage_product_description() != null
							&& element.getImportLabelRequest().getPackage_product_description().length() > 0) {
						updateProductDescription(element.getImportLabelRequest().getPackage_product_description(),
								entry.getKey());
					}
					if (element.getImportLabelRequest().getPackage_brand() != null
							&& element.getImportLabelRequest().getPackage_brand().length() > 0) {
						updateBrand(element.getImportLabelRequest().getPackage_brand(), entry.getKey());
					}

					if (element.getImportLabelRequest().getPackage_manufacturer() != null
							&& element.getImportLabelRequest().getPackage_manufacturer().length() > 0) {
						updateManufacturer(element.getImportLabelRequest().getPackage_manufacturer(), entry.getKey());
					}
					if (element.getImportLabelRequest().getClassification_number() != null) {
						
						updateClassification(element.getImportLabelRequest().getClassification_number(),
								entry.getKey());
					}
					try {
						insertNftImport(element, package_id);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//Add to report
					importLabelReport.getLabelsLinkedByLabelUpc().add(str);
				}

			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	// get data from dataFromTempByUpc which values' size is greater than one
	// for keys with only one value check if they contain a product grouping
	// value: if yes put in labelSortedByGrouping
	// else if check if contain nielsen item rank value: if yes check if nielsen
	// item rank exists in mapUpcImRank
	// if yes create label with product id found in upcItem
	// else add record to map of unique records

	public Map<Double, List<ImportLabelModel>> labelByUpcWithinFile(List<ImportLabelModel> uniqueLabels,
			Map<String, List<ImportLabelModel>> dataFromTempByUpc, Map<String, Integer> mapUpcImRank)
			throws DaoException {

		Map<Double, List<ImportLabelModel>> labelSortedByGrouping = new HashMap<Double, List<ImportLabelModel>>();

		for (final Iterator<Entry<String, List<ImportLabelModel>>> it = dataFromTempByUpc.entrySet().iterator(); it
				.hasNext();) {

			Entry<String, List<ImportLabelModel>> entry = it.next();

			if (entry.getValue().size() > 1) {
				
				//boolean flag = validateRecordsWithSameUpcHasSameProductGrouping
				
				insertLabelGroupedyUpcWithinWhile(entry.getValue());
				
				//TODO the client should confirm whether or not they want to insert those records or 
				//kicking them out

			} else {

				final List<ImportLabelModel> v = entry.getValue();

				for (final ImportLabelModel element : v) {

					if (element.getImportLabelRequest().getProduct_grouping() != null) {

						if (labelSortedByGrouping.containsKey(element.getImportLabelRequest().getProduct_grouping())) {

							labelSortedByGrouping.get(element.getImportLabelRequest().getProduct_grouping())
									.add(element);

						} else {

							List<ImportLabelModel> item = new ArrayList<>();
							item.add(element);
							labelSortedByGrouping.put(element.getImportLabelRequest().getProduct_grouping(), item);

						}

					} else if (element.getImportLabelRequest().getNielsen_item_rank() != null
							&& mapUpcImRank.containsKey(element.getImportLabelRequest().getNielsen_item_rank())) {

						createLabelLinkedByNielsenItemRank(element,mapUpcImRank);
						//update products' fields

					} else {

						uniqueLabels.add(element);
					}

				}

			}
			it.remove();
		}

		return labelSortedByGrouping;

	}


public void createLabelLinkedByNielsenItemRank(ImportLabelModel element,Map<String, Integer> upcItem) throws DaoException{

	String str = ""+element.getImportLabelRequest().getRecord_id()+" "+element.getImportLabelRequest().getPackage_description();
	importLabelReport.getLabelsLinkedByNielsenItemRank().add(str);
	try {

		int package_id = createLabel(
				upcItem.get(element.getImportLabelRequest().getNielsen_item_rank()),
				element.getImportLabelRequest());

		if (element.getImportLabelRequest().getPackage_product_description() != null
				&& element.getImportLabelRequest().getPackage_product_description().length() > 0) {
			updateProductDescription(
					element.getImportLabelRequest().getPackage_product_description(),
					upcItem.get(element.getImportLabelRequest().getNielsen_item_rank()));
		}
		if (element.getImportLabelRequest().getPackage_brand() != null
				&& element.getImportLabelRequest().getPackage_brand().length() > 0) {
			updateBrand(element.getImportLabelRequest().getPackage_brand(),
					upcItem.get(element.getImportLabelRequest().getNielsen_item_rank()));
		}

		if (element.getImportLabelRequest().getPackage_manufacturer() != null
				&& element.getImportLabelRequest().getPackage_manufacturer().length() > 0) {
			updateManufacturer(element.getImportLabelRequest().getPackage_manufacturer(),
					upcItem.get(element.getImportLabelRequest().getNielsen_item_rank()));
		}
		if (element.getImportLabelRequest().getClassification_number() != null) {
			updateClassification(element.getImportLabelRequest().getClassification_number(),
					upcItem.get(element.getImportLabelRequest().getNielsen_item_rank()));
		}
		insertNftImport(element, package_id);

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
	// Insert label sharing the same label upc within the given file
	private void insertLabelGroupedyUpcWithinWhile(List<ImportLabelModel> value) {
		// TODO Auto-generated method stub
		try {
			int product_id = insertProducts(value.get(0).getImportLabelRequest());
			// TODO add classification number, check consistency between product
			// description, manufacturer, class, etc...
			if (value.get(0).getImportLabelRequest().getClassification_number() != null
					&& !value.get(0).getImportLabelRequest().getClassification_number().isEmpty()) {
				if (checkClassification(value.get(0).getImportLabelRequest().getClassification_number())) {
					// System.out.println("oui called
					// 2"+element.getImportLabelRequest().getClassification_number());
					insertClassificationNumber(value.get(0).getImportLabelRequest().getClassification_number(),
							product_id);
				}
			}
			for (ImportLabelModel importLabelModel : value) {
				String str = ""+importLabelModel.getImportLabelRequest().getRecord_id()+" "+importLabelModel.getImportLabelRequest().getPackage_description();
				importLabelReport.getLabelsLinkedByLabelUpc().add(str);

				int package_id = createLabel(product_id, importLabelModel.getImportLabelRequest());
				insertNftImport(importLabelModel, package_id);
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buildByGrouping(Map<Double, List<ImportLabelModel>> labelSortedByGrouping,
			List<ImportLabelModel> uniqueLabels, Map<String, Integer> mapUpcImRank) {

		for (final Iterator<Entry<Double, List<ImportLabelModel>>> it = labelSortedByGrouping.entrySet().iterator(); it
				.hasNext();) {

			Entry<Double, List<ImportLabelModel>> entry = it.next();

			if (entry.getValue().size() > 1) {

				if (validateRecordsWithSameGrouping(entry.getValue())) {
					try {
						int product_id = insertProducts(entry.getValue().get(0).getImportLabelRequest());

						if (entry.getValue().get(0).getImportLabelRequest().getClassification_number() != null && !entry
								.getValue().get(0).getImportLabelRequest().getClassification_number().isEmpty()) {
							if (checkClassification(
									entry.getValue().get(0).getImportLabelRequest().getClassification_number())) {
								System.out.println("oui called 2"
										+ entry.getValue().get(0).getImportLabelRequest().getClassification_number());
								insertClassificationNumber(
										entry.getValue().get(0).getImportLabelRequest().getClassification_number(),
										product_id);
							}
						}
						// TODO add classification number, check consistency
						// between product description, manufacturer, class,
						// etc...

						for (ImportLabelModel importLabelModel : entry.getValue()) {
							String str = ""+importLabelModel.getImportLabelRequest().getRecord_id()+" "+importLabelModel.getImportLabelRequest().getPackage_description();
							importLabelReport.getLabelSharingSameGrouping().add(str);
							int package_id = createLabel(product_id, importLabelModel.getImportLabelRequest());
							insertNftImport(importLabelModel, package_id);

						}

					} catch (DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					for (ImportLabelModel importLabelModel : entry.getValue()) {
						String str = ""+importLabelModel.getImportLabelRequest().getRecord_id()+" "+importLabelModel.getImportLabelRequest().getPackage_description();
						importLabelReport.getProductGroupingAndLabelUpcNoMatch().add(str);
						System.out.println(str+" LABEL GROUPING NO MATCH");


					}

				}
			} else if(entry.getValue().get(0).getImportLabelRequest().getNielsen_item_rank() != null	
					&& mapUpcImRank.containsKey(entry.getValue().get(0).getImportLabelRequest().getNielsen_item_rank())){
				
				try {
					createLabelLinkedByNielsenItemRank(entry.getValue().get(0), mapUpcImRank);
					
				} catch (DaoException e) {
					
					e.printStackTrace();
				}
			}else{

				uniqueLabels.add(entry.getValue().get(0));
			}

		}
	}

	private boolean validateRecordsWithSameGrouping(List<ImportLabelModel> values) {

		boolean flag = true;
		
		LabelGrouping firstItem = new LabelGrouping(
				values.get(0).getImportLabelRequest().getPackage_brand(),
				values.get(0).getImportLabelRequest().getPackage_manufacturer(), 
				values.get(0).getImportLabelRequest().getClassification_number(),
				values.get(0).getImportLabelRequest().getType_of_restaurant()			
				);
		

		for (ImportLabelModel value : values) {
			
			LabelGrouping otherItems = new LabelGrouping(
					value.getImportLabelRequest().getPackage_brand(),
					value.getImportLabelRequest().getPackage_manufacturer(), 
					value.getImportLabelRequest().getClassification_number(),
					value.getImportLabelRequest().getType_of_restaurant()			
					);

		if(!firstItem.equals(otherItems)) {
			return false;
		}

		}


		return flag;
	}



	public Map<String, Integer> getUpcRank() throws SQLException {
		ResultSet resultSet = null;
		Map<String, Integer> upcItem = new HashMap<String, Integer>();
		String sql = "select distinct sales_product_id_fkey, sales_upc from " + schema + ".sales";

		try {
			resultSet = executeQuery(sql, null);
			while (resultSet.next()) {
				Integer product_id = resultSet.getInt("sales_product_id_fkey");
				String sales_upc = resultSet.getString("sales_upc");
				//System.out.println("upc: "+sales_upc);
				if (!upcItem.containsKey(product_id)) {
					upcItem.put(sales_upc, product_id);
				}
			}

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return upcItem;

	}

	public void buildQueryUniqueLabel(List<ImportLabelModel> uniqueLabels) {

		Map<Integer, List<ImportLabelNft>> nft = new HashMap<Integer, List<ImportLabelNft>>();

		for (ImportLabelModel element : uniqueLabels) {
			String str = ""+element.getImportLabelRequest().getRecord_id()+" "+element.getImportLabelRequest().getPackage_description();
			importLabelReport.getLabelsLinkedToNewProducts().add(str);

			try {
				int id = insertProducts(element.getImportLabelRequest());

				if (element.getImportLabelRequest().getClassification_number() != null
						&& !element.getImportLabelRequest().getClassification_number().isEmpty()) {
					if (checkClassification(element.getImportLabelRequest().getClassification_number())) {
						// System.out.println("oui called
						// 2"+element.getImportLabelRequest().getClassification_number());
						insertClassificationNumber(element.getImportLabelRequest().getClassification_number(), id);
					}
				}
				int label_id = createLabel(id, element.getImportLabelRequest());
				nft.put(label_id, element.getNftList());
				insertNftImport(element, label_id);

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

		// String query = "insert into "+schema+".package
		// (package_product_id_fkey,package_description,package_upc,"
		// + " package_brand,
		// package_manufacturer,package_country,package_size,package_size_unit_of_measure,
		// "
		// +
		// "storage_type,storage_statements,health_claims,other_package_statements,suggested_directions,
		// "
		// + "ingredients , multi_part_flag, nutrition_fact_table,
		// as_prepared_per_serving_amount, as_prepared_unit_of_measure, "
		// +
		// "as_sold_per_serving_amount,as_sold_unit_of_measure,as_prepared_per_serving_amount_in_grams,
		// "
		// + "as_sold_per_serving_amount_in_grams, package_comment,
		// package_source, package_product_description, "
		// + "package_collection_date,
		// number_of_units,edited_by,informed_dining_program,nft_last_update_date,
		// "
		// + "product_grouping,
		// child_item,classification_number,classification_name,
		// nielsen_item_rank, "
		// + " nutrient_claims,package_nielsen_category,
		// common_household_measure,creation_date, "
		// + " last_edit_date,calculated ) values(?, ?, ?, ?, ?, ?, ?, ?, ?,
		// ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
		// + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? )";

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
		// int package_id = 0;
		String query = SQL_INSERT.replaceFirst(TABLE_REGEX, schema + "." + "package");
		query = query.replaceFirst(KEYS_REGEX, StringUtils.join(columns, ","));
		query = query.replaceFirst(VALUES_REGEX, questionmarks);

		final List<Object> packageInsertList = DaoUtil.getQueryMap(importLabelRequest, product_id);

		int package_id = (int) executeUpdate(query, packageInsertList.toArray());

		return package_id;
	}

	public int insertProducts(ImportLabelRequest item) throws DaoException, SQLException {

		final Timestamp now = DateUtil.getCurrentTimeStamp();

		String sql = "insert into " + schema + ".product (product_manufacturer, product_description, "
				+ "cluster_number, product_brand, creation_date, last_edit_date, type, restaurant_type) values (?, ?, ?, ?, ?, ?, ?, ?) ";

		String description = item.getPackage_product_description() != null ? item.getPackage_product_description()
				: item.getPackage_description();
		int obj = (int) executeUpdate(sql, new Object[] { item.getPackage_manufacturer(), description, null,
				item.getPackage_brand(), now, now, item.getType(), item.getType_of_restaurant() });

		return obj;

	}
	
	private boolean insertNftImport(ImportLabelModel importLabelModel, Integer package_id)
			throws DaoException, SQLException {

		Double PerServingAmount = null;
		String PerServingUnit = null;
		Double PerServingInGrams = null;

		final String query =

				"insert into " + schema + "." + "product_component(component_id, package_id, amount,"
						+ " amount_unit_of_measure, percentage_daily_value, as_ppd_flag, amount_per100g) "
						+ "select component_id, ?, ?, ?, ?, ?, ? from " + schema + ".component "
						+ "where component_id = (" + "select component_id from " + schema + "."
						+ "component where component_name= ?)";

		try {
			connection.setAutoCommit(false);
			// resultSet = executeQuery(sql, new Object[]
			// { nftRequest.getPackage_id() });
			//
			// resultSet.next();
			// PerServingAmount =
			// resultSet.getDouble(per_serving_amount_place_holder);
			// PerServingUnit =
			// resultSet.getString(per_serving_amount_unit_of_measure_place_holder);
			// PerServingInGrams =
			// resultSet.getDouble(per_serving_amount_in_grams_place_holder);

			for (final ImportLabelNft element : importLabelModel.getNftList()) {

				if (element.getFlag()) {
					PerServingAmount = importLabelModel.getImportLabelRequest().getAs_sold_per_serving_amount();
					PerServingUnit = importLabelModel.getImportLabelRequest().getAs_sold_unit_of_measure();
					PerServingInGrams = importLabelModel.getImportLabelRequest()
							.getAs_sold_per_serving_amount_in_grams();

				} else {
					PerServingAmount = importLabelModel.getImportLabelRequest().getAs_prepared_per_serving_amount();
					PerServingUnit = importLabelModel.getImportLabelRequest().getAs_prepared_unit_of_measure();
					PerServingInGrams = importLabelModel.getImportLabelRequest()
							.getAs_prepared_per_serving_amount_in_grams();

				}

				Double value = null;
				final PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setObject(1, package_id);
				preparedStatement.setObject(2, element.getAmount());
				preparedStatement.setObject(3, element.getUnit_of_measure());
				preparedStatement.setObject(4, element.getDaily_value());
				preparedStatement.setObject(5, element.getFlag());

				if (element.getAmount() != null) {
					if (PerServingInGrams != null && PerServingInGrams != 0.0 && PerServingInGrams != 0) {

						value = (element.getAmount() / PerServingInGrams) * 100;

					} else if ((PerServingAmount != null && PerServingAmount != 0 && PerServingAmount != 0.0)
							&& (PerServingUnit.equals("g"))) {
						value = (element.getAmount() / PerServingAmount) * 100;

					}
				}

				preparedStatement.setObject(6, value);
				preparedStatement.setObject(7, element.getcomponent_name());
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
	
}
