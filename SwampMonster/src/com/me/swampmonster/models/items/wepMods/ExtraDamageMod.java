package com.me.swampmonster.models.items.wepMods;

import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Projectile.EffectCarriers;


public class ExtraDamageMod extends Modificator{
	
	public ExtraDamageMod() {
		super();
		setInfo();
	}

	@Override
	public void applyModificator(Projectile prj) {
		prj.effect = EffectCarriers.EXTRADAMAGE;
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}
