package com.me.swampmonster.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.utils.Assets;

public class BuzzShot extends Weapon{

	public BuzzShot() {
		super();
		weaponDescSprite = new Sprite(Assets.manager.get(Assets.buzzDesc));
	}

}
