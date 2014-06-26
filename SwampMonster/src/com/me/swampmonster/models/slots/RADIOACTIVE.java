package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class RADIOACTIVE extends Slot implements PositiveEffectInterface{
	public static int level;
	public void execute(Player target) {
		target.setPositiveEffect(PositiveEffects.RADIOACTIVE_AURA);
	}

	public RADIOACTIVE() {
		sprite = new Sprite(Assets.manager.get(Assets.RADIOACTIVE_AURA_ICON));
	}
}