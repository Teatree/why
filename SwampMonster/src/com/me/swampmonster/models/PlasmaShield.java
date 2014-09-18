package com.me.swampmonster.models;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class PlasmaShield extends AbstractGameObject{
	
	public int lifeTime;
	public int spawnTime;
	
	public PlasmaShield(Vector2 pos){
		state = State.SPAWNING;
		position = new Vector2(pos.x, pos.y);
		circle = new Circle();
		circle.radius = 128;
		circle.x = pos.x;
		circle.y = pos.y;
	}
	
	public void update(){
		lifeTime--;
		if(lifeTime<=80 && lifeTime>0){
			state = State.STANDARD;
		}
		if(lifeTime == 0){
			state = State.DEAD;
		}
	}
}
