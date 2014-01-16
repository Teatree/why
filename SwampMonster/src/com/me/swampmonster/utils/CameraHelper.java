package com.me.swampmonster.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 100.0f;
	
	private Vector2 position;
	private float zoom;
	private Sprite target;
	
	public CameraHelper(){
		position = new Vector2(400, 240);
		zoom = 100.0f;
	}
	public void upadate(float deltaTime){
		if(!hasTarget()) return;
		
		if(hasTarget()){
			position.x = target.getX() + target.getOriginX(); //this is weird, find a better way to 
			position.y = target.getY() + target.getOriginY(); //get player spawn point.
			System.out.println("camera position= " + position.x + " : " + position.y);
			System.out.println("tarteg position= " + target.getX() + " : " + target.getY());
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
	public Sprite getTarget() {
		return target;
	}
	public void setTarget(Sprite target) {
		this.target = target;
	}
	public boolean hasTarget(){
		return target != null;
	}
	public boolean hasTarget(Sprite target){
		return hasTarget() && this.target.equals(target);
	}
	
	public void applyTo(OrthographicCamera camera){
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
}