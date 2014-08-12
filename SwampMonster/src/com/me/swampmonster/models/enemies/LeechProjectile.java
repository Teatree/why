package com.me.swampmonster.models.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.utils.Assets;

public class LeechProjectile extends Projectile{

	public LeechProjectile(Vector2 position, float rot){
		
		this.position = position;
		sprite = new Sprite(Assets.manager.get(Assets.leachProjectile));
		sprite.setRotation(rot*57.29f);
		circle = new Circle();
		circle.radius = 8;
		
		movementSpeed = 1.8f;
		
		direction_x = 0;
		direction_y = 0;
	}
	// git is great !
	
	public void update(){
		circle.x = position.x+8;
		circle.y = position.y+8;
		
		position.x += direction_x*movementSpeed;
		position.y += direction_y*movementSpeed;
	}
}
