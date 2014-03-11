package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends AbstractGameObject{
	
	public Projectile(Vector2 position){
		this.position = position;
		
		sprite = new Sprite(new Texture("data/projectile.png"));
		circle = new Circle();
		circle.radius = 8;
	}
	
	public void update(){
		circle.x = position.x+8;
		circle.y = position.y+8;
		sprite.setRotation(90);
		if(position.x != theController.level1.getPlayer().getShotDir().x){
			if(position.x > theController.level1.getPlayer().getShotDir().x){
				position.x -= 0.6f;
			}
			if(position.x < theController.level1.getPlayer().getShotDir().x){
				position.x += 0.6f;
			}
		}
		if(position.y != theController.level1.getPlayer().getShotDir().y){
			if(position.y > theController.level1.getPlayer().getShotDir().y){
				position.y -= 0.6f;
			}
			if(position.y < theController.level1.getPlayer().getShotDir().y){
				position.y += 0.6f;
			}
		}
	}
	

}
