package com.me.swampmonster.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;

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
                return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
            }
        }
        return "";
    }
    
    public static void savePlayer(){
    	Json json = new Json();
    	JsomPlayer somPlayer = new JsomPlayer();
    	somPlayer.maxOxygen = Player.maxOxygen;
    	somPlayer.playerMaxHealth = Player.maxHealth;
    	somPlayer.score = Player.absoluteScore;
    	somPlayer.arrowMovementSpeed = Player.arrowMovementSpeed;
    	somPlayer.movementSpeed = L1.player.movementSpeed;
    	somPlayer.damage = L1.player.damage;
    	somPlayer.lastMap = LGenerator.lastMap;
    	somPlayer.hadLastAtmosphere = LGenerator.hadLastAtmosphere;
    	somPlayer.lastTileSet = LGenerator.lastTileSet;
    	somPlayer.wasLastElite = LGenerator.wasLastElite;
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
	        player.damage = somPlayer.damage;
	        player.movementSpeed = somPlayer.movementSpeed;
	        LGenerator.hadLastAtmosphere = somPlayer.hadLastAtmosphere;
	        LGenerator.wasLastElite = somPlayer.wasLastElite;
	        LGenerator.lastMap = somPlayer.lastMap;
	        LGenerator.lastTileSet = somPlayer.lastTileSet;
	        return player;
        } else {
        	return new Player(new Vector2());
        }
    }
    
    public static void saveLevel(){
    	Json json = new Json();
    	writeFile("world.sav", json.toJson(TheController.level1));
    }
    
    public static L1 loadLevel(){
    	String save = readFile("world.sav");
        if (!save.isEmpty()) {
	    	Json json = new Json();
	        L1 world = json.fromJson(L1.class, save);
	        return world;
        } else {
        	return null;
        }
    }
}
