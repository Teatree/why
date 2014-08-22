package com.me.swampmonster.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.swampmonster.AI.Node;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.Prop;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Constants;

public class L1Renderer {
	private OrthographicCamera cam;
	private TheController theController;
	
	// Temporary debug feature
	private ShapeRenderer sr;
	// Temporary debug feature
	
	private SpriteBatch batch;
//	private ParticleEffect effect;
	private OrthogonalTiledMapRenderer mapRenderer;
	private int timer;
	
	private Stage stage;
	private GShape gshape;
	
	private int[] background = {0};
	private int[] foreground = {1};
	private int[] forenahalfground = {2};
	private int[] fiveground = {3};
	
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
		mapRenderer = new OrthogonalTiledMapRenderer(TheController.level1.bunker.getMap());
		
		gshape = new GShape(theController);
		stage.addActor(gshape);
		
		timer = 60;
		
//		effect = new ParticleEffect();
//		effect.load(Gdx.files.local("effects/FlameEffectTemp.p"), Gdx.files.local("effects"));
//		effect.setPosition(TheController.level1.player.position.x, TheController.level1.player.position.y);
//		effect.start();
//		
	}	
	
	public void render() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		theController.cameraHelper.applyTo(cam);
		
		AnimatedTiledMapTile.updateAnimationBaseTime();
		
		stage.act();
		
//		cam.unproject(theController.touchPos);
//		// System.out.println("MY X IS: " + theController.touchPos.x + " MY Y IS: " + theController.touchPos.y + " AND MY Z IS: " + theController.touchPos.z); 
		
		
		batch.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		
		mapRenderer.setView(cam);
		mapRenderer.render(background);
		mapRenderer.render(foreground);
		mapRenderer.render(forenahalfground);
		
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.begin();
//		if(theController.explosion.explosionEffect != null){
//			theController.explosion.explosionEffect.draw(batch);
//			theController.explosion.explosionEffect.update(Gdx.graphics.getDeltaTime());
//		}
//		
//		for (Explosion e : L1.explosions){
//			if(e.explosionEffect!= null){
//				e.explosionEffect.draw(batch);
//				e.explosionEffect.update(Gdx.graphics.getDeltaTime());
//			}
//		}
		if (Gdx.input.isTouched()
				&& L1.player.state == State.GUNMOVEMENT
				&& theController.gui.getCroshair().isAiming()) {
			batch.draw(theController.gui.getCroshair().getSprite(),
					theController.gui.getCroshair().position.x,
					theController.gui.getCroshair().position.y,
					theController.gui.getCroshair().getSprite().getOriginX(),
					theController.gui.getCroshair().getSprite().getOriginY(),
					theController.gui.getCroshair().getSprite().getWidth(),
					theController.gui.getCroshair().getSprite().getHeight(),
					1,1,
					theController.gui.getCroshair().getSprite()
							.getRotation());
			batch.draw(L1.player.bow,
					L1.player.bow.getX(),
					L1.player.bow.getY(),
					L1.player.bow.getOriginX(),
					L1.player.bow.getOriginY(),
					L1.player.bow.getWidth(),
					L1.player.bow.getHeight(), 1, 1,
					L1.player.bow.getRotation());
		}
		// temporary drawing of a projectile
		
		for (Enemy enemy : L1.enemiesOnStage) {
			if (enemy.hurt) {
				if (enemy.toughness == null) {
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
								enemy.getSprite().getColor().r - enemy.toughness.red + 1,
								enemy.getSprite().getColor().g - enemy.toughness.green,
								enemy.getSprite().getColor().b -enemy.toughness.blue,
								enemy.getSprite().getColor().a);
					}
					if (enemy.time == 14 || enemy.time == 34) {
						enemy.getSprite().setColor(
								enemy.getSprite().getColor().r + enemy.toughness.red - 1,
								enemy.getSprite().getColor().g + enemy.toughness.green,
								enemy.getSprite().getColor().b + enemy.toughness.blue,
								enemy.getSprite().getColor().a);
					}
				}
			}

			for (Projectile p : enemy.enemyProjectiles) {
				if (p != null) {
					if (enemy.toughness != null) {
						batch.setColor(new Color(enemy.toughness.red,
								enemy.toughness.green, enemy.toughness.blue,
								enemy.toughness.alpha));
					}
					batch.draw(p.getSprite(), p.getPosition().x, p
							.getPosition().y, p.getSprite().getOriginX(), p
							.getSprite().getOriginY(),
							p.getSprite().getWidth(),
							p.getSprite().getHeight(), 1, 1, p.getSprite()
									.getRotation());
					batch.setColor(new Color(1,1,1,1));
				}
			}
		}
		
		if (L1.player.trap != null && L1.player.trap.position != null){
			
			L1.player.trap.trapSprite.setPosition(L1.player.trap.position.x,
					L1.player.trap.position.y);
			
			batch.draw(L1.player.trap.trapSprite, L1.player.trap.position.x,
					L1.player.trap.position.y, L1.player.trap.trapSprite.getWidth(),
					L1.player.trap.trapSprite.getHeight());
		}
		

		for (Prop p : L1.props) {
			if (p.sprite != null) {
				p.getSprite().setPosition(p.getPosition().x,
						p.getPosition().y);

				batch.draw(p.sprite, p.getPosition().x,
					p.getPosition().y, p.sprite.getWidth(),
					p.sprite.getHeight());
			}
		}
		
		for (Item item : L1.items) {
			if (item.sprite != null) {
				item.getSprite().setPosition(item.getPosition().x,
						item.getPosition().y);

				batch.draw(item.sprite, item.getPosition().x,
					item.getPosition().y, item.sprite.getWidth() / 2,
					item.sprite.getHeight() / 2);
			}
		}
		
		
		for (Enemy enemy : L1.enemiesOnStage){
			if(enemy.getPosition().y+42 > L1.player.getPosition().y+42){
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
		
			L1.player.getSprite().setPosition(L1.player.getPosition().x, L1.player.getPosition().y);
			L1.player.getSprite().draw(batch);
//			batch.draw(TheController.level1.player.getSprite(), TheController.level1.player.getPosition().x, TheController.level1.player.getPosition().y,
//					TheController.level1.player.getSprite().getWidth(), TheController.level1.player.getSprite().getHeight());
//			TheController.level1.drawEnemy(batch);
			
			if (Gdx.input.isTouched()
					&& L1.player.state == State.GUNMOVEMENT
					&& theController.gui.getCroshair().isAiming()
					&& L1.player.bow.getRotation() > 0
					&& L1.player.bow.getRotation() < 180) {
				
				batch.draw(L1.player.bow,
						L1.player.bow.getX(),
						L1.player.bow.getY(),
						L1.player.bow.getOriginX(),
						L1.player.bow.getOriginY(),
						L1.player.bow.getWidth(),
						L1.player.bow.getHeight(), 1, 1,
						L1.player.bow.getRotation());
			}
			
		for(Enemy enemy : L1.enemiesOnStage){
			if(enemy.getPosition().y+42 < L1.player.getPosition().y+42){
				enemy.getSprite().setPosition(enemy.getPosition().x, enemy.getPosition().y);
				enemy.getSprite().draw(batch);
			}
		}
		
		for(Projectile p: L1.player.projectiles){
			if(p != null){
				batch.draw(p.getSprite(), p.getPosition().x, p.getPosition().y, 
						p.getSprite().getOriginX(), p.getSprite().getOriginY(),
						p.getSprite().getWidth(), p.getSprite().getHeight(), 
						1,1,
						p.getSprite().getRotation());
			}
		}
		
		if (L1.player.trap != null && L1.player.trap.showEffect){
			L1.player.trap.effect.draw(batch);
			L1.player.trap.effect.update(Gdx.graphics.getDeltaTime());
		}
//		effect.draw(batch);
//		effect.update(Gdx.graphics.getDeltaTime());
		
		batch.end();
		
		// Temporary deBug feature
		sr.begin(ShapeType.Line);
//		for (Enemy enemy : L1.enemiesOnStage){
//			sr.setColor(Color.GREEN);
//			sr.circle(enemy.getgReenAura().x, enemy.getgReenAura().y, enemy.getgReenAura().radius);
//			sr.setColor(Color.BLUE);
//			sr.circle(enemy.getoRangeAura().x, enemy.getoRangeAura().y, enemy.getoRangeAura().radius);
//			sr.setColor(Color.YELLOW);
//			sr.circle(enemy.yellowAura.x, enemy.yellowAura.y, enemy.yellowAura.radius);
//			sr.setColor(Color.BLACK);
//			sr.circle(theController.explosion.position.x, theController.explosion.position.y, theController.explosion.explCircle.radius);
//			sr.circle(enemy.aimingAura.x, enemy.aimingAura.y, enemy.aimingAura.radius);
//		}	
		
		for(Projectile p: L1.player.projectiles){
			if(p != null){
				sr.rect(p.position.x+p.sprite.getWidth()/2, p.position.y+p.sprite.getHeight()/2, 3, 3, 1, 1, p.sprite.getRotation());
				sr.setColor(Color.RED);
				sr.rect(p.position.x-p.sprite.getRotation(), p.position.y-p.sprite.getRotation(), 2, 2, 1, 1, p.sprite.getRotation());
			}
		}
		sr.setColor(Color.WHITE);
		for(Projectile p: L1.player.projectiles){
			if(p!=null){
				sr.circle(p.circle.x, p.circle.y, p.circle.radius);
			}
		}
		
		sr.rect(theController.point.x, theController.point.y, 32, 32);
		sr.rect(L1.player.rectanlge.x, L1.player.rectanlge.y,
				L1.player.rectanlge.width, L1.player.rectanlge.height);
//		sr.circle(TheController.level1.player.invalidSpawnArea.x, TheController.level1.player.invalidSpawnArea.y, TheController.level1.player.invalidSpawnArea.radius);
		for(Enemy enemy : L1.enemiesOnStage){
			sr.rect(enemy.rectanlge.x, enemy.rectanlge.y, enemy.rectanlge.width, enemy.rectanlge.height);
		}
		for (Prop p : L1.props){
			sr.rect(p.sprite.getBoundingRectangle().x, p.sprite.getBoundingRectangle().y, p.sprite.getBoundingRectangle().width, p.sprite.getBoundingRectangle().height);
		}
		sr.setColor(Color.WHITE);
		if(L1.player.state == State.GUNMOVEMENT){
			sr.line(theController.V3playerPos, L1.player.shotDir);
		}
		if(theController.gui.getCroshair().isAiming()){
			sr.line(theController.V3playerPos, L1.player.aimLineHead);
		}
		sr.circle(L1.player.circle.x, L1.player.circle.y, L1.player.circle.radius);
		sr.setColor(Color.YELLOW);
//		sr.circle(TheController.level1.player.aimingArea.x, TheController.level1.player.aimingArea.y, TheController.level1.player.aimingArea.radius);
		sr.end();
		
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.begin(ShapeType.Filled);
		sr.setColor(1f, 0, 0.2f, 0.4f);
		for (Explosion e : L1.explosions){
			if (!e.type.equals(Explosion.EXPLOSION_TYPE_INVERTED))
			sr.circle(e.position.x, e.position.y, e.explCircle.radius);
		}
		sr.setColor(Color.RED);
		for (Enemy enemy : L1.enemiesOnStage){
			if(enemy.getPath() != null){
				for(Node n : enemy.getPath()){
					if(n != null){
						sr.rect((n.x*Constants.NodeSize), (n.y*Constants.NodeSize), 4, 4);
					}
				}
			}
		}
		sr.setColor(Color.BLACK);
		sr.rect(theController.pointRectV3.x, theController.pointRectV3.y, 1, 1);
		if (L1.player.radioactiveAura != null) {
			sr.setColor(new Color(1f, 0, 0.07f, 0.5f));
			sr.circle(L1.player.radioactiveAura.x,
					L1.player.radioactiveAura.y,
					L1.player.radioactiveAura.radius);
		} 
		sr.end();
		mapRenderer.render(fiveground);
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
