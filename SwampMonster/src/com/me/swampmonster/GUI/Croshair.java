package com.me.swampmonster.GUI;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject;

public class Croshair extends AbstractGameObject{
	
	
	public Croshair(){
		position = new Vector2();
		position.x = 400;
		position.y = 240;
		circle = new Circle();
		circle.x = position.x;
		circle.y = position.y;
		circle.radius = 64;
//		sprite = new Sprite(new Texture(""));
	}
	public void update(){
		position.x = theController.level1.getPlayer().getPosition().x;
		position.y = theController.level1.getPlayer().getPosition().y;
	}
}
