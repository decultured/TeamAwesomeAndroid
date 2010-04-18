package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.*;
import java.lang.Math;

public class EnemyOverbaron extends EnemyShip
{	
	private float projectilesToFire;
	private float projectilesPerSecond;
	
	public EnemyOverbaron(GL10 _gl, PlayerShip p, ProjectileManager _projManager)
	{
		super(_gl, p, _projManager);
		
		projectilesPerSecond = 0.5f;
		
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
        	
        projectilesToFire += elapsedTime * projectilesPerSecond;
		while (projectilesToFire > 1.0f) {
			projManager.AddProjectile(positionX, positionY, 0.283662185f, -0.958924275f, 150.0f, 7.0f, false);
			projManager.AddProjectile(positionX, positionY, 0.0f, -1.0f, 150.0f, 7.0f, false);
			projManager.AddProjectile(positionX, positionY, -0.283662185f, -0.958924275f, 150.0f, 7.0f, false);
			projectilesToFire -= 1.0f;	
		}
        
   	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime);
	}
}
