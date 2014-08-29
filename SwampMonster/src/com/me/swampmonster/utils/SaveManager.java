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
        System.out.println(" ! " + s);
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
    	writeFile("player.sav", json.toJson(L1.player));
    }
    
    public static Player loadPlayer(){
    	String save = readFile("player.sav");
        if (!save.isEmpty()) {
	    	Json json = new Json();
	        Player player = json.fromJson(Player.class, save);
	        return player;
        } else {
        	return new Player(new Vector2());
        }
    }
    
    public static void saveLevel(){
    	Json json = new Json();
    	System.out.println("!" + json.toJson(TheController.level1));
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
