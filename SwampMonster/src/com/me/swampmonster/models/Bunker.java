package com.me.swampmonster.models;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

public class Bunker extends AbstractGameObject{
	
	TiledMap map;
	TiledMapTileLayer tLayer;
	public AnimatedTiledMapTile animatedTile;
	
	public Bunker(String tileSet, String tileMap){
		TmxMapLoader mapResolver = new TmxMapLoader(new FileHandleResolver() {
			
			@Override
			public FileHandle resolve(String fileName) {
				return Gdx.files.local(fileName);
			}
		});
		map = mapResolver.load(tileMap);
		Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>(14);
		Iterator<TiledMapTile> tiles = map.getTileSets().getTileSet(tileSet).iterator();
		while(tiles.hasNext()){
			TiledMapTile tile = tiles.next();
			if(tile.getProperties().containsKey("animated") && tile.getProperties().get("animated", String.class).equals("console"))
					frameTiles.add((StaticTiledMapTile) tile);
		}
		
		animatedTile = new AnimatedTiledMapTile(1, frameTiles);
		
		tLayer = (TiledMapTileLayer) map.getLayers().get("background");
		
//		for(int x = 0; x < tLayer.getWidth(); x++){
//			for(int y = 0; y < tLayer.getHeight(); y++){
//				Cell cell = tLayer.getCell(x, y);
//				if(cell.getTile().getProperties().containsKey("animated") && cell.getTile().getProperties().get("animated", String.class).equals("console")){
//					cell.setTile(animatedTile);
//				}
//			}
//		}
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
