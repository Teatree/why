package com.me.swampmonster.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.screens.SlotMachineScreen;

public class SaveManager {
	public static void writeFile(String fileName, String s) {
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s), false);
    }
 
    public static String readFile(String fileName) {
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            String s = file.readString();
            if (!s.isEmpty()) {
            	System.err.println("puff: " + com.badlogic.gdx.utils.Base64Coder.decodeString(s));
                return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
            }
        }
        return "";
    }
    
    public static void savePlayer(){
    	Json json = new Json();
    	JsomPlayer somPlayer = new SaveManager.JsomPlayer();
    	somPlayer.maxOxygen = Player.maxOxygen;
    	somPlayer.playerMaxHealth = Player.maxHealth;
    	somPlayer.score = Player.absoluteScore;
    	somPlayer.arrowMovementSpeed = Player.arrowMovementSpeed;
    	if (L1.player != null){
	    	somPlayer.movementSpeed = L1.player.movementSpeed;
	    	somPlayer.damage = Player.damage;
    	}
    	somPlayer.lastMap = LGenerator.lastMap;
    	somPlayer.hadLastAtmosphere = LGenerator.hadLastAtmosphere;
    	somPlayer.lastTileSet = LGenerator.lastTileSet;
    	somPlayer.wasLastElite = LGenerator.wasLastElite;
    	List<JsomSlot> jsavedSlots = new ArrayList<SaveManager.JsomSlot>();
    	if (SlotMachineScreen.savedSlots != null && !SlotMachineScreen.savedSlots.isEmpty()){
	    	for (Slot slotik : SlotMachineScreen.savedSlots){
	    		try {
	    			JsomSlot somSlot = new JsomSlot();
	    			somSlot.className = slotik.getClass().toString().replace("class ", "");
					somSlot.level = slotik.getClass().getField("level").getInt(null);
					jsavedSlots.add(somSlot);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
		}
    	somPlayer.savedSlots = jsavedSlots;
    	if (ItemGenerator.usedTextures !=null){
    		somPlayer.usedSpritesForItems = ItemGenerator.usedTextures;
    	}
    	writeFile("player.sav", json.toJson(somPlayer));
    }
    
    public static Player loadPlayer(){
    	String save = readFile("player.sav");
        if (!save.isEmpty()) {
	    	Json json = new Json();
	        JsomPlayer somPlayer = json.fromJson(JsomPlayer.class, save);
	        Player player = new Player(new Vector2());
	        Player.maxOxygen = somPlayer.maxOxygen;
	        Player.maxHealth = somPlayer.playerMaxHealth;
	        Player.maxHealth = somPlayer.playerMaxHealth;
	        Player.absoluteScore = somPlayer.score;
	        Player.arrowMovementSpeed = somPlayer.arrowMovementSpeed;
	        player.oxygen = Player.maxOxygen;
	        player.health = Player.maxHealth;
	        Player.damage = somPlayer.damage;
	        player.movementSpeed = somPlayer.movementSpeed;
	        LGenerator.hadLastAtmosphere = somPlayer.hadLastAtmosphere;
	        LGenerator.wasLastElite = somPlayer.wasLastElite;
	        LGenerator.lastMap = somPlayer.lastMap;
	        LGenerator.lastTileSet = somPlayer.lastTileSet;
	        SlotMachineScreen.savedSlots = new ArrayList<Slot>();
	        if (somPlayer.savedSlots != null && !somPlayer.savedSlots.isEmpty()){
		        for (JsomSlot somSlot : somPlayer.savedSlots){
		        	try {
						Slot slot = (Slot) Class.forName(somSlot.className).newInstance();
						Field hack = slot.getClass().getDeclaredField("level");
						hack.setAccessible(true);
						hack.set(null, somSlot.level);
						SlotMachineScreen.savedSlots.add(slot);
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
	        }
	        if (somPlayer.usedSpritesForItems != null){
	        	ItemGenerator.usedTextures = (HashMap<Integer, String>) somPlayer.usedSpritesForItems;
	        }
	        for (Entry <Integer, String> entry : somPlayer.usedSpritesForItems.entrySet()){
	        	try {
					Class itemClass = Class.forName(entry.getValue());
					Field hack = itemClass.getDeclaredField("poisonSprite");
					hack.setAccessible(true);
					hack.set(null, ItemGenerator.poisonTextures.get(entry.getKey()));
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        return player;
        } else {
        	return new Player(new Vector2());
        }
    }
    
    public static class JsomPlayer {
    	public float maxOxygen;
    	public int playerMaxHealth;
    	public int score;
    	public float arrowMovementSpeed;
    	public String saved;
    	public float movementSpeed;
    	public float damage;
    	
    	public String lastTileSet;
    	public String lastMap;
    	public boolean wasLastElite;
    	public boolean hadLastAtmosphere;
    	public List <JsomSlot> savedSlots;
    	
    	public Map<Integer, String> usedSpritesForItems;
    }

    public static class JsomSlot {
    	public String className;
    	public int level;
    }
}
