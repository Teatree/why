package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.Arrows3;
import com.me.swampmonster.models.slots.DamageTrap;
import com.me.swampmonster.models.slots.ExplosiveArrow;
import com.me.swampmonster.models.slots.ExplozionTrap;
import com.me.swampmonster.models.slots.FrostTrap;
import com.me.swampmonster.models.slots.ImproveArrowDamage;
import com.me.swampmonster.models.slots.ImproveArrowSpeed;
import com.me.swampmonster.models.slots.ImproveMaxHealth;
import com.me.swampmonster.models.slots.ImproveMaxOxygen;
import com.me.swampmonster.models.slots.ImproveMovementSpeed;
import com.me.swampmonster.models.slots.PlasmaShieldSkill;
import com.me.swampmonster.models.slots.PoisonArrow;
import com.me.swampmonster.models.slots.PoisonTrap;
import com.me.swampmonster.models.slots.SPEED_BOOST;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.models.slots.TurretSkill;

public class SlotsGenerator {

	private static SlotsGenerator effectsGenerator;
	private Random random;
	Map<Integer, Class<? extends Slot>> slots;
	SlotParams slotParams;
	
	private static enum SlotParams{
		p0_500(0, 10, 10, 14),
		p500_1000(0, 10, 10, 14),
		p1000_2000(0, 10, 10, 14),
		p2000_4000(0, 10, 10, 14);
		
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
		slots = new HashMap<Integer, Class<? extends Slot>>();
		//:TODO Dmitriy, order is essential in this biatch, it determines what the player will receive.
		slots.put(0, SPEED_BOOST.class);
		slots.put(1, PoisonTrap.class);
		slots.put(2, DamageTrap.class);
		slots.put(3, FrostTrap.class);
		slots.put(4, ExplozionTrap.class);
		slots.put(5, Arrows3.class);
		slots.put(6, PlasmaShieldSkill.class);
		slots.put(7, PoisonArrow.class);
		slots.put(8, TurretSkill.class);
		slots.put(9, ExplosiveArrow.class);
		slots.put(10, ImproveArrowDamage.class);
		slots.put(11, ImproveArrowSpeed.class);
		slots.put(12, ImproveMaxHealth.class);
		slots.put(13, ImproveMaxOxygen.class);
		slots.put(14, ImproveMovementSpeed.class);
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
		if(playersScore>=0 && playersScore<500){
			slotParams = SlotParams.p0_500;
		}
		else if(playersScore>100 && playersScore<1000){
			slotParams = SlotParams.p500_1000;
		}
		else if(playersScore>1000 && playersScore<2000){
			slotParams = SlotParams.p1000_2000;
		}
		else if(playersScore>2000){
			slotParams = SlotParams.p2000_4000;
		}
	}
}


