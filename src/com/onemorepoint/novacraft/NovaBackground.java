package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.util.Log;
import android.graphics.Color;
import java.lang.Math;

public class NovaBackground
{
	NovaSprite images[];
	float offset = 0;
	float screenW = 480.0f;
	int baseIndex = 0;
	int totalImages = 0;
	
	public NovaBackground()
	{
		
	}
	
	private int _AdjustOffset(int idx)
	{
		while(idx >= totalImages)
			idx -= totalImages;
			
		return idx;
	}
	
	public boolean Load(GL10 _gl, int setID)
	{
		NovaImageManager im = NovaImageManager.GetInstance();
		
		if(setID == 0)
		{
			totalImages = 4;
			
			images = new NovaSprite[totalImages];
			for(int i=0; i<totalImages; i++)
			{
				NovaSprite ni = new NovaSprite(_gl);
				images[i] = ni;
			}
			if(!images[0].UseImage(im.LoadImage(R.raw.bg_canyon_04))) return false;
			if(!images[1].UseImage(im.LoadImage(R.raw.bg_canyon_03))) return false;
			if(!images[2].UseImage(im.LoadImage(R.raw.bg_canyon_02))) return false;
			if(!images[3].UseImage(im.LoadImage(R.raw.bg_canyon_01))) return false;
		}
		else if(setID == 1)
		{
			totalImages = 8;
			
			images = new NovaSprite[totalImages];
			for(int i=0; i<totalImages; i++)
			{
				NovaSprite ni = new NovaSprite(_gl);
				images[i] = ni;
			}
			if(!images[0].UseImage(im.LoadImage(R.raw.space_08))) return false;
			if(!images[1].UseImage(im.LoadImage(R.raw.space_07))) return false;
			if(!images[2].UseImage(im.LoadImage(R.raw.space_06))) return false;
			if(!images[3].UseImage(im.LoadImage(R.raw.space_05))) return false;
			if(!images[4].UseImage(im.LoadImage(R.raw.space_04))) return false;
			if(!images[5].UseImage(im.LoadImage(R.raw.space_03))) return false;
			if(!images[6].UseImage(im.LoadImage(R.raw.space_02))) return false;
			if(!images[7].UseImage(im.LoadImage(R.raw.space_01))) return false;
		}
		
		for(int i=0; i<totalImages; i++)
			images[i].SetSize(screenW, screenW);
		
		if(totalImages > 0)
			return true;
			
		return false;
	}
	
	public void AddOffset(float o)
	{
		offset -= o;
		
		if(offset < -screenW)
		{
			offset += screenW;
			
			baseIndex++;
			if(baseIndex >= totalImages)
				baseIndex -= totalImages;
		}
	}
	
	public void Render()
	{
		images[_AdjustOffset(baseIndex)].RenderZ(0.0f,(float)Math.floor(offset));
		images[_AdjustOffset(baseIndex+1)].RenderZ(0.0f,(float)Math.floor(offset+screenW));
		images[_AdjustOffset(baseIndex+2)].RenderZ(0.0f,(float)Math.floor(offset+screenW*2));
	}
}
