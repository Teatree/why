package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;

public class Explosion {
	public static final String EXPLOSION_TYPE_STANDART = "standart";
	public static final String EXPLOSION_TYPE_FROST = "frost";
	private static final float EXPLOSION_PUSH_FORCE = 2.2f;
	
	public Circle explCircle;
	public float damage;
	public ParticleEffect explosionEffect;
	public float incrementalDamageValue;
	public float incrementalCircleValue;
	public Vector2 position;
	public String type = EXPLOSION_TYPE_STANDART;
	public int causeDamageCounter;
	
	float explosion_dx;
	float explosion_dy;
	private int explosionLifeTime;
	private int explodionLifeTimeCounter;
	
	public Explosion(Vector2 position){
		this.position = position;
		this.damage = 1.8f;
		incrementalCircleValue = 4f;
		incrementalDamageValue = 0.06f;
		explCircle = new Circle();
		explCircle.radius = 90;
		explosionLifeTime = 30;
	}
	
	public void update(){
		if (/*explosionEffect != null && !explosionEffect.isComplete()*/explodionLifeTimeCounter < explosionLifeTime /*&& damage > incrementalDamageValue*/){
			explCircle.radius += incrementalCircleValue;
			this.damage -= incrementalDamageValue;
			explodionLifeTimeCounter++;
		}else if(/*explosionEffect != null && */explodionLifeTimeCounter >= explosionLifeTime /*explosionEffect.isComplete()*/){
			explCircle.radius = 0;
		}
	}
	
	public void cause(AbstractGameObject ago, TiledMapTileLayer collisionLayer){
		ago.hurt = true;
		ago.exploding = true;
//		if (ago instanceof Enemy){
//			try {
//				ago.getClass().getField("path").set(ago.getClass().getField("path"), new Node[99]); 
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
//		Collidable cL = CollisionHelper.isCollidable(ago.position.x, ago.position.y + ago.sprite.getHeight()/2, collisionLayer);
//		Collidable cR = CollisionHelper.isCollidable(ago.position.x + ago.sprite.getWidth(), ago.position.y + ago.sprite.getHeight()/2, collisionLayer);
//		Collidable cU = CollisionHelper.isCollidable(ago.position.x + ago.sprite.getWidth()/2, ago.position.y + ago.sprite.getHeight(), collisionLayer);
//		Collidable cD = CollisionHelper.isCollidable(ago.position.x + ago.sprite.getWidth()/2, ago.position.y, collisionLayer);
//		
//		if (cL == null && ago.getDx() <= 0 ||
//				cR == null && ago.getDx() > 0){
//			position.x += ago.getDx() * ago.movementSpeed*4;
//		} 
//		if (cD == null && ago.getDy() < 0 
//				|| cU == null && ago.getDy()  >= 0){
//			position.y += ago.getDy()  * ago.movementSpeed*4;
//		} 
		explosion_dx = ago.position.x - position.x;
		explosion_dy = ago.position.y - position.y;

		float length1 = (float) Math.sqrt(explosion_dx * explosion_dx + explosion_dy * explosion_dy);
		explosion_dx /= length1;
		explosion_dy /= length1;
		causeDamageCounter++;
		if (type != EXPLOSION_TYPE_FROST){
			if (causeDamageCounter % 15 == 0 && ago.health > 0){
				ago.health -= this.damage;
			}
		} else {
			ago.setNegativeEffect(NegativeEffects.FROZEN);
		}
//		ago.collidableLeft = ago.collisionCheckerRight(collisionLayer);
//		ago.collidableRight = ago.collisionCheckerLeft(collisionLayer);
//		ago.collidableDown = ago.collisionCheckerTop(collisionLayer);
//		ago.collidableUp = ago.collisionCheckerBottom(collisionLayer);
		
//		System.out.println("collidableLeft = " + ago.collidableLeft);
//		System.out.println("collidableRight = " + ago.collidableRight);
//		System.out.println("collidableUp = " + ago.collidableUp);
//		System.out.println("collidableDown = " + ago.collidableDown);
		
		
		Collidable cL = CollisionHelper.isCollidable(ago.position.x, ago.position.y + ago.sprite.getHeight()/2, collisionLayer);
		Collidable cR = CollisionHelper.isCollidable(ago.position.x + ago.sprite.getWidth(), ago.position.y + ago.sprite.getHeight()/2, collisionLayer);
		Collidable cU = CollisionHelper.isCollidable(ago.position.x + ago.sprite.getWidth()/2, ago.position.y + ago.sprite.getHeight(), collisionLayer);
		Collidable cD = CollisionHelper.isCollidable(ago.position.x + ago.sprite.getWidth()/2, ago.position.y, collisionLayer);
		
		if (cL == null && ago.getDx() <= 0 ||
				cR == null && ago.getDx() > 0){
			ago.position.x += explosion_dx * EXPLOSION_PUSH_FORCE;
		} 
		if (cD == null && ago.getDy() < 0 
				|| cU == null && ago.getDy()  >= 0){
			ago.position.y += explosion_dy * EXPLOSION_PUSH_FORCE;
		} 
		
//		if (ago.collidableLeft == null || ago.collidableRight == null) {
//			ago.position.x += explosion_dx * EXPLOSION_PUSH_FORCE;
//		}
//		if (ago.collidableUp == null || ago.collidableDown == null) {
//			ago.position.y += explosion_dy * EXPLOSION_PUSH_FORCE;
//		}
		ago.exploding = false;
	}
	
}
