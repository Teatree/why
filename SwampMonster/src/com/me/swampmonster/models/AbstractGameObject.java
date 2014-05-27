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
		STANDARD, DEAD, ANIMATING, ANIMATINGLARGE, ACTIVATING, 
		ATTACKING, PURSUIT, GUNMOVEMENT, SPAWNING, DESPAWNING;
	}
	public enum NegativeEffectsState {
		POISONED(900), FEAR(120), FROZEN(930), NONE(0);
		
		public int lifetime;
		
		NegativeEffectsState(int lifeTime){
			this.lifetime = lifeTime;
		}
	}
	public enum PositiveEffectsState {
		FADE(900), SPEED_BOOST(212), RADIOACTIVE_AURA(1890), NONE(0);
		
		public int lifetime;
		
		PositiveEffectsState(int lifeTime){
			this.lifetime = lifeTime;
		}
	}
	
	protected TextureRegion currentFrame;
	protected HashMap<State, AnimationControl> animationsStandard = new HashMap<State, AnimationControl>();   
	
	public State state;
	public NegativeEffectsState negativeEffectsState;
	public PositiveEffectsState positiveEffectsState;
	public Vector2 position;
	public Sprite sprite;
	public boolean dead;
	public Rectangle rectanlge;
	public Circle circle;
	protected Line2D line;
	protected TiledMap map;
	protected TiledMap map_inside_bunker;
	protected String damageType;
	public boolean hurt;
	protected boolean allowedToShoot;
	
	protected Vector2 oldPos;
	
	protected String playerMovementDirection; 
	protected String playerMovementDirectionLR; 
	protected String playerMovementDirectionUD; 
	protected float movementSpeed = 0.5f; 
	
	public int health;
	public int maxHealth;
	public int damage;
	public int points;
	public int reloadSpeed;
	public int attackSpeed;
	public int shotCoolDown;
	
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
	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
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
}
