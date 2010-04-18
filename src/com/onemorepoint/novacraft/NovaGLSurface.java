package com.onemorepoint.novacraft;

import com.onemorepoint.novacraft.game.*;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
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
}