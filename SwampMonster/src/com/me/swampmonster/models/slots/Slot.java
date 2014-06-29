package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Constants;

public class Slot {
	
	public Sprite sprite;
	public int lifeTime;
	public String description = this.getClass().getSimpleName() + "desc";
	public void execute (Player target){};
	public int coolDown;
	public boolean selected;
	
	@Override
	public boolean equals (Object slot){
		return (slot.getClass() == this.getClass());
	}
	
	@Override
	public int hashCode(){
		return this.getClass().getCanonicalName().length();
	}
}
