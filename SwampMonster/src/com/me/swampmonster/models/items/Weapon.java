package com.me.swampmonster.models.items;

import java.util.List;

import com.me.swampmonster.game.collision.Item;
import com.me.swampmonster.models.Projectile;

public class Weapon extends Item{
	
	public List<Projectile> projectiles;
	public int damage;
	public int coolDown;
	public int force;
	
	// different force
	
	public void shoot(){
		
	}

}
