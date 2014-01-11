package com.me.swampmonster.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.State;

public class Enemy extends AbstractGameObject{
	
	State state = State.STANDARD;
	int cunter;
	int timer;
	int timer2;
	
	public Circle gReenAura;
	public Circle oRangeAura;
	
	public Enemy(Vector2 position){
		this.position = position;
		gReenAura = new Circle();
		gReenAura.radius = 164;
		oRangeAura = new Circle();
		oRangeAura.radius = 16;
		animations.put(State.PURSUIT, new AnimationControl("data/Skelenten.png", 8, 16, 4)); 
		animations.put(State.STANDARD, new AnimationControl("data/Skelenten.png", 8, 16, 4)); 
		animations.put(State.ATTACKING, new AnimationControl("data/Skelenten.png", 8, 16, 4)); 
		oldPos = position;
		playerMovementSpeedX = 0.3f;
		playerMovementSpeedY = 0.3f;
		timer = 0;
		timer2 = 0;
		
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
		
		// Standing animation between attacks doesn't work.
		if(state.equals(State.ATTACKING)){
			if(timer2 < 80){
				timer2++;
				System.out.println("timer2: " + timer2 );
			    currentFrame = animations.get(state).animate(88);
			}
			if(timer2 >= 80 && timer < 40){
					System.out.println("timer1: " + timer);
					currentFrame = animations.get(state).doComplexAnimation(56, 1.8f, Gdx.graphics.getDeltaTime());
							
					sprite.setRegion(animations.get(state).getCurrentFrame());
					sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
					timer++;
					if(timer == 40 && timer2 >= 80){
						currentFrame = animations.get(state).animate(88);
						theController.level1.getPlayer().setState(State.HURT);
						theController.level1.getPlayer().setHealth(theController.level1.getPlayer().getHealth()-1);
						timer = 0;
						timer2 = 0;
					}
			}
			
			//MOVEMENT + COLLISION PROCESSING AND DETECTION

//            moveLeft();
//            Collidable collidable = collisionCheckerLeft();
//            collisionCheck(collidable);
//                    
//            moveRight();
//            collidable = collisionCheckerRight();
//            collisionCheck(collidable);
//                    
//            moveDown();
//            collidable = collisionCheckerBottom();
//            collisionCheck(collidable);
//            
//            moveUp();
//            collidable = collisionCheckerTop();
//            collisionCheck(collidable);
            
            standAnimation(56, 40, 48, 32);
		}
		
		// PURSUIT!
				if(state.equals(State.PURSUIT)){
					sprite.setRegion(animations.get(state).getCurrentFrame());
					
					theController.pathfinder.findPath(theController.level1.getEnemy().getPosition(), theController.level1.getPlayer().getPosition());
					
					//MOVEMENT + COLLISION PROCESSING AND DETECTION
						if(cunter == 0){ 
							cunter = theController.pathfinder.findLastNotNullInArray();
						}
						// boom!
				        onPathMovingAndCollisionDetection();
				        // boom!
				        
					orientOnPath();
					standAnimation(88, 72, 80, 64);
					
				}
		// STANDARD!
		if(state.equals(State.STANDARD)){
		sprite.setRegion(animations.get(state).getCurrentFrame());
		
		// X AXIS MOVEMENT + COLLISION PROCESSING AND DETECTION
		//movement
			if(cunter == 0){ 
				cunter = theController.pathfinder.findLastNotNullInArray();
			}
			
	        onPathMovingAndCollisionDetection();
		
		orientOnPath();
		standAnimation(88, 72, 80, 64);
	}
	}

	private void onPathMovingAndCollisionDetection() {
		moveLeftOnPath();
		Collidable collidable = collisionCheckerLeft();
		collisionCheck(collidable);
		
		moveRightOnPath();
		collidable = collisionCheckerRight();
		collisionCheck(collidable);
		
		moveDownOnPath();
		collidable = collisionCheckerBottom();
		collisionCheck(collidable);
		
		moveUpOnPath();
		collidable = collisionCheckerTop();
		collisionCheck(collidable);
	}


	private Collidable collisionCheckerTop() {
		Collidable collidable;
		collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), (position.y+sprite.getHeight()/2)-1, theController.collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, (position.y+sprite.getHeight()/2)-1, theController.collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), (position.y+sprite.getHeight()/2)-1, theController.collisionLayer);
		return collidable;
	}

	private Collidable collisionCheckerBottom() {
		Collidable collidable;
		collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y, theController.collisionLayer);
		return collidable;
	}

	private Collidable collisionCheckerRight() {
		Collidable collidable;
		collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + (sprite.getHeight()/2)-1, theController.collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, theController.collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/4), theController.collisionLayer);
		return collidable;
	}

	private Collidable collisionCheckerLeft() {
		Collidable collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2)-1, theController.collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, theController.collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/4), theController.collisionLayer);
		return collidable;
	}

	private void moveLeft() {
		if (position.x > theController.level1.getPlayer().getPosition().x+2) {
			position.x -= playerMovementSpeedX;
			playerMovementDirection = "left";
			if(position.x > theController.level1.getPlayer().getPosition().x+2 && position.y < theController.level1.getPlayer().getPosition().y-1 && position.y > theController.level1.getPlayer().getPosition().y-5){
				currentFrame = animations.get(state).animate(8);
			}
		}
	}
	
	private void moveRight() {
		if (position.x < theController.level1.getPlayer().getPosition().x+5) {
			position.x += playerMovementSpeedX;
			sprite.translateX(playerMovementSpeedX);
			playerMovementDirection = "right";
			if(position.x < theController.level1.getPlayer().getPosition().x+5 && position.y < theController.level1.getPlayer().getPosition().y-1 && position.y > theController.level1.getPlayer().getPosition().y-5){
				currentFrame = animations.get(state).animate(24);
			}        
		}
	}
	private void moveDown() {
		if (position.y > theController.level1.getPlayer().getPosition().y-1) {
		    position.y -= playerMovementSpeedY;
		    sprite.translateY(-playerMovementSpeedY);
		    playerMovementDirection = "down";
		    currentFrame = animations.get(state).animate(0);
         }
	}

	private void moveUp() {
		if (position.y < theController.level1.getPlayer().getPosition().y-5) {
		        position.y += playerMovementSpeedY;
		        sprite.translateY(playerMovementSpeedY);
		        playerMovementDirection = "up";
		        currentFrame = animations.get(state).animate(16);
		        }
	}

	private void collisionCheck(Collidable collidable) {
		if(collidable != null){
			contact(collidable);
		}
	}

	private void moveUpOnPath() {
		if (theController.pathfinder.getPath()[cunter] != null && (int)position.y < (int)(theController.pathfinder.getPath()[cunter].y*16)) {
			position.y += playerMovementSpeedY;
			sprite.translateY(playerMovementSpeedY);
			playerMovementDirection = "up";
			currentFrame = animations.get(state).animate(16);
		}
	}

	private void moveDownOnPath() {
		if (theController.pathfinder.getPath()[cunter] != null && (int)position.y > (int)(theController.pathfinder.getPath()[cunter].y*16)) {
			position.y -= playerMovementSpeedY;
			sprite.translateY(-playerMovementSpeedY);
			playerMovementDirection = "down";
		   	currentFrame = animations.get(state).animate(0);
		}
	}

	private void moveRightOnPath() {
		if (theController.pathfinder.getPath()[cunter] != null && (int)position.x < (int)(theController.pathfinder.getPath()[cunter].x*16)) {
			position.x += playerMovementSpeedX;
			sprite.translateX(playerMovementSpeedX);
			playerMovementDirection = "right";
			if(playerMovementDirection != "down" && playerMovementDirection != "up"){
				currentFrame = animations.get(state).animate(24);
			}
		}
	}

	private void moveLeftOnPath() {
		if (theController.pathfinder.getPath()[cunter] != null && (int)position.x > (int)(theController.pathfinder.getPath()[cunter].x*16)) {
			position.x -= playerMovementSpeedX;
			sprite.translateX(playerMovementSpeedX);
			playerMovementDirection = "left";
			if(playerMovementDirection != "down" && playerMovementDirection != "up"){
				currentFrame = animations.get(state).animate(8);
			}
		}
	}

	private void standAnimation(int r, int l, int u, int d) {
		if(oldPos.x == position.x && oldPos.y == position.y){
			if(playerMovementDirection == "right"){
				currentFrame = animations.get(state).animate(r);
			}
			if(playerMovementDirection == "left"){
				currentFrame = animations.get(state).animate(l);
			}
			if(playerMovementDirection == "up"){
				currentFrame = animations.get(state).animate(u);
			}
			if(playerMovementDirection == "down"){
				currentFrame = animations.get(state).animate(d);
			}
		}
	}

	private void orientOnPath() {
		if(theController.pathfinder.getPath()[cunter] != null && position.x >= (theController.pathfinder.getPath()[cunter].x*16)-1 && position.x <= (theController.pathfinder.getPath()[cunter].x*16)+1
				&& position.y <= (theController.pathfinder.getPath()[cunter].y*16)+1 && position.y >= (theController.pathfinder.getPath()[cunter].y*16)-1){
			if(cunter>0){
				theController.pathfinder.getPath()[cunter] = null;
				cunter--;
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
