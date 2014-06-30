package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class ImproveMovementSpeed extends Slot implements Perks{
	public static int level;
	public ImproveMovementSpeed() {
		sprite = new Sprite(Assets.manager.get(Assets.IMPROVEMOVEMENTSPEED_ICON));
	}
	
	public void execute (Player player){
		player.movementSpeed += Constants.ImproveMaxSpeed_SpeedValue_L1;
	}
	
	@Override
	public String getDescription() {
		return "";
	}
}

