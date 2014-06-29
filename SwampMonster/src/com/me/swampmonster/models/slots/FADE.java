package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class FADE extends Slot implements PositiveEffectInterface{
	
	public static int level;
		
	public void execute(Player target) {
		target.setPositiveEffect(PositiveEffects.FADE);
		
	}

	public FADE() {
		
		lifeTime = Constants.FADE_LifeTime_L1;
		sprite = new Sprite(Assets.manager.get(Assets.FADE_ICON));
		coolDown = Constants.FADE_CoolDown_L1;
	}
}
