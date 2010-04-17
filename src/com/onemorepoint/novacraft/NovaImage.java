package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.ByteOrder;

class NovaImage
{
	private static float texCoordArray[] = {
		      0.0f, 1.0f,  // 0, Top Left
		      0.0f, 0.0f,  // 1, Bottom Left
		      1.0f, 0.0f,  // 2, Bottom Right
		      1.0f, 1.0f,  // 3, Top Right
	};
	
	private final short[] indices = { 0, 1, 2, 0, 2, 3 };
		
	private int texID;
	private GL10 gl;
	FloatBuffer vertBuffer;
	FloatBuffer texCoordBuffer;
	ShortBuffer indexBuffer;
		
	public NovaImage(GL10 _gl)
	{
		texID = 0;
		gl = _gl;
	}
	
	public Boolean LoadImage(int resourceId)
	{
		Bitmap partyOBits = BitmapFactory.decodeResource(NovaCraft.instance.getResources(), resourceId);
		if(partyOBits == null)
		{
			Log.e(NovaCraft.TAG, "Failed to load image:" + resourceId);
			return false;
		}
		
		ByteBuffer pixels = ByteBuffer.allocate(partyOBits.getWidth()*partyOBits.getHeight()*4);
		partyOBits.copyPixelsToBuffer(pixels);
		
		
		int [] texIDA = new int[1];
		gl.glGenTextures(1, texIDA, 0);
		texID = texIDA[0];
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID);
		
		gl.glTexImage2D(gl.GL_TEXTURE_2D, 0, gl.GL_RGBA, partyOBits.getWidth(), partyOBits.getHeight(), 0, gl.GL_RGBA, gl.GL_UNSIGNED_BYTE, pixels);
		if(gl.glGetError() > 0)
		{
			Log.e(NovaCraft.TAG, "Failed to load texture:" + resourceId);
			return false;
		}
		else Log.v(NovaCraft.TAG, "Texture loaded:" + resourceId);
		
		float [] vertices = new float[12];
		// Top left
		vertices[0] = 0.0f;
		vertices[1] = 0.0f;
		vertices[2] = 0.0f;
		
		// Bottom left
		vertices[3] = 0.0f;
		vertices[4] = 100.0f;//(float)partyOBits.getHeight();
		vertices[5] = 0.0f;
		
		// Bottom right
		vertices[6] = 100.0f;//(float)partyOBits.getWidth();
		vertices[7] = 100.0f;//(float)partyOBits.getHeight();
		vertices[8] = 0.0f;
		
		// Top right
		vertices[9]  = 100.0f;//(float)partyOBits.getWidth();
		vertices[10] = 0.0f;
		vertices[11] = 0.0f;
		
		// Setup vertex buffer
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertBuffer = vbb.asFloatBuffer();
		vertBuffer.put(vertices);
		vertBuffer.position(0);
		
		// Setup index buffer
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
		return true;
	}
	
	public void Render()
	{
		gl.glBindTexture(gl.GL_TEXTURE_2D, 0);
		gl.glDisable(gl.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
		if(gl.glGetError() != 0)
			Log.v(NovaCraft.TAG, "GL ERROR!");
	}
}