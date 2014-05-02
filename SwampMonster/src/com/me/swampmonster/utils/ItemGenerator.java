package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Random;

import com.me.swampmonster.models.Item;

public class ItemGenerator {
	HashMap<Integer, String> itemTypeParams = new HashMap<Integer, String>();
	ItemParams itemParams;
	private Random random = new Random();
	
	private enum ItemParams{
		p0_500(600, 800, 200, 300),
		p500_1000(500, 700, 300, 400),
		p1000_2000(400, 600, 400, 500),
		p2000_4000(300, 500, 1000, 1000);
		
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
		itemTypeParams.put(0, "hp");
		itemTypeParams.put(1, "O2");
	}
	
	public Item getItem(int playersScore){
		setItemParams(playersScore);
		String type = itemTypeParams.get(random.nextInt(2));
		int lifeTime = random.nextInt(itemParams.maxLifeTime - itemParams.minLifeTime) + itemParams.minLifeTime;
		Item item = new Item(type, lifeTime);
		return item;
	}
	
	public int generateSpawnRate(int playersScore){
		setItemParams(playersScore);
		return random.nextInt(itemParams.maxSpawnRate - itemParams.minSpawnRate) + itemParams.minSpawnRate;
	}
	
	private void setItemParams(int playersScore) {
		if(playersScore>=0 && playersScore<100){
			itemParams = itemParams.p0_500;
		}
		else if(playersScore>100 && playersScore<1000){
			itemParams = itemParams.p500_1000;
		}
		else if(playersScore>1000 && playersScore<2000){
			itemParams = itemParams.p1000_2000;
		}
		else if(playersScore>2000 && playersScore<4000){
			itemParams = itemParams.p2000_4000;
		}
	}
}
