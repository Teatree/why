package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Player;

public abstract class Slot {
	
	public Sprite sprite;
	public int lifeTime;
	protected String description;
	public void execute (Player target){};
	public int coolDown;
	public boolean selected;
	public boolean selectedSaved;
	public int actualLevel;
	public State state;
	public Vector2 savedSlotPosition;
	public Vector2 position =  new Vector2(100, 100);
//	float dx;
//	float dy;
	
	public void update(float dx, float dy){
//		position =  new Vector2(sprite.getX(), sprite.getY());
		if(state == State.ANIMATING){
			if(sprite.getX() != savedSlotPosition.x && sprite.getY() != savedSlotPosition.y){
				position.x += dx * 1.5f;
				position.y += dy * 1.5f;
				
				System.out.println("sprite1: " + sprite.getX());
				sprite.setX(position.x);
				sprite.setY(position.y);
				System.out.println("sprite2: " + sprite.getX());
			}
		}
	}
	
	@Override
	public boolean equals (Object slot){
		return slot != null && (slot.getClass() == this.getClass());
	}
	
	@Override
	public int hashCode(){
		return this.getClass().getCanonicalName().length();
	}
	
	public abstract String getDescription();

	public void explode(Vector2 position) {
		
	}
	
	public abstract String getDescriptionForSaved();
}

