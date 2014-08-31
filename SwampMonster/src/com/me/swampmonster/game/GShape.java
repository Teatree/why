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
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.TutorialLevel;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.LGenerator;

public class GShape extends Group {
	
	private ShapeRenderer sr;
	private TheController theController;
	float ass = 1f;
	float assRevert = 0f;
	private int timer;
	private BitmapFont font;
	private int waveNotificationAnimationCounter;
	public static int unlockNotificationCounter;

	private CharSequence str;
	private CharSequence str2;
	private CharSequence str3;

	public Sprite feedbackWindow;
	public Sprite feedbackWindowYes;
	
	public Sprite exitMessageWindow;
	public Sprite gotoMenu;
	public Sprite backToGame;
	
	public GShape(TheController theController) {
		super();
		sr = new ShapeRenderer();
		this.theController = theController;
		waveNotificationAnimationCounter = 240;
		feedbackWindow = new Sprite(Assets.manager.get(Assets.slotMachineWindow));
		feedbackWindowYes = new Sprite(Assets.manager.get(Assets.slotMachineWindowYes));
		exitMessageWindow = new Sprite(Assets.manager.get(Assets.exitMessageWindow));
		gotoMenu = new Sprite(Assets.manager.get(Assets.slotMachineWindowYes));
		backToGame = new Sprite(Assets.manager.get(Assets.slotMachineWindowNo));
	}
	
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		font = Assets.manager.get(Assets.font);
		
		str = "points: " + Player.levelsScore;
		str2 = "Wave:" + TheController.level1.currentWave + "/" + TheController.level1.wavesAmount;
		str3 = "Wave:" + TheController.level1.currentWave;
		
		batch.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.setTransformMatrix(batch.getTransformMatrix());
		sr.translate(getX(), getY(), 0);
		sr.begin(ShapeType.Filled);
		if (L1.player.fearRectangle != null){
			sr.setColor(new Color(84/255f,84/255f,84/255f, 0.5f));
			sr.rect(0, 0, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		}
		sr.setColor(Color.RED);
		
		for (Rectangle r : theController.gui.getHealthBar().getHealthBarRect()) {
			if (r != null) {
				sr.rect(r.x, r.y, r.width, r.height);
			}
		}
		if (L1.player.hurt) {
			int j = 0;
			if (L1.player.health > 1) {
				j = (int) L1.player.health - 1;
			}
			L1.player.hurt = true;
			sr.setColor(new Color(200, 0, 0, ass));
			if (theController.gui.getHealthBar().getHealthBarRect()[j] != null) {
				sr.rect(theController.gui.getHealthBar().getHealthBarRect()[j].x,
						theController.gui.getHealthBar().getHealthBarRect()[j].y,
						theController.gui.getHealthBar().getHealthBarRect()[j].width,
						theController.gui.getHealthBar().getHealthBarRect()[j].height);
			}
			ass = ass - 0.02f;
		} else if (!L1.player.hurt) {
			ass = 1f;
		}
		if (L1.player.oxygen > 0) {
			sr.setColor(Color.YELLOW);
		}
		if (L1.player.oxygen < 42) {
			warningFlicker(sr);
		}
		if (L1.player.oxygen <= 0 && timer >= 10) {
			// System.out.println(timer);
			sr.setColor(new Color(0, 200, 20, 0.5f));
			sr.rect(16, 422, Player.maxOxygen, 22);
		}
		if (L1.player.maskOn) {
			if (L1.player.oxygen > 0) {
				sr.rect(16, 422, L1.player.oxygen, 22);
			}
		}
		sr.setColor(Color.BLUE);
		if (L1.player.maskOn
				&& L1.player.oxygen == 0) {
			sr.rect(16, 422, Player.maxOxygen, 22);
		}
		if (L1.player.state != State.DEAD) {
			if (theController.gui.getWeaponizer().on == false) {
				sr.setColor(Color.WHITE);
			} else if (theController.gui.getWeaponizer().on == true) {
				sr.setColor(Color.WHITE);
			}

			if (TheController.skill != null) {
				sr.circle(theController.gui.getWeaponizer().circle.x,
						theController.gui.getWeaponizer().circle.y,
						theController.gui.getWeaponizer().circle.radius);
			}
		}

		sr.end();

		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.setTransformMatrix(batch.getTransformMatrix());
		sr.translate(getX(), getY(), 0);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.begin(ShapeType.Line);
		sr.setColor(Color.MAGENTA);

		Vector2 point = new Vector2();
		point.x = (Gdx.input.getX() * Constants.VIEWPORT_WIDTH)
				/ Gdx.graphics.getWidth();
		point.y = Constants.VIEWPORT_HEIGHT
				- (Gdx.input.getY() * Constants.VIEWPORT_HEIGHT)
				/ Gdx.graphics.getHeight();
		// System.out.println("width: " + Gdx.graphics.getWidth() + ", height: "
		// + Gdx.graphics.getHeight());
		// System.out.println("point.x: " + point.x + ", point.y: " + point.y);

		sr.rect(point.x, point.y, 10, 10);

		if (theController.doesIntersect(point, new Vector2(
				L1.player.circle.x,
				L1.player.circle.y),
				L1.player.circle.radius * 2)) {
			sr.setColor(Color.WHITE);
		}

		sr.setColor(Color.PINK);
		sr.rect(theController.pointRect.x, theController.pointRect.y, 2, 2);

		sr.end();

		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.setTransformMatrix(batch.getTransformMatrix());
		sr.translate(getX(), getY(), 0);
		sr.begin(ShapeType.Filled);
		if (L1.player.isDead()) {
			sr.setColor(new Color(200, 0, 0, assRevert));
			sr.rect(theController.gui.getGameoverGUI().rectanlge.x,
					theController.gui.getGameoverGUI().rectanlge.y,
					theController.gui.getGameoverGUI().rectanlge.width + 200,
					theController.gui.getGameoverGUI().rectanlge.height + 200);
			if (assRevert < 0.5f && L1.player.state == State.DEAD) {
				assRevert = assRevert + 0.002f;
			}
		}
		if (assRevert >= 0.45f
				&& L1.player.state == State.DEAD) {
			sr.setColor(Color.GREEN);
			if (theController.gui.getGameoverGUI().circle.contains(point)) {
				sr.setColor(new Color(0, 200, 0.5f, 100));
				TheController.showFeedback = true;
			}
			sr.circle(theController.gui.getGameoverGUI().circle.x,
					theController.gui.getGameoverGUI().circle.y,
					theController.gui.getGameoverGUI().circle.radius);
		}

		sr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		sr.begin(ShapeType.Line);

		if (assRevert >= 0.45f
				&& L1.player.state == State.DEAD) {
			sr.setColor(Color.BLACK);
			sr.circle(theController.gui.getGameoverGUI().circle.x,
					theController.gui.getGameoverGUI().circle.y,
					theController.gui.getGameoverGUI().circle.radius);
		}
		sr.rect(TheController.debugRect.x, TheController.debugRect.y,
				TheController.debugRect.width, TheController.debugRect.height);

		sr.end();
			
		batch.begin();
		
		if (theController.unlockNotificationSprite != null && unlockNotificationCounter > 0){
//			System.out.println("unlockNotification " + theController.unlockNotificationSprite);
			batch.draw(theController.unlockNotificationSprite, 700, 100);
			unlockNotificationCounter--;
		}
		
		for (Sprite s: theController.gui.getHealthBar().sprites){
			batch.draw(s, s.getX(), s.getY(), s.getWidth(), s.getHeight()+6);
		}
		for (Sprite s: theController.gui.getOxygenBar().sprites){
			batch.draw(s, s.getX(), s.getY(), s.getWidth(), s.getHeight()+6);
		}
		if (TheController.skill != null){
			batch.draw(theController.gui.getWeaponizer().sprite, 0, 0);
		}
		
		if (L1.player.positiveEffectsState != null
				&& L1.player.positiveEffectsState != PositiveEffects.NONE) {
			batch.draw(L1.player.positiveEffectSprite,
					Constants.VIEWPORT_WIDTH - 64,
					Constants.VIEWPORT_HEIGHT - 100, 64, 64);
			font.draw(batch, L1.player.positiveEffectCounter
					.toString(), Constants.VIEWPORT_WIDTH - 64,
					Constants.VIEWPORT_HEIGHT - 110);
		}
		if (L1.player.negativeEffectsState != null
				&& L1.player.negativeEffectsState != NegativeEffects.NONE) {
			batch.draw(L1.player.negativeEffectsState.sprite,
					Constants.VIEWPORT_WIDTH - 64,
					Constants.VIEWPORT_HEIGHT - 174, 64, 64);
			font.draw(batch, L1.player.negativeEffectCounter
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
		
		try {
			if (TheController.skill != null) {
				Sprite s = new Sprite(
						SlotMachineTextures.slotLevelPic
								.get(TheController.skill.getClass()
										.getField("level").getInt(null) - 1));
				s.setPosition(
						theController.gui.getWeaponizer().position.x - 35,
						theController.gui.getWeaponizer().position.y - 35);
				s.setSize(27, 27);
				s.draw(batch);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		font.setColor(1.0f, 0f, 1.0f, 1.0f);
		font.draw(batch, str, 580, 460);
		font.draw(batch, str2, 580, 420);
		if(L1.enemiesOnStage.empty() && TheController.level1.waveTemp != null
				&& TheController.level1.currentWave < TheController.level1.wavesAmount && TheController.level1.pendingPeriodBetweedWavesCounter == 0){
			waveNotificationAnimationCounter=240;
		} else {
			waveNotificationAnimationCounter--;
		}
		if(waveNotificationAnimationCounter>0){
			font.setColor(1.0f, 0f, 1.0f, 0.5f);
//			font.setScale(waveNotificationAnimationCounter/10);
			font.draw(batch, str3, 350, 350);
		}
		
		font.setColor(Color.YELLOW);
		font.setScale(1);
		if(assRevert >= 0.4f && L1.player.state == State.DEAD){
			font.draw(batch, theController.gui.getGameoverGUI().getGameOverString(), 310, 280);
		}
//		if(assRevert >= 0.4f && TheController.level1.player.state == State.DEAD){
//			font.setScale(1);
//			font.draw(batch, theController.gui.getGameoverGUI().getWittyMessage(), 240-theController.gui.getGameoverGUI().getWittyMessage().length(), 230);
//		}
		if(assRevert >= 0.45f && L1.player.state == State.DEAD){
			font.setScale(1);
			font.draw(batch, theController.gui.getGameoverGUI().getRestartString(), 361, 170);
		}
		
		if(TheController.showFeedback){
			
			feedbackWindow.setSize(Constants.VIEWPORT_WIDTH/2.1f, Constants.VIEWPORT_HEIGHT/1.4f);
			feedbackWindow.setPosition(Constants.VIEWPORT_WIDTH/4f, Constants.VIEWPORT_HEIGHT/5f);
			feedbackWindow.draw(batch);
			font.setColor(Color.RED);
			String wittyMessage;
			String nextMessage;
			if(assRevert >= 0.4f && L1.player.state == State.DEAD){
				wittyMessage = theController.gui.getGameoverGUI().getWittyMessage();
				nextMessage = Constants.TRY_AGAIN;
			} else {
				wittyMessage = Constants.ONE_MORE_WORLD_CONQUERED;
				nextMessage = Constants.GET_REWARD;
			}
			font.draw(batch, wittyMessage, feedbackWindow.getBoundingRectangle().x+20, feedbackWindow.getBoundingRectangle().y+280);
			font.setColor(Color.BLACK);
			font.setScale(0.75f);
			font.draw(batch, Constants.SCORE + Player.levelsScore, feedbackWindow.getBoundingRectangle().x+25, feedbackWindow.getBoundingRectangle().y+240);
			font.draw(batch, Constants.ENEMIES_KILLED + Player.enemiesKilled, feedbackWindow.getBoundingRectangle().x+25, feedbackWindow.getBoundingRectangle().y+215);
			font.draw(batch, Constants.SHOT_ARROWS + Player.shotArrows, feedbackWindow.getBoundingRectangle().x+25, feedbackWindow.getBoundingRectangle().y+190);
			font.setColor(Color.GREEN);
			font.setScale(1f);
			font.draw(batch, nextMessage, feedbackWindow.getBoundingRectangle().x+10, feedbackWindow.getBoundingRectangle().y+50);
			feedbackWindowYes.setPosition(feedbackWindow.getBoundingRectangle().x+290, feedbackWindow.getBoundingRectangle().y+20);
			feedbackWindowYes.draw(batch);
			Vector2 victor = new Vector2(Gdx.input.getX(), Constants.VIEWPORT_HEIGHT - Gdx.input.getY());
			if (Gdx.input.justTouched()
					&& feedbackWindowYes.getBoundingRectangle().contains(victor)){
				if (L1.player.state != State.DEAD){
					LGenerator.lastMap = null;
					LGenerator.lastTileSet = null;
					LGenerator.hadLastAtmosphere = false;
					LGenerator.wasLastElite = false;
				}
				TheController.germany = true;
				TheController.showFeedback = false;
			}
		}
		
		batch.end();
		
		sr.begin(ShapeType.Filled);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.setColor(new Color(0.1f,  0.1f, 0.1f, 0.57f));
		if (TheController.skill != null) {
			sr.arc(theController.gui.getWeaponizer().position.x,
					theController.gui.getWeaponizer().position.y,
					theController.gui.getWeaponizer().circle.radius, 90,
					TheController.coolDownAngle);
		}
		if(TheController.paused || TheController.pausedTutorial){
			sr.setColor(new Color(0.5f,0.5f,0.5f,0.5f));
			sr.rect(-20, -20, Constants.VIEWPORT_GUI_WIDTH+40, Constants.VIEWPORT_GUI_HEIGHT+40);
		}
		sr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		batch.begin();
		if (TheController.paused){
			exitMessageWindow.setSize(Constants.VIEWPORT_WIDTH/2.1f, Constants.VIEWPORT_HEIGHT/1.4f);
			exitMessageWindow.setPosition(Constants.VIEWPORT_WIDTH/4f, Constants.VIEWPORT_HEIGHT/5f);
			exitMessageWindow.draw(batch);
			font.setColor(Color.RED);
			font.draw(batch, Constants.EXIT_MESSAGE, exitMessageWindow.getBoundingRectangle().x+20, exitMessageWindow.getBoundingRectangle().y+280);
			
			gotoMenu.setPosition(exitMessageWindow.getBoundingRectangle().x+90, exitMessageWindow.getBoundingRectangle().y+20);
			gotoMenu.draw(batch);
			
			
			backToGame.setPosition(exitMessageWindow.getBoundingRectangle().x+290, exitMessageWindow.getBoundingRectangle().y+20);
			backToGame.draw(batch);
			
			Vector2 victor2 = new Vector2(Gdx.input.getX(), Constants.VIEWPORT_HEIGHT - Gdx.input.getY());
			
			if (Gdx.input.justTouched()
					&& gotoMenu.getBoundingRectangle().contains(victor2)){
				TheController.gotoMenu = true;
				TheController.paused = false;
			}
			if (Gdx.input.justTouched()
					&& backToGame.getBoundingRectangle().contains(victor2)){
				TheController.gotoMenu = false;
				TheController.paused = false;
			}
			
		}
		if(TutorialLevel.aFingure != null){
			batch.draw(TutorialLevel.aFingure, TutorialLevel.aFingure.getX(), TutorialLevel.aFingure.getY(), TutorialLevel.aFingure.getWidth(), TutorialLevel.aFingure.getHeight());
		}
		
//		if ((Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE))&& TheController.showExitMessage){
//			TheController.showExitMessage = false;
//		} 
		batch.end();
		
		sr.begin(ShapeType.Filled);
		if(TutorialLevel.step == 2){
			sr.setColor(Color.RED);
			for (Rectangle r : theController.gui.getHealthBar().getHealthBarRect()) {
				if (r != null) {
					sr.rect(r.x, r.y, r.width, r.height);
				}
			}
			sr.setColor(Color.YELLOW);
			sr.rect(16, 422, Player.maxOxygen, 22);
		}
		sr.end();
		
		batch.begin();
		
		if(TutorialLevel.step == 2){
			for (Sprite s: theController.gui.getHealthBar().sprites){
				batch.draw(s, s.getX(), s.getY(), s.getWidth(), s.getHeight()+6);
			}
			
			for (Sprite s: theController.gui.getOxygenBar().sprites){
				batch.draw(s, s.getX(), s.getY(), s.getWidth(), s.getHeight()+6);
			}
			
			if (TutorialLevel.greenArrow != null){
				TutorialLevel.greenArrow.draw(batch);
			}
			
		}
		
		batch.end();
		
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
