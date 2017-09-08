package hc.fcdr.rws.except;

import hc.fcdr.rws.config.ResponseCodes;

/**
 * This class represents a generic DAO exception. It should wrap any exception on the database level , such as
 * SQLExceptions.
 */
public class DaoException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 4225639944462859016L;
    private ResponseCodes     error;

    /**
     * Constructs a DaoException with the given detail message.
     * 
     * @param message
     *            The detail message of the DaoException.
     */
    public DaoException(ResponseCodes error)
    {
        super(error.getMessage());
        this.error = error;
    }

    public DaoException(Exception e, ResponseCodes error)
    {
        super(e);
        this.error = error;
    }

    public DaoException(String message)
    {
        super(message);
    }

    public ResponseCodes getError()
    {
        return this.error;
    }

    /**
     * Constructs a DaoException with the given root cause.
     * 
     * @param cause
     *            The root cause of the DaoException.
     */
    public DaoException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a DaoException with the given detail message and root cause.
     * 
     * @param message
     *            The detail message of the DaoException.
     * @param cause
     *            The root cause of the DaoException.
     */
    public DaoException(String message, Throwable cause)
    {
        super(message, cause);
    }

}