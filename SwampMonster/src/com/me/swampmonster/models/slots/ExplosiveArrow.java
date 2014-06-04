package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.AssetsMainManager;

public class ExplosiveArrow extends Slot{
	
	public ExplosiveArrow() {
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.EXPLOSIVE_ARROW_ICON));
	}
	
	public void execute(Player player){
		// noting
	}
}
