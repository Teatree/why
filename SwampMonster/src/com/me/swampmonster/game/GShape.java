package com.me.swampmonster.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.me.swampmonster.GUI.GUI;
import com.me.swampmonster.GUI.Weaponizer;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.TutorialLevel;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.screens.AbstractGameScreen;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.LGenerator;
import com.me.swampmonster.utils.ScreenContainer;

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
	public static Weaponizer weaponizer;
	
	public Dialog exitDialog;
	
	public ImageButton slotMachineButton;
	
	public GShape(TheController theController) {
		super();
		sr = new ShapeRenderer();
		Skin skin = new Skin(Gdx.files.internal("skins\\slotMachineUI.json"), new TextureAtlas(Gdx.files.internal("skins\\slotMachineUI.pack")));
		weaponizer = new Weaponizer();
		slotMachineButton = new ImageButton(skin, "yes");
		slotMachineButton.debug();
		slotMachineButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("yes you pressed me");
//				Gdx.input.setInputProcessor(null);

				AbstractGameScreen sl;
				if (TheController.germany && L1.player.state == State.DEAD){
					sl = ScreenContainer.SS;
					TheController.gui = new GUI(L1.player);
//					reloadLevel(L1.player);
				} else {
					TheController.gui = new GUI(L1.player);
					ScreenContainer.SMS.yesWasJustPressed = false;
					SlotMachineTextures.peru = false;
					sl = ScreenContainer.SMS;
				}
				sl.player = L1.player;
				TheController.germany = false;
				TheController.paused = false;
				((Game) Gdx.app.getApplicationListener()).setScreen(sl);
			}
		});
		L1Renderer.stage.addActor(slotMachineButton);
		this.theController = theController;
		waveNotificationAnimationCounter = 240;
//		feedbackWindow = new Sprite(Assets.manager.get(Assets.slotMachineWindow));
//		feedbackWindowYes = new Sprite(Assets.manager.get(Assets.slotMachineWindowYes));
		exitDialog = new ExitDialog("", skin);
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
	super.draw(batch, parentAlpha);
	
//	weaponizer.update(L1.player, theController.point);
	
	slotMachineButton.setX(720);
	slotMachineButton.setY(370);
	slotMachineButton.setWidth(64);
	slotMachineButton.setHeight(64);
	
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
		for (Rectangle r : TheController.gui.getHealthBar().getHealthBarRect()) {
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
			if (TheController.gui.getHealthBar().getHealthBarRect()[j] != null) {
				sr.rect(TheController.gui.getHealthBar().getHealthBarRect()[j].x,
						TheController.gui.getHealthBar().getHealthBarRect()[j].y,
						TheController.gui.getHealthBar().getHealthBarRect()[j].width,
						TheController.gui.getHealthBar().getHealthBarRect()[j].height);
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
			if (weaponizer != null && weaponizer.on == false) {
				sr.setColor(Color.WHITE);
			} else if (weaponizer != null && weaponizer.on == true) {
				sr.setColor(Color.WHITE);
			}

			if (weaponizer != null && TheController.skill != null) {
				sr.circle(weaponizer.circle.x,
						weaponizer.circle.y,
						weaponizer.circle.radius);
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

//		Vector2 point = new Vector2();
//		point.x = (Gdx.input.getX() * Constants.VIEWPORT_WIDTH)
//				/ Gdx.graphics.getWidth();
//		point.y = Constants.VIEWPORT_HEIGHT
//				- (Gdx.input.getY() * Constants.VIEWPORT_HEIGHT)
//				/ Gdx.graphics.getHeight();
//
//		sr.rect(point.x, point.y, 10, 10);

//		if (theController.doesIntersect(point, new Vector2(
//				L1.player.circle.x,
//				L1.player.circle.y),
//				L1.player.circle.radius * 2)) {
//			sr.setColor(Color.WHITE);
//		}

		sr.setColor(Color.PINK);
		sr.rect(theController.pointRect.x, theController.pointRect.y, 2, 2);

		sr.end();

		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.setTransformMatrix(batch.getTransformMatrix());
		sr.translate(getX(), getY(), 0);
		sr.begin(ShapeType.Filled);
		if (L1.player.isDead()) {
			sr.setColor(new Color(200, 0, 0, assRevert));
			sr.rect(TheController.gui.getGameoverGUI().rectanlge.x,
					TheController.gui.getGameoverGUI().rectanlge.y,
					TheController.gui.getGameoverGUI().rectanlge.width + 200,
					TheController.gui.getGameoverGUI().rectanlge.height + 200);
			if (assRevert < 0.5f && L1.player.state == State.DEAD) {
				assRevert = assRevert + 0.002f;
			}
		}
		if (assRevert >= 0.45f
				&& L1.player.state == State.DEAD) {
			sr.setColor(Color.GREEN);
//			if (TheController.gui.getGameoverGUI().circle.contains(point)) {
//				sr.setColor(new Color(0, 200, 0.5f, 100));
//				TheController.showFeedback = true;
//			}
			sr.circle(TheController.gui.getGameoverGUI().circle.x,
					TheController.gui.getGameoverGUI().circle.y,
					TheController.gui.getGameoverGUI().circle.radius);
		}

		sr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		sr.begin(ShapeType.Line);

		if (assRevert >= 0.45f
				&& L1.player.state == State.DEAD) {
			sr.setColor(Color.BLACK);
			sr.circle(TheController.gui.getGameoverGUI().circle.x,
					TheController.gui.getGameoverGUI().circle.y,
					TheController.gui.getGameoverGUI().circle.radius);
		}

		sr.end();
			
		batch.begin();
		
		if (theController.unlockNotificationSprite != null && unlockNotificationCounter > 0){
//			System.out.println("unlockNotification " + theController.unlockNotificationSprite);
			batch.draw(theController.unlockNotificationSprite, 700, 100);
			unlockNotificationCounter--;
		}
		
		for (Sprite s: TheController.gui.getHealthBar().sprites){
			batch.draw(s, s.getX(), s.getY(), s.getWidth(), s.getHeight()+6);
		}
		for (Sprite s: TheController.gui.getOxygenBar().sprites){
			batch.draw(s, s.getX(), s.getY(), s.getWidth(), s.getHeight()+6);
		}
		if (weaponizer != null && TheController.skill != null){
			batch.draw(weaponizer.sprite, 0, 0);
		}
		
		if (L1.player.positiveEffectsState != null
				&& L1.player.positiveEffectsState != PositiveEffects.NONE) {
			batch.draw(L1.player.positiveEffectSprite,
					736, 380, 64, 64);
			font.draw(batch, L1.player.positiveEffectCounter
					.toString(), 736, 380);
		}
		if (L1.player.negativeEffectsState != null
				&& L1.player.negativeEffectsState != NegativeEffects.NONE) {
			batch.draw(L1.player.negativeEffectsState.sprite,
					736, 300, 64, 64);
			font.draw(batch, L1.player.negativeEffectCounter
					.toString(), 736, 300);
		}
		if (weaponizer != null &&TheController.skill != null && !(TheController.skill instanceof Perks)) {
			batch.draw(
					TheController.skill.sprite,
					weaponizer.sprite.getX()
							+ weaponizer.sprite
									.getWidth() / 4,
									weaponizer.sprite.getY()
							+ weaponizer.sprite
									.getHeight() / 4, 64, 64);
			
		}
		
		try {
			if (weaponizer != null && TheController.skill != null) {
				Sprite s = new Sprite(
						SlotMachineTextures.slotLevelPic
								.get(TheController.skill.getClass()
										.getField("level").getInt(null) - 1));
				s.setPosition(
						weaponizer.position.x - 35,
						weaponizer.position.y - 35);
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
			font.draw(batch, TheController.gui.getGameoverGUI().getGameOverString(), 310, 280);
		}
//		if(assRevert >= 0.4f && TheController.level1.player.state == State.DEAD){
//			font.setScale(1);
//			font.draw(batch, TheController.gui.getGameoverGUI().getWittyMessage(), 240-TheController.gui.getGameoverGUI().getWittyMessage().length(), 230);
//		}
		if(assRevert >= 0.45f && L1.player.state == State.DEAD){
			font.setScale(1);
			font.draw(batch, TheController.gui.getGameoverGUI().getRestartString(), 361, 170);
		}
		
		if(TheController.showFeedback){
			
			feedbackWindow.setSize(Constants.VIEWPORT_WIDTH/2.1f, Constants.VIEWPORT_HEIGHT/1.4f);
			feedbackWindow.setPosition(Constants.VIEWPORT_WIDTH/4f, Constants.VIEWPORT_HEIGHT/5f);
			feedbackWindow.draw(batch);
			font.setColor(Color.RED);
			String wittyMessage;
			String nextMessage;
			if(assRevert >= 0.4f && L1.player.state == State.DEAD){
				wittyMessage = TheController.gui.getGameoverGUI().getWittyMessage();
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
		if (weaponizer != null && TheController.skill != null) {
			sr.arc(weaponizer.position.x,
					weaponizer.position.y,
					weaponizer.circle.radius, 90,
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
//			Vector2 victor2 = new Vector2(Gdx.input.getX(), Constants.VIEWPORT_HEIGHT - Gdx.input.getY());
		}
		if(TutorialLevel.aFingure != null){
			batch.draw(TutorialLevel.aFingure, TutorialLevel.aFingure.getX(), TutorialLevel.aFingure.getY(), TutorialLevel.aFingure.getWidth(), TutorialLevel.aFingure.getHeight());
		}
		
		if ((Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE))/*&& TheController.showExitMessage*/){
//			TheController.showExitMessage = false;
			exitDialog.setPosition(Constants.VIEWPORT_WIDTH/2 - exitDialog.getWidth()/2, Constants.VIEWPORT_HEIGHT/2 - exitDialog.getHeight()/2);
			L1Renderer.stage.addActor(exitDialog);
		} 
		batch.end();
		
		sr.begin(ShapeType.Filled);
		if(TutorialLevel.step == 2){
			sr.setColor(Color.RED);
			for (Rectangle r : TheController.gui.getHealthBar().getHealthBarRect()) {
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
			for (Sprite s: TheController.gui.getHealthBar().sprites){
				batch.draw(s, s.getX(), s.getY(), s.getWidth(), s.getHeight()+6);
			}
			
			for (Sprite s: TheController.gui.getOxygenBar().sprites){
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
	
	public class ExitDialog extends Dialog {

		public ExitDialog(String title, Skin skin) {
			super(title, skin);
			Label message = new Label("why?", skin);
			message.setWrap(true);
			getContentTable().add(message).center();
			ImageButton yes = new ImageButton(skin, "yes");
			button(yes, "penis");
			ImageButton no = new ImageButton(skin, "no");
			button(no, "poop");
		}

		@Override
		protected void result(Object object) {
			if (object.equals("penis")){
				TheController.gotoMenu = true;
				TheController.paused = false;
			} else if (object.equals("poop")) {
				TheController.gotoMenu = false;
				TheController.paused = false;
			}
		}
		
	}
}
