package com.me.swampmonster.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.AbstractGameObject;

public class Weaponizer extends AbstractGameObject{
	
	private boolean on; 
	
	public Weaponizer(){
		sprite = new Sprite(new Texture("data/Weaponizer.png"));
		on = false;
		circle = new Circle();
		circle.x = 64;
		circle.y = 64;
		circle.radius = 56;
	}
	public void update(){
//		if(Gdx.input.justTouched()){
//			System.out.println("The Y: " + Gdx.input.getY() + " and the X: " + Gdx.input.getX());
//		}
		if(Gdx.input.justTouched() && Gdx.input.getY() < 476 && Gdx.input.getY() > 356 && Gdx.input.getX() > 4 && Gdx.input.getX() < 124 && !on){
			on = true;
			System.out.println(on);
		}else if(Gdx.input.justTouched() && Gdx.input.getY() < 476 && Gdx.input.getY() > 356 && Gdx.input.getX() > 4 && Gdx.input.getX() < 124 && on){
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
	
	
}
