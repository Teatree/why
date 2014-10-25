package com.me.swampmonster.models.slots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class SpeedBoost extends Slot implements PositiveEffectInterface{
	public static int level;
	public static Map<Integer, Integer> collDownByLevel;
	public static Map<Integer, Integer> lifeTimeByLevel;
	private static Map <Integer, String> descriptionByLevel;
	static {
		lifeTimeByLevel = new HashMap<Integer, Integer>();
		lifeTimeByLevel.put(0, Constants.SPEED_BOOST_LifeTime_L1);
		lifeTimeByLevel.put(1, Constants.SPEED_BOOST_LifeTime_L2);
		lifeTimeByLevel.put(2, Constants.SPEED_BOOST_LifeTime_L3);
		lifeTimeByLevel.put(3, Constants.SPEED_BOOST_LifeTime_L4);
		lifeTimeByLevel.put(4, Constants.SPEED_BOOST_LifeTime_L5);
		
		collDownByLevel = new HashMap<Integer, Integer>();
		collDownByLevel.put(0, Constants.SPEED_BOOST_CoolDown_L1);
		collDownByLevel.put(1, Constants.SPEED_BOOST_CoolDown_L2);
		collDownByLevel.put(2, Constants.SPEED_BOOST_CoolDown_L3);
		collDownByLevel.put(3, Constants.SPEED_BOOST_CoolDown_L4);
		collDownByLevel.put(4, Constants.SPEED_BOOST_CoolDown_L5);
		
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.SPEED_BOOST_Description_L1);
		descriptionByLevel.put(1, Constants.SPEED_BOOST_Description_L2);
		descriptionByLevel.put(2, Constants.SPEED_BOOST_Description_L3);
		descriptionByLevel.put(3, Constants.SPEED_BOOST_Description_L4);
		descriptionByLevel.put(4, Constants.SPEED_BOOST_Description_L5);
	}
	@Override
	public void execute(Player target) {
		target.setPositiveEffect(PositiveEffects.SPEED_BOOST);
	}

	public SpeedBoost() {
		sprite = new Sprite(Assets.manager.get(Assets.SPEED_BOOST_ICON));
		name = Constants.SPEED_BOOST_Name;

		lifeTime = Constants.SPEED_BOOST_LifeTime_L1;
		coolDown = Constants.SPEED_BOOST_CoolDown_L1;
		unlockScore = Constants.SPEED_BOOST_unlockScore;
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
		String lifeTimeString = "";
		if(level>0){
			int intuha = collDownByLevel.get(new Integer(level))-collDownByLevel.get(new Integer(level)-1);
			int lifeTdiff = lifeTimeByLevel.get(new Integer(level))-lifeTimeByLevel.get(new Integer(level)-1);
			intuha = intuha/60;
			lifeTdiff = lifeTdiff/60;
			if(intuha>0){
				intuhaString = "(+" + intuha + ")"; 
			}else if(intuha<0){
				intuhaString = "(" + intuha + ")"; 
			}
			if(lifeTdiff>0){
				lifeTimeString = "(+" + lifeTdiff + ")";
			}else if(lifeTdiff<0){
				lifeTimeString = "(" + lifeTdiff + ")";
			}
		}
		stats.add("t " + coolDown/60 + intuhaString);
		stats.add("h " + lifeTime/60 + lifeTimeString);
		
		return stats;
	}
}
