package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.AssetsMainManager;

public class SPEED_BOOST extends Slot implements PositiveEffectInterface{
	public void execute(Player target) {
		target.setPositiveEffect(PositiveEffects.SPEED_BOOST);
	}

	public SPEED_BOOST() {
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.SPEED_BOOST_ICON));
		
		description = "Makes player move faster for a certain amount of time";
	}
}