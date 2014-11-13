package com.me.swampmonster.models.items.wepMods;

import java.util.Random;

import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile;

public class StunPlayerMod extends Modificator{
	
	public int eTime;
	
	public StunPlayerMod() {
		super();
		setInfo();
		random = new Random();
		random = new Random();
		chance = 0;
		name = "Bashing";
		setStats(Player.absoluteScore);
		descriptio = chance + " % chance to stun the player for " + eTime/60 + " second(s)."; 
	}

	@Override
	public void setStats(int playerScore) {
		if (playerScore >= 0 && playerScore < 500) {
			chance = random.nextInt(3 - 1) + 1;
			eTime = random.nextInt(90 - 60) + 60;
		} else if (playerScore >= 500 && playerScore < 1500) {
			chance = random.nextInt(4 - 1) + 1;
			eTime = random.nextInt(90 - 60) + 60;
		} else if (playerScore >= 1500 && playerScore < 3000) {
			chance = random.nextInt(5 - 2) + 2;
			eTime = random.nextInt(120 - 90) + 90;
		} else if (playerScore >= 3000) {
			chance = random.nextInt(6 - 3) + 3;
			eTime = random.nextInt(150 - 120) + 120;
		}

	}
	
	@Override
	public void applyModificator(Projectile prj) {
		int ran = random.nextInt(100);
		if (ran <= chance) {
			L1.player.setNegativeEffect(NegativeEffects.STUN);
			L1.player.negativeEffectCounter = eTime;
		}
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}
