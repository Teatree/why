package com.me.swampmonster.slotMachineStuff;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.slots.ImproveArrowDamage;
import com.me.swampmonster.models.slots.ImproveMaxHealth;
import com.me.swampmonster.models.slots.ImproveMaxOxygen;
import com.me.swampmonster.models.slots.ImproveMovementSpeed;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;
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
	public Sprite slotMachineWindowNo;
	public Sprite slotLevel1;
	public Sprite slotLevel2;
	public Sprite slotLevel3;
	public Sprite slotLevel4;
	public Sprite slotLevel5;
	public Rectangle yes;
	public Rectangle no;
	public AnimationControl animantionCtlr;
	public boolean[] notAnimating;
	public boolean peru;
	public Slot selectedSlot;
	public int selectedSlotNumber;
	public int animCounter;
	
	public SlotMachineTextures(Player player) {
		font = Assets.manager.get(Assets.font);
		slotsGen = SlotsGenerator.getSlotGenerator();
		
		slotMachineWindow = new Sprite(Assets.manager.get(Assets.slotMachineWindow));
		slotMachineWindowYes = new Sprite(Assets.manager.get(Assets.slotMachineWindowYes));
		slotMachineWindowNo = new Sprite(Assets.manager.get(Assets.slotMachineWindowNo));
		
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
		
		boolean argentina = false;
		Random r = new Random();
		Slot temp;
		boolean madagascar = false;
		
		Set<Slot>slotsSet = new HashSet<Slot>();
		
		if (ImproveArrowDamage.level == 4 
				&& ImproveArrowDamage.level == 4
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
			} 
			else 
			{
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
			slots = slotsSet.toArray(slots); 
			shuffle(slots);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(Assets.manager
				.get(Assets.slotMachineCase), 144, 112);
		
		
		animantionCtlr.doComplexAnimation(0,
				1f, Gdx.graphics.getDeltaTime(), Animation.NORMAL);
		

		int i = 0;
//		Iterator<Slot> itr = slots.iterator();
		
		while (i<3) {
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
			}
			else
			{
				batch.draw(animantionCtlr.getCurrentFrame(), slotPositionsX[i], slotPositionY, 146, 146);
				animCounter++;
				
				if(animCounter == 25){
					notAnimating[i] = true;
				}
				if(animCounter == 50){
					notAnimating[i] = true;
				}
				if(animCounter == 75){
					notAnimating[i] = true;
				}
				i++;
			}
		}
		
		font.setColor(Color.BLACK);
		font.setScale(0.5f, 0.5f);
//		font.draw(batch, "MaxHP: " + p.getMaxHealth(), 284, 215);
//		font.draw(batch, "MaxO2: " + p.maxOxygen, 284, 200);
//		font.draw(batch, "Damage: " + p.damage, 284, 185);
//		font.draw(batch, "AS: " + p.shotCoolDown, 284, 170);
//		font.draw(batch, "Score: " + p.points, 156, 338);
		
		if (peru) {
			slotMachineWindow.setSize(Constants.VIEWPORT_WIDTH/2.1f, Constants.VIEWPORT_HEIGHT/1.4f);
			slotMachineWindow.draw(batch);
			slotMachineWindow.setPosition(Constants.VIEWPORT_WIDTH/3.5f, Constants.VIEWPORT_GUI_HEIGHT/7);
			slotMachineWindowYes.setSize(90,90);
			slotMachineWindowYes.draw(batch);
			slotMachineWindowYes.setPosition(Constants.VIEWPORT_WIDTH/3.5f + 10, Constants.VIEWPORT_GUI_HEIGHT/6.5f);			
			yes.setPosition(new Vector2(slotMachineWindowYes.getX(), slotMachineWindowYes.getY()));
			slotMachineWindowNo.setSize(90,90);
			slotMachineWindowNo.draw(batch);
			slotMachineWindowNo.setPosition(Constants.VIEWPORT_WIDTH/1.575f, Constants.VIEWPORT_GUI_HEIGHT/6.5f);
			no.setPosition(new Vector2(slotMachineWindowNo.getX(), slotMachineWindowNo.getY()));
			font.draw(batch, selectedSlot.getDescription(), slotMachineWindow.getBoundingRectangle().x+25, slotMachineWindow.getBoundingRectangle().y+200);
			
		}
		
	}
	
	// Method to be used whenever the slot Machine page is to be loaded.
	private static SlotMachineTextures smt;
	
	public static SlotMachineTextures getSlotMachine(Player player){
		if(smt!=null){
			smt = new SlotMachineTextures(player);
		}
		return smt;
	}
	private void shuffle(Slot[] slots)
	{
		Random rand = new Random();
	    for (int i = 0; i < slots.length; i++)
	    {
	        int swap = rand.nextInt(i + 1);
	        Slot temp = slots[swap];
	        slots[swap] = slots[i];
	        slots[i] = temp;
	    }
	}
}
