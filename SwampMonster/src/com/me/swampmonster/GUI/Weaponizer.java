package com.me.swampmonster.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject;

public class Weaponizer extends AbstractGameObject{
	
	public boolean on; 
	public float coolDownFill;
	
	public Weaponizer(){
		sprite = new Sprite(new Texture("data/Weaponizer.png"));
		position = new Vector2();
		position.x = 64;
		position.y = 64;
		on = false;
		circle = new Circle();
		circle.x = position.x;
		circle.y = position.y;
		circle.radius = 56;
		coolDownFill = 360f;
	}
	public void update(AbstractGameObject player, Vector2 point){
		if(Gdx.input.justTouched() && doesIntersect(new Vector2(circle.x, circle.y), circle.radius,
				new Vector2(point.x+player.getPosition().x, point.y+player.getPosition().y)) && !on){
			on = true;
		}else if(Gdx.input.justTouched() && doesIntersect(new Vector2(circle.x, circle.y), circle.radius, 
				new Vector2(point.x+player.getPosition().x, point.y+player.getPosition().y)) && on){
			on = false;
		}
		
	}
	
	public boolean doesIntersect(Vector2 center, float radius, Vector2 point){
		boolean questionMark;
		// this is crap
		if(Intersector.intersectSegmentCircle(point, point, center, radius*radius)){
			// this is crap
			questionMark = true;
		}else{
			questionMark = false;
		}
		return questionMark;
	}
}
