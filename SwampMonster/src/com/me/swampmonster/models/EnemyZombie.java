package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.AbstractGameObject.State;

public class EnemyZombie extends Enemy{
	
	public EnemyZombie(Vector2 position) {
		super(position);
		
		animationsStandard.put(State.PURSUIT, new AnimationControl("data/EnemyZombie.png", 8, 16, 8)); 
		animationsStandard.put(State.STANDARD, new AnimationControl("data/EnemyZombie.png", 8, 16, 8)); 
		animationsStandard.put(State.DEAD, new AnimationControl("data/EnemyZombie.png", 8, 16, 4)); 
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		sprite.setColor(1, 1, 1, 1);
		health = 2;
		damage = 2;
		playerMovementSpeed = 0.15f;
	}
}
