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

	public static Player player;
	public static List<Item> items;
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
	public static List<Prop> props =  new ArrayList<Prop>(); 

	public boolean isElite;
	public static boolean hasAtmosphere;
	
	public L1(Player player, String tileSet, String tileMap, boolean hasAtmosphere, boolean isElite) {
		create(player, tileSet, tileMap, hasAtmosphere, isElite);
	}

	public void create(Player player, String tileSet, String tileMap, boolean hasAtmosphere, boolean isElite) {
		L1.player = player;
		this.isElite = isElite;
		L1.hasAtmosphere = hasAtmosphere;
		wavesAmount = waveGenerator.getWavesAmount(Player.score, hasAtmosphere, isElite);
		currentWave = 1;
		wave = waveGenerator.generateWave(Player.score, hasAtmosphere, isElite);
		enemiesOnStage = new Stack<Enemy>();
		bunker = new Bunker(tileSet, tileMap);
		items = new LinkedList<Item>();
		explosions = new ArrayList<Explosion>();
	}

	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point,
			TiledMapTileLayer collisionLayer,
			CameraHelper cameraHelper, float dx, float dy) {
		
		player.update(aiming, touchPos, V3point, collisionLayer, dx, dy);
				
		Iterator<Prop> propItr = props.iterator();
		while (propItr.hasNext()){
			Prop p = propItr.next();
			if (p.state != State.DEAD){
				p.update();
				
				for (Explosion expl : explosions) {
					if (Intersector.overlaps(expl.explCircle, p.sprite.getBoundingRectangle()) && !(p instanceof ToxicPuddle) && p.state != State.DESPAWNING) {
						boolean fudge = expl.cause(p, collisionLayer);
						
						if (fudge)
						{
							p.state = State.DEAD;
//							propItr.remove();
						}
						
						if(p instanceof ExplosiveProp && !expl.type.equals(Explosion.EXPLOSION_TYPE_INVERTED) && !expl.equals(((ExplosiveProp) p).explosion)){
							p.state = State.ONFIRE;
							p.onFireCounter = 80;
						}
					}
				}
			} else {
				propItr.remove();
			}
		}
		
		Iterator<Explosion> explItr = explosions.iterator();
		while (explItr.hasNext()){
			Explosion expl = explItr.next();
			if (expl.type != expl.EXPLOSION_TYPE_INVERTED && Intersector.overlaps(expl.explCircle, player.rectanlge)) {
				expl.cause(player, collisionLayer);
			}
			else if (expl.type == expl.EXPLOSION_TYPE_INVERTED && expl.explCircle.contains(new Vector2(player.V3playerPos.x, player.V3playerPos.y))){
				expl.cause(player, collisionLayer);
				System.err.println("yes, we got it");
			}
			
			expl.update();
			if (expl.explCircle.radius == 0){
				explItr.remove();
			}
			
		}
		
		misterSpawner.setCollisionLayer(collisionLayer);
		updateWave();

		for (Enemy enemy : enemiesOnStage) {
			if (player.state == State.DEAD) {
				enemy.state = State.STANDARD;
			} 
			enemy.update(collisionLayer, this.player,
					cameraHelper, enemiesOnStage);
		}
		updateEnemies(collisionLayer);
		updateItems(collisionLayer);
		bunker.update();
	}

	private void updateWave() {
		if (wave.enemies.empty()) {
			needTogenerateNewWave = true;
		}
		
		if (waveTemp == null && needTogenerateNewWave) {
			needTogenerateNewWave = false;
			waveTemp = waveGenerator.generateWave(Player.score, hasAtmosphere, isElite);
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
			TheController.showFeedback = true;
//			TheController.germany = true;
//			System.out.println("germany = " + TheController.germany);
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
//			while (enemy.position.x < 1f || enemy.position.y < 1f) {
//				System.out.print("");
//				System.err.println("L1 1");
//			}
//			try {
			enemiesOnStage.push(enemy);
//			} catch (Exception e) {
//				
//			}
			enemySpawnRateCounter = (int) wave.rate;
		}else {
			enemySpawnRateCounter--;
		}
	}

	private void updateItems(TiledMapTileLayer collisionLayer) {
		Iterator<Item> itm = items.iterator();
		while(itm.hasNext()){
			Item item = itm.next();
			if(item.state == State.DEAD){
				itm.remove();
			}else{
					if(Intersector.overlaps(item.circle, player.rectanlge)){
					if(item.itemType=="hp" && player.health < Player.playerMaxHealth){
						player.health++;
						if (player.negativeEffectsState == NegativeEffects.POISONED){
							player.setNegativeEffect(NegativeEffects.NONE);
						}
						item.state = State.DEAD;
					}else if(item.itemType == "O2" && player.oxygen < Player.maxOxygen){
						if(player.oxygen+50 < Player.maxOxygen){
							player.oxygen = player.oxygen+50;
						}else{
							player.oxygen = Player.maxOxygen;
						}
						item.state = State.DEAD;
					}
				}
				for (Explosion expl : explosions) {
					if (item.sprite != null && Intersector.overlaps(expl.explCircle, item.sprite.getBoundingRectangle())) {
						
						boolean fudge = expl.cause(item, collisionLayer);
						
						if (fudge)
						{
							item.state = State.DEAD;
						}
					}
				}
				item.update();
			}
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
						
						System.err.println("Enemy position: " + e.position);
						boolean fudge = expl.cause(e, collisionLayer);
						
						if (fudge)
						{
							e.state = State.DEAD;
						}
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
					Item i = misterItemSpawner.spawnItem(player, e);
					if (i != null) {
						items.add(i);
					}
				}
			}
		}
	}
	
	
}
