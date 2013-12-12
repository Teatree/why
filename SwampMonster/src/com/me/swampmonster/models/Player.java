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
			
		
		 	// ---------------------left------------------------ //
			Collidable collidableLeft = null;
	        if (position.x > theController.touchPos.x -16/2 && collidableLeft == null) {
	        	position.x -= playerMovementSpeedX;
	        	playerMovementDirection = "left";
	        	sprite.translateX(-playerMovementSpeedX);
	        }
	        if(position.x > theController.touchPos.x -16/2 && position.y < theController.touchPos.y -1 && position.y > theController.touchPos.y -5 && oldPos.x != position.x && collidableLeft == null){
	        	currentFrame = animations.get(state).animate(24);
	        }
	        //Find a better way of doing this, like, for instance, getting for loop to work.
	        collidableLeft = CollisionHelper.isCollidable(position.x, position.y + sprite.getHeight(), theController.collisionLayer);
	        if(collidableLeft == null)collidableLeft = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
	        if(collidableLeft == null)collidableLeft = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2), theController.collisionLayer);

			if(collidableLeft != null){
				contact(collidableLeft);
			}
			// ---------------------left-end----------------------- //
			
			
			// ---------------------right------------------------ //
			Collidable collidableRight = null;
			if (position.x <  theController.touchPos.x -19/2 && collidableRight == null) {
	        	position.x += playerMovementSpeedX; 
	        	sprite.translateX(playerMovementSpeedX);
	        	playerMovementDirection = "right";
		    }
			if(position.x <  theController.touchPos.x -19/2 && position.y < theController.touchPos.y -1 && position.y > theController.touchPos.y -5 && oldPos.x != position.x && collidableRight == null){
				currentFrame = animations.get(state).animate(16);
			}
			//Find a better way of doing this, like, for instance, getting for loop to work.
			collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + sprite.getHeight(), theController.collisionLayer);
			if(collidableRight == null)collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
	        if(collidableRight == null)collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/2), theController.collisionLayer);

			if(collidableRight != null){
				contact(collidableRight);
			}
			// ---------------------right-end----------------------- //
			
			
			// ---------------------down------------------------ //
			Collidable collidableDown = null;
	        if (position.y > theController.touchPos.y -1 && collidableDown == null) {
		    	position.y -= playerMovementSpeedY;
		    	sprite.translateY(-playerMovementSpeedY);
		    	playerMovementDirection = "down";
		    	if(oldPos.y != position.y){
		    		currentFrame = animations.get(state).animate(0);
		    	}
	        }
	        //Find a better way of doing this, like, for instance, getting for loop to work.
	        collidableDown = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
	        if(collidableDown == null)collidableDown = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
	        if(collidableDown == null)collidableDown = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y, theController.collisionLayer);
	        
	        if(collidableDown != null){
	        	contact(collidableDown);
	        }
	        // ---------------------down-end----------------------- //
	        
	        
	        // ---------------------up------------------------ //
	        Collidable collidableUp = null;
	        if (position.y < theController.touchPos.y -5 && collidableUp == null) {
	        	position.y += playerMovementSpeedY;
	        	sprite.translateY(playerMovementSpeedY);
	        	playerMovementDirection = "up";
	        	if(oldPos.y != position.y){
	        		currentFrame = animations.get(state).animate(8);
	        	}
			}
	      //Find a better way of doing this, like, for instance, getting for loop to work.
	        collidableUp = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y+sprite.getHeight(), theController.collisionLayer);
	        if(collidableUp == null)collidableUp = CollisionHelper.isCollidable(position.x, position.y+sprite.getHeight(), theController.collisionLayer);
	        if(collidableUp == null)collidableUp = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y+sprite.getHeight(), theController.collisionLayer);

			if(collidableUp != null){
				contact(collidableUp);
			}
			// ---------------------up-end----------------------- //
			
			
			if(oldPos.x == position.x && oldPos.y == position.y){
				if(playerMovementDirection == "right"){
					currentFrame = animations.get(state).animate(16);
				}
				if(playerMovementDirection == "left"){
					currentFrame = animations.get(state).animate(24);
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
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
}
