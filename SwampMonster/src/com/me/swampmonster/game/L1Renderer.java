package com.me.swampmonster.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.utils.Constants;

public class L1Renderer {
	private L1 level1;
	private OrthographicCamera cam;
	private TheController theController;
	
	private SpriteBatch batch;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	private int width;
	private int height;
	private float unitScale = 1f;
	
	public L1Renderer(L1 level1, TheController theController){
		this.level1 = level1;
		this.theController = theController;
		this.cam = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		this.cam.update();
		
		batch = new SpriteBatch();
		mapRenderer = new OrthogonalTiledMapRenderer(level1.getBunker().getMap(), unitScale);
	}	
	public void render() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
//		cam.unproject(theController.touchPos);
//		System.out.println("MY X IS: " + theController.touchPos.x + " MY Y IS: " + theController.touchPos.y + " AND MY Z IS: " + theController.touchPos.z); 
		
		batch.setProjectionMatrix(cam.combined);
		
		mapRenderer.setView(cam);
		mapRenderer.render();
		
		
		batch.begin();
		batch.draw(theController.level1.getEnemy().getSprite(), theController.level1.getEnemy().getPosition().x, 
				theController.level1.getEnemy().getPosition().y, 
				theController.level1.getEnemy().getSprite().getWidth(), 
				theController.level1.getEnemy().getSprite().getHeight());
		batch.draw(theController.level1.getPlayer().getSprite(), theController.level1.getPlayer().getPosition().x, 
				theController.level1.getPlayer().getPosition().y, 
				theController.level1.getPlayer().getSprite().getWidth(), 
				theController.level1.getPlayer().getSprite().getHeight());
		batch.end();

		
		
		theController.cameraHelper.applyTo(cam);
	}
	
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
		cam.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) *	width;
		cam.update();
	}
	public OrthographicCamera getCam() {
		return cam;
	}
	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
	}
}
