package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class SPEED_BOOST extends Slot implements PositiveEffectInterface{
	public static int level;
	private static Map <Integer, String> descriptionByLevel;
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.SPEED_BOOST_Description_L1);
		descriptionByLevel.put(1, Constants.SPEED_BOOST_Description_L2);
		descriptionByLevel.put(2, Constants.SPEED_BOOST_Description_L3);
		descriptionByLevel.put(3, Constants.SPEED_BOOST_Description_L4);
		descriptionByLevel.put(4, Constants.SPEED_BOOST_Description_L5);
	}
	public void execute(Player target) {
		target.setPositiveEffect(PositiveEffects.SPEED_BOOST);
	}

	public SPEED_BOOST() {
		sprite = new Sprite(Assets.manager.get(Assets.SPEED_BOOST_ICON));
		
		switch (level) {
		case 0:
			lifeTime = Constants.SPEED_BOOST_LifeTime_L1;
			coolDown = Constants.SPEED_BOOST_CoolDown_L1;
			break;
		case 1:
			lifeTime = Constants.SPEED_BOOST_LifeTime_L2;
			coolDown = Constants.SPEED_BOOST_CoolDown_L2;
			break;
		case 2:
			lifeTime = Constants.SPEED_BOOST_LifeTime_L3;
			coolDown = Constants.SPEED_BOOST_CoolDown_L3;
			break;
		case 3:
			lifeTime = Constants.SPEED_BOOST_LifeTime_L4;
			coolDown = Constants.SPEED_BOOST_CoolDown_L4;
			break;
		case 4:
			lifeTime = Constants.SPEED_BOOST_LifeTime_L5;
			coolDown = Constants.SPEED_BOOST_CoolDown_L5;
			break;
		}
		
	}
	
	@Override
	public String getDescription() {
		return descriptionByLevel.get(level);
	}
}