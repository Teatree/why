package com.me.swampmonster.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.ItemGenerator;
import com.me.swampmonster.utils.MisterItemSpawner;
import com.me.swampmonster.utils.MisterSpawner;
import com.me.swampmonster.utils.WaveGenerator;

public class L1 {

	Player player;
	public List<Item> items;
	public ItemGenerator itemGenerator;
	public Stack<Enemy> enemiesOnStage;
	public Wave wave;
	public int wavesAmount;
	public int currentWave;
	Bunker bunker;
	public boolean korea;

	private Wave waveTemp;
	private boolean needTogenerateNewWave = false;
	WaveGenerator waveGenerator = new WaveGenerator();
	MisterSpawner misterSpawner = new MisterSpawner();
	MisterItemSpawner misterItemSpawner = new MisterItemSpawner();
	private int itemSpawnRate;
	private int enemySpawnRateCounter;
	private int enemyEnemyPendingCoounter;
	private int pendingPeriodBetweedWavesCounter;

	public L1() {
		create();
	}

	public void create() {
		player = new Player(new Vector2());
		wavesAmount = waveGenerator.getWavesAmount(player.getPoints());
		currentWave = 1;
		System.out.println("create wave ");
		wave = waveGenerator.generateWave(player.getPoints());
		enemiesOnStage = new Stack<Enemy>();
		bunker = new Bunker();
		items = new LinkedList<Item>();
		itemGenerator = new ItemGenerator();
		itemSpawnRate = itemGenerator.generateSpawnRate(player.getPoints());
	}

	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point,
			TiledMapTileLayer collisionLayer, List<Projectile> projectiles,
			CameraHelper cameraHelper, float dx, float dy) {
		this.player.update(aiming, touchPos, V3point, collisionLayer, dx, dy);
		misterSpawner.setCollisionLayer(collisionLayer);
		misterItemSpawner.setCollisionLayer(collisionLayer);
		
		if (wave.enemies.empty()) {
			needTogenerateNewWave = true;
		}
		
		if (waveTemp == null && needTogenerateNewWave) {
			System.out.println(" generating temp");
			needTogenerateNewWave = false;
			waveTemp = waveGenerator.generateWave(player.getPoints());
			System.err.println("enemiesOnBattlefield WAVE TEMP : "
					+ waveTemp.enemiesOnBattleField);
		}

		if (enemiesOnStage.empty() && waveTemp != null
				&& currentWave < wavesAmount) {
			if (pendingPeriodBetweedWavesCounter<=0){
			System.out.println("swapping waves");
			wave = waveTemp;
			waveTemp = null;
			needTogenerateNewWave = true;
			currentWave++;
			pendingPeriodBetweedWavesCounter = Constants.pendingPeriodBetweedWaves;
			System.out.println("NEXT WAVE!");
			}else{
				pendingPeriodBetweedWavesCounter--;
			}
		}
		
		if (!korea && enemiesOnStage.size() == wave.enemiesOnBattleField) {
			enemyEnemyPendingCoounter = wave.pendingPeriod;
			korea = true;
		}
		if (korea) {
			enemyEnemyPendingCoounter--;
		}
		if (enemyEnemyPendingCoounter <= 0) {
			korea = false;
		}
		
		if (!korea && wave != null && enemiesOnStage.size() <= wave.enemiesOnBattleField
				&& !wave.enemies.empty() && enemySpawnRateCounter <= 0) {
			Enemy enemy = wave.enemies.pop();
			misterSpawner.spawnEnemy(this, enemy);
//			System.err.println("(L1)enemy posX: " + enemy.getPosition().x
//					+ " posY: " + enemy.getPosition().y);
			while (enemy.position.x < 1f || enemy.position.y < 1f) {
				System.out.print("");
			}
			try {
				enemiesOnStage.push(enemy);
			} catch (Exception e) {
				
			}
			enemySpawnRateCounter = (int) wave.rate;
		}else {
			enemySpawnRateCounter--;
		}

		for (Enemy enemy : enemiesOnStage) {
			enemy.update(collisionLayer, projectiles, this.player,
					cameraHelper, enemiesOnStage);
		}
		
		if(itemSpawnRate<=0){
			items.add(misterItemSpawner.spawnItem(player));
			itemSpawnRate = itemGenerator.generateSpawnRate(player.getPoints());
		}else{
			itemSpawnRate--;
		}
		
		for (Item item : items) {
			item.update();
		}
		bunker.update();
	}

	// public void drawPlayer(SpriteBatch b){
	// b.begin();
	// b.draw(getPlayer().getSprite(), getPlayer().getPosition().x,
	// getPlayer().getPosition().y, getPlayer().getSprite().getWidth(),
	// getPlayer().getSprite().getHeight());
	// b.end();
	// }

	// public void drawEnemy(SpriteBatch b){
	// for (Enemy enemy : enemies){
	// b.begin();
	// b.draw(enemy.getSprite(), enemy.getPosition().x, enemy.getPosition().y,
	// enemy.getSprite().getWidth(), enemy.getSprite().getHeight());
	// b.end();
	// }
	// }

	public Bunker getBunker() {
		return bunker;
	}

	public void setBunker(Bunker bunker) {
		this.bunker = bunker;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
