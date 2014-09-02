package com.me.swampmonster.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.utils.Assets;

public class HASTE extends Item{

	public HASTE() {
		super();
		
		animationsStandard.put(State.SPAWNING, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
	}

}
