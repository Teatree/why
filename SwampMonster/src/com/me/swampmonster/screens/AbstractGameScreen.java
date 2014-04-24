package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class AbstractGameScreen implements Screen {
	protected Game game;
	
	public AbstractGameScreen(Game game){
		this.game = game;
	}
	
	public void dispose() {
	}
	public void render(float deltaTime) {		
	}
	public void resize(int width, int height) {
	}
	public void pause() {
	}
	public void resume() {
	}
}
