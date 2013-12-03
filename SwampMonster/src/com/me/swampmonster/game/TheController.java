package com.me.swampmonster.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BinaryHeap;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.utils.CameraHelper;

public class TheController extends InputAdapter{

	public CameraHelper cameraHelper;  
	public L1Renderer l1Renderer; //I am bad, and shouldn't be here
	public CollisionHelper collisionHandler;
	public L1 level1;
	public Vector3 touchPos;
	
	public Cell start;
	public Cell target;
	public boolean calculated = false;
	public int distance;
	public Cell left, right, down, up;
	public Cell parent;
	public boolean used;
	
	public TiledMapTileLayer collisionLayer;
	
	public TheController(){
		init();
	}
	
	public void update(float deltaTime){
		cameraHelper.upadate(deltaTime);
		level1.update();
		handleDebugInput(deltaTime);
		findPath();
	}
	
	public void init(){
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		level1 = new L1();
		level1.getPlayer().setTheController(this);
		level1.getPlayer().setPosition(new Vector2 (100f,100f));
		level1.getPlayer().getSprite().setSize(level1.getPlayer().getSprite().getWidth()/2, level1.getPlayer().getSprite().getHeight()/2);
		level1.getEnemy().setTheController(this);
		level1.getEnemy().setPosition(new Vector2 (200f,200f));
		level1.getEnemy().getSprite().setSize(level1.getEnemy().getSprite().getWidth()/2, level1.getEnemy().getSprite().getHeight()/2);
		
		collisionHandler = new CollisionHelper();
		touchPos = new Vector3(100f, 100f, 0);
		collisionLayer = (TiledMapTileLayer) level1.getBunker().getMap().getLayers().get(0);
		
	}

	private void handleDebugInput(float deltaTime) {
		
		float camMoveSpeed = 50 * deltaTime;
		float camMoveSpeedAccelerationFactor = 10;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.A)) moveCamera(-camMoveSpeed,0);
		if (Gdx.input.isKeyPressed(Keys.D)) moveCamera(camMoveSpeed,0);
		if (Gdx.input.isKeyPressed(Keys.W)) moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.S)) moveCamera(0,-camMoveSpeed);
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
	
	public Cell getCellAt(int x, int y){
//		System.out.println(x + " : " + y);
		if(x >= 0 && y >= 0 && x < collisionLayer.getWidth() && y < collisionLayer.getHeight())
			return collisionLayer.getCell(x, y);
		return null;
	}
	
	public Cell traceBack(){
//		onPath = true;
		return parent;
	}
	
//	public void calcDistance(Cell target){
//		distance = Math.abs((fx - target.fx)) + Math.abs((fy - target.fy)); 
//	}
	
	
	public void findPath(){
		target = getCellAt((int) level1.getPlayer().getPosition().x/16, (int) level1.getPlayer().getPosition().y/16);
		start = getCellAt((int) level1.getEnemy().getPosition().x/16, (int) level1.getEnemy().getPosition().y/16);
		
		int columns = collisionLayer.getWidth();
		int rows = collisionLayer.getHeight();
		
		System.out.println(collisionLayer.getWidth() + " " +  collisionLayer.getHeight());
		
		boolean calculated = false;
//		if(collisionLayer.getCell() != null){
//		for(int i = 0; i < collisionLayer.getWidth(); i++){
//			for(int j = 0; j < collisionLayer.getHeight(); j++){
//				collisionLayer.getCell(i, j).calcDistance(target);
//			}
//		}
		ArrayList<Cell> closed = new ArrayList<Cell>();
		ArrayList<Cell> open = new ArrayList<Cell>();
		open.add(start);
		while(!calculated){
			for(int i = 0; i < open.size(); i++){
				if(calculated){
					System.out.println(open);
					break;
				}
				Cell s = open.get(i);
				if(up != null && up == target){
					calculated = true;
				}
				else if(down != null && down == target){
					calculated = true;
				}
				else if(left != null && left == target){
					calculated = true;
				}
				else if(right != null && right == target){
					calculated = true;
				}
				else{
					if(up != null && !closed.contains(up) && !CollisionHelper.isCellBlocked(up, collisionLayer)){
						parent = up;
						open.add(up);
						used = true;
					}
						if(down != null && !closed.contains(down) && !CollisionHelper.isCellBlocked(down, collisionLayer)){
						parent = down;
						open.add(down);
						used = true;
					}
						if(left != null && !closed.contains(left) && !CollisionHelper.isCellBlocked(left, collisionLayer)){
						parent = left;
						open.add(left);
						used = true;
					}
						if(right != null && !closed.contains(right) && !!CollisionHelper.isCellBlocked(right, collisionLayer)){
						parent = right;
						open.add(right);
						used = true;
					}
//					closed.add(s);
				}
				
				if(calculated){
					System.out.println("something");
					Cell c = traceBack();
					while(c != null){
						c = traceBack();
					}
				}
			}
		}
	}
}

