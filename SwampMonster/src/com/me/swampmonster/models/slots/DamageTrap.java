package com.me.swampmonster.models.slots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class DamageTrap extends Trap {
	public float damage;
	public static int level;
	
	public static Map<Integer, Integer> radiusByLevel;
	public static Map<Integer, Integer> collDownByLevel;
	public static Map<Integer, Float> damageByLevel;
	public static Map<Integer, Integer> lifeTimeByLevel;
	private static Map <Integer, String> descriptionByLevel;
	static {
		lifeTimeByLevel = new HashMap<Integer, Integer>();
		lifeTimeByLevel.put(0, Constants.DamageTrap_LifeTimeMax_L1);
		lifeTimeByLevel.put(1, Constants.DamageTrap_LifeTimeMax_L2);
		lifeTimeByLevel.put(2, Constants.DamageTrap_LifeTimeMax_L3);
		lifeTimeByLevel.put(3, Constants.DamageTrap_LifeTimeMax_L4);
		lifeTimeByLevel.put(4, Constants.DamageTrap_LifeTimeMax_L5);
		
		damageByLevel = new HashMap<Integer, Float>();
		damageByLevel.put(0, Constants.DamageTrap_Damage_L1);
		damageByLevel.put(1, Constants.DamageTrap_Damage_L2);
		damageByLevel.put(2, Constants.DamageTrap_Damage_L3);
		damageByLevel.put(3, Constants.DamageTrap_Damage_L4);
		damageByLevel.put(4, Constants.DamageTrap_Damage_L5);
		
		collDownByLevel = new HashMap<Integer, Integer>();
		collDownByLevel.put(0, Constants.DamageTrap_CoolDown_L1);
		collDownByLevel.put(1, Constants.DamageTrap_CoolDown_L2);
		collDownByLevel.put(2, Constants.DamageTrap_CoolDown_L3);
		collDownByLevel.put(3, Constants.DamageTrap_CoolDown_L4);
		collDownByLevel.put(4, Constants.DamageTrap_CoolDown_L5);
		
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.DamageTrap_Description_L1);
		descriptionByLevel.put(1, Constants.DamageTrap_Description_L2);
		descriptionByLevel.put(2, Constants.DamageTrap_Description_L3);
		descriptionByLevel.put(3, Constants.DamageTrap_Description_L4);
		descriptionByLevel.put(4, Constants.DamageTrap_Description_L5);
	}

	public DamageTrap() {
		name = Constants.DamageTrap_Name;
		circle = new Circle();
		
		lifeTimeMax = Constants.DamageTrap_LifeTimeMax_L1;
		damage = Constants.DamageTrap_Damage_L1;
		coolDown = Constants.DamageTrap_CoolDown_L1;
		sprite = new Sprite(Assets.manager.get(Assets.DAMAGE_TRAP_ICON));
		trapSprite = new Sprite(Assets.manager.get(Assets.DAMAGE_TRAP));
		
//		System.out.println("placing a dmg trap, pos: " + position);
		unlockScore = Constants.DamageTrap_unlockScore;
	}

	@Override
	public void catchEnemy(Enemy enemy) {
		System.out.println("a dmg trap, pos: aaaaaaaaaaaaa " + position);
//		effect.load(Gdx.files.local("effects/explosionEffect.p"), Gdx.files.local("effects"));
//		effect.setPosition(position.x, position.y);
//		effect.start();
//		showEffect = true;
		enemy.health -= damage;
		position = null;
	
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
		String dmgDifString = "";
		String lifeTimeString = "";
		if(level>0){
			int intuha = collDownByLevel.get(new Integer(level))-collDownByLevel.get(new Integer(level)-1);
			float dmgDif = damageByLevel.get(new Integer(level))-damageByLevel.get(new Integer(level)-1);
			int lifeTdiff = lifeTimeByLevel.get(new Integer(level))-lifeTimeByLevel.get(new Integer(level)-1);
			intuha = intuha/60;
			lifeTdiff = lifeTdiff/60;
			if(intuha>0){
				intuhaString = "(+" + intuha + ")"; 
			}else if(intuha<0){
				intuhaString = "(" + intuha + ")"; 
			}
			if(dmgDif>0){
				dmgDifString = "(+" + dmgDif + ")";
			}else if(dmgDif<0){
				dmgDifString = "(" + dmgDif + ")";
			}
			if(lifeTdiff>0){
				lifeTimeString = "(+" + lifeTdiff + ")";
			}else if(lifeTdiff<0){
				lifeTimeString = "(" + lifeTdiff + ")";
			}
		}
		stats.add("t " + coolDown/60 + intuhaString);
		stats.add("d " + damage + dmgDifString);
		stats.add("h " + lifeTime/60 + lifeTimeString);
		
		return stats;
	}
}
