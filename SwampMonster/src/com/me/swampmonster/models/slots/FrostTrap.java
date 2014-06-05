package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.AssetsMainManager;

public class FrostTrap extends Trap{

	public int lifetime = 112;
	
	public FrostTrap() {
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.FROST_TRAP_ICON));
	}

	public void catchEnemy(Enemy enemy) {
		enemy.setNegativeEffect(NegativeEffects.FROZEN);
	}
}
