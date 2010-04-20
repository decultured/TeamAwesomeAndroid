package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.*;
import java.lang.Math;

public class EnemyScourb extends EnemyShip
{	
	float lastFrame;
	public int frame = 0;
	public float frameLength = 0.25f;
	
	public EnemyScourb(PlayerShip p, ProjectileManager _projManager)
	{
		super(p, _projManager);

		health = 50;
		lastFrame = 0;

		sprite.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.scorb, 32, 32));
		
		positionY = 854 + sprite.GetHeight()/2;
		positionX = (float)Math.random()*480.0f;
		velocityY = -200.0f - (float)Math.random()*40.0f;
		velocityX = (float)(Math.random()*300 - 150);
	}

    @Override
	public void Update(float elapsedTime)
	{
        super.Update(elapsedTime);
        
        if(positionY < -64)
        {
        	health = -1;
        }
        
        if(positionX < player.positionX - 64)
        	velocityX = (float)(Math.random()*100 + 50);
        else if(positionX > player.positionX + 64)
        	velocityX = (float)(Math.random()*-100 - 50);
        	
        if(timeAlive - lastFrame > frameLength)
		{
			lastFrame = timeAlive;
			frame++;
		}
		
		if(frame > 3) {
			frame = 0;
		}
        
   	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime, frame);
	}
}
