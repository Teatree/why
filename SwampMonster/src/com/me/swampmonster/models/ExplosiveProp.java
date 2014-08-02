package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.utils.Assets;

public class ExplosiveProp extends Prop{
	public Explosion explosion;
	
	public ExplosiveProp(Vector2 position){
		this.position = position;
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_TRAP_ICON));
	}
	


}
