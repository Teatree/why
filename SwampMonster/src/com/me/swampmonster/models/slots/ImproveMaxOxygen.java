package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class ImproveMaxOxygen extends Slot implements Perks{
	public static int level;
	
	private Map <Integer, Float> valuesByLevel; 
	public ImproveMaxOxygen() {
		valuesByLevel = new HashMap<Integer, Float>();
		valuesByLevel.put(0, Constants.ImpoveMaxOxygen_OxygenValue_L1);
		valuesByLevel.put(1, Constants.ImpoveMaxOxygen_OxygenValue_L2);
		valuesByLevel.put(2, Constants.ImpoveMaxOxygen_OxygenValue_L3);
		valuesByLevel.put(3, Constants.ImpoveMaxOxygen_OxygenValue_L4);
		valuesByLevel.put(4, Constants.ImpoveMaxOxygen_OxygenValue_L5);
		sprite = new Sprite(Assets.manager.get(Assets.IMPROVEMAXOXYGEN_ICON));
	}
	
	public void execute (Player player){
		System.out.println("level "+ level);
		Player.maxOxygen += valuesByLevel.get(level);
	}
}
