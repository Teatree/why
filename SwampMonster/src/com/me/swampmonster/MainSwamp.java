package com.me.swampmonster;

import com.badlogic.gdx.Game;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.SaveManager;
import com.me.swampmonster.utils.ScreenContainer;

public class MainSwamp extends Game {
	
	@Override
	public void create() {		
		Assets.load();
		while (!Assets.manager.update()){
		}
		setScreen(ScreenContainer.MS);
	}
	@Override
	public void dispose() {
		SaveManager.savePlayer();
		super.dispose();
	}
	@Override
	public void render() {		
		super.render();
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	@Override
	public void pause() {
		super.pause();
	}
	@Override
	public void resume() {
		super.resume();
	}
}