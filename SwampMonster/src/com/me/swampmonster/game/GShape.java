package com.me.swampmonster.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.me.swampmonster.GUI.GUI;
import com.me.swampmonster.GUI.Weaponizer;
import com.me.swampmonster.models.AbstractGameObject.NegativeEffects;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.TutorialLevel;
import com.me.swampmonster.models.items.Bow;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.PositiveEffects;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.screens.AbstractGameScreen;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.ItemGenerator;
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

	public static Weaponizer weaponizer;
	
	public Dialog exitDialog;
	public Dialog feeDialog;
	public static Dialog weaponDialog;
	
	public static Skin skin;
	
	public ImageButton slotMachineButton;
	
	public GShape(TheController theController) {
		super();
		sr = new ShapeRenderer();
		skin = new Skin(Gdx.files.internal("skins\\slotMachineUI.json"), new TextureAtlas(Gdx.files.internal("skins\\slotMachineUI.pack")));
		if(!(TheController.skill instanceof Perks)){
			weaponizer = new Weaponizer();
		}
		slotMachineButton = new ImageButton(skin, "yes");
//		slotMachineButton.debug();
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
		font.setScale(1);
		font.setColor(Color.YELLOW);
		
		str = "points: " + Player.absoluteScore;
		str2 = "Wave:" + TheController.level1.currentWave + "/" + TheController.level1.wavesAmount;
		str3 = "Wave:" + TheController.level1.currentWave;
		
		batch.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.setTransformMatrix(batch.getTransformMatrix());
		sr.translate(getX(), getY(), 0);
		sr.begin(ShapeType.Filled);
		if (L1.player.negativeEffectsState == NegativeEffects.FEAR){
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

		sr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		batch.begin();
		
		if (L1.player.absoluteScore>1 && theController.unlockNotificationSprite != null && unlockNotificationCounter > 0){
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
			font.setScale(1);
			font.draw(batch, L1.player.positiveEffectCounter/60 + " s"
					.toString(), 737, 380);
		}
		if (L1.player.negativeEffectsState != null
				&& L1.player.negativeEffectsState != NegativeEffects.NONE) {
			batch.draw(L1.player.negativeEffectsState.sprite,
					736, 300, 64, 64);
			font.setScale(1);
			font.draw(batch, L1.player.negativeEffectCounter/60 + " s"
					.toString(), 737, 300);
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
//				Sprite s = new Sprite(
//						SlotMachineTextures.slotLevelPic
//								.get(TheController.skill.getClass()
//										.getField("level").getInt(null) - 1));
//				s.setPosition(
//						weaponizer.position.x - 35,
//						weaponizer.position.y - 35);
//				s.setSize(27, 27);
//				s.draw(batch);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		font.setColor(Color.ORANGE);
		font.setScale(1);
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
//		if(assRevert >= 0.4f && TheController.level1.player.state == State.DEAD){
//			font.setScale(1);
//			font.draw(batch, TheController.gui.getGameoverGUI().getWittyMessage(), 240-TheController.gui.getGameoverGUI().getWittyMessage().length(), 230);
//		}
		if(L1.player.state == State.DEAD){
			TheController.showFeedback = true;
			TheController.paused = true;
		}
			
		if(TheController.showFeedback){
			if (feeDialog == null){
				for(Actor a: L1Renderer.stage.getActors()){
					if(a.equals(feeDialog)){
						a.remove();
					}
				}
				feeDialog = new FeedBackWindow("", skin);
				feeDialog.setX(250);
				feeDialog.setY(350);
				feeDialog.setWidth(300);
				feeDialog.setHeight(300);
				L1Renderer.stage.addActor(feeDialog);
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
			exitDialog.setWidth(250);
			exitDialog.setHeight(150);
			exitDialog.getColor().a = 1f;
			exitDialog.setPosition(300, 300);
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
	
	
	public class FeedBackWindow extends Dialog {

		private Label header;
		private Label score;
		private Label levelScore;
		private Label enemies_killed;
		private Label shot_arrows;
		private Table ourTable;
		private String[] WittyEnemyMessages;
		private String[] WittySuffocationMessages;
		private String[] WittyPoisonMessages;
		
		private ImageButton ok;
		private ImageButton ad;
		
		public FeedBackWindow(String title, Skin skin) {
			super(title, skin);
			
			Random randomGenerator = new Random();
			int i = randomGenerator.nextInt(3);
			
			WittyEnemyMessages = new String[3];
			WittySuffocationMessages = new String[3];
			WittyPoisonMessages = new String[3];
			WittyEnemyMessages[0] = Constants.enemyMessage1;
			WittyEnemyMessages[1] = Constants.enemyMessage2;
			WittyEnemyMessages[2] = Constants.enemyMessage3;
			WittySuffocationMessages[0] = Constants.sufficateMessage1;
			WittySuffocationMessages[1] = Constants.sufficateMessage2;
			WittySuffocationMessages[2] = Constants.sufficateMessage3;
			WittyPoisonMessages[0] = Constants.poisionedMessage1;
			WittyPoisonMessages[1] = Constants.poisionedMessage2;
			WittyPoisonMessages[2] = Constants.poisionedMessage3;
			
			String wittyMessage = null;
			
			if(L1.player.state == State.DEAD){
				if (L1.player.damageTypeOxygen == "lackOfOxygen") {
					wittyMessage = WittySuffocationMessages[i];
				} else if (L1.player.damageType == "enemy") {
					wittyMessage = WittyEnemyMessages[i];
				} else if (L1.player.damageType == "Poisoned") {
					wittyMessage = WittyPoisonMessages[i];
				}
			} else {
				wittyMessage = Constants.ONE_MORE_WORLD_CONQUERED;
			}
			header = new Label(wittyMessage, skin, "title");
			score = new Label(Constants.SCORE + L1.player.absoluteScore, skin);
			levelScore = new Label("level " + Constants.SCORE + L1.player.levelsScore, skin);
			enemies_killed = new Label(Constants.ENEMIES_KILLED + L1.player.enemiesKilled, skin);
			shot_arrows = new Label(Constants.SHOT_ARROWS + L1.player.shotArrows, skin);
			ok = new ImageButton(skin);
			ad = new ImageButton(skin, "ad");
			ourTable = new Table();
			
			ourTable.add(header).top().row();
			ourTable.add(score).top().row();
			ourTable.add(levelScore).top().row();
			ourTable.add(enemies_killed).top().row();
			ourTable.add(shot_arrows).top().row();
			
			getContentTable().add(ourTable);
//			getButtonTable().debug();
			
			button(ok, "Nastya");
			getButtonTable().getCell(ok).padLeft(23);
			if(L1.player.state == State.DEAD){
				button(ad, "Advertisement");
				getButtonTable().getCell(ad).padLeft(100).padRight(23);
			}
		}
		
		@Override
		protected void result(Object object) {
			for(Actor a: L1Renderer.stage.getActors()){
				if(a.equals(feeDialog)){
					System.out.println(a);
				}
			}
			System.out.println("player state: " + L1.player.state + " player object: " + object);
			if (L1.player.state != State.DEAD){
				LGenerator.lastMap = null;
				LGenerator.lastTileSet = null;
				LGenerator.hadLastAtmosphere = false;
				LGenerator.wasLastElite = false;
				
				TheController.showFeedback = false;
				feeDialog = null;
				for(Actor a: L1Renderer.stage.getActors()){
					if(a.equals(feeDialog)){
						a.remove();
					}
				}
			}else{
				if(object == "Nastya"){
					Player.maxOxygen = Player.DEFAULT_MAX_O2;
					L1.player.maxHealth = Player.DEFAULT_MAX_HEALTH;
					Player.absoluteScore = 0;
					L1.player.weapon = new Bow();
//					L1.player.weapon.minDD = (int) Constants.DEFAULT_PLAYER_min_DAMAGE;
//					L1.player.weapon.maxDD = (int) Constants.DEFAULT_PLAYER_max_DAMAGE;
					Player.DEFAULT_MINIMUM_DAMAGE = Constants.DEFAULT_PLAYER_min_DAMAGE;
					Player.DEFAULT_MAXIMUM_DAMAGE = Constants.DEFAULT_PLAYER_max_DAMAGE;
					Player.arrowMovementSpeed = Player.DEFAULT_ARROW_MOVEMENT_SPEED;
					L1.player.movementSpeed = Player.DEFAULT_MOVEMENT_SPEED;
					Player.levelsScore = 0;
					L1.player.trap = null;
					L1.player.turret = null;
					L1.hydra = null;
					L1.plasmaShield = null;
					for(Slot s: SlotMachineScreen.savedSlots){
						try {
							s.getClass().getField("level").setInt(null, 0);
						} catch(Exception e) {
							
						}
					}
					ItemGenerator.usedTextures = new HashMap<Integer, String>();
					SlotMachineScreen.savedSlots = new ArrayList<Slot>();
					TheController.skill = null;
					
					LGenerator.lastMap = null;
					LGenerator.lastTileSet = null;
					LGenerator.hadLastAtmosphere = false;
					LGenerator.wasLastElite = false;
					TheController.gotoMenu = true;
					
					TheController.showFeedback = false;
					feeDialog = null;
					for(Actor a: L1Renderer.stage.getActors()){
						if(a.equals(feeDialog)){
							a.remove();
						}
					}
				}else{
//					System.out.println("ps: " + Player.absoluteScore  + " pls: " + Player.levelsScore ) ;
					Player.absoluteScore -= Player.levelsScore;
					Player.levelsScore = 0;
					TheController.showFeedback = false;
					feeDialog = null;
					for(Actor a: L1Renderer.stage.getActors()){
						if(a.equals(feeDialog)){
							a.remove();
						}
					}
				}
			}
			TheController.germany = true;
			TheController.showFeedback = false;
			feeDialog = null;
			for(Actor a: L1Renderer.stage.getActors()){
				if(a.equals(feeDialog)){
					a.remove();
				}
			}
		}
		
	}
	
	public class ExitDialog extends Dialog {

		public ExitDialog(String title, Skin skin) {
			super(title, skin);
			Label message = new Label("Are you sure you want to quit?", skin);
//			message.setWrap(true);
			getContentTable().add(message).left();
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
			}
			if (object.equals("poop")) {
				TheController.gotoMenu = false;
				TheController.paused = false;
			}
		}
		
	}
}
