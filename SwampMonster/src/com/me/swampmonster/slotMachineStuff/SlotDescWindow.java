package com.me.swampmonster.slotMachineStuff;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.utils.Constants;

public class SlotDescWindow extends Dialog {

	public Slot slot;
	private Image image;
	private Image imageLevel;
	
	public SlotDescWindow(String title, Skin skin, final Slot slot) {
		super("", skin);
		
		this.slot = slot;
//		fadeDuration = 0;
		image = new Image(slot.sprite.getTexture());
		try {
			imageLevel = new Image(SlotMachineTextures.slotLevelPic.get(slot.getClass().getField("level").getInt(null)));
		} catch (Exception e) {
			
		}
		imageLevel.setSize(16, 16);
		ImageButton yesButton = new ImageButton(skin, "yes");
		ImageButton noButton = new ImageButton(skin, "no");
		Label text = new Label(slot.getDescription(), skin);
		text.setWrap(true);
		Label textI = new Label("a 19", skin, "stats");
		Label textO = new Label("g 11", skin, "stats");
		Label textP = new Label("m 1(+1)", skin, "stats");
		Label text2 = new Label(slot.name, skin);
		text.setWrap(true);
		
		row();
		button(yesButton, "penis sandwich");
		button(noButton, "poop nugget");
		getButtonTable().getCell(yesButton).padLeft(105);
		getButtonTable().getCell(noButton).padLeft(this.getWidth());
		row();
		
		Table firstColumn = new Table();
		firstColumn.add(image).size(100).left().top().row();
//		for(String sS: slot.getStats(L1.player)){
			firstColumn.add(textI).left().top().row();
			firstColumn.add(textO).left().top().row();
			firstColumn.add(textP).left().top().row();
//		}
		System.out.println((float)(this.getHeight()));
		getContentTable().add(firstColumn).top();
		
		columnDefaults(1);
		Table secondColumn = new Table();
		System.out.println(this.getHeight() + " - " + this.getWidth());
		secondColumn.add(text2).padLeft(5).height((float)((Constants.SLOT_DESC_WINDOW_HEIGHT-getButtonTable().getHeight())*0.13f)).
		width((float)((Constants.SLOT_DESC_WINDOW_WIDTH*0.6))).bottom();
		
		secondColumn.add(imageLevel).bottom().padBottom(5).height((float)((Constants.SLOT_DESC_WINDOW_HEIGHT-getButtonTable().getHeight())*0.08f)).
		width((float)((Constants.SLOT_DESC_WINDOW_WIDTH*0.1))).row();
		
//		secondColumn.add(text).colspan(2).height((float)((Constants.SLOT_DESC_WINDOW_HEIGHT-getButtonTable().getHeight())*0.73f)).
//		width((float)((Constants.SLOT_DESC_WINDOW_WIDTH*0.7))).top().left();
		secondColumn.add(text).colspan(2).fill().top().left();
		getContentTable().add(secondColumn).top();
		getContentTable().top();
		
		firstColumn.debug();
		secondColumn.debug();
	}

	@Override
	protected void result(Object object) {
		if (object == "penis sandwich") {
			SlotMachineScreen.yesWasJustPressed = true;
			System.out.println("pressed it");

			if (slot instanceof Perks) {
				slot.execute(L1.player);
				try {
					int i = slot.getClass().getField("level").getInt(null);
					if (i < Constants.Max_slot_level) {
						i++;
						slot.getClass().getField("level").setInt(null, i);
					}
				} catch (Exception e) {
				}

			} else {
				TheController.skill = slot;
				try {
					int i = TheController.skill.getClass().getField("level").getInt(null);
					if (i < Constants.Max_slot_level) {
						i++;
						TheController.skill.getClass().getField("level").setInt(null, i);
					}
				} catch (Exception e) {
				}
			}
			SlotMachineTextures.peru = false;

			for (Slot s : SlotMachineScreen.savedSlots) {
				if (s.getClass().equals(slot.getClass())) {
					// DON"T TOUCH THIS!
					s.rewritten = true;
					slot.rewritten = true;
					s.state = State.SPAWNING;
					slot.state = State.SPAWNING;
					s.rewritten = true;
					s = slot;
				}
			}
			if (!slot.rewritten) {
				slot.state = State.ANIMATING;
				// slot.selected = false;
				SlotMachineScreen.savedSlots.add(slot);
			} else {
				slot.state = State.SPAWNING;
				slot.rewritten = true;
			}
			SlotMachineScreen.yesWasJustPressed = true;
		}
		if (object == "poop nugget") {
			slot.selected = false;
		}
		SlotMachineScreen.isSlotDescWindowOpen = false;
		super.result(object);
	}
}
