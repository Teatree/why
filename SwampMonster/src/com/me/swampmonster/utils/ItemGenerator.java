package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Random;

import com.me.swampmonster.models.Item;

public class ItemGenerator {
	HashMap<Integer, String> itemTypeParams = new HashMap<Integer, String>();
	ItemParams itemParams;
	private Random random = new Random();
	
	private enum ItemParams{
		p0_500(600, 800),
		p500_1000(500, 700),
		p1000_2000(400, 600),
		p2000_4000(300, 500);
		
		public final int minLifeTime;
		public final int maxLifeTime;
		
		private ItemParams(int minLifeTime, int maxLifeTime){
			this.minLifeTime = minLifeTime;
			this.maxLifeTime = maxLifeTime;
		}
	}
	
	public ItemGenerator(){
		itemTypeParams.put(1, "hp");
		itemTypeParams.put(2, "O2");
	}
	
	public Item getItem(int playersScore){
		setItemParams(playersScore);
		String type = itemTypeParams.get(random.nextInt(2));
		int lifeTime = random.nextInt(itemParams.maxLifeTime - itemParams.minLifeTime) + itemParams.minLifeTime;
		Item item = new Item(type, lifeTime);
		return item;
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
