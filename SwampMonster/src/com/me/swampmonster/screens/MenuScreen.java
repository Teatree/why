package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MenuScreen extends AbstractGameScreen{

	ShapeRenderer s;
	
	public MenuScreen(Game game) {
		super(game);
		s = new ShapeRenderer();
	}

	@Override
	public void render (float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isTouched()){
			game.setScreen(new SwampScreen(game));
		}
		s.begin(ShapeType.Filled);
		s.rect(20, 20, 20, 20);
		s.end();
	}

	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void show() {
	}
	@Override
	public void hide() {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	@Override
	public void dispose() {
	}
}
