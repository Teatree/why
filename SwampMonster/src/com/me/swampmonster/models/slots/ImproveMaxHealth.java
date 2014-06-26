package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class ImproveMaxHealth extends Slot implements Perks{
	public static int level;
	public ImproveMaxHealth() {
		sprite = new Sprite(Assets.manager.get(Assets.IMPROVEMAXHEALTH_ICON));
	}
	
	public void execute (Player player){
		//
	}
}
