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
	public float frameLength = 0.0275f;
	
	public Splosion()
	{
		super();
		
		sprite.UseImage(NovaImageManager.GetInstance().LoadImage(R.raw.explosion, 128, 128));
	}

	public void Reset(float _x, float _y, float _width, float _height)
	{
		positionX = _x;
		positionY = _y;
		active = true;
		frame = 0;
		frameTime = 0.0f;
		
		sprite.SetSize(_width, _height);
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
	}
	
    @Override
	public void Render(float elapsedTime)
	{
        super.Render(elapsedTime, frame);
	}
}
