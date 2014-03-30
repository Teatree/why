package com.me.swampmonster.models;

public enum Toughness {
	ARMORED_GUY (-0.1, 0d, 1d, 192f/255, 192f/255, 192f/255, 1f), 
	SPEEDY_GUY (0.3, 0d, 0d, 1f, 1f, 0f, 1f), 
	ANGRY_GUY (0d, 1d, 0d, 220f/255, 20f/255, 60f/255, 1f ), 
	POISONOUS_GUY (0d, 0d, 0d, 124f/255, 252f/255, 0f, 1f), 
	FREEZER_GUY (0d, 0d, 0d, 0f, 191f/255, 1f, 1f),
	EXPLOSIVE_GUY (0d, 0d, -1d, 244f/255, 164f/255, 96f/255, 1f),
	PLASMA_GUY (0d, 1d, 0d, 139f/255, 0f, 139f/255, 1f);
	
	public final Double speed;
	public final Double damage; 
	public final Double health;
	public final float red;
	public final float blue;
	public final float green;
	public final float alpha;
	Toughness (Double speed, Double damage, Double health, float red, float green, float blue, float alpha){
		this.speed = speed;
		this.damage = damage;
		this.health = health;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
}




