package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class ImproveArrowSpeed extends Slot implements Perks{

	public ImproveArrowSpeed() {
		sprite = new Sprite(Assets.manager.get(Assets.IMPROVEARROWSPEED_ICON));
	}
	
	public void execute (Player player){
		//
	}
}
