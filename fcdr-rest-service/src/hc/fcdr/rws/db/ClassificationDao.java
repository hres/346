package hc.fcdr.rws.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import hc.fcdr.rws.config.ResponseCodes;
import hc.fcdr.rws.domain.Classification;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.classification.ClassificationData;
import hc.fcdr.rws.model.classification.ClassificationDataResponse;
import hc.fcdr.rws.model.classification.ClassificationResponse;
import hc.fcdr.rws.util.DaoUtil;

public class ClassificationDao extends PgDao
{
    private static final Logger logger = Logger.getLogger(
            ClassificationDao.class.getName());
    private final String        schema;

    public ClassificationDao(final Connection connection, final String schema)
    {
        super(connection);
        this.schema = schema;
    }

    public List<Classification> getClassification() throws DaoException
    {
        ResultSet resultSet = null;
        final List<Classification> classificationList = new ArrayList<Classification>();

        final String query = "select * from " + schema + "." + "classification";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
                classificationList.add(DaoUtil.getClassification(resultSet));
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return classificationList;
    }

    public Classification getClassification(final Long classificationId)
            throws DaoException
    {
        ResultSet resultSet = null;
        Classification classification = null;

        final String query = "select * from " + schema + "."
                + "classification where classification_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { classificationId });

            if (resultSet.next())
                classification = DaoUtil.getClassification(resultSet);
        }
        catch (final SQLException e)
        {
            logger.error(e);
            throw new DaoException(ResponseCodes.INTERNAL_SERVER_ERROR);
        }

        return classification;
    }

    // ===

    public ClassificationDataResponse getClassificationResponse()
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        ClassificationResponse classificationResponse = null;

        final ClassificationData data = new ClassificationData();

        final String query = "select * from " + schema + "." + "classification";

        try
        {
            resultSet = executeQuery(query, null);

            while (resultSet.next())
            {
                classificationResponse = DaoUtil.getClassificationResponse(
                        resultSet);
                data.add(classificationResponse);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new ClassificationDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new ClassificationDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

    // ===

    public ClassificationDataResponse getClassificationResponse(
            final Long classificationId)
            throws SQLException, IOException, Exception
    {
        ResultSet resultSet = null;
        ClassificationResponse classificationResponse = null;

        final ClassificationData data = new ClassificationData();

        final String query = "select * from " + schema + "."
                + "classification where classification_id = ?";

        try
        {
            resultSet = executeQuery(query, new Object[]
            { classificationId });

            if (resultSet.next())
            {
                classificationResponse = DaoUtil.getClassificationResponse(
                        resultSet);
                data.add(classificationResponse);
            }
        }
        catch (final SQLException e)
        {
            logger.error(e);
            return new ClassificationDataResponse(
                    ResponseCodes.INTERNAL_SERVER_ERROR.getCode(), null,
                    ResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
        }

        return new ClassificationDataResponse(ResponseCodes.OK.getCode(), data,
                ResponseCodes.OK.getMessage());
    }

}