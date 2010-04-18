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
	private LinkedList<Splosion> activeSplosions;
	private LinkedList<Splosion> deadSplosions;

	public SplosionManager()
	{
		activeSplosions = new LinkedList<Splosion>();
		deadSplosions = new LinkedList<Splosion>();
	}
	
	public void AddSplosion(float _x, float _y, float _width, float _height, int _numSplosions)
	{
		for (int i = 0; i < _numSplosions; i++) {
			float x = (float)((_x - (_width * 0.5)) + (Math.random() * _width));
			float y = (float)((_y - (_height * 0.5)) + (Math.random() * _height));

			Splosion newSplosion;
			
			if (deadSplosions.size() == 0)
				newSplosion = new Splosion();
			else
				newSplosion = deadSplosions.removeLast();
			
			newSplosion.Reset(x, y, _width, _height);
			activeSplosions.addLast(newSplosion);
		}
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
	
	public void Reset() {
		activeSplosions.clear();
		deadSplosions.clear();
	}
}
