package com.onemorepoint.novacraft.game;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;

public class GameObject
{
	public float positionX = 0.0f;
    public float positionY = 0.0f;
    public float previousPositionX = 0.0f;
    public float previousPositionY = 0.0f;
    public float velocityX = 0.0f;
    public float velocityY = 0.0f;
    public float accelerationX = 0.0f;
    public float accelerationY = 0.0f;
    public float forwardX = 0.0f;
    public float forwardY = 1.0f;
	public float timeAlive = 0.0f;

	public boolean visible = true;
	public boolean active = true;
	
	public NovaSprite sprite;

	public GameObject()
	{
		sprite = new NovaSprite();
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
		if (!active)
			return;

		timeAlive += elapsedTime;

		previousPositionX = positionX;
		previousPositionY = positionY;

		AddVelocity(accelerationX * elapsedTime, accelerationY * elapsedTime);
		Translate(velocityX * elapsedTime, velocityY * elapsedTime);
	}

	public boolean CollidesWithPoint(GameObject _gameObject)
	{
		float halfW = sprite.width * 0.5f;
		float halfH = sprite.height * 0.5f;
		
	    if (positionY + halfH < _gameObject.positionY) return false;
	    if (positionY - halfH > _gameObject.positionY) return false;
	    if (positionX - halfW > _gameObject.positionX) return false;
	    if (positionX + halfW < _gameObject.positionX) return false;

	    return true;
	}
	
	public boolean PointCollidesWithObject(GameObject _gameObject)
	{
		float halfW = sprite.width * 0.5f;
		float halfH = sprite.height * 0.5f;
		
	    if (_gameObject.positionY + halfH < positionY) return false;
	    if (_gameObject.positionY - halfH > positionY) return false;
	    if (_gameObject.positionX - halfW > positionX) return false;
	    if (_gameObject.positionX + halfW < positionX) return false;

	    return true;
	}
	
	public void Render(float elapsedTime)
	{
		if(!visible)
			return;
			
		sprite.Render(positionX,positionY);	
	}
}
