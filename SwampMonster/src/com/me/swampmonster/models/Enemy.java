package com.me.swampmonster.models;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.AI.Node;
import com.me.swampmonster.AI.Pathfinder;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.utils.CameraHelper;

public class Enemy extends AbstractGameObject implements Cloneable{
	
	State state = State.STANDARD;
	int cunter;
	int timer;
	int timeDead = 0;
	int timer2;
//	int number;
	
	public Circle gReenAura;
	public Circle oRangeAura;
	
	public Node[] path;
	
	public Enemy(Vector2 position){
		this.position = position;
		gReenAura = new Circle();
		gReenAura.radius = 164;
		oRangeAura = new Circle();
		oRangeAura.radius = 16;
		animationsStandard.put(State.PURSUIT, new AnimationControl("data/Skelenten.png", 8, 16, 8)); 
		animationsStandard.put(State.STANDARD, new AnimationControl("data/Skelenten.png", 8, 16, 8)); 
		animationsStandard.put(State.ATTACKING, new AnimationControl("data/Skelenten.png", 8, 16, 8)); 
		animationsStandard.put(State.ANIMATING, new AnimationControl("data/Skelenten.png", 8, 16, 8)); 
		animationsStandard.put(State.DEAD, new AnimationControl("data/Skelenten.png", 8, 16, 4)); 
		oldPos = position;
		// Timer is for the length of the actual animation
		// Timer2 is for the waiting period
		timer = 0;
		timer2 = 0;
		path = new Node[99];
		
//		number = 0;
		
		// ***Character stats board***
		characterStatsBoard();
		// ***Character stats board***
		
		sprite = new Sprite(animationsStandard.get(State.STANDARD).getCurrentFrame());
	}
	
	public void characterStatsBoard(){
		// HEALTH, DAMAGE, TYPE, TOUGHGUY, COLORSCHEME, ETC.
		health = 2;
		damage = 1;
		playerMovementSpeed = 0.3f;
	}
	
	public void update(TiledMapTileLayer collisionLayer, AbstractGameObject projectile, Player player, CameraHelper cameraHelper, float enemyDx, float enemyDy){
		oldPos.x = position.x;
		oldPos.y = position.y; 
		
		gReenAura.x = position.x;
		gReenAura.y = position.y;
		oRangeAura.x = position.x;
		oRangeAura.y = position.y;
		
		// remember this might be your chance.
		
		if(projectile != null && oRangeAura.overlaps(projectile.getCircle())){
			state = State.DEAD;
		}
		
//		this little thing is not done!
		
		HashMap<State, AnimationControl> animations = animationsStandard;
		
		if(!state.equals(State.STANDARD)){
			timer = 0;
			timer2 = 0;
		}
		
	// ATTACKING!
		
		// Standing animation between attacks doesn't work.
			
			//MOVEMENT + COLLISION PROCESSING AND DETECTION
		
		// PURSUIT!
				if(state.equals(State.PURSUIT)){
            		
					sprite.setRegion(animations.get(state).getCurrentFrame());
					
					//MOVEMENT + COLLISION PROCESSING AND DETECTION
					System.out.println("cunter = " + cunter);
					if(cunter  <= 0){ 
						cunter = findLastNotNullInArray();
					}
					
					onPathMovingAndCollisionDetection(collisionLayer, player);
				        
					orientOnPath();
					standAnimation(88, 72, 80, 64);
					
					
					
//					if(path.length == 0){
//						setState(State.STANDARD);
//					}
				}
		// STANDARD!
			if(state.equals(State.STANDARD)){
				sprite.setRegion(animations.get(state).getCurrentFrame());
            	
            	if(timer == 0 && timer2 == 0){
            		
            		Collidable collidableLeft = null;
            		Collidable collidableRight = null;
            		Collidable collidableDown = null;
            		Collidable collidableUp = null;
            		
	            	moveLeft(player, collidableLeft, collidableRight, collidableDown, collidableUp, enemyDx, enemyDy, playerMovementSpeed);
	            	collidableLeft = collisionCheckerLeft(collisionLayer);
	            	collisionCheck(collidableLeft, collisionLayer, player);
	            	
//	            	moveRight(player);
	            	collidableRight = collisionCheckerRight(collisionLayer);
	            	collisionCheck(collidableRight, collisionLayer, player);
//	            	moveDown(player);
	            	collidableDown = collisionCheckerBottom(collisionLayer);
	            	collisionCheck(collidableDown, collisionLayer, player);
//	            	moveUp(player);
	            	collidableUp = collisionCheckerTop(collisionLayer);
	            	collisionCheck(collidableUp, collisionLayer, player);
//	            	System.out.println("playerDircetion = " + playerMovementDirection);
            	}
            	
            	if(oldPos.x != position.x || oldPos.y != position.y && timer>0 && timer2>0){
            		timer = 0;
            		timer2 = 0;
            	}
            	
            	if(getoRangeAura().overlaps(player.getCircle()) && player.getState() != State.DEAD){
	            	if(playerMovementDirectionLR == "right"){
	            		inflictOnThe(88, 56, player, cameraHelper);
	            	}
	            	if(playerMovementDirectionLR == "left"){
	            		inflictOnThe(72, 40, player, cameraHelper);
	            	}
	            	if(playerMovementDirectionLR == "up"){
	            		inflictOnThe(80, 48, player, cameraHelper);
	            	}
	            	if(playerMovementDirectionLR == "down"){
	            		inflictOnThe(64, 32, player, cameraHelper);
	            	}
            	}
            	if(!getoRangeAura().overlaps(player.getCircle())){
            		timer = 0;
            		timer2 = 0;
            	}
		}
			//ANIMATING
			if(state.equals(State.ANIMATING)){
//				System.out.println(" (PLAYER): I'm currently in ANIMATING state");
				
			}
			//DEAD
			if(state.equals(State.DEAD)){
//				System.out.println(" (PLAYER): I'm DEAD :(");
				if(timeDead < 65){
					currentFrame = animations.get(state).doComplexAnimation(96, 2f, 0.03f);
					
					sprite.setRegion(animations.get(state).getCurrentFrame());
					sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
					timeDead++;
				}
				
				dead = true;
			}
	}

	private void inflictOnThe(int standing, int animation, Player player, CameraHelper cameraHelper) {
		// Timer is for the length of the actual animation
		// Timer2 is for the waiting period
		if(oldPos.x == position.x && oldPos.y == position.y){
			if(timer2 < 40){
				timer2++;
//            			System.out.println("timer2: " + timer2 );
				currentFrame = animationsStandard.get(state).animate(standing);
			}
			if(timer2 >= 40 && timer < 30){
				cameraHelper.setShakeAmt(25);
				cameraHelper.cameraShake();
//            			System.out.println("timer1: " + timer);
				currentFrame = animationsStandard.get(state).doComplexAnimation(animation, 1.8f, Gdx.graphics.getDeltaTime());
				
				sprite.setRegion(animationsStandard.get(state).getCurrentFrame());
				sprite.setBounds(sprite.getX(), sprite.getY(), 16, 32);
				timer++;
				if(timer == 30 && timer2 >= 40){
					currentFrame = animationsStandard.get(state).animate(standing);
					// And may be inflict different hurts, direction/ kinds of hurts/ etc.
					player.setDamageType("enemy");
					player.addHarmfulEnemy(this);
					player.setHurt(true);
					player.setHealth(player.getHealth()-damage);
					
					timer = 0;
					timer2 = 0;
				}
			}
		}
	}

	private void onPathMovingAndCollisionDetection(TiledMapTileLayer collisionLayer, AbstractGameObject player) {
		if(cunter >= 0){
			moveLeftOnPath();
			Collidable collidable = collisionCheckerLeft(collisionLayer);
			collisionCheck(collidable, collisionLayer, player);
		
			moveRightOnPath();
			collidable = collisionCheckerRight(collisionLayer);
			collisionCheck(collidable, collisionLayer, player);

			moveDownOnPath();
			collidable = collisionCheckerBottom(collisionLayer);
			collisionCheck(collidable, collisionLayer, player);

			moveUpOnPath();
			collidable = collisionCheckerTop(collisionLayer);
			collisionCheck(collidable, collisionLayer, player);
			if(cunter == 0){
				System.out.println("happened");
				path[cunter] = null;
//				if(number < 99){
//					clear();
//				}
				state = State.STANDARD;
			}
		}
	}

	private Collidable collisionCheckerTop(TiledMapTileLayer collisionLayer) {
		Collidable collidable;
		collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), (position.y+sprite.getHeight()/2)-1, collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, (position.y+sprite.getHeight()/2)-1, collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), (position.y+sprite.getHeight()/2)-1, collisionLayer);
		return collidable;
	}

	private Collidable collisionCheckerBottom(TiledMapTileLayer collisionLayer) {
		Collidable collidable;
		collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+(sprite.getWidth()/2), position.y, collisionLayer);
		return collidable;
	}

	private Collidable collisionCheckerRight(TiledMapTileLayer collisionLayer) {
		Collidable collidable;
		collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y + (sprite.getHeight()/2)-1, collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y, collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x+sprite.getWidth(), position.y +(sprite.getHeight()/4), collisionLayer);
		return collidable;
	}

	private Collidable collisionCheckerLeft(TiledMapTileLayer collisionLayer) {
		Collidable collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/2)-1, collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y, collisionLayer);
		if(collidable == null)collidable = CollisionHelper.isCollidable(position.x, position.y + (sprite.getHeight()/4), collisionLayer);
		return collidable;
	}

	private void moveLeft(AbstractGameObject player, Collidable collidableLeft, Collidable collidableRight, Collidable collidableDown, Collidable collidableUp, float enemyDx, float enemyDy, float playerMovementSpeed) {
		if (position.x > player.getPosition().x-4 || position.x < player.getPosition().x-10 || position.y > player.getPosition().y-4 || position.y < player.getPosition().y-10) {
			if(collidableLeft == null || collidableRight == null){
				position.x += enemyDx*playerMovementSpeed;
			}
			if(collidableUp == null || collidableDown == null){
				position.y += enemyDy*playerMovementSpeed;
			}
//			sprite.translateX(-playerMovementSpeed);
		}
		
		if(position.x > player.getPosition().x-10){
			playerMovementDirectionLR = "left";
		}
		if(position.x < player.getPosition().x-10){
			playerMovementDirectionLR = "right";
		}
		if(position.y > player.getPosition().y-10){
			playerMovementDirectionUD = "down";
		}
		if(position.y < player.getPosition().y-10){
			playerMovementDirectionUD = "up";
		}
		
		if(Math.abs((enemyDx*playerMovementSpeed))>Math.abs((enemyDy*playerMovementSpeed)) && enemyDx>0){
			playerMovementDirection = "right";
			currentFrame = animationsStandard.get(state).animate(16);
		}
		if(Math.abs((enemyDx*playerMovementSpeed))>Math.abs((enemyDy*playerMovementSpeed)) && enemyDx<0){
			playerMovementDirection = "left";
			currentFrame = animationsStandard.get(state).animate(24);
		}
		if(Math.abs((enemyDx*playerMovementSpeed))<Math.abs((enemyDy*playerMovementSpeed)) && enemyDy<0){
			playerMovementDirection = "down";
			currentFrame = animationsStandard.get(state).animate(0);
		}
		if(Math.abs((enemyDx*playerMovementSpeed))<Math.abs((enemyDy*playerMovementSpeed)) && enemyDy>0){
			playerMovementDirection = "up";
			currentFrame = animationsStandard.get(state).animate(8);
		}
	}
	
	private void moveRight(AbstractGameObject player) {
		if (position.x < player.getPosition().x-6) {
			position.x += playerMovementSpeed;
			sprite.translateX(playerMovementSpeed);
			playerMovementDirectionLR = "right";
			if(position.x < player.getPosition().x-6 && position.y < player.getPosition().y+3 && position.y > player.getPosition().y-3){
				currentFrame = animationsStandard.get(state).animate(24);
			}        
		}
		
	}
	private void moveDown(AbstractGameObject player) {
		if (position.y > player.getPosition().y+3) {
		    position.y -= playerMovementSpeed;
		    sprite.translateY(-playerMovementSpeed);
		    playerMovementDirectionLR = "down";
		    currentFrame = animationsStandard.get(state).animate(0);
         }
	}

	private void moveUp(AbstractGameObject player) {
		if (position.y < player.getPosition().y-3) {
		    position.y += playerMovementSpeed;
		    sprite.translateY(playerMovementSpeed);
		    playerMovementDirectionLR = "up";
		    currentFrame = animationsStandard.get(state).animate(16);
		 }
	}

	private void collisionCheck(Collidable collidable, TiledMapTileLayer collisionLayer, AbstractGameObject player) {
		if(collidable != null){
			contact(collidable, collisionLayer, player);
		}
	}

	private void moveUpOnPath() {
		if (path[cunter] != null && (int)position.y < (int)(path[cunter].y*16)) {
			position.y += playerMovementSpeed;
			sprite.translateY(playerMovementSpeed);
			playerMovementDirectionLR = "up";
			currentFrame = animationsStandard.get(state).animate(16);
		}
	}

	private void moveDownOnPath() {
		if (path[cunter] != null && (int)position.y > (int)(path[cunter].y*16)) {
			position.y -= playerMovementSpeed;
			sprite.translateY(-playerMovementSpeed);
			playerMovementDirectionLR = "down";
		   	currentFrame = animationsStandard.get(state).animate(0);
		}
	}

	private void moveRightOnPath() {
		if (path[cunter] != null && (int)position.x < (int)(path[cunter].x*16)) {
			position.x += playerMovementSpeed;
			sprite.translateX(playerMovementSpeed);
			playerMovementDirectionLR = "right";
			if(playerMovementDirectionLR != "down" && playerMovementDirectionLR != "up"){
				currentFrame = animationsStandard.get(state).animate(24);
			}
		}
	}

	private void moveLeftOnPath() {
		if (path[cunter] != null && (int)position.x > (int)(path[cunter].x*16)) {
			position.x -= playerMovementSpeed;
			sprite.translateX(-playerMovementSpeed);
			playerMovementDirectionLR = "left";
			if(playerMovementDirectionLR != "down" && playerMovementDirectionLR != "up"){
				currentFrame = animationsStandard.get(state).animate(8);
			}
		}
	}

	private void standAnimation(int r, int l, int u, int d) {
		if(oldPos.x == position.x && oldPos.y == position.y){
			if(playerMovementDirectionLR == "right"){
				currentFrame = animationsStandard.get(state).animate(r);
			}
			if(playerMovementDirectionLR == "left"){
				currentFrame = animationsStandard.get(state).animate(l);
			}
			if(playerMovementDirectionLR == "up"){
				currentFrame = animationsStandard.get(state).animate(u);
			}
			if(playerMovementDirectionLR == "down"){
				currentFrame = animationsStandard.get(state).animate(d);
			}
		}
	}

	private void orientOnPath() {
		if(cunter>=0 && path[cunter] != null && position.x >= (path[cunter].x*16)-1 && position.x <= (path[cunter].x*16)+1
				&& position.y <= (path[cunter].y*16)+1 && position.y >= (path[cunter].y*16)-1){
				path[cunter] = null;
				cunter--;
		}
	}
	
	
	
	private void contact(Collidable collidable, TiledMapTileLayer collisionLayer, AbstractGameObject player) {
		collidable.doCollide(this, collisionLayer);
		Pathfinder.findPathInThreadPool(position, player.position, collisionLayer, this);
//		System.out.println(position.x);
//		System.out.println(theController.level1.getPlayer().position.x);
		state = State.PURSUIT;
	}
//	private void clear(){
//		while(number < 99){
//			path[number] = null;
//			number++;
//		}
//	}

	public Vector2 getOldPos() {
		return oldPos;
	}
	
	public void setOldPos(Vector2 oldPos) {
		this.oldPos = oldPos;
	}


	public String getPlayerMovementDirection() {
		return playerMovementDirectionLR;
	}


	public void setPlayerMovementDirection(String playerMovementDirection) {
		this.playerMovementDirectionLR = playerMovementDirection;
	}

	public void doCollide(Player player) {
		
	}
	
	private int findLastNotNullInArray(){
		int i = 0;
		if (path != null)
			while(path[i] != null){
				i++;
			}
		return i - 1;
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

	public Node[] getPath() {
		return path;
	}

	public void setPath(Node[] path) {
		this.path = path;
	}
	
	
}
