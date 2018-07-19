package hc.fcdr.rws.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Singleton;

import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import hc.fcdr.rws.except.DaoException;

@Singleton
public class ImportImageDao extends PgDao{

	
	private final String schema;
	private int imageCounter = 0;
    static Properties prop = new Properties();
	static InputStream input = null;

	
	private  String UPLOADED_FILE_LOCATION = null;
	
	public ImportImageDao(Connection connection, final String schema) {
		
		super(connection);
		this.schema = schema;
		
    	try {
			input = new FileInputStream("/etc/sodium-monitoring/config.properties");
			prop.load(input);
			this.UPLOADED_FILE_LOCATION = prop.getProperty("images");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public File importImage(List<FormDataBodyPart> bodyParts) {
        BufferedWriter output = null;
		String reportFile = prop.getProperty("reports") + "report.txt";
		int counter = 0;
		int totalNumberOfImages = bodyParts.size();
		      File file = new File(reportFile);
		      try {
				output = new BufferedWriter(new FileWriter(file));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		Map<String, List<Integer>> labels = getLabelUpc();
		List<String> invalidImages = new ArrayList<String>();

		
    	for(int i = 0; i < bodyParts.size(); i++) {
    		
    		BodyPartEntity bodyPartEntity = (BodyPartEntity) bodyParts.get(i).getEntity();
    		String fileName = bodyParts.get(i).getContentDisposition().getFileName();
    		
    		if(fileName.contains(".")) {
    			
    		String upc = fileName.substring(0, fileName.lastIndexOf('.'));
    		System.out.println("here "+upc);
    		if(labels.containsKey(upc)) {
    			
    			String extension = fileName.substring(fileName.lastIndexOf(".")+1);
	
    			for (Integer id: labels.get(upc)) {
    				
    			
    				
    					
        				String secondaryFileName = ""+(++imageCounter)+"-"+fileName;

        				//TODO update folder path
        				String uploadedFileLocation = UPLOADED_FILE_LOCATION+secondaryFileName;
        				
        				try {
							try {
								insertImage(secondaryFileName,fileName, id, extension);
								++counter;
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							writeToFile(bodyPartEntity.getInputStream(), uploadedFileLocation);

						} catch (IOException e) {
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
			output.write("Total number of images: "+totalNumberOfImages);
			output.newLine();
			output.newLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			output.write("Total number of imported images: "+imageCounter);
			output.newLine();
			output.newLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
        if ( output != null ) {
            try {
				output.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
          }
		
		return file;
	}
	
	
	public void insertImage(String image_location, String fileName, Integer package_id, String extension) throws SQLException {
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
		
		public void writeToFile(InputStream uploadedInputStream,
				String uploadedFileLocation) throws IOException {

			
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
			

			}
	
	
}
