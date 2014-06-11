package com.me.swampmonster.slotMachineStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.utils.AssetsMainManager;
import com.me.swampmonster.utils.Constants;
import com.me.swampmonster.utils.SlotsGenerator;

public class SlotMachineTextures extends Group {
	private BitmapFont font;
	private ShapeRenderer sr;
	private Player p;
	private static SlotsGenerator slotsGen;
	public Slot[] slots;
	public Sprite slotMachineWindow;
	public Sprite slotMachineWindowYes;
	public Sprite slotMachineWindowNo;
	public Rectangle yes;
	public Rectangle no;
	public boolean peru;
	public int selectedSlotNumber;
	
	public SlotMachineTextures(Player player) {
		this.p = player;
		font = AssetsMainManager.manager.get(AssetsMainManager.font);
		slotsGen = SlotsGenerator.getSlotGenerator();
		
		slotMachineWindow = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.slotMachineWindow));
		slotMachineWindowYes = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.slotMachineWindowYes));
		slotMachineWindowNo = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.slotMachineWindowNo));
		yes = new Rectangle();
		yes.width = slotMachineWindowYes.getWidth();
		yes.height = slotMachineWindowYes.getHeight();
		no = new Rectangle();
		no.width = slotMachineWindowNo.getWidth();
		no.height = slotMachineWindowNo.getHeight();
		
		sr = new ShapeRenderer();
		
		slots = new Slot[3];
		slots[0] =  slotsGen.getSlot(0, 15);
		slots[1] =  slotsGen.getSlot(0, 15);
		slots[2] =  slotsGen.getSlot(0, 15);
		
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(AssetsMainManager.manager
				.get(AssetsMainManager.slotMachineCase), 144, 112);
		batch.draw(AssetsMainManager.manager
				.get(AssetsMainManager.slotMachineNextButton), 535, 125,
				0.10f * Gdx.graphics.getWidth(), 0.20f * Gdx.graphics
						.getHeight());
		batch.end();
		
		sr.begin(ShapeType.Filled);
		sr.rect(slots[0].sprite.getBoundingRectangle().x,
				slots[0].sprite.getBoundingRectangle().y,
				slots[0].sprite.getBoundingRectangle().width,
				slots[0].sprite.getBoundingRectangle().height);
		sr.rect(slots[1].sprite.getBoundingRectangle().x,
				slots[1].sprite.getBoundingRectangle().y,
				slots[1].sprite.getBoundingRectangle().width,
				slots[1].sprite.getBoundingRectangle().height);
		sr.rect(slots[2].sprite.getBoundingRectangle().x,
				slots[2].sprite.getBoundingRectangle().y,
				slots[2].sprite.getBoundingRectangle().width,
				slots[2].sprite.getBoundingRectangle().height);
		sr.end();
		batch.begin();
		
//		batch.draw(slots[0].sprite, 250, 300, 100, 100);
		slots[0].sprite.setPosition(285, 250);
		slots[0].sprite.setSize(100, 100);
		slots[0].sprite.draw(batch);
//		batch.draw(slots[1].sprite, 350, 300, 100, 100);
		slots[1].sprite.setPosition(415, 250);
		slots[1].sprite.setSize(100, 100);
		slots[1].sprite.draw(batch);
//		batch.draw(slots[2].sprite, 580, 300, 100, 100);
		slots[2].sprite.setPosition(540, 250);
		slots[2].sprite.setSize(100, 100);
		slots[2].sprite.draw(batch);
		
		font.setColor(Color.BLACK);
		font.setScale(0.5f, 0.5f);
		font.draw(batch, "MaxHP: " + p.getMaxHealth(), 284, 215);
		font.draw(batch, "MaxO2: " + p.maxOxygen, 284, 200);
		font.draw(batch, "Damage: " + p.damage, 284, 185);
		font.draw(batch, "AS: " + p.shotCoolDown, 284, 170);
		font.draw(batch, "Score: " + p.points, 156, 338);
		
		if (peru) {
//			game.setScreen(new SwampScreen(game));
			slotMachineWindow.draw(batch);
			slotMachineWindow.setPosition(Constants.VIEWPORT_WIDTH/3, Constants.VIEWPORT_GUI_HEIGHT/3);
			slotMachineWindowYes.draw(batch);
			slotMachineWindowYes.setPosition(Constants.VIEWPORT_WIDTH/3, Constants.VIEWPORT_GUI_HEIGHT/3);			
			yes.setPosition(new Vector2(slotMachineWindowYes.getX(), slotMachineWindowYes.getY()));
			slotMachineWindowNo.draw(batch);
			slotMachineWindowNo.setPosition(Constants.VIEWPORT_WIDTH/3+192, Constants.VIEWPORT_GUI_HEIGHT/3);
			no.setPosition(new Vector2(slotMachineWindowNo.getX(), slotMachineWindowNo.getY()));
			font.draw(batch, slots[selectedSlotNumber].description, slotMachineWindow.getBoundingRectangle().x+25, slotMachineWindow.getBoundingRectangle().y+100);
			
		}
		
	}
	
	// Method to be used whenever the slot Machine page is to be loaded.
	private static SlotMachineTextures smt;
	
	public static SlotMachineTextures getSlotMachine(Player player){
		if(smt!=null){
			smt = new SlotMachineTextures(player);
		}else{
			smt.slots[0] =  slotsGen.getSlot(0, 3);
			smt.slots[1] =  slotsGen.getSlot(0, 3);
			smt.slots[2] =  slotsGen.getSlot(0, 3);
		}
		return smt;
	}
}
