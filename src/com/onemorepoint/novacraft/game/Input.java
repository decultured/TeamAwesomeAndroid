package com.onemorepoint.novacraft.game;

public class Input
{
	public static float xPos;
	public static float yPos;
	public static boolean isDown;
	
	public Input()
	{
	}

	public static void SetInput(boolean down, float x, float y)
	{
		isDown = down;
		xPos = (x / 320.0f) * 480.0f;
		yPos = ((570.0f - y)/ 570.0f) * 854.0f;
	}	
}
