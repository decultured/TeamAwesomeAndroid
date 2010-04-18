package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.novagame.*;
import javax.microedition.khronos.opengles.GL10;

public class Projectile extends GameObject
{	
	public static int PLASMA = 0;
	public static int OVERBARON_SPOOGE = 1;
	public static int MUTALISK_BALLS = 2;

	public boolean fromPlayer;
	public float lifeSpan;
	NovaSprite s[];

	public Projectile()
	{
		super();

		s = new NovaSprite[5];

		s[0] = new NovaSprite();
		s[0].UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.plasma));

		s[1] = new NovaSprite();
		s[1].UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.overbaronspooge));

		s[2] = new NovaSprite();
		s[2].UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.mutaliskballs));
		
		sprite = s[0];
	}

	public void Reset(int type, float _x, float _y, float _xDir, float _yDir, float _speed, float _lifeSpan, boolean _fromPlayer)
	{
		if (type >= 0 && type < 3)
			sprite = s[type];
		
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
