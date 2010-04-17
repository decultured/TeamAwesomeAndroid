package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.game.*;

public class PlayerShip extends GameObject
{	
	public PlayerShip(GL10 _gl)
	{
		super(_gl);

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
	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime);
		
	}
}
