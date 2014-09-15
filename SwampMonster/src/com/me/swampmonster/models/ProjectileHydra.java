package com.me.swampmonster.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

public class ProjectileHydra extends Projectile {
	public static float resistance;
	public static float g = 9.8f;
	public float direction_x;
	public float direction_y;
	public float force;
	public int lifeTime;
	public AnimationControl animControl;

	public int effectCounter;
	public Sprite trailEffect;
	public List<hydraTrailAnimation> animsTrailList;
	public static int musltiplyCounter = 3;

	private Vector2 target;
	private List<ProjectileHydra> miniHydras = new ArrayList<ProjectileHydra>();

	public ProjectileHydra(Vector2 position) {
		super(position);
		position = new Vector2(position.x, position.y);
		animControl = new AnimationControl(Assets.manager.get(Assets.projectileHydra), 4, 2, 3.6f);
		sprite = new Sprite(animControl.getCurrentFrame());
		sprite.setSize(16, 16);
		circle = new Circle();
		circle.radius = 6;
		damage = 1f;
//		System.out.println("target: " + target);
		target = findClosestTarget();
//		System.out.println("findClosest: " + findClosestTarget());
		animsTrailList = new ArrayList<hydraTrailAnimation>();

		// System.err.println("target xy: " + target + "position " + position);
		if (target != null) {
			direction_x = target.x - position.x;
			direction_y = target.y - position.y;

			float length1 = (float) Math.sqrt(direction_x * direction_x
					+ direction_y * direction_y);
			direction_x /= length1;
			direction_y /= length1;

			sprite.setRotation(getRotation(target) * 57.29f);

			// animControl = new
			// AnimationControl(Assets.manager.get(Assets.trailEffect), 4, 1,
			// 3.9f);
			//
			force = 0.9f;
			resistance = 0f;
			initialSurfaceLevel = getSurfaceLevelProjectile(TheController.collisionLayer);
		}
		// listHydras.add(this);
	}

	public void update() {
		lifeTime++;
		if (state == State.DESPAWNING){
			animControl.animate(4);
			//animation
			sprite.setRegion(animControl.getCurrentFrame());
			if(!animControl.animating2){
				state = State.DEAD;
			}
			
		}
		else if (state != State.DEAD && state != State.DESPAWNING) {
			animControl.animate(0);
			sprite.setRegion(animControl.getCurrentFrame());
			
			if (lifeTime == 600) {
				state = State.DESPAWNING;
				lifeTime = 0;
			}
			sprite.setX(position.x);
			sprite.setY(position.y);

			position.x += direction_x * force / (g / 30);
			position.y += direction_y * force / (g / 30);
			if (force > 0) {
				force -= resistance;
			} else {
				force = 0;
				state = State.DESPAWNING;
			}

			circle.x = position.x + sprite.getWidth() / 2;
			circle.y = position.y + sprite.getHeight() / 2;

			for (Enemy e : L1.enemiesOnStage) {
				if (Intersector.overlaps(this.circle, e.rectanlge)
						&& !e.injuredByHydra && e.state != State.DEAD) {
					e.health--;
					state = State.DESPAWNING;
					musltiplyCounter--;
					if (musltiplyCounter >= 0) {
						ProjectileHydra projectile = new ProjectileHydra(
								new Vector2(position.x, position.y));
						ProjectileHydra projectile2 = new ProjectileHydra(
								new Vector2(position.x, position.y));
						if (projectile.target != null) {
							miniHydras.add(projectile);
						}
						if (projectile2.target != null) {
							miniHydras.add(projectile2);
						}
						e.injuredByHydra = true;
					}
					break;
				}
			}
			// if(miniHydras != null && !miniHydras.isEmpty()){
			// for(ProjectileHydra mini : miniHydras){
			// mini.update();
			// }
			// }
		} else {
			position = null;
		}
		if (miniHydras != null && !miniHydras.isEmpty()) {
			for (ProjectileHydra mini : miniHydras) {
				mini.update();
			}
		}

		if (musltiplyCounter == 0 && miniHydras.isEmpty()) {
			musltiplyCounter = 3;
		}

		effectCounter++;
		if (effectCounter == 6) {
			hydraTrailAnimation hydraTrail = new hydraTrailAnimation();
			if (position != null) {
				hydraTrail.position.x = position.x;
				hydraTrail.position.y = position.y;
			}
			if(state != State.DESPAWNING){
				animsTrailList.add(hydraTrail);
			}
			effectCounter = 0;
		}

		Iterator<hydraTrailAnimation> trailItr = animsTrailList.iterator();
		while (trailItr.hasNext()) {
			hydraTrailAnimation hydraTrail = trailItr.next();
			if (!hydraTrail.animationControl.animating2) {
				trailItr.remove();
			}
		}
	}

	public List<ProjectileHydra> getProjectiles() {
		List<ProjectileHydra> result = new ArrayList<ProjectileHydra>();
		result.add(this);
		if (miniHydras != null && !miniHydras.isEmpty()) {
			for (ProjectileHydra p : miniHydras) {
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

	public boolean allDead() {
		for (ProjectileHydra ph : getProjectiles()) {
			if (ph.state != State.DEAD) {
				return false;
			}
		}

		return true;
	}

	private Vector2 findClosestTarget() {
		Vector2 target = null;
		float minLengthToEnemy = Integer.MAX_VALUE;
		Enemy closestEnemy = null;
		for (Enemy e : L1.enemiesOnStage) {
			if (e.state != State.DEAD && !e.isAimedByHydra) {
				float length;
				length = (float) Math.sqrt(Math.pow(
						(this.position.x - e.position.x), 2)
						+ (Math.pow((this.position.y - e.position.y), 2)));
				if (length <= minLengthToEnemy) {
					minLengthToEnemy = length;
					closestEnemy = e;
				}
			}
		}
		if (closestEnemy != null){
			closestEnemy.isAimedByHydra = true;
			target = new Vector2(closestEnemy.position.x, closestEnemy.position.y);
		}
		return target;
	}

	public float getRotation(Vector2 shotDir) {
		double angle1 = Math.atan2((position.y + circle.radius / 2)
				- position.y, (position.x + circle.radius / 2) - 0);
		double angle2 = Math.atan2(
				(position.y + circle.radius / 2) - shotDir.y,
				(position.x + circle.radius / 2) - shotDir.x);
		return (float) (angle2 - angle1);
	}

	public ProjectileHydra() {
	}

	public class hydraTrailAnimation {
		public AnimationControl animationControl;
		public Vector2 position;

		// public boolean

		public hydraTrailAnimation() {
			animationControl = new AnimationControl(
					Assets.manager.get(Assets.trailEffect), 4, 1, 3.9f);

			position = new Vector2();
		}

		public Sprite getCurrentSprite() {
			animationControl.animate(0);
			Sprite s = new Sprite(animationControl.getCurrentFrame());
			s.setX(position.x);
			s.setY(position.y);
			return s;
		}

	}
}
