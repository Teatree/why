package com.me.swampmonster.utils;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.enemies.Enemy;

public class MisterSpawner {

	Random random = new Random();
	int mapWith;
	int mapHeight;
	TiledMapTileLayer collisionLayer;
	Vector2 v2;

	static ExecutorService threadPool = Executors.newCachedThreadPool();

	public void spawnEnemy(final L1 l, final Enemy enemy) {
//		threadPool.submit(new Runnable() {
//			public void run() {
				mapWith = (int) collisionLayer.getTileWidth()
						* collisionLayer.getWidth()
						- (int) l.player.getSprite().getWidth();
				mapHeight = (int) collisionLayer.getTileHeight()
						* collisionLayer.getHeight()
						- (int) l.player.getSprite().getHeight();
				v2 = calculateEnemiesPosition(l.player);
				while (!isValidPosition(v2)) {
//					System.err.println("enemy posX: " + enemy.getPosition().x
//							+ " posY: " + enemy.getPosition().y);
//					System.err.println("Enemy: " + enemy);
//					System.err.println("CollisionLayer TileWidth = " +collisionLayer.getTileWidth());
//					System.err.println("CollisionLayer TileHeight = " +collisionLayer.getTileHeight());
//					System.err.println("CollisionLayer Width = " + collisionLayer.getWidth() + " CollisionLayer Height = " + collisionLayer.getHeight());
					v2 = calculateEnemiesPosition(l.player);
				}
//				// System.out.println("Spawn enemy x=" + v2.x + " y=" + v2.y);
				enemy.position = v2;
//				System.err.println("enemy posX: " + enemy.getPosition().x
//						+ " posY: " + enemy.getPosition().y);
//				System.err.println("Enemy: " + enemy);
//				System.err.println("CollisionLayer TileWidth = " +collisionLayer.getTileWidth());
//				System.err.println("CollisionLayer TileHeight = " +collisionLayer.getTileHeight());
//				System.err.println("CollisionLayer Width = " + collisionLayer.getWidth() + " CollisionLayer Height = " + collisionLayer.getHeight());
//			}
//		});
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
		
		System.out.println("[ " + Constants.VIEWPORT_GUI_WIDTH / 2 + " x " + Constants.VIEWPORT_GUI_HEIGHT / 2 + " ]");

		switch (spawnRegion) {
		case 0: {
			minPosX = (int) (player.position.x-570 < 17 ? 17 : player.position.x-570);
			maxPosX = (int) (player.position.x+570 >= mapWith - 17 ? mapWith - 17 : player.position.x+570);
			minPosY = (int) (player.getPosition().y + Constants.VIEWPORT_GUI_HEIGHT / 2);
			maxPosY = (int) (player.position.y+570 >= mapHeight - 17 ? mapHeight - 17 : player.position.y+570)/*(mapHeight - player.getSprite().getHeight())*/;
			 System.out.println("case 0 minX=" + minPosX + " maxX =" + maxPosX
					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		case 1: {
			minPosX = (int) (player.getPosition().x + Constants.VIEWPORT_GUI_WIDTH / 2 + player.sprite.getWidth());
			maxPosX = (int) player.position.x + 570/*(mapWith - player.getSprite().getWidth())*/;
			minPosY = (int) (player.position.y-570 < 17 ? 17 : player.position.y-570);
			maxPosY = (int) (player.position.x+570 >= mapWith - 17 ? mapWith - 17 : player.position.x+570);
			 System.out.println("case 1 minX=" + minPosX + " maxX =" + maxPosX
					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		case 2: {
			minPosX = (int) (player.position.x-570 < 17 ? 17 : player.position.x-570);
			maxPosX = (int) (player.position.x+570 >= mapWith - 17 ? mapWith - 17 : player.position.x+570);
			minPosY = (int) (player.position.y-570 < 17 ? 17 : player.position.y-570);
			maxPosY = (int) (player.getPosition().y - Constants.VIEWPORT_GUI_HEIGHT / 2);
			 System.out.println("case 2 minX=" + minPosX + " maxX =" + maxPosX
					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		default: {
			minPosX = (int) (player.position.x-570 < 17 ? 17 : player.position.x-570);
			maxPosX = (int) (player.getPosition().x - Constants.VIEWPORT_GUI_HEIGHT / 2);
			minPosY = (int) (player.position.y+570 >= mapWith - 17 ? mapWith - 17 : player.position.y+570);
			maxPosY = (int) (/*mapHeight - player.getSprite().getHeight()*/(player.position.y+570 >= mapWith - 17 ? mapWith - 17 : player.position.y+570));
			 System.out.println("case 3 minX=" + minPosX + " maxX =" + maxPosX
					+ " minY=" + minPosY + " maxY=" + maxPosY);
		}
		}

		
		if (maxPosX >= mapWith - player.getSprite().getWidth()-18) {
			maxPosX = (int) (mapWith - player.getSprite().getWidth()-18);
			minPosX = (int) (player.getPosition().x + Constants.VIEWPORT_GUI_WIDTH / 2);
			System.out
			.println("maxPosX >= mapWith - player.getSprite().getWidth() == " + player.position.x );
		}
		
		if (maxPosY >= mapHeight - player.getSprite().getHeight()-18) {
			maxPosY = (int) (mapHeight - player.getSprite().getHeight()-18);
			minPosY = (int) (player.position.y - player.getPosition().y + Constants.VIEWPORT_GUI_HEIGHT / 2/* - 570*/);
			System.out
			.println("maxPosY >= mapHeight - player.getSprite().getHeight() == " + player.position.y);
		}
		
		if (minPosX >= mapWith - player.getSprite().getWidth()) {
			maxPosX = (int) (player.getPosition().x - Constants.VIEWPORT_GUI_WIDTH / 2);
			minPosX = 17;
			System.out.println("minPosX >= mapWith - player.getSprite().getWidth()");
		}

		if (minPosY >= mapHeight - player.getSprite().getHeight()) {
			maxPosY = (int) (player.getPosition().y - Constants.VIEWPORT_GUI_HEIGHT / 2);
			minPosY = 17;
			System.out.println("minPosY >= mapHeight - player.getSprite().getHeight()");
		}

		int temp;
		if (minPosX > maxPosX) {
			temp = minPosX;
			minPosX = maxPosX;
			maxPosX = temp;
		}
		if (minPosY > maxPosY) {
			temp = minPosY;
			minPosY = maxPosY;
			maxPosY = temp;
		}
		System.out.println("maxPosX = " + maxPosX + " minPosX = " + minPosX);
		System.out.println("maxPosY = " + maxPosY + " minPosY = " + minPosY);
		vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
		vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
		while (vector2.x < 1f || vector2.y < 1f) {
			vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
			vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
		}
		return vector2;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
}
