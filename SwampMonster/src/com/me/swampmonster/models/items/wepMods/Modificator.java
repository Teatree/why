package com.me.swampmonster.models.items.wepMods;

import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.enemies.Enemy;

public abstract class Modificator {
	
	public int probability = 900;
	public String name;
	public String descriptio;
	public Enemy targetEnemy;
	
	
	public abstract void applyModificator(Projectile prj);
	
	public void setInfo(){
		name = this.getClass().getSimpleName();
		descriptio = "descriptio";
	}

}