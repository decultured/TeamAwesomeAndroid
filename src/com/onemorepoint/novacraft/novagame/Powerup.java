package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.novagame.*;
import javax.microedition.khronos.opengles.GL10;

public class Powerup extends GameObject
{	
	public Powerup()
	{
		super();

		active = false;
		velocityY = -100.0f;
		
		sprite.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.powerup));
	}

	public void Reset(float _x, float _y)
	{
		positionX = _x;
		positionY = _y;
		active = true;
		visible = true;
	}

    @Override
	public void Update(float elapsedTime)
	{
        super.Update(elapsedTime);
	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime);
	}
}
