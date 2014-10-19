package com.me.swampmonster.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.items.CHAIN_ARROWS;
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
import com.me.swampmonster.models.items.WEAKENED;

public class ItemGenerator {
	HashMap<Integer, String> itemTypeParams = new HashMap<Integer, String>();
	Map<Integer, Class<? extends Item>> items;
	Items itEmsTypes;
	private Random random = new Random();
	public static Map<Integer, AssetDescriptor<Texture>> poisonTextures;
	public static HashMap<Integer, String> usedTextures = new HashMap<Integer, String>(); 
	
	static {
		poisonTextures = new HashMap<Integer,AssetDescriptor<Texture>>();
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
	
	private static enum Items{
		p0_500(2, 12),
		p500_1000(2, 12),
		p1000_2000(2, 12),
		p2000_4000(2, 12);
		
		public int minItemGenerate;
		public int maxItemGenerate;
		
		private Items(int minItemGenerate, int maxItemGenerate){
			this.maxItemGenerate = maxItemGenerate;
			this.minItemGenerate = minItemGenerate;
		}
	}
	
	public ItemGenerator(){
		random = new Random();
		items = new HashMap<Integer, Class<? extends Item>>();
		items.put(0, Oxygen.class);
		items.put(1, HealthKit.class);
		items.put(2, FADE.class);
		items.put(3, CHAIN_ARROWS.class);
		items.put(4, HASTE.class);
		items.put(5, ICE_THING.class);
		items.put(6, NUKE.class);
		items.put(7, RADIOACTIVE.class);
		items.put(8, WEAKENED.class);
		items.put(9, POISONED.class);
		items.put(10, SCARED.class);
		items.put(11, SLOWED.class);
	}
	
	public Item getItem(int playersScore){
		Item resulItem = generateItem(playersScore);
		return resulItem;
	}
	
	public Item generateSpecialItem(int playerScore){
		setItemParams(Player.absoluteScore);
		int number = random.nextInt(itEmsTypes.maxItemGenerate
				- itEmsTypes.minItemGenerate)
				+ itEmsTypes.minItemGenerate;
		Item item = null;
		try {
			Class<? extends Item> itemClass = items.get(number);
//			Class<? extends Item> itemClass = CHAIN_ARROWS.class;
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
				System.out.println("poisonSprite: " + randomTextureNumber);
			}else{
				item = itemClass.getConstructor().newInstance();
				item.name = item.constatName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
	
	
	public Item generateItem(int playersScore) {
		int probability = random.nextInt(100);
		if (probability > 70){
			return generateSpecialItem(playersScore);
		} else {
			return getPlainItem(playersScore);
		}
	}
	
	public Item getMoreLikelyOxugenItem(int playersScore){
		int probability = random.nextInt(100);
		if (probability > 33){
			return new Oxygen();
		} else {
			return new HealthKit();
		}
	}
	
	private void setItemParams(int playersScore) {
		if(playersScore>=0 && playersScore<500){
			itEmsTypes = Items.p0_500;
		}
		else if(playersScore>=100 && playersScore<1000){
			itEmsTypes = Items.p500_1000;
		}
		else if(playersScore>=1000 && playersScore<2000){
			itEmsTypes = Items.p1000_2000;
		}
		else if(playersScore>=2000){
			itEmsTypes = Items.p2000_4000;
		}
	}
	
	public Item getPlainItem(int playersScore) {
		int probability = random.nextInt(100);
		if (probability > 50){
			return new Oxygen();
		} else {
			return new HealthKit();
		}
	}
}
