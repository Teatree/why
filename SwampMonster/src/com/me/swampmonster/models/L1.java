package com.me.swampmonster.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.MisterItemSpawner;
import com.me.swampmonster.utils.MisterSpawner;
import com.me.swampmonster.utils.WaveGenerator;

public class L1 {

	public Player player;
	public List<Item> items;
	public static Stack<Enemy> enemiesOnStage;
	public Wave wave;
	public int wavesAmount;
	public int currentWave;
	public Bunker bunker;
	public boolean korea;

	public Wave waveTemp;
	private boolean needTogenerateNewWave = false;
	private int enemySpawnRateCounter;
	private int enemyEnemyPendingCoounter;
	public int pendingPeriodBetweedWavesCounter;
	private WaveGenerator waveGenerator = new WaveGenerator();
	private MisterSpawner misterSpawner = new MisterSpawner();
	private MisterItemSpawner misterItemSpawner = new MisterItemSpawner();
	
	public static List<Explosion> explosions;

	public L1(Player player) {
		create(player);
	}

	public void create(Player player) {
		this.player = player;
		this.player.position = new Vector2();
		wavesAmount = waveGenerator.getWavesAmount(Player.score);
		currentWave = 1;
		wave = waveGenerator.generateWave(Player.score);
		enemiesOnStage = new Stack<Enemy>();
		bunker = new Bunker();
		items = new LinkedList<Item>();
		explosions = new ArrayList<Explosion>();
	}

	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point,
			TiledMapTileLayer collisionLayer,
			CameraHelper cameraHelper, float dx, float dy) {
		for (Explosion expl : explosions) {
			if (Intersector.overlaps(expl.explCircle, player.rectanlge)) {
				expl.cause(player, collisionLayer);
			}
		}
		
		for (Explosion e : explosions){
			e.update();
		}
		player.update(aiming, touchPos, V3point, collisionLayer, dx, dy);
		
		misterSpawner.setCollisionLayer(collisionLayer);
		misterItemSpawner.setCollisionLayer(collisionLayer);
		updateWave();

		for (Enemy enemy : enemiesOnStage) {
			if (player.state == State.DEAD) {
				enemy.state = State.STANDARD;
			} 
			enemy.update(collisionLayer, this.player,
					cameraHelper, enemiesOnStage);
		}
		updateEnemies(collisionLayer);
		updateItems();
		bunker.update();
	}

	private void updateWave() {
		if (wave.enemies.empty()) {
			needTogenerateNewWave = true;
		}
		
		if (waveTemp == null && needTogenerateNewWave) {
			needTogenerateNewWave = false;
			waveTemp = waveGenerator.generateWave(Player.score);
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
		}else if(enemiesOnStage.empty() && currentWave == wavesAmount){
			TheController.germany = true;
			System.out.println("germany = " + TheController.germany);
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
				if(item.itemType=="hp" && player.health < Player.playerMaxHealth){
					player.health++;
					if (player.negativeEffectsState == NegativeEffects.POISONED){
						player.setNegativeEffect(NegativeEffects.NONE);
					}
					itm.remove();
				}else if(item.itemType == "O2" && player.oxygen < Player.maxOxygen){
					if(player.oxygen+50 < Player.maxOxygen){
						player.oxygen = player.oxygen+50;
					}else{
						player.oxygen = Player.maxOxygen;
					}
					itm.remove();
				}
			}
			item.update();
		}
	}
	
	private void updateEnemies(TiledMapTileLayer collisionLayer) {
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
					if (p.effect != EffectCarriers.SHADOW && Intersector.overlaps(p.circle, e.rectanlge)) {
						if (p.effect == EffectCarriers.EXPLOSIVE){
							TheController.skill.explode(p.position);
						}
						prj.remove();
						break;
					}
				}
				
				if (player.trap != null && Intersector.overlaps(player.trap.circle, e.yellowAura)){
					player.trap.catchEnemy(e);
					player.trap.position = null;
				}
				
				
//				List<Enemy> eNeMes = new ArrayList<Enemy>();
//				for(Enemy x : enemiesOnStage){
//					eNeMes.add(x);
//				}
				
				for (Explosion expl : explosions) {
					if (Intersector.overlaps(expl.explCircle, e.rectanlge)) {
						expl.cause(e, collisionLayer);
					}
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
