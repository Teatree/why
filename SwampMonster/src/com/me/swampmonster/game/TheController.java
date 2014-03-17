package com.me.swampmonster.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.GUI.GUI;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.Enemy;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.utils.CameraHelper;

public class TheController extends InputAdapter{
	public CameraHelper cameraHelper;  
	public L1Renderer l1Renderer; //I am bad, and shouldn't be here
	public CollisionHelper collisionHandler;
	public L1 level1;
	public GUI gui;
	public Vector3 touchPos;
	int timer;
	int timer2;
	public Vector2 point;
	public Vector3 V3point;
	public Vector3 V3playerPos;
	public Vector2 randVector2;
	Random randomGenerator = new Random();
	public Projectile projectile;
	
	//temp
	public boolean restart;
	public boolean NalreadyPressed = false;
	// temp
	
	public TiledMapTileLayer collisionLayer;
	
	public TheController(){
		init();
	}
	
	// INIT METHOD! 
	public void init(){
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		randVector2 = new Vector2();
		level1 = new L1();
//		Pathfinder.setTiledMap(level1.getBunker().getMap());
		level1.getPlayer().setPosition(new Vector2 (180f,380f));
		level1.getPlayer().getSprite().setSize(level1.getPlayer().getSprite().getWidth()/2, level1.getPlayer().getSprite().getHeight()/2);
		
		//:TODO change 
		level1.getEnemies().get(0).setPosition(new Vector2 (110f,100f));
		level1.getEnemies().get(0).getSprite().setSize(level1.getEnemies().get(0).getSprite().getWidth()/2, level1.getEnemies().get(0).getSprite().getHeight()/2);
		
		level1.getEnemies().get(1).setPosition(new Vector2 (80f,70f));
		level1.getEnemies().get(1).getSprite().setSize(level1.getEnemies().get(0).getSprite().getWidth()/2, level1.getEnemies().get(0).getSprite().getHeight()/2);
		
		level1.getPlayer().setHurt(false);
		
		collisionHandler = new CollisionHelper();
		collisionLayer = (TiledMapTileLayer) level1.getBunker().getMap().getLayers().get(0);
		
		gui = new GUI();
		gui.getCroshair().setPosition(new Vector2 (330f,100f));
		
		touchPos = new Vector3(level1.getPlayer().getPosition().x+10, level1.getPlayer().getPosition().y, 0);
		point = new Vector2();
		V3point = new Vector3();
		V3playerPos = new Vector3();
		
	}
	
	public void update(float deltaTime){
		restarter();
		cameraHelper.upadate(V3playerPos.x, V3playerPos.y, 5);
		level1.update(gui.getCroshair().isAiming(), touchPos, V3point, collisionLayer, projectile, cameraHelper);
		
		// I don't fucking know if thsi is better, I just spent 2 hours on this solution, so deal with it!
		if(Gdx.input.justTouched() && !level1.getPlayer().isJustSpawned()){
			inputNav();
		}
		
		if(level1.getPlayer().shooting && level1.getPlayer().getTimeShooting() < 2){
			projectile = new Projectile(new Vector2(level1.getPlayer().getPosition().x, level1.getPlayer().getPosition().y), getRotation());
			projectile.setPosition(new Vector2(level1.getPlayer().getPosition().x, level1.getPlayer().getPosition().y));
		}
		
		gui.update(level1.getPlayer(), point);
		handleDebugInput(deltaTime);
		pathfindingStuff();
		//could probably be in the right class
		painLogic();
		//just saying
		point.x = Gdx.input.getX();
		point.y = 480-Gdx.input.getY();
		
		V3point.x = Gdx.input.getX();
		V3point.y = Gdx.input.getY();
		l1Renderer.getCam().unproject(V3point);
		V3point.z = 0;
		
		V3playerPos.x = level1.getPlayer().getPosition().x + level1.getPlayer().getCircle().radius/2;
		V3playerPos.y = level1.getPlayer().getPosition().y + level1.getPlayer().getCircle().radius/2;
		V3playerPos.z = 0;
		
				
//		l1Renderer.getCam().position.x = level1.getPlayer().getPosition().x;
//		l1Renderer.getCam().position.y = level1.getPlayer().getPosition().y;
		
		// This bit is responsible for calculating where exactly the projective has to go when shot.
		float direction_x = level1.getPlayer().getShotDir().x - V3playerPos.x;
		float direction_y = level1.getPlayer().getShotDir().y - V3playerPos.y;
		
		float length =(float) Math.sqrt(direction_x*direction_x + direction_y*direction_y);
		direction_x /= length;
		direction_y /= length;
		
		if(projectile != null){
			projectile.update(level1.getPlayer().getShotDir(), direction_x, direction_y);
		}
		// It gives the actual direction to the projective as a parameter.
	}

	private void inputNav() {
		if(!level1.getPlayer().getState().equals(State.DEAD)){
			if(!doesIntersect(gui.getWeaponizer().getPosition(), gui.getWeaponizer().getCircle().radius)){
				touchPos.y = Gdx.input.getY();
				touchPos.x = Gdx.input.getX();
				l1Renderer.getCam().unproject(touchPos);
				touchPos.z = 0;
			}else if(Intersector.intersectSegmentCircle(point, point, gui.getWeaponizer().getPosition(), gui.getWeaponizer().getCircle().radius*gui.getWeaponizer().getCircle().radius) == true){
	//			System.out.println("yes it intersects");
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
			for (Enemy enemy : level1.getEnemies())
				enemy.setState(State.DEAD);
		}
		if (Gdx.input.isKeyPressed(Keys.O) && Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)){
			level1.getPlayer().setState(State.ANIMATINGLARGE);
			level1.getPlayer().setDoing("pullingGunOut");
		}
		if (Gdx.input.isKeyPressed(Keys.X) && Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)){
		// property in the setShakeAmt is supposed to be SHAKE_INTENCITY, but it's not, deal with it!
			cameraHelper.setShakeAmt(45);
			cameraHelper.cameraShake();
		}
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 50;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.Q))
		cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.E)) cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.F)) cameraHelper.setZoom(1);
		if (Gdx.input.isKeyPressed(Keys.N) && !NalreadyPressed){
			level1.getPlayer().setHurt(true);
			timer2=80; // Remember that this one is supposed to be the same as the pending time of hurt state animation
			System.out.println("Health: " + level1.getPlayer().getHealth());
			NalreadyPressed = true;
		}else if(!Gdx.input.isKeyPressed(Keys.N)){
			NalreadyPressed = false;
		}
		if (Gdx.input.isKeyPressed(Keys.B)){
			decreaseOxygen();
			System.out.println("Oxygen: " + level1.getPlayer().getOxygen());
		}
		
		if(gui.getWeaponizer().isOn() && level1.getPlayer().getState() == State.STANDARD){
			level1.getPlayer().setDoing("pullingGunOut");
			level1.getPlayer().setState(State.ANIMATINGLARGE);
		}else if(!gui.getWeaponizer().isOn() && level1.getPlayer().getState() == State.GUNMOVEMENT){
			level1.getPlayer().setState(State.ANIMATINGLARGE);
			level1.getPlayer().setDoing("puttingGunAway");
		}
		
	}
	
	private float getRotation(){
		double angle1 = Math.atan2(V3playerPos.y - level1.getPlayer().getPosition().y,
				V3playerPos.x - 0);
		double angle2 = Math.atan2(V3playerPos.y - level1.getPlayer().getShotDir().y,
				V3playerPos.x - level1.getPlayer().getShotDir().x);
		return (float)(angle2-angle1);
	}
	
	private void pathfindingStuff(){
		
//		if(level1.getEnemy().getoRangeAura().overlaps(level1.getPlayer().getCircle()) && level1.getPlayer().getState() != State.DEAD){
//			level1.getEnemy().setState(State.STANDARD);
//		}else if(level1.getEnemy().getgReenAura().overlaps(level1.getPlayer().getCircle()) && level1.getPlayer().getState() != State.DEAD){
//			level1.getEnemy().setCunter(0);
//			level1.getEnemy().setState(State.PURSUIT);
//		}else{
//			level1.getEnemy().setState(State.STANDARD);
//		}
		
//		if(level1.getEnemy().getPosition().x == level1.getEnemy().getOldPos().x && level1.getEnemy().getPosition().y == level1.getEnemy().getOldPos().y 
//				&& level1.getEnemy().getState() == State.STANDARD){
//			findRandomPos();
//		}
	}

//	private void findRandomPos() {
//		timer++;
//		if(timer > 70){
//			int x = (int) (level1.getEnemy().getgReenAura().x - (level1.getEnemy().getgReenAura().radius/2));
//			int x1 = (int) (level1.getEnemy().getgReenAura().x + (level1.getEnemy().getgReenAura().radius/2));
//			int Rx = randomGenerator.nextInt(x1 - x) + x;
//			if(Rx > 0 && Rx < 800){
////				System.out.println("(pathfinderStuff()) getting the random number -X- ");
//				randVector2.x = Rx;
//			}
//			int y = (int) (level1.getEnemy().getgReenAura().y - (level1.getEnemy().getgReenAura().radius/2));
//			int y1 = (int) (level1.getEnemy().getgReenAura().y + (level1.getEnemy().getgReenAura().radius/2));
//			int Ry = randomGenerator.nextInt(y1 - y) + y;
//			if(Ry > 0 && Ry < 480){
////				System.out.println("(pathfinderStuff()) getting the random number -Y- ");
//				randVector2.y = Ry;
//			}
////			System.out.println("randVector2 is: " + randVector2.x + " : " + randVector2.y);
////			if(randVector2){
//				pathfinder.findPath(level1.getEnemy().getPosition(), randVector2);
////			}
//			level1.getEnemy().setCunter(pathfinder.findLastNotNullInArray());
//			timer = 0;
//		}
//	}
	
	private void painLogic() {
		if(timer2 > 0){
			if(timer2 == 80){
				level1.getPlayer().setDamageType("lackOfOxygen");
				hurt();
			}
			timer2--;
		}else if(timer2 == 0 && level1.getPlayer().isHurt()){
		}
		
		if(level1.getPlayer().getOxygen() <= 0 && !level1.getPlayer().isHurt()){
			timer2 = 80;
		}
		
		if(level1.getPlayer().getHealth() <= 0){
			level1.getPlayer().setState(State.DEAD);
		}
		if(level1.getPlayer().getState() == State.DEAD){
			for (Enemy enemy : level1.getEnemies())
				enemy.setState(State.STANDARD);
		}
		
	}
	
	private void restarter(){
		if(level1.getPlayer().getState() == State.DEAD && Gdx.input.justTouched() && doesIntersect(new Vector2(400, 140), 60)){
			init();
			level1.getPlayer().setJustSpawned(true);
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
			System.out.println("Game world resetted");
			}
		if (keycode == Keys.ENTER && cameraHelper.hasTarget) {
			cameraHelper.hasTarget = false;
			System.out.println(cameraHelper.hasTarget + " " + level1.getPlayer().getSprite().getOriginX());
		}
		return false;
	}
	
	public void hurt(){
		level1.getPlayer().setState(State.STANDARD);
		if(level1.getPlayer().getHealth()>=0){
			level1.getPlayer().setHealth(level1.getPlayer().getHealth() - 1);
		}
	}
	private void decreaseOxygen() {
		if(level1.getPlayer().getOxygen()>=0){
			level1.getPlayer().setOxygen(level1.getPlayer().getOxygen() - 1);
		}
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
	public boolean doesIntersect(Vector2 v2, float radius){
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

