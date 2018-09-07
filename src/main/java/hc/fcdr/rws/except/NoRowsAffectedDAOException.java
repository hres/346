package hc.fcdr.rws.except;

import hc.fcdr.rws.config.ResponseCodes;

public class NoRowsAffectedDAOException extends DaoException
{
    /**
     * 
     */
    private static final long serialVersionUID = -1056474221823377173L;

    public NoRowsAffectedDAOException(final ResponseCodes error)
    {
        super(error);
    }

    public NoRowsAffectedDAOException(final Exception e,
            final ResponseCodes error)
    {
        super(e, error);
    }

    public NoRowsAffectedDAOException(final String message)
    {
        super(message);
    }

    public NoRowsAffectedDAOException(final Throwable cause)
    {
        super(cause);
    }

    public NoRowsAffectedDAOException(final String message,
            final Throwable cause)
    {
        super(message, cause);
    }
}