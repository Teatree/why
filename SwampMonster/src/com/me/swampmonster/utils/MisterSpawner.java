package com.me.swampmonster.utils;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.enemies.Enemy;

public class MisterSpawner {

	private static final int SPAWN_RADIUS = 570;
	private static final int MIN_SPAWN_POS = 90;
	Random random = new Random();
	int mapWith;
	int mapHeight;
	public TiledMapTileLayer collisionLayer;
	Vector2 v2;

	static ExecutorService threadPool = Executors.newCachedThreadPool();

	public void spawnEnemy(final L1 l, final Enemy enemy) {
				mapWith = (int) collisionLayer.getTileWidth()
						* collisionLayer.getWidth()
						- (int) L1.player.getSprite().getWidth();
				mapHeight = (int) collisionLayer.getTileHeight()
						* collisionLayer.getHeight()
						- (int) L1.player.getSprite().getHeight();
				v2 = calculateEnemiesPosition(L1.player, enemy);
				while (!isValidPosition(v2)) {
//					System.err.println("enemy posX: " + enemy.getPosition().x
//							+ " posY: " + enemy.getPosition().y);
//					System.err.println("Enemy: " + enemy);
//					System.err.println("CollisionLayer TileWidth = " +collisionLayer.getTileWidth());
//					System.err.println("CollisionLayer TileHeight = " +collisionLayer.getTileHeight());
//					System.err.println("CollisionLayer Width = " + collisionLayer.getWidth() + " CollisionLayer Height = " + collisionLayer.getHeight());
					v2 = calculateEnemiesPosition(L1.player, enemy);
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

	public Vector2 calculateEnemiesPosition(Player player, AbstractGameObject enemy) {
		Vector2 vector2 = new Vector2();
		int spawnRegion = random.nextInt(3); // 0=north, 1=east, 2=south, 3=west
		int minPosX;
		int maxPosX;
		int minPosY;
		int maxPosY;
		
//		System.out.println("[ " + Constants.VIEWPORT_GUI_WIDTH / 2 + " x " + Constants.VIEWPORT_GUI_HEIGHT / 2 + " ]");

		switch (spawnRegion) {
		case 0: {
			minPosX = (int) (player.position.x-SPAWN_RADIUS < MIN_SPAWN_POS ? MIN_SPAWN_POS : player.position.x-SPAWN_RADIUS);
			maxPosX = (int) (player.position.x+SPAWN_RADIUS >= mapWith - MIN_SPAWN_POS ? mapWith - MIN_SPAWN_POS : player.position.x+SPAWN_RADIUS);
			minPosY = (int) (player.getPosition().y + Constants.VIEWPORT_GUI_HEIGHT / 2);
			maxPosY = (int) (player.position.y+SPAWN_RADIUS >= mapHeight - MIN_SPAWN_POS ? mapHeight - MIN_SPAWN_POS : player.position.y+SPAWN_RADIUS)/*(mapHeight - player.getSprite().getHeight())*/;
//			 System.out.println("case 0 minX=" + minPosX + " maxX =" + maxPosX
//					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		case 1: {
			minPosX = (int) (player.getPosition().x + Constants.VIEWPORT_GUI_WIDTH / 2 + player.sprite.getWidth());
			maxPosX = (int) player.position.x + SPAWN_RADIUS/*(mapWith - player.getSprite().getWidth())*/;
			minPosY = (int) (player.position.y-SPAWN_RADIUS < MIN_SPAWN_POS ? MIN_SPAWN_POS : player.position.y-SPAWN_RADIUS);
			maxPosY = (int) (player.position.x+SPAWN_RADIUS >= mapWith - MIN_SPAWN_POS ? mapWith - MIN_SPAWN_POS : player.position.x+SPAWN_RADIUS);
//			 System.out.println("case 1 minX=" + minPosX + " maxX =" + maxPosX
//					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		case 2: {
			minPosX = (int) (player.position.x-SPAWN_RADIUS < MIN_SPAWN_POS ? MIN_SPAWN_POS : player.position.x-SPAWN_RADIUS);
			maxPosX = (int) (player.position.x+SPAWN_RADIUS >= mapWith - MIN_SPAWN_POS ? mapWith - MIN_SPAWN_POS : player.position.x+SPAWN_RADIUS);
			minPosY = (int) (player.position.y-SPAWN_RADIUS < MIN_SPAWN_POS ? MIN_SPAWN_POS : player.position.y-SPAWN_RADIUS);
			maxPosY = (int) (player.getPosition().y - Constants.VIEWPORT_GUI_HEIGHT / 2 - enemy.sprite.getHeight());
//			 System.out.println("case 2 minX=" + minPosX + " maxX =" + maxPosX
//					+ " minY=" + minPosY + " maxY=" + maxPosY);
			break;
		}
		default: {
			minPosX = (int) (player.position.x-SPAWN_RADIUS < MIN_SPAWN_POS ? MIN_SPAWN_POS : player.position.x-SPAWN_RADIUS);
			maxPosX = (int) (player.getPosition().x - Constants.VIEWPORT_GUI_HEIGHT / 2-enemy.sprite.getWidth());
			minPosY = (int) (player.position.y+SPAWN_RADIUS >= mapWith - MIN_SPAWN_POS ? mapWith - MIN_SPAWN_POS : player.position.y+SPAWN_RADIUS);
			maxPosY = (int) (/*mapHeight - player.getSprite().getHeight()*/(player.position.y+SPAWN_RADIUS >= mapWith - MIN_SPAWN_POS ? mapWith - MIN_SPAWN_POS : player.position.y+SPAWN_RADIUS));
//			 System.out.println("case 3 minX=" + minPosX + " maxX =" + maxPosX
//					+ " minY=" + minPosY + " maxY=" + maxPosY);
		}
		}

		
		if (maxPosX >= mapWith - player.sprite.getWidth()-18) {
			maxPosX = (int) (mapWith - player.getSprite().getWidth()-18);
			minPosX = (int) (player.getPosition().x + Constants.VIEWPORT_GUI_WIDTH / 2);
//			System.out.println("maxPosX >= mapWith - player.getSprite().getWidth() == " + player.position.x );
		}
		
		if (maxPosY >= mapHeight - player.sprite.getHeight()-18) {
			maxPosY = (int) (mapHeight - player.getSprite().getHeight()-18);
			minPosY = (int) (player.position.y + Constants.VIEWPORT_GUI_HEIGHT / 2/* - SPAWN_RADIUS*/);
//			System.out.println("maxPosY >= mapHeight - player.getSprite().getHeight() == " + player.position.y);
		}
		
		if (minPosX >= mapWith - player.sprite.getWidth()) {
			maxPosX = (int) (player.getPosition().x - Constants.VIEWPORT_GUI_WIDTH / 2);
			minPosX = MIN_SPAWN_POS;
//			System.out.println("minPosX >= mapWith - player.getSprite().getWidth()");
		}

		if (minPosY >= mapHeight - player.sprite.getHeight()) {
			maxPosY = (int) (player.getPosition().y - Constants.VIEWPORT_GUI_HEIGHT / 2);
			minPosY = MIN_SPAWN_POS;
//			System.out.println("minPosY >= mapHeight - player.getSprite().getHeight()");
		}

		int temp;
		if (minPosX > maxPosX) {
//			System.out.println("switch x");
			temp = minPosX;
			minPosX = maxPosX;
			maxPosX = temp;
		}
		if (minPosY > maxPosY) {
//			System.out.println("switch y");
			temp = minPosY;
			minPosY = maxPosY;
			maxPosY = temp;
		}
//		System.out.println("maxPosX = " + maxPosX + " minPosX = " + minPosX);
//		System.out.println("maxPosY = " + maxPosY + " minPosY = " + minPosY);
		vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
		vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
		while (vector2.x < 1f || vector2.y < 1f) {
			vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
			vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
		}
		return vector2;
	}

	public Vector2 teleportPlayerPos(){
		mapWith = (int) collisionLayer.getTileWidth()
				* collisionLayer.getWidth()
				- (int) L1.player.getSprite().getWidth();
		mapHeight = (int) collisionLayer.getTileHeight()
				* collisionLayer.getHeight()
				- (int) L1.player.getSprite().getHeight();
		v2 = calculateEnemiesPosition(L1.player, L1.player);
		while (!isValidPosition(v2)) {
			v2 = calculateEnemiesPosition(L1.player, L1.player);
		}
		L1.player.position = v2;
		TheController.touchPos = new Vector3(v2.x, v2.y, 0);
		return v2;
	}
	
	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
}
