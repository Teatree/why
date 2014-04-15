package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;

public class EnemyLeech extends Enemy{

	public EnemyLeech(Vector2 position) {
		super(position);
		
		animationsStandard.put(State.STANDARD, new AnimationControl("data/EnemyLeech.png", 8, 16, 7)); 
		animationsStandard.put(State.PURSUIT, new AnimationControl("data/EnemyLeech.png", 8, 16, 7)); 
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		playerMovementSpeed = 0.2f;
		health = 1;
		damage = 1;
		points = 200;
		attackSpeed = 80;
	}

}
