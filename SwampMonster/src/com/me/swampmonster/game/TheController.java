package com.me.swampmonster.game;

import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.GUI.GUI;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Enemy;
import com.me.swampmonster.models.Item;
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
	int timer2;
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
//		Pathfinder.setTiledMap(level1.getBunker().getMap());
		level1.getPlayer().setPosition(new Vector2 (756f,659f));
		level1.getPlayer().getSprite().setSize(level1.getPlayer().getSprite().getWidth()/2, level1.getPlayer().getSprite().getHeight()/2);
		level1.getPlayer().setHurt(false);
		
		collisionHandler = new CollisionHelper();
		collisionLayer = (TiledMapTileLayer) level1.getBunker().getMap().getLayers().get(0);
		
		gui = new GUI();
		gui.getCroshair().setPosition(new Vector2 (330f,100f));
		
		touchPos = new Vector3(level1.getPlayer().getPosition().x+10, level1.getPlayer().getPosition().y, 0);
		point = new Vector2();
		V3point = new Vector3();
		V3playerPos = new Vector3();
		
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
	
	public void update(float deltaTime, Game game){
		restarter();
		
		pointRectV3.x = touchPos.x;
		pointRectV3.y = touchPos.y;
		
		pointRect.x = point.x;
		pointRect.y = point.y;
		
		cameraHelper.upadate(V3playerPos.x, V3playerPos.y, 5);
		level1.update(gui.getCroshair().isAiming(), touchPos, V3point, collisionLayer, level1.getPlayer().projectiles, cameraHelper, dx, dy);
		
		Iterator<Enemy> itr = level1.enemiesOnStage.iterator();
		int i = 0;
		while(itr.hasNext()){
			Enemy e = (Enemy) itr.next();
			i++;
			if(!e.isDead()){
				Iterator<Projectile> prj = level1.getPlayer().projectiles.iterator();
				while(prj.hasNext()){
//					System.out.println("enemies on Stage: " + level1.enemiesOnStage.size());
					Projectile p = (Projectile) prj.next();
					if(Intersector.overlaps(p.getCircle(), e.getRectanlge())){
						System.out.println("enemy number: " + i);
							prj.remove();
							break;
						}
					}
			}
			if(e.isDead()){
//				System.out.println("must work!");
				if(e.timeRemove < 180){
					e.timeRemove++;
				}else if(e.timeRemove > 179){
					itr.remove();
					e.timeRemove = 0;
				}
			}
		}
		Iterator<Item> itm = level1.items.iterator();
		while(itm.hasNext()){
			Item item = itm.next();
			if(item.deadAnimTimer==33){
				itm.remove();
			}
			if(Intersector.overlaps(item.getCircle(), level1.getPlayer().getRectanlge())){
				if(item.itemType=="hp" && level1.getPlayer().getHealth() < level1.getPlayer().getMaxHealth()){
					level1.getPlayer().setHealth(level1.getPlayer().getHealth()+1);
					itm.remove();
				}else if(item.itemType == "O2" && level1.getPlayer().oxygen < level1.getPlayer().maxOxygen){
					level1.getPlayer().oxygen = level1.getPlayer().oxygen+50;
					itm.remove();
				}
			}
		}
		
		// PROJECTILE COLLISION DETECTION
		for (Enemy e : level1.enemiesOnStage) {
			for (Projectile p : e.enemyProjectiles) {
				if (p.getCircle().overlaps(level1.getPlayer().aimingArea) && !level1.getPlayer().isHurt()) {
					cameraHelper.setShakeAmt(25);
					cameraHelper.cameraShake();

					level1.getPlayer().setHurt(true);
					level1.getPlayer().setDamageType("enemy");
					level1.getPlayer().setHealth(
							level1.getPlayer().getHealth() - e.getDamage());
				}
			}
		}
		
		// I don't fucking know if thsi is better, I just spent 2 hours on this solution, so deal with it!
		if(Gdx.input.justTouched() && !level1.getPlayer().isJustSpawned()){
			inputNav();
		}
		
		
		gui.update(level1.getPlayer(), point, V3point);
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
		
		//:TODO pff
		if(Intersector.overlaps(debugRect, pointRect)){
			SlotMachineScreen sl = new SlotMachineScreen(game);
			sl.player = level1.getPlayer();
			game.setScreen(sl);
			
		}
				
//		l1Renderer.getCam().position.x = level1.getPlayer().getPosition().x;
//		l1Renderer.getCam().position.y = level1.getPlayer().getPosition().y;
		
		// This bit is responsible for calculating where exactly the projective has to go when shot.
		dx = touchPos.x - V3playerPos.x;
		dy = touchPos.y - V3playerPos.y;
		
		float length1 = (float) Math.sqrt(dx*dx + dy*dy);
		dx /= length1;
		dy /= length1;
		
		//:TODO Make sure this works, Dmitriy
		//:TODO This is some weird shit, if you activate this,
		//:TODO it will actually launch every other time,
		//:TODO and other times give a null pointer, couldn't 
		//:TODO trace it, good luck to you, love past me.
//			for(Enemy enemy : level1.enemiesOnStage){
//				if(enemy!=null && level1.getPlayer() != null && enemy.cunter >= 0 && enemy.path[enemy.cunter] != null){
//					if (level1.getPlayer().getPosition().x > (enemy.path[enemy.cunter].x*16+level1.getPlayer().getSprite().getWidth()*3)){
//						System.out.println("enemy: " + enemy + " player has steped out of the last node on the path area on the RIGHT");
//						enemy.setState(State.STANDARD);
//					}
//					if (level1.getPlayer().getPosition().x < (enemy.path[enemy.cunter].x*16-level1.getPlayer().getSprite().getWidth()*3)){
//						System.out.println("enemy: " + enemy + " player has steped out of the last node on the path area on the LEFT");
//						enemy.setState(State.STANDARD);
//					}
//					if (level1.getPlayer().getPosition().y < (enemy.path[enemy.cunter].y*16-level1.getPlayer().getSprite().getWidth()*3)){
//						System.out.println("enemy: " + enemy + " player has steped out of the last node on the path area on the BOTTOM");
//						enemy.setState(State.STANDARD);
//					}
//					if (level1.getPlayer().getPosition().y > (enemy.path[enemy.cunter].y*16+level1.getPlayer().getSprite().getWidth()*3)){
//						System.out.println("enemy: " + enemy + " player has steped out of the last node on the path area on the TOP");
//						enemy.setState(State.STANDARD);
//					}
//				}
//			}
		
		//
		
		
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
			for (Enemy enemy : level1.enemiesOnStage)
				enemy.setState(State.DEAD);
		}
		if (Gdx.input.isKeyPressed(Keys.X) && Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)){
		// property in the setShakeAmt is supposed to be SHAKE_INTENCITY, but it's not, deal with it!
			cameraHelper.setShakeAmt(45);
			cameraHelper.cameraShake();
		}
		// Camera Controls (zoom)
		float camZoomSpeed = 0.1f * deltaTime;
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
			System.out.println("Oxygen: " + level1.getPlayer().oxygen);
		}
		
//		if(gui.getWeaponizer().isOn() && level1.getPlayer().getState() == State.STANDARD){
//			level1.getPlayer().setDoing("pullingGunOut");
//			level1.getPlayer().setState(State.ANIMATINGLARGE);
//		}else if(!gui.getWeaponizer().isOn() && level1.getPlayer().getState() == State.GUNMOVEMENT){
//			level1.getPlayer().setState(State.ANIMATINGLARGE);
//			level1.getPlayer().setDoing("puttingGunAway");
//		}
		
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
		
		if(level1.getPlayer().oxygen <= 0 && !level1.getPlayer().isHurt()){
			timer2 = 80;
		}
		
		if(level1.getPlayer().getHealth() <= 0){
			level1.getPlayer().setState(State.DEAD);
		}
		if(level1.getPlayer().getState() == State.DEAD){
			for (Enemy enemy : level1.enemiesOnStage)
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
		
		if (keycode == Keys.X){
			for (Enemy e : level1.enemiesOnStage){
//				System.out.print("Enemy " + e.getClass().getSimpleName());
//				System.out.println(" state = " + e.getState());
//				System.out.print(" path = " + e.getPath());
//				System.out.print(" cunter = " + e.getCunter());
//				System.out.print(" timereskin = " + e.getTimereskin());
//				System.out.println(" switcerland = " + e.isSwitzerland());
			}
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
		if(level1.getPlayer().oxygen>=0){
			level1.getPlayer().oxygen = level1.getPlayer().oxygen - 1;
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

