package com.playground;

import com.badlogic.gdx.Game;

public class PeMain extends Game{

	
	public void create() {
		setScreen(new PeScreen());
	}

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void render() {
		super.render();
	}
	public void pause() {
		super.pause();
	}
	public void resume() {
		super.resume();
	}
	public void dispose() {
		super.dispose();
	}
}