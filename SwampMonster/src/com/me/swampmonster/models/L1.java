package com.me.swampmonster.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.me.swampmonster.game.GShape;
import com.me.swampmonster.game.L1Renderer;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.enemies.EnemySofa;
import com.me.swampmonster.models.enemies.PossessedTurret;
import com.me.swampmonster.models.items.HealthKit;
import com.me.swampmonster.models.items.Oxygen;
import com.me.swampmonster.models.items.WeaponItem;
import com.me.swampmonster.models.slots.PoisonArrow;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.MisterItemSpawner;
import com.me.swampmonster.utils.MisterSpawner;
import com.me.swampmonster.utils.PropsSpawnGenerator;
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
	public Vector2 pos;
	public static PlasmaShield plasmaShield;
	private List<Integer> yPositionsForButton; 
	private int i;
	public Random random;

	public Wave waveTemp;
	private boolean needTogenerateNewWave = false;
	private int enemySpawnRateCounter;
	private int enemyEnemyPendingCoounter;
	public int pendingPeriodBetweedWavesCounter;
	private WaveGenerator waveGenerator = new WaveGenerator();
	public static MisterSpawner misterSpawner = new MisterSpawner();
	private MisterItemSpawner misterItemSpawner = new MisterItemSpawner();
	
	public static List<Explosion> explosions;
	public static List<Prop> props =  new ArrayList<Prop>(); 

	public static ProjectileHydra hydra;
	
	public boolean isElite;
	public static boolean hasAtmosphere;
	public static List<PossessedTurret> pTurrets =  new ArrayList<PossessedTurret>(); 
	
	public L1(String tileSet, String tileMap, boolean isElite) {
		create(tileSet, tileMap, isElite);
		pTurrets = new ArrayList<PossessedTurret>();
	}

	public void create(String tileSet, String tileMap, boolean isElite) {
		this.isElite = isElite;
		wavesAmount = waveGenerator.getWavesAmount(Player.absoluteScore, hasAtmosphere, isElite);
		currentWave = 1;
		yPositionsForButton = new ArrayList<Integer>();
		yPositionsForButton.add(20);
		yPositionsForButton.add(80);
		yPositionsForButton.add(140);
		yPositionsForButton.add(200);
		wave = waveGenerator.generateWave(Player.absoluteScore, hasAtmosphere, isElite);
		enemiesOnStage = new Stack<Enemy>();
		bunker = new Bunker(tileSet, tileMap);
		items = new LinkedList<Item>();
		random = new Random();
		explosions = new ArrayList<Explosion>();
	}

	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point,
			TiledMapTileLayer collisionLayer,
			CameraHelper cameraHelper, float dx, float dy) {
		
		player.update(aiming, touchPos, V3point, collisionLayer, dx, dy);
		if(plasmaShield != null){
			plasmaShield.update();
			if(plasmaShield.state == State.DEAD){
				plasmaShield = null;
			}
		}
			
		Iterator<PossessedTurret> pTurretItr = pTurrets.iterator();
		while (pTurretItr.hasNext()){
			PossessedTurret possessedTurret = pTurretItr.next();
			if (possessedTurret != null){
				possessedTurret.update();
				if(Intersector.overlaps(possessedTurret.killingAura, player.sprite.getBoundingRectangle())){
					possessedTurret.victim = player;
				}
			}
			if(possessedTurret.state == State.DEAD){
				pTurretItr.remove();
			}
		}
		
		Iterator<Prop> propItr = props.iterator();
		while (propItr.hasNext()){
			Prop p = propItr.next();
			if (p.state != State.DEAD){
				if (player.radioactiveAura != null
						&& Intersector.overlaps(player.radioactiveAura,
								p.sprite.getBoundingRectangle()) && p instanceof ExplosiveProp) {
					p.state = State.ONFIRE;
					p.onFireCounter = 20;					
				}
				
				p.update();
				
				for (Explosion expl : explosions) {
					if (Intersector.overlaps(expl.explCircle, p.sprite.getBoundingRectangle()) && !(p instanceof ToxicPuddle) && p.state != State.DESPAWNING) {
						boolean fudge = expl.cause(p, collisionLayer);
						if (fudge)
						{
							p.state = State.DEAD;
						}
						
						if (p instanceof ExplosiveProp
								&& !expl.type
										.equals(Explosion.EXPLOSION_TYPE_INVERTED)
								&& !expl.type
										.equals(Explosion.EXPLOSION_TYPE_STUN)
								&& !expl.equals(((ExplosiveProp) p).explosion)) {
							p.state = State.ONFIRE;
							p.onFireCounter = 80;
						}
					}
				}
			} else {
				propItr.remove();
			}
		}
		
//		ListIterator<ProjectileHydra> pH = ProjectileHydra.listHydras.listIterator();
//		while(pH.hasNext()){
//			ProjectileHydra p = pH.next();
//			
//			p.update();
//		}
		if(hydra != null){
			hydra.update();
			if(hydra.allDead()){
				ProjectileHydra.musltiplyCounter = 3;
				hydra = null;
			}
		}
		
		Iterator<Explosion> explItr = explosions.iterator();
		while (explItr.hasNext()){
			Explosion expl = explItr.next();
			if (expl.type != Explosion.EXPLOSION_TYPE_INVERTED && Intersector.overlaps(expl.explCircle, player.rectanlge)) {
				expl.cause(player, collisionLayer);
			}
			else if (expl.type == Explosion.EXPLOSION_TYPE_INVERTED && expl.explCircle.contains(new Vector2(player.V3playerPos.x, player.V3playerPos.y))){
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
			if (hydra == null){
				enemy.isAimedByHydra = false;
			}
			enemy.update(collisionLayer, L1.player,
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
			waveTemp = waveGenerator.generateWave(Player.absoluteScore, hasAtmosphere, isElite);
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
			TheController.paused = true;
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
			final Item item = itm.next();
			if(item.state == State.DEAD){
				itm.remove();
				for(Actor a: L1Renderer.stage.getActors()){
					if(a.equals(item.pickUpButton)){
						a.remove();
						i--;
					}
				}for(Actor a: L1Renderer.stage.getActors()){
					if(a.equals(item.throwButton)){
						a.remove();
					}
				}for(Actor a: L1Renderer.stage.getActors()){
					if(a.equals(item.itemName)){
						a.remove();
					}
			}
			}else{
				if (Intersector.overlaps(item.circle, player.rectanlge)
						&& item.state != State.SPAWNING
						&& item.pickUpButton == null 
						&& item.throwButton == null && !(item instanceof Oxygen)
								&& !(item instanceof HealthKit)) {
					item.itemName = new Label(item.name, GShape.skin, "title");
					item.itemName.setX(260);
					item.itemName.setY(yPositionsForButton.get(i));
					item.pickUpButton = new ImageButton(GShape.skin, "use");
					item.pickUpButton.setSize(80, 80);
					item.pickUpButton.setX(380);
					item.pickUpButton.setY(yPositionsForButton.get(i));
					if(!(item instanceof WeaponItem)){
						item.throwButton = new ImageButton(GShape.skin, "throw");
						item.throwButton.setSize(80, 80);
						item.throwButton.setX(470);
						item.throwButton.setY(yPositionsForButton.get(i));
						item.throwButton.addListener(new ChangeListener() {
						@Override
							public void changed(ChangeEvent event, Actor actor) {
//								System.out.println("item: " + item);
								item.parametersForThrowing(player);
								for(Actor a: L1Renderer.stage.getActors()){
									if(a.equals(item.pickUpButton)){
										a.remove();
										item.pickUpButton = null;
										i--;
									}
								}
								for(Actor a: L1Renderer.stage.getActors()){
									if(a.equals(item.throwButton)){
										a.remove();
										item.throwButton = null;
									}
								}
								for(Actor a: L1Renderer.stage.getActors()){
									if(a.equals(item.itemName)){
										a.remove();
										item.itemName = null;
									}
								}
	//							Gdx.input.setInputProcessor(null);
							}
						});
					}
					i++;
					item.pickUpButton.addListener(new ChangeListener() {
						@Override
						public void changed(ChangeEvent event, Actor actor) {
							item.pickMeUp(player);
							for(Actor a: L1Renderer.stage.getActors()){
								if(a.equals(item.pickUpButton)){
									a.remove();
									item.pickUpButton = null;
									i--;
								}
							}
							for(Actor a: L1Renderer.stage.getActors()){
								if(a.equals(item.throwButton)){
									a.remove();
									item.throwButton = null;
								}
							}
							for(Actor a: L1Renderer.stage.getActors()){
								if(a.equals(item.itemName)){
									a.remove();
									item.itemName = null;
								}
							}
//							Gdx.input.setInputProcessor(null);
						}
					});
					
					L1Renderer.stage.addActor(item.pickUpButton);
					if(!(item instanceof WeaponItem)){
						L1Renderer.stage.addActor(item.throwButton);
					}
					L1Renderer.stage.addActor(item.itemName);
				}
				else if(!Intersector.overlaps(item.circle, player.rectanlge)){
					for(Actor a: L1Renderer.stage.getActors()){
						if(a.equals(item.pickUpButton)){
							a.remove();
							i--;
						}
					}
					for(Actor a: L1Renderer.stage.getActors()){
						if(a.equals(item.throwButton)){
							a.remove();
						}
					}
					for(Actor a: L1Renderer.stage.getActors()){
						if(a.equals(item.itemName)){
							a.remove();
						}
					}
					item.throwButton = null;
					item.itemName = null;
					item.pickUpButton = null;
				}
				if(Intersector.overlaps(item.circle, player.rectanlge) && (item instanceof Oxygen
						|| item instanceof HealthKit) ){
					item.pickMeUp(player);
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
			Enemy e = itr.next();
			if (!e.isDead()) {
				if (player.radioactiveAura != null
						&& Intersector.overlaps(player.radioactiveAura,
								e.sprite.getBoundingRectangle())) {
				}
				if (e.state != State.DEAD && player.weapon.projectiles != null) {
					Iterator<Projectile> prj = player.weapon.projectiles.iterator();
					while (prj.hasNext()) {
						Projectile p = prj.next();
						if(p.position!=null){
							pos = new Vector2(p.position.x, p.position.y);
						}
						if (Intersector.overlaps(p.circle, e.sprite.getBoundingRectangle())
								&& !e.hurt
								|| (e.iceCube != null && Intersector.overlaps(p.circle,
										e.iceCube.getBoundingRectangle()))) {
							
							if (p.effect == EffectCarriers.EXPLOSIVE) {
								TheController.skill.explode(p.position);
							}
							if(e instanceof EnemySofa){
								e.damagePushForce = 0;
							}else{
								e.damagePushForce = p.force-e.health/5;
							}
							
							
							// might be some issues with this
							if ((p.effect.equals(EffectCarriers.NONE) 
									|| p.effect.equals(EffectCarriers.SHADOW))
									&& !e.negativeEffectsState.equals(NegativeEffects.FADE_N)
									&& p.state != State.DESPAWNING) {
								e.damageType = "player";
								e.hurt = true;
								e.enemyHurt(random
										.nextInt((int) (player.weapon.maxDD - player.weapon.minDD))
										+ player.weapon.minDD);
							}
							
							
							if (p.effect == EffectCarriers.POISONED) {
								PoisonArrow.injectPoison(e);
								e.setNegativeEffect(NegativeEffects.POISONED);
							}
							if (p.effect == EffectCarriers.FADE) {
//								System.out.println("FADE PROJECTIL!~");
								e.setNegativeEffect(NegativeEffects.FADE_N);
							}
							if (p.effect == EffectCarriers.RADIOACTIVE) {
								e.setNegativeEffect(NegativeEffects.RADIOACTIVE_N);
							}
							if (p.effect == EffectCarriers.SCARED) {
								// that's right, nothin
							}
							if (p.effect == EffectCarriers.CHAIN_ARROWS) {
								hydra  = new ProjectileHydra(new Vector2(p.position.x, p.position.y));
								ProjectileHydra.musltiplyCounter = 3;
							}
							if (p.effect == EffectCarriers.FROST_EXPLOSIVE) {
								e.setNegativeEffect(NegativeEffects.FROZEN);
							}
							if (p.effect == EffectCarriers.HASTE) {
								e.setNegativeEffect(NegativeEffects.HASTE_N);
							}
							if (p.effect == EffectCarriers.WEAKENED) {
								e.setNegativeEffect(NegativeEffects.WEAKENED);
							}
							if (p.effect == EffectCarriers.ICE_CUBE) {
								e.setNegativeEffect(NegativeEffects.ICE);
							}
							if (p.effect == EffectCarriers.NUKE) {
								Explosion explosion = new Explosion(pos, Explosion.EXPLOSION_TYPE_STANDART);
								explosion.explCircle.setPosition(pos.x, pos.y);
//								System.out.println("pos: " + pos);
								L1.explosions.add(explosion);
							}
							if (p.effect == EffectCarriers.POISON) {
								e.setNegativeEffect(NegativeEffects.POISONED);
							}
							
							if (p.effect == EffectCarriers.STUN_ENEMY) {
								e.setNegativeEffect(NegativeEffects.STUN);
							}
							
							
							//Modificators
							if (p.effect == EffectCarriers.ENEMY_BLEED) {
								e.damageType = "player";
								e.hurt = true;
								e.enemyHurt(random.nextInt((int) (player.weapon.maxDD-player.weapon.minDD))+player.weapon.minDD);
								e.setNegativeEffect(NegativeEffects.WEAKENED);
							}
							if (p.effect == EffectCarriers.EXTRADAMAGE) {
								e.damageType = "player";
								e.hurt = true;
								e.enemyHurt(2*random.nextInt((int) (player.weapon.maxDD-player.weapon.minDD))+player.weapon.minDD);
							}
							if (p.effect == EffectCarriers.HEAL_ENEMY) {
								e.heal = true;
							}
							if (p.effect == EffectCarriers.SPEEDUP_ENEMY) {
								e.damageType = "player";
								e.hurt = true;
								e.enemyHurt(random.nextInt((int) (player.weapon.maxDD-player.weapon.minDD))+player.weapon.minDD);
								e.movementSpeed*=2;
							}
							if (p.effect == EffectCarriers.VAMPIRE) {
								e.damageType = "player";
								e.hurt = true;
								float hurtEnemy = random.nextInt((int) (player.weapon.maxDD-player.weapon.minDD))+player.weapon.minDD;
								e.enemyHurt(hurtEnemy);
								if (L1.player.health + hurtEnemy >= Player.maxHealth){
									L1.player.health = Player.maxHealth;
								} else {
									L1.player.health += hurtEnemy;									
								}
							}
							
							if (p.effect == EffectCarriers.EXTRADAMAGE_BY_TYPE 
									&& ((L1.player.weapon.mod1.targetEnemy != null && this.getClass().equals(L1.player.weapon.mod1.targetEnemy.getClass())) 
									|| (L1.player.weapon.mod1.targetEnemy != null && this.getClass().equals(L1.player.weapon.mod2.targetEnemy.getClass())))) {
								e.damageType = "player";
								e.hurt = true;
								e.enemyHurt(2*random.nextInt((int) (player.weapon.maxDD-player.weapon.minDD))+player.weapon.minDD);
							}
							prj.remove();
						}
					}
				}				
				if (player.trap != null && Intersector.overlaps(player.trap.circle, e.yellowAura)){
//					System.out.println(player.trap + ": caught one!");
					player.trap.catchEnemy(e);
					player.trap.position = null;
				}
				
				for (Explosion expl : explosions) {
					if (Intersector.overlaps(expl.explCircle, e.sprite.getBoundingRectangle())) {
						
//						System.err.println("Enemy position: " + e.position);
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
					Player.enemiesKilled++;
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
