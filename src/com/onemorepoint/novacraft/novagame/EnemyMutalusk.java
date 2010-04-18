package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.*;
import java.lang.Math;

public class EnemyMutalusk extends EnemyShip
{	
	NovaSprite spriteF1, spriteF2, spriteF3, spriteF4;
	float lastFrame;
	private float projectilesToFire;
	private float projectilesPerSecond;
	
	public EnemyMutalusk(PlayerShip p, ProjectileManager _projManager)
	{
		super(p, _projManager);

		health = 500;
		lastFrame = 0;
		projectilesPerSecond = 1.0f;

		spriteF1 = new NovaSprite();
		spriteF1.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.mutalusk_1));
		
		spriteF2 = new NovaSprite();
		spriteF2.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.mutalusk_2));
		
		spriteF3 = new NovaSprite();
		spriteF3.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.mutalusk_3));
		
		spriteF4 = new NovaSprite();
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
        
        projectilesToFire += elapsedTime * projectilesPerSecond;
		while (projectilesToFire > 1.0f) {
			projManager.AddProjectile(Projectile.MUTALISK_BALLS, positionX, positionY, 0.0f, -1.0f, 250.0f, 7.0f, false);
			projectilesToFire -= 1.0f;	
		}
   	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime);
	}
}
