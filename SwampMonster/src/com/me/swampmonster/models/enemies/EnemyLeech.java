package com.me.swampmonster.models.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.utils.AssetsMainManager;
import com.me.swampmonster.utils.CameraHelper;

public class EnemyLeech extends Enemy{

	public EnemyLeech(Vector2 position) {
		super(position);
		
		animationsStandard.put(State.STANDARD, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.enemyLeech), 8, 16, 7)); 
		animationsStandard.put(State.PURSUIT, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.enemyLeech), 8, 16, 7)); 
		
		yellowAura.radius = yellowAura.radius*14;
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		movementSpeed = 0.2f;
		health = 1;
		damage = 1;
		points = 200;
		attackSpeed = 150;
		STANDART_MOVEMENT_SPEED = movementSpeed;
	}
	
	@Override
	protected void inflictOnThe(int standing, int animation, Player player, CameraHelper cameraHelper, int attackSpeed) {
		// Timer is for the length of the actual animation
		// Timer2 is for the waiting period
		if(oldPos.x == position.x && oldPos.y == position.y){
			if(timer2 < attackSpeed){
				timer2++;
//            			// System.out.println("timer2: " + timer2 );
				currentFrame = animationsStandard.get(state).animate(standing);
			}
			if(timer2 >= attackSpeed && timer < 30){
				currentFrame = animationsStandard.get(state).doComplexAnimation(animation, 1.8f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
				
				sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				timer++;
				if(timer == 30 && timer2 >= attackSpeed){
					float direction_x = player.position.x - position.x;
					float direction_y = player.position.y - position.y;
					
					Projectile p = new Projectile(new Vector2(100, 100), getRotation(player));
					p.setPosition(new Vector2(oRangeAura.x+direction_x/100-8, oRangeAura.y+direction_y/100-8));
					
					float length =(float) Math.sqrt(direction_x*direction_x + direction_y*direction_y);
					direction_x /= length;
					direction_y /= length;
					
					p.setDirection(direction_x, direction_y);
					
					enemyProjectiles.add(p);
					
					currentFrame = animationsStandard.get(state).animate(standing);
					// And may be inflict different hurts, direction/ kinds of hurts/ etc.
					player.setDamageType("enemy");
					player.harmfulEnemy = this;
					
					timer = 0;
					timer2 = 0;
				}
			}
		}
	}
}
