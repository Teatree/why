package com.me.swampmonster.models.enemies;

import com.badlogic.gdx.math.Vector2;

public class EnemyBreather extends Enemy {

	public EnemyBreather(Vector2 position) {
		super(position);
		points = 75;
		health = 1;
		damage = 1;
	}

}
