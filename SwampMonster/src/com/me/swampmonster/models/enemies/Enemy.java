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
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.Prop;
import com.me.swampmonster.models.ToxicPuddle;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.models.slots.RADIOACTIVE;
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
	int timer;
	public int time;
	int timeDead = 0;
	int timer2;
	int timereskin = 0;
	public int timeRemove = 0;
	public String projectileLocation;
	public List<Projectile> enemyProjectiles;
	public Toughness toughness;

	float enemyDx;
	float enemyDy;

	float enemyPathDx;
	float enemyPathDy;

	boolean iAmWaiting = false;
	boolean currentlyMovingOnPath = false;
	boolean attackSequenceStarted = false;

	public Circle gReenAura;
	public Circle oRangeAura;
	public Circle yellowAura;
	public Circle aimingAura;
	public Rectangle aimerBot;

	public Node[] path;

	private int negativeEffectTimer;
	private int timerPoisoned;

	Random random = new Random();
	int generateNewRandomPosForScared;
	public int negativeEffectCounter;

	public Enemy(Vector2 position) {
		this.position = position;
		rectanlge = new Rectangle();
		gReenAura = new Circle();
		gReenAura.radius = 164;
		oRangeAura = new Circle();
		oRangeAura.radius = 16;
		yellowAura = new Circle();
		yellowAura.radius = 8;
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
		oldPos = position;
		// Timer is for the length of the actual animation
		// Timer2 is for the waiting period
		timer = 0;
		timer2 = 0;
		path = new Node[99];

		enemyProjectiles = new LinkedList<Projectile>();

		// ***Character stats board, probably need to delete this***
		characterStatsBoard();
		// ***Character stats board, probably need to delete this***

		sprite = new Sprite(animationsStandard.get(State.STANDARD)
				.getCurrentFrame());

	}

	public void characterStatsBoard() {
		// HEALTH, DAMAGE, TYPE, TOUGHGUY, COLORSCHEME, ETC.
		health = 2;
		damage = 1;
		points = 0;
		attackSpeed = 40;
		movementSpeed = 0.3f;
	}

	public void update(TiledMapTileLayer collisionLayer, Player player,
			CameraHelper cameraHelper, List<Enemy> enemies) {

		iAmWaiting = souldIWait(enemies);
		oldPos.x = position.x;
		oldPos.y = position.y;

		gReenAura.x = position.x;
		gReenAura.y = position.y;
		aimingAura.x = position.x + sprite.getWidth() / 2;
		aimingAura.y = position.y + sprite.getHeight() / 2;
		oRangeAura.x = position.x + sprite.getWidth() / 2;
		oRangeAura.y = position.y + sprite.getHeight() / 2;
		yellowAura.x = position.x + sprite.getWidth() / 2;
		yellowAura.y = position.y + sprite.getHeight() / 2;

		rectanlge.x = sprite.getX();
		rectanlge.y = sprite.getY();
		rectanlge.width = sprite.getWidth();
		rectanlge.height = sprite.getHeight();

//		if (negativeEffectCounter <= 0) {
//			setNegativeEffect(NegativeEffects.NONE);
//		} else {
//			negativeEffectCounter--;
//		}
		// Direction for the standard state
		if (negativeEffectsState != NegativeEffects.FEAR) {
			enemyDx = player.position.x - position.x;
			enemyDy = player.position.y - position.y;
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
				&& Intersector.overlaps(player.radioactiveAura, rectanlge)) {
			hurt = true;
			damageType = "player";
			enemyHurt(player);
		}

		if (player.projectiles != null)
			for (Projectile projectile : player.projectiles) {
				if (projectile != null
						&& Intersector.overlaps(projectile.circle, rectanlge)
						&& !hurt) {
					hurt = true;
					damageType = "player";
					enemyHurt(player);
					if (projectile.effect == EffectCarriers.POISONED) {
						this.setNegativeEffect(NegativeEffects.POISONED);
					}
				}
			}

		if (health <= 0) {
			Player.enemiesKilled++;
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
		if (!hurt) {
			if (state.equals(State.PURSUIT)) {

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

				if (path[0] != null) {
					if (player.position.x > path[0].x * Constants.NodeSize
							+ 120
							|| player.position.x < path[0].x
									* Constants.NodeSize - 120
							|| player.position.y > path[0].y
									* Constants.NodeSize + 120
							|| player.position.y < path[0].y
									* Constants.NodeSize - 120) {
						System.out
								.println("You are officially outside the last seen zone!");
						state = State.STANDARD;
						for (int i = 0; i < path.length; i++) {
							path[i] = null;
						}
					}
				}
			}
		}

		// THIS IS STANDARD!
		if (state.equals(State.STANDARD) && !aiming) {
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
			if (!hurt) {
				if (timer == 0 && timer2 == 0
						&& !yellowAura.overlaps(player.circle)
						&& player.state != State.DEAD) {

					collidableLeft = null;
					collidableRight = null;
					collidableDown = null;
					collidableUp = null;

					if (!preparingToCharge && !charging && !waiting) {
						move(player, collidableLeft, collidableRight,
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
		}
		// ANIMATING
		if (state.equals(State.ANIMATING)) {

		}

		if (negativeEffectsState != NegativeEffects.NONE
				&& negativeEffectTimer > 0) {
			if (negativeEffectsState == NegativeEffects.POISONED) {
				sprite.setColor(Color.GREEN);
				if (timerPoisoned == 45) {
					health -= 0.03f;
					timerPoisoned = 0;
				} else {
					timerPoisoned++;
				}
			}
			negativeEffectTimer--;
		} else {
			setNegativeEffect(NegativeEffects.NONE);
		}

		// DEAD
		if (state.equals(State.DEAD)) {
			if (timeDead < 65) {
				currentFrame = animations.get(state).doComplexAnimation(96, 2f,
						0.03f, Animation.NORMAL);

				sprite.setRegion(animations.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
				timeDead++;
			}
			if (timeDead == 1) {
				rewardPlayer(player);
				// "points: " + Player.score);
			}
			if (timeDead > 64) {
				dead = true;
			}
		}
		if (hurt && !exploding && !charging) {
			if (player.projectiles != null) {
				getProjectileLocationRelativeToSprite(player.projectiles);
			}
			if (time < 40) {
				time++;
				if (damageType == "player" && state != State.DEAD) {
					Collidable collidableUp = null;

					damagedFromTop(collidableUp, animationsStandard);
					collidableUp = collisionCheckerTop(collisionLayer, enemies);
					collisionCheck(collidableUp, collisionLayer, player);

					Collidable collidableDown = null;

					damageFromBottom(collidableDown, animationsStandard);
					collidableDown = collisionCheckerBottom(collisionLayer,
							enemies);
					collisionCheck(collidableDown, collisionLayer, player);

					Collidable collidableLeft = null;

					damageFromLeft(collidableLeft, animationsStandard);
					collidableLeft = collisionCheckerLeft(collisionLayer,
							enemies);
					collisionCheck(collidableLeft, collisionLayer, player);

					Collidable collidableRight = null;

					damageFromRight(collidableRight, animationsStandard);
					collidableRight = collisionCheckerRight(collisionLayer,
							enemies);
					collisionCheck(collidableRight, collisionLayer, player);
				}

			} else if (time > 39) {
				hurt = false;
				time = 0;
			}
		}
		Iterator<Projectile> prj = enemyProjectiles.iterator();
		while (prj.hasNext()) {
			Projectile p = prj.next();
			if (p != null) {
				p.movementSpeed = 1f;
				if (p.isCollision(collisionLayer)
						|| Intersector.overlaps(p.circle, player.aimingArea)) {
					prj.remove();
					break;
				}
				p.update();
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
		Player.score += this.points;

	}

	protected float getRotation(Player player) {
		double angle1 = Math.atan2(position.y
				- (position.y + oRangeAura.radius / 2), position.x - 0);
		double angle2 = Math.atan2(position.y - player.position.y, position.x
				- player.position.x);
		return (float) (angle2 - angle1);
	}

	protected void inflictOnThe(int standing, int animation, Player player,
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
				if (oRangeAura.overlaps(player.circle)) {
					cameraHelper.setShakeAmt(25);
					cameraHelper.cameraShake();
				}
				// // System.out.println("timer1: " + timer);
				currentFrame = animationsStandard.get(state)
						.doComplexAnimation(animation, 1.8f,
								Gdx.graphics.getDeltaTime(), Animation.NORMAL);

				sprite.setRegion(animationsStandard.get(state)
						.getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);
				timer++;
				if (timer == 30 && timer2 >= attackSpeed) {
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
						player.health -= damage;
					}

					timer = 0;
					timer2 = 0;
					attackSequenceStarted = false;
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

	public void move(AbstractGameObject player, Collidable collidableLeft,
			Collidable collidableRight, Collidable collidableDown,
			Collidable collidableUp, float enemyDx, float enemyDy,
			float playerMovementSpeed, List<Enemy> enemies) {
		if (!iAmWaiting) {
			if (position.x > player.getPosition().x - 4
					|| position.x < player.getPosition().x - 10
					|| position.y > player.getPosition().y - 4
					|| position.y < player.getPosition().y - 10) {
				// // System.out.println("yes it is !");
				if (collidableLeft == null || collidableRight == null) {
					position.x += enemyDx * playerMovementSpeed;
				}
				if (collidableUp == null || collidableDown == null) {
					position.y += enemyDy * playerMovementSpeed;
				}
				sprite.translateX(-playerMovementSpeed);
			}

			if (position.x > player.getPosition().x - 10) {
				playerMovementDirectionLR = "left";
			}
			if (position.x < player.getPosition().x - 10) {
				playerMovementDirectionLR = "right";
			}
			if (position.y > player.getPosition().y - 10) {
				playerMovementDirectionUD = "down";
			}
			if (position.y < player.getPosition().y - 10) {
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
					System.out.println("I realy should be moving on path right about now");

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
							Gdx.graphics.getDeltaTime() / 2, Animation.NORMAL);
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
							Gdx.graphics.getDeltaTime() / 2, Animation.NORMAL);

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
							Gdx.graphics.getDeltaTime() / 2, Animation.NORMAL);

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
							Gdx.graphics.getDeltaTime() / 2, Animation.NORMAL);

			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 32, 32);

			position.y -= movementSpeed / 3;
			sprite.translateY(movementSpeed / 3);
		}
	}

	public void enemyHurt(Player player) {
		state = State.STANDARD;
		if (health >= 0) {
			if (player.positiveEffectsState == PositiveEffects.RADIOACTIVE_AURA) {
				health -= RADIOACTIVE.RADIOACTIVE_Damage;
			}
			health -= player.damage;
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
			if (e != this && Intersector.overlaps(e.rectanlge, this.rectanlge)
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
				movementSpeed *= 0.4f;
				negativeEffectsState = negativeEffect;
				negativeEffectTimer = negativeEffect.lifetime;
			}
			break;
		case POISONED:
			if (negativeEffectsState != NegativeEffects.POISONED) {
				movementSpeed = STANDART_MOVEMENT_SPEED;
				negativeEffectsState = NegativeEffects.POISONED;
				negativeEffectTimer = negativeEffect.lifetime;
			}
			break;
		case FEAR:
			sprite.setColor(0.6f, 0.1f, 0.9f, 1);
			path = new Node[99];
			negativeEffectsState = negativeEffect;
			negativeEffectTimer = negativeEffect.lifetime;
			System.out.println("negativeEffectCounter" + negativeEffectCounter);
			break;
		case NONE:
			sprite.setColor(1, 1, 1, 1);
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