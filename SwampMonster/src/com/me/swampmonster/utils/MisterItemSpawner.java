package com.me.swampmonster.utils;

import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.Enemy;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;

public class MisterItemSpawner {

	public static int spavning_distance_x = 51;
	public static int spavning_distance_y = 51;
	
	Random random = new Random();
	int mapWith;
	int mapHeight;
	TiledMapTileLayer collisionLayer;
	ItemGenerator itemGenerator = new ItemGenerator();
	Item item;
	int spawnRate;
	
	public Item spawnItem(Player player, Enemy enmy) {
		spawnRate = itemGenerator.generateSpawnRate(player.getPoints());

		int haveIitem;

		if (enmy.toughness != null) {
			haveIitem = random.nextInt(102);
		} else {
			haveIitem = random.nextInt(100);
		}

		if (haveIitem < 100) {
			if (player.oxygen <= 13) {
				item = itemGenerator.getMoreLikelyOxugenItem(player.getPoints());
			} else {
				item = itemGenerator.getItem(player.getPoints());
			}
		} else {
			return null;
		}
		item.position = new Vector2(enmy.position);
//		item.position.x = enmy.position.x;
//		item.position.y = enmy.position.y;
		mapWith = (int) collisionLayer.getTileWidth()
				* collisionLayer.getWidth();
		mapHeight = (int) collisionLayer.getTileHeight()
				* collisionLayer.getHeight();

		setItemTargetPos(item, player, enmy);

		while (!isValidTargetPosition(item, player)) {
			setItemTargetPos(item, player, enmy);
		}
//		item.position = new Vector2(item.spawnPos.x, item.spawnPos.y);
		return item;
	}

	private boolean isValidTargetPosition(Item item, Player player) {
		if (CollisionHelper.isCollidable(item.targetPos.x, item.targetPos.y, collisionLayer) == null
				&& !Intersector.overlaps(item.getCircle(), player.getRectanlge())) {
			return true;
		}
		return false;
	}

	private void setItemTargetPos(Item i, Player player, Enemy enemy) {
		int minX = (int) (enemy.getPosition().x - spavning_distance_x);
		if (minX <= 0) {
			minX = 1;
		}
		int maxX = (int) (enemy.getPosition().x + enemy.sprite.getWidth() + spavning_distance_x);
		if (maxX >= mapWith) {
			maxX = (int) (mapWith - i.sprite.getWidth() - 1);
		}
		int minY = (int) enemy.getPosition().y - spavning_distance_y;
		if (minY <= 0) {
			minY = 1;
		}
		int maxY = (int) (enemy.getPosition().y + enemy.sprite.getHeight() + spavning_distance_y);
		if (maxY >= mapHeight) {
			maxY = (int) (mapHeight - i.sprite.getHeight() - 1);
		}

		Vector2 vector2 = new Vector2();
		vector2.x = random.nextInt(maxX - minX) + minX;
		vector2.y = random.nextInt(maxY - minY) + minY;
		i.targetPos = vector2;
		i.spawned = true;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

}
