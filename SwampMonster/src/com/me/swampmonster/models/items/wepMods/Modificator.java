package com.me.swampmonster.models.items.wepMods;

import java.util.Random;

import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.enemies.Enemy;

public abstract class Modificator {
	
	public int probability = 300;
	public String name;
	public String descriptio;
	public Enemy targetEnemy;
	public Random random;
	public int chance;
	
	public abstract void applyModificator(Projectile prj);
	
	public void setInfo(){
		name = this.getClass().getSimpleName();
		descriptio = "descriptio";
		
	}
	
	public abstract void setStats(int playerScore);

}