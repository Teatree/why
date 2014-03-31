package com.me.swampmonster.models;

import com.badlogic.gdx.math.Vector2;

public class EnemySofa extends Enemy {

	public EnemySofa(Vector2 position) {
		super(position);
		playerMovementSpeed = 0.3f;
		health = 4;
		damage = 2;
		points = 150;
	}

}
