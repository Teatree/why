package com.me.swampmonster.game;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.items.Weapon;
import com.me.swampmonster.models.slots.Perks;
import com.me.swampmonster.models.slots.Slot;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.slotMachineStuff.SlotMachineTextures;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class WeaponUIWindow extends Dialog{

	public Table tabl1;
	public Table tabl2;
	public Image wepPicture1;
	public Image wepPicture2;
	public Label header1;
	public Label header2;
	public TextButton keepButton;
	public TextButton takeButton;
	public Weapon wep;
	
	public WeaponUIWindow(String title, Skin skin, Weapon weapon) {
		super("", skin);
		
		wep = weapon;
		
		tabl1 = new Table();
		tabl2 = new Table();
		wepPicture1 = new Image(L1.player.weapon.sprite);
		wepPicture2 = new Image(weapon.sprite.getTexture());
		header1 = new Label(L1.player.weapon.name, skin);
		header2 = new Label(weapon.name, skin);
		keepButton = new TextButton("KEEP", skin);
		takeButton = new TextButton("TAKE", skin);
		
		tabl1.add(header1);
		tabl1.add(wepPicture1);
		tabl2.add(header2);
		tabl2.add(wepPicture2);
		
		getContentTable().add(tabl1).left();
		getContentTable().add(tabl2).right();
		
		button(keepButton, "Keep");
		button(takeButton, "Take");
	}
	
	@Override
	protected void result(Object object) {
		if(object == "Take"){
			L1.player.weapon = wep;
		}
			
		TheController.paused = false;
		super.result(object);
	}
	
}
