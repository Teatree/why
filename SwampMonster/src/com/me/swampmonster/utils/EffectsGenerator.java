package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.me.swampmonster.models.AbstractGameObject.PositiveEffects;

public class EffectsGenerator {

	private static EffectsGenerator effectsGenerator;
	private Random random;
	Map<Integer, PositiveEffects> positiveEffects;

	private EffectsGenerator() {
		random = new Random();
		positiveEffects = new HashMap<Integer, PositiveEffects>();
		positiveEffects.put(0, PositiveEffects.SPEED_BOOST);
		positiveEffects.put(1, PositiveEffects.FADE);
		positiveEffects.put(2, PositiveEffects.RADIOACTIVE_AURA);
	}

	public static EffectsGenerator getEffectGenerator() {
		if (effectsGenerator == null) {
			effectsGenerator = new EffectsGenerator();
		}
		return effectsGenerator;
	}

	public PositiveEffects getPositiveEffect(int effectMin, int effectMax) {
		int effectCode = random.nextInt(effectMax - effectMin) + effectMin;
		System.out.println(effectCode);
		PositiveEffects effect = null;
		effect = positiveEffects.get(effectCode);
		return effect;
	}

}
