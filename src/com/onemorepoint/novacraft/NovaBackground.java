package com.onemorepoint.novacraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.util.Log;
import android.graphics.Color;

class NovaBackground
{
	NovaImage images[];
	float offset = 0;
	float screenW = 480.0f;
	
	public NovaBackground()
	{
		
	}
	
	public boolean Load(GL10 _gl, int setID)
	{
		if(setID == 0)
		{
			images = new NovaImage[4];
			for(int i=0; i<4; i++)
			{
				NovaImage ni = new NovaImage(_gl);
				images[i] = ni;
			}
			if(!images[0].LoadImage(R.raw.bg_canyon_04)) return false;
			if(!images[1].LoadImage(R.raw.bg_canyon_03)) return false;
			if(!images[2].LoadImage(R.raw.bg_canyon_02)) return false;
			if(!images[3].LoadImage(R.raw.bg_canyon_01)) return false;
			
			for(int i=0; i<4; i++)
				images[i].SetSize(screenW, screenW);

			return true;
		}
		
		return false;
	}
	
	public void Render()
	{
		images[0].RenderZ(0,0);
		images[1].RenderZ(0,screenW);
	}
}
