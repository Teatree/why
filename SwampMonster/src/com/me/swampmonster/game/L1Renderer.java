package com.me.swampmonster.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Projectile;
import com.me.swampmonster.models.ProjectileHydra;
import com.me.swampmonster.models.ProjectileHydra.hydraTrailAnimation;
import com.me.swampmonster.models.Prop;
import com.me.swampmonster.models.TutorialLevel;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.enemies.PossessedTurret;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class L1Renderer {
	private static OrthographicCamera cam;
	private ScreenViewport viewport;
	private TheController theController;

	// Temporary debug feature
	private ShapeRenderer sr;
	private BitmapFont fontss;

	private SpriteBatch batch;
	// private ParticleEffect effect;
	private OrthogonalTiledMapRenderer mapRenderer;
	private int timer;

	public static Stage stage;
	private Array<Viewport> viewports;
	Viewport viewport1;
	private Array<String> names;
	private GShape gshape;

	private int[] background = { 0 };
	private int[] foreground = { 1 };
	private int[] forenahalfground = { 2 };
	private int[] fiveground = { 3 };

	public L1Renderer(TheController theController) {
		this.theController = theController;
		this.cam = new OrthographicCamera(800, 480);
		viewport = new ScreenViewport();
		viewport.setScreenWidth((int) Constants.VIEWPORT_WIDTH);
		viewport.setScreenHeight((int) Constants.VIEWPORT_HEIGHT);
		fontss = Assets.manager.get(Assets.font1);
		fontss.setScale(0.002f);
		// stage.setViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_WIDTH,
		// true);
		// Temporary debug feature
		// Pathfinder.setTiledMap(level1.bunker.getMap());
		// temporary bedug feature
		batch = new SpriteBatch();
		stage = new Stage(viewport, batch);
		sr = new ShapeRenderer();
		// System.out.println(TheController.level1);
		mapRenderer = new OrthogonalTiledMapRenderer(
				TheController.level1.bunker.getMap());

		viewports = getViewports(stage.getCamera());
		names = getViewportNames();

		stage.setViewport(viewports.first());

		gshape = new GShape(theController);
		stage.addActor(gshape);
		Gdx.input.setInputProcessor(stage);
		
		timer = 60;

		// effect = new ParticleEffect();
		// effect.load(Gdx.files.local("effects/FlameEffectTemp.p"),
		// Gdx.files.local("effects"));
		// effect.setPosition(TheController.level1.player.position.x,
		// TheController.level1.player.position.y);
		// effect.start();
		//
	}

	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		theController.cameraHelper.applyTo(cam);

		AnimatedTiledMapTile.updateAnimationBaseTime();

		// cam.unproject(theController.touchPos);
		// // System.out.println("MY X IS: " + theController.touchPos.x +
		// " MY Y IS: " + theController.touchPos.y + " AND MY Z IS: " +
		// theController.touchPos.z);

		batch.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);

		mapRenderer.setView(cam);
		mapRenderer.render(background);
		mapRenderer.render(foreground);
		mapRenderer.render(forenahalfground);

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.begin();
		// if(theController.explosion.explosionEffect != null){
		// theController.explosion.explosionEffect.draw(batch);
		// theController.explosion.explosionEffect.update(Gdx.graphics.getDeltaTime());
		// }
		//
		// for (Explosion e : L1.explosions){
		// if(e.explosionEffect!= null){
		// e.explosionEffect.draw(batch);
		// e.explosionEffect.update(Gdx.graphics.getDeltaTime());
		// }
		// }
		if (Gdx.input.isTouched() && L1.player.state == State.GUNMOVEMENT
				&& TheController.gui.getCroshair().isAiming()) {
			batch.draw(TheController.gui.getCroshair().getSprite(),
					TheController.gui.getCroshair().position.x,
					TheController.gui.getCroshair().position.y,
					TheController.gui.getCroshair().getSprite().getOriginX(),
					TheController.gui.getCroshair().getSprite().getOriginY(),
					TheController.gui.getCroshair().getSprite().getWidth(),
					TheController.gui.getCroshair().getSprite().getHeight(), 1,
					1, TheController.gui.getCroshair().getSprite()
							.getRotation());
			batch.draw(L1.player.bow, L1.player.bow.getX(),
					L1.player.bow.getY(), L1.player.bow.getOriginX(),
					L1.player.bow.getOriginY(), L1.player.bow.getWidth(),
					L1.player.bow.getHeight(), 1, 1,
					L1.player.bow.getRotation());
		}
		// temporary drawing of a projectile
		

		for (Enemy enemy : L1.enemiesOnStage) {
//			fontss.setColor(Color.GREEN);
//			fontss.setScale(0.52f);
//			 fontss.draw(batch, "dmg: " + enemy.damage,
//			 enemy.position.x+enemy.sprite.getWidth()+5, enemy.position.y);
//			 fontss.draw(batch, "hp: " + enemy.health,
//			 enemy.position.x+enemy.sprite.getWidth()+5, enemy.position.y+12);
//			 fontss.draw(batch, "sp: " + enemy.movementSpeed,
//			 enemy.position.x+enemy.sprite.getWidth()+5, enemy.position.y+24);
//			 fontss.draw(batch, "size: " + enemy.sprite.getScaleX(),
//			 enemy.position.x+enemy.sprite.getWidth()+5, enemy.position.y+36);
			if (enemy.heal) {
				fontss.setColor(Color.GREEN);
				fontss.setScale(0.52f);
				fontss.draw(batch, 10
						+ " +HP", enemy.position.x, enemy.position.y
						+ enemy.sprite.getHeight() + 5 + enemy.healCounter / 5);
			}
			if (enemy.hurt) {
				if (enemy.negativeEffectsState == NegativeEffects.POISONED) {
					fontss.setColor(Color.MAGENTA);
					fontss.setScale(0.52f);
					fontss.draw(batch, enemy.poisonDamage * 10 + " DMG",
							enemy.position.x,
							enemy.position.y + enemy.sprite.getHeight() + 5
									+ enemy.time / 5);
				} else {
					fontss.setColor(Color.RED);
					fontss.setScale(0.52f);
					fontss.draw(batch, (int) enemy.floatingOutputDamage
							+ " DMG", enemy.position.x, enemy.position.y
							+ enemy.sprite.getHeight() + 5 + enemy.time / 5);
				}
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
								enemy.getSprite().getColor().r
										- enemy.toughness.red + 1,
								enemy.getSprite().getColor().g
										- enemy.toughness.green,
								enemy.getSprite().getColor().b
										- enemy.toughness.blue,
								enemy.getSprite().getColor().a);
					}
					if (enemy.time == 14 || enemy.time == 34) {
						enemy.getSprite().setColor(
								enemy.getSprite().getColor().r
										+ enemy.toughness.red - 1,
								enemy.getSprite().getColor().g
										+ enemy.toughness.green,
								enemy.getSprite().getColor().b
										+ enemy.toughness.blue,
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
					batch.setColor(new Color(1, 1, 1, 1));
				}
			}
		}

		if (L1.player.trap != null && L1.player.trap.position != null) {

			L1.player.trap.trapSprite.setPosition(L1.player.trap.position.x,
					L1.player.trap.position.y);

			batch.draw(L1.player.trap.trapSprite, L1.player.trap.position.x,
					L1.player.trap.position.y,
					L1.player.trap.trapSprite.getWidth(),
					L1.player.trap.trapSprite.getHeight());
		}

		for (Prop p : L1.props) {
			if (p.sprite != null) {
				p.getSprite().setPosition(p.getPosition().x, p.getPosition().y);

				batch.draw(p.sprite, p.getPosition().x, p.getPosition().y,
						p.sprite.getWidth(), p.sprite.getHeight());
			}
		}

		for (Item item : L1.items) {
			if (item.sprite != null) {
				item.getSprite().setPosition(item.getPosition().x,
						item.getPosition().y);

				batch.draw(item.sprite, item.getPosition().x,
						item.getPosition().y, item.sprite.getWidth() / 2,
						item.sprite.getHeight() / 2);
				// if(item.pickUpButton != null){
				// item.pickUpButton.draw(batch, 1);
				// }
				if (item.pickUpButton != null) {
					// item.pickUpButton.setX(L1Renderer.getCam().unproject(
					// new
					// Vector3(L1Renderer.stage.screenToStageCoordinates(item.position).x,
					// L1Renderer.stage
					// .screenToStageCoordinates(item.position).y, 0)).x);
					// item.pickUpButton.setY(L1Renderer.getCam().unproject(
					// new
					// Vector3(L1Renderer.stage.screenToStageCoordinates(item.position).x,
					// L1Renderer.stage
					// .screenToStageCoordinates(item.position).y, 0)).y+32);

				}
			}
		}

		for (Enemy enemy : L1.enemiesOnStage) {
			if (enemy.getPosition().y + 42 > L1.player.getPosition().y + 42) {
				enemy.getSprite().setPosition(enemy.getPosition().x,
						enemy.getPosition().y);
				if (enemy.timeRemove < 110) {
					enemy.getSprite().draw(batch);
				}
				if (enemy.timeRemove > 110 && enemy.timeRemove < 115) {
					enemy.getSprite().draw(batch);
				}
				if (enemy.timeRemove > 120 && enemy.timeRemove < 125) {
					enemy.getSprite().draw(batch);
				}
				if (enemy.timeRemove > 130 && enemy.timeRemove < 135) {
					enemy.getSprite().draw(batch);
				}
				if (enemy.timeRemove > 140 && enemy.timeRemove < 145) {
					enemy.getSprite().draw(batch);
				}
				if (enemy.timeRemove > 150 && enemy.timeRemove < 155) {
					enemy.getSprite().draw(batch);
				}
				if (enemy.timeRemove > 160 && enemy.timeRemove < 165) {
					enemy.getSprite().draw(batch);
				}
				if (enemy.timeRemove > 170 && enemy.timeRemove < 175) {
					enemy.getSprite().draw(batch);
				}
			}
		}

		if (L1.player.turret != null && L1.player.turret.position != null) {
			if (L1.player.turret.hurt) {
				System.out
						.println("I really should be flickering red right now!");
				if (L1.player.turret.time == 4 || L1.player.turret.time == 24) {
					L1.player.turret.sprite.setColor(

					L1.player.turret.sprite.getColor().r,
							L1.player.turret.sprite.getColor().g - 1,
							L1.player.turret.sprite.getColor().b - 1,
							L1.player.turret.sprite.getColor().a);
				}
				if (L1.player.turret.time == 14 || L1.player.turret.time == 34) {
					L1.player.turret.sprite.setColor(
							L1.player.turret.sprite.getColor().r,
							L1.player.turret.sprite.getColor().g + 1,
							L1.player.turret.sprite.getColor().b + 1,
							L1.player.turret.sprite.getColor().a);
				}
			}
			// batch.draw(L1.player.turret.sprite, L1.player.turret.position.x,
			// L1.player.turret.position.y);
			if (L1.player.turret.projectiles != null
					&& !L1.player.turret.projectiles.isEmpty()) {
				for (Projectile p : L1.player.turret.projectiles) {
					batch.draw(p.sprite, p.position.x, p.position.y);
				}
			}
			if (L1.player.turret.timeRemove < 110) {
				batch.draw(L1.player.turret.sprite,
						L1.player.turret.position.x,
						L1.player.turret.position.y);
			}
			if (L1.player.turret.timeRemove > 110
					&& L1.player.turret.timeRemove < 115) {
				batch.draw(L1.player.turret.sprite,
						L1.player.turret.position.x,
						L1.player.turret.position.y);
			}
			if (L1.player.turret.timeRemove > 120
					&& L1.player.turret.timeRemove < 125) {
				batch.draw(L1.player.turret.sprite,
						L1.player.turret.position.x,
						L1.player.turret.position.y);
			}
			if (L1.player.turret.timeRemove > 130
					&& L1.player.turret.timeRemove < 135) {
				batch.draw(L1.player.turret.sprite,
						L1.player.turret.position.x,
						L1.player.turret.position.y);
			}
			if (L1.player.turret.timeRemove > 140
					&& L1.player.turret.timeRemove < 145) {
				batch.draw(L1.player.turret.sprite,
						L1.player.turret.position.x,
						L1.player.turret.position.y);
			}
			if (L1.player.turret.timeRemove > 150
					&& L1.player.turret.timeRemove < 155) {
				batch.draw(L1.player.turret.sprite,
						L1.player.turret.position.x,
						L1.player.turret.position.y);
			}
			if (L1.player.turret.timeRemove > 160
					&& L1.player.turret.timeRemove < 165) {
				batch.draw(L1.player.turret.sprite,
						L1.player.turret.position.x,
						L1.player.turret.position.y);
			}
			if (L1.player.turret.timeRemove > 170
					&& L1.player.turret.timeRemove < 175) {
				batch.draw(L1.player.turret.sprite,
						L1.player.turret.position.x,
						L1.player.turret.position.y);
			}
		}
		
	//bad turret	
		for (PossessedTurret possessedTurret : L1.pTurrets) {
			if (possessedTurret != null && possessedTurret.position != null) {
				if (possessedTurret.hurt) {
					System.out
							.println("I really should be flickering red right now!");
					if (possessedTurret.time == 4 || possessedTurret.time == 24) {
						possessedTurret.sprite.setColor(

						possessedTurret.sprite.getColor().r,
								possessedTurret.sprite.getColor().g - 1,
								possessedTurret.sprite.getColor().b - 1,
								possessedTurret.sprite.getColor().a);
					}
					if (possessedTurret.time == 14
							|| possessedTurret.time == 34) {
						possessedTurret.sprite.setColor(
								possessedTurret.sprite.getColor().r,
								possessedTurret.sprite.getColor().g + 1,
								possessedTurret.sprite.getColor().b + 1,
								possessedTurret.sprite.getColor().a);
					}
				}
				// batch.draw(L1.player.turret.sprite,
				// L1.player.turret.position.x,
				// L1.player.turret.position.y);
				if (possessedTurret.projectiles != null
						&& !possessedTurret.projectiles.isEmpty()) {
					for (Projectile p : possessedTurret.projectiles) {
						batch.draw(p.sprite, p.position.x, p.position.y);
					}
				}
				if (possessedTurret.timeRemove < 110) {
					batch.draw(possessedTurret.sprite,
							possessedTurret.position.x,
							possessedTurret.position.y);
				}
				if (possessedTurret.timeRemove > 110
						&& possessedTurret.timeRemove < 115) {
					batch.draw(possessedTurret.sprite,
							possessedTurret.position.x,
							possessedTurret.position.y);
				}
				if (possessedTurret.timeRemove > 120
						&& possessedTurret.timeRemove < 125) {
					batch.draw(possessedTurret.sprite,
							possessedTurret.position.x,
							possessedTurret.position.y);
				}
				if (possessedTurret.timeRemove > 130
						&& possessedTurret.timeRemove < 135) {
					batch.draw(possessedTurret.sprite,
							possessedTurret.position.x,
							possessedTurret.position.y);
				}
				if (possessedTurret.timeRemove > 140
						&& possessedTurret.timeRemove < 145) {
					batch.draw(possessedTurret.sprite,
							possessedTurret.position.x,
							possessedTurret.position.y);
				}
				if (possessedTurret.timeRemove > 150
						&& possessedTurret.timeRemove < 155) {
					batch.draw(possessedTurret.sprite,
							possessedTurret.position.x,
							possessedTurret.position.y);
				}
				if (possessedTurret.timeRemove > 160
						&& possessedTurret.timeRemove < 165) {
					batch.draw(possessedTurret.sprite,
							possessedTurret.position.x,
							possessedTurret.position.y);
				}
				if (possessedTurret.timeRemove > 170
						&& possessedTurret.timeRemove < 175) {
					batch.draw(possessedTurret.sprite,
							possessedTurret.position.x,
							possessedTurret.position.y);
				}
			}
		}
	
		
		
		L1.player.getSprite().setPosition(L1.player.getPosition().x,
				L1.player.getPosition().y);
		L1.player.getSprite().draw(batch);
		if (Player.shootingSwitch) {
			L1.player.aimingAuraSprite.draw(batch);
		}
		// batch.draw(TheController.level1.player.getSprite(),
		// TheController.level1.player.getPosition().x,
		// TheController.level1.player.getPosition().y,
		// TheController.level1.player.getSprite().getWidth(),
		// TheController.level1.player.getSprite().getHeight());
		// TheController.level1.drawEnemy(batch);

		if (Gdx.input.isTouched() && L1.player.state == State.GUNMOVEMENT
				&& TheController.gui.getCroshair().isAiming()
				&& L1.player.bow.getRotation() > 0
				&& L1.player.bow.getRotation() < 180) {

			batch.draw(L1.player.bow, L1.player.bow.getX(),
					L1.player.bow.getY(), L1.player.bow.getOriginX(),
					L1.player.bow.getOriginY(), L1.player.bow.getWidth(),
					L1.player.bow.getHeight(), 1, 1,
					L1.player.bow.getRotation());
		}

		for (Enemy enemy : L1.enemiesOnStage) {
			if (enemy.getPosition().y + 42 < L1.player.getPosition().y + 42) {
				enemy.sprite.setPosition(enemy.getPosition().x,
						enemy.getPosition().y);
				enemy.sprite.draw(batch);
			}
		}
		for (Enemy enemy : L1.enemiesOnStage) {
			if (enemy.negativeEffectsState == NegativeEffects.ICE
					&& enemy.state != State.DEAD) {
				enemy.iceCube.draw(batch);
			}
			
			if (enemy.negativeEffectsState == NegativeEffects.STUN
					&& enemy.state != State.DEAD){ 
				batch.draw(enemy.effectCarrier, enemy.position.x + 5,
						enemy.position.y + enemy.sprite.getHeight() - 6, 25, 25);
			}
			
		}		

		for (Projectile p : L1.player.weapon.projectiles) {
			if (p != null) {
				batch.draw(p.getSprite(), p.getPosition().x, p.getPosition().y,
						p.getSprite().getOriginX(), p.getSprite().getOriginY(),
						p.getSprite().getWidth(), p.getSprite().getHeight(), 1,
						1, p.getSprite().getRotation());
			}
		}

		if (L1.player.trap != null && L1.player.trap.showEffect) {
			L1.player.trap.effect.draw(batch);
			L1.player.trap.effect.update(Gdx.graphics.getDeltaTime());
		}
		// effect.draw(batch);
		// effect.update(Gdx.graphics.getDeltaTime());
		if (TutorialLevel.movehere != null) {
			batch.draw(TutorialLevel.movehere, TutorialLevel.movehere.getX(),
					TutorialLevel.movehere.getY());
		}
		
		batch.end();

		// Temporary deBug feature
		sr.begin(ShapeType.Line);
		if (L1.plasmaShield != null) {
			sr.circle(L1.plasmaShield.circle.x, L1.plasmaShield.circle.y,
					L1.plasmaShield.circle.radius);
		}
		if(!Player.shootingSwitch){
			sr.arc(L1.player.aimingArea.x,
					L1.player.aimingArea.y,
					L1.player.aimingArea.radius, 90,
					L1.player.weapon.coolDownAngle);
		}
		for (PossessedTurret possessedTurret : L1.pTurrets) {
			sr.setColor(Color.GRAY);
			sr.circle(possessedTurret.circle.x, possessedTurret.circle.y, possessedTurret.circle.radius);
			
		}
		
		for (Enemy enemy : L1.enemiesOnStage) {
			// sr.setColor(Color.GREEN);
			// sr.circle(enemy.getgReenAura().x, enemy.getgReenAura().y,
			// enemy.getgReenAura().radius);
			// sr.setColor(Color.BLUE);
			// sr.circle(enemy.getoRangeAura().x, enemy.getoRangeAura().y,
			// enemy.getoRangeAura().radius);

			// :TODO bring me back
			 sr.setColor(Color.YELLOW);
//			 sr.circle(enemy.yellowAura.x, enemy.yellowAura.y,
//			 enemy.yellowAura.radius);
			 sr.setColor(Color.ORANGE);
//			 sr.circle(enemy.oRangeAura.x, enemy.oRangeAura.y,
//			 enemy.oRangeAura.radius);

			// sr.setColor(Color.BLACK);
			// sr.circle(theController.explosion.position.x,
			// theController.explosion.position.y,
			// theController.explosion.explCircle.radius);
			// sr.circle(enemy.aimingAura.x, enemy.aimingAura.y,
			// enemy.aimingAura.radius);
		}

		// if (L1.player.turret != null) {
		// sr.rect(L1.player.turret.turretAimerBot.x,
		// L1.player.turret.turretAimerBot.y,
		// L1.player.turret.turretAimerBot.width,
		// L1.player.turret.turretAimerBot.height);
		// sr.circle(L1.player.turret.circle.x, L1.player.turret.circle.y,
		// L1.player.turret.circle.radius);
		// }
		//
		// sr.setColor(Color.WHITE);
		// for (Projectile p : L1.player.weapon.projectiles) {
		// if (p != null) {
		// sr.circle(p.circle.x, p.circle.y, p.circle.radius);
		// }
		// }
		//
		// for (Item item : L1.items) {
		// if (item.sprite != null) {
		// sr.circle(item.circle.x, item.circle.y, item.circle.radius);
		// }
		//
		// }
		// sr.rect(theController.point.x, theController.point.y, 32, 32);
		// sr.rect(L1.player.rectanlge.x, L1.player.rectanlge.y,
		// L1.player.rectanlge.width, L1.player.rectanlge.height);
		// // sr.circle(TheController.level1.player.invalidSpawnArea.x,
		// // TheController.level1.player.invalidSpawnArea.y,
		// // TheController.level1.player.invalidSpawnArea.radius);
		// for (Enemy enemy : L1.enemiesOnStage) {
		// sr.setColor(Color.WHITE);
		// // sr.rect(enemy.rectanlge.x, enemy.rectanlge.y,
		// // enemy.rectanlge.width, enemy.rectanlge.height);
		// sr.setColor(Color.BLACK);
		// sr.rect(enemy.sprite.getBoundingRectangle().x,
		// enemy.sprite.getBoundingRectangle().y,
		// enemy.sprite.getBoundingRectangle().width,
		// enemy.sprite.getBoundingRectangle().height);
		// for(Projectile p: enemy.enemyProjectiles){
		// sr.circle(p.circle.x, p.circle.y, p.circle.radius);
		// }
		// }
		// for (Prop p : L1.props) {
		// sr.rect(p.sprite.getBoundingRectangle().x,
		// p.sprite.getBoundingRectangle().y,
		// p.sprite.getBoundingRectangle().width,
		// p.sprite.getBoundingRectangle().height);
		// }
		// sr.setColor(Color.WHITE);
		// if (L1.player.state == State.GUNMOVEMENT) {
		// sr.line(theController.V3playerPos, L1.player.shotDir);
		// }
		// if (TheController.gui.getCroshair().isAiming()) {
		// sr.line(theController.V3playerPos, L1.player.aimLineHead);
		// }
		sr.setColor(Color.RED);
//		 sr.circle(L1.player.circle.x, L1.player.circle.y,
//		 L1.player.circle.radius);
//		  sr.circle(TheController.level1.player.aimingArea.x,
//		  TheController.level1.player.aimingArea.y,
//		  TheController.level1.player.aimingArea.radius);
//		  sr.rect(L1.player.sprite.getBoundingRectangle().x, L1.player.sprite.getBoundingRectangle().y,
//				  L1.player.sprite.getBoundingRectangle().width, L1.player.sprite.getBoundingRectangle().height);
		sr.end();
		//
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.begin(ShapeType.Filled);
		sr.setColor(1f, 0, 0.2f, 0.4f);
		for (Explosion e : L1.explosions) {
			if (!e.type.equals(Explosion.EXPLOSION_TYPE_INVERTED))
				sr.circle(e.position.x, e.position.y, e.explCircle.radius);
		}
		for (Enemy enemy : L1.enemiesOnStage) {
			if (enemy.radioactiveAura != null) {
				sr.setColor(new Color(1f, 0, 0.07f, 0.5f));
				sr.circle(enemy.radioactiveAura.x, enemy.radioactiveAura.y,
						enemy.radioactiveAura.radius);
			}
			// if (enemy.getPath() != null) {
			// for (Node n : enemy.getPath()) {
			// if (n != null) {
			// sr.rect((n.x * Constants.NodeSize),
			// (n.y * Constants.NodeSize), 4, 4);
			// }
			// }
			// }
		}
		// sr.setColor(Color.BLACK);
		// sr.rect(theController.pointRectV3.x, theController.pointRectV3.y, 1,
		// 1);
		if (L1.player.radioactiveAura != null) {
			sr.setColor(new Color(1f, 0, 0.07f, 0.5f));
			sr.circle(L1.player.radioactiveAura.x, L1.player.radioactiveAura.y,
					L1.player.radioactiveAura.radius);
		}

		sr.end();

		mapRenderer.render(fiveground);
		batch.begin();
		if (L1.plasmaShield != null) {
			// L1.plasmaShield.sprite.draw(batch);
			batch.draw(L1.plasmaShield.sprite, L1.plasmaShield.sprite.getX(),
					L1.plasmaShield.sprite.getY(),
					L1.plasmaShield.sprite.getWidth(),
					L1.plasmaShield.sprite.getHeight());
		}
		if (L1.hydra != null) {
			for (ProjectileHydra p : L1.hydra.getProjectiles()) {
				if (p.state != State.DEAD) {
					batch.draw(p.sprite, p.sprite.getX(), p.sprite.getY(),
							p.sprite.getOriginX(), p.sprite.getOriginY(),
							p.sprite.getWidth(), p.sprite.getHeight(), 1, 1,
							p.sprite.getRotation());
					for (hydraTrailAnimation h : p.animsTrailList) {
						// System.out.println("s: " + s);
						// System.out.println(" s.getX(): " + s.getX());
						// System.out.println("s.getY(): " + s.getY());

						// :TODO FINISH THIS BULLSHIT!
						batch.draw(
								h.getCurrentSprite(),
								h.getCurrentSprite().getX()
										+ (p.sprite.getWidth() / 2 * Math
												.signum(p.sprite.getRotation())),
								h.getCurrentSprite().getY()
										+ (p.sprite.getHeight() / 2 * Math
												.signum(p.sprite.getRotation())));
					}
				}
			}
		}
		if (L1.player.negativeEffectsState == NegativeEffects.STUN) {
			batch.draw(L1.player.effectCarrier, L1.player.position.x + 5,
					L1.player.position.y + L1.player.sprite.getHeight() - 6,
					25, 25);
		}
		batch.end();
		stage.act();
		stage.draw();
		batch.begin();
		if(L1.player.bow != null && TutorialLevel.step == 8 || TutorialLevel.step == 9){
			batch.draw(L1.player.bow, L1.player.bow.getX(),
					L1.player.bow.getY(), L1.player.bow.getOriginX(),
					L1.player.bow.getOriginY(), L1.player.bow.getWidth()*2,
					L1.player.bow.getHeight()*2, 1, 1,
					L1.player.bow.getRotation());
		}
		batch.end();
	}

	public void warningFlicker(ShapeRenderer Sr) {
		if (timer >= 42) {
			Sr.setColor(Color.YELLOW);
			timer--;
		} else if (timer <= 42 && timer > 0) {
			Sr.setColor(Color.CYAN);
			timer--;
		} else if (timer <= 1) {
			timer = 60;
		}
	}

	static public Array<String> getViewportNames() {
		Array<String> names = new Array();
		names.add("FillViewport");
		names.add("StretchViewport");
		names.add("FitViewport");
		names.add("ExtendViewport: no max");
		names.add("ExtendViewport: max");
		names.add("ScreenViewport: 1:1");
		names.add("ScreenViewport: 0.75:1");
		names.add("ScalingViewport: none");
		return names;
	}

	static public Array<Viewport> getViewports(Camera camera) {
		int minWorldWidth = 800;
		int minWorldHeight = 480;
		// int minWorldWidth = 1280;
		// int minWorldHeight = 768;
		int maxWorldWidth = 640;
		int maxWorldHeight = 480;

		Array<Viewport> viewports = new Array();
		viewports.add(new FillViewport(minWorldWidth, minWorldHeight, camera));
		viewports
				.add(new StretchViewport(minWorldWidth, minWorldHeight, camera));
		viewports.add(new FitViewport(minWorldWidth, minWorldHeight, camera));
		viewports
				.add(new ExtendViewport(minWorldWidth, minWorldHeight, camera));
		viewports.add(new ExtendViewport(minWorldWidth, minWorldHeight,
				maxWorldWidth, maxWorldHeight, camera));
		viewports.add(new ScreenViewport(camera));

		ScreenViewport screenViewport = new ScreenViewport(camera);
		screenViewport.setUnitsPerPixel(0.75f);
		viewports.add(screenViewport);

		viewports.add(new ScalingViewport(Scaling.none, minWorldWidth,
				minWorldHeight, camera));
		return viewports;
	}

	// there are two of those;

	public void setSize(int width, int height) {
		// this.width = width;
		// this.height = height;
		stage.setViewport(viewport);
		cam.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		cam.update();
	}

	public void dispose() {
		batch.dispose();
		sr.dispose();
		stage.dispose();
	}

	public static OrthographicCamera getCam() {
		return cam;
	}

	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
	}

}
