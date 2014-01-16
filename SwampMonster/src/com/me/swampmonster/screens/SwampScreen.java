package com.me.swampmonster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.me.swampmonster.game.L1Renderer;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.L1;

public class SwampScreen implements Screen {

	private L1 level1;
	private L1Renderer renderer;
	private TheController theController;
	
	public void show() {
		theController = new TheController();
		level1 = new L1(); 
		renderer = new L1Renderer(level1, theController);
		theController.l1Renderer = renderer;
	}
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		theController.update(Gdx.graphics.getDeltaTime());
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
