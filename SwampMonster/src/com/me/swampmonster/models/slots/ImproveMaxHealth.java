package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.AssetsMainManager;

public class ImproveMaxHealth extends Slot{

	public ImproveMaxHealth() {
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.IMPROVEMAXHEALTH_ICON));
	}
	
	public void execute (Player player){
		//
	}
}
