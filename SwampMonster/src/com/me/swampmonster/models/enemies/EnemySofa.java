package com.me.swampmonster.models.enemies;

import com.badlogic.gdx.math.Vector2;

public class EnemySofa extends Enemy {

	public EnemySofa(Vector2 position) {
		super(position);
		movementSpeed = 0.3f;
		health = 4;
		damage = 2;
		points = 35;
	}

}
