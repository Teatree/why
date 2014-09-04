package com.me.swampmonster.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.items.HASTE;
import com.me.swampmonster.models.items.RADIOACTIVE;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.models.slots.Trap;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.utils.Assets;

public class Player extends AbstractGameObject {

	private static final float DEFAULT_DAMAGE = 1f;
	private static final float DEFAULT_ARROW_MOVEMENT_SPEED = 1.8f;
	private static final float DEFAULT_MOVEMENT_SPEED = 1.5f;
	private static final int DEFAULT_MAX_HEALTH = 7;
	private static final int DEFAULT_MAX_O2 = 96;
	private static final float FROZEN_MOVEMENT = 0.16f;
	private static final float SPEED_BOOST_EFFECT = 1.1f;

	// feedback
	public static int enemiesKilled;
	public static int playerKilled;
	public static int shotArrows;
	public static float damage;

	public static boolean shootingSwitch;

	public Sprite positiveEffectSprite;
	public Sprite aimingAuraSprite;
	int time = 0;
	int timer3hurt = 0;
	int timerPoisoned = 0;
	int timeDead = 0;
	private int timeShooting = 0;
	String nastyaSpriteStandard;
	String nastyaSpriteGun;
	public Sprite bow;
	public TextureRegion[][] bowFrames;
	public TextureRegion[][] aimingAuraFrames;
	// responsible for what kind of animation are to be played in the Animating
	// State
	String doing;
	// responsible for what kind of animation are to be played in the Animating
	// State, to be changed to something better
	public boolean maskOn;
	public boolean justSpawned;
	public boolean shooting;
	public boolean pointGathered;
	public boolean ThreeArrowsFlag;
	public boolean stuned;
	public List<Projectile> projectiles;
	public Vector3 shotDir;
	public Vector3 V3playerPos;
	public Vector3 aimLineHead;
	public float oxygen;
	public static float maxOxygen;
	public static int absoluteScore;
	public static int levelsScore;
	public Integer positiveEffectCounter;
	public Integer negativeEffectCounter;
	private Random random;
	public Rectangle fearRectangle;
	public Turret turret;

	public Circle aimingArea;
	// public Circle invalidSpawnArea;

	public Enemy harmfulEnemy;

	public Circle radioactiveAura = null;
	public int timer2;

	private float playerDy;
	private float playerDx;

	public Trap trap;
	public int trapTimer;

	public EffectCarriers arrowEffectCarrier;
	public static float arrowMovementSpeed;

	public Player(Vector2 position) {
		maxOxygen = DEFAULT_MAX_O2;
		maxHealth = DEFAULT_MAX_HEALTH;
		arrowMovementSpeed = DEFAULT_ARROW_MOVEMENT_SPEED;

		levelsScore = absoluteScore;
		state = State.STANDARD;
		positiveEffectsState = PositiveEffects.NONE;
		negativeEffectsState = NegativeEffects.NONE;
		positiveEffectCounter = new Integer(0);
		negativeEffectCounter = new Integer(0);

		this.position = position;
		movementSpeed = DEFAULT_MOVEMENT_SPEED;
		STANDART_MOVEMENT_SPEED = DEFAULT_MOVEMENT_SPEED;
		random = new Random();
		bowFrames = TextureRegion.split((Assets.manager.get(Assets.bow)), 32,
				32);
		bow = new Sprite(bowFrames[0][0]);
		aimingAuraFrames = TextureRegion.split(
				(Assets.manager.get(Assets.aimingAuraSprite)), 64, 64);
		aimingAuraSprite = new Sprite(aimingAuraFrames[0][0]);
		aimingAuraSprite.setSize(48, 48);
		hurt = false;
		aimingArea = new Circle();
		aimingArea.radius = 8;
		circle = new Circle();
		circle.radius = 16;
		V3playerPos = new Vector3();
		rectanlge = new Rectangle();
		projectiles = new LinkedList<Projectile>();
		aimLineHead = new Vector3();

		arrowEffectCarrier = EffectCarriers.NONE;

		animationsStandard.put(State.STANDARD, new AnimationControl(
				Assets.manager.get(Assets.nastyaSpriteStandard), 8, 32, 7));
		animationsStandard.put(State.ANIMATING, new AnimationControl(
				Assets.manager.get(Assets.nastyaSpriteStandard), 8, 32, 8));
		animationsStandard.put(State.ANIMATINGLARGE, new AnimationControl(
				Assets.manager.get(Assets.nastyaSpriteStandard), 8, 32, 8));
		animationsStandard.put(State.ACTIVATING, new AnimationControl(
				Assets.manager.get(Assets.nastyaSpriteStandard), 8, 32, 8));
		animationsStandard.put(State.GUNMOVEMENT, new AnimationControl(
				Assets.manager.get(Assets.nastyaSpriteGun), 8, 32, 7));
		animationsStandard.put(
				State.DEAD,
				new AnimationControl(Assets.manager
						.get(Assets.nastyaSpriteStandard), 8, 32, 8));

		oldPos = position;

		dead = false;
		maskOn = true;
		justSpawned = true;
		shooting = false;

		// ***Character stats board***
		characterStatsBoard();
		// ***Character stats board***
		// sprite = new Sprite(animationsStandard.get(State.STANDARD)
		// .getCurrentFrame());
		// sprite.setColor(1, 1, 1, 1);
		// shotDir = new Vector3();
		// sprite.setSize(sprite.getWidth() / 2, sprite.getHeight() / 2);
		// allowedToShoot = true;
	}

	public void characterStatsBoard() {
		// HEALTH, DAMAGE, OXYGEN, TYPE, TOUGHGUY, COLORSCHEME, ETC.
		// playerMaxHealth = 16;
		health = maxHealth;
		oxygen = maxOxygen;
		sprite = new Sprite(animationsStandard.get(State.STANDARD)
				.getCurrentFrame());
		sprite.setColor(1, 1, 1, 1);
		shotDir = new Vector3();
		sprite.setSize(sprite.getWidth() / 2, sprite.getHeight() / 2);
		// :TODO IN ORDER TO CHANGE THIS, YOU GOT TO GET DOWN TO WHERE SHOOTIGN
		// IS HAPPENING!
		// shotCoolDown = 90;
		state = State.STANDARD;
		movementSpeed = STANDART_MOVEMENT_SPEED;
	}

	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point,
			TiledMapTileLayer collisionLayer, float dx, float dy) {

		// System.out.println("player pos: " + position.x + " : " + position.y +
		// " __ " + position);
		oldPos.x = position.x;
		oldPos.y = position.y;

		circle.x = position.x + sprite.getWidth() / 2;
		circle.y = position.y + sprite.getHeight() / 2;

		aimingArea.x = position.x + sprite.getWidth() / 2;
		aimingArea.y = position.y + sprite.getHeight() / 2;
		// invalidSpawnArea.x = position.x + 8;
		// invalidSpawnArea.y = position.y + 16;

		V3playerPos.x = position.x + circle.radius / 2;
		V3playerPos.y = position.y + circle.radius / 2;
		V3playerPos.z = 0;

		sprite.setX(position.x);
		sprite.setY(position.y);

		rectanlge.x = position.x;
		rectanlge.y = position.y;
		rectanlge.width = sprite.getWidth();
		rectanlge.height = sprite.getHeight();

		aimingAuraSprite.setX(position.x - 9);
		aimingAuraSprite.setY(position.y - 8);

		if (!L1.hasAtmosphere) {
			oxygen -= 0.005f;
		}

		if (Gdx.input.justTouched()) {
			justSpawned = false;
		}

		painLogic();

		shotDir.x = (position.x + sprite.getWidth() / 2) * 2 - V3point.x;
		shotDir.y = (position.y + sprite.getHeight() / 2) * 2 - V3point.y;

		if (turret != null) {
			turret.update();

			if (turret.state == State.DEAD) {
				if (turret.timeRemove < 180) {
					turret.timeRemove++;
				} else if (turret.timeRemove > 179) {
					turret.timeRemove = 0;
					this.turret = null;
				}
			}
		}

		// STANDARD
		if (state.equals(State.STANDARD)) {
			standart(touchPos, collisionLayer, dx, dy);
		}

		// GUN
		if (state.equals(State.GUNMOVEMENT)
				&& negativeEffectsState != NegativeEffects.STUN) {
			gunMovement(aiming, touchPos, V3point);
		}

		// DEAD
		if (state.equals(State.DEAD)) {
			maxOxygen = DEFAULT_MAX_O2;
			maxHealth = DEFAULT_MAX_HEALTH;
			damage = DEFAULT_DAMAGE;
			arrowMovementSpeed = DEFAULT_ARROW_MOVEMENT_SPEED;
			movementSpeed = DEFAULT_MOVEMENT_SPEED;
			absoluteScore = 0;
			levelsScore = 0;
			trap = null;
			turret = null;
			SlotMachineScreen.savedSlots = new ArrayList<Slot>();
			TheController.skill = null;
			dying();
		}

		// Hurt
		if (hurt && !positiveEffectsState.equals(PositiveEffects.FADE)) {
			if (time < 40) {
				time++;
				if (damageType == "enemy") {
					takingDamageFromEnemy(harmfulEnemy, touchPos,
							collisionLayer);
				}
				if (/* damageType != "lackOfOxygen" && */time > 39) {
					hurt = false;
					time = 0;
				}
			}
		}

		// TODO tried to fix when player stuck in shooting
		// if (!rectanlge.contains(V3point.x, V3point.y))
		shooting(V3point);

		updateProjectiles(collisionLayer);

		checkEffects(touchPos);
		if (radioactiveAura != null) {
			radioactiveAura.x = position.x + sprite.getWidth() / 2;
			radioactiveAura.y = position.y + sprite.getHeight() / 2;
		}

		if (negativeEffectsState == NegativeEffects.POISONED) {
			poisoning();
		}

		updateTrap();

		Iterator<Prop> propItr = L1.props.iterator();
		while (propItr.hasNext()) {
			Prop prop = propItr.next();

			float propDx = prop.position.x - V3playerPos.x;
			float propDy = prop.position.y - V3playerPos.y;

			float length1 = (float) Math
					.sqrt(propDx * propDx + propDy * propDy);
			propDx /= length1;
			propDy /= length1;

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

					if (cL == null && propDx <= 0 || cR == null && propDx > 0) {
						prop.position.x += propDx / 2 /** movementSpeed*4 */
						;
						// position.x += getDx()* movementSpeed;
					}
					if (cD == null && propDy <= 0 || cU == null && propDy > 0) {
						prop.position.y += propDy / 2 /** movementSpeed*4 */
						;
						// position.y += getDy()* movementSpeed;
					}
				}
			}
		}
	}

	private void updateProjectiles(TiledMapTileLayer collisionLayer) {
		Iterator<Projectile> prj = projectiles.iterator();
		while (prj.hasNext()) {
			// System.err.println("player");
			Projectile p = prj.next();
			p.update();
			p.getSurfaceLevelProjectile(collisionLayer);
			if (p.isCollision(collisionLayer)
					&& p.effect != EffectCarriers.SHADOW) {
				if (p.effect == EffectCarriers.EXPLOSIVE) {
					TheController.skill.explode(p.position);
				}
				prj.remove();
			} else if (p.isCollisionNBreakable(collisionLayer)
					&& L1.hasAtmosphere) {
				Explosion expl = new Explosion(new Vector2(p.position.x,
						p.position.y), Explosion.EXPLOSION_TYPE_INVERTED);
				L1.explosions.add(expl);
				L1.hasAtmosphere = false;
				prj.remove();
			} else if (p != null && p.state == State.DEAD) {
				prj.remove();
			}
		}
	}

	private void shooting(Vector3 V3point) {
		if (shooting && timeShooting < 30) {
			if (positiveEffectsState == PositiveEffects.FADE) {
				setPositiveEffect(PositiveEffects.NONE);
			}
			currentFrame = animationsStandard.get(state).doComplexAnimation(32,
					0.5f, 0.001f, Animation.NORMAL);
			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);

			timeShooting++;

		}
		// if (shooting && timeShooting < 2) {
		// shotDir.x = (position.x + sprite.getWidth() / 2) * 2 - V3point.x;
		// shotDir.y = (position.y + sprite.getHeight() / 2) * 2 - V3point.y;
		//
		// }
		if (shooting && timeShooting > 29) {
			animationsStandard.get(state).setCurrentFrame(currentFrame);
			shooting = false;
			timeShooting = 0;
		}

		// PROJECTILE
		if (shooting && timeShooting < 2) {
			float direction_x = shotDir.x - V3playerPos.x;
			float direction_y = shotDir.y - V3playerPos.y;

			// : TODO This look terrible, make it better bro...
			Projectile p = new Projectile(new Vector2(aimingArea.x
					+ direction_x / 100 - 8, aimingArea.y + direction_y / 100
					- 8), getRotation(shotDir), arrowEffectCarrier);
			shotArrows++;

			p.setPosition(new Vector2(aimingArea.x + direction_x / 100 - 8,
					aimingArea.y + direction_y / 100 - 8));

			p.force = (float) Math.sqrt(Math.pow((V3point.x - position.x), 2)
					+ Math.pow((V3point.y - position.y), 2)) / 50;

			float length = (float) Math.sqrt(direction_x * direction_x
					+ direction_y * direction_y);
			direction_x /= length;
			direction_y /= length;

			p.setDirection(direction_x, direction_y);

			projectiles.add(p);
			if (ThreeArrowsFlag) {
				float direction_x2 = shotDir.x - 40 - V3playerPos.x;
				float direction_y2 = shotDir.y - 40 - V3playerPos.y;
				float direction_x3 = shotDir.x + 48 - V3playerPos.x;
				float direction_y3 = shotDir.y + 48 - V3playerPos.y;

				Projectile p2 = new Projectile(new Vector2(aimingArea.x
						+ direction_x2 / 100 - 8, aimingArea.y + direction_y2
						/ 100 - 8), getRotation(new Vector3(shotDir.x - 40,
						shotDir.y - 40, 0)), arrowEffectCarrier);
				Projectile p3 = new Projectile(new Vector2(aimingArea.x
						+ direction_x3 / 100 - 8, aimingArea.y + direction_y3
						/ 100 - 8), getRotation(new Vector3(shotDir.x + 48,
						shotDir.y + 48, 0)), arrowEffectCarrier);

				p2.setPosition(new Vector2(aimingArea.x + direction_x2 / 100
						- 8, aimingArea.y + direction_y2 / 100 - 8));
				p3.setPosition(new Vector2(aimingArea.x + direction_x3 / 100
						- 8, aimingArea.y + direction_y3 / 100 - 8));

				p2.force = (float) Math.sqrt(Math.pow((V3point.x - position.x),
						2) + Math.pow((V3point.y - position.y), 2)) / 50;
				p3.force = (float) Math.sqrt(Math.pow((V3point.x - position.x),
						2) + Math.pow((V3point.y - position.y), 2)) / 50;

				float length2 = (float) Math.sqrt(direction_x2 * direction_x2
						+ direction_y2 * direction_y2);
				direction_x2 /= length2;
				direction_y2 /= length2;
				float length3 = (float) Math.sqrt(direction_x3 * direction_x3
						+ direction_y3 * direction_y3);
				direction_x3 /= length3;
				direction_y3 /= length3;

				p2.setDirection(direction_x2, direction_y2);
				p3.setDirection(direction_x3, direction_y3);

				projectiles.add(p2);
				projectiles.add(p3);
				ThreeArrowsFlag = false;
			}
			arrowEffectCarrier = EffectCarriers.NONE;
		}
	}

	private void dying() {
		if (timeDead < 89) {
			currentFrame = animationsStandard.get(state).doComplexAnimation(
					112, 1.6f, 0.018f, Animation.NORMAL);
			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
			timeDead++;
		}
		playerKilled++;
		dead = true;
	}

	private void checkEffects(Vector3 touchPos) {
		if (positiveEffectCounter <= 0) {
			setPositiveEffect(PositiveEffects.NONE);
			if (radioactiveAura != null) {
				radioactiveAura.radius = 0;
			}

		} else {
			positiveEffectCounter--;
		}

		if (negativeEffectCounter <= 0) {
			negativeEffectsState = NegativeEffects.NONE;
		} else {
			negativeEffectCounter--;
		}

		if (negativeEffectsState == NegativeEffects.FEAR && pointGathered) {
			touchPos.x = random.nextInt(300) + touchPos.x - 150;
			touchPos.y = random.nextInt(300) + touchPos.y - 150;
			pointGathered = false;
		}

	}

	private void standart(Vector3 touchPos, TiledMapTileLayer collisionLayer,
			float dx, float dy) {
		sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
		sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);

		if (!hurt && negativeEffectsState != NegativeEffects.STUN) {
			movementCollisionAndAnimation(movementSpeed, touchPos,
					collisionLayer, dx, dy);
		}
	}

	// :TODO GUNMOVEMENT
	private void gunMovement(boolean aiming, Vector3 touchPos, Vector3 V3point) {

		double aimingLength = Math.sqrt(Math.pow(aimLineHead.x - position.x, 2)
				+ Math.pow(aimLineHead.y - position.y, 2));

		sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
		sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
		if (aimingLength < 50) {
			bow = new Sprite(bowFrames[0][0]);

		} else if (aimingLength < 100) {
			bow = new Sprite(bowFrames[1][0]);
		} else {
			bow = new Sprite(bowFrames[2][0]);
		}
		bow.setPosition(position.x - 10, position.y);
		bow.setRotation(getRotation(shotDir) * 57.29f);

		// System.out.println(bow.getRotation());

		if (!aiming) {
			currentFrame = animationsStandard.get(state).doComplexAnimation(0,
					0.5f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
			aimingAuraSprite.setRegion(aimingAuraFrames[0][0]);
		}

		if (aiming) {
			aimingAuraSprite.setRegion(aimingAuraFrames[0][1]);
			touchPos.x = position.x + 9;
			touchPos.y = position.y + 4;
			aimLineHead.x = (position.x + sprite.getWidth() / 2) * 2
					- V3point.x;
			aimLineHead.y = (position.y + sprite.getHeight() / 2) * 2
					- V3point.y;

		}
		if (aiming && V3point.y > position.y + 8 && V3point.x < position.x + 32
				&& V3point.x > position.x) {

			currentFrame = animationsStandard.get(state).doComplexAnimation(24,
					0.5f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
		} else if (aiming && V3point.y < position.y + 8
				&& V3point.x < position.x + 32 && V3point.x > position.x) {
			currentFrame = animationsStandard.get(state).doComplexAnimation(0,
					0.5f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
		}
		if (aiming && V3point.x < position.x) {
			currentFrame = animationsStandard.get(state).doComplexAnimation(8,
					0.5f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
		} else if (aiming && V3point.x > position.x + 32) {
			currentFrame = animationsStandard.get(state).doComplexAnimation(16,
					0.5f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
		}

		if (!Gdx.input.isTouched() && aiming) {
			shooting = true;
		}
		if (!aiming && timeShooting == 0) {
			shooting = false;
			state = State.STANDARD;
		}
		if (shooting) {
			aiming = false;
		}
	}

	public void setPositiveEffect(PositiveEffects positiveEffect) {
		switch (positiveEffect) {
		case FADE:
			setNegativeEffect(NegativeEffects.NONE);
			movementSpeed = STANDART_MOVEMENT_SPEED;
			this.sprite.setColor(sprite.getColor().r, sprite.getColor().g,
					sprite.getColor().b, 0.5f);
			radioactiveAura = null;
			positiveEffectsState = positiveEffect;
			positiveEffectCounter = positiveEffect.lifetime;
			break;
		case RADIOACTIVE_AURA:
			movementSpeed = STANDART_MOVEMENT_SPEED;
			radioactiveAura = new Circle(position.x + sprite.getWidth() / 2,
					position.y + sprite.getHeight() / 2,
					RADIOACTIVE.RADIOACTIVE_Radius);
			System.out.println("radioactiveRadius: " + RADIOACTIVE.RADIOACTIVE_Radius);
			positiveEffectsState = positiveEffect;
			positiveEffectCounter = positiveEffect.lifetime;
			break;
		case SPEED_BOOST:
			if (negativeEffectsState == NegativeEffects.FROZEN) {
				setNegativeEffect(NegativeEffects.NONE);
			}
			this.sprite.setColor(1f, 1f, 0f, 1f);
			movementSpeed = SPEED_BOOST_EFFECT;
			radioactiveAura = null;
			positiveEffectsState = positiveEffect;
			positiveEffectCounter = positiveEffect.lifetime;
			break;
		case HASTE:
			if (negativeEffectsState == NegativeEffects.FROZEN) {
				setNegativeEffect(NegativeEffects.NONE);
			}
			this.sprite.setColor(220f/255, 20f/255, 60f/255, 1f);
			radioactiveAura = null;
			damage = damage+2;
			positiveEffectsState = positiveEffect;
			positiveEffectCounter = HASTE.lifeTime;
			break;
		case NONE:
			if (negativeEffectsState == NegativeEffects.NONE
					|| negativeEffectsState == null) {
				sprite.setColor(1, 1, 1, 1);
				movementSpeed = STANDART_MOVEMENT_SPEED;
			}
			radioactiveAura = null;
			damage = DEFAULT_DAMAGE;
			positiveEffectsState = PositiveEffects.NONE;
		}

	}

	@Override
	public void setNegativeEffect(NegativeEffects negativeEffect) {
		if (positiveEffectsState != PositiveEffects.FADE) {
			switch (negativeEffect) {
			case FEAR:
				fearRectangle = new Rectangle();
				movementSpeed = STANDART_MOVEMENT_SPEED;
				negativeEffectsState = negativeEffect;
				negativeEffectCounter = negativeEffect.lifetime;
				break;
			case FROZEN:
				if (positiveEffectsState == PositiveEffects.SPEED_BOOST) {
					setPositiveEffect(PositiveEffects.NONE);
				}
				this.sprite.setColor(4 / 255f, 180 / 255f, 1f, 1f);
				movementSpeed = FROZEN_MOVEMENT;
				fearRectangle = null;
				negativeEffectsState = negativeEffect;
				negativeEffectCounter = negativeEffect.lifetime;
				break;
			case POISONED:
				poisoning();
				negativeEffectsState = negativeEffect;
				negativeEffectCounter = negativeEffect.lifetime;
				break;
			case STUN:
				negativeEffectsState = negativeEffect;
				negativeEffectCounter = negativeEffect.lifetime;
				break;
			case NONE:
				if (positiveEffectsState == PositiveEffects.NONE
						|| positiveEffectsState == null) {
					sprite.setColor(1, 1, 1, 1);
					movementSpeed = STANDART_MOVEMENT_SPEED;
				}
				fearRectangle = null;
				negativeEffectsState = negativeEffect;
			}

		}
	}

	private void takingDamageFromEnemy(Enemy enemy, Vector3 touchPos,
			TiledMapTileLayer collisionLayer) {

		if (enemy.toughness != null) {
			switch (enemy.toughness) {
			case FREEZER_GUY:
				setNegativeEffect(NegativeEffects.FROZEN);
				break;
			case POISONOUS_GUY:
				setNegativeEffect(NegativeEffects.POISONED);
				break;
			case PLASMA_GUY:
				setNegativeEffect(NegativeEffects.FEAR);
				break;
			}
		}
		Collidable collidableUp = null;

		damagedFromTop(collidableUp, enemy, touchPos);
		collidableUp = collisionCheckerUp(collisionLayer);
		collisionCheck(collidableUp, collisionLayer);

		Collidable collidableDown = null;

		damageFromBottom(collidableDown, enemy, touchPos);
		collidableDown = collisionCheckerDown(collisionLayer);
		collisionCheck(collidableDown, collisionLayer);

		Collidable collidableLeft = null;

		damageFromLeft(collidableLeft, enemy, touchPos);
		collidableLeft = collisionCheckerLeft(collisionLayer);
		collisionCheck(collidableLeft, collisionLayer);

		Collidable collidableRight = null;

		damageFromRight(collidableRight, enemy, touchPos);
		collidableRight = collisionCheckerRight(collisionLayer);
		collisionCheck(collidableRight, collisionLayer);
	}

	private void damageFromRight(Collidable collidableUp,
			AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "right" && collidableUp == null) {
			currentFrame = animationsStandard.get(State.STANDARD)
					.doComplexAnimation(108, 0.2f,
							Gdx.graphics.getDeltaTime() / 2, Animation.NORMAL);

			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
			position.x += movementSpeed / 2;
			touchPos.x += movementSpeed / 2;
			sprite.translateY(movementSpeed / 2);
		}
	}

	private void damageFromLeft(Collidable collidableUp,
			AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "left" && collidableUp == null) {
			currentFrame = animationsStandard.get(State.STANDARD)
					.doComplexAnimation(106, 0.2f,
							Gdx.graphics.getDeltaTime() / 2, Animation.NORMAL);

			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
			position.x -= movementSpeed / 2;
			touchPos.x -= movementSpeed / 2;
			sprite.translateY(movementSpeed / 2);
		}
	}

	private void damageFromBottom(Collidable collidableUp,
			AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "down" && collidableUp == null) {
			currentFrame = animationsStandard.get(State.STANDARD)
					.doComplexAnimation(110, 0.2f,
							Gdx.graphics.getDeltaTime() / 2, Animation.NORMAL);

			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
			position.y -= movementSpeed / 2;
			touchPos.y -= movementSpeed / 2;
			sprite.translateY(movementSpeed / 2);
		}
	}

	private void damagedFromTop(Collidable collidableUp,
			AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "up" && collidableUp == null) {
			currentFrame = animationsStandard.get(State.STANDARD)
					.doComplexAnimation(104, 0.2f,
							Gdx.graphics.getDeltaTime() / 2, Animation.NORMAL);

			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);

			position.y += movementSpeed / 2;
			touchPos.y += movementSpeed / 2;
			sprite.translateY(movementSpeed / 2);
		}
	}

	private void movementCollisionAndAnimation(float speed, Vector3 touchPos,
			TiledMapTileLayer collisionLayer, float dx, float dy) {
		// ---------------------movement, just, movement------------------------
		// //
		Collidable collidableLeft = null;
		Collidable collidableRight = null;
		Collidable collidableDown = null;
		Collidable collidableUp = null;

		move(collidableLeft, collidableRight, collidableUp, collidableDown,
				speed, touchPos, dx, dy);
		collidableLeft = collisionCheckerLeft(collisionLayer);
		collisionCheck(collidableLeft, collisionLayer);
		collidableRight = collisionCheckerRight(collisionLayer);
		collisionCheck(collidableRight, collisionLayer);
		collidableDown = collisionCheckerDown(collisionLayer);
		collisionCheck(collidableDown, collisionLayer);
		collidableUp = collisionCheckerUp(collisionLayer);
		collisionCheck(collidableUp, collisionLayer);

		standingAnimation(animationsStandard);
	}

	public Collidable collisionCheckerUp(TiledMapTileLayer collisionLayer) {
		Collidable collidableUp;
		collidableUp = CollisionHelper.isCollidable(
				position.x + (sprite.getWidth() / 2),
				position.y + sprite.getHeight(), collisionLayer);
		if (collidableUp == null)
			collidableUp = CollisionHelper.isCollidable(position.x, position.y
					+ sprite.getHeight(), collisionLayer);
		if (collidableUp == null)
			collidableUp = CollisionHelper.isCollidable(
					position.x + (sprite.getWidth() / 4),
					position.y + sprite.getHeight(), collisionLayer);
		return collidableUp;
	}

	public Collidable collisionCheckerDown(TiledMapTileLayer collisionLayer) {
		Collidable collidableDown;
		collidableDown = CollisionHelper.isCollidable(
				position.x + sprite.getWidth(), position.y, collisionLayer);
		if (collidableDown == null)
			collidableDown = CollisionHelper.isCollidable(position.x,
					position.y, collisionLayer);
		if (collidableDown == null)
			collidableDown = CollisionHelper.isCollidable(
					position.x + (sprite.getWidth() / 2), position.y,
					collisionLayer);
		return collidableDown;
	}

	@Override
	public Collidable collisionCheckerRight(TiledMapTileLayer collisionLayer) {
		Collidable collidableRight;
		collidableRight = CollisionHelper.isCollidable(
				position.x + sprite.getWidth(),
				position.y + (sprite.getHeight() / 2), collisionLayer);
		if (collidableRight == null)
			collidableRight = CollisionHelper.isCollidable(
					position.x + sprite.getWidth(), position.y, collisionLayer);
		if (collidableRight == null)
			collidableRight = CollisionHelper.isCollidable(
					position.x + sprite.getWidth(),
					position.y + (sprite.getHeight() / 4), collisionLayer);
		return collidableRight;
	}

	private void collisionCheck(Collidable collidableLeft,
			TiledMapTileLayer collisionLayer) {
		if (collidableLeft != null) {
			contact(collidableLeft, collisionLayer);
		}
	}

	@Override
	public Collidable collisionCheckerLeft(TiledMapTileLayer collisionLayer) {
		Collidable collidableLeft;
		collidableLeft = CollisionHelper.isCollidable(position.x, position.y
				+ (sprite.getHeight() / 2), collisionLayer);
		if (collidableLeft == null)
			collidableLeft = CollisionHelper.isCollidable(position.x,
					position.y, collisionLayer);
		if (collidableLeft == null)
			collidableLeft = CollisionHelper.isCollidable(position.x,
					position.y + (sprite.getHeight() / 4), collisionLayer);
		return collidableLeft;
	}

	private void move(Collidable collidableLeft, Collidable collidableRight,
			Collidable collidableUp, Collidable collidableDown, float speeds,
			Vector3 touchPos, float dx, float dy) {
		if (position.x > touchPos.x - 4 || position.x < touchPos.x - 14
				|| position.y > touchPos.y - 4 || position.y < touchPos.y - 14) {
			if (collidableLeft == null || collidableRight == null) {
				position.x += dx * movementSpeed;
			}
			if (collidableUp == null || collidableDown == null) {
				position.y += dy * movementSpeed;
			}
			sprite.translateX(-speeds);
		}

		if (position.x > touchPos.x - 4) {
			playerMovementDirectionLR = "left";
		}
		if (position.x < touchPos.x - 14) {
			playerMovementDirectionLR = "right";
		}
		if (position.y > touchPos.y - 4) {
			playerMovementDirectionUD = "down";
		}
		if (position.y < touchPos.y - 14) {
			playerMovementDirectionUD = "up";
		}

		if (Math.abs((dx * movementSpeed)) > Math.abs((dy * movementSpeed))
				&& playerMovementDirectionLR == "right") {
			playerMovementDirection = "right";
			currentFrame = animationsStandard.get(state).animate(16);
		}
		if (Math.abs((dx * movementSpeed)) > Math.abs((dy * movementSpeed))
				&& playerMovementDirectionLR == "left") {
			playerMovementDirection = "left";
			currentFrame = animationsStandard.get(state).animate(24);
		}
		if (Math.abs((dx * movementSpeed)) < Math.abs((dy * movementSpeed))
				&& playerMovementDirectionUD == "down") {
			playerMovementDirection = "down";
			currentFrame = animationsStandard.get(state).animate(0);
		}
		if (Math.abs((dx * movementSpeed)) < Math.abs((dy * movementSpeed))
				&& playerMovementDirectionUD == "up") {
			playerMovementDirection = "up";
			currentFrame = animationsStandard.get(state).animate(8);
		}
	}

	private void standingAnimation(HashMap<State, AnimationControl> animations) {
		if (oldPos.x == position.x && oldPos.y == position.y) {
			if (playerMovementDirection == "right") {
				currentFrame = animations.get(state).animate(48);
			}
			if (playerMovementDirection == "left") {
				currentFrame = animations.get(state).animate(56);
			}
			if (playerMovementDirection == "up") {
				currentFrame = animations.get(state).animate(40);
			}
			if (playerMovementDirection == "down") {
				currentFrame = animations.get(state).animate(32);
			}
		}
	}

	// Collision reaction
	public void contact(Collidable collidable, TiledMapTileLayer collisionLayer) {
		collidable.doCollide(this, collisionLayer);
	}

	public void hurt() {
		if (state != State.DEAD) {
			if (timer3hurt < 50) {
				timer3hurt++;
				if (timer3hurt == 25) {
					// sprite.setColor(sprite.getColor().r,
					// sprite.getColor().g - 1, sprite.getColor().b - 1,
					// sprite.getColor().a);
					sprite.setColor(Color.RED);
				}
				if (timer3hurt == 45) {
					if (negativeEffectsState == NegativeEffects.FROZEN) {
						this.sprite.setColor(4 / 255f, 180 / 255f, 1f, 1f);
					} else if (negativeEffectsState == NegativeEffects.POISONED) {
						sprite.setColor(Color.GREEN);
					} else {
						sprite.setColor(1, 1, 1, 1);
					}
					// sprite.setColor(sprite.getColor().r,
					// sprite.getColor().g + 1, sprite.getColor().b + 1,
					// sprite.getColor().a);
				}
			}
			if (timer3hurt == 50) {
				health--;
				timer3hurt = 0;
			}
		}
	}

	public float getRotation(Vector3 shotDir) {
		double angle1 = Math.atan2(V3playerPos.y - position.y,
				V3playerPos.x - 0);
		double angle2 = Math.atan2(V3playerPos.y - shotDir.y, V3playerPos.x
				- shotDir.x);
		return (float) (angle2 - angle1);
	}

	private void painLogic() {
		if (timer2 > 0) {
			damageTypeOxygen = "lackOfOxygen";
			hurt();
			timer2--;
		}
		if (timer2 <= 0 && oxygen <= 0 && !hurt) {
			timer2 = 80;
		}
		if (health <= 0) {
			state = State.DEAD;
		}
	}

	private void poisoning() {
		damageType = "Poisoned";
		fearRectangle = null;
		if (positiveEffectsState != PositiveEffects.SPEED_BOOST) {
			movementSpeed = STANDART_MOVEMENT_SPEED;
		}
		if (timerPoisoned == 0) {
			this.sprite.setColor(Color.GREEN);
		}
		if (timerPoisoned < 50) {
			timerPoisoned++;

			// if (health > 1) {
			// hurt();
			// }
		}
		if (timerPoisoned == 50) {
			timerPoisoned = 0;
			if (health > 1)
				health--;
		}
	}

	public void decreaseOxygen() {
		// if (oxygen >= 0 && !L1.hasAtmosphere) {
		// oxygen -= 2.8f;
		// }
	}

	private void updateTrap() {
		if (trap != null) {
			if (trapTimer < trap.lifeTime) {
				trapTimer++;
				if (trap.effect != null /* && trap.effect.isComplete() */) {
					trap.position = null;
				}
			} else {
				trap.position = null;
				trapTimer = 0;
			}
		}
	}

	@Override
	public float getDx() {
		return playerDx;
	}

	@Override
	public float getDy() {
		return playerDy;
	}

	public void setDx(float playerDx) {
		this.playerDx = playerDx;
	}

	public void setDy(float playerDy) {
		this.playerDy = playerDy;
	}
}
