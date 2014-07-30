package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;

public class LGenerator {
	private static String DEFAULT_TILESET = "tileSet_SAND_WORLD\\d*.png";
	private static final int PLAYER_SPRITE_HEIGHT = 64;
	private static final int PLAYER_SPRITE_WIDTH = 32;
	public static final int TILE_SIZE = 16;

	Map<Integer, String> maps;
	Map<Integer, String> tileSets;
	private Random random;

	public LGenerator() {
		maps = new HashMap<Integer, String>();
		tileSets = new HashMap<Integer, String>();
		random = new Random();

		maps.put(0, "Map.tmx");
		maps.put(1, "Map.tmx");
		maps.put(2, "Map2.tmx");
		maps.put(3, "Map2.tmx");
		maps.put(4, "Map.tmx");

		tileSets.put(0, "tileSet_SAND_WORLD");
		tileSets.put(1, "tileSet_SAND_WORLD2");
		tileSets.put(2, "tileSet_SAND_WORLD3");
		tileSets.put(3, "tileSet_SAND_WORLD");
	}

	public L1 createLevel(Player player) {
		String map = maps.get(random.nextInt(maps.size() - 1));
		String tileSet = tileSets.get(random.nextInt(tileSets.size()));
		
		boolean isLevelElite = random.nextBoolean();
		
		boolean hasLevelAtmosphere;
		if (isLevelElite){
			hasLevelAtmosphere = false;
		} else {
			hasLevelAtmosphere = random.nextBoolean();
		}

		String br = Gdx.files.local("data\\" + map).readString();
		if (L1.hasAtmosphere){
			br = br.replaceAll(DEFAULT_TILESET, ".png");
		} else {
			br = br.replaceAll(DEFAULT_TILESET,"tileSet_SAND_WORLD4.png");
		}
		Gdx.files.local("data\\" + map).writeString(br, false);
		br = Gdx.files.local("data\\" + map).readString();
		
		
		L1 level = new L1(player, "tileSet_SAND_WORLD", "data/" + map, hasLevelAtmosphere, isLevelElite);
		player.oxygen = Player.maxOxygen;
		player.health = Player.playerMaxHealth;
		player.characterStatsBoard();
		TheController.collisionLayer = (TiledMapTileLayer) level.bunker.getMap().getLayers().get(0);
		Vector2 v2 = new Vector2();
		while (!isValidPosition(v2)) {
			v2 = calculateRandomPlayerPos();
		}
		player.setPosition(v2);
		return level;
	}

	private boolean isValidPosition(Vector2 v2) {
		if (CollisionHelper.isCollidable(v2.x, v2.y,
				TheController.collisionLayer) == null) {
			return true;
		}
		return false;
	}

	public Vector2 calculateRandomPlayerPos() {
		Vector2 vector2 = new Vector2();
		int minPosX = 230;
		int maxPosX = (int) (TheController.collisionLayer.getWidth()
				* TILE_SIZE - PLAYER_SPRITE_WIDTH - 200);
		int minPosY = 230;
		int maxPosY = (int) (TheController.collisionLayer.getHeight()
				* TILE_SIZE - PLAYER_SPRITE_HEIGHT - 200);

		vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
		vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
		while (vector2.x < 1f || vector2.y < 1f) {
			vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
			vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
		}
		return vector2;
	}
}
