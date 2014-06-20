package com.me.swampmonster;

import com.badlogic.gdx.Game;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.utils.Assets;

public class MainSwamp extends Game {

	public void create() {		
		Assets.load();
		while (!Assets.manager.update()){
		}
		setScreen(new SlotMachineScreen(this));
	}
	public void dispose() {
		super.dispose();
	}
	public void render() {		
		super.render();
	}
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	public void pause() {
		super.pause();
	}
	public void resume() {
		super.resume();
	}
}