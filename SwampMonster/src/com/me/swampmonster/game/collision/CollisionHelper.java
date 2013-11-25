package com.me.swampmonster.game.collision;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class CollisionHelper {
	
	public static String isCellBlocked(float x, float y, TiledMapTileLayer collisionLayer){
		Cell cell = collisionLayer.getCell((int) (x/collisionLayer.getTileWidth()), (int) (y/collisionLayer.getTileHeight()) );
		return (String) (cell != null && cell.getTile() != null ? cell.getTile().getProperties().get("blocked") : null);
	}

	public static Collidable isCollidable(float x, float y, TiledMapTileLayer collisionLayer){
		Solid solid = null;
		if(isCellBlocked(x, y, collisionLayer)!=null){
			solid = new Solid();
		}
		return solid;
		
	}
}
