package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class PoisonArrow extends Slot{
	public static int level;
	private static Map <Integer, String> descriptionByLevel;
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.PoisonArrow_Description_L1);
		descriptionByLevel.put(1, Constants.PoisonArrow_Description_L2);
		descriptionByLevel.put(2, Constants.PoisonArrow_Description_L3);
		descriptionByLevel.put(3, Constants.PoisonArrow_Description_L4);
		descriptionByLevel.put(4, Constants.PoisonArrow_Description_L5);
	}
	
	public PoisonArrow() {
		name = Constants.PoisonArrow_Name;
		sprite = new Sprite(Assets.manager.get(Assets.POISONED_ARROW_ICON));
		coolDown = Constants.PoisonArrow_CoolDown_L1;
		
		switch (level) {
		case 0:
			coolDown = Constants.PoisonArrow_CoolDown_L1;
			break;
		case 1:
			coolDown = Constants.PoisonArrow_CoolDown_L2;
			break;
		case 2:
			coolDown = Constants.PoisonArrow_CoolDown_L3;
			break;
		case 3:
			coolDown = Constants.PoisonArrow_CoolDown_L4;
			break;
		case 4:
			coolDown = Constants.PoisonArrow_CoolDown_L5;
			break;
		}
	}
	
	@Override
	public void execute(Player player){
		player.arrowEffectCarrier = EffectCarriers.POISONED;
	}
	
	@Override
	public String getDescription() {
		return descriptionByLevel.get(level);
	}
	
	@Override
	public String getDescriptionForSaved() {
		return descriptionByLevel.get(level-1);
	}
}
