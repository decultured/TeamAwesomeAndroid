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
	private SplosionManager splosionManager;
	private float lastSpawn;
	
	private int numPowerups = 2;
	private Powerup powerups[];
	
	private int scourbsOut = 0;
	private int overbaronsOut = 0;
	private int mutalusksOut = 0;
	
	private LinkedList<EnemyShip> enemies;
	
	public NovaGame()
	{
		super();
		
		projManager = new ProjectileManager();
        player = new PlayerShip(projManager);
        enemies = new LinkedList<EnemyShip>();
		background = new NovaBackground();
		splosionManager = SplosionManager.GetInstance();
        background.Load(0);

		powerups = new Powerup[numPowerups];
		
		for (int i = 0; i < numPowerups; i++)
		{
			powerups[i] = new Powerup();
		}

		lastSpawn = -2;
	}

	private void SpawnPowerup(float _chance, float _x, float _y)
	{
		if (Math.random() > _chance)
			return;
			
		Powerup powerup = null;
		for (int i = 0; i < numPowerups; i++) {
			if (!powerups[i].active) {
				powerups[i].Reset(_x, _y);
				return;
			}
		}
	}

    @Override
	public void Update()
	{
        super.Update();
		projManager.Update(elapsedTime);
		splosionManager.Update(elapsedTime);
		player.Update(elapsedTime);
		
		if(totalTime - lastSpawn > 1)
     	{
     		if(scourbsOut < 5)
     		{
	     		// Spawn enemies
	     		EnemyScourb scourb = new EnemyScourb(player, projManager);
	     		enemies.addLast(scourb);
	     		scourbsOut++;
	     	}
     		
     		if(overbaronsOut < 1)
     		{
     			EnemyOverbaron baron = new EnemyOverbaron(player, projManager);
	     		enemies.addLast(baron);
	     		overbaronsOut++;
	     	}
	     	
	     	if(mutalusksOut < 3)
	     	{
	     		EnemyMutalusk muta = new EnemyMutalusk(player, projManager);
	     		enemies.addLast(muta);
	     		mutalusksOut++;
	     	}
     		
     		lastSpawn = totalTime;
     	}
     	
		for (int i = 0; i < numPowerups; i++) {
			if (powerups[i].active)
				powerups[i].Update(elapsedTime);
			
			if (powerups[i].positionY < -32.0f)
				powerups[i].active = false;
			
			if (powerups[i].active && player.PointCollidesWithObject(powerups[i])) {
				powerups[i].active = false;
				player.Powerup(true);
			}			
		}

     	if (projManager.CollidesWith(player, true, true))
     	{
     		if(player.Hurt(20.0f))
     		{
     			Log.v(NovaCraft.TAG, "YOU BE DED!");
     		}

			player.Powerup(false);
     	}
     	
     	Iterator enemyIter = enemies.iterator();
     	while(enemyIter.hasNext())
		{	
			EnemyShip enemy = (EnemyShip)enemyIter.next();
			if (projManager.CollidesWith(enemy, true, false))
			{
				enemy.Hurt(1000);
			}
			if(enemy.health <= 0)
			{
				if(enemy instanceof EnemyOverbaron) {
					overbaronsOut--;
					splosionManager.AddSplosion(enemy.positionX, enemy.positionY, enemy.sprite.width, enemy.sprite.height, 3);
					SpawnPowerup(0.25f, enemy.positionX, enemy.positionY);
				} else if(enemy instanceof EnemyScourb) {
					scourbsOut--;
					splosionManager.AddSplosion(enemy.positionX, enemy.positionY, enemy.sprite.width, enemy.sprite.height, 1);
					SpawnPowerup(0.05f, enemy.positionX, enemy.positionY);
				} else if(enemy instanceof EnemyMutalusk) {
					splosionManager.AddSplosion(enemy.positionX, enemy.positionY, enemy.sprite.width, enemy.sprite.height, 2);
					SpawnPowerup(0.15f, enemy.positionX, enemy.positionY);
					mutalusksOut--;
				}
			
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
     	
		splosionManager.Render(elapsedTime);
		projManager.Render(elapsedTime);

		for (int i = 0; i < numPowerups; i++) {
			if (powerups[i].active)
				powerups[i].Render(elapsedTime);
		}

     	Iterator enemyIter = enemies.iterator();
     	while(enemyIter.hasNext())
		{	
			EnemyShip enemy = (EnemyShip)enemyIter.next();
			enemy.Render(elapsedTime);
		}
		
		player.Render(elapsedTime);
	}
}
