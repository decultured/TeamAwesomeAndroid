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
	private int resourceID;
	private int texID;
	int width;
	int height;
	
	public NovaImage()
	{
		width = height = 0;
		texID = 0;
		resourceID = 0;
	}
			
	public Boolean LoadImage(int resourceId, GL10 gl)
	{
		Bitmap partyOBits = BitmapFactory.decodeResource(NovaCraft.instance.getResources(), resourceId);
		if(partyOBits == null)
		{
			Log.e(NovaCraft.TAG, "Failed to load image:" + resourceId);
			return false;
		}
		
		resourceID = resourceId;
		
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
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		width = partyOBits.getWidth();
		height = partyOBits.getHeight();
		
		return true;
	}
	
	int GetTextureID()
	{
		return texID;
	}
	
	int GetResourceID()
	{
		return resourceID;
	}
	
	int GetWidth()
	{
		return width;
	}
	
	int GetHeight()
	{
		return height;
	}
}