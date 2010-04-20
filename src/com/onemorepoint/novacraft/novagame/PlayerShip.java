package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.novagame.*;
import android.util.Log;

public class PlayerShip extends GameObject
{	
	public static int SHIP_SCOOT = 0;
	public static int SHIP_WRATH = 1;
	
	public static int SHIP_FRAME_MOVE_LEFT = 0;
	public static int SHIP_FRAME_NORMAL = 1;
	public static int SHIP_FRAME_MOVE_RIGHT = 2;
	
	
	ProjectileManager projManager;
	
	private float projectilesToFire;
	private float projectilesPerSecond = 2.0f;
	private float maxProjectilesPerSecond = 6.0f;
	private float projectilePower = 1.0f;

	private int alternate = 0;
	private int currentFrame = SHIP_FRAME_NORMAL;
		
	float health;
	
	public PlayerShip(ProjectileManager _projManager)
	{
		super();
		
		projManager = _projManager;
		
		Reset();
	}
	
	public void Powerup(boolean increase)
	{
		if (increase && projectilesPerSecond <= maxProjectilesPerSecond - projectilePower) {
			projectilesPerSecond += projectilePower;
		} else if (!increase && projectilesPerSecond >= 2.0f + projectilePower) {
			projectilesPerSecond -= projectilePower;
		}
	}

    @Override
	public void Update(float elapsedTime)
	{
		if (Input.isDown)
		{
			if(Input.xPos < positionX - 24 && positionX > 32.0f)
			{
				velocityX = -300.0f;
				currentFrame = SHIP_FRAME_MOVE_LEFT;
			}
			else if(Input.xPos > positionX + 24 && positionX < 448.0f)
			{
				velocityX = 300.0f;
				currentFrame = SHIP_FRAME_MOVE_RIGHT;
			} else velocityX = 0.0f;
			
			if(Input.yPos < positionY - 32 && positionY > 32.0f) {
				velocityY = -200.0f;
			} else if(Input.yPos > positionY + 32 && positionY < 300.0f) {
				velocityY = 200.0f;
			} else {
				velocityY = 0.0f;
			}
		}
		else
		{
			currentFrame = SHIP_FRAME_NORMAL;
			velocityX = 0;
			velocityY = 0;
		}

		projectilesToFire += elapsedTime * projectilesPerSecond;
		while (projectilesToFire > 1.0f) {
			if (projectilesPerSecond < 4.0f) {
				projManager.AddProjectile(Projectile.PLASMA, positionX, positionY, 0.0f, 1.0f, 300.0f, 8.0f, true);
			} else if (projectilesPerSecond >= 4.0f && projectilesPerSecond < 5.0f) {
				if (alternate > 1)
					alternate = 0;
				if (alternate == 0)
					projManager.AddProjectile(Projectile.PLASMA, positionX - 25.0f, positionY, 0.0f, 1.0f, 300.0f, 8.0f, true);
				else if (alternate == 1)
					projManager.AddProjectile(Projectile.PLASMA, positionX + 25.0f, positionY, 0.0f, 1.0f, 300.0f, 8.0f, true);
				alternate++;
			} else if (projectilesPerSecond >= 5.0f) {
				if (alternate > 2)
					alternate = 0;
				if (alternate == 0)
					projManager.AddProjectile(Projectile.PLASMA, positionX - 25.0f, positionY, 0.0f, 1.0f, 300.0f, 8.0f, true);
				else if (alternate == 1)
					projManager.AddProjectile(Projectile.PLASMA, positionX + 25.0f, positionY, 0.0f, 1.0f, 300.0f, 8.0f, true);
				else if (alternate == 2)
					projManager.AddProjectile(Projectile.PLASMA, positionX, positionY, 0.0f, 1.0f, 300.0f, 8.0f, true);
				alternate++;
			}
			
			projectilesToFire -= 1.0f;	
		}
		
		if(positionX <= 0)
		{
			positionX = 0;
			if(velocityX < 0)
				velocityX = 0;
		}
		else if(positionX >= 480)
		{
			positionX = 480;
			if(velocityX > 0)
				velocityX = 0;
		}
		
		super.Update(elapsedTime);
	}
	
    @Override
	public void Render(float elapsedTime)
	{
        //super.Render(elapsedTime);
		
		sprite.Render(positionX, positionY, currentFrame);
	}
	
	public boolean Hurt(float damage)
	{
		if(damage < 0)
			return false;
			
		health -= damage;
		Log.v(NovaCraft.TAG, "HEALTH: " + health);
		
		if(health <= 0)
			return true;
		return false;
	}
	
	public void SelectShip(int shipID) {
		if(shipID == SHIP_SCOOT) {
			sprite.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.player_ship_scoot, 64, 128));
		} else if(shipID == SHIP_WRATH) {
			sprite.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.player_ship_wrath, 64, 64));
		}
	}
	
	public boolean isAlive() {
		if(health <= 0)
			return false;
		return true;
	}
	
	@Override
	public void Reset() {
		health = 100.0f;
		currentFrame = SHIP_FRAME_NORMAL;
		projectilesPerSecond = 3.0f;
		
		positionX = 240.0f;
		positionY = 140.0f;
	}
}
