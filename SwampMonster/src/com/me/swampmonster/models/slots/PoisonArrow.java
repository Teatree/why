package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.utils.AssetsMainManager;

public class PoisonArrow extends Slot{
	
	public PoisonArrow() {
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.POISONED_ARROW_ICON));
	}
	
	public void execute(Player player){
		player.arrowEffectCarrier = EffectCarriers.POISONED;
	}
}
