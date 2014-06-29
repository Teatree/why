package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class Arrows3 extends Slot{
	
	public static int level;
	
	public Arrows3() {
		sprite = new Sprite(Assets.manager.get(Assets.THREEARROW_ICON));
		description = Constants.Arrows3_Description_L1;
		coolDown = Constants.Arrows3_CoolDown_L1;
	}
	
	public void execute(Player player){
//		player.arrowEffectCarrier = EffectCarriers.;	
	}
}
