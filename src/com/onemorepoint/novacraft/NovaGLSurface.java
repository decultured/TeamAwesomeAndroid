package com.onemorepoint.novacraft;

import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.novagame.*;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.content.Context;
import android.util.Log;

public class NovaGLSurface extends GLSurfaceView
{
	NovaRenderer mRenderer;
    public NovaGLSurface(Context context)
    {
        super(context);
        mRenderer = new NovaRenderer();
        setRenderer(mRenderer);
    }

    public boolean onTouchEvent(final MotionEvent event)
    {
        queueEvent(new Runnable(){
            public void run() {
            }});
			
		if (event.getAction() == MotionEvent.ACTION_UP)
			Input.SetInput(false, event.getX(), event.getY());
		else
			Input.SetInput(true, event.getX(), event.getY());
		
        return true;
	}
	
	@Override
    public void onResume()
    {
    	super.onResume();
    	mRenderer.onResume();
    }

	// @Override
	// public void onUpdate() {
	// 	super.onUpdate();
	// 	
	// 	if(NovaGame.instance != null) {
	// 		TextView gameHUDScore = (TextView)findViewById(R.id.gameHUDScore);
	// 		if(gameHUDScore != null) {
	// 			try {
	// 				gameHUDScore.setText("" + NovaGame.instance.getScore());
	// 			} catch(Exception e) {
	// 				Log.v(NovaCraft.TAG, ""+ e);
	// 			}
	// 		}
	// 		
	// 		TextView gameHUDLives = (TextView)findViewById(R.id.gameHUDLives);
	// 		if(gameHUDLives != null) {
	// 			gameHUDLives.setText("" + NovaGame.instance.getLives());
	// 		}
	// 					
	// 		ProgressBar gameHUDHealthBar = (ProgressBar)findViewById(R.id.gameHUDHealthBar);
	// 		Log.v(NovaCraft.TAG, "U HAS HUD: "+ gameHUDHealthBar);
	// 		if(gameHUDHealthBar != null) {
	// 			gameHUDHealthBar.setProgress( (int)(NovaGame.instance.getPlayerHealth()) );
	// 		}
	// 	}
	// }

    @Override
    public void onPause()
    {
    	super.onPause();
    	mRenderer.onPause();
    }
    
    public void onDestroy()
    {
    	mRenderer.onDestroy();
    }
}