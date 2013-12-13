package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;


public class Enemy extends AbstractGameObject{
	
	State state;
	
	public Enemy(Vector2 position){
		this.position = position;
		this.state = State.STANDARD;
		animations.put(State.STANDARD, new AnimationControl("data/Skelenten.png", 8, 16, 4)); 
		oldPos = position;
		playerMovementSpeedX = 0.3f;
		playerMovementSpeedY = 0.3f;
		
		sprite = new Sprite(animations.get(state).getCurrentFrame());
	}
	
	public void update(){
		oldPos.x = position.x;
		oldPos.y = position.y;
		sprite.setRegion(animations.get(state).getCurrentFrame());
		
		// X AXIS MOVEMENT + COLLISION PROCESSING AND DETECTION
		//movement

	        if (position.x > theController.level1.getPlayer().getPosition().x+2) {
	        	position.x -= playerMovementSpeedX;
	        	playerMovementDirection = "left";
	        	if(position.x > theController.level1.getPlayer().getPosition().x+2 && position.y < theController.level1.getPlayer().getPosition().y-1 && position.y > theController.level1.getPlayer().getPosition().y-5){
	        		currentFrame = animations.get(state).animate(8);
	        	}
	        }
	        //Find a better way of doing this, like, for instance, getting for loop to work.
	        Collidable collidable = CollisionHelper.isCollidable(position.x, position.y + sprite.getHeight(), theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2), theController.collisionLayer);

			if(collidable != null){
				contact(collidable);
			}
			if (position.x < theController.level1.getPlayer().getPosition().x+5) {
	        	position.x += playerMovementSpeedX;
	        	sprite.translateX(playerMovementSpeedX);
	        	playerMovementDirection = "right";
	        	if(position.x < theController.level1.getPlayer().getPosition().x+5 && position.y < theController.level1.getPlayer().getPosition().y-1 && position.y > theController.level1.getPlayer().getPosition().y-5){
	        		currentFrame = animations.get(state).animate(24);
	        	}	
		    }
			//Find a better way of doing this, like, for instance, getting for loop to work.
			collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + sprite.getHeight(), theController.collisionLayer);
			if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/2), theController.collisionLayer);

			if(collidable != null){
				contact(collidable);
			}
			if (position.y > theController.level1.getPlayer().getPosition().y-1) {
		    	position.y -= playerMovementSpeedY;
		    	sprite.translateY(-playerMovementSpeedY);
		    	playerMovementDirection = "down";
		    	currentFrame = animations.get(state).animate(0);
	        }
	      //Find a better way of doing this, like, for instance, getting for loop to work.
	        collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y, theController.collisionLayer);
	        
	        if(collidable != null){
	        	contact(collidable);
	        }
	        if (position.y < theController.level1.getPlayer().getPosition().y-5) {
	        	position.y += playerMovementSpeedY;
	        	sprite.translateY(playerMovementSpeedY);
	        	playerMovementDirection = "up";
	        	currentFrame = animations.get(state).animate(16);
			}
	      //Find a better way of doing this, like, for instance, getting for loop to work.
	        collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y+sprite.getHeight(), theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y+sprite.getHeight(), theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y+sprite.getHeight(), theController.collisionLayer);

		if(collidable != null){
			contact(collidable);
		}
		
		
		if(oldPos.x == position.x && oldPos.y == position.y){
			if(playerMovementDirection == "right"){
				currentFrame = animations.get(state).animate(56);
			}
			if(playerMovementDirection == "left"){
				currentFrame = animations.get(state).animate(40);
			}
			if(playerMovementDirection == "up"){
				currentFrame = animations.get(state).animate(48);
			}
			if(playerMovementDirection == "down"){
				currentFrame = animations.get(state).animate(32);
			}
		}
	}
	
	private void contact(Collidable collidable) {
		collidable.doCollide(this);
	}

	public Vector2 getOldPos() {
		return oldPos;
	}


	public void setOldPos(Vector2 oldPos) {
		this.oldPos = oldPos;
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

	public TheController getTheController() {
		return theController;
	}


	public void setTheController(TheController theController) {
		this.theController = theController;
	}

	public void doCollide(Player player) {
		
	}

}
