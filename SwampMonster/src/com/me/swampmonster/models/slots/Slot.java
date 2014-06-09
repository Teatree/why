package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.me.swampmonster.models.Player;

public class Slot {
	
	public Sprite sprite;
	public int lifeTime;
	public String description = this.getClass().getSimpleName() + "desc";
	public void execute (Player target){};
	
}
