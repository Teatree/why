package com.me.swampmonster.models.slots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class ImproveArrowSpeed extends Slot implements Perks{

	public static int level;
	public static Map<Integer, Float> valuesByLevel;
	private static HashMap<Integer, String> descriptionByLevel;
	static {
		valuesByLevel = new HashMap<Integer, Float>();
		valuesByLevel.put(0, Constants.ImproveArrowSpeed_value_L1);
		valuesByLevel.put(1, Constants.ImproveArrowSpeed_value_L2);
		valuesByLevel.put(2, Constants.ImproveArrowSpeed_value_L3);
		valuesByLevel.put(3, Constants.ImproveArrowSpeed_value_L4);
		valuesByLevel.put(4, Constants.ImproveArrowSpeed_value_L5);
		
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.ImproveArrowSpeed_Description_l1);
		descriptionByLevel.put(1, Constants.ImproveArrowSpeed_Description_l2);
		descriptionByLevel.put(2, Constants.ImproveArrowSpeed_Description_l3);
		descriptionByLevel.put(3, Constants.ImproveArrowSpeed_Description_l4);
		descriptionByLevel.put(4, Constants.ImproveArrowSpeed_Description_l5);
	}	
	
	public ImproveArrowSpeed() {
		name = Constants.ImproveArrowSpeed_Name;
		sprite = new Sprite(Assets.manager.get(Assets.IMPROVEARROWSPEED_ICON));
		unlockScore = Constants.ImproveArrowSpeed_unlockScore;
	}
	
	@Override
	public void execute (Player player){
		Player.arrowMovementSpeed += valuesByLevel.get(level);
	}
	
	@Override
	public String getDescription() {
		return descriptionByLevel.get(level);
	}
	
	@Override
	public String getDescriptionForSaved() {
		return descriptionByLevel.get(level-1);
	}

	@Override
	public List<String> getStats(Player player) {
		List<String> stats = new ArrayList<String>();
		String valueString = "";
//		if(level>0){
//			float valueDiff = valuesByLevel.get(new Integer(level))-valuesByLevel.get(new Integer(level)-1);
//			if(valueDiff>0){
//				valueString = "(+" + valueDiff + ")"; 
//			}else if(valueDiff<0){
//				valueString = "(" + valueDiff + ")"; 
//			}
//			
//		}
		stats.add("+" + valuesByLevel.get(level) + valueString);
		
		return stats;
	}
}
