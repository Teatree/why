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
import com.sun.swing.internal.plaf.basic.resources.basic;

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
	public Sprite slotLevel1;
	public Sprite slotLevel2;
	public Sprite slotLevel3;
	public Sprite slotLevel4;
	public Sprite slotLevel5;
	public ShapeRenderer sr;
	public Rectangle yes;
	public Rectangle no;
	public AnimationControl animantionCtlr;
	public boolean[] notAnimating;
	public boolean peru;
	public Slot selectedSlot;
	public int selectedSlotNumber;
	public int animCounter;
	
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
		
		yes = new Rectangle();
		yes.width = slotMachineWindowYes.getWidth();
		yes.height = slotMachineWindowYes.getHeight();
		no = new Rectangle();
		no.width = slotMachineWindowNo.getWidth();
		no.height = slotMachineWindowNo.getHeight();
		
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
		
		goButton.setSize(100, 100);
		goButton.setPosition(Constants.VIEWPORT_GUI_WIDTH*0.85f, Constants.VIEWPORT_GUI_HEIGHT*0.05f);
		batch.draw(goButton, Constants.VIEWPORT_GUI_WIDTH*0.85f, Constants.VIEWPORT_GUI_HEIGHT*0.05f, goButton.getWidth(), goButton.getHeight());
		
		goButton.draw(batch);
		
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
		
		int Oppa = 32;
		for(Slot s: SlotMachineScreen.savedSlots){
			s.sprite.setPosition(Oppa, 10);
			s.sprite.setSize(32, 32);
			s.sprite.setX(Oppa);
			s.sprite.setY(10);
			batch.draw(s.sprite, s.sprite.getX(), s.sprite.getY(), s.sprite.getWidth(), s.sprite.getHeight());
			Oppa += s.sprite.getWidth()+5;
//			System.out.println("Oppa " + Oppa + " spriteSize " + s.sprite.getWidth());
		}
		
		while (i < 3) {
			if (notAnimating[i]) {
				Slot slot = slots[i];
				slot.sprite.setPosition(slotPositionsX[i], slotPositionY);
				i++;
				slot.sprite.setSize(146, 146);
				slot.sprite.draw(batch);
				try {
					Sprite s = new Sprite(slotLevelPic.get(slot.getClass()
							.getField("level").getInt(null)));
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

		if (peru) {
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
			if (selectedSlot.selected){
				font.draw(batch, selectedSlot.getDescription(),
					slotMachineWindow.getBoundingRectangle().x + 25,
					slotMachineWindow.getBoundingRectangle().y + 200);
			} else if (selectedSlot.selectedSaved){
				font.draw(batch, selectedSlot.getDescriptionForSaved(),
						slotMachineWindow.getBoundingRectangle().x + 25,
						slotMachineWindow.getBoundingRectangle().y + 200);
			}
		}
		batch.end();
		if(SlotMachineScreen.yesWasJustPressed){
			sr = new ShapeRenderer();
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			sr.begin(ShapeType.Filled);
			sr.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			int ka = 0;
			while(ka<3){
				sr.rect(slotPositionsX[ka], slotPositionY, 146, 146);
				ka++;
			}
			sr.end();
		}
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
