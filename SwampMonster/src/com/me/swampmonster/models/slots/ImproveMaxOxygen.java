package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class ImproveMaxOxygen extends Slot implements Perks{
	public static int level;
	
	private static Map <Integer, Float> valuesByLevel; 
	private static Map <Integer, String> descriptionByLevel;
	
	static {
		valuesByLevel = new HashMap<Integer, Float>();
		valuesByLevel.put(0, Constants.ImproveMaxOxygen_OxygenValue_L1);
		valuesByLevel.put(1, Constants.ImproveMaxOxygen_OxygenValue_L2);
		valuesByLevel.put(2, Constants.ImproveMaxOxygen_OxygenValue_L3);
		valuesByLevel.put(3, Constants.ImproveMaxOxygen_OxygenValue_L4);
		valuesByLevel.put(4, Constants.ImproveMaxOxygen_OxygenValue_L5);
		
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.ImproveMaxOxygen_Description_l1);
		descriptionByLevel.put(1, Constants.ImproveMaxOxygen_Description_l2);
		descriptionByLevel.put(2, Constants.ImproveMaxOxygen_Description_l3);
		descriptionByLevel.put(3, Constants.ImproveMaxOxygen_Description_l4);
		descriptionByLevel.put(4, Constants.ImproveMaxOxygen_Description_l5);
	}
	
	public ImproveMaxOxygen() {
		sprite = new Sprite(Assets.manager.get(Assets.IMPROVEMAXOXYGEN_ICON));
	}
	
	public void execute (Player player){
		Player.maxOxygen += valuesByLevel.get(level);
	}
	
	public String getDescription() {
		return descriptionByLevel.get(level);
	}
}
