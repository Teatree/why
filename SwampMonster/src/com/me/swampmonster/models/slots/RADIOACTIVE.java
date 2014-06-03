package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.AssetsMainManager;

public class RADIOACTIVE extends Slot{
	public void execute(Player target) {
		target.setPositiveEffect(PositiveEffects.RADIOACTIVE_AURA);
	}

	public RADIOACTIVE() {
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.RADIOACTIVE_AURA_ICON));
	}
}