package com.onemorepoint.novacraft.novagame;

import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.*;
import android.util.Log;

public class NovaGame extends Game
{
	private int Score;
	private int Lives;
	private NovaBackground background;
	
	public NovaGame(GL10 _gl)
	{
		super(_gl);
		
		background = new NovaBackground();
        background.Load(_gl, 0);
	}

    @Override
	public void Update()
	{
        super.Update();
	}
	
    @Override
	public void Render()
	{
        super.Render();
		
		background.Render();
     	background.AddOffset((float)elapsedTime * 180.0f);
	}
}
