package com.me.swampmonster.slotMachineStuff;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.me.swampmonster.models.slots.Slot;

public class SlotMiniWindow extends Window{

	private Image image;
	private Label textDesc;
	private Label textName;
	private Table contentTable;
	
	public boolean theWindowIsShown;
	
	public SlotMiniWindow(String title, Skin skin, String style, final Slot slot) {
		super("", skin, style);
		
		try {
			image = new Image(SlotMachineTextures.slotLevelPic.get(slot.getClass().getField("level").getInt(null)-1));
		} catch (Exception e) {
		}
		contentTable = new Table();
		textDesc = new Label(slot.getDescription(), skin);
		textDesc.setWrap(true);
		textName = new Label(slot.name, skin);
		textName.setWrap(true);
		
		contentTable.add(textName).top().left().pad(10).width(this.getWidth());
		contentTable.add(image).size(32).top().right().pad(10).row();
		contentTable.add(textDesc).expand().top().left().pad(10).width(this.getWidth());
		
		add(contentTable).width(250).height(200).top().right();
		
	}
}
