package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.FADE;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.models.slots.RADIOACTIVE;
import com.me.swampmonster.models.slots.SPEED_BOOST;
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


