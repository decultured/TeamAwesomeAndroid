package com.onemorepoint.novacraft.game;

import java.lang.System;

public class Game
{
	protected double elapsedTime;
	
	private long lastTime;
	private long thisTime;	

	public Game()
	{
		elapsedTime = 0;
		thisTime = System.nanoTime();
	}

	private void UpdateTime()
	{
		lastTime = thisTime;
		thisTime = System.nanoTime();
		elapsedTime = (double)(thisTime - lastTime) * 0.000000001;
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
