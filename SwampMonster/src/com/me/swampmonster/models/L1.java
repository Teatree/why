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
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.MisterItemSpawner;
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
	private MisterItemSpawner misterItemSpawner = new MisterItemSpawner();

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
		misterItemSpawner.setCollisionLayer(collisionLayer);
		updateWave();
		
		for (Enemy enemy : enemiesOnStage) {
			if (player.state == State.DEAD) {
				enemy.state = State.STANDARD;
			} 
			enemy.update(collisionLayer, projectiles, this.player,
						cameraHelper, enemiesOnStage);
		}

		updateEnemies();
		updateItems();
		bunker.update();
	}

	private void updateWave() {
		if (wave.enemies.empty()) {
			needTogenerateNewWave = true;
		}
		
		if (waveTemp == null && needTogenerateNewWave) {
			needTogenerateNewWave = false;
			waveTemp = waveGenerator.generateWave(player.points);
		}

		if (enemiesOnStage.empty() && waveTemp != null
				&& currentWave < wavesAmount) {
			if (pendingPeriodBetweedWavesCounter<=0){
			wave = waveTemp;
			waveTemp = null;
			needTogenerateNewWave = true;
			currentWave++;
			pendingPeriodBetweedWavesCounter = Constants.pendingPeriodBetweedWaves;
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
	}

	private void updateItems() {
		Iterator<Item> itm = items.iterator();
		while(itm.hasNext()){
			Item item = itm.next();
			if(item.state == State.DEAD){
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
	}
	
	private void updateEnemies() {
		Iterator<Enemy> itr = enemiesOnStage.iterator();
		while (itr.hasNext()) {
			Enemy e = (Enemy) itr.next();
			if (!e.isDead()) {
				if (player.radioactiveAura != null
						&& Intersector.overlaps(player.radioactiveAura,
								e.rectanlge)) {

				}
				Iterator<Projectile> prj = player.projectiles.iterator();
				while (prj.hasNext()) {
					Projectile p = (Projectile) prj.next();
					if (Intersector.overlaps(p.circle, e.rectanlge)) {
						prj.remove();
						break;
					}
				}
				
				if (player.trap != null && Intersector.overlaps(player.trap.circle, e.yellowAura)){
					player.trap.catchEnemy(e);
				}
			}

			if (e.isDead()) {
				if (e.timeRemove < 180) {
					e.timeRemove++;
				} else if (e.timeRemove > 179) {
					itr.remove();
					e.timeRemove = 0;
				}
				if (e.timeRemove == 1) {
					Item i = misterItemSpawner .spawnItem(player, e);
					if (i != null) {
						items.add(i);
					}
				}
			}
		}
	}
	
	
}
