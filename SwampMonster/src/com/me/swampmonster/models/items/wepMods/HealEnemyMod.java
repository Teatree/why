package com.me.swampmonster.models.items.wepMods;

import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Projectile.EffectCarriers;


public class HealEnemyMod extends Modificator{
	
	public HealEnemyMod() {
		super();
		setInfo();
	}

	@Override
	public void applyModificator(Projectile prj) {
		prj.effect = EffectCarriers.HEAL_ENEMY;
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}
