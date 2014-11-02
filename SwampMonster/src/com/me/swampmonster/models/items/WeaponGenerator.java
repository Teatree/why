package com.me.swampmonster.models.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WeaponGenerator {
	Map<Integer, Class<? extends Weapon>> weapons;
	Weapons weaponTypes;
	private Random random = new Random();
	
	private static enum Weapons{
		p0_500(0, 2),
		p500_1500(0, 3),
		p1500_3000(0, 4),
		p3000_plus(0, 4);
		
		public int minItemGenerate;
		public int maxItemGenerate;
		
		private Weapons(int minWepGenerate, int maxWepGenerate){
			this.maxItemGenerate = maxWepGenerate;
			this.minItemGenerate = minWepGenerate;
		}
	}
	
	public WeaponGenerator(){
		random = new Random();
		weapons = new HashMap<Integer, Class<? extends Weapon>>();
		weapons.put(0, Bow.class);
		weapons.put(1, SlingShot.class);
		weapons.put(2, Spear.class);
		weapons.put(3, CrossBow.class);
		weapons.put(4, BuzzShot.class);
	}
	
	public Weapon generateWep(int playersScore) {
		int probability = random.nextInt(100);
//		if (probability > 70){
//			return generateSpecialWep(playersScore);
//		} else {
			return getPlainWep(playersScore);
//		}
	}
	//
	public Weapon getWep(int playersScore){
		Weapon resultWep = generateWeapon(playersScore);
		return resultWep;
	}
	
//	public Item generateSpecialWep(int playerScore){
//		setItemParams(Player.absoluteScore);
//		int number = random.nextInt(weaponTypes.maxItemGenerate
//				- weaponTypes.minItemGenerate)
//				+ weaponTypes.minItemGenerate;
//		Item item = null;
//		try {
//			Class<? extends Item> itemClass = weapons.get(number);
////			Class<? extends Item> itemClass = RADIOACTIVE.class;
//			int randomTextureNumber;
//			if (itemClass.getDeclaredField("poisonSprite").get(null) == null) {
//				randomTextureNumber = random.nextInt(poisonTextures.size());
//				while (usedTextures.keySet().contains(randomTextureNumber)) {
//					randomTextureNumber = random.nextInt(poisonTextures.size());
//				}
//				usedTextures.put(randomTextureNumber, itemClass.getName());
//				try {
//					Field hack = itemClass.getDeclaredField("poisonSprite");
//					hack.setAccessible(true);
//					hack.set(null, poisonTextures.get(randomTextureNumber));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				item = itemClass.getConstructor().newInstance();
//				System.out.println("poisonSprite: " + randomTextureNumber);
//			}else{
//				item = itemClass.getConstructor().newInstance();
//				item.name = item.constatName;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return item;
//	}
	
	
	public Weapon generateWeapon(int playersScore) {
		int probability = random.nextInt(100);
//		if (probability > 70){
//			return generateSpecialItem(playersScore);
//		} else {
			return getPlainWep(playersScore);
//		}
	}
	
	
	private void setItemParams(int playersScore) {
		if(playersScore>=0 && playersScore<500){
			weaponTypes = Weapons.p0_500;
		}
		else if(playersScore>=500 && playersScore<1500){
			weaponTypes = Weapons.p500_1500;
		}
		else if(playersScore>=1500 && playersScore<3000){
			weaponTypes = Weapons.p1500_3000;
		}
		else if(playersScore>=3000){
			weaponTypes = Weapons.p3000_plus;
		}
	}
	
	public Weapon getPlainWep(int playersScore) {
		int wepType = random.nextInt(5);
		Weapon result;
		try {
			result = weapons.get(wepType).getConstructor().newInstance();
			return result;
		} catch (Exception e) {
			return null;
		}
//		if (probability > 50){
//			return new Oxygen();
//		} else {
//		}
	}

}
