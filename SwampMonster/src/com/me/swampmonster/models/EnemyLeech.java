package com.me.swampmonster.models;

import com.badlogic.gdx.math.Vector2;

public class EnemyLeech extends Enemy{

	public EnemyLeech(Vector2 position) {
		super(position);
		playerMovementSpeed = 0.2f;
		health = 1;
		damage = 1;
	}

}
