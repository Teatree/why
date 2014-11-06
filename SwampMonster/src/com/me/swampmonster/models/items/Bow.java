package com.me.swampmonster.models.items;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.me.swampmonster.utils.Assets;

public class Bow extends Weapon{
	
	public static AssetDescriptor<Texture> poisonSprite;
	
	public Bow(){
		super();
		
		sprite = new Sprite(Assets.manager.get(Assets.wepBOW));
		
		minDD = 1;
		maxDD = 3;
	}

	public void update(TiledMapTileLayer collisionLayer) {
		super.update(collisionLayer);
	}
	
	
}
