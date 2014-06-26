package com.me.swampmonster.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class GShape extends Group {
	private ShapeRenderer sr;
	private TheController theController;
	float ass = 1f;
	float assRevert = 0f;
	private int timer;
	private BitmapFont font;
	
	private CharSequence str;
	private CharSequence str2;

	public GShape(TheController theController) {
		super();
		sr = new ShapeRenderer();
		this.theController = theController;
	}
	
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		font = Assets.manager.get(Assets.font);
		
		str = "points: " + theController.level1.player.points;
		str2 = "Wave:" + theController.level1.currentWave + "/" + theController.level1.wavesAmount;
		
		batch.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.setTransformMatrix(batch.getTransformMatrix());
		sr.translate(getX(), getY(), 0);
		sr.begin(ShapeType.Filled);
		if (theController.level1.player.fearRectangle != null){
			sr.setColor(new Color(84/255f,84/255f,84/255f, 0.5f));
			sr.rect(0, 0, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		}
		sr.setColor(Color.RED);
		
		if (theController != null) {
			for (Rectangle r : theController.gui.getHealthBar()
					.getHealthBarRect()) {
				if (r != null) {
					sr.rect(r.x, r.y, r.width, r.height);
				}
			}
			if (theController.level1.player.hurt) {
				int j = 0;
				if (theController.level1.player.health > 1) {
					j = (int)theController.level1.player.health - 1;
				}
				theController.level1.player.hurt = true;
				sr.setColor(new Color(200, 0, 0, ass));
				if (theController.gui.getHealthBar().getHealthBarRect()[j] != null) {
					sr.rect(theController.gui.getHealthBar().getHealthBarRect()[j].x,
							theController.gui.getHealthBar().getHealthBarRect()[j].y,
							theController.gui.getHealthBar().getHealthBarRect()[j].width,
							theController.gui.getHealthBar().getHealthBarRect()[j].height);
				}
				ass = ass - 0.02f;
			} else if (!theController.level1.player.hurt) {
				ass = 1f;
			}
			if (theController.level1.player.oxygen > 0) {
				sr.setColor(Color.YELLOW);
			}
			if (theController.level1.player.oxygen < 42) {
				warningFlicker(sr);
			}
			if (theController.level1.player.oxygen <= 0 && timer >= 10) {
				// System.out.println(timer);
				sr.setColor(new Color(0, 200, 20, 0.5f));
				sr.rect(16, 422, 96, 22);
			}
			if (theController.level1.player.maskOn) {
				if (theController.level1.player.oxygen > 0) {
					sr.rect(16, 422, theController.level1.player.oxygen,
							22);
				}
			}
			sr.setColor(Color.BLUE);
			if (theController.level1.player.maskOn
					&& theController.level1.player.oxygen == 0) {
				sr.rect(16, 422, 96, 22);
			}
			if (theController.level1.player.state != State.DEAD) {
				if (theController.gui.getWeaponizer().on == false) {
					sr.setColor(Color.WHITE);
				} else if (theController.gui.getWeaponizer().on == true) {
					sr.setColor(Color.WHITE);
				}

				sr.circle(theController.gui.getWeaponizer().circle.x,
						theController.gui.getWeaponizer().circle.y,
						theController.gui.getWeaponizer().circle.radius);
			}
			
			sr.end();
			
			//hhjkl
			
			sr.setProjectionMatrix(batch.getProjectionMatrix());
			sr.setTransformMatrix(batch.getTransformMatrix());
			sr.translate(getX(), getY(), 0);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			sr.begin(ShapeType.Line);
			sr.setColor(Color.MAGENTA);
			
			Vector2 point = new Vector2();
			point.x = (Gdx.input.getX()*Constants.VIEWPORT_WIDTH)/Gdx.graphics.getWidth();
			point.y = Constants.VIEWPORT_HEIGHT-(Gdx.input.getY()*Constants.VIEWPORT_HEIGHT)/Gdx.graphics.getHeight();
//			System.out.println("width: " + Gdx.graphics.getWidth() + ", height: " + Gdx.graphics.getHeight());
//			System.out.println("point.x: " + point.x + ", point.y: " + point.y);
			
			sr.rect(point.x, point.y, 10, 10);
			
			if (theController.doesIntersect(point, new Vector2(theController.level1
					.player.circle.x, theController.level1
					.player.circle.y), theController.level1
					.player.circle.radius * 2)) {
				sr.setColor(Color.WHITE);
			}

			sr.setColor(Color.PINK);
			sr.rect(theController.pointRect.x, theController.pointRect.y, 2, 2);
			
			sr.end();
			
			sr.setProjectionMatrix(batch.getProjectionMatrix());
			sr.setTransformMatrix(batch.getTransformMatrix());
			sr.translate(getX(), getY(), 0);
			sr.begin(ShapeType.Filled);
			if (theController.level1.player.isDead()) {
				sr.setColor(new Color(200, 0, 0, assRevert));
				sr.rect(theController.gui.getGameoverGUI().rectanlge.x,
						theController.gui.getGameoverGUI().rectanlge.y,
						theController.gui.getGameoverGUI().rectanlge.width+200,
						theController.gui.getGameoverGUI().rectanlge.height+200);
				if (assRevert < 0.5f
						&& theController.level1.player.state == State.DEAD) {
					assRevert = assRevert + 0.002f;
				}
			}
			if (assRevert >= 0.45f
					&& theController.level1.player.state == State.DEAD) {
				sr.setColor(Color.GREEN);
				if (theController.gui.getGameoverGUI().circle.contains(point)){
					System.out.println("X: " + point.x + " Y: " + point.y);
					sr.setColor(new Color(0, 200, 0.5f, 100));
				}
				sr.circle(theController.gui.getGameoverGUI().circle.x,
						theController.gui.getGameoverGUI().circle.y,
						theController.gui.getGameoverGUI().circle.radius);
			}
			
			

			sr.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

			
			sr.begin(ShapeType.Line);
			
			if (assRevert >= 0.45f
					&& theController.level1.player.state == State.DEAD) {
				sr.setColor(Color.BLACK);
				sr.circle(theController.gui.getGameoverGUI().circle.x,
						theController.gui.getGameoverGUI().circle.y,
						theController.gui.getGameoverGUI().circle.radius);
			}
			sr.rect(theController.debugRect.x, theController.debugRect.y,
					theController.debugRect.width,
					theController.debugRect.height);
			
			
			
			sr.end();
			
		}
		batch.begin();
		for (Sprite s: theController.gui.getHealthBar().sprites){
			batch.draw(s, s.getX(), s.getY(), s.getWidth(), s.getHeight()+6);
		}
		for (Sprite s: theController.gui.getOxygenBar().sprites){
			batch.draw(s, s.getX(), s.getY(), s.getWidth(), s.getHeight()+6);
		}
		batch.draw(theController.gui.getWeaponizer().sprite, 0, 0);
		
		if (theController.level1.player.positiveEffectsState != null
				&& theController.level1.player.positiveEffectsState != PositiveEffects.NONE) {
			batch.draw(theController.level1.player.positiveEffectSprite,
					Constants.VIEWPORT_WIDTH - 64,
					Constants.VIEWPORT_HEIGHT - 100, 64, 64);
			font.draw(batch, theController.level1.player.positiveEffectCounter
					.toString(), Constants.VIEWPORT_WIDTH - 64,
					Constants.VIEWPORT_HEIGHT - 110);
		}
		if (theController.level1.player.negativeEffectsState != null
				&& theController.level1.player.negativeEffectsState != NegativeEffects.NONE) {
			batch.draw(theController.level1.player.negativeEffectsState.sprite,
					Constants.VIEWPORT_WIDTH - 64,
					Constants.VIEWPORT_HEIGHT - 174, 64, 64);
			font.draw(batch, theController.level1.player.negativeEffectCounter
					.toString(), Constants.VIEWPORT_WIDTH - 64,
					Constants.VIEWPORT_HEIGHT - 184);
		}
		if (TheController.skill != null && !(TheController.skill instanceof Perks)) {
			batch.draw(
					TheController.skill.sprite,
					theController.gui.getWeaponizer().sprite.getX()
							+ theController.gui.getWeaponizer().sprite
									.getWidth() / 4,
					theController.gui.getWeaponizer().sprite.getY()
							+ theController.gui.getWeaponizer().sprite
									.getHeight() / 4, 64, 64);
			
		}
		
		
		
		
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, str, 580, 460);
		font.draw(batch, str2, 580, 420);
		font.setColor(Color.YELLOW);
		font.setScale(1);
		if(assRevert >= 0.4f && theController.level1.player.state == State.DEAD){
			font.draw(batch, theController.gui.getGameoverGUI().getGameOverString(), 310, 280);
		}
		if(assRevert >= 0.4f && theController.level1.player.state == State.DEAD){
			font.setScale(1);
			font.draw(batch, theController.gui.getGameoverGUI().getWittyMessage(), 240-theController.gui.getGameoverGUI().getWittyMessage().length(), 230);
		}
		if(assRevert >= 0.45f && theController.level1.player.state == State.DEAD){
			font.setScale(1);
			font.draw(batch, theController.gui.getGameoverGUI().getRestartString(), 361, 170);
		}
		
		batch.end();
		
		sr.begin(ShapeType.Filled);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.setColor(new Color(0.1f,  0.1f, 0.1f, 0.57f));
		sr.arc(theController.gui.getWeaponizer().position.x,
				theController.gui.getWeaponizer().position.y,
				theController.gui.getWeaponizer().circle.radius, 90, theController.coolDownAngle);
		sr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		batch.begin();
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
}
