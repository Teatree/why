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

public class FADE extends Item {
	public static AssetDescriptor<Texture> poisonSprite;
	
	public FADE() {
		super();
		Random random = new Random();
		int level = random.nextInt(5);

		switch (level) {
		case 0:
			lifeTime = Constants.FADE_LifeTime_L1;
			break;
		case 1:
			lifeTime = Constants.FADE_LifeTime_L2;
			break;
		case 2:
			lifeTime = Constants.FADE_LifeTime_L3;
			break;
		case 3:
			lifeTime = Constants.FADE_LifeTime_L4;
			break;
		case 4:
			lifeTime = Constants.FADE_LifeTime_L5;
			break;
		}

		animationsStandard.put(State.SPAWNING, new AnimationControl(
				Assets.manager.get(poisonSprite), 4, 2, 4));
		animationsStandard.put(State.STANDARD, new AnimationControl(
				Assets.manager.get(poisonSprite), 4, 2, 4));
		animationsStandard.put(State.DEAD,
				new AnimationControl(Assets.manager.get(poisonSprite),
						4, 2, 4));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(
				Assets.manager.get(poisonSprite), 4, 2, 4));

		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
	}

	public static int level;

	@Override
	public void pickMeUp(Player player) {
		player.positiveEffectSprite = new Sprite(
				Assets.manager.get(Assets.FADE_ICON));
		player.setPositiveEffect(PositiveEffects.FADE);
		// System.out.println("Hi! I am RADIOACTIVE aura, circle radius: ");
		this.state = State.DEAD;

	}

	// @Override
	// public void execute(Player target) {
	// target.setPositiveEffect(PositiveEffects.FADE);
	//
	// }
	//
	// public FADE() {
	// sprite = new Sprite(Assets.manager.get(Assets.FADE_ICON));
	//
	// switch (level) {
	// case 0:
	// lifeTime = Constants.FADE_LifeTime_L1;
	// coolDown = Constants.FADE_CoolDown_L1;
	// break;
	// case 1:
	// lifeTime = Constants.FADE_LifeTime_L2;
	// coolDown = Constants.FADE_CoolDown_L2;
	// break;
	// case 2:
	// lifeTime = Constants.FADE_LifeTime_L3;
	// coolDown = Constants.FADE_CoolDown_L3;
	// break;
	// case 3:
	// lifeTime = Constants.FADE_LifeTime_L4;
	// coolDown = Constants.FADE_CoolDown_L4;
	// break;
	// case 4:
	// lifeTime = Constants.FADE_LifeTime_L5;
	// coolDown = Constants.FADE_CoolDown_L5;
	// break;
	// }
	// }

	// @Override
	// public String getDescription() {
	// return descriptionByLevel.get(level);
	// }
}
