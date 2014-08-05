package com.me.swampmonster.models.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.utils.Assets;

public class EnemyZombie extends Enemy{
	
	public EnemyZombie(Vector2 position) {
		super(position);
		
		animationsStandard.put(State.PURSUIT, new AnimationControl(Assets.manager.get(Assets.enemyZombie), 8, 16, 7)); 
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.enemyZombie), 8, 16, 7)); 
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.enemyZombie), 8, 16, 4)); 
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		health = 2;
		damage = 2;
		points = 20;
		movementSpeed = 0.15f;
		attackSpeed = 50;
	}
}
