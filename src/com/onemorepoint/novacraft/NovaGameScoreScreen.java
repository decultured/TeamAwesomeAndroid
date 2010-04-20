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

public class NovaGameScoreScreen extends Activity
{
	private int gameScore = 0;
	private int gameLives = 0;
	private int gameState = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//		   this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.score_screen);
		
		ImageView btnContinue = (ImageView)findViewById(R.id.btnContinue);
		btnContinue.setOnClickListener(btnContinueListener);
		
		UpdateUI();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		UpdateUI();
	}
	
	public void UpdateUI() {
		gameState = NovaGame.instance.getState();
		gameScore = NovaGame.instance.getScore();
		gameLives = NovaGame.instance.getLives();
		
		Log.v(NovaCraft.TAG, "NovaGameScoreScreen got gameState = "+ gameState);
		
		TextView playerLives = (TextView)findViewById(R.id.playerLives);
		playerLives.setText( ""+ gameLives );
		
		TextView playerScore = (TextView)findViewById(R.id.playerScore);
		playerScore.setText( ""+ gameScore );
		
		TextView gameMessage = (TextView)findViewById(R.id.gameMessage);
		if(gameState == NovaGame.STATE_PLAYER_WON) {
			gameMessage.setTextColor(0xFF00FF00);
			gameMessage.setText(R.string.game_state_player_won);
		} else {
			gameMessage.setTextColor(0xFFFF0000);
			gameMessage.setText(R.string.game_state_player_lost);
		}
	}
	
	View.OnClickListener btnContinueListener = new View.OnClickListener() {
		public void onClick(View v) {
			Intent myIntent = new Intent(v.getContext(), NovaCraft.class);
			startActivity(myIntent);
		}
	};
}
