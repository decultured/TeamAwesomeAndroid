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
import com.onemorepoint.geom.Rectangle;

public class NovaImage
{
	private static final float texCoordArray_Full[] = {
		0.0f, 1.0f,  // 0, Top Left
		0.0f, 0.0f,  // 1, Bottom Left
		1.0f, 0.0f,  // 2, Bottom Right
		1.0f, 1.0f,  // 3, Top Right
	};
	
	private int resourceID;
	private int texID;
	int width;
	int height;
	
	private FloatBuffer texCoordBuffer;
	private FloatBuffer texCoordBuffers[];
	
	private int segmentWidth;
	private int segmentHeight;
	
	public NovaImage()
	{
		width = height = 0;
		segmentWidth = segmentHeight = 0;
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
		
		NovaRenderer.gl.glGetError();
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
		
		texCoordBuffers = null;
		
		ByteBuffer tbb = ByteBuffer.allocateDirect(texCoordArray_Full.length * 4);
		tbb.order(ByteOrder.nativeOrder());
		texCoordBuffer = tbb.asFloatBuffer();
		texCoordBuffer.put(texCoordArray_Full);
		texCoordBuffer.position(0);
		
		return true;
	}

	
	public void FreeTexture()
	{
		if(texID != 0)
		{
			int [] texIDA = new int[1];
			texIDA[0] = texID;
			NovaRenderer.gl.glDeleteTextures(1, texIDA, 0);
			texID = 0;
			Log.v(NovaCraft.TAG, "Freed texture with resource:" + resourceID);
		}
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
		if(texCoordBuffers != null) {
			return segmentWidth;
		} else {
			return width;
		}
	}
	
	int GetHeight()
	{
		if(texCoordBuffers != null) {
			return segmentHeight;
		} else {
			return height;
		}
	}
	
	public void setSegmentSize(int _w, int _h) {
		segmentWidth = _w;
		segmentHeight = _h;
		
		Segment();
	}
	
	private void Segment() {
		if(texCoordBuffers != null || segmentWidth == 0 || segmentHeight == 0 || segmentWidth > width || segmentHeight > height) {
			return;
		}
		
		Log.v(NovaCraft.TAG, "NovaImage::Segment() started, sw="+ segmentWidth +", sh="+segmentHeight +", w="+ width +", h="+height);
		
		int segmentsX = (width / segmentWidth);
		int segmentsY = (height / segmentHeight);
		int numSegments = segmentsX * segmentsY;
		float segNormWidth = (float)segmentWidth / (float)width;
		float segNormHeight = (float)segmentHeight / (float)height;
		Log.v(NovaCraft.TAG, "NovaImage::Segment() segments nw="+ segNormWidth +", nh="+segNormHeight);
		
		// segments = new Rectangle[numSegments];
		texCoordBuffers = new FloatBuffer[numSegments];
		
		int index = 0;
		for(int r = 0; r < segmentsY; r++) {
			for(int c = 0; c < segmentsX; c++) {
				// segments[index] = new Rectangle(c * segmentWidth, r * segmentHeight, segmentWidth, segmentHeight);
				
				// generate this segments texture coordinates
				float offsetX = (float)c * segNormWidth;
				float offsetY = (float)r * segNormHeight;
				
				Log.v(NovaCraft.TAG, "NovaImage::Segment() segments offsetX="+ offsetX +", offsetY="+offsetY);
				
				float texCoordArray[] = {
					      offsetX, offsetY + segNormHeight,  // 0, Top Left
					      offsetX, offsetY,  // 1, Bottom Left
					      offsetX + segNormWidth, offsetY,  // 2, Bottom Right
					      offsetX + segNormWidth, offsetY + segNormHeight,  // 3, Top Right
				};
				
				ByteBuffer tbb = ByteBuffer.allocateDirect(texCoordArray.length * 4);
				tbb.order(ByteOrder.nativeOrder());
				FloatBuffer texCoordBuffer = tbb.asFloatBuffer();
				texCoordBuffer.put(texCoordArray);
				texCoordBuffer.position(0);
				
				texCoordBuffers[index] = texCoordBuffer;
				
				index += 1;
			}
		}
		
		Log.v(NovaCraft.TAG, "NovaImage::Segment() finished, len = "+ index);
	}
	
	public FloatBuffer getTexCoords() {
		return getTexCoords(0);
	}
	
	public FloatBuffer getTexCoords(int segment) {
		if(texCoordBuffers == null) {
			return texCoordBuffer;
		} else {
			return texCoordBuffers[segment];
		}
	}
}