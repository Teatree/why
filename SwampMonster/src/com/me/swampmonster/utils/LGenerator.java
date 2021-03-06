package com.me.swampmonster.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Prop;
import com.me.swampmonster.models.TutorialLevel;
import com.me.swampmonster.models.Wave;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.enemies.EnemyMaggot;
import com.me.swampmonster.models.enemies.EnemyZombie;
import com.me.swampmonster.models.enemies.PossessedTurret;
import com.me.swampmonster.models.slots.PositiveEffects;

public class LGenerator {
	private static String[] RUINS_TILESETS = {
		"tileSet_SAND_WORLD_RUINS1.png",
		"tileSet_SAND_WORLD_RUINS2.png"
	};
	private static String[] LAB_TILESETS = {
		"tileSet_SAND_WORLD2.png",
		"tileSet_SAND_WORLD2.png"
	};
	private static String DEFAULT_TILESET = "tileSet_SAND_WORLD\\d*.png";
	private static final int PLAYER_SPRITE_HEIGHT = 64;
	private static final int PLAYER_SPRITE_WIDTH = 32;
	public static final int TILE_SIZE = 16;

	static Map<Integer, String> maps;
	static Map<Integer, String> tileSets;
	private static Random random;
	static PropsSpawnGenerator propsSpawnGenerator;
	
	public static  String lastTileSet;
	public static String lastMap;
	public static boolean hadLastAtmosphere;
	public static boolean wasLastRuins;
	public static boolean wasLastElite;

	static  {
		maps = new HashMap<Integer, String>();
		tileSets = new HashMap<Integer, String>();
		
		random = new Random();
		propsSpawnGenerator = new PropsSpawnGenerator();

		maps.put(0, "Map.tmx");
		maps.put(1, "Map.tmx");
		maps.put(2, "Map2.tmx");
		maps.put(3, "Map3.tmx");
		maps.put(4, "Map3.tmx");

		tileSets.put(0, "tileSet_SAND_WORLD");
		tileSets.put(1, "tileSet_SAND_WORLD5");
		tileSets.put(2, "tileSet_SAND_WORLD3");
		tileSets.put(3, "tileSet_SAND_WORLD4");
		
		//
		Gdx.files.local("Tiles.png").write(Gdx.files.internal("data\\Tiles.png").read(), false);
		Gdx.files.local("tileSet_SAND_WORLD.png").write(Gdx.files.internal("data\\tileSet_SAND_WORLD.png").read(), false);
		Gdx.files.local(LAB_TILESETS[0]).write(Gdx.files.internal("data\\tileSet_SAND_WORLD2.png").read(), false);
		Gdx.files.local(LAB_TILESETS[1]).write(Gdx.files.internal("data\\tileSet_SAND_WORLD2.png").read(), false);
		Gdx.files.local(RUINS_TILESETS[0]).write(Gdx.files.internal("data\\tileSet_SAND_WORLD_RUINS1.png").read(), false);
		Gdx.files.local(RUINS_TILESETS[1]).write(Gdx.files.internal("data\\tileSet_SAND_WORLD_RUINS2.png").read(), false);
		Gdx.files.local("tileSet_SAND_WORLD3.png").write(Gdx.files.internal("data\\tileSet_SAND_WORLD3.png").read(), false);
		Gdx.files.local("tileSet_SAND_WORLD4.png").write(Gdx.files.internal("data\\tileSet_SAND_WORLD4.png").read(), false);
		Gdx.files.local("tileSet_SAND_WORLD5.png").write(Gdx.files.internal("data\\tileSet_SAND_WORLD5.png").read(), false);
	}

	public static L1 createLevel(Player player) {
		String map;
		String tileSet;
		boolean isLevelElite = false;
		boolean hasLevelAtmosphere = false;
		boolean isRuins = false;
		if (lastMap == null && lastTileSet == null){
			map = maps.get(random.nextInt(maps.size() - 1));
			tileSet = tileSets.get(random.nextInt(tileSets.size()));
			int rand = random.nextInt(200);
			if(rand < 30){
				isLevelElite = true;
				wasLastElite = isLevelElite;
			}else if(rand > 30 && rand < 60 && player.points >= 0){
				hasLevelAtmosphere = true;
				hadLastAtmosphere = hasLevelAtmosphere;
			}else if(rand > 60 && rand < 90 && player.points >= 0){
				isRuins = true;
				wasLastRuins = isRuins;
			}
		} else {
			map = lastMap;
			tileSet = lastTileSet;
			isLevelElite = wasLastElite;
			hasLevelAtmosphere = hadLastAtmosphere;
		}
		
		lastMap = map;
		String br = Gdx.files.internal("data\\" + map).readString();
		if (!hasLevelAtmosphere && !isRuins) {
			br = br.replaceAll(DEFAULT_TILESET, tileSet + ".png");
			lastTileSet = tileSet;
		} else if (hasLevelAtmosphere){
			int tileSetNum = random.nextInt(LAB_TILESETS.length);
			br = br.replaceAll(DEFAULT_TILESET, LAB_TILESETS[tileSetNum]);
			lastTileSet = LAB_TILESETS[tileSetNum];
		} else if (isRuins){
			int tileSetNum = random.nextInt(RUINS_TILESETS.length);
			br = br.replaceAll(DEFAULT_TILESET, RUINS_TILESETS[tileSetNum]);
			lastTileSet = RUINS_TILESETS[tileSetNum];
		}
		Gdx.files.local("MapTemp.tmx").writeString(br, false);
		
		L1.player = player;
		Player.shootingSwitch = true;
		L1.hasAtmosphere = hasLevelAtmosphere;
		L1 level = new L1("tileSet_SAND_WORLD", "MapTemp.tmx", false);
		//:TODO isLevelElite !
		player.oxygen = Player.maxOxygen;
		player.health = Player.maxHealth;
		player.setPositiveEffect(PositiveEffects.NONE);
		player.setNegativeEffect(NegativeEffects.NONE);
		player.movementSpeed = 1.4f;
		player.characterStatsBoard();
		TheController.collisionLayer = (TiledMapTileLayer) level.bunker.getMap().getLayers().get(0);
		Vector2 v2 = new Vector2();
		while (!isValidPosition(v2)) {
			v2 = calculateRandomPlayerPos();
		}
		player.setPosition(v2);
		TheController.touchPos = new Vector3(v2.x+10, v2.y+5, 0);
//		System.err.println("toughtPos: " + TheController.touchPos);
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
		if(isRuins){
			for (int i = 0; i < 3; i++) {
				L1.pTurrets.add(propsSpawnGenerator.spawnTurret(player));
			}
		}
		if(TheController.skill != null && !TheController.paused){
			TheController.coolDownCounter = 0;
			TheController.coolDownAngle = 0;
			TheController.coolDownStep = 0;
		}
		player.levelsScore = 0;
		return level;
	}
	
	public static L1 createTutorialLevel(){
		String br = Gdx.files.internal("data\\" + "Map_TUTORIAL.tmx").readString();
		Gdx.files.local("MapTemp.tmx").writeString(br, false);
		L1 tutorialLevel = new TutorialLevel("tileSet_SAND_WORLD", "MapTemp.tmx");
//		System.out.println("newPlayer3");
		TutorialLevel.player = new Player(new Vector2(318,375));
		TutorialLevel.player.oxygen = Player.maxOxygen;
		TutorialLevel.player.health = Player.maxHealth;
		TutorialLevel.player.setPositiveEffect(PositiveEffects.NONE);
		TutorialLevel.player.setNegativeEffect(NegativeEffects.NONE);
		TutorialLevel.player.movementSpeed = 1.4f;
		TutorialLevel.player.characterStatsBoard();
		
		TheController.touchPos = new Vector3(TutorialLevel.player.position.x+10, TutorialLevel.player.position.y+5, 0);
		
		TheController.collisionLayer = (TiledMapTileLayer) tutorialLevel.bunker.getMap().getLayers().get(0);
		
		Wave wave = new Wave();
		wave.enemies = new Stack<Enemy>();
		wave.enemiesOnBattleField = 3;
		wave.enemies.push(new EnemyMaggot(new Vector2(1520,944)));
		wave.enemies.push(new EnemyZombie(new Vector2(1560, 944)));
//		wave.enemies.push(new EnemyLeech(new Vector2(1610, 944)));
		tutorialLevel.wave = wave;
		L1.enemiesOnStage = wave.enemies;
		tutorialLevel.wave.enemies = new Stack<Enemy>();
		tutorialLevel.wavesAmount = 1;
		
		
		return tutorialLevel;
		
	}

	private static boolean isValidPosition(Vector2 v2) {
		if (CollisionHelper.isCollidable(v2.x, v2.y,
				TheController.collisionLayer) == null) {
			return true;
		}
		return false;
	}

	public static Vector2 calculateRandomPlayerPos() {
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
