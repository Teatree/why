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
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.CameraHelper;

public class EnemyMaggot extends Enemy {
	
	private int randomChargeCounter;
	private int counter;
	private int prepareChargeCoutner;
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
		points = 50;
		attackSpeed = 30;
	}
	
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
			
			if (prepareChargeCoutner > 0){
				preparingToCharge = true;
				prepareChargeCoutner--;
				currentFrame = animationsStandard.get(state).doComplexAnimation(118, 1.8f,Gdx.graphics.getDeltaTime(), Animation.NORMAL);
				
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
						preparingToCharge = false;
					}
				}
				
				if (aimerBot.overlaps(player.rectanlge)){
					aimerBot.x = position.x;
					aimerBot.y = position.y;
				}
			}else if(prepareChargeCoutner==0 && preparingToCharge && !charging){
				
				savedPlayerPosX = player.position.x;
				savedPlayerPosY = player.position.y;
				
				savedEnemyDx = savedPlayerPosX - position.x;
				savedEnemyDy = savedPlayerPosY - position.y;
				
				float length = (float) Math.sqrt(savedEnemyDx * savedEnemyDx + savedEnemyDy * savedEnemyDy);
				savedEnemyDx /= length;
				savedEnemyDy /= length;
				
				charging = true;
				preparingToCharge = false;
				
				chargeCoutner = 120;
			}
		}
		if(charging){
			if(chargeCoutner > 0){
				chargeCoutner--;
				currentFrame = animationsStandard.get(state).animate(126);
				
				if (position.x > savedPlayerPosX - 4
						|| position.x < savedPlayerPosX - 10
						|| position.y > savedPlayerPosY - 4
						|| position.y < savedPlayerPosY - 10) {
					// // System.out.println("yes it is !");
					if (collidableLeft == null || collidableRight == null || collidableUp == null || collidableDown == null) {
						position.x += savedEnemyDx * movementSpeed*4;
						position.y += savedEnemyDy * movementSpeed*4;
						System.out.println("colliding");
					}
					if (collidableDown != null){
						state = State.DEAD;
					}
					if (collidableUp != null){
						state = State.DEAD;
					}
					if (collidableRight != null){
						state = State.DEAD;
					}
					if (collidableLeft != null){
						state = State.DEAD;
					}
				}
				
				collidableLeft = collisionCheckerLeft(collisionLayer,
						enemies);
				collisionCheck(collidableLeft, collisionLayer, player);
				collidableRight = collisionCheckerRight(collisionLayer,
						enemies);
				collisionCheck(collidableRight, collisionLayer, player);
				collidableDown = collisionCheckerBottom(collisionLayer,
						enemies);
				collisionCheck(collidableDown, collisionLayer, player);
				collidableUp = collisionCheckerTop(collisionLayer, enemies);
				collisionCheck(collidableUp, collisionLayer, player);
				
			}else if(chargeCoutner == 0){
				charging = false;
			}
		}
	}
	
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
