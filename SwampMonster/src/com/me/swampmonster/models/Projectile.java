package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Projectile extends AbstractGameObject{
	
	public Projectile(Vector2 position, float rot){
		this.position = position;
		sprite = new Sprite(new Texture("data/projectile.png"));
		sprite.setRotation(rot*57.29f);
		circle = new Circle();
		circle.radius = 8;
		
		playerMovementSpeed = 1.8f;
	}
	// git is great !
	
	public void update(Vector3 shotDir, float direction_x, float direction_y){
		circle.x = position.x+8;
		circle.y = position.y+8;
		
		position.x += direction_x*playerMovementSpeed;
		position.y += direction_y*playerMovementSpeed;
	}
}
