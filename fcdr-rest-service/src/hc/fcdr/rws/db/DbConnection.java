package hc.fcdr.rws.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbConnection
{

	
    Properties prop = new Properties();
	InputStream input = null;

    private DataSource  source;

    public DbConnection()
    {
    	try {
			this.input = new FileInputStream("/etc/sodium-monitoring/config.properties");
			prop.load(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    public void initialize()
    {
        try
        {
            final InitialContext ic = new InitialContext();
            final String dsName = "java:comp/env/jdbc/fcdrrest";
            source = (javax.sql.DataSource) ic.lookup(dsName);
        }
        catch (final NamingException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() throws SQLException
    {
        try
        
        {
            return source.getConnection();


        }
        catch (final Exception e)
        {
            throw new SQLException(e);
        }
    }

    public  Connection getConnections()
           
    {
        Connection connection = null;
  

		try {
		
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if (connection != null)
            return connection;
        else
            return null;

    }
    public String getSchema() {
    	
    	return prop.getProperty("schema");
    }
}
