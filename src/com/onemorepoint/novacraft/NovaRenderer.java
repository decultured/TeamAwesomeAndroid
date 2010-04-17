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
	private NovaGame gameObject;

    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
    	// DO THIS FIRST!!
    	NovaImageManager im = new NovaImageManager(gl);
        
		gameObject = new NovaGame(gl);
		gameObject.Initialize();
    }

    public void onSurfaceChanged(GL10 gl, int w, int h)
    {
    	NovaImageManager.GetInstance().ClearImageList();
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
    

    public void onDrawFrame(GL10 gl)
    {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
 
		gameObject.Update();
		gameObject.Render();
    }
}