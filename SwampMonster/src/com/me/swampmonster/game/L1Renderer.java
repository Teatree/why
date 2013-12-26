package com.me.swampmonster.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.me.swampmonster.AI.Node;
import com.me.swampmonster.AI.Pathfinder;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.utils.Constants;

public class L1Renderer {
	private L1 level1;
	private OrthographicCamera cam;
	private TheController theController;
	
	// Temporary debug feature
	private Pathfinder pathfinder;
	private ShapeRenderer sr;
	// Temporary debug feature
	
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
		
		// Temporary debug feature
		pathfinder = new Pathfinder(level1.getBunker().getMap());
		// temporary bedug feature
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
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
		sr.setColor(Color.RED);
		sr.circle(theController.level1.getPlayer().getPosition().x+8, theController.level1.getPlayer().getPosition().y+16, theController.level1.getPlayer().getTempCircle().radius);
		sr.end();
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.YELLOW);
		sr.rect(theController.level1.getEnemy().getPosition().x, theController.level1.getEnemy().getPosition().y, 10, 10);
		sr.setColor(Color.RED);
		for(Node n : theController.pathfinder.getPath()){
			if(n != null){
				sr.rect((n.x*16)+6, (n.y*16)+6, 4, 4);
			}
		}
		sr.end();
		// Temporary deBug feature
		
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
