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
	
	public NovaSoundManager sound;

	public Game()
	{
		elapsedTime = 0;
		thisTime = System.nanoTime();
		sound = new NovaSoundManager();
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
	
	public void ChangeGameState(int _state) {
		
	}
	
	public void ChangeLevel() {
		
	}
	
	public void ResetLevel() {
		
	}
	
	public void ResetGame() {
		
	}
}
