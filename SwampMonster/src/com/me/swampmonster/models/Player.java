package com.me.swampmonster.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;

public class Player extends AbstractGameObject{
	
	State state = State.STANDARD;
	int time = 0;
	
	
	public Player(Vector2 position){
		this.position = position;
		
		circle = new Circle();
		circle.radius = 16;
		
		animations.put(state.STANDARD, new AnimationControl("data/NastyaSheet2.png", 8, 32, 7)); 
		animations.put(state.ANIMATING, new AnimationControl("data/NastyaSheet2.png", 8, 32, 8)); 
		animations.put(state.HURT, new AnimationControl("data/NastyaSheet2.png", 8, 32, 8)); 
		oldPos = position;
		
		health = 6;
		oxygen = 96;
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
		
		circle.x = position.x;
		circle.y = position.y;
		
	//ANIMATING
		if(state.equals(State.ANIMATING)){
			if(time < 108){
				sprite = new Sprite(animations.get(state.ANIMATING).getCurrentFrame());
				currentFrame = animations.get(state).doComplexAnimation(112, 1.8f, Gdx.graphics.getDeltaTime());
				
				sprite.setRegion(animations.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				time++;
			}else{
				time = 0;
				state = State.STANDARD;
			}
		}
			
	//HURT
		if(state.equals(State.HURT)){
			
			if(time < 30){
				sprite = new Sprite(animations.get(state.HURT).getCurrentFrame());
				currentFrame = animations.get(state.HURT).doComplexAnimation(104, 0.2f, Gdx.graphics.getDeltaTime()/2);
				
				sprite.setRegion(animations.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				time++;
				
				Collidable collidableUp = null;
				
				// For the moment he moves according to the direction he is moving in at the moment of the strike, should be change to being 
				// From what direction he was hit.
				if (position.y < theController.touchPos.y -5 && collidableUp == null) { 
					position.y += playerMovementSpeedY/2;
					sprite.translateY(playerMovementSpeedY/2);
				}
				collidableUp = collisionCheckerUp();
				collisionCheck(collidableUp);
			}else{
				currentFrame = animations.get(state.HURT).animate(64);
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

	        inputNav();
	    }	
		//movement
		 	movementCollisionAndAnimation();
		}
	}
	
	//DEAD
	//
	//
	
	private void inputNav() {
		if(!theController.doesIntersect(theController.gui.getWeaponizer().getPosition(), theController.gui.getWeaponizer().getCircle().radius) && !theController.doesIntersect(theController.gui.getMaskizer().getPosition(), theController.gui.getMaskizer().getCircle().radius)){
			theController.touchPos.y = Gdx.input.getY();
			theController.touchPos.x = Gdx.input.getX();
			theController.l1Renderer.getCam().unproject(theController.touchPos);
			theController.touchPos.z = 0;
		}else if(Intersector.intersectSegmentCircle(theController.point, theController.point, theController.gui.getWeaponizer().getPosition(), theController.gui.getWeaponizer().getCircle().radius*theController.gui.getWeaponizer().getCircle().radius) == true){
//			System.out.println("yes it intersects");
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
}
