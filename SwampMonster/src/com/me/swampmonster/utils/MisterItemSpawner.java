package com.me.swampmonster.utils;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Prop;
import com.me.swampmonster.models.enemies.Enemy;

public class MisterItemSpawner {

	public static int spavning_distance_x = 24;
	public static int spavning_distance_y = 24;
	
	static Random random = new Random();
	static int mapWith;
	static int mapHeight;
//	static TiledMapTileLayer collisionLayer;
	ItemGenerator itemGenerator = new ItemGenerator();
	Item item;
	int spawnRate;
	
	
	public Item spawnItem(Player player, Enemy enmy) {
		spawnRate = itemGenerator.generateSpawnRate(Player.score);

		int haveIitem;

		if (enmy.toughness != null) {
			haveIitem = random.nextInt(102);
		} else {
			haveIitem = random.nextInt(100);
		}

		if (haveIitem < 100) {
			if (player.oxygen <= 13) {
				item = itemGenerator.getMoreLikelyOxugenItem(Player.score);
			} else {
				item = itemGenerator.getItem(Player.score);
			}
		} else {
			return null;
		}
		item.position = new Vector2(enmy.position);
		mapWith = (int) TheController.collisionLayer.getTileWidth()
				* TheController.collisionLayer.getWidth();
		mapHeight = (int) TheController.collisionLayer.getTileHeight()
				* TheController.collisionLayer.getHeight();

		setItemTargetPos(item, player, enmy);

		while (!isValidTargetPosition(item, player)) {
			setItemTargetPos(item, player, enmy);
		}
		return item;
	}

	
	public static Item spawnPropsItem(Player player, Prop prop) {
		ItemGenerator itemGenerator = new ItemGenerator();
		Random random = new Random();

		int haveIitem = random.nextInt(100);
		Item item;
		if (haveIitem < 100) {
			if (player.oxygen <= 13) {
				item = itemGenerator.getMoreLikelyOxugenItem(Player.score);
			} else {
				item = itemGenerator.getItem(Player.score);
			}
		} else {
			return null;
		}

		item.position = new Vector2(prop.position);
		setItemTargetPos(item, player, prop);
		while (!isValidTargetPosition(item, player)) {
			setItemTargetPos(item, player, prop);
		}
		return item;
	}
	
	private static boolean isValidTargetPosition(Item item, Player player) {
		if (CollisionHelper.isCollidable(item.targetPos.x, item.targetPos.y, TheController.collisionLayer) == null
				&& !Intersector.overlaps(item.circle, player.rectanlge)) {
			return true;
		}
		return false;
	}

	private static void setItemTargetPos(Item i, Player player, AbstractGameObject enemy) {
		mapWith = (int) TheController.collisionLayer.getTileWidth()
				* TheController.collisionLayer.getWidth();
		mapHeight = (int) TheController.collisionLayer.getTileHeight()
				* TheController.collisionLayer.getHeight();
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
		i.collisionLayer = TheController.collisionLayer;
		i.spawned = true;
	}


}
