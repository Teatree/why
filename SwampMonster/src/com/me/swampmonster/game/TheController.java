package com.me.swampmonster.game;

import sun.misc.Cleaner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.AI.Pathfinder;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.utils.CameraHelper;

public class TheController extends InputAdapter{
	public CameraHelper cameraHelper;  
	public L1Renderer l1Renderer; //I am bad, and shouldn't be here
	public CollisionHelper collisionHandler;
	public L1 level1;
	public Vector3 touchPos;
	
	public Pathfinder pathfinder;
	public TiledMapTileLayer collisionLayer;
	
	// Temporary debug feature
	public Vector2 tempTargetPos;
	// Temporary debug feature
	
	public TheController(){
		init();
	}
	
	public void update(float deltaTime){
		cameraHelper.upadate(deltaTime);
		level1.update();
		handleDebugInput(deltaTime);
		
	}
	
	public void init(){
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		level1 = new L1();
		pathfinder = new Pathfinder(level1.getBunker().getMap());
		level1.getPlayer().setTheController(this);
		level1.getPlayer().setPosition(new Vector2 (30f,100f));
		level1.getPlayer().getSprite().setSize(level1.getPlayer().getSprite().getWidth()/2, level1.getPlayer().getSprite().getHeight()/2);
		level1.getEnemy().setTheController(this);
		level1.getEnemy().setPosition(new Vector2 (110f,100f));
		level1.getEnemy().getSprite().setSize(level1.getEnemy().getSprite().getWidth()/2, level1.getEnemy().getSprite().getHeight()/2);
		
		collisionHandler = new CollisionHelper();
		touchPos = new Vector3(100f, 100f, 0);
		collisionLayer = (TiledMapTileLayer) level1.getBunker().getMap().getLayers().get(0);
		
		tempTargetPos = new Vector2();
		tempTargetPos.x = 160f;
		tempTargetPos.y = 240f;
		
//		pathfinder.findPath(levelvel1.getEnemy().getPosition(), tempTargetPos);
		pathfinder.findPath(level1.getEnemy().getPosition(), level1.getPlayer().getPosition());
	}

	private void handleDebugInput(float deltaTime) {
		
		float camMoveSpeed = 50 * deltaTime;
		float camMoveSpeedAccelerationFactor = 10;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.A)) moveCamera(-camMoveSpeed,0);
		if (Gdx.input.isKeyPressed(Keys.D)) moveCamera(camMoveSpeed,0);
		if (Gdx.input.isKeyPressed(Keys.W)) moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.S)) moveCamera(0,-camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.O)) level1.getPlayer().setState(State.ANIMATING);
		if (Gdx.input.isKeyPressed(Keys.I)) level1.getPlayer().setState(State.STANDARD);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
		cameraHelper.setPosition(0, 0);
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 50;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.Q))
		cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.E)) cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.F)) cameraHelper.setZoom(1);
		if (Gdx.input.isKeyPressed(Keys.K)){ 
			level1.getEnemy().setCunter(0);
			pathfinder.findPath(level1.getEnemy().getPosition(), level1.getPlayer().getPosition());
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
		if (keycode == Keys.ENTER && !cameraHelper.hasTarget()) {
			cameraHelper.setTarget(level1.getPlayer().getSprite());
			System.out.println(cameraHelper.hasTarget() + " " + level1.getPlayer().getSprite().getOriginX());
		}
		return false;
	}
}

