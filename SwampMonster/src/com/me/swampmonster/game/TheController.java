package com.me.swampmonster.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.GUI.GUI;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.AbstractGameObject.PositiveEffects;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Enemy;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.utils.CameraHelper;

public class TheController extends InputAdapter{
	public CameraHelper cameraHelper;  
	public L1Renderer l1Renderer; //I am bad, and shouldn't be here
	public CollisionHelper collisionHandler;
	public L1 level1;
	public GUI gui;
	public Vector3 touchPos;
	int timer;
	int timer3hurt;
	public Vector2 point;
	public Vector3 V3point;
	public Vector3 V3playerPos;
	public Vector3 V3enemyPos;
	public Vector2 randVector2;
	
	float dx;
	float dy;
	
	float enemyDx;
	float enemyDy;
	
	//temp
	public boolean restart;
	public boolean NalreadyPressed = false;
	public Rectangle debugRect;
	public Rectangle pointRectV3;
	public Rectangle pointRect;
	// temp
	
	public TiledMapTileLayer collisionLayer;
	
	public TheController(Game game){
		init();
	}
	
	// INIT METHOD! 
	public void init(){
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		randVector2 = new Vector2();
		level1 = new L1();
		level1.player.setPosition(new Vector2 (756f,659f));		
		collisionHandler = new CollisionHelper();
		collisionLayer = (TiledMapTileLayer) level1.bunker.getMap().getLayers().get(0);
		gui = new GUI();
		gui.getCroshair().setPosition(new Vector2 (330f,100f));
		
		touchPos = new Vector3(level1.player.getPosition().x+10, level1.player.getPosition().y, 0);
		point = new Vector2();
		V3point = new Vector3();
		V3playerPos = new Vector3();
		
		timer3hurt=0;
		
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
		restarter();

		pointRectV3.x = touchPos.x;
		pointRectV3.y = touchPos.y;

		pointRect.x = point.x;
		pointRect.y = point.y;

		cameraHelper.upadate(V3playerPos.x, V3playerPos.y, 5);
		
		level1.update(gui.getCroshair().isAiming(), touchPos, V3point,
				collisionLayer, level1.player.projectiles, cameraHelper, dx, dy);
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

		V3playerPos.x = level1.player.getPosition().x + level1.player.circle.radius / 2;
		V3playerPos.y = level1.player.getPosition().y + level1.player.circle.radius / 2;
		V3playerPos.z = 0;

		if (Intersector.overlaps(debugRect, pointRect)) {
			SlotMachineScreen sl = new SlotMachineScreen(game);
			sl.player = level1.player;
			game.setScreen(sl);

		}

		// This bit is responsible for calculating where exactly the projective
		// has to go when shot.
		dx = touchPos.x - V3playerPos.x;
		dy = touchPos.y - V3playerPos.y;

		float length1 = (float) Math.sqrt(dx * dx + dy * dy);
		dx /= length1;
		dy /= length1;

	}

	
	private void projectileCollisionDetection() {
		for (Enemy e : level1.enemiesOnStage) {
			for (Projectile p : e.enemyProjectiles) {
				if (p.circle.overlaps(level1.player.aimingArea)
						&& !level1.player.hurt
						&& level1.player.state != State.DEAD
						&& level1.player.positiveEffectsState != PositiveEffects.FADE) {
					cameraHelper.setShakeAmt(25);
					cameraHelper.cameraShake();

					level1.player.hurt = true;
					level1.player.setDamageType("enemy");
					level1.player.health -= e.damage;
				}
			}
		}
	}

	private void inputNav() {
		if(!level1.player.state.equals(State.DEAD)){
			if(!doesIntersect(point, gui.getWeaponizer().getPosition(), gui.getWeaponizer().circle.radius)){
				touchPos.y = Gdx.input.getY();
				touchPos.x = Gdx.input.getX();
				l1Renderer.getCam().unproject(touchPos);
				touchPos.z = 0;
				level1.player.pointGathered = true;
			}else if(Intersector.intersectSegmentCircle(point, point, gui.getWeaponizer().getPosition(), 
					gui.getWeaponizer().circle.radius*gui.getWeaponizer().circle.radius)){
			}
		}
	}
	
	private void handleDebugInput(float deltaTime) {
		float camMoveSpeed = 50 * deltaTime;
		float camMoveSpeedAccelerationFactor = 10;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.A) && !cameraHelper.hasTarget) moveCamera(-camMoveSpeed,0);
		if (Gdx.input.isKeyPressed(Keys.D) && !cameraHelper.hasTarget) moveCamera(camMoveSpeed,0);
		if (Gdx.input.isKeyPressed(Keys.W) && !cameraHelper.hasTarget) moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.S) && !cameraHelper.hasTarget) moveCamera(0,-camMoveSpeed);
		
		if (Gdx.input.isKeyPressed(Keys.O)){
			for (Enemy enemy : level1.enemiesOnStage)
				enemy.state = State.DEAD;
		}
		if (Gdx.input.isKeyPressed(Keys.P)){
			level1.player.state = State.DEAD;
		}
		if (Gdx.input.isKeyPressed(Keys.X) && Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)){
			cameraHelper.setShakeAmt(45);
			cameraHelper.cameraShake();
		}
		// Camera Controls (zoom)
		float camZoomSpeed = 0.1f * deltaTime;
		float camZoomSpeedAccelerationFactor = 50;
		
		//Pos effects
		if (Gdx.input.isKeyPressed(Keys.T)) level1.player.setPositiveEffect(PositiveEffects.NONE);
		if (Gdx.input.isKeyPressed(Keys.Y)) level1.player.setPositiveEffect(PositiveEffects.FADE);
		if (Gdx.input.isKeyPressed(Keys.U)) level1.player.setPositiveEffect(PositiveEffects.RADIOACTIVE_AURA);
		if (Gdx.input.isKeyPressed(Keys.I)) level1.player.setPositiveEffect(PositiveEffects.SPEED_BOOST);
		//Neg effects
		if (Gdx.input.isKeyPressed(Keys.G)) level1.player.setNegativeEffect(NegativeEffects.NONE);
		if (Gdx.input.isKeyPressed(Keys.H)) level1.player.setNegativeEffect(NegativeEffects.FEAR);
		if (Gdx.input.isKeyPressed(Keys.J)) level1.player.setNegativeEffect(NegativeEffects.FROZEN);
		if (Gdx.input.isKeyPressed(Keys.K)) level1.player.setNegativeEffect(NegativeEffects.POISONED);
		
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.Q))
		cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.E)) cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.F)) cameraHelper.setZoom(1);
		if (Gdx.input.isKeyPressed(Keys.N) && !NalreadyPressed){
			level1.player.hurt = true;
			level1.player.timer2=80; // Remember that this one is supposed to be the same as the pending time of hurt state animation
			NalreadyPressed = true;
		}else if(!Gdx.input.isKeyPressed(Keys.N)){
			NalreadyPressed = false;
		}
		if (Gdx.input.isKeyPressed(Keys.B)){
			level1.player.decreaseOxygen();
		}
	}
	
	private void restarter(){
		if(level1.player.state == State.DEAD && Gdx.input.justTouched() && doesIntersect(point, new Vector2(400, 140), 60)){
			init();
			level1.player.justSpawned = true;
		}
	}
	
	private void moveCamera (float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}
	
	public boolean keyUp (int keycode) {
		if (keycode == Keys.R) {
			init();
			}
		
		if (keycode == Keys.ENTER && cameraHelper.hasTarget) {
			cameraHelper.hasTarget = false;
		}
		return false;
	}
	
	public int findLastNotNullInArray(){
		int i = 0;
		while(gui.getHealthBar().getHealthBarRect()[i] != null){
			i++;
		}
		return i - 1;
	}
	
	// User log:
	// v2 is the position at which the circle is situated
	// radius is the circles radius
	// inside Mr. Point is where the mouse clicks.
	public boolean doesIntersect(Vector2 point, Vector2 v2, float radius){
		boolean questionMark;
		if(Intersector.intersectSegmentCircle(point, point, v2, radius*radius)){
			questionMark = true;
		}else{
			questionMark = false;
		}
		return questionMark;
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

