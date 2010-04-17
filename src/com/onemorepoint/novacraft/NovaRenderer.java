package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.onemorepoint.novacraft.novagame.*;

public class NovaRenderer implements GLSurfaceView.Renderer
{
	float colorR, colorG, colorB;
	NovaSprite tstr;
	private NovaGame gameObject;
	private NovaBackground background;

    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
    	// DO THIS FIRST!!
    	NovaImageManager im = new NovaImageManager(gl);
    	
        colorR = 1;
        colorG = 1;
        colorB = 1;
                
        tstr = new NovaSprite(gl);
        tstr.UseImage(im.LoadImage(R.raw.player_ship));

		gameObject = new NovaGame();
		gameObject.Initialize();
        
        background = new NovaBackground();
        background.Load(gl, 1);
    }

    public void onSurfaceChanged(GL10 gl, int w, int h)
    {
        gl.glViewport(0,0,320,570);
		gl.glMatrixMode(gl.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0.0f,480.0f,0.0f,854.0f,1.0f,-1.0f);
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glFrontFace(gl.GL_CCW);
		gl.glDisable(gl.GL_CULL_FACE);
		gl.glDisable(gl.GL_DEPTH_TEST);
		gl.glHint(gl.GL_PERSPECTIVE_CORRECTION_HINT, gl.GL_FASTEST);
		gl.glEnable(gl.GL_BLEND);
		gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(gl.GL_ALPHA_TEST);
		gl.glEnable(gl.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
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
        
     	background.Render();
     	background.AddOffset(6.0f);
 
		//gameObject.Update();
		//gameObject.Render();
       
        tstr.Render(240.0f, 100.0f);
    }
}