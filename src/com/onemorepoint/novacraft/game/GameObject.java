package com.onemorepoint.novacraft.game;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;

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
	
	public float timeAlive;

	public boolean visible;
	public boolean active;

	public GL10 gl;
	
	public NovaSprite sprite;

	public GameObject(GL10 _gl)
	{
		gl = _gl;
		sprite = new NovaSprite(_gl);
		timeAlive = 0.0f;
		positionX = 0.0f;
	    positionY = 0.0f;
	    previousPositionX = 0.0f;
	    previousPositionY = 0.0f;
	    velocityX = 0.0f;
	    velocityY = 0.0f;
	    accelerationX = 0.0f;
	    accelerationY = 0.0f;
	    forwardX = 0.0f;
	    forwardY = 1.0f;
		timeAlive = 0.0f;
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
