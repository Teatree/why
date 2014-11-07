package com.me.swampmonster.models.items.wepMods;

import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Projectile;

public class StunPlayerMod extends Modificator{
	
	public StunPlayerMod() {
		super();
		setInfo();
	}

	@Override
	public void applyModificator(Projectile prj) {
		L1.player.setNegativeEffect(NegativeEffects.STUN);
		L1.player.negativeEffectCounter = 180;
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}
