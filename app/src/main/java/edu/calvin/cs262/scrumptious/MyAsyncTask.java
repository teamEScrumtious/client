/**
 * MyAsncTask.java
 *
 * Created by tjluce on 12/14/15.
 *
 * MyAsyncTask is a asynchronous task that handles HTTP calls to the server and returns the results
 * with the help of the AsyncResponse
 *
 * Uses http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
 */

package edu.calvin.cs262.scrumptious;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MyAsyncTask extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate=null;
    private String URI = "";

    public MyAsyncTask(String newURI) {
        URI = newURI;
    }

    /**
     * This method extracts text from the HTTP response entity.
     *
     * @param entity
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();
        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n > 0) {
            byte[] b = new byte[4096];
            n = in.read(b);
            if (n > 0) out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    /**
     * This method issues the HTTP GET request.
     *
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        String s = "";
        if (params.length > 0) {
            s = params[0];
        }
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost;
        HttpGet httpGet;
        ArrayList<NameValuePair> postParameters;

        httpGet = new HttpGet(URI);
        httpPost = new HttpPost(URI);


        if (s.length() > 0) {
           // postParameters = new ArrayList<NameValuePair>();
            //postParameters.add(new BasicNameValuePair("param1", s));
            try {
                httpPost.setEntity(new StringEntity(s));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String text = null;
        try {
            HttpResponse response;
            if (s.length() > 0) {
                response = httpClient.execute(httpPost, localContext);
            } else {
                response = httpClient.execute(httpGet, localContext);
            }

            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);
            Log.d(Scrumptious.class.getSimpleName(), "Received data from server at address " + URI);
            Log.d(Scrumptious.class.getSimpleName(), "Data received is:\n" + text);
        } catch (Exception e) {
            Log.d(Scrumptious.class.getSimpleName(), "Failed to retrieve data from the server.");
            return e.getLocalizedMessage();
        }
        return text;
    }

    /**
     * The method takes the results of the request, when they arrive, and updates the interface.
     *
     * @param results
     */
    @Override
    protected void onPostExecute(String results) {
        super.onPostExecute(results);
        if (results != null) {
            delegate.processFinish(results);
        } else {
            delegate.processFinish("Nothing found.");
        }
    }
}
