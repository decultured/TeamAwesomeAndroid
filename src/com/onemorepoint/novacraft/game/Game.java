package com.onemorepoint.novacraft.game;

import android.util.Log;
import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import java.lang.System;

public class Game
{
	protected float elapsedTime;
	
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
