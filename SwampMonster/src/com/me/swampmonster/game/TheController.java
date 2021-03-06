package com.me.swampmonster.game;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.GUI.GUI;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.TutorialLevel;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.slots.PositiveEffectInterface;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.screens.AbstractGameScreen;
import com.me.swampmonster.screens.MenuScreen;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.LGenerator;
import com.me.swampmonster.utils.ScreenContainer;
import com.me.swampmonster.utils.SlotsGenerator;


public class TheController extends InputAdapter {
	public CameraHelper cameraHelper;
	public L1Renderer l1Renderer; // I am bad, and shouldn't be here
	public CollisionHelper collisionHandler;
	public static L1 level1;
	public static GUI gui;
	public Explosion explosion;
	public static Vector3 touchPos;
	int timer;
	int timer3hurt;
	public static int coolDownCounter;
	public static float coolDownStep;
	public static float coolDownAngle;
	public Vector2 point;
	public Vector3 V3point;
	public Vector3 V3playerPos;
	public Vector3 V3enemyPos;
	public Vector2 randVector2;
	
	public static int savedScore;

	public static HashMap<Integer, Sprite> unlockNotifications;
	static{
		unlockNotifications = new HashMap<Integer, Sprite>();
		fillNotifications();	
	}
	public Sprite unlockNotificationSprite;

	float dx;
	float dy;

	// temp
	public boolean restart;
	public boolean NalreadyPressed = false;
	public Rectangle pointRectV3;
	public Rectangle pointRect;
	// temp

	public static TiledMapTileLayer collisionLayer;
	public static Slot skill;
	public static boolean germany;
	public static boolean showFeedback;
	public static boolean showWeaponInv;
	public static boolean gotoMenu;
	public static boolean paused;
	public static boolean pausedTutorial;

	public TheController(Game game, Player player) {
		init(player);
	}

	// INIT METHOD!
	public void init(Player player) {
		Gdx.input.setInputProcessor(this);
		if(MenuScreen.lessBytes == 1 && !MenuScreen.showTutorialButton){
//			level1 = LGenerator.createTutorialLevel();
			level1 = LGenerator.createLevel(player);
		}else if (MenuScreen.lessBytes == 1 && MenuScreen.showTutorialButton){
			level1 = LGenerator.createLevel(player);
			Player.shootingSwitch = true;
		}
		if (MenuScreen.lessBytes == 2){
			level1 = LGenerator.createTutorialLevel();
//			level1 = LGenerator.createLevel(player);
		}
		
		
		cameraHelper = new CameraHelper();
		gui = new GUI(player);
		gui.getCroshair().setPosition(new Vector2(330f, 100f));

//		touchPos = new Vector3(player.getPosition().x + 10,
//				player.getPosition().y, 0);
		point = new Vector2();
		V3point = new Vector3();
		V3playerPos = new Vector3();

		timer3hurt = 0;

		// debug feature!!!

		pointRectV3 = new Rectangle();
		pointRectV3.width = 1;
		pointRectV3.height = 1;

		pointRect = new Rectangle();
		pointRect.width = 1;
		pointRect.height = 1;
		paused = false;
	}

	public void update(float deltaTime, Game game) {
		pointRectV3.x = touchPos.x;
		pointRectV3.y = touchPos.y;
		
//		System.out.println("deltatime: " + Gdx.graphics.getFramesPerSecond());
//		System.err.println("toughtPos: " + touchPos);
		
		pointRect.x = point.x;
		pointRect.y = point.y;

		cameraHelper.upadate(V3playerPos.x, V3playerPos.y, 5);

		if(!paused && !pausedTutorial){
			level1.update(gui.getCroshair().isAiming(), touchPos, V3point,
					collisionLayer, cameraHelper, dx, dy);
		}
		if(TutorialLevel.animating){
			TutorialLevel.animating();
		}
		
		if(!L1.explosions.isEmpty()){
			cameraHelper.setShakeAmt(25);
		}
		if(L1.explosions.isEmpty()){
			cameraHelper.cameraShake();
		}

		// I don't fucking know if thsi is better, I just spent 2 hours on this
		// solution, so deal with it!
		if (Gdx.input.isTouched() && !L1.player.justSpawned) {
			inputNav();
		}

		gui.update(L1.player, point, V3point);
		handleDebugInput(deltaTime);
		point.x = Gdx.input.getX();
		point.y = Gdx.input.getY();

		V3point.x = Gdx.input.getX();
		V3point.y = Gdx.input.getY();
		l1Renderer.getCam().unproject(V3point);
		V3point.z = 0;

		V3playerPos.x = L1.player.getPosition().x
				+ L1.player.circle.radius / 2;
		V3playerPos.y = L1.player.getPosition().y
				+ L1.player.circle.radius / 2;
		V3playerPos.z = 0;
		
		if (germany) {
			AbstractGameScreen sl;
			if (germany && L1.player.state == State.DEAD){
				sl = ScreenContainer.SS;
				gui = new GUI(L1.player);
//				reloadLevel(L1.player);
			} else {
				gui = new GUI(L1.player);
				ScreenContainer.SMS.yesWasJustPressed = false;
				SlotMachineTextures.peru = false;
				sl = ScreenContainer.SMS;
			}
			sl.player = L1.player;
			germany = false;
			paused = false;
			((Game) Gdx.app.getApplicationListener()).setScreen(sl);
		} 
		
		// This bit is responsible for calculating where exactly the projective
		// has to go when shot.
		dx = touchPos.x - V3playerPos.x;
		dy = touchPos.y - V3playerPos.y;

		float length1 = (float) Math.sqrt(dx * dx + dy * dy);
		dx /= length1;
		dy /= length1;
		
		L1.player.setDx(dx);
		L1.player.setDy(dy);
		if (coolDownCounter > 0 && !paused && !pausedTutorial) {
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

			if (e != null && e.getKey() <= Player.absoluteScore) {
//				System.out.println("in da if");
				unlockNotificationSprite = e.getValue();
				GShape.unlockNotificationCounter = 240;
				itr.remove();
			}
		}
	}

//	private void projectileCollisionDetection() {
//		for (Enemy e : L1.enemiesOnStage) {
//			for (Projectile p : e.enemyProjectiles) {
//				if (p.circle.overlaps(L1.player.circle)
//						&& !L1.player.hurt
//						&& L1.player.state != State.DEAD
//						&& L1.player.positiveEffectsState != PositiveEffects.FADE) {
//					cameraHelper.setShakeAmt(25);
//					cameraHelper.cameraShake();
//					System.out.println("YES, GOT HIUT BY PROJECTILE!");
////					L1.player.damage_dx = L1.player.position.x - e.position.x;
////					L1.player.damage_dy = L1.player.position.y - e.position.y;
////					float length1 = (float) Math.sqrt(L1.player.damage_dx * L1.player.damage_dx + L1.player.damage_dy * L1.player.damage_dy);
////					L1.player.damage_dx /= length1;
////					L1.player.damage_dy /= length1;
////					L1.player.damagePushForce = e.damage;
//					L1.player.hurt = true;
//					L1.player.damageType = "enemy";
//					L1.player.health -= e.damage;
//				}
//			}
//		}
//	}

	private void inputNav() {
		Vector2 pint = new Vector2();
		pint.x = Gdx.input.getX();
		pint.y = Gdx.input.getY();
		pint = L1Renderer.stage.screenToStageCoordinates(pint);
		if (!L1.player.state.equals(State.DEAD) && !paused && !pausedTutorial ) {
			
			if (GShape.weaponizer == null || (!doesIntersect(pint, GShape.weaponizer.position,
					GShape.weaponizer.circle.radius))) {
//			if (GShape.weaponizer == null || (GShape.weaponizer.sprite.getBoundingRectangle().contains(pint))) {
				boolean updateTouchPos = true;
				for(Item i: L1.items){
					if(i.pickUpButton != null && i.pickUpButton.isPressed()){
						updateTouchPos = false;
					}
					if(i.throwButton != null && i.throwButton.isPressed()){
						updateTouchPos = false;
					}
				}
				if(updateTouchPos){
					touchPos.y = Gdx.input.getY();
					touchPos.x = Gdx.input.getX();
					L1Renderer.getCam().unproject(touchPos);
					touchPos.z = 0;
					L1.player.pointGathered = true;
				}
			} else if (Intersector.intersectSegmentCircle(pint, pint, GShape.weaponizer.position,
										GShape.weaponizer.circle.radius
												* GShape.weaponizer.circle.radius)) {
//				 } else if (GShape.weaponizer != null && GShape.weaponizer.sprite.getBoundingRectangle().contains(pint)){
				if (skill != null && coolDownCounter == 0) {
					skill.execute(L1.player);
					coolDownAngle = 360;
					coolDownStep = 360f / skill.coolDown;
					coolDownCounter = skill.coolDown;
					if (skill instanceof PositiveEffectInterface) {
						L1.player.positiveEffectSprite = skill.sprite;
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
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			L1.player.setNegativeEffect(NegativeEffects.STUN);
		}
		if (Gdx.input.isKeyPressed(Keys.P)) {
			L1.player.state = State.DEAD;
		}
		if (Gdx.input.isKeyPressed(Keys.X)
				&& Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)) {
			cameraHelper.setShakeAmt(45);
			cameraHelper.cameraShake();
		}
		// Camera Controls (zoom)
		float camZoomSpeed = 0.1f * deltaTime;
		float camZoomSpeedAccelerationFactor = 50;

		if (pausedTutorial && Gdx.input.justTouched() && !TutorialLevel.animating && TutorialLevel.step != 13){
//			touchPos.x = L1.player.position.x +5;
//			touchPos.y = L1.player.position.y +5;
			TutorialLevel.step++;
			pausedTutorial = false;
		}if(pausedTutorial && Gdx.input.justTouched() && TutorialLevel.step == 14){
			SlotMachineScreen.tutorial = true;
			((Game) Gdx.app.getApplicationListener()).setScreen(ScreenContainer.SMS);
		}
		
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)){
			paused = true;
			System.out.println("isKeyPressed(Keys.BACK) " + Gdx.input.isKeyPressed(Keys.BACK));
			System.out.println("isKeyPressed(Keys.ESCAPE) " + Gdx.input.isKeyPressed(Keys.ESCAPE));
		} 
		
		// Pos effects
		if (Gdx.input.isKeyPressed(Keys.T))
			L1.player.setPositiveEffect(PositiveEffects.NONE);
		if (Gdx.input.isKeyPressed(Keys.Y))
			L1.player.setPositiveEffect(PositiveEffects.FADE);
		if (Gdx.input.isKeyPressed(Keys.U))
			L1.player.setPositiveEffect(PositiveEffects.RADIOACTIVE_AURA);
		if (Gdx.input.isKeyPressed(Keys.I))
			L1.player.setPositiveEffect(PositiveEffects.SPEED_BOOST);
		// Neg effects
		if (Gdx.input.isKeyPressed(Keys.G))
			L1.player.setNegativeEffect(NegativeEffects.NONE);
		if (Gdx.input.isKeyPressed(Keys.H))
			L1.player.setNegativeEffect(NegativeEffects.FEAR);
		if (Gdx.input.isKeyPressed(Keys.J))
			L1.player.setNegativeEffect(NegativeEffects.FROZEN);
		if (Gdx.input.isKeyPressed(Keys.K))
			L1.player.turret.state = State.DEAD;

		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.Q))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.E))
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.F))
			cameraHelper.setZoom(1);
		if (Gdx.input.isKeyPressed(Keys.L) && Gdx.input.justTouched()) {
			explosion = new Explosion(new Vector2(pointRectV3.x, pointRectV3.y), Explosion.EXPLOSION_TYPE_STANDART);
//			explosion.type = Explosion.EXPLOSION_TYPE_INVERTED;
			explosion.incrementalDamageValue = 0;
			explosion.incrementalCircleValue = 6;
//			explosion.explCircle.setPosition(new Vector2(pointRectV3.x,
//					pointRectV3.y));
//			explosion.explCircle.radius = 1f;

//			explosion.explosionEffect = new ParticleEffect();
//			explosion.explosionEffect.load(
//					Gdx.files.local("effects/explosionEffect.p"),
//					Gdx.files.local("effects"));
//			explosion.explosionEffect.setPosition(pointRectV3.x, pointRectV3.y);
//			explosion.explosionEffect.start();
			L1.explosions.add(explosion);
		}
		if (Gdx.input.isKeyPressed(Keys.N) && !NalreadyPressed) {
			L1.player.hurt = true;
			L1.player.timer2 = 80; // Remember that this one is supposed to
										// be the same as the pending time of
										// hurt state animation
			NalreadyPressed = true;
		} else if (!Gdx.input.isKeyPressed(Keys.N)) {
			NalreadyPressed = false;
		}
		if (Gdx.input.isKeyPressed(Keys.B)) {
			L1.player.decreaseOxygen();
		}
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.R) {
			init(L1.player);
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

	public static void fillNotifications() {
		for(Class<? extends Slot> s : SlotsGenerator.slots.values()){ 
			Slot slot;
			try {
				slot = s.newInstance();
				if(slot.unlockScore>L1.player.absoluteScore){
					unlockNotifications.put(slot.unlockScore, slot.sprite);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	// TODO You also gotta change stuff here in orde to load tutorial level, bro!
	public static void reloadLevel(Player player) {
		gui = new GUI(L1.player);
		System.out.println("reload level");
		if(MenuScreen.lessBytes == 1 && !MenuScreen.showTutorialButton){
//			level1 = LGenerator.createTutorialLevel();
			level1 = LGenerator.createLevel(player);
		}else if (MenuScreen.lessBytes == 1 && MenuScreen.showTutorialButton){
			level1 = LGenerator.createLevel(player);
		}
		if (MenuScreen.lessBytes == 2){
//			level1 = LGenerator.createTutorialLevel();
			level1 = LGenerator.createLevel(player);
		}
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
