package com.me.swampmonster.models.items;

import java.util.Random;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class NUKE extends Item{
	Explosion explosion;
	private int level;
	public static AssetDescriptor<Texture> poisonSprite;
	
	public NUKE() {
		super();
		animationsStandard.put(State.SPAWNING, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		
		explosion = new Explosion(new Vector2(), Explosion.EXPLOSION_TYPE_STANDART);
		explosion.isNuke = true;
		Random random = new Random();
		level = random.nextInt(5);
		switch (level) {
		case 0:
			explosion.explCircle.radius = Constants.NUKE_explCircleRadius_L1;
			explosion.damage = Constants.NUKE_damage_L1;
			explosion.incrementalCircleValue = Constants.NUKE_incrementExplosionRadius_L1;
			explosion.incrementalDamageValue = Constants.NUKE_incrementDamage_L1;
			break;
		case 1:
			explosion.explCircle.radius = Constants.NUKE_explCircleRadius_L2;
			explosion.damage = Constants.NUKE_damage_L2;
			explosion.incrementalCircleValue = Constants.NUKE_incrementExplosionRadius_L2;
			explosion.incrementalDamageValue = Constants.NUKE_incrementDamage_L2;
			break;
		case 2:
			explosion.explCircle.radius = Constants.NUKE_explCircleRadius_L3;
			explosion.damage = Constants.NUKE_damage_L3;
			explosion.incrementalCircleValue = Constants.NUKE_incrementExplosionRadius_L3;
			explosion.incrementalDamageValue = Constants.NUKE_incrementDamage_L3;
			break;
		case 3:
			explosion.explCircle.radius = Constants.NUKE_explCircleRadius_L4;
			explosion.damage = Constants.NUKE_damage_L4;
			explosion.incrementalCircleValue = Constants.NUKE_incrementExplosionRadius_L4;
			explosion.incrementalDamageValue = Constants.NUKE_incrementDamage_L4;
			break;
		case 4:
			explosion.explCircle.radius = Constants.NUKE_explCircleRadius_L5;
			explosion.damage = Constants.NUKE_damage_L5;
			explosion.incrementalCircleValue = Constants.NUKE_incrementExplosionRadius_L5;
			explosion.incrementalDamageValue = Constants.NUKE_incrementDamage_L5;
			break;
		}
		
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_TRAP_ICON));
		
		constatName = "NUKE";
	}

	@Override
	public void pickMeUp(Player player) {
		explosion.position.x = this.position.x+1;
		explosion.position.y = this.position.y+1;
		explosion.explCircle.setPosition(this.position.x, this.position.y);
		L1.explosions.add(explosion);
		state=State.DEAD;
	}

	@Override
	public void parametersForThrowing(Player player) {
		player.arrowEffectCarrier = EffectCarriers.NUKE;
		this.state = State.DEAD;
		
	}

}
