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
import hc.fcdr.rws.model.pkg.NftView;

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
            connection.setAutoCommit(false);
            preparedStatement = prepareStatement(connection, query, true,
                    values);
            System.out.println(preparedStatement);
            final int affectedRows = preparedStatement.executeUpdate();

            System.out.println("the number returned is: " + affectedRows);

            if (affectedRows == 0)
                throw new NoRowsAffectedDAOException(
                        "Execute update error: no rows affected.");

            if (query.startsWith("insert"))
            {
                generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next())
                {
                    key = generatedKeys.getObject(1);
                    System.out.println("Id of the package: " + key);

                }
                else
                    throw new DaoException(
                            "Creating object failed, no generated key obtained.");
            }
            else if (query.startsWith("delete"))
                return affectedRows;

            connection.commit();
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(e, ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return key;
    }

    protected boolean executeInsertNft(final NftRequest nftRequest,
            final String schema) throws DaoException, SQLException
    {

        final String query = "insert into " + schema + "."
                + "product_component(component_id, package_id, amount,"
                + " amount_unit_of_measure, percentage_daily_value, as_ppd_flag) "
                + "select component_id, ?, ?, ?, ?, ? from " + schema
                + ".component " + "where component_id = ("
                + "select component_id from " + schema + "."
                + "component where component_name= ?)";

        try
        {
            connection.setAutoCommit(false);
            for (final NftModel element : nftRequest.getNft())
            {
                // ecuteInsertNft(element, nftRequest.getPackage_id(), nftRequest.getFlag(), schema);

                final PreparedStatement preparedStatement = connection.prepareStatement(
                        query);
                preparedStatement.setObject(1, nftRequest.getPackage_id());
                preparedStatement.setObject(2, element.getAmount());
                preparedStatement.setObject(3, element.getUnit_of_measure());
                preparedStatement.setObject(4, element.getDaily_value());
                preparedStatement.setObject(5, nftRequest.getFlag());
                preparedStatement.setObject(6, element.getName());
                preparedStatement.executeUpdate();
            }
        }
        catch (final SQLException e)
        {
            // TODO Auto-generated catch block
            connection.rollback();
            e.printStackTrace();

            return false;
        }
        connection.commit();
        return true;

    }

    protected boolean executeUpdateNft(final NftRequest nftRequest,
            final String schema) throws DaoException, SQLException
    {

        final String query = "insert into " + schema + "."
                + "product_component(component_id, package_id, amount,"
                + " amount_unit_of_measure, percentage_daily_value, as_ppd_flag) "
                + "select component_id, ?, ?, ?, ?, ? from " + schema
                + ".component " + "where component_id = ("
                + "select component_id from " + schema + "."
                + "component where component_name= ?)";

        try
        {
            for (final NftModel element : nftRequest.getNft())
            {
                // ecuteInsertNft(element, nftRequest.getPackage_id(), nftRequest.getFlag(), schema);

                final PreparedStatement preparedStatement = connection.prepareStatement(
                        query);
                preparedStatement.setObject(1, nftRequest.getPackage_id());
                preparedStatement.setObject(2, element.getAmount());
                preparedStatement.setObject(3, element.getUnit_of_measure());
                preparedStatement.setObject(4, element.getDaily_value());
                preparedStatement.setObject(5, nftRequest.getFlag());
                preparedStatement.setObject(6, element.getName());
                preparedStatement.executeUpdate();
            }
        }
        catch (final SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return false;
        }
        return true;

    }

    protected NftView executeGetNft(final String query,
            final Integer package_id, final Boolean flag)
            throws DaoException, SQLException
    {
        final NftView nftList = new NftView();
        ResultSet resultSet = null;

        try
        {

            final PreparedStatement preparedStatement = connection.prepareStatement(
                    query);
            preparedStatement.setObject(1, package_id);
            preparedStatement.setObject(2, flag);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                String name = resultSet.getString("component_name");
                name = resultSet.wasNull() ? null
                        : resultSet.getString("component_name");

                Double amount = resultSet.getDouble("amount");
                amount = resultSet.wasNull() ? null
                        : resultSet.getDouble("amount");
                System.out.println(
                        resultSet.getString("component_name") + ": " + amount);
                String amount_unit_of_measure = resultSet.getString(
                        "amount_unit_of_measure");
                amount_unit_of_measure = resultSet.wasNull() ? null
                        : resultSet.getString("amount_unit_of_measure");

                Double percentage_daily_value = resultSet.getDouble(
                        "percentage_daily_value");
                percentage_daily_value = resultSet.wasNull() ? null
                        : resultSet.getDouble("percentage_daily_value");
                // String name, Double amount, String unit_of_measure, Double daily_value
                nftList.getNft().add(new NftModel(name, amount,
                        amount_unit_of_measure, percentage_daily_value));
            }

        }
        catch (final SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return null;
        }
        return nftList;

    }

    public int checkIfHasNft(final NftRequest nftRequest, final String schema,
            final Boolean flag) throws DaoException
    {

        ResultSet resultSetCount = null;
        int number_of_records = 0;

        final String query = "select count (*) AS COUNT from " + schema + "."
                + "product_component where as_ppd_flag = ? and package_id = ?";

        try
        {
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    query);
            preparedStatement.setObject(1, flag);
            preparedStatement.setObject(2, nftRequest.getPackage_id());
            resultSetCount = preparedStatement.executeQuery();

            resultSetCount.next();
            number_of_records = resultSetCount.getInt("COUNT");
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return number_of_records;
    }
}
