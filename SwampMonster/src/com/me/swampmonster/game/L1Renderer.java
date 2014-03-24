package com.me.swampmonster.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.AI.Node;
import com.me.swampmonster.models.Enemy;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.utils.Constants;

public class L1Renderer {
	private OrthographicCamera cam;
	private TheController theController;
	
	// Temporary debug feature
	private ShapeRenderer sr;
	private ShapeRenderer staticSr;
	// Temporary debug feature
	
	private SpriteBatch batch;
	private SpriteBatch staticBatch;
	private OrthogonalTiledMapRenderer mapRenderer;
	private TiledMapTileLayer layer1;
	private BitmapFont font;
	private int timer;
	
	
	private int[] background = {0};
	private int[] foreground = {1};
	private int[] fiveground = {2};
	
	float ass = 1f;
	float assRevert = 0f;
	
	public L1Renderer(TheController theController){
		this.theController = theController;
		this.cam = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"),
		         Gdx.files.internal("data/font_0.tga"), false);
		
		// Temporary debug feature
//		Pathfinder.setTiledMap(level1.getBunker().getMap());
		// temporary bedug feature
		staticBatch = new SpriteBatch();
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		staticSr = new ShapeRenderer();
		mapRenderer = new OrthogonalTiledMapRenderer(theController.level1.getBunker().getMap());
		layer1 = theController.level1.getBunker().gettLayer();
		
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
			batch.draw(theController.gui.getCroshair().getSprite(), theController.getV3point().x-16, theController.getV3point().y-16, 
					theController.gui.getCroshair().getSprite().getWidth(), 
					theController.gui.getCroshair().getSprite().getHeight());
		}
		// temporary drawing of a projectile
		if(theController.projectile != null){
			batch.draw(theController.projectile.getSprite(), theController.projectile.getPosition().x, theController.projectile.getPosition().y, 
					theController.projectile.getSprite().getOriginX(), theController.projectile.getSprite().getOriginY(),
					theController.projectile.getSprite().getWidth(), theController.projectile.getSprite().getHeight(), 
					1,1,
				theController.projectile.getSprite().getRotation());
		}
		batch.end();
		
		batch.begin();
		for (Enemy enemy : theController.level1.getEnemies()){
			if(enemy.getPosition().y+32 > theController.level1.getPlayer().getPosition().y+32){
				enemy.getSprite().setPosition(enemy.getPosition().x, enemy.getPosition().y);
				enemy.getSprite().draw(batch);
			}
		}
			
			theController.level1.getPlayer().getSprite().setPosition(theController.level1.getPlayer().getPosition().x, theController.level1.getPlayer().getPosition().y);
			theController.level1.getPlayer().getSprite().draw(batch);
//			batch.draw(theController.level1.getPlayer().getSprite(), theController.level1.getPlayer().getPosition().x, theController.level1.getPlayer().getPosition().y,
//					theController.level1.getPlayer().getSprite().getWidth(), theController.level1.getPlayer().getSprite().getHeight());
//			theController.level1.drawEnemy(batch);
		for(Enemy enemy : theController.level1.getEnemies()){
			if(enemy.getPosition().y+32 < theController.level1.getPlayer().getPosition().y+32){
				enemy.getSprite().setPosition(enemy.getPosition().x, enemy.getPosition().y);
				enemy.getSprite().draw(batch);
			}
		}
		
		batch.end();
		
		
		// Temporary deBug feature
		sr.begin(ShapeType.Line);
		for (Enemy enemy : theController.level1.getEnemies()){
			sr.setColor(Color.GREEN);
			sr.circle(enemy.getPosition().x+8, enemy.getPosition().y+16, enemy.getgReenAura().radius);
			sr.setColor(Color.BLUE);
			sr.circle(enemy.getoRangeAura().x+8, enemy.getoRangeAura().y+16, enemy.getoRangeAura().radius);
		}	
		sr.setColor(Color.WHITE);
		sr.rect(theController.level1.getPlayer().getPosition().x, theController.level1.getPlayer().getPosition().y,
				theController.level1.getPlayer().getRectanlge().width, theController.level1.getPlayer().getRectanlge().height);
		sr.setColor(Color.WHITE);
		if(theController.level1.getPlayer().getState() == State.GUNMOVEMENT){
			sr.line(theController.V3playerPos, theController.level1.getPlayer().getShotDir());
		}
		sr.end();
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
//		sr.circle(theController.projectile.getCircle().x, theController.projectile.getCircle().y, theController.projectile.getCircle().radius);
		for (Enemy enemy : theController.level1.getEnemies())
			if(enemy.getPath() != null){
				for(Node n : enemy.getPath()){
					if(n != null){
						sr.rect((n.x*16)+6, (n.y*16)+6, 4, 4);
					}
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
		if(theController.level1.getPlayer().isHurt()){
			int j = 0;
			if(theController.level1.getPlayer().getHealth()>1){
				j = theController.level1.getPlayer().getHealth()-1;
			}
			theController.level1.getPlayer().setHurt(true);
			staticSr.setColor(new Color(200, 0, 0, ass));
			if(theController.gui.getHealthBar().getHealthBarRect()[j]!=null){
				staticSr.rect(theController.gui.getHealthBar().getHealthBarRect()[j].x+16, theController.gui.getHealthBar().getHealthBarRect()[j].y, 
						theController.gui.getHealthBar().getHealthBarRect()[j].width, theController.gui.getHealthBar().getHealthBarRect()[j].height);
			}
			ass = ass - 0.02f;
		}else if(!theController.level1.getPlayer().isHurt()){
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
		staticSr.circle(400, 255, theController.level1.getPlayer().getCircle().radius*2);
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
