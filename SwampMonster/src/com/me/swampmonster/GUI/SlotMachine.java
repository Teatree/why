package com.me.swampmonster.GUI;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.Toughness;
import com.me.swampmonster.pickable.Perks;

public class SlotMachine extends AbstractGameObject{
	
	boolean slotMachine;
	
	private Random random;
	private Map <Integer, Perks> perkParams;
	
	public SlotMachine(){
		slotMachine = false;
		sprite = new Sprite(new Texture("data/slotMachineCase.png"));
		sprite.setX(275);
		sprite.setY(175);
		sprite.setScale(2, 2);
		
		perkParams = new HashMap<Integer, Perks>();
		perkParams.put(0, null);
		perkParams.put(1, Perks.RELOAD_SPEED);
		perkParams.put(2, Perks.OXYGEN_COPACITY);
		perkParams.put(3, Perks.MOVEMENT_SPEED);
		perkParams.put(4, Perks.HEALTH_COPACITY);
		perkParams.put(4, Perks.MELEE_RADIUS);
		perkParams.put(6, Perks.MELEE_RELOAD_SPEED);
		perkParams.put(7, Perks.ARROW_DAMAGE);
		perkParams.put(8, Perks.HUNRETPOINTS);
		perkParams.put(9, Perks.FIVEHUNRETPOINTS);
		perkParams.put(10, Perks.NOTHING);
		perkParams.put(11, Perks.ARROW_SPEED);
		random = new Random();
		
	}
	
	public void update(){
		
	}

	public boolean isSlotMachine() {
		return slotMachine;
	}

	public void setSlotMachine(boolean slotMachine) {
		this.slotMachine = slotMachine;
	}
	
}
