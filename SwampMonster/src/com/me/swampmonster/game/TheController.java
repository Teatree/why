package com.me.swampmonster.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.AI.Pathfinder;
import com.me.swampmonster.GUI.GUI;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.AbstractGameObject.State;
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
	public Vector2 randVector2;
	public Vector2 supportVector2; // maybe not needed here; it's for the enemies to no move large distance to the player from the start
	Random randomGenerator = new Random();
	
	//temp
	public boolean hurt;
	public boolean NalreadyPressed = false;
	// temp
	
	public Pathfinder pathfinder;
	public TiledMapTileLayer collisionLayer;
	
	public TheController(){
		init();
	}
	
	public void update(float deltaTime){
		cameraHelper.upadate(deltaTime);
		level1.update();
		gui.update(level1.getPlayer().getHealth());
		handleDebugInput(deltaTime);
		pathfindingStuff();
		painLogic();
	}

	private void painLogic() {
		if(timer2 > 0){
			if(timer2 == 40){
				hurt();
			}
			hurt = true;
			timer2--;
			System.out.println("Timer2: " + timer2);
		}else if(timer2 == 0 && hurt){
			hurt = false;
		}
		
		if(level1.getPlayer().getOxygen() <= 0 && !hurt){
			timer2 = 40;
		}
		
		if(level1.getPlayer().getHealth() <= 0){
			level1.getPlayer().setState(State.ANIMATING);
		}
	}
	
	public void init(){
		hurt = false;
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		randVector2 = new Vector2();
		level1 = new L1();
		pathfinder = new Pathfinder(level1.getBunker().getMap());
		level1.getPlayer().setTheController(this);
		level1.getPlayer().setPosition(new Vector2 (330f,100f));
		level1.getPlayer().getSprite().setSize(level1.getPlayer().getSprite().getWidth()/2, level1.getPlayer().getSprite().getHeight()/2);
		level1.getEnemy().setTheController(this);
		level1.getEnemy().setPosition(new Vector2 (110f,100f));
		level1.getEnemy().getSprite().setSize(level1.getEnemy().getSprite().getWidth()/2, level1.getEnemy().getSprite().getHeight()/2);
		
		collisionHandler = new CollisionHelper();
		touchPos = new Vector3(level1.getPlayer().getPosition().x+10, level1.getPlayer().getPosition().y, 0);
		collisionLayer = (TiledMapTileLayer) level1.getBunker().getMap().getLayers().get(0);
		
		supportVector2 = new Vector2(level1.getEnemy().getPosition().x+17, level1.getEnemy().getPosition().y+17);
		pathfinder.findPath(level1.getEnemy().getPosition(), supportVector2);
		gui = new GUI();
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
		if (Gdx.input.isKeyPressed(Keys.N) && !NalreadyPressed){
			hurt = true;
			timer2=40; // Remember that this one is supposed to be the same as the pending time of hurt state animation
			System.out.println("Health: " + level1.getPlayer().getHealth());
			NalreadyPressed = true;
		}else if(!Gdx.input.isKeyPressed(Keys.N)){
			NalreadyPressed = false;
		}
		if (Gdx.input.isKeyPressed(Keys.B)){
			decreaseOxygen();
			System.out.println("Oxygen: " + level1.getPlayer().getOxygen());
		}
	}
	

	private void pathfindingStuff(){
		
		if(level1.getEnemy().getoRangeAura().overlaps(level1.getPlayer().getTempCircle())){
			level1.getEnemy().setState(State.ATTACKING);
		}else if(level1.getEnemy().getgReenAura().overlaps(level1.getPlayer().getTempCircle())){
			level1.getEnemy().setCunter(0);
			level1.getEnemy().setState(State.PURSUIT);
			pathfinder.findPath(level1.getEnemy().getPosition(), level1.getPlayer().getPosition());
		}else{
			level1.getEnemy().setState(State.STANDARD);
		}
		
		if(level1.getEnemy().getPosition().x == level1.getEnemy().getOldPos().x && level1.getEnemy().getPosition().y == level1.getEnemy().getOldPos().y){
			timer++;
			if(timer > 350){
				int x = (int) (level1.getEnemy().getgReenAura().x - (level1.getEnemy().getgReenAura().radius/2));
				int x1 = (int) (level1.getEnemy().getgReenAura().x + (level1.getEnemy().getgReenAura().radius/2));
				int Rx = randomGenerator.nextInt(x1 - x) + x;
				if(Rx > 0 && Rx < 800){
					System.out.println("(pathfinderStuff()) getting the random number -X- ");
					randVector2.x = Rx;
				}
				int y = (int) (level1.getEnemy().getgReenAura().y - (level1.getEnemy().getgReenAura().radius/2));
				int y1 = (int) (level1.getEnemy().getgReenAura().y + (level1.getEnemy().getgReenAura().radius/2));
				int Ry = randomGenerator.nextInt(y1 - y) + y;
				if(Ry > 0 && Ry < 480){
					System.out.println("(pathfinderStuff()) getting the random number -Y- ");
					randVector2.y = Ry;
				}
				System.out.println("randVector2 is: " + randVector2.x + " : " + randVector2.y);
			
				pathfinder.findPath(level1.getEnemy().getPosition(), randVector2);
				level1.getEnemy().setCunter(pathfinder.findLastNotNullInArray());
				timer = 0;
			}
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
	
	public void hurt(){
		level1.getPlayer().setState(State.HURT);
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
}

