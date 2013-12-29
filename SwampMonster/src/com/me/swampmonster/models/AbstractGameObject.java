package com.me.swampmonster.models;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;

public abstract class AbstractGameObject {

	public enum State{
		STANDARD, HURT, DEAD, ANIMATING, ATTACKING, PURSUIT;
	}
	
	protected TextureRegion currentFrame;
	protected Map<State, AnimationControl> animations = new HashMap<State, AnimationControl>();   
	
	protected Vector2 position;
	protected Sprite sprite;
	protected TiledMap map;
	
	protected Vector2 oldPos;
	protected TheController theController;
	
	protected String playerMovementDirection; 
	protected float playerMovementSpeedX = 0.5f; 
	protected float playerMovementSpeedY = 0.5f;
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	public TiledMap getMap() {
		return map;
	}
	public void setMap(TiledMap map) {
		this.map = map;
	}
	public Vector2 getOldPos() {
		return oldPos;
	}
	public void setOldPos(Vector2 oldPos) {
		this.oldPos = oldPos;
	}
	public TheController getTheController() {
		return theController;
	}
	public void setTheController(TheController theController) {
		this.theController = theController;
	}
	public String getPlayerMovementDirection() {
		return playerMovementDirection;
	}
	public void setPlayerMovementDirection(String playerMovementDirection) {
		this.playerMovementDirection = playerMovementDirection;
	}
	public float getPlayerMovementSpeedX() {
		return playerMovementSpeedX;
	}
	public void setPlayerMovementSpeedX(float playerMovementSpeedX) {
		this.playerMovementSpeedX = playerMovementSpeedX;
	}
	public float getPlayerMovementSpeedY() {
		return playerMovementSpeedY;
	}
	public void setPlayerMovementSpeedY(float playerMovementSpeedY) {
		this.playerMovementSpeedY = playerMovementSpeedY;
	}
	
}
