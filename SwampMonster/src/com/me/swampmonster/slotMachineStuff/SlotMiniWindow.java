package com.me.swampmonster.slotMachineStuff;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.me.swampmonster.models.slots.Slot;

public class SlotMiniWindow extends Window{

	private Image image;
	private Label Text;
	
	public SlotMiniWindow(String title, Skin skin, String style, final Slot slot) {
		super("", skin, style);
		
		image = new Image(slot.sprite.getTexture());
		Text = new Label(slot.getDescription() + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", skin);
		Text.setWrap(true);
		
		add(image).size(100);
		add(Text).fill().top().left();
		Text.setWrap(true);
		
		this.top().left();
	}
	
}
