package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;

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
	public Vector2 position;
//	float dx;
//	float dy;
	
	public void update(float dx, float dy){
//		position =  new Vector2(sprite.getX(), sprite.getY());
		if(state == State.ANIMATING){
			if(sprite.getX() != savedSlotPosition.x && sprite.getY() != savedSlotPosition.y){
				if(position!= null){
					position.x += dx * SlotMachineTextures.slotAnimSpeed;
					position.y += dy * SlotMachineTextures.slotAnimSpeed;
					
					sprite.setX(position.x);
					sprite.setY(position.y);
					if(SlotMachineTextures.width>32){
						SlotMachineTextures.width -= 2.28f;
						SlotMachineTextures.height -= 2.28f;
					}
					System.out.println("sprite.size " + sprite.getWidth() + " _ " + sprite.getHeight());
				}
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

