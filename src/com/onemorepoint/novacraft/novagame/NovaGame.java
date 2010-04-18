package com.onemorepoint.novacraft.novagame;

import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.*;
import android.util.Log;
import java.util.LinkedList;
import java.util.Iterator;

public class NovaGame extends Game
{
	private int Score;
	private int Lives;
	private NovaBackground background;
	private PlayerShip player;
	private ProjectileManager projManager;
	private float lastSpawn;
	
	private LinkedList<EnemyShip> enemies;
	
	public NovaGame(GL10 _gl)
	{
		super(_gl);
		
		projManager = new ProjectileManager(_gl);
        player = new PlayerShip(_gl, projManager);
        enemies = new LinkedList<EnemyShip>();
		background = new NovaBackground();
        background.Load(_gl, 0);

		lastSpawn = -2;
	}

    @Override
	public void Update()
	{
        super.Update();
		projManager.Update(elapsedTime);
		player.Update(elapsedTime);
		
		if(totalTime - lastSpawn > 3)
     	{
     		// Spawn enemies
     		EnemyOverbaron enemy = new EnemyOverbaron(gl);
     		enemies.addLast((EnemyShip)enemy);
     		lastSpawn = totalTime;
     	}
     	
     	Iterator enemyIter = enemies.iterator();
     	while(enemyIter.hasNext())
		{	
			EnemyShip enemy = (EnemyShip)enemyIter.next();
			if (projManager.CollidesWith(enemy, true, false))
			{
				enemyIter.remove();
			}
			enemy.Update(elapsedTime);
		}
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
     	
     	Iterator enemyIter = enemies.iterator();
     	while(enemyIter.hasNext())
		{	
			EnemyShip enemy = (EnemyShip)enemyIter.next();
			enemy.Render(elapsedTime);
		}
	}
}
