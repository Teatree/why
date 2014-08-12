package com.me.swampmonster.slotMachineStuff;

public enum Perks{
	
		RELOAD_SPEED (0, "text", 0d, 2d, 0d, 0d, 0d), 
		OXYGEN_COPACITY (1, "text", 0d, 0d, 0d, 0d, 0d), 
		MOVEMENT_SPEED (2, "text", 2d, 0d, 0d, 0d, 0d), 
		HEALTH_COPACITY (3, "text", 0d, 0d, 0d, 0d, 0d), 
		MELEE_RADIUS (4, "text", 0d, 0d, 0d, 0d, 0d),
		MELEE_RELOAD_SPEED (5, "text", 0d, 0d, 0d, 0d, 0d),
		ARROW_DAMAGE (6, "text", 0d, 0d, 2d, 0d, 0d),
		HUNRETPOINTS (7, "text", 0d, 0d, 0d, 0d, 100d),
		FIVEHUNRETPOINTS (8, "text", 0d, 0d, 0d, 0d, 500d),
		NOTHING (9, "text", 0d, 0d, 0d, 0d, 0d),
		ARROW_SPEED (10, "text", 0d, 0d, 0d, 0d, 0d);
		
		public final Double speed;
		public final Double reloadSpeed;
		public final Double damage; 
		public final Double health;
		public final Double points;
		public final String description;
		public final int region;
		
		Perks (int region, String description, Double speed, Double reloadSpeed, Double damage, Double health, Double points){
			this.region = region;
			this.description = description;
			this.speed = speed;
			this.reloadSpeed = reloadSpeed;
			this.damage = damage;
			this.health = health;
			this.points = points;
		}
}
