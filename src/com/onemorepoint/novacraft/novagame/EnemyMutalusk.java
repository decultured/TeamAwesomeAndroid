package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.*;
import java.lang.Math;

public class EnemyMutalusk extends EnemyShip
{	
	public float lastFrame;
	public int frame = 0;
	public float frameLength = 0.25f;
	
	private float projectilesToFire;
	private float projectilesPerSecond;
	
	public EnemyMutalusk(PlayerShip p, ProjectileManager _projManager)
	{
		super(p, _projManager);

		health = 100;
		lastFrame = 0;
		projectilesPerSecond = 0.667f;
		
		sprite.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.mutalusk, 64, 64));
		
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
			
		if(timeAlive - lastFrame > frameLength)
		{
			lastFrame = timeAlive;
			frame++;
		}
		
		if(frame > 3) {
			frame = 0;
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
		super.Render(elapsedTime, frame);
	}
}
