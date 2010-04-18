package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
//import game;

public class NovaGameOverScreen extends Activity
{
	/** Called when the activity is first created. */
	private int gameScore = 0;
	private int gameLives = 0;
	private boolean isGameOver = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//		   this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.game_over);
		
		Intent intent = getIntent();
		Log.v(NovaCraft.TAG, "INTENTS = "+ intent);
		if(intent != null) {
			isGameOver = intent.getBooleanExtra("com.onemorepoint.novacraft.GameOver", false);
			gameScore = intent.getIntExtra("com.onemorepoint.novacraft.GameScore", 0);
			gameLives = intent.getIntExtra("com.onemorepoint.novacraft.GameLives", 0);
		}
		
		TextView playerLives = (TextView)findViewById(R.id.playerLives);
		playerLives.setText( ""+ gameLives );
		
		TextView playerScore = (TextView)findViewById(R.id.playerScore);
		playerScore.setText( ""+ gameScore );
		
		TextView gameMessage = (TextView)findViewById(R.id.gameMessage);
		if(isGameOver == true) {
			gameMessage.setTextColor(0xFFFF0000);
			gameMessage.setText("GAME OVER");
			gameMessage.setVisibility(View.VISIBLE);
		} else {
			gameMessage.setVisibility(View.GONE);
		}
		
		ImageView btnNextLevel = (ImageView)findViewById(R.id.btnNextLevel);
		btnNextLevel.setOnClickListener(btnNextLevelListener);
	}
	
	View.OnClickListener btnNextLevelListener = new View.OnClickListener() {
		public void onClick(View v) {
			Intent myIntent = new Intent(v.getContext(), NovaCraft.class);
			
			myIntent.putExtra("com.onemorepoint.novacraft.GameOver", isGameOver);
			myIntent.putExtra("com.onemorepoint.novacraft.GameLives", gameLives);
			myIntent.putExtra("com.onemorepoint.novacraft.GameScore", gameScore);
			
			startActivityForResult(myIntent, 0);
		}
	};
	
	@Override
	protected void onResume()
	{
		// Ideally a game should implement onResume() and onPause()
		// to take appropriate action when the activity looses focus
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		// Ideally a game should implement onResume() and onPause()
		// to take appropriate action when the activity looses focus
		super.onPause();
	}
}
