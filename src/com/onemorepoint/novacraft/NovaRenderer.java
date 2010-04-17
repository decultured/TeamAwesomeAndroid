package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.app.Activity;

public class NovaRenderer implements GLSurfaceView.Renderer
{
	float colorR, colorG, colorB;
	
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        colorR = 1;
        colorG = 1;
        colorB = 1;
    }

    public void onSurfaceChanged(GL10 gl, int w, int h)
    {
        gl.glViewport(0, 0, w, h);
    }
    
    public void setColor(float r, float g, float b)
    {
    	colorR = r;
    	colorG = g;
    	colorB = b;
    }

    public void onDrawFrame(GL10 gl)
    {
    	gl.glClearColor(colorR, colorG, colorB, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    }
}