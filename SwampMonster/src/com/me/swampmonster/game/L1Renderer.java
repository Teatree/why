package com.me.swampmonster.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.me.swampmonster.AI.Node;
import com.me.swampmonster.AI.Pathfinder;
import com.me.swampmonster.GUI.GUI;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.utils.Constants;

public class L1Renderer {
	private L1 level1;
	private GUI gui;
	private OrthographicCamera cam;
	private TheController theController;
	
	// Temporary debug feature
	private Pathfinder pathfinder;
	private ShapeRenderer sr;
	private ShapeRenderer staticSr;
	// Temporary debug feature
	
	private SpriteBatch batch;
	private SpriteBatch staticBatch;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	private int width;
	private int height;
	private float unitScale = 1f;
	
	public L1Renderer(L1 level1, TheController theController){
		this.level1 = level1;
		this.theController = theController;
		this.cam = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		this.cam.update();
		
		gui = new GUI();
		// Temporary debug feature
		pathfinder = new Pathfinder(level1.getBunker().getMap());
		// temporary bedug feature
		staticBatch = new SpriteBatch();
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		staticSr = new ShapeRenderer();
		mapRenderer = new OrthogonalTiledMapRenderer(level1.getBunker().getMap(), unitScale);
	}	
	public void render() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
//		cam.unproject(theController.touchPos);
//		System.out.println("MY X IS: " + theController.touchPos.x + " MY Y IS: " + theController.touchPos.y + " AND MY Z IS: " + theController.touchPos.z); 
		
		batch.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		
		mapRenderer.setView(cam);
		mapRenderer.render();
		
		batch.begin();
		batch.draw(theController.level1.getPlayer().getSprite(), theController.level1.getPlayer().getPosition().x, 
				theController.level1.getPlayer().getPosition().y, 
				theController.level1.getPlayer().getSprite().getWidth(), 
				theController.level1.getPlayer().getSprite().getHeight());
		batch.draw(theController.level1.getEnemy().getSprite(), theController.level1.getEnemy().getPosition().x, 
				theController.level1.getEnemy().getPosition().y, 
				theController.level1.getEnemy().getSprite().getWidth(), 
				theController.level1.getEnemy().getSprite().getHeight());
		batch.end();
		
		
		// Temporary deBug feature
		sr.begin(ShapeType.Line);
		sr.setColor(Color.GREEN);
		sr.circle(theController.level1.getEnemy().getPosition().x+8, theController.level1.getEnemy().getPosition().y+16, theController.level1.getEnemy().getgReenAura().radius);
		sr.setColor(Color.BLUE);
		sr.circle(theController.level1.getEnemy().getoRangeAura().x+8, theController.level1.getEnemy().getoRangeAura().y+16, theController.level1.getEnemy().getoRangeAura().radius);
		sr.setColor(Color.RED);
		sr.circle(theController.level1.getPlayer().getPosition().x+8, theController.level1.getPlayer().getPosition().y+16, theController.level1.getPlayer().getTempCircle().radius);
		sr.end();
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
		for(Node n : theController.pathfinder.getPath()){
			if(n != null){
				sr.rect((n.x*16)+6, (n.y*16)+6, 4, 4);
			}
		}
		sr.end();
		// Temporary deBug feature
		
		staticSr.begin(ShapeType.Filled);
		staticSr.setColor(Color.RED);
		for(Rectangle r : theController.gui.getHealthBar()){
			if(r != null){
				staticSr.rect(r.x, r.y, r.width, r.height);
			}
		}
		staticSr.setColor(Color.YELLOW);
		for(Rectangle r : theController.gui.getOxygenBar()){
			if(r != null){
				staticSr.rect(r.x, r.y, r.width, r.height);
			}
		}
		staticSr.end();
		
		staticBatch.begin();
		staticBatch.draw(theController.gui.getHealthBarSprite(), 0, 448, theController.gui.getHealthBarSprite().getWidth(), theController.gui.getHealthBarSprite().getHeight());
		staticBatch.draw(theController.gui.getOxygenBarSprite(), 0, 416, theController.gui.getHealthBarSprite().getWidth(), theController.gui.getHealthBarSprite().getHeight());
		staticBatch.end();
		
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
