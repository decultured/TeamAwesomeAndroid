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

	public PlayerShip(GL10 _gl, ProjectileManager _projManager)
	{
		super(_gl);

		projManager = _projManager;
		projectilesPerSecond = 3.0f;
		
		sprite.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.player_ship));
		
		positionX = 240.0f;
		positionY = 80.0f;
	}

    @Override
	public void Update(float elapsedTime)
	{
        super.Update(elapsedTime);
		if (Input.isDown)
			positionX = Input.xPos;

		projectilesToFire += elapsedTime * projectilesPerSecond;
		while (projectilesToFire > 1.0f) {
			projManager.AddProjectile(positionX, positionY, 0.0f, 1.0f, 300.0f, 8.0f, true);
			projectilesToFire -= 1.0f;	
		}
	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime);
		
	}
}
