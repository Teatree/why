package com.me.swampmonster.GUI;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.me.swampmonster.models.AbstractGameObject;

public class GameOverGUI extends AbstractGameObject{
	
	public GameOverGUI(){
		circle = new Circle();
		circle.radius = 60;
		circle.x = 400;
		circle.y = 140;
		
		rectanlge = new Rectangle();
		rectanlge.height = 480;
		rectanlge.width = 800;
		rectanlge.x = 0;
		rectanlge.y = 0;
	}
	
	public void update(){
		
	}
}
