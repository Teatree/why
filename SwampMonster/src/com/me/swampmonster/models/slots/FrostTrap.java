package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class FrostTrap extends Trap{

	public static int level;
	private static Map <Integer, String> descriptionByLevel;
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.FrostTrap_Description_L1);
		descriptionByLevel.put(1, Constants.FrostTrap_Description_L2);
		descriptionByLevel.put(2, Constants.FrostTrap_Description_L3);
		descriptionByLevel.put(3, Constants.FrostTrap_Description_L4);
		descriptionByLevel.put(4, Constants.FrostTrap_Description_L5);
	}
	
	public FrostTrap() {
		sprite = new Sprite(Assets.manager.get(Assets.FROST_TRAP_ICON));
		circle = new Circle();
		
		switch (level) {
		case 0:
			lifeTime = Constants.FrostTrap_LifeTime_L1;
			coolDown = Constants.FrostTrap_CoolDown_L1;
			circle.radius = Constants.FrostTrap_CircleRadius_L1;
			break;
		case 1:
			lifeTime = Constants.FrostTrap_LifeTime_L2;
			coolDown = Constants.FrostTrap_CoolDown_L2;
			circle.radius = Constants.FrostTrap_CircleRadius_L2;
			break;
		case 2:
			lifeTime = Constants.FrostTrap_LifeTime_L3;
			coolDown = Constants.FrostTrap_CoolDown_L3;
			circle.radius = Constants.FrostTrap_CircleRadius_L3;
			break;
		case 3:
			lifeTime = Constants.FrostTrap_LifeTime_L4;
			coolDown = Constants.FrostTrap_CoolDown_L4;
			circle.radius = Constants.FrostTrap_CircleRadius_L4;
			break;
		case 4:
			lifeTime = Constants.FrostTrap_LifeTime_L5;
			coolDown = Constants.FrostTrap_CoolDown_L5;
			circle.radius = Constants.FrostTrap_CircleRadius_L5;
			break;
		}
		
		trapSprite = new Sprite(Assets.manager.get(Assets.FROZEN_TRAP));
		coolDown = Constants.FrostTrap_CoolDown_L1;
	}

	public void catchEnemy(Enemy enemy) {
		enemy.setNegativeEffect(NegativeEffects.FROZEN);
	}

	public String getDescription() {
		return descriptionByLevel.get(level);

	}
}
