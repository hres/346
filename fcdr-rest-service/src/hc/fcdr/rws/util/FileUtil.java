package hc.fcdr.rws.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

public class FileUtil
{
    public static String getContentType(final String fileName)
    {
        FileInputStream is = null;

        try
        {
            final File f = new File(fileName);
            is = new FileInputStream(f);

            final ContentHandler contenthandler = new BodyContentHandler();
            final Metadata metadata = new Metadata();
            metadata.set(TikaMetadataKeys.RESOURCE_NAME_KEY, f.getName());
            final Parser parser = new AutoDetectParser();
            parser.parse(is, contenthandler, metadata, null);
            // /System.out.println("content: " + contenthandler.toString());

            return metadata.get(HttpHeaders.CONTENT_TYPE);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (is != null)
                try
                {
                    is.close();
                }
                catch (final IOException e)
                {
                    e.printStackTrace();
                }
        }

        return "";
    }
}
