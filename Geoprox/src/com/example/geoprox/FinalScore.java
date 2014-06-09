package com.example.geoprox;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FinalScore extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final_score);
		
		String score = getIntent().getExtras().getString("Score");
		TextView mTextField = (TextView) findViewById(R.id.final_score);
		mTextField.setText("Final Score: " + score);
		
		Button mButton = (Button) findViewById(R.id.restart);
		mButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View buttonview) {
				setResult(RESULT_OK);
				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.final_score, menu);
		return true;
	}

}
