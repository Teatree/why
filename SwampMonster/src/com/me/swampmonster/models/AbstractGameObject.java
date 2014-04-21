package com.me.swampmonster.models;

import java.awt.geom.Line2D;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;

public abstract class AbstractGameObject {

	public enum State{
		STANDARD, DEAD, ANIMATING, ANIMATINGLARGE, ACTIVATING, ATTACKING, PURSUIT, GUNMOVEMENT;
	}
	
	protected TextureRegion currentFrame;
	protected HashMap<State, AnimationControl> animationsStandard = new HashMap<State, AnimationControl>();   
	
	protected State state;
	protected Vector2 position;
	protected Sprite sprite;
	protected boolean dead;
	protected Rectangle rectanlge;
	protected Circle circle;
	protected Line2D line;
	protected TiledMap map;
	protected TiledMap map_inside_bunker;
	protected String damageType;
	protected boolean hurt;
	
	protected Vector2 oldPos;
	
	protected String playerMovementDirection; 
	protected String playerMovementDirectionLR; 
	protected String playerMovementDirectionUD; 
	protected float movementSpeed = 0.5f; 
	
	protected int health;
	protected int damage;
	protected int points;
	protected int reloadSpeed;
	protected int attackSpeed;
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
	public String getPlayerMovementDirection() {
		return playerMovementDirection;
	}
	public void setPlayerMovementDirection(String playerMovementDirection) {
		this.playerMovementDirection = playerMovementDirection;
	}
	public String getPlayerMovementDirectionLR() {
		return playerMovementDirectionLR;
	}
	public void setPlayerMovementDirectionLR(String playerMovementDirectionLR) {
		this.playerMovementDirectionLR = playerMovementDirectionLR;
	}
	public String getPlayerMovementDirectionUD() {
		return playerMovementDirectionUD;
	}
	public void setPlayerMovementDirectionUD(String playerMovementDirectionUD) {
		this.playerMovementDirectionUD = playerMovementDirectionUD;
	}
	public float getPlayerMovementSpeedX() {
		return movementSpeed;
	}
	public void setPlayerMovementSpeedX(float playerMovementSpeedX) {
		this.movementSpeed = playerMovementSpeedX;
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
	public String getDamageType() {
		return damageType;
	}
	public void setDamageType(String damageType) {
		this.damageType = damageType;
	}
	public TiledMap getMap_inside_bunker() {
		return map_inside_bunker;
	}
	public void setMap_inside_bunker(TiledMap map_inside_bunker) {
		this.map_inside_bunker = map_inside_bunker;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public boolean isHurt() {
		return hurt;
	}
	public void setHurt(boolean hurt) {
		this.hurt = hurt;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getReloadSpeed() {
		return reloadSpeed;
	}
	public void setReloadSpeed(int reloadSpeed) {
		this.reloadSpeed = reloadSpeed;
	}
	public int getAttackSpeed() {
		return attackSpeed;
	}
	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
	
	
}
