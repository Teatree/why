package com.me.swampmonster.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
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
//		if(Gdx.input.justTouched()){
//			System.out.println("The Y: " + Gdx.input.getY() + " and the X: " + Gdx.input.getX());
//		}
		if(Gdx.input.justTouched() && Gdx.input.getY() < 352 && Gdx.input.getY() > 228 && Gdx.input.getX() > 4 && Gdx.input.getX() < 60 && !on){
			on = true;
//			System.out.println(on);
		}else if(Gdx.input.justTouched() && Gdx.input.getY() < 352 && Gdx.input.getY() > 228 && Gdx.input.getX() > 4 && Gdx.input.getX() < 124 && on){
			on = false;
//			System.out.println(on);
		}
	}
	public boolean isOn() {
		return on;
	}
	public void setOn(boolean on) {
		this.on = on;
	}
	

}
