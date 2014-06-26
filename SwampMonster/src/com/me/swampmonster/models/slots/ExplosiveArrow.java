package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.utils.Assets;

public class ExplosiveArrow extends Slot{
	
	public static int level;
	
	public ExplosiveArrow() {
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_ARROW_ICON));
		coolDown = 200;
	}
	
	public void execute(Player player){
		player.arrowEffectCarrier = EffectCarriers.EXPLOSIVE;
	}
}
