package com.onemorepoint.novacraft;

import android.util.Log;
import javax.microedition.khronos.opengles.GL10;
import java.util.LinkedList;
import java.util.Iterator;

public class NovaImageManager
{
	public static NovaImageManager instance = null;
	
	static LinkedList<NovaImage> images;
	
	public static boolean needsReload = true;
	
	public NovaImageManager()
	{	
		images = new LinkedList<NovaImage>();
		
		if(instance == null)
			instance = this;
	}
	
	public static NovaImageManager GetInstance()
	{
		if(instance == null)
			instance = new NovaImageManager();
			
		return instance;
	}
	
	public void ClearImageList()
	{
		images.clear();
	}
	
	public NovaImage LoadImage(int resourceId)
	{
		NovaImage img = null;
		Iterator iterator = images.iterator();
		while(iterator.hasNext())
		{	
			NovaImage thisImg = (NovaImage)iterator.next();
			if(thisImg.GetResourceID() == resourceId)
			{
				img = thisImg;
				break;
			}
		}
		
		if(img != null)
		{
			//Log.v(NovaCraft.TAG, "Reusing image resource " + resourceId);
			return img;
		}
			
		img = new NovaImage();
		if(!img.LoadImage(resourceId))
			return null;
			
		images.addLast(img);
		
		return img;
	}
	
	public void ReloadTextures()
	{
		Log.v(NovaCraft.TAG, "Reloading textures...");
		
		Iterator iterator = images.iterator();
		while(iterator.hasNext())
		{	
			NovaImage thisImg = (NovaImage)iterator.next();
			thisImg.LoadImage(thisImg.GetResourceID());
		}
		
		needsReload = false;
	}
	
	public void FreeTextures()
	{
		Log.v(NovaCraft.TAG, "Freeing textures...");
		Iterator iterator = images.iterator();
		while(iterator.hasNext())
		{	
			NovaImage thisImg = (NovaImage)iterator.next();
			thisImg.FreeTexture();
		}
		
		needsReload = true;
	}
}