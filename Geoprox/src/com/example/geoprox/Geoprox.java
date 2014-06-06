package com.example.geoprox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Geoprox extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geoprox);
		
		
		Button popButton = (Button) findViewById(R.id.get_button);
		popButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//HTTP URL declared here
				String url = "http://geo-prox.herokuapp.com/api/locate?lon=35&lat=40";
				HttpAsyncTask httprun = new HttpAsyncTask();
			    httprun.execute(url);
			}
		});
		
	}

	/*
	 * Simple HTTP GET routine
	 * Takes HTTP URL and returns GET response in String
	 */
	public String httpGet(String url) throws IOException
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = httpclient.execute(request);

		// Get the response
		BufferedReader rd = new BufferedReader
		  (new InputStreamReader(response.getEntity().getContent()));
		
		String readLine;
		String responseBody = "";
		while (((readLine = rd.readLine()) != null)) {
		 responseBody += "\n" + readLine;
		}
		return responseBody;
	}
	
	/*
	 * AsyncTask to perform network task on main thread
	 */
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
        	String response = "NO RESPONSE";
            try {
				response = httpGet(url[0]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return response;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        	Toast.makeText(getApplicationContext(), result, 
					   Toast.LENGTH_LONG).show();
       }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.geoprox, menu);
		return true;
	}

}
