package com.me.swampmonster.GUI;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject;

public class Croshair extends AbstractGameObject{
	
	private boolean aiming = false;
	
	public Croshair(Vector2 position){
		this.position = position;
		
		sprite = new Sprite(new Texture("data/Croshair.png"));
		aiming = false;
	}
	public void update(AbstractGameObject player, Vector2 point){
		position.x = player.getPosition().x;
		position.y = player.getPosition().y;
		
//		System.out.println("aiming " + aiming);
		if(doesIntersect(new Vector2(400,255), player.getCircle().radius*2, point) && !aiming && Gdx.input.isTouched()){
			player.setState(state.GUNMOVEMENT);
			aiming = true;
		}else if(!doesIntersect(new Vector2(400,255), player.getCircle().radius*2, point) && aiming && !Gdx.input.isTouched()){
			aiming = false;
		}
	}
	
	public boolean doesIntersect(Vector2 v2, float radius, Vector2 point){
		boolean questionMark;
		if(Intersector.intersectSegmentCircle(point, point, v2, radius*radius)){
			questionMark = true;
		}else{
			questionMark = false;
		}
		return questionMark;
	}
	
	public boolean isAiming() {
		return aiming;
	}
	public void setAiming(boolean aiming) {
		this.aiming = aiming;
	}
	
}
