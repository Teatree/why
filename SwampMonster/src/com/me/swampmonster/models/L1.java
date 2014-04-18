package com.me.swampmonster.models;

import java.util.Stack;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.utils.CameraHelper;
import com.me.swampmonster.utils.MisterSpawner;


public class L1{
	
	Player player;
	public Stack<Enemy> enemiesOnStage;
	Wave wave;
	public Projectile projectile;
	Bunker bunker;
	
	public L1(){
		create();
	}
	
	public void create(){
		MisterSpawner misterSpawner = new MisterSpawner();
		player = new Player(new Vector2());
		projectile = new Projectile(new Vector2(), 34f);
		wave = misterSpawner.generateWave(player.getPoints());
		enemiesOnStage = new Stack<Enemy>();
		bunker = new Bunker();
		
		while (enemiesOnStage.size() < wave.enemiesOnBattleField && !wave.enemies.empty()){
			enemiesOnStage.add(((Stack<Enemy>) wave.enemies).pop());
		}
	}
	
	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point, TiledMapTileLayer collisionLayer, 
			AbstractGameObject projectile, CameraHelper cameraHelper, float dx, float dy) {
		this.player.update(aiming, touchPos, V3point, collisionLayer, dx, dy);
		if (enemiesOnStage.size() < wave.enemiesOnBattleField && !wave.enemies.empty()){
			addEnemyOnBattlefield();
		}
		for (Enemy enemy : enemiesOnStage){
			enemy.update(collisionLayer, projectile, this.player, cameraHelper, enemiesOnStage);
		}
		
		bunker.update();
	}
	
//	public void drawPlayer(SpriteBatch b){
//		b.begin();
//		b.draw(getPlayer().getSprite(), getPlayer().getPosition().x, getPlayer().getPosition().y, getPlayer().getSprite().getWidth(), getPlayer().getSprite().getHeight());
//		b.end();
//	}
	
//	public void drawEnemy(SpriteBatch b){
//		for (Enemy enemy : enemies){
//			b.begin();
//			b.draw(enemy.getSprite(), enemy.getPosition().x, enemy.getPosition().y, enemy.getSprite().getWidth(), enemy.getSprite().getHeight());
//			b.end();
//		}
//	}
	
	private void addEnemyOnBattlefield(){
		if (!wave.enemies.empty()){
			Enemy e = wave.enemies.pop();
			e.setPosition(new Vector2 (110f,450f));
			e.getSprite().setSize(e.getSprite().getWidth()/2, e.getSprite().getHeight()/2);
			enemiesOnStage.add(e);
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
}
