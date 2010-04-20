package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Window;
import android.view.View;
import android.widget.*;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import com.onemorepoint.novacraft.novagame.NovaGame;

public class NovaCraft extends Activity
{
	public static final String TAG = "NovaCraft";
	public static NovaCraft instance;
	private NovaGLSurface mGLSurfaceView;
	AbsoluteLayout glSurfaceContainer;
	LinearLayout gameHUDBox;
	
	public static Handler hRefresh;
	public static int MSG_UPDATE_GUI = 100;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		if(instance == null)
			instance = this;
			
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

        Log.v(TAG, "Welcome to NovaCraft");

	    // Create our Preview view and set it as the content of our Activity
		glSurfaceContainer = (AbsoluteLayout)findViewById(R.id.glSurfaceContainer);
		
		mGLSurfaceView = new NovaGLSurface(this);
		glSurfaceContainer.addView(mGLSurfaceView);
		
		hRefresh = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == MSG_UPDATE_GUI) {
					updateGameHUD();
				}
			}
		};
	}
	
	public void updateGameHUD() {
		NovaGame gameObject = NovaGame.instance;
		
		TextView gameHUDScore = (TextView)NovaCraft.instance.findViewById(R.id.gameHUDScore);
		gameHUDScore.setText("" + gameObject.getScore());
		
		TextView gameHUDLives = (TextView)NovaCraft.instance.findViewById(R.id.gameHUDLives);
		gameHUDLives.setText("" + gameObject.getLives());
		
		ProgressBar gameHUDHealthBar = (ProgressBar)NovaCraft.instance.findViewById(R.id.gameHUDHealthBar);
		gameHUDHealthBar.setProgress( (int)(gameObject.getPlayerHealth()) );
	}
	
	@Override
	protected void onResume()
	{
		// Ideally a game should implement onResume() and onPause()
		// to take appropriate action when the activity looses focus
		
		super.onResume();
		mGLSurfaceView.onResume();
		
		// Intent intent = getIntent();
		// Log.v(NovaCraft.TAG, "Intent passed = "+ intent);
		// if(intent != null) {
		// 	NovaCraft.hasGameState = true;
		// 	NovaCraft.isGameOver = intent.getBooleanExtra("com.onemorepoint.novacraft.GameOver", false);
		// 	NovaCraft.gameScore = intent.getIntExtra("com.onemorepoint.novacraft.GameScore", 0);
		// 	NovaCraft.gameLives = intent.getIntExtra("com.onemorepoint.novacraft.GameLives", 0);
		// }
	}

    @Override
    protected void onPause()
    {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onPause();
        mGLSurfaceView.onPause();
    }
    
    @Override
    protected void onDestroy()
    {
    	super.onDestroy();
    	mGLSurfaceView.onDestroy();
    	glSurfaceContainer.removeView(mGLSurfaceView);
    	mGLSurfaceView = null;
    }
}
