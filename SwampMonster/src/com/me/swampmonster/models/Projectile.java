package com.me.swampmonster.models;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.utils.Assets;

public class Projectile extends AbstractGameObject {

	public static float resistance;
	public static float g = 9.8f;
	public float direction_x;
	public float direction_y;
	public float force;
//	public static float arrowMovementSpeed;
	public EffectCarriers effect;
	public int currentSurfaceLevel;
	public int initialSurfaceLevel;
	public int despawningCounter;

	public enum EffectCarriers {
		POISONED(new Sprite(Assets.manager.get(Assets.arrowPoisoned))), 
		EXPLOSIVE(new Sprite(Assets.manager.get(Assets.arrowExplosive))),
		
		//effect carriers for weapon mods
		ENEMY_BLEED(new Sprite(Assets.manager.get(Assets.arrow))),
		EXTRADAMAGE(new Sprite(Assets.manager.get(Assets.arrow))),
		EXTRADAMAGE_BY_TYPE(new Sprite(Assets.manager.get(Assets.arrow))),
		HEAL_ENEMY(new Sprite(Assets.manager.get(Assets.arrow))),
		SHADOW(new Sprite(Assets.manager.get(Assets.arrow))),
		SPEEDUP_ENEMY(new Sprite(Assets.manager.get(Assets.arrow))),
		STUN_ENEMY(new Sprite(Assets.manager.get(Assets.arrow))),
		VAMPIRE(new Sprite(Assets.manager.get(Assets.arrow))),
		
		FROST_EXPLOSIVE(new Sprite(Assets.manager.get(Assets.itemProjectile))),
		HASTE(new Sprite(Assets.manager.get(Assets.itemProjectile))),
		POISON(new Sprite(Assets.manager.get(Assets.itemProjectile))),
		NUKE(new Sprite(Assets.manager.get(Assets.itemProjectile))),
		FADE(new Sprite(Assets.manager.get(Assets.itemProjectile))),
		RADIOACTIVE(new Sprite(Assets.manager.get(Assets.itemProjectile))),
		WEAKENED(new Sprite(Assets.manager.get(Assets.itemProjectile))),
		ICE_CUBE(new Sprite(Assets.manager.get(Assets.itemProjectile))),
		SCARED(new Sprite(Assets.manager.get(Assets.itemProjectile))),
		CHAIN_ARROWS(new Sprite(Assets.manager.get(Assets.itemProjectile))),
		NONE(new Sprite(Assets.manager.get(Assets.arrow)));

		public Sprite sprite;

		EffectCarriers(Sprite sprite) {
			this.sprite = sprite;
		}
	}

	public Projectile(Vector2 position) {

		this.position = position;
		sprite = new Sprite(EffectCarriers.NONE.sprite);
		sprite.setSize(32, 32);
		circle = new Circle();
		circle.radius = 6;

		state = State.STANDARD;
//		arrowMovementSpeed = 1.8f;

		direction_x = 0;
		direction_y = 0;

		force = 1.8f;
		resistance = 0.07f;

		initialSurfaceLevel = getSurfaceLevelProjectile(TheController.collisionLayer);
	}
	
	public Projectile(Vector2 position, float rot, EffectCarriers effect, Sprite s) {

		this.position = position;
		sprite = new Sprite(s);
		sprite.setSize(32, 32);
		sprite.setRotation(rot * 57.29f);
		circle = new Circle();
		circle.radius = 6;
		this.effect = effect;

		state = State.STANDARD;

//		arrowMovementSpeed = 1.8f;

		direction_x = 0;
		direction_y = 0;

		force = 1.8f;
		resistance = 0.07f;

		initialSurfaceLevel = getSurfaceLevelProjectile(TheController.collisionLayer);
	}

	public void update() {
		if (state == State.STANDARD) {
			position.x += direction_x * force / (g / 30);
			position.y += direction_y * force / (g / 30);
			if (force > 0) {
				force -= resistance;
			} else {
				force = 0;
				state = State.DESPAWNING;
				despawningCounter=0;
			}
		}
		if(state == State.DESPAWNING){
			if(sprite.getTexture().equals(Assets.manager.get(Assets.arrow))){
				sprite = new Sprite(Assets.manager.get(Assets.arrowStuck));
			}
			if(sprite.getTexture().equals(Assets.manager.get(Assets.spear))){
				sprite = new Sprite(Assets.manager.get(Assets.spearStuck));
			}
			if(sprite.getTexture().equals(Assets.manager.get(Assets.bolt))){
				sprite = new Sprite(Assets.manager.get(Assets.boltStuck));
			}
			if(sprite.getTexture().equals(Assets.manager.get(Assets.buzz))){
				sprite = new Sprite(Assets.manager.get(Assets.buzzStuck));
			}
			
			if(despawningCounter>120){
				state = State.DEAD;
			}else{
				despawningCounter++;
			}
			
		}

		circle.x = position.x + sprite.getWidth() / 2;
		circle.y = position.y + sprite.getHeight() / 2;

		Iterator<Prop> propItr = L1.props.iterator();
		while (propItr.hasNext()) {
			Prop prop = propItr.next();
			if (Intersector.overlaps(this.circle,
					prop.sprite.getBoundingRectangle())
					&& !(prop instanceof ToxicPuddle)) {
				prop.toDoSomething();
				prop.state = State.DESPAWNING;
//				propItr.remove();
				state = State.DEAD;
			}
		}
	}

	public int getSurfaceLevelProjectile(TiledMapTileLayer collisionLayer) {
		// if(sprite.getRotation()<0){
		currentSurfaceLevel = CollisionHelper.getSurfaceLevel(position.x + sprite.getWidth()/2, 
				position.y + sprite.getHeight()/2,
				collisionLayer);
		// }else{
		// currentSurfaceLevel = CollisionHelper.getSurfaceLevel(position.x,
		// position.y+sprite.getHeight()/2, collisionLayer);
		// }
		return currentSurfaceLevel;
	}

	public boolean isCollision(TiledMapTileLayer collisionLayer) {
//		System.out.println("yes collision!");
		return CollisionHelper.isCollidableLevel(position.x + sprite.getWidth()
				/ 2, position.y + sprite.getHeight() / 2, collisionLayer, this) != null;
	}

	public boolean isCollisionNBreakable(TiledMapTileLayer collisionLayer) {
		return CollisionHelper.isCollidableBreakable(
				position.x + sprite.getWidth() / 2,
				position.y + sprite.getHeight() / 2, collisionLayer) != null;
	}

	public void setDirection(float direction_x, float direction_y) {
		this.direction_x = direction_x;
		this.direction_y = direction_y;
	}

	public Projectile() {
	}
}
