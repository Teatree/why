package com.me.swampmonster.models;

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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.models.slots.Trap;
import com.me.swampmonster.utils.AssetsMainManager;

public class Player extends AbstractGameObject {

	private static final float FROZEN_MOVEMENT = 0.16f;
	private static final int RADIOACTIVE_RADIUS = 57;
	private static final float SPEED_BOOST_EFFECT = 1.1f;

	public Sprite positiveEffectSprite;
	int time = 0;
	int timer3hurt = 0;
	int timerPoisoned = 0;
	int timeDead = 0;
	private int timeShooting = 0;
	String nastyaSpriteStandard;
	String nastyaSpriteGun;
	// responsible for what kind of animation are to be played in the Animating
	// State
	String doing;
	// responsible for what kind of animation are to be played in the Animating
	// State, to be changed to something better
	public boolean maskOn;
	public boolean justSpawned;
	public boolean shooting;
	public boolean pointGathered;
	public List<Projectile> projectiles;
	public Vector3 shotDir;
	public Vector3 V3playerPos;
	public Vector3 aimLineHead;
	public float oxygen;
	public float maxOxygen;
	public Integer positiveEffectCounter;
	public Integer negativeEffectCounter;
	private Random random;
	public Rectangle fearRectangle;

	public Circle aimingArea;
	public Circle invalidSpawnArea;

	public Enemy harmfulEnemy;

	public Circle radioactiveAura = null;
	public int radioactiveDamage = 1;
	public int timer2;

	public Trap trap;
	public int trapTimer;

	public Player(Vector2 position) {
		state = State.STANDARD;
		positiveEffectsState = PositiveEffects.NONE;
		negativeEffectsState = NegativeEffects.NONE;
		positiveEffectCounter = new Integer(0);
		negativeEffectCounter = new Integer(0);
		this.position = position;
		movementSpeed = 0.5f;
		random = new Random();
		points = 0;
		hurt = false;
		aimingArea = new Circle();
		aimingArea.radius = 8;
		invalidSpawnArea = new Circle();
		invalidSpawnArea.radius = 90;
		circle = new Circle();
		circle.radius = 16;
		V3playerPos = new Vector3();
		rectanlge = new Rectangle();
		projectiles = new LinkedList<Projectile>();
		aimLineHead = new Vector3();

		animationsStandard.put(State.STANDARD,
						new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.nastyaSpriteStandard),8, 32, 7));
		animationsStandard.put(State.ANIMATING,
						new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.nastyaSpriteStandard),8, 32, 8));
		animationsStandard
				.put(State.ANIMATINGLARGE,
						new AnimationControl(AssetsMainManager.manager
								.get(AssetsMainManager.nastyaSpriteStandard),
								8, 32, 8));
		animationsStandard
				.put(State.ACTIVATING,
						new AnimationControl(AssetsMainManager.manager
								.get(AssetsMainManager.nastyaSpriteStandard),
								8, 32, 8));
		animationsStandard.put(
				State.GUNMOVEMENT,
				new AnimationControl(AssetsMainManager.manager
						.get(AssetsMainManager.nastyaSpriteGun), 8, 16, 7));
		animationsStandard
				.put(State.DEAD,
						new AnimationControl(AssetsMainManager.manager
								.get(AssetsMainManager.nastyaSpriteStandard),
								8, 32, 8));

		oldPos = position;

		dead = false;
		maskOn = true;
		justSpawned = true;
		shooting = false;

		// ***Character stats board***
		characterStatsBoard();
		// ***Character stats board***
		sprite = new Sprite(animationsStandard.get(State.STANDARD)
				.getCurrentFrame());
		sprite.setColor(1, 1, 1, 1);
		shotDir = new Vector3();
		sprite.setSize(sprite.getWidth() / 2, sprite.getHeight() / 2);
		// allowedToShoot = true;
	}

	public void characterStatsBoard() {
		// HEALTH, DAMAGE, OXYGEN, TYPE, TOUGHGUY, COLORSCHEME, ETC.
		maxHealth = 6;
		health = maxHealth;
		maxOxygen = 96;
		oxygen = maxOxygen;
		for(Projectile p : projectiles){
			p.damage = 1f;
		}
		
		// :TODO IN ORDER TO CHANGE THIS, YOU GOT TO GET DOWN TO WHERE SHOOTIGN
		// IS HAPPENING!
		// shotCoolDown = 90;
		movementSpeed = STANDART_MOVEMENT_SPEED;
	}

	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point,
			TiledMapTileLayer collisionLayer, float dx, float dy) {
		oldPos.x = position.x;
		oldPos.y = position.y;

		circle.x = position.x + sprite.getWidth() / 2;
		circle.y = position.y + sprite.getHeight() / 2;

		aimingArea.x = position.x + sprite.getWidth() / 2;
		aimingArea.y = position.y + sprite.getHeight() / 2;
		invalidSpawnArea.x = position.x + 8;
		invalidSpawnArea.y = position.y + 16;

		V3playerPos.x = position.x + circle.radius / 2;
		V3playerPos.y = position.y + circle.radius / 2;
		V3playerPos.z = 0;

		sprite.setX(position.x);
		sprite.setY(position.y);

		rectanlge.x = sprite.getX();
		rectanlge.y = sprite.getY();
		rectanlge.width = sprite.getWidth();
		rectanlge.height = sprite.getHeight();

		oxygen -= 0.05f;

		if (Gdx.input.justTouched()) {
			justSpawned = false;
		}

		painLogic();

		// STANDARD
		if (state.equals(State.STANDARD)) {
			standart(touchPos, collisionLayer, dx, dy);
		}

		// GUN
		if (state.equals(State.GUNMOVEMENT)) {
			gunMovement(aiming, touchPos, V3point);
		}

		// DEAD
		if (state.equals(State.DEAD)) {
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
				if (damageType != "lackOfOxygen" && time > 39) {
					hurt = false;
					time = 0;
				}
			}
		}
		
		//TODO tried to fix when player stuck in shooting
// 		if (!rectanlge.contains(V3point.x, V3point.y))
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
	}

	private void updateProjectiles(TiledMapTileLayer collisionLayer) {
		Iterator<Projectile> prj = projectiles.iterator();
		while (prj.hasNext()) {
			Projectile p = (Projectile) prj.next();
			if (p != null && p.isCollision(collisionLayer)) {
				prj.remove();
				break;
			}
			if (p != null) {
				p.update();
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
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);

			timeShooting++;

		}
		if (shooting && timeShooting < 2) {
			shotDir.x = (position.x+sprite.getWidth()/2)*2 - V3point.x;
			shotDir.y = (position.y+sprite.getHeight()/2)*2 - V3point.y;
		}
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
					- 8), getRotation());

			p.setPosition(new Vector2(aimingArea.x + direction_x / 100 - 8,
					aimingArea.y + direction_y / 100 - 8));

			float length = (float) Math.sqrt(direction_x * direction_x
					+ direction_y * direction_y);
			direction_x /= length;
			direction_y /= length;

			p.setDirection(direction_x, direction_y);

			projectiles.add(p);
		}
	}

	private void dying() {
		if (timeDead < 89) {
			currentFrame = animationsStandard.get(state).doComplexAnimation(
					112, 1.6f, 0.018f, Animation.NORMAL);
			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			timeDead++;
		}
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
		sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);

		if (!hurt) {
			movementCollisionAndAnimation(movementSpeed, touchPos,
					collisionLayer, dx, dy);
		}
	}

	private void gunMovement(boolean aiming, Vector3 touchPos, Vector3 V3point) {
		sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
		sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);

		if (!aiming) {
			currentFrame = animationsStandard.get(state).doComplexAnimation(0,
					0.5f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
		}

		if (aiming) {
			touchPos.x = position.x + 9;
			touchPos.y = position.y + 4;
			aimLineHead.x = (position.x+sprite.getWidth()/2)*2 - V3point.x;
			aimLineHead.y = (position.y+sprite.getHeight()/2)*2 - V3point.y;
			
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
					position.y + sprite.getHeight() / 2, RADIOACTIVE_RADIUS);
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
		case NONE:
			if (negativeEffectsState == NegativeEffects.NONE
					|| negativeEffectsState == null) {
				sprite.setColor(1, 1, 1, 1);
				movementSpeed = STANDART_MOVEMENT_SPEED;
			}
			radioactiveAura = null;
			positiveEffectsState = PositiveEffects.NONE;
		}

	}

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
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
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
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
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
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
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
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);

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

	private Collidable collisionCheckerUp(TiledMapTileLayer collisionLayer) {
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

	private Collidable collisionCheckerDown(TiledMapTileLayer collisionLayer) {
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

	private Collidable collisionCheckerRight(TiledMapTileLayer collisionLayer) {
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

	private Collidable collisionCheckerLeft(TiledMapTileLayer collisionLayer) {
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
//					sprite.setColor(sprite.getColor().r,
//							sprite.getColor().g - 1, sprite.getColor().b - 1,
//							sprite.getColor().a);
					sprite.setColor(Color.RED);
				}
				if (timer3hurt == 45) {
					if (negativeEffectsState == NegativeEffects.FROZEN){
						this.sprite.setColor(4 / 255f, 180 / 255f, 1f, 1f);
					} else if (negativeEffectsState == NegativeEffects.POISONED){
						sprite.setColor(Color.GREEN);
					} else {
						sprite.setColor(1,1,1,1);
					}
//					sprite.setColor(sprite.getColor().r,
//							sprite.getColor().g + 1, sprite.getColor().b + 1,
//							sprite.getColor().a);
				}
			}
			if (timer3hurt == 50) {
				health--;
				timer3hurt = 0;
			}
		}
	}

	private float getRotation() {
		double angle1 = Math.atan2(V3playerPos.y - position.y,
				V3playerPos.x - 0);
		double angle2 = Math.atan2(V3playerPos.y - shotDir.y, V3playerPos.x
				- shotDir.x);
		return (float) (angle2 - angle1);
	}

	private void painLogic() {
		if (timer2 > 0) {
			damageType = "lackOfOxygen";
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
			
//			if (health > 1) {
//				hurt();
//			}
		}
		if (timerPoisoned == 50) {
			timerPoisoned = 0;
			if (health > 1) health--;
		}
	}

	public void decreaseOxygen() {
		if (oxygen >= 0) {
			oxygen--;
		}
	}

	private void updateTrap() {
		if (trap != null) {
			if (trapTimer < trap.lifeTime) {
				trapTimer++;
			} else {
				trap = null;
			}
		}
	}
}
