package com.me.swampmonster.models;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.AI.Node;
import com.me.swampmonster.models.enemies.Enemy;

public class Explosion {
	private static final String EXPLOSION_TYPE_STANDART = "standart";
	public static final float EXPLOSION_PUSH_FORCE = 2.2f;
	
	public Circle explCircle;
	public float damage;
	public ParticleEffect explosionEffect;
	public float incrementalDamageValue;
	public float incrementalCircleValue;
	public Vector2 position;
	String type = EXPLOSION_TYPE_STANDART;
	
	float explosion_dx;
	float explosion_dy;
	
	public Explosion(Vector2 position){
		this.position = position;
		damage = 1f;
		incrementalCircleValue = 0.6f;
		incrementalDamageValue = 0.6f;
		explCircle = new Circle();
		explCircle.radius = 90;
	}
	
	public void update(){
		
		if (explosionEffect != null && !explosionEffect.isComplete()){
			explCircle.radius += incrementalCircleValue;
			damage += incrementalDamageValue;
			
		}else if(explosionEffect != null && explosionEffect.isComplete()){
			explCircle.radius = 0;
		}
	}
	
	public void cause(Enemy ago, TiledMapTileLayer collisionLayer, List<Enemy> enemies){
		System.out.println("penis face");
		ago.hurt = true;
		ago.exploding = true;
		ago.path = new Node[99];
		explosion_dx = ago.position.x - position.x;
		explosion_dy = ago.position.y - position.y;

		float length1 = (float) Math.sqrt(explosion_dx * explosion_dx + explosion_dy * explosion_dy);
		explosion_dx /= length1;
		explosion_dy /= length1;
		
		ago.health = ago.health - damage;
		
		ago.collidableLeft = ago.collisionCheckerRight(collisionLayer, enemies);
		ago.collidableRight = ago.collisionCheckerLeft(collisionLayer, enemies);
		ago.collidableDown = ago.collisionCheckerTop(collisionLayer, enemies);
		ago.collidableUp = ago.collisionCheckerBottom(collisionLayer, enemies);
		
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
