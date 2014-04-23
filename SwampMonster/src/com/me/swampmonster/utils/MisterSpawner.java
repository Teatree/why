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
	
	int mapWith;
	int mapHeight;
	TiledMapTileLayer collisionLayer;
	
	public void spawnEnemy(L1 l, Enemy enemy){
		mapWith = (int)collisionLayer.getTileWidth()*collisionLayer.getWidth();
		mapHeight = (int)collisionLayer.getTileHeight()*collisionLayer.getHeight();
		enemy.setPosition(calculateEnemiesPosition(l.getPlayer()));
		Vector2 v2 = calculateEnemiesPosition(l.getPlayer());
		if(!isValidPosition(v2, enemy, l)){
			enemy.setPosition(calculateEnemiesPosition(l.getPlayer()));
		}
	}
	
	private boolean isValidPosition(Vector2 v2, Enemy enemy, L1 l) {
		if(CollisionHelper.isCollidable(v2.x, v2.y, collisionLayer) != null || CollisionHelper.isCollidableEnemy(enemy, l.enemiesOnStage) != null
				|| v2.x+enemy.getSprite().getWidth() >= mapWith || v2.y+enemy.getSprite().getHeight() >= mapHeight){
			return false;
		}
		return true;
	}

	public Vector2 calculateEnemiesPosition(Player player){
		Vector2 vector2 = new Vector2();
		
		int minPosX = (int)(player.getPosition().x + Constants.VIEWPORT_GUI_WIDTH/2);
		int maxPosX = mapWith/2;
		int minPosY = (int)(player.getPosition().y + Constants.VIEWPORT_GUI_HEIGHT/2);
		int maxPosY = mapHeight/2;
		
		if (minPosX >= mapWith){
			maxPosX = (int)(player.getPosition().x - Constants.VIEWPORT_GUI_WIDTH/2);
			minPosX = 0;
		}
		
		if (minPosY >= mapHeight){
			maxPosY = (int)(player.getPosition().y - Constants.VIEWPORT_GUI_HEIGHT/2);
			minPosY = 0;
		}
		
		System.out.println(" map width = " + mapWith + " map height " + mapHeight);
		vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
		vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
		System.out.println(" x = " + vector2.x + " y = " + vector2.y);
		return vector2;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
	
	
	
}
