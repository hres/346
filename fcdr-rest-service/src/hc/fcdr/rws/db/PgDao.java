package hc.fcdr.rws.db;

import static hc.fcdr.rws.util.DaoUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.except.NoRowsAffectedDAOException;

public class PgDao
{
    private static final Logger logger =
            Logger.getLogger(PgDao.class.getName());

    protected Connection        connection;

    public PgDao(final Connection connection)
    {
        this.connection = connection;
    }

    protected ResultSet executeQuery(final String query, final Object[] values)
            throws DaoException
    {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            preparedStatement =
                    prepareStatement(connection, query, false, values);
          
            resultSet = preparedStatement.executeQuery();
            System.out.println(preparedStatement+" is the query");

        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(e, ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return resultSet;
    }

    protected Object executeUpdate(final String query, final Object[] values)
            throws DaoException
    {

        Object key = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        if(values!= null){
      //  System.out.println(values[1] + "est la date");
        }
        try
        {
        	
            preparedStatement =
                    prepareStatement(connection, query, true, values);
            final int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0 && (query.startsWith("insert") || query.startsWith("update") ||query.startsWith("COPY")))
                throw new NoRowsAffectedDAOException(
                        "Execute update error: no rows affected.");

            if (query.startsWith("insert"))
            {
                generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next())
                    key = generatedKeys.getObject(1);
                else
                    throw new DaoException(
                            "Creating object failed, no generated key obtained.");
            }
            else if (query.startsWith("delete"))
            {
            	//connection.commit(); 
            return affectedRows;
            }
            
        }
        catch (final SQLException e)
        {
            logger.error(e);
            
            throw new DaoException(e, ResponseCodes.INTERNAL_SERVER_ERROR);
        }
       
        return key;
    }

}
