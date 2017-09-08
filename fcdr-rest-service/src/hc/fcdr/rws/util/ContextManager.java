package hc.fcdr.rws.util;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 * General helper class assisting with authentication, login, environment variables, etc.
 */
public class ContextManager
{
    private static final Logger        logger                  = Logger.getLogger(
            ContextManager.class.getName());

    private static Map<String, String> environmentVars;

    public final static String         APPLICATION_ENVIRONMENT = "APPLICATION_ENVIRONMENT";
    public final static String         SCHEMA                  = "SCHEMA";

    public static String getJndiValue(String key)
    {
        try
        {
            Map<String, String> context = getContext();
            return context.get(key);
        }
        catch (Exception e)
        {
            logger.fatal("Exception retrieving value: " + key + "::"
                    + e.getMessage(), e);
            return null;
        }
    }

    private static synchronized Map<String, String> getContext()
    {
        if (environmentVars == null)
            initialEnvVars();
        return environmentVars;
    }

    private static void initialEnvVars()
    {
        Map<String, String> m = new HashMap<String, String>();

        m.put(APPLICATION_ENVIRONMENT,
                getBindingVariable("application_environment"));
        m.put(SCHEMA, getBindingVariable("schema"));

        environmentVars = m;
    }

    private static String getBindingVariable(String varName)
    {
        String value = "";
        try
        {
            Context initialContext = new InitialContext();
            value = (String) initialContext.lookup(
                    "java:comp/env/" + "fcdr/" + varName);

        }
        catch (NameNotFoundException e)
        {
            logger.fatal("WARNING: JNDI binding was not found: " + varName);
        }
        catch (NamingException e)
        {
            logger.fatal("WARNING: JNDI binding error: " + varName);
        }
        catch (Exception e)
        {
            logger.fatal("WARNING: Unable to locate configuration JNDI key: "
                    + varName);
            throw new RuntimeException("Unable to locate:" + varName, e);
        }
        return value;
    }

    public static String getSchema()
    {
        return ContextManager.getJndiValue("SCHEMA");
    }

}
