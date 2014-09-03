package com.me.swampmonster.models;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.collision.CollisionHelper;

public abstract class Item extends AbstractGameObject{
	public String itemType;
	public static int lifeTime;
	public int deadAnimTimer;
	public boolean spawned;
	
	private int animTimer2;
	private int pendingTimer;
	
	public Vector2 spawnPos;
	public Vector2 targetPos;
	public TiledMapTileLayer collisionLayer;
	
	//Animations
	public Item(){
		state = State.SPAWNING;
		
		circle = new Circle();
		circle.radius = 16;
		
		Random random = new Random();
		this.lifeTime = random.nextInt((800-600) + 600);
//		this.itemType = itemType;
		pendingTimer = 180;
		
		
	}
	
	public void update(){
		circle.x = position.x;
		circle.y = position.y;
		
		if(state.equals(State.STANDARD)){
			pendingTimer++;
			lifeTime--;
			if (lifeTime == 0) {
				state = State.DESPAWNING;
				deadAnimTimer = 0;
			}

			if (pendingTimer > 180) {
				if(animTimer2<32){
					currentFrame = doItemAnimation(0, 0.9f, 0.03f, Animation.NORMAL);
					animTimer2++;
				}else if(animTimer2==32){
					animTimer2=0;
					pendingTimer=0;
				}
			}
		}
		
		if (state.equals(State.SPAWNING)) {
			if (!(Math.round(position.x) == Math.round(targetPos.x)) ) {
				if (CollisionHelper.isCollidable(position.x + sprite.getWidth(), position.y, collisionLayer) != null ||
						CollisionHelper.isCollidable(position.x, position.y + sprite.getHeight(), collisionLayer) != null
						|| CollisionHelper.isCollidable(position.x, position.y, collisionLayer) != null) {
					state = State.STANDARD;
				}
				if (position.x < targetPos.x) {
					position.x += 0.8f;
					position.y += 4.33 * Math.sin(0.1 * position.x);
				} else {
					position.x -= 0.8f;
					position.y += 4.33 * Math.sin(0.1 * position.x);
				}
			} else {
				state = State.STANDARD;
			}
			currentFrame = doItemAnimation(0, 0.9f, 0.03f, Animation.NORMAL);
		}
		
		sprite = new Sprite(new TextureRegion(currentFrame));
		
		if (state.equals(State.DESPAWNING)) {
			if (deadAnimTimer == 4 || deadAnimTimer == 34 || deadAnimTimer == 54 
					|| deadAnimTimer == 74 || deadAnimTimer == 94) {
				sprite = null;
			}
			if (deadAnimTimer == 24 || deadAnimTimer == 44 || deadAnimTimer == 64 
					|| deadAnimTimer == 84 || deadAnimTimer == 104) {
				sprite = new Sprite(new TextureRegion(currentFrame));
			}
			deadAnimTimer++;
			if (deadAnimTimer == 105) {
				this.state = State.DEAD;
				deadAnimTimer = 0;
			}
		}
		
	}
	
	private TextureRegion doItemAnimation(int i, float Comparator, float speedAdjust, int playMode){
			return animationsStandard.get(state).doComplexAnimation(i, Comparator, speedAdjust, playMode);
	}
	
	public abstract void pickMeUp(Player player);
}
