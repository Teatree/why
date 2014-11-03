package com.me.swampmonster.game.collision;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.me.swampmonster.models.Projectile;

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
	
	public static String isCellBreakable(float x, float y, TiledMapTileLayer collisionLayer){
		Cell cell = collisionLayer.getCell((int) (x/collisionLayer.getTileWidth()), (int) (y/collisionLayer.getTileHeight()) );
		return (String) (cell != null && cell.getTile() != null ? cell.getTile().getProperties().get("breakable") : null);
	}
	
	public static Collidable isCollidableBreakable(float x, float y, TiledMapTileLayer collisionLayer){
		Solid solid = null;
		if(isCellBreakable(x, y, collisionLayer)!=null){
			solid = new Solid();
			
		}
		return solid;
	}
	
	public static int getSurfaceLevel(float x, float y, TiledMapTileLayer collisionLayer){
		Cell cell = collisionLayer.getCell(
				(int) (x / collisionLayer.getTileWidth()),
				(int) (y / collisionLayer.getTileHeight()));
		int i = Integer.parseInt((String) cell.getTile().getProperties()
				.get("level"));
//		System.out.println("collision layer level: " + i);
		return i;
	}
	
	public static String isCellBlockedLevel(float x, float y, TiledMapTileLayer collisionLayer){
		Cell cell = collisionLayer.getCell((int) (x/collisionLayer.getTileWidth()), (int) (y/collisionLayer.getTileHeight()));
		return (String) (cell != null && cell.getTile() != null ? cell.getTile().getProperties().get("blocked") : null);
	}
	
	public static Collidable isCollidableLevel(float x, float y, TiledMapTileLayer collisionLayer, Projectile p){
		Solid solid = null;
		if(isCellBlockedLevel(x, y, collisionLayer)!=null){
			Cell cell = collisionLayer.getCell((int) (x/collisionLayer.getTileWidth()), (int) (y/collisionLayer.getTileHeight()));
			if ((String) cell.getTile().getProperties().get("level") != null) {
				int i = Integer.parseInt((String) cell.getTile()
						.getProperties().get("level"));
//				System.out.println("sufraceLevel: " + p.currentSurfaceLevel
//						+ " i " + i);
				if (p.initialSurfaceLevel <= i) {
					solid = new Solid();
				}
			}
		}
		return solid;
	}
}
