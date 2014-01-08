package com.me.swampmonster.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;

public class Player extends AbstractGameObject{
	
	State state = State.STANDARD;
	int time = 0;
	// temporary
	private Circle tempCircle;
	// temporary
	
	
	public Player(Vector2 position){
		this.position = position;
		
		// Temporary circle 
		tempCircle = new Circle();
		tempCircle.radius = 16;
		// Temporary circle
		
		animations.put(state.STANDARD, new AnimationControl("data/NastyaSheet2.png", 8, 32, 7)); 
		animations.put(state.ANIMATING, new AnimationControl("data/NastyaSheet2.png", 8, 32, 8)); 
		oldPos = position;
		
		health = 6;
		oxygen = 6;
		sprite = new Sprite(animations.get(state.STANDARD).getCurrentFrame());
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
		
		tempCircle.x = position.x;
		tempCircle.y = position.y;
		
	//ANIMATING
		if(state.equals(State.ANIMATING)){
			if(time < 150){
				sprite = new Sprite(animations.get(state.ANIMATING).getCurrentFrame());
				sprite.rotate(32);
				currentFrame = animations.get(state).doComplexAnimation(112, 4, 10, 0.01f);
				
				sprite.setRegion(animations.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				time++;
			}
			else{
				currentFrame = animations.get(state.ANIMATING).animate(64);
				state = State.STANDARD;
				time = 0;
			}
		}
			
	//STANDARD
		if(state.equals(State.STANDARD)){
		sprite = new Sprite(animations.get(state.STANDARD).getCurrentFrame());
		sprite.setRegion(animations.get(state).getCurrentFrame());
		sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
		
		if (Gdx.input.justTouched()) {

	        theController.touchPos.y = Gdx.input.getY();
	        theController.touchPos.x = Gdx.input.getX();
	        theController.l1Renderer.getCam().unproject(theController.touchPos);
	        theController.touchPos.z = 0;
	    }	
		//movement
		 	movementCollisionAndAnimation();
		}
	}
	private void movementCollisionAndAnimation() {
		// ---------------------left------------------------ //
		Collidable collidableLeft = null;
		
		moveLeft(collidableLeft);
		collidableLeft = collisionCheckerLeft();
		collisionCheck(collidableLeft);
		
		// ---------------------right------------------------ //
		Collidable collidableRight = null;
		
		moveRight(collidableRight);
		collidableRight = collisionCheckerRight();
		collisionCheck(collidableRight);
		
		// ---------------------down------------------------ //
		Collidable collidableDown = null;
		
		moveDown(collidableDown);
		collidableDown = collisionCheckerDown();
		collisionCheck(collidableDown);
		
		// ---------------------up------------------------ //
		Collidable collidableUp = null;
		
		moveUp(collidableUp);
		collidableUp = collisionCheckerUp();
		collisionCheck(collidableUp);
		
		standingAnimation();
	}
	private Collidable collisionCheckerUp() {
		Collidable collidableUp;
		collidableUp = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y+sprite.getHeight(), theController.collisionLayer);
		if(collidableUp == null)collidableUp = CollisionHelper.isCollidable(position.x, position.y+sprite.getHeight(), theController.collisionLayer);
		if(collidableUp == null)collidableUp = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y+sprite.getHeight(), theController.collisionLayer);
		return collidableUp;
	}
	private void moveUp(Collidable collidableUp) {
		if (position.y < theController.touchPos.y -5 && collidableUp == null) {
			position.y += playerMovementSpeedY;
			sprite.translateY(playerMovementSpeedY);
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
	private void moveDown(Collidable collidableDown) {
		if (position.y > theController.touchPos.y -1 && collidableDown == null) {
			position.y -= playerMovementSpeedY;
			sprite.translateY(-playerMovementSpeedY);
			playerMovementDirection = "down";
			if(oldPos.y != position.y){
				currentFrame = animations.get(state).animate(0);
			}
		}
	}
	private Collidable collisionCheckerRight() {
		Collidable collidableRight;
		collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + sprite.getHeight(), theController.collisionLayer);
		if(collidableRight == null)collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
		if(collidableRight == null)collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/2), theController.collisionLayer);
		return collidableRight;
	}
	private void moveRight(Collidable collidableRight) {
		if (position.x <  theController.touchPos.x -19/2 && collidableRight == null) {
			position.x += playerMovementSpeedX; 
			sprite.translateX(playerMovementSpeedX);
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
		collidableLeft = CollisionHelper.isCollidable(position.x, position.y + sprite.getHeight(), theController.collisionLayer);
		if(collidableLeft == null)collidableLeft = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
		if(collidableLeft == null)collidableLeft = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2), theController.collisionLayer);
		return collidableLeft;
	}
	private void moveLeft(Collidable collidableLeft) {
		if (position.x > theController.touchPos.x -16/2 && collidableLeft == null) {
			position.x -= playerMovementSpeedX;
			playerMovementDirection = "left";
			sprite.translateX(-playerMovementSpeedX);
		}
		if(position.x > theController.touchPos.x -16/2 && position.y < theController.touchPos.y -1 && position.y > theController.touchPos.y -5 && oldPos.x != position.x && collidableLeft == null){
			currentFrame = animations.get(state).animate(24);
		}
	}
	private void standingAnimation() {
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
		return playerMovementSpeedX;
	}
	public void setPlayerMovementSpeedX(float playerMovementSpeedX) {
		this.playerMovementSpeedX = playerMovementSpeedX;
	}
	public float getPlayerMovementSpeedY() {
		return playerMovementSpeedY;
	}
	public void setPlayerMovementSpeedY(float playerMovementSpeedY) {
		this.playerMovementSpeedY = playerMovementSpeedY;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Circle getTempCircle() {
		return tempCircle;
	}
	public void setTempCircle(Circle tempCircle) {
		this.tempCircle = tempCircle;
	}
	
	
	
}
