package com.me.swampmonster.models.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.items.wepMods.DamagePlayerMod;
import com.me.swampmonster.models.items.wepMods.EnemyBleedMod;
import com.me.swampmonster.models.items.wepMods.ExtraDamageMod;
import com.me.swampmonster.models.items.wepMods.ExtraDamageToEnemyTypeMod;
import com.me.swampmonster.models.items.wepMods.HealEnemyMod;
import com.me.swampmonster.models.items.wepMods.Modificator;
import com.me.swampmonster.models.items.wepMods.ShootThoughCollisionMod;
import com.me.swampmonster.models.items.wepMods.SpeedUpEnemyMod;
import com.me.swampmonster.models.items.wepMods.StunEnemyMod;
import com.me.swampmonster.models.items.wepMods.StunPlayerMod;
import com.me.swampmonster.models.items.wepMods.VampireMod;

public class WeaponGenerator {
	Map<Integer, Class<? extends Weapon>> weapons;
	Map<Integer, Class<? extends Modificator>> mods;
	Weapons weaponTypes;
	private Random random = new Random();
	
	private static enum Weapons{
		p0_500(0, 2, 900, 2000),
		p500_1500(0, 3, 900, 2000),
		p1500_3000(0, 4, 900, 2000),
		p3000_plus(0, 4, 900, 2000);
		
		public int minWepGenerate;
		public int maxWepGenerate;
		public int firstModificatorChance;
		public int secondModificatorChance;
		
		private Weapons(int minWepGenerate, int maxWepGenerate, int firstModificatorChance, int secondModificatorChance){
			this.maxWepGenerate = maxWepGenerate;
			this.minWepGenerate = minWepGenerate;
			this.firstModificatorChance = firstModificatorChance;
			this.secondModificatorChance = secondModificatorChance;
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
		
		mods = new HashMap<Integer, Class<? extends Modificator>>();
		mods.put(0, EnemyBleedMod.class);
		mods.put(1, StunEnemyMod.class);
		mods.put(2, ExtraDamageToEnemyTypeMod.class);
		mods.put(3, VampireMod.class);
		mods.put(4, ExtraDamageMod.class);
		mods.put(5, ShootThoughCollisionMod.class);
		mods.put(6, DamagePlayerMod.class);
		mods.put(7, HealEnemyMod.class);
		mods.put(8, SpeedUpEnemyMod.class);
		mods.put(9, StunPlayerMod.class);
	}
	
	public Weapon generateWep(int playersScore) {
		int probability = random.nextInt(100);
//		if (probability > 70){
			return generateSpecialWep(playersScore);
//		} else {
//			return getPlainWep(playersScore);
//		}
	}
	
	public Weapon generateSpecialWep(int playerScore){
		setWepParams(Player.absoluteScore);
//		Weapon weapon = getPlainWep(playerScore);
		Weapon weapon = new CrossBow();
		int rNumberFirst = random.nextInt(1000);
//		System.out.println("rNumberFirst " + rNumberFirst);
//		System.out.println("weaponTypes.firstModificatorChance " + weaponTypes.firstModificatorChance);
		
		if(rNumberFirst<weaponTypes.firstModificatorChance){
			int modNumber = random.nextInt(mods.size());
			Modificator mod1 = null;
			try {
				mod1 = mods.get(modNumber).getConstructor().newInstance();
//				mod1 = new ShootThoughColli/sionMod();
			} catch (Exception e) {
				e.printStackTrace();
			} 
//			System.out.println("wep " + weapon);
//			System.out.println("wepmod1 " + weapon.mod1);
//			System.out.println("mod1 " + mod1);
			if(weapon.mod1 == null){
				weapon.mod1 = mod1;
			}
		}
		
		int rNumberSecond = random.nextInt(1000);
		
		if(rNumberSecond<weaponTypes.firstModificatorChance){
			int modNumber = random.nextInt(mods.size());
			Modificator mod2 = null;
			try {
				mod2 = mods.get(modNumber).getConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			if(mod2 != null ){
				if(weapon.mod1 == null || !mod2.getClass().equals(weapon.mod1.getClass())){
					weapon.mod2 = mod2;
//					if(weapon.mod1 == null){
//						weapon.mod1 = mod2;
//					}
				}
			}
		}
		return weapon;
	}
	
	
	
	private void setWepParams(int playersScore) {
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
