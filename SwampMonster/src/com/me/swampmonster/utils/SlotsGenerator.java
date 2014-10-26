package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.Arrows3;
import com.me.swampmonster.models.slots.DamageTrap;
import com.me.swampmonster.models.slots.ExplosiveArrow;
import com.me.swampmonster.models.slots.FrostTrap;
import com.me.swampmonster.models.slots.ImproveArrowDamage;
import com.me.swampmonster.models.slots.ImproveArrowSpeed;
import com.me.swampmonster.models.slots.ImproveMaxHealth;
import com.me.swampmonster.models.slots.ImproveMaxOxygen;
import com.me.swampmonster.models.slots.ImproveMovementSpeed;
import com.me.swampmonster.models.slots.PanicTeleport;
import com.me.swampmonster.models.slots.PlasmaShieldSkill;
import com.me.swampmonster.models.slots.PoisonArrow;
import com.me.swampmonster.models.slots.PoisonTrap;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.models.slots.SpeedBoost;
import com.me.swampmonster.models.slots.TurretSkill;

public class SlotsGenerator {

	private static SlotsGenerator effectsGenerator;
	private Random random;
	public static Map<Integer, Class<? extends Slot>> slots;
	SlotParams slotParams;
	
	static{
		slots = new HashMap<Integer, Class<? extends Slot>>();
		//:TODO Dmitriy, order is essential in this biatch, it determines what the player will receive.
		slots.put(0, Arrows3.class);
		slots.put(1, DamageTrap.class);
		slots.put(2, SpeedBoost.class);
		slots.put(3, PlasmaShieldSkill.class);
		slots.put(4, PanicTeleport.class);
		slots.put(5, ExplosiveArrow.class);
		slots.put(6, PoisonArrow.class);
		slots.put(7, FrostTrap.class);
		slots.put(8, TurretSkill.class);
		slots.put(9, PoisonTrap.class);
		slots.put(10, ImproveMovementSpeed.class);
		slots.put(11, ImproveArrowSpeed.class);
		slots.put(12, ImproveMaxOxygen.class);
		slots.put(13, ImproveArrowDamage.class);
		slots.put(14, ImproveMaxHealth.class);
	}
	
	private static enum SlotParams{
		p0_200(0, 3, 10, 12),
		p200_500(0, 4, 10, 12),
		p500_750(0, 4, 10, 13),
		p750_1000(0, 5, 10, 13),
		p1000_1200(0, 6, 10, 13),
		p1200_1350(0, 6, 10, 14),
		p1350_1500(0, 7, 10, 14),
		p1500_1750(0, 8, 10, 14),
		p1750_4200(0, 9, 10, 14),
		p4200_(0, 10, 10, 14);
		
		public final int minRandActiveSkillValue;
		public final int maxRandActiveSkillValue;
		public final int minRandPerkValue;
		public final int maxRandPerkValue;
		
		private SlotParams(int minRandActiveSkillValue, int maxRandActiveSkillValue, int minRandPerkValue,
				int maxRandPerkValue) {
			this.minRandActiveSkillValue = minRandActiveSkillValue;
			this.maxRandActiveSkillValue = maxRandActiveSkillValue;
			this.minRandPerkValue = minRandPerkValue;
			this.maxRandPerkValue = maxRandPerkValue;
		}
	}
	
	private SlotsGenerator() {
		random = new Random();
		
	}

	public static SlotsGenerator getSlotGenerator() {
		if (effectsGenerator == null) {
			effectsGenerator = new SlotsGenerator();
		}
		return effectsGenerator;
	}

	public Slot getActiveSkillSlot(Player player) {
		setDemParams(Player.absoluteScore);
		int activeSkillCode = random.nextInt(slotParams.maxRandActiveSkillValue - slotParams.minRandActiveSkillValue) + slotParams.minRandActiveSkillValue;
		Slot slot = null;
		try {
//			System.out.println("active skill : " + activeSkillCode);
			slot = slots.get(activeSkillCode).getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return slot;
	}
	
	public Slot getPerkSlot(Player player) {
		setDemParams(Player.absoluteScore);
		int perkCode = random.nextInt(slotParams.maxRandPerkValue - slotParams.minRandPerkValue) + slotParams.minRandPerkValue ;
		Slot slot = null;
		try {
			slot = slots.get(perkCode).getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return slot;
	}
	
	private void setDemParams(int playersScore) {
		if(playersScore>=0 && playersScore<200){
			slotParams = SlotParams.p0_200;
		}
		else if(playersScore>=200 && playersScore<500){
			slotParams = SlotParams.p200_500;
		}
		else if(playersScore>=500 && playersScore<750){
			slotParams = SlotParams.p500_750;
		}
		else if(playersScore>=750 && playersScore<1000){
			slotParams = SlotParams.p750_1000;
		}
		else if(playersScore>=1000 && playersScore<1200){
			slotParams = SlotParams.p1000_1200;
		}
		else if(playersScore>=1200 && playersScore<1350){
			slotParams = SlotParams.p1200_1350;
		}
		else if(playersScore>=1350 && playersScore<1500){
			slotParams = SlotParams.p1350_1500;
		}
		else if(playersScore>=1500 && playersScore<1750){
			slotParams = SlotParams.p1500_1750;
		}
		else if(playersScore>=1750 && playersScore<4200){
			slotParams = SlotParams.p1750_4200;
		}
		else if(playersScore>=4200){
			slotParams = SlotParams.p4200_;
		}
	}
}


