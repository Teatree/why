package com.me.swampmonster.slotMachineStuff;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.slots.ImproveArrowDamage;
import com.me.swampmonster.models.slots.ImproveMaxHealth;
import com.me.swampmonster.models.slots.ImproveMaxOxygen;
import com.me.swampmonster.models.slots.ImproveMovementSpeed;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.GeneralUtils;
import com.me.swampmonster.utils.SlotsGenerator;

public class SlotMachineTextures extends Group {
	private BitmapFont font;
	private static SlotsGenerator slotsGen;
	public Slot[] slots;
	public static Map<Integer, Sprite> slotLevelPic;
	int [] slotPositionsX = {159, 328, 497};
	int slotPositionY = 174;
	public Sprite slotMachineWindow;
	public Sprite slotMachineWindowYes;
	public Sprite rerollButton;
	public Sprite goButton;
	public Sprite slotMachineWindowNo;
	public Sprite savedSlotBar;
	public Sprite slotLevel1;
	public Sprite slotLevel2;
	public Sprite slotLevel3;
	public Sprite slotLevel4;
	public Sprite slotLevel5;
	public Sprite selectedSavedSlotRectangle;
	public ShapeRenderer sr;
	public Rectangle yes;
	public Rectangle no;
	public AnimationControl animantionCtlr;
	public AnimationControl animantionSavedSelectedCtlr;
	public static int animSavedSelectedCounter;
	public Sprite selectedSavedSlotFrame;
	public boolean[] notAnimating;
	public static boolean peru;
	public Slot selectedSlot;
	public int selectedSlotNumber;
	
	public int animCounter;
	public int animSlotCounter = 50;
	public float animDx;
	public float animDy;
//	public static float width = 146;
//	public static float height = 146;
	public static float slotAnimSpeed;
	
	private Skin skin;
	public ImageButton goButtonButton;

	public SlotMachineTextures(Player player) {
		skin = new Skin(Gdx.files.internal("skins\\style.json"), new TextureAtlas(Gdx.files.internal("skins\\main.pack")));
		goButtonButton = new ImageButton(skin);
		
		font = Assets.manager.get(Assets.font);
		slotsGen = SlotsGenerator.getSlotGenerator();
		
		slotMachineWindow = new Sprite(Assets.manager.get(Assets.slotMachineWindow));
		slotMachineWindowYes = new Sprite(Assets.manager.get(Assets.slotMachineWindowYes));
		slotMachineWindowNo = new Sprite(Assets.manager.get(Assets.slotMachineWindowNo));
		rerollButton = new Sprite(Assets.manager.get(Assets.slotMachineWindowYes));
		goButton = new Sprite (Assets.manager.get(Assets.goButton));
		selectedSavedSlotRectangle = new Sprite (Assets.manager.get(Assets.selectedSavedSlot));
		savedSlotBar = new Sprite(Assets.manager.get(Assets.saveSlotBar));
		
		animantionCtlr = new AnimationControl(Assets.manager.get(Assets.slotAnimation), 8, 1, 8);
		animantionSavedSelectedCtlr = new AnimationControl(Assets.manager.get(Assets.addedSavedSlotAnimation), 4, 1, 3.8f);
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
		
		yes = new Rectangle();
		yes.width = slotMachineWindowYes.getWidth();
		yes.height = slotMachineWindowYes.getHeight();
		no = new Rectangle();
		no.width = slotMachineWindowNo.getWidth();
		no.height = slotMachineWindowNo.getHeight();
		
//		animSavedSelectedCounter = 0;
		
		slots = new Slot[3];
		
		generateSlots(player);
	}

	public void generateSlots(Player player) {
		Slot temp;
		Random r = new Random();
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
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(Assets.manager.get(Assets.slotMachineCase), 144, 112);
		
		rerollButton.setSize(90, 90);
		rerollButton.setPosition(Constants.VIEWPORT_GUI_WIDTH*0.45f, Constants.VIEWPORT_GUI_HEIGHT*0.18f);
		batch.draw(rerollButton, Constants.VIEWPORT_GUI_WIDTH*0.45f, Constants.VIEWPORT_GUI_HEIGHT*0.18f);
		
		batch.draw(savedSlotBar, 0, -26);
		
		goButton.setSize(100, 100);
		goButton.setPosition(Constants.VIEWPORT_GUI_WIDTH*0.85f, Constants.VIEWPORT_GUI_HEIGHT*0.05f);
		batch.draw(goButton, Constants.VIEWPORT_GUI_WIDTH*0.85f, Constants.VIEWPORT_GUI_HEIGHT*0.05f, goButton.getWidth(), goButton.getHeight());
		
		animantionCtlr.doComplexAnimation(0, 1f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
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
		
		int Oppa = 5;
		for(Slot s: SlotMachineScreen.savedSlots){
			if(s.state == State.SPAWNING){
				if(animSavedSelectedCounter<90){
					animSavedSelectedCounter++;
	//				System.out.println("boom");
					animantionSavedSelectedCtlr.animate(0);
					selectedSavedSlotFrame = new Sprite(animantionSavedSelectedCtlr.getCurrentFrame());
					System.out.println("animSavedSelected: " + animSavedSelectedCounter + " slot: " + s);
				}
				if(animSavedSelectedCounter == 90){
					s.state = State.STANDARD;
					animSavedSelectedCounter = 0;
				}
				batch.draw(selectedSavedSlotFrame, s.sprite.getX(), s.sprite.getY()+20);
			}
			if(s.state != State.ANIMATING){
				s.sprite.setPosition(Oppa, 3);
				s.sprite.setSize(32, 32);
				s.sprite.setX(Oppa);
				s.sprite.setY(3);
				batch.draw(s.sprite, s.sprite.getX(), s.sprite.getY(), s.sprite.getWidth(), s.sprite.getHeight());
				Oppa += s.sprite.getWidth()+5;
	//			System.out.println("Oppa " + Oppa + " spriteSize " + s.sprite.getWidth());
			}else{
				s.savedSlotPosition = new Vector2(Oppa, 3);
				if(selectedSlot!=null && s.selected){
					if(animSlotCounter>0){
						animSlotCounter--;
					}
					if(animSlotCounter==49){
						s.position = new Vector2(selectedSlot.sprite.getX(), selectedSlot.sprite.getY());
						System.out.println("rewrite the fuckign pos! " + s.position);
						System.err.println("position: " + s.position);
						s.width = 146;
						s.height = 146;
						animDx = /*selectedSlot.sprite.getX() -*/ s.savedSlotPosition.x - selectedSlot.sprite.getX();
						animDy = /*selectedSlot.sprite.getY() -*/ s.savedSlotPosition.y - selectedSlot.sprite.getY();
						
						float length1 = (float) Math.sqrt(animDx * animDx + animDy * animDy);
						slotAnimSpeed = length1 / 50;
						
						animDx = animDx /= length1;
						animDy = animDy /= length1;
					}
					if(animSlotCounter<=1 && animSavedSelectedCounter==0){
						animSlotCounter=50;
						s.state = State.SPAWNING;
					}
				}
				if(animSlotCounter>0 && animSlotCounter<=49 && s.selected){
					s.update(animDx, animDy, animSlotCounter);
				}
				batch.draw(s.sprite, s.sprite.getX(), s.sprite.getY(), s.width, s.height);
				
				Oppa += 37;
			}
			if(s.selectedSaved){
				System.out.println("yes savedselected");
				batch.draw(selectedSavedSlotRectangle, s.sprite.getX(), s.sprite.getY());
			}
		}
		
		while (i < 3) {
			if (notAnimating[i]) {
				Slot slot = slots[i];
				slot.sprite.setPosition(slotPositionsX[i], slotPositionY);
				i++;
				slot.sprite.setSize(146, 146);
				slot.sprite.draw(batch);
				try {
					Sprite s;
					if(slot.selected && SlotMachineScreen.yesWasJustPressed){
						s = new Sprite(slotLevelPic.get(slot.getClass()
								.getField("level").getInt(null)-1));
					}else{
						s = new Sprite(slotLevelPic.get(slot.getClass()
								.getField("level").getInt(null)));
					}
					s.setPosition(slot.sprite.getX(), slot.sprite.getY());
					s.setSize(32, 32);
					s.draw(batch);

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

		font.setColor(Color.BLACK);
		font.setScale(0.5f, 0.5f);
		// font.draw(batch, "MaxHP: " + p.getMaxHealth(), 284, 215);
		// font.draw(batch, "MaxO2: " + p.maxOxygen, 284, 200);
		// font.draw(batch, "Damage: " + p.damage, 284, 185);
		// font.draw(batch, "AS: " + p.shotCoolDown, 284, 170);
		// font.draw(batch, "Score: " + p.points, 156, 338);

		if (peru && !SlotMachineScreen.yesWasJustPressed) {
			slotMachineWindow.setSize(Constants.VIEWPORT_WIDTH / 2.1f,
					Constants.VIEWPORT_HEIGHT / 1.4f);
			slotMachineWindow.draw(batch);
			slotMachineWindow.setPosition(Constants.VIEWPORT_WIDTH / 3.5f,
					Constants.VIEWPORT_GUI_HEIGHT / 7);
			slotMachineWindowYes.setSize(90, 90);
			slotMachineWindowYes.draw(batch);
			slotMachineWindowYes.setPosition(
					Constants.VIEWPORT_WIDTH / 3.5f + 10,
					Constants.VIEWPORT_GUI_HEIGHT / 6.5f);
			yes.setPosition(new Vector2(slotMachineWindowYes.getX(),
					slotMachineWindowYes.getY()));
			slotMachineWindowNo.setSize(90, 90);
			slotMachineWindowNo.draw(batch);
			slotMachineWindowNo.setPosition(Constants.VIEWPORT_WIDTH / 1.575f,
					Constants.VIEWPORT_GUI_HEIGHT / 6.5f);
			no.setPosition(new Vector2(slotMachineWindowNo.getX(),
					slotMachineWindowNo.getY()));
			if (selectedSlot != null && selectedSlot.selected){
				font.draw(batch, selectedSlot.getDescription(),
					slotMachineWindow.getBoundingRectangle().x + 25,
					slotMachineWindow.getBoundingRectangle().y + 200);
			} else if (selectedSlot != null && selectedSlot.selectedSaved){
				font.draw(batch, selectedSlot.getDescriptionForSaved(),
						slotMachineWindow.getBoundingRectangle().x + 25,
						slotMachineWindow.getBoundingRectangle().y + 200);
			}
		}
		batch.end();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr = new ShapeRenderer();
		sr.begin(ShapeType.Filled);
		sr.setColor(0.5f, 0.5f, 0.5f, 0.5f);
		if(SlotMachineScreen.yesWasJustPressed){
			int ka = 0;
			while(ka<3){
				sr.rect(slotPositionsX[ka], slotPositionY, 146, 146);
				ka++;
			}
		}else{
			sr.rect(goButton.getX(), goButton.getY(), goButton.getWidth(), goButton.getHeight());
		}
		sr.end();
		batch.begin();
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
