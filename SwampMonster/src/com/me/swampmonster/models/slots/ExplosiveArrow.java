package com.me.swampmonster.models.slots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class ExplosiveArrow extends Slot{
	
	public static int level;
	public static int actualLevel;
	public static Map<Integer, Integer> collDownByLevel;
	private static Map <Integer, String> descriptionByLevel;
	public Explosion explosion; 
	static {
		collDownByLevel = new HashMap<Integer, Integer>();
		collDownByLevel.put(0, Constants.ExplosiveArrow_CoolDown_L1);
		collDownByLevel.put(1, Constants.ExplosiveArrow_CoolDown_L2);
		collDownByLevel.put(2, Constants.ExplosiveArrow_CoolDown_L3);
		collDownByLevel.put(3, Constants.ExplosiveArrow_CoolDown_L4);
		collDownByLevel.put(4, Constants.ExplosiveArrow_CoolDown_L5);
		
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.ExplosiveArrow_Description_L1);
		descriptionByLevel.put(1, Constants.ExplosiveArrow_Description_L2);
		descriptionByLevel.put(2, Constants.ExplosiveArrow_Description_L3);
		descriptionByLevel.put(3, Constants.ExplosiveArrow_Description_L4);
		descriptionByLevel.put(4, Constants.ExplosiveArrow_Description_L5);
	}
	
	public ExplosiveArrow() {
		name = Constants.ExplosiveArrow_Name;
		coolDown = Constants.ExplosiveArrow_CoolDown_L1;
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_ARROW_ICON));
	}
	
	@Override
	public void execute(Player player){
		player.arrowEffectCarrier = EffectCarriers.EXPLOSIVE;
	}

	@Override
	public void explode(Vector2 pos) {
		System.out.println("explode arrow");
		explosion = new Explosion(new Vector2(), Explosion.EXPLOSION_TYPE_STANDART);
		switch (level) {
		case 0:
			explosion.explCircle.radius = Constants.ExplosiveArrow_explCircleRadius_L1;
			explosion.damage = Constants.ExplosiveArrow_damage_L1;
			explosion.incrementalCircleValue = Constants.ExplosiveArrow_incrementExplosionRadius_L1;
			explosion.incrementalDamageValue = Constants.ExplosiveArrow_incrementDamage_L1;
			break;
		case 1:
			explosion.explCircle.radius = Constants.ExplosiveArrow_explCircleRadius_L2;
			explosion.damage = Constants.ExplosiveArrow_damage_L2;
			explosion.incrementalCircleValue = Constants.ExplosiveArrow_incrementExplosionRadius_L2;
			explosion.incrementalDamageValue = Constants.ExplosiveArrow_incrementDamage_L2;
			break;
		case 2:
			explosion.explCircle.radius = Constants.ExplosiveArrow_explCircleRadius_L3;
			explosion.damage = Constants.ExplosiveArrow_damage_L3;
			explosion.incrementalCircleValue = Constants.ExplosiveArrow_incrementExplosionRadius_L3;
			explosion.incrementalDamageValue = Constants.ExplosiveArrow_incrementDamage_L3;
			break;
		case 3:
			explosion.explCircle.radius = Constants.ExplosiveArrow_explCircleRadius_L4;
			explosion.damage = Constants.ExplosiveArrow_damage_L4;
			explosion.incrementalCircleValue = Constants.ExplosiveArrow_incrementExplosionRadius_L4;
			explosion.incrementalDamageValue = Constants.ExplosiveArrow_incrementDamage_L4;
			break;
		case 4:
			explosion.explCircle.radius = Constants.ExplosiveArrow_explCircleRadius_L5;
			explosion.damage = Constants.ExplosiveArrow_damage_L5;
			explosion.incrementalCircleValue = Constants.ExplosiveArrow_incrementExplosionRadius_L5;
			explosion.incrementalDamageValue = Constants.ExplosiveArrow_incrementDamage_L5;
			break;
		}
			explosion.position = pos;
			explosion.explCircle.setPosition(pos.x, pos.y);
			L1.explosions.add(explosion);
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
