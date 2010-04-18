package com.onemorepoint.novacraft.novagame;

import java.util.LinkedList;
import com.onemorepoint.novacraft.*;
import com.onemorepoint.novacraft.novagame.*;
import javax.microedition.khronos.opengles.GL10;

public class ProjectileManager
{
	private LinkedList<Projectile> activeProjs;
	private LinkedList<Projectile> deadProjs;
	
	protected GL10 gl;

	public ProjectileManager(GL10 _gl)
	{
		gl = _gl;
		activeProjs = new LinkedList<Projectile>();
		deadProjs = new LinkedList<Projectile>();
	}
	
	public void AddProjectile(float _x, float _y, float _xDir, float _yDir, float _speed, float _lifeSpan, boolean _fromPlayer)
	{
		if (deadProjs.size() == 0)
		{
			Projectile newProj = new Projectile(gl);
			newProj.Reset(_x, _y, _xDir, _yDir, _speed, _lifeSpan, _fromPlayer);
			activeProjs.addLast(newProj);
			return;
		}
		
		Projectile newProj = deadProjs.removeLast();
		newProj.Reset(_x, _y, _xDir, _yDir, _speed, _lifeSpan, _fromPlayer);
		activeProjs.addLast(newProj);
	}

	public void Update(float elapsedTime)
	{
		for (int i = 0; i < activeProjs.size(); i++)
		{
			Projectile proj = activeProjs.get(i);
			if (!proj.active) {
				deadProjs.addLast(proj);
				activeProjs.remove(i);
				i--;
				continue;
			}
			proj.Update(elapsedTime);
		}
	}
	
	public void Render(float elapsedTime)
	{
		for (int i = 0; i < activeProjs.size(); i++)
		{
			Projectile proj = activeProjs.get(i);
			proj.Render(elapsedTime);
		}
	}
}
