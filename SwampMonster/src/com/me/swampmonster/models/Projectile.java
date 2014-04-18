package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends AbstractGameObject{
	
	private float direction_x;
	private float direction_y;
	private String projectileTypeLoc;
	
	public Projectile(Vector2 position, float rot){
		projectileTypeLoc = "data/projectile.png";
		this.position = position;
		sprite = new Sprite(new Texture(projectileTypeLoc));
		sprite.setRotation(rot*57.29f);
		circle = new Circle();
		circle.radius = 8;
		
		playerMovementSpeed = 1.8f;
		
		direction_x = 0;
		direction_y = 0;
	}
	// git is great !
	
	public void update(){
		circle.x = position.x+8;
		circle.y = position.y+8;
		
		position.x += direction_x*playerMovementSpeed;
		position.y += direction_y*playerMovementSpeed;
	}
	
	public void setDirection(float direction_x, float direction_y){
		this.direction_x = direction_x;
		this.direction_y = direction_y;
	}

	public String getProjectileTypeLoc() {
		return projectileTypeLoc;
	}

	public void setProjectileTypeLoc(String projectileTypeLoc) {
		this.projectileTypeLoc = projectileTypeLoc;
	}
	
}
