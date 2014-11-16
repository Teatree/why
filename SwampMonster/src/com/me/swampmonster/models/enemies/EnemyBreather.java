package com.me.swampmonster.models.enemies;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.CameraHelper;

public class EnemyBreather extends Enemy {

	static {
		levelByPlayesScore = new ArrayList<Integer>();
		levelByPlayesScore.add(1000);
		levelByPlayesScore.add(3000);
	}
	public EnemyBreather(Vector2 position) {
		super(position);
	}
	
	@Override
	public void update(TiledMapTileLayer collisionLayer, Player player,
			CameraHelper cameraHelper, List<Enemy> enemies) {
		super.update(collisionLayer, player, cameraHelper, enemies);
		if (!L1.hasAtmosphere){
			health -= 0.0004;
		}
	}

	@Override
	public void difficultyLevelParams() {
		if(difficultyLevel==0){
			points = 25;
			minHealth = 15;
			maxHealth = 30;
			minDamage = 1;
			maxDamage = 3;
			minSpeed = 10;
			maxSpeed = 15;
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
			points = 25;
			minHealth = 15;
			maxHealth = 30;
			minDamage = 1;
			maxDamage = 3;
			minSpeed = 10;
			maxSpeed = 15;
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
			points = 25;
			minHealth = 15;
			maxHealth = 30;
			minDamage = 1;
			maxDamage = 3;
			minSpeed = 10;
			maxSpeed = 15;
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
