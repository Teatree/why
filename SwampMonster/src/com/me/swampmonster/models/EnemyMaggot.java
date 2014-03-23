package com.me.swampmonster.models;

import com.badlogic.gdx.math.Vector2;

public class EnemyMaggot extends Enemy {

	public EnemyMaggot(Vector2 position) {
		super(position);
		sprite.setColor(0, 1, 0, 0.7f);
		playerMovementSpeed = 0.6f;
		health = 1;
		damage = 1;
	}
	

}
