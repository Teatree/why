package com.me.swampmonster.models.slots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Turret;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class TurretSkill extends Slot {

	public Turret turret;
	public static int level;
	public static Map<Integer, Integer> collDownByLevel;
	public static Map<Integer, Float> minDamageByLevel;
	public static Map<Integer, Float> maxDamageByLevel;
	public static Map<Integer, Integer> lifeTimeByLevel;
	public static Map<Integer, Integer> attackSpeedByLevel;
	public static Map<Integer, Integer> healthByLevel;
	private static Map <Integer, String> descriptionByLevel;
	static {
		minDamageByLevel = new HashMap<Integer, Float>();
		minDamageByLevel.put(0, Constants.TURRET_min_Damage_L1);
		minDamageByLevel.put(1, Constants.TURRET_min_Damage_L2);
		minDamageByLevel.put(2, Constants.TURRET_min_Damage_L3);
		minDamageByLevel.put(3, Constants.TURRET_min_Damage_L4);
		minDamageByLevel.put(4, Constants.TURRET_min_Damage_L5);
		
		maxDamageByLevel = new HashMap<Integer, Float>();
		maxDamageByLevel.put(0, Constants.TURRET_max_Damage_L1);
		maxDamageByLevel.put(1, Constants.TURRET_max_Damage_L2);
		maxDamageByLevel.put(2, Constants.TURRET_max_Damage_L3);
		maxDamageByLevel.put(3, Constants.TURRET_max_Damage_L4);
		maxDamageByLevel.put(4, Constants.TURRET_max_Damage_L5);
		
		attackSpeedByLevel = new HashMap<Integer, Integer>();
		attackSpeedByLevel.put(0, Constants.TURRET_AttackSpeed_L1);
		attackSpeedByLevel.put(1, Constants.TURRET_AttackSpeed_L1);
		attackSpeedByLevel.put(2, Constants.TURRET_AttackSpeed_L1);
		attackSpeedByLevel.put(3, Constants.TURRET_AttackSpeed_L1);
		attackSpeedByLevel.put(4, Constants.TURRET_AttackSpeed_L1);
		
		healthByLevel = new HashMap<Integer, Integer>();
		healthByLevel.put(0, Constants.TURRET_Health_L1);
		healthByLevel.put(1, Constants.TURRET_Health_L2);
		healthByLevel.put(2, Constants.TURRET_Health_L3);
		healthByLevel.put(3, Constants.TURRET_Health_L4);
		healthByLevel.put(4, Constants.TURRET_Health_L5);
		
		lifeTimeByLevel = new HashMap<Integer, Integer>();
		lifeTimeByLevel.put(0, Constants.TURRET_LifeTime_L1);
		lifeTimeByLevel.put(1, Constants.TURRET_LifeTime_L2);
		lifeTimeByLevel.put(2, Constants.TURRET_LifeTime_L3);
		lifeTimeByLevel.put(3, Constants.TURRET_LifeTime_L4);
		lifeTimeByLevel.put(4, Constants.TURRET_LifeTime_L5);
		
		collDownByLevel = new HashMap<Integer, Integer>();
		collDownByLevel.put(0, Constants.TURRET_CoolDown_L1);
		collDownByLevel.put(1, Constants.TURRET_CoolDown_L2);
		collDownByLevel.put(2, Constants.TURRET_CoolDown_L3);
		collDownByLevel.put(3, Constants.TURRET_CoolDown_L4);
		collDownByLevel.put(4, Constants.TURRET_CoolDown_L5);
		
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.TURRET_Description_L1);
		descriptionByLevel.put(1, Constants.TURRET_Description_L2);
		descriptionByLevel.put(2, Constants.TURRET_Description_L3);
		descriptionByLevel.put(3, Constants.TURRET_Description_L4);
		descriptionByLevel.put(4, Constants.TURRET_Description_L5);
	}

	public TurretSkill() {
		name = Constants.TURRET_Name;
		sprite = new Sprite(Assets.manager.get(Assets.TURRET_ICON));
		turret = new Turret();
		turret.state = State.SPAWNING;
		turret.health = Constants.TURRET_Health_L1;
		turret.minDD = Constants.TURRET_min_Damage_L1;
		turret.maxDD = Constants.TURRET_max_Damage_L1;
		turret.attackSpeed = Constants.TURRET_AttackSpeed_L1;
		
		lifeTime = Constants.TURRET_LifeTime_L1;
		coolDown = Constants.TURRET_CoolDown_L1;
		unlockScore = Constants.TURRET_unlockScore;
	}

	@Override
	public void execute(Player player) {
		System.out.println("execute, turret: " + player.turret);
		
		switch (level) {
		case 0:
			turret.minDD = Constants.TURRET_min_Damage_L1;
			turret.maxDD = Constants.TURRET_max_Damage_L1;
			turret.health = Constants.TURRET_Health_L1;
			turret.attackSpeed = Constants.TURRET_AttackSpeed_L1;
			turret.lifeTime = Constants.TURRET_LifeTime_L1;
			coolDown = Constants.TURRET_CoolDown_L1;
			break;
		case 1:
			turret.minDD = Constants.TURRET_min_Damage_L2;
			turret.maxDD = Constants.TURRET_max_Damage_L2;
			turret.health = Constants.TURRET_Health_L2;
			turret.attackSpeed = Constants.TURRET_AttackSpeed_L2;
			turret.lifeTime = Constants.TURRET_LifeTime_L2;
			coolDown = Constants.TURRET_CoolDown_L2;
			break;
		case 2:
			turret.minDD = Constants.TURRET_min_Damage_L3;
			turret.maxDD = Constants.TURRET_max_Damage_L3;
			turret.health = Constants.TURRET_Health_L3;
			turret.attackSpeed = Constants.TURRET_AttackSpeed_L3;
			turret.lifeTime = Constants.TURRET_LifeTime_L3;
			coolDown = Constants.TURRET_CoolDown_L3;
			break;
		case 3:
			turret.minDD = Constants.TURRET_min_Damage_L4;
			turret.maxDD = Constants.TURRET_max_Damage_L4;
			turret.health = Constants.TURRET_Health_L4;
			turret.attackSpeed = Constants.TURRET_AttackSpeed_L4;
			turret.lifeTime = Constants.TURRET_LifeTime_L4;
			coolDown = Constants.TURRET_CoolDown_L4;
			break;
		case 4:
			turret.minDD = Constants.TURRET_min_Damage_L5;
			turret.maxDD = Constants.TURRET_max_Damage_L5;
			turret.health = Constants.TURRET_Health_L5;
			turret.attackSpeed = Constants.TURRET_AttackSpeed_L5;
			turret.lifeTime = Constants.TURRET_LifeTime_L5;
			coolDown = Constants.TURRET_CoolDown_L5;
			break;
		}
		
		turret.standardLifeTime = turret.lifeTime - 40;
		
		player.turret = this.turret;
		player.turret.state = State.SPAWNING;
		player.turret.position = new Vector2(player.position.x,
				player.position.y);
//		System.out.println("turret.pos : " + player.turret.position);
//		System.out.println("turret.STATE : " + player.turret.state);
//		System.out.println("turret.sprite : " + player.turret.sprite);
		player.turret.killingAura = new Circle();
		player.turret.killingAura.x = player.turret.position.x;
		player.turret.killingAura.y = player.turret.position.y;
		player.turret.killingAura.radius = 128;
		player.turret.turretAimerBot.x = player.turret.position.x;
		player.turret.turretAimerBot.y = player.turret.position.y;
		player.turret.turretAimerBot.width = 5;
		player.turret.turretAimerBot.height = 5;
		player.turret.circle = new Circle();
		player.turret.circle.radius = 16;
		player.turret.circle.x = player.turret.position.x
				+ player.turret.sprite.getWidth() / 2;
		player.turret.circle.y = player.turret.position.y
				+ player.turret.sprite.getHeight() / 2;
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
		String healthString = "";
		String attackSpeedString = "";
		if(level>0){
			int intuha = collDownByLevel.get(new Integer(level))-collDownByLevel.get(new Integer(level)-1);
			float dmgDif = maxDamageByLevel.get(new Integer(level))-maxDamageByLevel.get(new Integer(level)-1);
			int lifeTdiff = lifeTimeByLevel.get(new Integer(level))-lifeTimeByLevel.get(new Integer(level)-1);
			int healthDiff = healthByLevel.get(new Integer(level))-healthByLevel.get(new Integer(level)-1);
			int aSDiff = attackSpeedByLevel.get(new Integer(level))-attackSpeedByLevel.get(new Integer(level)-1);
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
			if(healthDiff>0){
				healthString = "(+" + healthDiff + ")";
			}else if(healthDiff<0){
				healthString = "(" + healthDiff + ")";
			}
			if(aSDiff>0){
				attackSpeedString = "(+" + aSDiff + ")";
			}else if(aSDiff<0){
				attackSpeedString = "(" + aSDiff + ")";
			}
		}
		stats.add("t " + coolDown/60 + intuhaString);
		stats.add("d " + turret.maxDD + dmgDifString);
		stats.add("m " + turret.attackSpeed/60 + attackSpeedString);
		stats.add("g " + turret.health + healthString);
		stats.add("h " + lifeTime/60 + lifeTimeString);
		
		return stats;
	}
}
