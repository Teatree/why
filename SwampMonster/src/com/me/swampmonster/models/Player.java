package com.me.swampmonster.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;

public class Player extends AbstractGameObject{
	String state;
	
	public Player(Vector2 position){
		this.position = position;
		state = "STANDARD";
		animations.put("STANDARD", new AnimationControl("data/NastyaSheet2.png", 8, 8, 8)); 
		oldPos = position;
		
		sprite = new Sprite(animations.get(state).getCurrentFrame());
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
		sprite.setRegion(animations.get(state).getCurrentFrame());
		
		if (Gdx.input.justTouched()) {

	        theController.touchPos.y = Gdx.input.getY();
	        theController.touchPos.x = Gdx.input.getX();
	        theController.l1Renderer.getCam().unproject(theController.touchPos);
	        theController.touchPos.z = 0;
	    }		
		// X AXIS MOVEMENT + COLLISION PROCESSING AND DETECTION
		//movement

	        if (position.x > theController.touchPos.x -16/2) {
	        	position.x -= playerMovementSpeedX;
	        	playerMovementDirection = "left";
	        	sprite.translateX(-playerMovementSpeedX);
	        	currentFrame = animations.get(state).animate(24);
	        }
	        //Find a better way of doing this, like, for instance, getting for look to work.
	        Collidable collidable = CollisionHelper.isCollidable(position.x, position.y + sprite.getHeight(), theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2), theController.collisionLayer);

			if(collidable != null){
				contact(collidable);
			}
			if (position.x <  theController.touchPos.x -19/2) {
	        	position.x += playerMovementSpeedX;
	        	sprite.translateX(playerMovementSpeedX);
	        	playerMovementDirection = "right";
	        	currentFrame = animations.get(state).animate(16);
		    }
			//Find a better way of doing this, like, for instance, getting for look to work.
			collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + sprite.getHeight(), theController.collisionLayer);
			if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/2), theController.collisionLayer);

			if(collidable != null){
				contact(collidable);
			}
	        if (position.y > theController.touchPos.y -1) {
		    	position.y -= playerMovementSpeedY;
		    	sprite.translateY(-playerMovementSpeedY);
		    	playerMovementDirection = "down";
		    	currentFrame = animations.get(state).animate(0);
	        }
	      //Find a better way of doing this, like, for instance, getting for look to work.
	        collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y, theController.collisionLayer);
	        
	        if(collidable != null){
	        	contact(collidable);
	        }
	        if (position.y < theController.touchPos.y -5) {
	        	position.y += playerMovementSpeedY;
	        	sprite.translateY(playerMovementSpeedY);
	        	playerMovementDirection = "up";
	        	currentFrame = animations.get(state).animate(8);
			}
	      //Find a better way of doing this, like, for instance, getting for look to work.
	        collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y+sprite.getHeight(), theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y+sprite.getHeight(), theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y+sprite.getHeight(), theController.collisionLayer);

		if(collidable != null){
			contact(collidable);
		}
		if(oldPos.x == position.x && oldPos.y == position.y){
			if(playerMovementDirection == "right"){
				currentFrame = animations.get(state).animate(16);
			}
			if(playerMovementDirection == "left"){
				currentFrame = animations.get(state).animate(24);
			}
			if(playerMovementDirection == "up"){
				currentFrame = animations.get(state).animate(8);
			}
			if(playerMovementDirection == "down"){
				currentFrame = animations.get(state).animate(0);
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
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
}
