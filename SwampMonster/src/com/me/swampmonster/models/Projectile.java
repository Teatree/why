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
	}
	// git is great !
	
	public void update(Vector3 shotDir){
		circle.x = position.x+8;
		circle.y = position.y+8;
		
		if(position.x > shotDir.x){
			position.x -= 0.6f;
		}
		if(position.x < shotDir.x){
			position.x += 0.6f;
		}
		if(position.y > shotDir.y){
			position.y -= 0.6f;
		}
		if(position.y < shotDir.y){
			position.y += 0.6f;
		}
	}
}
