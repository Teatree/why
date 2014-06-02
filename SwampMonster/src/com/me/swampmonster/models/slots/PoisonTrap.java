package com.me.swampmonster.models.slots;

import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.enemies.Enemy;

public class PoisonTrap {
	public int lifetime = 112;
	
	public void execute (Enemy enemy){
		enemy.negativeEffectsState = NegativeEffects.POISONED;
	}
}
