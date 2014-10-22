package com.me.swampmonster.models.slots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.PlasmaShield;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class PlasmaShieldSkill extends Slot{

	public static int level;
	public static Map<Integer, Integer> collDownByLevel;
	private static Map <Integer, String> descriptionByLevel;
	static {
		collDownByLevel = new HashMap<Integer, Integer>();
		collDownByLevel.put(0, Constants.PLASMA_SHIELD_CoolDown_L1);
		collDownByLevel.put(1, Constants.PLASMA_SHIELD_CoolDown_L2);
		collDownByLevel.put(2, Constants.PLASMA_SHIELD_CoolDown_L3);
		collDownByLevel.put(3, Constants.PLASMA_SHIELD_CoolDown_L4);
		collDownByLevel.put(4, Constants.PLASMA_SHIELD_CoolDown_L5);
		
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.PLASMA_SHIELD_Description_L1);
		descriptionByLevel.put(1, Constants.PLASMA_SHIELD_Description_L2);
		descriptionByLevel.put(2, Constants.PLASMA_SHIELD_Description_L3);
		descriptionByLevel.put(3, Constants.PLASMA_SHIELD_Description_L4);
		descriptionByLevel.put(4, Constants.PLASMA_SHIELD_Description_L5);
	}
	public PlasmaShieldSkill() {
		name = Constants.PLASMA_SHIELD_Name;
		sprite = new Sprite(Assets.manager.get(Assets.SHADOW_ARROW_ICON));
		coolDown = Constants.PLASMA_SHIELD_CoolDown_L1;
	}
	
	@Override
	public void execute(Player player){
		L1.plasmaShield = new PlasmaShield(player.position); 
		L1.plasmaShield.lifeTime = lifeTime;
		L1.plasmaShield.spawnTime = lifeTime-50;
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
		String intuhaString = "";
		if(level>0){
			int intuha = collDownByLevel.get(new Integer(level))-collDownByLevel.get(new Integer(level)-1);
			intuha = intuha/60;
			if(intuha>0){
				intuhaString = "(+" + intuha + ")"; 
			}else if(intuha<0){
				intuhaString = "(" + intuha + ")"; 
			}
		}
		stats.add("t " + coolDown/60 + intuhaString);
		
		return stats;
	}
}
