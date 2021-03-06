package com.me.swampmonster.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.items.Bow;
import com.me.swampmonster.models.items.BuzzShot;
import com.me.swampmonster.models.items.CHAIN_ARROWS;
import com.me.swampmonster.models.items.CrossBow;
import com.me.swampmonster.models.items.FADE;
import com.me.swampmonster.models.items.HASTE;
import com.me.swampmonster.models.items.HealthKit;
import com.me.swampmonster.models.items.ICE_THING;
import com.me.swampmonster.models.items.NUKE;
import com.me.swampmonster.models.items.Oxygen;
import com.me.swampmonster.models.items.POISONED;
import com.me.swampmonster.models.items.RADIOACTIVE;
import com.me.swampmonster.models.items.SCARED;
import com.me.swampmonster.models.items.SLOWED;
import com.me.swampmonster.models.items.SlingShot;
import com.me.swampmonster.models.items.Spear;
import com.me.swampmonster.models.items.WEAKENED;
import com.me.swampmonster.models.items.WeaponGenerator;
import com.me.swampmonster.models.items.WeaponItem;

public class ItemGenerator {
	Map<Integer, Class<? extends Item>> items;
	Items itEmsTypes;
	WeaponGenerator wepGenerator = new WeaponGenerator();
	private Random random = new Random();
	public static Map<Integer, AssetDescriptor<Texture>> poisonTextures;
	public static HashMap<Integer, String> usedTextures = new HashMap<Integer, String>();

	static {
		poisonTextures = new HashMap<Integer, AssetDescriptor<Texture>>();
		poisonTextures.put(0, Assets.blueItem);
		poisonTextures.put(1, Assets.greenItem);
		poisonTextures.put(2, Assets.redItem);
		poisonTextures.put(3, Assets.yellowItem);
		poisonTextures.put(4, Assets.purpleItem);
		poisonTextures.put(5, Assets.orangeItem);
		poisonTextures.put(6, Assets.pinkItem);
		poisonTextures.put(7, Assets.lightBlueItem);
		poisonTextures.put(8, Assets.greyItem);
	}

	private static enum Items {
		p0_500(2, 12), p500_1500(2, 10), p1500_3000(2, 11), p3000_plus(2, 12);

		public int minItemGenerate;
		public int maxItemGenerate;

		private Items(int minItemGenerate, int maxItemGenerate) {
			this.maxItemGenerate = maxItemGenerate;
			this.minItemGenerate = minItemGenerate;
		}
	}

	public ItemGenerator() {
		random = new Random();
		items = new HashMap<Integer, Class<? extends Item>>();
		items.put(0, Oxygen.class);
		items.put(1, HealthKit.class);
		items.put(2, FADE.class);
		items.put(3, ICE_THING.class);
		items.put(4, NUKE.class);
		items.put(5, POISONED.class);
		items.put(6, SCARED.class);
		items.put(7, HASTE.class);
		items.put(8, SLOWED.class);
		items.put(9, CHAIN_ARROWS.class);
		items.put(10, WEAKENED.class);
		items.put(11, RADIOACTIVE.class);
	}

	public Item getItem(int playersScore) {
		Item resulItem = generateItem(playersScore);
		// System.out.println("getItem");
		// if(TutorialLevel.step == 10 || TutorialLevel.step == 11){
		// System.out.println("YES");
		// return new Oxygen();
		// }else{
		return resulItem;
		// }
	}

	public Item getWeaponItem(int playersScore) {
		WeaponItem resulItem = new WeaponItem();
		resulItem.weapon = wepGenerator.generateSpecialWep(playersScore);

		if (resulItem.weapon instanceof Bow) {
			resulItem.animationsStandard.put(State.SPAWNING,
					new AnimationControl(Assets.manager.get(Assets.wepBOW), 4,
							2, 4));
			resulItem.animationsStandard.put(State.STANDARD,
					new AnimationControl(Assets.manager.get(Assets.wepBOW), 4,
							2, 4));
			resulItem.animationsStandard.put(State.DEAD,
					new AnimationControl(Assets.manager.get(Assets.wepBOW), 4,
							2, 4));
			resulItem.animationsStandard.put(State.DESPAWNING,
					new AnimationControl(Assets.manager.get(Assets.wepBOW), 4,
							2, 4));
			// resulItem.weapon.state = State.STANDARD;
			resulItem.sprite = new Sprite(
					resulItem.animationsStandard.get(State.SPAWNING)
							.getCurrentFrame());
		}
		if (resulItem.weapon instanceof CrossBow) {
			resulItem.animationsStandard.put(State.SPAWNING,
					new AnimationControl(
							Assets.manager.get(Assets.wepCROSSBOW), 4, 2, 4));
			resulItem.animationsStandard.put(State.STANDARD,
					new AnimationControl(
							Assets.manager.get(Assets.wepCROSSBOW), 4, 2, 4));
			resulItem.animationsStandard.put(State.DEAD,
					new AnimationControl(
							Assets.manager.get(Assets.wepCROSSBOW), 4, 2, 4));
			resulItem.animationsStandard.put(State.DESPAWNING,
					new AnimationControl(
							Assets.manager.get(Assets.wepCROSSBOW), 4, 2, 4));
			// resulItem.weapon.state = State.SPAWNING;
			resulItem.sprite = new Sprite(
					resulItem.animationsStandard.get(State.SPAWNING)
							.getCurrentFrame());
		}

		if (resulItem.weapon instanceof BuzzShot) {
			resulItem.animationsStandard.put(State.SPAWNING,
					new AnimationControl(
							Assets.manager.get(Assets.wepBUZZSHOT), 4, 2, 4));
			resulItem.animationsStandard.put(State.STANDARD,
					new AnimationControl(
							Assets.manager.get(Assets.wepBUZZSHOT), 4, 2, 4));
			resulItem.animationsStandard.put(State.DEAD,
					new AnimationControl(
							Assets.manager.get(Assets.wepBUZZSHOT), 4, 2, 4));
			resulItem.animationsStandard.put(State.DESPAWNING,
					new AnimationControl(
							Assets.manager.get(Assets.wepBUZZSHOT), 4, 2, 4));
			// resulItem.weapon.state = State.SPAWNING;
			resulItem.sprite = new Sprite(
					resulItem.animationsStandard.get(State.SPAWNING)
							.getCurrentFrame());
		}

		if (resulItem.weapon instanceof SlingShot) {
			resulItem.animationsStandard.put(
					State.SPAWNING,
					new AnimationControl(Assets.manager
							.get(Assets.wepSLINGSHOT), 4, 2, 4));
			resulItem.animationsStandard.put(
					State.STANDARD,
					new AnimationControl(Assets.manager
							.get(Assets.wepSLINGSHOT), 4, 2, 4));
			resulItem.animationsStandard.put(
					State.DEAD,
					new AnimationControl(Assets.manager
							.get(Assets.wepSLINGSHOT), 4, 2, 4));
			resulItem.animationsStandard.put(
					State.DESPAWNING,
					new AnimationControl(Assets.manager
							.get(Assets.wepSLINGSHOT), 4, 2, 4));
			// resulItem.weapon.state = State.SPAWNING;
			resulItem.sprite = new Sprite(
					resulItem.animationsStandard.get(State.SPAWNING)
							.getCurrentFrame());
		}

		if (resulItem.weapon instanceof Spear) {
			resulItem.animationsStandard.put(State.SPAWNING,
					new AnimationControl(Assets.manager.get(Assets.wepSPEAR),
							4, 2, 4));
			resulItem.animationsStandard.put(State.STANDARD,
					new AnimationControl(Assets.manager.get(Assets.wepSPEAR),
							4, 2, 4));
			resulItem.animationsStandard.put(State.DEAD,
					new AnimationControl(Assets.manager.get(Assets.wepSPEAR),
							4, 2, 4));
			resulItem.animationsStandard.put(State.DESPAWNING,
					new AnimationControl(Assets.manager.get(Assets.wepSPEAR),
							4, 2, 4));
			// resulItem.weapon.state = State.SPAWNING;
			resulItem.sprite = new Sprite(
					resulItem.animationsStandard.get(State.SPAWNING)
							.getCurrentFrame());
		}

		return resulItem;
	}

	public Item generateSpecialItem(int playerScore) {
		setItemParams(Player.absoluteScore);
		int number = random.nextInt(itEmsTypes.maxItemGenerate
				- itEmsTypes.minItemGenerate)
				+ itEmsTypes.minItemGenerate;
		Item item = null;
		try {
			Class<? extends Item> itemClass = items.get(number);
//			 Class<? extends Item> itemClass = POISONED.class;
			int randomTextureNumber;
			if (itemClass.getDeclaredField("poisonSprite").get(null) == null) {
				randomTextureNumber = random.nextInt(poisonTextures.size());
				while (usedTextures.keySet().contains(randomTextureNumber)) {
					randomTextureNumber = random.nextInt(poisonTextures.size());
				}
				usedTextures.put(randomTextureNumber, itemClass.getName());
				try {
					Field hack = itemClass.getDeclaredField("poisonSprite");
					hack.setAccessible(true);
					hack.set(null, poisonTextures.get(randomTextureNumber));
				} catch (Exception e) {
					e.printStackTrace();
				}
				item = itemClass.getConstructor().newInstance();
				// System.out.println("poisonSprite: " + randomTextureNumber);
			} else {
				item = itemClass.getConstructor().newInstance();
//				item.name = item.constatName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	public Item generateItem(int playersScore) {
		int probability = random.nextInt(100);
		if (probability < 40) {
//			 return getPlainItem(playersScore);
			 return generateSpecialItem(playersScore);
//			return getWeaponItem(playersScore);
		} else if (probability >= 40 && probability <= 80) {
			 return generateSpecialItem(playersScore);
//			return getWeaponItem(playersScore);
		} else {
//			return getWeaponItem(playersScore);
			return generateSpecialItem(playersScore);
		}
	}

	public Item getMoreLikelyOxugenItem(int playersScore) {
		int probability = random.nextInt(100);
		if (probability > 33) {
			return new Oxygen();
		} else {
			return new HealthKit();
		}
	}

	private void setItemParams(int playersScore) {
		if (playersScore >= 0 && playersScore < 500) {
			itEmsTypes = Items.p0_500;
		} else if (playersScore >= 500 && playersScore < 1500) {
			itEmsTypes = Items.p500_1500;
		} else if (playersScore >= 1500 && playersScore < 3000) {
			itEmsTypes = Items.p1500_3000;
		} else if (playersScore >= 3000) {
			itEmsTypes = Items.p3000_plus;
		}
	}

	public Item getPlainItem(int playersScore) {
		int probability = random.nextInt(100);
		if (probability > 50) {
			return new Oxygen();
		} else {
			return new HealthKit();
		}
	}
}
