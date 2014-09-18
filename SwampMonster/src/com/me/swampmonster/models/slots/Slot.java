package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
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
		// TODO Auto-generated method stub
		
	}
	
	public abstract String getDescriptionForSaved();
}

