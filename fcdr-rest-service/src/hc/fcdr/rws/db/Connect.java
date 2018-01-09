package hc.fcdr.rws.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect
{
    static final String URL      = "jdbc:postgresql://localhost:5432/basedebonnee";
    static final String USER     = "postgres";
    static final String PASSWORD = "romario";

    public static Connection getConnections()
            throws SQLException, Exception, IOException
    {
        Class.forName("org.postgresql.Driver");
        final Connection connection = DriverManager.getConnection(URL, USER,
                PASSWORD);

        if (connection != null)
        {
            System.out.println("Connection has been established");
            return connection;
        }
        else
            return null;

    }
}