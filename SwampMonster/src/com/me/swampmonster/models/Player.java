package com.me.swampmonster.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;

public class Player extends AbstractGameObject{
	
	public Player(Vector2 position){
		this.position = position;
		oldPos = position;
		sprite = new Sprite(new Texture(Gdx.files.internal("data/Nastya.png")));
	}
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
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
		
		if (Gdx.input.justTouched()) {

	        theController.touchPos.y = Gdx.input.getY();
	        theController.touchPos.x = Gdx.input.getX();
	        theController.l1Renderer.getCam().unproject(theController.touchPos);
	        theController.touchPos.z = 0;
	    }		// X AXIS MOVEMENT + COLLISION PROCESSING AND DETECTION
		//movement

	        if (position.x >  theController.touchPos.x -16/2) {
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
			if (position.x <  theController.touchPos.x -19/2) {
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
	        if (position.y > theController.touchPos.y -1) {
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
	        if (position.y < theController.touchPos.y -5) {
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
//        //collision detection
//        if(playerMovementDirection == "left"){
//        	//left side
//        	collisionX = (theController.collisionHandler.collidesLeft(theController.collisionLayer, this)) != null;
//	    }
//	    else if(playerMovementDirection == "right"){
//	    	//right side
//	    	collisionX = theController.collisionHandler.collidesRight(theController.collisionLayer, this);
//	    }
//        //collision result
//	    if(collisionX){
//	    	System.out.println("shit");
//	    	setPosition(new Vector2(OldX, position.y));
//	    	playerMovementSpeedX = 0;
//	    }

	    // Y AXIS MOVEMENT + COLLISION PROCESSING AND DETECTION
	    //movement
		
       
//        //collision detection
//	    if(playerMovementDirection == "up"){
//	    	//top side
//	    	collisionY = theController.collisionHandler.collidesTop(theController.collisionLayer, this);
//	    }else if(playerMovementDirection == "down"){
//	    	//bottom side
//	    	collisionY = theController.collisionHandler.coolidesBottom(theController.collisionLayer, this);
//	    }
//	    //collision result
//	    if(collisionY){
//	    	System.out.println("shit + 100000");
//	    	setPosition(new Vector2(position.x, OldY));
//	    	playerMovementSpeedY = 0;
//	    }
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
}
