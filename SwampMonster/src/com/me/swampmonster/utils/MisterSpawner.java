package com.me.swampmonster.utils;

import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.Enemy;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;

public class MisterSpawner {
	Random random = new Random();
	
	public void spawnEnemy(L1 l, TiledMapTileLayer collisionLayer, Enemy enemy){
		enemy.setPosition(calculateEnemiesPosition(l.getPlayer()));
		Vector2 v2 = calculateEnemiesPosition(l.getPlayer());
		if(!isValidPosition(v2, collisionLayer, enemy, l)){
			enemy.setPosition(calculateEnemiesPosition(l.getPlayer()));
		}
	}
	
	private boolean isValidPosition(Vector2 v2, TiledMapTileLayer collisionLayer, Enemy enemy, L1 l) {
		if(CollisionHelper.isCollidable(v2.x, v2.y, collisionLayer) != null || CollisionHelper.isCollidableEnemy(enemy, l.enemiesOnStage) != null
				|| v2.x+enemy.getSprite().getWidth() >= collisionLayer.getWidth() || v2.y+enemy.getSprite().getHeight() >= collisionLayer.getHeight()){
			return false;
		}
		return true;
	}

	public Vector2 calculateEnemiesPosition(Player player){
		Vector2 vector2 = new Vector2();
		vector2.y = player.getPosition().x + random.nextInt((int)Constants.VIEWPORT_GUI_HEIGHT/2 - (int)Constants.VIEWPORT_GUI_HEIGHT/5) + (int)Constants.VIEWPORT_GUI_HEIGHT/5;
		vector2.x = player.getPosition().y + random.nextInt((int)Constants.VIEWPORT_GUI_WIDTH/2 - (int)Constants.VIEWPORT_GUI_WIDTH/5) + (int)Constants.VIEWPORT_GUI_WIDTH/5;
		return vector2;
	}
	
}
