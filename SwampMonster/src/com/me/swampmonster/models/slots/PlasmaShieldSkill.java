package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.PlasmaShield;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class PlasmaShieldSkill extends Slot{

	public static int level;
	private static Map <Integer, String> descriptionByLevel;
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.PLASMA_SHIELD_Description_L1);
		descriptionByLevel.put(1, Constants.PLASMA_SHIELD_Description_L2);
		descriptionByLevel.put(2, Constants.PLASMA_SHIELD_Description_L3);
		descriptionByLevel.put(3, Constants.PLASMA_SHIELD_Description_L4);
		descriptionByLevel.put(4, Constants.PLASMA_SHIELD_Description_L5);
	}
	public PlasmaShieldSkill() {
		sprite = new Sprite(Assets.manager.get(Assets.SHADOW_ARROW_ICON));
		
		switch (level) {
		case 0:
			coolDown = Constants.PLASMA_SHIELD_CoolDown_L1;
			lifeTime = Constants.PLASMA_SHIELD_LifeTime_L1;
			break;
		case 1:
			coolDown = Constants.PLASMA_SHIELD_CoolDown_L2;
			lifeTime = Constants.PLASMA_SHIELD_LifeTime_L2;
			break;
		case 2:
			coolDown = Constants.PLASMA_SHIELD_CoolDown_L3;
			lifeTime = Constants.PLASMA_SHIELD_LifeTime_L3;
			break;
		case 3:
			coolDown = Constants.PLASMA_SHIELD_CoolDown_L4;
			lifeTime = Constants.PLASMA_SHIELD_LifeTime_L4;
			break;
		case 4:
			coolDown = Constants.PLASMA_SHIELD_CoolDown_L5;
			lifeTime = Constants.PLASMA_SHIELD_LifeTime_L5;
			break;
		}
	}
	
	@Override
	public void execute(Player player){
		L1.plasmaShield = new PlasmaShield(player.position); 
		L1.plasmaShield.lifeTime = lifeTime;
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
