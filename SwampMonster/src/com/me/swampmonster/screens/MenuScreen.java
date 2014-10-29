package com.me.swampmonster.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.ScreenContainer;

public class MenuScreen extends AbstractGameScreen{
	
	private Stage stage;
	private Skin skin;
	Button playButton;
	Button exitButton;
	Button tutorialButton;
	public static Button soundButton;
	Label wrldConqueror;
	Table table;
	// Dmitriy's shinanigans
	public static short lessBytes; 
	public static boolean tutorialFinished;
	public static boolean showTutorialButton;
	public static boolean soundsEnabled = true;
	public Music menuMusic;
	private Array<Viewport> viewports;
	private  static  Array<String> names;
	
	public MenuScreen(Game game) {
		super(game);
		skin = new Skin(Gdx.files.internal("skins\\style.json"), new TextureAtlas(Gdx.files.internal("skins\\main.pack")));
		menuMusic = Assets.manager.get(Assets.menuBackgroundMusic);
		wrldConqueror = new Label(Constants.WORLDS_CONQUERROR, skin);
		Gdx.input.setInputProcessor(stage);
		
		soundButton = new ImageButton(skin);
		soundButton.addListener(new ClickListener(){
			@Override
			public void clicked (InputEvent event, float x, float y){
				if (soundsEnabled){
					soundsEnabled = false;
					menuMusic.setVolume(0);
				} else {
					soundsEnabled = true;
					menuMusic.setVolume(13);
					menuMusic.play();
					menuMusic.setLooping(true);
				}
			}
		});
		if (!soundsEnabled){
			soundButton.setChecked(true);
		}
	}

	@Override
	public void render (float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		stage.act();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void show() {
		if (soundsEnabled){
			menuMusic.play();
			menuMusic.setLooping(true);
		}
		table = new Table().right();
		stage = new Stage();
		Image img = new Image(new Texture("data/ui/bgImage.png"));
		img.toBack();
		img.setHeight(Constants.VIEWPORT_GUI_HEIGHT);
		img.setWidth(Constants.VIEWPORT_GUI_WIDTH);
		stage.addActor(img);
		
		viewports = getViewports(stage.getCamera());
		names = getViewportNames();

		stage.setViewport(viewports.first());
		
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
		
		table.add(playButton).size(150,60).padBottom(20).row().right();
//		showTutorialButton = true;
		if(showTutorialButton){
			tutorialButton = new TextButton(Constants.TUTORIAL, skin);
			tutorialButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					lessBytes = 2;
					Gdx.input.setInputProcessor(null);
//					System.out.println("tutorial clicked");
//					MenuScreen.tutorialFinished = false;
					menuMusic.stop();
		            ((Game) Gdx.app.getApplicationListener()).setScreen(ScreenContainer.SS);
		        }
			});
			table.add(tutorialButton).size(150,60).padBottom(20).row().right();
		}
		
		exitButton = new TextButton(Constants.EXIT, skin);
		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked (InputEvent event, float x, float y){
				Gdx.app.exit();
			}
		});
		
	    table.add(exitButton).size(150,60).padBottom(20).row().right();

		table.add(soundButton).size(64,64).padBottom(50);
	    table.setFillParent(true);
	    stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
	}
	
	static public Array<String> getViewportNames () {
		/*Array<String> */names = new Array();
		names.add("StretchViewport");
		names.add("FillViewport");
		names.add("FitViewport");
		names.add("ExtendViewport: no max");
		names.add("ExtendViewport: max");
		names.add("ScreenViewport: 1:1");
		names.add("ScreenViewport: 0.75:1");
		names.add("ScalingViewport: none");
		return names;
	}

	static public Array<Viewport> getViewports (Camera camera) {
		int minWorldWidth = 800;
		int minWorldHeight = 480;
		int maxWorldWidth = 800;
		int maxWorldHeight = 480;

		Array<Viewport> viewports = new Array();
		viewports.add(new StretchViewport(minWorldWidth, minWorldHeight, camera));
		viewports.add(new FillViewport(minWorldWidth, minWorldHeight, camera));
		viewports.add(new FitViewport(minWorldWidth, minWorldHeight, camera));
		viewports.add(new ExtendViewport(minWorldWidth, minWorldHeight, camera));
		viewports.add(new ExtendViewport(minWorldWidth, minWorldHeight, maxWorldWidth, maxWorldHeight, camera));
		viewports.add(new ScreenViewport(camera));

		ScreenViewport screenViewport = new ScreenViewport(camera);
		screenViewport.setUnitsPerPixel(0.75f);
		viewports.add(screenViewport);

		viewports.add(new ScalingViewport(Scaling.none, minWorldWidth, minWorldHeight, camera));
		return viewports;
	}
	
	@Override
	public void hide() {
	}
	
	@Override
	public void pause() {
		menuMusic.pause();
	}
	
	@Override
	public void resume() {
		menuMusic.play();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
        skin.dispose();
	}
}
