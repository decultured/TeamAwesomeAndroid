package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.content.Context;
import android.util.Log;

class NovaGLSurface extends GLSurfaceView
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
                mRenderer.setColor(event.getX() / getWidth(),
                        event.getY() / getHeight(), (event.getX()+event.getY())/(getWidth()+getHeight()));
            }});
			Log.v(NovaCraft.TAG, "MouseX: " + event.getX() + " MouseY: " + event.getY());
            return true;
	}
}