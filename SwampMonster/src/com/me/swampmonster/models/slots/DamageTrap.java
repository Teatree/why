package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class DamageTrap extends Trap {
	public float damage;
	public static int level;
	
	private static Map <Integer, String> descriptionByLevel;
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.DamageTrap_Description_L1);
		descriptionByLevel.put(1, Constants.DamageTrap_Description_L2);
		descriptionByLevel.put(2, Constants.DamageTrap_Description_L3);
		descriptionByLevel.put(3, Constants.DamageTrap_Description_L4);
		descriptionByLevel.put(4, Constants.DamageTrap_Description_L5);
	}

	public DamageTrap() {
		circle = new Circle();
		switch (level) {
		case 0:
			lifeTimeMax = Constants.DamageTrap_LifeTimeMax_L1;
			damage = Constants.DamageTrap_Damage_L1;
			coolDown = Constants.DamageTrap_CoolDown_L1;
			circle.radius = Constants.DamageTrap_CircleRadius_L1;
			break;
		case 1:
			lifeTimeMax = Constants.DamageTrap_LifeTimeMax_L2;
			damage = Constants.DamageTrap_Damage_L2;
			coolDown = Constants.DamageTrap_CoolDown_L2;
			circle.radius = Constants.DamageTrap_CircleRadius_L2;
			break;
		case 2:
			lifeTimeMax = Constants.DamageTrap_LifeTimeMax_L3;
			damage = Constants.DamageTrap_Damage_L3;
			coolDown = Constants.DamageTrap_CoolDown_L3;
			circle.radius = Constants.DamageTrap_CircleRadius_L3;
			break;
		case 3:
			lifeTimeMax = Constants.DamageTrap_LifeTimeMax_L4;
			damage = Constants.DamageTrap_Damage_L4;
			coolDown = Constants.DamageTrap_CoolDown_L4;
			circle.radius = Constants.DamageTrap_CircleRadius_L4;
			break;
		case 4:
			lifeTimeMax = Constants.DamageTrap_LifeTimeMax_L5;
			damage = Constants.DamageTrap_Damage_L5;
			coolDown = Constants.DamageTrap_CoolDown_L5;
			circle.radius = Constants.DamageTrap_CircleRadius_L5;
			break;
		}

		sprite = new Sprite(Assets.manager.get(Assets.DAMAGE_TRAP_ICON));
		trapSprite = new Sprite(Assets.manager.get(Assets.DAMAGE_TRAP));
	}

	@Override
	public void catchEnemy(Enemy enemy) {
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
}
