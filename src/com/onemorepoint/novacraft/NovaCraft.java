package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class NovaCraft extends Activity
{
	public static final String TAG = "NovaCraft";
	public static NovaCraft instance;
	private NovaGLSurface mGLSurfaceView;
//	private GameObject gameObject;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	if(instance == null)
    		instance = this;
    		
    	// BELOW PRESENTLY CAUSES A CRASH
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        
        Log.v(TAG, "Welcome to NovaCraft");
	    
	    // Create our Preview view and set it as the content of our Activity
        mGLSurfaceView = new NovaGLSurface(this);
        setContentView(mGLSurfaceView);
    }
    
	@Override
    protected void onResume()
    {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause()
    {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onPause();
        mGLSurfaceView.onPause();
    }
}
