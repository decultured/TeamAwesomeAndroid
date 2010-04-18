package com.onemorepoint.novacraft.game;

import android.util.Log;
import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import java.lang.System;

public class Game
{
	protected float elapsedTime;
	protected float totalTime;
	
	private long lastTime;
	private long thisTime;

	protected GL10 gl;

	public Game(GL10 _gl)
	{
		gl = _gl;
		elapsedTime = 0;
		thisTime = System.nanoTime();
	}

	private void UpdateTime()
	{
		lastTime = thisTime;
		thisTime = System.nanoTime();
		elapsedTime = (float)((thisTime - lastTime) * 0.000000001);
		if(elapsedTime > 0.25f)
			elapsedTime = 0.25f;
			
		totalTime += elapsedTime;
	}
		
	public void Initialize()
	{
		
	}
	
	public void Update()
	{
		UpdateTime();

	}
	
	public void Render()
	{
		
	}
}
