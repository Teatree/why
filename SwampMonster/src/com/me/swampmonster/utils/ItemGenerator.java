package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//import com.me.swampmonster.GUI.HealthBar;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.items.CHAIN_ARROWS;
import com.me.swampmonster.models.items.FADE;
import com.me.swampmonster.models.items.HASTE;
import com.me.swampmonster.models.items.HealthKit;
import com.me.swampmonster.models.items.ICE_THING;
import com.me.swampmonster.models.items.NUKE;
import com.me.swampmonster.models.items.Oxygen;
import com.me.swampmonster.models.items.RADIOACTIVE;
//import com.me.swampmonster.models.slots.Slot;

public class ItemGenerator {
	
//	private static final String HEALTH = "hp";
//	private static final String OXYGEN = "O2";
	
	HashMap<Integer, String> itemTypeParams = new HashMap<Integer, String>();
	Map<Integer, Class<? extends Item>> items;
//	ItemParams itemParams;
	Items itEms;
	private Random random = new Random();
	
//	private static enum ItemParams{
//		p0_500(200, 230, 10, 20),
//		p500_1000(300, 310, 200, 300),
//		p1000_2000(300, 310, 300, 400),
//		p2000_4000(300, 310, 400, 500);
//		
//		public final int minLifeTime;
//		public final int maxLifeTime;
//		public final int minSpawnRate;
//		public final int maxSpawnRate;
//		
//		private ItemParams(int minLifeTime, int maxLifeTime, int minSpawnRate,
//				int maxSpawnRate) {
//			this.minLifeTime = minLifeTime;
//			this.maxLifeTime = maxLifeTime;
//			this.minSpawnRate = minSpawnRate;
//			this.maxSpawnRate = maxSpawnRate;
//		}
//	}
	
	private static enum Items{
		p0_500(0, 7),
		p500_1000(0, 7),
		p1000_2000(0, 7),
		p2000_4000(0, 7);
		
		public int minItemGenerate;
		public int maxItemGenerate;
		
		private Items(int minItemGenerate, int maxItemGenerate){
			this.maxItemGenerate = maxItemGenerate;
			this.minItemGenerate = minItemGenerate;
			
		}
	}
	
	
	public ItemGenerator(){
//		itemTypeParams.put(0, HEALTH);
//		itemTypeParams.put(1, OXYGEN);
		
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
	}
	
	public Item getItem(int playersScore){
		return generateItem(playersScore);
	}
	
	public Item generateItem(int playersScore) {
		setItemParams(Player.absoluteScore);
		int number = random.nextInt(itEms.maxItemGenerate - itEms.minItemGenerate) + itEms.minItemGenerate ;
		Item item = null;
		try {
			item = items.get(number).getConstructor().newInstance();
			System.out.println(item + " num "+ number);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return item;
	}
	
//	public Item getMoreLikelyOxugenItem(int playersScore){
//		Map<Integer, String> param = new HashMap<Integer, String>(itemTypeParams);
//		param.put(2, OXYGEN);
//		return generateItem(playersScore,);
//	}
	
//	public int generateSpawnRate(int playersScore){
//		setItemParams(playersScore);
//		//:TODO NPE
//		return random.nextInt(itemParams.maxSpawnRate - itemParams.minSpawnRate) + itemParams.minSpawnRate;
//	}
//	
	private void setItemParams(int playersScore) {
		if(playersScore>=0 && playersScore<500){
//			itemParams = ItemParams.p0_500;
			itEms = Items.p0_500;
		}
		else if(playersScore>=100 && playersScore<1000){
//			itemParams = ItemParams.p500_1000;
			itEms = Items.p500_1000;
		}
		else if(playersScore>=1000 && playersScore<2000){
//			itemParams = ItemParams.p1000_2000;
			itEms = Items.p1000_2000;
		}
		else if(playersScore>=2000){
//			itemParams = ItemParams.p2000_4000;
			itEms = Items.p2000_4000;
		}
	}
}
