package com.onemorepoint.novacraft.game;

import android.graphics.Point;

public class GameObject
{
	private Point position;
	private Point previousPosition;
	private Point velocity;
	private Point acceleration;
	
	private Point forward;
	
	private float width;
	private float height;
	
	private boolean visible;
	private boolean active;

	public GameObject()
	{
		
	}
	
	public void Translate(Point pos)
	{
		position.x += pos.x;
		position.y += pos.y;
	}
	
	public void Translate(float x, float y)
	{
		position.x += x;
		position.y += y;
	}
	
	public void AddVelocity(Point vel)
	{
		velocity.x += vel.x;
		velocity.y += vel.y;
	}
	
	public void AddVelocity(float x, float y)
	{
		velocity.x += x;
		velocity.y += y;
	}
	
	public void AddAcceleration(Point acc)
	{
		acceleration.x += acc.x;
		acceleration.y += acc.y;
	}

	public void AddAcceleration(float x, float y)
	{
		acceleration.x += x;
		acceleration.y += y;
	}
	
	public void Update(float elapsedTime)
	{
		
	}
	
	public void Render(float elapsedTime)
	{
		
	}
}
