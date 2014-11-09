package com.me.swampmonster.models.items.wepMods;

import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.enemies.Enemy;


public class ExtraDamageToEnemyTypeMod extends Modificator{
	public Enemy targetEnemy;
	
	public ExtraDamageToEnemyTypeMod() {
		super();
		setInfo();
		name = "Slayer's";
	}

	@Override
	public void applyModificator(Projectile prj) {
//		prj.effect = EffectCarriers.EXTRADAMAGE;
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}
