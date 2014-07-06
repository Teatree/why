package com.me.swampmonster.utils;

import java.util.Random;

public class GeneralUtils {
	
	public static <T> T[] shuffle(T [] objects)
	{
		Random rand = new Random();
	    for (int i = 0; i < objects.length; i++)
	    {
	        int swap = rand.nextInt(i + 1);
	        T temp = objects[swap];
	        objects[swap] = objects[i];
	        objects[i] = temp;
	    }
	    return objects;
	}
}
