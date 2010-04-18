package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.game.*;

public class EnemyShip extends GameObject
{	
	float health;
	boolean firstMovement;
	
	public EnemyShip(GL10 _gl)
	{
		super(_gl);
		
		positionX = 240.0f;
		positionY = 854.0f;
		health = 1;
		firstMovement = true;
	}

    @Override
	public void Update(float elapsedTime)
	{
        super.Update(elapsedTime);
        
        if(health <= 0)
        {
        	// Dead
        }
	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime);
		
	}
	
	// Returns true if shit died
	public boolean Hurt(float damage)
	{
		if(damage > 0)
			return false;
			
		health -= damage;
		if(health <= 0)
			return true;
		return false;
	}
}
