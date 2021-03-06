package com.me.swampmonster.models.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.CameraHelper;

public class EnemySofa extends Enemy {

	public Random randomStat;
	public Random random;
	
	public int roarCoolDown;
	public int roarCoolDownCounter;
	public int roaringTimer;
	
	public int stunCoolDown;
	public int stunCoolDownCounter;
	
	public int animatingTimer;
	
	public float sofa_dx;
	public float sofa_dy;
	
	static {
		levelByPlayesScore = new ArrayList<Integer>();
		levelByPlayesScore.add(1000);
		levelByPlayesScore.add(3000);
	}
	
	public EnemySofa(Vector2 position) {
		super(position);
	}
	
	@Override
	public void update(TiledMapTileLayer collisionLayer, Player player,
			CameraHelper cameraHelper, List<Enemy> enemies){
		super.update(collisionLayer, player, cameraHelper, enemies);
		sprite.setSize(55, 55);
		yellowAura.x = position.x+sprite.getWidth()/2;
		yellowAura.y = position.y+sprite.getHeight()/2;
//		rectanlge.x = position.x;
//		rectanlge.y = position.y;
//		rectanlge.width = 55;
//		rectanlge.height = 55;
		
		sofa_dx = L1.player.position.x - position.x;
		sofa_dy = L1.player.position.y - position.y;
		float length3 = (float) Math.sqrt(sofa_dx * sofa_dx  + sofa_dy * sofa_dy);
		
		if(state != State.DEAD && state != State.ANIMATINGLARGE && state != State.ANIMATING && length3>200 && difficultyLevel!=0){
			int number = random.nextInt(1000);
			if(number < 900 && roarCoolDownCounter == 0){
				state = State.ANIMATING;
				animatingTimer = 300;
				roarCoolDownCounter = roarCoolDown;
			}
		}
		if (state == State.ANIMATING ) {
			currentFrame = animationsStandard.get(state).doComplexAnimation(
					180, 1.4f, Gdx.graphics.getDeltaTime(),
					Animation.PlayMode.NORMAL);
			sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
			animatingTimer --;
			if(animatingTimer==0){
				state = State.STANDARD;
				roaringTimer = 900;
			}
		}
		if(state != State.DEAD && state != State.ANIMATING && state != State.ANIMATINGLARGE && length3<60){
			int number = random.nextInt(1000);
			if(number < 900 && stunCoolDownCounter == 0){
				state = State.ANIMATINGLARGE;
				animatingTimer = 300;
				stunCoolDownCounter = stunCoolDown;
			}
		}
		if (state == State.ANIMATINGLARGE ) {
			currentFrame = animationsStandard.get(state).doComplexAnimation(
					160, 1.4f, Gdx.graphics.getDeltaTime(),
					Animation.PlayMode.NORMAL);
			sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
			animatingTimer --;
			if(animatingTimer==0){
				Explosion ex = new Explosion(new Vector2(position.x+1, position.y+1), "stun");
				L1.explosions.add(ex);
				state = State.STANDARD;
			}
		}
		
		
		if(roaringTimer>0){
			movementSpeed = STANDART_MOVEMENT_SPEED*2;
			roaringTimer --;
		}else{
			movementSpeed = STANDART_MOVEMENT_SPEED;
		}
		if(roarCoolDownCounter>0){
			roarCoolDownCounter --;
		}
		if(stunCoolDownCounter>0){
			stunCoolDownCounter --;
		}
		
	}

	@Override
	public void difficultyLevelParams() {
		if(difficultyLevel==0){
			animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7)); 
			animationsStandard.put(State.PURSUIT, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7)); 
			animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 4)); 
			animationsStandard.put(State.ANIMATING, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7));
			animationsStandard.put(State.ANIMATINGLARGE, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7));
			sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
			animatingTimer = 300;
			random = new Random();
			roarCoolDown = 2100;
//			roarCoolDown = random.nextInt(100-50)+50;
			stunCoolDown = 2000;
			points = 50;
			attackSpeed = 15;
			yellowAura.radius = 32;
			yellowAura.x = position.x + sprite.getWidth() / 2;
			yellowAura.y = position.y + sprite.getHeight() / 2;
			oRangeAura.radius = yellowAura.radius*1.3f;
			oRangeAura.x = position.x + sprite.getWidth() / 2;
			oRangeAura.y = position.y + sprite.getHeight() / 2;
			minHealth = 65;
			maxHealth = 90;
			minDamage = 3;
			maxDamage = 4;
			minSpeed = 4;
			maxSpeed = 5;
			health = random.nextInt(maxHealth - minHealth) + minHealth;
			damage = random.nextInt(maxDamage - minDamage) + minDamage;
			movementSpeed = (float)(((float)(random.nextInt(maxSpeed - minSpeed) + minSpeed))/10);
			minScale = (int) (16+health/10+damage-(int)(movementSpeed*10));
			maxScale = (int) (18+health/10+damage-(int)(movementSpeed*10));
//			System.out.println("minscale: " + minScale +  " maxScale: " + maxScale);
			sprite.setScale((float)(((float)(random.nextInt(maxScale - minScale) + minScale)))/10);
//			rectanlge.setSize(sprite.getBoundingRectangle().getWidth()*sprite.getScaleX(), sprite.getBoundingRectangle().getHeight()*sprite.getScaleY());
			STANDART_MOVEMENT_SPEED = movementSpeed;
		}
		if(difficultyLevel==1){
			animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7)); 
			animationsStandard.put(State.PURSUIT, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7)); 
			animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 4)); 
			animationsStandard.put(State.ANIMATING, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7));
			animationsStandard.put(State.ANIMATINGLARGE, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7));
			sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
			animatingTimer = 300;
			random = new Random();
			roarCoolDown = 2100;
//			roarCoolDown = random.nextInt(100-50)+50;
			stunCoolDown = 2000;
			points = 50;
			attackSpeed = 15;
			yellowAura.radius = 32;
			yellowAura.x = position.x + sprite.getWidth() / 2;
			yellowAura.y = position.y + sprite.getHeight() / 2;
			oRangeAura.radius = yellowAura.radius*1.3f;
			oRangeAura.x = position.x + sprite.getWidth() / 2;
			oRangeAura.y = position.y + sprite.getHeight() / 2;
			minHealth = 65;
			maxHealth = 90;
			minDamage = 3;
			maxDamage = 4;
			minSpeed = 4;
			maxSpeed = 5;
			health = random.nextInt(maxHealth - minHealth) + minHealth;
			damage = random.nextInt(maxDamage - minDamage) + minDamage;
			movementSpeed = (float)(((float)(random.nextInt(maxSpeed - minSpeed) + minSpeed))/10);
			minScale = (int) (16+health/10+damage-(int)(movementSpeed*10));
			maxScale = (int) (18+health/10+damage-(int)(movementSpeed*10));
//			System.out.println("minscale: " + minScale +  " maxScale: " + maxScale);
			sprite.setScale((float)(((float)(random.nextInt(maxScale - minScale) + minScale)))/10);
//			rectanlge.setSize(sprite.getBoundingRectangle().getWidth()*sprite.getScaleX(), sprite.getBoundingRectangle().getHeight()*sprite.getScaleY());
			STANDART_MOVEMENT_SPEED = movementSpeed;
		}
		if(difficultyLevel==2){
			animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7)); 
			animationsStandard.put(State.PURSUIT, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7)); 
			animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 4)); 
			animationsStandard.put(State.ANIMATING, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7));
			animationsStandard.put(State.ANIMATINGLARGE, new AnimationControl(Assets.manager.get(Assets.enemySofa), 8, 32, 7));
			sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
			animatingTimer = 300;
			random = new Random();
			roarCoolDown = 2100;
//			roarCoolDown = random.nextInt(100-50)+50;
			stunCoolDown = 2000;
			points = 50;
			attackSpeed = 15;
			yellowAura.radius = 32;
			yellowAura.x = position.x + sprite.getWidth() / 2;
			yellowAura.y = position.y + sprite.getHeight() / 2;
			oRangeAura.radius = yellowAura.radius*1.3f;
			oRangeAura.x = position.x + sprite.getWidth() / 2;
			oRangeAura.y = position.y + sprite.getHeight() / 2;
			minHealth = 65;
			maxHealth = 90;
			minDamage = 3;
			maxDamage = 4;
			minSpeed = 4;
			maxSpeed = 5;
			health = random.nextInt(maxHealth - minHealth) + minHealth;
			damage = random.nextInt(maxDamage - minDamage) + minDamage;
			movementSpeed = (float)(((float)(random.nextInt(maxSpeed - minSpeed) + minSpeed))/10);
			minScale = (int) (16+health/10+damage-(int)(movementSpeed*10));
			maxScale = (int) (18+health/10+damage-(int)(movementSpeed*10));
//			System.out.println("minscale: " + minScale +  " maxScale: " + maxScale);
			sprite.setScale((float)(((float)(random.nextInt(maxScale - minScale) + minScale)))/10);
//			rectanlge.setSize(sprite.getBoundingRectangle().getWidth()*sprite.getScaleX(), sprite.getBoundingRectangle().getHeight()*sprite.getScaleY());
			STANDART_MOVEMENT_SPEED = movementSpeed;
		}
		
	}

}
