package com.me.swampmonster.models;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.slots.PositiveEffects;

public class Explosion {
	public static final String EXPLOSION_TYPE_STANDART = "standart";
	public static final String EXPLOSION_TYPE_FROST = "frost";
	public static final String EXPLOSION_TYPE_INVERTED = "inverted";
	private static final float EXPLOSION_PUSH_FORCE = 2.2f;
	
	public Circle explCircle;
	public float damage;
	public ParticleEffect explosionEffect;
	public float incrementalDamageValue;
	public float incrementalCircleValue;
	public Vector2 position;
	public String type;
	public int causeDamageCounter;
	private Random random;
	float explosion_dx;
	float explosion_dy;
	public int explosionLifeTime;
	private int explodionLifeTimeCounter;
	
	public boolean isNuke;
	
	public Explosion(Vector2 position, String type){
		this.position = position;
		this.type = type;
		random = new Random();
		this.damage = random.nextFloat()+0.7f;
		explCircle = new Circle();
		explCircle.setPosition(new Vector2(position.x, position.y));
		if(!type.equals(EXPLOSION_TYPE_INVERTED)){
			explCircle.radius = random.nextInt(40)+50;
			explosionLifeTime = random.nextInt(25)+15;
			incrementalCircleValue = 4f;
			incrementalDamageValue = 0.16f;
		}else{
			explCircle.radius = random.nextInt(40)+460;
			explosionLifeTime = random.nextInt(25)+185;
			incrementalCircleValue = 0.1f;
			incrementalDamageValue = 0;
		}
	}
	
	public void update(){
		if (/*explosionEffect != null && !explosionEffect.isComplete()*/explodionLifeTimeCounter < explosionLifeTime /*&& damage > incrementalDamageValue*/){
			explCircle.radius += incrementalCircleValue;
			if(damage >= incrementalDamageValue){
				this.damage -= incrementalDamageValue;
			}
			explodionLifeTimeCounter++;
		}else if(/*explosionEffect != null && */explodionLifeTimeCounter >= explosionLifeTime /*explosionEffect.isComplete()*/){
			explCircle.radius = 0;
		}
	}
	
	public boolean cause(AbstractGameObject ago, TiledMapTileLayer collisionLayer){
		if(!((ago instanceof Player) && isNuke)){
			ago.hurt = true;
		}
		ago.exploding = true;
		
		explosion_dx = ago.position.x - position.x;
		explosion_dy = ago.position.y - position.y;
		float length1 = (float) Math.sqrt(explosion_dx * explosion_dx + explosion_dy * explosion_dy);
		explosion_dx /= length1;
		explosion_dy /= length1;
		
		causeDamageCounter++;
		if (type == EXPLOSION_TYPE_STANDART){
			if (causeDamageCounter % 15 == 0 && ago.health > 0){
				if (ago.positiveEffectsState != PositiveEffects.FADE && !((ago instanceof Player) && isNuke)){
					ago.health -= this.damage;
				}
			}
		} 
		if (type == EXPLOSION_TYPE_FROST){
			if (!(ago instanceof Player)){
				ago.setNegativeEffect(NegativeEffects.FROZEN);
			}
		}
		
		if (type.equals(EXPLOSION_TYPE_INVERTED)){
//			System.out.println("bla");
			
			ago.hurt = false;
			
			explosion_dx = -explosion_dx/3;
			explosion_dy = -explosion_dy/3;
			
			if(!(ago instanceof Player)){
				explosion_dx = explosion_dx*3;
				explosion_dy = explosion_dy*3;
			}
			
		}
		
		
		explosionEffect(ago, collisionLayer); 
		ago.exploding = false;
		
		if(ago.sprite.getBoundingRectangle().contains(explCircle.x, explCircle.y) && type==EXPLOSION_TYPE_INVERTED){
			return true;
		}else{
			return false;
		}
		
	}

	public void explosionEffect(AbstractGameObject ago, TiledMapTileLayer collisionLayer) {
		Collidable cL = CollisionHelper.isCollidable(ago.position.x, ago.position.y + ago.sprite.getHeight()/2, collisionLayer);
		Collidable cR = CollisionHelper.isCollidable(ago.position.x + ago.sprite.getWidth(), ago.position.y + ago.sprite.getHeight()/2, collisionLayer);
		Collidable cU = CollisionHelper.isCollidable(ago.position.x + ago.sprite.getWidth()/2, ago.position.y + ago.sprite.getHeight(), collisionLayer);
		Collidable cD = CollisionHelper.isCollidable(ago.position.x + ago.sprite.getWidth()/2, ago.position.y, collisionLayer);
		if (!((ago instanceof Player) && isNuke)){
			if (cL == null && ago.getDx() <= 0 ||
					cR == null && ago.getDx() > 0){
				ago.position.x += explosion_dx * EXPLOSION_PUSH_FORCE;
			} 
			if (cD == null && ago.getDy() < 0 
					|| cU == null && ago.getDy()  >= 0){
				ago.position.y += explosion_dy * EXPLOSION_PUSH_FORCE;
			}
		}
	}
	
}
