package hc.fcdr.rws.db;

import java.sql.Connection;
import java.sql.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.StopWatch;




import hc.fcdr.rws.except.DaoException;

import hc.fcdr.rws.model.sales.ImportMarketShare;
import hc.fcdr.rws.model.sales.ImportSalesMadatoryFields;
public class ImportSalesDao extends PgDao {

	

    private final String        schema;


    public ImportSalesDao(final Connection connection, final String schema)
    {
        super(connection);
        this.schema = schema;
    }
    
    
    
    public void testImport(String csvFile){
    	
    	System.out.println("yes here "+csvFile);
    	
    	Map<Double, String> queryMap = new HashMap<Double, String> ();
    	
    	List<ImportMarketShare> upcMatchInDB= new ArrayList<ImportMarketShare>();
    	
    	List<ImportSalesMadatoryFields> importSalesMadatoryFields = new ArrayList<ImportSalesMadatoryFields>();
//    	list =  new CsvToBeanBuilder(new FileReader(csvFile))
//                .withType(ImportMarketShare.class).withSkipLines(1)
//                .build().parse();
    	
    	String sql =  "COPY fcdrschema.sales_temp(record_id, sales_upc,sales_description, sales_brand, sales_manufacturer,dollar_rank,dollar_volume,dollar_share, "
    			+ "   dollar_volume_percentage_change,kilo_volume, kilo_share, kilo_volume_percentage_change, average_ac_dist, average_retail_price, "
    			+ "  sales_source, nielsen_category, sales_collection_date, sales_year, control_label_flag, kilo_volume_total, kilo_rank, dollar_volume_total, "
    			+ " cluster_number,product_grouping, sales_product_description, classification_number, classification_type, sales_comment) "
    			+ "FROM '" + csvFile+"'  CSV HEADER DELIMITER ','";
    	
    	// PreparedStatement preparedStatement = connection.prepareStatement(sql);
    	 
    	//System.out.printf("returned: ", preparedStatement.executeUpdate());
    	
    	try{
    		connection.setAutoCommit(false);
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            System.out.println("Loading of sales data started...");
         Statement stmt = connection.createStatement();
    	
         stmt.execute(sql);

        	 try {
				queryMap = getMissingMandatoryFields();
				
				
//				for (Map.Entry<Double, String> entry : queryMap.entrySet())
//				{
//				    System.out.println(entry.getKey() + "/" + entry.getValue());
//				}
				deleteMissingMadatoryFieldsRecords();

				importSalesMadatoryFields = getDuplicatesInFile();
				deleteDuplicatesFoundInDB();
				importSalesMadatoryFields = addOtherMissingFields(importSalesMadatoryFields, queryMap);
				deleteOtherDuplicates(importSalesMadatoryFields);
				upcMatchInDB = getRecordsWithMatchingUPC();
				insertSales(upcMatchInDB);
	
				
				  stopWatch.split();
			        System.out.println("Total time spent on loading the sales data: "
			                + (stopWatch.getSplitTime() / 1000) + " seconds.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 
        	 
        
         connection.commit();
         
    	}catch (SQLException  e){
    		   System.out.print("Error: ");
               System.out.println(e.toString());
               try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
       
    	}
//    	for (final ImportMarketShare importMarketShare : list){
//    		System.out.println("humm: "+importMarketShare.getSales_description());
//    	}
    	
    }
    
    
    public  Map<Double, String> getMissingMandatoryFields() throws SQLException{
    	
    	ResultSet resultSet = null;
    	  final Map<Double, String> queryMap = new HashMap<Double, String> ();
    	String sql = "select record_id, sales_description  from "+ schema + ".sales_temp where sales_description IS null or sales_upc IS null or kilo_volume "
    			+ "IS null or sales_source IS NULL or sales_year IS null or sales_collection_date is null"; 

    	try {
			resultSet = executeQuery(sql, null);
	    	while(resultSet.next()){
	    		
	    		//System.out.println(resultSet.getDouble("record_id") +" is the record id" );
	    		queryMap.put(resultSet.getDouble("record_id"), resultSet.getString("sales_description"));
	    	}
			
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated catch block
    	

    	
    	
		return queryMap;

    }
    
    public void deleteMissingMadatoryFieldsRecords(){
    	
    	String sql = "delete  from "+ schema + ".sales_temp where sales_description IS null or sales_upc IS null or kilo_volume "
    			+ "IS null or sales_source IS NULL or sales_year IS null or sales_collection_date is null"; 

    	try {
			executeUpdate(sql, null);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public  Map<Double, String> getDuplicatesInDB() throws SQLException{
    	ResultSet resultSet = null;
    	final Map<Double, String> queryMap = new HashMap<Double, String> ();
    	String sql = "select st.record_id, st.sales_description from "+ schema + "..sales_temp st where exists (select * from  "
    			+schema+".sales s where s.sales_upc = st.sales_upc and s.sales_collection_date = st.sales_collection_date "
    					+ " and s.kilo_volume = st.kilo_volume)";
    	
    	try {
			resultSet = executeQuery(sql, null);
	    	while(resultSet.next()){
	    		
	    		System.out.println(resultSet.getDouble("record_id") +" is the record id"+resultSet.getDate("sales_collection_date") );
	    		queryMap.put(resultSet.getDouble("record_id"), resultSet.getString("sales_description"));
	    	}
			
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated catch block
    	

    	
    	
		return queryMap;
    }
    
    public void deleteDuplicatesFoundInDB(){
    	
    	String sql = "delete from "+ schema + ".sales_temp st where exists (select * from  "
    			+schema+".sales s where s.sales_upc = st.sales_upc and s.sales_description = st.sales_description and s.sales_collection_date = st.sales_collection_date "
    					+ " and s.kilo_volume = st.kilo_volume)";

    	try {
			executeUpdate(sql, null);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public  List<ImportSalesMadatoryFields> getDuplicatesInFile() throws SQLException{
    	
    	ResultSet resultSet = null;
    	
    	  List<ImportSalesMadatoryFields> list = new ArrayList<ImportSalesMadatoryFields>();

    	String sql = "select *  from (SELECT record_id, sales_upc, sales_collection_date,kilo_volume, sales_description,  ROW_NUMBER() "
    			+ "  OVER(PARTITION BY sales_upc, sales_collection_date, kilo_volume ORDER BY record_id asc) AS Row "
    			+ " from "+schema+".sales_temp) dups where dups.Row > 1";
    	
    	
    	
    	try {
			resultSet = executeQuery(sql, null);
	    	while(resultSet.next()){
	    		
	    		list.add(new ImportSalesMadatoryFields(resultSet.getString("sales_upc"), resultSet.getString("sales_description"), resultSet.getDate("sales_collection_date"),resultSet.getDouble("kilo_volume"),resultSet.getDouble("record_id")));
	    		
	    		
	    		System.out.println(resultSet.getDouble("record_id") +resultSet.getString("sales_collection_date") );
	    	}
			
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated catch block
    	

    	
    	
		return list;

    }
    public List<ImportSalesMadatoryFields> addOtherMissingFields(List<ImportSalesMadatoryFields> list, Map<Double, String> map) throws SQLException{
    	ResultSet resultSet = null;
    	
    	List<ImportSalesMadatoryFields> allDups = new ArrayList<ImportSalesMadatoryFields>();
    	
    	String sql = "select record_id, sales_description, sales_upc, kilo_volume, sales_collection_date from "+schema+".sales_temp where sales_upc = ? and sales_collection_date = ? and sales_description = ? and kilo_volume = ? ";
    	
    	
      	try {
      		for (final ImportSalesMadatoryFields element : list){
      			System.out.println("date: "+element.getSales_collection_date());
    			resultSet = executeQuery(sql, new Object[]{element.getSales_upc(), element.getSales_collection_date(), element.getSales_description(), element.getKilo_volume()});
    	    	while(resultSet.next()){
    	    		map.put(resultSet.getDouble("record_id"), resultSet.getString("sales_description"));
    	    		allDups.add(new ImportSalesMadatoryFields (resultSet.getString("sales_upc"),resultSet.getString("sales_description"),resultSet.getDate("sales_collection_date"), (Double)resultSet.getDouble("kilo_volume"), (Double)resultSet.getDouble("record_id")));

    	    	}
    			
      		}
    			
    		} catch (DaoException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
      	
      	return allDups;
    	
    }

    
    public void deleteOtherDuplicates(List<ImportSalesMadatoryFields> list) throws SQLException{

    	String sql = "delete from "+schema+".sales_temp where sales_upc = ? and sales_collection_date = ? and kilo_volume = ? and sales_description = ?";
    	
    	
      	try {
      		for (final ImportSalesMadatoryFields element : list){
      			System.out.println(element.getSales_collection_date()+ "a scene");
      			
    			 executeUpdate(sql, new Object[]{element.getSales_upc(),(Date)element.getSales_collection_date(), element.getKilo_volume(), element.getSales_description()});
    	    
      		}
    			
    		} catch (DaoException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
    	
    }
    
    public List<ImportMarketShare>  getRecordsWithMatchingUPC() throws SQLException{
    	
    	List<ImportMarketShare> list = new ArrayList<ImportMarketShare>();
    	ResultSet resultSet = null;
    	String sql = "select * from "+schema+".sales_temp st where exists (select * from " +schema+".sales s where s.sales_upc = st.sales_upc)";
    	
    	try{
    		
    		resultSet = executeQuery(sql, null);
    		while(resultSet.next()){
    			
    			
    			list.add(new ImportMarketShare(
    					
//    					resultSet.getDouble("record_id"),
//    					resultSet.getString("sales_upc"),
//    					resultSet.getString("sales_description"),
//    					resultSet.getString("sales_brand"),
//    					resultSet.getString("sales_manufacturer"),
//    					resultSet.getDouble("dollar_rank"),
//    					resultSet.getDouble("dollar_volume"),
//    					resultSet.getDouble("dollar_share"),
//    					resultSet.getDouble("dollar_volume_percentage_change"),
//    					resultSet.getDouble("kilo_volume"),
//    					resultSet.getDouble("kilo_share"),
//    					resultSet.getDouble("kilo_volume_percentage_change"),
//    					resultSet.getDouble("average_ac_dist"),
//    					resultSet.getDouble("average_retail_price"),
//    					resultSet.getString("sales_source"),
//    					resultSet.getString("nielsen_category"),
//    					resultSet.getString("sales_collection_date"),
//    					resultSet.getInt("sales_year"),
//    					resultSet.getBoolean("control_label_flag"),
//    					resultSet.getDouble("kilo_volume_total"),
//    					resultSet.getDouble("kilo_rank"),
//    					resultSet.getDouble("dollar_volume_total"),
//    					resultSet.getInt("cluster_number"),
//    					resultSet.getDouble("product_grouping"),
//    					resultSet.getString("sales_product_description"),
//    					resultSet.getDouble("classification_number"),
//    					resultSet.getString("classification_type"),
//    					resultSet.getString("sales_comment")
    					
    					
    					
    					)); 
    			
    			
    		}
    		
        	String query = "delete from "+schema+".sales_temp st where exists (select * from  "+schema+".sales s where s.sales_upc = st.sales_upc)";
        	executeUpdate(query, null);
    		
    	}catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
  	
    	
    	
    	return list;
    }
    
    public  void insertSales(List<ImportMarketShare> list) throws SQLException{
    	
    	
    	String sql = "insert into "+schema+".sales (sales_product_id_fkey, "
    			+ "sales_upc,sales_description, sales_brand, sales_manufacturer,"
    			+ "dollar_rank,dollar_volume, "
    			+ "dollar_share, dollar_volume_percentage_change,kilo_volume, "
    			+ "kilo_share, kilo_volume_percentage_change, average_ac_dist, "
    			+ "average_retail_price, sales_source, nielsen_category, "
    			+ "sales_collection_date, sales_year, control_label_flag, "
    			+ "kilo_volume_total, kilo_rank, "
    			+ "dollar_volume_total, cluster_number, sales_product_description, "
    			+ "classification_number, classification_type, sales_comment) "
    			+ " select distinct sales_product_id_fkey, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::date, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? "
    			+ " from "+schema+".sales where sales_upc = ?";
    	
    	
    	try{
    	for (final ImportMarketShare importMarketShare : list){
    		
//    		d= d.substring(0, d.length());
    		executeUpdate(sql, new Object[]{
    				importMarketShare.getSales_upc(),
    				importMarketShare.getSales_description(),
    				importMarketShare.getSales_brand(),
    				importMarketShare.getSales_manufacturer(),
    				importMarketShare.getDollar_rank(),
    				importMarketShare.getDollar_volume(),
    				importMarketShare.getDollar_share(),
    				importMarketShare.getDollar_volume_percentage_change(),
    				importMarketShare.getKilo_volume(),
    				importMarketShare.getKilo_share(),
    				importMarketShare.getKilo_volume_percentage_change(),
    				importMarketShare.getAverage_ac_dist(),
    				importMarketShare.getAverage_retail_price(),
    				importMarketShare.getSales_source(),
    				importMarketShare.getNielsen_category(),
    				
    				(Date)importMarketShare.getSales_collection_date(),
    				importMarketShare.getsales_year(),
    				importMarketShare.getControl_label_flag(),
    				importMarketShare.getKilo_volume_total(),
    				importMarketShare.getKilo_rank(),
    				importMarketShare.getDollar_volume_total(),
    				importMarketShare.getCluster_number(),
    				importMarketShare.getProduct_description(),
    				importMarketShare.getClassification_number(),
    				importMarketShare.getClassification_type(),
    				importMarketShare.getSales_comment(),
    				importMarketShare.getSales_upc()
    				
    		});
    	}
    	
    	}catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
}

}