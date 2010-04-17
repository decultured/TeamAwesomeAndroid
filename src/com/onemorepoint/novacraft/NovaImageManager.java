package com.onemorepoint.novacraft;

import android.util.Log;
import javax.microedition.khronos.opengles.GL10;
import java.util.*;

public class NovaImageManager
{
	public static NovaImageManager instance = null;
	
	LinkedList<NovaImage> images;
	GL10 gl;
	
	public NovaImageManager(GL10 _gl)
	{	
		images = new LinkedList<NovaImage>();
		gl = _gl;
		
		if(instance == null)
			instance = this;
	}
	
	public static NovaImageManager GetInstance()
	{
		if(instance == null)
			instance = new NovaImageManager(null);
			
		return instance;
	}
	
	public NovaImage LoadImage(int resourceId)
	{
		NovaImage img = null;
		Iterator iterator;
		iterator = images.iterator();
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
}