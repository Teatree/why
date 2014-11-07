package com.me.swampmonster.models.items.wepMods;

import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Projectile.EffectCarriers;


public class EnemyBleedMod extends Modificator{
	
	public EnemyBleedMod() {
		super();
		setInfo();
	}

	@Override
	public void applyModificator(Projectile prj) {
		prj.effect = EffectCarriers.ENEMY_BLEED;
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}