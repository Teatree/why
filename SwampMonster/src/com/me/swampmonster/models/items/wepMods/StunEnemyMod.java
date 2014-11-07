package com.me.swampmonster.models.items.wepMods;

import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Projectile.EffectCarriers;


public class StunEnemyMod extends Modificator{
	
	public StunEnemyMod() {
		super();
		setInfo();
	}

	@Override
	public void applyModificator(Projectile prj) {
		prj.effect = EffectCarriers.SPEEDUP_ENEMY;
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}