package com.me.swampmonster.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;

public class ProjectileHydra extends Projectile{
	public static float resistance;
	public static float g = 9.8f;
	public float direction_x;
	public float direction_y;
	public float force;
//	public static float arrowMovementSpeed;
	public EffectCarriers effect;
	public int currentSurfaceLevel;
	public int initialSurfaceLevel;
	public static int musltiplyCounter;
	
	private Vector2 target;
	public static List<ProjectileHydra> listHydras = new ArrayList<ProjectileHydra>();
	
	public ProjectileHydra(Vector2 position) {
		super(position);
		position = new Vector2(position.x, position.y);
		sprite = new Sprite(Assets.manager.get(Assets.projectileHydra));
		sprite.setSize(16, 16);
		circle = new Circle();
		circle.radius = 6;
		damage = 1f;

		target = findClosestTarget();
		
		direction_x = target.x - position.x;
		direction_y = target.y - position.y;

		float length1 = (float) Math.sqrt(direction_x * direction_x + direction_y * direction_y);
		direction_x /= length1;
		direction_y /= length1;
		
		sprite.setRotation(getRotation(target)* 57.29f);
		

		force = 1.3f;
		resistance = 0f;

		initialSurfaceLevel = getSurfaceLevelProjectile(TheController.collisionLayer);
		
//		listHydras.add(this);
	}
	

	public void update() {
		sprite.setX(position.x);
		sprite.setY(position.y);

		
			position.x += direction_x * force / (g / 30);
			position.y += direction_y * force / (g / 30);
			if (force > 0) {
				force -= resistance;
			} else {
				force = 0;
				state = State.DEAD;
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
		currentSurfaceLevel = CollisionHelper.getSurfaceLevel(position.x
				+ sprite.getWidth() / 2, position.y + sprite.getHeight() / 2,
				collisionLayer);
		// }else{
		// currentSurfaceLevel = CollisionHelper.getSurfaceLevel(position.x,
		// position.y+sprite.getHeight()/2, collisionLayer);
		// }
		return currentSurfaceLevel;
	}

	public boolean isCollision(TiledMapTileLayer collisionLayer) {
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

	private Vector2 findClosestTarget() {
		Vector2 target = null;
		float minLengthToEnemy = 901;
		for(Enemy e : L1.enemiesOnStage){
			if(e.state != State.DEAD){
				float length;
				length = (float) Math.sqrt(Math.pow((this.position.x - e.position.x), 2) +
							(Math.pow((this.position.y - e.position.y), 2)));
				if(length<minLengthToEnemy){
					minLengthToEnemy = length;
					target = new Vector2(e.position.x, e.position.y);
				}
			}
		}
		return target;
	}

	public float getRotation(Vector2 shotDir) {
		double angle1 = Math.atan2((position.y + circle.radius/2) - position.y,
				(position.x + circle.radius/2) - 0);
		double angle2 = Math.atan2((position.y + circle.radius/2) - shotDir.y, (position.x + circle.radius/2)
				- shotDir.x);
		return (float) (angle2 - angle1);
	}
	
	
	public ProjectileHydra() {
	}

}
