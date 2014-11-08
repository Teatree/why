package com.me.swampmonster.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.utils.Assets;

public class SlingShot extends Weapon{

	public SlingShot() {
		super();
		weaponDescSprite = new Sprite(Assets.manager.get(Assets.slingShotDesc));
	}
	
	

}
