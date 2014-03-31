package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;

public class EnemyMaggot extends Enemy {

	public EnemyMaggot(Vector2 position) {
		super(position);
		
		animationsStandard.put(State.STANDARD, new AnimationControl("data/EnemyMaggot.png", 8, 16, 7)); 
		animationsStandard.put(State.PURSUIT, new AnimationControl("data/EnemyMaggot.png", 8, 16, 7)); 
		animationsStandard.put(State.DEAD, new AnimationControl("data/EnemyMaggot.png", 8, 16, 4)); 
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
//		sprite.setColor(0, 1, 0, 0.7f);
		playerMovementSpeed = 0.6f;
		health = 1;
		damage = 1;
		points = 50;
	}
	

}
