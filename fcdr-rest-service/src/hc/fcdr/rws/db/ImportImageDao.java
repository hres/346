package hc.fcdr.rws.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import hc.fcdr.rws.except.DaoException;

public class ImportImageDao extends PgDao{

	
	private final String schema;
	
	public ImportImageDao(Connection connection, final String schema) {
		
		super(connection);
		this.schema = schema;
		
	}
	
	public void importImage(List<FormDataBodyPart> bodyParts, BufferedWriter output) {
		
		Map<String, List<Integer>> labels = getLabelUpc();
		List<String> invalidImages = new ArrayList<String>();
		
    	for(int i = 0; i < bodyParts.size(); i++) {
    		
    		BodyPartEntity bodyPartEntity = (BodyPartEntity) bodyParts.get(i).getEntity();
    		String fileName = bodyParts.get(i).getContentDisposition().getFileName();
    		
    		if(fileName.contains(".")) {
    		String upc = fileName.substring(0, fileName.lastIndexOf('.'));
    		
    		if(labels.containsKey(upc)) {
    			
    			String extension = fileName.substring(fileName.indexOf(".")+1);
    			for (Integer id: labels.get(upc)) {
    				
    			insertImage(bodyPartEntity.getInputStream(),fileName, id, extension);
    			}
    			
    			
    		}else {
    			invalidImages.add(fileName);
    		}
    		}else {
    			invalidImages.add(fileName);
    		}
    		
    	}
    	   
		
		System.out.println("Number of invalid records: "+invalidImages.size());
	}
	
	
	private void insertImage(InputStream image, String fileName, Integer package_id, String extension) {
		// TODO Auto-generated method stub
		final String query = "insert into "+schema+".image (image_name, image, package_id_fkey, extension) "
				+ "values(?, ?, ?, ?)";
		
		
		try {
			final PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, fileName);
			preparedStatement.setBinaryStream(2, image);
			preparedStatement.setInt(3, package_id);
			preparedStatement.setString(4, extension);
			preparedStatement.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}

		public Map<String, List<Integer>> getLabelUpc() {
			
			Map<String, List<Integer>> labels = new HashMap<String,List<Integer>>();
			
			ResultSet resultSet = null;
			String sql = "select distinct package_upc, package_id  "
					+ " from " + schema
					+ ".package";
			
			
			try {
				resultSet = executeQuery(sql, null);
			} catch (DaoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				while (resultSet.next()) {

					Integer package_id = resultSet.getInt("package_id");
					String label_upc = resultSet.getString("package_upc");
					
					if(labels.containsKey(label_upc)) {
						labels.get(label_upc).add(package_id);
					}else {
						List<Integer> item= new ArrayList<>();
						item.add(package_id);
						labels.put(label_upc, item);
						
						
						
					}
				}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return labels;
		}
	
}
