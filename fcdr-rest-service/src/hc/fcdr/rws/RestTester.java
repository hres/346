package hc.fcdr.rws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class RestTester
{
    public static void main(final String[] args)
            throws ClientProtocolException, IOException
    {
        final HttpClient client = new DefaultHttpClient();
        final HttpPost post = new HttpPost(
                "http://localhost:8087/fcdr-rest-service/rest/ProductService/productsfiltered");

        final JSONObject json = new JSONObject();
        /// json.put("product_id", 10);
        final StringEntity input = new StringEntity(json.toString());

        /// StringEntity input = new StringEntity("{\'id\': 10}");
        input.setContentType("application/json");

        post.setEntity(input);
        final HttpResponse response = client.execute(post);

        final BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        String line = "";

        while ((line = rd.readLine()) != null)
            System.out.println(line);
    }
}
