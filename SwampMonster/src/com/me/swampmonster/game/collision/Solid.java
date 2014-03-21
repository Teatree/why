package com.me.swampmonster.game.collision;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject;

public class Solid implements Collidable{

	public void doCollide(AbstractGameObject abstractGameObject, TiledMapTileLayer collisionLayer) {
		boolean collisionX = false;
		boolean collisionY = false; 
        //collision detection
        if(abstractGameObject.getPlayerMovementDirectionLR() == "left"){
        	//left side
        	collisionX = collidesLeft(collisionLayer, abstractGameObject);
	    }
	    else if(abstractGameObject.getPlayerMovementDirectionLR() == "right"){
	    	//right side
	    	collisionX = collidesRight(collisionLayer, abstractGameObject);
	    }
        //collision result
	    if(collisionX){
	    	abstractGameObject.setPosition(new Vector2(abstractGameObject.getOldPos().x, abstractGameObject.getPosition().y));
	    }
	      //collision detection
		    if(abstractGameObject.getPlayerMovementDirectionUD() == "up"){
		    	//top side
		    	collisionY = collidesTop(collisionLayer, abstractGameObject);
		    }else if(abstractGameObject.getPlayerMovementDirectionUD() == "down"){
		    	//bottom side
		    	collisionY = coolidesBottom(collisionLayer, abstractGameObject);
		    }
		    //collision result
		    if(collisionY){
		    	abstractGameObject.setPosition(new Vector2(abstractGameObject.getPosition().x, abstractGameObject.getOldPos().y));
		    }
	}
		public boolean collidesRight( TiledMapTileLayer collisionLayer, AbstractGameObject mob){
			String collides = null;
			for(float step = 0f; step < mob.getSprite().getHeight(); step += collisionLayer.getTileHeight()/2){
				collides = CollisionHelper.isCellBlocked((mob.getPosition().x + mob.getSprite().getWidth()), mob.getPosition().y + step, collisionLayer);
				if(collides!=null && collides.equals("blocked")){
					return true;
				}
			}
			return false;
		}
		public boolean collidesLeft( TiledMapTileLayer collisionLayer, AbstractGameObject mob){
			String collides = null;
			for(float step = 0; step < mob.getSprite().getHeight(); step+=collisionLayer.getTileHeight() /2){
				collides = CollisionHelper.isCellBlocked(mob.getPosition().x, mob.getPosition().y + step, collisionLayer);
				if(collides!=null && collides.equals("blocked"))
					return true;
			}
			return false;
		}
		public boolean collidesTop( TiledMapTileLayer collisionLayer, AbstractGameObject mob){
			String collides = null;
			for(float step = 0; step < mob.getSprite().getWidth(); step+=collisionLayer.getTileWidth() /2){
				collides = CollisionHelper.isCellBlocked(mob.getPosition().x + step, mob.getPosition().y + mob.getSprite().getHeight(), collisionLayer);
				if(collides!=null && collides.equals("blocked")){
					return true;
				}
			}
			return false;
		}
		public boolean coolidesBottom( TiledMapTileLayer collisionLayer, AbstractGameObject mob){
			String collides = null;
			for(float step = 0; step < mob.getSprite().getWidth(); step+=collisionLayer.getTileWidth() /2){
				collides = CollisionHelper.isCellBlocked(mob.getPosition().x + step, mob.getPosition().y, collisionLayer);
				if(collides!=null && collides.equals("blocked"))
					return true;
			}
			return false;
		}
}
