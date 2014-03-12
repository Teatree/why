package com.me.swampmonster.models;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.State;

public class Player extends AbstractGameObject{
	
	State state = State.STANDARD;
	int time = 0;
	int timeDead = 0;
	private int timeShooting = 0;
	String nastyaSpriteStandard;
	String nastyaSpriteGun;
	// responsible for what kind of animation are to be played in the Animating State
	String doing;
	// responsible for what kind of animation are to be played in the Animating State, to be changed to something better
	boolean maskOn;
	boolean justSpawned;
	public boolean shooting;
	Vector3 shotDir;
	private boolean hurt;
	
	public Player(Vector2 position){
		this.position = position;
		nastyaSpriteStandard = "data/NastyaOxygenSheet.png";
		nastyaSpriteGun = "data/NastyaGunSheet.png";
		
		circle = new Circle();
		circle.radius = 16;
		rectanlge = new Rectangle();
		
		animationsStandard.put(state.STANDARD, new AnimationControl(nastyaSpriteStandard, 8, 32, 7)); 
		animationsStandard.put(state.ANIMATING, new AnimationControl(nastyaSpriteStandard, 8, 32, 8)); 
		animationsStandard.put(state.ANIMATINGLARGE, new AnimationControl(nastyaSpriteStandard, 8, 32, 8)); 
		animationsStandard.put(state.ACTIVATING, new AnimationControl(nastyaSpriteStandard, 8, 32, 8)); 
		animationsStandard.put(state.GUNMOVEMENT, new AnimationControl(nastyaSpriteGun, 8, 16, 7)); 
		animationsStandard.put(state.DEAD, new AnimationControl(nastyaSpriteStandard, 8, 32, 8)); 
		
		oldPos = position;
		
		dead = false;
		maskOn = true;
		justSpawned = true;
		shooting = false;
		health = 6;
		oxygen = 96;
		sprite = new Sprite(animationsStandard.get(state.STANDARD).getCurrentFrame());
		shotDir = new Vector3();
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
		 
		 
	public void update(boolean aiming, AbstractGameObject enemy, Vector3 touchPos, Vector3 V3point, TiledMapTileLayer collisionLayer) {
		oldPos.x = position.x;
		oldPos.y = position.y;
		
		circle.x = position.x;
		circle.y = position.y;
		
		sprite.setX(position.x);
		sprite.setY(position.y);
		
		rectanlge.x = sprite.getX();
		rectanlge.y = sprite.getY();
		rectanlge.width = sprite.getWidth();
		rectanlge.height = sprite.getHeight();
		
		HashMap<State, AnimationControl> animations = new HashMap<AbstractGameObject.State, AnimationControl>();
		
		animations = animationsStandard;
		
		if(Gdx.input.justTouched()){
			justSpawned = false;
		}
		
//		System.out.println("player position = " + position.x + " : " + position.y);
//		System.out.println("player position = " + sprite.getX() + " : " + sprite.getY());
		
	//ANIMATINGLARGE
		if(state.equals(State.ANIMATINGLARGE)){
			if(doing.equals("puttingGunAway")){
				if(time < 83){
					sprite = new Sprite(animations.get(state.GUNMOVEMENT).getCurrentFrame());
					currentFrame = animations.get(state.GUNMOVEMENT).doComplexAnimation(40, 1f, Gdx.graphics.getDeltaTime()*0.7f);
					
					sprite.setRegion(animations.get(state.GUNMOVEMENT).getCurrentFrame());
					sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
					time++;
				}
				else if(doing.equals("pullingGunOut")){
					time = 0;
					state = State.GUNMOVEMENT;
				}
				else if(doing.equals("puttingGunAway")){
					time = 0;
					state = State.STANDARD;
				}
			}
			if(doing.equals("pullingGunOut")){
				if(time < 83){
					sprite = new Sprite(animations.get(state.ANIMATINGLARGE).getCurrentFrame());
					currentFrame = animations.get(state).doComplexAnimation(64, 1f, Gdx.graphics.getDeltaTime()*0.7f);
					
					sprite.setRegion(animations.get(state).getCurrentFrame());
					sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
					time++;
				}
				else if(doing.equals("pullingGunOut")){
					time = 0;
					state = State.GUNMOVEMENT;
				}
				else if(doing.equals("puttingGunAway")){
					time = 0;
					standingAnimation(animations);
					state = State.STANDARD;
				}
			}
		}
	//ANIMATING
		if(state.equals(State.ANIMATING)){
//			System.out.println(" (PLAYER): I'm currently in ANIMATING state");
			
		}
		
	//HURT
		if(hurt){
//			System.out.println(" (PLAYER): I'm currently in HURT state");
			if(time < 40){
				sprite = new Sprite(animations.get(state.STANDARD).getCurrentFrame());
				
				time++;
				
				if(damageType == "enemy"){
					takingDamageFromEnemy(animations, enemy, touchPos, collisionLayer);
				}
				if(damageType == "lackOfOxygen"){
					currentFrame = animations.get(state.STANDARD).doComplexAnimation(104, 0.2f, Gdx.graphics.getDeltaTime()/2);
					
					sprite.setRegion(animations.get(state).getCurrentFrame());
					sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				}else if(time > 39){
					hurt = false;
					time = 0;
				}
			}
		}
		
	//STANDARD
		if(state.equals(State.STANDARD)){
//			System.out.println(" (PLAYER): I'm currently in STANDARD state");
			sprite = new Sprite(animations.get(state.STANDARD).getCurrentFrame());
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			
			
			//movement
			if(!hurt){
			 	movementCollisionAndAnimation(playerMovementSpeed, animations, touchPos, collisionLayer);
			}
		}
		
	//GUN MOVEMENT
		if(state.equals(State.GUNMOVEMENT)){
//			System.out.println(" (PLAYER): I'm currently in GUNMOVEMENT state");
			sprite = new Sprite(animations.get(state.GUNMOVEMENT).getCurrentFrame());
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			
//			
//			if (!aiming && Gdx.input.justTouched() && !theController.doesIntersect(new Vector2(400,255), circle.radius*2)) {
//		        inputNav();
//		    }	
				
				if(!aiming){
					currentFrame = animations.get(state).doComplexAnimation(0, 0.5f, Gdx.graphics.getDeltaTime());
				}
				
				if(aiming){
					touchPos.x = position.x+9;
					touchPos.y = position.y+4;
				}
				if(aiming && V3point.y > position.y+8 && V3point.x < position.x+32 &&
						V3point.x > position.x){
					currentFrame = animations.get(state).doComplexAnimation(24, 0.5f, Gdx.graphics.getDeltaTime());
				}
				else if(aiming && V3point.y < position.y+8 && V3point.x < position.x+32 &&
						V3point.x > position.x){
					currentFrame = animations.get(state).doComplexAnimation(0, 0.5f, Gdx.graphics.getDeltaTime());
				}
				if(aiming && V3point.x < position.x){
					currentFrame = animations.get(state).doComplexAnimation(8, 0.5f, Gdx.graphics.getDeltaTime());
				}
				else if(aiming && V3point.x > position.x+32){
					currentFrame = animations.get(state).doComplexAnimation(16, 0.5f, Gdx.graphics.getDeltaTime());
				}
				
			if(!Gdx.input.isTouched() && aiming){
				shooting = true;
				
			}
			if(!aiming && timeShooting == 0){
				shooting = false;
			}
			
		}
		
	//DEAD
		if(state.equals(State.DEAD)){
//			System.out.println(" (PLAYER): I'm DEAD :(");
			if(timeDead < 89){
				sprite = new Sprite(animations.get(state.ANIMATING).getCurrentFrame());
				currentFrame = animations.get(state).doComplexAnimation(112, 1.6f, 0.018f);
				
				sprite.setRegion(animations.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				timeDead++;
			}
			
			dead = true;
		}
		
		// Shooting
		if(shooting && timeShooting < 30){
			System.out.println("shooting...");
			currentFrame = animations.get(state).doComplexAnimation(32, 0.5f, 0.001f);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			
			timeShooting++;
		}
		if(shooting && timeShooting < 2){
			shotDir.x = V3point.x;
			shotDir.y = V3point.y;
//			theController.projectile.setPosition(new Vector2(position.x, position.y));
		}
		if(shooting && timeShooting > 29){
			animations.get(state).setCurrentFrame(currentFrame);
			shooting = false;
			timeShooting = 0;
		}
		
		
	}

	private void takingDamageFromEnemy(HashMap<State, AnimationControl> animations, AbstractGameObject enemy, Vector3 touchPos, TiledMapTileLayer collisionLayer) {
		
		Collidable collidableUp = null;
		
		damagedFromTop(collidableUp, animations, enemy, touchPos);
		collidableUp = collisionCheckerUp(collisionLayer);
		collisionCheck(collidableUp, collisionLayer);
		
		Collidable collidableDown = null;
		
		damageFromBottom(collidableDown, animations, enemy, touchPos);
		collidableDown = collisionCheckerDown(collisionLayer);
		collisionCheck(collidableDown, collisionLayer);
		
		Collidable collidableLeft = null;
		
		damageFromLeft(collidableLeft, animations, enemy, touchPos);
		collidableLeft = collisionCheckerLeft(collisionLayer);
		collisionCheck(collidableLeft, collisionLayer);
		
		Collidable collidableRight = null;
		
		damageFromRight(collidableRight, animations, enemy, touchPos);
		collidableRight = collisionCheckerRight(collisionLayer);
		collisionCheck(collidableRight, collisionLayer);
	}
	
	private void damageFromRight(Collidable collidableUp, HashMap<State, AnimationControl> animations, AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "right" && collidableUp == null) { 
			currentFrame = animations.get(state.STANDARD).doComplexAnimation(108, 0.2f, Gdx.graphics.getDeltaTime()/2);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			position.x += playerMovementSpeed/2;
			touchPos.x += playerMovementSpeed/2;
			sprite.translateY(playerMovementSpeed/2);
		}
	}
	private void damageFromLeft(Collidable collidableUp, HashMap<State, AnimationControl> animations, AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "left" && collidableUp == null) { 
			currentFrame = animations.get(state.STANDARD).doComplexAnimation(106, 0.2f, Gdx.graphics.getDeltaTime()/2);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			position.x -= playerMovementSpeed/2;
			touchPos.x -= playerMovementSpeed/2;
			sprite.translateY(playerMovementSpeed/2);
		}
	}
	private void damageFromBottom(Collidable collidableUp, HashMap<State, AnimationControl> animations, AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "down" && collidableUp == null) { 
			currentFrame = animations.get(state.STANDARD).doComplexAnimation(110, 0.2f, Gdx.graphics.getDeltaTime()/2);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			position.y -= playerMovementSpeed/2;
			touchPos.y -= playerMovementSpeed/2;
			sprite.translateY(playerMovementSpeed/2);
		}
	}
	private void damagedFromTop(Collidable collidableUp, HashMap<State, AnimationControl> animations, AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "up" && collidableUp == null) { 
			currentFrame = animations.get(state.STANDARD).doComplexAnimation(104, 0.2f, Gdx.graphics.getDeltaTime()/2);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			
			position.y += playerMovementSpeed/2;
			touchPos.y += playerMovementSpeed/2;
			sprite.translateY(playerMovementSpeed/2);
		}
	}
	
	
	private void movementCollisionAndAnimation(float speed, HashMap<State, AnimationControl> animations, Vector3 touchPos, TiledMapTileLayer collisionLayer) {
		// ---------------------left------------------------ //
		Collidable collidableLeft = null;
		
		moveLeft(collidableLeft, speed, animations, touchPos);
		collidableLeft = collisionCheckerLeft(collisionLayer);
		collisionCheck(collidableLeft, collisionLayer);
		
		// ---------------------right------------------------ //
		Collidable collidableRight = null;
		
		moveRight(collidableRight, speed, animations, touchPos);
		collidableRight = collisionCheckerRight(collisionLayer);
		collisionCheck(collidableRight, collisionLayer);
		
		// ---------------------down------------------------ //
		Collidable collidableDown = null;
		
		moveDown(collidableDown, speed, animations, touchPos);
		collidableDown = collisionCheckerDown(collisionLayer);
		collisionCheck(collidableDown, collisionLayer);
		
		// ---------------------up------------------------ //
		Collidable collidableUp = null;
		
		moveUp(collidableUp, speed, animations, touchPos);
		collidableUp = collisionCheckerUp(collisionLayer);
		collisionCheck(collidableUp, collisionLayer);
		
		standingAnimation(animations);
	}
	private Collidable collisionCheckerUp(TiledMapTileLayer collisionLayer) {
		Collidable collidableUp;
		collidableUp = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y+sprite.getHeight(), collisionLayer);
		if(collidableUp == null)collidableUp = CollisionHelper.isCollidable(position.x, position.y+sprite.getHeight(), collisionLayer);
		if(collidableUp == null)collidableUp = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/4), position.y+sprite.getHeight(), collisionLayer);
		return collidableUp;
	}
	
	private void moveUp(Collidable collidableUp, float speeds, HashMap<State, AnimationControl> animations, Vector3 touchPos) {
		if (position.y < touchPos.y -5 && collidableUp == null) {
			position.y += speeds;
			sprite.translateY(speeds);
			playerMovementDirection = "up";
			if(oldPos.y != position.y){
				currentFrame = animations.get(state).animate(8);
			}
		}
	}
	private Collidable collisionCheckerDown(TiledMapTileLayer collisionLayer) {
		Collidable collidableDown;
		collidableDown = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, collisionLayer);
		if(collidableDown == null)collidableDown = CollisionHelper.isCollidable(position.x, position.y, collisionLayer);
		if(collidableDown == null)collidableDown = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y, collisionLayer);
		return collidableDown;
	}
	
	private void moveDown(Collidable collidableDown, float speeds, HashMap<State, AnimationControl> animations, Vector3 touchPos) {
		if (position.y > touchPos.y -1 && collidableDown == null) {
			position.y -= speeds;
			sprite.translateY(-speeds);
			playerMovementDirection = "down";
			if(oldPos.y != position.y){
				currentFrame = animations.get(state).animate(0);
			}
		}
	}
	private Collidable collisionCheckerRight(TiledMapTileLayer collisionLayer) {
		Collidable collidableRight;
		collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + (sprite.getHeight()/2), collisionLayer);
		if(collidableRight == null)collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, collisionLayer);
		if(collidableRight == null)collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/4), collisionLayer);
		return collidableRight;
	}
	private void moveRight(Collidable collidableRight, float speeds, HashMap<State, AnimationControl> animations, Vector3 touchPos) {
		if (position.x <  touchPos.x -19/2 && collidableRight == null) {
			position.x += speeds; 
			sprite.translateX(speeds);
			playerMovementDirection = "right";
		}
		if(position.x <  touchPos.x -19/2 && position.y < touchPos.y -1 && position.y > touchPos.y -5 && oldPos.x != position.x && collidableRight == null){
			currentFrame = animations.get(state).animate(16);
		}
	}
	private void collisionCheck(Collidable collidableLeft, TiledMapTileLayer collisionLayer) {
		if(collidableLeft != null){
			contact(collidableLeft, collisionLayer);
		}
	}
	private Collidable collisionCheckerLeft(TiledMapTileLayer collisionLayer) {
		Collidable collidableLeft;
		collidableLeft = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2), collisionLayer);
		if(collidableLeft == null)collidableLeft = CollisionHelper.isCollidable(position.x, position.y, collisionLayer);
		if(collidableLeft == null)collidableLeft = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/4), collisionLayer);
		return collidableLeft;
	}
	private void moveLeft(Collidable collidableLeft, float speeds, HashMap<State, AnimationControl> animations, Vector3 touchPos) {
		if (position.x > touchPos.x -16/2 && collidableLeft == null) {
			position.x -= speeds;
			playerMovementDirection = "left";
			sprite.translateX(-speeds);
		}
		if(position.x > touchPos.x -16/2 && position.y < touchPos.y -1 && position.y > touchPos.y -5 && oldPos.x != position.x && collidableLeft == null){
			currentFrame = animations.get(state).animate(24);
		}
	}
	private void standingAnimation(HashMap<State, AnimationControl> animations) {
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
	
	//Collision reaction
	public void contact(Collidable collidable, TiledMapTileLayer collisionLayer){
		collidable.doCollide(this, collisionLayer);
	}
	public String getPlayerMovementDirection() {
		return playerMovementDirection;
	}
	public void setPlayerMovementDirection(String playerMovementDirection) {
		this.playerMovementDirection = playerMovementDirection;
	}
	public float getPlayerMovementSpeedX() {
		return playerMovementSpeed;
	}
	public void setPlayerMovementSpeedX(float playerMovementSpeedX) {
		this.playerMovementSpeed = playerMovementSpeedX;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public boolean isMaskOn() {
		return maskOn;
	}
	public void setMaskOn(boolean maskOn) {
		this.maskOn = maskOn;
	}

	public String getDoing() {
		return doing;
	}

	public void setDoing(String doing) {
		this.doing = doing;
	}

	public boolean isJustSpawned() {
		return justSpawned;
	}

	public void setJustSpawned(boolean justSpawned) {
		this.justSpawned = justSpawned;
	}

	public Vector3 getShotDir() {
		return shotDir;
	}

	public void setShotDir(Vector3 shotDir) {
		this.shotDir = shotDir;
	}
	
	public boolean isHurt() {
		return hurt;
	}

	public void setHurt(boolean hurt) {
		this.hurt = hurt;
	}

	public int getTimeShooting() {
		return timeShooting;
	}

	public void setTimeShooting(int timeShooting) {
		this.timeShooting = timeShooting;
	}
	
	
}
