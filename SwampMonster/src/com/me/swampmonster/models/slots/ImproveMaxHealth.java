package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class ImproveMaxHealth extends Slot implements Perks{
	public static int level;
	public static Map<Integer, Integer> valuesByLevel;
	private static HashMap<Integer, String> descriptionByLevel;
	static {
		valuesByLevel = new HashMap<Integer, Integer>();
		valuesByLevel.put(0, Constants.ImproveMaxHealth_HealthValue_L1);
		valuesByLevel.put(1, Constants.ImproveMaxHealth_HealthValue_L2);
		valuesByLevel.put(2, Constants.ImproveMaxHealth_HealthValue_L3);
		valuesByLevel.put(3, Constants.ImproveMaxHealth_HealthValue_L4);
		valuesByLevel.put(4, Constants.ImproveMaxHealth_HealthValue_L5);
		
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.ImproveMaxHealth_Description_l1);
		descriptionByLevel.put(1, Constants.ImproveMaxHealth_Description_l2);
		descriptionByLevel.put(2, Constants.ImproveMaxHealth_Description_l3);
		descriptionByLevel.put(3, Constants.ImproveMaxHealth_Description_l4);
		descriptionByLevel.put(4, Constants.ImproveMaxHealth_Description_l5);
	}	
	
	public ImproveMaxHealth() {
		name = Constants.ImproveMaxHealth_Name;
		sprite = new Sprite(Assets.manager.get(Assets.IMPROVEMAXHEALTH_ICON));
	}
	
	@Override
	public void execute (Player player){
		Player.maxHealth += Constants.ImproveMaxHealth_HealthValue_L1;
	}
	
	@Override
	public String getDescription(){
		return descriptionByLevel.get(level);
	}
	
	@Override
	public String getDescriptionForSaved() {
		return descriptionByLevel.get(level-1);
	}

	@Override
	public List<String> getStats(Player player) {
		// TODO Auto-generated method stub
		return null;
	}
}
