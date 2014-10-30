package com.me.swampmonster.models.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.collision.CollisionHelper;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.utils.Assets;

public class LeechProjectile extends Projectile{
	
	public int initialSurfaceLevel;
	public int currentSurfaceLevel;

	public LeechProjectile(Vector2 position, float rot){
		
		this.position = position;
		sprite = new Sprite(Assets.manager.get(Assets.leachProjectile));
		sprite.setRotation(rot*57.29f);
		circle = new Circle();
		circle.radius = 6;
		
		movementSpeed = 2f;
		
		direction_x = 0;
		direction_y = 0;
		
		initialSurfaceLevel = getSurfaceLevelProjectile(TheController.collisionLayer);
	}
	// git is great !
	
	@Override
	public void update(){
		super.update();
		
		circle.x = position.x+8;
		circle.y = position.y+8;
//		circle.x = position.x + sprite.getWidth() / 2;
//		circle.y = position.y + sprite.getHeight() / 2;
		
		position.x += direction_x*movementSpeed;
		position.y += direction_y*movementSpeed;
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
	
}