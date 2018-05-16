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
    // Temp solution.
//    static final String URL      =
//            "jdbc:postgresql://172.17.0.2:5432/sodium";
//    static final String USER     = "postgres";
//    static final String PASSWORD = "secret";
    
    static final String URL      =
            "jdbc:postgresql://localhost:5432/postgres";
    static final String USER     = "postgres";
    static final String PASSWORD = "postgres";
    static final String SCHEMA_NAME = "fcdrschema";
	Properties prop = new Properties();
	InputStream input = null;

    private DataSource  source;

    public DbConnection()
    {

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

    // Uses context.xml
    public synchronized Connection getConnection() throws SQLException
    {
        try
        
        {
            return source.getConnection();

          //  return getConnections();
        	//return   DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (final Exception e)
        {
            throw new SQLException(e);
        }
    }

    // Uses non context.xml mechanism, if needed.
    public  Connection getConnections()
           
    {
        Connection connection = null;
  

		try {
			input = new FileInputStream("/home/romario/Documents/config.properties");
			prop.load(input);
			Class.forName("org.postgresql.Driver");
//			connection = DriverManager.getConnection(URL, USER, PASSWORD);
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
}
