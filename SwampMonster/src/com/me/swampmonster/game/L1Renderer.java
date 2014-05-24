package com.me.swampmonster.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.swampmonster.AI.Node;
import com.me.swampmonster.models.Enemy;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.EnemyGenerator.Toughness;

public class L1Renderer {
	private OrthographicCamera cam;
	private TheController theController;
	
	// Temporary debug feature
	private ShapeRenderer sr;
	// Temporary debug feature
	
	private SpriteBatch batch;
	private OrthogonalTiledMapRenderer mapRenderer;
	private int timer;
	private Stage stage;
	private GShape gshape;
	
	private int[] background = {0};
	
	public L1Renderer(TheController theController){
		this.theController = theController;
		this.cam = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
//		stage.setViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_WIDTH, true);
		// Temporary debug feature
//		Pathfinder.setTiledMap(level1.bunker.getMap());
		// temporary bedug feature
		batch = new SpriteBatch();
		stage = new Stage(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, false, batch);
		sr = new ShapeRenderer();
		mapRenderer = new OrthogonalTiledMapRenderer(theController.level1.bunker.getMap());
		
		Image healthBarImage = new Image(theController.gui.getHealthBar().getSprite()); 
		healthBarImage.setPosition(theController.gui.getHealthBar().getPosition().x, theController.gui.getHealthBar().getPosition().y);
		Image weaponizerImage = new Image(theController.gui.getWeaponizer().getSprite()); 
		Image oxygenBarImage = new Image(theController.gui.getOxygenBar().getSprite());
		oxygenBarImage.setPosition(theController.gui.getOxygenBar().getPosition().x, theController.gui.getOxygenBar().getPosition().y);
		
		gshape = new GShape(theController);
		stage.addActor(gshape);
		
		stage.addActor(healthBarImage);
		stage.addActor(weaponizerImage);
		stage.addActor(oxygenBarImage);
		
		timer = 60;
	}	
	
	public void render() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		theController.cameraHelper.applyTo(cam);
		
		stage.act();
		
//		cam.unproject(theController.touchPos);
//		// System.out.println("MY X IS: " + theController.touchPos.x + " MY Y IS: " + theController.touchPos.y + " AND MY Z IS: " + theController.touchPos.z); 
		
		batch.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		
		mapRenderer.setView(cam);
		mapRenderer.render(background);
		
		batch.begin();
		if(Gdx.input.isTouched() && theController.level1.player.getState() == State.GUNMOVEMENT && theController.gui.getCroshair().isAiming()){
			batch.draw(theController.gui.getCroshair().getSprite(), theController.getV3point().x-16, theController.getV3point().y-16, 
					theController.gui.getCroshair().getSprite().getWidth(), 
					theController.gui.getCroshair().getSprite().getHeight());
		}
		// temporary drawing of a projectile
		batch.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.begin();
		for (Enemy enemy : theController.level1.enemiesOnStage) {
			if (enemy.isHurt()) {
				if (enemy.toughness != Toughness.ANGRY_GUY) {
					if (enemy.time == 4 || enemy.time == 24) {
						enemy.getSprite().setColor(
								enemy.getSprite().getColor().r,
								enemy.getSprite().getColor().g - 1,
								enemy.getSprite().getColor().b - 1,
								enemy.getSprite().getColor().a);
					}
					if (enemy.time == 14 || enemy.time == 34) {
						enemy.getSprite().setColor(
								enemy.getSprite().getColor().r,
								enemy.getSprite().getColor().g + 1,
								enemy.getSprite().getColor().b + 1,
								enemy.getSprite().getColor().a);
					}
				} else {
					if (enemy.time == 4 || enemy.time == 24) {
						enemy.getSprite().setColor(
								enemy.getSprite().getColor().r - Toughness.ANGRY_GUY.red,
								enemy.getSprite().getColor().g - Toughness.ANGRY_GUY.green,
								enemy.getSprite().getColor().b - Toughness.ANGRY_GUY.blue,
								enemy.getSprite().getColor().a);
					}
					if (enemy.time == 14 || enemy.time == 34) {
						enemy.getSprite().setColor(
								enemy.getSprite().getColor().r + Toughness.ANGRY_GUY.red,
								enemy.getSprite().getColor().g + Toughness.ANGRY_GUY.green,
								enemy.getSprite().getColor().b + Toughness.ANGRY_GUY.blue,
								enemy.getSprite().getColor().a);
					}
				}
			}
		}

		for (Item item : theController.level1.items) {
			if (item.sprite != null) {
				item.getSprite().setPosition(item.getPosition().x,
						item.getPosition().y);

				batch.draw(item.sprite, item.getPosition().x,
					item.getPosition().y, item.sprite.getWidth() / 2,
					item.sprite.getHeight() / 2);
			}
		}
		
		for (Enemy enemy : theController.level1.enemiesOnStage){
			if(enemy.getPosition().y+42 > theController.level1.player.getPosition().y+42){
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
		
			theController.level1.player.getSprite().setPosition(theController.level1.player.getPosition().x, theController.level1.player.getPosition().y);
			theController.level1.player.getSprite().draw(batch);
//			batch.draw(theController.level1.player.getSprite(), theController.level1.player.getPosition().x, theController.level1.player.getPosition().y,
//					theController.level1.player.getSprite().getWidth(), theController.level1.player.getSprite().getHeight());
//			theController.level1.drawEnemy(batch);
			

		for(Enemy enemy : theController.level1.enemiesOnStage){
			if(enemy.getPosition().y+42 < theController.level1.player.getPosition().y+42){
				enemy.getSprite().setPosition(enemy.getPosition().x, enemy.getPosition().y);
				enemy.getSprite().draw(batch);
			}
		}
		
		for(Projectile p: theController.level1.player.projectiles){
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
		for(Projectile p: theController.level1.player.projectiles){
			if(p!=null){
				sr.circle(p.getCircle().x, p.getCircle().y, p.getCircle().radius);
			}
		}
		
		sr.rect(theController.point.x, theController.point.y, 32, 32);
		sr.rect(theController.level1.player.getPosition().x, theController.level1.player.getPosition().y,
				theController.level1.player.getRectanlge().width, theController.level1.player.getRectanlge().height);
		sr.circle(theController.level1.player.invalidSpawnArea.x, theController.level1.player.invalidSpawnArea.y, theController.level1.player.invalidSpawnArea.radius);
		for(Enemy enemy:theController.level1.enemiesOnStage){
			sr.rect(enemy.getRectanlge().x, enemy.getRectanlge().y,
					enemy.getRectanlge().width, enemy.getRectanlge().height);
		}
		sr.setColor(Color.WHITE);
		if(theController.level1.player.getState() == State.GUNMOVEMENT){
			sr.line(theController.V3playerPos, theController.level1.player.shotDir);
		}
		sr.circle(theController.level1.player.getCircle().x, theController.level1.player.getCircle().y, theController.level1.player.getCircle().radius);
		sr.setColor(Color.YELLOW);
		sr.circle(theController.level1.player.aimingArea.x, theController.level1.player.aimingArea.y, theController.level1.player.aimingArea.radius);
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
		
		if (theController.level1.player.radioactiveAura != null) {
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			sr.setProjectionMatrix(cam.combined);
			sr.begin(ShapeType.Filled);
			sr.setColor(new Color(1f, 0, 0.07f, 0.5f));
			sr.circle(theController.level1.player.radioactiveAura.x,
					theController.level1.player.radioactiveAura.y,
					theController.level1.player.radioactiveAura.radius);
			sr.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
		} 
		stage.draw();
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
		stage.setViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_GUI_HEIGHT, true);
		cam.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) *	width;
		cam.update();
	}
	public void dispose(){
		batch.dispose();
		sr.dispose();
		stage.dispose();
	}
	public OrthographicCamera getCam() {
		return cam;
	}
	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
	}
	
}
