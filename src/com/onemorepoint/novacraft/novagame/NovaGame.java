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
	private float lastSpawn;
	
	private int scourbsOut = 0;
	private int overbaronsOut = 0;
	private int mutalusksOut = 0;
	
	private LinkedList<EnemyShip> enemies;
	
	public NovaGame(GL10 _gl)
	{
		super(_gl);
		
		lastSpawn = -2;
		
		background = new NovaBackground();
        background.Load(_gl, 0);
        
        player = new PlayerShip(_gl);
        enemies = new LinkedList<EnemyShip>();
	}

    @Override
	public void Update()
	{
        super.Update();
		player.Update(elapsedTime);
		
		if(totalTime - lastSpawn > 1)
     	{
     		if(scourbsOut < 5)
     		{
	     		// Spawn enemies
	     		EnemyScourb scourb = new EnemyScourb(gl, player);
	     		enemies.addLast(scourb);
	     		scourbsOut++;
	     	}
     		
     		if(overbaronsOut < 1)
     		{
     			EnemyOverbaron baron = new EnemyOverbaron(gl, player);
	     		enemies.addLast(baron);
	     		overbaronsOut++;
	     	}
	     	
	     	if(mutalusksOut < 3)
	     	{
	     		EnemyMutalusk muta = new EnemyMutalusk(gl, player);
	     		enemies.addLast(muta);
	     		mutalusksOut++;
	     	}
     		
     		lastSpawn = totalTime;
     	}
     	
     	Iterator enemyIter = enemies.iterator();
     	while(enemyIter.hasNext())
		{	
			EnemyShip enemy = (EnemyShip)enemyIter.next();
			
			if(enemy.health <= 0)
			{
				if(enemy instanceof EnemyOverbaron)
					overbaronsOut--;
				else if(enemy instanceof EnemyScourb)
					scourbsOut--;
					
				enemyIter.remove();
			}
			else enemy.Update(elapsedTime);
		}
	}
	
    @Override
	public void Render()
	{
        super.Render();
		
		// DO ME FIRST, PLAYER GETS SLOPPY SECONDS!
		background.Render();
     	background.AddOffset(elapsedTime * 180.0f);
     	
     	Iterator enemyIter = enemies.iterator();
     	while(enemyIter.hasNext())
		{	
			EnemyShip enemy = (EnemyShip)enemyIter.next();
			enemy.Render(elapsedTime);
		}
		
		player.Render(elapsedTime);
	}
}
