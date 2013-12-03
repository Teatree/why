package com.me.swampmonster.models;

import com.badlogic.gdx.math.Vector2;


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
	public void update(){
		player.update();
		enemy.update();
		bunker.update();
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
