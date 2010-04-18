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
	private PlayerShip player;
	private ProjectileManager projManager;
	
	public NovaGame(GL10 _gl)
	{
		super(_gl);
		
		projManager = new ProjectileManager(_gl);

		background = new NovaBackground();
        background.Load(_gl, 0);
        
        player = new PlayerShip(_gl, projManager);

	}

    @Override
	public void Update()
	{
        super.Update();
		projManager.Update(elapsedTime);
		player.Update(elapsedTime);
	}
	
    @Override
	public void Render()
	{
        super.Render();
		
		// DO ME FIRST, PLAYER GETS SLOPPY SECONDS!
		background.Render();
     	background.AddOffset(elapsedTime * 180.0f);
     	
		projManager.Render(elapsedTime);
     	player.Render(elapsedTime);
	}
}
