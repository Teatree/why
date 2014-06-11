package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.AssetsMainManager;

public class FADE extends Slot implements PositiveEffectInterface{
		public void execute(Player target) {
			target.setPositiveEffect(PositiveEffects.FADE);
		}

		public FADE() {
			sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.FADE_ICON));
		}
}
