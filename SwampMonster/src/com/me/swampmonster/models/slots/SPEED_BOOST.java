package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class SPEED_BOOST extends Slot implements PositiveEffectInterface{
	public static int level;
	public void execute(Player target) {
		target.setPositiveEffect(PositiveEffects.SPEED_BOOST);
	}

	public SPEED_BOOST() {
		sprite = new Sprite(Assets.manager.get(Assets.SPEED_BOOST_ICON));
		
		lifeTime = Constants.SPEED_BOOST_LifeTime_L1;
		coolDown = Constants.SPEED_BOOST_CoolDown_L1;
		description = Constants.SPEED_BOOST_Description_L1;
	}
}