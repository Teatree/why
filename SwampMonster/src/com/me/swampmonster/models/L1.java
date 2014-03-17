package com.me.swampmonster.models;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.utils.CameraHelper;


public class L1{
	
	Player player;
	List <Enemy> enemies;
	Bunker bunker;
	
	public L1(){
		create();
	}
	public void create(){
		player = new Player(new Vector2());
		enemies = new ArrayList<Enemy>();
		enemies.add(new Enemy(new Vector2()));
		enemies.add(new Enemy(new Vector2()));
		bunker = new Bunker();
	}
	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point, TiledMapTileLayer collisionLayer, AbstractGameObject projectile, CameraHelper cameraHelper) {
		this.player.update(aiming, touchPos, V3point, collisionLayer);
		for (Enemy enemy : enemies){
			enemy.update(collisionLayer, projectile, this.player, cameraHelper);
		}
		bunker.update();
	}
	
	public void drawPlayer(SpriteBatch b){
		b.begin();
		b.draw(getPlayer().getSprite(), getPlayer().getPosition().x, getPlayer().getPosition().y, getPlayer().getSprite().getWidth(), getPlayer().getSprite().getHeight());
		b.end();
	}
	
	public void drawEnemy(SpriteBatch b){
		for (Enemy enemy : enemies){
			b.begin();
			b.draw(enemy.getSprite(), enemy.getPosition().x, enemy.getPosition().y, enemy.getSprite().getWidth(), enemy.getSprite().getHeight());
			b.end();
		}
	}
	
	public Bunker getBunker() {
		return bunker;
	}
	public void setBunker(Bunker bunker) {
		this.bunker = bunker;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public List<Enemy> getEnemies() {
		return enemies;
	}
	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	
}
