package com.onemorepoint.novacraft;

import android.util.Log;
import android.media.SoundPool;
import java.util.LinkedList;
import java.util.Iterator;
import android.media.AudioManager;
import android.content.res.Resources;
import android.content.Context;

class NovaSoundTracker
{
	public int soundID;
	public int resourceID;
	
	public NovaSoundTracker()
	{
		soundID = resourceID = 0;
	}
}

public class NovaSoundManager
{
	public static NovaSoundManager instance = null;
	
	static LinkedList<NovaSoundTracker> sounds;
	public SoundPool pool;
	
	public NovaSoundManager()
	{	
		sounds = new LinkedList<NovaSoundTracker>();
		pool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		
		if(instance == null)
			instance = this;
	}
	
	public static NovaSoundManager GetInstance()
	{
		if(instance == null)
			instance = new NovaSoundManager();
			
		return instance;
	}
	
	public int LoadSound(int resourceId)
	{
		int soundID = 0;
		Iterator iterator = sounds.iterator();
		while(iterator.hasNext())
		{	
			NovaSoundTracker thisSnd = (NovaSoundTracker)iterator.next();
			if(thisSnd.resourceID == resourceId)
			{
				soundID = thisSnd.soundID;
				break;
			}
		}
		
		if(soundID != 0)
			return soundID;
					
		NovaSoundTracker snd = new NovaSoundTracker();
		snd.resourceID = resourceId;
		
	
		soundID = pool.load(NovaCraft.instance.getResources().openRawResourceFd(resourceId), 1);
		if(soundID != 0)
		{
			snd.soundID = soundID;
			sounds.addLast(snd);
			Log.v(NovaCraft.TAG, "Loaded new sound by resource");
		} else Log.v(NovaCraft.TAG, "Failed to load sound by resource!");
		
		return soundID;
	}
}