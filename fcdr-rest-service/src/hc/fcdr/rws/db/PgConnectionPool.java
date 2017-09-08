package hc.fcdr.rws.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PgConnectionPool extends ConnectionPool
{
    private DataSource source;

    public PgConnectionPool()
    {

    }

    public void initialize()
    {
        try
        {
            InitialContext ic = new InitialContext();
            String dsName = "java:comp/env/jdbc/fcdrrest";
            source = (javax.sql.DataSource) ic.lookup(dsName);
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized Connection getConnection() throws SQLException
    {
        try
        {
            return source.getConnection();
        }
        catch (Exception e)
        {
            throw new SQLException(e);
        }
    }
}
