package com.me.swampmonster.game;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.GUI.GUI;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.PositiveEffectInterface;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.screens.AbstractGameScreen;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.screens.SwampScreen;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.LGenerator;

public class TheController extends InputAdapter {
	public CameraHelper cameraHelper;
	public L1Renderer l1Renderer; // I am bad, and shouldn't be here
	public CollisionHelper collisionHandler;
	public static L1 level1;
	public GUI gui;
	public Explosion explosion;
	public static Vector3 touchPos;
	int timer;
	int timer3hurt;
	int coolDownCounter;
	float coolDownStep;
	float coolDownAngle;
	public Vector2 point;
	public Vector3 V3point;
	public Vector3 V3playerPos;
	public Vector3 V3enemyPos;
	public Vector2 randVector2;

	public HashMap<Integer, Sprite> unlockNotifications;
	public Sprite unlockNotificationSprite;

	float dx;
	float dy;

	// temp
	public boolean restart;
	public boolean NalreadyPressed = false;
	public static Rectangle debugRect;
	public Rectangle pointRectV3;
	public Rectangle pointRect;
	// temp

	public static TiledMapTileLayer collisionLayer;
	public static Slot skill;
	public static boolean germany;
	public static boolean showFeedback;
	
	private static LGenerator levelGenerator;

	public TheController(Game game, Player player) {
		init(player);
	}

	// INIT METHOD!
	public void init(Player player) {
		Gdx.input.setInputProcessor(this);
//		random = new Random();
		levelGenerator = new LGenerator();
		level1 = levelGenerator.createLevel(player);
		cameraHelper = new CameraHelper();
		gui = new GUI(player);
		gui.getCroshair().setPosition(new Vector2(330f, 100f));
		explosion = new Explosion(player.position);

		touchPos = new Vector3(player.getPosition().x + 10,
				player.getPosition().y, 0);
		point = new Vector2();
		V3point = new Vector3();
		V3playerPos = new Vector3();

		//vqwe
		unlockNotifications = new HashMap<Integer, Sprite>();
		fillNotifications();

		timer3hurt = 0;

		// debug feature!!!
		debugRect = new Rectangle();
		debugRect.x = 780;
		debugRect.y = 370;
		debugRect.width = 20;
		debugRect.height = 20;

		pointRectV3 = new Rectangle();
		pointRectV3.width = 1;
		pointRectV3.height = 1;

		pointRect = new Rectangle();
		pointRect.width = 1;
		pointRect.height = 1;
	}

	public void update(float deltaTime, Game game) {
		pointRectV3.x = touchPos.x;
		pointRectV3.y = touchPos.y;

		pointRect.x = point.x;
		pointRect.y = point.y;

		cameraHelper.upadate(V3playerPos.x, V3playerPos.y, 5);

		level1.update(gui.getCroshair().isAiming(), touchPos, V3point,
				collisionLayer, cameraHelper, dx, dy);
		projectileCollisionDetection();

		// I don't fucking know if thsi is better, I just spent 2 hours on this
		// solution, so deal with it!
		if (Gdx.input.justTouched() && !level1.player.justSpawned) {
			inputNav();
		}

		gui.update(level1.player, point, V3point);
		handleDebugInput(deltaTime);
		point.x = Gdx.input.getX();
		point.y = 480 - Gdx.input.getY();

		V3point.x = Gdx.input.getX();
		V3point.y = Gdx.input.getY();
		l1Renderer.getCam().unproject(V3point);
		V3point.z = 0;

		V3playerPos.x = level1.player.getPosition().x
				+ level1.player.circle.radius / 2;
		V3playerPos.y = level1.player.getPosition().y
				+ level1.player.circle.radius / 2;
		V3playerPos.z = 0;
		
		if (Intersector.overlaps(debugRect, pointRect) || germany) {
			AbstractGameScreen sl;
			if (germany && level1.player.state == State.DEAD){
				sl = new SwampScreen(game);
				reloadLevel(level1.player);
			} else {
				sl = new SlotMachineScreen(game);
			}
			sl.player = level1.player;
			germany = false;
			game.setScreen(sl);
		} 
		
		// This bit is responsible for calculating where exactly the projective
		// has to go when shot.
		dx = touchPos.x - V3playerPos.x;
		dy = touchPos.y - V3playerPos.y;

		float length1 = (float) Math.sqrt(dx * dx + dy * dy);
		dx /= length1;
		dy /= length1;
		
		level1.player.setDx(dx);
		level1.player.setDy(dy);
		if (coolDownCounter > 0) {
			coolDownAngle = coolDownAngle - coolDownStep;
			// c -= coolDownStep;
			coolDownCounter--;
		}
		// :TODO Ckeck this out

		Iterator<Entry<Integer, Sprite>> itr = unlockNotifications.entrySet()
				.iterator();

		while (itr.hasNext()) {
			Entry<Integer, Sprite> e = itr.next();
			// System.err.println("notification: " + e);
			// System.err.println("points: " + Player.score);
			// System.err.println("getKey: " + e.getKey());

			if (e != null && e.getKey() <= Player.score) {
//				System.out.println("in da if");
				unlockNotificationSprite = e.getValue();
				GShape.unlockNotificationCounter = 240;
				itr.remove();
			}
		}
	}

	private void projectileCollisionDetection() {
		for (Enemy e : L1.enemiesOnStage) {
			for (Projectile p : e.enemyProjectiles) {
				if (p.circle.overlaps(level1.player.aimingArea)
						&& !level1.player.hurt
						&& level1.player.state != State.DEAD
						&& level1.player.positiveEffectsState != PositiveEffects.FADE) {
					cameraHelper.setShakeAmt(25);
					cameraHelper.cameraShake();

					level1.player.hurt = true;
					level1.player.damageType = "enemy";
					level1.player.health -= e.damage;
				}
			}
		}
	}

	private void inputNav() {
		if (!level1.player.state.equals(State.DEAD)) {
			if (!doesIntersect(point, gui.getWeaponizer().getPosition(),
					gui.getWeaponizer().circle.radius)) {
				touchPos.y = Gdx.input.getY();
				touchPos.x = Gdx.input.getX();
				l1Renderer.getCam().unproject(touchPos);
				touchPos.z = 0;
				level1.player.pointGathered = true;
			} else if (Intersector.intersectSegmentCircle(point, point, gui
					.getWeaponizer().getPosition(),
					gui.getWeaponizer().circle.radius
							* gui.getWeaponizer().circle.radius)) {
				if (skill != null && coolDownCounter == 0) {
					skill.execute(level1.player);
					coolDownAngle = 360;
					coolDownStep = 360f / skill.coolDown;
					coolDownCounter = skill.coolDown;
					if (skill instanceof PositiveEffectInterface) {
						level1.player.positiveEffectSprite = skill.sprite;
					}
				}
			}
		}
	}

	private void handleDebugInput(float deltaTime) {
		float camMoveSpeed = 50 * deltaTime;
		float camMoveSpeedAccelerationFactor = 10;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.A) && !cameraHelper.hasTarget)
			moveCamera(-camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.D) && !cameraHelper.hasTarget)
			moveCamera(camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.W) && !cameraHelper.hasTarget)
			moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.S) && !cameraHelper.hasTarget)
			moveCamera(0, -camMoveSpeed);

		if (Gdx.input.isKeyPressed(Keys.O)) {
			for (Enemy enemy : L1.enemiesOnStage) {
				enemy.state = State.DEAD;
			}
		}
		if (Gdx.input.isKeyPressed(Keys.P)) {
			level1.player.state = State.DEAD;
		}
		if (Gdx.input.isKeyPressed(Keys.X)
				&& Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)) {
			cameraHelper.setShakeAmt(45);
			cameraHelper.cameraShake();
		}
		// Camera Controls (zoom)
		float camZoomSpeed = 0.1f * deltaTime;
		float camZoomSpeedAccelerationFactor = 50;

		// Pos effects
		if (Gdx.input.isKeyPressed(Keys.T))
			level1.player.setPositiveEffect(PositiveEffects.NONE);
		if (Gdx.input.isKeyPressed(Keys.Y))
			level1.player.setPositiveEffect(PositiveEffects.FADE);
		if (Gdx.input.isKeyPressed(Keys.U))
			level1.player.setPositiveEffect(PositiveEffects.RADIOACTIVE_AURA);
		if (Gdx.input.isKeyPressed(Keys.I))
			level1.player.setPositiveEffect(PositiveEffects.SPEED_BOOST);
		// Neg effects
		if (Gdx.input.isKeyPressed(Keys.G))
			level1.player.setNegativeEffect(NegativeEffects.NONE);
		if (Gdx.input.isKeyPressed(Keys.H))
			level1.player.setNegativeEffect(NegativeEffects.FEAR);
		if (Gdx.input.isKeyPressed(Keys.J))
			level1.player.setNegativeEffect(NegativeEffects.FROZEN);
		if (Gdx.input.isKeyPressed(Keys.K))
			level1.player.setNegativeEffect(NegativeEffects.POISONED);

		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.Q))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.E))
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.F))
			cameraHelper.setZoom(1);
		if (Gdx.input.isKeyPressed(Keys.L) && Gdx.input.justTouched()) {
			explosion = new Explosion(new Vector2(pointRectV3.x, pointRectV3.y));
			explosion.explCircle = new Circle();
			explosion.incrementalDamageValue = 0;
			explosion.incrementalCircleValue = 6;
			explosion.explCircle.setPosition(new Vector2(pointRectV3.x,
					pointRectV3.y));
			explosion.explCircle.radius = 1f;

			explosion.explosionEffect = new ParticleEffect();
			explosion.explosionEffect.load(
					Gdx.files.local("effects/explosionEffect.p"),
					Gdx.files.local("effects"));
			explosion.explosionEffect.setPosition(pointRectV3.x, pointRectV3.y);
			explosion.explosionEffect.start();
			L1.explosions.add(explosion);
		}
		if (Gdx.input.isKeyPressed(Keys.N) && !NalreadyPressed) {
			level1.player.hurt = true;
			level1.player.timer2 = 80; // Remember that this one is supposed to
										// be the same as the pending time of
										// hurt state animation
			NalreadyPressed = true;
		} else if (!Gdx.input.isKeyPressed(Keys.N)) {
			NalreadyPressed = false;
		}
		if (Gdx.input.isKeyPressed(Keys.B)) {
			level1.player.decreaseOxygen();
		}
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

	public boolean keyUp(int keycode) {
		if (keycode == Keys.R) {
			init(level1.player);
		}

		if (keycode == Keys.ENTER && cameraHelper.hasTarget) {
			cameraHelper.hasTarget = false;
		}
		return false;
	}

	public int findLastNotNullInArray() {
		int i = 0;
		while (gui.getHealthBar().getHealthBarRect()[i] != null) {
			i++;
		}
		return i - 1;
	}

//	public static Vector2 calculateRandomPlayerPos() {
////		System.err.println("89");
//		Vector2 vector2 = new Vector2();
//		int minPosX = 230;
//		int maxPosX = (int) (collisionLayer.getWidth() * 16
//				- level1.player.sprite.getWidth() - 200);
//		int minPosY = 230;
//		int maxPosY = (int) (collisionLayer.getHeight() * 16
//				- level1.player.sprite.getHeight() - 200);
//
//		vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
//		vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
//		while (vector2.x < 1f || vector2.y < 1f) {
//			vector2.x = random.nextInt(maxPosX - minPosX) + minPosX;
//			vector2.y = random.nextInt(maxPosY - minPosY) + minPosY;
//		}
//		return vector2;
//	}

	// User log:
	// v2 is the position at which the circle is situated
	// radius is the circles radius
	// inside Mr. Point is where the mouse clicks.
	public boolean doesIntersect(Vector2 point, Vector2 v2, float radius) {
		boolean questionMark;
		if (Intersector.intersectSegmentCircle(point, point, v2, radius
				* radius)) {
			questionMark = true;
		} else {
			questionMark = false;
		}
		return questionMark;
	}

	public void fillNotifications() {
		unlockNotifications.put(500,
				new Sprite(Assets.manager.get(Assets.SHADOW_ARROW_ICON)));
		unlockNotifications.put(1000,
				new Sprite(Assets.manager.get(Assets.POISONED_ARROW_ICON)));
		unlockNotifications.put(2000,
				new Sprite(Assets.manager.get(Assets.EXPLOSIVE_ARROW_ICON)));

	}

	public static void reloadLevel(Player player) {
		level1 = levelGenerator.createLevel(player);
	}

	public Vector2 getPoint() {
		return point;
	}

	public void setPoint(Vector2 point) {
		this.point = point;
	}

	public Vector3 getV3point() {
		return V3point;
	}

	public void setV3point(Vector3 v3point) {
		V3point = v3point;
	}

	public Vector3 getV3playerPos() {
		return V3playerPos;
	}

	public void setV3playerPos(Vector3 v3playerPos) {
		V3playerPos = v3playerPos;
	}

}
