package com.me.swampmonster.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;


// ONE BIG QUESTION, WE ARE ABOUT TO START SOLVING

public class Enemy extends AbstractGameObject{
	
	public Enemy(Vector2 position){
		this.position = position;
		oldPos = position;
		playerMovementSpeedX = 0.5f;
		playerMovementSpeedY = 0.5f;
		sprite = new Sprite(new Texture(Gdx.files.internal("data/EvilNastya.png")));
	}
	
	public void update(){
		oldPos.x = position.x;
		oldPos.y = position.y;
		
		// X AXIS MOVEMENT + COLLISION PROCESSING AND DETECTION
		//movement

	        if (position.x > theController.level1.getPlayer().getPosition().x) {
	        	position.x -= playerMovementSpeedX;
	        	playerMovementDirection = "left";
	        	sprite.translateX(-playerMovementSpeedX);
	        }
	        //Find a better way of doing this, like, for instance, getting for look to work.
	        Collidable collidable = CollisionHelper.isCollidable(position.x, position.y + sprite.getHeight(), theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2), theController.collisionLayer);

			if(collidable != null){
				contact(collidable);
			}
			if (position.x < theController.level1.getPlayer().getPosition().x) {
	        	position.x += playerMovementSpeedX;
	        	sprite.translateX(playerMovementSpeedX);
	        	playerMovementDirection = "right";
		    }
			//Find a better way of doing this, like, for instance, getting for look to work.
			collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + sprite.getHeight(), theController.collisionLayer);
			if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/2), theController.collisionLayer);

			if(collidable != null){
				contact(collidable);
			}
	        if (position.y > theController.level1.getPlayer().getPosition().y) {
		    	position.y -= playerMovementSpeedY;
		    	sprite.translateY(-playerMovementSpeedY);
		    	playerMovementDirection = "down";
	        }
	      //Find a better way of doing this, like, for instance, getting for look to work.
	        collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y, theController.collisionLayer);
	        
	        if(collidable != null){
	        	contact(collidable);
	        }
	        if (position.y < theController.level1.getPlayer().getPosition().y) {
	        	position.y += playerMovementSpeedY;
	        	sprite.translateY(playerMovementSpeedY);
	        	playerMovementDirection = "up";
			}
	      //Find a better way of doing this, like, for instance, getting for look to work.
	        collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y+sprite.getHeight(), theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y+sprite.getHeight(), theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y+sprite.getHeight(), theController.collisionLayer);

		if(collidable != null){
			contact(collidable);
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
