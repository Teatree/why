package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.utils.CameraHelper;


public class L1{
	
	Player player;
	Enemy enemy;
	Bunker bunker;
	
	public L1(){
		create();
	}
	public void create(){
		player = new Player(new Vector2());
		enemy = new Enemy(new Vector2());
		bunker = new Bunker();
	}
	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point, TiledMapTileLayer collisionLayer, AbstractGameObject projectile, CameraHelper cameraHelper) {
		this.player.update(aiming, this.enemy, touchPos, V3point, collisionLayer);
		this.enemy.update(collisionLayer, projectile, this.player, cameraHelper);
		bunker.update();
	}
	
	public void drawPlayer(SpriteBatch b){
		b.begin();
		b.draw(getPlayer().getSprite(), getPlayer().getPosition().x, getPlayer().getPosition().y, getPlayer().getSprite().getWidth(), getPlayer().getSprite().getHeight());
		b.end();
	}
	public void drawEnemy(SpriteBatch b){
		b.begin();
		b.draw(getEnemy().getSprite(), getEnemy().getPosition().x, getEnemy().getPosition().y, getEnemy().getSprite().getWidth(), getEnemy().getSprite().getHeight());
		b.end();
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
	public Enemy getEnemy() {
		return enemy;
	}
	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}
	
}
