package com.me.swampmonster.models.items;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class FADE{
	
	public static int level;
	private static Map <Integer, String> descriptionByLevel;
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.FADE_Description_L1);
		descriptionByLevel.put(1, Constants.FADE_Description_L2);
		descriptionByLevel.put(2, Constants.FADE_Description_L3);
		descriptionByLevel.put(3, Constants.FADE_Description_L4);
		descriptionByLevel.put(4, Constants.FADE_Description_L5);
	}
	
	
//	@Override
//	public void execute(Player target) {
//		target.setPositiveEffect(PositiveEffects.FADE);
//		
//	}
//
//	public FADE() {
//		sprite = new Sprite(Assets.manager.get(Assets.FADE_ICON));
//		
//		switch (level) {
//		case 0:
//			lifeTime = Constants.FADE_LifeTime_L1;
//			coolDown = Constants.FADE_CoolDown_L1;
//			break;
//		case 1:
//			lifeTime = Constants.FADE_LifeTime_L2;
//			coolDown = Constants.FADE_CoolDown_L2;
//			break;
//		case 2:
//			lifeTime = Constants.FADE_LifeTime_L3;
//			coolDown = Constants.FADE_CoolDown_L3;
//			break;
//		case 3:
//			lifeTime = Constants.FADE_LifeTime_L4;
//			coolDown = Constants.FADE_CoolDown_L4;
//			break;
//		case 4:
//			lifeTime = Constants.FADE_LifeTime_L5;
//			coolDown = Constants.FADE_CoolDown_L5;
//			break;
//		}
//	}

//	@Override
//	public String getDescription() {
//		return descriptionByLevel.get(level);
//	}
}
