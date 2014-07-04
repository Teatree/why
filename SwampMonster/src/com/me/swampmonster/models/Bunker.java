package com.me.swampmonster.models;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

public class Bunker extends AbstractGameObject{
	
	TiledMapTileLayer tLayer;
	
	public Bunker(){
		map = new TmxMapLoader().load("data/Map.tmx");
		
		Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>(2);
		
		Iterator<TiledMapTile> tiles = map.getTileSets().getTileSet("MarsDesertTileset").iterator();
		while(tiles.hasNext()){
			TiledMapTile tile = tiles.next();
			if(tile.getProperties().containsKey("animation") && tile.getProperties().get("animation", String.class).equals("console"))
					frameTiles.add((StaticTiledMapTile) tile);
		}
		
//		AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(1, frameTiles);
		
		tLayer = (TiledMapTileLayer) map.getLayers().get("background");
		
		for(int x = 0; x < tLayer.getWidth(); x++){
//			for(int y = 0; y < tLayer.getHeight(); y++){
//				Cell cell = tLayer.getCell(x, y);
//				if(cell.getTile().getProperties().containsKey("animation") && cell.getTile().getProperties().get("animation", String.class).equals("console")){
//					cell.setTile(animatedTile);
//				}
//			}
		}
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}
	
	public TiledMapTileLayer gettLayer() {
		return tLayer;
	}

	public void settLayer(TiledMapTileLayer tLayer) {
		this.tLayer = tLayer;
	}

	public void update(){
		AnimatedTiledMapTile.updateAnimationBaseTime();
	}
	
	public void render(SpriteBatch batch) {
		
	}
}
