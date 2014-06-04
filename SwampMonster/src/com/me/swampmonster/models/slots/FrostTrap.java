package com.me.swampmonster.models.slots;

import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.enemies.Enemy;

public class FrostTrap extends Slot implements Trap{

	public int lifetime = 112;
	
	public void execute (Enemy enemy){
		enemy.health = 0;
	}

	@Override
	public void catchEnemy(Enemy enemy) {
		enemy.setNegativeEffect(NegativeEffects.FROZEN);
	}
}
