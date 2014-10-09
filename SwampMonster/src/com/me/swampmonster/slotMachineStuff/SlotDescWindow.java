package com.me.swampmonster.slotMachineStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.utils.Constants;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

public class SlotDescWindow extends Dialog {

	public Slot slot;
	private Image image;

	public SlotDescWindow(String title, Skin skin, final Slot slot) {
		super(title, skin);
		this.slot = slot;
		fadeDuration = 0;
		image = new Image(slot.sprite.getTexture());
		ImageButton yesButton = new ImageButton(skin, "yes");
		ImageButton noButton = new ImageButton(skin, "no");
		Label text = new Label(slot.getDescription()+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", skin);
		Label text2 = new Label(slot.getDescription(), skin);
		text.setWrap(true);
		
		row();
//		getButtonTable().add(yesButton).padRight(101);
		button(yesButton, "penis sandwich");
//		getButtonTable().columnDefaults(2);
		button(noButton, "poop nugget");
//		getButtonTable().padLeft(200);
		getButtonTable().getCell(yesButton).padLeft(100);
		getButtonTable().getCell(noButton).padLeft(this.getWidth());
//		getButtonTable().getCell(noButton).padLeft(this.getWidth()-image.getWidth());
		row();
		getContentTable().add(image).size(100);
		getContentTable().left();
		getContentTable().top();
		columnDefaults(2);
		getContentTable().add(text2).size(230, 25).padTop(1);
		getContentTable().row();
		getContentTable().add(text).size(230,100).padTop(10);

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
