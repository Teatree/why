package com.me.swampmonster.models.items;


import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class RADIOACTIVE extends Item{
	
	public static float RADIOACTIVE_Damage;
	public static float RADIOACTIVE_Radius;
	
	public RADIOACTIVE() {
		super();
		
		animationsStandard.put(State.SPAWNING, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		Random random = new Random();
		int level = random.nextInt(5);
		switch (level) {
		case 0:
			lifeTime = Constants.RADIOACTIVE_LifeTime_L1;
			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L1;
			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L1;
			break;
		case 1:
			lifeTime = Constants.RADIOACTIVE_LifeTime_L2;
			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L2;
			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L2;
			break;
		case 2:
			lifeTime = Constants.RADIOACTIVE_LifeTime_L3;
			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L3;
			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L3;
			break;
		case 3:
			lifeTime = Constants.RADIOACTIVE_LifeTime_L4;
			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L4;
			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L4;
			break;
		case 4:
			lifeTime = Constants.RADIOACTIVE_LifeTime_L5;
			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L5;
			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L5;
			break;
		}
	}
	

	@Override
	public void pickUpMe(Player player) {
		player.setPositiveEffect(PositiveEffects.RADIOACTIVE_AURA);
	}
	
//	@Override
//	public void execute(Player target) {
//		target.setPositiveEffect(PositiveEffects.RADIOACTIVE_AURA);
//	}
//
//	public RADIOACTIVE() {
//		sprite = new Sprite(Assets.manager.get(Assets.RADIOACTIVE_AURA_ICON));
//		
//		switch (level) {
//		case 0:
//			lifeTime = Constants.RADIOACTIVE_LifeTime_L1;
//			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L1;
//			coolDown = Constants.RADIOACTIVE_CoolDown_L1;
//			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L1;
//			break;
//		case 1:
//			lifeTime = Constants.RADIOACTIVE_LifeTime_L2;
//			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L2;
//			coolDown = Constants.RADIOACTIVE_CoolDown_L2;
//			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L2;
//			break;
//		case 2:
//			lifeTime = Constants.RADIOACTIVE_LifeTime_L3;
//			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L3;
//			coolDown = Constants.RADIOACTIVE_CoolDown_L3;
//			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L3;
//			break;
//		case 3:
//			lifeTime = Constants.RADIOACTIVE_LifeTime_L4;
//			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L4;
//			coolDown = Constants.RADIOACTIVE_CoolDown_L4;
//			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L4;
//			break;
//		case 4:
//			lifeTime = Constants.RADIOACTIVE_LifeTime_L5;
//			RADIOACTIVE_Damage = Constants.RADIOACTIVE_Damage_L5;
//			coolDown = Constants.RADIOACTIVE_CoolDown_L5;
//			RADIOACTIVE_Radius = Constants.RADIOACTIVE_Radius_L5;
//			break;
//		}
//	}
//
//	@Override
//	public String getDescription() {
//		return descriptionByLevel.get(level);
//	}
}
