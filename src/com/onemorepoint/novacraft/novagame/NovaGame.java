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
	
	private int Score = 0;
	private int Lives = 0;
	private int Level = 0;
	private NovaBackground background;
	private PlayerShip player;
	private ProjectileManager projManager;
	private SplosionManager splosionManager;
	private float lastSpawn;
	
	private int scourbsOut = 0;
	private int overbaronsOut = 0;
	private int mutalusksOut = 0;
	
	private int laserSound = 0;
	
	private LinkedList<EnemyShip> enemies;
	
	public NovaGame()
	{
		super();
		
		instance = this;
		
		projManager = new ProjectileManager();
		splosionManager = new SplosionManager();
		player = new PlayerShip(projManager);
		enemies = new LinkedList<EnemyShip>();
		background = new NovaBackground();
		laserSound = sound.LoadSound(R.raw.laser);
		
		Reset();
	}
	
	public int getScore() { return Score; }
	public int getLives() { return Lives; }
	public int getLevel() { return Level; }
	
	public float getPlayerHealth() { return player.health; }
	
	public void processGameState(boolean isGameOver, int _Score, int _Lives) {
		if(isGameOver) {
			Reset();
		} else {
			Score = _Score;
			Lives = _Lives;
			
			ChangeLevel();
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
     		sound.pool.play(laserSound, 1, 1, 1, 0, 1);
     		
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
     	
     	if (projManager.CollidesWith(player, true, true))
     	{
     		if(player.Hurt(20.0f))
     		{
     			Log.v(NovaCraft.TAG, "YOU BE DED!");
     		}
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
				} else if(enemy instanceof EnemyScourb) {
					Score += 81;
					scourbsOut--;
					splosionManager.AddSplosion(enemy.positionX, enemy.positionY, enemy.sprite.width, enemy.sprite.height, 1);
				} else if(enemy instanceof EnemyMutalusk) {
					Score += 152;
					splosionManager.AddSplosion(enemy.positionX, enemy.positionY, enemy.sprite.width, enemy.sprite.height, 2);
					mutalusksOut--;
				}
				
				enemyIter.remove();
			}
			else enemy.Update(elapsedTime);
		}
		
		// added GUI update
		if(NovaCraft.instance != null) {
			if(!player.isAlive()) {
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

     	Iterator enemyIter = enemies.iterator();
     	while(enemyIter.hasNext())
		{	
			EnemyShip enemy = (EnemyShip)enemyIter.next();
			enemy.Render(elapsedTime);
		}
		
		player.Render(elapsedTime);
	}
	
	public void GameOver() {
		View v = NovaCraft.instance.findViewById(R.id.gameMain);
		
		Intent intent = new Intent(v.getContext(), NovaGameOverScreen.class);
		intent.putExtra("com.onemorepoint.novacraft.GameOver", Score);
		intent.putExtra("com.onemorepoint.novacraft.GameScore", Score);
		intent.putExtra("com.onemorepoint.novacraft.GameLives", Lives);
		v.getContext().startActivity(intent);
		return;
	}
	
	@Override
	public void ChangeLevel() {
		Iterator enemyIter = enemies.iterator();
		while(enemyIter.hasNext()) {
			enemyIter.remove();
		}
		
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
