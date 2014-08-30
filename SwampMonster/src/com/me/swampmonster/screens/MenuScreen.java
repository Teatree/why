package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.ScreenContainer;

public class MenuScreen extends AbstractGameScreen{
	
	private Stage stage;
	private Skin skin;
	Button playButton;
	Button exitButton;
	Button tutorialButton;
	Label wrldConqueror;
	Table table;
	// Dmitriy's shinanigans
	public static short lessBytes; 
	public static boolean tutorialFinished;
	public static boolean showTutorialButton;
	public Music menuMusic;

	
	public MenuScreen(Game game) {
		super(game);
		skin = new Skin(Gdx.files.internal("skins\\style.json"), new TextureAtlas(Gdx.files.internal("skins\\main.pack")));
		menuMusic = Assets.manager.get(Assets.menuBackgroundMusic);
		
		wrldConqueror = new Label(Constants.WORLDS_CONQUERROR, skin);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render (float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void show() {
		menuMusic.play();
		menuMusic.setLooping(true);
		table = new Table();
		stage = new Stage();
		Image img = new Image(new Texture("data/ui/wrldcnqr.png"));
		img.toBack();
		img.setHeight(Constants.VIEWPORT_GUI_HEIGHT);
		img.setWidth(Constants.VIEWPORT_GUI_WIDTH);
		stage.addActor(img);
		
		
		table.add(wrldConqueror).padBottom(40).row();
		playButton = new TextButton(Constants.PLAY, skin);
		playButton.addListener(new ClickListener(){
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				lessBytes = 1;
				Gdx.input.setInputProcessor(null);
				menuMusic.stop();
	            ((Game) Gdx.app.getApplicationListener()).setScreen(ScreenContainer.SS);
	        }
		});
		
		table.add(playButton).size(150,60).padBottom(20).row();
//		showTutorialButton = true;
		if(showTutorialButton){
			tutorialButton = new TextButton(Constants.TUTORIAL, skin);
			tutorialButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					lessBytes = 2;
					Gdx.input.setInputProcessor(null);
					System.out.println("tutorial clicked");
//					MenuScreen.tutorialFinished = false;
					menuMusic.stop();
		            ((Game) Gdx.app.getApplicationListener()).setScreen(ScreenContainer.SS);
		        }
			});
			table.add(tutorialButton).size(150,60).padBottom(20).row();;
		}
		
		exitButton = new TextButton(Constants.EXIT, skin);
		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked (InputEvent event, float x, float y){
				Gdx.app.exit();
			}
		});
	    table.add(exitButton).size(150,60).padBottom(20).row();

	    table.setFillParent(true);
	    stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
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
		stage.dispose();
        skin.dispose();
	}
}
