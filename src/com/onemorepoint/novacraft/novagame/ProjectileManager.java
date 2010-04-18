package com.onemorepoint.novacraft.novagame;

import android.util.Log;
import java.util.LinkedList;
import java.util.Iterator;
import com.onemorepoint.novacraft.*;
import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.novagame.*;
import javax.microedition.khronos.opengles.GL10;

public class ProjectileManager
{
	private LinkedList<Projectile> activeProjs;
	private LinkedList<Projectile> deadProjs;

	public ProjectileManager()
	{
		activeProjs = new LinkedList<Projectile>();
		deadProjs = new LinkedList<Projectile>();
	}
	
	public void AddProjectile(int _type, float _x, float _y, float _xDir, float _yDir, float _speed, float _lifeSpan, boolean _fromPlayer)
	{
		if (deadProjs.size() == 0)
		{
			Projectile newProj = new Projectile();
			newProj.Reset(_type, _x, _y, _xDir, _yDir, _speed, _lifeSpan, _fromPlayer);
			activeProjs.addLast(newProj);
			return;
		}
		
		//Log.e(NovaCraft.TAG, "Proj Active:" + activeProjs.size() + " Dead:" + deadProjs.size());
		
		Projectile newProj = deadProjs.removeLast();
		newProj.Reset(_type, _x, _y, _xDir, _yDir, _speed, _lifeSpan, _fromPlayer);
		activeProjs.addLast(newProj);
	}

	public boolean CollidesWith(GameObject _obj, boolean _remove, boolean _isPlayer)
	{
     	Iterator projIter = activeProjs.iterator();
     	while(projIter.hasNext())
		{	
			Projectile proj = (Projectile)projIter.next();
			if (proj.active && _isPlayer != proj.fromPlayer && _obj.PointCollidesWithObject(proj)) {
				if (_remove) {
					proj.active = false;
					deadProjs.addLast(proj);
					projIter.remove();
				}
				return true;
			}
		}
		return false;
	}

	public void Update(float elapsedTime)
	{
    	Iterator projIter = activeProjs.iterator();
    	while(projIter.hasNext())
		{	
			Projectile proj = (Projectile)projIter.next();
			if (!proj.active) {
				deadProjs.addLast(proj);
				projIter.remove();
				continue;
			}
			proj.Update(elapsedTime);
		}
	}
	
	public void Render(float elapsedTime)
	{
    	Iterator projIter = activeProjs.iterator();
    	while(projIter.hasNext())
		{	
			Projectile proj = (Projectile)projIter.next();
			proj.Render(elapsedTime);
		}
	}
}
