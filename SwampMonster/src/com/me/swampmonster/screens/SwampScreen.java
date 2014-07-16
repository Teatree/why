package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.me.swampmonster.game.L1Renderer;
import com.me.swampmonster.game.TheController;

public class SwampScreen extends AbstractGameScreen {

	private L1Renderer renderer;
	private static TheController theController;
	
	private boolean paused;
	
	public SwampScreen(Game game){
		super(game);
	}
	
	public void show() {
		if(theController == null){
			theController = new TheController(game, this.player);
		}else{
			TheController.reloadLevel(this.player);
		}
		renderer = new L1Renderer(theController);
		theController.l1Renderer = renderer;
	}
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!paused){
			theController.update(Gdx.graphics.getDeltaTime(), game);
		}
		renderer.render();
	}

	public void resize(int width, int height) {
		renderer.setSize(width, height);
	}

	public void hide() {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
		
	}
}
