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
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.PlasmaShield;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.items.Bow;
import com.me.swampmonster.models.items.Weapon;
import com.me.swampmonster.models.items.wepMods.Modificator;
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
import com.me.swampmonster.models.slots.PoisonArrow;
import com.me.swampmonster.models.slots.PoisonTrap;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.models.slots.SpeedBoost;
import com.me.swampmonster.models.slots.TurretSkill;
import com.me.swampmonster.screens.MenuScreen;
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
//            	System.err.println("puff: " + com.badlogic.gdx.utils.Base64Coder.decodeString(s));
                return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
            }
        }
        return "";
    }
    
    public static void savePlayer(){
    	Json json = new Json();
    	JsomPlayer somPlayer = new SaveManager.JsomPlayer();
    	
    	somPlayer.soundEnabled = MenuScreen.soundsEnabled;
    	somPlayer.maxOxygen = Player.maxOxygen;
    	somPlayer.playerMaxHealth = Player.maxHealth;
    	somPlayer.score = Player.absoluteScore;
    	somPlayer.arrowMovementSpeed = Player.arrowMovementSpeed;
    	if (L1.player != null){
	    	somPlayer.movementSpeed = L1.player.movementSpeed;
	    	somPlayer.weaponClassName = L1.player.weapon.getClass().toString().replace("class ", "");
	    	if (L1.player.weapon.mod1 != null){
	    		somPlayer.weaponMod1ClassName = L1.player.weapon.mod1.getClass().toString().replace("class ", "");
	    	}
	     	if (L1.player.weapon.mod2 != null){
	    		somPlayer.weaponMod2ClassName = L1.player.weapon.mod2.getClass().toString().replace("class ", "");
	    	}
	    	somPlayer.minDD = L1.player.weapon.minDD;
	    	somPlayer.maxDD = L1.player.weapon.maxDD;
    	}
    	if(TheController.skill != null){
    		somPlayer.skill = TheController.skill.getClass().getName();
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
    	
    	saveSlotsLevels(somPlayer);
    	
    	writeFile("player.sav", json.toJson(somPlayer));
    }
    
    public static Player loadPlayer(){
    	String save = readFile("player.sav");
        if (!save.isEmpty()) {
	    	Json json = new Json();
	        JsomPlayer somPlayer = json.fromJson(JsomPlayer.class, save);
	        MenuScreen.soundsEnabled = somPlayer.soundEnabled;
	        System.out.println("newPlayer2");
	        Player player = new Player(new Vector2());
	        Player.maxOxygen = somPlayer.maxOxygen;
	        Player.maxHealth = somPlayer.playerMaxHealth;
	        Player.maxHealth = somPlayer.playerMaxHealth;
	        Player.absoluteScore = somPlayer.score;
	        Player.arrowMovementSpeed = somPlayer.arrowMovementSpeed;
	        player.oxygen = Player.maxOxygen;
	        player.health = Player.maxHealth;
	        try {
	        	if (somPlayer.weaponClassName != null){
	        		player.weapon = (Weapon) Class.forName(somPlayer.weaponClassName).newInstance();
	        	} else {
//	        		player.weapon = new Bow();
	        	}
				if (somPlayer.weaponMod1ClassName != null)
				player.weapon.mod1 = (Modificator) Class.forName(somPlayer.weaponMod1ClassName).newInstance();
				if (somPlayer.weaponMod2ClassName != null)
				player.weapon.mod2 = (Modificator) Class.forName(somPlayer.weaponMod2ClassName).newInstance();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	        player.weapon.minDD = (int) somPlayer.minDD;
	        player.weapon.maxDD = (int) somPlayer.maxDD;
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
	        if (somPlayer.skill != null){
	        	try {
					Slot slot = (Slot) Class.forName(somPlayer.skill).newInstance();
					TheController.skill = slot;
				} catch (Exception e) {
				}
	        }
	        return player;
        } else {
        	System.out.println("newPlayer1");
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
    	public float minDD;
    	public float maxDD;
    	public String weaponClassName;
    	public String weaponMod1ClassName;
		public String weaponMod2ClassName;
    	public String skill;
    	
    	public String lastTileSet;
    	public String lastMap;
    	public boolean wasLastElite;
    	public boolean hadLastAtmosphere;
    	public List <JsomSlot> savedSlots;
    	
    	public Map<Integer, String> usedSpritesForItems;
    	public Map<Class, Integer> slotsLevels;
    	
    	public boolean soundEnabled;
    }

    public static class JsomSlot {
    	public String className;
    	public int level;
    }
    
	private static void saveSlotsLevels(JsomPlayer somPlayer) {
		somPlayer.slotsLevels = new HashMap<Class, Integer>();
    	somPlayer.slotsLevels.put(Arrows3.class, Arrows3.level);
    	somPlayer.slotsLevels.put(DamageTrap.class, DamageTrap.level);
    	somPlayer.slotsLevels.put(ExplosiveArrow.class, ExplosiveArrow.level);
    	somPlayer.slotsLevels.put(FrostTrap.class, FrostTrap.level);
    	somPlayer.slotsLevels.put(ImproveArrowDamage.class, ImproveArrowDamage.level);
    	somPlayer.slotsLevels.put(ImproveArrowSpeed.class, ImproveArrowSpeed.level);
    	somPlayer.slotsLevels.put(ImproveMaxHealth.class, ImproveMaxHealth.level);
    	somPlayer.slotsLevels.put(ImproveMaxOxygen.class, ImproveMaxOxygen.level);
    	somPlayer.slotsLevels.put(ImproveMovementSpeed.class, ImproveMovementSpeed.level);
    	somPlayer.slotsLevels.put(PanicTeleport.class, PanicTeleport.level);
    	somPlayer.slotsLevels.put(PlasmaShield.class, PlasmaShield.level);
    	somPlayer.slotsLevels.put(PoisonArrow.class, PoisonArrow.level);
    	somPlayer.slotsLevels.put(PoisonTrap.class, PoisonTrap.level);
    	somPlayer.slotsLevels.put(SpeedBoost.class, SpeedBoost.level);
    	somPlayer.slotsLevels.put(TurretSkill.class, TurretSkill.level);
	}
	
	
}
