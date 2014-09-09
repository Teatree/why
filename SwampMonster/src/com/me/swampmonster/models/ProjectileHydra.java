package com.me.swampmonster.models;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;

public class ProjectileHydra extends Projectile{
	public static float resistance;
	public static float g = 9.8f;
	public float direction_x;
	public float direction_y;
	public float force;
	
	public int effectCounter;
	public Sprite trailEffect;
	private AnimationControl animControl;
	public List<Sprite> animsTrailList;
//	public static float arrowMovementSpeed;
	public static int musltiplyCounter = 3;
	
	private Vector2 target;
//	public static List<ProjectileHydra> listHydras = new ArrayList<ProjectileHydra>();
	private List<ProjectileHydra> miniHydras = new ArrayList<ProjectileHydra>();
	
	public ProjectileHydra(Vector2 position) {
		super(position);
		position = new Vector2(position.x, position.y);
		sprite = new Sprite(Assets.manager.get(Assets.projectileHydra));
		sprite.setSize(16, 16);
		circle = new Circle();
		circle.radius = 6;
		damage = 1f;
		target = findClosestTarget();
		animsTrailList = new ArrayList<Sprite>();
		
//		System.err.println("target xy: " + target + "position " + position);
		if (target != null){
		direction_x = target.x - position.x;
		direction_y = target.y - position.y;

		float length1 = (float) Math.sqrt(direction_x * direction_x + direction_y * direction_y);
		direction_x /= length1;
		direction_y /= length1;
		
		sprite.setRotation(getRotation(target)* 57.29f);

		animControl = new AnimationControl(Assets.manager.get(Assets.trailEffect), 4, 1, 3.9f);
		
		force = 0.8f;
		resistance = 0f;
		initialSurfaceLevel = getSurfaceLevelProjectile(TheController.collisionLayer);
		}
//		listHydras.add(this);
	}
	

	public void update() {
		if(state != State.DEAD){
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
	
				for(Enemy e : L1.enemiesOnStage){
					if (Intersector.overlaps(this.circle, e.rectanlge) && !e.injuredByHydra && e.state != State.DEAD) {
						e.health--;
						state = State.DEAD;
						musltiplyCounter--;
						if(musltiplyCounter>=0){
							ProjectileHydra projectile = new ProjectileHydra(new Vector2(position.x, position.y));
							ProjectileHydra projectile2 = new ProjectileHydra(new Vector2(position.x, position.y));
							if (projectile.target != null){
								miniHydras.add(projectile);
							}
							if (projectile2.target != null){
								miniHydras.add(projectile2);
							}
							e.injuredByHydra = true;
						}
						break;
					}
				}
//				if(miniHydras != null && !miniHydras.isEmpty()){
//					for(ProjectileHydra mini : miniHydras){
//						mini.update();
//					}
//				}
		}
		else{
			position = null;
		}
		if(miniHydras != null && !miniHydras.isEmpty()){
			for(ProjectileHydra mini : miniHydras){
				mini.update();
			}
		}
		
		effectCounter++;
		animControl.animate(0);
		if(effectCounter==30){
			Sprite s = new Sprite(animControl.getCurrentFrame());
			animsTrailList.add(s);
			effectCounter=0;
		}
		System.out.println("thing: " + animsTrailList.size());
		for(Sprite s: animsTrailList){
			s = new Sprite(animControl.getCurrentFrame());
			if(position != null){
				s.setX(position.x);
				s.setY(position.y);
			}
		}
		
	}

	public List<ProjectileHydra> getProjectiles(){
		List<ProjectileHydra> result = new ArrayList<ProjectileHydra>();
		result.add(this);
		if(miniHydras != null && !miniHydras.isEmpty()){
			for(ProjectileHydra p: miniHydras){
				result.addAll(p.getProjectiles());
			}
		}
		return result;
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
		float minLengthToEnemy = 99901;
		Enemy closestEnemy = null;
		for(Enemy e : L1.enemiesOnStage){
			if(e.state != State.DEAD && e.isAimedByHydra != true){
				float length;
				length = (float) Math.sqrt(Math.pow((this.position.x - e.position.x), 2) +
							(Math.pow((this.position.y - e.position.y), 2)));
				if(length<=minLengthToEnemy){
					minLengthToEnemy = length;
					target = new Vector2(e.position.x, e.position.y);
					closestEnemy = e;
				}
			}
		}
		if (closestEnemy != null)
			closestEnemy.isAimedByHydra = true;
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
