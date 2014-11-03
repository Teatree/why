package com.me.swampmonster.models.items;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Bow extends Weapon{
	
	public static AssetDescriptor<Texture> poisonSprite;
	
	public Bow(){
		super();
		
		minDD = 1;
		maxDD = 3;
	}

	public void update(TiledMapTileLayer collisionLayer) {
		super.update(collisionLayer);
	}
	
	
}
