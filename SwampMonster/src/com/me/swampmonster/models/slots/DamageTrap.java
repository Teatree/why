package com.me.swampmonster.models.slots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
//import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class DamageTrap extends Trap {
	public int lifetime = 9000;
	public float damage;
	public static int level;

	public DamageTrap() {
		switch (level) {
		case 0:
			lifeTimeMax = Constants.DamageTrap_LifeTimeMax_L1;
			damage = Constants.DamageTrap_Damage_L1;
			coolDown = Constants.DamageTrap_CoolDown_L1;
			break;
		case 1:
			lifeTimeMax = 1000;
			damage = 1;
			coolDown = 2500;
			break;
		case 2:
			lifeTimeMax = 1500;
			damage = 3;
			coolDown = 2000;
			break;
		case 3:
			lifeTimeMax = 1500;
			damage = 3;
			coolDown = 2000;
			break;
		case 4:
			lifeTimeMax = 5100;
			damage = 4;
			coolDown = 2000;
			break;
		}
		effect = new ParticleEffect();
		effect.load(Gdx.files.local("effects\\FlameEffectTemp.p"),
				Gdx.files.local("effects"));
		circle = new Circle();
		circle.radius = 8;

		// :TODO Had to change because images didn't weren't commited and I need
		// a working version now
		sprite = new Sprite(Assets.manager.get(Assets.DAMAGE_TRAP_ICON));
		trapSprite = new Sprite(Assets.manager.get(Assets.DAMAGE_TRAP));
		coolDown = 600;
	}

	public void catchEnemy(Enemy enemy) {
		effect.load(Gdx.files.local("effects/FlameEffectTemp.p"), Gdx.files.local("effects"));
		effect.setPosition(position.x, position.y);
		effect.start();
		showEffect = true;
		enemy.health -= damage;
	}
}
