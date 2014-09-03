package com.me.swampmonster.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class Oxygen extends Item{

	public Oxygen() {
		super();
		
		animationsStandard.put(State.SPAWNING, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());

	}

	@Override
	public void pickMeUp(Player player) {
		if (player.oxygen < Player.maxOxygen) {
			if (player.oxygen + 50 < Player.maxOxygen) {
				player.oxygen = player.oxygen + 50;
			} else {
				player.oxygen = Player.maxOxygen;
			}
			this.state = State.DEAD;
			System.out.println("Hi! I am radioactive aura");
		}
	}

}
