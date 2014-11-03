package com.me.swampmonster.models.items;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class HealthKit extends Item{

	public static AssetDescriptor<Texture> poisonSprite;
	
	public HealthKit() {
		super();
		
		animationsStandard.put(State.SPAWNING, new AnimationControl(Assets.manager.get(Assets.healthKitItem), 4, 2, 4));
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.healthKitItem), 4, 2, 4));
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.healthKitItem), 4, 2, 4));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(Assets.manager.get(Assets.healthKitItem), 4, 2, 4));
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		
		circle.radius = 16;
		
		constatName = "HEALTH KIT";
	}

	@Override
	public void pickMeUp(Player player) {
		if (player.health < Player.maxHealth) {
			player.health++;
			if (player.negativeEffectsState == NegativeEffects.POISONED) {
				player.setNegativeEffect(NegativeEffects.NONE);
			}
			state = State.DEAD;
		}
	}

	@Override
	public void parametersForThrowing(Player player) {
		// TODO Auto-generated method stub
		
	}

}
