package com.me.swampmonster.models.items.wepMods;

import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Projectile.EffectCarriers;


public class VampireMod extends Modificator{
	
	public VampireMod() {
		super();
		setInfo();
		name = "Vampire's";
	}

	@Override
	public void applyModificator(Projectile prj) {
		prj.effect = EffectCarriers.VAMPIRE;
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}
