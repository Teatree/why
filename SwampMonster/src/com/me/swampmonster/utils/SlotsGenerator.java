package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.Arrows3;
import com.me.swampmonster.models.slots.DamageTrap;
import com.me.swampmonster.models.slots.ExplosiveArrow;
import com.me.swampmonster.models.slots.ExplozionTrap;
import com.me.swampmonster.models.slots.FADE;
import com.me.swampmonster.models.slots.FrostTrap;
import com.me.swampmonster.models.slots.ImproveArrowDamage;
import com.me.swampmonster.models.slots.ImproveArrowSpeed;
import com.me.swampmonster.models.slots.ImproveMaxHealth;
import com.me.swampmonster.models.slots.ImproveMaxOxygen;
import com.me.swampmonster.models.slots.ImproveMovementSpeed;
import com.me.swampmonster.models.slots.PoisonArrow;
import com.me.swampmonster.models.slots.PoisonTrap;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.models.slots.RADIOACTIVE;
import com.me.swampmonster.models.slots.SPEED_BOOST;
import com.me.swampmonster.models.slots.ShadowArrow;
import com.me.swampmonster.models.slots.Slot;

public class SlotsGenerator {

	private static SlotsGenerator effectsGenerator;
	private Random random;
	Map<Integer, Class<? extends Slot>> slots;

	private SlotsGenerator() {
		random = new Random();
		slots = new HashMap<Integer, Class<? extends Slot>>();
		slots.put(0, SPEED_BOOST.class);
		slots.put(1, FADE.class);
		slots.put(2, RADIOACTIVE.class);
		slots.put(3, PoisonTrap.class);
		slots.put(4, DamageTrap.class);
		slots.put(5, FrostTrap.class);
		slots.put(6, ExplozionTrap.class);
		slots.put(7, Arrows3.class);
		slots.put(8, ShadowArrow.class);
		slots.put(9, PoisonArrow.class);
		slots.put(10, ExplosiveArrow.class);
		slots.put(11, ImproveArrowDamage.class);
		slots.put(12, ImproveArrowSpeed.class);
		slots.put(13, ImproveMaxHealth.class);
		slots.put(14, ImproveMaxOxygen.class);
		slots.put(15, ImproveMovementSpeed.class);
	}

	public static SlotsGenerator getSlotGenerator() {
		if (effectsGenerator == null) {
			effectsGenerator = new SlotsGenerator();
		}
		return effectsGenerator;
	}

	public Slot getSlot(int slotMin, int slotMax) {
		int slotCode = random.nextInt(slotMax - slotMin) + slotMin;
		Slot slot = null;
		try {
			slot = slots.get(slotCode).getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return slot;
	}	
}


