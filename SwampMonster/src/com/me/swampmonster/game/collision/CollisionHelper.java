package com.me.swampmonster.game.collision;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.me.swampmonster.models.Enemy;

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
	
	public static Collidable isCollidableEnemy(Enemy enemyKing, List<Enemy> enemies){
		enemyKing.setSwitzerland(true);
		if(enemies!=null && enemyKing!=null){
			for(Enemy enemy : enemies){
				if(enemy != enemyKing && enemyKing.getRectanlge().overlaps(enemy.getRectanlge())){
					if (enemy.isSwitzerland()){
						enemyKing.setSwitzerland(false);
					}
					return enemy;
				}
			}
		}
		return null;
	}
}
