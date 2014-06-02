package com.me.swampmonster.models.slots;
public enum PositiveEffects {
		FADE(900), SPEED_BOOST(212), RADIOACTIVE_AURA(1890), NONE(0);
		
		public int lifetime;
		
		PositiveEffects(int lifeTime){
			this.lifetime = lifeTime;
		}
	}
