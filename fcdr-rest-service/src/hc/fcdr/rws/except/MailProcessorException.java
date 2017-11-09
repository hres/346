package hc.fcdr.rws.except;

public class MailProcessorException extends RuntimeException
{
    /**
     * 
     */
    private static final long serialVersionUID = 5636970932904099547L;

    /**
     * Constructs an MailProcessorException with a detail message.
     * 
     * @@param errorMessage the message to display.
     */
    public MailProcessorException(final String errorMessage)
    {
        super(errorMessage);
    }
}
