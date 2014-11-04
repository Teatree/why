package com.me.swampmonster.models.enemies;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.CameraHelper;

public class EnemyLeech extends Enemy{

	public EnemyLeech(Vector2 position) {
		super(position);
		
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.enemyLeech), 8, 32, 7)); 
		animationsStandard.put(State.PURSUIT, new AnimationControl(Assets.manager.get(Assets.enemyLeech), 8, 32, 7)); 
		animationsStandard.put(State.ATTACKING, new AnimationControl(Assets.manager.get(Assets.enemyLeech), 8, 32, 7)); 
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.enemyLeech), 8, 32, 7)); 
		
		yellowAura.radius = yellowAura.radius*20;
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		points = 35;
		attackSpeed = 120;
		minHealth = 2;
		maxHealth = 3;
		minDamage = 1;
		maxDamage = 2;
		minSpeed = 3;
		maxSpeed = 4;
		health = random.nextInt(maxHealth - minHealth) + minHealth;
		movementSpeed = (float)(((float)(random.nextInt(maxSpeed - minSpeed) + minSpeed))/10);
		damage = random.nextInt(maxDamage - minDamage) + minDamage;
		minScale = (int) (11+health+damage-(int)(movementSpeed*10));
		maxScale = (int) (14+health+damage-(int)(movementSpeed*10));
		System.out.println("minscale: " + minScale +  " maxScale: " + maxScale);
		sprite.setScale((float)(((float)(random.nextInt(maxScale - minScale) + minScale)))/10);
//		rectanlge.setSize(sprite.getBoundingRectangle().getWidth()*sprite.getScaleX(), sprite.getBoundingRectangle().getHeight()*sprite.getScaleY());
		STANDART_MOVEMENT_SPEED = movementSpeed;
	}
	
	@Override
	public void update(TiledMapTileLayer collisionLayer, Player player,
			CameraHelper cameraHelper, List<Enemy> enemies) {
		super.update(collisionLayer, player, cameraHelper, enemies);
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
			if(timer2 == attackSpeed && timer < 30){
				currentFrame = animationsStandard.get(state).doComplexAnimation(animation, 1.8f, Gdx.graphics.getDeltaTime(), Animation.PlayMode.NORMAL);
				
				sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
				timer++;
				if(timer == 30){
					float direction_x = player.position.x - position.x;
					float direction_y = player.position.y - position.y;
					
					LeechProjectile p = new LeechProjectile(new Vector2(position.x
							+ direction_x / 100 - 8, position.y + direction_y / 100
							- 8), getRotation(player.position));
					if(this.toughness != null){
						p.setColour(this.toughness.red, this.toughness.green, this.toughness.blue, this.toughness.alpha);
					}
					p.setPosition(new Vector2(oRangeAura.x+direction_x/100-8, oRangeAura.y+direction_y/100-8));
					
					float length =(float) Math.sqrt(direction_x*direction_x + direction_y*direction_y);
					direction_x /= length;
					direction_y /= length;
					
					p.setDirection(direction_x, direction_y);
					
					this.enemyProjectiles.add(p);
					
					currentFrame = animationsStandard.get(state).animate(standing);
					// And may be inflict different hurts, direction/ kinds of hurts/ etc.
					player.damageType = "enemy";
					player.harmfulEnemy = this;
					
					timer2 = 0;
					timer =  0;
					if(negativeEffectsState.equals(NegativeEffects.FADE_N)){
						setNegativeEffect(NegativeEffects.NONE);
					}
					attackSequenceStarted = false;
				}
			}
		}
	}

}
