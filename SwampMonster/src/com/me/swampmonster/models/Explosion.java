package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
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
	
	public Explosion(Vector2 position){
		this.position = position;
		this.damage = 0.9f;
		incrementalCircleValue = 0.6f;
		incrementalDamageValue = 0.06f;
		explCircle = new Circle();
		explCircle.radius = 90;
	}
	
	public void update(){
		if (explosionEffect != null && !explosionEffect.isComplete() && damage > incrementalDamageValue){
			explCircle.radius += incrementalCircleValue;
			this.damage -= incrementalDamageValue;
		}else if(explosionEffect != null && explosionEffect.isComplete()){
			explCircle.radius = 0;
		}
	}
	
	public void cause(AbstractGameObject ago, TiledMapTileLayer collisionLayer/*, Stack<Enemy> enemies*/){
		ago.hurt = true;
		ago.exploding = true;
//		if (ago instanceof Enemy){
//			try {
//				ago.getClass().getField("path").set(ago.getClass().getField("path"), new Node[99]); 
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
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
		ago.collidableLeft = ago.collisionCheckerRight(collisionLayer);
		ago.collidableRight = ago.collisionCheckerLeft(collisionLayer);
		ago.collidableDown = ago.collisionCheckerTop(collisionLayer);
		ago.collidableUp = ago.collisionCheckerBottom(collisionLayer);
		
//		System.out.println("collidableLeft = " + ago.collidableLeft);
//		System.out.println("collidableRight = " + ago.collidableRight);
//		System.out.println("collidableUp = " + ago.collidableUp);
//		System.out.println("collidableDown = " + ago.collidableDown);
		
		if (ago.collidableLeft == null || ago.collidableRight == null) {
			ago.position.x += explosion_dx * EXPLOSION_PUSH_FORCE;
		}
		if (ago.collidableUp == null || ago.collidableDown == null) {
			ago.position.y += explosion_dy * EXPLOSION_PUSH_FORCE;
		}
		ago.exploding = false;
	}
	
}
