package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;
import com.me.swampmonster.utils.Assets;

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
	public float width = 146;
	public float height = 146;
	
	public float animSlotCounter = 50;
	
	public float animSavedSelectedCounter = 0;
	public AnimationControl animantionSavedSelectedCtlr = new AnimationControl(Assets.manager.get(Assets.addedSavedSlotAnimation), 4, 1, 3.8f);
	public Sprite selectedSavedSlotFrame = new Sprite();
//	float dx;
//	float dy;
	
	public void updateAnimate(float dx, float dy){
//		position =  new Vector2(sprite.getX(), sprite.getY());
		if(state == State.ANIMATING){
			if(sprite.getX() != savedSlotPosition.x && sprite.getY() != savedSlotPosition.y){
				if(position!= null){
					position.x += dx * SlotMachineTextures.slotAnimSpeed;
					position.y += dy * SlotMachineTextures.slotAnimSpeed;
					
					sprite.setX(position.x);
					sprite.setY(position.y);
					if(width>32 || height>32){
//						System.out.println("w: " + width + " frame: " + frame);
						width -= 2.28f;
						height -= 2.28f;
					}
//					System.out.println("sprite.pos " + sprite.getX() + " _ " + sprite.getY());
				}
			}
		}
	}
	public void update(){
		if(state == State.SPAWNING){
			System.out.println("spawning");
			if(animSavedSelectedCounter<90){
				animSavedSelectedCounter++;
				animantionSavedSelectedCtlr.animate(0);
				selectedSavedSlotFrame.setRegion(animantionSavedSelectedCtlr.getCurrentFrame());
				System.out.println("animSavedSelected: " + animSavedSelectedCounter + " slot: " + this);
			}
			if(animSavedSelectedCounter == 90){
				animSavedSelectedCounter = 0;
				System.out.println("animSavedSelected: " + animSavedSelectedCounter + " slot: " + this);
				state = State.STANDARD;
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

