package hc.fcdr.rws.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    public static Connection getConnections()
            throws SQLException, Exception, IOException
    {
        Class.forName("org.postgresql.Driver");
        final Connection connection =
                DriverManager.getConnection(URL, USER, PASSWORD);

        if (connection != null)
            return connection;
        else
            return null;

    }
}
