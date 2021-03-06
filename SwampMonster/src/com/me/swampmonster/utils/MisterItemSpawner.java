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
import com.me.swampmonster.models.TutorialLevel;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.items.Oxygen;

public class MisterItemSpawner {

	public static int spavning_distance_x = 24;
	public static int spavning_distance_y = 24;

	static Random random = new Random();
	static int mapWith;
	static int mapHeight;
	// static TiledMapTileLayer collisionLayer;
	static ItemGenerator itemGenerator = new ItemGenerator();
	// WeaponGenerator weaponGenerator = new WeaponGenerator();

	Item item;
	int spawnRate;

	public Item spawnItem(Player player, Enemy enmy) {
		// spawnRate = itemGenerator.generateSpawnRate(Player.levelsScore);
		if (TutorialLevel.step == 12) {
			item = new Oxygen();
		} else {

			int haveItem = 0;

			if (enmy.toughness != null) {
				haveItem = random.nextInt(102);
			} else {
				haveItem = random.nextInt(100);
			}
			// if (haveIitem < 100) {
			if (haveItem > 10) {
				if (player.oxygen <= 13) {
					item = itemGenerator
							.getMoreLikelyOxugenItem(Player.absoluteScore);
				} else {
					item = itemGenerator.getItem(Player.absoluteScore);
					// item =
					// itemGenerator.generateSpecialItem(Player.absoluteScore);
				}
			} else {
				return null;
			}
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
		// ItemGenerator itemGenerator = new ItemGenerator();
		Item item;
		item = itemGenerator.getItem(Player.absoluteScore);
		if (item != null) {
			item.position = new Vector2(prop.position);
			setItemTargetPos(item, player, prop);
			while (!isValidTargetPosition(item, player)) {
				setItemTargetPos(item, player, prop);
			}
		}
		return item;
	}

	private static boolean isValidTargetPosition(Item item, Player player) {
		if (CollisionHelper.isCollidable(item.targetPos.x, item.targetPos.y,
				TheController.collisionLayer) == null
				&& !Intersector.overlaps(item.circle, player.rectanlge)) {
			return true;
		}
		return false;
	}

	private static void setItemTargetPos(Item i, Player player,
			AbstractGameObject enemy) {
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
