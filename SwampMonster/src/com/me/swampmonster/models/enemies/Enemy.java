package com.me.swampmonster.models.enemies;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.AI.Node;
import com.me.swampmonster.AI.Pathfinder;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.ProjectileHydra;
import com.me.swampmonster.models.Prop;
import com.me.swampmonster.models.ToxicPuddle;
import com.me.swampmonster.models.Turret;
import com.me.swampmonster.models.items.ICE_THING;
import com.me.swampmonster.models.items.RADIOACTIVE;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.EnemyGenerator.Toughness;

public class Enemy extends AbstractGameObject implements Cloneable, Collidable {

	public int cunter;
	public boolean aiming;
	public boolean preparingToCharge;
	public boolean charging;
	public boolean waiting;
	public boolean injuredByHydra;
	public boolean isAimedByHydra;
	public float poisonDamage;
	public int poisonDamageInterval;
	public Circle radioactiveAura;
	int timer;
	public int time;
	int timeDead = 0;
	int timer2;
	int timereskin = 0;
	public int timeRemove = 0;
	public String projectileLocation;
	public List<LeechProjectile> enemyProjectiles;
	public Toughness toughness;
	public Vector2 target;
	public Sprite iceCube;
	float damage_dx;
	float damage_dy;
	
	public static float floatingOutputDamage;
	
	float enemyDx;
	float enemyDy;

	public int minDamage;
	public int maxDamage;
	public int minHealth;
	public int maxHealth;
	public int minScale;
	public int maxScale;
	public int minSpeed;
	public int maxSpeed;
	public float maxColour;
	public float minColour;
	
	float enemyPathDx;
	float enemyPathDy;

	public float damagePushForce;
		
	boolean iAmWaiting = false;
	boolean currentlyMovingOnPath = false;
	boolean attackSequenceStarted = false;
	boolean turretAttackSequenceStarted = false;

	public Circle oRangeAura;
	public Circle yellowAura;
	public Circle aimingAura;
	public Rectangle aimerBot;

	public Node[] path;

	private int negativeEffectTimer;
	private int timerPoisoned;

	public Random random = new Random();
	int generateNewRandomPosForScared;

	public Sprite effectCarrier;
	public AnimationControl effectAnimator;
	
	public Enemy(Vector2 position) {
		this.position = position;
//		rectanlge = new Rectangle();
		yellowAura = new Circle();
		yellowAura.radius = 8;
		oRangeAura = new Circle();
		oRangeAura.radius = yellowAura.radius*2;
		aimingAura = new Circle();
		aimingAura.radius = 140;
		aimingAura.x = position.x;
		aimingAura.y = position.y;
		aimerBot = new Rectangle();
		aimerBot.x = position.x;
		aimerBot.y = position.y;
		aimerBot.width = 5;
		aimerBot.height = 5;
		state = State.STANDARD;
		poisonDamage = 0.5f;
		poisonDamageInterval = 60;
		animationsStandard.put(State.PURSUIT, new AnimationControl(
				Assets.manager.get(Assets.enemy), 8, 32, 8));
		animationsStandard.put(State.STANDARD, new AnimationControl(
				Assets.manager.get(Assets.enemy), 8, 32, 8));
		animationsStandard.put(State.ATTACKING, new AnimationControl(
				Assets.manager.get(Assets.enemy), 8, 32, 8));
		animationsStandard.put(State.ANIMATING, new AnimationControl(
				Assets.manager.get(Assets.enemy), 8, 32, 8));
		animationsStandard.put(State.DEAD,new AnimationControl(Assets.manager.get(Assets.enemy),
								8, 32, 4));
		animationsStandard.put(State.ICECUBESDEAD,new AnimationControl(Assets.manager.get(Assets.enemy),
				8, 32, 4));
		oldPos = position;
		// Timer is for the length of the actual animation
		// Timer2 is for the waiting period
		timer = 0;
		timer2 = 0;
		path = new Node[99];

		enemyProjectiles = new LinkedList<LeechProjectile>();

		// ***Character stats board, probably need to delete this***
		// ***Character stats board, probably need to delete this***

		sprite = new Sprite(animationsStandard.get(State.STANDARD)
				.getCurrentFrame());
		
		effectAnimator = new AnimationControl(Assets.manager.get(Assets.stunEffectAnimation), 4, 1, 12);
		effectCarrier = new Sprite(effectAnimator.getCertainFrame(0));
		
		characterStatsBoard();
	}

	public void characterStatsBoard() {
		// HEALTH, DAMAGE, TYPE, TOUGHGUY, COLORSCHEME, ETC.
//		minHealth = 1;
//		maxHealth = 3;
//		minDamage = 1;
//		maxDamage = 3;
//		minSpeed = 3;
//		maxSpeed = 7;
//		minScale = 0;
//		maxScale = 2;
		points = 0;
		attackSpeed = 40;
//		health = random.nextInt(maxHealth - minHealth) + minHealth;
//		damage = random.nextInt(maxDamage - minDamage) + minDamage;
//		movementSpeed = (random.nextInt(maxSpeed - minSpeed) + minSpeed)/10;
//		sprite.setScale((random.nextInt(maxScale - minScale) + minScale)/10);
	}

	public void update(TiledMapTileLayer collisionLayer, Player player,
			CameraHelper cameraHelper, List<Enemy> enemies) {

		iAmWaiting = souldIWait(enemies);
		oldPos.x = position.x;
		oldPos.y = position.y;
		
		if(radioactiveAura!=null){
			radioactiveAura.x = position.x+sprite.getWidth()/2;
			radioactiveAura.y = position.y+sprite.getHeight()/2;
		}

		aimingAura.x = position.x + sprite.getWidth() / 2;
		aimingAura.y = position.y + sprite.getHeight() / 2;
		oRangeAura.x = position.x + sprite.getWidth() / 2;
		oRangeAura.y = position.y + sprite.getHeight() / 2;
		if(!(this instanceof EnemySofa)){
			yellowAura.x = position.x + sprite.getWidth() / 2;
			yellowAura.y = position.y + sprite.getHeight() / 2;
		}

//		rectanlge.x = sprite.getX();
//		rectanlge.y = sprite.getY();
//		rectanlge.width = sprite.getWidth()*sprite.getScaleX();
//		rectanlge.height = sprite.getHeight()*sprite.getScaleY();
		
		damage_dx = position.x - L1.player.position.x;
		damage_dy = position.y - L1.player.position.y;
		float length1 = (float) Math.sqrt(damage_dx * damage_dx + damage_dy * damage_dy);
		damage_dx /= length1;
		damage_dy /= length1;

//		if (negativeEffectCounter <= 0) {
//			setNegativeEffect(NegativeEffects.NONE);
//		} else {
//			negativeEffectCounter--;
//		}
		// Direction for the standard state
		Vector2 target;
		float distanceToPlayer;
		float distanceToTurret;
		if(player.turret != null){
//			System.out.println("yes turret is not null!");
			distanceToPlayer = (float) Math.sqrt(Math.pow((this.position.x - player.position.x), 2) +
					(Math.pow((this.position.y - player.position.y), 2)));
			distanceToTurret = (float) Math.sqrt(Math.pow((this.position.x - player.turret.position.x), 2) +
					(Math.pow((this.position.y - player.turret.position.y), 2)));
			if(distanceToPlayer > distanceToTurret){
//				System.out.println("yes, player is further");
				target = player.turret.position;
//				System.out.println("target is turret: " + player.turret.position);
			}else{
				target = player.position;
			}
		}else{
			target = player.position;
		}
		
		if(negativeEffectsState != NegativeEffects.ICE){
			iceCube = null;
		} else {
			iceCube.setSize(sprite.getBoundingRectangle().width, sprite.getBoundingRectangle().height);
			iceCube.setX(sprite.getX());
			iceCube.setY(sprite.getY());
		}
		if (negativeEffectsState != NegativeEffects.FEAR) {
			enemyDx = target.x - position.x;
			enemyDy = target.y - position.y;
		} else {
			if (generateNewRandomPosForScared == 0) {
				int randomTargetX = random.nextInt(1000);
				int randomTargetY = random.nextInt(1000);
//				System.out.println(randomTargetX + " : " + randomTargetY);
				enemyDx = randomTargetX - position.x;
				enemyDy = randomTargetY - position.y;
				generateNewRandomPosForScared = 90;
			} else {
				generateNewRandomPosForScared--;
			}
		}
		float enemyLength = (float) Math.sqrt(enemyDx * enemyDx + enemyDy
				* enemyDy);
		enemyDx /= enemyLength;
		enemyDy /= enemyLength;

		// // System.out.println("currentlyMovingOnPath " +
		// currentlyMovingOnPath);

		// Direction for the pursuit state
		if (cunter != -1 && path != null && path[cunter] != null) {
			enemyPathDx = path[cunter].x * Constants.NodeSize - position.x;
			enemyPathDy = path[cunter].y * Constants.NodeSize - position.y;

			float enemyPathLength = (float) Math.sqrt(enemyPathDx * enemyPathDx
					+ enemyPathDy * enemyPathDy);
			enemyPathDx /= enemyPathLength;
			enemyPathDy /= enemyPathLength;
		}

		if (player.radioactiveAura != null
				&& Intersector.overlaps(player.radioactiveAura, sprite.getBoundingRectangle()) && state != State.DEAD) {
			hurt = true;
			damageType = "player";
			enemyHurt(RADIOACTIVE.RADIOACTIVE_Damage);
		}

		if (player.turret != null && player.turret.projectiles != null && !player.turret.projectiles.isEmpty()){
				Iterator<Projectile> itrTP = player.turret.projectiles.iterator(); 
				while(itrTP.hasNext()){
					Projectile tp = itrTP.next();
					if (Intersector.overlaps(tp.circle, sprite.getBoundingRectangle())
							&& !hurt) {
						hurt = true;
						damageType = "turret";
						enemyTurretHurt(player.turret);
						itrTP.remove();
				}
			}
		}
		if (health <= 0) {
			state = State.DEAD;
		}
		// this little thing is not done!

		HashMap<State, AnimationControl> animations = animationsStandard;

		// // System.out.println("enemy state: " + state);

		if (!state.equals(State.STANDARD)) {
			timer = 0;
			timer2 = 0;
		}

		// Standing animation between attacks doesn't work.

		// MOVEMENT + COLLISION PROCESSING AND DETECTION

		// PURSUIT!
		if (!hurt ||(damageType != null && (damageType.equals("turret") || damageType.equals("poison")))) {
			if (state.equals(State.PURSUIT)  && negativeEffectsState != NegativeEffects.STUN 
					&& negativeEffectsState != NegativeEffects.ICE) {

				sprite.setRegion(animations.get(state).getCurrentFrame());

				// MOVEMENT + COLLISION PROCESSING AND DETECTION
				// // System.out.println("cunter = " + cunter);
				if (path != null && (cunter <= 0 || path[cunter] == null)) {
					cunter = findLastNotNullInArray();
					if (cunter <= 0) {
						state = State.STANDARD;
					}

				} else {
					onPathMovingAndCollisionDetection(collisionLayer, player,
							enemyPathDx, enemyPathDy, enemies);
					orientOnPath();
					standAnimation(88, 72, 80, 64);
				}

				if (path != null && path[0] != null) {
					if (player.position.x > path[0].x * Constants.NodeSize + 120
							|| player.position.x < path[0].x * Constants.NodeSize - 120
							|| player.position.y > path[0].y * Constants.NodeSize + 120
							|| player.position.y < path[0].y * Constants.NodeSize - 120) {
						System.out.println("You are officially outside the last seen zone!");
						state = State.STANDARD;
						for (int i = 0; i < path.length; i++) {
							path[i] = null;
						}
						currentlyMovingOnPath = false;
					}
				}
			}
		}

		// THIS IS STANDARD!
		if (state.equals(State.STANDARD) && !aiming && negativeEffectsState != NegativeEffects.ICE 
				&& negativeEffectsState != NegativeEffects.STUN) {
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
				if (timer == 0 && timer2 == 0
						&& !(yellowAura.overlaps(player.circle) || player.turret!=null && yellowAura.overlaps(player.turret.circle))
						&& player.state != State.DEAD) {

					collidableLeft = null;
					collidableRight = null;
					collidableDown = null;
					collidableUp = null;

					if (!preparingToCharge && !charging && !waiting) {
						move(target, collidableLeft, collidableRight,
								collidableDown, collidableUp, enemyDx, enemyDy,
								movementSpeed, enemies);
					}
					collidableLeft = collisionCheckerLeft(collisionLayer,
							enemies);
					collisionCheck(collidableLeft, collisionLayer, player);
					collidableRight = collisionCheckerRight(collisionLayer,
							enemies);
					collisionCheck(collidableRight, collisionLayer, player);
					collidableDown = collisionCheckerBottom(collisionLayer,
							enemies);
					collisionCheck(collidableDown, collisionLayer, player);
					collidableUp = collisionCheckerTop(collisionLayer, enemies);
					collisionCheck(collidableUp, collisionLayer, player);
				}

				if (!preparingToCharge) {
					if (oldPos.x != position.x || oldPos.y != position.y
							&& timer > 0 && timer2 > 0) {
						// // System.out.println("yes!1");
						timer = 0;
						timer2 = 0;
					}

					
					atackLogic(player, cameraHelper);
				}
		}

		if (negativeEffectsState != NegativeEffects.NONE
				&& negativeEffectTimer > 0) {
			if (negativeEffectsState == NegativeEffects.POISONED && state != State.DEAD) {
				sprite.setColor(Color.GREEN);
				if (timerPoisoned == poisonDamageInterval) {
					hurt = true;
					damageType = "poison";
					health -= poisonDamage;
					timerPoisoned = 0;
				} else {
					timerPoisoned++;
				}
			}
			negativeEffectTimer--;
		} else {
			setNegativeEffect(NegativeEffects.NONE);
		}

		if(negativeEffectsState == NegativeEffects.STUN){
			effectAnimator.doComplexAnimation(8, 1f, 0.009f, Animation.PlayMode.LOOP);
			effectCarrier = new Sprite(effectAnimator.getCurrentFrame());
		} 
		// DEAD
		if (state.equals(State.DEAD)) {
			
			if (timeDead < 65) {
				if(negativeEffectsState == NegativeEffects.STUN){
					currentFrame = animations.get(State.ICECUBESDEAD).doComplexAnimation(136, 2f,
							0.03f, Animation.PlayMode.NORMAL);
					sprite.setRegion(animations.get(State.ICECUBESDEAD).getCurrentFrame());
					System.out.println("YOU ARE STUNNEd!");
				}else{
					currentFrame = animations.get(state).doComplexAnimation(96, 2f,
							0.03f, Animation.PlayMode.NORMAL);
					sprite.setRegion(animations.get(state).getCurrentFrame());
				}

				sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
				timeDead++;
			}
			if (timeDead == 1) {
				rewardPlayer(player);
				// "pointfAura " + Player.score);
			}
			if (timeDead > 64) {
				dead = true;
			}
		}
		if (hurt && !exploding && !charging && !negativeEffectsState.equals(NegativeEffects.STUN)) {
			if (player.weapon.projectiles != null) {
				getProjectileLocationRelativeToSprite(player.weapon.projectiles);
			}
			if (time < 40) {
				time++;
				if (damageType == "player" && state != State.DEAD) {
					movementSpeed = movementSpeed/2;
//					Collidable collidableUp = null;
					// TERRIBLE! 
					//:TODO CHANGE ALL OF THIS
					
					Collidable cL = CollisionHelper.isCollidable(position.x, position.y + sprite.getHeight()/2, collisionLayer);
					Collidable cR = CollisionHelper.isCollidable(position.x + sprite.getWidth(), position.y + sprite.getHeight()/2, collisionLayer);
					Collidable cU = CollisionHelper.isCollidable(position.x + sprite.getWidth()/2, position.y + sprite.getHeight(), collisionLayer);
					Collidable cD = CollisionHelper.isCollidable(position.x + sprite.getWidth()/2, position.y, collisionLayer);
//					explosionPushForce -= 0.01f;
					if (cL == null && getDx() <= 0 ||
							cR == null && getDx() > 0){
//						position.x += 0.2f;
						position.x += damage_dx * damagePushForce/23;
					} 
					if (cD == null && getDy() < 0 
							|| cU == null && getDy() >= 0){
//						position.y += 0.2f;
						position.y += damage_dy * damagePushForce/23;
					}
									
//					collidableUp = collisionCheckerTop(collisionLayer, enemies);
//					collisionCheck(collidableUp, collisionLayer, player);

//					Collidable collidableDown = null;

//					collidableDown = collisionCheckerBottom(collisionLayer,
//							enemies);
//					collisionCheck(collidableDown, collisionLayer, player);
//
//					Collidable collidableLeft = null;

//					damageFromLeft(collidableLeft, animationsStandard);
//					collidableLeft = collisionCheckerLeft(collisionLayer,
//							enemies);
//					collisionCheck(collidableLeft, collisionLayer, player);
//
//					Collidable collidableRight = null;

//					damageFromRight(collidableRight, animationsStandard);
//					collidableRight = collisionCheckerRight(collisionLayer,
//							enemies);
//					collisionCheck(collidableRight, collisionLayer, player);
				}

			} else if (time > 39) {
				hurt = false;
				if(!negativeEffectsState.equals(NegativeEffects.FROZEN)){
					movementSpeed = STANDART_MOVEMENT_SPEED;
				}else if(negativeEffectsState.equals(NegativeEffects.FROZEN)){
					movementSpeed = STANDART_MOVEMENT_SPEED/2;
				}
				time = 0;
			}
		}
		Iterator<LeechProjectile> prj2 = enemyProjectiles.iterator();
		while (prj2.hasNext()) {
			LeechProjectile p2 = prj2.next();
				if (p2.isCollision(collisionLayer)
						|| Intersector.overlaps(p2.circle, player.aimingArea)) {
					System.out.println("colision");
					player.damage_dx = L1.player.position.x - position.x;
					player.damage_dy = L1.player.position.y - position.y;
					float length3 = (float) Math.sqrt(L1.player.damage_dx * L1.player.damage_dx + L1.player.damage_dy * L1.player.damage_dy);
					player.damage_dx /= length3;
					player.damage_dy /= length3;
					player.damagePushForce = random.nextInt((int) (((maxDamage-minDamage))+minDamage));
					p2.state = State.DEAD;
				} else if (L1.plasmaShield != null
						&& p2.circle.overlaps(L1.plasmaShield.circle)) {
					if (!L1.plasmaShield.circle.contains(oRangeAura)) {
						p2.state = State.DEAD;
					}
				}
				p2.update();
				p2.getSurfaceLevelProjectile(collisionLayer);
//				System.out.println("p2.getSurfaceLevelProjectile " + p2.getSurfaceLevelProjectile(collisionLayer));
				if (p2.state == State.DEAD) {
					prj2.remove();
				}
		}

		Iterator<Prop> propItr = L1.props.iterator();
		while (propItr.hasNext()) {
			Prop prop = propItr.next();
			if (prop.sprite.getBoundingRectangle().overlaps(
					this.sprite.getBoundingRectangle())) {
				if (prop instanceof ToxicPuddle) {
					prop.toDoSomething(this);
				} else {
					Collidable cL = CollisionHelper.isCollidable(
							prop.position.x,
							prop.position.y + prop.sprite.getHeight() / 2,
							collisionLayer);
					Collidable cR = CollisionHelper.isCollidable(
							prop.position.x + prop.sprite.getWidth(),
							prop.position.y + prop.sprite.getHeight() / 2,
							collisionLayer);
					Collidable cU = CollisionHelper.isCollidable(
							prop.position.x + prop.sprite.getWidth() / 2,
							prop.position.y + prop.sprite.getHeight(),
							collisionLayer);
					Collidable cD = CollisionHelper.isCollidable(
							prop.position.x + prop.sprite.getWidth() / 2,
							prop.position.y, collisionLayer);

					if (cL == null && getDx() <= 0 ||
							cR == null && getDx() > 0){
						prop.position.x += -getDx();
					} 
					if (cD == null && getDy() <= 0 
							|| cU == null && getDy() > 0){
						prop.position.y += -getDy();
					} 
				}
			}
		}
		
		
	}

	public void atackLogic(Player player, CameraHelper cameraHelper) {
		if (yellowAura.overlaps(player.circle) && player.state != State.DEAD) {
			attackSequenceStarted = true;
		}
		else if (player.turret != null && yellowAura.overlaps(player.turret.circle)
				&& player.turret.state != State.DEAD && player.turret.state != State.DESPAWNING) {
			turretAttackSequenceStarted = true;
		}

		if (attackSequenceStarted) {
			if (playerMovementDirection == "right") {
				inflictOnThe(88, 56, player, cameraHelper, attackSpeed);
			}
			if (playerMovementDirection == "left") {
				inflictOnThe(72, 40, player, cameraHelper, attackSpeed);
			}
			if (playerMovementDirection == "up") {
				inflictOnThe(80, 48, player, cameraHelper, attackSpeed);
			}
			if (playerMovementDirection == "down") {
				inflictOnThe(64, 32, player, cameraHelper, attackSpeed);
			}
		}
		if (turretAttackSequenceStarted) {
			if (playerMovementDirection == "right") {
				inflictToTurret(88, 56, player.turret, cameraHelper, attackSpeed);
			}
			if (playerMovementDirection == "left") {
				inflictToTurret(72, 40, player.turret, cameraHelper, attackSpeed);
			}
			if (playerMovementDirection == "up") {
				inflictToTurret(80, 48, player.turret, cameraHelper, attackSpeed);
			}
			if (playerMovementDirection == "down") {
				inflictToTurret(64, 32, player.turret, cameraHelper, attackSpeed);
			}
		}
	}

	private String getProjectileLocationRelativeToSprite(
			List<Projectile> projectiles) {
		for (Projectile projectile : projectiles) {
			if (projectile.getPosition().y > position.y + sprite.getHeight()
					/ 2
					&& projectile.getPosition().x > position.x - 10
					&& projectile.getPosition().x < position.x
							+ sprite.getWidth() + 10) {
				projectileLocation = "top";
			}
			if (projectile.getPosition().y < position.y
					&& projectile.getPosition().x > position.x - 10
					&& projectile.getPosition().x < position.x
							+ sprite.getWidth() + 10) {
				projectileLocation = "bottom";
			}
			if (projectile.getPosition().x < position.x
					&& projectile.getPosition().y > position.y
					&& projectile.getPosition().y < position.y
							+ sprite.getHeight()) {
				projectileLocation = "right";
			}
			if (projectile.getPosition().x > position.x + sprite.getWidth() / 2
					&& projectile.getPosition().y > position.y
					&& projectile.getPosition().y < position.y
							+ sprite.getHeight()) {
				projectileLocation = "left";
			}
		}
		// Stuff and Stuff, lov you git!
		return projectileLocation;
	}

	private void rewardPlayer(AbstractGameObject player) {
		Player.absoluteScore += this.points;
		Player.levelsScore += this.points;

	}

	protected float getRotation(Vector2 target) {
		double angle1 = Math.atan2(position.y
				- (position.y + oRangeAura.radius / 2), position.x - 0);
		double angle2 = Math.atan2(position.y - target.y, position.x
				- target.x);
		return (float) (angle2 - angle1);
	}

	protected void inflictOnThe(int standing, int animation, Player player,
			CameraHelper cameraHelper, int attackSpeed) {
		// Timer is for the length of the actual animation
		// Timer2 is for the waiting period
		if (oldPos.x == position.x && oldPos.y == position.y) {
			if (timer2 < attackSpeed) {
				timer2++;
				// System.out.println("timer2: " + timer2 );
				currentFrame = animationsStandard.get(state).animate(standing);
			}
			if (timer2 >= attackSpeed && timer < 30) {
				if (oRangeAura.overlaps(player.circle)) {
					cameraHelper.setShakeAmt(25);
					cameraHelper.cameraShake();
				}
				// System.out.println("timer1: " + timer);
				currentFrame = animationsStandard.get(state)
						.doComplexAnimation(animation, 1.8f,
								Gdx.graphics.getDeltaTime(), Animation.PlayMode.NORMAL);

				sprite.setRegion(animationsStandard.get(state)
						.getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
				timer++;
				if (timer == 30 && timer2 == attackSpeed) {
					currentFrame = animationsStandard.get(state).animate(
							standing);
					// And may be inflict different hurts, direction/ kinds of
					// hurts/ etc.
					if (oRangeAura.overlaps(player.circle)
							&& !player.hurt
							&& player.positiveEffectsState != PositiveEffects.FADE) {
						player.damageType = "enemy";
						player.harmfulEnemy = this;
						player.hurt = true;
						player.damage_dx = player.position.x - position.x;
						player.damage_dy = player.position.y - position.y;
						float length1 = (float) Math.sqrt(damage_dx * damage_dx/4 + damage_dy * damage_dy/4);
						player.damage_dx /= length1;
						player.damage_dy /= length1;
						if(player.negativeEffectsState == NegativeEffects.WEAKENED){
							player.health -= random.nextInt((int) (maxDamage*2-minDamage*2))+minDamage*2;
							player.damagePushForce = random.nextInt((int) (((maxDamage-minDamage))+minDamage));
						}else{
							player.health -= random.nextInt((int) (maxDamage-minDamage))+minDamage;
							player.damagePushForce = random.nextInt((int) (((maxDamage-minDamage))+minDamage));
						}
						
					}

					timer = 0;
					timer2 = 0;
					if(negativeEffectsState.equals(NegativeEffects.FADE_N)){
						setNegativeEffect(NegativeEffects.NONE);
					}
					attackSequenceStarted = false;
				}
			}
		}
	}
	protected void inflictToTurret(int standing, int animation, Turret turret,
			CameraHelper cameraHelper, int attackSpeed) {
		// Timer is for the length of the actual animation
		// Timer2 is for the waiting period
		if (oldPos.x == position.x && oldPos.y == position.y) {
			if (timer2 < attackSpeed) {
				timer2++;
				// // System.out.println("timer2: " + timer2 );
				currentFrame = animationsStandard.get(state).animate(standing);
			}
			if (timer2 >= attackSpeed && timer < 30) {
//				if (oRangeAura.overlaps(player.circle)) {
//					cameraHelper.setShakeAmt(25);
//					cameraHelper.cameraShake();
//				}
				// // System.out.println("timer1: " + timer);
				currentFrame = animationsStandard.get(state)
						.doComplexAnimation(animation, 1.8f,
								Gdx.graphics.getDeltaTime(), Animation.PlayMode.NORMAL);
				
				sprite.setRegion(animationsStandard.get(state)
						.getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
				timer++;
				if (timer == 30 && timer2 >= attackSpeed) {
					currentFrame = animationsStandard.get(state).animate(
							standing);
					// And may be inflict different hurts, direction/ kinds of
					// hurts/ etc.
					if (turret!=null && oRangeAura.overlaps(turret.circle)
							&& !turret.hurt) {
						turret.hurt = true;
						turret.health -= random.nextInt((int) (maxDD-minDD))+minDD;
						System.out.println("turret health has been decreased by 1 + " + turret.health );
					}
					
					timer = 0;
					timer2 = 0;
					turretAttackSequenceStarted = false;
				}
			}
		}
	}

	private void onPathMovingAndCollisionDetection(
			TiledMapTileLayer collisionLayer, AbstractGameObject player,
			float enemyPathDx, float enemyPathDy, List<Enemy> enemies) {
		Collidable collidableLeft = null;
		Collidable collidableRight = null;
		Collidable collidableDown = null;
		Collidable collidableUp = null;

		moveOnPath(collidableLeft, collidableRight, collidableDown,
				collidableUp, enemyPathDx, enemyPathDy, movementSpeed, enemies,
				collisionLayer, player);
		collidableLeft = collisionCheckerLeft(collisionLayer, enemies);
		collidableRight = collisionCheckerRight(collisionLayer, enemies);
		collidableDown = collisionCheckerBottom(collisionLayer, enemies);
		collidableUp = collisionCheckerTop(collisionLayer, enemies);
	}

	@Override
	public Collidable collisionCheckerRight(TiledMapTileLayer collisionLayer) {
		return collisionCheckerRight(collisionLayer, L1.enemiesOnStage);
	}

	@Override
	public Collidable collisionCheckerLeft(TiledMapTileLayer collisionLayer) {
		return collisionCheckerLeft(collisionLayer, L1.enemiesOnStage);
	}

	@Override
	public Collidable collisionCheckerTop(TiledMapTileLayer collisionLayer) {
		return collisionCheckerTop(collisionLayer, L1.enemiesOnStage);
	}

	@Override
	public Collidable collisionCheckerBottom(TiledMapTileLayer collisionLayer) {
		return collisionCheckerBottom(collisionLayer, L1.enemiesOnStage);
	}

	public Collidable collisionCheckerTop(TiledMapTileLayer collisionLayer,
			List<Enemy> enemies) {
		Collidable collidable;
		collidable = CollisionHelper.isCollidable(
				position.x + sprite.getWidth(),
				(position.y + sprite.getHeight()) - 1, collisionLayer);
		if (collidable == null)
			collidable = CollisionHelper.isCollidable(position.x,
					(position.y + sprite.getHeight()) - 1, collisionLayer);
		if (collidable == null)
			collidable = CollisionHelper.isCollidable(
					position.x + (sprite.getWidth() / 2),
					(position.y + sprite.getHeight()) - 1, collisionLayer);
		// if(collidable == null)collidable =
		// CollisionHelper.isCollidableEnemy(this, enemies);
		return collidable;
	}

	public Collidable collisionCheckerBottom(TiledMapTileLayer collisionLayer,
			List<Enemy> enemies) {
		Collidable collidable;
		collidable = CollisionHelper.isCollidable(
				position.x + sprite.getWidth(), position.y, collisionLayer);
		if (collidable == null)
			collidable = CollisionHelper.isCollidable(position.x, position.y,
					collisionLayer);
		if (collidable == null)
			collidable = CollisionHelper.isCollidable(
					position.x + (sprite.getWidth() / 2), position.y,
					collisionLayer);
		// if(collidable == null)collidable =
		// CollisionHelper.isCollidableEnemy(this, enemies);
		return collidable;
	}

	public Collidable collisionCheckerRight(TiledMapTileLayer collisionLayer,
			List<Enemy> enemies) {
		Collidable collidable;
		collidable = CollisionHelper.isCollidable(
				position.x + sprite.getWidth(),
				position.y + (sprite.getHeight() / 2) - 1, collisionLayer);
		if (collidable == null)
			collidable = CollisionHelper.isCollidable(
					position.x + sprite.getWidth(), position.y, collisionLayer);
		if (collidable == null)
			collidable = CollisionHelper.isCollidable(
					position.x + sprite.getWidth(),
					position.y + (sprite.getHeight() / 4), collisionLayer);
		// if(collidable == null)collidable =
		// CollisionHelper.isCollidableEnemy(this, enemies);
		return collidable;
	}

	public Collidable collisionCheckerLeft(TiledMapTileLayer collisionLayer,
			List<Enemy> enemies) {
		Collidable collidable = CollisionHelper.isCollidable(position.x,
				position.y + (sprite.getHeight() / 2) - 1, collisionLayer);
		if (collidable == null)
			collidable = CollisionHelper.isCollidable(position.x, position.y,
					collisionLayer);
		if (collidable == null)
			collidable = CollisionHelper.isCollidable(position.x, position.y
					+ (sprite.getHeight() / 4), collisionLayer);
		// if(collidable == null)collidable =
		// CollisionHelper.isCollidableEnemy(this, enemies);
		return collidable;
	}

	public void move(Vector2 target, Collidable collidableLeft,
			Collidable collidableRight, Collidable collidableDown,
			Collidable collidableUp, float enemyDx, float enemyDy,
			float playerMovementSpeed, List<Enemy> enemies) {
		if (!iAmWaiting) {
			
//			System.err.println("moving...");
			if (position.x > target.x - 4
					|| position.x < target.x - 10
					|| position.y > target.y - 4
					|| position.y < target.y - 10) {
				// // System.out.println("yes it is !");
				if (collidableLeft == null || collidableRight == null) {
					position.x += enemyDx * playerMovementSpeed;
				}
				if (collidableUp == null || collidableDown == null) {
					position.y += enemyDy * playerMovementSpeed;
				}
				sprite.translateX(-playerMovementSpeed);
			}

			if (position.x > target.x - 10) {
				playerMovementDirectionLR = "left";
			}
			if (position.x < target.x - 10) {
				playerMovementDirectionLR = "right";
			}
			if (position.y > target.y - 10) {
				playerMovementDirectionUD = "down";
			}
			if (position.y < target.y - 10) {
				playerMovementDirectionUD = "up";
			}

			if (Math.abs((enemyDx * playerMovementSpeed)) > Math
					.abs((enemyDy * playerMovementSpeed)) && enemyDx > 0) {
				playerMovementDirection = "right";
				currentFrame = animationsStandard.get(state).animate(24);
			}
			if (Math.abs((enemyDx * playerMovementSpeed)) > Math
					.abs((enemyDy * playerMovementSpeed)) && enemyDx < 0) {
				playerMovementDirection = "left";
				currentFrame = animationsStandard.get(state).animate(8);
			}
			if (Math.abs((enemyDx * playerMovementSpeed)) < Math
					.abs((enemyDy * playerMovementSpeed)) && enemyDy < 0) {
				playerMovementDirection = "down";
				currentFrame = animationsStandard.get(state).animate(0);
			}
			if (Math.abs((enemyDx * playerMovementSpeed)) < Math
					.abs((enemyDy * playerMovementSpeed)) && enemyDy > 0) {
				playerMovementDirection = "up";
				currentFrame = animationsStandard.get(state).animate(16);
			}
		} else {
			position = oldPos;
			stop(156);
		}
	}

	protected void collisionCheck(Collidable collidable,
			TiledMapTileLayer collisionLayer, AbstractGameObject player) {
		if (collidable != null) {
			contact(collidable, collisionLayer, player);
		}
	}

	private void moveOnPath(Collidable collidableLeft,
			Collidable collidableRight, Collidable collidableDown,
			Collidable collidableUp, float enemyPathDx, float enemyPathDy,
			float playerMovementSpeed, List<Enemy> enemies,
			TiledMapTileLayer collisionLayer, AbstractGameObject player) {
		currentlyMovingOnPath = true;
		if (!iAmWaiting) {
			if (path != null && path[cunter] != null) {
				
				//:TODO You shitty problem with the enemy moving on the path shittily is over here!
				
				if (position.x+sprite.getWidth()/2 > (path[cunter].x * Constants.NodeSize) - 10
						|| position.x+sprite.getWidth()/2 < (path[cunter].x * Constants.NodeSize) - 20
						|| position.y > (path[cunter].y * Constants.NodeSize) - 10
						|| position.y < (path[cunter].y * Constants.NodeSize) - 20) {
//					System.out.println("I realy should be moving on path right about now");

					if (collidableLeft == null || collidableRight == null) {
						position.x += enemyPathDx * playerMovementSpeed;
					}
					if (collidableUp == null || collidableDown == null) {
						position.y += enemyPathDy * playerMovementSpeed;
					}
					sprite.translateX(-playerMovementSpeed);
				}
				if (position.x > (path[cunter].x * Constants.NodeSize) - 10) {
					playerMovementDirectionLR = "left";
				}
				if (position.x < (path[cunter].x * Constants.NodeSize) - 10) {
					playerMovementDirectionLR = "right";
				}
				if (position.y > (path[cunter].x * Constants.NodeSize) - 10) {
					playerMovementDirectionUD = "down";
				}
				if (position.y < (path[cunter].x * Constants.NodeSize) - 10) {
					playerMovementDirectionUD = "up";
				}
			}
		} else {
			position = oldPos;
			stop(145);
		}

		if (Math.abs((enemyPathDx * playerMovementSpeed)) > Math
				.abs((enemyPathDy * playerMovementSpeed)) && enemyPathDx > 0) {
			playerMovementDirection = "right";
			currentFrame = animationsStandard.get(state).animate(24);
		}
		if (Math.abs((enemyPathDx * playerMovementSpeed)) > Math
				.abs((enemyPathDy * playerMovementSpeed)) && enemyPathDx < 0) {
			playerMovementDirection = "left";
			currentFrame = animationsStandard.get(state).animate(8);
		}
		if (Math.abs((enemyPathDx * playerMovementSpeed)) < Math
				.abs((enemyPathDy * playerMovementSpeed)) && enemyPathDy < 0) {
			playerMovementDirection = "down";
			currentFrame = animationsStandard.get(state).animate(0);
		}
		if (Math.abs((enemyPathDx * playerMovementSpeed)) < Math
				.abs((enemyPathDy * playerMovementSpeed)) && enemyPathDy > 0) {
			playerMovementDirection = "up";
			currentFrame = animationsStandard.get(state).animate(16);
		}

		if (cunter <= 1) {
			currentlyMovingOnPath = false;
		}
	}

	private void standAnimation(int r, int l, int u, int d) {
		if (oldPos.x == position.x && oldPos.y == position.y) {
			if (playerMovementDirectionLR == "right") {
				currentFrame = animationsStandard.get(state).animate(r);
			}
			if (playerMovementDirectionLR == "left") {
				currentFrame = animationsStandard.get(state).animate(l);
			}
			if (playerMovementDirectionLR == "up") {
				currentFrame = animationsStandard.get(state).animate(u);
			}
			if (playerMovementDirectionLR == "down") {
				currentFrame = animationsStandard.get(state).animate(d);
			}
		}
	}

	private void orientOnPath() {
		if (path != null && cunter >= 0 && path[cunter] != null
				&& position.x >= (path[cunter].x * Constants.NodeSize) - 1
				&& position.x <= (path[cunter].x * Constants.NodeSize) + 1
				&& position.y <= (path[cunter].y * Constants.NodeSize) + 1
				&& position.y >= (path[cunter].y * Constants.NodeSize) - 1) {
			path[cunter] = null;
			// //
			// System.out.println("taking of one Node from the path of Nodes, there was: "
			// + cunter + " Nodes and now there are: ");
			cunter--;
			// // System.out.println(cunter);
		}
	}

	private void stop(float secs) {
		if (timereskin < secs) {
			enemyDx = 0;
			enemyDy = 0;
			timereskin++;
			// // System.out.println("stoped: " + timereskin);
		} else if (timereskin > secs - 1) {
			iAmWaiting = false;
			timereskin = 0;
		}
	}

	private void contact(Collidable collidable,
			TiledMapTileLayer collisionLayer, AbstractGameObject player) {
		if (!charging) {
			collidable.doCollide(this, collisionLayer);
			collidable.doCollideAbstactObject(this);
			if (!currentlyMovingOnPath && position.x + 200 > player.position.x
					&& position.x - 200 < player.position.x
					&& position.y + 200 > player.position.y
					&& position.y - 200 < player.position.y) {
				Pathfinder.findPathInThreadPool(position, player.position,
						collisionLayer, this);
			}
			// // System.out.println(position.x);
			// //
			// System.out.println(theController.level1.getPlayer().position.x);
			state = State.PURSUIT;
		}
	}

	// temporary look

	private void damageFromRight(Collidable collidableUp,
			HashMap<State, AnimationControl> animationsStandard) {
		if (projectileLocation == "right" && collidableUp == null) {
			// // System.out.println("supposed to be animating... Right");
			currentFrame = animationsStandard.get(State.STANDARD)
					.doComplexAnimation(112, 0.6f,
							Gdx.graphics.getDeltaTime() / 2, Animation.PlayMode.NORMAL);
			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);

			position.x -= movementSpeed / 3;
			sprite.translateY(movementSpeed / 3);
		}
	}

	private void damageFromLeft(Collidable collidableUp,
			HashMap<State, AnimationControl> animationsStandard) {
		if (projectileLocation == "left" && collidableUp == null) {
			// // System.out.println("supposed to be animating... Left");
			currentFrame = animationsStandard.get(State.STANDARD)
					.doComplexAnimation(108, 0.6f,
							Gdx.graphics.getDeltaTime() / 2, Animation.PlayMode.NORMAL);

			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
			position.x += movementSpeed / 3;
			sprite.translateY(movementSpeed / 3);
		}
	}

	private void damageFromBottom(Collidable collidableUp,
			HashMap<State, AnimationControl> animationsStandard) {
		if (projectileLocation == "bottom" && collidableUp == null) {
			currentFrame = animationsStandard.get(State.STANDARD)
					.doComplexAnimation(104, 0.6f,
							Gdx.graphics.getDeltaTime() / 2, Animation.PlayMode.NORMAL);

			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
			position.y += movementSpeed / 3;
			sprite.translateY(movementSpeed / 3);
		}
	}

	private void damagedFromTop(Collidable collidableUp,
			HashMap<State, AnimationControl> animationsStandard) {
		if (projectileLocation == "top" && collidableUp == null) {

			// // System.out.println("supposed to be animating... Top");
			currentFrame = animationsStandard.get(State.STANDARD)
					.doComplexAnimation(116, 0.6f,
							Gdx.graphics.getDeltaTime() / 2, Animation.PlayMode.NORMAL);

			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);

			position.y -= movementSpeed / 3;
			sprite.translateY(movementSpeed / 3);
		}
	}

	public void enemyHurt(float dmg) {
		state = State.STANDARD;
		if (health >= 0 && !negativeEffectsState.equals(NegativeEffects.FADE_N)) {
//			movementSpeed = movementSpeed/2;
			if(negativeEffectsState.equals(NegativeEffects.WEAKENED)){
				dmg=dmg*2;
			}
			health -= dmg;
			floatingOutputDamage = dmg;
//			System.out.println("Player.damage " + dmg);
		}
	}

	public void enemyTurretHurt(Turret turret) {
		state = State.STANDARD;
		if (health >= 0) {
			health -= random.nextInt((int) (turret.maxDD-turret.minDD*2))+turret.minDD*2;;
		}
	}
	
	public void enemyHydraHurt(Turret turret) {
		state = State.STANDARD;
		if (health >= 0) {
			health -= ProjectileHydra.damage;
		}
	}
	// temporary look
	@Override
	public void setPlayerMovementDirection(String playerMovementDirection) {
		this.playerMovementDirectionLR = playerMovementDirection;
	}

	private int findLastNotNullInArray() {
		int i = 0;
		if (path != null)
			while (path[i] != null) {
				i++;
			}
		return i - 1;
	}

	public Node[] getPath() {
		return path;
	}

	public void setPath(Node[] path) {
		this.path = path;
	}

	public int getTimereskin() {
		return timereskin;
	}

	public void setTimereskin(int timereskin) {
		this.timereskin = timereskin;
	}

	@Override
	public void doCollide(AbstractGameObject abstractGameObject,
			TiledMapTileLayer collisionLayer) {
	}
	

	@Override
	public void doCollideAbstactObject(AbstractGameObject abstractGameObject) {
		if (playerMovementDirectionLR == "right") {
			abstractGameObject.setPosition(new Vector2(abstractGameObject
					.getPosition().x - 3, abstractGameObject.getPosition().y));
			state = State.STANDARD;
		}
		if (playerMovementDirectionLR == "left") {
			abstractGameObject.setPosition(new Vector2(abstractGameObject
					.getPosition().x + 3, abstractGameObject.getPosition().y));
			state = State.STANDARD;
		}
		if (playerMovementDirectionUD == "up") {
			abstractGameObject.setPosition(new Vector2(abstractGameObject
					.getPosition().x, abstractGameObject.getPosition().y - 3));
			state = State.STANDARD;
		}
		if (playerMovementDirectionUD == "down") {
			abstractGameObject.setPosition(new Vector2(abstractGameObject
					.getPosition().x, abstractGameObject.getPosition().y + 3));
			state = State.STANDARD;
		}

	}

	private boolean souldIWait(List<Enemy> enemies) {
		for (Enemy e : enemies) {
			if (e != this && Intersector.overlaps(e.sprite.getBoundingRectangle(), this.sprite.getBoundingRectangle())
					&& !e.iAmWaiting) {
				this.iAmWaiting = true;
				return true;
			}
		}
		return false;
	}

	@Override
	public void setNegativeEffect(NegativeEffects negativeEffect) {
		switch (negativeEffect) {
		case FROZEN:
			if (negativeEffectsState != NegativeEffects.FROZEN) {
				sprite.setColor(4 / 255f, 180 / 255f, 1f, 1f);
				movementSpeed = movementSpeed/2;
				negativeEffectsState = NegativeEffects.FROZEN;
				negativeEffectTimer = negativeEffect.lifetime;
				System.out.println("negative effect timer " + negativeEffectTimer);
			}
			radioactiveAura = null;
			break;
		case POISONED:
			if (negativeEffectsState != NegativeEffects.POISONED) {
				movementSpeed = STANDART_MOVEMENT_SPEED;
				negativeEffectsState = NegativeEffects.POISONED;
				negativeEffectTimer = negativeEffect.lifetime;
			}
			radioactiveAura = null;
			break;
		case FADE_N:
				System.out.println("I AM SETTING FADE!~");
				movementSpeed = STANDART_MOVEMENT_SPEED;
				this.sprite.setColor(sprite.getColor().r, sprite.getColor().g,
						sprite.getColor().b, 0.5f);
				negativeEffectsState = NegativeEffects.FADE_N;
				radioactiveAura = null;
				negativeEffectTimer = 900;
			break;
		case HASTE_N:
			if (negativeEffectsState != NegativeEffects.HASTE_N) {
				movementSpeed = STANDART_MOVEMENT_SPEED;
				this.sprite.setColor(220f / 255, 20f / 255, 60f / 255, 1f);
				negativeEffectsState = NegativeEffects.HASTE_N;
				radioactiveAura = null;
				minDamage = minDamage + 2;
				maxDamage = maxDamage + 2;
				negativeEffectTimer = 900;
				//: TODO possible change the timer to something else;
			}
			break;
		case RADIOACTIVE_N:
			if (negativeEffectsState != NegativeEffects.RADIOACTIVE_N) {
				movementSpeed = STANDART_MOVEMENT_SPEED;
				negativeEffectsState = NegativeEffects.RADIOACTIVE_N;
				radioactiveAura = new Circle(position.x + sprite.getWidth() / 2,
						position.y + sprite.getHeight() / 2,
						RADIOACTIVE.RADIOACTIVE_Radius);
				negativeEffectTimer = 100;
				//: TODO possible change the timer to something else;
			}
			break;
		case WEAKENED:
			if (negativeEffectsState != NegativeEffects.WEAKENED) {
				this.sprite.setColor(220f / 255, 20f / 255, 170f / 255, 1f);
				negativeEffectsState = NegativeEffects.WEAKENED;
				movementSpeed = STANDART_MOVEMENT_SPEED;
				negativeEffectTimer = 900;
				//: TODO possible change the timer to something else;
			}
			break;
		case ICE:
			if (negativeEffectsState != NegativeEffects.ICE) {
				negativeEffectsState = negativeEffect;
				negativeEffectTimer = ICE_THING.negativeEffectLifeTime;
				iceCube = new Sprite(Assets.manager.get(Assets.iceCube));
				iceCube.setSize(sprite.getBoundingRectangle().width, sprite.getBoundingRectangle().height);
				iceCube.setX(sprite.getX());
				iceCube.setY(sprite.getY());
			}
			radioactiveAura = null;
			break;
		case STUN:
			if (negativeEffectsState != NegativeEffects.STUN && negativeEffectsState != NegativeEffects.ICE) {
				negativeEffectsState = negativeEffect;
				negativeEffectTimer = NegativeEffects.STUN.lifetime;
			} else {
				damageType = "player";
				hurt = true;
				enemyHurt(random.nextInt((int) (L1.player.weapon.maxDD - L1.player.weapon.minDD)) + L1.player.weapon.minDD);
			}
			radioactiveAura = null;
			break;
		case NONE:
			if(toughness==null){
				sprite.setColor(1, 1, 1, 1);
			}
			radioactiveAura = null;
			iceCube = null;
			if(negativeEffectsState != null && negativeEffectsState.equals(NegativeEffects.HASTE_N)){
				minDamage = minDamage - 2;
				maxDamage = maxDamage - 2;
			}
			if(negativeEffectsState != null && negativeEffectsState.equals(NegativeEffects.RADIOACTIVE_N)){
				state = State.DEAD;
			}
			movementSpeed = STANDART_MOVEMENT_SPEED;
			negativeEffectsState = negativeEffect;
			break;
		}
	}

	
	@Override
	public float getDx() {
		return enemyDx;
	}

	@Override
	public float getDy() {
		return enemyDy;
	}

	public void setDx(float playerDx) {
		this.enemyDx = playerDx;
	}

	public void setDy(float playerDy) {
		this.enemyDy = playerDy;
	}

}
