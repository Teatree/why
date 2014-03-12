package com.me.swampmonster.game.collision;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.me.swampmonster.models.AbstractGameObject;

public interface Collidable {
	
	public void doCollide(AbstractGameObject abstractGameObject, TiledMapTileLayer collisionLayer);
	
}
