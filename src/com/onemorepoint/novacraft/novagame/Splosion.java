package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.*;
import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.novagame.*;
import javax.microedition.khronos.opengles.GL10;

public class Splosion extends GameObject
{	
	NovaSprite s[];
	public int frame = 0;
	public float frameTime = 0.0f;
	public float frameLength = 0.11f;
	
	public Splosion()
	{
		super();

		s = new NovaSprite[5];

		s[0] = new NovaSprite();
		s[0].UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.explosion_1));

		s[1] = new NovaSprite();
		s[1].UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.explosion_2));

		s[2] = new NovaSprite();
		s[2].UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.explosion_3));

		s[3] = new NovaSprite();
		s[3].UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.explosion_4));

		s[4] = new NovaSprite();
		s[4].UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.explosion_5));

		sprite = s[0];
	}

	public void Reset(float _x, float _y, float _width, float _height)
	{
		positionX = _x;
		positionY = _y;
		active = true;
		frame = 0;
		frameTime = 0.0f;
		
		s[0].SetSize(_width, _height);
		s[1].SetSize(_width, _height);
		s[2].SetSize(_width, _height);
		s[3].SetSize(_width, _height);
		s[4].SetSize(_width, _height);
	}

    @Override
	public void Update(float elapsedTime)
	{
        super.Update(elapsedTime);

		frameTime += elapsedTime;
		
		if (frameTime > frameLength) {
			frameTime -= frameLength;
			frame++;
		}
		
		if (frame > 4) {
			frame = 4;
			active = false;
		}
		
		sprite = s[frame];
	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime);
	}
}
