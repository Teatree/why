package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class Arrows3 extends Slot{
	
	public static int level;
	
	public static Map<Integer, Integer> collDownByLevel;
	private static HashMap<Integer, String> descriptionByLevel;
	static {
		collDownByLevel = new HashMap<Integer, Integer>();
		collDownByLevel.put(0, Constants.Arrows3_CoolDown_L1);
		collDownByLevel.put(1, Constants.Arrows3_CoolDown_L2);
		collDownByLevel.put(2, Constants.Arrows3_CoolDown_L3);
		collDownByLevel.put(3, Constants.Arrows3_CoolDown_L4);
		collDownByLevel.put(4, Constants.Arrows3_CoolDown_L5);
		
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.Arrows3_Description_L1);
		descriptionByLevel.put(1, Constants.Arrows3_Description_L2);
		descriptionByLevel.put(2, Constants.Arrows3_Description_L3);
		descriptionByLevel.put(3, Constants.Arrows3_Description_L4);
		descriptionByLevel.put(4, Constants.Arrows3_Description_L5);
	}
	
	public Arrows3() {
		sprite = new Sprite(Assets.manager.get(Assets.THREEARROW_ICON));
		description = Constants.Arrows3_Description_L1;
		coolDown = Constants.Arrows3_CoolDown_L1;
	}
	
	@Override
	public void execute(Player player){
		player.ThreeArrowsFlag = true;
	}

	@Override
	public String getDescription() {
		return descriptionByLevel.get(level);
	}
}
