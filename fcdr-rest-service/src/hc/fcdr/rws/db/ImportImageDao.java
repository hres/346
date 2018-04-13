package hc.fcdr.rws.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.util.DateUtil;

@Singleton
public class ImportImageDao extends PgDao{

	
	private final String schema;
	private int imageCounter = 0;
	
	public ImportImageDao(Connection connection, final String schema) {
		
		super(connection);
		this.schema = schema;
		
	}
	
	public void importImage(List<FormDataBodyPart> bodyParts, BufferedWriter output) {
		
		//String filesLocation = "/tmp/images";
		//String uploadedFileLocation = "/tmp/" + fileDetail.getFileName();
		Map<String, List<Integer>> labels = getLabelUpc();
		List<String> invalidImages = new ArrayList<String>();
        final Timestamp now = DateUtil.getCurrentTimeStamp();

		
    	for(int i = 0; i < bodyParts.size(); i++) {
    		
    		BodyPartEntity bodyPartEntity = (BodyPartEntity) bodyParts.get(i).getEntity();
    		String fileName = bodyParts.get(i).getContentDisposition().getFileName();
    		
    		if(fileName.contains(".")) {
    			
    		String upc = fileName.substring(0, fileName.lastIndexOf('.'));
    		
    		if(labels.containsKey(upc)) {
    			
    			String extension = fileName.substring(fileName.indexOf(".")+1);
    			for (Integer id: labels.get(upc)) {
    				
    			
    				try {
    					
        				String secondaryFileName = ""+(++imageCounter)+"-"+fileName;
        				
        				//TODO update folder path
        				String uploadedFileLocation = "/home/romario/Documents/imagesLabel/"+secondaryFileName;

        				writeToFile(bodyPartEntity.getInputStream(), uploadedFileLocation);
						insertImage(secondaryFileName,fileName, id, extension);
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				
					}
    			
    			
    			}
    			
    			
    		}else {
    			invalidImages.add(fileName);
    		}
    		}else {
    			invalidImages.add(fileName);
    		}
    		
    	}
    	   
    	try {
			output.write("Number of skipped images: "+invalidImages.size());
			output.newLine();
			output.newLine();
			
			for(String elem: invalidImages) {
				output.write(elem);
				output.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
		System.out.println("Number of invalid records: "+invalidImages.size());
	}
	
	
	private void insertImage(String image_location, String fileName, Integer package_id, String extension) throws SQLException {
		// TODO Auto-generated method stub
		final String query = "insert into "+schema+".image (image_name, image_path, package_id_fkey, extension) "
				+ "values(?, ?, ?, ?)";
		
		
	
			final PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, fileName);
			preparedStatement.setString(2, image_location);
			preparedStatement.setInt(3, package_id);
			preparedStatement.setString(4, extension);
			preparedStatement.executeUpdate();
			
			
		
		
		
		
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
		
		private void writeToFile(InputStream uploadedInputStream,
				String uploadedFileLocation) {

				try {
					OutputStream out = new FileOutputStream(new File(
							uploadedFileLocation));
					int read = 0;
					byte[] bytes = new byte[1024];

					out = new FileOutputStream(new File(uploadedFileLocation));
					while ((read = uploadedInputStream.read(bytes)) != -1) {
						out.write(bytes, 0, read);
					}
					out.flush();
					out.close();
				} catch (IOException e) {

					e.printStackTrace();
				}

			}
	
	
}
