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
    public static String getContentType(String fileName)
    {
        FileInputStream is = null;

        try
        {
            File f = new File(fileName);
            is = new FileInputStream(f);

            ContentHandler contenthandler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            metadata.set(TikaMetadataKeys.RESOURCE_NAME_KEY, f.getName());
            Parser parser = new AutoDetectParser();
            parser.parse(is, contenthandler, metadata, null);
            // /System.out.println("content: " + contenthandler.toString());

            return metadata.get(HttpHeaders.CONTENT_TYPE);
        }
        catch (Exception e)
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
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }

        return "";
    }
}
