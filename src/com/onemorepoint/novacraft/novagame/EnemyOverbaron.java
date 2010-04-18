package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.*;
import java.lang.Math;

public class EnemyOverbaron extends EnemyShip
{	
	public EnemyOverbaron(GL10 _gl, PlayerShip p)
	{
		super(_gl, p);
		
		health = 500;
		sprite.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.overbaron_1));
		positionY = 854 + sprite.GetHeight()/2;
		positionX = (float)Math.random()*480.0f;
		velocityY = -50;
		velocityX = (float)(Math.random()*40 - 20);
	}

    @Override
	public void Update(float elapsedTime)
	{
        super.Update(elapsedTime);
        
        if(positionY < 500)
        {
        	velocityY = (float)(Math.random()*25 + 25);
        	firstMovement = false;
        }
        else if(positionY > 750 && !firstMovement)
        	velocityY = (float)(Math.random()*-25 - 25);
        
        if(positionX < 68)
        	velocityX = (float)(Math.random()*20 + 20);
        else if(positionX > 410)
        	velocityX = (float)(Math.random()*-20 - 20);
        
   	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime);
	}
}
