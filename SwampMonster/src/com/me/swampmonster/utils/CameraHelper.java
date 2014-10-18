package com.me.swampmonster.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.TheController;

public class CameraHelper {
	public TheController theController;
	
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 100.0f;
	
	private static final float SHAKE_DECAY = 0.23f;
	private static final int SHAKE_DELAY = 10;
	public static final boolean SHAKE_SNAP = false;
	public static final int SHAKE_INTENSITY = 15;
	
	private int shakeTime = SHAKE_DELAY;
    private float shakeAmt = 0f;
    private float shakeX = 0f;
    private float shakeY = 0f;
	
	public boolean hasTarget;
	
	private Vector2 position;
	private float zoom;
	
	public CameraHelper(){
		position = new Vector2(400, 140);
		zoom = 2.0f;
		hasTarget = true;
	}
	public void upadate(float x, float y, int delta){
		if(!hasTarget) return;
		
		if(hasTarget){
			position.x = x;
			position.y = y; 
			zoom = 0.7f;
		}
		
		if (shakeX!=0 && shakeY!=0){
			position.x -= shakeX;
			position.y -= shakeY;
		}
		
		 if (shakeAmt>0f) {
	          shakeTime -= delta;
	          if (shakeTime <= 0) {
	           cameraShake();
	         }
	    }
		
		if (shakeX!=0 && shakeY!=0){
			position.x += shakeX;
			position.y += shakeY;
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
	public void cameraShake(){
		 shakeX = (float)(Math.random()*shakeAmt/4);
	        shakeY = (float)(Math.random()*shakeAmt/4);
	        if (SHAKE_SNAP) {
	            shakeX = (int)shakeX;
	            shakeY = (int)shakeY;
	        }
	        shakeTime = SHAKE_DELAY;
	        shakeAmt -= SHAKE_DECAY*SHAKE_INTENSITY;
	        if (shakeAmt<0f)
	            shakeAmt = 0f;
	}
	
	public void applyTo(OrthographicCamera camera){
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
	
	public float getShakeAmt() {
		return shakeAmt;
	}
	public void setShakeAmt(float shakeAmt) {
		this.shakeAmt = shakeAmt;
	}
	public static int getShakeIntensity() {
		return SHAKE_INTENSITY;
	}
}