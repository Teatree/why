package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.AssetsMainManager;

public class FrostTrap extends Trap{

	public FrostTrap() {
		lifeTime = 600;
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.FROST_TRAP_ICON));
		
		circle = new Circle();
		circle.radius = 16;
		
		trapSprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.FROZEN_TRAP));
	}

	public void catchEnemy(Enemy enemy) {
		enemy.setNegativeEffect(NegativeEffects.FROZEN);
	}
}
