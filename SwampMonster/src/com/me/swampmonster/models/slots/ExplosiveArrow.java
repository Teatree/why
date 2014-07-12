package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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
	private static Map <Integer, String> descriptionByLevel;
	public Explosion explosion; 
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.ExplosiveArrow_Description_L1);
		descriptionByLevel.put(1, Constants.ExplosiveArrow_Description_L2);
		descriptionByLevel.put(2, Constants.ExplosiveArrow_Description_L3);
		descriptionByLevel.put(3, Constants.ExplosiveArrow_Description_L4);
		descriptionByLevel.put(4, Constants.ExplosiveArrow_Description_L5);
	}
	
	public ExplosiveArrow() {
		explosion = new Explosion(null);
		switch (level) {
		case 0:
			coolDown = Constants.ExplosiveArrow_CoolDown_L1;
			explosion.explCircle.radius = Constants.ExplosiveArrow_explCircleRadius_L1;
			explosion.damage = Constants.ExplosiveArrow_damage_L1;
			explosion.incrementalCircleValue = Constants.ExplosiveArrow_incrementExplosionRadius_L1;
			explosion.incrementalDamageValue = Constants.ExplosiveArrow_incrementDamage_L1;
			break;
		case 1:
			coolDown = Constants.ExplosiveArrow_CoolDown_L2;
			explosion.explCircle.radius = Constants.ExplosiveArrow_explCircleRadius_L2;
			explosion.damage = Constants.ExplosiveArrow_damage_L2;
			explosion.incrementalCircleValue = Constants.ExplosiveArrow_incrementExplosionRadius_L2;
			explosion.incrementalDamageValue = Constants.ExplosiveArrow_incrementDamage_L2;
			break;
		case 2:
			coolDown = Constants.ExplosiveArrow_CoolDown_L3;
			explosion.explCircle.radius = Constants.ExplosiveArrow_explCircleRadius_L3;
			explosion.damage = Constants.ExplosiveArrow_damage_L3;
			explosion.incrementalCircleValue = Constants.ExplosiveArrow_incrementExplosionRadius_L3;
			explosion.incrementalDamageValue = Constants.ExplosiveArrow_incrementDamage_L3;
			break;
		case 3:
			coolDown = Constants.ExplosiveArrow_CoolDown_L4;
			explosion.explCircle.radius = Constants.ExplosiveArrow_explCircleRadius_L4;
			explosion.damage = Constants.ExplosiveArrow_damage_L4;
			explosion.incrementalCircleValue = Constants.ExplosiveArrow_incrementExplosionRadius_L4;
			explosion.incrementalDamageValue = Constants.ExplosiveArrow_incrementDamage_L4;
			break;
		case 4:
			coolDown = Constants.ExplosiveArrow_CoolDown_L5;
			explosion.explCircle.radius = Constants.ExplosiveArrow_explCircleRadius_L5;
			explosion.damage = Constants.ExplosiveArrow_damage_L5;
			explosion.incrementalCircleValue = Constants.ExplosiveArrow_incrementExplosionRadius_L5;
			explosion.incrementalDamageValue = Constants.ExplosiveArrow_incrementDamage_L5;
			break;
		}
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_ARROW_ICON));
	}
	
	public void execute(Player player){
		player.arrowEffectCarrier = EffectCarriers.EXPLOSIVE;
	}

	public void explode(Vector2 pos) {
//		if (!cuba) {
			explosion.position = pos;
			explosion.explCircle.setPosition(pos.x, pos.y);
			explosion.explosionEffect = new ParticleEffect();
			explosion.explosionEffect.load(Gdx.files.local("effects/explosionEffect.p"),Gdx.files.local("effects"));
			explosion.explosionEffect.setPosition(pos.x, pos.y);
			explosion.explosionEffect.start();
			L1.explosions.add(explosion);
//			cuba = true;
//		} else {
//			this.position = null;
//		}
	}
	@Override
	public String getDescription() {
		return descriptionByLevel.get(level);
	}
}
