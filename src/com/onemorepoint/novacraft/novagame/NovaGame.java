package com.onemorepoint.novacraft.novagame;

import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.*;
import android.util.Log;

public class NovaGame extends Game
{
	private int Score;
	private int Lives;
	private NovaBackground background;
	
	public void NovaGame()
	{
		background = new NovaBackground();
        background.Load(gl, 0);
	}

    @Override
	public void Update()
	{
        super.Update();
//		Log.v(NovaCraft.TAG, "treaijahslkgjhsa " + elapsedTime);
	}
	
    @Override
	public void Render()
	{
        super.Render();
		
		background.Render();
     	background.AddOffset(elapsedTime * 0.6f);
	}
}
