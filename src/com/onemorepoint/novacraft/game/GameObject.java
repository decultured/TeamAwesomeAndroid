package com.onemorepoint.novacraft.game;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import android.graphics.Point;

public class GameObject
{
	public float positionX;
    public float positionY;
    public float previousPositionX;
    public float previousPositionY;
    public float velocityX;
    public float velocityY;
    public float accelerationX;
    public float accelerationY;
    
    public float forwardX;
    public float forwardY;
	
	public boolean visible;
	public boolean active;
	
	public float timeAlive;

	public GL10 gl;
	
	public NovaSprite sprite;

	public GameObject(GL10 _gl)
	{
		gl = _gl;
		sprite = new NovaSprite(_gl);
		visible = true;
        active = true;
	}
	
	public void Translate(float x, float y)
    {
        positionX += x;
        positionY += y;
    }
    
    public void AddVelocity(float x, float y)
    {
        velocityX += x;
        velocityY += y;
    }
    
    public void AddAcceleration(float x, float y)
    {
        accelerationX += x;
        accelerationY += y;
    }
	
	public void Update(float elapsedTime)
	{
		timeAlive += elapsedTime;

        previousPositionX = positionX;
        previousPositionY = positionY;

        AddVelocity(accelerationX * elapsedTime, accelerationY * elapsedTime);
        Translate(velocityX * elapsedTime, velocityY * elapsedTime);
	}
	
	public void Render(float elapsedTime)
	{
		if(!visible)
			return;
			
		sprite.Render(positionX,positionY);	
	}
}
