package hc.fcdr.rws.mail;

import java.io.InputStream;
import java.util.Arrays;

public class Mail
{
    private String      smtp;
    private String      senderName;
    private String      senderAddress;
    private String[]    receiverAddresses;
    private String      imap;
    private String      id;
    private String      passwd;
    private String      subject;
    private String      text;
    private InputStream istream;
    private String      receiverName;

    /** Default constructor. */
    public Mail()
    {
        smtp = "";
        senderName = "";
        senderAddress = "";
        receiverAddresses = null;
        imap = "";
        id = "";
        passwd = "";
        subject = "";
        text = "";
        istream = null;
        receiverName = "";
    }

    /**
     * Returns the id.
     * 
     * @@return The id.
     */
    public String getId()
    {
        return id;
    }

    public String getImap()
    {
        return imap;
    }

    /**
     * Returns the InputStream object.
     * 
     * @@return The InputStream object.
     */
    public InputStream getIstream()
    {
        return istream;
    }

    /**
     * Returns the password.
     * 
     * @@return The password.
     */
    public String getPasswd()
    {
        return passwd;
    }

    /**
     * Returns the addresses of the receivers.
     * 
     * @@return The addresses of the receivers.
     */
    public String[] getReceiverAddresses()
    {
        return receiverAddresses;
    }

    /**
     * Returns the name of the receiver.
     * 
     * @@return The name of the receiver.
     */
    public String getReceiverName()
    {
        return receiverName;
    }

    /**
     * Returns the address of the sender.
     * 
     * @@return The address of the sender.
     */
    public String getSenderAddress()
    {
        return senderAddress;
    }

    /**
     * Returns the name of the sender.
     * 
     * @@return The name of the sender.
     */
    public String getSenderName()
    {
        return senderName;
    }

    /**
     * Returns the smtp of this email.
     * 
     * @@return smtp of this email.
     */
    public String getSmtp()
    {
        return smtp;
    }

    /**
     * Returns the subject line of the message.
     * 
     * @@return The subject line of the message.
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * Returns the text of the message.
     * 
     * @@return The text of the message.
     */
    public String getText()
    {
        return text;
    }

    /**
     * Parses a given expression.
     * 
     * @@param inputExp The String expression to be parsed.
     * @@return The String array representation of the parsed expression.
     */
    private String[] parseExp(final String inputExp)
    {
        final java.util.Vector<String> v = new java.util.Vector<String>();
        final java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(
                inputExp, ",;:", false);

        while (tokenizer.hasMoreTokens())
        {
            final String token = tokenizer.nextToken();
            if (token.trim().length() > 0)
                v.add(token);
        }

        final String exp[] = new String[v.size()];
        for (int i = 0; i < v.size(); ++i)
            exp[i] = v.get(i);

        return exp;
    }

    /**
     * Sets the id.
     * 
     * @@param id The id.
     */
    public void setId(final String id)
    {
        this.id = id;
    }

    /**
     * Sets the imap for this email.
     * 
     * @@param imap imap.
     */
    public void setImap(final String imap)
    {
        this.imap = imap;
    }

    /**
     * Sets the input stream.
     * 
     * @@param istream input stream.
     */
    public void setIstream(final InputStream istream)
    {
        this.istream = istream;
    }

    /**
     * Sets the password.
     * 
     * @@param passwd The password.
     */
    public void setPasswd(final String passwd)
    {
        this.passwd = passwd;
    }

    /**
     * Sets the addresses of the receivers.
     * 
     * @@param receiverAddresses The addresses of the receivers.
     */
    public void setReceiverAddresses(final String receiverAddresses)
    {
        this.receiverAddresses = parseExp(receiverAddresses);
    }

    /**
     * Sets the name of the receiver.
     * 
     * @@param receiverName The name of the receiver.
     */
    public void setReceiverName(final String receiverName)
    {
        this.receiverName = receiverName;
    }

    /**
     * Sets the address of the sender.
     * 
     * @@param senderAddress The address of the sender.
     */
    public void setSenderAddress(final String senderAddress)
    {
        this.senderAddress = senderAddress;
    }

    /**
     * Sets the name of the sender.
     * 
     * @@param senderName The name of the sender.
     */
    public void setSenderName(final String senderName)
    {
        this.senderName = senderName;
    }

    /**
     * Sets the smtp for this email.
     * 
     * @@param smtp smtp.
     */
    public void setSmtp(final String smtp)
    {
        this.smtp = smtp;
    }

    /**
     * Sets the subject line.
     * 
     * @@param subject The subject line.
     */
    public void setSubject(final String subject)
    {
        this.subject = subject;
    }

    /**
     * Sets the text of the email.
     * 
     * @@param text The text of the email.
     */
    public void setText(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "Mail [id=" + id + ", imap=" + imap + ", istream=" + istream
                + ", passwd=" + passwd + ", receiverAddresses="
                + Arrays.toString(receiverAddresses) + ", receiverName="
                + receiverName + ", senderAddress=" + senderAddress
                + ", senderName=" + senderName + ", smtp=" + smtp + ", subject="
                + subject + ", text=" + text + "]";
    }
}
