package com.me.swampmonster.models.items;

import java.util.Random;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class WEAKENED extends Item{

	public static AssetDescriptor<Texture> poisonSprite;
	
	public WEAKENED() {
		super();
				
				
		Random random = new Random();
		int level = random.nextInt(5);
		switch (level) {
		case 0:
			lifeTime = Constants.WEAKENED_LifeTime_L1;
			break;
		case 1:
			lifeTime = Constants.WEAKENED_LifeTime_L2;
			break;
		case 2:
			lifeTime = Constants.WEAKENED_LifeTime_L3;
			break;
		case 3:
			lifeTime = Constants.WEAKENED_LifeTime_L4;
			break;
		case 4:
			lifeTime = Constants.WEAKENED_LifeTime_L5;
			break;
		}
		
		animationsStandard.put(State.SPAWNING, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		
		constatName = "WEAKENED";
	}

	@Override
	public void pickMeUp(Player player) {
		this.state=State.DEAD;
		player.setNegativeEffect(NegativeEffects.WEAKENED);
		System.out.println("I am Haste, biatch");
	}

}
