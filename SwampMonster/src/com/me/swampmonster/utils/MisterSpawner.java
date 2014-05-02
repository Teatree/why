package com.me.swampmonster.utils;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	Vector2 v2;
	
	static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	public void spawnEnemy(final L1 l, final Enemy enemy){
		threadPool.submit(new Runnable() {
            public void run() {
            	mapWith = (int)collisionLayer.getTileWidth()*collisionLayer.getWidth() - (int)l.getPlayer().getSprite().getWidth();
            	mapHeight = (int)collisionLayer.getTileHeight()*collisionLayer.getHeight() - (int)l.getPlayer().getSprite().getHeight();
            	v2 = calculateEnemiesPosition(l.getPlayer());
            	while(!isValidPosition(v2 )){
            		v2 = calculateEnemiesPosition(l.getPlayer());
            	}
            	System.out.print("Spawn enemy x=" + v2.x + " y=" + v2.y);	
            	System.out.println(CollisionHelper.isCollidable(v2.x, v2.y, collisionLayer) == null);
            	enemy.setPosition(v2);
            }
        });
	}
	
	private boolean isValidPosition(Vector2 v2) {
		if (CollisionHelper.isCollidable(v2.x, v2.y, collisionLayer) == null) {
			return true;
		}
		return false;
	}

	public Vector2 calculateEnemiesPosition(Player player) {
		Vector2 vector2 = new Vector2();
		int spawnRegion = random.nextInt(3); // 0=north, 1=east, 2=south, 3=west
		int minPosX;
		int maxPosX;
		int minPosY;
		int maxPosY;

		switch (spawnRegion) {
		case 0: {
			minPosX = 1;
			maxPosX = mapWith;
			minPosY = (int) (player.getPosition().y + Constants.VIEWPORT_GUI_HEIGHT / 2);
			maxPosY = (int) (mapHeight - player.getSprite().getHeight());
			System.out.println("case 1 minX=" + maxPosX + " maxX =" + maxPosX
					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		case 1: {
			minPosX = (int) (player.getPosition().x + Constants.VIEWPORT_GUI_HEIGHT / 2);
			maxPosX = (int) (mapWith - player.getSprite().getWidth());
			minPosY = 1;
			maxPosY = mapHeight;
			System.out.println("case 2 minX=" + maxPosX + " maxX =" + maxPosX
					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		case 2: {
			minPosX = 0;
			maxPosX = mapWith;
			minPosY = 1;
			maxPosY = (int) (player.getPosition().y + Constants.VIEWPORT_GUI_HEIGHT / 2);
			System.out.println("case 2 minX=" + minPosX + " maxX =" + maxPosX
					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		default: {
			minPosX = 1;
			maxPosX = (int) (player.getPosition().x - Constants.VIEWPORT_GUI_HEIGHT / 2);
			minPosY = 1;
			maxPosY = (int) (mapHeight - player.getSprite().getHeight());
			System.out.println("case 3 minX=" + maxPosX + " maxX =" + maxPosX
					+ " minY=" + minPosY + " maxY=" + maxPosY);
		}
		}
		
		if (minPosX >= mapWith - player.getSprite().getWidth()){
			maxPosX = (int)(player.getPosition().x - Constants.VIEWPORT_GUI_WIDTH/2);
			minPosX = 0;
			System.out.println("minPosX >= mapWith - player.getSprite().getWidth()");
		}
		
		if (minPosY >= mapHeight - player.getSprite().getHeight()){
			maxPosY = (int)(player.getPosition().y - Constants.VIEWPORT_GUI_HEIGHT/2);
			minPosY = 0;
			System.out.println("minPosY >= mapHeight - player.getSprite().getHeight()");
		}
		
		vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
		vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
		return vector2;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
}
