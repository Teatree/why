package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;

public class Enemy extends AbstractGameObject{
	
	State state = State.STANDARD;
	int cunter;
	public Circle gReenAura;
	public Circle oRangeAura;
	
	public Enemy(Vector2 position){
		this.position = position;
		gReenAura = new Circle();
		gReenAura.radius = 164;
		oRangeAura = new Circle();
		oRangeAura.radius = 30;
		animations.put(State.PURSUIT, new AnimationControl("data/Skelenten.png", 8, 16, 4)); 
		animations.put(State.STANDARD, new AnimationControl("data/Skelenten.png", 8, 16, 4)); 
		animations.put(State.ATTACKING, new AnimationControl("data/Skelenten.png", 8, 16, 4)); 
		oldPos = position;
		playerMovementSpeedX = 0.3f;
		playerMovementSpeedY = 0.3f;
		
		sprite = new Sprite(animations.get(state.PURSUIT).getCurrentFrame());
		sprite = new Sprite(animations.get(state.STANDARD).getCurrentFrame());
		sprite = new Sprite(animations.get(state.ATTACKING).getCurrentFrame());
	}
	
	public void update(){
		oldPos.x = position.x;
		oldPos.y = position.y; 
		
		gReenAura.x = position.x;
		gReenAura.y = position.y;
		oRangeAura.x = position.x;
		oRangeAura.y = position.y;
		
		
		// ATTACKING!
		if(state.equals(State.ATTACKING)){
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
		
		// PURSUIT!
				if(state.equals(State.PURSUIT)){
				sprite.setRegion(animations.get(state).getCurrentFrame());
				
				// X AXIS MOVEMENT + COLLISION PROCESSING AND DETECTION
				//movement
					if(cunter == 0){ 
						cunter = theController.pathfinder.findLastNotNullInArray();
					}
					
			        if (theController.pathfinder.getPath()[cunter] != null && (int)position.x > (int)(theController.pathfinder.getPath()[cunter].x*16)) {
			        	position.x -= playerMovementSpeedX;
			        	sprite.translateX(playerMovementSpeedX);
			        	playerMovementDirection = "left";
			        	if(playerMovementDirection != "down" && playerMovementDirection != "up"){
			        		currentFrame = animations.get(state).animate(8);
			        	}
			        }
			        //Find a better way of doing this, like, for instance, getting for loop to work.
			        Collidable collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2)-1, theController.collisionLayer);
			        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
			        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/4), theController.collisionLayer);

					if(collidable != null){
						contact(collidable);
					}
					if (theController.pathfinder.getPath()[cunter] != null && (int)position.x < (int)(theController.pathfinder.getPath()[cunter].x*16)) {
			        	position.x += playerMovementSpeedX;
			        	sprite.translateX(playerMovementSpeedX);
			        	playerMovementDirection = "right";
			        	if(playerMovementDirection != "down" && playerMovementDirection != "up"){
			        		currentFrame = animations.get(state).animate(24);
			        	}
				    }
					//Find a better way of doing this, like, for instance, getting for loop to work.
					collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + (sprite.getHeight()/2)-1, theController.collisionLayer);
					if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
			        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/4), theController.collisionLayer);

					if(collidable != null){
						contact(collidable);
					}
					
					// Y AXIS MOVEMENT + COLLISION PROCESSING AND DETECTION
					if (theController.pathfinder.getPath()[cunter] != null && (int)position.y > (int)(theController.pathfinder.getPath()[cunter].y*16)) {
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
			        if (theController.pathfinder.getPath()[cunter] != null && (int)position.y < (int)(theController.pathfinder.getPath()[cunter].y*16)) {
			        	position.y += playerMovementSpeedY;
			        	sprite.translateY(playerMovementSpeedY);
			        	playerMovementDirection = "up";
			        	currentFrame = animations.get(state).animate(16);
					}
			      //Find a better way of doing this, like, for instance, getting for loop to work.
			        collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), (position.y+sprite.getHeight()/2)-1, theController.collisionLayer);
			        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, (position.y+sprite.getHeight()/2)-1, theController.collisionLayer);
			        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), (position.y+sprite.getHeight()/2)-1, theController.collisionLayer);

				if(collidable != null){
					contact(collidable);
				}
				// MOVING ON A PATH ( BEST MOVE THIS TO THE PATHFINDER PROPERLY )
				if(theController.pathfinder.getPath()[cunter] != null && position.x >= (theController.pathfinder.getPath()[cunter].x*16)-1 && position.x <= (theController.pathfinder.getPath()[cunter].x*16)+1
						&& position.y <= (theController.pathfinder.getPath()[cunter].y*16)+1 && position.y >= (theController.pathfinder.getPath()[cunter].y*16)-1){
					System.out.println("Passing array index numb: " + cunter);
					theController.pathfinder.getPath()[cunter] = null;
					if(cunter>1){
						cunter--;
					}
				}
				// MOVING ON A PATH ( BEST MOVE THIS TO THE PATHFINDER PROPERLY )
				int counter = 0;
				if(oldPos.x == position.x && oldPos.y == position.y){
					if(playerMovementDirection == "right"){
						currentFrame = animations.get(state).animate(88);
					}
					if(playerMovementDirection == "left"){
						currentFrame = animations.get(state).animate(72);
					}
					if(playerMovementDirection == "up"){
						currentFrame = animations.get(state).animate(80);
					}
					if(playerMovementDirection == "down"){
						currentFrame = animations.get(state).animate(64);
					}
				}
			}
		// STANDARD!
		if(state.equals(State.STANDARD)){
		sprite.setRegion(animations.get(state).getCurrentFrame());
		
		// X AXIS MOVEMENT + COLLISION PROCESSING AND DETECTION
		//movement
			if(cunter == 0){ 
				cunter = theController.pathfinder.findLastNotNullInArray();
			}
			
	        if (theController.pathfinder.getPath()[cunter] != null && (int)position.x > (int)(theController.pathfinder.getPath()[cunter].x*16)) {
	        	position.x -= playerMovementSpeedX;
	        	sprite.translateX(playerMovementSpeedX);
	        	playerMovementDirection = "left";
	        	if(playerMovementDirection != "down" && playerMovementDirection != "up"){
	        		currentFrame = animations.get(state).animate(8);
	        	}
	        }
	        //Find a better way of doing this, like, for instance, getting for loop to work.
	        Collidable collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2)-1, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/4), theController.collisionLayer);

			if(collidable != null){
				contact(collidable);
			}
			if (theController.pathfinder.getPath()[cunter] != null && (int)position.x < (int)(theController.pathfinder.getPath()[cunter].x*16)) {
	        	position.x += playerMovementSpeedX;
	        	sprite.translateX(playerMovementSpeedX);
	        	playerMovementDirection = "right";
	        	if(playerMovementDirection != "down" && playerMovementDirection != "up"){
	        		currentFrame = animations.get(state).animate(24);
	        	}
		    }
			//Find a better way of doing this, like, for instance, getting for loop to work.
			collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + (sprite.getHeight()/2)-1, theController.collisionLayer);
			if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/4), theController.collisionLayer);

			if(collidable != null){
				contact(collidable);
			}
			
			// Y AXIS MOVEMENT + COLLISION PROCESSING AND DETECTION
			if (theController.pathfinder.getPath()[cunter] != null && (int)position.y > (int)(theController.pathfinder.getPath()[cunter].y*16)) {
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
	        if (theController.pathfinder.getPath()[cunter] != null && (int)position.y < (int)(theController.pathfinder.getPath()[cunter].y*16)) {
	        	position.y += playerMovementSpeedY;
	        	sprite.translateY(playerMovementSpeedY);
	        	playerMovementDirection = "up";
	        	currentFrame = animations.get(state).animate(16);
			}
	      //Find a better way of doing this, like, for instance, getting for loop to work.
	        collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), (position.y+sprite.getHeight()/2)-1, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, (position.y+sprite.getHeight()/2)-1, theController.collisionLayer);
	        if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), (position.y+sprite.getHeight()/2)-1, theController.collisionLayer);

		if(collidable != null){
			contact(collidable);
		}
		// MOVING ON A PATH ( BEST MOVE THIS TO THE PATHFINDER PROPERLY )
		if(theController.pathfinder.getPath()[cunter] != null && position.x >= (theController.pathfinder.getPath()[cunter].x*16)-1 && position.x <= (theController.pathfinder.getPath()[cunter].x*16)+1
				&& position.y <= (theController.pathfinder.getPath()[cunter].y*16)+1 && position.y >= (theController.pathfinder.getPath()[cunter].y*16)-1){
			System.out.println("Passing array index numb: " + cunter);
			theController.pathfinder.getPath()[cunter] = null;
			if(cunter>1){
				cunter--;
			}
		}
		// MOVING ON A PATH ( BEST MOVE THIS TO THE PATHFINDER PROPERLY )
		int counter = 0;
		if(oldPos.x == position.x && oldPos.y == position.y){
			if(playerMovementDirection == "right"){
				currentFrame = animations.get(state).animate(88);
			}
			if(playerMovementDirection == "left"){
				currentFrame = animations.get(state).animate(72);
			}
			if(playerMovementDirection == "up"){
				currentFrame = animations.get(state).animate(80);
			}
			if(playerMovementDirection == "down"){
				currentFrame = animations.get(state).animate(64);
			}
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

	public int getCunter() {
		return cunter;
	}

	public void setCunter(int cunter) {
		this.cunter = cunter;
	}

	public Circle getgReenAura() {
		return gReenAura;
	}

	public void setgReenAura(Circle gReenAura) {
		this.gReenAura = gReenAura;
	}

	public Circle getoRangeAura() {
		return oRangeAura;
	}

	public void setoRangeAura(Circle oRangeAura) {
		this.oRangeAura = oRangeAura;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	
}
