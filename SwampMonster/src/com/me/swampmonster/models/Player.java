package com.me.swampmonster.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.utils.AssetsMainManager;

public class Player extends AbstractGameObject{
	
	private static final float FROZEN_MOVEMENT = 0.666f;
	private static final int RADIOACTIVE_RADIUS = 57;
	private static final float SPEED_BOOST_EFFECT = 1.1f;
	
	int time = 0;
	int timeDead = 0;
	private int timeShooting = 0;
	String nastyaSpriteStandard;
	String nastyaSpriteGun;
	// responsible for what kind of animation are to be played in the Animating State
	String doing;
	// responsible for what kind of animation are to be played in the Animating State, to be changed to something better
	public boolean maskOn;
	public boolean justSpawned;
	public boolean shooting;
	public List<Projectile> projectiles;
	public Vector3 shotDir;
	Vector3 V3playerPos;
	public float oxygen;
	public float maxOxygen;
	public int positiveEffectCounter;
	public int negativeEffectCounter;
	
	public Circle aimingArea;
	public Circle invalidSpawnArea;
	
	public Enemy harmfulEnemy;
	
	public Circle radioactiveAura = null;
	public int radioactiveDamage = 1;
	
	public Player(Vector2 position){
		state = State.STANDARD;
		setPositiveEffect(PositiveEffectsState.FADE);
//		setNegativeEffect(NegativeEffectsState.FROZEN);
		this.position = position;
		
		points = 0;
		aimingArea = new Circle();
		aimingArea.radius = 8;
		invalidSpawnArea = new Circle();
		invalidSpawnArea.radius = 90;
		circle = new Circle();
		circle.radius = 16;
		V3playerPos = new Vector3();
		rectanlge = new Rectangle();
		projectiles = new LinkedList<Projectile>();

//		projectiles.add(new Projectile(position, 12f));
		
		animationsStandard.put(State.STANDARD, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.nastyaSpriteStandard), 8, 32, 7)); 
		animationsStandard.put(State.ANIMATING, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.nastyaSpriteStandard), 8, 32, 8)); 
		animationsStandard.put(State.ANIMATINGLARGE, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.nastyaSpriteStandard), 8, 32, 8)); 
		animationsStandard.put(State.ACTIVATING, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.nastyaSpriteStandard), 8, 32, 8)); 
		animationsStandard.put(State.GUNMOVEMENT, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.nastyaSpriteGun), 8, 16, 7)); 
		animationsStandard.put(State.DEAD, new AnimationControl(AssetsMainManager.manager.get(AssetsMainManager.nastyaSpriteStandard), 8, 32, 8)); 
		
		oldPos = position;
		
		dead = false;
		maskOn = true;
		justSpawned = true;
		shooting = false;
		
		// ***Character stats board***
		characterStatsBoard();
		// ***Character stats board***
		sprite = new Sprite(animationsStandard.get(State.STANDARD).getCurrentFrame());
		sprite.setColor(1,1,1,0.39f);
		shotDir = new Vector3();
		
		allowedToShoot = true;
	}
	
	public void characterStatsBoard(){
		// HEALTH, DAMAGE, OXYGEN, TYPE, TOUGHGUY, COLORSCHEME, ETC.
		maxHealth = 6;
		health = maxHealth;
		maxOxygen = 96;
		oxygen = maxOxygen;
		damage = 1;
		//:TODO IN ORDER TO CHANGE THIS, YOU GOT TO GET DOWN TO WHERE SHOOTIGN IS HAPPENING!
		shotCoolDown = 90;
	}
	
	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point, TiledMapTileLayer collisionLayer, float dx, float dy) {
		oldPos.x = position.x;
		oldPos.y = position.y;
		
		circle.x = position.x+sprite.getWidth()/2;
		circle.y = position.y+sprite.getHeight()/2;
		
		aimingArea.x = position.x+sprite.getWidth()/2;
		aimingArea.y = position.y+sprite.getHeight()/2;
		invalidSpawnArea.x = position.x+8;
		invalidSpawnArea.y = position.y+16;
	
		V3playerPos.x = position.x + circle.radius/2;
		V3playerPos.y = position.y + circle.radius/2;
		V3playerPos.z = 0;
		
		sprite.setX(position.x);
		sprite.setY(position.y);
		
		rectanlge.x = sprite.getX();
		rectanlge.y = sprite.getY();
		rectanlge.width = sprite.getWidth();
		rectanlge.height = sprite.getHeight();
		
		oxygen -= 0.05f;
		
		HashMap<State, AnimationControl> animations = new HashMap<AbstractGameObject.State, AnimationControl>();
		
		animations = animationsStandard;
		
		if(Gdx.input.justTouched()){
			justSpawned = false;
		}
		
		if(positiveEffectCounter<=0){
			positiveEffectsState = PositiveEffectsState.NONE;
			radioactiveAura = null;
		}else{
			positiveEffectCounter--;
		}
		
		if(negativeEffectCounter<=0){
			negativeEffectsState = NegativeEffectsState.NONE;
		}else{
			negativeEffectCounter--;
		}
		
		
	//ANIMATINGLARGE
		if(state.equals(State.ANIMATINGLARGE)){
			
		}
	//ANIMATING
		if(state.equals(State.ANIMATING)){
//			// System.out.println(" (PLAYER): I'm currently in ANIMATING state");
			
		}
		
		
	//STANDARD
		if(state.equals(State.STANDARD)){
			sprite = new Sprite(animations.get(State.STANDARD).getCurrentFrame());
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			
			//movement
			if(!hurt){
			 	movementCollisionAndAnimation(movementSpeed, animations, touchPos, collisionLayer, dx, dy);
			}
		}
		
	//GUN MOVEMENT
		if(state.equals(State.GUNMOVEMENT)){
//			// System.out.println(" (PLAYER): I'm currently in GUNMOVEMENT state");
			sprite = new Sprite(animations.get(State.GUNMOVEMENT).getCurrentFrame());
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			
//			
//			if (!aiming && Gdx.input.justTouched() && !theController.doesIntersect(new Vector2(400,255), circle.radius*2)) {
//		        inputNav();
//		    }	
				
				if(!aiming){
					currentFrame = animations.get(state).doComplexAnimation(0, 0.5f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
				}
				
				if(aiming){
					touchPos.x = position.x+9;
					touchPos.y = position.y+4;
				}
				if(aiming && V3point.y > position.y+8 && V3point.x < position.x+32 &&
						V3point.x > position.x){
					currentFrame = animations.get(state).doComplexAnimation(24, 0.5f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
				}
				else if(aiming && V3point.y < position.y+8 && V3point.x < position.x+32 &&
						V3point.x > position.x){
					currentFrame = animations.get(state).doComplexAnimation(0, 0.5f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
				}
				if(aiming && V3point.x < position.x){
					currentFrame = animations.get(state).doComplexAnimation(8, 0.5f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
				}
				else if(aiming && V3point.x > position.x+32){
					currentFrame = animations.get(state).doComplexAnimation(16, 0.5f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
				}
				
			if(!Gdx.input.isTouched() && aiming && allowedToShoot){
				shooting = true;
				allowedToShoot = false;
			}
			if(!aiming && timeShooting == 0){
				shooting = false;
				state = State.STANDARD;
			}
			if(shooting){
				aiming=false;
			}
			
		}
		
	//DEAD
		if(state.equals(State.DEAD)){
//			// System.out.println(" (PLAYER): I'm DEAD :(");
			if(timeDead < 89){
				sprite = new Sprite(animations.get(State.ANIMATING).getCurrentFrame());
				currentFrame = animations.get(state).doComplexAnimation(112, 1.6f, 0.018f, Animation.NORMAL);
				
				sprite.setRegion(animations.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				timeDead++;
			}
			
			dead = true;
		}
		
		//Hurt
		if (hurt && !positiveEffectsState.equals(PositiveEffectsState.FADE)) {
			// // System.out.println(" (PLAYER): I'm currently in HURT state");
			if (time < 40) {
				sprite = new Sprite(animations.get(State.STANDARD).getCurrentFrame());
				
				time++;

				if (damageType == "enemy") {
					takingDamageFromEnemy(animations, harmfulEnemy, touchPos, collisionLayer);
				}
				if (damageType == "lackOfOxygen") {
					currentFrame = animations.get(State.STANDARD)
							.doComplexAnimation(104, 0.2f,
									Gdx.graphics.getDeltaTime() / 2,
									Animation.NORMAL);

					sprite.setRegion(animations.get(state).getCurrentFrame());
					sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				} else if (time > 39) {
					hurt = false;
					time = 0;
				}
			}
		}
		
		// Shooting
		if(shooting && timeShooting < 30){
			if (positiveEffectsState == PositiveEffectsState.FADE){
				positiveEffectsState = PositiveEffectsState.NONE;
			}
//			// System.out.println("shooting...");
			currentFrame = animations.get(state).doComplexAnimation(32, 0.5f, 0.001f, Animation.NORMAL);
			
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
			shooting=false;
			timeShooting = 0;
		}
		if(!allowedToShoot){
			shotCoolDown--;
		}
		if(!allowedToShoot && shotCoolDown<2){
			allowedToShoot=true;
			// THIS IS WERID, BUT EVERY TIME YOU WANT TO CAHNGE YOUR 
			// SHOOTING COOLDOWN SPEED, YOU GOTTA GET DOWN HERE
			shotCoolDown = 90;
		}
		
		
		// PROJECTILE
		if(shooting && timeShooting < 2){
			float direction_x = shotDir.x - V3playerPos.x;
			float direction_y = shotDir.y - V3playerPos.y;
			
			//: TODO This look terrible, make it better bro...
			Projectile p = new Projectile(new Vector2(aimingArea.x+direction_x/100-8, aimingArea.y+direction_y/100-8), getRotation());
			// System.out.println("direction_x and aimingArea.x" + direction_x + aimingArea.x);
			
			p.setPosition(new Vector2(aimingArea.x+direction_x/100-8, aimingArea.y+direction_y/100-8));
			
			float length =(float) Math.sqrt(direction_x*direction_x + direction_y*direction_y);
			direction_x /= length;
			direction_y /= length;
			
			p.setDirection(direction_x, direction_y);
			
			projectiles.add(p);
			
		}
		
		
		Iterator<Projectile> prj = projectiles.iterator();
		while (prj.hasNext()) {
			Projectile p = (Projectile) prj.next();
			if (p != null && p.isCollision(collisionLayer)) {
				prj.remove();
				break;
			}
			if (p != null) {
				p.update();
			}
		}

		switch (positiveEffectsState) {
		case FADE:
			this.sprite.setColor(sprite.getColor().r, sprite.getColor().g,
					sprite.getColor().b, 0.5f);
			break;
		case RADIOACTIVE_AURA:
			radioactiveAura = new Circle(position.x + sprite.getWidth()/2, position.y + sprite.getHeight()/2, RADIOACTIVE_RADIUS);
			break;
		case SPEED_BOOST:
			this.sprite.setColor(1f, 1f, 0f, 1f);
			movementSpeed = SPEED_BOOST_EFFECT;
			break;
		default:
			break;
		}
		
		switch (negativeEffectsState){
		case FEAR:
			break;
		case FROZEN:
			this.sprite.setColor(4/255f, 180/255f, 1f, 1f);
			movementSpeed = FROZEN_MOVEMENT;
			break;
		case NONE:
			break;
		case POISONED:
			break;
		default:
			break;
		}
	}
	
	private void setPositiveEffect(PositiveEffectsState positiveEffect){
		positiveEffectsState = positiveEffect;
		positiveEffectCounter = positiveEffect.lifetime;
	}

	private void setNegativeEffect (NegativeEffectsState negativeEffect){
		negativeEffectsState = negativeEffect;
		negativeEffectCounter = negativeEffect.lifetime;
	}
	
	private void takingDamageFromEnemy(HashMap<State, AnimationControl> animations, AbstractGameObject enemy, Vector3 touchPos, TiledMapTileLayer collisionLayer) {
//		// System.out.println(enemy.getPlayerMovementDirection());
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
	
	private void damageFromRight(Collidable collidableUp,
			HashMap<State, AnimationControl> animations,
			AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "right" && collidableUp == null) {
			currentFrame = animations.get(State.STANDARD).doComplexAnimation(
					108, 0.2f, Gdx.graphics.getDeltaTime() / 2,
					Animation.NORMAL);

			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			position.x += movementSpeed / 2;
			touchPos.x += movementSpeed / 2;
			sprite.translateY(movementSpeed / 2);
		}
	}
	private void damageFromLeft(Collidable collidableUp, HashMap<State, AnimationControl> animations, AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "left" && collidableUp == null) { 
			currentFrame = animations.get(State.STANDARD).doComplexAnimation(106, 0.2f, Gdx.graphics.getDeltaTime()/2, Animation.NORMAL);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			position.x -= movementSpeed/2;
			touchPos.x -= movementSpeed/2;
			sprite.translateY(movementSpeed/2);
		}
	}
	private void damageFromBottom(Collidable collidableUp, HashMap<State, AnimationControl> animations, AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "down" && collidableUp == null) { 
			currentFrame = animations.get(State.STANDARD).doComplexAnimation(110, 0.2f, Gdx.graphics.getDeltaTime()/2, Animation.NORMAL);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			position.y -= movementSpeed/2;
			touchPos.y -= movementSpeed/2;
			sprite.translateY(movementSpeed/2);
		}
	}
	private void damagedFromTop(Collidable collidableUp, HashMap<State, AnimationControl> animations, AbstractGameObject enemy, Vector3 touchPos) {
		if (enemy.playerMovementDirection == "up" && collidableUp == null) { 
			currentFrame = animations.get(State.STANDARD).doComplexAnimation(104, 0.2f, Gdx.graphics.getDeltaTime()/2, Animation.NORMAL);
			
			sprite.setRegion(animations.get(state).getCurrentFrame());
			sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
			
			position.y += movementSpeed/2;
			touchPos.y += movementSpeed/2;
			sprite.translateY(movementSpeed/2);
		}
	}
	
	
	private void movementCollisionAndAnimation(float speed, HashMap<State, AnimationControl> animations, Vector3 touchPos, TiledMapTileLayer collisionLayer, float dx, float dy) {
		// ---------------------movement, just, movement------------------------ //
		Collidable collidableLeft = null;
		Collidable collidableRight = null;
		Collidable collidableDown = null;
		Collidable collidableUp = null;
		
		move(collidableLeft, collidableRight, collidableUp, collidableDown, speed, animations, touchPos, dx, dy);
		collidableLeft = collisionCheckerLeft(collisionLayer);
		collisionCheck(collidableLeft, collisionLayer);
		collidableRight = collisionCheckerRight(collisionLayer);
		collisionCheck(collidableRight, collisionLayer);
		collidableDown = collisionCheckerDown(collisionLayer);
		collisionCheck(collidableDown, collisionLayer);
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
	
	private Collidable collisionCheckerDown(TiledMapTileLayer collisionLayer) {
		Collidable collidableDown;
		collidableDown = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, collisionLayer);
		if(collidableDown == null)collidableDown = CollisionHelper.isCollidable(position.x, position.y, collisionLayer);
		if(collidableDown == null)collidableDown = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y, collisionLayer);
		return collidableDown;
	}
	
	private Collidable collisionCheckerRight(TiledMapTileLayer collisionLayer) {
		Collidable collidableRight;
		collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + (sprite.getHeight()/2), collisionLayer);
		if(collidableRight == null)collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, collisionLayer);
		if(collidableRight == null)collidableRight = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/4), collisionLayer);
		return collidableRight;
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
	private void move(Collidable collidableLeft, Collidable collidableRight, Collidable collidableUp, Collidable collidableDown, float speeds, HashMap<State, AnimationControl> animations, Vector3 touchPos, float dx, float dy) {
		if (position.x > touchPos.x-4 || position.x < touchPos.x-14 || position.y > touchPos.y-4 || position.y < touchPos.y-14) {
			if(collidableLeft == null || collidableRight == null){
				position.x += dx*movementSpeed;
//				// System.out.println("dx*playerMovementSpeed: " + dx*playerMovementSpeed + " position.x: " + position.x);
			}
			if(collidableUp == null || collidableDown == null){
				position.y += dy*movementSpeed;
			}
			sprite.translateX(-speeds);
		}
		
		if(position.x > touchPos.x-4){
			playerMovementDirectionLR = "left";
		}
		if(position.x < touchPos.x-14){
			playerMovementDirectionLR = "right";
		}
		if(position.y > touchPos.y-4){
			playerMovementDirectionUD = "down";
		}
		if(position.y < touchPos.y-14){
			playerMovementDirectionUD = "up";
		}
		
		if(Math.abs((dx*movementSpeed))>Math.abs((dy*movementSpeed)) && playerMovementDirectionLR == "right"){
			playerMovementDirection = "right";
			currentFrame = animationsStandard.get(state).animate(16);
		}
		if(Math.abs((dx*movementSpeed))>Math.abs((dy*movementSpeed)) && playerMovementDirectionLR == "left"){
			playerMovementDirection = "left";
			currentFrame = animationsStandard.get(state).animate(24);
		}
		if(Math.abs((dx*movementSpeed))<Math.abs((dy*movementSpeed)) && playerMovementDirectionUD == "down"){
			playerMovementDirection = "down";
			currentFrame = animationsStandard.get(state).animate(0);
		}
		if(Math.abs((dx*movementSpeed))<Math.abs((dy*movementSpeed)) && playerMovementDirectionUD == "up"){
			playerMovementDirection = "up";
			currentFrame = animationsStandard.get(state).animate(8);
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
	public float getPlayerMovementSpeedX() {
		return movementSpeed;
	}
	public void setPlayerMovementSpeedX(float playerMovementSpeedX) {
		this.movementSpeed = playerMovementSpeedX;
	}
	
	
//	public void getPerkEffect(Perks perk){
//		this.movementSpeed += perk.speed;
//		this.reloadSpeed += perk.reloadSpeed;
//		this.damage += perk.damage;
//		this.health += perk.health;
//		this.points += perk.points;
//	}

	private float getRotation(){
		double angle1 = Math.atan2(V3playerPos.y - position.y,
				V3playerPos.x - 0);
		double angle2 = Math.atan2(V3playerPos.y - shotDir.y,
				V3playerPos.x - shotDir.x);
		return (float)(angle2-angle1);
	}
}
