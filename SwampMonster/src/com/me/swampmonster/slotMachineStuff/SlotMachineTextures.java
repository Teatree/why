package com.me.swampmonster.slotMachineStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.utils.AssetsMainManager;
import com.me.swampmonster.utils.SlotsGenerator;

public class SlotMachineTextures extends Group {
	private BitmapFont font;
	private Player p;
	private static SlotsGenerator slotsGen;
	public Slot slot1 ;
	public Slot slot2 ;
	public Slot slot3 ;
	
	public SlotMachineTextures(Player player) {
		this.p = player;
		font = AssetsMainManager.manager.get(AssetsMainManager.font);
		slotsGen = SlotsGenerator.getSlotGenerator();
		
		slot1 =  slotsGen.getSlot(0, 15);
		slot2 =  slotsGen.getSlot(0, 15);
		slot3 =  slotsGen.getSlot(0, 15);
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
		batch.draw(slot1.sprite, 250, 300, 100, 100);
		batch.draw(slot2.sprite, 350, 300, 100, 100);
		batch.draw(slot3.sprite, 580, 300, 100, 100);
		font.draw(batch, slot1.description, 300, 200);
		font.draw(batch, slot1.description, 400, 200);
		font.draw(batch, slot1.description, 560, 200);
		font.setColor(Color.BLACK);
		font.setScale(0.5f, 0.5f);
		font.draw(batch, "MaxHP: " + p.getMaxHealth(), 284, 215);
		font.draw(batch, "MaxO2: " + p.maxOxygen, 284, 200);
		font.draw(batch, "Damage: " + p.damage, 284, 185);
		font.draw(batch, "AS: " + p.shotCoolDown, 284, 170);
		font.draw(batch, "Score: " + p.points, 156, 338);
	}
	
	// Method to be used whenever the slot Machine page is to be loaded.
	private static SlotMachineTextures smt;
	
	public static SlotMachineTextures getSlotMachine(Player player){
		if(smt!=null){
			smt = new SlotMachineTextures(player);
		}else{
			smt.slot1 =  slotsGen.getSlot(0, 3);
			smt.slot2 =  slotsGen.getSlot(0, 3);
			smt.slot3 =  slotsGen.getSlot(0, 3);
			
		}
		return smt;
	}
}
