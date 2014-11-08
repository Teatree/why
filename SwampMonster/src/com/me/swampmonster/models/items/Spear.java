package com.me.swampmonster.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.utils.Assets;

public class Spear extends Weapon{

	public Spear() {
		super();
		weaponDescSprite = new Sprite(Assets.manager.get(Assets.spearDesc));
	}
	
}
