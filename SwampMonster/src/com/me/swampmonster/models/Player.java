package com.me.swampmonster.models;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.State;

public class Player extends AbstractGameObject{
	
	State state = State.STANDARD;
	int time = 0;
	String nastyaSpriteStandard;
	String nastyaSpriteOxygen;
	boolean maskOn;
	
	public Player(Vector2 position){
		this.position = position;
		nastyaSpriteStandard = "data/NastyaSheet2.png";
		nastyaSpriteOxygen = "data/NastyaOxygenSheet.png";
		
		circle = new Circle();
		circle.radius = 16;
		
		animationsStandard.put(state.STANDARD, new AnimationControl(nastyaSpriteStandard, 8, 32, 7)); 
		animationsStandard.put(state.ANIMATING, new AnimationControl(nastyaSpriteStandard, 8, 32, 8)); 
		animationsStandard.put(state.HURT, new AnimationControl(nastyaSpriteStandard, 8, 32, 8)); 
		animationsStandard.put(state.GUNMOVEMENT, new AnimationControl(nastyaSpriteStandard, 8, 32, 7)); 
		animationsStandard.put(state.DEAD, new AnimationControl(nastyaSpriteStandard, 8, 32, 8)); 
		animationsOxygen.put(state.STANDARD, new AnimationControl(nastyaSpriteOxygen, 8, 32, 7)); 
		animationsOxygen.put(state.ANIMATING, new AnimationControl(nastyaSpriteOxygen, 8, 32, 8)); 
		animationsOxygen.put(state.HURT, new AnimationControl(nastyaSpriteOxygen, 8, 32, 8)); 
		animationsOxygen.put(state.GUNMOVEMENT, new AnimationControl(nastyaSpriteOxygen, 8, 32, 7)); 
		animationsOxygen.put(state.DEAD, new AnimationControl(nastyaSpriteOxygen, 8, 32, 8)); 
		
		oldPos = position;
		
		dead = false;
		maskOn = false;
		health = 6;
		oxygen = 96;
		sprite = new Sprite(animationsStandard.get(state.STANDARD).getCurrentFrame());
	}
	
	  public Vector2 getPosition() {
		   return position;
		  }
	 public void setPosition(Vector2 position) {
		  this.position = position;
	}
	 public Vector2 getOldPos() {
		 return oldPos;
		}
		 public void setOldPos(Vector2 oldPos) {
		   this.oldPos = oldPos;
	 }
	public void update() {
		oldPos.x = position.x;
		oldPos.y = position.y;
		
		circle.x = position.x;
		circle.y = position.y;
		
		sprite.setX(position.x);
		sprite.setY(position.y);
		
		HashMap<State, AnimationControl> animations = new HashMap<AbstractGameObject.State, AnimationControl>();
		
		System.out.println("mask: " + maskOn);
		if(maskOn){
			animations = animationsOxygen;
			oxygen -= 0.01f;
		}else if(!maskOn){
			animations = animationsStandard;
		}
//		System.out.println("player position = " + position.x + " : " + position.y);
//		System.out.println("player position = " + sprite.getX() + " : " + sprite.getY());
		
	//ANIMATING
		if(state.equals(State.ANIMATING)){
//			System.out.println(" (PLAYER): I'm currently in ANIMATING state");
			if(time < 62){
				sprite = new Sprite(animations.get(state.ANIMATING).getCurrentFrame());
				currentFrame = animations.get(state).doComplexAnimation(128, 1f, Gdx.graphics.getDeltaTime());
				
				sprite.setRegion(animations.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				time++;
			}
			else{
				time = 0;
				if(!maskOn){
					setMaskOn(true);
				}else if(maskOn){
					setMaskOn(false);
				}
				state = State.STANDARD;
			}
		}
			
	//HURT
		if(state.equals(State.HURT)){
//			System.out.println(" (PLAYER): I'm currently in HURT state");
			if(time < 30){
				sprite = new Sprite(animations.get(state.HURT).getCurrentFrame());
				
				time++;
				
				Collidable collidableUp = null;
				
				damagedFromTop(collidableUp, animations);
				collidableUp = collisionCheckerUp();
				collisionCheck(collidableUp);
				
				Collidable collidableDown = null;
				
				damageFromBottom(collidableDown, animations);
				collidableDown = collisionCheckerDown();
				collisionCheck(collidableDown);
				
				Collidable collidableLeft = null;
				
				damageFromLeft(collidableLeft, animations);
				collidableLeft = collisionCheckerLeft();
				collisionCheck(collidableLeft);
				
				Collidable collidableRight = null;
				
				damageFromRight(collidableRight, animations);
				collidableRight = collisionCheckerRight();
				collisionCheck(collidableRight);
			}else{
				currentFrame = animations.get(state.HURT).animate(64);
				state = State.STANDARD;
				time = 0;
			}
		}
		
	//STANDARD
		if(state.equals(State.STANDARD)){
//			System.out.println(" (PLAYER): I'm currently in STANDARD state");
			sprite = new Sprite(animations.get(state.STANDARD).getCurrentFrame());
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			
			if (Gdx.input.justTouched()) {
	
		        inputNav();
		    }	
			//movement
			 	movementCollisionAndAnimation(playerMovementSpeed, animations);
		}
		
	//GUN MOVEMENT
		if(state.equals(State.GUNMOVEMENT)){
//			System.out.println(" (PLAYER): I'm currently in GUNMOVEMENT state");
			sprite = new Sprite(animations.get(state.GUNMOVEMENT).getCurrentFrame());
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			
			if (Gdx.input.justTouched()) {

		        inputNav();
		    }	
			
			movementCollisionAndAnimation(playerMovementSpeed/3, animations);
		}
		
	//DEAD
		if(state.equals(State.DEAD)){
//			System.out.println(" (PLAYER): I'm DEAD :(");
			if(time < 108){
				sprite = new Sprite(animations.get(state.ANIMATING).getCurrentFrame());
				currentFrame = animations.get(state).doComplexAnimation(112, 1.8f, Gdx.graphics.getDeltaTime());
				
				sprite.setRegion(animations.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				time++;
			}
			
			dead = true;
		}
		
	}
	private void damageFromRight(Collidable collidableUp, HashMap<State, AnimationControl> animations) {
		if (theController.level1.getEnemy().playerMovementDirection == "right" && collidableUp == null) { 
			currentFrame = animations.get(state.HURT).doComplexAnimation(108, 0.2f, Gdx.graphics.getDeltaTime()/2);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			position.x += playerMovementSpeed/2;
			theController.touchPos.x += playerMovementSpeed/2;
			sprite.translateY(playerMovementSpeed/2);
		}
	}
	private void damageFromLeft(Collidable collidableUp, HashMap<State, AnimationControl> animations) {
		if (theController.level1.getEnemy().playerMovementDirection == "left" && collidableUp == null) { 
			currentFrame = animations.get(state.HURT).doComplexAnimation(106, 0.2f, Gdx.graphics.getDeltaTime()/2);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			position.x -= playerMovementSpeed/2;
			theController.touchPos.x -= playerMovementSpeed/2;
			sprite.translateY(playerMovementSpeed/2);
		}
	}
	private void damageFromBottom(Collidable collidableUp, HashMap<State, AnimationControl> animations) {
		if (theController.level1.getEnemy().playerMovementDirection == "down" && collidableUp == null) { 
			currentFrame = animations.get(state.HURT).doComplexAnimation(110, 0.2f, Gdx.graphics.getDeltaTime()/2);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			position.y -= playerMovementSpeed/2;
			theController.touchPos.y -= playerMovementSpeed/2;
			sprite.translateY(playerMovementSpeed/2);
		}
	}
	private void damagedFromTop(Collidable collidableUp, HashMap<State, AnimationControl> animations) {
		if (theController.level1.getEnemy().playerMovementDirection == "up" && collidableUp == null) { 
			currentFrame = animations.get(state.HURT).doComplexAnimation(104, 0.2f, Gdx.graphics.getDeltaTime()/2);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			
			position.y += playerMovementSpeed/2;
			theController.touchPos.y += playerMovementSpeed/2;
			sprite.translateY(playerMovementSpeed/2);
		}
	}
	
	
	private void inputNav() {
		if(!theController.doesIntersect(theController.gui.getWeaponizer().getPosition(), theController.gui.getWeaponizer().getCircle().radius) &&
				!theController.doesIntersect(theController.gui.getMaskizer().getPosition(), theController.gui.getMaskizer().getCircle().radius)){
			theController.touchPos.y = Gdx.input.getY();
			theController.touchPos.x = Gdx.input.getX();
			theController.l1Renderer.getCam().unproject(theController.touchPos);
			theController.touchPos.z = 0;
		}else if(Intersector.intersectSegmentCircle(theController.point, theController.point, theController.gui.getWeaponizer().getPosition(), theController.gui.getWeaponizer().getCircle().radius*theController.gui.getWeaponizer().getCircle().radius) == true){
//			System.out.println("yes it intersects");
		}
	}
	private void movementCollisionAndAnimation(float speed, HashMap<State, AnimationControl> animations) {
		// ---------------------left------------------------ //
		Collidable collidableLeft = null;
		
		moveLeft(collidableLeft, speed, animations);
		collidableLeft = collisionCheckerLeft();
		collisionCheck(collidableLeft);
		
		// ---------------------right------------------------ //
		Collidable collidableRight = null;
		
		moveRight(collidableRight, speed, animations);
		collidableRight = collisionCheckerRight();
		collisionCheck(collidableRight);
		
		// ---------------------down------------------------ //
		Collidable collidableDown = null;
		
		moveDown(collidableDown, speed, animations);
		collidableDown = collisionCheckerDown();
		collisionCheck(collidableDown);
		
		// ---------------------up------------------------ //
		Collidable collidableUp = null;
		
		moveUp(collidableUp, speed, animations);
		collidableUp = collisionCheckerUp();
		collisionCheck(collidableUp);
		
		standingAnimation(animations);
	}
	private Collidable collisionCheckerUp() {
		Collidable collidableUp;
		collidableUp = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y+sprite.getHeight(), theController.collisionLayer);
		if(collidableUp == null)collidableUp = CollisionHelper.isCollidable(position.x, position.y+sprite.getHeight(), theController.collisionLayer);
		if(collidableUp == null)collidableUp = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/4), position.y+sprite.getHeight(), theController.collisionLayer);
		return collidableUp;
	}
	private void moveUp(Collidable collidableUp, float speeds, HashMap<State, AnimationControl> animations) {
		if (position.y < theController.touchPos.y -5 && collidableUp == null) {
			position.y += speeds;
			sprite.translateY(speeds);
			playerMovementDirection = "up";
			if(oldPos.y != position.y){
				currentFrame = animations.get(state).animate(8);
			}
		}
	}
	private Collidable collisionCheckerDown() {
		Collidable collidableDown;
		collidableDown = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
		if(collidableDown == null)collidableDown = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
		if(collidableDown == null)collidableDown = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y, theController.collisionLayer);
		return collidableDown;
	}
	private void moveDown(Collidable collidableDown, float speeds, HashMap<State, AnimationControl> animations) {
		if (position.y > theController.touchPos.y -1 && collidableDown == null) {
			position.y -= speeds;
			sprite.translateY(-speeds);
			playerMovementDirection = "down";
			if(oldPos.y != position.y){
				currentFrame = animations.get(state).animate(0);
			}
		}
	}
	private Collidable collisionCheckerRight() {
		Collidable collidableRight;
		collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + (sprite.getHeight()/2), theController.collisionLayer);
		if(collidableRight == null)collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
		if(collidableRight == null)collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/4), theController.collisionLayer);
		return collidableRight;
	}
	private void moveRight(Collidable collidableRight, float speeds, HashMap<State, AnimationControl> animations) {
		if (position.x <  theController.touchPos.x -19/2 && collidableRight == null) {
			position.x += speeds; 
			sprite.translateX(speeds);
			playerMovementDirection = "right";
		}
		if(position.x <  theController.touchPos.x -19/2 && position.y < theController.touchPos.y -1 && position.y > theController.touchPos.y -5 && oldPos.x != position.x && collidableRight == null){
			currentFrame = animations.get(state).animate(16);
		}
	}
	private void collisionCheck(Collidable collidableLeft) {
		if(collidableLeft != null){
			contact(collidableLeft);
		}
	}
	private Collidable collisionCheckerLeft() {
		Collidable collidableLeft;
		collidableLeft = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2), theController.collisionLayer);
		if(collidableLeft == null)collidableLeft = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
		if(collidableLeft == null)collidableLeft = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/4), theController.collisionLayer);
		return collidableLeft;
	}
	private void moveLeft(Collidable collidableLeft, float speeds, HashMap<State, AnimationControl> animations) {
		if (position.x > theController.touchPos.x -16/2 && collidableLeft == null) {
			position.x -= speeds;
			playerMovementDirection = "left";
			sprite.translateX(-speeds);
		}
		if(position.x > theController.touchPos.x -16/2 && position.y < theController.touchPos.y -1 && position.y > theController.touchPos.y -5 && oldPos.x != position.x && collidableLeft == null){
			currentFrame = animations.get(state).animate(24);
		}
	}
	private void standingAnimation(HashMap<State, AnimationControl> animations) {
		if(oldPos.x == position.x && oldPos.y == position.y){
			if(playerMovementDirection == "right"){
				currentFrame = animations.get(state).animate(48);
			}
			if(playerMovementDirection == "left"){
				currentFrame = animations.get(state).animate(56);
			}
			if(playerMovementDirection == "up"){
				currentFrame = animations.get(state).animate(40);
			}
			if(playerMovementDirection == "down"){
				currentFrame = animations.get(state).animate(32);
			}
		}
	}
	public TheController getTheController() {
		return theController;
	}
	public void setTheController(TheController theController) {
		this.theController = theController;
	}
	
	//Collision reaction
	public void contact(Collidable collidable){
		collidable.doCollide(this);
	}
	public String getPlayerMovementDirection() {
		return playerMovementDirection;
	}
	public void setPlayerMovementDirection(String playerMovementDirection) {
		this.playerMovementDirection = playerMovementDirection;
	}
	public float getPlayerMovementSpeedX() {
		return playerMovementSpeed;
	}
	public void setPlayerMovementSpeedX(float playerMovementSpeedX) {
		this.playerMovementSpeed = playerMovementSpeedX;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public boolean isMaskOn() {
		return maskOn;
	}
	public void setMaskOn(boolean maskOn) {
		this.maskOn = maskOn;
	}
	
}
