package com.example.geoprox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class Geoprox extends Activity {

	static final int RESTART_GAME = 1;
	int [] buttoncolor;
	int colorblue;
	int colorgrey;
	int score;
	Button [] popButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geoprox);
		startGame();
	}
	
	private void startGame()
	{
		popButton = new Button[12];
		String buttonstring = "";
		buttoncolor = new int[12];
		colorblue = Color.argb(255, 50, 200, 255);
		colorgrey = Color.argb(255, 100, 100, 100);
		score = 0;
		TextView mTextField = (TextView) findViewById(R.id.score);
		mTextField.setText("Score: " + score);
		
		for (int i=0; i<12; i++)
		{
			buttoncolor[i] = 0;
			buttonstring = "button" + Integer.toString(i+1);
			int resID = getResources().getIdentifier(buttonstring, "id", "com.example.geoprox");
			popButton[i] = (Button) findViewById(resID);
			popButton[i].setBackgroundColor(colorgrey);
			popButton[i].setTag(R.id.string_key, 0);
			popButton[i].setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View buttonview) {
					int toggle = (Integer) buttonview.getTag(R.id.string_key);
					if (toggle == 0)
					{
						//buttonview.setBackgroundColor(colorblue);
						//buttonview.setTag(R.id.string_key,1);
						Log.v("HI","YOUFAIL");
						score--;
						TextView mTextField = (TextView) findViewById(R.id.score);
						mTextField.setText("Score: " + score);
					}
					else
					{
						turnOn();
						buttonview.setBackgroundColor(colorgrey);
						buttonview.setTag(R.id.string_key,0);
						
						score++;
						TextView mTextField = (TextView) findViewById(R.id.score);
						mTextField.setText("Score: " + score);
						Log.v("HI",Integer.toString(score));
					}
				}
			});
		}
		
		//Generate initial 3 blocks
		int i = 0;
		while (i < 3)
		{
			turnOn();
			i++;
		}
		
		//Countdown timer
		new CountDownTimer(10000, 10) {
			 TextView mTextField = (TextView) findViewById(R.id.timer);
		     public void onTick(long millisUntilFinished) {
		         mTextField.setText("Time Left: " + millisUntilFinished / 1000.0);
		     }

		     public void onFinish() {
		         mTextField.setText("TIMES UP!");
		         for (int i=0; i<12; i++)
		 		{
		        	String buttonstring = "";
		 			buttoncolor[i] = 0;
		 			buttonstring = "button" + Integer.toString(i+1);
		 			int resID = getResources().getIdentifier(buttonstring, "id", "com.example.geoprox");
		 			popButton[i] = (Button) findViewById(resID);
		 			popButton[i].setBackgroundColor(colorgrey);
		 			popButton[i].setTag(R.id.string_key, 0);
		 			popButton[i].setOnClickListener(null);
		 		}
		        scoreScreen();
		        
		         
		     }
		  }.start();
	}
	
	public void scoreScreen(){
		 Intent mIntent = new Intent(this, FinalScore.class);
         mIntent.putExtra("Score", Integer.toString(score));
         startActivityForResult(mIntent, RESTART_GAME);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (resultCode == RESULT_OK) {
	        // Make sure the request was successful
	    	Log.v("HI","RESTARTGMAE");
	        startGame();
	    }
	    else
	    {
	    	Log.v("HI","fail");
	    }
	}
	
	public void turnOn()
	{
		int i = 0;
		int randomnum = 0;
		while(i == 0)
		{
			randomnum = (int) (Math.random() * 12);
			Log.v("number",Integer.toString(randomnum));
			int toggle = (Integer) popButton[randomnum].getTag(R.id.string_key);
			if (toggle == 1)
				continue;
			else
				break;
		}
		popButton[randomnum].setBackgroundColor(colorblue);
		popButton[randomnum].setTag(R.id.string_key,1);
		
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
