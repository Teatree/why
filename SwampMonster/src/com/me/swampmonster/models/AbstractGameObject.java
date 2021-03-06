package com.me.swampmonster.models;

import java.awt.geom.Line2D;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.collision.Collidable;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.utils.Assets;

public abstract class AbstractGameObject {

	public enum State{
		STANDARD, DEAD, ANIMATING, ANIMATINGLARGE, ACTIVATING, TELEPORTING,
		ATTACKING, PURSUIT, GUNMOVEMENT, SPAWNING, DESPAWNING, ONFIRE, ICECUBESDEAD;
	}
	public enum NegativeEffects {
		POISONED(300, new Sprite(Assets.manager.get(Assets.POISONEDNEGATIVEEFFECT_ICON))),
				FEAR(300, new Sprite(Assets.manager.get(Assets.SCAREDNEGATIVEEFFECT_ICON))),
				FROZEN(300, new Sprite(Assets.manager.get(Assets.FROZENNEGATIVEEFFECT_ICON))),
				ICE(100, new Sprite(Assets.manager.get(Assets.STUNNEGATIVEEFFECT_ICON))),
				STUN(100, new Sprite(Assets.manager.get(Assets.STUNNEGATIVEEFFECT_ICON))),
				WEAKENED(200, new Sprite(Assets.manager.get(Assets.WEAKENED_ICON))),
				FADE_N(200, new Sprite(Assets.manager.get(Assets.WEAKENED_ICON))),
				RADIOACTIVE_N(200, new Sprite(Assets.manager.get(Assets.WEAKENED_ICON))),
				HASTE_N(200, new Sprite(Assets.manager.get(Assets.WEAKENED_ICON))),
				NONE(0, null);
		
		public int lifetime;
		public Sprite sprite;
		
		NegativeEffects(int lifeTime, Sprite sprite){
			this.lifetime = lifeTime;
			this.sprite = sprite;
		}
	}
	
	protected TextureRegion currentFrame;
	public HashMap<State, AnimationControl> animationsStandard = new HashMap<State, AnimationControl>();   
	
	public State state;
	public NegativeEffects negativeEffectsState;
	public PositiveEffects positiveEffectsState;
	public Vector2 position;
	public Sprite sprite;
	public boolean dead;
	public Rectangle rectanlge;
	public Circle circle;
	protected Line2D line;
	public String damageType;
	public String damageTypeOxygen;
	public boolean hurt;
	public boolean exploding;
	protected boolean allowedToShoot;
	
	protected Vector2 oldPos;
	
	protected String playerMovementDirection; 
	protected String playerMovementDirectionLR; 
	protected String playerMovementDirectionUD; 
	public float movementSpeed = 0.7f; 
	
	public float health;
	public static int maxHealth;
	public float minDD;
	public float maxDD;
	public int damage;
	public int points;
	public int reloadSpeed;
	public int attackSpeed;
	public int shotCoolDown;
	public float STANDART_MOVEMENT_SPEED = movementSpeed;
	
	public Collidable collidableRight;
	public Collidable collidableLeft;
	public Collidable collidableUp;
	public Collidable collidableDown;
	public int oxygen;
	
	public void toDoSomething(AbstractGameObject abs) {
		
	}
	
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
	public float getPlayerMovementSpeed() {
		return movementSpeed;
	}
	public void setPlayerMovementSpeed(float playerMovementSpeedX) {
		this.movementSpeed = playerMovementSpeedX;
		STANDART_MOVEMENT_SPEED = movementSpeed;
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
	public void setLine(Line2D line) 
	{
		this.line = line;
	}
	
	public Collidable collisionCheckerRight(TiledMapTileLayer collisionLayer){
		return null;
	}
	public Collidable collisionCheckerLeft(TiledMapTileLayer collisionLayer){
		return null;
	}
	public Collidable collisionCheckerTop(TiledMapTileLayer collisionLayer){
		return null;
	}
	public Collidable collisionCheckerBottom(TiledMapTileLayer collisionLayer){
		return null;
	}
	
	public void setColour(float red, float green, float blue, float alpha) {
		sprite.setColor(red, green, blue, alpha);
	}
	
	public float getDx() {
		return 0;
	}

	public float getDy() {
		return 0;
	}
	
	public void setNegativeEffect(NegativeEffects negativeEffects){
		
	}
}
