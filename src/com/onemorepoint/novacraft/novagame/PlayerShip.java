package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.novagame.*;

public class PlayerShip extends GameObject
{	
	ProjectileManager projManager;
	
	private float projectilesToFire;
	private float projectilesPerSecond;

	NovaSprite spriteL;
	NovaSprite spriteR;
	NovaSprite currentSprite;
	
	public PlayerShip(GL10 _gl, ProjectileManager _projManager)
	{
		super(_gl);

		projManager = _projManager;
		projectilesPerSecond = 3.0f;

		sprite.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.player_ship_wrath_normal));
		
		spriteL = new NovaSprite(gl);
		spriteL.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.player_ship_wrath_left));
		
		spriteR = new NovaSprite(gl);
		spriteR.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.player_ship_wrath_right));
		
		currentSprite = sprite;
		
		positionX = 240.0f;
		positionY = 140.0f;
	}

    @Override
	public void Update(float elapsedTime)
	{
		if (Input.isDown)
		{
			if(Input.xPos < 240.0f)
			{
				velocityX = -200.0f;
				currentSprite = spriteL;
			}
			else if(Input.xPos > 240.0f)
			{
				velocityX = 200.0f;
				currentSprite = spriteR;
			}
		}
		else
		{
			currentSprite = sprite;
			velocityX = 0;
		}

		projectilesToFire += elapsedTime * projectilesPerSecond;
		while (projectilesToFire > 1.0f) {
			projManager.AddProjectile(positionX, positionY, 0.0f, 1.0f, 300.0f, 8.0f, true);
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
		
		currentSprite.Render(positionX, positionY);
	}
}
