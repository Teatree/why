package com.me.swampmonster.models.items;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.ProjectileHydra;
import com.me.swampmonster.utils.Assets;

public class CHAIN_ARROWS extends Item{
	public static AssetDescriptor<Texture> poisonSprite;
	
	public CHAIN_ARROWS() {
		super();
		
		animationsStandard.put(State.SPAWNING, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(Assets.manager.get(Assets.oxygenKitItem), 4, 2, 4));
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
	}

	@Override
	public void pickMeUp(Player player) {
		this.state = State.DEAD;
		System.out.println("Hello, I am Chain arrows!");
		
		ProjectileHydra p = new ProjectileHydra(new Vector2(position.x, position.y));
		ProjectileHydra.listHydras.add(p);
	}

}
