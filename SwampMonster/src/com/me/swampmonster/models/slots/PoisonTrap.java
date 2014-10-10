package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class PoisonTrap extends Trap{
	public static int level;
	private static Map <Integer, String> descriptionByLevel;
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.PoisonTrap_Description_L1);
		descriptionByLevel.put(1, Constants.PoisonTrap_Description_L2);
		descriptionByLevel.put(2, Constants.PoisonTrap_Description_L3);
		descriptionByLevel.put(3, Constants.PoisonTrap_Description_L4);
		descriptionByLevel.put(4, Constants.PoisonTrap_Description_L5);
	}
	
	public PoisonTrap() {
		lifeTime = Constants.PoisonTrap_LifeTime_L1;
		circle = new Circle();
		circle.radius = Constants.PoisonTrap_Radius_L1;
		name = Constants.PoisonTrap_Name;
		switch (level) {
		case 0:
			lifeTimeMax = Constants.PoisonTrap_LifeTime_L1;
			coolDown = Constants.PoisonTrap_CoolDown_L1;
			circle.radius = Constants.PoisonTrap_Radius_L1;
			break;
		case 1:
			lifeTimeMax = Constants.PoisonTrap_LifeTime_L2;
			coolDown = Constants.PoisonTrap_CoolDown_L2;
			circle.radius = Constants.PoisonTrap_Radius_L2;
			break;
		case 2:
			lifeTimeMax = Constants.PoisonTrap_LifeTime_L3;
			coolDown = Constants.PoisonTrap_CoolDown_L3;
			circle.radius = Constants.PoisonTrap_Radius_L3;
			break;
		case 3:
			lifeTimeMax = Constants.PoisonTrap_LifeTime_L4;
			coolDown = Constants.PoisonTrap_CoolDown_L4;
			circle.radius = Constants.PoisonTrap_Radius_L4;
			break;
		case 4:
			lifeTimeMax = Constants.PoisonTrap_LifeTime_L5;
			coolDown = Constants.PoisonTrap_CoolDown_L5;
			circle.radius = Constants.PoisonTrap_Radius_L5;
			break;
		}
		
		trapSprite = new Sprite(Assets.manager.get(Assets.POISON_TRAP));
		sprite = new Sprite(Assets.manager.get(Assets.POISONED_TRAP_ICON));
		
	}

	@Override
	public void catchEnemy(Enemy enemy) {
		enemy.setNegativeEffect(NegativeEffects.POISONED);
		this.position = null;
	}

	public Sprite getTrapSprite() {
		return trapSprite;
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
