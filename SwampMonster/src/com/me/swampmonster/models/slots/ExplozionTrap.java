package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class ExplozionTrap extends Trap{
	public int lifetime = 112;
	public static int level;
	private static Map <Integer, String> descriptionByLevel;
//	public Explosion explosion;
	public static boolean cuba;
	
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.ExplozionTrap_Description_L1);
		descriptionByLevel.put(1, Constants.ExplozionTrap_Description_L2);
		descriptionByLevel.put(2, Constants.ExplozionTrap_Description_L3);
		descriptionByLevel.put(3, Constants.ExplozionTrap_Description_L4);
		descriptionByLevel.put(4, Constants.ExplozionTrap_Description_L5);
	}
	
	public ExplozionTrap() {
		circle = new Circle();
		explosion = new Explosion(new Vector2(), Explosion.EXPLOSION_TYPE_STANDART);
		switch (level) {
		case 0:
			lifeTimeMax = Constants.ExplozionTrap_LifeTime_L1;
			coolDown = Constants.ExplozionTrap_CoolDown_L1;
			circle.radius = Constants.ExplozionTrap_CircleRadius_L1;
			explosion.explCircle.radius = Constants.ExplozionTrap_explCircleRadius_L1;
			explosion.damage = Constants.ExplozionTrap_damage_L1;
			explosion.incrementalCircleValue = Constants.ExplozionTrap_incrementExplosionRadius_L1;
			explosion.incrementalDamageValue = Constants.ExplozionTrap_incrementDamage_L1;
			break;
		case 1:
			lifeTimeMax = Constants.ExplozionTrap_LifeTime_L2;
			coolDown = Constants.ExplozionTrap_CoolDown_L2;
			circle.radius = Constants.ExplozionTrap_CircleRadius_L2;
			explosion.explCircle.radius = Constants.ExplozionTrap_explCircleRadius_L2;
			explosion.damage = Constants.ExplozionTrap_damage_L2;
			explosion.incrementalCircleValue = Constants.ExplozionTrap_incrementExplosionRadius_L2;
			explosion.incrementalDamageValue = Constants.ExplozionTrap_incrementDamage_L2;
			break;
		case 2:
			lifeTimeMax = Constants.ExplozionTrap_LifeTime_L3;
			coolDown = Constants.ExplozionTrap_CoolDown_L3;
			circle.radius = Constants.ExplozionTrap_CircleRadius_L3;
			explosion.explCircle.radius = Constants.ExplozionTrap_explCircleRadius_L3;
			explosion.damage = Constants.ExplozionTrap_damage_L3;
			explosion.incrementalCircleValue = Constants.ExplozionTrap_incrementExplosionRadius_L3;
			explosion.incrementalDamageValue = Constants.ExplozionTrap_incrementDamage_L3;
			break;
		case 3:
			lifeTimeMax = Constants.ExplozionTrap_LifeTime_L4;
			coolDown = Constants.ExplozionTrap_CoolDown_L4;
			circle.radius = Constants.ExplozionTrap_CircleRadius_L4;
			explosion.explCircle.radius = Constants.ExplozionTrap_explCircleRadius_L4;
			explosion.damage = Constants.ExplozionTrap_damage_L4;
			explosion.incrementalCircleValue = Constants.ExplozionTrap_incrementExplosionRadius_L4;
			explosion.incrementalDamageValue = Constants.ExplozionTrap_incrementDamage_L4;
			break;
		case 4:
			lifeTimeMax = Constants.ExplozionTrap_LifeTime_L5;
			coolDown = Constants.ExplozionTrap_CoolDown_L5;
			circle.radius = Constants.ExplozionTrap_CircleRadius_L5;
			explosion.explCircle.radius = Constants.ExplozionTrap_explCircleRadius_L5;
			explosion.damage = Constants.ExplozionTrap_damage_L5;
			explosion.incrementalCircleValue = Constants.ExplozionTrap_incrementExplosionRadius_L5;
			explosion.incrementalDamageValue = Constants.ExplozionTrap_incrementDamage_L5;
			break;
		}
		
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_TRAP_ICON));
		trapSprite = new Sprite(Assets.manager.get(Assets.explosiveTrap));
	}
	
	@Override
	public void catchEnemy(Enemy enemy) {
		
		
		if (!cuba) {
			explosion = new Explosion(new Vector2(), Explosion.EXPLOSION_TYPE_STANDART);
			explosion.explCircle = new Circle();
			switch (level) {
			case 0:
				explosion.explCircle.radius = Constants.ExplozionTrap_explCircleRadius_L1;
				explosion.damage = Constants.ExplozionTrap_damage_L1;
				explosion.incrementalCircleValue = Constants.ExplozionTrap_incrementExplosionRadius_L1;
				explosion.incrementalDamageValue = Constants.ExplozionTrap_incrementDamage_L1;
				break;
			case 1:
				explosion.explCircle.radius = Constants.ExplozionTrap_explCircleRadius_L2;
				explosion.damage = Constants.ExplozionTrap_damage_L2;
				explosion.incrementalCircleValue = Constants.ExplozionTrap_incrementExplosionRadius_L2;
				explosion.incrementalDamageValue = Constants.ExplozionTrap_incrementDamage_L2;
				break;
			case 2:
				explosion.explCircle.radius = Constants.ExplozionTrap_explCircleRadius_L3;
				explosion.damage = Constants.ExplozionTrap_damage_L3;
				explosion.incrementalCircleValue = Constants.ExplozionTrap_incrementExplosionRadius_L3;
				explosion.incrementalDamageValue = Constants.ExplozionTrap_incrementDamage_L3;
				break;
			case 3:
				explosion.explCircle.radius = Constants.ExplozionTrap_explCircleRadius_L4;
				explosion.damage = Constants.ExplozionTrap_damage_L4;
				explosion.incrementalCircleValue = Constants.ExplozionTrap_incrementExplosionRadius_L4;
				explosion.incrementalDamageValue = Constants.ExplozionTrap_incrementDamage_L4;
				break;
			case 4:
				explosion.explCircle.radius = Constants.ExplozionTrap_explCircleRadius_L5;
				explosion.damage = Constants.ExplozionTrap_damage_L5;
				explosion.incrementalCircleValue = Constants.ExplozionTrap_incrementExplosionRadius_L5;
				explosion.incrementalDamageValue = Constants.ExplozionTrap_incrementDamage_L5;
				break;
			}
			
			
			explosion.position = this.position;
			explosion.explCircle.setPosition(this.position.x, this.position.y);
//			explosion.explosionEffect = new ParticleEffect();
//			explosion.explosionEffect.load(Gdx.files.local("effects/FlameEffectTemp.p"),Gdx.files.local("effects"));
//			explosion.explosionEffect.setPosition(this.position.x,this.position.y);
//			explosion.explosionEffect.start();
			L1.explosions.add(explosion);
			cuba = true;
		} else {
			this.position = null;
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
}
