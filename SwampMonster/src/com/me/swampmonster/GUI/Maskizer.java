package com.me.swampmonster.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject;

public class Maskizer extends AbstractGameObject{
	
	private boolean on;
	
	public Maskizer(){
		sprite = new Sprite(new Texture("data/Maskizer.png"));
		position = new Vector2();
		position.x = 32;
		position.y = 160;
		on = false;
		circle = new Circle();
		circle.x = position.x;
		circle.y = position.y;
		circle.radius = 28;
	}
	public void update(){
		if(Gdx.input.justTouched() && doesIntersect(position, circle.radius) && !on){
			on = true;
			System.out.println(on);
		}else if(Gdx.input.justTouched() && doesIntersect(position, circle.radius) && on){
			on = false;
			System.out.println(on);
		}
	}
	public boolean isOn() {
		return on;
	}
	public void setOn(boolean on) {
		this.on = on;
	}
	public boolean doesIntersect(Vector2 center, float radius){
		boolean questionMark;
		// this is crap
		if(Intersector.intersectSegmentCircle(theController.point, theController.point, center, radius*radius)){
			// this is crap
			questionMark = true;
		}else{
			questionMark = false;
		}
		return questionMark;
	}
}
