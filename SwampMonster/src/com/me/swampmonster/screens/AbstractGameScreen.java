package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.me.swampmonster.models.Player;

public abstract class AbstractGameScreen implements Screen {
	protected Game game;
	public Player player;
	
	public AbstractGameScreen(Game game){
		this.game = game;
//		player = SaveManager.loadPlayer();
//		if (player == null) {
//			System.out.println("newPlayer4");
//			player = new Player(new Vector2());
//		} 
	}
	
	@Override
	public void dispose() {
	}
	@Override
	public void render(float deltaTime) {		
	}
	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
}
