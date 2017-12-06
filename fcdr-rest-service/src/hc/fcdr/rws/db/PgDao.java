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
import hc.fcdr.rws.model.pkg.NftModel;
import hc.fcdr.rws.model.pkg.NftRequest;

public class PgDao
{
    private static final Logger logger = Logger.getLogger(
            PgDao.class.getName());
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
            preparedStatement = prepareStatement(connection, query, false,
                    values);
            resultSet = preparedStatement.executeQuery();

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

        try
        {
            preparedStatement = prepareStatement(connection, query, true,
                    values);
            final int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0)
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
                return affectedRows;

        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(e, ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return key;
    }
    
    protected boolean executeInsertNft(NftRequest nftRequest,  String schema)
            throws DaoException, SQLException
    {
    	
    	String query = "insert into "+schema+"."+"product_component(component_id, package_id, amount,"
    					+ " amount_unit_of_measure, percentage_daily_value, as_ppd_flag) "
    					+ "select component_id, ?, ?, ?, ?, ? from fcdrschema.component "
    					+ "where component_id = ("
    					+ "select component_id from "+schema+"."+"component where component_name= ?)";
    	
    	try {
    		connection.setAutoCommit(false);
    		for(NftModel element : nftRequest.getNft()){
    			//ecuteInsertNft(element, nftRequest.getPackage_id(), nftRequest.getFlag(), schema);
    			
    		
    		
			PreparedStatement preparedStatement = connection.prepareStatement(query);
												  preparedStatement.setObject(1, nftRequest.getPackage_id());
												  preparedStatement.setObject(2, element.getAmount());
												  preparedStatement.setObject(3, element.getUnit_of_measure());
												  preparedStatement.setObject(4, element.getDaily_value());
												  preparedStatement.setObject(5, nftRequest.getFlag());
												  preparedStatement.setObject(6, element.getName());
												  preparedStatement.executeUpdate();
    		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			connection.rollback();
			e.printStackTrace();
			
			return false;
		}
    	connection.commit();
    	return true; 

    }
}
