package com.me.swampmonster.models.items.wepMods;

import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Projectile.EffectCarriers;


public class ShootThoughCollisionMod extends Modificator{
	
	public ShootThoughCollisionMod() {
		super();
		setInfo();
		name = "Ghost's";
	}

	@Override
	public void applyModificator(Projectile prj) {
		prj.effect = EffectCarriers.SHADOW;
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}
