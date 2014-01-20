package com.me.swampmonster.models;

import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;

public abstract class AbstractGameObject {

	public enum State{
		STANDARD, HURT, DEAD, ANIMATING, ANIMATING2, ATTACKING, PURSUIT, GUNMOVEMENT;
	}
	
	protected TextureRegion currentFrame;
	protected HashMap<State, AnimationControl> animationsStandard = new HashMap<State, AnimationControl>();   
	protected HashMap<State, AnimationControl> animationsOxygen = new HashMap<State, AnimationControl>();   
	
	protected Vector2 position;
	protected Vector3 V3Pos;
	protected Sprite sprite;
	protected boolean dead;
	protected Rectangle rectanlge;
	protected Circle circle;
	protected Line2D line;
	protected TiledMap map;
	
	protected Vector2 oldPos;
	protected TheController theController;
	
	protected String playerMovementDirection; 
	protected float playerMovementSpeed = 0.5f; 
	
	protected int health;
	protected float oxygen;
	
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
		return playerMovementSpeed;
	}
	public void setPlayerMovementSpeedX(float playerMovementSpeedX) {
		this.playerMovementSpeed = playerMovementSpeedX;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public float getOxygen() {
		return oxygen;
	}
	public void setOxygen(float oxygen) {
		this.oxygen = oxygen;
	}
	public Rectangle getRectanlge() {
		return rectanlge;
	}
	public void setRectanlge(Rectangle rectanlge) {
		this.rectanlge = rectanlge;
	}
	public Circle getCircle() {
		return circle;
	}
	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public Line2D getLine() {
		return line;
	}
	public void setLine(Line2D line) {
		this.line = line;
	}
	public Vector3 getV3Pos() {
		return V3Pos;
	}
	public void setV3Pos(Vector3 v3Pos) {
		V3Pos = v3Pos;
	}
	
}
