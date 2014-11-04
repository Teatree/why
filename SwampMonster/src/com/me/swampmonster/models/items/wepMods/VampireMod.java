package com.me.swampmonster.models.items.wepMods;


public class VampireMod extends Modificator{
	
	@Override
	public void applyModificator() {
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}
