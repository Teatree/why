package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.utils.AssetsMainManager;

public class EnemyZombie extends Enemy{
	
	public EnemyZombie(Vector2 position) {
		super(position);
		
		animationsStandard.put(State.PURSUIT, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.enemyZombie), 8, 16, 7)); 
		animationsStandard.put(State.STANDARD, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.enemyZombie), 8, 16, 7)); 
		animationsStandard.put(State.DEAD, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.enemyZombie), 8, 16, 4)); 
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		health = 2;
		damage = 2;
		points = 100;
		movementSpeed = 0.15f;
		attackSpeed = 50;
	}
}
