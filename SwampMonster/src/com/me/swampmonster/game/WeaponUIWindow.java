package com.me.swampmonster.game;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.items.Weapon;

public class WeaponUIWindow extends Dialog{

	public Table currentTabl;
	public Table newTabl;
	public Image currentWepPicture;
	public Image newWepPicture;
	public Label currentHeader;
	public Label newHeader;
	public TextButton keepButton;
	public TextButton takeButton;
	public Weapon wep;
	
	public Label currentMod1Name;
	public Label currentMod2Name;
	public Label newMod1Name;
	public Label newMod2Name;
	
	public Label currentMod1Desc;
	public Label currentMod2Desc;
	public Label newMod1Desc;
	public Label newMod2Desc;
	
	public WeaponUIWindow(String title, Skin skin, Weapon weapon) {
		super("", skin);
		
		wep = weapon;
		
		currentTabl = new Table();
		newTabl = new Table();
		currentWepPicture = new Image(L1.player.weapon.sprite);
		newWepPicture = new Image(weapon.sprite.getTexture());
		currentHeader = new Label(L1.player.weapon.name, skin);
		newHeader = new Label(weapon.name, skin);
		keepButton = new TextButton("KEEP", skin);
		takeButton = new TextButton("TAKE", skin);
		
		currentTabl.add(currentHeader).row();
		currentTabl.add(currentWepPicture).row();
		newTabl.add(newHeader).row();
		newTabl.add(newWepPicture).row();
		
		System.out.println(L1.player.weapon.mod1);
		if (L1.player.weapon.mod1 != null){
			currentMod1Name = new Label(L1.player.weapon.mod1.name, skin);
			currentMod1Desc = new Label(L1.player.weapon.mod1.descriptio, skin);
			currentTabl.add(currentMod1Name).row();
			currentTabl.add(currentMod1Desc).row();
		}
		if (L1.player.weapon.mod2 != null){
			currentMod2Name = new Label(L1.player.weapon.mod2.name, skin);
			currentMod2Desc = new Label(L1.player.weapon.mod2.descriptio, skin);
			currentTabl.add(currentMod2Name).row();
			currentTabl.add(currentMod2Desc).row();
		}
		
		if (weapon.mod1 != null){
			newMod1Name = new Label(weapon.mod1.name, skin);
			newMod1Desc = new Label(weapon.mod1.descriptio, skin);
			newTabl.add(newMod1Name).row();
			newTabl.add(newMod1Desc).row();
		}
		if (weapon.mod2 != null){
			newMod2Name = new Label(weapon.mod2.name, skin);
			newMod2Desc = new Label(weapon.mod2.descriptio, skin);
			newTabl.add(newMod2Name).row();
			newTabl.add(newMod2Desc).row();
		}
		
		getContentTable().add(currentTabl).left();
		getContentTable().add(newTabl).right();
		
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
