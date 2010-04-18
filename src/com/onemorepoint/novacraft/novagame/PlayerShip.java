package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.game.*;

public class PlayerShip extends GameObject
<<<<<<< HEAD
{
=======
{	
	NovaSprite spriteL;
	NovaSprite spriteR;
	NovaSprite currentSprite;
>>>>>>> 4c6a59cd57bcea0f608898ac1b63e063a4cb7c86
	
	public PlayerShip(GL10 _gl)
	{
		super(_gl);

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
