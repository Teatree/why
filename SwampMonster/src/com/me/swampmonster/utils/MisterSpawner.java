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
//		System.out.println("number of threads " + Runtime.getRuntime().availableProcessors());
		threadPool.submit(new Runnable() {
			public void run() {
				mapWith = (int) collisionLayer.getTileWidth()
						* collisionLayer.getWidth()
						- (int) l.player.getSprite().getWidth();
				mapHeight = (int) collisionLayer.getTileHeight()
						* collisionLayer.getHeight()
						- (int) l.player.getSprite().getHeight();
				v2 = calculateEnemiesPosition(l.player);
				while (!isValidPosition(v2)) {
//					System.out.println("73");
					v2 = calculateEnemiesPosition(l.player);
				}
//				// System.out.println("Spawn enemy x=" + v2.x + " y=" + v2.y);
				enemy.position = v2;
//				System.err.println("enemy posX: " + enemy.getPosition().x
//						+ " posY: " + enemy.getPosition().y);
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
			minPosX = 22;
			maxPosX = mapWith;
			minPosY = (int) (player.getPosition().y + Constants.VIEWPORT_GUI_HEIGHT / 2);
			maxPosY = (int) (mapHeight - player.getSprite().getHeight());
			// System.out.println("case 0 minX=" + minPosX + " maxX =" + maxPosX
//					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		case 1: {
			minPosX = (int) (player.getPosition().x + Constants.VIEWPORT_GUI_HEIGHT / 2);
			maxPosX = (int) (mapWith - player.getSprite().getWidth());
			minPosY = 22;
			maxPosY = mapHeight;
			// System.out.println("case 1 minX=" + minPosX + " maxX =" + maxPosX
//					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		case 2: {
			minPosX = 22;
			maxPosX = mapWith - 1;
			minPosY = 22;
			maxPosY = (int) (player.getPosition().y - Constants.VIEWPORT_GUI_HEIGHT / 2);
			// System.out.println("case 2 minX=" + minPosX + " maxX =" + maxPosX
//					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		default: {
			minPosX = 22;
			maxPosX = (int) (player.getPosition().x - Constants.VIEWPORT_GUI_HEIGHT / 2);
			minPosY = 22;
			maxPosY = (int) (mapHeight - player.getSprite().getHeight());
			// System.out.println("case 3 minX=" + minPosX + " maxX =" + maxPosX
//					+ " minY=" + minPosY + " maxY=" + maxPosY);
		}
		}

		if (minPosX >= mapWith - player.getSprite().getWidth()) {
			maxPosX = (int) (player.getPosition().x - Constants.VIEWPORT_GUI_WIDTH / 2);
			minPosX = 1;
//			System.out
//					.println("minPosX >= mapWith - player.getSprite().getWidth()");
		}

		if (minPosY >= mapHeight - player.getSprite().getHeight()) {
			maxPosY = (int) (player.getPosition().y - Constants.VIEWPORT_GUI_HEIGHT / 2);
			minPosY = 1;
//			System.out
//					.println("minPosY >= mapHeight - player.getSprite().getHeight()");
		}

		vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
		vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
		while (vector2.x < 1f || vector2.y < 1f) {
			System.out.println("loopty loop ");
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
