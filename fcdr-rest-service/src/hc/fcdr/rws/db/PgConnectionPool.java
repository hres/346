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
            final InitialContext ic = new InitialContext();
            final String dsName = "java:comp/env/jdbc/fcdrrest";
            source = (javax.sql.DataSource) ic.lookup(dsName);
        }
        catch (final NamingException e)
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
        catch (final Exception e)
        {
            throw new SQLException(e);
        }
    }
}
