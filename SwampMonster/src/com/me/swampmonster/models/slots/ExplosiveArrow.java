package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class ExplosiveArrow extends Slot{
	
	public static int level;
	private static Map <Integer, String> descriptionByLevel;
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.ExplosiveArrow_Description_L1);
		descriptionByLevel.put(1, Constants.ExplosiveArrow_Description_L2);
		descriptionByLevel.put(2, Constants.ExplosiveArrow_Description_L3);
		descriptionByLevel.put(3, Constants.ExplosiveArrow_Description_L4);
		descriptionByLevel.put(4, Constants.ExplosiveArrow_Description_L5);
	}
	
	public ExplosiveArrow() {
		
		switch (level) {
		case 0:
			coolDown = Constants.ExplosiveArrow_CoolDown_L1;
			break;
		case 1:
			coolDown = Constants.ExplosiveArrow_CoolDown_L2;
			break;
		case 2:
			coolDown = Constants.ExplosiveArrow_CoolDown_L3;
			break;
		case 3:
			coolDown = Constants.ExplosiveArrow_CoolDown_L4;
			break;
		case 4:
			coolDown = Constants.ExplosiveArrow_CoolDown_L5;
			break;
		}
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_ARROW_ICON));
	}
	
	public void execute(Player player){
		player.arrowEffectCarrier = EffectCarriers.EXPLOSIVE;
	}

	@Override
	public String getDescription() {
		return descriptionByLevel.get(level);
	}
}
