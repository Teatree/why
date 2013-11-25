package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Bunker extends AbstractGameObject{
	
	public Bunker(){
		map = new TmxMapLoader().load("data/Map.tmx");
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}

	public void render(SpriteBatch batch) {
		
	}
}
