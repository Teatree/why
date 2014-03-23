package com.me.swampmonster.models;

import com.badlogic.gdx.math.Vector2;

public class EnemyZombie extends Enemy{
	
	public EnemyZombie(Vector2 position) {
		super(position);
		
		sprite.setColor(1, 0, 0, 1);
		health = 2;
		damage = 2;
		playerMovementSpeed = 0.6f;
	}
}
