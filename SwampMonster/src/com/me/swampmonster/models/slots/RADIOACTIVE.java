package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class RADIOACTIVE extends Slot implements PositiveEffectInterface{
	public static int level;
	public void execute(Player target) {
		target.setPositiveEffect(PositiveEffects.RADIOACTIVE_AURA);
	}

	public RADIOACTIVE() {
		lifeTime = Constants.RADIOACTIVE_LifeTime_L1;
		sprite = new Sprite(Assets.manager.get(Assets.RADIOACTIVE_AURA_ICON));
		coolDown = Constants.RADIOACTIVE_CoolDown_L1;
	}
}