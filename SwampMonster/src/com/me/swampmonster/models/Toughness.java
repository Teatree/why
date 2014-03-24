package com.me.swampmonster.models;

public enum Toughness {
	ARMORED_GUY (-0.1, 0d, 1d), 
	SPEEDY_GUY (0.3, 0d, 0d), 
	ANGRY_GUY (0d, 1d, 0d), 
	POISONOUS_GUY (0d, 0d, 0d), 
	FREEZER_GUY (0d, 0d, 0d),
	EXPLOSIVE_GUY (0d, 0d, -1d),
	PLASMA_GUY (0d, 1d, 0d);
	
	public final Double speed;
	public final Double damage; 
	public final Double health;
	
	Toughness (Double speed, Double damage, Double health){
		this.speed = speed;
		this.damage = damage;
		this.health = health;
	}
}
