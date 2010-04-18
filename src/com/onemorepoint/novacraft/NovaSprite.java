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

public class NovaSprite
{
	private static final float texCoordArray[] = {
		      0.0f, 1.0f,  // 0, Top Left
		      0.0f, 0.0f,  // 1, Bottom Left
		      1.0f, 0.0f,  // 2, Bottom Right
		      1.0f, 1.0f,  // 3, Top Right
	};
	private static final short[] indices = { 0, 1, 2, 0, 2, 3 };
		
	private int resourceID;
	private NovaImage img;
	private GL10 gl;
	FloatBuffer vertBuffer;
	FloatBuffer texCoordBuffer;
	ShortBuffer indexBuffer;

	public float width;
	public float height;
	float originalWidth;
	float originalHeight;
	
	public NovaSprite()
	{
		gl = null;
		width = height = 0;
		originalWidth = originalHeight = 0;
		resourceID = 0;
	}
		
	public NovaSprite(GL10 _gl)
	{
		gl = _gl;
		width = height = 0;
		originalWidth = originalHeight = 0;
		resourceID = 0;
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
	
	public Boolean UseImage(NovaImage _img)
	{	
		if(_img == null)
		{
			Log.d(NovaCraft.TAG, "NovaSprite::UseImage got null img");
			return false;
		}
			
		if(_img.GetTextureID() == 0)
		{
			Log.d(NovaCraft.TAG, "NovaSprite::UseImage got img with null texture ID");
			return false;
		}
			
		if(_img.GetWidth() < 2 || _img.GetHeight() < 2)
		{
			Log.d(NovaCraft.TAG, "NovaSprite::UseImage got img with invalid size");
			return false;
		}
			
		img = _img;
		width = originalWidth = img.GetWidth();
		height = originalHeight = img.GetHeight();
		
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
		if(img.GetTextureID() == 0)
			return;
			
		gl.glPushMatrix();
		gl.glTranslatef(_x-(width*0.5f), _y-(height*0.5f), 0);
		gl.glBindTexture(gl.GL_TEXTURE_2D, img.GetTextureID());
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertBuffer);
		gl.glTexCoordPointer(2, gl.GL_FLOAT, 0, texCoordBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		gl.glPopMatrix();
	}
	public void RenderZ(float _x, float _y)
	{
		if(img.GetTextureID() == 0)
			return;
			
		gl.glPushMatrix();
		gl.glTranslatef(_x, _y, 0);
		gl.glBindTexture(gl.GL_TEXTURE_2D, img.GetTextureID());
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertBuffer);
		gl.glTexCoordPointer(2, gl.GL_FLOAT, 0, texCoordBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		gl.glPopMatrix();
	}
	
	public float GetWidth()
	{
		return width;
	}
	public float GetHeight()
	{
		return height;
	}
}