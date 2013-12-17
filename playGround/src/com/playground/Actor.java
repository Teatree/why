package com.playground;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Actor {
	
	Rectangle rectangle;
	Vector2 position; 
	
	public Actor(Vector2 position){
		this.position = position;
		rectangle = new Rectangle(position.x, position.y, 16, 16);
	}

	public void update(){
		rectangle.set(position.x, position.y, 16, 16);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
}
