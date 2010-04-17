package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.content.Context;

class NovaGLSurface extends GLSurfaceView
{
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
            return true;
	}

	NovaRenderer mRenderer;
}