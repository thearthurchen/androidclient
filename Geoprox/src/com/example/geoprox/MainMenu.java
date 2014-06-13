package com.example.geoprox;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		Button createRoom = (Button) findViewById(R.id.createroom);
		Button joinRoom = (Button) findViewById(R.id.joinroom);
		Button startGame = (Button) findViewById(R.id.startgame);
		
		startGame.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View myview) {
				Intent mIntent = new Intent(myview.getContext(), Geoprox.class);
		        startActivity(mIntent);
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

}
