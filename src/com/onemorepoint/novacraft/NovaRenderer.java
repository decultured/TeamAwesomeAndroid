package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class NovaRenderer implements GLSurfaceView.Renderer
{
	float colorR, colorG, colorB;
	NovaImage tstr;
	
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        colorR = 1;
        colorG = 1;
        colorB = 1;
        
        Log.v(NovaCraft.TAG, "Creating NovaImage test...");
        tstr = new NovaImage(gl);
 
        tstr.LoadImage(R.raw.player_ship);
    }

    public void onSurfaceChanged(GL10 gl, int w, int h)
    {
        //gl.glViewport(0, 0, w, h);
        
        gl.glViewport(0,0,480,854);
		gl.glMatrixMode(gl.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0.0f,480.0f,-854.0f,0.0f,0.0f,1.0f);
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glFrontFace(gl.GL_CCW);
		gl.glDisable(gl.GL_CULL_FACE);
		gl.glDisable(gl.GL_DEPTH_TEST);
		gl.glDisable(gl.GL_DITHER);
		gl.glHint(gl.GL_PERSPECTIVE_CORRECTION_HINT, gl.GL_FASTEST);
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
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(100, 100, 0);
        
        tstr.Render();
    }
}