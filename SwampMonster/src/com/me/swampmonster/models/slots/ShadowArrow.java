package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.utils.Assets;

public class ShadowArrow extends Slot{
	
	public ShadowArrow() {
		sprite = new Sprite(Assets.manager.get(Assets.SHADOW_ARROW_ICON));
	}
	
	public void execute(Player player){
		player.arrowEffectCarrier = EffectCarriers.SHADOW;
	}
}
