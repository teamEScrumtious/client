package edu.calvin.cs262.scrumptious;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tjluce on 12/14/15.
 * Uses http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
 */
public class MyAsyncTask extends AsyncTask<Void, Void, String> {
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
    protected String doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet(URI);
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
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
