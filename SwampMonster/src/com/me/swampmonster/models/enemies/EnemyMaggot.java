package com.me.swampmonster.models.enemies;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.CameraHelper;

public class EnemyMaggot extends Enemy {

	private boolean aiming;
	private int randomChargeCounter;
	private int counter;
	private Random rand;
	
	public EnemyMaggot(Vector2 position) {
		super(position);
		
		randomChargeCounter = 2;
		counter = 0;
		
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.enemyMaggot), 8, 16, 7)); 
		animationsStandard.put(State.PURSUIT, new AnimationControl(Assets.manager.get(Assets.enemyMaggot), 8, 16, 7)); 
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.enemyMaggot), 8, 16, 4)); 
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		movementSpeed = 0.6f;
		health = 1;
		damage = 1;
		points = 50;
		attackSpeed = 30;
	}
	
	public void update(TiledMapTileLayer collisionLayer, Player player, CameraHelper cameraHelper, List<Enemy> enemies) {
		super.update(collisionLayer, player, cameraHelper, enemies);
		
		if(aiming){
//			counter++;
			if (position.x > player.getPosition().x - 4
					|| position.x < player.getPosition().x - 10
					|| position.y > player.getPosition().y - 4
					|| position.y < player.getPosition().y - 10) {
				if (collidableLeft == null || collidableRight == null) {
					position.x += enemyDx * 5;
				}
				if (collidableUp == null || collidableDown == null) {
					position.y += enemyDy * 5;
				}
			}
		}
	}
	
	public void atackLogic(Player player, CameraHelper cameraHelper) {
		if (yellowAura.overlaps(player.circle) && player.state != State.DEAD) {
			attackSequenceStarted = true;
		}
		
		if (aimingAura.overlaps(player.circle) && !attackSequenceStarted && player.state != State.DEAD) {
//			if (rand.nextInt(randomChargeCounter) == randomChargeCounter) {
//				aiming = true;
//			}
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
