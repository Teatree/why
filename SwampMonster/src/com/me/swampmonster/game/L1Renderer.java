package com.me.swampmonster.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
	
	float ass = 1f;
	
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
		batch.draw(theController.gui.getCroshair().getSprite(), (theController.gui.getCroshair().getPosition().x - theController.gui.getCroshair().getCircle().radius) + theController.gui.getCroshair().getSprite().getWidth()/2, 
				(theController.gui.getCroshair().getPosition().y - theController.gui.getCroshair().getCircle().radius) + theController.gui.getCroshair().getSprite().getHeight()/2, 
				theController.gui.getCroshair().getSprite().getWidth(), 
				theController.gui.getCroshair().getSprite().getHeight());
		batch.end();
		
		
		// Temporary deBug feature
		sr.begin(ShapeType.Line);
		sr.setColor(Color.GREEN);
		sr.circle(theController.level1.getEnemy().getPosition().x+8, theController.level1.getEnemy().getPosition().y+16, theController.level1.getEnemy().getgReenAura().radius);
		sr.setColor(Color.BLUE);
		sr.circle(theController.level1.getEnemy().getoRangeAura().x+8, theController.level1.getEnemy().getoRangeAura().y+16, theController.level1.getEnemy().getoRangeAura().radius);
		sr.setColor(Color.RED);
		sr.circle(theController.level1.getPlayer().getPosition().x+8, theController.level1.getPlayer().getPosition().y+16, theController.level1.getPlayer().getCircle().radius);
		sr.setColor(Color.WHITE);
		sr.circle(theController.gui.getCroshair().getPosition().x + theController.level1.getPlayer().getSprite().getWidth()/2,
				theController.gui.getCroshair().getPosition().y + theController.level1.getPlayer().getSprite().getHeight()/2, theController.gui.getCroshair().getCircle().radius);
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
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		staticSr.begin(ShapeType.Filled);
		staticSr.setColor(Color.RED);
		for(Rectangle r : theController.gui.getHealthBar().getHealthBarRect()){
			if(r != null){
				staticSr.rect(r.x, r.y, r.width, r.height);
			}
		}
		if(theController.hurt){
			System.out.println("Hurt is trye!");
			int j = 0;
			if(theController.level1.getPlayer().getHealth()>1){
				j = theController.level1.getPlayer().getHealth()-1;
			}
			theController.hurt = true;
			staticSr.setColor(new Color(200, 0, 0, ass));
			if(theController.gui.getHealthBar().getHealthBarRect()[j]!=null){
				staticSr.rect(theController.gui.getHealthBar().getHealthBarRect()[j].x+16, theController.gui.getHealthBar().getHealthBarRect()[j].y, 
						theController.gui.getHealthBar().getHealthBarRect()[j].width, theController.gui.getHealthBar().getHealthBarRect()[j].height);
			}
			ass = ass - 0.02f;
			System.out.println(ass);
		}else if(!theController.hurt){
			ass = 1f;
		}
		staticSr.setColor(Color.YELLOW);
		if(theController.level1.getPlayer().getOxygen()>0){
			staticSr.rect(30, 422, theController.level1.getPlayer().getOxygen(), 22);
		}
		if(theController.gui.getWeaponizer().isOn() == false){
			staticSr.setColor(Color.LIGHT_GRAY);
		}else if(theController.gui.getWeaponizer().isOn() == true){
			staticSr.setColor(Color.WHITE);
		}
		staticSr.circle(theController.gui.getWeaponizer().getCircle().x, theController.gui.getWeaponizer().getCircle().y, theController.gui.getWeaponizer().getCircle().radius);
		
		if(theController.gui.getMaskizer().isOn() == false){
			staticSr.setColor(Color.LIGHT_GRAY);
		}else if(theController.gui.getMaskizer().isOn() == true){
			staticSr.setColor(Color.WHITE);
		}
		staticSr.circle(theController.gui.getMaskizer().getCircle().x, theController.gui.getMaskizer().getCircle().y, theController.gui.getMaskizer().getCircle().radius);
		staticSr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		staticBatch.begin();
		staticBatch.draw(theController.gui.getHealthBar().getSprite(), 0, 448, theController.gui.getHealthBar().getSprite().getWidth(), theController.gui.getHealthBar().getSprite().getHeight());
		staticBatch.draw(theController.gui.getOxygenBar().getSprite(), 0, 416, theController.gui.getHealthBar().getSprite().getWidth(), theController.gui.getHealthBar().getSprite().getHeight());
		staticBatch.draw(theController.gui.getWeaponizer().getSprite(), 0, 0);
		staticBatch.draw(theController.gui.getMaskizer().getSprite(), 0, 128);
		staticBatch.end();
		
//		staticSr.begin(ShapeType.Filled);
//		staticSr.setColor(Color.GRAY);
//		staticSr.rect(theController.gui.getGameoverGUI().getRectanlge().x, theController.gui.getGameoverGUI().getRectanlge().y,
//				theController.gui.getGameoverGUI().getRectanlge().width, theController.gui.getGameoverGUI().getRectanlge().height);
//		staticSr.end();
		
		theController.cameraHelper.applyTo(cam);
	}
	//there are two of those;
	
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
