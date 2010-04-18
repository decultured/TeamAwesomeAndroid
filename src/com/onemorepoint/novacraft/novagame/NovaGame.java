package com.onemorepoint.novacraft.novagame;

import javax.microedition.khronos.opengles.GL10;
import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.*;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import java.util.LinkedList;
import java.util.Iterator;

public class NovaGame extends Game
{
	public static NovaGame instance;
	
	private boolean gameComplete = false;
	private int Score = 0;
	private int Lives = 0;
	private int Level = 0;
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
	
	private int laserSound = 0;
	private int mutaDiesSound = 0;
	private int scourbDiesSound = 0;
	private int baronDiesSound = 0;
	
	private LinkedList<EnemyShip> enemies;
	
	public NovaGame()
	{
		super();
		
		instance = this;
		
		projManager = new ProjectileManager();
		splosionManager = SplosionManager.GetInstance();
		player = new PlayerShip(projManager);
		enemies = new LinkedList<EnemyShip>();
		background = new NovaBackground();
		laserSound = sound.LoadSound(R.raw.laser);
        mutaDiesSound = sound.LoadSound(R.raw.mutalusk_dies);
        scourbDiesSound = sound.LoadSound(R.raw.scourb_dies);
        baronDiesSound = sound.LoadSound(R.raw.overbaron_dies);

		powerups = new Powerup[numPowerups];
		
		for (int i = 0; i < numPowerups; i++)
		{
			powerups[i] = new Powerup();
		}
		
		Reset();
	}
	
	public int getScore() { return Score; }
	public int getLives() { return Lives; }
	public int getLevel() { return Level; }
	
	public float getPlayerHealth() { return player.health; }
	
	public void processGameState() {
		// if(NovaCraft.isGameOver) {
		// 	Reset();
		// } else {
		// 	Score = NovaCraft.gameScore;
		// 	Lives = NovaCraft.gameLives;
		// 	
		// 	ChangeLevel();
		// }
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
		
		if(gameComplete == true) {
			gameComplete = false;
			
			if(Lives <= 0) {
				Reset();
			} else {
				ChangeLevel();
			}
			
			return;
		}
		
		if(totalTime - lastSpawn > 1)
     	{
     		//sound.pool.play(laserSound, 1, 1, 1, 0, 1);
     		
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
				enemy.Hurt(51);
				Score += 27;
			}
			else if(enemy instanceof EnemyScourb)
			{
				if(enemy.CollidesWithObject(player))
				{
					enemy.Hurt(1000000);
					player.Hurt(30);
				}
			}
			if(enemy.health <= 0)
			{
				if(enemy instanceof EnemyOverbaron) {
					Score += 243;
					overbaronsOut--;
					splosionManager.AddSplosion(enemy.positionX, enemy.positionY, enemy.sprite.width, enemy.sprite.height, 3);
					SpawnPowerup(0.40f, enemy.positionX, enemy.positionY);
					sound.pool.play(baronDiesSound, 1, 1, 1, 0, 1);
				} else if(enemy instanceof EnemyScourb) {
					Score += 81;
					scourbsOut--;
					splosionManager.AddSplosion(enemy.positionX, enemy.positionY, enemy.sprite.width, enemy.sprite.height, 1);
					SpawnPowerup(0.10f, enemy.positionX, enemy.positionY);
					sound.pool.play(scourbDiesSound, 1, 1, 1, 0, 1);
				} else if(enemy instanceof EnemyMutalusk) {
					Score += 152;
					splosionManager.AddSplosion(enemy.positionX, enemy.positionY, enemy.sprite.width, enemy.sprite.height, 2);
					SpawnPowerup(0.20f, enemy.positionX, enemy.positionY);
					mutalusksOut--;
					sound.pool.play(mutaDiesSound, 1, 1, 1, 0, 1);
				}
				
				enemyIter.remove();
			}
			else enemy.Update(elapsedTime);
		}
		
		// added GUI update
		if(NovaCraft.instance != null) {
			if(!player.isAlive()) {
				Lives--;
				GameOver();
			}
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
	
	public void GameOver() {
		gameComplete = true;
		
		View v = NovaCraft.instance.findViewById(R.id.gameMain);
		
		Intent intent = new Intent(v.getContext(), NovaGameOverScreen.class);
		intent.putExtra("com.onemorepoint.novacraft.GameOver", Score);
		intent.putExtra("com.onemorepoint.novacraft.GameScore", Score);
		intent.putExtra("com.onemorepoint.novacraft.GameLives", Lives);
		
		v.getContext().startActivity(intent);
	}
	
	@Override
	public void ChangeLevel() {
		enemies.clear();
		projManager.Reset();
		splosionManager.Reset();
		
		player.Reset();
		
		scourbsOut = 0;
		overbaronsOut = 0;
		mutalusksOut = 0;
		
		lastSpawn = 0;
		
		Level++;
		
		if(Level % 2 == 0) {
			background.Load(1);
			player.SelectShip(PlayerShip.SHIP_SCOOT);
		} else {
			background.Load(0);
			player.SelectShip(PlayerShip.SHIP_WRATH);
		}
		
		Log.v(NovaCraft.TAG, "Level changed: "+ Level);
	}
	
	@Override
	public void Reset() {
		Log.v(NovaCraft.TAG, "Game reset started");
		Score = 0;
		Lives = 5;
		Level = 0;
		
		ChangeLevel();
		
		Log.v(NovaCraft.TAG, "Game reset finished");
	}
}
