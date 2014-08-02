package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.utils.Assets;

public class Projectile extends AbstractGameObject{
	
	public static float resistance;
	public static float g = 9.8f;
	public float direction_x;
	public float direction_y;
	public float force;
	public static float arrowMovementSpeed;
	public EffectCarriers effect;
	public int currentSurfaceLevel;
	
	public enum EffectCarriers{
		POISONED(new Sprite(Assets.manager.get(Assets.arrowPoisoned))),
		SHADOW(new Sprite(Assets.manager.get(Assets.SHADOW_ARROW_ICON))),
		EXPLOSIVE(new Sprite(Assets.manager.get(Assets.arrowExplosive))),
		NONE(new Sprite(Assets.manager.get(Assets.arrow)));

		Sprite sprite;
		
		EffectCarriers(Sprite sprite){
			this.sprite = sprite;
		}
	}	
	
	public Projectile(Vector2 position, float rot, EffectCarriers effect){
		
		this.position = position;
		sprite = new Sprite(effect.sprite);
		sprite.setSize(32, 32);
		sprite.setRotation(rot*57.29f);
		circle = new Circle();
		circle.radius = 6;
		damage = 1f;
		this.effect = effect;
		
		state = State.STANDARD;
		
		arrowMovementSpeed = 1.8f;
		
		direction_x = 0;
		direction_y = 0;
		
		force = 1.8f;
		resistance = 0.07f;
	}
	// git is great !
	
	public void update(){
		if(state == State.STANDARD) {
				position.x += direction_x * force/(g/30);
				position.y += direction_y * force/(g/30);
			if(force > 0){
				force -= resistance;
			}else{
				force = 0;
				state = State.DEAD;
			}
		}
		
		circle.x = position.x+sprite.getWidth()/2;
		circle.y = position.y+sprite.getHeight()/2;
	}

	public void getSurfaceLevelProjectile(TiledMapTileLayer collisionLayer) {
		if(sprite.getRotation()<0){
			currentSurfaceLevel = CollisionHelper.getSurfaceLevel(position.x+sprite.getWidth(), 
					position.y+sprite.getHeight()/2, collisionLayer);
		}else{
			currentSurfaceLevel = CollisionHelper.getSurfaceLevel(position.x,
					position.y+sprite.getHeight()/2, collisionLayer);
		}
	}
	
	public boolean isCollision(TiledMapTileLayer collisionLayer){
		return CollisionHelper.isCollidableLevel(position.x+sprite.getWidth()/2, 
				position.y+sprite.getHeight()/2, collisionLayer, this) != null;
	}
	public boolean isCollisionNBreakable(TiledMapTileLayer collisionLayer){
		return CollisionHelper.isCollidableBreakable(position.x+sprite.getWidth()/2, 
				position.y+sprite.getHeight()/2, collisionLayer) != null;
	}
	
	public void setDirection(float direction_x, float direction_y){
		this.direction_x = direction_x;
		this.direction_y = direction_y;
	}
	
	public Projectile (){}
}
