package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.me.swampmonster.utils.Constants;

public class MenuScreen extends AbstractGameScreen{

	ShapeRenderer s;
	SpriteBatch batch;
	SpriteDrawable sDrawable1;
	SpriteDrawable sDrawable2;
	Sprite sprite1;
	Sprite sprite2;
	ImageButton ib;
	
//	private SpriteBatch batch;
//	private Stage stage;
//	private Skin skin;
	
	public MenuScreen(Game game) {
		super(game);
//		batch = new SpriteBatch();
//		skin = new Skin(Gdx.files.internal("data\\ui.json"));
		s = new ShapeRenderer();
//		stage = new Stage(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, false, batch);
		batch = new SpriteBatch();
		
		sprite1 = new Sprite(new Texture("data/button.png"));
		sprite2 = new Sprite(new Texture("data/button_down.png"));
		sDrawable1 = new SpriteDrawable(sprite1);
		sDrawable2 = new SpriteDrawable(sprite2);
		
//		Button startCont = new TextButton("Text", skin);
		ib = new ImageButton(sDrawable1, sDrawable2); 
		
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
		
		batch.begin();
		ib.draw(batch, 100);
		batch.end();
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
