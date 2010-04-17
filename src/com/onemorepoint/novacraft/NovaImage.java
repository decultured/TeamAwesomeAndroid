package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.IntBuffer;
import java.nio.ByteOrder;
import android.graphics.Color;

class NovaImage
{
	private static final float texCoordArray[] = {
		      0.0f, 1.0f,  // 0, Top Left
		      0.0f, 0.0f,  // 1, Bottom Left
		      1.0f, 0.0f,  // 2, Bottom Right
		      1.0f, 1.0f,  // 3, Top Right
	};
	private static final short[] indices = { 0, 1, 2, 0, 2, 3 };
		
	private int texID;
	private GL10 gl;
	FloatBuffer vertBuffer;
	FloatBuffer texCoordBuffer;
	ShortBuffer indexBuffer;
	float width;
	float height;
	
	public NovaImage()
	{
		texID = 0;
		gl = null;
		width = height = 0;
	}
		
	public NovaImage(GL10 _gl)
	{
		texID = 0;
		gl = _gl;
		width = height = 0;
	}
	
	public void SetGL(GL10 _gl)
	{
		gl = _gl;
	}
	
	private void _BuildVerts()
	{
		float [] vertices = new float[8];
		
		// Top left
		vertices[0] = 0.0f;
		vertices[1] = 0.0f;
		
		// Bottom left
		vertices[2] = 0.0f;
		vertices[3] = height;
		
		// Bottom right
		vertices[4] = width;
		vertices[5] = height;
		
		// Top right
		vertices[6]  = width;
		vertices[7] = 0.0f;
		
		// Setup vertex buffer
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertBuffer = vbb.asFloatBuffer();
		vertBuffer.put(vertices);
		vertBuffer.position(0);
	}
	
	// Passing <= 0 for _w or _h will scale proportional
	void SetSize(float _w, float _h)
	{
		float oldW = width;
		float oldH = height;
		
		if(_w > 0)
		{
			width = _w;
			if(_h > 0)
				height = _h;
			else 
				height = (width/oldH)*height;
		}
		else if(_h > 0)
		{
			height = _h;
			width = (width/oldW)*width;
		}
		else return;
		
		_BuildVerts();
	}
	
	public Boolean LoadImage(int resourceId)
	{
		Bitmap partyOBits = BitmapFactory.decodeResource(NovaCraft.instance.getResources(), resourceId);
		if(partyOBits == null)
		{
			Log.e(NovaCraft.TAG, "Failed to load image:" + resourceId);
			return false;
		}
		
		if(partyOBits.hasAlpha())
			Log.v(NovaCraft.TAG, "HAS ALPHA");
		else Log.v(NovaCraft.TAG, "NO ALPHA");
		
		int bpp = partyOBits.hasAlpha() ? 4 : 3;
		ByteBuffer bb = ByteBuffer.allocateDirect(partyOBits.getHeight()*partyOBits.getWidth()*bpp);
		bb.order(ByteOrder.nativeOrder());
		partyOBits.copyPixelsToBuffer(bb);
		bb.position(0);
		
		gl.glPixelStorei(gl.GL_UNPACK_ALIGNMENT, 1);
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID);
		
		int [] texIDA = new int[1];
		gl.glGenTextures(1, texIDA, 0);
		texID = texIDA[0];
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID);
		
		gl.glTexImage2D(gl.GL_TEXTURE_2D, 0, partyOBits.hasAlpha() ? gl.GL_RGBA : gl.GL_RGB, partyOBits.getWidth(), partyOBits.getHeight(), 0, partyOBits.hasAlpha() ? gl.GL_RGBA : gl.GL_RGB, partyOBits.hasAlpha() ? gl.GL_UNSIGNED_BYTE : gl.GL_UNSIGNED_SHORT_5_6_5, bb);
		if(gl.glGetError() > 0)
		{
			Log.e(NovaCraft.TAG, "Failed to load texture:" + resourceId);
			return false;
		}
		else Log.v(NovaCraft.TAG, "Texture loaded:" + resourceId + " with GL ID:" + texID);
		
		width = partyOBits.getWidth();
		height = partyOBits.getHeight();
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		_BuildVerts();
		
		// Setup tex coord buffer
		ByteBuffer tbb = ByteBuffer.allocateDirect(texCoordArray.length * 4);
		tbb.order(ByteOrder.nativeOrder());
		texCoordBuffer = tbb.asFloatBuffer();
		texCoordBuffer.put(texCoordArray);
		texCoordBuffer.position(0);
		
		// Setup index buffer
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
		return true;
	}
	
	public void Render(float _x, float _y)
	{
		gl.glPushMatrix();
		gl.glTranslatef(_x-(width*0.5f), _y-(height*0.5f), 0);
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertBuffer);
		gl.glTexCoordPointer(2, gl.GL_FLOAT, 0, texCoordBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		gl.glPopMatrix();
	}
	public void RenderZ(float _x, float _y)
	{
		gl.glPushMatrix();
		gl.glTranslatef(_x, _y, 0);
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertBuffer);
		gl.glTexCoordPointer(2, gl.GL_FLOAT, 0, texCoordBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		gl.glPopMatrix();
	}
}