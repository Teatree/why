package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.game.L1Renderer;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.SaveManager;
import com.me.swampmonster.utils.ScreenContainer;

public class SwampScreen extends AbstractGameScreen  implements Screen {

	private L1Renderer renderer;
	private static TheController theController;
	private boolean paused;
	
	public SwampScreen(Game game){
		super(game);
		
		player = SaveManager.loadPlayer();
		if (player == null) {
			System.out.println("newPlayer4");
			player = new Player(new Vector2());
		} 
	}
	
	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true);
		if(theController == null){
			theController = new TheController(game, this.player);
		}else{
//			TheController.savedScore = L1.player.points;
			TheController.reloadLevel(this.player);
		}
		renderer = new L1Renderer(theController);
		theController.l1Renderer = renderer;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!paused){
			theController.update(Gdx.graphics.getDeltaTime(), game);
		}
		if (TheController.gotoMenu){
			TheController.gotoMenu = false;
			((Game) Gdx.app.getApplicationListener()).setScreen(ScreenContainer.MS);
		}
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.stage.getViewport().update(width, height, true);
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
