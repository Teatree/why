package com.me.swampmonster.GUI;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject;

public class Croshair extends AbstractGameObject{
	
	private boolean aiming;
	
	public Croshair(Vector2 position){
		this.position = position;
		
		sprite = new Sprite(new Texture("data/Croshair.png"));
	}
	public void update(){
		position.x = theController.level1.getPlayer().getPosition().x;
		position.y = theController.level1.getPlayer().getPosition().y;
	}
	public boolean isAiming() {
		return aiming;
	}
	public void setAiming(boolean aiming) {
		this.aiming = aiming;
	}
	
}
