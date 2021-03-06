package hc.fcdr.rws.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import hc.fcdr.rws.db.DbConnection;
import hc.fcdr.rws.db.ImportImageDao;
import hc.fcdr.rws.db.ImportLabelDao;
import hc.fcdr.rws.db.ImportMarketShareDao;
import java.util.Properties;

@Path("/ImportService")
public class ImportService extends Application
{


    static ImportMarketShareDao importSalesDao = null;
    static ImportLabelDao importLabelDao = null;
    static ImportImageDao importImageDao = null;
    static Properties prop = new Properties();
	static InputStream input = null;


    @PostConstruct
    public static void initialize()
    {
     
    	
    	
            final DbConnection pgConnectionPool = new DbConnection();
//            pgConnectionPool.initialize();

            try
            {
                importSalesDao =
                        new ImportMarketShareDao(pgConnectionPool.getConnections(),
                        		pgConnectionPool.getSchema());
                
                importLabelDao =
                        new ImportLabelDao(pgConnectionPool.getConnections(),
                        		pgConnectionPool.getSchema());
                
                importImageDao =
                        new ImportImageDao(pgConnectionPool.getConnections(),
                        		pgConnectionPool.getSchema());
            }
            catch (final Exception e)
            {
                e.printStackTrace();
            }
        	try {
    			input = new FileInputStream("/etc/sodium-monitoring/config.properties");
    			prop.load(input);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        
    }


    
    @POST
    @Path("/importLabel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({"text/plain"})
    public Response getImportLabel(@FormDataParam("csv_file") InputStream fileInputStream,
    		@FormDataParam("csv_file") FormDataContentDisposition fileDetail)
            throws SQLException, IOException, Exception
    {
    	 File file = null;
    	

    	 file = importLabelDao.importLabel(fileInputStream, fileDetail);


 	ResponseBuilder response = Response.ok((Object) file);

	response.header("Content-Disposition",
			"attachment; filename=importReport.txt");
	return response.build();
    }
    
    
    @POST
    @Path("/importMarketShare")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("text/plain")
    public Response getFile(@FormDataParam("csv_file") InputStream fileInputStream,
    		@FormDataParam("csv_file") FormDataContentDisposition fileDetail) throws IOException{
    	
    	//TODO will update the folder path
		String uploadedFileLocation = prop.getProperty("csvfiles") + fileDetail.getFileName();

		String reportFile = prop.getProperty("reports") + "report.txt";
		
		writeToFile(fileInputStream, uploadedFileLocation);


         BufferedWriter output = null;
         File file = new File(reportFile);
         file.createNewFile();


            
             try {
				output = new BufferedWriter(new FileWriter(file));
				try {
					importSalesDao.testImport(uploadedFileLocation, output);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
	             if ( output != null ) {
	                 try {
						output.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               }
	             }
			deleteFolder(new File(uploadedFileLocation));
		

     	ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=importSalesReport.txt");
		return response.build();
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
	
	public static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { 
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
	
    @POST
    @Path("/importImage")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("text/plain")
    public Response getImportImages(FormDataMultiPart fileInputStream,
    		@FormDataParam("image") FormDataContentDisposition fileDetail) throws IOException
           
    {
    	
    	System.out.println("Here-------------------------------------------");
    	
    	List<FormDataBodyPart> bodyParts = fileInputStream.getFields("image");
         File file = null;
        	 file = 	importImageDao.importImage(bodyParts);

	
			
 	ResponseBuilder response = Response.ok((Object) file);
	response.header("Content-Disposition",
			"attachment; filename=importReport.txt");
	return response.build();
    }
    

    
    
}
