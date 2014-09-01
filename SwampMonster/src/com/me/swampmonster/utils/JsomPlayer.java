package com.me.swampmonster.utils;

import java.util.List;

import com.me.swampmonster.models.slots.Slot;

public class JsomPlayer {
	public float maxOxygen;
	public int playerMaxHealth;
	public int score;
	public float arrowMovementSpeed;
	public String saved;
	public float movementSpeed;
	public float damage;
	public List<Slot> savedSlots;
	
	public String lastTileSet;
	public String lastMap;
	public boolean wasLastElite;
	public boolean hadLastAtmosphere;
}
