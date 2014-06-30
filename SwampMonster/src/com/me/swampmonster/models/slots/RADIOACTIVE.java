package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class RADIOACTIVE extends Slot implements PositiveEffectInterface{
	public static int level;
	public static float RADIOACTIVE_Damage;
	public static float RADIOACTIVE_Radius;
	private static Map <Integer, String> descriptionByLevel;
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.RADIOACTIVE_Description_L1);
		descriptionByLevel.put(1, Constants.RADIOACTIVE_Description_L2);
		descriptionByLevel.put(2, Constants.RADIOACTIVE_Description_L3);
		descriptionByLevel.put(3, Constants.RADIOACTIVE_Description_L4);
		descriptionByLevel.put(4, Constants.RADIOACTIVE_Description_L5);
	}
	
	public void execute(Player target) {
		target.setPositiveEffect(PositiveEffects.RADIOACTIVE_AURA);
	}

	public RADIOACTIVE() {
		sprite = new Sprite(Assets.manager.get(Assets.RADIOACTIVE_AURA_ICON));
		
		switch (level) {
		case 0:
			lifeTime = Constants.RADIOACTIVE_LifeTime_L1;
			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L1;
			coolDown = Constants.RADIOACTIVE_CoolDown_L1;
			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L1;
			break;
		case 1:
			lifeTime = Constants.RADIOACTIVE_LifeTime_L2;
			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L2;
			coolDown = Constants.RADIOACTIVE_CoolDown_L2;
			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L2;
			break;
		case 2:
			lifeTime = Constants.RADIOACTIVE_LifeTime_L3;
			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L3;
			coolDown = Constants.RADIOACTIVE_CoolDown_L3;
			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L3;
			break;
		case 3:
			lifeTime = Constants.RADIOACTIVE_LifeTime_L4;
			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L4;
			coolDown = Constants.RADIOACTIVE_CoolDown_L4;
			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L4;
			break;
		case 4:
			lifeTime = Constants.RADIOACTIVE_LifeTime_L5;
			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L5;
			coolDown = Constants.RADIOACTIVE_CoolDown_L5;
			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L5;
			break;
		}
	}

	@Override
	public String getDescription() {
		return descriptionByLevel.get(level);
	}
}