package hc.fcdr.rws.except;

public class DaoConfigurationException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = -2730398846999010551L;

    /**
     * Constructs a DAOConfigurationException with the given detail message.
     * 
     * @param message
     *            The detail message of the DAOConfigurationException.
     */
    public DaoConfigurationException(String message)
    {
        super(message);
    }

    /**
     * Constructs a DAOConfigurationException with the given root cause.
     * 
     * @param cause
     *            The root cause of the DAOConfigurationException.
     */
    public DaoConfigurationException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a DAOConfigurationException with the given detail message and root cause.
     * 
     * @param message
     *            The detail message of the DAOConfigurationException.
     * @param cause
     *            The root cause of the DAOConfigurationException.
     */
    public DaoConfigurationException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
