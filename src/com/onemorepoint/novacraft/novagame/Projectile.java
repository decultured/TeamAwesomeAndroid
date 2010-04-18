package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.novagame.*;
import javax.microedition.khronos.opengles.GL10;

public class Projectile extends GameObject
{	
	public boolean fromPlayer;
	public float lifeSpan;

	public Projectile(GL10 _gl)
	{
		super(_gl);

		sprite.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.plasma));
	}

	public void Reset(float _x, float _y, float _xDir, float _yDir, float _speed, float _lifeSpan, boolean _fromPlayer)
	{
		positionX = _x;
		positionY = _y;
		velocityX = _xDir * _speed;
		velocityY = _yDir * _speed;
		fromPlayer = _fromPlayer;
		lifeSpan = _lifeSpan;
		timeAlive = 0.0f;
		active = true;
		visible = true;
		
	}

    @Override
	public void Update(float elapsedTime)
	{
        super.Update(elapsedTime);
			
		if (timeAlive > lifeSpan)
			active = false;
	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime);
		
	}
}
