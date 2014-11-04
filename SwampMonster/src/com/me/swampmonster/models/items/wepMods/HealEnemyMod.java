package com.me.swampmonster.models.items.wepMods;


public class HealEnemyMod extends Modificator{
	
	@Override
	public void applyModificator() {
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}
