package com.me.swampmonster.models.items.wepMods;

import java.util.Random;

import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Projectile.EffectCarriers;


public class ShootThoughCollisionMod extends Modificator{
	
	public ShootThoughCollisionMod() {
		super();
		random = new Random();
		chance = 0;
		setInfo();
		name = "Ghost's";
		setStats(Player.absoluteScore);
		descriptio = chance + "% chance of shooting a ghost arrow";
	}

	@Override
	public void setStats(int playerScore) {
		if (playerScore >= 0 && playerScore < 500) {
			chance = random.nextInt(3 - 1) + 1;
		} else if (playerScore >= 500 && playerScore < 1500) {
			chance = random.nextInt(4 - 1) + 1;
		} else if (playerScore >= 1500 && playerScore < 3000) {
			chance = random.nextInt(5 - 2) + 2;
		} else if (playerScore >= 3000) {
			chance = random.nextInt(6 - 3) + 3;
		}
	
	}
	
	@Override
	public void applyModificator(Projectile prj) {
		int ran = random.nextInt(100);
		if (ran <= chance) {
			prj.effect = EffectCarriers.SHADOW;
		}
		System.out.println("mod: " + this.getClass().getSimpleName());
	}
	
}
