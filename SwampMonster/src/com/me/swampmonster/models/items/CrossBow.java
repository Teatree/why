package com.me.swampmonster.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.utils.Assets;

public class CrossBow extends Weapon{

	public CrossBow() {
		super();
		weaponDescSprite = new Sprite(Assets.manager.get(Assets.crossBowDesc));
	}

}
