package hc.fcdr.rws.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import hc.fcdr.rws.except.MailProcessorException;

public class MailProcessor
{
    private final Mail       mail;
    private String[]         filesToAttach;
    private final Properties props;
    private static Logger    log = Logger.getLogger(MailProcessor.class);

    /**
     * Constructor #1.
     * 
     * @@param mail The Mail object used to initialize the email message.
     */
    public MailProcessor(final Mail mail)
    {
        this.mail = mail;
        filesToAttach = null;

        props = new Properties();
        props.setProperty("mail.user", mail.getId());
        props.setProperty("mail.password", mail.getPasswd());
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", mail.getSmtp());
        props.put("mail.smtp.sendpartial", "true");
    }

    /**
     * Constructor #2.
     * 
     * @@param properties The Hashmap object used to initialize the email message.
     */
    public MailProcessor(final Properties properties)
    {
        mail = load(properties);

        props = new Properties();
        props.setProperty("mail.user", mail.getId());
        props.setProperty("mail.password", mail.getPasswd());
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", mail.getSmtp());
        props.put("mail.smtp.sendpartial", "true");
    }

    /** Loads the properties from the initialization file. */
    private Mail load(final Properties properties)
    {
        final Mail newMail = new Mail();

        newMail.setSmtp(properties.getProperty("mailSmtp"));
        newMail.setSenderName(properties.getProperty("mailSenderName"));
        newMail.setSenderAddress(properties.getProperty("mailSenderAddress"));
        newMail.setReceiverAddresses(
                properties.getProperty("mailReceiverAddress"));
        newMail.setId(properties.getProperty("mailId"));
        newMail.setPasswd(properties.getProperty("mailPasswd"));
        newMail.setSubject(properties.getProperty("mailSubject"));
        newMail.setText(properties.getProperty("mailText"));

        return newMail;
    }

    /**
     * Sends the email message with or without attachments to one or more recipients.
     */
    public void sendMessage()
    {
        Session session;
        Transport transport;
        MimeMessage aMessage;
        Address fromAddress;

        try
        {
            // Create session and connect to MTA.
            session = Session.getInstance(props, null);
            session.setDebug(false);
            transport = session.getTransport("smtp");

            log.info("Connecting to MTA...");
            transport.connect();
            log.info("Connection established.");

            // Create a message object.
            aMessage = new MimeMessage(session);
            if (aMessage == null)
                throw new MessagingException();

            final ArrayList<InternetAddress> recipients =
                    new ArrayList<InternetAddress>();
            InternetAddress internetAddress;

            for (int i = 0; i < (mail.getReceiverAddresses()).length; ++i)
            {
                internetAddress =
                        new InternetAddress((mail.getReceiverAddresses())[i]);

                if (internetAddress != null)
                    recipients.add(internetAddress);
            }

            final Address toAddresses[] = recipients.toArray(new Address[0]);

            if (mail.getSenderName() != null)
                fromAddress =
                        new InternetAddress(mail.getSenderAddress(),
                                mail.getSenderName());
            else
                fromAddress = new InternetAddress(mail.getSenderAddress());

            aMessage.setFrom(fromAddress);
            aMessage.setRecipients(javax.mail.Message.RecipientType.TO,
                    toAddresses);
            aMessage.setSubject(mail.getSubject());
            aMessage.setSentDate(new Date());

            // Set the text part of message.
            final MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(mail.getText(), "text/plain");

            // Assemble mail.
            final Multipart multipart = new MimeMultipart();

            if (filesToAttach != null)
                for (final String element : filesToAttach)
                    if (element != null)
                    {
                        final MimeBodyPart attachedFile = new MimeBodyPart();
                        attachedFile.attachFile(element);
                        multipart.addBodyPart(attachedFile);
                    }

            // Set the text part of message.
            multipart.addBodyPart(textPart);

            // Finally, set the content.
            aMessage.setContent(multipart);

            // Send message.
            aMessage.saveChanges();
            log.info("Sending message...");
            transport.sendMessage(aMessage, toAddresses);
            log.info("Email has successfully been sent to ");
            for (int i = 0; i < (mail.getReceiverAddresses()).length; ++i)
                if (i == ((mail.getReceiverAddresses()).length - 1))
                    log.info(((mail.getReceiverAddresses())[i]).trim() + ".");
                else
                    log.info((mail.getReceiverAddresses())[i] + ",");

            transport.close();
        }
        catch (final java.io.UnsupportedEncodingException e)
        {
            log.error("UnsupportedEncodingException ::  "
                    + mail.getReceiverAddresses());
            throw new MailProcessorException(e.getMessage());
        }
        catch (final javax.mail.internet.AddressException e)
        {
            log.error("AddressException ::  " + mail.getReceiverAddresses());
            throw new MailProcessorException(e.getMessage());
        }
        catch (final SendFailedException e)
        {
            log.error("SendFailedException ::  " + mail.getReceiverAddresses());
            throw new MailProcessorException(e.getMessage());
        }
        catch (final MessagingException e)
        {
            log.error("MessagingException ::  "
                    + mail.getReceiverAddresses() + "\n" + e.getMessage());
            throw new MailProcessorException(e.getMessage());
        }
        catch (final Exception e)
        {
            log.error("Exception ::  " + mail.getReceiverAddresses());
            try
            {
                throw new MailProcessorException(e.getMessage());
            }
            catch (final Exception e1)
            {

                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Includes one or more file attachments to this email message.
     * 
     * @@param filesToAttach One or more file attachments included with this email message.
     */
    public void setAttachments(final String[] filesToAttach)
    {
        if (filesToAttach != null)
        {
            this.filesToAttach = new String[filesToAttach.length];

            for (int i = 0; i < filesToAttach.length; ++i)
                this.filesToAttach[i] = filesToAttach[i];
        }
    }
}
