package com.me.swampmonster.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;

public class CameraHelper {
	public TheController theController;
	
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 100.0f;
	
	public boolean hasTarget;
	
	private Vector2 position;
	private float zoom;
	private Sprite target;
	
	public CameraHelper(){
		position = new Vector2(400, 140);
		zoom = 50.0f;
		hasTarget = true;
	}
	public void upadate(float x, float y){
		if(!hasTarget) return;
		
		if(hasTarget){
			position.x = x;
			position.y = y; 
			zoom = 50f;
		}
	}
	public void setPosition(float x, float y){
		this.position.set(x, y);
	}
	public Vector2 getPosition(){
		return position;
	}
	public void addZoom(float amount){
		setZoom(zoom + amount);
	}
	public float getZoom() {
		return zoom;
	}
	public void setZoom(float zoom) {
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}
	
	public void applyTo(OrthographicCamera camera){
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
}