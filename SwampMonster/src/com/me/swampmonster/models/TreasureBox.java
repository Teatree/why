package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.MisterItemSpawner;

public class TreasureBox extends Prop{
	public TreasureBox(Vector2 position){
		this.position = position;
		sprite = new Sprite(Assets.manager.get(Assets.SCAREDNEGATIVEEFFECT_ICON));
		sprite.setSize(32, 32);
	}
	
	public void toDoSomething(AbstractGameObject abs){
		L1.items.add(MisterItemSpawner.spawnPropsItem(L1.player, this));
	}
}
