package com.onemorepoint.novacraft.game;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import android.graphics.Point;

public class GameObject
{
	protected float positionX;
    protected float positionY;
    protected float previousPositionX;
    protected float previousPositionY;
    protected float velocityX;
    protected float velocityY;
    protected float accelerationX;
    protected float accelerationY;
    
    protected float forwardX;
    protected float forwardY;
	
	private boolean visible;
	private boolean active;

	protected GL10 gl;
	
	protected NovaSprite sprite;

	public GameObject(GL10 _gl)
	{
		gl = _gl;
		sprite = new NovaSprite(_gl);
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
		
	}
	
	public void Render(float elapsedTime)
	{
		sprite.Render(positionX,positionY);	
	}
}
