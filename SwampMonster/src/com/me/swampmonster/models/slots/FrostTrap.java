package com.me.swampmonster.models.slots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class FrostTrap extends Trap{

	public static int level;
	
	public static Map<Integer, Integer> radiusByLevel;
	public static Map<Integer, Integer> collDownByLevel;
	public static Map<Integer, Integer> lifeTimeByLevel;
	public static Map<Integer, Integer> frostLifeTimeByLevel;
	private static Map <Integer, String> descriptionByLevel;
	private boolean cuba;
	
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.FrostTrap_Description_L1);
		descriptionByLevel.put(1, Constants.FrostTrap_Description_L2);
		descriptionByLevel.put(2, Constants.FrostTrap_Description_L3);
		descriptionByLevel.put(3, Constants.FrostTrap_Description_L4);
		descriptionByLevel.put(4, Constants.FrostTrap_Description_L5);
		
		radiusByLevel = new HashMap<Integer, Integer>();
		radiusByLevel.put(0, Constants.FrostTrap_CircleRadius_L1);
		radiusByLevel.put(1, Constants.FrostTrap_CircleRadius_L2);
		radiusByLevel.put(2, Constants.FrostTrap_CircleRadius_L3);
		radiusByLevel.put(3, Constants.FrostTrap_CircleRadius_L4);
		radiusByLevel.put(4, Constants.FrostTrap_CircleRadius_L5);
		
		frostLifeTimeByLevel = new HashMap<Integer, Integer>();
		frostLifeTimeByLevel.put(0, Constants.FrostTrap_FrostLifeTime_L1);
		frostLifeTimeByLevel.put(1, Constants.FrostTrap_FrostLifeTime_L2);
		frostLifeTimeByLevel.put(2, Constants.FrostTrap_FrostLifeTime_L3);
		frostLifeTimeByLevel.put(3, Constants.FrostTrap_FrostLifeTime_L4);
		frostLifeTimeByLevel.put(4, Constants.FrostTrap_FrostLifeTime_L5);
		
		lifeTimeByLevel = new HashMap<Integer, Integer>();
		lifeTimeByLevel.put(0, Constants.FrostTrap_LifeTime_L1);
		lifeTimeByLevel.put(1, Constants.FrostTrap_LifeTime_L2);
		lifeTimeByLevel.put(2, Constants.FrostTrap_LifeTime_L3);
		lifeTimeByLevel.put(3, Constants.FrostTrap_LifeTime_L4);
		lifeTimeByLevel.put(4, Constants.FrostTrap_LifeTime_L5);
		
		collDownByLevel = new HashMap<Integer, Integer>();
		collDownByLevel.put(0, Constants.FrostTrap_CoolDown_L1);
		collDownByLevel.put(1, Constants.FrostTrap_CoolDown_L2);
		collDownByLevel.put(2, Constants.FrostTrap_CoolDown_L3);
		collDownByLevel.put(3, Constants.FrostTrap_CoolDown_L4);
		collDownByLevel.put(4, Constants.FrostTrap_CoolDown_L5);
	}
	
	public FrostTrap() {
		name = Constants.FrostTrap_Name;
		sprite = new Sprite(Assets.manager.get(Assets.FROST_TRAP_ICON));
		circle = new Circle();
		
		circle.radius = Constants.FrostTrap_CircleRadius_L1;
		lifeTimeMax = Constants.FrostTrap_LifeTime_L1;
		coolDown = Constants.FrostTrap_CoolDown_L1;
		trapSprite = new Sprite(Assets.manager.get(Assets.FROST_TRAP_ICON));
		unlockScore = Constants.FrostTrap_unlockScore;
	}

	@Override
	public void catchEnemy(Enemy enemy) {
		if (!cuba && position!= null) {
			explosion = new Explosion(this.position, Explosion.EXPLOSION_TYPE_FROST);
			if(FrostTrap.level>=1){
				explosion.frozenLifeTime = frostLifeTimeByLevel.get(FrostTrap.level-1);
			}else{
				explosion.frozenLifeTime = frostLifeTimeByLevel.get(FrostTrap.level);
			}
			explosion.damage = 0;
			explosion.incrementalDamageValue = 0;
			explosion.incrementalCircleValue = 6;
			explosion.explCircle.setPosition(this.position.x, this.position.y);
			explosion.explCircle.radius = 1f;
			L1.explosions.add(explosion);
			cuba = true;
		} else {
			this.position = null;
		}
	}
	
	@Override
	public void execute(Player player){
		super.execute(player);
		cuba = false;
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
