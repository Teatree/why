package com.me.swampmonster.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;

public class MisterItemSpawner {

	Random random = new Random();
	int mapWith;
	int mapHeight;
	TiledMapTileLayer collisionLayer;
	ItemGenerator itemGenerator = new ItemGenerator();
	Item item;
	int spawnRate;
	
	public Item spawnItem(Player player) {
		spawnRate = itemGenerator.generateSpawnRate(player.getPoints());
		item = itemGenerator.getItem(player.getPoints());

		mapWith = (int) collisionLayer.getTileWidth()
				* collisionLayer.getWidth();
		mapHeight = (int) collisionLayer.getTileHeight()
				* collisionLayer.getHeight();
		setItemPos(item, player);
		while (!isValidPosition(item, player)) {
			setItemPos(item, player);
		}
		return item;
	}

	private boolean isValidPosition(Item item, Player player) {
		if (CollisionHelper.isCollidable(item.getPosition().x,
				item.getPosition().y, collisionLayer) == null
				&& !Intersector.overlaps(player.invalidSpawnArea,
						item.getCircle())) {
			return true;
		}
		return false;
	}

	private void setItemPos(Item i, Player player) {
		int minX = (int) (player.getPosition().x - Constants.VIEWPORT_WIDTH * 3 / 4);
		if (minX <= 0) {
			minX = (int) (i.sprite.getWidth() + 1);
		}
		int maxX = (int) (player.getPosition().x + player.sprite.getWidth() + Constants.VIEWPORT_WIDTH * 3 / 4);
		if (maxX >= mapWith) {
			maxX = (int) (mapWith - i.sprite.getWidth() - 1);
		}
		int minY = (int) (player.getPosition().y - Constants.VIEWPORT_HEIGHT * 3 / 4);
		if (minY <= 0) {
			minY = (int) (i.sprite.getHeight() + 1);
		}
		int maxY = (int) (player.getPosition().y + player.sprite.getHeight() + Constants.VIEWPORT_HEIGHT * 3 / 4);
		if (maxY >= mapHeight) {
			maxY = (int) (mapHeight - i.sprite.getHeight() - 1);
		}

		Vector2 vector2 = new Vector2();
		vector2.x = random.nextInt(maxX - minX) + minX;
		vector2.y = random.nextInt(maxY - minY) + minY;
		i.setPosition(vector2);
		i.spawned = true;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

}
