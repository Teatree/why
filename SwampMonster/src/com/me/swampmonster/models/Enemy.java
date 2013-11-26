package com.me.swampmonster.models;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;


// ONE BIG QUESTION, WE ARE ABOUT TO START SOLVING

public class Enemy extends AbstractGameObject{
	
	private static final int col = 8;
	private static final int row = 4;
	
	private float stateTime = 0;
	
	public Enemy(Vector2 position){
		this.position = position;
		oldPos = position;
		playerMovementSpeedX = 0.3f;
		playerMovementSpeedY = 0.3f;
		
		playerTexture = new Texture(Gdx.files.internal("data/Skelenten.png"));
		TextureRegion[][] tmp = TextureRegion
				.split(playerTexture, playerTexture.getWidth() / col,
						playerTexture.getHeight() / row);
		frames = new TextureRegion[col * row];

		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				frames[index++] = tmp[i][j];
			}
		}

		animation = new Animation(1, frames);
		stateTime = 0f;
		currentFrame = animation.getKeyFrame(0);
		sprite = new Sprite(currentFrame);
	}
	
	public void update(){
		oldPos.x = position.x;
		oldPos.y = position.y;
		sprite.setRegion(currentFrame);
		
		if (stateTime < 1) {
			stateTime += Gdx.graphics.getDeltaTime();
		} else {
			stateTime = 0;
		}
		
		// X AXIS MOVEMENT + COLLISION PROCESSING AND DETECTION
		//movement

	        if (position.x > theController.level1.getPlayer().getPosition().x-16/2) {
	        	position.x -= playerMovementSpeedX;
	        	playerMovementDirection = "left";
	        	currentFrame = animation.getKeyFrame(8 + stateTime*4);
	        }
	        //Find a better way of doing this, like, for instance, getting for loop to work.
	        Collidable collidable = CollisionHelper.isCollidable(position.x, position.y + sprite.getHeight(), theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2), theController.collisionLayer);

			if(collidable != null){
				contact(collidable);
			}
			if (position.x < theController.level1.getPlayer().getPosition().x-19/2) {
	        	position.x += playerMovementSpeedX;
	        	sprite.translateX(playerMovementSpeedX);
	        	playerMovementDirection = "right";
	        	currentFrame = animation.getKeyFrame(24 + stateTime*4);
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
		    	currentFrame = animation.getKeyFrame(0 + stateTime*4);
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
	        	currentFrame = animation.getKeyFrame(16 + stateTime*4);
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
				currentFrame = animation.getKeyFrame(24);
			}
			if(playerMovementDirection == "left"){
				currentFrame = animation.getKeyFrame(8);
			}
			if(playerMovementDirection == "up"){
				currentFrame = animation.getKeyFrame(16);
			}
			if(playerMovementDirection == "down"){
				currentFrame = animation.getKeyFrame(0);
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
