package hc.fcdr.rws.except;

import hc.fcdr.rws.config.ResponseCodes;

public class NoRowsAffectedDAOException extends DaoException
{
    /**
     * 
     */
    private static final long serialVersionUID = -1056474221823377173L;

    public NoRowsAffectedDAOException(ResponseCodes error)
    {
        super(error);
    }

    public NoRowsAffectedDAOException(Exception e, ResponseCodes error)
    {
        super(e, error);
    }

    public NoRowsAffectedDAOException(String message)
    {
        super(message);
    }

    public NoRowsAffectedDAOException(Throwable cause)
    {
        super(cause);
    }

    public NoRowsAffectedDAOException(String message, Throwable cause)
    {
        super(message, cause);
    }
}