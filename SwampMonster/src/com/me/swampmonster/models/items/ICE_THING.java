package com.me.swampmonster.models.items;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Projectile.EffectCarriers;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;

public class ICE_THING extends Item{

	public static int negativeEffectLifeTime;
	public static AssetDescriptor<Texture> poisonSprite;
	
	public ICE_THING() {
		super();
		
		animationsStandard.put(State.SPAWNING, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(Assets.manager.get(poisonSprite), 4, 2, 4));
		
		negativeEffectLifeTime = 480;
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		
		constatName = "ICE THING";
	}

	@Override
	public void pickMeUp(Player player) {
		state=State.DEAD;
		for(Enemy e : L1.enemiesOnStage){
			e.setNegativeEffect(NegativeEffects.STUN);
		}
		System.out.println("IceThing");
	}

	@Override
	public void parametersForThrowing(Player player) {
		this.state = State.DEAD;
		player.arrowEffectCarrier = EffectCarriers.ICE_CUBE;
		
	}

}
