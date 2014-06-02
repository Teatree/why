package com.me.swampmonster.models.slots;

import com.me.swampmonster.models.enemies.Enemy;

public class DamageTrap {
	public int lifetime = 112;

	public void execute (Enemy enemy){
		enemy.health = 0;
	}
}
