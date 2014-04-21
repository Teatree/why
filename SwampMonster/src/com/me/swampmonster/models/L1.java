package com.me.swampmonster.models;

import java.util.List;
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
	public int wavesAmount;
	public int currentWave;
	Bunker bunker;
	WaveGenerator waveGenerator = new WaveGenerator();
	MisterSpawner misterSpawner = new MisterSpawner();
	
	public L1(){
		create();
	}
	
	public void create(){
		player = new Player(new Vector2());
		wavesAmount = waveGenerator.getWavesAmount(player.getPoints());
		currentWave = 0;
//		wave = waveGenerator.generateWave(player.getPoints());
		enemiesOnStage = new Stack<Enemy>();
		bunker = new Bunker();
		
	}
	
	public void update(boolean aiming, Vector3 touchPos, Vector3 V3point, TiledMapTileLayer collisionLayer, 
			List<Projectile> projectiles, CameraHelper cameraHelper, float dx, float dy) {
		this.player.update(aiming, touchPos, V3point, collisionLayer, dx, dy);
		misterSpawner.setCollisionLayer(collisionLayer);
		if(wave == null || wave.enemies.empty() && currentWave<wavesAmount){
			wave = waveGenerator.generateWave(player.getPoints());
			currentWave++;
		}
		if(wave!=null){
		if (enemiesOnStage.size() < wave.enemiesOnBattleField && !wave.enemies.empty()){
			Enemy enemy = wave.enemies.pop();
			misterSpawner.spawnEnemy(this, enemy);
			enemiesOnStage.push(enemy);
			
		}
		
		}
		for (Enemy enemy : enemiesOnStage){
			enemy.update(collisionLayer, projectiles, this.player, cameraHelper, enemiesOnStage);
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
