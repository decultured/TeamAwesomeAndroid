package com.onemorepoint.novacraft;

import android.util.Log;
import javax.microedition.khronos.opengles.GL10;
import java.util.LinkedList;
import java.util.Iterator;

public class NovaImageManager
{
	public static NovaImageManager instance = null;
	
	static LinkedList<NovaImage> images;
	static GL10 gl;
	
	public NovaImageManager(GL10 _gl)
	{	
		images = new LinkedList<NovaImage>();
		gl = _gl;
		
		if(instance == null)
			instance = this;
		else instance.gl = _gl;
	}
	
	public static NovaImageManager GetInstance()
	{
		if(instance == null)
			instance = new NovaImageManager(null);
			
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
			Log.v(NovaCraft.TAG, "Reusing image resource " + resourceId);
			return img;
		}
			
		img = new NovaImage();
		if(!img.LoadImage(resourceId, gl))
			return null;
			
		images.addLast(img);
		
		return img;
	}
	
	public GL10 GetGL()
	{
		return gl;
	}
	
	public void ReloadTextures()
	{
		Log.v(NovaCraft.TAG, "Reloading textures...");
		
		Iterator iterator = images.iterator();
		while(iterator.hasNext())
		{	
			NovaImage thisImg = (NovaImage)iterator.next();
			thisImg.LoadImage(thisImg.GetResourceID(), gl);
		}
	}
}