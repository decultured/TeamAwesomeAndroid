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

public class NovaImage
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
			
	public Boolean LoadImage(int resourceId)
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
		
		
		NovaRenderer.gl.glPixelStorei(NovaRenderer.gl.GL_UNPACK_ALIGNMENT, 1);
		
		if(texID != 0)
		{
			int [] texIDA = new int[1];
			texIDA[0] = texID;
			NovaRenderer.gl.glDeleteTextures(1, texIDA, 0);
			texID = 0;
		}
		
		int [] texIDA = new int[1];
		NovaRenderer.gl.glGenTextures(1, texIDA, 0);
		texID = texIDA[0];
		NovaRenderer.gl.glBindTexture(NovaRenderer.gl.GL_TEXTURE_2D, texID);
		
		NovaRenderer.gl.glTexImage2D(NovaRenderer.gl.GL_TEXTURE_2D, 0, partyOBits.hasAlpha() ? NovaRenderer.gl.GL_RGBA : NovaRenderer.gl.GL_RGB, partyOBits.getWidth(), partyOBits.getHeight(), 0, partyOBits.hasAlpha() ? NovaRenderer.gl.GL_RGBA : NovaRenderer.gl.GL_RGB, partyOBits.hasAlpha() ? NovaRenderer.gl.GL_UNSIGNED_BYTE : NovaRenderer.gl.GL_UNSIGNED_SHORT_5_6_5, bb);
		if(NovaRenderer.gl.glGetError() > 0)
		{
			Log.e(NovaCraft.TAG, "Failed to load texture:" + resourceId);
			return false;
		}
		else Log.v(NovaCraft.TAG, "Texture loaded:" + resourceId + " with GL ID:" + texID);
		
		NovaRenderer.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		NovaRenderer.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
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