package com.onemorepoint.novacraft.novagame;

import android.util.Log;
import java.util.LinkedList;
import java.util.Iterator;
import com.onemorepoint.novacraft.*;
import com.onemorepoint.novacraft.game.*;
import com.onemorepoint.novacraft.novagame.*;
import javax.microedition.khronos.opengles.GL10;

public class SplosionManager
{
	private static SplosionManager _Instance = null;
	
	private LinkedList<Splosion> activeSplosions;
	private LinkedList<Splosion> deadSplosions;

	public SplosionManager()
	{
		activeSplosions = new LinkedList<Splosion>();
		deadSplosions = new LinkedList<Splosion>();
	}
	
	public static SplosionManager GetInstance()
	{
		if (_Instance == null)
			_Instance = new SplosionManager();
		
		return _Instance;
	}
	
	public void AddSplosion(float _x, float _y, float _width, float _height, int _numSplosions)
	{
		for (int i = 0; i < _numSplosions; i++) {
			float x = (float)((_x - (_width * 0.25f)) + (Math.random() * _width * 0.5f));
			float y = (float)((_y - (_height * 0.25f)) + (Math.random() * _height * 0.5f));

			Splosion newSplosion;
			
			if (deadSplosions.size() == 0)
				newSplosion = new Splosion();
			else
				newSplosion = deadSplosions.removeLast();
			
			newSplosion.Reset(x, y, _width, _height);
			activeSplosions.addLast(newSplosion);
		}
		
		//Log.e(NovaCraft.TAG, "Splosion Active:" + activeSplosions.size() + " Dead:" + deadSplosions.size());
	}


	public void Update(float elapsedTime)
	{
    	Iterator splosionIter = activeSplosions.iterator();
    	while(splosionIter.hasNext())
		{	
			Splosion splosion = (Splosion)splosionIter.next();
			if (!splosion.active) {
				deadSplosions.addLast(splosion);
				splosionIter.remove();
				continue;
			}
			splosion.Update(elapsedTime);
		}
	}
	
	public void Render(float elapsedTime)
	{
    	Iterator splosionIter = activeSplosions.iterator();
    	while(splosionIter.hasNext())
		{	
			Splosion splosion = (Splosion)splosionIter.next();
			splosion.Render(elapsedTime);
		}
	}
}
