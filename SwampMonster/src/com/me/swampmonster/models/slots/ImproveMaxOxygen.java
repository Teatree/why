package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class ImproveMaxOxygen extends Slot implements Perks{
	public static int level;
	
	private Map <Integer, Float> valuesByLevel; 
	public ImproveMaxOxygen() {
		valuesByLevel = new HashMap<Integer, Float>();
		valuesByLevel.put(0, 90.5f);
		valuesByLevel.put(1, 20.75f);
		valuesByLevel.put(2, 30f);
		valuesByLevel.put(3, 41.25f);
		valuesByLevel.put(4, 51.5f);
		sprite = new Sprite(Assets.manager.get(Assets.IMPROVEMAXOXYGEN_ICON));
	}
	
	public void execute (Player player){
		System.out.println("level "+ level);
		player.maxOxygen += valuesByLevel.get(level);
		System.out.println("player.maxOxygen " + player.maxOxygen);
	}
}
