package com.me.swampmonster.slotMachineStuff;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.ImproveArrowDamage;
import com.me.swampmonster.models.slots.ImproveMaxHealth;
import com.me.swampmonster.models.slots.ImproveMaxOxygen;
import com.me.swampmonster.models.slots.ImproveMovementSpeed;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.GeneralUtils;
import com.me.swampmonster.utils.ScreenContainer;
import com.me.swampmonster.utils.SlotsGenerator;

public class SlotMachineTextures extends Group {
	private BitmapFont font;
	private static SlotsGenerator slotsGen;
	public Slot[] slots;
	public static Map<Integer, Sprite> slotLevelPic;
	int [] slotPositionsX = {159, 328, 497};
	int slotPositionY = 174;
	public Sprite savedSlotBar;
	public Sprite slotLevel1;
	public Sprite slotLevel2;
	public Sprite slotLevel3;
	public Sprite slotLevel4;
	public Sprite slotLevel5;
	public Sprite selectedSavedSlotRectangle;
	public ShapeRenderer sr;
	public AnimationControl animantionCtlr;
	public boolean[] notAnimating;
	public static boolean peru;
	public Slot selectedSlot;
	public int selectedSlotNumber;
	public int timeCOutner;
	
	public int animCounter;
	public float animDx;
	public float animDy;
	public static float slotAnimSpeed;
	
	public Skin skin;
	public ImageButton goButtonButton;
	public ImageButton rerollButton;
	Random r = new Random();
	public Rectangle rectangle = new Rectangle();
	
	public SlotMachineTextures(Player player) {
		skin = new Skin(Gdx.files.internal("skins\\slotMachineUI.json"), new TextureAtlas(Gdx.files.internal("skins\\slotMachineUI.pack")));
		goButtonButton = new ImageButton(skin, "go");
		goButtonButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.input.setInputProcessor(null);
				if(!(selectedSlot instanceof Perks)){
					TheController.skill = selectedSlot;
				}
				selectedSlot = null;
				((Game) Gdx.app.getApplicationListener()).setScreen(ScreenContainer.SS);
			}
		});
		
		goButtonButton.setDisabled(true);
		SlotMachineScreen.stage.addActor(goButtonButton);
		
		rerollButton = new ImageButton(skin, "reroll");
		rerollButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				generateSlots(L1.player);
				SlotMachineScreen.yesWasJustPressed = false;
				for (int i = 0; i < notAnimating.length; i++) {
					notAnimating[i] = false;
				}
				animCounter = 0;
			}
		});
//		rerollButton.setSize(90, 90);
//		rerollButton.setPosition(Constants.VIEWPORT_GUI_WIDTH*0.45f, Constants.VIEWPORT_GUI_HEIGHT*0.18f);
		rerollButton.setPosition(368, 90);
		SlotMachineScreen.stage.addActor(rerollButton);
		
		font = Assets.manager.get(Assets.font);
		slotsGen = SlotsGenerator.getSlotGenerator();
		
		selectedSavedSlotRectangle = new Sprite (Assets.manager.get(Assets.selectedSavedSlot));
		savedSlotBar = new Sprite(Assets.manager.get(Assets.saveSlotBar));
		
		animantionCtlr = new AnimationControl(Assets.manager.get(Assets.slotAnimation), 8, 1, 8);
		notAnimating = new boolean[3];
		
		slotLevel1 = new Sprite(Assets.manager.get(Assets.slotLevel1));
		slotLevel2 = new Sprite(Assets.manager.get(Assets.slotLevel2));
		slotLevel3 = new Sprite(Assets.manager.get(Assets.slotLevel3));
		slotLevel4 = new Sprite(Assets.manager.get(Assets.slotLevel4));
		slotLevel5 = new Sprite(Assets.manager.get(Assets.slotLevel5));
		
		slotLevelPic = new HashMap<Integer, Sprite>();
		slotLevelPic.put(0, slotLevel1);
		slotLevelPic.put(1, slotLevel2);
		slotLevelPic.put(2, slotLevel3);
		slotLevelPic.put(3, slotLevel4);
		slotLevelPic.put(4, slotLevel5);
		
		slots = new Slot[3];
		generateSlots(player);
	}

	public void generateSlots(Player player) {
		Slot temp;
		boolean argentina = false;
		boolean madagascar = false;
		Set<Slot>slotsSet = new HashSet<Slot>();
		
		if (ImproveArrowDamage.level == 4 
				&& ImproveMaxHealth.level == 4 
				&& ImproveMaxOxygen.level == 4
				&& ImproveMovementSpeed.level == 4){
			madagascar = true;
		}
			while (slotsSet.size() < 3){
			if(r.nextBoolean() && !argentina && !madagascar)
			{
				temp = slotsGen.getPerkSlot(player);
				try{
				if(temp.getClass().getField("level").getInt(null) < 4){
					argentina = true;
				}
				}catch(Exception e){
					
				}
			}
			else if (slotsSet.size() == 2 && !argentina && !madagascar){
				temp = slotsGen.getPerkSlot(player);
			} else {
				temp = slotsGen.getActiveSkillSlot(player);
			}
			try {
				if (TheController.skill == null
						|| !(temp.getClass() == TheController.skill.getClass() && TheController.skill
								.getClass().getField("level").getInt(null) == 4)) {
					if (temp instanceof Perks){
						if (temp.getClass().getField("level").getInt(null) < 4){
							slotsSet.add(temp);
						}
					}else{
						slotsSet.add(temp);
					}
					
				}
			} catch (Exception e) {

			}
		}
			this.slots = slotsSet.toArray(slots); 
			GeneralUtils.shuffle(slots);
	}

	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {

		super.draw(batch, parentAlpha);
		timeCOutner++;
		batch.draw(Assets.manager.get(Assets.slotMachineCase), 144, 112);
		
		
		goButtonButton.setSize(100, 100);
//		goButtonButton.setPosition(Constants.VIEWPORT_GUI_WIDTH*0.85f, Constants.VIEWPORT_GUI_HEIGHT*0.05f);
		goButtonButton.setPosition(660, 40);
		goButtonButton.draw(batch, 1);
		savedSlotBar.setPosition(0, -26);
		savedSlotBar.draw(batch);
		
		animantionCtlr.doComplexAnimation(0, 1f, Gdx.graphics.getDeltaTime(), Animation.PlayMode.NORMAL);
		int i = 0;
		
		Collections.sort(SlotMachineScreen.savedSlots, new Comparator<Slot>() {

			@Override
			public int compare(Slot o1, Slot o2) {
				int o1level = 0;
				int o2level = 0;
				try {
					o1level = o1.getClass().getField("level").getInt(null);
					o2level = o2.getClass().getField("level").getInt(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (o1 instanceof Perks && o2 instanceof Perks){
					if(o1level == o2level){
						return 0;
					} else if (o1level > o2level){
						return -1;
					} else {
						return 1;
					}
				} else if(!(o1 instanceof Perks) && !(o2 instanceof Perks)){
					if(o1level == o2level){
						return 0;
					} else if (o1level > o2level){
						return -1;
					} else {
						return 1;
					}
				} else if (o1 instanceof Perks && !(o2 instanceof Perks)){
					return 1;
				} else/* if (!(o1 instanceof Perks) && (o2 instanceof Perks))*/{
					return -1;
				} 
			}
		});
		
		while (i < 3) {
			if (notAnimating[i]) {
				Slot slot = slots[i];
				slot.sprite.setPosition(slotPositionsX[i], slotPositionY);
				i++;
				slot.sprite.setSize(146, 146);
				rectangle.width = slot.sprite.getWidth();
				rectangle.height = slot.sprite.getHeight();
				rectangle.setX(slot.sprite.getX());
				rectangle.setY(slot.sprite.getY());
				slot.sprite.draw(batch);
				try {
					if (slot.levelSprite == null){
						slot.levelSprite = new Sprite();
					}
					if(slot.selected && SlotMachineScreen.yesWasJustPressed){
						slot.levelSprite.setRegion(slotLevelPic.get(slot.getClass()
								.getField("level").getInt(null)-1));
						rerollButton.setDisabled(true);
					}else{
						slot.levelSprite.setRegion(slotLevelPic.get(slot.getClass()
								.getField("level").getInt(null)));
					}
					slot.levelSprite.setPosition(slot.sprite.getX(), slot.sprite.getY());
					slot.levelSprite.setSize(32, 32);
					slot.levelSprite.draw(batch);

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				batch.draw(animantionCtlr.getCurrentFrame(), slotPositionsX[i],
						slotPositionY, 146, 146);
				animCounter++;

				if (animCounter == 25) {
					notAnimating[i] = true;
				}
				if (animCounter == 50) {
					notAnimating[i] = true;
				}
				if (animCounter == 75) {
					notAnimating[i] = true;
				}
				i++;
			}
		}
		
		int Oppa = 5;
		for(Slot s: SlotMachineScreen.savedSlots){
			if(s.state != State.ANIMATING){
				s.sprite.setPosition(Oppa, 3);
				s.sprite.setSize(32, 32);
				s.sprite.setX(Oppa);
				s.sprite.setY(3);
				batch.draw(s.sprite, s.sprite.getX(), s.sprite.getY(), s.sprite.getWidth(), s.sprite.getHeight());
				Oppa += s.sprite.getWidth()+5;
				if(s.rewritten){
					s.state = State.SPAWNING;
				}
				if(s.state == State.SPAWNING){
					s.update();
					batch.draw(s.selectedSavedSlotFrame, s.sprite.getX()-1, s.sprite.getY()-1);
				}
			}else{
				s.savedSlotPosition = new Vector2(Oppa, 3);
				if(selectedSlot!=null && s.selected){
					if(s.animSlotCounter>0){
						s.animSlotCounter--;
					}
					if(s.animSlotCounter==49){
						s.position = new Vector2(selectedSlot.sprite.getX(), selectedSlot.sprite.getY());
						s.width = 146;
						s.height = 146;
						animDx = s.savedSlotPosition.x - selectedSlot.sprite.getX();
						animDy = s.savedSlotPosition.y - selectedSlot.sprite.getY();
						
						float length1 = (float) Math.sqrt(animDx * animDx + animDy * animDy);
						slotAnimSpeed = length1 / 50;
						
						animDx = animDx /= length1;
						animDy = animDy /= length1;
					}
					if(s.animSlotCounter<=1){
//						s.animSlotCounter=0;
						s.state = State.SPAWNING;
					}
				}
				if(s.animSlotCounter>0 && s.animSlotCounter<=49 && s.selected){
					s.updateAnimate(animDx, animDy);
				}
				batch.draw(s.sprite, s.sprite.getX(), s.sprite.getY(), s.width, s.height);
				
				Oppa += 37;
			}
			if(s.selectedSaved){
				if (!(s instanceof Perks)) {
					batch.draw(selectedSavedSlotRectangle, s.sprite.getX(), s.sprite.getY());
				}
			}
		}
		
	

		font.setColor(Color.BLACK);
		font.setScale(0.5f, 0.5f);

		
//		batch.end();
//		Gdx.gl.glEnable(GL20.GL_BLEND);
//		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//		sr = new ShapeRenderer();
//		sr.begin(ShapeType.Filled);
//		sr.setColor(0.5f, 0.5f, 0.5f, 0.5f);
		if(SlotMachineScreen.yesWasJustPressed){
			goButtonButton.setDisabled(false);
//			int ka = 0;
//			while(ka<3){
//				sr.rect(SlotMachineScreen.stage.screenToStageCoordinates(new Vector2((float) slotPositionsX[ka],
//						(float) slotPositionY)).x, SlotMachineScreen.stage.screenToStageCoordinates(new Vector2(
//						(float) slotPositionsX[ka], (float) slotPositionY)).y, 146, 146);
//				ka++;
//			}
		}else{
			goButtonButton.setDisabled(true);
		}
//		sr.end();
//		batch.begin();
	}

	// Method to be used whenever the slot Machine page is to be loaded.
	private static SlotMachineTextures smt;
	
	public static SlotMachineTextures getSlotMachine(Player player){
		if(smt!=null){
			smt = new SlotMachineTextures(player);
		}
		return smt;
	}
}
