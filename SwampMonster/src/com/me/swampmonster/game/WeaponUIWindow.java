package com.me.swampmonster.game;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.items.Weapon;
import com.me.swampmonster.models.items.WeaponItem;

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
	public WeaponItem wepItem;
	public Label currentDamage;
	public Label newDamage;
	public Label currentCoolDown;
	public Label newCoolDown;
	
	public Label currentMod1Name;
	public Label currentMod2Name;
	public Label newMod1Name;
	public Label newMod2Name;
	
	public Label currentMod1Desc;
	public Label currentMod2Desc;
	public Label newMod1Desc;
	public Label newMod2Desc;
	
	
	public WeaponUIWindow(String title, Skin skin, WeaponItem weaponItem) {
		super("", skin);
		
		wepItem = weaponItem;
		wep = weaponItem.weapon;
		
		currentTabl = new Table();
		newTabl = new Table();
		newTabl.debug();
		currentTabl.debug();
		currentWepPicture = new Image(L1.player.weapon.weaponDescSprite);
		newWepPicture = new Image(wep.weaponDescSprite);
		currentHeader = new Label(L1.player.weapon.getName(), skin, "title");
		newHeader = new Label(wep.getName(), skin, "title");
		
		currentDamage = new Label("d" + L1.player.weapon.minDD + " - " + L1.player.weapon.maxDD, skin, "stats");
		newDamage = new Label("d" + wep.minDD + " - " + wep.maxDD, skin, "stats");
		currentCoolDown = new Label("t" + (double)Math.round((float)L1.player.weapon.coolDown/60 * 10) / 10 + " s", skin, "stats");
		newCoolDown = new Label("t" + (double)Math.round((float)wep.coolDown/60 *10) /10 + " s", skin, "stats");
		
		keepButton = new TextButton("KEEP", skin);
		takeButton = new TextButton("TAKE", skin);
		
		currentTabl.left();
		currentTabl.top();
		currentTabl.add(currentHeader).fill().row();
		currentTabl.add(currentWepPicture).left().size(100).row();
		currentTabl.add(currentDamage).left().row();
		currentTabl.add(currentCoolDown).left().row();
		currentTabl.add(new Label("", skin)).row();
		newTabl.left();
		newTabl.top();
		newTabl.add(newHeader).fill().row();
		newTabl.add(newWepPicture).left().size(100).row();
		newTabl.add(newDamage).left().row();
		newTabl.add(newCoolDown).left().row();
		newTabl.add(new Label("", skin)).row();
		 
		if (L1.player.weapon.mod1 != null){
//			currentMod1Name = new Label(L1.player.weapon.mod1.name, skin);
			currentMod1Desc = new Label(L1.player.weapon.mod1.descriptio, skin);
			currentMod1Desc.setWrap(true);
			currentTabl.add(currentMod1Desc).height(60).width(180).row();
		}
		if (L1.player.weapon.mod2 != null){
//			currentMod2Name = new Label(L1.player.weapon.mod2.name, skin);
			currentMod2Desc = new Label(L1.player.weapon.mod2.descriptio, skin);
			currentMod2Desc.setWrap(true);
			currentTabl.add(currentMod2Desc).height(60).width(180).row();
		}
		
		if (wep.mod1 != null){
//			newMod1Name = new Label(weapon.mod1.name, skin);
			newMod1Desc = new Label(wep.mod1.descriptio, skin);
			newMod1Desc.setWrap(true);
			newTabl.add(newMod1Desc).height(60).width(180).row();
		}
		if (wep.mod2 != null){
//			newMod2Name = new Label(weapon.mod2.name, skin);
			newMod2Desc = new Label(wep.mod2.descriptio, skin);
			newMod2Desc.setWrap(true);
			newTabl.add(newMod2Desc).height(60).width(180).row();
		}
		
		getContentTable().add(currentTabl).top().width(190).height(350).left();
		getContentTable().add(newTabl).top().width(190).height(350).right();
		
		button(keepButton, "Keep");
		button(takeButton, "Take");
	}
	
	@Override
	protected void result(Object object) {
		if(object == "Take"){
			L1.player.weapon = wepItem.weapon;
			wepItem.state = State.DEAD;
		}
			
		TheController.paused = false;
		super.result(object);
	}
	
}
