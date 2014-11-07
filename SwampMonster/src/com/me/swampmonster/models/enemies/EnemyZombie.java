package com.me.swampmonster.models.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.utils.Assets;

public class EnemyZombie extends Enemy{
	
	public EnemyZombie(Vector2 position) {
		super(position);
		
		animationsStandard.put(State.PURSUIT, new AnimationControl(Assets.manager.get(Assets.enemyZombie), 8, 32, 7)); 
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.enemyZombie), 8, 32, 7)); 
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.enemyZombie), 8, 32, 4)); 
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		points = 15;
		attackSpeed = 50;
		yellowAura.radius = 16;
		oRangeAura.radius = yellowAura.radius*2;
		minHealth = 2;
		maxHealth = 3;
		minDamage = 2;
		maxDamage = 3;
		minSpeed = 3;
		maxSpeed = 5;
		health = random.nextInt(maxHealth - minHealth) + minHealth;
		damage = random.nextInt(maxDamage - minDamage) + minDamage;
		movementSpeed = (float)(((float)(random.nextInt(maxSpeed - minSpeed) + minSpeed))/10);
		minScale = (int) (11+health+damage-(int)(movementSpeed*10));
		maxScale = (int) (14+health+damage-(int)(movementSpeed*10));
//		System.out.println("minscale: " + minScale +  " maxScale: " + maxScale);
		sprite.setScale((float)(((float)(random.nextInt(maxScale - minScale) + minScale)))/10);
//		rectanlge.setSize(sprite.getBoundingRectangle().getWidth()*sprite.getScaleX(), sprite.getBoundingRectangle().getHeight()*sprite.getScaleY());
		STANDART_MOVEMENT_SPEED = movementSpeed;
	}
}
