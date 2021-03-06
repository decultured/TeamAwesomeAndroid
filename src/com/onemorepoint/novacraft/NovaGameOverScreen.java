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

import com.onemorepoint.novacraft.novagame.NovaGame;

public class NovaGameOverScreen extends Activity
{
	/** Called when the activity is first created. */
	private int gameScore = 0;
	private int gameLives = 0;
	private int gameState = -1;
	
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
			gameState = intent.getIntExtra("com.onemorepoint.novacraft.GameState", -1);
			gameScore = intent.getIntExtra("com.onemorepoint.novacraft.GameScore", -1);
			gameLives = intent.getIntExtra("com.onemorepoint.novacraft.GameLives", -1);
		}
		
		TextView playerLives = (TextView)findViewById(R.id.playerLives);
		playerLives.setText( ""+ gameLives );
		
		TextView playerScore = (TextView)findViewById(R.id.playerScore);
		playerScore.setText( ""+ gameScore );
		
		ImageView btnNewGame = (ImageView)findViewById(R.id.btnNewGame);
		btnNewGame.setOnClickListener(btnNewGameListener);
	}
	
	View.OnClickListener btnNewGameListener = new View.OnClickListener() {
		public void onClick(View v) {
			Intent myIntent = new Intent(v.getContext(), NovaCraft.class);
			startActivity(myIntent);
		}
	};
}
