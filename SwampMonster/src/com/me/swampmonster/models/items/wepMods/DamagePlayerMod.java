package com.me.swampmonster.models.items.wepMods;

import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Projectile;


public class DamagePlayerMod extends Modificator{
	
	public DamagePlayerMod() {
		setInfo();
		probability = 900;
	}
	
	
	@Override
	public void applyModificator(Projectile prj) {
		L1.player.health--;
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}
