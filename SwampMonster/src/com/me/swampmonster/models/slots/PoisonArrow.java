package com.me.swampmonster.models.slots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class PoisonArrow extends Slot{
	public static int level;
	public static Map<Integer, Integer> collDownByLevel;
	private static Map <Integer, String> descriptionByLevel;
	public static Map<Integer, Float> poisonDamageByLVL;
	public static Map<Integer, Integer> poisonIntervalByLVL;
	public static Map<Integer, Integer> poisonLifeTimeByLVL;
	static {
		collDownByLevel = new HashMap<Integer, Integer>();
		collDownByLevel.put(0, Constants.PoisonArrow_CoolDown_L1);
		collDownByLevel.put(1, Constants.PoisonArrow_CoolDown_L2);
		collDownByLevel.put(2, Constants.PoisonArrow_CoolDown_L3);
		collDownByLevel.put(3, Constants.PoisonArrow_CoolDown_L4);
		collDownByLevel.put(4, Constants.PoisonArrow_CoolDown_L5);
		
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.PoisonArrow_Description_L1);
		descriptionByLevel.put(1, Constants.PoisonArrow_Description_L2);
		descriptionByLevel.put(2, Constants.PoisonArrow_Description_L3);
		descriptionByLevel.put(3, Constants.PoisonArrow_Description_L4);
		descriptionByLevel.put(4, Constants.PoisonArrow_Description_L5);
		
		poisonDamageByLVL = new HashMap<Integer, Float>();
		poisonDamageByLVL.put(0, Constants.PoisonArrow_poisonDamage_L1);
		poisonDamageByLVL.put(1, Constants.PoisonArrow_poisonDamage_L2);
		poisonDamageByLVL.put(2, Constants.PoisonArrow_poisonDamage_L3);
		poisonDamageByLVL.put(3, Constants.PoisonArrow_poisonDamage_L4);
		poisonDamageByLVL.put(4, Constants.PoisonArrow_poisonDamage_L5);
		
		poisonIntervalByLVL = new HashMap<Integer, Integer>();
		poisonIntervalByLVL.put(0, Constants.PoisonArrow_poisonInterval_L1);
		poisonIntervalByLVL.put(1, Constants.PoisonArrow_poisonInterval_L2);
		poisonIntervalByLVL.put(2, Constants.PoisonArrow_poisonInterval_L3);
		poisonIntervalByLVL.put(3, Constants.PoisonArrow_poisonInterval_L4);
		poisonIntervalByLVL.put(4, Constants.PoisonArrow_poisonInterval_L5);
		
		poisonLifeTimeByLVL = new HashMap<Integer, Integer>();
		poisonLifeTimeByLVL.put(0, Constants.PoisonArrow_poisonLifeTime_L1);
		poisonLifeTimeByLVL.put(1, Constants.PoisonArrow_poisonLifeTime_L2);
		poisonLifeTimeByLVL.put(2, Constants.PoisonArrow_poisonLifeTime_L3);
		poisonLifeTimeByLVL.put(3, Constants.PoisonArrow_poisonLifeTime_L4);
		poisonLifeTimeByLVL.put(4, Constants.PoisonArrow_poisonLifeTime_L5);
	}
	
	public PoisonArrow() {
		name = Constants.PoisonArrow_Name;
		sprite = new Sprite(Assets.manager.get(Assets.POISONED_ARROW_ICON));
		coolDown = Constants.PoisonArrow_CoolDown_L1;
		unlockScore = Constants.PoisonArrow_unlockScore;
	}
	
	@Override
	public void execute(Player player){
		player.arrowEffectCarrier = EffectCarriers.POISONED;
	}
	
	public static void injectPoison(Enemy enemy){
		enemy.setNegativeEffect(NegativeEffects.POISONED);
		NegativeEffects.POISONED.lifetime = poisonLifeTimeByLVL.get(PoisonTrap.level-1);
		
		if(PoisonTrap.level>=1){
			enemy.poisonDamage = poisonDamageByLVL.get(PoisonTrap.level-1);
			enemy.poisonDamageInterval = poisonIntervalByLVL.get(PoisonTrap.level-1);
		}else{
			enemy.poisonDamage = poisonDamageByLVL.get(PoisonTrap.level);
			enemy.poisonDamageInterval = poisonIntervalByLVL.get(PoisonTrap.level);
		}
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
