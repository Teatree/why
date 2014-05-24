package com.me.swampmonster.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.MisterSpawner;
import com.me.swampmonster.utils.WaveGenerator;

public class L1 {

	public Player player;
	public List<Item> items;
	public Stack<Enemy> enemiesOnStage;
	public Wave wave;
	public int wavesAmount;
	public int currentWave;
	public Bunker bunker;
	public boolean korea;

	private Wave waveTemp;
	private boolean needTogenerateNewWave = false;
	private int enemySpawnRateCounter;
	private int enemyEnemyPendingCoounter;
	private int pendingPeriodBetweedWavesCounter;
	private WaveGenerator waveGenerator = new WaveGenerator();
	private MisterSpawner misterSpawner = new MisterSpawner();

	public L1() {
		create();
	}

	public void create() {
		player = new Player(new Vector2());
		wavesAmount = waveGenerator.getWavesAmount(player.points);
		currentWave = 1;
		wave = waveGenerator.generateWave(player.points);
		enemiesOnStage = new Stack<Enemy>();
		bunker = new Bunker();
		items = new LinkedList<Item>();
	}

	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point,
			TiledMapTileLayer collisionLayer, List<Projectile> projectiles,
			CameraHelper cameraHelper, float dx, float dy) {
		this.player.update(aiming, touchPos, V3point, collisionLayer, dx, dy);
		misterSpawner.setCollisionLayer(collisionLayer);
		
		if (wave.enemies.empty()) {
			needTogenerateNewWave = true;
		}
		
		if (waveTemp == null && needTogenerateNewWave) {
			needTogenerateNewWave = false;
			waveTemp = waveGenerator.generateWave(player.points);
			System.err.println("enemiesOnBattlefield WAVE TEMP : "
					+ waveTemp.enemiesOnBattleField);
		}

		if (enemiesOnStage.empty() && waveTemp != null
				&& currentWave < wavesAmount) {
			if (pendingPeriodBetweedWavesCounter<=0){
			wave = waveTemp;
			waveTemp = null;
			needTogenerateNewWave = true;
			currentWave++;
			pendingPeriodBetweedWavesCounter = Constants.pendingPeriodBetweedWaves;
			// System.out.println("NEXT WAVE!");
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
		
//		if(itemSpawnRate<=0){
//		}else{
//			itemSpawnRate--;
//		}
		
		Iterator<Item> itm = items.iterator();
		while(itm.hasNext()){
			Item item = itm.next();
			if(item.state == State.DEAD){
				System.out.println("Remove item ");
				itm.remove();
			}
			if(Intersector.overlaps(item.circle, player.rectanlge)){
				if(item.itemType=="hp" && player.health < player.getMaxHealth()){
					player.health++;
					itm.remove();
				}else if(item.itemType == "O2" && player.oxygen < player.maxOxygen){
					player.oxygen = player.oxygen+50;
					itm.remove();
				}
			}
			item.update();
		}
		
		bunker.update();
	}
}
