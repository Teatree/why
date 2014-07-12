package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.me.swampmonster.models.Item;

public class ItemGenerator {
	
	private static final String HEALTH = "hp";
	private static final String OXYGEN = "O2";
	
	HashMap<Integer, String> itemTypeParams = new HashMap<Integer, String>();
	ItemParams itemParams;
	private Random random = new Random();
	
	private static enum ItemParams{
		p0_500(200, 230, 10, 20),
		p500_1000(300, 310, 200, 300),
		p1000_2000(300, 310, 300, 400),
		p2000_4000(300, 310, 400, 500);
		
		public final int minLifeTime;
		public final int maxLifeTime;
		public final int minSpawnRate;
		public final int maxSpawnRate;
		
		
		private ItemParams(int minLifeTime, int maxLifeTime, int minSpawnRate,
				int maxSpawnRate) {
			this.minLifeTime = minLifeTime;
			this.maxLifeTime = maxLifeTime;
			this.minSpawnRate = minSpawnRate;
			this.maxSpawnRate = maxSpawnRate;
		}
	}
	
	public ItemGenerator(){
		itemTypeParams.put(0, HEALTH);
		itemTypeParams.put(1, OXYGEN);
	}
	
	public Item getItem(int playersScore){
		return generateItem(playersScore, itemTypeParams);
	}
	
	public Item generateItem(int playersScore, Map<Integer, String> param) {
		setItemParams(playersScore);
		String type = param.get(random.nextInt(param.size()));
		int lifeTime = random.nextInt(itemParams.maxLifeTime - itemParams.minLifeTime) + itemParams.minLifeTime;
		Item item = new Item(type, lifeTime);
		return item;
	}
	
	public Item getMoreLikelyOxugenItem(int playersScore){
		Map<Integer, String> param = new HashMap<Integer, String>(itemTypeParams);
		param.put(2, OXYGEN);
		return generateItem(playersScore, param);
	}
	
	public int generateSpawnRate(int playersScore){
		setItemParams(playersScore);
		return random.nextInt(itemParams.maxSpawnRate - itemParams.minSpawnRate) + itemParams.minSpawnRate;
	}
	
	private void setItemParams(int playersScore) {
		if(playersScore>=0 && playersScore<500){
			itemParams = ItemParams.p0_500;
		}
		else if(playersScore>100 && playersScore<1000){
			itemParams = ItemParams.p500_1000;
		}
		else if(playersScore>1000 && playersScore<2000){
			itemParams = ItemParams.p1000_2000;
		}
		else if(playersScore>2000){
			itemParams = ItemParams.p2000_4000;
		}
	}
}
