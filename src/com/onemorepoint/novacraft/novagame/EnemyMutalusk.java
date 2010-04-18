package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.*;
import java.lang.Math;

public class EnemyMutalusk extends EnemyShip
{	
	NovaSprite spriteF1, spriteF2, spriteF3, spriteF4;
	float lastFrame;
	
	public EnemyMutalusk(GL10 _gl, PlayerShip p)
	{
		super(_gl, p);

		health = 500;
		lastFrame = 0;

		spriteF1 = new NovaSprite(gl);
		spriteF1.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.mutalusk_1));
		
		spriteF2 = new NovaSprite(gl);
		spriteF2.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.mutalusk_2));
		
		spriteF3 = new NovaSprite(gl);
		spriteF3.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.mutalusk_3));
		
		spriteF4 = new NovaSprite(gl);
		spriteF4.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.mutalusk_4));
		
		sprite = spriteF1;
		
		positionY = 854 + sprite.GetHeight()/2;
		positionX = (float)Math.random()*480.0f;
		velocityY = -75;
		velocityX = (float)(Math.random()*40 - 20);
	}

    @Override
	public void Update(float elapsedTime)
	{
        super.Update(elapsedTime);
        
        if(positionY < 350)
        {
        	firstMovement = false;
        	velocityY = (float)(Math.random()*50 + 50);
        }
        else if(positionY > 700 && !firstMovement)
        	velocityY = (float)(Math.random()*-50 - 50);
        
        if(positionX < player.positionX - 64)
        	velocityX = (float)(Math.random()*100 + 50);
        else if(positionX > player.positionX + 64)
        	velocityX = (float)(Math.random()*-100 - 50);
        	
        if(timeAlive - lastFrame > 0.25)
        {
        	lastFrame = timeAlive;
        	if(sprite == spriteF1)
        		sprite = spriteF2;
        	else if(sprite == spriteF2)
        		sprite = spriteF3;
        	else if(sprite == spriteF3)
        		sprite = spriteF4;
        	else if(sprite == spriteF4)
        		sprite = spriteF1;
        }
        
   	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime);
	}
}
