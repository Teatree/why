package com.me.swampmonster.models.enemies;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.CameraHelper;

public class EnemyMaggot extends Enemy {
	
	private int randomChargeCounter;
	private int counter;
	private int prepareChargeCoutner;
	private int waitingCounter;
	private int chargeCoutner;
	private Random rand;
	
	private float savedPlayerPosX;
	private float savedPlayerPosY;
	
	private float savedEnemyDx;
	private float savedEnemyDy;
	
	public EnemyMaggot(Vector2 position) {
		super(position);
		
		rand = new Random();
		randomChargeCounter = 100;
		counter = 0;
		preparingToCharge = false;
		
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.enemyMaggot), 8, 32, 7)); 
		animationsStandard.put(State.PURSUIT, new AnimationControl(Assets.manager.get(Assets.enemyMaggot), 8, 32, 7)); 
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.enemyMaggot), 8, 32, 4)); 
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		movementSpeed = 0.6f;
		health = 1;
		damage = 1;
		points = 10;
		attackSpeed = 30;
	}
	
	@Override
	public void update(TiledMapTileLayer collisionLayer, Player player, CameraHelper cameraHelper, List<Enemy> enemies) {
		super.update(collisionLayer, player, cameraHelper, enemies);
		
		if(!aiming && !preparingToCharge){
			aimerBot.x = position.x;
			aimerBot.y = position.y;
		}
		if(!charging){
			if(counter > 0){
				aiming = true;
				counter--;
				if (aimerBot.x > player.getPosition().x - 4
						|| aimerBot.x < player.getPosition().x - 10
						|| aimerBot.y > player.getPosition().y - 4
						|| aimerBot.y < player.getPosition().y - 10) {
					Collidable collidable = CollisionHelper.isCollidable(aimerBot.x+5, aimerBot.y+5, collisionLayer);
					if (collidable == null){
						aimerBot.x += enemyDx * 5;
					}
					if (collidable == null){
						aimerBot.y += enemyDy * 5;
					}
					if (collidable != null){
						System.out.println("can't charge there!");
						aimerBot.x = position.x;
						aimerBot.y = position.y;
						aiming = false;
					}
				}
				
				if (aimerBot.overlaps(player.rectanlge)){
					prepareChargeCoutner = 60;
					counter = 0;
					aiming = false;
					aimerBot.x = position.x;
					aimerBot.y = position.y;
				}
			}else if(counter==0){
				aiming = false;
			}
			
			if (prepareChargeCoutner > 0 && !charging && state != State.DEAD){
				preparingToCharge = true;
				prepareChargeCoutner--;
				currentFrame = animationsStandard.get(state).doComplexAnimation(120, 1.4f,Gdx.graphics.getDeltaTime(), Animation.NORMAL);
				
				if (aimerBot.x > player.getPosition().x - 4
						|| aimerBot.x < player.getPosition().x - 10
						&& aimerBot.y > player.getPosition().y - 4
						|| aimerBot.y < player.getPosition().y - 10) {
					Collidable collidable = CollisionHelper.isCollidable(aimerBot.x+5, aimerBot.y+5, collisionLayer);
					if (collidable == null){
						aimerBot.x += enemyDx * 5;
					}
					if (collidable == null){
						aimerBot.y += enemyDy * 5;
					}
					if (collidable != null){
						System.out.println("can't charge there!");
						aimerBot.x = position.x;
						aimerBot.y = position.y;
						preparingToCharge = false;
					}
				}
				
				if (aimerBot.overlaps(player.rectanlge)){
					aimerBot.x = position.x;
					aimerBot.y = position.y;
				}
			}else if(prepareChargeCoutner==0 && preparingToCharge && !charging){
				
				preparingToCharge = false;
				
				waiting = true;
				
				waitingCounter = 150;
				
			}
			if(waitingCounter > 0 && state != State.DEAD){
				waitingCounter--;
				currentFrame = animationsStandard.get(state).animate(136);
				
			}
			else if(waitingCounter==0 && waiting){
				waiting = false;
				
				chargeCoutner = 222;
				charging = true;
			}
			if(waitingCounter==30){
				savedPlayerPosX = player.position.x;
				savedPlayerPosY = player.position.y;
				
				savedEnemyDx = savedPlayerPosX - position.x;
				savedEnemyDy = savedPlayerPosY - position.y;
				
				float length = (float) Math.sqrt(savedEnemyDx * savedEnemyDx + savedEnemyDy * savedEnemyDy);
				savedEnemyDx /= length;
				savedEnemyDy /= length;
			}
		}
		if(charging){
			if(chargeCoutner > 0){
				chargeCoutner--;
				currentFrame = animationsStandard.get(state).animate(128);
				
				if (position.x > savedPlayerPosX - 4
						|| position.x < savedPlayerPosX - 10
						|| position.y > savedPlayerPosY - 4
						|| position.y < savedPlayerPosY - 10) {

					Collidable cL = CollisionHelper.isCollidable(position.x, position.y+sprite.getHeight()/2, collisionLayer);
					Collidable cR = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y+sprite.getHeight()/2, collisionLayer);
					Collidable cU = CollisionHelper.isCollidable(position.x+sprite.getWidth()/2, position.y+sprite.getHeight(), collisionLayer);
					Collidable cD = CollisionHelper.isCollidable(position.x+sprite.getWidth()/2, position.y, collisionLayer);
					
					if (cL == null && savedEnemyDx <= 0 ||
							cR == null && savedEnemyDx > 0){
						position.x += savedEnemyDx * movementSpeed*4;
					} 
					if (cD == null && savedEnemyDy < 0 
							|| cU == null && savedEnemyDy >= 0){
						position.y += savedEnemyDy * movementSpeed*4;
					} 
					
//					if (cU == null && savedEnemyDy >= 0){ 
//						position.y += savedEnemyDy * movementSpeed*4;
//					}
//					
//					if (cD == null && savedEnemyDy < 0){
//						position.y += savedEnemyDy * movementSpeed*4;
//					}
					
					if (cD != null || 
							cU != null ||
							cR != null||
							cL != null ){
						charging = false;
						state = State.DEAD;
					}
					
//					if (cU != null){
//						position = oldPos;
//						state = State.DEAD;
//						System.out.println("CU");
//					}
//					if (cR != null){
//						position = oldPos;
//						state = State.DEAD;
//						System.out.println("CR");
//					}
//					if (cL != null){
//						position = oldPos;
//						state = State.DEAD;
//						System.out.println("CL");
//					}
				}
				
			}else if(chargeCoutner == 0){
				charging = false;
			}
			
			if(rectanlge.overlaps(player.rectanlge)){
				player.setNegativeEffect(NegativeEffects.STUN);
			}
		}
	}
	
	@Override
	public void atackLogic(Player player, CameraHelper cameraHelper) {
		if (yellowAura.overlaps(player.circle) && player.state != State.DEAD && !charging) {
			attackSequenceStarted = true;
		}
		
		if (aimingAura.overlaps(player.circle) && !attackSequenceStarted && player.state != State.DEAD && !charging) {
			if (rand.nextInt(randomChargeCounter) == randomChargeCounter-1) {
				aiming = true;
				counter = 200;
			}
		}
		
		if (attackSequenceStarted) {
			if (playerMovementDirection == "right") {
				inflictOnThe(88, 56, player, cameraHelper, attackSpeed);
			}
			if (playerMovementDirection == "left") {
				inflictOnThe(72, 40, player, cameraHelper, attackSpeed);
			}
			if (playerMovementDirection == "up") {
				inflictOnThe(80, 48, player, cameraHelper, attackSpeed);
			}
			if (playerMovementDirection == "down") {
				inflictOnThe(64, 32, player, cameraHelper, attackSpeed);
			}
		}
	}

}
