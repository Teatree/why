package com.me.swampmonster.models;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.me.swampmonster.game.collision.CollisionHelper;

public abstract class Item extends AbstractGameObject{
	public String itemType;
	public static int lifeTime;
	public int deadAnimTimer;
	public static String name = "unidentified";
	public String constatName;
	public boolean spawned;
	
	private int animTimer2;
	private int pendingTimer;
	public static Sprite poisonSprite;
	
	public Vector2 spawnPos;
	public Vector2 targetPos;
	public TiledMapTileLayer collisionLayer;
	public ImageButton pickUpButton;
	public ImageButton throwButton;
	public ImageButton inspectButton;
	public Label itemName;
	
	//Animations
	public Item(){
		state = State.SPAWNING;
		
		circle = new Circle();
		circle.radius = 35;
		
		Random random = new Random();
		this.lifeTime = random.nextInt((800-600) + 600);
//		this.itemType = itemType;
		pendingTimer = 180;
		
//		name = "unidentified";
	}
	
	public void update(){
		circle.x = position.x+8;
		circle.y = position.y+8;
		
		if(state.equals(State.STANDARD)){
			pendingTimer++;
			lifeTime--;
			if (lifeTime == 0) {
				state = State.DESPAWNING;
				deadAnimTimer = 0;
			}

			if (pendingTimer > 180) {
				if(animTimer2<32){
					currentFrame = doItemAnimation(0, 0.9f, 0.03f, Animation.PlayMode.NORMAL);
					animTimer2++;
				}else if(animTimer2==32){
					animTimer2=0;
					pendingTimer=0;
				}
			}
		}
		
		
		if (state.equals(State.SPAWNING)) {
			if (!(Math.round(position.x) == Math.round(targetPos.x)) ) {
//				System.out.println("collisionLayer " + collisionLayer + " position " + position + " sprite " + sprite);
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
			currentFrame = doItemAnimation(0, 0.9f, 0.03f, Animation.PlayMode.NORMAL);
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
	
	private TextureRegion doItemAnimation(int i, float Comparator, float speedAdjust, PlayMode normal){
			return animationsStandard.get(state).doComplexAnimation(i, Comparator, speedAdjust, normal);
	}
	
	public abstract void pickMeUp(Player player);
	
	public abstract void parametersForThrowing(Player player);
}
