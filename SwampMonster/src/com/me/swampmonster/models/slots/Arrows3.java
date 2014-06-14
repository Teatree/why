package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.utils.AssetsMainManager;

public class Arrows3 extends Slot{
	
	public Arrows3() {
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.THREEARROW_ICON));
		description = "I shoot 3 arrows at thee";
	}
	
	public void execute(Player player){
//		player.arrowEffectCarrier = EffectCarriers.;	
	}
}
