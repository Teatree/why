package com.me.swampmonster.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.AI.Node;
import com.me.swampmonster.AI.Pathfinder;
import com.me.swampmonster.GUI.GUI;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.AbstractGameObject.State;
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
	private OrthogonalTiledMapRenderer mapInsideBunkerRenderer;
	private BatchTiledMapRenderer batchMapRenderer;
	private TiledMapTileLayer layer1;
	private BitmapFont font;
	private int timer;
	
	private int width;
	private int height;
	
	private int[] background = {0};
	private int[] foreground = {1};
	private int[] fiveground = {2};
	
	float ass = 1f;
	float assRevert = 0f;
	
	public L1Renderer(L1 level1, TheController theController){
		this.level1 = level1;
		this.theController = theController;
		this.cam = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"),
		         Gdx.files.internal("data/font_0.tga"), false);
		
		gui = new GUI();
		// Temporary debug feature
		pathfinder = new Pathfinder(level1.getBunker().getMap());
		// temporary bedug feature
		staticBatch = new SpriteBatch();
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		staticSr = new ShapeRenderer();
		mapRenderer = new OrthogonalTiledMapRenderer(level1.getBunker().getMap());
		layer1 = level1.getBunker().gettLayer();
		
		timer = 60;
		
	}	
	public void render() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		theController.cameraHelper.applyTo(cam);
		
//		cam.unproject(theController.touchPos);
//		System.out.println("MY X IS: " + theController.touchPos.x + " MY Y IS: " + theController.touchPos.y + " AND MY Z IS: " + theController.touchPos.z); 
		
		batch.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		
		mapRenderer.setView(cam);
		mapRenderer.render(background);
//		if(theController.level1.getPlayer().getPosition().x > 650){
//			mapRenderer.render(background);
//			mapRenderer.render(fiveground);
//		}
		
		batch.begin();
		if(Gdx.input.isTouched() && theController.level1.getPlayer().getState() == State.GUNMOVEMENT && theController.gui.getCroshair().isAiming()){
			batch.draw(theController.gui.getCroshair().getSprite(), theController.getV3point().x, theController.getV3point().y, 
					theController.gui.getCroshair().getSprite().getWidth(), 
					theController.gui.getCroshair().getSprite().getHeight());
		}
		batch.end();
		
		if(theController.level1.getEnemy().getPosition().y+20 < theController.level1.getPlayer().getPosition().y+20){
			theController.level1.drawPlayer(batch);
		}	
		theController.level1.drawEnemy(batch);
		if(theController.level1.getEnemy().getPosition().y+20 > theController.level1.getPlayer().getPosition().y+20){
			theController.level1.drawPlayer(batch);
		}
		
		// Temporary deBug feature
		sr.begin(ShapeType.Line);
		sr.setColor(Color.GREEN);
		sr.circle(theController.level1.getEnemy().getPosition().x+8, theController.level1.getEnemy().getPosition().y+16, theController.level1.getEnemy().getgReenAura().radius);
		sr.setColor(Color.BLUE);
		sr.circle(theController.level1.getEnemy().getoRangeAura().x+8, theController.level1.getEnemy().getoRangeAura().y+16, theController.level1.getEnemy().getoRangeAura().radius);
		sr.setColor(Color.WHITE);
		sr.rect(theController.level1.getPlayer().getPosition().x, theController.level1.getPlayer().getPosition().y,
				theController.level1.getPlayer().getRectanlge().width, theController.level1.getPlayer().getRectanlge().height);
		sr.setColor(Color.WHITE);
		if(Gdx.input.isTouched() && theController.level1.getPlayer().getState() == State.GUNMOVEMENT && theController.gui.getCroshair().isAiming()){
			sr.line(theController.V3playerPos, theController.V3point);
		}
		sr.end();
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
		for(Node n : theController.pathfinder.getPath()){
			if(n != null){
				sr.rect((n.x*16)+6, (n.y*16)+6, 4, 4);
			}
		}
		sr.setColor(Color.BLACK);
		sr.rect(theController.touchPos.x, theController.touchPos.y, 1, 1);
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
		}else if(!theController.hurt){
			ass = 1f;
		}
		if(theController.level1.getPlayer().getOxygen()>0){
			staticSr.setColor(Color.YELLOW);
		}
		if(theController.level1.getPlayer().getOxygen()<42){
			warningFlicker(staticSr);
		}
		if(theController.level1.getPlayer().getOxygen()<=0 && timer >= 10){
			System.out.println(timer);
			staticSr.setColor(new Color(0, 200, 20, 0.5f));
			staticSr.rect(30, 422, 96, 22);
		}
		if(theController.level1.getPlayer().isMaskOn()){
			if(theController.level1.getPlayer().getOxygen()>0){
				staticSr.rect(30, 422, theController.level1.getPlayer().getOxygen(), 22);
			}
		}
		staticSr.setColor(Color.BLUE);
		if(theController.level1.getPlayer().isMaskOn() && theController.level1.getPlayer().getOxygen() == 0){
			staticSr.rect(30, 422, 96, 22);
		}
		if(theController.level1.getPlayer().getState() != State.DEAD){
			if(theController.gui.getWeaponizer().isOn() == false){
				staticSr.setColor(Color.LIGHT_GRAY);
			}else if(theController.gui.getWeaponizer().isOn() == true){
				staticSr.setColor(Color.WHITE);
			}
		staticSr.circle(theController.gui.getWeaponizer().getCircle().x, theController.gui.getWeaponizer().getCircle().y, theController.gui.getWeaponizer().getCircle().radius);
		
		}
		staticSr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		staticSr.begin(ShapeType.Line);
		staticSr.setColor(Color.MAGENTA);
		if(theController.doesIntersect(new Vector2(416,255), theController.level1.getPlayer().getCircle().radius*2)){
			staticSr.setColor(Color.WHITE);
		}
		staticSr.circle(416, 255, theController.level1.getPlayer().getCircle().radius*2);
		staticSr.end();
		
		staticBatch.begin();
		staticBatch.draw(theController.gui.getHealthBar().getSprite(), 0, 448, theController.gui.getHealthBar().getSprite().getWidth(), theController.gui.getHealthBar().getSprite().getHeight());
		if(theController.level1.getPlayer().isMaskOn()){
			staticBatch.draw(theController.gui.getOxygenBar().getSprite(), 0, 416, theController.gui.getHealthBar().getSprite().getWidth(), theController.gui.getHealthBar().getSprite().getHeight());
		}
		if(theController.level1.getPlayer().getState() != State.DEAD){
			staticBatch.draw(theController.gui.getWeaponizer().getSprite(), 0, 0);
		}
		staticBatch.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		staticSr.begin(ShapeType.Filled);
			if(theController.level1.getPlayer().isDead()){
				staticSr.setColor(new Color(200, 0, 0, assRevert));
				staticSr.rect(theController.gui.getGameoverGUI().getRectanlge().x, theController.gui.getGameoverGUI().getRectanlge().y,
						theController.gui.getGameoverGUI().getRectanlge().width, theController.gui.getGameoverGUI().getRectanlge().height);
				if(assRevert < 0.5f && theController.level1.getPlayer().getState() == State.DEAD){
					assRevert = assRevert + 0.002f;
				}
			}
			if(assRevert >= 0.45f && theController.level1.getPlayer().getState() == State.DEAD){
				staticSr.setColor(Color.GREEN);
				if(theController.doesIntersect(new Vector2(400, 140), 60)){
					staticSr.setColor(new Color(0, 200, 0.5f, 100));
				}
				staticSr.circle(theController.gui.getGameoverGUI().getCircle().x, theController.gui.getGameoverGUI().getCircle().y, theController.gui.getGameoverGUI().getCircle().radius);
			}
		staticSr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		staticSr.begin(ShapeType.Line);
		if(assRevert >= 0.45f && theController.level1.getPlayer().getState() == State.DEAD){
			staticSr.setColor(Color.BLACK);
			staticSr.circle(theController.gui.getGameoverGUI().getCircle().x, theController.gui.getGameoverGUI().getCircle().y , theController.gui.getGameoverGUI().getCircle().radius);
		}
		staticSr.end();
		
		staticBatch.begin();
		font.setColor(Color.YELLOW);
		font.setScale(2);
		if(assRevert >= 0.4f && theController.level1.getPlayer().getState() == State.DEAD){
			font.draw(staticBatch, theController.gui.getGameoverGUI().getGameOverString(), 310, 280);
		}
		if(assRevert >= 0.4f && theController.level1.getPlayer().getState() == State.DEAD){
			font.setScale(1);
			font.draw(staticBatch, theController.gui.getGameoverGUI().getWittyMessage(), 240-theController.gui.getGameoverGUI().getWittyMessage().length(), 230);
		}
		if(assRevert >= 0.45f && theController.level1.getPlayer().getState() == State.DEAD){
			font.setScale(1);
			font.draw(staticBatch, theController.gui.getGameoverGUI().getRestartString(), 361, 170);
		}
		staticBatch.end();
	}
	
	public void warningFlicker(ShapeRenderer Sr){
		if(timer >= 42){
			Sr.setColor(Color.YELLOW);
			timer--;
		}else if(timer <= 42 && timer > 0){
			Sr.setColor(Color.CYAN);
			timer--;
		}else if(timer <= 1){
			timer = 60;
		}
	}
	
	//there are two of those;
	
	public void setSize(int width, int height){
//		this.width = width;
//		this.height = height;
		cam.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) *	width;
		cam.update();
	}
	public void dispose(){
		batch.dispose();
		sr.dispose();
		staticBatch.dispose();
		staticSr.dispose();
	}
	public OrthographicCamera getCam() {
		return cam;
	}
	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
	}
	
}
