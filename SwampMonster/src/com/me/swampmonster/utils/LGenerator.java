package com.me.swampmonster.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Prop;
import com.me.swampmonster.models.TutorialLevel;
import com.me.swampmonster.models.Wave;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.enemies.EnemyLeech;
import com.me.swampmonster.models.enemies.EnemyMaggot;
import com.me.swampmonster.models.enemies.EnemyZombie;
import com.me.swampmonster.models.slots.PositiveEffects;

public class LGenerator {
	private static String DEFAULT_TILESET = "tileSet_SAND_WORLD\\d*.png";
	private static final int PLAYER_SPRITE_HEIGHT = 64;
	private static final int PLAYER_SPRITE_WIDTH = 32;
	public static final int TILE_SIZE = 16;

	Map<Integer, String> maps;
	Map<Integer, String> tileSets;
	private Random random;
	PropsSpawnGenerator propsSpawnGenerator;

	public LGenerator() {
		maps = new HashMap<Integer, String>();
		tileSets = new HashMap<Integer, String>();
		random = new Random();
		propsSpawnGenerator = new PropsSpawnGenerator();

		maps.put(0, "Map.tmx");
		maps.put(1, "Map.tmx");
		maps.put(2, "Map.tmx");
		maps.put(3, "Map.tmx");
		maps.put(4, "Map.tmx");

		tileSets.put(0, "tileSet_SAND_WORLD");
		tileSets.put(1, "tileSet_SAND_WORLD5");
		tileSets.put(2, "tileSet_SAND_WORLD3");
		tileSets.put(3, "tileSet_SAND_WORLD4");
		
		//
		System.out.println("ous " + Gdx.files);
		Gdx.files.local("Tiles.png").write(Gdx.files.internal("data\\Tiles.png").read(), false);
		Gdx.files.local("tileSet_SAND_WORLD.png").write(Gdx.files.internal("data\\tileSet_SAND_WORLD.png").read(), false);
		Gdx.files.local("tileSet_SAND_WORLD2.png").write(Gdx.files.internal("data\\tileSet_SAND_WORLD2.png").read(), false);
		Gdx.files.local("tileSet_SAND_WORLD3.png").write(Gdx.files.internal("data\\tileSet_SAND_WORLD3.png").read(), false);
		Gdx.files.local("tileSet_SAND_WORLD4.png").write(Gdx.files.internal("data\\tileSet_SAND_WORLD4.png").read(), false);
		Gdx.files.local("tileSet_SAND_WORLD5.png").write(Gdx.files.internal("data\\tileSet_SAND_WORLD5.png").read(), false);
	}

	public L1 createLevel(Player player) {
		String map = maps.get(random.nextInt(maps.size() - 1));
		String tileSet = tileSets.get(random.nextInt(tileSets.size()));

		boolean isLevelElite = random.nextBoolean();
//
		boolean hasLevelAtmosphere;
		if (isLevelElite) {
			hasLevelAtmosphere = false;
		} else {
			hasLevelAtmosphere = random.nextBoolean();
		}

		String br = Gdx.files.internal("data\\" + map).readString();
		if (!hasLevelAtmosphere) {
			br = br.replaceAll(DEFAULT_TILESET, tileSet + ".png");
		} else {
			br = br.replaceAll(DEFAULT_TILESET, "tileSet_SAND_WORLD2.png");
		}
		Gdx.files.local("MapTemp.tmx").writeString(br, false);
		
		L1.player = player;
		L1.hasAtmosphere = hasLevelAtmosphere;
		L1 level = new L1("tileSet_SAND_WORLD", "MapTemp.tmx", false);
		//:TODO isLevelElite !
		player.oxygen = Player.maxOxygen;
		player.health = Player.playerMaxHealth;
		player.setPositiveEffect(PositiveEffects.NONE);
		player.setNegativeEffect(NegativeEffects.NONE);
		player.movementSpeed = 1.4f;
		player.characterStatsBoard();
		TheController.collisionLayer = (TiledMapTileLayer) level.bunker
				.getMap().getLayers().get(0);
		Vector2 v2 = new Vector2();
		while (!isValidPosition(v2)) {
			v2 = calculateRandomPlayerPos();
		}
		player.setPosition(v2);
		propsSpawnGenerator.collisionLayer = TheController.collisionLayer;
		
		int propsInLevelAmount = random.nextInt(8) + 3;
		L1.props = new ArrayList<Prop>();
		for (int i = 0; i < propsInLevelAmount; i++){
			L1.props.add(propsSpawnGenerator.getSomeProp(player));
		}
		for (Prop p : L1.props) {
			if (p != null)
				propsSpawnGenerator.spawnProp(player, p);
		}
		if(TheController.skill != null && !TheController.paused){
			TheController.coolDownCounter = 0;
			TheController.coolDownAngle = 0;
			TheController.coolDownStep = 0;
		}
		return level;
	}
	
	public L1 createTutorialLevel(){
		String br = Gdx.files.internal("data\\" + "Map2.tmx").readString();
		Gdx.files.local("MapTemp.tmx").writeString(br, false);
		L1 tutorialLevel = new TutorialLevel("tileSet_SAND_WORLD", "MapTemp.tmx");
		TutorialLevel.player = new Player(new Vector2(230,230));
		TutorialLevel.player.oxygen = Player.maxOxygen;
		TutorialLevel.player.health = Player.playerMaxHealth;
		TutorialLevel.player.setPositiveEffect(PositiveEffects.NONE);
		TutorialLevel.player.setNegativeEffect(NegativeEffects.NONE);
		TutorialLevel.player.movementSpeed = 1.4f;
		TutorialLevel.player.characterStatsBoard();
		TheController.collisionLayer = (TiledMapTileLayer) tutorialLevel.bunker.getMap().getLayers().get(0);
		
		Wave wave = new Wave();
		wave.enemies = new Stack<Enemy>();
		wave.enemiesOnBattleField = 3;
		wave.enemies.push(new EnemyMaggot(new Vector2(400,400)));
		wave.enemies.push(new EnemyZombie(new Vector2(440,400)));
		wave.enemies.push(new EnemyLeech(new Vector2(480,400)));
		tutorialLevel.wave = wave;
		tutorialLevel.enemiesOnStage = wave.enemies;
		tutorialLevel.wave.enemies = new Stack<Enemy>();
		tutorialLevel.wavesAmount = 1;
		
		
		return tutorialLevel;
		
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
		int maxPosX = TheController.collisionLayer.getWidth()
				* TILE_SIZE - PLAYER_SPRITE_WIDTH - 200;
		int minPosY = 230;
		int maxPosY = TheController.collisionLayer.getHeight()
				* TILE_SIZE - PLAYER_SPRITE_HEIGHT - 200;

		vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
		vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
		while (vector2.x < 1f || vector2.y < 1f) {
			vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
			vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
		}
		return vector2;
	}
}
