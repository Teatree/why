package com.me.swampmonster.models.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.utils.AssetsMainManager;

public class EnemyMaggot extends Enemy {

	public EnemyMaggot(Vector2 position) {
		super(position);
		
		animationsStandard.put(State.STANDARD, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.enemyMaggot), 8, 16, 7)); 
		animationsStandard.put(State.PURSUIT, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.enemyMaggot), 8, 16, 7)); 
		animationsStandard.put(State.DEAD, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.enemyMaggot), 8, 16, 4)); 
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		movementSpeed = 0.6f;
		health = 1;
		damage = 1;
		points = 50;
		attackSpeed = 30;
	}
	

}
