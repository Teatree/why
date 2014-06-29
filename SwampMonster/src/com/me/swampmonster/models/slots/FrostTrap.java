package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class FrostTrap extends Trap{

	public static int level;
	
	public FrostTrap() {
		lifeTime = Constants.FrostTrap_LifeTime_L1;
		sprite = new Sprite(Assets.manager.get(Assets.FROST_TRAP_ICON));
		
		circle = new Circle();
		circle.radius = Constants.FrostTrap_CircleRadius_L1;
		
		trapSprite = new Sprite(Assets.manager.get(Assets.FROZEN_TRAP));
		coolDown = Constants.FrostTrap_CoolDown_L1;
	}

	public void catchEnemy(Enemy enemy) {
		enemy.setNegativeEffect(NegativeEffects.FROZEN);
	}
}
