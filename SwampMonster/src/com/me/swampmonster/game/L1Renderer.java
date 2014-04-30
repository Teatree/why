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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.AI.Node;
import com.me.swampmonster.models.Enemy;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.utils.Constants;

public class L1Renderer {
	private OrthographicCamera cam;
	private Matrix4 matrix;
	private TheController theController;
	private Vector2 rPoint;
	
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
	private CharSequence str;
	private CharSequence str2;
	
	private int[] background = {0};
	
	float ass = 1f;
	float assRevert = 0f;
	
	public L1Renderer(TheController theController){
		this.theController = theController;
		this.cam = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"),
		         Gdx.files.internal("data/font_0.tga"), false);
		matrix = cam.combined.cpy();
		matrix.setToOrtho2D(0,0,Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT);
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
		
		rPoint = theController.point;
//		rPoint.unproject
	}	
	public void render() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		str = "points: " + theController.level1.getPlayer().getPoints();
		str2 = "Wave:" + theController.level1.currentWave + "/" + theController.level1.wavesAmount;
		
		theController.cameraHelper.applyTo(cam);
		
//		cam.unproject(theController.touchPos);
//		System.out.println("MY X IS: " + theController.touchPos.x + " MY Y IS: " + theController.touchPos.y + " AND MY Z IS: " + theController.touchPos.z); 
		
		batch.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
//		staticSr.setProjectionMatrix(matrix);
		
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
		batch.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.begin();
		for (Enemy enemy : theController.level1.enemiesOnStage){
			if(enemy.isHurt()){
				if(enemy.time==4){
					enemy.getSprite().setColor(enemy.getSprite().getColor().r, enemy.getSprite().getColor().g-1, enemy.getSprite().getColor().b-1, enemy.getSprite().getColor().a);;
				}
				if(enemy.time==14){
					enemy.getSprite().setColor(enemy.getSprite().getColor().r, enemy.getSprite().getColor().g+1, enemy.getSprite().getColor().b+1, enemy.getSprite().getColor().a);
				}
				if(enemy.time==24){
					enemy.getSprite().setColor(enemy.getSprite().getColor().r, enemy.getSprite().getColor().g-1, enemy.getSprite().getColor().b-1, enemy.getSprite().getColor().a);;
				}
				if(enemy.time==34){
					enemy.getSprite().setColor(enemy.getSprite().getColor().r, enemy.getSprite().getColor().g+1, enemy.getSprite().getColor().b+1, enemy.getSprite().getColor().a);
				}
				
			}
		}
		for (Enemy enemy : theController.level1.enemiesOnStage){
			if(enemy.getPosition().y+42 > theController.level1.getPlayer().getPosition().y+42){
				enemy.getSprite().setPosition(enemy.getPosition().x, enemy.getPosition().y);
				if(enemy.timeRemove<110){
					enemy.getSprite().draw(batch);
				}
				if(enemy.timeRemove>110 && enemy.timeRemove<115){
					enemy.getSprite().draw(batch);
				}
				if(enemy.timeRemove>120 && enemy.timeRemove<125){
					enemy.getSprite().draw(batch);
				}
				if(enemy.timeRemove>130 && enemy.timeRemove<135){
					enemy.getSprite().draw(batch);
				}
				if(enemy.timeRemove>140 && enemy.timeRemove<145){
					enemy.getSprite().draw(batch);
				}
				if(enemy.timeRemove>150 && enemy.timeRemove<155){
					enemy.getSprite().draw(batch);
				}
				if(enemy.timeRemove>160 && enemy.timeRemove<165){
					enemy.getSprite().draw(batch);
				}
				if(enemy.timeRemove>170 && enemy.timeRemove<175){
					enemy.getSprite().draw(batch);
				}
				
			}
		}
		
			
			theController.level1.getPlayer().getSprite().setPosition(theController.level1.getPlayer().getPosition().x, theController.level1.getPlayer().getPosition().y);
			theController.level1.getPlayer().getSprite().draw(batch);
//			batch.draw(theController.level1.getPlayer().getSprite(), theController.level1.getPlayer().getPosition().x, theController.level1.getPlayer().getPosition().y,
//					theController.level1.getPlayer().getSprite().getWidth(), theController.level1.getPlayer().getSprite().getHeight());
//			theController.level1.drawEnemy(batch);
		for(Enemy enemy : theController.level1.enemiesOnStage){
			if(enemy.getPosition().y+42 < theController.level1.getPlayer().getPosition().y+42){
				enemy.getSprite().setPosition(enemy.getPosition().x, enemy.getPosition().y);
				enemy.getSprite().draw(batch);
			}
		}
		for(Projectile p: theController.level1.getPlayer().projectiles){
			if(p != null){
				batch.draw(p.getSprite(), p.getPosition().x, p.getPosition().y, 
						p.getSprite().getOriginX(), p.getSprite().getOriginY(),
						p.getSprite().getWidth(), p.getSprite().getHeight(), 
						1,1,
						p.getSprite().getRotation());
			}
		}
		for(Enemy enemy : theController.level1.enemiesOnStage){
			for(Projectile p : enemy.enemyProjectiles){
				if(p != null){
					batch.draw(p.getSprite(), p.getPosition().x, p.getPosition().y, 
							p.getSprite().getOriginX(), p.getSprite().getOriginY(),
							p.getSprite().getWidth(), p.getSprite().getHeight(), 
							1,1,
							p.getSprite().getRotation());
				}
			}
		}
		for (Item item: theController.level1.items) {
			batch.draw(item.sprite, item.getPosition().x, item.getPosition().y, item.sprite.getWidth()/2, item.sprite.getHeight()/2);
		}
		
		batch.end();
		
		
		// Temporary deBug feature
		sr.begin(ShapeType.Line);
		for (Enemy enemy : theController.level1.enemiesOnStage){
			sr.setColor(Color.GREEN);
			sr.circle(enemy.getgReenAura().x, enemy.getgReenAura().y, enemy.getgReenAura().radius);
			sr.setColor(Color.BLUE);
			sr.circle(enemy.getoRangeAura().x, enemy.getoRangeAura().y, enemy.getoRangeAura().radius);
			sr.setColor(Color.YELLOW);
			sr.circle(enemy.yellowAura.x, enemy.yellowAura.y, enemy.yellowAura.radius);
		}	
		sr.setColor(Color.WHITE);
		for(Projectile p: theController.level1.getPlayer().projectiles){
			if(p!=null){
				sr.circle(p.getCircle().x, p.getCircle().y, p.getCircle().radius);
			}
		}
		sr.rect(theController.point.x, theController.point.y, 32, 32);
		sr.rect(theController.level1.getPlayer().getPosition().x, theController.level1.getPlayer().getPosition().y,
				theController.level1.getPlayer().getRectanlge().width, theController.level1.getPlayer().getRectanlge().height);
		for(Enemy enemy:theController.level1.enemiesOnStage){
			sr.rect(enemy.getRectanlge().x, enemy.getRectanlge().y,
					enemy.getRectanlge().width, enemy.getRectanlge().height);
		}
		sr.setColor(Color.WHITE);
		if(theController.level1.getPlayer().getState() == State.GUNMOVEMENT){
			sr.line(theController.V3playerPos, theController.level1.getPlayer().getShotDir());
		}
		sr.circle(theController.level1.getPlayer().getCircle().x, theController.level1.getPlayer().getCircle().y, theController.level1.getPlayer().getCircle().radius);
		sr.setColor(Color.YELLOW);
		sr.circle(theController.level1.getPlayer().aimingArea.x, theController.level1.getPlayer().aimingArea.y, theController.level1.getPlayer().aimingArea.radius);
		sr.end();
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
//		sr.circle(theController.projectile.getCircle().x, theController.projectile.getCircle().y, theController.projectile.getCircle().radius);
		for (Enemy enemy : theController.level1.enemiesOnStage)
			if(enemy.getPath() != null){
				for(Node n : enemy.getPath()){
					if(n != null){
						sr.rect((n.x*16)+6, (n.y*16)+6, 4, 4);
					}
				}
			}
		sr.setColor(Color.BLACK);
		sr.rect(theController.pointRectV3.x, theController.pointRectV3.y, 1, 1);
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
		if(theController.doesIntersect(new Vector2(theController.level1.getPlayer().getCircle().x, theController.level1.getPlayer().getCircle().y), theController.level1.getPlayer().getCircle().radius*2)){
				staticSr.setColor(Color.WHITE);
		}
		
		staticSr.setColor(Color.PINK);
		staticSr.rect(theController.pointRect.x, theController.pointRect.y, 2, 2);
		
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
		staticSr.rect(theController.debugRect.x, theController.debugRect.y, theController.debugRect.width, theController.debugRect.height);
		staticSr.end();
		
		staticBatch.begin();
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(staticBatch, str, 580, 460);
		font.draw(staticBatch, str2, 580, 420);
		font.setColor(Color.YELLOW);
		font.setScale(1);
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
