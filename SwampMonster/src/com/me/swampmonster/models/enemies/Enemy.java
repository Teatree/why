package com.me.swampmonster.models.enemies;

import java.awt.peer.SystemTrayPeer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.AI.Node;
import com.me.swampmonster.AI.Pathfinder;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.utils.AssetsMainManager;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.EnemyGenerator.Toughness;
import com.badlogic.gdx.math.Intersector;

public class Enemy extends AbstractGameObject implements Cloneable, Collidable {

	public int cunter;
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

	public Node[] path;

	public NegativeEffects negativeEffect;
	private int negativeEffectTimer;
//	private float FROZEN_MOVEMENT;
	private int timerPoisoned;
private int timer3hurt;
	
	
	public Enemy(Vector2 position) {
		this.position = position;
		rectanlge = new Rectangle();
		gReenAura = new Circle();
		gReenAura.radius = 164;
		oRangeAura = new Circle();
		oRangeAura.radius = 16;
		yellowAura = new Circle();
		yellowAura.radius = 8;
		state = State.STANDARD;
		animationsStandard.put(State.PURSUIT, new AnimationControl(
				AssetsMainManager.manager.get(AssetsMainManager.enemy), 8, 16, 8));
		animationsStandard.put(State.STANDARD, new AnimationControl(
				AssetsMainManager.manager.get(AssetsMainManager.enemy), 8, 16, 8));
		animationsStandard.put(State.ATTACKING, new AnimationControl(
				AssetsMainManager.manager.get(AssetsMainManager.enemy), 8, 16, 8));
		animationsStandard.put(State.ANIMATING, new AnimationControl(
				AssetsMainManager.manager.get(AssetsMainManager.enemy), 8, 16, 8));
		animationsStandard.put(State.DEAD, new AnimationControl(
				AssetsMainManager.manager.get(AssetsMainManager.enemy), 8, 16, 4));
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

	public void update(TiledMapTileLayer collisionLayer,
			List<Projectile> projectiles, Player player,
			CameraHelper cameraHelper, List<Enemy> enemies) {
		
		iAmWaiting = souldIWait(enemies);
		oldPos.x = position.x;
		oldPos.y = position.y;

		gReenAura.x = position.x;
		gReenAura.y = position.y;
		oRangeAura.x = position.x + sprite.getWidth() / 2;
		oRangeAura.y = position.y + sprite.getHeight() / 2;
		yellowAura.x = position.x + sprite.getWidth() / 2;
		yellowAura.y = position.y + sprite.getHeight() / 2;

		rectanlge.x = sprite.getX();
		rectanlge.y = sprite.getY();
		rectanlge.width = sprite.getWidth();
		rectanlge.height = sprite.getHeight();

		// Direction for the standard state
		enemyDx = player.getPosition().x - position.x;
		enemyDy = player.getPosition().y - position.y;

		float enemyLength = (float) Math.sqrt(enemyDx * enemyDx + enemyDy
				* enemyDy);
		enemyDx /= enemyLength;
		enemyDy /= enemyLength;

		// // System.out.println("currentlyMovingOnPath " + currentlyMovingOnPath);

		// Direction for the pursuit state
		if (cunter != -1 && path != null && path[cunter] != null) {
			enemyPathDx = path[cunter].x * 16 - position.x;
			enemyPathDy = path[cunter].y * 16 - position.y;

			float enemyPathLength = (float) Math.sqrt(enemyPathDx * enemyPathDx
					+ enemyPathDy * enemyPathDy);
			enemyPathDx /= enemyPathLength;
			enemyPathDy /= enemyPathLength;
		}
		
		if (player.radioactiveAura != null && 
				Intersector.overlaps(player.radioactiveAura, rectanlge)){
			hurt = true;
			damageType = "player";
			enemyHurt(player);
		}
		
		if (projectiles != null)
		for (Projectile projectile : projectiles) {
			if (projectile != null  
					&& Intersector.overlaps(projectile.circle, rectanlge)
					&& !hurt) {
				hurt = true;
				damageType = "player";
				enemyHurt(player);
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
					// System.out.println("it's not null");
					// System.out.println("pathX: " + path[0].x * 16 + "pathY:"
//							+ path[0].y * 16);
					// System.out.println("playerX: " + player.position.x
//							+ "playerY:" + player.position.y);
					if (player.position.x > path[0].x * 16 + 120
							|| player.position.x < path[0].x * 16 - 120
							|| player.position.y > path[0].y * 16 + 120
							|| player.position.y < path[0].y * 16 - 120) {
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
		if (state.equals(State.STANDARD)) {
			sprite.setRegion(animations.get(state).getCurrentFrame());
			if (!hurt) {
				if (timer == 0 && timer2 == 0
						&& !yellowAura.overlaps(player.circle)
						&& player.state != State.DEAD) {

					Collidable collidableLeft = null;
					Collidable collidableRight = null;
					Collidable collidableDown = null;
					Collidable collidableUp = null;

					move(player, collidableLeft, collidableRight,
							collidableDown, collidableUp, enemyDx, enemyDy,
							movementSpeed, enemies);
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

				if (oldPos.x != position.x || oldPos.y != position.y
						&& timer > 0 && timer2 > 0) {
					// // System.out.println("yes!1");
					timer = 0;
					timer2 = 0;
				}

				if (yellowAura.overlaps(player.circle)
						&& player.state != State.DEAD) {
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
		}
		// ANIMATING
		if (state.equals(State.ANIMATING)) {

		}
		// DEAD
		if (state.equals(State.DEAD)) {
			if (timeDead < 65) {
				currentFrame = animations.get(state).doComplexAnimation(96, 2f,
						0.03f, Animation.NORMAL);

				sprite.setRegion(animations.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				timeDead++;
			}
			if (timeDead == 1) {
				rewardPlayer(player);
				// "points: " + player.points);
			}
			if (timeDead > 64) {
				dead = true;
			}
		}
		if (hurt) {
			if (projectiles != null) {
				getProjectileLocationRelativeToSprite(projectiles);
			}
			// // System.out.println(" (PLAYER): I'm currently in HURT state");
			if (time < 40) {
				time++;
				// // System.out.println("projectileLocationRelativeTo: " +
				// projectileLocation);

				if (damageType == "player" && state != State.DEAD) {
					// private void takingDamageFromEnemy(HashMap<State,
					// AnimationControl> animations, AbstractGameObject enemy,
					// Vector3 touchPos, TiledMapTileLayer collisionLayer) {
					// // System.out.println(enemy.getPlayerMovementDirection());
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
			Projectile p = (Projectile) prj.next();
			if (p != null){
				p.movementSpeed = 1f;
				if (p.isCollision(collisionLayer) || Intersector.overlaps(p.circle, player.aimingArea)) {
					prj.remove();
					break;
				}
				p.update();
			}
		}
		
		if (negativeEffectsState == NegativeEffects.POISONED){
			poisoning();
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
		player.points += this.points;

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
				sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				timer++;
				if (timer == 30 && timer2 >= attackSpeed) {
					currentFrame = animationsStandard.get(state).animate(
							standing);
					// And may be inflict different hurts, direction/ kinds of
					// hurts/ etc.
					if (oRangeAura.overlaps(player.circle) && !player.hurt && 
							player.positiveEffectsState != PositiveEffects.FADE) {
						player.setDamageType("enemy");
						player.harmfulEnemy = this;
						player.hurt = true;
						player.health-= damage;
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

	private Collidable collisionCheckerTop(TiledMapTileLayer collisionLayer,
			List<Enemy> enemies) {
		Collidable collidable;
		collidable = CollisionHelper.isCollidable(
				position.x + sprite.getWidth(),
				(position.y + sprite.getHeight() / 2) - 1, collisionLayer);
		if (collidable == null)
			collidable = CollisionHelper.isCollidable(position.x,
					(position.y + sprite.getHeight() / 2) - 1, collisionLayer);
		if (collidable == null)
			collidable = CollisionHelper.isCollidable(
					position.x + (sprite.getWidth() / 2),
					(position.y + sprite.getHeight() / 2) - 1, collisionLayer);
		// if(collidable == null)collidable =
		// CollisionHelper.isCollidableEnemy(this, enemies);
		return collidable;
	}

	private Collidable collisionCheckerBottom(TiledMapTileLayer collisionLayer,
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

	private Collidable collisionCheckerRight(TiledMapTileLayer collisionLayer,
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

	private Collidable collisionCheckerLeft(TiledMapTileLayer collisionLayer,
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

	private void move(AbstractGameObject player, Collidable collidableLeft,
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
					// // System.out.println("enemyDx*playerMovementSpeed: " +
					// enemyDx*playerMovementSpeed + " position.x: " +
					// position.x);
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

	private void collisionCheck(Collidable collidable,
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
				if (position.x > (path[cunter].x * 16) - 4
						|| position.x < (path[cunter].x * 16) - 10
						|| position.y > (path[cunter].y * 16) - 4
						|| position.y < (path[cunter].y * 16) - 10) {

					if (collidableLeft == null || collidableRight == null) {
						position.x += enemyPathDx * playerMovementSpeed;
					}
					if (collidableUp == null || collidableDown == null) {
						position.y += enemyPathDy * playerMovementSpeed;
					}
					sprite.translateX(-playerMovementSpeed);
				}
				if (position.x > (path[cunter].x * 16) - 10) {
					playerMovementDirectionLR = "left";
				}
				if (position.x < (path[cunter].x * 16) - 10) {
					playerMovementDirectionLR = "right";
				}
				if (position.y > (path[cunter].x * 16) - 10) {
					playerMovementDirectionUD = "down";
				}
				if (position.y < (path[cunter].x * 16) - 10) {
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
				&& position.x >= (path[cunter].x * 16) - 1
				&& position.x <= (path[cunter].x * 16) + 1
				&& position.y <= (path[cunter].y * 16) + 1
				&& position.y >= (path[cunter].y * 16) - 1) {
			path[cunter] = null;
			// // System.out.println("taking of one Node from the path of Nodes, there was: "
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
//			// System.out.println("stoped: " + timereskin);
		} else if (timereskin > secs - 1) {
			iAmWaiting = false;
			timereskin = 0;
		}
	}

	private void contact(Collidable collidable,
			TiledMapTileLayer collisionLayer, AbstractGameObject player) {
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
		// // System.out.println(theController.level1.getPlayer().position.x);
		state = State.PURSUIT;
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
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);

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
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			position.x += movementSpeed / 3;
			sprite.translateY(movementSpeed / 3);
		}
	}

	private void damageFromBottom(Collidable collidableUp,
			HashMap<State, AnimationControl> animationsStandard) {
		if (projectileLocation == "bottom" && collidableUp == null) {
			// // System.out.println("supposed to be animating... Bottom");
			currentFrame = animationsStandard.get(State.STANDARD)
					.doComplexAnimation(104, 0.6f,
							Gdx.graphics.getDeltaTime() / 2, Animation.NORMAL);

			sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
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
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);

			position.y -= movementSpeed / 3;
			sprite.translateY(movementSpeed / 3);
		}
	}

	public void enemyHurt(Player player) {
		state = State.STANDARD;
		if (health >= 0) {
			if (player.positiveEffectsState == PositiveEffects.RADIOACTIVE_AURA){
				health -= player.radioactiveDamage;
			}
			health -= player.damage;
		}
	}

	// temporary look

	public Vector2 getOldPos() {
		return oldPos;
	}

	public void setOldPos(Vector2 oldPos) {
		this.oldPos = oldPos;
	}

	public String getPlayerMovementDirection() {
		return playerMovementDirectionLR;
	}

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

	public int getCunter() {
		return cunter;
	}

	public void setCunter(int cunter) {
		this.cunter = cunter;
	}

	public Circle getgReenAura() {
		return gReenAura;
	}

	public void setgReenAura(Circle gReenAura) {
		this.gReenAura = gReenAura;
	}

	public Circle getoRangeAura() {
		return oRangeAura;
	}

	public void setoRangeAura(Circle oRangeAura) {
		this.oRangeAura = oRangeAura;
	}

	public Node[] getPath() {
		return path;
	}

	public void setPath(Node[] path) {
		this.path = path;
	}

	public void setColour(float red, float green, float blue, float alpha) {
		sprite.setColor(red, green, blue, alpha);
	}

	public int getTimereskin() {
		return timereskin;
	}

	public void setTimereskin(int timereskin) {
		this.timereskin = timereskin;
	}

	public void doCollide(AbstractGameObject abstractGameObject,
			TiledMapTileLayer collisionLayer) {

	}

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
	
	private boolean souldIWait(List <Enemy> enemies){
		for (Enemy e : enemies){
			if (e != this && Intersector.overlaps(e.rectanlge, this.rectanlge) && !e.iAmWaiting){
				this.iAmWaiting = true;
				return true;
			}
		}
		return false;
	}
	
	public void setNegativeEffect(NegativeEffects negativeEffect) {
		switch (negativeEffect) {
		case FROZEN:
			this.sprite.setColor(4 / 255f, 180 / 255f, 1f, 1f);
			movementSpeed = movementSpeed - 0.3f;
			negativeEffectsState = negativeEffect;
			negativeEffectTimer = negativeEffect.lifetime;
			break;
		case POISONED:
			poisoning();
			movementSpeed = STANDART_MOVEMENT_SPEED;
			negativeEffectsState = negativeEffect;
			negativeEffectTimer = negativeEffect.lifetime;
			break;
		case NONE:
			sprite.setColor(1, 1, 1, 1);
			movementSpeed = STANDART_MOVEMENT_SPEED;
			negativeEffectsState = negativeEffect;
		}
	}
	
	private void poisoning() {
		damageType = "Poisoned";
		if (timerPoisoned == 0) {
			this.sprite.setColor(Color.GREEN);
		}
		if (timerPoisoned < 50) {
			timerPoisoned++;
			if (health > 1) {
				hurt();
			}
		}
		if (timerPoisoned == 50) {
			timerPoisoned = 0;
		}
	}
	
	public void hurt() {
		if (state != State.DEAD) {
			if (timer3hurt < 50) {
				timer3hurt++;
				if (timer3hurt == 25) {
					sprite.setColor(sprite.getColor().r, sprite.getColor().g - 1,
							sprite.getColor().b - 1, sprite.getColor().a);
				}
				if (timer3hurt == 45) {
					sprite.setColor(sprite.getColor().r, sprite.getColor().g + 1,
							sprite.getColor().b + 1, sprite.getColor().a);
				}
			}
			if (timer3hurt == 50) {
				health--;
				timer3hurt = 0;
			}
		}
	}
}