package com.me.swampmonster.models.slots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class PoisonTrap extends Trap{
	public static int level;
	public static Map<Integer, Integer> radiusByLevel;
	public static Map<Integer, Integer> collDownByLevel;
	public static Map<Integer, Integer> lifeTimeByLevel;
	public static Map<Integer, Float> poisonDamageByLVL;
	public static Map<Integer, Integer> poisonIntervalByLVL;
	public static Map<Integer, Integer> poisonLifeTimeByLVL;
	private static Map <Integer, String> descriptionByLevel;
	static {
		radiusByLevel = new HashMap<Integer, Integer>();
		radiusByLevel.put(0, Constants.PoisonTrap_Radius_L1);
		radiusByLevel.put(1, Constants.PoisonTrap_Radius_L2);
		radiusByLevel.put(2, Constants.PoisonTrap_Radius_L3);
		radiusByLevel.put(3, Constants.PoisonTrap_Radius_L4);
		radiusByLevel.put(4, Constants.PoisonTrap_Radius_L5);
		
		lifeTimeByLevel = new HashMap<Integer, Integer>();
		lifeTimeByLevel.put(0, Constants.PoisonTrap_LifeTime_L1);
		lifeTimeByLevel.put(1, Constants.PoisonTrap_LifeTime_L2);
		lifeTimeByLevel.put(2, Constants.PoisonTrap_LifeTime_L3);
		lifeTimeByLevel.put(3, Constants.PoisonTrap_LifeTime_L4);
		lifeTimeByLevel.put(4, Constants.PoisonTrap_LifeTime_L5);
		
		poisonDamageByLVL = new HashMap<Integer, Float>();
		poisonDamageByLVL.put(0, Constants.PoisonTrap_poisonDamage_L1);
		poisonDamageByLVL.put(1, Constants.PoisonTrap_poisonDamage_L2);
		poisonDamageByLVL.put(2, Constants.PoisonTrap_poisonDamage_L3);
		poisonDamageByLVL.put(3, Constants.PoisonTrap_poisonDamage_L4);
		poisonDamageByLVL.put(4, Constants.PoisonTrap_poisonDamage_L5);
		
		poisonIntervalByLVL = new HashMap<Integer, Integer>();
		poisonIntervalByLVL.put(0, Constants.PoisonTrap_poisonInterval_L1);
		poisonIntervalByLVL.put(1, Constants.PoisonTrap_poisonInterval_L2);
		poisonIntervalByLVL.put(2, Constants.PoisonTrap_poisonInterval_L3);
		poisonIntervalByLVL.put(3, Constants.PoisonTrap_poisonInterval_L4);
		poisonIntervalByLVL.put(4, Constants.PoisonTrap_poisonInterval_L5);
		
		poisonLifeTimeByLVL = new HashMap<Integer, Integer>();
		poisonLifeTimeByLVL.put(0, Constants.PoisonTrap_poisonLifeTime_L1);
		poisonLifeTimeByLVL.put(1, Constants.PoisonTrap_poisonLifeTime_L2);
		poisonLifeTimeByLVL.put(2, Constants.PoisonTrap_poisonLifeTime_L3);
		poisonLifeTimeByLVL.put(3, Constants.PoisonTrap_poisonLifeTime_L4);
		poisonLifeTimeByLVL.put(4, Constants.PoisonTrap_poisonLifeTime_L5);
		
		collDownByLevel = new HashMap<Integer, Integer>();
		collDownByLevel.put(0, Constants.PoisonTrap_CoolDown_L1);
		collDownByLevel.put(1, Constants.PoisonTrap_CoolDown_L2);
		collDownByLevel.put(2, Constants.PoisonTrap_CoolDown_L3);
		collDownByLevel.put(3, Constants.PoisonTrap_CoolDown_L4);
		collDownByLevel.put(4, Constants.PoisonTrap_CoolDown_L5);
		
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.PoisonTrap_Description_L1);
		descriptionByLevel.put(1, Constants.PoisonTrap_Description_L2);
		descriptionByLevel.put(2, Constants.PoisonTrap_Description_L3);
		descriptionByLevel.put(3, Constants.PoisonTrap_Description_L4);
		descriptionByLevel.put(4, Constants.PoisonTrap_Description_L5);
	}
	
	public PoisonTrap() {
		lifeTimeMax = Constants.PoisonTrap_LifeTime_L1;
		circle = new Circle();
		circle.radius = Constants.PoisonTrap_Radius_L1;
		name = Constants.PoisonTrap_Name;
		coolDown = Constants.PoisonTrap_CoolDown_L1;
		trapSprite = new Sprite(Assets.manager.get(Assets.POISON_TRAP));
		sprite = new Sprite(Assets.manager.get(Assets.POISONED_TRAP_ICON));
		unlockScore = Constants.PoisonTrap_unlockScore;
	}

	@Override
	public void catchEnemy(Enemy enemy) {
		enemy.setNegativeEffect(NegativeEffects.POISONED);
		NegativeEffects.POISONED.lifetime = poisonLifeTimeByLVL.get(PoisonTrap.level-1);
		
		if(PoisonTrap.level>=1){
			enemy.poisonDamage = poisonDamageByLVL.get(PoisonTrap.level-1);
			enemy.poisonDamageInterval = poisonIntervalByLVL.get(PoisonTrap.level-1);
		}else{
			enemy.poisonDamage = poisonDamageByLVL.get(PoisonTrap.level);
			enemy.poisonDamageInterval = poisonIntervalByLVL.get(PoisonTrap.level);
		}
		this.position = null;
	}

	public Sprite getTrapSprite() {
		return trapSprite;
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
		String radiusString = "";
		if(level>0){
			int intuha = collDownByLevel.get(new Integer(level))-collDownByLevel.get(new Integer(level)-1);
			int lifeTdiff = lifeTimeByLevel.get(new Integer(level))-lifeTimeByLevel.get(new Integer(level)-1);
			int radiusDiff = radiusByLevel.get(new Integer(level))-radiusByLevel.get(new Integer(level)-1);
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
			if(radiusDiff>0){
				radiusString = "(+" + radiusDiff + ")";
			}else if(radiusDiff<0){
				radiusString = "(" + radiusDiff + ")";
			}
		}
		stats.add("t " + coolDown/60 + intuhaString);
		stats.add("h " + lifeTime/60 + lifeTimeString);
		stats.add("a " + circle.radius + radiusString);
		
		return stats;
	}
}
